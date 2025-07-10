package com.air.advantage.cbmessages;

public class CANMessageAircon00Unknown extends CANMessageAircon {
    public CANMessageAircon00Unknown() {
        super();
        this.messageType = MessageType.UNKNOWN; // Set the message type
    }
    public static CANMessage deserialize(byte[] data, int offset) {
        return new CANMessageAircon00Unknown();
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        // No fields to serialize for this message
        return offset;
    }
}
