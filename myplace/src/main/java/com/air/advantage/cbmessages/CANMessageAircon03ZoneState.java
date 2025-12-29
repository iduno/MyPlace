package com.air.advantage.cbmessages;

public class CANMessageAircon03ZoneState extends CANMessageAircon {
    private int zoneNumber;
    private ZoneState zoneState;
    private int zonePercent;
    private int sensorType;
    private float setTemp;
    private float measuredTemp;

    public CANMessageAircon03ZoneState() {
        super();
        this.messageType = MessageType.ZONE_STATE;
        this.zoneNumber = 0;
        this.zoneState = null;
        this.zonePercent = 0;
        this.sensorType = 0;
        this.setTemp = 0.0f;
        this.measuredTemp = 0.0f;
    }

    public enum ZoneState {
        CLOSE(0),
        OPEN(1);
        private final int value;

        ZoneState(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
        public static ZoneState fromValue(int value) {
            for (ZoneState state : ZoneState.values()) {
                if (state.value == value) {
                    return state;
                }
            }
            throw new IllegalArgumentException("Invalid ZoneState value: " + value);
        }
    }

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageAircon03ZoneState msg = new CANMessageAircon03ZoneState();
        
        if (data.length >= offset + 12) {
            msg.zoneNumber = ByteArray.parseHexValue(offset, data);
            int stateByte = ByteArray.parseHexValue(offset + 2, data);
            msg.zoneState = ZoneState.fromValue((stateByte & 0x80) >> 7);
            msg.zonePercent = stateByte & 0x7F;
            msg.sensorType = ByteArray.parseHexValue(offset + 4, data);
            msg.setTemp = ByteArray.parseHexValue(offset + 6, data) / 2.0f;
            // Measured temperature is in the format of xx.yy
            int measuredTempInt = ByteArray.parseHexValue(offset + 8, data);
            int measuredTempDec = ByteArray.parseHexValue(offset + 10, data);
            msg.measuredTemp = Float.parseFloat(measuredTempInt + "." + measuredTempDec);
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(zoneNumber, data, offset);
        int stateByte = (zoneState != null ? zoneState.getValue() <<7 : 0) | (zonePercent & 0x7F);
        ByteArray.toHexDigits(stateByte, data, offset + 2);
        ByteArray.toHexDigits(sensorType, data, offset + 4);
        ByteArray.toHexDigits((int)(setTemp * 2.0f), data, offset + 6);
        int measuredTempInt = (int) measuredTemp;
        int measuredTempDec = (int) ((measuredTemp - measuredTempInt) * 10);
        ByteArray.toHexDigits(measuredTempInt, data, offset + 8);
        ByteArray.toHexDigits(measuredTempDec, data, offset + 10);
        return offset + 14;
    }

    public int getZoneNumber() { return zoneNumber; }
    public ZoneState getZoneState() { return zoneState; }
    public int getZonePercent() { return zonePercent; }
    public int getSensorType() { return sensorType; }
    public float getSetTemp() { return setTemp; }
    public float getMeasuredTemp() { return measuredTemp; }
    
    public void setZoneNumber(int zoneNumber) { this.zoneNumber = zoneNumber; }
    public void setZoneState(ZoneState zoneState) { this.zoneState = zoneState; }
    public void setZonePercent(int zonePercent) { this.zonePercent = zonePercent; }
    public void setSensorType(int zoneType) { this.sensorType = zoneType; }
    public void setSetTemp(float setTemp) { this.setTemp = setTemp; }
    public void setMeasuredTemp(float measuredTemp) { this.measuredTemp = measuredTemp; }
}
