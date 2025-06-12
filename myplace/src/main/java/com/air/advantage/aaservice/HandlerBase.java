package com.air.advantage.aaservice;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.air.advantage.cbmessages.ByteArray;

/* compiled from: HandlerBase.java */
/* renamed from: com.air.advantage.aaservice.h */
/* loaded from: classes.dex */
class HandlerBase {

    private static final Logger LOGGER = Logger.getLogger(HandlerBase.class.getName());

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    HandlerBase() {
    }

    /**
     * Compares a hex value extracted from the byte array with the given string value.
     *
     * @param data   The byte array containing the data.
     * @param offset The offset in the byte array to start reading.
     * @param value  The hex string value to compare.
     * @return True if the extracted value matches the given value, false otherwise.
     */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: a */
    boolean compareHexValue(byte[] data, int offset, String value) {
        if (offset + 2 > data.length) {
            LOGGER.log(Level.WARNING, "Offset out of bounds for data array.");
            return false;
        }

        Integer extractedValue = extractIntValue(data, offset);
        if (extractedValue == null) {
            LOGGER.log(Level.WARNING, "Failed to extract integer value from data.");
            return false;
        }

        try {
            return extractedValue == Integer.parseInt(value, 16);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Error parsing hex value: {0}", e.getMessage());
            return false;
        }
    }

    /**
     * Extracts a hex string from the byte array at the given offset.
     *
     * @param data   The byte array containing the data.
     * @param offset The offset in the byte array to start reading.
     * @return The extracted hex string.
     */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: b */
    String extractHexString(byte[] data, int offset) {
        String hexString = new String(new ByteArray().getSubBytes(data, offset, offset + 2));
        if (hexString.length() == 1) {
            return "0" + hexString;
        }
        return hexString;
    }

    /**
     * Extracts a UID value from the byte array at the given offset.
     *
     * @param data   The byte array containing the data.
     * @param offset The offset in the byte array to start reading.
     * @return The extracted UID value as a string, or null if an error occurs.
     */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: c */
    String extractUIDValue(byte[] data, int offset) {
        byte[] subBytes = new ByteArray().getSubBytes(data, offset, offset + 5);
        if (subBytes != null) {
            return new String(subBytes);
        }
        LOGGER.log(Level.WARNING, "Error extracting UID value.");
        return null;
    }

    /**
     * Extracts an integer value from the byte array at the given offset.
     *
     * @param data   The byte array containing the data.
     * @param offset The offset in the byte array to start reading.
     * @return The extracted integer value, or null if an error occurs.
     */
    /* renamed from: a */
    Integer extractIntValue(byte[] data, int offset) {
        try {
            String hexString = extractHexString(data, offset);
            return Integer.valueOf(Integer.parseInt(hexString, 16));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Error parsing integer value: {0}", e.getMessage());
            return null;
        }
    }
}