package com.air.advantage.cbmessages;

public class CANMessageAircon27RfDeviceCalibration extends CANMessageAircon {
    private int calibrationControl;
    private int channelNo;
    private int upDownPosition;

    public CANMessageAircon27RfDeviceCalibration() {
        super();
        this.messageType = MessageType.RF_DEVICE_CALIBRATION;
        this.calibrationControl = 0;
        this.channelNo = 0;
        this.upDownPosition = 0;
    }

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

    public void setCalibrationControl(int calibrationControl) {
        this.calibrationControl = calibrationControl;
    }

    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    public int getUpDownPosition() {
        return upDownPosition;
    }

    public void setUpDownPosition(int upDownPosition) {
        this.upDownPosition = upDownPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CANMessageAircon27RfDeviceCalibration)) return false;
        if (!super.equals(o)) return false;
        CANMessageAircon27RfDeviceCalibration that = (CANMessageAircon27RfDeviceCalibration) o;
        return calibrationControl == that.calibrationControl && channelNo == that.channelNo && upDownPosition == that.upDownPosition;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), calibrationControl, channelNo, upDownPosition);
    }

}
