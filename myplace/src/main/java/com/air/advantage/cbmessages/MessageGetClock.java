package com.air.advantage.cbmessages;

public class MessageGetClock extends Message {
    public MessageGetClock() {
        this.messageType = MessageType.GET_CLOCK;
    }

    public static MessageGetClock deserialize(byte[] data) {
        MessageGetClock message = new MessageGetClock();
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