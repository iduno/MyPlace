package com.air.advantage.cbmessages;

public class CANMessageLighting02LmUpdateLightWithAck extends CANMessageLighting {
    public int majorFWVersion;
    public int minorFWVersion;
    public boolean[] roomExists = new boolean[8];
    public boolean[] validRooms = new boolean[8];
    public boolean[] relayRooms = new boolean[8];
    public int infoByte;



    public static CANMessage deserialize(byte[] data, int offset) {
        CANMessageLighting02LmUpdateLightWithAck msg = new CANMessageLighting02LmUpdateLightWithAck();
        
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
}
