package com.air.advantage.cbmessages;

public class CANMessageAircon26RfDevicePairing extends CANMessageAircon {

    private int pairingControl;
    private int rfDeviceType;
    private int channelNo;

    public CANMessageAircon26RfDevicePairing() {
        super();
        this.messageType = MessageType.RF_DEVICE_PAIRING;
        this.pairingControl = 0;
        this.rfDeviceType = 0;
        this.channelNo = 0;
    }

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
    
    public void setPairingControl(int pairingControl) {
        this.pairingControl = pairingControl;
    }
    public void setRfDeviceType(int rfDeviceType) {
        this.rfDeviceType = rfDeviceType;
    }
    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CANMessageAircon26RfDevicePairing)) return false;
        if (!super.equals(o)) return false;
        CANMessageAircon26RfDevicePairing that = (CANMessageAircon26RfDevicePairing) o;
        return pairingControl == that.pairingControl && rfDeviceType == that.rfDeviceType && channelNo == that.channelNo;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), pairingControl, rfDeviceType, channelNo);
    }

}
