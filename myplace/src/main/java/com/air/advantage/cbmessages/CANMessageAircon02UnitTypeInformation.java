package com.air.advantage.cbmessages;

public class CANMessageAircon02UnitTypeInformation extends CANMessageAircon {
    public enum CodeStatus {
        NO_CODE(0),
        CODE_ENABLED(1),
        EXPIRED(2);
        private final int value;
        CodeStatus(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
        public static CodeStatus fromValue(int value) {
            for (CodeStatus status : CodeStatus.values()) {
                if (status.value == value) {
                    return status;
                }
            }
            return null;
        }
    }
    private int unitType;
    private CodeStatus activationStatus;
    private int fwMajor;
    private int fwMinor;

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageAircon02UnitTypeInformation msg = new CANMessageAircon02UnitTypeInformation();
        
        if (data.length >= offset + 4) {
            msg.unitType = ByteArray.parseHexValue(offset, data);
            msg.activationStatus = CodeStatus.fromValue(ByteArray.parseHexValue(offset + 2, data));
            if (data.length >= offset + 8) {
                msg.fwMajor = ByteArray.parseHexValue(offset + 4, data);
                msg.fwMinor = ByteArray.parseHexValue(offset + 6, data);
            }
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(unitType, data, offset);
        ByteArray.toHexDigits(activationStatus != null ? activationStatus.getValue() : 0, data, offset + 2);
        ByteArray.toHexDigits(fwMajor, data, offset + 4);
        ByteArray.toHexDigits(fwMinor, data, offset + 6);
        return offset + 14;
    }

    public int getUnitType() { return unitType; }
    public CodeStatus getActivationStatus() { return activationStatus; }
    public int getFwMajor() { return fwMajor; }
    public int getFwMinor() { return fwMinor; }
}
