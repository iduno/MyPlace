package com.air.advantage.cbmessages;

public class MessagePing extends Message {
    public MessagePing() {
        this.messageType = MessageType.PING;
    }

    public static MessagePing deserialize(byte[] data) {
        return new MessagePing();
    }

    @Override
    protected int serializeBody(byte[] data, int offset) {
        String messageTypeString = this.messageType.getValue();
        System.arraycopy(messageTypeString.getBytes(), 0, data, offset, messageTypeString.length());
        return messageTypeString.length();
    }
}
