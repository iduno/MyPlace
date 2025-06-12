package com.air.advantage.cbmessages;

public class MessageGetZoneDataZone extends Message {
    public MessageGetZoneDataZone() {
        this.messageType = MessageType.GET_ZONE_DATA_ZONE;
    }

    public static MessageGetZoneDataZone deserialize(byte[] data) {
        return new MessageGetZoneDataZone();
    }
}