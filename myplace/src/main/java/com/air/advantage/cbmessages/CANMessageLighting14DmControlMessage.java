package com.air.advantage.cbmessages;

public class CANMessageLighting14DmControlMessage extends CANMessageLighting {
    public int roomNumber;
    public boolean lightState;
    public int dimLevel;
    public int infoByte;
    
    public CANMessageLighting14DmControlMessage() {
        super();
        this.messageType = MessageType.DM_UPDATE_BRIGHTNESS_LEVEL;
        this.roomNumber = 0;
        this.lightState = false;
        this.dimLevel = 0;

    }
    
    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageLighting14DmControlMessage msg = new CANMessageLighting14DmControlMessage();
        
        if (data.length >= offset + 2) {
            msg.roomNumber = ByteArray.parseHexValue(offset, data);
            offset += 2;
            msg.infoByte = ByteArray.parseHexValue(offset, data);
            offset += 2;
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        ByteArray.toHexDigits(roomNumber, data, offset);
        offset += 2;
        ByteArray.toHexDigits(infoByte, data, offset);
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
        if (!(o instanceof CANMessageLighting14DmControlMessage)) return false;
        if (!super.equals(o)) return false;
        CANMessageLighting14DmControlMessage that = (CANMessageLighting14DmControlMessage) o;
        return roomNumber == that.roomNumber && dimLevel == that.dimLevel;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), roomNumber, dimLevel);
    }

}
