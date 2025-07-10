package com.air.advantage.cbmessages;

public class CANMessageAircon12ZoneSensorPairing extends CANMessageAircon {

    private String sensorUID;
    private int infoByte;
    private int sensorMajorRev;

    public CANMessageAircon12ZoneSensorPairing() {
        super();
        this.messageType = MessageType.ZONE_SENSOR_PAIRING;
        this.sensorUID = null;
        this.infoByte = 0;
        this.sensorMajorRev = 0;
    }

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
    
    public String getSensorUID() {
        return sensorUID;
    }
    
    public void setSensorUID(String sensorUID) {
        this.sensorUID = sensorUID;
    }
    
    public int getInfoByte() {
        return infoByte;
    }
    
    public void setInfoByte(int infoByte) {
        this.infoByte = infoByte;
    }
    
    public int getSensorMajorRev() {
        return sensorMajorRev;
    }
    
    public void setSensorMajorRev(int sensorMajorRev) {
        this.sensorMajorRev = sensorMajorRev;
    }
}
