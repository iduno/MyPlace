package com.air.advantage.cbmessages;

public class CANMessageAircon13CBInfoByte extends CANMessageAircon {
    private int infoByte;

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageAircon13CBInfoByte msg = new CANMessageAircon13CBInfoByte();
        
        if (data.length >= offset + 2) {
            msg.infoByte = ByteArray.parseHexValue(offset, data);
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(infoByte, data, offset);
        return offset + 14;
    }

    public int getInfoByte() { return infoByte; }
}
