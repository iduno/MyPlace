package com.air.advantage.cbmessages;

public class MessageNak extends Message {
    public MessageNak() {
        this.messageType = MessageType.NAK;
    }

    public static MessageNak deserialize(byte[] data) {
        return new MessageNak();
    }

    @Override
    protected int serializeBody(byte[] data, int offset) {
        String messageTypeString = this.messageType.getValue();
        System.arraycopy(messageTypeString.getBytes(), 0, data, offset, messageTypeString.length());
        return messageTypeString.length();
    }
}