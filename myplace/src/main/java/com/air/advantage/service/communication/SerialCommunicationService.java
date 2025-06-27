package com.air.advantage.service.communication;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.jboss.logging.Logger;

import com.air.advantage.cbmessages.Message;
import com.air.advantage.service.communication.config.CommunicationConfig;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;

import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.eventbus.EventBus;

public class SerialCommunicationService implements CommunicationService {
    
    private static final Logger LOG = Logger.getLogger(SerialCommunicationService.class);
    private SerialPort serialPort;
    
    private final Vertx vertx;
    private final EventBus eventBus;
    private final CommunicationConfig.SerialConfig config;
    
    public SerialCommunicationService(Vertx vertx, EventBus eventBus, CommunicationConfig config) {
        this.vertx = vertx;
        this.eventBus = eventBus;
        this.config = config.serial();
    }
    
    @Override
    public boolean initialize() {
        LOG.info("Initializing Serial Communication Service");
        listAvailablePorts();
        return true;
    }
    
    public List<String> listAvailablePorts() {
        SerialPort[] ports = SerialPort.getCommPorts();
        LOG.info("Available serial ports: " + ports.length);
        
        return Arrays.stream(ports)
                .map(SerialPort::getSystemPortName)
                .peek(port -> LOG.info("Found port: " + port))
                .toList();
    }
    
    @Override
    public boolean open() {
        try {
            if (serialPort != null && serialPort.isOpen()) {
                serialPort.closePort();
            }
            
            serialPort = SerialPort.getCommPort(config.port());
            
            int stopBitValue = switch (config.stopBits()) {
                case 2 -> SerialPort.TWO_STOP_BITS;
                case 3 -> SerialPort.ONE_POINT_FIVE_STOP_BITS;
                default -> SerialPort.ONE_STOP_BIT;
            };
            
            serialPort.setComPortParameters(config.baudRate(), config.dataBits(), stopBitValue, config.parity());
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, config.timeout(), 0);
            
            boolean opened = serialPort.openPort();
            if (opened) {
                LOG.info("Successfully opened port: " + config.port());
                setupMessageListener();
                return true;
            } else {
                LOG.error("Failed to open port: " + config.port());
                return false;
            }
        } catch (Exception e) {
            LOG.error("Error opening port: " + e.getMessage(), e);
            return false;
        }
    }
    
    private void setupMessageListener() {
        serialPort.addDataListener(new SerialPortMessageListener() {
            @Override
            public byte[] getMessageDelimiter() {
                return new byte[] { '\n' }; // Using newline as message delimiter
            }
            
            @Override
            public boolean delimiterIndicatesEndOfMessage() {
                return true;
            }
            
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED | SerialPort.LISTENING_EVENT_PORT_DISCONNECTED;
            }
            
            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() == SerialPort.LISTENING_EVENT_PORT_DISCONNECTED) {
                    LOG.warn("Serial port disconnected");
                    eventBus.publish("communication-status", "disconnected");
                    return;
                }
                
                byte[] data = event.getReceivedData();
                String message = new String(data, StandardCharsets.UTF_8).trim();
                LOG.info("Received message from serial port: " + message);
                
                // Publish the message to the event bus
                eventBus.publish("communication-data", message);
            }
        });
    }
    
    @Override
    public boolean send(Message data) {
        if (serialPort != null && serialPort.isOpen()) {
            try {
                byte[] bytes = (data + "\n").getBytes(StandardCharsets.UTF_8);
                int written = serialPort.writeBytes(bytes, bytes.length);
                LOG.info("Sent " + written + " bytes to serial port");
                return written == bytes.length;
            } catch (Exception e) {
                LOG.error("Error writing to serial port: " + e.getMessage(), e);
                return false;
            }
        } else {
            LOG.warn("Cannot send data, serial port not open");
            return false;
        }
    }
    
    @Override
    public boolean close() {
        if (serialPort != null && serialPort.isOpen()) {
            boolean closed = serialPort.closePort();
            if (closed) {
                LOG.info("Successfully closed serial port");
            } else {
                LOG.error("Failed to close serial port");
            }
            return closed;
        }
        return false;
    }

    @Override
    public boolean isConnected() {
        return serialPort != null && serialPort.isOpen();
    }
    
    @Override
    public CommunicationType getType() {
        return CommunicationType.SERIAL;
    }
}