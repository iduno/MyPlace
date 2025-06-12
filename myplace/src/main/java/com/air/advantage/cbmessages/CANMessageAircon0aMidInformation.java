package com.air.advantage.cbmessages;

public class CANMessageAircon0aMidInformation extends CANMessageAircon {
    public static CANMessage deserialize(byte[] data, int offset) {
        return new CANMessageAircon0aMidInformation();
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        // No fields to serialize for this message
        return offset;
    }
}
