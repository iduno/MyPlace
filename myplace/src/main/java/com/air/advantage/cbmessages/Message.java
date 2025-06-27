package com.air.advantage.cbmessages;

public class Message {

    MessageType messageType;
    Boolean crcValid;
    String data;

    public enum MessageType {
        PING("Ping"),
        ACK("<ack>1</ack>"),
        NAK("<ack>0</ack>"),
        GET_CAN("getCAN "),
        SET_CAN("setCAN "),
        ACK_CAN("ackCAN "),
        UNKNOWN_REQUEST("<request>Unknown</request>"),
        GET_SYSTEM_DATA("getSystemData"),
        GET_CLOCK("getClock"),
        GET_ZONE_DATA_ZONE("getZoneData"),
        GET_ZONE_TIMER("getZoneTimer"),
        GET_SCHEDULE_DATA_SCHEDULE("getScheduleData"),
        CAN2_IN_USE("CAN2 in use"),
        UNKNOWN("Unknown");

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
            return null; // or throw an exception if preferred
        }
    }
    public Message() {
        this.messageType = MessageType.UNKNOWN;
        this.crcValid = true;
        this.data = "";
    }

    public Message(String data) {
        this.messageType = MessageType.UNKNOWN;
        this.crcValid = true;
        this.data = data;
    }

    public void setCrcValid(Boolean crcValid) {
        this.crcValid = crcValid;
    }
    public Boolean getCrcValid() {
        return crcValid;
    }
    public MessageType getMessageType() {
        return messageType;
    }

    public static Message deserialize(byte[] data) {
        MessageType messageType = MessageType.fromString(new String(data));
        // Decode the message based on the type
        if (messageType == null) {
            return new Message(new String(data));
        }
        switch (messageType) {
            case PING:
                return MessagePing.deserialize(data);
            case ACK:
                return MessageAck.deserialize(data);
            case NAK:
                return MessageNak.deserialize(data);
            case GET_CAN:
            case SET_CAN:
            case ACK_CAN:
                return MessageCAN.deserialize(data,messageType);
            case GET_SYSTEM_DATA:
            case CAN2_IN_USE:
                return MessageGetSystemData.deserialize(data,messageType);
            case GET_CLOCK:
                return MessageGetClock.deserialize(data);
            case GET_ZONE_DATA_ZONE:
                return MessageGetZoneDataZone.deserialize(data);
            case GET_ZONE_TIMER:
                return MessageGetZoneTimer.deserialize(data);
            case GET_SCHEDULE_DATA_SCHEDULE:
                return MessageGetScheduleDataSchedule.deserialize(data);
            default:
                return new Message(new String(data));
        }
    }
    public int serialize(byte[] data, int offset) {
        if (data == null) {
            return 0;
        }
        
        // Write <U> start tag
        byte[] startTag = "<U>".getBytes();
        System.arraycopy(startTag, 0, data, offset, startTag.length);
        int currentOffset = offset + startTag.length;
        
        // Write message body data
        int bytesWritten = serializeBody(data, currentOffset);
        if (bytesWritten == 0) {
            return 0;
        }
        currentOffset += bytesWritten;
        
        // Calculate CRC8 for the body data
        int crc = CalculateCRC8.calculateCRC8FromBytes(offset + startTag.length, currentOffset, data);
        
        // Write </U= tag
        byte[] endTag = "</U=".getBytes();
        System.arraycopy(endTag, 0, data, currentOffset, endTag.length);
        currentOffset += endTag.length;
        
        // Write CRC8 hex value
        ByteArray.toHexDigits(crc, data, currentOffset);
        currentOffset += 2;
        
        // Write closing >
        data[currentOffset++] = '>';
        data[currentOffset++] = ' ';
        
        return currentOffset - offset;
    }

    // Protected method for subclasses to implement their specific serialization
    protected int serializeBody(byte[] data, int offset) {
        if (this.data != null) {
            byte[] bodyData = this.data.getBytes();
            System.arraycopy(bodyData, 0, data, offset, bodyData.length);
            return bodyData.length;
        }
        return 0;
    }

}
