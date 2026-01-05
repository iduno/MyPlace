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
            char[] chars = new char[5];
            chars[0] = (char)ByteArray.parseHexValue(offset, data);
            chars[1] = (char)ByteArray.parseHexValue(offset + 2, data);
            chars[2] = (char)ByteArray.parseHexValue(offset + 4, data);
            chars[3] = (char)ByteArray.parseHexValue(offset + 6, data);
            chars[4] = (char)ByteArray.parseHexValue(offset + 8, data);
            // Build string skipping any null (\0) characters and then trim trailing whitespace
            StringBuilder sb = new StringBuilder(5);
            for (char c : chars) {
                if (c == '\0') break; // stop at first null to preserve original semantics
                sb.append(c);
            }
            msg.errorCode = sb.toString().stripTrailing();
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
