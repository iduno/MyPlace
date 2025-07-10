package com.air.advantage.cbmessages;

public class CANMessageLighting02LmStatusMessage extends CANMessageLighting {
    public int majorFWVersion;
    public int minorFWVersion;
    public boolean[] roomExists = new boolean[8];
    public boolean[] validRooms = new boolean[8];
    public boolean[] relayRooms = new boolean[8];
    public int infoByte;

    public CANMessageLighting02LmStatusMessage() {
        super();
        this.messageType = MessageType.LM_SETUP;
        this.majorFWVersion = 0;
        this.minorFWVersion = 0;
        for (int i = 0; i < 8; i++) {
            this.roomExists[i] = false;
            this.validRooms[i] = false;
            this.relayRooms[i] = false;
        }
        this.infoByte = 0;
    }

    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageLighting02LmStatusMessage msg = new CANMessageLighting02LmStatusMessage();
        
        if (data.length >= offset + 2) {
            msg.majorFWVersion = ByteArray.parseHexValue(offset, data);
            msg.minorFWVersion = ByteArray.parseHexValue(offset + 2, data);
            int roomExistsBinary = ByteArray.parseHexValue(offset + 4, data);
            int validRoomsBinary = ByteArray.parseHexValue(offset + 6, data);
            int relayRoomsBinary = ByteArray.parseHexValue(offset + 8, data);
            msg.infoByte = ByteArray.parseHexValue(offset + 10, data);
            for (int i = 0; i < 8; i++) {
                msg.roomExists[i] = ((roomExistsBinary >> i) & 0x01) == 1;
                msg.validRooms[i] = ((validRoomsBinary >> i) & 0x01) == 1;
                msg.relayRooms[i] = ((relayRoomsBinary >> i) & 0x01) == 1;
            }
        }
        return msg;
    }
    @Override
    public int serialize(byte[] data, int offset) {
        offset = super.serialize(data, offset);
        
        ByteArray.toHexDigits(majorFWVersion, data, offset);
        ByteArray.toHexDigits(minorFWVersion, data, offset + 2);
        int roomExistsBinary = 0;
        int validRoomsBinary = 0;
        int relayRoomsBinary = 0;
        for (int i = 0; i < 8; i++) {
            if (roomExists[i]) roomExistsBinary |= (1 << i);
            if (validRooms[i]) validRoomsBinary |= (1 << i);
            if (relayRooms[i]) relayRoomsBinary |= (1 << i);
        }
        ByteArray.toHexDigits(roomExistsBinary, data, offset + 4);
        ByteArray.toHexDigits(validRoomsBinary, data, offset + 6);
        ByteArray.toHexDigits(relayRoomsBinary, data, offset + 8);
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
    
    public boolean[] getRelayRooms() {
        return relayRooms;
    }
    
    public void setRelayRooms(boolean[] relayRooms) {
        if (relayRooms != null && relayRooms.length == 8) {
            System.arraycopy(relayRooms, 0, this.relayRooms, 0, 8);
        }
    }
    
    public boolean getRelayRoom(int index) {
        if (index >= 0 && index < 8) {
            return relayRooms[index];
        }
        return false;
    }
    
    public void setRelayRoom(int index, boolean relay) {
        if (index >= 0 && index < 8) {
            relayRooms[index] = relay;
        }
    }
    
    public int getInfoByte() {
        return infoByte;
    }
    
    public void setInfoByte(int infoByte) {
        this.infoByte = infoByte;
    }
}
