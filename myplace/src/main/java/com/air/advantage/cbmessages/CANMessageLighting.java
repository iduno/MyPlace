package com.air.advantage.cbmessages;


public class CANMessageLighting extends CANMessage{

    public enum MessageType {
        LM_UPDATE_LIGHT("00"),
        LM_UPDATE_BRIGHTNESS_LEVEL("01"),
        LM_UPDATE_LIGHT_WITH_ACK("02"),
        RM2_THING_STATE("15"),
        RM2_DIP_THING("16");
        

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
            case LM_UPDATE_LIGHT:
                message = CANMessageLighting00LmUpdateLight.deserialize(data, offset + 2);
                break;
            case LM_UPDATE_BRIGHTNESS_LEVEL:
                message = CANMessageLighting01LmUpdateBrightnessLevel.deserialize(data, offset + 2);
                break;
            case LM_UPDATE_LIGHT_WITH_ACK:
                message = CANMessageLighting02LmUpdateLightWithAck.deserialize(data, offset + 2);
                break;
            case RM2_THING_STATE:
                message = CANMessageLighting15Rm2ThingState.deserialize(data, offset + 2);
                break;
            case RM2_DIP_THING:
                message = CANMessageLighting16Rm2DipThing.deserialize(data, offset + 2);
                break;
            default:
                message = CANMessageLighting00LmUpdateLight.deserialize(data, offset + 2); // fallback to a default handler
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
