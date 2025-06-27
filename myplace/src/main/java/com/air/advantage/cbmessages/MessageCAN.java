package com.air.advantage.cbmessages;

import java.util.ArrayList;
import java.util.List;

public class MessageCAN extends Message {
    private List<CANMessage> messageCANBaseList;
    public MessageCAN() {
        this.messageType = MessageType.GET_CAN; // or SET_CAN based on context
        this.messageCANBaseList = new ArrayList<>();
    }
    public MessageCAN(MessageType messageType) {
        this.messageType = messageType;
        this.messageCANBaseList = new ArrayList<>();
    }

    public List<CANMessage> getMessageCANBaseList() {
        return messageCANBaseList;
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

    @Override
    protected int serializeBody(byte[] data, int offset) {
        if (data == null || data.length < offset + 25) {
            throw new IllegalArgumentException("Data array too small for serialization");
        }
        int currentOffset = offset;
        // Serialize the message type
        String messageTypeString = this.messageType.getValue();
        System.arraycopy(messageTypeString.getBytes(), 0, data, currentOffset, messageTypeString.length());
        currentOffset += messageTypeString.length();

        // Serialize each CANMessage in the list
        for (CANMessage canMessage : messageCANBaseList) {
            currentOffset = canMessage.serialize(data, currentOffset);
            data[currentOffset++] = ' '; // Add space between messages
        }
        return currentOffset - offset;
    }
}