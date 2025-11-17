package com.air.advantage.cbmessages;

public class MessageGetAllZoneSensorData extends Message {
    public MessageGetAllZoneSensorData(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageGetAllZoneSensorData() {
        this.messageType = MessageType.GET_ALL_ZONE_SENSOR_DATA;
    }

    public static MessageGetAllZoneSensorData deserialize(byte[] data,MessageType messageType) {
        MessageGetAllZoneSensorData message = new MessageGetAllZoneSensorData(messageType);
        message.data = new String(data);
        return message;
    }

    @Override
    protected int serializeBody(byte[] data, int offset) {
        String messageTypeString = this.messageType.getValue();
        System.arraycopy(messageTypeString.getBytes(), 0, data, offset, messageTypeString.length());
        return messageTypeString.length();
    }
}