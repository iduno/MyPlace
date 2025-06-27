package com.air.advantage.cbmessages;

public class MessageGetSystemData extends Message {
    public MessageGetSystemData(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageGetSystemData() {
        this.messageType = MessageType.GET_SYSTEM_DATA;
    }

    public static MessageGetSystemData deserialize(byte[] data,MessageType messageType) {
        return new MessageGetSystemData(messageType);
    }

    @Override
    protected int serializeBody(byte[] data, int offset) {
        String messageTypeString = this.messageType.getValue();
        System.arraycopy(messageTypeString.getBytes(), 0, data, offset, messageTypeString.length());
        return messageTypeString.length();
    }
}