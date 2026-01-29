package com.air.advantage.service.communication;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.jboss.logging.Logger;

import com.air.advantage.aaservice.data.MyMasterData;
import com.air.advantage.canhandler.Handler;
import com.air.advantage.cbmessages.CANMessage;
import com.air.advantage.cbmessages.CANMessage.DeviceType;
import com.air.advantage.cbmessages.CANMessageAircon06CBStatus;
import com.air.advantage.cbmessages.CANMessageAircon08CBErrorStatus;
import com.air.advantage.cbmessages.CANMessageAircon0aMidInformation;
import com.air.advantage.cbmessages.Message;
import com.air.advantage.cbmessages.Message.MessageType;
import com.air.advantage.cbmessages.MessageCAN;
import com.air.advantage.cbmessages.MessageGetSystemData;
import com.air.advantage.cbmessages.MessagePing;
import com.air.advantage.config.CommunicationConfig;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class CommunicationDataHandler {
    private static final Logger LOG = Logger.getLogger(CommunicationDataHandler.class);
    private final AtomicBoolean isConnected = new AtomicBoolean(false);
    private final AtomicBoolean isReconnecting = new AtomicBoolean(false);
    private final AtomicLong lastMessageTime = new AtomicLong(0);
    private final AtomicLong lastStatusTime = new AtomicLong(0);
    private Long reconnectTimerId;
    
    @Inject
    Vertx vertx;
    
    @Inject
    EventBus eventBus;
    
    @Inject
    CommunicationManager communicationManager;

    @Inject
    CommunicationConfig config;

    @Inject
    MyMasterData myMasterData;

    BoundedMessageQueue<Message> messageQueue = new BoundedMessageQueue<>(50);

    BoundedMessageQueue<CANMessage> messageCANQueue = new BoundedMessageQueue<>(50);

    
    private int reconnectAttempts = 0;
    private String currentEndpoint;
    private Long pingTimerId;
    private boolean sendAck = false;
    
    void onStart(@Observes StartupEvent ev) {
        LOG.info("Initializing Communication Data Handler");
        
        // Set up a connection status handler
        eventBus.consumer("communication-status", message -> {
            String status = (String) message.body();
            if ("disconnected".equals(status)) {
                handleDisconnection();
            } else if ("connected".equals(status)) {
                isConnected.set(true);
                reconnectAttempts = 0;
            }
        });
        sendAck = false;
        
        // If auto-connect is enabled, initiate the connection
        if (config.autoconnect()) {
            connect();
        }
        if (config.runMode() == CommunicationConfig.RunMode.CB) {
            pingTimerId = vertx.setPeriodic(10000, id -> pingTimer());
        }
    }
    
    void onShutdown(@Observes ShutdownEvent ev) {
        LOG.info("Shutting down Communication Data Handler");
        stopReconnectionTimer();
    }

    private void pingTimer() {
        if (!isConnected.get()) {
            LOG.warn("Cannot send ping, not connected");
            return;
        }

        long now = System.currentTimeMillis();
        long elapsed = now - lastMessageTime.get();
        if (elapsed >= 1000) {
            LOG.debug("Sending ping to check connection");
            lastMessageTime.set(System.currentTimeMillis());
            Message pingMessage = new MessagePing();
            communicationManager.send(pingMessage);
        }
    }
    
    @ConsumeEvent("communication-send")
    public boolean sendMessage(Message message) {
        messageQueue.push(message);
        return true;
    }

    @ConsumeEvent("communication-send-can")
    public boolean sendCANMessage(CANMessage message) {
        messageCANQueue.push(message);
        return true;
    }

    public void connect() {
        LOG.info("Connecting");
        isConnected.set(false);
        reconnectAttempts = 0;
        
        // Attempt connection
        boolean success = communicationManager.open("connect");
        if (success) {
            LOG.info("Successfully connected to: ");
            isConnected.set(true);
            if (config.runMode() == CommunicationConfig.RunMode.MYAIR) {
                // Send initial ping message if in MYAIR mode
                MessageCAN canMessage = new MessageCAN(MessageType.SET_CAN);
                CANMessageAircon06CBStatus airconStatus = new CANMessageAircon06CBStatus();
                airconStatus.setDeviceType(DeviceType.CONTROL_BOARD);
                canMessage.getMessageCANBaseList().add(airconStatus);
                sendMessage(canMessage);
            }
        } else {
            LOG.warn("Failed to connect to: ");
            handleDisconnection();
        }
    }
    
    private void handleDisconnection() {
        LOG.info("Connection lost, handling disconnection");
        isConnected.set(false);
        
        // If already reconnecting, don't start another timer
        if (isReconnecting.compareAndSet(false, true)) {
            startReconnectionTimer();
        }
    }
    
    private void startReconnectionTimer() {
        LOG.info("Starting reconnection timer with interval: " + config.reconnectInterval() + "ms");
        stopReconnectionTimer(); // Stop any existing timer
        
        reconnectTimerId = vertx.setTimer(config.reconnectInterval(), id -> attemptReconnection());
    }
    
    private void attemptReconnection() {
        if (isConnected.get()) {
            LOG.info("Already connected, cancelling reconnection");
            isReconnecting.set(false);
            return;
        }
        
        reconnectAttempts++;
        LOG.info("Attempting reconnection (" + reconnectAttempts + 
                (config.maxReconnectAttempts() > 0 ? "/" + config.maxReconnectAttempts() : "") + ")");
        
        // Check if we've exceeded the maximum number of attempts
        if (config.maxReconnectAttempts() > 0 && reconnectAttempts > config.maxReconnectAttempts()) {
            LOG.warn("Maximum reconnection attempts reached, giving up");
            isReconnecting.set(false);
            return;
        }
        
        // Attempt reconnection
        try {
            boolean success = communicationManager.open("retry");
            if (success) {
                LOG.info("Reconnection successful");
                isConnected.set(true);
                isReconnecting.set(false);
            } else {
                LOG.warn("Reconnection failed, scheduling next attempt");
                reconnectTimerId = vertx.setTimer(config.reconnectInterval(), id -> attemptReconnection());
            }
        } catch (Exception e) {
            LOG.error("Error during reconnection attempt: " + e.getMessage(), e);
            reconnectTimerId = vertx.setTimer(config.reconnectInterval(), id -> attemptReconnection());
        }
    }
    
    private void stopReconnectionTimer() {
        if (reconnectTimerId != null) {
            vertx.cancelTimer(reconnectTimerId);
            reconnectTimerId = null;
        }
    }
    
    @ConsumeEvent("communication-data")
    public void handleData(Message data) {
        LOG.debug("Processing communication data: " + data);
        lastMessageTime.set(System.currentTimeMillis());
        if (config.runMode() == CommunicationConfig.RunMode.MYAIR) {
            if (data instanceof MessagePing) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e)
                {
                    LOG.error("Error during sleep: " + e.getMessage(), e);
                }
                if (sendAck) {
                    sendAck = false;
                    LOG.debug("Received ping message, sending ACK");
                    MessageCAN canMessage = new MessageCAN(MessageType.ACK_CAN,MessageCAN.AckType.ACK);
                    communicationManager.send(canMessage);
                } else {
                    LOG.debug("Received ping message, but not sending ACK");
                    Message canMessage = messageQueue.pop();
                    if (canMessage == null) {
                        MessageCAN cm = new MessageCAN(MessageType.SET_CAN);
                        CANMessage canMsg = messageCANQueue.pop();
                        while (canMsg != null) {
                            cm.getMessageCANBaseList().add(canMsg);
                            canMsg = messageCANQueue.pop();
                        }
                        canMessage= cm;
                    }
                    communicationManager.send(canMessage);
                    if (canMessage.getMessageType() == MessageType.SET_CAN) {
                        sendAck = true;
                    }
                }
            }
            else if (data instanceof MessageCAN) {
                List<CANMessage> canMessages = ((MessageCAN) data).getMessageCANBaseList();
                for (CANMessage canMessage : canMessages) {
                    LOG.debug("Processing CAN message: " + canMessage);
                    Handler.dispatch(canMessage,myMasterData, eventBus);
                }
            
            }
        }
        else if (config.runMode() == CommunicationConfig.RunMode.CB)
        {
            if (data instanceof MessageCAN) {
                List<CANMessage> canMessages = ((MessageCAN) data).getMessageCANBaseList();
                for (CANMessage canMessage : canMessages) {
                    LOG.debug("Processing CAN message: " + canMessage);
                    Handler.dispatch(canMessage,myMasterData, eventBus);
                }
            
            }
            else if (data instanceof MessageGetSystemData) {
                MessageGetSystemData systemData = (MessageGetSystemData) data;
                LOG.debug("Received system data: " + systemData);
                Message messageGetSystemData = new MessageGetSystemData(MessageType.CAN2_IN_USE);
                sendMessage(messageGetSystemData);
                // Handle system data as needed
            }
            else {
                LOG.warn("Received unsupported message type: " + data.getClass().getSimpleName());
            }

            if (lastStatusTime.get() + 30000 < System.currentTimeMillis()) {
                lastStatusTime.set(System.currentTimeMillis());

                String uid = myMasterData.masterData.getAirconByUID("00000").airconInfo.uid;
           
                CANMessageAircon0aMidInformation cbMidInfoMessage = new CANMessageAircon0aMidInformation();
                cbMidInfoMessage.setDeviceType(DeviceType.AIRCON_1);
                cbMidInfoMessage.setSystemType(CANMessage.SystemType.CAN_AIRCON);
                cbMidInfoMessage.setUid(uid);
                sendCANMessage(cbMidInfoMessage);

                CANMessageAircon08CBErrorStatus cbErrorStatusMessage = new CANMessageAircon08CBErrorStatus();
                cbErrorStatusMessage.setDeviceType(DeviceType.AIRCON_1);
                cbErrorStatusMessage.setSystemType(CANMessage.SystemType.CAN_AIRCON);
                cbErrorStatusMessage.setUid(uid);
                sendCANMessage(cbErrorStatusMessage);

            }
 
            // If in CB mode, send the ping message immediately
            Message canMessage = messageQueue.pop();
            if (canMessage != null) {
                communicationManager.send(canMessage);
            } else {
                if (data.getMessageType() == MessageType.SET_CAN) {
                    MessageCAN cm = new MessageCAN(MessageType.GET_CAN,MessageCAN.AckType.ACK);
                    CANMessage canMsg = messageCANQueue.pop();
                    while (canMsg != null) {
                        cm.getMessageCANBaseList().add(canMsg);
                        canMsg = messageCANQueue.pop();
                    }
                    canMessage= cm;
        
                    communicationManager.send(canMessage);
                }
            }
            try
            {
            Thread.sleep(200);
            } catch (InterruptedException e)
            {
                LOG.error("Error during sleep: " + e.getMessage(), e);
            }
            Message pingMessage = new MessagePing();
            communicationManager.send(pingMessage);
            
        }
        // Process your data here
        // For example, parse XML or JSON responses from your device
    }
}