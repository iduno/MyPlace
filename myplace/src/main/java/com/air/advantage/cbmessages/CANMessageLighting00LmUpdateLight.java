package com.air.advantage.cbmessages;

public class CANMessageLighting00LmUpdateLight extends CANMessageLighting {
    public boolean[] roomExists = new boolean[8];
    public boolean[] validRooms = new boolean[8];
    public int version;



    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageLighting00LmUpdateLight msg = new CANMessageLighting00LmUpdateLight();
        
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
}
