package com.air.advantage.canhandler;

import com.air.advantage.cbmessages.CANMessage;
import com.air.advantage.cbmessages.CANMessageLighting;
// import your LightingState data class as needed

public class HandlerLighting extends Handler {
    // Reference to LightingState data class (should be injected or managed)
    // private LightingState lightingState;

    @Override
    public void process(CANMessage message) {
        if (message instanceof CANMessageLighting) {
            processLighting((CANMessageLighting) message);
        }
        // else ignore or throw exception
    }

    private void processLighting(CANMessageLighting lightingMsg) {
        // TODO: Add business logic to update LightingState based on lightingMsg fields
    }
}
