package com.air.advantage.canhandler;

import com.air.advantage.aaservice.data.MyMasterData;
import com.air.advantage.cbmessages.CANMessage;
import com.air.advantage.cbmessages.CANMessage.DeviceType;

import io.vertx.mutiny.core.eventbus.EventBus;


public abstract class Handler {

    protected MyMasterData myMasterData;

    protected EventBus eventBus;

    public abstract void process(CANMessage message);

    public static void dispatch(CANMessage message,MyMasterData myMasterData,EventBus eventBus) {
        if (message == null) return;
        if (message.getDeviceType() == DeviceType.CONTROL_BOARD)
        {
            if (message instanceof com.air.advantage.cbmessages.CANMessageLighting) {
                new HandlerLighting(myMasterData,eventBus).process(message);
            } else if (message instanceof com.air.advantage.cbmessages.CANMessageAircon) {
                new HandlerAirconCB(myMasterData,eventBus).process(message);
            } else {
                // Optionally handle unknown or unsupported message types
            }

        }
        else
        {
            if (message instanceof com.air.advantage.cbmessages.CANMessageLighting) {
                new HandlerLightingCB(myMasterData,eventBus).process(message);
            } else if (message instanceof com.air.advantage.cbmessages.CANMessageAircon) {
                new HandlerAircon(myMasterData,eventBus).process(message);
            } else {
                // Optionally handle unknown or unsupported message types
            }
        }
    }
}
