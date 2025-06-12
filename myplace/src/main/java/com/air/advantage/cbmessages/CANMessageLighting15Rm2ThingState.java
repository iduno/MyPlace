package com.air.advantage.cbmessages;

public class CANMessageLighting15Rm2ThingState extends CANMessageLighting {
    private int roomNumber;
    private int lightState;
    private int switchState; //0,1,2,3,8,9,10,
    private int dimLevel;
    private int nodeDipState;
    private int dimOffset;
    private int statusState;
    private boolean lowBattery;
    private boolean isCalibrated;
    private boolean isPoll;
    
    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageLighting15Rm2ThingState msg = new CANMessageLighting15Rm2ThingState();
        
        if (data.length >= offset + 2) {
            msg.roomNumber = ByteArray.parseHexValue(offset, data);
            msg.lightState = ByteArray.parseHexValue(offset + 2, data);
            msg.dimLevel = ByteArray.parseHexValue(offset + 4, data);
            msg.switchState = ByteArray.parseHexValue(offset + 6, data);
            msg.nodeDipState = ByteArray.parseHexValue(offset + 8, data);
            msg.dimOffset = ByteArray.parseHexValue(offset + 10, data);
            msg.statusState = ByteArray.parseHexValue(offset + 12, data);
            msg.lowBattery = ((msg.statusState & 0x80) == 0x80);
            msg.isCalibrated = ((msg.statusState & 0x40) == 0x40);
            msg.isPoll = ((msg.statusState & 0x20) == 0x20);
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(roomNumber, data, offset);
        ByteArray.toHexDigits(lightState, data, offset + 2);
        ByteArray.toHexDigits(dimLevel, data, offset + 4);
        ByteArray.toHexDigits(switchState, data, offset + 6);
        ByteArray.toHexDigits(nodeDipState, data, offset + 8);
        ByteArray.toHexDigits(dimOffset, data, offset + 10);
        ByteArray.toHexDigits(statusState, data, offset + 12);
        return offset + 14;
    }
}
