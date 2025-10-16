package com.air.advantage.cbmessages;

public class MessageGetAllZoneSensorData extends Message {
    public MessageGetAllZoneSensorData(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageGetAllZoneSensorData() {
        this.messageType = MessageType.GET_ALL_ZONE_SENSOR_DATA;
    }

    public static MessageGetAllZoneSensorData deserialize(byte[] data,MessageType messageType) {
        return new MessageGetAllZoneSensorData(messageType);
    }

    @Override
    protected int serializeBody(byte[] data, int offset) {
        String messageTypeString = this.messageType.getValue();
        System.arraycopy(messageTypeString.getBytes(), 0, data, offset, messageTypeString.length());
        return messageTypeString.length();
    }
}