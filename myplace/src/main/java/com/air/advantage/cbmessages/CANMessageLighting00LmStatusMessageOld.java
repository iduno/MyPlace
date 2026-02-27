package com.air.advantage.cbmessages;

public class CANMessageLighting00LmStatusMessageOld extends CANMessageLighting {
    public int roomExists;
    public int validRooms;
    public int version;

    public CANMessageLighting00LmStatusMessageOld() {
        super();
        this.messageType = MessageType.LM_SETUP_OLD;
        this.roomExists = 0;
        this.validRooms = 0;
        this.version = 0;
    }

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageLighting00LmStatusMessageOld msg = new CANMessageLighting00LmStatusMessageOld();
        
        if (data.length >= offset + 2) {
            msg.roomExists = ByteArray.parseHexValue(offset + 2, data);
            msg.validRooms = ByteArray.parseHexValue(offset + 4, data);
            msg.version = ByteArray.parseHexValue(offset + 6, data);
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(roomExists, data, offset + 2);
        ByteArray.toHexDigits(validRooms, data, offset + 4);
        ByteArray.toHexDigits(version, data, offset + 6);
        return offset + 14;
    }
    
    public int getRoomExists() {
        return roomExists;
    }
    
    public void setRoomExists(int roomExists) {
        this.roomExists = roomExists;
    }
    
    public boolean getRoomExists(int index) {
        if (index >= 0 && index < 6) {
            return ((roomExists >> index) & 0x01) == 1;
        }
        return false;
    }
    
    public void setRoomExists(int index, boolean exists) {
        if (index >= 0 && index < 6) {
            if (exists) {
                roomExists |= (1 << index);
            } else {
                roomExists &= ~(1 << index);
            }
        }
    }
    
    public int getValidRooms() {
        return validRooms;
    }
    
    public void setValidRooms(int validRooms) {
        this.validRooms = validRooms;
    }
    
    public boolean getValidRoom(int index) {
        if (index >= 0 && index < 6) {
            return ((validRooms >> index) & 0x01) == 1;
        }
        return false;
    }
    
    public void setValidRoom(int index, boolean valid) {
        if (index >= 0 && index < 6) {
            if (valid) {
                validRooms |= (1 << index);
            } else {
                validRooms &= ~(1 << index);
            }
        }
    }
    
    public int getVersion() {
        return version;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CANMessageLighting00LmStatusMessageOld)) return false;
        if (!super.equals(o)) return false;
        CANMessageLighting00LmStatusMessageOld that = (CANMessageLighting00LmStatusMessageOld) o;
        if (version != that.version) return false;
        if (roomExists != that.roomExists) return false;
        return validRooms == that.validRooms;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), roomExists, validRooms, version);
    }

}
