package com.air.advantage.canhandler;

import com.air.advantage.aaservice.data.MyMasterData;
import com.air.advantage.cbmessages.CANMessage;
import com.air.advantage.cbmessages.CANMessageLighting;
import com.air.advantage.cbmessages.CANMessageLighting00LmStatusMessageOld;
import com.air.advantage.cbmessages.CANMessageLighting02LmStatusMessage;

import io.vertx.mutiny.core.eventbus.EventBus;
public class HandlerLightingCB extends Handler {
    // Reference to LightingState data class (should be injected or managed)
    // private LightingState lightingState;
    public HandlerLightingCB(MyMasterData myMasterData, EventBus eventBus) {
        this.myMasterData = myMasterData;
        this.eventBus = eventBus;
    }

    @Override
    public void process(CANMessage message) {
        if (message instanceof CANMessageLighting) {
            processLighting((CANMessageLighting) message);
        }
        else if (message instanceof CANMessageLighting02LmStatusMessage) {
            processLighting((CANMessageLighting02LmStatusMessage) message);
        }
        else if (message instanceof CANMessageLighting00LmStatusMessageOld) {
            processLighting((CANMessageLighting00LmStatusMessageOld) message);
        }

        // else ignore or throw exception
    }

    private void processLighting(CANMessageLighting lightingMsg) {
        // TODO: Add business logic to update LightingState based on lightingMsg fields
    }
    private void processLighting(CANMessageLighting02LmStatusMessage lightingMsg) {
        // TODO: Add business logic to update LightingState based on lightingMsg fields
    }
    private void processLighting(CANMessageLighting00LmStatusMessageOld lightingMsg) {
        // TODO: Add business logic to update LightingState based on lightingMsg fields
    }
}
