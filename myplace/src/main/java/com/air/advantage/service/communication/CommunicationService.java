package com.air.advantage.service.communication;

import com.air.advantage.cbmessages.Message;

public interface CommunicationService {
    /**
     * Initialize the communication service
     * @return true if initialization was successful
     */
    boolean initialize();
    
    /**
     * Open the communication channel
     * @param endpoint The endpoint identifier (port name, file path, host:port, etc.)
     * @return true if channel was opened successfully
     */
    boolean open();
    
    /**
     * Send data to the communication channel
     * @param data The data to send
     * @return true if data was sent successfully
     */
    boolean send(Message data);
    
    /**
     * Close the communication channel
     * @return true if channel was closed successfully
     */
    boolean close();

    boolean isConnected();
    
    /**
     * Get the service type
     * @return the type of communication service
     */
    CommunicationType getType();
}