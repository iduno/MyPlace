package com.air.advantage.cbmessages;

public class CANMessageAircon0aMidInformation extends CANMessageAircon {

    public CANMessageAircon0aMidInformation() {
        super();
        this.messageType = MessageType.MID_INFORMATION;
        // No fields to default
    }
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
