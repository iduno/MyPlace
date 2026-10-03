package com.air.advantage.cbmessages;

public class CANMessageLighting03LmAck extends CANMessageLighting {

    public CANMessageLighting03LmAck() {
        super();
        this.messageType = MessageType.LM_ACK;
    }

    public static CANMessage deserialize(byte[] data, int offset) {
        return new CANMessageLighting03LmAck();
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        return offset + 14;
    }
}
