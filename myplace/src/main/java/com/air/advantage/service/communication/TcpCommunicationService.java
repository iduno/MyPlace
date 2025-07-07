package com.air.advantage.service.communication;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.jboss.logging.Logger;

import com.air.advantage.cbmessages.Message;
import com.air.advantage.service.communication.config.CommunicationConfig;

import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.net.NetClient;
import io.vertx.mutiny.core.net.NetServer;
import io.vertx.mutiny.core.net.NetSocket;

public class TcpCommunicationService implements CommunicationService {
    private static final Logger LOG = Logger.getLogger(TcpCommunicationService.class);
    private final AtomicReference<NetSocket> clientSocket = new AtomicReference<>();
    private final Map<String, NetSocket> activeConnections = new ConcurrentHashMap<>();
    private NetServer server;
    private NetClient client;
    
    private final Vertx vertx;
    private final EventBus eventBus;
    private final CommunicationConfig.TcpConfig config;

    private Parser parser;
    
    public TcpCommunicationService(Vertx vertx, EventBus eventBus, CommunicationConfig config) {
        this.vertx = vertx;
        this.eventBus = eventBus;
        this.config = config.tcp();
        this.parser = new Parser();
    }
    
    @Override
    public boolean initialize() {
        return true;
    }
    
    @Override
    public boolean open() {
        try {
            if (config.isServer()) {
                return setupServer();
            } else {
                return setupClient();
            }
        } catch (Exception e) {
            LOG.error("Error opening TCP connection: " + e.getMessage(), e);
            return false;
        }
    }
    
    private boolean setupServer() {
        server = vertx.createNetServer();
        server.connectHandler(socket -> {
            String clientId = socket.remoteAddress().toString();
            LOG.info("New client connected: " + clientId);
            
            activeConnections.put(clientId, socket);
            eventBus.publish("communication-status", "connected");
            
            socket.handler(buffer -> {
                this.parser.parse(buffer);
                Message message = this.parser.pollMessage();
                while (message != null) {
                    LOG.info("Received message from client: " + message.data);
                    eventBus.publish("communication-data", message);
                    message = this.parser.pollMessage();
                }
            });
            
            socket.closeHandler(() -> {
                LOG.info("Client disconnected: " + clientId);
                activeConnections.remove(clientId);
                eventBus.publish("communication-status", "disconnected");
            });
            
            socket.exceptionHandler(e -> {
                LOG.error("Socket error: " + e.getMessage(), e);
                activeConnections.remove(clientId);
                eventBus.publish("communication-status", "disconnected");
            });
        });
        
        server.listen(config.port())
            .subscribe().with(
                netServer -> {
                    LOG.info("TCP server started on port " + config.port());
                    eventBus.publish("communication-status", "connected");
                },
                error -> {
                    LOG.error("Failed to start TCP server: " + error.getMessage(), error);
                    eventBus.publish("communication-status", "disconnected");
                }
            );
        
        return true; // Return true for the initial attempt, actual status will be reported via events
    }
    
    private boolean setupClient() {
        client = vertx.createNetClient();
        client.connect(config.port(), config.host())
            .subscribe().with(
                socket -> {
                    LOG.info("Connected to server: " + config.host() + ":" + config.port());
                    clientSocket.set(socket);
                    
                    // Notify that connection is established
                    eventBus.publish("communication-status", "connected");
                    
                    socket.handler(buffer -> {
                        this.parser.parse(buffer);
                        Message message = this.parser.pollMessage();
                        while (message != null) {
                            LOG.info("Received message from server: " + message);
                            eventBus.publish("communication-data", message);
                            message = this.parser.pollMessage();
                        }
                    });
                    
                    
                    socket.closeHandler(() -> {
                        LOG.info("Disconnected from server");
                        clientSocket.set(null);
                        eventBus.publish("communication-status", "disconnected");
                    });
                    
                    socket.exceptionHandler(e -> {
                        LOG.error("Socket error: " + e.getMessage(), e);
                        clientSocket.set(null);
                        eventBus.publish("communication-status", "disconnected");
                    });
                },
                error -> {
                    LOG.error("Failed to connect to server: " + error.getMessage(), error);
                    eventBus.publish("communication-status", "disconnected");
                }
            );
        
        return true; // Return true for the initial attempt, actual status will be reported via events
    }
    
    private int parsePort(String endpoint) {
        try {
            if (endpoint.contains(":")) {
                return Integer.parseInt(endpoint.split(":")[1]);
            } else if (endpoint.matches("\\d+")) {
                return Integer.parseInt(endpoint);
            }
        } catch (NumberFormatException e) {
            LOG.warn("Invalid port number, using default: " + config.port());
        }
        return config.port();
    }
    
    @Override
    public boolean send(Message data) {
        if (data == null) {
            return true;
        }
        if (config.isServer()) {
            if (server != null && !activeConnections.isEmpty()) {
                byte[] serializedData = new byte[4096];
                int bytesWritten = data.serialize(serializedData, 0);
                byte[] dataToSend = new byte[bytesWritten];
                System.arraycopy(serializedData, 0, dataToSend, 0, bytesWritten);
                Buffer buffer = Buffer.buffer(dataToSend);
                activeConnections.values().forEach(socket -> {
                    socket.write(buffer)
                        .subscribe().with(
                            v -> LOG.info("Sent data to client: " + socket.remoteAddress()),
                            e -> LOG.error("Failed to send data to client: " + socket.remoteAddress() + " - " + e.getMessage(), e)
                        );
                });
                return true;
            }
        } else {
            // In client mode, send to the server
            NetSocket socket = clientSocket.get();
            if (socket != null) {
                byte[] serializedData = new byte[4096]; // Adjust size as needed
                int bytesWritten = data.serialize(serializedData, 0);
                byte[] dataToSend = new byte[bytesWritten];
                System.arraycopy(serializedData, 0, dataToSend, 0, bytesWritten);
                Buffer buffer = Buffer.buffer(dataToSend);
                socket.write(buffer)
                        .subscribe().with(
                            v -> LOG.info("Sent data to client: " + socket.remoteAddress()),
                            e -> LOG.error("Failed to send data to client: " + socket.remoteAddress() + " - " + e.getMessage(), e)
                        );
                LOG.info("Sent data to server: " + data);
                return true;
            }
        }
        
        LOG.warn("Cannot send data, no connection established");
        return false;
    }
    
    @Override
    public boolean close() {
        try {
            if (config.isServer() && server != null) {
                activeConnections.values().forEach(NetSocket::close);
                activeConnections.clear();
                server.close()
                    .subscribe().with(
                        v -> LOG.info("TCP server closed"),
                        e -> LOG.error("Error closing TCP server: " + e.getMessage(), e)
                    );
            } else if (client != null) {
                NetSocket socket = clientSocket.getAndSet(null);
                if (socket != null) {
                    socket.close();
                }
                client.close();
                LOG.info("TCP client closed");
            }
            return true;
        } catch (Exception e) {
            LOG.error("Error closing TCP connection: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean isConnected() {
        if (config.isServer()) {
            return server != null && !activeConnections.isEmpty();
        } else {
            return clientSocket.get() != null;
        }
    }
    
    @Override
    public CommunicationType getType() {
        return CommunicationType.TCP;
    }
}