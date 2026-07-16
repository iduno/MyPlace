package com.air.advantage.cbmessages;

import java.util.Arrays;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class MessageEventBusCodec implements MessageCodec<Message, Message> {
    public static final String CODEC_NAME = "cb-message-codec";
    private static final int SERIALIZE_BUFFER_SIZE = 4096;

    @Override
    public void encodeToWire(Buffer buffer, Message message) {
        byte[] serialized = serializeMessage(message);
        buffer.appendInt(serialized.length);
        buffer.appendBytes(serialized);
    }

    @Override
    public Message decodeFromWire(int pos, Buffer buffer) {
        int length = buffer.getInt(pos);
        int start = pos + Integer.BYTES;
        byte[] serialized = buffer.getBytes(start, start + length);
        return deserializeMessage(serialized);
    }

    @Override
    public Message transform(Message message) {
        return deserializeMessage(serializeMessage(message));
    }

    @Override
    public String name() {
        return CODEC_NAME;
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }

    private byte[] serializeMessage(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        byte[] serialized = new byte[SERIALIZE_BUFFER_SIZE];
        int bytesWritten = message.serialize(serialized, 0);
        if (bytesWritten <= 0) {
            throw new IllegalArgumentException("Unable to serialize message: " + message.getClass().getSimpleName());
        }

        return Arrays.copyOf(serialized, bytesWritten);
    }

    private Message deserializeMessage(byte[] serialized) {
        if (serialized == null || serialized.length == 0) {
            throw new IllegalArgumentException("Serialized message cannot be empty");
        }

        Parser parser = new Parser();
        parser.parse(io.vertx.mutiny.core.buffer.Buffer.buffer(serialized));
        Message message = parser.pollMessage();
        if (message == null) {
            throw new IllegalArgumentException("Unable to deserialize message from event bus payload");
        }

        return message;
    }
}
