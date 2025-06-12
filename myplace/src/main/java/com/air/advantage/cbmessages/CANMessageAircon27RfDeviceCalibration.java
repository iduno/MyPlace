package com.air.advantage.cbmessages;

public class CANMessageAircon27RfDeviceCalibration extends CANMessageAircon {
    private int calibrationControl;
    private int channelNo;
    private int upDownPosition;
    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageAircon27RfDeviceCalibration msg = new CANMessageAircon27RfDeviceCalibration();
        
        if (data.length >= offset + 6) {
            msg.calibrationControl = ByteArray.parseHexValue(offset, data);
            msg.channelNo = ByteArray.parseHexValue(offset + 2, data);
            msg.upDownPosition = ByteArray.parseHexValue(offset + 4, data);
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(calibrationControl, data, offset);
        ByteArray.toHexDigits(channelNo, data, offset + 2);
        ByteArray.toHexDigits(upDownPosition, data, offset + 4);
        for (int i = offset + 6; i < data.length; i++) {
            data[i] = '0';
        }
        return offset + 14;
    }
    public int getCalibrationControl() {
        return calibrationControl;
    }
    public int getChannelNo() {
        return channelNo;
    }
    public int getUpDownPosition() {
        return upDownPosition;
    }
}
