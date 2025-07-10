package com.air.advantage.cbmessages;


public class CANMessageLighting extends CANMessage{

    public enum MessageType {
        LM_SETUP_OLD("00"),
        LM_UPDATE_BRIGHTNESS_LEVEL("01"),
        LM_SETUP("02"),
        RM2_THING_STATE("15"),
        RM2_DIP_THING("16"),
        RM2_STATUS_ADD_DEVICE("17");
        

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

    public CANMessageLighting() {
        super();
        this.systemType = SystemType.LIGHTING;
        this.deviceType = DeviceType.UNKNOWN; // Default device type
    }

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessage message = null;
        MessageType messageType = MessageType.fromBytes(data, offset);
        offset += 2;
        switch (messageType) {
            case LM_SETUP_OLD:
                message = CANMessageLighting00LmStatusMessageOld.deserialize(data, offset);
                break;
            case LM_UPDATE_BRIGHTNESS_LEVEL:
                message = CANMessageLighting01LmControlMessage.deserialize(data, offset);
                break;
            case LM_SETUP:
                message = CANMessageLighting02LmStatusMessage.deserialize(data, offset);
                break;
            case RM2_THING_STATE:
                message = CANMessageLighting15Rm2ControlMessage.deserialize(data, offset);
                break;
            case RM2_DIP_THING:
                message = CANMessageLighting16Rm2StatusMessage.deserialize(data, offset);
                break;
            case RM2_STATUS_ADD_DEVICE:
                message = CANMessageLighting17Rm2AddDevice.deserialize(data, offset);
                break;
            default:
                message = CANMessageLighting00LmStatusMessageOld.deserialize(data, offset); // fallback to a default handler
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
