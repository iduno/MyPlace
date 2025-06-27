package com.air.advantage.service.communication;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import com.air.advantage.cbmessages.Message;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class CommunicationManager {
    
    private static final Logger LOG = Logger.getLogger(CommunicationManager.class);
    
    private final AtomicBoolean isConnected = new AtomicBoolean(false);
    
    @Inject
    CommunicationService communicationService;
    
    @Inject
    EventBus eventBus;
    
    @Inject
    Vertx vertx;
    
    @Inject
    @ConfigProperty(name = "communication.autoconnect", defaultValue = "false")
    boolean autoConnect;
    
    
    @Inject
    @ConfigProperty(name = "communication.heartbeat.interval.ms", defaultValue = "30000")
    long heartbeatInterval;
    
    private Long heartbeatTimerId;
    private String currentEndpoint;
    
    void onStart(@Observes StartupEvent ev) {
        LOG.info("Initializing Communication Manager");
        communicationService.initialize();
        
        if (autoConnect) {
            LOG.info("Auto-connecting");
            open("auto");
        }
        
        // Start the heartbeat timer for connection monitoring
        startHeartbeatTimer();
    }
    
    private void startHeartbeatTimer() {
        if (heartbeatInterval > 0) {
            LOG.info("Starting connection heartbeat with interval: " + heartbeatInterval + "ms");
            heartbeatTimerId = vertx.setPeriodic(heartbeatInterval, id -> checkConnectionStatus());
        }

    }
    
    private void checkConnectionStatus() {
        if (isConnected.get()) {
            // Implement a heartbeat check if the underlying service supports it
            // For now, just check if we can still send data
            // if (!communicationService.isConnected()) {
            //     LOG.warn("Connection heartbeat failed, marking as disconnected");
            //     isConnected.set(false);
            //     eventBus.publish("communication-status", "disconnected");
            // }
        }
    }


    
    @ConsumeEvent("communication-open")
    public boolean open(String message) {
        LOG.info("Opening communication channel");
        boolean success = communicationService.open();
        
        if (success) {
            isConnected.set(true);
            eventBus.publish("communication-status", "connected");
        } else {
            isConnected.set(false);
            eventBus.publish("communication-status", "disconnected");
        }
        
        return success;
    }
    

    public boolean send(Message data) {
        if (!isConnected.get()) {
            LOG.warn("Cannot send data, not connected");
            return false;
        }
        
        //LOG.info("Sending data: " + data);
        boolean success = communicationService.send(data);
        
        if (!success) {
            LOG.warn("Failed to send data, connection may be lost");
            //isConnected.set(false);
            eventBus.publish("communication-status", "disconnected");
        }
        
        return success;
    }
    
    @ConsumeEvent("communication-close")
    public boolean close(String message) {
        LOG.info("Closing communication channel");
        boolean success = communicationService.close();
        
        isConnected.set(false);
        eventBus.publish("communication-status", "disconnected");
        
        return success;
    }
    
    public CommunicationType getActiveServiceType() {
        return communicationService.getType();
    }
    
    public boolean isConnected() {
        return isConnected.get();
    }
    
    public String getCurrentEndpoint() {
        return currentEndpoint;
    }
}