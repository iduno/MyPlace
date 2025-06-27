package com.air.advantage.cbmessages;

public class MessageGetZoneDataZone extends Message {
    public MessageGetZoneDataZone() {
        this.messageType = MessageType.GET_ZONE_DATA_ZONE;
    }

    public static MessageGetZoneDataZone deserialize(byte[] data) {
        return new MessageGetZoneDataZone();
    }

    @Override
    protected int serializeBody(byte[] data, int offset) {
        String messageTypeString = this.messageType.getValue();
        System.arraycopy(messageTypeString.getBytes(), 0, data, offset, messageTypeString.length());
        return messageTypeString.length();
    }
}