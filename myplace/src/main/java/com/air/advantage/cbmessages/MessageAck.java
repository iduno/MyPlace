package com.air.advantage.cbmessages;

public class MessageAck extends Message {
    public MessageAck() {
        this.messageType = MessageType.ACK;
    }

    public static MessageAck deserialize(byte[] data) {
        return new MessageAck();
    }
}