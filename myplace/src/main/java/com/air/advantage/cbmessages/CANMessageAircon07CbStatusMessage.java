package com.air.advantage.cbmessages;

public class CANMessageAircon07CbStatusMessage extends CANMessageAircon {
    private int cbFwMajor;
    private int cbFwMinor;
    private int cbType;
    private int rfFwMajor;

    public CANMessageAircon07CbStatusMessage() {
        super();
        this.messageType = MessageType.CB_STATUS_MESSAGE;
        this.cbFwMajor = 0;
        this.cbFwMinor = 0;
        this.cbType = 0;
        this.rfFwMajor = 0;
    }

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageAircon07CbStatusMessage msg = new CANMessageAircon07CbStatusMessage();
        
        if (data.length >= offset + 8) {
            msg.cbFwMajor = ByteArray.parseHexValue(offset, data);
            msg.cbFwMinor = ByteArray.parseHexValue(offset + 2, data);
            msg.cbType = ByteArray.parseHexValue(offset + 4, data);
            msg.rfFwMajor = ByteArray.parseHexValue(offset + 6, data);
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(cbFwMajor, data, offset);
        ByteArray.toHexDigits(cbFwMinor, data, offset + 2);
        ByteArray.toHexDigits(cbType, data, offset + 4);
        ByteArray.toHexDigits(rfFwMajor, data, offset + 6);
        return offset + 14;
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
