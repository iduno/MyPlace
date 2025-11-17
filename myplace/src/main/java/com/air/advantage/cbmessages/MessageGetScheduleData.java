package com.air.advantage.cbmessages;

public class MessageGetScheduleData extends Message {
    public MessageGetScheduleData() {
        this.messageType = MessageType.GET_SCHEDULE_DATA;
    }

    public static MessageGetScheduleData deserialize(byte[] data) {
        MessageGetScheduleData message = new MessageGetScheduleData();
        message.data = new String(data);
        return message;
    }

    @Override
    protected int serializeBody(byte[] data, int offset) {
        String messageTypeString = this.messageType.getValue();
        System.arraycopy(messageTypeString.getBytes(), 0, data, offset, messageTypeString.length());
        return messageTypeString.length();
    }
}