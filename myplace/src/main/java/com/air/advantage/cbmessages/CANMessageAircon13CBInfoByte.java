package com.air.advantage.cbmessages;

public class CANMessageAircon13CBInfoByte extends CANMessageAircon {
    private int infoByte;

    public CANMessageAircon13CBInfoByte() {
        super();
        this.messageType = MessageType.INFO_BYTE;
        this.infoByte = 0;
    }

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
    
    public void setInfoByte(int infoByte) { this.infoByte = infoByte; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CANMessageAircon13CBInfoByte)) return false;
        if (!super.equals(o)) return false;
        CANMessageAircon13CBInfoByte that = (CANMessageAircon13CBInfoByte) o;
        return infoByte == that.infoByte;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), infoByte);
    }

}
