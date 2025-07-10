package com.air.advantage.cbmessages;

public class CANMessageAircon06CBStatus extends CANMessageAircon {
    private int cbFwMajor;
    private int cbFwMinor;
    private int cbType;
    private int rfFwMajor;

    public CANMessageAircon06CBStatus() {
        super();
        this.messageType = MessageType.CB_STATUS;
        this.cbFwMajor = 0;
        this.cbFwMinor = 0;
        this.cbType = 0;
        this.rfFwMajor = 0;
    }

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageAircon06CBStatus msg = new CANMessageAircon06CBStatus();
        
        if (data.length >= offset + 8) {
            msg.cbFwMajor = ByteArray.parseHexValue(offset, data);
            offset += 2;
            msg.cbFwMinor = ByteArray.parseHexValue(offset, data);
            offset += 2;
            msg.cbType = ByteArray.parseHexValue(offset, data);
            offset += 2;
            msg.rfFwMajor = ByteArray.parseHexValue(offset, data);
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(cbFwMajor, data, offset);
        offset += 2;
        ByteArray.toHexDigits(cbFwMinor, data, offset);
        offset += 2;
        ByteArray.toHexDigits(cbType, data, offset);
        offset += 2;
        ByteArray.toHexDigits(rfFwMajor, data, offset);
        offset += 2;
        return offset;
    }

    public int getCbFwMajor() { return cbFwMajor; }
    public int getCbFwMinor() { return cbFwMinor; }
    public int getCbType() { return cbType; }
    public int getRfFwMajor() { return rfFwMajor; }
    
    public void setCbFwMajor(int cbFwMajor) { this.cbFwMajor = cbFwMajor; }
    public void setCbFwMinor(int cbFwMinor) { this.cbFwMinor = cbFwMinor; }
    public void setCbType(int cbType) { this.cbType = cbType; }
    public void setRfFwMajor(int rfFwMajor) { this.rfFwMajor = rfFwMajor; }
}
