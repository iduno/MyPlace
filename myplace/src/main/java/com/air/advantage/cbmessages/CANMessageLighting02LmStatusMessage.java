package com.air.advantage.cbmessages;

public class CANMessageLighting02LmStatusMessage extends CANMessageLighting {
    public int majorFWVersion;
    public int minorFWVersion;
    public int roomExists;
    public int validRooms;
    public int relayRooms;
    public int infoByte;

    public CANMessageLighting02LmStatusMessage() {
        super();
        this.messageType = MessageType.LM_SETUP;
        this.majorFWVersion = 0;
        this.minorFWVersion = 0;
        this.roomExists = 0;
        this.validRooms = 0;
        this.relayRooms = 0;
        this.infoByte = 0;
    }

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageLighting02LmStatusMessage msg = new CANMessageLighting02LmStatusMessage();
        
        if (data.length >= offset + 2) {
            msg.majorFWVersion = ByteArray.parseHexValue(offset, data);
            msg.minorFWVersion = ByteArray.parseHexValue(offset + 2, data);
            msg.roomExists = ByteArray.parseHexValue(offset + 4, data);
            msg.validRooms = ByteArray.parseHexValue(offset + 6, data);
            msg.relayRooms = ByteArray.parseHexValue(offset + 8, data);
            msg.infoByte = ByteArray.parseHexValue(offset + 10, data);
        }
        return msg;
    }
    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(majorFWVersion, data, offset);
        ByteArray.toHexDigits(minorFWVersion, data, offset + 2);
        ByteArray.toHexDigits(roomExists, data, offset + 4);
        ByteArray.toHexDigits(validRooms, data, offset + 6);
        ByteArray.toHexDigits(relayRooms, data, offset + 8);
        ByteArray.toHexDigits(infoByte, data, offset + 10);
        return offset + 14;
    }
    
    public int getMajorFWVersion() {
        return majorFWVersion;
    }
    
    public void setMajorFWVersion(int majorFWVersion) {
        this.majorFWVersion = majorFWVersion;
    }
    
    public int getMinorFWVersion() {
        return minorFWVersion;
    }
    
    public void setMinorFWVersion(int minorFWVersion) {
        this.minorFWVersion = minorFWVersion;
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
    
    public int getRelayRooms() {
        return relayRooms;
    }
    
    public void setRelayRooms(int relayRooms) {
        this.relayRooms = relayRooms;
    }
    
    public boolean getRelayRoom(int index) {
        if (index >= 0 && index < 6) {
            return ((relayRooms >> index) & 0x01) == 1;
        }
        return false;
    }
    
    public void setRelayRoom(int index, boolean relay) {
        if (index >= 0 && index < 6) {
            if (relay) {
                relayRooms |= (1 << index);
            } else {
                relayRooms &= ~(1 << index);
            }
        }
    }
    
    public int getInfoByte() {
        return infoByte;
    }
    
    public void setInfoByte(int infoByte) {
        this.infoByte = infoByte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CANMessageLighting02LmStatusMessage)) return false;
        if (!super.equals(o)) return false;
        CANMessageLighting02LmStatusMessage that = (CANMessageLighting02LmStatusMessage) o;
        if (majorFWVersion != that.majorFWVersion) return false;
        if (minorFWVersion != that.minorFWVersion) return false;
        if (infoByte != that.infoByte) return false;
        if (roomExists != that.roomExists) return false;
        return validRooms == that.validRooms && relayRooms == that.relayRooms;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), majorFWVersion, minorFWVersion, roomExists, validRooms, relayRooms, infoByte);
    }

}
