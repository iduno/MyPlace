package com.air.advantage.cbmessages;

public class MessageAck extends Message {
    public MessageAck() {
        this.messageType = MessageType.ACK;
    }

    public static MessageAck deserialize(byte[] data) {
        return new MessageAck();
    }

    @Override
    protected int serializeBody(byte[] data, int offset) {
        String messageTypeString = this.messageType.getValue();
        System.arraycopy(messageTypeString.getBytes(), 0, data, offset, messageTypeString.length());
        return messageTypeString.length();
    }
}