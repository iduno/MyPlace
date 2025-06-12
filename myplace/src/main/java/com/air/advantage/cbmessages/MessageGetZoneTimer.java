package com.air.advantage.cbmessages;

public class MessageGetZoneTimer extends Message {
    public MessageGetZoneTimer() {
        this.messageType = MessageType.GET_ZONE_TIMER;
    }

    public static MessageGetZoneTimer deserialize(byte[] data) {
        return new MessageGetZoneTimer();
    }
}