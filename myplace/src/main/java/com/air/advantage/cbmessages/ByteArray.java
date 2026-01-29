package com.air.advantage.cbmessages;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ByteArray {

    private static final Logger LOGGER = Logger.getLogger(ByteArray.class.getName());

    private static final byte[] PARSE_NAK = "<ack>0</ack>".getBytes(Charset.defaultCharset());
    private static final byte[] PARSE_ACK = "<ack>1</ack>".getBytes(Charset.defaultCharset());
    private static final byte[] PARSE_BLOCK_TAG_END_ONE = "</U=".getBytes(Charset.defaultCharset());
    private static final byte[] PARSE_BLOCK_TAG_END_TWO = ">".getBytes(Charset.defaultCharset());
    private static final byte[] GET_CAN = "getCAN ".getBytes(Charset.defaultCharset());
    private static final byte[] PARSE_BLOCK_TAG_PING = "<U>Ping</U=db>".getBytes(Charset.defaultCharset());
    private static final byte[] PARSE_BLOCK_TAG_STARTU = "<U>".getBytes(Charset.defaultCharset());
    private static final byte[] UNKNOWN_REQUEST = "<request>Unknown</request>".getBytes(Charset.defaultCharset());
    public static String PARSE_BLOCK_TAG = "<U>%s</U=%s>";

    public static final String[] SYSTEM_DATA_REQUESTS = {"getSystemData", "getClock", "getZoneData?zone=1", "getZoneData?zone=2", "getZoneData?zone=3", "getZoneData?zone=4", "getZoneData?zone=5", "getZoneData?zone=6", "getZoneData?zone=7", "getZoneData?zone=8", "getZoneData?zone=9", "getZoneData?zone=10"};
    public static final String[] SCHEDULE_DATA_REQUESTS = {"getZoneTimer", "getScheduleData?schedule=1", "getScheduleData?schedule=2", "getScheduleData?schedule=3", "getScheduleData?schedule=4", "getScheduleData?schedule=5"};

    public ByteArray() {
    }

    private static int parseHexDigit(byte b) {
        if (b >= '0' && b <= '9') {
            return b - '0';
        }
        if (b >= 'a' && b <= 'f') {
            return b - 'a' + 10;
        }
        if (b >= 'A' && b <= 'F') {
            return b - 'A' + 10;
        }
        return -1;
    }

    /**
     * Searches for the first occurrence of a specified byte array within another byte array,
     * starting from a given index.
     *
     * @param startIndex The index in the source byte array to start the search from.
     * @param buffer The source byte array to search within. Must not be null.
     * @param findText The target byte array to search for. Must not be null.
     * @return The index of the first occurrence of the target byte array within the source byte array,
     *         or -1 if the target byte array is not found or if the input parameters are invalid.
     * @throws IllegalArgumentException if the source or target byte array are null, or if the source
     *         byte array is smaller than the target byte array.
     */
    public static int findString(byte[] buffer, int startIndex, int endIndex, byte[] findText) {
        if (buffer == null || findText == null || buffer.length < findText.length) {
            LOGGER.log(Level.WARNING, "Invalid source or target array for findParseBlockEnd.");
            return -1;
        }

        for (int i = startIndex; i <= buffer.length - findText.length; i++) {
            boolean match = true;
            for (int j = 0; j < findText.length; j++) {
                if (buffer[i + j] != findText[j]) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return i;
            }
        }
        return -1;
    }

    private static byte[] openingTag(byte[] content) {
        byte[] wrapped = new byte[content.length + 2];
        wrapped[0] = '<';
        wrapped[wrapped.length - 1] = '>';
        System.arraycopy(content, 0, wrapped, 1, content.length);
        return wrapped;
    }

    private static byte[] closingTag(byte[] content) {
        byte[] wrapped = new byte[content.length + 3];
        wrapped[0] = '<';
        wrapped[1] = '/';
        wrapped[wrapped.length - 1] = '>';
        System.arraycopy(content, 0, wrapped, 2, content.length);
        return wrapped;
    }

    public static int findGetCan(byte[] buffer, int startIndex, int endIndex) {
        return findString(buffer, startIndex, endIndex, GET_CAN);
    }

    public static int findGetCan(byte[] buffer) {
        return findGetCan(buffer, 0, buffer.length);
    }

    public static int findNak(byte[] buffer, int startIndex, int endIndex) {
        return findString(buffer, startIndex, endIndex, PARSE_NAK);
    }

    public static int findNak(byte[] buffer) {
        return findNak(buffer, 0, buffer.length);
    }

    public static int findAck(byte[] buffer, int startIndex, int endIndex) {
        return findString(buffer, startIndex, endIndex, PARSE_ACK);
    }

    public static int findAck(byte[] buffer) {
        return findAck(buffer, 0, buffer.length);
    }

    public static int findParseBlockTagStart(byte[] buffer, int startIndex, int endIndex) {
        return findString(buffer, startIndex, endIndex, PARSE_BLOCK_TAG_STARTU);
    }

    public static int findParseBlockTagStart(byte[] buffer) {
        return findParseBlockTagStart(buffer, 0, buffer.length);
    }

    public static int findUnknownRequest(byte[] buffer, int startIndex, int endIndex) {
        return findString(buffer, startIndex, endIndex, UNKNOWN_REQUEST);
    }

    public static int findUnknownRequest(byte[] buffer) {
        return findUnknownRequest(buffer, 0, buffer.length);
    }

    public static int findParseBlockTagPing(byte[] buffer, int startIndex, int endIndex) {
        return findString(buffer, startIndex, endIndex, PARSE_BLOCK_TAG_PING) + PARSE_BLOCK_TAG_PING.length;
    }

    public static int findParseBlockTagPing(byte[] buffer) {
        return findParseBlockTagPing(buffer, 0, buffer.length);
    }

    public static int parseCrcValue(int index, byte[] buffer) {
        if (buffer == null || index > buffer.length) {
            LOGGER.log(Level.WARNING, "Invalid index or source array for parsing hex value.");
            return -1;
        }

        int high = parseHexDigit(buffer[index-3]);
        int low = parseHexDigit(buffer[index-2]);
        if (high < 0 || low < 0) {
            LOGGER.log(Level.WARNING, "Invalid hex digits at index {0}", index);
            return -1;
        }
        return (high << 4) + low;
    }

    public static int parseHexValue(int index, byte[] buffer) {
        int high = ByteArray.parseHexDigit(buffer[index]);
        int low = ByteArray.parseHexDigit(buffer[index + 1]);
        if (high < 0 || low < 0) {
            return -1;
        }
        return (high << 4) + low;
    }

    public static void shiftArrayLeft(int startIndex, byte[] source) {
        if (source == null || startIndex >= source.length) {
            LOGGER.log(Level.WARNING, "Invalid source array or start index for shifting.");
            return;
        }

        int writeIndex = 0;
        for (int readIndex = startIndex; readIndex < source.length; readIndex++) {
            source[writeIndex++] = source[readIndex];
        }
        while (writeIndex < source.length) {
            source[writeIndex++] = 0;
        }
    }

    public static byte[] getSubBytes(byte[] source, int start, int end) {
        if (source == null || start < 0 || end > source.length || start >= end) {
            LOGGER.log(Level.WARNING, "Invalid range for getting subBytes: start={0}, end={1}", new Object[]{start, end});
            return new byte[0];
        }
        return Arrays.copyOfRange(source, start, end);
    }

    public static byte[] getTagContent(byte[] buffer, byte[] tag) {
        byte[] openingTag = openingTag(tag);
        int start = findString(buffer, 0, buffer.length, openingTag);
        if (start < 0) {
            throw new IllegalArgumentException("Opening tag not found.");
        }

        byte[] closingTag = closingTag(tag);
        int end = findString(buffer, start + openingTag.length, buffer.length, closingTag);
        if (end < 0) {
            throw new IllegalArgumentException("XML tag not found.");
        }

        return Arrays.copyOfRange(buffer, start + openingTag.length, end);
    }

    public static boolean areArraysEqual(byte[] array1, byte[] array2) {
        return Arrays.equals(array1, array2);
    }

    /* renamed from: a */
    public static int findParseBlockTagEnd(byte[] buffer, int startIndex, int endIndex) {
        int checkSumPosition;
        int endMessagePosition = -1;
        if (buffer.length <= startIndex + 7 || 
            (checkSumPosition = findString(buffer, startIndex, endIndex, PARSE_BLOCK_TAG_END_ONE)) <= 0 || 
            (endMessagePosition = findString(buffer, checkSumPosition, endIndex, PARSE_BLOCK_TAG_END_TWO)) <= 0) {
            return -1;
        }
        return endMessagePosition + 1;
    }

    /* renamed from: b */
    public static byte[] getTagContent(byte[] buffer, byte[] openingTag, byte[] closingTag) {
        byte[] wrapWithAngleBrackets = openingTag(openingTag);
        int findParseBlockEnd = findString(buffer, 0, buffer.length, wrapWithAngleBrackets);
        if (findParseBlockEnd > 0) {
        byte[] wrapWithClosingTag = closingTag(closingTag);
        int findParseBlockEnd2 = findString(buffer, findParseBlockEnd,buffer.length, wrapWithClosingTag) + wrapWithClosingTag.length;
        if (wrapWithAngleBrackets.length + findParseBlockEnd < findParseBlockEnd2) {
                byte[] bArr4 = new byte[(buffer.length - findParseBlockEnd2) + findParseBlockEnd];
                System.arraycopy(buffer, 0, bArr4, 0, findParseBlockEnd);
                System.arraycopy(buffer, findParseBlockEnd2, bArr4, findParseBlockEnd, buffer.length - findParseBlockEnd2);
                return bArr4;
            }
        }
        return buffer;
    }

    /* renamed from: a */
    public static byte[] copySubArray(byte[] buffer, int startIndex, int endIndex) {
        int length = endIndex - startIndex;
        byte[] newMessage = new byte[length];
        if (buffer.length <= length) {
            return null;
        }
        System.arraycopy(buffer, startIndex, newMessage, 0, length);
        return newMessage;
    }

    /* renamed from: a */
    public static byte[] replaceTagContent(byte[] buffer, byte[] fromTag, byte[] toTag) {
        int closingTagPos;
        byte[] openTagString = openingTag(fromTag);
        int openTagPos = findString(buffer, 0, buffer.length, openTagString);
        if (openTagPos > 0 && 
                openTagString.length + openTagPos <= (closingTagPos = findString(buffer, openTagPos, buffer.length, closingTag(fromTag)))) {
            int length = openTagPos + openTagString.length;
            byte[] newMessage = new byte[((buffer.length + length) - closingTagPos) + toTag.length];
        System.arraycopy(buffer, 0, newMessage, 0, length);
            System.arraycopy(toTag, 0, newMessage, length, toTag.length);
            System.arraycopy(buffer, closingTagPos, newMessage, length + toTag.length, buffer.length - closingTagPos);
            return newMessage;
        }
        throw new IllegalArgumentException("XML tag not found");
    }

/* renamed from: a */
    public static String extractTagContentAsString(byte[] buffer, byte[] tag) {
        byte[] extractTagContent = getTagContent(buffer, tag);
        if (extractTagContent != null) {
            return new String(extractTagContent);
        }
        throw new IllegalArgumentException("XML String is null");
    }

    public static byte[] toHexDigits(int value) {
        // Ensure value is 8 bits
        value = value & 0xFF;
        byte[] result = new byte[2];
        int high = (value >> 4) & 0x0F;
        int low = value & 0x0F;
        result[0] = (byte) (high < 10 ? ('0' + high) : ('a' + high - 10));
        result[1] = (byte) (low < 10 ? ('0' + low) : ('a' + low - 10));
        return result;
    }

    public static String toHexString(byte[] data) {
        if (data == null || data.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            int value = b & 0xFF; // Convert byte to unsigned int
            sb.append(String.format("%02x", value)); // Format as two-digit hex
        }
        return sb.toString();
    }

    /**
     * Writes the two lowercase hex digits representing the 8-bit value into the buffer at the given index.
     * @param value 8-bit integer value to convert
     * @param buffer byte array to write into
     * @param index starting index in buffer to write the two hex digits
     */
    public static void toHexDigits(int value, byte[] buffer, int index) {
        value = value & 0xFF;
        int high = (value >> 4) & 0x0F;
        int low = value & 0x0F;
        buffer[index] = (byte) (high < 10 ? ('0' + high) : ('a' + high - 10));
        buffer[index + 1] = (byte) (low < 10 ? ('0' + low) : ('a' + low - 10));
    }
}