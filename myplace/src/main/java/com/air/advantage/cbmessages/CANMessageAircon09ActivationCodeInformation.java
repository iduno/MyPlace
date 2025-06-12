package com.air.advantage.cbmessages;

public class CANMessageAircon09ActivationCodeInformation extends CANMessageAircon {
    private int action; // 1 = set new code, 2 = unlock
    private int unlockCode; // 2 bytes
    private int activationTimeDays; // 1 byte

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageAircon09ActivationCodeInformation msg = new CANMessageAircon09ActivationCodeInformation();
        
        if (data.length >= offset + 8) {
            msg.action = ByteArray.parseHexValue(offset, data);
            msg.unlockCode = (ByteArray.parseHexValue(offset + 2, data) << 8) | ByteArray.parseHexValue(offset + 4, data);
            msg.activationTimeDays = ByteArray.parseHexValue(offset + 6, data);
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(action, data, offset);
        ByteArray.toHexDigits((unlockCode >> 8) & 0xFF, data, offset + 2);
        ByteArray.toHexDigits(unlockCode & 0xFF, data, offset + 4);
        ByteArray.toHexDigits(activationTimeDays, data, offset + 6);
        return offset + 14;
    }

    public int getAction() {
        return action;
    }
    public int getUnlockCode() {
        return unlockCode;
    }
    public int getActivationTimeDays() {
        return activationTimeDays;
    }
}
