package com.air.advantage.aaservice;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.air.advantage.cbmessages.ByteArray;
import com.air.advantage.libraryairconlightjson.DataCanMessageStore;

/* compiled from: HandlerCan.java */
/* renamed from: com.air.advantage.aaservice.i */
/* loaded from: classes.dex */
class HandlerCan extends HandlerBase {

    private static final Logger LOGGER = Logger.getLogger(HandlerCan.class.getName());

    static boolean isRetry = false;
    private static int retryCount;
    private static HandlerCan instance;

    public final DataCanMessageStore dataCanMessageStore = new DataCanMessageStore();
    private final ByteArray byteArray = new ByteArray();

    private HandlerCan() {
    }

    /**
     * Returns the singleton instance of HandlerCan.
     *
     * @return The singleton instance.
     */
    public static HandlerCan getInstance() {
        if (instance == null) {
            synchronized (HandlerCan.class) {
                if (instance == null) {
                    instance = new HandlerCan();
                }
            }
        }
        return instance;
    }

    /**
     * Stores a CAN message in the message store.
     *
     * @param message The message to store.
     */
    void storeMessage(String message) {
        this.dataCanMessageStore.addMessage(message);
    }

    /**
     * Processes a CAN message.
     *
     * @param message The byte array representing the CAN message.
     * @return True if the message is processed successfully, false otherwise.
     */
    boolean processMessage(byte[] message) {
        if (this.byteArray.findGetCan(message) != 0) {
            LOGGER.log(Level.FINE, "Error - start of message is corrupt {0}", new String(message));
            return false;
        }

        if (message[7] == 48) {
            LOGGER.log(Level.SEVERE, "Warning - last can messages were not sent - retry!");
            if (retryCount < 3) {
                retryCount++;
                isRetry = true;
                return false;
            }
        }

        retryCount = 0;
        isRetry = false;
        return true;
    }
}