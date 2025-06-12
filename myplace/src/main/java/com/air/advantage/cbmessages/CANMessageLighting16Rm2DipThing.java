package com.air.advantage.cbmessages;

public class CANMessageLighting16Rm2DipThing extends CANMessageLighting {
    public int dip1State;
    public int dip2State;
    public int dip3State;
    public int dip4State;
    public int dip5State;
    public int dip6State;
    public int infoByte;
    // DIP States
    // 1 BUTTON TYPE UP DOWN
    // 2 BUTTON TYPE UP DOWN
    // 3 BUTTON TYPE UP DOWN GARRAGE
    // 8 BUTTON TYPE ON OFF
    // 9 BUTTON TYPE DIMMABLE
    // 10 BUTTON TYPE NONE
    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageLighting16Rm2DipThing msg = new CANMessageLighting16Rm2DipThing();
        
        if (data.length >= offset + 2) {
            msg.dip1State = ByteArray.parseHexValue(offset, data);
            msg.dip2State = ByteArray.parseHexValue(offset + 2, data);
            msg.dip3State = ByteArray.parseHexValue(offset + 4, data);
            msg.dip4State = ByteArray.parseHexValue(offset + 6, data);
            msg.dip5State = ByteArray.parseHexValue(offset + 8, data);
            msg.dip6State = ByteArray.parseHexValue(offset + 10, data);
            msg.infoByte = ByteArray.parseHexValue(offset + 12, data);
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(dip1State, data, offset);
        ByteArray.toHexDigits(dip2State, data, offset + 2);
        ByteArray.toHexDigits(dip3State, data, offset + 4);
        ByteArray.toHexDigits(dip4State, data, offset + 6);
        ByteArray.toHexDigits(dip5State, data, offset + 8);
        ByteArray.toHexDigits(dip6State, data, offset + 10);
        ByteArray.toHexDigits(infoByte, data, offset + 12);
        return offset + 14;
    }
}
