package com.air.advantage.aaservice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.air.advantage.cbmessages.ByteArray;
import com.air.advantage.cbmessages.CalculateCRC8;
import com.air.advantage.libraryairconlightjson.TabletInfo;

public class HandlerUartStream {

    private static final Logger LOGGER = Logger.getLogger(HandlerUartStream.class.getName());

    boolean isRunning;
    private InputStream uartInputStream;
    private int bufferSize;
    private int bytesRead;
    private int bytesToWrite;
    private int bufferReadPos;
    private OutputStream uartOutputStream;
    final String LOGNAME = HandlerUartStream.class.getSimpleName();
    final ArrayList<String> requestMessageList = new ArrayList<>();
    final AtomicBoolean isCan2InUse = new AtomicBoolean(false);
    final ArrayList<String> outboundMessageQueue = new ArrayList<>();
    private final byte[] buffer = new byte[3072];
    private final ParseCBMessage runnableParseMessage = new ParseCBMessage();
    int runMode = 1;
    AtomicBoolean hasMessageBeenSent = new AtomicBoolean(false);
    AtomicReference<String> currentMessage = new AtomicReference<>("");
    AtomicInteger requestMessageIndex = new AtomicInteger(0);
    AtomicBoolean isMessageInProgress = new AtomicBoolean(false);
    private int bufferPos = 0;
    private boolean isOk = false;
    private boolean sendReply = false;
    private int successAck = 1;
    private int retryCount = 0;
    private boolean messageInProgress = false;
    private int messagesSent = 0;

    HandlerUartStream(InputStream inputStream, OutputStream outputStream) {
        if (inputStream == null || outputStream == null) {
            return;
        }
        this.uartInputStream = inputStream;
        this.uartOutputStream = outputStream;
        this.requestMessageList.clear();

        CalculateCRC8 calculateCRC8 = new CalculateCRC8();
        for (String str : ByteArray.SYSTEM_DATA_REQUESTS) {
            this.requestMessageList.add(String.format(ByteArray.PARSE_BLOCK_TAG, str, calculateCRC8.getCRC8HexString(str)));
        }
        if (TabletInfo.isMyAir5OrTspTablet() || TabletInfo.isEZoneTablet()) {
            return;
        }
        for (String str2 : ByteArray.SCHEDULE_DATA_REQUESTS) {
            this.requestMessageList.add(String.format(ByteArray.PARSE_BLOCK_TAG, str2, calculateCRC8.getCRC8HexString(str2)));
        }
    }

    private void removePingMessages() {
        ByteArray byteArray = new ByteArray();
        this.bytesToWrite = byteArray.findParseBlockTagPing(buffer, 0, buffer.length);
        while (true) {
            int i = this.bytesToWrite;
            if (i <= 13) {
                return;
            }
            byteArray.shiftArrayLeft(i, this.buffer);
            this.bufferPos -= this.bytesToWrite;
            this.bytesToWrite = byteArray.findParseBlockTagPing(buffer, 0, buffer.length);
        }
    }

    private void processMessage() {
        // ServiceUart serviceUart;
        // WeakReference<ServiceUart> weakReference = ServiceUart.instance;

        // if (weakReference != null && (serviceUart = weakReference.get()) != null) {
        //     serviceUart.showNotification(true);
        // }

// Handle sending reply
        if (this.sendReply) {
            String ackMessage = "ackCAN " + this.successAck;
            String formattedMessage = String.format(ByteArray.PARSE_BLOCK_TAG, ackMessage, new CalculateCRC8().getCRC8HexString(ackMessage));
            sendMessageToUart(formattedMessage.getBytes(Charset.defaultCharset()));
            this.sendReply = false;
            this.hasMessageBeenSent.set(true);
            return;
        }

        if (this.messageInProgress || this.isCan2InUse.get()) {
            if (HandlerCan.isRetry) {
                HandlerCan handlerCan = HandlerCan.getInstance();
                synchronized (handlerCan.dataCanMessageStore) {
                    String currentMessage = handlerCan.dataCanMessageStore.getCurrentMessage();
                    LOGGER.log(Level.FINE, "Sending retry setCAN message {0}", currentMessage);
                    sendMessageToUart(currentMessage.getBytes(Charset.defaultCharset()));
                }
                this.hasMessageBeenSent.set(true);
                this.messageInProgress = false;
                return;
            }

            StringBuilder sb = new StringBuilder("setCAN ");
            HandlerCan handlerCan = HandlerCan.getInstance();
            int messageCount = 1;

            while (messageCount <= 25) {
                synchronized (handlerCan.dataCanMessageStore) {
                    if (handlerCan.dataCanMessageStore.isEmpty()) {
                        break;
                    }
                    if (messageCount != 1) {
                        sb.append(" ");
                    }
                    sb.append(handlerCan.dataCanMessageStore.getNextMessage());
                }
                messageCount++;
            }

            String formattedMessage = String.format(ByteArray.PARSE_BLOCK_TAG, sb, new CalculateCRC8().getCRC8HexString(sb.toString()));
            if (formattedMessage.length() > 17) {
                synchronized (handlerCan.dataCanMessageStore) {
                    handlerCan.dataCanMessageStore.setCurrentMessage(formattedMessage);
                }
            }
            sendMessageToUart(formattedMessage.getBytes(Charset.defaultCharset()));
            this.hasMessageBeenSent.set(true);
            this.messageInProgress = false;
            return;
        }

        this.hasMessageBeenSent.set(true);

        if (!this.isMessageInProgress.get()) {
            this.messageInProgress = true;
        }

        synchronized (this.outboundMessageQueue) {
            if (!this.outboundMessageQueue.isEmpty()) {
                LOGGER.log(Level.FINE, "Message just sent - {0}", this.currentMessage.get());
                if (this.currentMessage.get().equals(this.outboundMessageQueue.get(0))) {
                    this.retryCount++;
                } else {
                    this.retryCount = 0;
                }

                if (this.retryCount > 2) {
                    LOGGER.log(Level.FINE, "Warning sent the same message three times");
                    this.outboundMessageQueue.remove(0);
                    return;
                }

                this.messagesSent++;
                if (this.messagesSent < 15) {
                    this.currentMessage.set(this.outboundMessageQueue.get(0));
                    sendMessageToUart(this.currentMessage.get().getBytes(Charset.defaultCharset()));
                    LOGGER.log(Level.FINE, "Sent a message: {0}", this.currentMessage.get());
                    return;
                }

                synchronized (HandlerUartStream.class) {
                    this.requestMessageIndex.set(0);
                }
                this.isMessageInProgress.set(false);
            }

            this.messagesSent = 0;
            this.currentMessage.set("");

            synchronized (HandlerUartStream.class) {
                if (this.requestMessageIndex.get() == 0) {
                    LOGGER.log(Level.FINE, "Starting poll loop again");
                }
                LOGGER.log(Level.FINE, "Sent poll: {0}", this.requestMessageList.get(this.requestMessageIndex.get()));
                sendMessageToUart(this.requestMessageList.get(this.requestMessageIndex.get()).getBytes(Charset.defaultCharset()));
            }
        }
    }

    private void readFromUart() {
        this.isOk = true;
        byte[] bArr = this.buffer;
        Arrays.fill(bArr, 0, bArr.length, (byte) 0);
        int i = 0;
        while (this.isOk && this.isRunning) {
            try {
                if (this.bufferPos + 256 > this.buffer.length) {
                    Arrays.fill(this.buffer, 0, this.buffer.length, (byte) 0);
                    this.bufferPos = 0;
                }
                this.bufferPos += this.uartInputStream.read(this.buffer, this.bufferPos, 256);
            } catch (IOException e2) {
                int i2 = i + 1;
                if (i > 3) {
                    LOGGER.log(Level.FINE, "UART Failed to read");
                    this.isOk = false;
                    this.isRunning = false;
                    return;
                }
                Throwable cause = e2.getCause();
                if (cause != null && cause.getMessage().contains("EBADF")) {
                    LOGGER.log(Level.FINE, "UART Failed with EBADF");
                    this.isOk = false;
                    this.isRunning = false;
                    return;
                }
                i = i2;
            } catch (ArrayIndexOutOfBoundsException unused) {
                LOGGER.log(Level.FINE, "ArrayIndexOutOfBoundsException");
                this.bufferPos = this.buffer.length;
            }
            if (this.isRunning) {
                ByteArray byteArray = new ByteArray();
                int findParseBlockTagStartu = byteArray.findParseBlockTagStart(this.buffer);
                this.bufferReadPos = findParseBlockTagStartu;
                if (findParseBlockTagStartu >= 0) {
                    int findParseBlockTagPing = byteArray.findParseBlockTagPing(this.buffer,findParseBlockTagStartu, this.buffer.length);
                    this.bytesToWrite = findParseBlockTagPing;
                    if (findParseBlockTagPing > 13) {
                        processMessage();
                        removePingMessages();
                    } else {
                        int checksum=0;
                        int findParseBlockEnd = byteArray.findParseBlockTagEnd(this.buffer,this.bufferReadPos, this.buffer.length);
                        this.bytesToWrite = findParseBlockEnd;
                        if (findParseBlockEnd > 0) {
                            this.bufferSize = byteArray.parseCrcValue(findParseBlockEnd, this.buffer);
                            this.bytesRead = new CalculateCRC8().calculateCRC8FromBytes(this.bufferReadPos + 3, this.bytesToWrite - 7, this.buffer);
                            if (byteArray.findGetCan(this.buffer) != -1) {
                                this.sendReply = true;
                            }
                            if (this.bufferSize == this.bytesRead) {
                                byte[] copySubArray = byteArray.copySubArray(this.buffer, this.bufferReadPos + 3, this.bytesToWrite - 7);

                                // if (serviceUart != null) {
                                //     this.runnableParseMessage.setMessageBytes(copySubArray);
                                //     //serviceUart.executor.execute(this.runnableParseMessage);
                                // }
                                this.successAck = 1;
                            } else {
                                LOGGER.log(Level.FINE, "crc fail: {0}", new String(this.buffer, 0, this.bufferPos));
                                this.successAck = 0;
                            }
                            byteArray.shiftArrayLeft(this.bytesToWrite, this.buffer);
                            this.bufferPos -= this.bytesToWrite;
                        }
                    }
                }
            }
        }
    }

    private boolean sendConfigPacket() {
        LOGGER.log(Level.FINE, "Sending config packet");
        return sendMessageToUart(new byte[]{0, (byte)0xE1, 0, 0, 8, 1, 0, 9});
    }

    void close() {
        //ServiceUart serviceUart;
        LOGGER.log(Level.FINE, "Close down called - closing input and output stream, then parcelFileDescriptor");
        //WeakReference<ServiceUart> weakReference = ServiceUart.instance;
        // if (weakReference != null && (serviceUart = weakReference.get()) != null) {
        //     serviceUart.showNotification(false);
        // }
        InputStream fileInputStream = this.uartInputStream;
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            this.uartInputStream = null;
        }
        OutputStream fileOutputStream = this.uartOutputStream;
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            this.uartOutputStream = null;
        }

        //ServiceUart.isUartThreadRunning.set(false);
        this.requestMessageIndex.set(0);
        this.isMessageInProgress.set(false);
    }

    //@Override
    public void run() {
        Thread.currentThread().setName("ThreadUartAccess");
        this.isRunning = true;
        this.runMode = 1;
        while (this.isRunning) {
            int i = this.runMode;
            if (i == 1) {
                for (int i2 = 0; i2 < 1; i2++) {
                    if (sendConfigPacket()) {
                        this.runMode = 2;
                    } else {
                        this.isRunning = false;
                    }
                }
            } else if (i == 2) {
                LOGGER.log(Level.FINE, "get data");
                readFromUart();
            }
        }
        close();
    }

    private boolean sendMessageToUart(byte[] bArr) {
        if (this.uartOutputStream == null || bArr == null) {
            return false;
        }
        int length = bArr.length;
        int i = 0;
        while (length > 0) {
            int i2 = length <= 63 ? length : 63;
            try {
                this.uartOutputStream.write(bArr, i, i2);
                //SystemClock.sleep(1L);
                length -= i2;
                i += i2;
            } catch (IOException unused) {
                LOGGER.log(Level.FINE, "Error sending packets, close down");
                // ServiceUart serviceUart = ServiceUart.instance.get();
                // if (serviceUart != null) {
                //     serviceUart.stopUartThread();
                // }
                return false;
            }
        }
        return true;
    }

    void sendMessage(String str) {
        if (str != null) {
            synchronized (this.outboundMessageQueue) {
                Iterator<String> it = this.outboundMessageQueue.iterator();
                while (it.hasNext()) {
                    if (it.next().equals(str)) {
                        it.remove();
                    }
                }
                this.outboundMessageQueue.add(str);
            }
        }
    }
}
