package com.air.advantage.cbmessages;

public class CANMessageAircon12ZoneSensorPairing extends CANMessageAircon {

    private String sensorUID;
    private int infoByte;
    private int sensorMajorRev;

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageAircon12ZoneSensorPairing message = new CANMessageAircon12ZoneSensorPairing();
        
        if (data.length >= offset + 10) {
            message.sensorUID = new String(data, offset, 6);
            message.infoByte = ByteArray.parseHexValue(offset + 6, data);
            message.sensorMajorRev = ByteArray.parseHexValue(offset + 8, data);
        }
        return message;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        if (sensorUID != null) {
            byte[] uidBytes = sensorUID.getBytes();
            System.arraycopy(uidBytes, 0, data, offset, Math.min(6, uidBytes.length));
        }
        ByteArray.toHexDigits(infoByte, data, offset + 6);
        ByteArray.toHexDigits(sensorMajorRev, data, offset + 8);
        return offset + 14;
    }
}
