package com.air.advantage.cbmessages;

public class CANMessage {

    public enum SystemType {
        LIGHTING("02"),
        AIRCON("07"),
        RF("08"),
        UNKNOWN("00");

        private final String value;
        
        SystemType(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public static SystemType fromString(String text) {
            for (SystemType type : SystemType.values()) {
                if (text.startsWith(type.getValue())) {
                    return type;
                }
            }
            return null; // or throw an exception if preferred
        }
        public static SystemType fromBytes(byte[] data, int offset) {
            return fromString(new String(data, offset, 2));
        }
    }
    public enum DeviceType {
        UNKNOWN("00"),
        LIGHT("01"),
        RF_CONTROLLER("02"),
        AIRCON_1("03"),
        AIRCON_2("04");

        private final String value;
        
        DeviceType(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public static DeviceType fromString(String text) {
            for (DeviceType type : DeviceType.values()) {
                if (text.startsWith(type.getValue())) {
                    return type;
                }
            }
            return null; // or throw an exception if preferred
        }
        public static DeviceType fromBytes(byte[] data, int offset) {
            return fromString(new String(data, offset, 2));
        }
    }

    protected SystemType systemType;
    protected DeviceType deviceType;
    protected String uid;

    // Getters and setters
    public SystemType getSystemType() {
        return systemType;
    }

    public void setSystemType(SystemType systemType) {
        this.systemType = systemType;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessage message = null;
        SystemType systemType = SystemType.fromBytes(data, offset);
        DeviceType deviceType = DeviceType.fromBytes(data, offset + 2);
        String uid = new String(data, offset + 4, 5); // UID is 5 bytes long

        switch (systemType) {
            case LIGHTING:
                message = CANMessageLighting.deserialize(data, offset + 9);
                break;
            case RF:
            case AIRCON:
                message = CANMessageAircon.deserialize(data, offset + 9);
                break;
            default:
                return null; // or throw an exception if preferred
        }
        message.setSystemType(systemType);
        message.setDeviceType(deviceType);
        message.setUid(uid);
        return message;
    }

    /**
     * Initializes a 25-byte array (all zero) for serialization. Subclasses should call this first and then fill their fields.
     * @param data the byte array to fill (should be 25 bytes)
     * @param offset the offset to start writing in the array
     * @return the new offset after writing
     */
    public int serialize(byte[] data, int offset) {
        if (data == null || data.length < offset + 25) {
            throw new IllegalArgumentException("Data array too small for serialization");
        }
        for (int i = offset; i < offset + 25; i++) {
            data[i] = '0';
        }
        // Write systemType
        if (systemType != null) {
            byte[] sysTypeBytes = systemType.getValue().getBytes();
            System.arraycopy(sysTypeBytes, 0, data, offset, Math.min(2, sysTypeBytes.length));
        }
        // Write deviceType
        if (deviceType != null) {
            byte[] devTypeBytes = deviceType.getValue().getBytes();
            System.arraycopy(devTypeBytes, 0, data, offset + 2, Math.min(2, devTypeBytes.length));
        }
        // Write uid
        if (uid != null) {
            byte[] uidBytes = uid.getBytes();
            System.arraycopy(uidBytes, 0, data, offset + 4, Math.min(5, uidBytes.length));
        }
        return offset + 9;
    }
}
