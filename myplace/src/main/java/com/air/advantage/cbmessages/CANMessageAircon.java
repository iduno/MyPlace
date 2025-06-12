package com.air.advantage.cbmessages;


public class CANMessageAircon extends CANMessage {

    public enum MessageType {
        ZONE_INFORMATION("01"),
        UNIT_TYPE_INFORMATION("02"),
        ZONE_STATE("03"),
        ZONE_CONFIGURATION("04"),
        AIRCON_STATE("05"),
        CB_STATUS("06"),
        CB_STATUS_MESSAGE("07"),
        CB_ERROR("08"),
        ACTIVATION_CODE_INFORMATION("09"),
        MID_INFORMATION("0a"),
        ZONE_SENSOR_PAIRING("12"),
        INFO_BYTE("13"),
        RF_DEVICE_PAIRING("26"),
        RF_DEVICE_CALIBRATION("27"),
        UNKNOWN("00");

        private final String value;

        MessageType(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public static MessageType fromString(String text) {
            for (MessageType type : MessageType.values()) {
                if (text.startsWith(type.getValue())) {
                    return type;
                }
            }
            return null;
        }
        public static MessageType fromBytes(byte[] data, int offset) {
            return fromString(new String(data, offset, 2));
        }
    }

    protected MessageType messageType;

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessage message = null;
        MessageType messageType = MessageType.fromBytes(data, offset);
        switch (messageType) {
            case ZONE_INFORMATION:
                message = CANMessageAircon01ZoneInformation.deserialize(data, offset + 2);
                break;
            case UNIT_TYPE_INFORMATION:
                message = CANMessageAircon02UnitTypeInformation.deserialize(data, offset + 2);
                break;
            case ZONE_STATE:
                message = CANMessageAircon03ZoneState.deserialize(data, offset + 2);
                break;
            case ZONE_CONFIGURATION:
                message = CANMessageAircon04ZoneConfiguration.deserialize(data, offset + 2);
                break;
            case AIRCON_STATE:
                message = CANMessageAircon05AirconState.deserialize(data, offset + 2);
                break;
            case CB_STATUS:
                message = CANMessageAircon06CBStatus.deserialize(data, offset + 2);
                break;
            case CB_STATUS_MESSAGE:
                message = CANMessageAircon07CbStatusMessage.deserialize(data, offset + 2);
                break;
            case CB_ERROR:
                message = CANMessageAircon08CBErrorStatus.deserialize(data, offset + 2);
                break;
            case ACTIVATION_CODE_INFORMATION:
                message = CANMessageAircon09ActivationCodeInformation.deserialize(data, offset + 2);
                break;
            case MID_INFORMATION:
                message = CANMessageAircon0aMidInformation.deserialize(data, offset + 2);
                break;
            case ZONE_SENSOR_PAIRING:
                message = CANMessageAircon12ZoneSensorPairing.deserialize(data, offset + 2);
                break;
            case INFO_BYTE:
                message = CANMessageAircon13CBInfoByte.deserialize(data, offset + 2);
                break;
            case RF_DEVICE_PAIRING:
                message = CANMessageAircon26RfDevicePairing.deserialize(data, offset + 2);
                break;
            case RF_DEVICE_CALIBRATION:
                message = CANMessageAircon27RfDeviceCalibration.deserialize(data, offset + 2);
                break;
            case UNKNOWN:
            default:
                message = CANMessageAircon00Unknown.deserialize(data, offset + 2);
                break;
        }
        return message;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        // Write messageType
        if (messageType != null) {
            byte[] typeBytes = messageType.getValue().getBytes();
            System.arraycopy(typeBytes, 0, data, offset, Math.min(2, typeBytes.length));
        }
        offset += 2;
        // Subclass should continue from here
        return offset;
    }


}
