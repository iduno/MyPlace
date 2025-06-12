package com.air.advantage.aaservice;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.air.advantage.aaservice.data.DataLight;
import com.air.advantage.libraryairconlightjson.LightState;

public class HandlerLights extends HandlerBase {

    private static final Logger LOGGER = Logger.getLogger(HandlerLights.class.getName());
    private static HandlerLights instance;

    /**
     * Returns the singleton instance of HandlerLights.
     *
     * @return The singleton instance.
     */
    public static HandlerLights getInstance() {
        if (instance == null) {
            synchronized (HandlerLights.class) {
                if (instance == null) {
                    instance = new HandlerLights();
                }
            }
        }
        return instance;
    }

    /**
     * Returns the initialization command for lights.
     *
     * @return The initialization command string.
     */
    String getInitCommand() {
        return "0201000000000360000000000 0201000000236000000000000";
    }

    /**
     * Processes a CAN message for lights.
     *
     * @param message The byte array representing the CAN message.
     * @return A DataLight object if the message is valid, null otherwise.
     */
    public DataLight processLightMessage(byte[] message) {
        DataLight dataLight = new DataLight();

        if (!compareHexValue(message, 2, "01")) {
            LOGGER.log(Level.FINE, "Rejected can message - incorrect device type {0}", new String(message));
            return null;
        }

        if (!compareHexValue(message, 9, "01")) {
            LOGGER.log(Level.FINE, "Rejected can message - incorrect message type");
            return null;
        }

        String uid = extractUIDValue(message, 4);
        if (uid == null) {
            LOGGER.log(Level.FINE, "Rejected LM control message - invalid UID");
            return null;
        }

        Integer roomNumber = extractIntValue(message, 11);
        String hexString = extractHexString(message, 11);
        if (roomNumber == null || roomNumber < 1 || roomNumber > 6) {
            LOGGER.log(Level.FINE, "Rejected LM control message - invalid room number");
            return null;
        }

        Integer stateAndValue = extractIntValue(message, 13);
        if (stateAndValue == null) {
            LOGGER.log(Level.FINE, "Rejected LM control message - invalid state and value");
            return null;
        }

        LightState lightState = LightState.off;
        if ((stateAndValue & 128) == 128) {
            lightState = LightState.on;
        }

        Integer decimalValue = extractIntValue(message, 15);
        if (decimalValue == null) {
            LOGGER.log(Level.FINE, "Rejected LM control message - invalid decimal value");
            return null;
        }

        double brightnessValue = (stateAndValue & 127) + (decimalValue / 10.0);
        LightBrightness.getBrightnessLevel(brightnessValue);

        dataLight.id = uid + hexString;
        return dataLight;
    }
}