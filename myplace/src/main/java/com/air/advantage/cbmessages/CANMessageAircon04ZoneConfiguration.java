package com.air.advantage.cbmessages;

public class CANMessageAircon04ZoneConfiguration extends CANMessageAircon {
    private int zoneNumber;
    private int minDamper;
    private int maxDamper;
    private int motionStatus;
    private int motionConfig;
    private int zoneError;
    private int rssi;

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageAircon04ZoneConfiguration msg = new CANMessageAircon04ZoneConfiguration();
        
        if (data.length >= offset + 14) {
            msg.zoneNumber = ByteArray.parseHexValue(offset, data);
            msg.minDamper = ByteArray.parseHexValue(offset + 2, data);
            msg.maxDamper = ByteArray.parseHexValue(offset + 4, data);
            msg.motionStatus = ByteArray.parseHexValue(offset + 6, data);
            msg.motionConfig = ByteArray.parseHexValue(offset + 8, data);
            msg.zoneError = ByteArray.parseHexValue(offset + 10, data);
            msg.rssi = ByteArray.parseHexValue(offset + 12, data);
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(zoneNumber, data, offset);
        ByteArray.toHexDigits(minDamper, data, offset + 2);
        ByteArray.toHexDigits(maxDamper, data, offset + 4);
        ByteArray.toHexDigits(motionStatus, data, offset + 6);
        ByteArray.toHexDigits(motionConfig, data, offset + 8);
        ByteArray.toHexDigits(zoneError, data, offset + 10);
        ByteArray.toHexDigits(rssi, data, offset + 12);
        return offset + 14;
    }

    public int getZoneNumber() { return zoneNumber; }
    public int getMinDamper() { return minDamper; }
    public int getMaxDamper() { return maxDamper; }
    public int getMotionStatus() { return motionStatus; }
    public int getMotionConfig() { return motionConfig; }
    public int getZoneError() { return zoneError; }
    public int getRssi() { return rssi; }
}
