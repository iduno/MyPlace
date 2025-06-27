package com.air.advantage.cbmessages;

public class MessageGetScheduleDataSchedule extends Message {
    public MessageGetScheduleDataSchedule() {
        this.messageType = MessageType.GET_SCHEDULE_DATA_SCHEDULE;
    }

    public static MessageGetScheduleDataSchedule deserialize(byte[] data) {
        return new MessageGetScheduleDataSchedule();
    }

    @Override
    protected int serializeBody(byte[] data, int offset) {
        String messageTypeString = this.messageType.getValue();
        System.arraycopy(messageTypeString.getBytes(), 0, data, offset, messageTypeString.length());
        return messageTypeString.length();
    }
}