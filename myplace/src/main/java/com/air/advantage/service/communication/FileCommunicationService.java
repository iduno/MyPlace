package com.air.advantage.service.communication;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

import org.jboss.logging.Logger;

import com.air.advantage.cbmessages.Message;
import com.air.advantage.config.MyPlaceConfig.CommunicationConfig;

import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.file.FileSystem;

public class FileCommunicationService implements CommunicationService {
    
    private static final Logger LOG = Logger.getLogger(FileCommunicationService.class);
    private final AtomicReference<String> currentFilePath = new AtomicReference<>();
    private Long fileCheckTimerId;
    
    private final Vertx vertx;
    private final EventBus eventBus;
    private final CommunicationConfig.FileConfig config;
    
    public FileCommunicationService(Vertx vertx, EventBus eventBus, CommunicationConfig config) {
        this.vertx = vertx;
        this.eventBus = eventBus;
        this.config = config.file();
    }
    
    @Override
    public boolean initialize() {
        LOG.info("Initializing File Communication Service");
        return true;
    }
    
    @Override
    public boolean open() {
        try {
            LOG.info("Opening file: " + config.path());
            currentFilePath.set(config.path());
            
            FileSystem fs = vertx.fileSystem();
            Path path = Paths.get(config.path());
            Path parentDir = path.getParent();
            
            // Make sure the parent directory exists
            if (parentDir != null && !fs.existsBlocking(parentDir.toString())) {
                fs.mkdirsBlocking(parentDir.toString());
            }
            
            // Create the file if it doesn't exist
            if (!fs.existsBlocking(config.path())) {
                fs.createFileBlocking(config.path());
            }
            
            // Set up a file poller
            setupFilePoller(config.path());
            
            // Notify that connection is established
            eventBus.publish("communication-status", "connected");
            
            return true;
        } catch (Exception e) {
            LOG.error("Error opening file: " + e.getMessage(), e);
            eventBus.publish("communication-status", "disconnected");
            return false;
        }
    }
    
    private void setupFilePoller(String filePath) {
        try {
            if (fileCheckTimerId != null) {
                vertx.cancelTimer(fileCheckTimerId);
            }
            
            // Set up a periodic timer to poll the file for changes
            fileCheckTimerId = vertx.setPeriodic(config.pollingInterval(), id -> {
                readFileContent(filePath);
            });
            
            // Initial read of the file
            readFileContent(filePath);
            
            LOG.info("File poller set up for: " + filePath);
        } catch (Exception e) {
            LOG.error("Error setting up file poller: " + e.getMessage(), e);
        }
    }
    
    private void readFileContent(String filePath) {
        try {
            FileSystem fs = vertx.fileSystem();
            if (fs.existsBlocking(filePath)) {
                String content = fs.readFileBlocking(filePath).toString().trim();
                if (!content.isEmpty()) {
                    LOG.info("Read content from file: " + content);
                    eventBus.publish("communication-data", content);
                    
                    // Clear the file after reading
                    fs.writeFileBlocking(filePath, io.vertx.mutiny.core.buffer.Buffer.buffer(""));
                }
            } else {
                LOG.warn("File no longer exists: " + filePath);
                eventBus.publish("communication-status", "disconnected");
            }
        } catch (Exception e) {
            LOG.error("Error reading file: " + e.getMessage(), e);
            eventBus.publish("communication-status", "disconnected");
        }
    }
    
    @Override
    public boolean send(Message data) {
        String filePath = currentFilePath.get();
        if (filePath == null) {
            LOG.warn("Cannot send data, no file path set");
            return false;
        }
        
        try {
            vertx.fileSystem().writeFileBlocking(filePath, io.vertx.mutiny.core.buffer.Buffer.buffer(data + "\n"));
            LOG.info("Wrote data to file: " + data);
            return true;
        } catch (Exception e) {
            LOG.error("Error writing to file: " + e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public boolean close() {
        try {
            if (fileCheckTimerId != null) {
                vertx.cancelTimer(fileCheckTimerId);
                fileCheckTimerId = null;
            }
            currentFilePath.set(null);
            LOG.info("File communication closed");
            
            // Notify that connection is closed
            eventBus.publish("communication-status", "disconnected");
            
            return true;
        } catch (Exception e) {
            LOG.error("Error closing file communication: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean isConnected() {
        // File communication is considered connected if the file path is set
        return currentFilePath.get() != null;
    }
    
    @Override
    public CommunicationType getType() {
        return CommunicationType.FILE;
    }
}