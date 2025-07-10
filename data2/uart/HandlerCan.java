package com.air.advantage.uart;

import android.content.Context;
import android.content.Intent;
import com.air.advantage.ActivityMain;
import com.air.advantage.AppFeatures;
import com.air.advantage.UartStrings;
import com.air.advantage.di.LocalBroadcaster;
import com.air.advantage.libraryairconlightjson.AAServiceConstants;
import com.air.advantage.libraryairconlightjson.DumpStringToFile;
import com.air.advantage.libraryairconlightjson.TabletInfo;
import java.util.List;
import java.util.ListIterator;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

@PurelyImplements({"SMAP\nHandlerCan.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HandlerCan.kt\ncom/air/advantage/uart/HandlerCan\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,232:1\n731#2,9:233\n37#3,2:242\n*S KotlinDebug\n*F\n+ 1 HandlerCan.kt\ncom/air/advantage/uart/HandlerCan\n*L\n112#1:233,9\n112#1:242,2\n*E\n"})
/* renamed from: com.air.advantage.uart.j */
/* loaded from: classes.dex */
public final class HandlerCan extends FRLParser {

    /* renamed from: V */
    @NotNull
    public static final Companion Companion = new Companion(null);

    /* renamed from: W */
    @NotNull
    private static final char[] HEX_DIGITS;

    /* renamed from: X */
    private static final String LOG_NAME;

    /* renamed from: Y */
    @Nullable
    private static HandlerCan INSTANCE;

    /* renamed from: U */
    @NotNull
    private final AES256 aes256;

    /* renamed from: com.air.advantage.uart.j$a */
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.j.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: b */
        private final char[] convertToHex_(char[] input, boolean addColons) {
            int charsPerByte = addColons ? 3 : 2;
            char[] cArr = new char[input.length * charsPerByte];
            int length = input.length;
            for (int i10 = 0; i10 < length; i10++) {
                int i11 = input[i10] & 255;
                int i12 = i10 * charsPerByte;
                cArr[i12] = HandlerCan.HEX_DIGITS[i11 >>> 4];
                cArr[i12 + 1] = HandlerCan.HEX_DIGITS[i11 & 15];
                if (addColons) {
                    cArr[i12 + 2] = (char) 58;
                }
            }
            return cArr;
        }

        @JvmStatic
        public static /* synthetic */ void e() {
        }

        @NotNull
        /* renamed from: a */
        public final String convertToHex(@NotNull char[] input, boolean addColons) {
            Intrinsics.checkNotNullParameter(input, "chars");
            return new String(convertToHex_(input, addColons));
        }

        @JvmStatic
        /* renamed from: c */
        public final void destroy() {
            HandlerCan.INSTANCE = null;
        }

        @NotNull
        /* renamed from: d */
        public final HandlerCan getInstance() {
            if (HandlerCan.INSTANCE == null) {
                synchronized (HandlerCan.class) {
                    if (HandlerCan.INSTANCE == null) {
                        Companion companion = HandlerCan.Companion;
                        HandlerCan.INSTANCE = new HandlerCan(null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            HandlerCan handlerCan = HandlerCan.INSTANCE;
            Intrinsics.checkNotNull(handlerCan);
            return handlerCan;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private Companion() {
        }
    }

    static {
        char[] charArray = "0123456789ABCDEF".toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
        HEX_DIGITS = charArray;
        LOG_NAME = HandlerCan.class.getSimpleName();
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.j.<init>():void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ HandlerCan(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @JvmStatic
    public static final void i() {
        Companion.destroy();
    }

    @NotNull
    /* renamed from: j */
    public static final HandlerCan getInstance() {
        return Companion.getInstance();
    }

    /* renamed from: k */
    public final void parseCanMessage(@NotNull Context context, @NotNull String message) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(message, "message");
        if (compareFRLValue(message, 4, "00") && compareFRLValue(message, 6, "00") && compareFRLValue(message, 7, "00")) {
            Timber.forest.d("Rejected can message - UID is zero! - " + message, new Object[0]);
            return;
        }
        if (compareFRLValue(message, 0, "02")) {
            if (TabletInfo.isMyAir5Tablet()) {
                Timber.forest.v("Parsing light message.", new Object[0]);
                HandlerLights.Companion.getInstance().parseMessage(context, message, true);
                return;
            }
            return;
        }
        if (compareFRLValue(message, 0, "07")) {
            Timber.forest.v("Parsing aircon message.", new Object[0]);
            ((HandlerAircon) KoinJavaComponent.get$default(HandlerAircon.class, null, null, 6, null)).parseMessage(message);
        } else {
            if (!compareFRLValue(message, 0, "08")) {
                Timber.forest.d("Rejected can message - incorrect system type ", new Object[0]);
                return;
            }
            Timber.forest.v("Parsing RF device message.", new Object[0]);
            if (compareFRLValue(message, 2, "02")) {
                HandlerLights.Companion.getInstance().parseMessage(context, message, true);
            } else {
                ((HandlerAircon) KoinJavaComponent.get$default(HandlerAircon.class, null, null, 6, null)).parseMessage(message);
            }
        }
    }

    /* renamed from: l */
    public final void parseRawCanMessage(@NotNull Context context, @NotNull String localMessage) {
        List emptyList;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(localMessage, "localMessage");
        if (StringsKt__StringsJVMKt.startsWith(localMessage, "getCan", false, 2, null)) {
            Timber.forest.d("Error - start of message is corrupt " + localMessage, new Object[0]);
            return;
        }
        Timber.forest.v("Starting parsing message ", new Object[0]);
        DumpStringToFile.getInstance().dumpToFile("getCan", localMessage);
        List<String> split = new Regex(" ").split(localMessage, 0);
        if (split.isEmpty()) {
            emptyList = CollectionsKt.emptyList();
        } else {
            ListIterator<String> listIterator = split.listIterator(split.size());
            while (listIterator.hasPrevious()) {
                if (!(listIterator.previous().length() == 0)) {
                    emptyList = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                    break;
                }
            }
            emptyList = CollectionsKt.emptyList();
        }
        for (String str : (String[]) emptyList.toArray(new String[0])) {
            if (str.length() == 25) {
                parseCanMessage(context, str);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x006e A[Catch: all -> 0x007e, TryCatch #0 {, blocks: (B:10:0x003e, B:12:0x0051, B:15:0x0055, B:17:0x0058, B:18:0x007a, B:23:0x006e), top: B:9:0x003e }] */
    /* renamed from: m */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void sendCanBroadcastToCB(@NotNull Context context, @NotNull String canMessage) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(canMessage, "canMessage");
        Timber.forest.d("Broadcasting can message : " + canMessage, new Object[0]);
        String packageName = ActivityMain.Companion.getPackageName();
        if (!(packageName != null && StringsKt__StringsKt.startsWith$default(packageName, "zone10", false, 2, null))) {
            Intent intent = new Intent(AAServiceConstants.BROADCAST_CAN_TO_CB);
            intent.putExtra(AAServiceConstants.BROADCAST_CAN_TO_CB, canMessage);
            if (AppFeatures.isAnywair()) {
                context.sendBroadcast(intent, AAServiceConstants.secure_comms_fujitsu);
                return;
            } else {
                context.sendBroadcast(intent, AAServiceConstants.secure_comms);
                return;
            }
        }
        synchronized (this.aes256) {
            AES256 aes256 = this.aes256;
            byte[] bytes = canMessage.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
            byte[] encrypt = aes256.encrypt(bytes);
            if (encrypt == null) {
                AppFeatures.logError(AppFeatures.instance, new RuntimeException("HandlerCan - sendCanBroadcastToCB - Error encrypting canMessage - encodedMessage is null"), null, 2, null);
                Unit unit = Unit.INSTANCE;
            } else {
                if (!(encrypt.length == 0)) {
                    UartStrings uartStrings = UartStrings.INSTANCE;
                    Intent intent2 = new Intent(uartStrings.BROADCAST_CAN_TO_CB_NO_PERMISSION());
                    intent2.putExtra(uartStrings.BROADCAST_CAN_TO_CB_NO_PERMISSION(), encrypt);
                    context.sendBroadcast(intent2);
                }
                Unit unit2 = Unit.INSTANCE;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0078 A[Catch: all -> 0x0088, TryCatch #0 {, blocks: (B:18:0x0048, B:20:0x005b, B:23:0x005f, B:25:0x0062, B:26:0x0084, B:30:0x0078), top: B:17:0x0048 }] */
    /* renamed from: n */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void sendCanMessageToCB(@NotNull Context context, @Nullable String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (str == null || str.length() == 0) {
            return;
        }
        Timber.forest.d("Sending can message : " + str, new Object[0]);
        String packageName = ActivityMain.Companion.getPackageName();
        if (!(packageName != null && StringsKt__StringsKt.startsWith$default(packageName, "zone10", false, 2, null))) {
            ((LocalBroadcaster) KoinJavaComponent.get$default(LocalBroadcaster.class, null, null, 6, null)).sendCanMessage(str);
            return;
        }
        synchronized (this.aes256) {
            AES256 aes256 = this.aes256;
            byte[] bytes = str.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
            byte[] encrypt = aes256.encrypt(bytes);
            if (encrypt == null) {
                AppFeatures.logError(AppFeatures.instance, new RuntimeException("HandlerCan - sendCanMessageToCB - Error encrypting canMessage - encodedMessage is null"), null, 2, null);
                Unit unit = Unit.INSTANCE;
            } else {
                if (true ^ (encrypt.length == 0)) {
                    UartStrings uartStrings = UartStrings.INSTANCE;
                    Intent intent = new Intent(uartStrings.CAN_TO_CB_NO_PERMISSION());
                    intent.putExtra(uartStrings.CAN_TO_CB_NO_PERMISSION(), encrypt);
                    context.sendBroadcast(intent);
                }
                Unit unit2 = Unit.INSTANCE;
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private HandlerCan() {
        this.aes256 = new AES256();
    }
}