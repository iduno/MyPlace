package com.air.advantage.cbmessages;

import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;

public class Parser {
    private InputStream inputStream;
    private final byte[] buffer = new byte[3072]; 
    private int bufferSize = 0;
    private int bufferReadPos;
    private ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(100);
    private int calculatedCRC = 0;
    private int messageCRC = 0;


    Parser(InputStream inputStream) {
        this.inputStream = inputStream;
        Arrays.fill(buffer, 0, buffer.length, (byte) 0);

    }

    public void parse() {
        bufferReadPos = 0;
        try {
            // If the buffer size goes beyond the buffer length, reset it
            if (this.bufferSize + 256 > buffer.length) {
                this.bufferSize = 0;
            }
            int readSize = inputStream.read(buffer,this.bufferSize, 256);
            if (readSize == -1) {
                return;
            }
            this.bufferSize += readSize;
            int endMessageIndex = -1;

            while (bufferReadPos < this.bufferSize) {
                endMessageIndex = -1;
                bufferReadPos = ByteArray.findParseBlockTagStart(buffer, bufferReadPos, bufferSize);
                if (bufferReadPos >= 0) {
                    int pingIndex = ByteArray.findParseBlockTagPing(buffer, bufferReadPos, bufferSize);
                    // Found a ping message
                    if (pingIndex > 13) {
                        Message message = new MessagePing();
                        if (!messages.offer(message)) {
                            messages.poll(); // Remove oldest
                            messages.offer(message); // Try again
                        }
                        // Remove all of the Ping messages in the buffer and discard any data until the last Ping message
                        while (pingIndex - bufferReadPos > 13) {
                            bufferReadPos = pingIndex;
                            pingIndex = ByteArray.findParseBlockTagPing(buffer, bufferReadPos, bufferSize);
                        }
                    }
                    endMessageIndex = ByteArray.findParseBlockTagEnd(buffer, bufferReadPos, bufferSize);
                    if (endMessageIndex >= 0) {
                        try
                        {
                            this.messageCRC = ByteArray.parseCrcValue(endMessageIndex, buffer);
                            // get the data of the message contained within the U tags 3 characters for <U> 7 for </U=XX>
                            byte[] messageData = ByteArray.copySubArray(this.buffer, bufferReadPos + 3, endMessageIndex - 7);
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
                            // Handle the exception if needed
                            e.printStackTrace();
                        }
                    }
                }

                if (endMessageIndex == -1) {
                    break;
                }
            }
            if ((bufferReadPos > 0) && (bufferReadPos <= this.bufferSize)) {
                ByteArray.shiftArrayLeft(bufferReadPos, this.buffer);
                this.bufferSize -= bufferReadPos;
            }
        } catch (Exception e) {
            // Reset the buffer if a unexpected exception occurs
            this.bufferSize=0;
            bufferReadPos=0;
            e.printStackTrace();

        }
    }

    public Message pollMessage() {
        return messages.poll();
    }
}
