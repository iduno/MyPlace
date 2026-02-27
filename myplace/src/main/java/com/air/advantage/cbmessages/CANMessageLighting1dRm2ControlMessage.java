package com.air.advantage.cbmessages;

public class CANMessageLighting1dRm2ControlMessage extends CANMessageLighting {
    public int roomNumber;
    public int dimLevel;
    
    public CANMessageLighting1dRm2ControlMessage() {
        super();
        this.messageType = MessageType.RM2_UPDATE_BRIGHTNESS_LEVEL;
        this.roomNumber = 0;
        this.dimLevel = 0;

    }
    
    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageLighting1dRm2ControlMessage msg = new CANMessageLighting1dRm2ControlMessage();
        
        if (data.length >= offset + 2) {
            msg.roomNumber = ByteArray.parseHexValue(offset, data);
            offset += 2;
            msg.dimLevel = ByteArray.parseHexValue(offset, data);
            offset += 2;
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        ByteArray.toHexDigits(roomNumber, data, offset);
        offset += 2;
        ByteArray.toHexDigits(dimLevel, data, offset);
        offset += 2;
        return offset;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    public int getDimLevel() {
        return dimLevel;
    }
    public void setDimLevel(int dimLevel) {
        this.dimLevel = dimLevel;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CANMessageLighting1dRm2ControlMessage)) return false;
        if (!super.equals(o)) return false;
        CANMessageLighting1dRm2ControlMessage that = (CANMessageLighting1dRm2ControlMessage) o;
        return roomNumber == that.roomNumber && dimLevel == that.dimLevel;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), roomNumber, dimLevel);
    }

}
