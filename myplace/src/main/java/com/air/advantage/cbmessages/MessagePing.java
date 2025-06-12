package com.air.advantage.cbmessages;

public class MessagePing extends Message {
    public MessagePing() {
        this.messageType = MessageType.PING;
    }

    public static MessagePing deserialize(byte[] data) {
        return new MessagePing();
    }
}
