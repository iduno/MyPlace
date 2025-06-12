package com.air.advantage.cbmessages;

public class MessageNak extends Message {
    public MessageNak() {
        this.messageType = MessageType.NAK;
    }

    public static MessageNak deserialize(byte[] data) {
        return new MessageNak();
    }
}