package com.air.advantage.cbmessages;

public class CANMessage {

    public enum SystemType {
        LIGHTING("02"),
        AIRCON("07"),
        RF("08"),
        UNKNOWN("00");

        private final String value;
        private String rawText = null;
        
        SystemType(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public String getRawText() {
            return rawText != null ? rawText : value;
        }
        public static SystemType fromString(String text) {
            for (SystemType type : SystemType.values()) {
                if (!type.equals(UNKNOWN) && text.startsWith(type.getValue())) {
                    return type;
                }
            }
            SystemType unknown = UNKNOWN;
            unknown.rawText = text;
            return unknown;
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
        private String rawText = null;
        
        DeviceType(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public String getRawText() {
            return rawText != null ? rawText : value;
        }
        public static DeviceType fromString(String text) {
            for (DeviceType type : DeviceType.values()) {
                if (!type.equals(UNKNOWN) && text.startsWith(type.getValue())) {
                    return type;
                }
            }
            DeviceType unknown = UNKNOWN;
            unknown.rawText = text;
            return unknown;
        }
        public static DeviceType fromBytes(byte[] data, int offset) {
            return fromString(new String(data, offset, 2));
        }
    }

    protected SystemType systemType;
    protected DeviceType deviceType;
    protected String uid;
    protected byte[] payload;

    public CANMessage() {
        this.systemType = SystemType.UNKNOWN;
        this.deviceType = DeviceType.UNKNOWN;
        this.uid = "00000"; // Default UID
        this.payload = new byte[8]; // Default payload size
    }

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

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public static CANMessage deserialize(byte[] data, int offset) {
        // Parse systemType (2 bytes)
        SystemType systemType = SystemType.fromBytes(data, offset);
        offset += 2;
        // Parse deviceType (2 bytes)
        DeviceType deviceType = DeviceType.fromBytes(data, offset);
        offset += 2;
        // Parse uid (5 bytes as string)
        String uid = new String(data, offset, 5);
        offset += 5;
        // Parse payload (remaining bytes) as hex values
        int payloadLength = 8;
        byte[] payload = new byte[payloadLength];
        for (int i = 0; i < payloadLength; i++) {
            int hexIndex = offset + (i * 2);
            if (hexIndex + 1 < data.length) {
                payload[i] = (byte) ByteArray.parseHexValue(hexIndex, data);
            }
        }

        CANMessage message = null;
        switch (systemType) {
            case LIGHTING:
                message = CANMessageLighting.deserialize(data, offset);
                break;
            case RF:
            case AIRCON:
                message = CANMessageAircon.deserialize(data, offset);
                break;
            default:
                message = new CANMessage();
                break;
        }
        if (message != null) {
            message.setSystemType(systemType);
            message.setDeviceType(deviceType);
            message.setUid(uid);
            message.setPayload(payload);
        }
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
        // Write systemType (2 bytes)
        if (systemType != null) {
            byte[] sysTypeBytes = systemType.getValue().getBytes();
            System.arraycopy(sysTypeBytes, 0, data, offset, Math.min(2, sysTypeBytes.length));
        }
        offset += 2;
        // Write deviceType (2 bytes)
        if (deviceType != null) {
            byte[] devTypeBytes = deviceType.getValue().getBytes();
            System.arraycopy(devTypeBytes, 0, data, offset, Math.min(2, devTypeBytes.length));
        }
        offset += 2;
        // Write uid (5 bytes)
        if (uid != null) {
            byte[] uidBytes = uid.getBytes();
            System.arraycopy(uidBytes, 0, data, offset, Math.min(5, uidBytes.length));
        }
        offset += 5;

        return offset;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CANMessage{");
        sb.append("systemType=").append(systemType != null ? systemType.getRawText() : "null").append(", ");
        sb.append("deviceType=").append(deviceType != null ? deviceType.getRawText() : "null").append(", ");
        sb.append("uid='").append(uid).append('\'');
        sb.append(", payload=");
        if (payload != null) {
            sb.append(ByteArray.toHexString(payload));
        } else {
            sb.append("null");
        }
        sb.append('}');
        return sb.toString();
    }
}
