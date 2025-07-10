package com.air.advantage.canhandler;

import com.air.advantage.aaservice.data.MyMasterData;
import com.air.advantage.cbmessages.CANMessage;


public abstract class Handler {

    protected MyMasterData myMasterData;

    public abstract void process(CANMessage message);

    public static void dispatch(CANMessage message,MyMasterData myMasterData) {
        if (message == null) return;
        if (message instanceof com.air.advantage.cbmessages.CANMessageLighting) {
            new HandlerLighting(myMasterData).process(message);
        } else if (message instanceof com.air.advantage.cbmessages.CANMessageAircon) {
            new HandlerAircon(myMasterData).process(message);
        } else {
            // Optionally handle unknown or unsupported message types
        }
    }
}
