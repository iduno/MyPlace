package com.air.advantage.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "myplace")
public interface MyPlaceConfig {

    Config config();
    SystemSettings system();

    @ConfigMapping(prefix = "config")
    interface Config {
        @WithDefault("config.json")
        String path();
        @WithDefault("1")
        Long saveDelayMinutes();
        @WithDefault("true")
        Boolean autoSave();
    }
    
    @ConfigMapping(prefix = "system")
    interface SystemSettings {
        @WithDefault("14.116")
        String aaServiceRev();

        @WithDefault("15.1084")
        String myAppRev();

        @WithDefault("MyPlace")
        String name();

        @WithDefault("MyAir5")
        String sysType();

        @WithDefault("PIC8KS6-TSP7")
        String tspModel();

        @WithDefault("127.0.0.1")
        String tspIp();
    }
}
