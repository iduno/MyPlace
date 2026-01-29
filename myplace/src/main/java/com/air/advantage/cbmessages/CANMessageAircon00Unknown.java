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
        return offset + 14;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CANMessageAircon00Unknown)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode());
    }
}
