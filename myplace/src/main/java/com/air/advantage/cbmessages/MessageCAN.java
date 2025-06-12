package com.air.advantage.cbmessages;

import java.util.ArrayList;
import java.util.List;

public class MessageCAN extends Message {
    private List<CANMessage> messageCANBaseList;
    public MessageCAN() {
        this.messageType = MessageType.GET_CAN; // or SET_CAN based on context
    }
    private static int findEndOfMessageOffset(byte[] data, int offset) {
        if (offset >= data.length) {
            return -1;
        }
        int newOffset = offset;
        // Find the next message offset. The message is separated by a space (ASCII 32)
        while (newOffset < data.length && data[newOffset] > 32 && data[newOffset] <= 127) {
            newOffset++;
        }
        return newOffset;
    }

    public static MessageCAN deserialize(byte[] data,MessageType messageType) {
        MessageCAN message = new MessageCAN();
        message.messageType = messageType;
        message.data = new String(data);
        message.messageCANBaseList = new ArrayList<>();
        int offset = 0; 
        while (offset < data.length) {
            int messageLength = findEndOfMessageOffset(data, offset) - offset;
            if (messageLength <= 0) {
                break;
            }
            else if (messageLength < 25) {
                offset += messageLength + 1;
                continue;
            }
            CANMessage canMessage = CANMessage.deserialize(data,offset);
            message.messageCANBaseList.add(canMessage);
            offset += messageLength + 1; // +1 for the space character
        }


        return message;
    }
}