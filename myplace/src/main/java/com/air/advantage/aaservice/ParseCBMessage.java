package com.air.advantage.aaservice;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.air.advantage.cbmessages.ByteArray;

/* compiled from: ParseCBMessage.java */
/* renamed from: com.air.advantage.aaservice.m */
/* loaded from: classes.dex */
class ParseCBMessage implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ParseCBMessage.class.getName());

    private static final byte[] DHCP_BYTES = "dhcp".getBytes();
    private static final byte[] GATEWAY_BYTES = "gateway".getBytes();
    private static final byte[] EZONE_BYTES = "ezone".getBytes();
    private static final byte[] MYAIR4_BYTES = "MyAir4".getBytes();
    private static final byte[] MYAIR5_BYTES = "MyAir5".getBytes();
    private static final byte[] ZONE10E_BYTES = "zone10e".getBytes();
    private static final byte[] VAMS_BYTES = "vams".getBytes();

    private final HandlerCan handlerCan = HandlerCan.getInstance();
    private final AtomicReference<String> messageReference = new AtomicReference<>("");
    private byte[] messageBytes;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    ParseCBMessage() {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    void setMessageBytes(byte[] message) {
        byte[] bArr = new byte[message.length];
        this.messageBytes = bArr;
        System.arraycopy(message, 0, bArr, 0, message.length);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public void run() {
        String str;
        String str2;
        // ServiceUart serviceUart = ServiceUart.instance.get();
        // if (serviceUart == null) {
        //     LOGGER.log(Level.FINE, "Context is null");
        //     return;
        // }
        HandlerUartStream threadUartAccess = new HandlerUartStream(null, null);
        // if (threadUartAccess == null) {
        //     LOGGER.log(Level.FINE, "Context is null");
        //     return;
        // }
        ByteArray byteArray = new ByteArray();
        byte[] bArr = this.messageBytes;
        if (bArr != null) {
            boolean z = true;
            if (byteArray.findNak(bArr) > 0) {
                if (threadUartAccess.hasMessageBeenSent.get()) {
                    if (byteArray.findUnknownRequest(this.messageBytes) != -1) {
                        LOGGER.log(Level.WARNING, "CB doesn't support can messages");
                        threadUartAccess.isMessageInProgress.set(true);
                        return;
                    }
                    LOGGER.log(Level.FINE, "Warning got a failed ack back sending CAN message");
                } else if (threadUartAccess.currentMessage.get().isEmpty()) {
                    LOGGER.log(Level.FINE, "Warning got a failed ack back ");
                } else {
                    LOGGER.log(Level.FINE, "Warning got a failed ack back ");
                }
            } else if (byteArray.findAck(this.messageBytes) > 0) {
                LOGGER.log(Level.FINE, "Got a successful ack back");
            }
            if (byteArray.findGetCan(this.messageBytes) != -1) {
                byte[] bArr2 = this.messageBytes;
                if (bArr2.length <= 9 || !this.handlerCan.processMessage(bArr2)) {
                    return;
                }
                String str3 = new String(this.messageBytes);
                AtomicReference<String> atomicReference = this.messageReference;
                if (atomicReference.compareAndSet(atomicReference.get(), str3)) {
                    LOGGER.log(Level.FINE, "Can message " + str3);
                    // String str4 = CommonFunctions.isPackageNameContainsFgassist(serviceUart.getBaseContext()) ? "com.air.advantage.MESSAGE_FROM_CB_SECURE_FUJITSU" : "com.air.advantage.MESSAGE_FROM_CB_SECURE";
                    // Intent intent = new Intent(str4);
                    // intent.putExtra("com.air.advantage.GET_DATA_REQUEST", "rawCan");
                    // intent.putExtra(str4, str3);
                    // if (CommonFunctions.isPackageNameContainsFgassist(serviceUart.getBaseContext())) {
                    //     serviceUart.sendBroadcast(intent, "com.air.android.secure_comms_fujitsu");
                    // } else {
                    //     serviceUart.sendBroadcast(intent, "com.air.android.secure_comms");
                    // }
                    // byte[] encrypt = new AES256().encrypt(str3.getBytes());
                    // if (encrypt == null || encrypt.length <= 0) {
                    //     CommonFuncs.logException(new RuntimeException("ParseCBMessage - Error encrypting rawCan message - encodedMessage is null"));
                    //     return;
                    // }
                    // Intent intent2 = new Intent("com.air.advantage.MESSAGE_TO_CB_NO_PERMISSION_BROADCAST");
                    // intent2.setComponent(new ComponentName("com.air.advantage.zone10", "com.air.advantage.ReceiverDataUartForNoPermissionBroadcast"));
                    // intent2.putExtra("com.air.advantage.GET_DATA_REQUEST", "rawCan");
                    // intent2.putExtra("com.air.advantage.MESSAGE_TO_CB_NO_PERMISSION_BROADCAST", encrypt);
                    // serviceUart.sendBroadcast(intent2);
                    return;
                }
                return;
            }
            if (byteArray.findUnknownRequest(this.messageBytes) != -1) {
                LOGGER.log(Level.WARNING, "Just got reply unknown");
                return;
            }
            if (threadUartAccess.currentMessage.get().length() > 0) {
                String substring = threadUartAccess.currentMessage.get().substring(3, threadUartAccess.currentMessage.get().length() - 7);
                if (substring.contains("?")) {
                    substring = substring.substring(0, substring.indexOf("?"));
                }
                try {
                    str2 = byteArray.extractTagContentAsString(this.messageBytes, "request".getBytes());
                } catch (Exception unused) {
                    str2 = "";
                }
                if (substring.equals(str2)) {
                    synchronized (threadUartAccess.outboundMessageQueue) {
                        if (threadUartAccess.outboundMessageQueue.isEmpty()) {
                            LOGGER.log(Level.SEVERE, "Trying to remove a message from the queue that doesn't exist. " + substring + " " + new String(this.messageBytes));
                        } else {
                            threadUartAccess.outboundMessageQueue.remove(0);
                        }
                    }
                    return;
                }
                String str5 = new String(this.messageBytes);
                if (str5.equals("CAN2 in use")) {
                    threadUartAccess.isCan2InUse.set(true);
                    return;
                }
                LOGGER.log(Level.WARNING, "request and returned value don't match - " + substring + " " + str5);
                return;
            }
        //     synchronized (ServiceUart.ThreadUartAccess.class) {
        //         if (threadUartAccess.messageQueue.size() != 0 && threadUartAccess.messageLength.get() < threadUartAccess.messageQueue.size()) {
        //             String str6 = threadUartAccess.messageQueue.get(threadUartAccess.messageLength.get());
        //             String substring2 = str6.contains("?") ? str6.substring(3, str6.indexOf("?")) : str6.substring(3, str6.length() - 7);
        //             try {
        //                 str = byteArray.extractTagContentAsString(this.messageBytes, "request".getBytes());
        //             } catch (Exception unused2) {
        //                 str = "";
        //             }
        //             if (!substring2.equals(str)) {
        //                 String str7 = new String(this.messageBytes);
        //                 if (str7.equals("CAN2 in use")) {
        //                     threadUartAccess.isMessagePending.set(true);
        //                 } else {
        //                     LOGGER.log(Level.WARNING, "poll request and returned value don't match - " + substring2 + " " + str7);
        //                 }
        //                 return;
        //             }
        //             String substring3 = threadUartAccess.messageQueue.get(threadUartAccess.messageLength.get()).substring(3, str6.length() - 7);
        //             threadUartAccess.messageLength.incrementAndGet();
        //             if (threadUartAccess.messageLength.get() >= threadUartAccess.messageQueue.size()) {
        //                 threadUartAccess.messageLength.set(0);
        //                 threadUartAccess.isMessageComplete.set(false);
        //             }
        //             if (substring3.equals("getSystemData")) {
        //                 byte[] bytes = "00".getBytes();
        //                 byte[] bArr3 = null;
        //                 if (TabletInfo.isMyAir5OrTspTablet()) {
        //                     bytes = "17".getBytes();
        //                     bArr3 = MYAIR5_BYTES;
        //                 } else if (TabletInfo.isMyAir4Tablet()) {
        //                     bytes = "16".getBytes();
        //                     bArr3 = MYAIR4_BYTES;
        //                 } else if (TabletInfo.isEZoneTablet()) {
        //                     bytes = "15".getBytes();
        //                     bArr3 = EZONE_BYTES;
        //                 } else if (TabletInfo.isZone10ETablet()) {
        //                     bytes = "18".getBytes();
        //                     bArr3 = ZONE10E_BYTES;
        //                 } else if (TabletInfo.isVamsTablet()) {
        //                     bytes = "21".getBytes();
        //                     bArr3 = VAMS_BYTES;
        //                 }
        //                 try {
        //                     this.messageBytes = byteArray.replaceTagContent(this.messageBytes, "type".getBytes(), bytes);
        //                 } catch (Exception e2) {
        //                     LOGGER.log(Level.FINE, new String(this.messageBytes));
        //                     e2.printStackTrace();
        //                 }
        //                 if (bArr3 != null) {
        //                     try {
        //                         this.messageBytes = byteArray.replaceTagContent(this.messageBytes, "AppStore".getBytes(), bArr3);
        //                     } catch (Exception e3) {
        //                         LOGGER.log(Level.FINE, new String(this.messageBytes));
        //                         e3.printStackTrace();
        //                     }
        //                 }
        //                 byte[] removeTag = byteArray.removeTag(this.messageBytes, DHCP_BYTES, GATEWAY_BYTES);
        //                 this.messageBytes = removeTag;
        //                 try {
        //                     this.messageBytes = byteArray.replaceTagContent(removeTag, "MyAppRev".getBytes(), "14.116".getBytes());
        //                 } catch (Exception e4) {
        //                     LOGGER.log(Level.FINE, new String(this.messageBytes));
        //                     e4.printStackTrace();
        //                     return;
        //                 }
        //             }
        //             synchronized (serviceUart.usbDataMap) {
        //                 if (byteArray.areArraysEqual(serviceUart.usbDataMap.get(substring3), this.messageBytes)) {
        //                     z = false;
        //                 } else {
        //                     serviceUart.usbDataMap.put(substring3, this.messageBytes);
        //                 }
        //                 if (z) {
        //                     serviceUart.sendRequest(substring3);
        //                 }
        //             }
        //             return;
        //         }
        //         LOGGER.log(Level.FINE, "poll number issue");
        //     }
        }
    }
}