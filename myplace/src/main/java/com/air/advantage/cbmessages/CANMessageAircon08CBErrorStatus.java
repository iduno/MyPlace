package com.air.advantage.cbmessages;

public class CANMessageAircon08CBErrorStatus extends CANMessageAircon {
    private String errorCode;

    public CANMessageAircon08CBErrorStatus() {
        super();
        this.messageType = MessageType.CB_ERROR;
        this.errorCode = null;
    }

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageAircon08CBErrorStatus msg = new CANMessageAircon08CBErrorStatus();
        
        if (data.length >= offset + 10) {
            char errChar1 = (char)ByteArray.parseHexValue(offset, data);
            char errChar2 = (char)ByteArray.parseHexValue(offset + 2, data);
            char errChar3 = (char)ByteArray.parseHexValue(offset + 4, data);
            char errChar4 = (char)ByteArray.parseHexValue(offset + 6, data);
            char errChar5 = (char)ByteArray.parseHexValue(offset + 8, data);
            msg.errorCode = ("" + errChar1 + errChar2 + errChar3 + errChar4 + errChar5).stripTrailing();
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        String code = errorCode != null ? errorCode : "";
        for (int i = 0; i < Math.min(5, code.length()); i++) {
            ByteArray.toHexDigits(code.charAt(i), data, offset + i * 2);
        }
        return offset + 14;
    }

    public String getErrorCode() { return errorCode; }
    
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }
}
