package com.air.advantage.config;

import org.eclipse.microprofile.config.spi.Converter;

import com.air.advantage.service.communication.CommunicationType;

public class CommunicationTypeConverter implements Converter<CommunicationType> {
    
    @Override
    public CommunicationType convert(String value) {
        if (value == null || value.trim().isEmpty()) {
            return CommunicationType.NONE;
        }
        try {
            return CommunicationType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CommunicationType.NONE;
        }
    }
}