package com.air.advantage.canhandler;

import com.air.advantage.aaservice.data.MyMasterData;
import com.air.advantage.cbmessages.CANMessage;

import jakarta.inject.Inject;

public abstract class Handler {
    @Inject
    protected MyMasterData myMasterData;

    public abstract void process(CANMessage message);

    public static void dispatch(CANMessage message) {
        if (message == null) return;
        if (message instanceof com.air.advantage.cbmessages.CANMessageLighting) {
            new HandlerLighting().process(message);
        } else if (message instanceof com.air.advantage.cbmessages.CANMessageAircon) {
            new HandlerAircon().process(message);
        } else {
            // Optionally handle unknown or unsupported message types
        }
    }
}
