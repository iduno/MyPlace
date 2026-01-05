package com.air.advantage.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "myplace")
public interface MyPlaceConfig {

    Config config();

    @ConfigMapping(prefix = "config")
    interface Config {
        @WithDefault("config.json")
        String path();
        @WithDefault("1")
        Long saveDelayMinutes();
        @WithDefault("true")
        Boolean autoSave();
    }
}
