package com.air.advantage.cbmessages;

public class MessageGetSystemData extends Message {
    public MessageGetSystemData() {
        this.messageType = MessageType.GET_SYSTEM_DATA;
    }

    public static MessageGetSystemData deserialize(byte[] data) {
        return new MessageGetSystemData();
    }
}