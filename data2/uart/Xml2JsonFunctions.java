package com.air.advantage.uart;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.air.advantage.ActivityMain;
import com.air.advantage.AppFeatures;
import com.air.advantage.SharedPreferencesStore;
import com.air.advantage.UartStrings;
import com.air.advantage.XMLParser;
import com.air.advantage.data.DataAirconInfo;
import com.air.advantage.data.DataAirconSystem;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataSystem;
import com.air.advantage.data.DataZone;
import com.air.advantage.energymonitoring.FragmentEnergyMonitoring;
import com.air.advantage.jsondata.MasterStore;
import com.air.advantage.libraryairconlightjson.AirconFunctionsConstants;
import com.air.advantage.libraryairconlightjson.AirconMode;
import com.air.advantage.libraryairconlightjson.CommonFuncs;
import com.air.advantage.libraryairconlightjson.DumpStringToFile;
import com.air.advantage.libraryairconlightjson.FanStatus;
import com.air.advantage.uart.HandlerJson;
import com.google.firebase.appindexing.builders.AlarmBuilder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.Unit;
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

@PurelyImplements({"SMAP\nXml2JsonFunctions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Xml2JsonFunctions.kt\ncom/air/advantage/uart/Xml2JsonFunctions\n+ 2 Strings.kt\nkotlin/text/StringsKt__StringsKt\n*L\n1#1,585:1\n107#2:586\n79#2,22:587\n*S KotlinDebug\n*F\n+ 1 Xml2JsonFunctions.kt\ncom/air/advantage/uart/Xml2JsonFunctions\n*L\n174#1:586\n174#1:587,22\n*E\n"})
/* renamed from: com.air.advantage.uart.k0 */
/* loaded from: classes.dex */
public final class Xml2JsonFunctions {

    /* renamed from: j */
    @NotNull
    public static final String DEFAULT_UID = "000000000000";

    /* renamed from: k */
    public static final long DELAY_MS = 4000;

    /* renamed from: n */
    private static final int MAX_RETRIES = 5;

    /* renamed from: o */
    @NotNull
    private static final String DEFAULT_PIN = "0000";

    /* renamed from: p */
    @NotNull
    private static final String DEFAULT_PHONE_NUMBER = "0000000000";

    /* renamed from: q */
    @NotNull
    private static final String DEFAULT_SYSTEM_NAME = "AIRCON";

    /* renamed from: r */
    @Nullable
    private static Xml2JsonFunctions INSTANCE;

    /* renamed from: a */
    @NotNull
    private final AtomicLong lastMessageTime;

    /* renamed from: b */
    @NotNull
    private final Comparator<String> messageComparator;

    /* renamed from: c */
    @NotNull
    private final TreeMap<String, byte[]> messageQueue;

    /* renamed from: d */
    @NotNull
    private final ProcessStoredMessagesRunnable processStoredMessagesRunnable;

    /* renamed from: e */
    @NotNull
    private final Xml2JsonCommon xml2JsonCommon;

    /* renamed from: f */
    @NotNull
    private final UpdateAirconRunnable updateAirconRunnable;

    /* renamed from: g */
    private boolean disableSchedules;

    /* renamed from: h */
    @Nullable
    private String currentAirconUid;

    /* renamed from: i */
    @NotNull
    public static final Companion Companion = new Companion(null);

    /* renamed from: l */
    @NotNull
    private static final AtomicBoolean isProcessing = new AtomicBoolean(false);

    /* renamed from: m */
    private static final String LOG_TAG = Xml2JsonFunctions.class.getSimpleName();

    /* renamed from: com.air.advantage.uart.k0$a */
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.k0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void d() {
        }

        /* renamed from: a */
        public final void reset() {
            Xml2JsonFunctions.INSTANCE = null;
            getIsProcessing().set(false);
        }

        @NotNull
        /* renamed from: b */
        public final AtomicBoolean getIsProcessing() {
            return Xml2JsonFunctions.isProcessing;
        }

        @NotNull
        /* renamed from: c */
        public final Xml2JsonFunctions getInstance() {
            if (Xml2JsonFunctions.INSTANCE == null) {
                synchronized (Xml2JsonFunctions.class) {
                    if (Xml2JsonFunctions.INSTANCE == null) {
                        Companion companion = Xml2JsonFunctions.Companion;
                        Xml2JsonFunctions.INSTANCE = new Xml2JsonFunctions(null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            Xml2JsonFunctions xml2JsonFunctions = Xml2JsonFunctions.INSTANCE;
            Intrinsics.checkNotNull(xml2JsonFunctions);
            return xml2JsonFunctions;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private Companion() {
        }
    }

    /* renamed from: com.air.advantage.uart.k0$b */
    private static final class ProcessStoredMessagesRunnable implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
            ActivityMain companion = ActivityMain.Companion.getInstance();
            if (companion != null) {
                Xml2JsonFunctions.Companion.getInstance().processStoredXMLMessages(companion);
            }
        }
    }

    /* renamed from: com.air.advantage.uart.k0$c */
    private static final class UpdateAirconRunnable implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
            DataAirconSystem airconByUid;
            ActivityMain companion = ActivityMain.Companion.getInstance();
            if (companion != null) {
                Xml2JsonFunctions companion2 = Xml2JsonFunctions.Companion.getInstance();
                synchronized (MyMasterData.class) {
                    DataMaster dataMaster = MyMasterData.Companion.getDataMaster(companion);
                    if (companion2.currentAirconUid != null && (airconByUid = dataMaster.getAirconByUid(companion2.currentAirconUid)) != null) {
                        HandlerAircon.Companion.getInstance().s(companion, airconByUid);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.k0.<init>():void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ Xml2JsonFunctions(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* renamed from: h */
    private final void sendSystemData(Context context, DataSystem dataSystem) {
        sendMessages(context, this.xml2JsonCommon.getSystemDataMessages(dataSystem));
    }

    @NotNull
    /* renamed from: i */
    public static final Xml2JsonFunctions getInstance() {
        return Companion.getInstance();
    }

    /* renamed from: j */
    private final boolean isValidMac(String str) {
        if (str != null && str.length() == 12) {
            return new Regex("^[A-F0-9]+$").matches(str);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: l */
    public final void processStoredXMLMessages(Context context) {
        Timber.forest.d("processStoredXMLMessages called", new Object[0]);
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            boolean z7 = false;
            for (String str : this.messageQueue.keySet()) {
                byte[] bArr = this.messageQueue.get(str);
                if (bArr == null) {
                    return;
                }
                Intrinsics.checkNotNull(bArr);
                Intrinsics.checkNotNull(str);
                if (processMessage(context, dataMaster, str, bArr)) {
                    z7 = true;
                }
            }
            this.messageQueue.clear();
            if (z7) {
                HandlerJson.Companion companion = HandlerJson.Companion;
                companion.getIsProcessing().set(false);
                companion.getInstance(context).processData(dataMaster, "processStoredXMLMessages");
            }
            isProcessing.set(false);
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:149:0x0279 A[Catch: IllegalArgumentException -> 0x004b, TryCatch #4 {IllegalArgumentException -> 0x004b, blocks: (B:7:0x0027, B:12:0x005d, B:86:0x0230, B:88:0x023e, B:90:0x0246, B:91:0x024a, B:93:0x024e, B:95:0x0257, B:97:0x025d, B:98:0x0261, B:99:0x0265, B:102:0x026d, B:107:0x0293, B:109:0x02a5, B:111:0x02ab, B:114:0x02bb, B:117:0x02cc, B:119:0x02b5, B:121:0x02e1, B:124:0x02ea, B:126:0x02f0, B:127:0x02f6, B:130:0x02ff, B:132:0x0303, B:134:0x0318, B:136:0x032b, B:140:0x0334, B:142:0x0353, B:143:0x0357, B:144:0x035f, B:149:0x0279, B:152:0x0281, B:158:0x028d, B:247:0x04d1, B:249:0x04d7, B:251:0x04e3), top: B:5:0x0025 }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x028d A[Catch: IllegalArgumentException -> 0x004b, TryCatch #4 {IllegalArgumentException -> 0x004b, blocks: (B:7:0x0027, B:12:0x005d, B:86:0x0230, B:88:0x023e, B:90:0x0246, B:91:0x024a, B:93:0x024e, B:95:0x0257, B:97:0x025d, B:98:0x0261, B:99:0x0265, B:102:0x026d, B:107:0x0293, B:109:0x02a5, B:111:0x02ab, B:114:0x02bb, B:117:0x02cc, B:119:0x02b5, B:121:0x02e1, B:124:0x02ea, B:126:0x02f0, B:127:0x02f6, B:130:0x02ff, B:132:0x0303, B:134:0x0318, B:136:0x032b, B:140:0x0334, B:142:0x0353, B:143:0x0357, B:144:0x035f, B:149:0x0279, B:152:0x0281, B:158:0x028d, B:247:0x04d1, B:249:0x04d7, B:251:0x04e3), top: B:5:0x0025 }] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x01ce A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:182:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x049b A[Catch: IllegalArgumentException -> 0x0368, TRY_LEAVE, TryCatch #9 {IllegalArgumentException -> 0x0368, blocks: (B:20:0x00ac, B:22:0x00be, B:23:0x00c7, B:28:0x00d5, B:31:0x00e2, B:33:0x00e8, B:37:0x0107, B:43:0x0121, B:55:0x0130, B:59:0x0144, B:60:0x0160, B:62:0x0166, B:64:0x016e, B:65:0x0179, B:67:0x017f, B:69:0x0187, B:70:0x0192, B:73:0x019e, B:75:0x01ac, B:77:0x01b8, B:79:0x01be, B:183:0x018d, B:184:0x0174, B:52:0x0128, B:190:0x0149, B:192:0x0155, B:193:0x015c, B:203:0x0380, B:207:0x0387, B:209:0x038e, B:211:0x0396, B:214:0x03a5, B:216:0x03a9, B:218:0x03ad, B:220:0x03bb, B:224:0x03d8, B:226:0x03ff, B:227:0x0421, B:229:0x0432, B:230:0x0463, B:233:0x046a, B:238:0x048e, B:240:0x049b, B:259:0x0476, B:262:0x047c, B:268:0x0488, B:269:0x0403, B:277:0x04fd, B:279:0x0503, B:281:0x0509, B:283:0x0533, B:285:0x0542, B:287:0x0551, B:289:0x0560, B:291:0x056f, B:293:0x057e, B:298:0x0595, B:300:0x05a8, B:302:0x05ae, B:306:0x0608, B:308:0x0623, B:312:0x0634, B:314:0x063a), top: B:18:0x00aa }] */
    /* JADX WARN: Removed duplicated region for block: B:249:0x04d7 A[Catch: IllegalArgumentException -> 0x004b, TryCatch #4 {IllegalArgumentException -> 0x004b, blocks: (B:7:0x0027, B:12:0x005d, B:86:0x0230, B:88:0x023e, B:90:0x0246, B:91:0x024a, B:93:0x024e, B:95:0x0257, B:97:0x025d, B:98:0x0261, B:99:0x0265, B:102:0x026d, B:107:0x0293, B:109:0x02a5, B:111:0x02ab, B:114:0x02bb, B:117:0x02cc, B:119:0x02b5, B:121:0x02e1, B:124:0x02ea, B:126:0x02f0, B:127:0x02f6, B:130:0x02ff, B:132:0x0303, B:134:0x0318, B:136:0x032b, B:140:0x0334, B:142:0x0353, B:143:0x0357, B:144:0x035f, B:149:0x0279, B:152:0x0281, B:158:0x028d, B:247:0x04d1, B:249:0x04d7, B:251:0x04e3), top: B:5:0x0025 }] */
    /* JADX WARN: Removed duplicated region for block: B:254:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:257:0x04d0  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0476 A[Catch: IllegalArgumentException -> 0x0368, TryCatch #9 {IllegalArgumentException -> 0x0368, blocks: (B:20:0x00ac, B:22:0x00be, B:23:0x00c7, B:28:0x00d5, B:31:0x00e2, B:33:0x00e8, B:37:0x0107, B:43:0x0121, B:55:0x0130, B:59:0x0144, B:60:0x0160, B:62:0x0166, B:64:0x016e, B:65:0x0179, B:67:0x017f, B:69:0x0187, B:70:0x0192, B:73:0x019e, B:75:0x01ac, B:77:0x01b8, B:79:0x01be, B:183:0x018d, B:184:0x0174, B:52:0x0128, B:190:0x0149, B:192:0x0155, B:193:0x015c, B:203:0x0380, B:207:0x0387, B:209:0x038e, B:211:0x0396, B:214:0x03a5, B:216:0x03a9, B:218:0x03ad, B:220:0x03bb, B:224:0x03d8, B:226:0x03ff, B:227:0x0421, B:229:0x0432, B:230:0x0463, B:233:0x046a, B:238:0x048e, B:240:0x049b, B:259:0x0476, B:262:0x047c, B:268:0x0488, B:269:0x0403, B:277:0x04fd, B:279:0x0503, B:281:0x0509, B:283:0x0533, B:285:0x0542, B:287:0x0551, B:289:0x0560, B:291:0x056f, B:293:0x057e, B:298:0x0595, B:300:0x05a8, B:302:0x05ae, B:306:0x0608, B:308:0x0623, B:312:0x0634, B:314:0x063a), top: B:18:0x00aa }] */
    /* JADX WARN: Removed duplicated region for block: B:268:0x0488 A[Catch: IllegalArgumentException -> 0x0368, TryCatch #9 {IllegalArgumentException -> 0x0368, blocks: (B:20:0x00ac, B:22:0x00be, B:23:0x00c7, B:28:0x00d5, B:31:0x00e2, B:33:0x00e8, B:37:0x0107, B:43:0x0121, B:55:0x0130, B:59:0x0144, B:60:0x0160, B:62:0x0166, B:64:0x016e, B:65:0x0179, B:67:0x017f, B:69:0x0187, B:70:0x0192, B:73:0x019e, B:75:0x01ac, B:77:0x01b8, B:79:0x01be, B:183:0x018d, B:184:0x0174, B:52:0x0128, B:190:0x0149, B:192:0x0155, B:193:0x015c, B:203:0x0380, B:207:0x0387, B:209:0x038e, B:211:0x0396, B:214:0x03a5, B:216:0x03a9, B:218:0x03ad, B:220:0x03bb, B:224:0x03d8, B:226:0x03ff, B:227:0x0421, B:229:0x0432, B:230:0x0463, B:233:0x046a, B:238:0x048e, B:240:0x049b, B:259:0x0476, B:262:0x047c, B:268:0x0488, B:269:0x0403, B:277:0x04fd, B:279:0x0503, B:281:0x0509, B:283:0x0533, B:285:0x0542, B:287:0x0551, B:289:0x0560, B:291:0x056f, B:293:0x057e, B:298:0x0595, B:300:0x05a8, B:302:0x05ae, B:306:0x0608, B:308:0x0623, B:312:0x0634, B:314:0x063a), top: B:18:0x00aa }] */
    /* JADX WARN: Removed duplicated region for block: B:307:0x0590  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x0623 A[Catch: IllegalArgumentException -> 0x0368, TRY_LEAVE, TryCatch #9 {IllegalArgumentException -> 0x0368, blocks: (B:20:0x00ac, B:22:0x00be, B:23:0x00c7, B:28:0x00d5, B:31:0x00e2, B:33:0x00e8, B:37:0x0107, B:43:0x0121, B:55:0x0130, B:59:0x0144, B:60:0x0160, B:62:0x0166, B:64:0x016e, B:65:0x0179, B:67:0x017f, B:69:0x0187, B:70:0x0192, B:73:0x019e, B:75:0x01ac, B:77:0x01b8, B:79:0x01be, B:183:0x018d, B:184:0x0174, B:52:0x0128, B:190:0x0149, B:192:0x0155, B:193:0x015c, B:203:0x0380, B:207:0x0387, B:209:0x038e, B:211:0x0396, B:214:0x03a5, B:216:0x03a9, B:218:0x03ad, B:220:0x03bb, B:224:0x03d8, B:226:0x03ff, B:227:0x0421, B:229:0x0432, B:230:0x0463, B:233:0x046a, B:238:0x048e, B:240:0x049b, B:259:0x0476, B:262:0x047c, B:268:0x0488, B:269:0x0403, B:277:0x04fd, B:279:0x0503, B:281:0x0509, B:283:0x0533, B:285:0x0542, B:287:0x0551, B:289:0x0560, B:291:0x056f, B:293:0x057e, B:298:0x0595, B:300:0x05a8, B:302:0x05ae, B:306:0x0608, B:308:0x0623, B:312:0x0634, B:314:0x063a), top: B:18:0x00aa }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x022d  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0230 A[Catch: IllegalArgumentException -> 0x004b, TRY_ENTER, TryCatch #4 {IllegalArgumentException -> 0x004b, blocks: (B:7:0x0027, B:12:0x005d, B:86:0x0230, B:88:0x023e, B:90:0x0246, B:91:0x024a, B:93:0x024e, B:95:0x0257, B:97:0x025d, B:98:0x0261, B:99:0x0265, B:102:0x026d, B:107:0x0293, B:109:0x02a5, B:111:0x02ab, B:114:0x02bb, B:117:0x02cc, B:119:0x02b5, B:121:0x02e1, B:124:0x02ea, B:126:0x02f0, B:127:0x02f6, B:130:0x02ff, B:132:0x0303, B:134:0x0318, B:136:0x032b, B:140:0x0334, B:142:0x0353, B:143:0x0357, B:144:0x035f, B:149:0x0279, B:152:0x0281, B:158:0x028d, B:247:0x04d1, B:249:0x04d7, B:251:0x04e3), top: B:5:0x0025 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x023e A[Catch: IllegalArgumentException -> 0x004b, TryCatch #4 {IllegalArgumentException -> 0x004b, blocks: (B:7:0x0027, B:12:0x005d, B:86:0x0230, B:88:0x023e, B:90:0x0246, B:91:0x024a, B:93:0x024e, B:95:0x0257, B:97:0x025d, B:98:0x0261, B:99:0x0265, B:102:0x026d, B:107:0x0293, B:109:0x02a5, B:111:0x02ab, B:114:0x02bb, B:117:0x02cc, B:119:0x02b5, B:121:0x02e1, B:124:0x02ea, B:126:0x02f0, B:127:0x02f6, B:130:0x02ff, B:132:0x0303, B:134:0x0318, B:136:0x032b, B:140:0x0334, B:142:0x0353, B:143:0x0357, B:144:0x035f, B:149:0x0279, B:152:0x0281, B:158:0x028d, B:247:0x04d1, B:249:0x04d7, B:251:0x04e3), top: B:5:0x0025 }] */
    /* renamed from: m */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean processMessage(Context context, DataMaster dataMaster, String str, byte[] bArr) {
        Charset charset;
        byte[] bytes;
        String str2;
        boolean z7;
        boolean z10;
        boolean z11;
        String str3;
        Integer num;
        String str4;
        String str5;
        boolean z12;
        boolean z13;
        DataAirconSystem aircon;
        Integer num2;
        Boolean bool;
        XMLParser xMLParser = new XMLParser();
        boolean z14 = false;
        try {
            charset = Charsets.UTF_8;
            bytes = "iZS10.3".getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
            try {
            } catch (IllegalArgumentException e7) {
                e = e7;
            }
        } catch (IllegalArgumentException e10) {
            e = e10;
        }
        if (!xMLParser.isValidMessage(bArr, bytes)) {
            Timber.forest.d("XML failed - no start and stop tags " + bArr, new Object[0]);
            DumpStringToFile.getInstance().dumpToFile("app_error", bArr.toString());
            return false;
        }
        byte[] bytes2 = "request".getBytes(charset);
        Intrinsics.checkNotNullExpressionValue(bytes2, "this as java.lang.String).getBytes(charset)");
        String stringElement = xMLParser.getStringElement(bArr, bytes2);
        if (stringElement == null) {
            Timber.forest.d("XML failed - no request string", new Object[0]);
            DumpStringToFile.getInstance().dumpToFile("app_error", bArr.toString());
            return false;
        }
        try {
            byte[] bytes3 = "mac".getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(bytes3, "this as java.lang.String).getBytes(charset)");
            str2 = xMLParser.getStringElement(bArr, bytes3);
        } catch (IllegalArgumentException unused) {
            str2 = DEFAULT_UID;
        }
        Timber.Forest forest = Timber.forest;
        forest.d("Processing " + str, new Object[0]);
        String str6 = "&day=&startHours=0&startMinutes=0&endHours=0&endMinutes=0&scheduleStatus=0&zoneStatus=0&zones=";
        int i10 = 1;
        try {
            try {
                if (Intrinsics.areEqual(stringElement, "getSystemData")) {
                    DataMaster dataMasterFromXml = this.xml2JsonCommon.dataMasterFromXml(str2, bArr);
                    HandlerAircon companion = HandlerAircon.Companion.getInstance();
                    if (AppFeatures.hasVams()) {
                        ZoneData.Companion.getInstance(context, dataMasterFromXml.getAirconByUid(str2));
                    }
                    if (Intrinsics.areEqual(str2, DEFAULT_UID)) {
                        MasterStore.lock.set(true);
                        return false;
                    }
                    MasterStore.lock.set(false);
                    String str7 = dataMasterFromXml.system.name;
                    if (str7 == null || Intrinsics.areEqual(str7, DEFAULT_SYSTEM_NAME)) {
                        str4 = "&day=&startHours=0&startMinutes=0&endHours=0&endMinutes=0&scheduleStatus=0&zoneStatus=0&zones=";
                        DataAirconSystem dataAirconSystem = dataMasterFromXml.aircons.get(FragmentEnergyMonitoring.DEFAULT_AIRCON_KEY);
                        if (dataAirconSystem != null) {
                            str5 = null;
                            dataAirconSystem.info.name = null;
                        } else {
                            str5 = null;
                        }
                        dataMasterFromXml.system.name = str5;
                    } else {
                        DataSystem dataSystem = new DataSystem();
                        dataSystem.name = DEFAULT_SYSTEM_NAME;
                        sendSystemData(context, dataSystem);
                        String str8 = dataMasterFromXml.system.name;
                        Intrinsics.checkNotNull(str8);
                        int length = str8.length() - 1;
                        int i11 = 0;
                        boolean z15 = false;
                        while (true) {
                            if (i11 > length) {
                                str4 = str6;
                                break;
                            }
                            str4 = str6;
                            boolean z16 = Intrinsics.compare(str8.charAt(!z15 ? i11 : length), 32) <= 0;
                            if (z15) {
                                if (!z16) {
                                    i10 = 1;
                                    break;
                                }
                                length--;
                            } else if (z16) {
                                i11++;
                            } else {
                                str6 = str4;
                                i10 = 1;
                                z15 = true;
                            }
                            str6 = str4;
                            i10 = 1;
                        }
                        if (str8.subSequence(i11, length + i10).toString().length() == 0) {
                            dataMasterFromXml.system.name = DEFAULT_SYSTEM_NAME;
                        }
                    }
                    String str9 = dataMasterFromXml.system.logoPIN;
                    if (str9 == null || !Intrinsics.areEqual(str9, DEFAULT_PIN)) {
                        sendMessageToCB(context, "setSystemData?logoPIN=0000");
                    } else {
                        dataMasterFromXml.system.logoPIN = null;
                    }
                    String str10 = dataMasterFromXml.system.dealerPhoneNumber;
                    if (str10 == null || !Intrinsics.areEqual(str10, DEFAULT_PHONE_NUMBER)) {
                        sendMessageToCB(context, "setSystemData?dealerPhoneNumber=0000000000");
                    } else {
                        dataMasterFromXml.system.dealerPhoneNumber = null;
                    }
                    DataAirconSystem dataAirconSystem2 = dataMasterFromXml.aircons.get(FragmentEnergyMonitoring.DEFAULT_AIRCON_KEY);
                    if (dataAirconSystem2 == null) {
                        AppFeatures.Error(AppFeatures.instance, new NullPointerException("newDataAircon is null"), null, 2, null);
                        return false;
                    }
                    dataMaster.oneAirconOnly = true;
                    dataMaster.multipleAirconDetectedOnOneAirconOnlySystem = false;
                    DataAirconSystem airconByUid = dataMaster.getAirconByUid(str2);
                    if (airconByUid == null || (bool = airconByUid.info.enabled) == null) {
                        z12 = true;
                        if (z12) {
                            z13 = false;
                        } else {
                            try {
                                AirconDBStore companion2 = AirconDBStore.Companion.getInstance(context);
                                if (airconByUid == null) {
                                    airconByUid = companion2.getAircon(context, str2);
                                    dataMaster.aircons.put(str2, airconByUid);
                                    dataMaster.sortAircons(false);
                                } else {
                                    companion2.getAircon(context, str2);
                                    airconByUid.info.enabled = Boolean.TRUE;
                                }
                                Boolean bool2 = airconByUid.info.quietNightModeIsRunning;
                                if (bool2 != null) {
                                    Intrinsics.checkNotNull(bool2);
                                    if (bool2.booleanValue() && (aircon = ((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).getAircon(context, str2)) != null) {
                                        companion.getFanSpeedMap().put(str2, aircon);
                                    }
                                }
                                Boolean bool3 = airconByUid.info.myFanSpeedIsRunning;
                                if (bool3 != null) {
                                    Intrinsics.checkNotNull(bool3);
                                    if (bool3.booleanValue()) {
                                        airconByUid.info.fan = FanStatus.autoAA;
                                    }
                                }
                                z13 = true;
                            } catch (IllegalArgumentException e11) {
                                e = e11;
                                z14 = true;
                            }
                        }
                        if (airconByUid != null) {
                            AppFeatures.Error(AppFeatures.instance, new NullPointerException("newDataAircon is null"), null, 2, null);
                            return false;
                        }
                        DataAirconInfo dataAirconInfo = airconByUid.info;
                        FanStatus fanStatus = dataAirconInfo.fan;
                        FanStatus fanStatus2 = FanStatus.autoAA;
                        if (fanStatus == fanStatus2) {
                            dataAirconSystem2.info.fan = fanStatus2;
                        }
                        Boolean bool4 = dataAirconInfo.myAutoModeIsRunning;
                        if (bool4 != null) {
                            Intrinsics.checkNotNull(bool4);
                            if (bool4.booleanValue()) {
                                DataAirconInfo dataAirconInfo2 = dataAirconSystem2.info;
                                AirconMode airconMode = dataAirconInfo2.mode;
                                if (airconMode != null) {
                                    airconByUid.info.myAutoModeCurrentSetMode = airconMode;
                                }
                                dataAirconInfo2.mode = AirconMode.myauto;
                            }
                        }
                        String str11 = airconByUid.info.name;
                        if (str11 == null) {
                            String str12 = dataAirconSystem2.info.name;
                            if (str12 == null) {
                                dataAirconSystem2.info.name = AirconFunctionsConstants.AC;
                            } else if (str12 == null || str12.length() == 0) {
                            }
                        } else if (str11 == null || str11.length() == 0) {
                        }
                        airconByUid.info.expireTime = Long.valueOf(CommonFuncs.getUptime() + 160);
                        TreeMap<String, DataZone> treeMap = airconByUid.zones;
                        if (treeMap != null && (num2 = dataAirconSystem2.info.noOfZones) != null) {
                            Intrinsics.checkNotNull(treeMap);
                            int size = treeMap.size();
                            if (num2 == null || num2.intValue() != size) {
                                Integer num3 = dataAirconSystem2.info.noOfZones;
                                Intrinsics.checkNotNull(num3);
                                for (int intValue = num3.intValue() + 1; intValue < 11; intValue++) {
                                    TreeMap<String, DataZone> treeMap2 = airconByUid.zones;
                                    Intrinsics.checkNotNull(treeMap2);
                                    treeMap2.remove(DataZone.Companion.getZoneKey(Integer.valueOf(intValue)));
                                }
                            }
                        }
                        if (companion.u0(airconByUid, dataAirconSystem2.info)) {
                            z13 = true;
                        }
                        if (dataMaster.system.hasLights == null) {
                            dataMasterFromXml.system.hasLights = Boolean.FALSE;
                        }
                        if (companion.update(dataMaster, dataMasterFromXml.system)) {
                            z13 = true;
                        }
                        if (this.disableSchedules) {
                            Timber.Forest forest2 = Timber.forest;
                            forest2.v("Disabling timer and schedules", new Object[0]);
                            sendMessageToCB(context, "setZoneTimer?startTimeHours=0&startTimeMinutes=0&endTimeHours=0&endTimeMinutes=0&scheduleStatus=0");
                            if (airconByUid.info.noOfZones != null) {
                                StringBuilder sb = new StringBuilder();
                                Integer num4 = airconByUid.info.noOfZones;
                                Intrinsics.checkNotNull(num4);
                                int intValue2 = num4.intValue();
                                for (int i12 = 0; i12 < intValue2; i12++) {
                                    sb.append("1");
                                }
                                int i13 = 1;
                                while (i13 < 6) {
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append("setScheduleData?schedule=");
                                    sb2.append(i13);
                                    String str13 = str4;
                                    sb2.append(str13);
                                    sb2.append((Object) sb);
                                    sendMessageToCB(context, sb2.toString());
                                    i13++;
                                    str4 = str13;
                                }
                                this.disableSchedules = false;
                            } else {
                                forest2.v("Haven't got no of zones - cannot disable schedules yet", new Object[0]);
                            }
                        }
                        if (companion.v(dataMaster)) {
                            return true;
                        }
                        return z13;
                    }
                    Intrinsics.checkNotNull(bool);
                    if (bool.booleanValue()) {
                        z12 = false;
                    }
                    if (z12) {
                    }
                    if (airconByUid != null) {
                    }
                } else {
                    try {
                        if (StringsKt__StringsKt.startsWith$default(stringElement, "getZoneData", false, 2, null)) {
                            DataZone zoneData = this.xml2JsonCommon.getZoneData(bArr);
                            if (zoneData == null || (num = zoneData.number) == null || num == null) {
                                z10 = true;
                            } else {
                                z10 = true;
                                if (num.intValue() == 1 && isValidMac(zoneData.name)) {
                                    zoneData.name = "ZONE 1";
                                    sendMessageToCB(context, "setZoneData?zone=1&name=ZONE 1");
                                }
                            }
                            if (zoneData != null && (str3 = zoneData.name) != null && zoneData.number != null) {
                                Intrinsics.checkNotNull(str3);
                                if (StringsKt__StringsKt.startsWith$default(str3, "iApp ZONE ", false, 2, null)) {
                                    zoneData.name = "ZONE " + zoneData.number;
                                }
                            }
                            DataAirconSystem airconByUid2 = dataMaster.getAirconByUid(str2);
                            if (airconByUid2 == null || zoneData == null) {
                                return false;
                            }
                            airconByUid2.info.expireTime = Long.valueOf(CommonFuncs.getUptime() + 160);
                            String str14 = "ZONE " + zoneData.number;
                            if (Intrinsics.areEqual(zoneData.name, str14)) {
                                zoneData.name = null;
                            } else {
                                sendMessageToCB(context, "setZoneData?zone=" + zoneData.number + "&name=" + str14);
                            }
                            TreeMap<String, DataZone> treeMap3 = airconByUid2.zones;
                            Intrinsics.checkNotNull(treeMap3);
                            DataZone dataZone = treeMap3.get(zoneData.getZoneKey());
                            if (dataZone == null) {
                                dataZone = new DataZone(zoneData.number);
                                TreeMap<String, DataZone> treeMap4 = airconByUid2.zones;
                                Intrinsics.checkNotNull(treeMap4);
                                treeMap4.put(dataZone.getZoneKey(), dataZone);
                                forest.d("Zone " + zoneData.number + " added", new Object[0]);
                            }
                            DataZone dataZone2 = dataZone;
                            String str15 = dataZone2.name;
                            if (str15 == null) {
                                String str16 = zoneData.name;
                                if (str16 == null) {
                                    zoneData.name = zoneData.defaultZoneName();
                                    if (DataZone.update$default(dataZone2, zoneData, null, null, false, 12, null)) {
                                        AirconDBStore.Companion.getInstance(context).updateStore(context, airconByUid2.info.uid, airconByUid2);
                                        try {
                                            ActivityMain companion3 = ActivityMain.Companion.getInstance();
                                            if (companion3 != null) {
                                                this.currentAirconUid = airconByUid2.info.uid;
                                                companion3.getMainHandler().removeCallbacks(this.updateAirconRunnable);
                                                companion3.getMainHandler().postDelayed(this.updateAirconRunnable, 1000L);
                                            }
                                            z11 = z10;
                                        } catch (IllegalArgumentException e12) {
                                            e = e12;
                                            z14 = z10;
                                        }
                                    } else {
                                        z11 = false;
                                    }
                                    if (!AppFeatures.hasVams()) {
                                        return z11;
                                    }
                                    Integer num5 = zoneData.number;
                                    Intrinsics.checkNotNull(num5);
                                    if (num5.intValue() > 2) {
                                        return z11;
                                    }
                                    ZoneData.Companion.dataZoneUpdate(context, zoneData);
                                    return z11;
                                }
                                if ((str16 == null || str16.length() == 0) ? z10 : false) {
                                }
                                if (DataZone.update$default(dataZone2, zoneData, null, null, false, 12, null)) {
                                }
                                if (!AppFeatures.hasVams()) {
                                }
                            } else {
                                if ((str15 == null || str15.length() == 0) ? z10 : false) {
                                }
                                if (DataZone.update$default(dataZone2, zoneData, null, null, false, 12, null)) {
                                }
                                if (!AppFeatures.hasVams()) {
                                }
                            }
                        } else {
                            try {
                                if (StringsKt__StringsKt.startsWith$default(stringElement, "getScheduleData", false, 2, null)) {
                                    DataAirconSystem airconByUid3 = dataMaster.getAirconByUid(str2);
                                    if (airconByUid3 != null) {
                                        DataAirconInfo dataAirconInfo3 = airconByUid3.info;
                                        if (dataAirconInfo3.noOfZones != null) {
                                            dataAirconInfo3.expireTime = Long.valueOf(CommonFuncs.getUptime() + 160);
                                            Charset charset2 = Charsets.UTF_8;
                                            byte[] bytes4 = "schedule".getBytes(charset2);
                                            Intrinsics.checkNotNullExpressionValue(bytes4, "this as java.lang.String).getBytes(charset)");
                                            int intAttribute = xMLParser.getIntAttribute(bArr, bytes4);
                                            byte[] bytes5 = AlarmBuilder.MONDAY.getBytes(charset2);
                                            Intrinsics.checkNotNullExpressionValue(bytes5, "this as java.lang.String).getBytes(charset)");
                                            if (!xMLParser.getBooleanElement(bArr, bytes5)) {
                                                byte[] bytes6 = AlarmBuilder.TUESDAY.getBytes(charset2);
                                                Intrinsics.checkNotNullExpressionValue(bytes6, "this as java.lang.String).getBytes(charset)");
                                                if (!xMLParser.getBooleanElement(bArr, bytes6)) {
                                                    byte[] bytes7 = AlarmBuilder.WEDNESDAY.getBytes(charset2);
                                                    Intrinsics.checkNotNullExpressionValue(bytes7, "this as java.lang.String).getBytes(charset)");
                                                    if (!xMLParser.getBooleanElement(bArr, bytes7)) {
                                                        byte[] bytes8 = AlarmBuilder.THURSDAY.getBytes(charset2);
                                                        Intrinsics.checkNotNullExpressionValue(bytes8, "this as java.lang.String).getBytes(charset)");
                                                        if (!xMLParser.getBooleanElement(bArr, bytes8)) {
                                                            byte[] bytes9 = AlarmBuilder.FRIDAY.getBytes(charset2);
                                                            Intrinsics.checkNotNullExpressionValue(bytes9, "this as java.lang.String).getBytes(charset)");
                                                            if (!xMLParser.getBooleanElement(bArr, bytes9)) {
                                                                byte[] bytes10 = AlarmBuilder.SATURDAY.getBytes(charset2);
                                                                Intrinsics.checkNotNullExpressionValue(bytes10, "this as java.lang.String).getBytes(charset)");
                                                                if (!xMLParser.getBooleanElement(bArr, bytes10)) {
                                                                    byte[] bytes11 = AlarmBuilder.SUNDAY.getBytes(charset2);
                                                                    Intrinsics.checkNotNullExpressionValue(bytes11, "this as java.lang.String).getBytes(charset)");
                                                                    boolean z17 = xMLParser.getBooleanElement(bArr, bytes11);
                                                                    if (z17) {
                                                                        StringBuilder sb3 = new StringBuilder();
                                                                        Integer num6 = airconByUid3.info.noOfZones;
                                                                        Intrinsics.checkNotNull(num6);
                                                                        int intValue3 = num6.intValue();
                                                                        for (int i14 = 0; i14 < intValue3; i14++) {
                                                                            sb3.append("1");
                                                                        }
                                                                        Timber.forest.v("Schedule " + intAttribute + " is enabled - disabling it", new Object[0]);
                                                                        sendMessageToCB(context, "setScheduleData?schedule=" + intAttribute + "&day=&startHours=0&startMinutes=0&endHours=0&endMinutes=0&scheduleStatus=0&zoneStatus=0&zones=" + ((Object) sb3));
                                                                        AppFeatures.Error(AppFeatures.instance, new RuntimeException("getScheduleData - found old CB schedule being not cleared on previously (schedule no " + intAttribute + ") - trying to clear it again! - " + bArr), null, 2, null);
                                                                    } else {
                                                                        forest.v("Schedule " + intAttribute + " already disabled", new Object[0]);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            forest.v("Haven't got number of zones - cannot check", new Object[0]);
                                        }
                                    }
                                    return z7;
                                }
                                try {
                                    if (!Intrinsics.areEqual(stringElement, "getClock")) {
                                        z7 = false;
                                        try {
                                            forest.v("Request : " + stringElement + " do not know what to do with it!", new Object[0]);
                                            return z7;
                                        } catch (IllegalArgumentException e13) {
                                            e = e13;
                                            z14 = z7;
                                            AppFeatures.Error(AppFeatures.instance, e, null, 2, null);
                                            return z14;
                                        }
                                    }
                                    DataAirconSystem airconByUid4 = dataMaster.getAirconByUid(str2);
                                    if (airconByUid4 != null) {
                                        forest.d("getClock - updating expireTime", new Object[0]);
                                        airconByUid4.info.expireTime = Long.valueOf(CommonFuncs.getUptime() + 160);
                                    }
                                } catch (IllegalArgumentException e14) {
                                    e = e14;
                                    z7 = false;
                                }
                                z7 = false;
                                return z7;
                            } catch (IllegalArgumentException e15) {
                                e = e15;
                                z7 = false;
                            }
                        }
                    } catch (IllegalArgumentException e16) {
                        e = e16;
                        z7 = false;
                    }
                }
            } catch (IllegalArgumentException e17) {
                e = e17;
                z14 = false;
            }
        } catch (IllegalArgumentException e18) {
            e = e18;
            z14 = false;
        }
        AppFeatures.Error(AppFeatures.instance, e, null, 2, null);
        return z14;
    }

    /* renamed from: o */
    private final void sendMessages(Context context, ArrayList<String> arrayList) {
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            sendMessageToCB(context, it.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: p */
    public static final int compareZones(String str, String str2) {
        if (Intrinsics.areEqual(str, "getZoneData?zone=10")) {
            str = "getZoneData?zone=99";
        }
        if (Intrinsics.areEqual(str2, "getZoneData?zone=10")) {
            str2 = "getZoneData?zone=99";
        }
        Intrinsics.checkNotNull(str2);
        return str.compareTo(str2);
    }

    /* renamed from: g */
    public final void sendMessages(@NotNull Context context, @Nullable DataAirconSystem dataAirconSystem) {
        Integer num;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNull(dataAirconSystem);
        DataAirconInfo dataAirconInfo = dataAirconSystem.info;
        synchronized (MyMasterData.class) {
            DataAirconSystem airconByUid = MyMasterData.Companion.getDataMaster(context).getAirconByUid(dataAirconInfo.uid);
            num = airconByUid != null ? airconByUid.info.myZone : null;
            Unit unit = Unit.INSTANCE;
        }
        sendMessages(context, this.xml2JsonCommon.getMessageList(dataAirconSystem, num, false));
    }

    /* renamed from: k */
    public final void parseMessage(@NotNull Context context, @NotNull DataMaster masterData, @NotNull String uartRequest, @NotNull byte[] message) {
        Handler mainHandler;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        Intrinsics.checkNotNullParameter(uartRequest, "uartRequest");
        Intrinsics.checkNotNullParameter(message, "message");
        long currentTimeMillis = System.currentTimeMillis();
        if (StringsKt__StringsJVMKt.startsWith(uartRequest, "getClock", false, 2, null) || StringsKt__StringsJVMKt.startsWith(uartRequest, "getScheduleData", false, 2, null) || HandlerAircon.lock.get()) {
            processMessage(context, masterData, uartRequest, message);
        } else {
            ActivityMain companion = ActivityMain.Companion.getInstance();
            if (companion != null && (mainHandler = companion.getMainHandler()) != null) {
                mainHandler.removeCallbacks(this.processStoredMessagesRunnable);
            }
            this.messageQueue.put(uartRequest, message);
            if (companion == null || currentTimeMillis >= this.lastMessageTime.get()) {
                processStoredXMLMessages(context);
            } else {
                Timber.forest.d("Storing " + uartRequest + " for later", new Object[0]);
                companion.getMainHandler().postDelayed(this.processStoredMessagesRunnable, DELAY_MS);
            }
        }
        Timber.forest.v("Took : " + (System.currentTimeMillis() - currentTimeMillis), new Object[0]);
    }

    /* renamed from: n */
    public final void sendMessageToCB(@NotNull Context context, @Nullable String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        Timber.forest.d("sending XML message to cb - " + str, new Object[0]);
        Intrinsics.checkNotNull(str);
        if (!StringsKt__StringsKt.startsWith$default(str, "setScheduleData", false, 2, null) && !StringsKt__StringsKt.startsWith$default(str, "setZoneTimer", false, 2, null)) {
            this.lastMessageTime.set(System.currentTimeMillis() + DELAY_MS);
            isProcessing.set(true);
            ActivityMain companion = ActivityMain.Companion.getInstance();
            if (companion != null) {
                companion.getMainHandler().removeCallbacks(this.processStoredMessagesRunnable);
                companion.getMainHandler().postDelayed(this.processStoredMessagesRunnable, DELAY_MS);
            }
        }
        UartStrings uartStrings = UartStrings.INSTANCE;
        Intent intent = new Intent(uartStrings.MESSAGE_TO_CB());
        intent.putExtra(uartStrings.MESSAGE_TO_CB(), str);
        context.sendBroadcast(intent);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private Xml2JsonFunctions() {
        AtomicLong atomicLong = new AtomicLong(0L);
        this.lastMessageTime = atomicLong;
        j0 j0Var = new Comparator() { // from class: com.air.advantage.uart.j0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Xml2JsonFunctions.compareZones((String) obj, (String) obj2);
            }
        };
        this.messageComparator = j0Var;
        this.messageQueue = new TreeMap<>(j0Var);
        this.processStoredMessagesRunnable = new ProcessStoredMessagesRunnable();
        this.xml2JsonCommon = new Xml2JsonCommon();
        this.updateAirconRunnable = new UpdateAirconRunnable();
        this.disableSchedules = true;
        atomicLong.set(System.currentTimeMillis() + DELAY_MS);
        isProcessing.set(true);
    }
}