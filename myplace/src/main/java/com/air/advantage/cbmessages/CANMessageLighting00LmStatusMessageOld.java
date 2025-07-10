package com.air.advantage.cbmessages;

public class CANMessageLighting00LmStatusMessageOld extends CANMessageLighting {
    public boolean[] roomExists = new boolean[8];
    public boolean[] validRooms = new boolean[8];
    public int version;

    public CANMessageLighting00LmStatusMessageOld() {
        super();
        this.messageType = MessageType.LM_SETUP_OLD;
        for (int i = 0; i < 8; i++) {
            this.roomExists[i] = false;
            this.validRooms[i] = false;
        }
        this.version = 0;
    }

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageLighting00LmStatusMessageOld msg = new CANMessageLighting00LmStatusMessageOld();
        
        if (data.length >= offset + 2) {
            int roomExistsBinary = ByteArray.parseHexValue(offset + 2, data);
            int validRoomsBinary = ByteArray.parseHexValue(offset + 4, data);
            msg.version = ByteArray.parseHexValue(offset + 6, data);
            for (int i = 0; i < 8; i++) {
                msg.roomExists[i] = ((roomExistsBinary >> i) & 0x01) == 1;
                msg.validRooms[i] = ((validRoomsBinary >> i) & 0x01) == 1;
            }
        }
        return msg;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        int roomExistsBinary = 0;
        int validRoomsBinary = 0;
        for (int i = 0; i < 8; i++) {
            if (roomExists[i]) roomExistsBinary |= (1 << i);
            if (validRooms[i]) validRoomsBinary |= (1 << i);
        }
        ByteArray.toHexDigits(roomExistsBinary, data, offset + 2);
        ByteArray.toHexDigits(validRoomsBinary, data, offset + 4);
        ByteArray.toHexDigits(version, data, offset + 6);
        return offset + 14;
    }
    
    public boolean[] getRoomExists() {
        return roomExists;
    }
    
    public void setRoomExists(boolean[] roomExists) {
        if (roomExists != null && roomExists.length == 8) {
            System.arraycopy(roomExists, 0, this.roomExists, 0, 8);
        }
    }
    
    public boolean getRoomExists(int index) {
        if (index >= 0 && index < 8) {
            return roomExists[index];
        }
        return false;
    }
    
    public void setRoomExists(int index, boolean exists) {
        if (index >= 0 && index < 8) {
            roomExists[index] = exists;
        }
    }
    
    public boolean[] getValidRooms() {
        return validRooms;
    }
    
    public void setValidRooms(boolean[] validRooms) {
        if (validRooms != null && validRooms.length == 8) {
            System.arraycopy(validRooms, 0, this.validRooms, 0, 8);
        }
    }
    
    public boolean getValidRoom(int index) {
        if (index >= 0 && index < 8) {
            return validRooms[index];
        }
        return false;
    }
    
    public void setValidRoom(int index, boolean valid) {
        if (index >= 0 && index < 8) {
            validRooms[index] = valid;
        }
    }
    
    public int getVersion() {
        return version;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
}
