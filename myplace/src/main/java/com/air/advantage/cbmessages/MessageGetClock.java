package com.air.advantage.cbmessages;

public class MessageGetClock extends Message {
    public MessageGetClock() {
        this.messageType = MessageType.GET_CLOCK;
    }

    public static MessageGetClock deserialize(byte[] data) {
        return new MessageGetClock();
    }
}