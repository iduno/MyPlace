package com.air.advantage.libraryairconlightjson;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: DataCanMessageStore.java */
/* renamed from: b.a.a.a.c */
/* loaded from: classes.dex */
public class DataCanMessageStore {

    /* renamed from: c */
    private static final Logger LOGGER = Logger.getLogger(DataCanMessageStore.class.getName());

    /* renamed from: a */
    private final List<String> messageList = new LinkedList<>();

    /* renamed from: b */
    private String currentMessage = "";

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: a */
    public String getCurrentMessage() {
        String message;
        synchronized (this.messageList) {
            message = this.currentMessage;
        }
        return message;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: b */
    public void setCurrentMessage(String message) {
        synchronized (this.messageList) {
            this.currentMessage = message;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: c */
    public String getNextMessage() {
        synchronized (this.messageList) {
            if (this.messageList.isEmpty()) {
                return null;
            }
            String str = this.messageList.get(0);
            this.messageList.remove(0);
            return str;
        }
    }

    /* renamed from: a */
    public void addMessage(String message) {
        synchronized (this.messageList) {
            int i = 0;
            while (true) {
                if (i >= this.messageList.size()) {
                    break;
                }
                if (this.messageList.get(i).substring(0, 13).equals(message.substring(0, 13))) {
                    LOGGER.log(Level.FINE, "Duplicate can message {0} with {1}, removing first message.", new Object[]{this.messageList.get(i), message});
                    this.messageList.remove(i);
                    break;
                }
                i++;
            }
            this.messageList.add(message);
        }
    }

    /* renamed from: b */
    public boolean isEmpty() {
        boolean isEmpty;
        synchronized (this.messageList) {
            isEmpty = this.messageList.isEmpty();
        }
        return isEmpty;
    }
}