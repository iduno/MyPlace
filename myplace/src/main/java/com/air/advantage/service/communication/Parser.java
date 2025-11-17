package com.air.advantage.service.communication;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

import com.air.advantage.cbmessages.ByteArray;
import com.air.advantage.cbmessages.CalculateCRC8;
import com.air.advantage.cbmessages.Message;
import com.air.advantage.cbmessages.MessagePing;

import io.vertx.mutiny.core.buffer.Buffer;

public class Parser {
    private final byte[] internalBuffer = new byte[8192];
    private int bufferSize = 0;
    private int bufferReadPos;
    private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(100);
    private int calculatedCRC = 0;
    private int messageCRC = 0;


    public Parser() {
        Arrays.fill(internalBuffer, 0, internalBuffer.length, (byte) 0);
    }

    public void parse(Buffer buffer) {
        bufferReadPos = 0;
        try {
            // If the buffer size goes beyond the buffer length, reset it
            if (this.bufferSize + 4096 > internalBuffer.length) {
                this.bufferSize = 0;
            }
            
            int available = buffer.length();
            if (available <= 0) {
                return;
            }
            
            int readSize = Math.min(4096, available);
            byte[] tempBuffer = buffer.getBytes(0, readSize);
            System.arraycopy(tempBuffer, 0, internalBuffer, this.bufferSize, readSize);
            this.bufferSize += readSize;
            int endMessageIndex = -1;

            while (bufferReadPos < this.bufferSize) {
                endMessageIndex = -1;
                int nextTagPos = ByteArray.findParseBlockTagStart(internalBuffer, bufferReadPos, bufferSize);
                if (nextTagPos >= 0) {
                    bufferReadPos = nextTagPos;
                    int pingIndex = ByteArray.findParseBlockTagPing(internalBuffer, bufferReadPos, bufferSize);
                    // // Found a ping message
                    // if (pingIndex > 13) {
                    //     Message message = new MessagePing();
                    //     if (!messages.offer(message)) {
                    //         messages.poll(); // Remove oldest
                    //         messages.offer(message); // Try again
                    //     }
                    //     // Remove all of the Ping messages in the buffer and discard any data until the last Ping message
                    //     while (pingIndex - bufferReadPos > 13) {
                    //         bufferReadPos = pingIndex;
                    //         pingIndex = ByteArray.findParseBlockTagPing(internalBuffer, bufferReadPos, bufferSize);
                    //     }
                    // }
                    endMessageIndex = ByteArray.findParseBlockTagEnd(internalBuffer, bufferReadPos, bufferSize);
                    if (endMessageIndex >= 0) {
                        try
                        {
                            this.messageCRC = ByteArray.parseCrcValue(endMessageIndex, internalBuffer);
                            // get the data of the message contained within the U tags 3 characters for <U> 7 for </U=XX>
                            byte[] messageData = ByteArray.copySubArray(this.internalBuffer, bufferReadPos + 3, endMessageIndex - 7);
                            bufferReadPos = endMessageIndex;
                            this.calculatedCRC = CalculateCRC8.calculateCRC8FromBytes(0, messageData.length, messageData);
                        
                            Message message = Message.deserialize(messageData);
                            if (message != null) {
                                message.setCrcValid(this.calculatedCRC == this.messageCRC);
                                if (!messages.offer(message)) {
                                    messages.poll(); // Remove oldest
                                    messages.offer(message); // Try again
                                }
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (endMessageIndex == -1) {
                    break;
                }
            }
            if ((bufferReadPos > 0) && (bufferReadPos <= this.bufferSize)) {
                ByteArray.shiftArrayLeft(bufferReadPos, this.internalBuffer);
                this.bufferSize -= bufferReadPos;
            }
            
            // Consume the read bytes from the buffer
            // if (readSize > 0) {
            //     buffer = buffer.slice(readSize, buffer.length());
            // }
        } catch (Exception e) {
            // Reset the buffer if a unexpected exception occurs
            this.bufferSize = 0;
            bufferReadPos = 0;
            e.printStackTrace();
        }
    }

    public Message pollMessage() {
        return messages.poll();
    }
}
