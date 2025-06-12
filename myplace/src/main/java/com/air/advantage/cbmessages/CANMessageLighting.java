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
            StringBuilder sb = new StringBuilder();
            for (int i = offset; i < offset + 2; i++) {
                sb.append(String.format("%02x", data[i]));
            }
            return fromString(sb.toString());
        }
    }

    protected MessageType messageType;

    public static CANMessage deserialize(byte[] data, int offset) {
        // Implement logic for base class if needed
        return null;
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
