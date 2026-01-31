package com.air.advantage.service.communication;

import org.jboss.logging.Logger;

import com.air.advantage.config.MyPlaceConfig;

import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
public class CommunicationServiceFactory {
    
    private static final Logger LOG = Logger.getLogger(CommunicationServiceFactory.class);
    
    @Inject
    EventBus eventBus;
    
    @Inject
    Vertx vertx;
    

    @Inject
    MyPlaceConfig config;
    
    @Produces
    @ApplicationScoped
    public CommunicationService createCommunicationService() {

        LOG.info("Creating communication service of type: " + config.communication().type());
        
        return switch (config.communication().type()) {
            case SERIAL -> new SerialCommunicationService(vertx, eventBus, config.communication());
            case FILE -> new FileCommunicationService(vertx, eventBus, config.communication());
            case TCP -> new TcpCommunicationService(vertx, eventBus, config.communication());
            default -> null;
        };
    }
}