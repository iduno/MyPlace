package com.air.advantage.cbmessages;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class CANMessageEventBusCodec implements MessageCodec<CANMessage, CANMessage> {
    public static final String CODEC_NAME = "can-message-codec";
    private static final int CAN_FRAME_LENGTH = 25;

    @Override
    public void encodeToWire(Buffer buffer, CANMessage message) {
        byte[] frame = toFrame(message);
        buffer.appendInt(frame.length);
        buffer.appendBytes(frame);
    }

    @Override
    public CANMessage decodeFromWire(int pos, Buffer buffer) {
        int length = buffer.getInt(pos);
        int start = pos + Integer.BYTES;
        byte[] frame = buffer.getBytes(start, start + length);
        return fromFrame(frame);
    }

    @Override
    public CANMessage transform(CANMessage message) {
        return fromFrame(toFrame(message));
    }

    @Override
    public String name() {
        return CODEC_NAME;
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }

    private byte[] toFrame(CANMessage message) {
        if (message == null) {
            throw new IllegalArgumentException("CANMessage cannot be null");
        }

        byte[] frame = new byte[CAN_FRAME_LENGTH];
        int bytesWritten = message.serialize(frame, 0);
        if (bytesWritten != CAN_FRAME_LENGTH) {
            throw new IllegalArgumentException("Expected 25-byte CAN frame, got " + bytesWritten);
        }
        return frame;
    }

    private CANMessage fromFrame(byte[] frame) {
        if (frame == null || frame.length != CAN_FRAME_LENGTH) {
            throw new IllegalArgumentException("Invalid CAN frame length: " + (frame == null ? -1 : frame.length));
        }

        return CANMessage.deserialize(frame, 0);
    }
}
