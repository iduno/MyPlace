package com.air.advantage.service.communication.config;

import com.air.advantage.service.communication.CommunicationType;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithConverter;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "communication")
public interface CommunicationConfig {

    enum RunMode {
        LISTEN, CB, MYAIR
    }
    
    @WithConverter(CommunicationTypeConverter.class)
    @WithDefault("NONE")
    CommunicationType type();
    
    @WithDefault("false")
    boolean autoconnect();
    
    @WithName("reconnect.interval.ms")
    @WithDefault("60000")
    long reconnectInterval();
    
    @WithName("max.reconnect.attempts")
    @WithDefault("0")
    int maxReconnectAttempts();

    @WithName("runmode")
    @WithDefault("LISTEN")
    RunMode runMode(); // Flag to indicate if this is a CB communication handler
    
    SerialConfig serial();
    TcpConfig tcp();
    FileConfig file();
    
    @ConfigMapping(prefix = "serial")
    interface SerialConfig {
        @WithDefault("/dev/ttyUSB0")
        String port();
        
        @WithDefault("9600")
        int baudRate();
        
        @WithDefault("8")
        int dataBits();
        
        @WithDefault("1")
        int stopBits();
        
        @WithDefault("0")
        int parity();
        
        @WithDefault("1000")
        int timeout();
    }
    
    @ConfigMapping(prefix = "tcp")
    interface TcpConfig {
        @WithDefault("localhost")
        String host();
        
        @WithDefault("8123")
        int port();
        
        @WithName("server")
        @WithDefault("true")
        boolean isServer();
    }
    
    @ConfigMapping(prefix = "file")
    interface FileConfig {
        @WithDefault("/tmp/communication.txt")
        String path();
        
        @WithName("polling.interval.ms")
        @WithDefault("1000")
        long pollingInterval();
    }
}