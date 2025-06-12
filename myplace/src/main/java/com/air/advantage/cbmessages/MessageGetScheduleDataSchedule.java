package com.air.advantage.cbmessages;

public class MessageGetScheduleDataSchedule extends Message {
    public MessageGetScheduleDataSchedule() {
        this.messageType = MessageType.GET_SCHEDULE_DATA_SCHEDULE;
    }

    public static MessageGetScheduleDataSchedule deserialize(byte[] data) {
        return new MessageGetScheduleDataSchedule();
    }
}