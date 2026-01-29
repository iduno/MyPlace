package com.air.advantage.cbmessages;

public class CANMessageLighting17Rm2AddDevice extends CANMessageLighting {
    public int majorFWVersion;
    public int minorFWVersion;
    public int infoByte;
    
    public CANMessageLighting17Rm2AddDevice() {
        super();
        this.messageType = MessageType.RM2_STATUS_ADD_DEVICE;
        this.majorFWVersion = 0;
        this.minorFWVersion = 0;
        this.infoByte = 0;
    }
    
    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageLighting17Rm2AddDevice msg = new CANMessageLighting17Rm2AddDevice();
        
        if (data.length >= offset + 2) {
            msg.majorFWVersion = ByteArray.parseHexValue(offset, data);
            offset += 2;
            msg.minorFWVersion = ByteArray.parseHexValue(offset, data);
            offset += 2;
            msg.infoByte = ByteArray.parseHexValue(offset, data);
            offset += 2;
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        ByteArray.toHexDigits(majorFWVersion, data, offset);
        offset += 2;
        ByteArray.toHexDigits(minorFWVersion, data, offset);
        offset += 2;
        ByteArray.toHexDigits(infoByte, data, offset);
        offset += 2;
        return offset;
    }

    public int getMajorFWVersion() {
        return majorFWVersion;
    }
    public void setMajorFWVersion(int majorFWVersion) {
        this.majorFWVersion = majorFWVersion;
    }
    public int getMinorFWVersion() {
        return minorFWVersion;
    }
    public void setMinorFWVersion(int minorFWVersion) {
        this.minorFWVersion = minorFWVersion;
    }
    
    
    public int getInfoByte() {
        return infoByte;
    }
    
    public void setInfoByte(int infoByte) {
        this.infoByte = infoByte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CANMessageLighting17Rm2AddDevice)) return false;
        if (!super.equals(o)) return false;
        CANMessageLighting17Rm2AddDevice that = (CANMessageLighting17Rm2AddDevice) o;
        return majorFWVersion == that.majorFWVersion && minorFWVersion == that.minorFWVersion && infoByte == that.infoByte;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), majorFWVersion, minorFWVersion, infoByte);
    }

}
