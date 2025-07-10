package com.air.advantage.servicehandler;

import com.air.advantage.aaservice.data.MasterData;
import com.air.advantage.aaservice.data.MyMasterData;

public abstract class Handler {
        protected MyMasterData myMasterData;


    public static void dispatch(MasterData message,MyMasterData myMasterData) {
        if (message == null) return;
        if (message.aircons != null) {
            HandlerAircon handlerAircon = new HandlerAircon(myMasterData);
            for (String uid : message.aircons.keySet()) {
                handlerAircon.getUpdateMessagesForCB(message.aircons.get(uid), uid, false);
            }
        }
    }
}
