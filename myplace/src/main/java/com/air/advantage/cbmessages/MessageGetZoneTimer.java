package com.air.advantage.cbmessages;

public class MessageGetZoneTimer extends Message {
    public MessageGetZoneTimer() {
        this.messageType = MessageType.GET_ZONE_TIMER;
    }

    public static MessageGetZoneTimer deserialize(byte[] data) {
        return new MessageGetZoneTimer();
    }

    @Override
    protected int serializeBody(byte[] data, int offset) {
        String messageTypeString = this.messageType.getValue();
        System.arraycopy(messageTypeString.getBytes(), 0, data, offset, messageTypeString.length());
        return messageTypeString.length();
    }
}