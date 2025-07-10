package com.air.advantage.cbmessages;

public class CANMessageAircon01ZoneInformation extends CANMessageAircon {

    public enum DestinationType {
        CB("11"),
        TABLET("20");

        private final String value;

        DestinationType(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public static DestinationType fromString(String text) {
            for (DestinationType type : DestinationType.values()) {
                if (text.startsWith(type.getValue())) {
                    return type;
                }
            }
            return null;
        }
        public static DestinationType fromBytes(byte[] data, int offset) {
            return fromString(new String(data, offset, 2));
        }
    }

    private int destination;
    private int numZones;
    private int numConstantZones;
    private int constantZone1;
    private int constantZone2;
    private int constantZone3;
    private int filterCleanStatus;
    
    public CANMessageAircon01ZoneInformation() {
        super();
        this.messageType = MessageType.ZONE_INFORMATION;
        this.destination = 0;
        this.numZones = 0;
        this.numConstantZones = 0;
        this.constantZone1 = 0;
        this.constantZone2 = 0;
        this.constantZone3 = 0;
        this.filterCleanStatus = 0;
    }

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageAircon01ZoneInformation msg = new CANMessageAircon01ZoneInformation();
        
        if (data.length >= offset + 14) {
            msg.destination = ByteArray.parseHexValue(offset, data);
            msg.numZones = ByteArray.parseHexValue(offset + 2, data);
            msg.numConstantZones = ByteArray.parseHexValue(offset + 4, data);
            msg.constantZone1 = ByteArray.parseHexValue(offset + 6, data);
            msg.constantZone2 = ByteArray.parseHexValue(offset + 8, data);
            msg.constantZone3 = ByteArray.parseHexValue(offset + 10, data);
            msg.filterCleanStatus = ByteArray.parseHexValue(offset + 12, data);
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        ByteArray.toHexDigits(destination, data, offset);
        ByteArray.toHexDigits(numZones, data, offset + 2);
        ByteArray.toHexDigits(numConstantZones, data, offset + 4);
        ByteArray.toHexDigits(constantZone1, data, offset + 6);
        ByteArray.toHexDigits(constantZone2, data, offset + 8);
        ByteArray.toHexDigits(constantZone3, data, offset + 10);
        ByteArray.toHexDigits(filterCleanStatus, data, offset + 12);
        return offset + 14;
    }

    public int getDestination() {
        return destination;
    }
    public void setDestination(int destination) {
        this.destination = destination;
    }
    public int getNumZones() {
        return numZones;
    }
    public void setNumZones(int numZones) {
        this.numZones = numZones;
    }
    public int getNumConstantZones() {
        return numConstantZones;
    }
    public void setNumConstantZones(int numConstantZones) {
        this.numConstantZones = numConstantZones;
    }
    public int getConstantZone1() {
        return constantZone1;
    }
    public void setConstantZone1(int constantZone1) {
        this.constantZone1 = constantZone1;
    }
    public int getConstantZone2() {
        return constantZone2;
    }
    public void setConstantZone2(int constantZone2) {
        this.constantZone2 = constantZone2;
    }
    public int getConstantZone3() {
        return constantZone3;
    }
    public void setConstantZone3(int constantZone3) {
        this.constantZone3 = constantZone3;
    }
    public int getFilterCleanStatus() {
        return filterCleanStatus;
    }
    public void setFilterCleanStatus(int filterCleanStatus) {
        this.filterCleanStatus = filterCleanStatus;
    }
}
