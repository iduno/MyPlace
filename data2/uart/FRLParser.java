package com.air.advantage.uart;

import com.air.advantage.AppFeatures;
import java.util.Arrays;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.CharsKt__CharJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.uart.i */
/* loaded from: classes.dex */
public class FRLParser {

    /* renamed from: A */
    @NotNull
    public static final String COMMAND_SET_TEMPERATURE = "16";

    @NotNull
    public static final String B = "17";

    @NotNull
    public static final String C = "1d";

    @NotNull
    public static final String D = "1e";

    /* renamed from: E */
    @NotNull
    public static final String COMMAND_SET_PAIRING = "26";

    /* renamed from: F */
    @NotNull
    public static final String COMMAND_CALIBRATE_PAIRING = "27";

    /* renamed from: G */
    public static final int STOP_FOREGROUND_LEGACY = 0;

    /* renamed from: H */
    public static final int STOP_FOREGROUND_DETACH = 2;
    public static final int I = 4;
    public static final int J = 9;
    public static final int K = 11;
    public static final int L = 13;
    public static final int M = 15;
    public static final int N = 17;
    public static final int O = 19;
    public static final int P = 21;
    public static final int Q = 23;
    public static final int R = 6;

    /* renamed from: S */
    @NotNull
    public static final String DEFAULT_UID = "00000";

    /* renamed from: b */
    @NotNull
    public static final String DEFAULT_SENSORUID = "000000";

    /* renamed from: c */
    @NotNull
    public static final String SYSTEM_TYPE_AIRCON = "07";

    /* renamed from: d */
    @NotNull
    public static final String SYSTEM_TYPE_RF = "08";

    /* renamed from: e */
    @NotNull
    public static final String SYSTEM_TYPE_LIGHTING = "02";

    /* renamed from: f */
    @NotNull
    public static final String DEVICE_TYPE_LIGHT = "01";

    /* renamed from: g */
    @NotNull
    public static final String DEVICE_TYPE_RF = "02";

    /* renamed from: h */
    @NotNull
    public static final String DEVICE_TYPE_h = "03";

    /* renamed from: i */
    @NotNull
    public static final String DEVICE_TYPE_i = "04";

    /* renamed from: j */
    @NotNull
    public static final String f7117j = "00";

    /* renamed from: k */
    @NotNull
    public static final String f7118k = "00";

    /* renamed from: l */
    @NotNull
    public static final String f7119l = "01";

    /* renamed from: m */
    @NotNull
    public static final String f7120m = "01";

    /* renamed from: n */
    @NotNull
    public static final String f7121n = "02";

    /* renamed from: o */
    @NotNull
    public static final String f7122o = "03";

    /* renamed from: p */
    @NotNull
    public static final String f7123p = "04";

    /* renamed from: q */
    @NotNull
    public static final String f7124q = "05";

    /* renamed from: r */
    @NotNull
    public static final String f7125r = "06";

    /* renamed from: s */
    @NotNull
    public static final String f7126s = "07";

    /* renamed from: t */
    @NotNull
    public static final String f7127t = "08";

    /* renamed from: u */
    @NotNull
    public static final String f7128u = "09";

    /* renamed from: v */
    @NotNull
    public static final String f7129v = "0a";

    /* renamed from: w */
    @NotNull
    public static final String f7130w = "12";

    @NotNull
    public static final String x = "13";

    /* renamed from: y */
    @NotNull
    public static final String f7131y = "14";

    /* renamed from: z */
    @NotNull
    public static final String f7132z = "15";

    /* renamed from: a */
    @NotNull
    public static final Companion Companion = new Companion(null);

    /* renamed from: T */
    private static final String LOG_NAME = FRLParser.class.getSimpleName();

    /* renamed from: com.air.advantage.uart.i$a */
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.i.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private Companion() {
        }
    }

    /* renamed from: a */
    public final boolean compareFRLValue(@NotNull String message, int i10, @NotNull String compareValue) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(compareValue, "compareValue");
        int i11 = i10 + 2;
        if (i11 > message.length()) {
            return false;
        }
        String substring = message.substring(i10, i11);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return Intrinsics.areEqual(substring, compareValue);
    }

    @NotNull
    /* renamed from: b */
    public final String formatIntToHex(@Nullable Integer num) {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.ENGLISH, "%02x", Arrays.copyOf(new Object[]{num}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(locale, format, *args)");
        return format;
    }

    @Nullable
    /* renamed from: c */
    public final Integer parseHexToInt(@NotNull String message, int i10) {
        Intrinsics.checkNotNullParameter(message, "message");
        String extractHexString = extractHexString(message, i10);
        try {
            Intrinsics.checkNotNull(extractHexString);
            return Integer.valueOf(Integer.parseInt(extractHexString, CharsKt__CharJVMKt.checkRadix(16)));
        } catch (Exception e7) {
            AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
            return null;
        }
    }

    @Nullable
    /* renamed from: d */
    public final String extractHexString(@NotNull String message, int i10) {
        Intrinsics.checkNotNullParameter(message, "message");
        int i11 = i10 + 2;
        if (message.length() < i11) {
            return null;
        }
        String substring = message.substring(i10, i11);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return substring;
    }

    @Nullable
    /* renamed from: e */
    public final String extractUIDValue(@NotNull String message, int i10) {
        Intrinsics.checkNotNullParameter(message, "message");
        int i11 = i10 + 5;
        if (message.length() <= i11) {
            Timber.forest.d("Error making UID", new Object[0]);
            return null;
        }
        String substring = message.substring(i10, i11);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return substring;
    }
}