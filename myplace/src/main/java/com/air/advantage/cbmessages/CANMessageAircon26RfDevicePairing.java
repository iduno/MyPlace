package com.air.advantage.cbmessages;

public class CANMessageAircon26RfDevicePairing extends CANMessageAircon {

    private int pairingControl;
    private int rfDeviceType;
    private int channelNo;

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageAircon26RfDevicePairing msg = new CANMessageAircon26RfDevicePairing();
        
        if (data.length >= offset + 6) {
            msg.pairingControl = ByteArray.parseHexValue(offset, data);
            msg.rfDeviceType = ByteArray.parseHexValue(offset + 2, data);
            msg.channelNo = ByteArray.parseHexValue(offset + 4, data);
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(pairingControl, data, offset);
        ByteArray.toHexDigits(rfDeviceType, data, offset + 2);
        ByteArray.toHexDigits(channelNo, data, offset + 4);
        return offset + 14;
    }

    public int getPairingControl() {
        return pairingControl;
    }
    public int getRfDeviceType() {
        return rfDeviceType;
    }
    public int getChannelNo() {
        return channelNo;
    }
}
