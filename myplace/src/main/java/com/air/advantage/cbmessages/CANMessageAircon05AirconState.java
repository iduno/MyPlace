package com.air.advantage.cbmessages;

public class CANMessageAircon05AirconState extends CANMessageAircon {
    public enum SystemState {
        OFF(0),
        ON(1);

        private final int value;

        SystemState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
        public static SystemState fromValue(int value) {
            for (SystemState state : SystemState.values()) {
                if (state.getValue() == value) {
                    return state;
                }
            }
            return null;
        }
    }

    public enum SystemMode {
        COOL(1),
        HEAT(2),
        VENT(3),
        AUTO(4),
        DRY(5),
        MYAUTO(6);

        private final int value;

        SystemMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
        public static SystemMode fromValue(int value) {
            for (SystemMode mode : SystemMode.values()) {
                if (mode.getValue() == value) {
                    return mode;
                }
            }
            return null;
        }
    }
    public enum FanState {
        OFF(0),
        LOW(1),
        MEDIUM(2),
        HIGH(3),
        AUTO(4),
        AUTOAA(5);

        private final int value;

        FanState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
        public static FanState fromValue(int value) {
            for (FanState state : FanState.values()) {
                if (state.getValue() == value) {
                    return state;
                }
            }
            return null;
        }
    }
    public enum FreshAirStatus {
        NONE(0),
        OFF(1),
        ON(2);

        private final int value;

        FreshAirStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
        public static FreshAirStatus fromValue(int value) {
            for (FreshAirStatus status : FreshAirStatus.values()) {
                if (status.getValue() == value) {
                    return status;
                }
            }
            return null;
        }
    }
    private SystemState systemState;
    private SystemMode systemMode;
    private FanState systemFan;
    private float setTemp;
    private int myZoneId;
    private FreshAirStatus freshAirStatus;
    private int rfSysId;

    public CANMessageAircon05AirconState() {
        super();
        this.messageType = MessageType.AIRCON_STATE;
        this.systemState = null;
        this.systemMode = null;
        this.systemFan = null;
        this.setTemp = 0.0f;
        this.myZoneId = 0;
        this.freshAirStatus = null;
        this.rfSysId = 0;
    }

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageAircon05AirconState msg = new CANMessageAircon05AirconState();
        
        if (data.length >= offset + 14) {
            msg.systemState = SystemState.fromValue(ByteArray.parseHexValue(offset, data));
            msg.systemMode = SystemMode.fromValue(ByteArray.parseHexValue(offset + 2, data));
            msg.systemFan = FanState.fromValue(ByteArray.parseHexValue(offset + 4, data));
            msg.setTemp = (ByteArray.parseHexValue(offset + 6, data)) / 2.0f;
            msg.myZoneId = ByteArray.parseHexValue(offset + 8, data);
            msg.freshAirStatus = FreshAirStatus.fromValue(ByteArray.parseHexValue(offset + 10, data));
            msg.rfSysId = ByteArray.parseHexValue(offset + 12, data);
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(systemState != null ? systemState.getValue() : 0, data, offset);
        ByteArray.toHexDigits(systemMode != null ? systemMode.getValue() : 0, data, offset + 2);
        ByteArray.toHexDigits(systemFan != null ? systemFan.getValue() : 0, data, offset + 4);
        ByteArray.toHexDigits((int)(setTemp * 2.0f), data, offset + 6);
        ByteArray.toHexDigits(myZoneId, data, offset + 8);
        ByteArray.toHexDigits(freshAirStatus != null ? freshAirStatus.getValue() : 0, data, offset + 10);
        ByteArray.toHexDigits(rfSysId, data, offset + 12);
        return offset + 14;
    }

    public SystemState getSystemState() { return systemState; }
    public SystemMode getSystemMode() { return systemMode; }
    public FanState getSystemFan() { return systemFan; }
    public float getSetTemp() { return setTemp; }
    public int getMyZoneId() { return myZoneId; }
    public FreshAirStatus getFreshAirStatus() { return freshAirStatus; }
    public int getRfSysId() { return rfSysId; }
    
    public void setSystemState(SystemState systemState) { this.systemState = systemState; }
    public void setSystemMode(SystemMode systemMode) { this.systemMode = systemMode; }
    public void setSystemFan(FanState systemFan) { this.systemFan = systemFan; }
    public void setSetTemp(float setTemp) { this.setTemp = setTemp; }
    public void setMyZoneId(int myZoneId) { this.myZoneId = myZoneId; }
    public void setFreshAirStatus(FreshAirStatus freshAirStatus) { this.freshAirStatus = freshAirStatus; }
    public void setRfSysId(int rfSysId) { this.rfSysId = rfSysId; }
}
