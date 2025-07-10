package com.air.advantage.uart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.air.advantage.ActivityMain;
import com.air.advantage.AppFeatures;
import com.air.advantage.MyApp;
import com.air.advantage.RunnableParseData;
import com.air.advantage.SharedPreferencesStore;
import com.air.advantage.aircon.AirconFunctions;
import com.air.advantage.data.DataAirconInfo;
import com.air.advantage.data.DataAirconSystem;
import com.air.advantage.data.DataGroup;
import com.air.advantage.data.DataGroupThing;
import com.air.advantage.data.DataLight;
import com.air.advantage.data.DataLightsSystem;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataMonitor;
import com.air.advantage.data.DataMyScene;
import com.air.advantage.data.DataMyThing;
import com.air.advantage.data.DataScene;
import com.air.advantage.data.DataSensor;
import com.air.advantage.data.DataSystem;
import com.air.advantage.data.DataZone;
import com.air.advantage.data.ErrorCodes;
import com.air.advantage.data.SnapShot;
import com.air.advantage.di.LocalBroadcaster;
import com.air.advantage.energymonitoring.FragmentEnergyMonitoring;
import com.air.advantage.libraryairconlightjson.AirconMode;
import com.air.advantage.libraryairconlightjson.CommonFuncs;
import com.air.advantage.libraryairconlightjson.LightState;
import com.air.advantage.libraryairconlightjson.TabletInfo;
import com.air.advantage.libraryairconlightjson.UartConstants;
import com.air.advantage.systemlistener.SystemListener;
import com.air.advantage.webserver.WebServer;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import kotlin.text.Charsets;
import kotlin.text.StringsKt__IndentKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

@PurelyImplements({"SMAP\nHandlerJson.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HandlerJson.kt\ncom/air/advantage/uart/HandlerJson\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1439:1\n1603#2,9:1440\n1855#2:1449\n1856#2:1451\n1612#2:1452\n1603#2,9:1453\n1855#2:1462\n1856#2:1464\n1612#2:1465\n1603#2,9:1466\n1855#2:1475\n1856#2:1477\n1612#2:1478\n1603#2,9:1479\n1855#2:1488\n1856#2:1490\n1612#2:1491\n1603#2,9:1492\n1855#2:1501\n1856#2:1503\n1612#2:1504\n1603#2,9:1505\n1855#2:1514\n1856#2:1516\n1612#2:1517\n1603#2,9:1518\n1855#2:1527\n1856#2:1529\n1612#2:1530\n1603#2,9:1531\n1855#2:1540\n1856#2:1542\n1612#2:1543\n1603#2,9:1544\n1855#2:1553\n1856#2:1555\n1612#2:1556\n1603#2,9:1557\n1855#2:1566\n1856#2:1568\n1612#2:1569\n1603#2,9:1570\n1855#2:1579\n1856#2:1581\n1612#2:1582\n1603#2,9:1583\n1855#2:1592\n1856#2:1594\n1612#2:1595\n1603#2,9:1596\n1855#2:1605\n1856#2:1607\n1612#2:1608\n1603#2,9:1609\n1855#2:1618\n1856#2:1620\n1612#2:1621\n1603#2,9:1622\n1855#2:1631\n1856#2:1633\n1612#2:1634\n1603#2,9:1635\n1855#2:1644\n1856#2:1646\n1612#2:1647\n1#3:1450\n1#3:1463\n1#3:1476\n1#3:1489\n1#3:1502\n1#3:1515\n1#3:1528\n1#3:1541\n1#3:1554\n1#3:1567\n1#3:1580\n1#3:1593\n1#3:1606\n1#3:1619\n1#3:1632\n1#3:1645\n*S KotlinDebug\n*F\n+ 1 HandlerJson.kt\ncom/air/advantage/uart/HandlerJson\n*L\n489#1:1440,9\n489#1:1449\n489#1:1451\n489#1:1452\n514#1:1453,9\n514#1:1462\n514#1:1464\n514#1:1465\n738#1:1466,9\n738#1:1475\n738#1:1477\n738#1:1478\n763#1:1479,9\n763#1:1488\n763#1:1490\n763#1:1491\n937#1:1492,9\n937#1:1501\n937#1:1503\n937#1:1504\n973#1:1505,9\n973#1:1514\n973#1:1516\n973#1:1517\n1099#1:1518,9\n1099#1:1527\n1099#1:1529\n1099#1:1530\n1111#1:1531,9\n1111#1:1540\n1111#1:1542\n1111#1:1543\n1117#1:1544,9\n1117#1:1553\n1117#1:1555\n1117#1:1556\n1120#1:1557,9\n1120#1:1566\n1120#1:1568\n1120#1:1569\n1142#1:1570,9\n1142#1:1579\n1142#1:1581\n1142#1:1582\n1181#1:1583,9\n1181#1:1592\n1181#1:1594\n1181#1:1595\n1193#1:1596,9\n1193#1:1605\n1193#1:1607\n1193#1:1608\n1199#1:1609,9\n1199#1:1618\n1199#1:1620\n1199#1:1621\n1202#1:1622,9\n1202#1:1631\n1202#1:1633\n1202#1:1634\n1221#1:1635,9\n1221#1:1644\n1221#1:1646\n1221#1:1647\n489#1:1450\n514#1:1463\n738#1:1476\n763#1:1489\n937#1:1502\n973#1:1515\n1099#1:1528\n1111#1:1541\n1117#1:1554\n1120#1:1567\n1142#1:1580\n1181#1:1593\n1193#1:1606\n1199#1:1619\n1202#1:1632\n1221#1:1645\n*E\n"})
/* renamed from: com.air.advantage.uart.p */
/* loaded from: classes.dex */
public final class HandlerJson {

    /* renamed from: l */
    private static final int f7157l = 500;

    /* renamed from: m */
    @Nullable
    private static byte[] jsonData;

    /* renamed from: p */
    @Nullable
    private static HandlerJson handlerJson;

    /* renamed from: a */
    @NotNull
    @JvmField
    public final AtomicBoolean oldAAServiceMode;

    /* renamed from: b */
    @NotNull
    private final AtomicReference<String> backupJsonString;

    /* renamed from: c */
    @NotNull
    private final BroadcastReceiverLocalMessage f7160c;

    /* renamed from: d */
    @NotNull
    private final Gson gson;

    /* renamed from: e */
    @Nullable
    private String masterJsonString;

    /* renamed from: f */
    @NotNull
    public static final Companion Companion = new Companion(null);

    /* renamed from: g */
    @NotNull
    private static final AtomicBoolean f7153g = new AtomicBoolean(true);

    /* renamed from: h */
    @NotNull
    private static final AtomicReference<String> jsonString = new AtomicReference<>("");

    /* renamed from: i */
    private static final String f7154i = HandlerJson.class.getSimpleName();

    /* renamed from: j */
    @NotNull
    private static final RunnableSendUpdate f7155j = new RunnableSendUpdate();

    /* renamed from: k */
    @NotNull
    private static final CommonFuncs f7156k = new CommonFuncs();

    /* renamed from: n */
    @NotNull
    @JvmField
    public static AtomicLong f7158n = new AtomicLong(0);

    /* renamed from: o */
    private static boolean f7159o = true;

    /* renamed from: com.air.advantage.uart.p$a */
    public static final class BroadcastReceiverLocalMessage extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(@NotNull Context context, @NotNull Intent intent) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(intent, "intent");
            Timber.Forest forest = Timber.forest;
            forest.d("BroadcastReceiverLocalMessage triggered.", new Object[0]);
            String action = intent.getAction();
            if (action == null) {
                forest.d("Warning null intent.getAction", new Object[0]);
                return;
            }
            if (Intrinsics.areEqual(action, UartConstants.SEND_JSON_MESSAGE_REQUEST)) {
                String stringExtra = intent.getStringExtra("messageRequest");
                String stringExtra2 = intent.getStringExtra("messageParams");
                HandlerJson companion = HandlerJson.Companion.getInstance(context);
                if (companion.oldAAServiceMode.get() && !Intrinsics.areEqual(stringExtra, "setMySystem")) {
                    forest.d("Old AAservice - not processing - " + stringExtra, new Object[0]);
                    return;
                }
                HashMap<String, String> hashMap = new HashMap<>();
                if (Intrinsics.areEqual(stringExtra, "setLightToGroup") || Intrinsics.areEqual(stringExtra, "setLightToNewGroup") || Intrinsics.areEqual(stringExtra, "setLightGroupName") || Intrinsics.areEqual(stringExtra, "setLightNewGroupName") || Intrinsics.areEqual(stringExtra, "setThingToGroupThing") || Intrinsics.areEqual(stringExtra, "setThingToNewGroupThing") || Intrinsics.areEqual(stringExtra, "setGroupThingName") || Intrinsics.areEqual(stringExtra, "setNewGroupThingName") || Intrinsics.areEqual(stringExtra, "setFcmToken") || Intrinsics.areEqual(stringExtra, "setRemoveDevice")) {
                    hashMap = HandlerJson.f7156k.b(stringExtra2);
                    Intrinsics.checkNotNullExpressionValue(hashMap, "decodeParms(...)");
                } else if (stringExtra2 != null && stringExtra2.length() > 6) {
                    String substring = stringExtra2.substring(5);
                    Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
                    hashMap.put("json", substring);
                }
                try {
                    if (companion.q(context, stringExtra, hashMap, intent.getBooleanExtra("doSanitising", true)) != WebServer.NACK) {
                        return;
                    }
                    throw new NullPointerException("NACK thrown from an internal message - " + stringExtra + " " + hashMap);
                } catch (ExceptionUart e7) {
                    ExceptionUart exceptionUart = new ExceptionUart(e7.getMessage() + " from request " + stringExtra + " params " + hashMap);
                    exceptionUart.setStackTrace(e7.getStackTrace());
                    AppFeatures.Error(AppFeatures.instance, exceptionUart, null, 2, null);
                } catch (Exception e10) {
                    Exception exc = new Exception(e10.getMessage() + " from request " + stringExtra + " params " + hashMap);
                    exc.setStackTrace(e10.getStackTrace());
                    AppFeatures.Error(AppFeatures.instance, exc, null, 2, null);
                }
            }
        }
    }

    /* renamed from: com.air.advantage.uart.p$b */
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.p.b.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        /* renamed from: a */
        public final void destroy() {
            HandlerJson.handlerJson = null;
            getIsProcessing().set(true);
            HandlerJson.jsonString.set("");
            setJsonData(null);
            HandlerJson.f7158n.set(0L);
            HandlerJson.f7159o = true;
        }

        @NotNull
        /* renamed from: b */
        public final AtomicBoolean getIsProcessing() {
            return HandlerJson.f7153g;
        }

        @NotNull
        /* renamed from: c */
        public final HandlerJson getInstance(@Nullable Context context) {
            if (HandlerJson.handlerJson == null) {
                synchronized (HandlerJson.class) {
                    if (context == null) {
                        throw new NullPointerException("HandlerJson is not initialized need to call with context first");
                    }
                    if (HandlerJson.handlerJson == null) {
                        Companion companion = HandlerJson.Companion;
                        Context applicationContext = context.getApplicationContext();
                        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                        HandlerJson.handlerJson = new HandlerJson(applicationContext, null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            HandlerJson handlerJson = HandlerJson.handlerJson;
            Intrinsics.checkNotNull(handlerJson);
            return handlerJson;
        }

        @Nullable
        /* renamed from: d */
        public final byte[] getJsonData() {
            return HandlerJson.jsonData;
        }

        /* renamed from: e */
        public final void setJsonData(@Nullable byte[] bArr) {
            HandlerJson.jsonData = bArr;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private Companion() {
        }
    }

    /* renamed from: com.air.advantage.uart.p$c */
    private static final class RunnableSendUpdate implements Runnable {

        /* renamed from: a */
        @NotNull
        private final RunnableParseData runnableParseData = new RunnableParseData(true);

        @Override // java.lang.Runnable
        public void run() {
            ActivityMain companion = ActivityMain.Companion.getInstance();
            if (companion != null) {
                Companion companion2 = HandlerJson.Companion;
                if (companion2.getIsProcessing().get()) {
                    companion.getMainHandler().removeCallbacks(HandlerJson.f7155j);
                    companion.getMainHandler().postDelayed(HandlerJson.f7155j, 2000L);
                    return;
                }
                if (((String) HandlerJson.jsonString.get()).length() > 0) {
                    Object obj = HandlerJson.jsonString.get();
                    Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
                    byte[] bytes = ((String) obj).getBytes(Charsets.UTF_8);
                    Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
                    companion2.setJsonData(bytes);
                    Timber.forest.d("RunnableSendUpdate - running", new Object[0]);
                    RunnableParseData runnableParseData = this.runnableParseData;
                    byte[] jsonData = companion2.getJsonData();
                    Intrinsics.checkNotNull(jsonData);
                    runnableParseData.initialize(companion, jsonData);
                    try {
                        this.runnableParseData.run();
                    } catch (NullPointerException e7) {
                        NullPointerException nullPointerException = new NullPointerException(StringsKt__IndentKt.trimIndent("\n    runnableParseData error - " + e7.getMessage() + "\n    " + HandlerJson.jsonString.get() + "\n    "));
                        nullPointerException.setStackTrace(e7.getStackTrace());
                        throw nullPointerException;
                    }
                }
            }
        }
    }

    /* renamed from: com.air.advantage.uart.p$d */
    public /* synthetic */ class d {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ErrorCodes.values().length];
            try {
                iArr[ErrorCodes.AA123.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ErrorCodes.AA124.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ErrorCodes.AA125.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ErrorCodes.AA126.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ErrorCodes.AA127.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            a = iArr;
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 android.content.Context) A[MD:(android.content.Context):void (m)] (LINE:1) call: com.air.advantage.uart.p.<init>(android.content.Context):void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ HandlerJson(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    /* JADX WARN: Removed duplicated region for block: B:147:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0108  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void j(DataMaster dataMaster, ErrorCodes errorCodes) {
        ErrorCodes errorCodes2 = ErrorCodes.noError;
        HashMap hashMap = new HashMap();
        if (errorCodes != errorCodes2) {
            int i10 = d.a[errorCodes.ordinal()];
            if (i10 == 1) {
                hashMap.put("AA123", "unable to determine system type");
            } else if (i10 == 2) {
                hashMap.put("AA124", "unable to determine system type");
            } else if (i10 == 3) {
                hashMap.put("AA125", "unable to determine system type");
            } else if (i10 == 4) {
                hashMap.put("AA126", "unable to determine system type");
            } else if (i10 == 5) {
                hashMap.put("AA127", "unable to determine system type");
            }
        } else {
            errorCodes = errorCodes2;
        }
        ActivityMain.Companion companion = ActivityMain.Companion;
        String packageName = companion.getPackageName();
        if (packageName != null && StringsKt__StringsKt.startsWith$default(packageName, ActivityMain.EZONE, false, 2, null)) {
            String str = dataMaster.system.sysType;
            if (str != null && StringsKt__StringsKt.startsWith$default(str, "e-zone", false, 2, null)) {
            }
        } else {
            String packageName2 = companion.getPackageName();
            if (packageName2 != null && StringsKt__StringsKt.startsWith$default(packageName2, ActivityMain.ANYWAIR, false, 2, null)) {
                String str2 = dataMaster.system.sysType;
                if (str2 != null && StringsKt__StringsKt.startsWith$default(str2, DataAirconSystem.SYSTEM_TYPE_ANYWAIR, false, 2, null)) {
                }
            } else {
                String packageName3 = companion.getPackageName();
                if (packageName3 != null && StringsKt__StringsKt.startsWith$default(packageName3, "zone10", false, 2, null)) {
                    String str3 = dataMaster.system.sysType;
                    if (str3 != null && StringsKt__StringsKt.startsWith$default(str3, "zone10", false, 2, null)) {
                    }
                } else {
                    String packageName4 = companion.getPackageName();
                    if (packageName4 != null && StringsKt__StringsKt.startsWith$default(packageName4, ActivityMain.MYAIR4, false, 2, null)) {
                        String str4 = dataMaster.system.sysType;
                        if (str4 != null && StringsKt__StringsKt.startsWith$default(str4, "MyAir4", false, 2, null)) {
                        }
                    } else {
                        String packageName5 = companion.getPackageName();
                        if (packageName5 != null && StringsKt__StringsKt.startsWith$default(packageName5, ActivityMain.MYAIR5, false, 2, null)) {
                            String str5 = dataMaster.system.sysType;
                            if (!(str5 != null && StringsKt__StringsKt.startsWith$default(str5, DataAirconSystem.SYSTEM_TYPE_MYAIR5, false, 2, null)) || TabletInfo.isPic7SeriesTablet()) {
                                String str6 = dataMaster.system.sysType;
                                if (!(str6 == null || str6.length() == 0)) {
                                    hashMap.put("AA129", "incompatible app");
                                    int ordinal = errorCodes.ordinal();
                                    ErrorCodes errorCodes3 = ErrorCodes.AA129;
                                    if (ordinal > errorCodes3.ordinal()) {
                                        errorCodes = errorCodes3;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        String packageName6 = companion.getPackageName();
        if (packageName6 != null && StringsKt__StringsKt.startsWith$default(packageName6, ActivityMain.EZONE, false, 2, null)) {
            Collection<DataAirconSystem> values = dataMaster.aircons.values();
            Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
            ArrayList arrayList = new ArrayList();
            for (DataAirconSystem dataAirconSystem : values) {
                if (dataAirconSystem != null) {
                    arrayList.add(dataAirconSystem);
                }
            }
            Iterator it = arrayList.iterator();
            boolean z7 = false;
            while (it.hasNext()) {
                if (((DataAirconSystem) it.next()).isUnitControlOnOffOnlyDb()) {
                    z7 = true;
                }
            }
            if (z7) {
                hashMap.put("AA98", "MyPlace or e-zone TSP connected to CB with on-off-only DB");
                int ordinal2 = errorCodes.ordinal();
                ErrorCodes errorCodes4 = ErrorCodes.AA98;
                if (ordinal2 > errorCodes4.ordinal()) {
                    errorCodes = errorCodes4;
                }
            }
        } else {
            String packageName7 = companion.getPackageName();
            if (!(packageName7 != null && StringsKt__StringsKt.startsWith$default(packageName7, ActivityMain.MYAIR5, false, 2, null))) {
                String packageName8 = companion.getPackageName();
                if (packageName8 != null && StringsKt__StringsKt.startsWith$default(packageName8, "zone10", false, 2, null)) {
                    DataAirconSystem dataAirconSystem2 = dataMaster.aircons.get(FragmentEnergyMonitoring.DEFAULT_AIRCON_KEY);
                    if (dataAirconSystem2 != null) {
                        if (dataAirconSystem2.isUnitControlFullControlDb()) {
                            hashMap.put("AA101", "zone10e TSP connected to CB with full control DB");
                            int ordinal3 = errorCodes.ordinal();
                            ErrorCodes errorCodes5 = ErrorCodes.AA101;
                            if (ordinal3 > errorCodes5.ordinal()) {
                                errorCodes = errorCodes5;
                            }
                        }
                        TreeMap<String, DataZone> treeMap = dataAirconSystem2.zones;
                        Intrinsics.checkNotNull(treeMap);
                        if (treeMap.size() > 0) {
                            TreeMap<String, DataZone> treeMap2 = dataAirconSystem2.zones;
                            Intrinsics.checkNotNull(treeMap2);
                            Collection<DataZone> values2 = treeMap2.values();
                            Intrinsics.checkNotNullExpressionValue(values2, "<get-values>(...)");
                            ArrayList<DataZone> arrayList2 = new ArrayList();
                            for (DataZone dataZone : values2) {
                                if (dataZone != null) {
                                    arrayList2.add(dataZone);
                                }
                            }
                            boolean z10 = false;
                            for (DataZone dataZone2 : arrayList2) {
                                Integer num = dataZone2.type;
                                if (num == null) {
                                    Timber.forest.d("checkAndProcessTspErrors - warning found zone with null type!", new Object[0]);
                                } else if (num != null && num.intValue() == 2) {
                                    dataZone2.type = 1;
                                    z10 = true;
                                }
                                if (z10) {
                                    hashMap.put("AA104", "zone10e detect WSW or WSRF");
                                    int ordinal4 = errorCodes.ordinal();
                                    ErrorCodes errorCodes6 = ErrorCodes.AA104;
                                    if (ordinal4 > errorCodes6.ordinal()) {
                                        errorCodes = errorCodes6;
                                    }
                                }
                            }
                        } else {
                            Timber.forest.d("checkAndProcessTspErrors - warning zone10e system has 0 number of zone!", new Object[0]);
                            AppFeatures.logError(AppFeatures.instance, new NullPointerException("warning zone10e system has 0 number of zone!"), null, 2, null);
                        }
                    } else {
                        Timber.forest.d("checkAndProcessTspErrors - first aircon (ac1) is null on a zone10e system!", new Object[0]);
                        AppFeatures.logError(AppFeatures.instance, new NullPointerException("first aircon (ac1) is null on a zone10e system!"), null, 2, null);
                    }
                }
            }
        }
        if (!AppFeatures.isAnywair() && dataMaster.oneAirconOnly && (dataMaster.aircons.size() > 1 || dataMaster.multipleAirconDetectedOnOneAirconOnlySystem)) {
            hashMap.put("AA107", "e-zone or zone10e detected more than one CB connected");
            int ordinal5 = errorCodes.ordinal();
            ErrorCodes errorCodes7 = ErrorCodes.AA107;
            if (ordinal5 > errorCodes7.ordinal()) {
                errorCodes = errorCodes7;
            }
        }
        if (!dataMaster.listOfRM2WithInvalidDipSetting.isEmpty()) {
            hashMap.put("AA121", "Invalid RM2 dip setting detected");
            int ordinal6 = errorCodes.ordinal();
            ErrorCodes errorCodes8 = ErrorCodes.AA121;
            if (ordinal6 > errorCodes8.ordinal()) {
                errorCodes = errorCodes8;
            }
        }
        if (!dataMaster.listOfNonConfiguredRM2.isEmpty()) {
            hashMap.put("AA122", "one or more RM2 with all DIPs set to 0 detected");
            int ordinal7 = errorCodes.ordinal();
            ErrorCodes errorCodes9 = ErrorCodes.AA122;
            if (ordinal7 > errorCodes9.ordinal()) {
                errorCodes = errorCodes9;
            }
        }
        if (!dataMaster.listOfDMWithInvalidDipSetting.isEmpty()) {
            Timber.forest.d("DBG-DM invalid Dip not empty", new Object[0]);
            hashMap.put("AA132", "Invalid DM dip setting detected");
            int ordinal8 = errorCodes.ordinal();
            ErrorCodes errorCodes10 = ErrorCodes.AA132;
            if (ordinal8 > errorCodes10.ordinal()) {
                errorCodes = errorCodes10;
            }
        }
        if (!dataMaster.listOfNonConfiguredDM.isEmpty()) {
            hashMap.put("AA133", "one or more DM with all DIPs set to 0 detected");
            int ordinal9 = errorCodes.ordinal();
            ErrorCodes errorCodes11 = ErrorCodes.AA133;
            if (ordinal9 > errorCodes11.ordinal()) {
                errorCodes = errorCodes11;
            }
        }
        HandlerHue companion2 = HandlerHue.Companion.getInstance();
        if (companion2.r()) {
            hashMap.put("AA134", "Unable to communicate with Hue Bridge - Wifi disabled");
            int ordinal10 = errorCodes.ordinal();
            ErrorCodes errorCodes12 = ErrorCodes.AA134;
            if (ordinal10 > errorCodes12.ordinal()) {
                errorCodes = errorCodes12;
            }
        }
        if (companion2.q()) {
            hashMap.put("AA135", "Unable to communicate with Hue Bridge - Can't connect to Hue Bridge");
            int ordinal11 = errorCodes.ordinal();
            ErrorCodes errorCodes13 = ErrorCodes.AA135;
            if (ordinal11 > errorCodes13.ordinal()) {
                errorCodes = errorCodes13;
            }
        }
        DataSystem dataSystem = dataMaster.system;
        dataSystem.tspErrorCode = errorCodes;
        HashMap<String, String> hashMap2 = dataSystem.allTspErrorCodes;
        Intrinsics.checkNotNull(hashMap2);
        hashMap2.clear();
        for (String str7 : hashMap.keySet()) {
            AbstractMap abstractMap = dataMaster.system.allTspErrorCodes;
            Intrinsics.checkNotNull(abstractMap);
            Intrinsics.checkNotNull(str7);
            abstractMap.put(str7, hashMap.get(str7));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0010  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean k(DataSystem dataSystem) {
        boolean z7;
        Boolean bool = dataSystem.hasLights;
        if (bool != null) {
            Intrinsics.checkNotNull(bool);
            z7 = bool.booleanValue();
        }
        Boolean bool2 = dataSystem.hasThingsLight;
        if (bool2 != null) {
            Intrinsics.checkNotNull(bool2);
            if (bool2.booleanValue()) {
                return true;
            }
        }
        return z7;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0010  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean l(DataSystem dataSystem) {
        boolean z7;
        Boolean bool = dataSystem.hasThingsBOG;
        if (bool != null) {
            Intrinsics.checkNotNull(bool);
            z7 = bool.booleanValue();
        }
        Boolean bool2 = dataSystem.hasSensors;
        if (bool2 != null) {
            Intrinsics.checkNotNull(bool2);
            if (bool2.booleanValue()) {
                z7 = true;
            }
        }
        Boolean bool3 = dataSystem.hasLocks;
        if (bool3 != null) {
            Intrinsics.checkNotNull(bool3);
            if (bool3.booleanValue()) {
                z7 = true;
            }
        }
        Boolean bool4 = dataSystem.hasAircons;
        if (bool4 != null) {
            Intrinsics.checkNotNull(bool4);
            if (bool4.booleanValue()) {
                Boolean bool5 = dataSystem.hasLights;
                if (bool5 != null) {
                    Intrinsics.checkNotNull(bool5);
                    if (bool5.booleanValue()) {
                        z7 = true;
                    }
                }
                Boolean bool6 = dataSystem.hasThingsLight;
                if (bool6 != null) {
                    Intrinsics.checkNotNull(bool6);
                    if (bool6.booleanValue()) {
                        return true;
                    }
                }
            }
        }
        return z7;
    }

    @JvmStatic
    public static final void m() {
        Companion.destroy();
    }

    private final boolean n(Context context) {
        synchronized (MyMasterData.class) {
            Collection<DataAirconSystem> values = MyMasterData.Companion.getDataMaster(context).aircons.values();
            Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
            ArrayList arrayList = new ArrayList();
            for (DataAirconSystem dataAirconSystem : values) {
                if (dataAirconSystem != null) {
                    arrayList.add(dataAirconSystem);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                DataAirconSystem.CodeStatus codeStatus = ((DataAirconSystem) it.next()).info.activationCodeStatus;
                if (codeStatus != null && codeStatus == DataAirconSystem.CodeStatus.expired) {
                    return true;
                }
            }
            Unit unit = Unit.INSTANCE;
            return false;
        }
    }

    /* renamed from: r */
    private final void processBackup(Context context, DataMaster dataMaster) {
        String str = this.backupJsonString.get();
        Intrinsics.checkNotNull(str);
        if (str.length() == 0) {
            return;
        }
        DataMaster dataMaster2 = new DataMaster();
        BackupDataFunctions backupDataFunctions = new BackupDataFunctions();
        backupDataFunctions.processBackupJson(dataMaster2, str);
        Iterator<String> it = backupDataFunctions.getMessages().iterator();
        while (it.hasNext()) {
            String next = it.next();
            Intrinsics.checkNotNull(next);
            if (StringsKt__StringsKt.startsWith$default(next, "?", false, 2, null)) {
                String substring = next.substring(0, StringsKt__StringsKt.p3(next, "?", 0, false, 6, null));
                Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
                String substring2 = next.substring(StringsKt__StringsKt.p3(next, "?", 0, false, 6, null) + 1);
                Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String).substring(startIndex)");
                Intent intent = new Intent(UartConstants.SEND_JSON_MESSAGE_REQUEST);
                intent.putExtra("messageRequest", substring);
                intent.putExtra("messageParams", substring2);
                intent.putExtra("doSanitising", false);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        }
    }

    private final int v(Context context, String str) {
        Object obj;
        boolean z7;
        try {
            obj = this.gson.fromJson(str, DataMaster.class);
        } catch (JsonIOException e7) {
            AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
            obj = null;
        }
        if (obj == null) {
            return WebServer.NACK;
        }
        DataMaster dataMaster = (DataMaster) obj;
        dataMaster.clearDataForBackup();
        dataMaster.system.backupId = null;
        HandlerAircon.Companion.getInstance().h0(dataMaster);
        synchronized (MyMasterData.class) {
            DataMaster dataMaster2 = MyMasterData.Companion.getDataMaster(context);
            HandlerLights companion = HandlerLights.Companion.getInstance();
            TreeMap<String, DataLight> treeMap = ((DataMaster) obj).myLights.lights;
            Intrinsics.checkNotNull(treeMap);
            for (String str2 : treeMap.keySet()) {
                DataLight lightData = ((DataMaster) obj).myLights.getLightData(str2);
                DataLight lightData2 = dataMaster2.myLights.getLightData(str2);
                if (lightData2 != null) {
                    DataLight.update$default(lightData2, null, lightData, null, false, 8, null);
                }
            }
            TreeMap<String, DataGroup> treeMap2 = ((DataMaster) obj).myLights.groups;
            Intrinsics.checkNotNull(treeMap2);
            for (String str3 : treeMap2.keySet()) {
                TreeMap<String, DataGroup> treeMap3 = ((DataMaster) obj).myLights.groups;
                Intrinsics.checkNotNull(treeMap3);
                DataGroup dataGroup = treeMap3.get(str3);
                if (dataGroup != null && dataGroup.id == null) {
                    dataGroup.id = str3;
                    Timber.forest.d("light dataGroup id fixed : " + str3, new Object[0]);
                }
            }
            ArrayList<String> arrayList = dataMaster2.myLights.groupsOrder;
            Intrinsics.checkNotNull(arrayList);
            arrayList.clear();
            ArrayList<String> arrayList2 = dataMaster2.myLights.groupsOrder;
            Intrinsics.checkNotNull(arrayList2);
            ArrayList<String> arrayList3 = ((DataMaster) obj).myLights.groupsOrder;
            Intrinsics.checkNotNull(arrayList3);
            arrayList2.addAll(arrayList3);
            TreeMap<String, DataGroup> treeMap4 = dataMaster2.myLights.groups;
            Intrinsics.checkNotNull(treeMap4);
            treeMap4.clear();
            TreeMap<String, DataGroup> treeMap5 = dataMaster2.myLights.groups;
            Intrinsics.checkNotNull(treeMap5);
            TreeMap<String, DataGroup> treeMap6 = ((DataMaster) obj).myLights.groups;
            Intrinsics.checkNotNull(treeMap6);
            treeMap5.putAll(treeMap6);
            ArrayList arrayList4 = new ArrayList();
            TreeMap<String, DataGroup> treeMap7 = dataMaster2.myLights.groups;
            Intrinsics.checkNotNull(treeMap7);
            Collection<DataGroup> values = treeMap7.values();
            Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
            ArrayList arrayList5 = new ArrayList();
            for (DataGroup dataGroup2 : values) {
                if (dataGroup2 != null) {
                    arrayList5.add(dataGroup2);
                }
            }
            Iterator it = arrayList5.iterator();
            while (it.hasNext()) {
                Iterator<String> it2 = ((DataGroup) it.next()).lightsOrder.iterator();
                while (it2.hasNext()) {
                    String next = it2.next();
                    TreeMap<String, DataLight> treeMap8 = dataMaster2.myLights.lights;
                    Intrinsics.checkNotNull(treeMap8);
                    if (!treeMap8.containsKey(next)) {
                        arrayList4.add(next);
                    }
                }
            }
            Iterator it3 = arrayList4.iterator();
            while (it3.hasNext()) {
                String str4 = (String) it3.next();
                TreeMap<String, DataGroup> treeMap9 = dataMaster2.myLights.groups;
                Intrinsics.checkNotNull(treeMap9);
                Collection<DataGroup> values2 = treeMap9.values();
                Intrinsics.checkNotNullExpressionValue(values2, "<get-values>(...)");
                ArrayList arrayList6 = new ArrayList();
                for (DataGroup dataGroup3 : values2) {
                    if (dataGroup3 != null) {
                        arrayList6.add(dataGroup3);
                    }
                }
                Iterator it4 = arrayList6.iterator();
                while (it4.hasNext()) {
                    ((DataGroup) it4.next()).lightsOrder.remove(str4);
                }
            }
            ArrayList arrayList7 = new ArrayList();
            TreeMap<String, DataLight> treeMap10 = dataMaster2.myLights.lights;
            Intrinsics.checkNotNull(treeMap10);
            Collection<DataLight> values3 = treeMap10.values();
            Intrinsics.checkNotNullExpressionValue(values3, "<get-values>(...)");
            ArrayList arrayList8 = new ArrayList();
            for (DataLight dataLight : values3) {
                if (dataLight != null) {
                    arrayList8.add(dataLight);
                }
            }
            Iterator it5 = arrayList8.iterator();
            while (true) {
                boolean z10 = true;
                if (!it5.hasNext()) {
                    break;
                }
                DataLight dataLight2 = (DataLight) it5.next();
                TreeMap<String, DataGroup> treeMap11 = dataMaster2.myLights.groups;
                Intrinsics.checkNotNull(treeMap11);
                Collection<DataGroup> values4 = treeMap11.values();
                Intrinsics.checkNotNullExpressionValue(values4, "<get-values>(...)");
                ArrayList arrayList9 = new ArrayList();
                for (DataGroup dataGroup4 : values4) {
                    if (dataGroup4 != null) {
                        arrayList9.add(dataGroup4);
                    }
                }
                Iterator it6 = arrayList9.iterator();
                while (true) {
                    if (!it6.hasNext()) {
                        z10 = false;
                        break;
                    }
                    if (((DataGroup) it6.next()).lightsOrder.contains(dataLight2.id)) {
                        break;
                    }
                }
                if (!z10) {
                    arrayList7.add(dataLight2.id);
                }
            }
            Iterator it7 = arrayList7.iterator();
            while (it7.hasNext()) {
                companion.isValidLight(dataMaster2, (String) it7.next());
            }
            ArrayList arrayList10 = new ArrayList();
            TreeMap<String, DataLight> treeMap12 = dataMaster2.myLights.lights;
            Intrinsics.checkNotNull(treeMap12);
            Collection<DataLight> values5 = treeMap12.values();
            Intrinsics.checkNotNullExpressionValue(values5, "<get-values>(...)");
            ArrayList<DataLight> arrayList11 = new ArrayList();
            for (DataLight dataLight3 : values5) {
                if (dataLight3 != null) {
                    arrayList11.add(dataLight3);
                }
            }
            for (DataLight dataLight4 : arrayList11) {
                String str5 = dataLight4.moduleType;
                if (str5 != null && Intrinsics.areEqual(str5, DataLight.MODULE_TYPE_STRING_HUE)) {
                    arrayList10.add(dataLight4.id);
                }
            }
            Iterator it8 = arrayList10.iterator();
            while (it8.hasNext()) {
                String str6 = (String) it8.next();
                TreeMap<String, DataLight> treeMap13 = dataMaster2.myLights.lights;
                Intrinsics.checkNotNull(treeMap13);
                treeMap13.remove(str6);
            }
            if (((DataMaster) obj).myLights.system != null) {
                DataLightsSystem dataLightsSystem = dataMaster2.myLights.system;
                Intrinsics.checkNotNull(dataLightsSystem);
                DataLightsSystem.update$default(dataLightsSystem, ((DataMaster) obj).myLights.system, null, false, 4, null);
            }
            companion.updateLightJson(context, dataMaster2);
            TreeMap<String, DataMyThing> treeMap14 = ((DataMaster) obj).myThings.things;
            Intrinsics.checkNotNull(treeMap14);
            for (String str7 : treeMap14.keySet()) {
                DataMyThing thingData = ((DataMaster) obj).myThings.getThingData(str7);
                DataMyThing thingData2 = dataMaster2.myThings.getThingData(str7);
                if (thingData2 != null) {
                    DataMyThing.update$default(thingData2, null, thingData, null, false, 8, null);
                }
            }
            TreeMap<String, DataGroupThing> treeMap15 = ((DataMaster) obj).myThings.groups;
            Intrinsics.checkNotNull(treeMap15);
            for (String str8 : treeMap15.keySet()) {
                TreeMap<String, DataGroupThing> treeMap16 = ((DataMaster) obj).myThings.groups;
                Intrinsics.checkNotNull(treeMap16);
                DataGroupThing dataGroupThing = treeMap16.get(str8);
                if (dataGroupThing != null && dataGroupThing.id == null) {
                    dataGroupThing.id = str8;
                    Timber.forest.d("thing dataGroup id fixed : " + str8, new Object[0]);
                }
            }
            ArrayList<String> arrayList12 = dataMaster2.myThings.groupsOrder;
            Intrinsics.checkNotNull(arrayList12);
            arrayList12.clear();
            ArrayList<String> arrayList13 = dataMaster2.myThings.groupsOrder;
            Intrinsics.checkNotNull(arrayList13);
            ArrayList<String> arrayList14 = ((DataMaster) obj).myThings.groupsOrder;
            Intrinsics.checkNotNull(arrayList14);
            arrayList13.addAll(arrayList14);
            TreeMap<String, DataGroupThing> treeMap17 = dataMaster2.myThings.groups;
            Intrinsics.checkNotNull(treeMap17);
            treeMap17.clear();
            TreeMap<String, DataGroupThing> treeMap18 = dataMaster2.myThings.groups;
            Intrinsics.checkNotNull(treeMap18);
            TreeMap<String, DataGroupThing> treeMap19 = ((DataMaster) obj).myThings.groups;
            Intrinsics.checkNotNull(treeMap19);
            treeMap18.putAll(treeMap19);
            ArrayList arrayList15 = new ArrayList();
            TreeMap<String, DataGroupThing> treeMap20 = dataMaster2.myThings.groups;
            Intrinsics.checkNotNull(treeMap20);
            Collection<DataGroupThing> values6 = treeMap20.values();
            Intrinsics.checkNotNullExpressionValue(values6, "<get-values>(...)");
            ArrayList arrayList16 = new ArrayList();
            for (DataGroupThing dataGroupThing2 : values6) {
                if (dataGroupThing2 != null) {
                    arrayList16.add(dataGroupThing2);
                }
            }
            Iterator it9 = arrayList16.iterator();
            while (it9.hasNext()) {
                Iterator<String> it10 = ((DataGroupThing) it9.next()).thingsOrder.iterator();
                while (it10.hasNext()) {
                    String next2 = it10.next();
                    TreeMap<String, DataMyThing> treeMap21 = dataMaster2.myThings.things;
                    Intrinsics.checkNotNull(treeMap21);
                    if (!treeMap21.containsKey(next2)) {
                        arrayList15.add(next2);
                    }
                }
            }
            Iterator it11 = arrayList15.iterator();
            while (it11.hasNext()) {
                String str9 = (String) it11.next();
                TreeMap<String, DataGroupThing> treeMap22 = dataMaster2.myThings.groups;
                Intrinsics.checkNotNull(treeMap22);
                Collection<DataGroupThing> values7 = treeMap22.values();
                Intrinsics.checkNotNullExpressionValue(values7, "<get-values>(...)");
                ArrayList arrayList17 = new ArrayList();
                for (DataGroupThing dataGroupThing3 : values7) {
                    if (dataGroupThing3 != null) {
                        arrayList17.add(dataGroupThing3);
                    }
                }
                Iterator it12 = arrayList17.iterator();
                while (it12.hasNext()) {
                    ((DataGroupThing) it12.next()).thingsOrder.remove(str9);
                }
            }
            ArrayList arrayList18 = new ArrayList();
            TreeMap<String, DataMyThing> treeMap23 = dataMaster2.myThings.things;
            Intrinsics.checkNotNull(treeMap23);
            Collection<DataMyThing> values8 = treeMap23.values();
            Intrinsics.checkNotNullExpressionValue(values8, "<get-values>(...)");
            ArrayList<DataMyThing> arrayList19 = new ArrayList();
            for (DataMyThing dataMyThing : values8) {
                if (dataMyThing != null) {
                    arrayList19.add(dataMyThing);
                }
            }
            for (DataMyThing dataMyThing2 : arrayList19) {
                TreeMap<String, DataGroupThing> treeMap24 = dataMaster2.myThings.groups;
                Intrinsics.checkNotNull(treeMap24);
                Collection<DataGroupThing> values9 = treeMap24.values();
                Intrinsics.checkNotNullExpressionValue(values9, "<get-values>(...)");
                ArrayList arrayList20 = new ArrayList();
                for (DataGroupThing dataGroupThing4 : values9) {
                    if (dataGroupThing4 != null) {
                        arrayList20.add(dataGroupThing4);
                    }
                }
                Iterator it13 = arrayList20.iterator();
                while (true) {
                    if (!it13.hasNext()) {
                        z7 = false;
                        break;
                    }
                    if (((DataGroupThing) it13.next()).thingsOrder.contains(dataMyThing2.id)) {
                        z7 = true;
                        break;
                    }
                }
                if (!z7) {
                    arrayList18.add(dataMyThing2.id);
                }
            }
            Iterator it14 = arrayList18.iterator();
            while (it14.hasNext()) {
                companion.isValidThing(dataMaster2, (String) it14.next());
            }
            companion.updateThingJson(context, dataMaster2);
            TreeMap<String, DataScene> treeMap25 = ((DataMaster) obj).myScenes.scenes;
            Intrinsics.checkNotNull(treeMap25);
            Set<String> keySet = treeMap25.keySet();
            Intrinsics.checkNotNullExpressionValue(keySet, "<get-keys>(...)");
            ArrayList<String> arrayList21 = new ArrayList();
            for (String str10 : keySet) {
                if (str10 != null) {
                    arrayList21.add(str10);
                }
            }
            for (String str11 : arrayList21) {
                TreeMap<String, DataScene> treeMap26 = ((DataMaster) obj).myScenes.scenes;
                Intrinsics.checkNotNull(treeMap26);
                DataScene dataScene = treeMap26.get(str11);
                if (dataScene != null && dataScene.id == null) {
                    Intrinsics.checkNotNull(str11);
                    dataScene.id = str11;
                    Timber.forest.d("scene id fixed : " + str11, new Object[0]);
                }
            }
            TreeMap<String, DataScene> treeMap27 = dataMaster2.myScenes.scenes;
            Intrinsics.checkNotNull(treeMap27);
            treeMap27.clear();
            ArrayList<String> arrayList22 = dataMaster2.myScenes.scenesOrder;
            Intrinsics.checkNotNull(arrayList22);
            arrayList22.clear();
            ArrayList<String> arrayList23 = ((DataMaster) obj).myScenes.scenesOrder;
            Intrinsics.checkNotNull(arrayList23);
            Iterator<String> it15 = arrayList23.iterator();
            while (it15.hasNext()) {
                companion.setLightScene(context, this.gson.toJson(((DataMaster) obj).myScenes.getScene(it15.next())), false);
            }
            DataScene dataScene2 = DataMyScene.SCENE_MY_WELCOME;
            String str12 = dataScene2.id;
            String str13 = dataScene2.name;
            Intrinsics.checkNotNull(str13);
            DataScene dataScene3 = new DataScene(str12, str13, "");
            dataScene3.aircons = new HashMap<>();
            dataScene3.lights = new HashMap<>();
            TreeMap<String, DataLight> treeMap28 = dataMaster2.myLights.lights;
            Intrinsics.checkNotNull(treeMap28);
            for (DataLight dataLight5 : treeMap28.values()) {
                DataLight dataLight6 = new DataLight();
                DataLight.update$default(dataLight6, context, dataLight5, null, false, 8, null);
                dataLight6.state = LightState.on;
                HashMap<String, DataLight> hashMap = dataScene3.lights;
                Intrinsics.checkNotNull(hashMap);
                hashMap.put(dataLight6.id, dataLight6);
            }
            dataScene3.monitors = new HashMap<>();
            companion.setLightScene(context, this.gson.toJson(dataScene3), false);
            DataScene dataScene4 = DataMyScene.SCENE_MY_GOODBYE;
            String str14 = dataScene4.id;
            String str15 = dataScene4.name;
            Intrinsics.checkNotNull(str15);
            DataScene dataScene5 = new DataScene(str14, str15, "");
            dataScene5.aircons = new HashMap<>();
            dataScene5.lights = new HashMap<>();
            TreeMap<String, DataLight> treeMap29 = dataMaster2.myLights.lights;
            Intrinsics.checkNotNull(treeMap29);
            for (Iterator<DataLight> it16 = treeMap29.values().iterator(); it16.hasNext(); it16 = it16) {
                DataLight next3 = it16.next();
                DataLight dataLight7 = new DataLight();
                DataLight.update$default(dataLight7, context, next3, null, false, 8, null);
                dataLight7.state = LightState.off;
                HashMap<String, DataLight> hashMap2 = dataScene5.lights;
                Intrinsics.checkNotNull(hashMap2);
                hashMap2.put(dataLight7.id, dataLight7);
            }
            dataScene5.monitors = new HashMap<>();
            companion.setLightScene(context, this.gson.toJson(dataScene5), false);
            DataScene dataScene6 = DataMyScene.SCENE_MY_SUNSET;
            String str16 = dataScene6.id;
            String str17 = dataScene6.name;
            Intrinsics.checkNotNull(str17);
            DataScene dataScene7 = new DataScene(str16, str17, "");
            dataScene7.aircons = new HashMap<>();
            dataScene7.lights = new HashMap<>();
            TreeMap<String, DataLight> treeMap30 = dataMaster2.myLights.lights;
            Intrinsics.checkNotNull(treeMap30);
            for (Iterator<DataLight> it17 = treeMap30.values().iterator(); it17.hasNext(); it17 = it17) {
                DataLight next4 = it17.next();
                DataLight dataLight8 = new DataLight();
                DataLight.update$default(dataLight8, context, next4, null, false, 8, null);
                dataLight8.state = LightState.on;
                HashMap<String, DataLight> hashMap3 = dataScene7.lights;
                Intrinsics.checkNotNull(hashMap3);
                hashMap3.put(dataLight8.id, dataLight8);
            }
            dataScene7.monitors = new HashMap<>();
            companion.setLightScene(context, this.gson.toJson(dataScene7), false);
            HashMap<String, DataMonitor> hashMap4 = ((DataMaster) obj).myMonitors.monitors;
            Intrinsics.checkNotNull(hashMap4);
            for (String str18 : hashMap4.keySet()) {
                HashMap<String, DataMonitor> hashMap5 = ((DataMaster) obj).myMonitors.monitors;
                Intrinsics.checkNotNull(hashMap5);
                DataMonitor dataMonitor = hashMap5.get(str18);
                if (dataMonitor != null && dataMonitor.id == null) {
                    dataMonitor.id = str18;
                    Timber.forest.d("monitor id fixed : " + str18, new Object[0]);
                }
            }
            HandlerMonitor b10 = HandlerMonitor.Companion.b();
            ArrayList<String> arrayList24 = dataMaster2.myMonitors.monitorsOrder;
            Intrinsics.checkNotNull(arrayList24);
            arrayList24.clear();
            HashMap<String, DataMonitor> hashMap6 = dataMaster2.myMonitors.monitors;
            Intrinsics.checkNotNull(hashMap6);
            hashMap6.clear();
            ArrayList<String> arrayList25 = ((DataMaster) obj).myMonitors.monitorsOrder;
            Intrinsics.checkNotNull(arrayList25);
            Iterator<String> it18 = arrayList25.iterator();
            while (it18.hasNext()) {
                b10.setMonitor(context, this.gson.toJson(((DataMaster) obj).myMonitors.getMonitor(it18.next())), Boolean.FALSE);
            }
            Unit unit = Unit.INSTANCE;
        }
        return WebServer.ACK;
    }

    private final ErrorCodes y(DataMaster dataMaster) {
        DataAirconSystem dataAirconSystem;
        if (TabletInfo.isPic7SeriesTablet() && (dataAirconSystem = dataMaster.aircons.get(FragmentEnergyMonitoring.DEFAULT_AIRCON_KEY)) != null) {
            DataAirconInfo dataAirconInfo = dataAirconSystem.info;
            Integer num = dataAirconInfo.unitType;
            if (num == null) {
                dataMaster.system.sysType = "";
                return ErrorCodes.AA123;
            }
            if (dataAirconInfo.cbFWRevMajor == null || dataAirconInfo.cbFWRevMinor == null) {
                dataMaster.system.sysType = "";
                return ErrorCodes.AA125;
            }
            if (dataAirconInfo.cbType == null) {
                dataMaster.system.sysType = "";
                return ErrorCodes.AA124;
            }
            if (num.intValue() == 0) {
                boolean isAnywair = AppFeatures.isAnywair();
                DataSystem dataSystem = dataMaster.system;
                if (isAnywair) {
                    dataSystem.sysType = DataAirconSystem.SYSTEM_TYPE_ANYWAIR;
                } else {
                    dataSystem.sysType = "e-zone";
                }
            } else if (num.intValue() == 26) {
                Integer num2 = dataAirconSystem.info.cbFWRevMajor;
                if (num2 != null && num2.intValue() == 6) {
                    dataMaster.system.sysType = "zone10e";
                } else {
                    Integer num3 = dataAirconSystem.info.cbFWRevMajor;
                    if (num3 == null || num3.intValue() != 7) {
                        dataMaster.system.sysType = "";
                        return ErrorCodes.AA126;
                    }
                    Integer num4 = dataAirconSystem.info.cbType;
                    if (num4 != null && num4.intValue() == 1) {
                        dataMaster.system.sysType = "vams";
                    } else {
                        Integer num5 = dataAirconSystem.info.cbType;
                        if (num5 == null || num5.intValue() != 2) {
                            dataMaster.system.sysType = "";
                            return ErrorCodes.AA124;
                        }
                        dataMaster.system.sysType = "CB ZL";
                    }
                }
            } else if (num.intValue() == 1 || (num.intValue() >= 4 && num.intValue() <= 49)) {
                if (AppFeatures.isAnywair()) {
                    dataMaster.system.sysType = DataAirconSystem.SYSTEM_TYPE_ANYWAIR;
                } else {
                    dataMaster.system.sysType = "e-zone";
                }
            } else {
                if (num.intValue() < 50 || num.intValue() > 60) {
                    dataMaster.system.sysType = "";
                    return ErrorCodes.AA127;
                }
                dataMaster.system.sysType = "zone10e";
            }
        }
        return ErrorCodes.noError;
    }

    public final void o(@Nullable Context context, @Nullable String str) {
    }

    public final void p(@NotNull Context context, @NotNull DataMaster masterData) {
        String str;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        String str2 = this.backupJsonString.get();
        Intrinsics.checkNotNull(str2);
        if (str2.length() == 0) {
            return;
        }
        SharedPreferencesStore.Companion.setBackupHasRun(context, true);
        DataMaster dataMaster = new DataMaster();
        new BackupDataFunctions().c(dataMaster, str2);
        String str3 = "json=" + dataMaster.system.generateJSONString();
        Intent intent = new Intent(UartConstants.SEND_JSON_MESSAGE_REQUEST);
        intent.putExtra("messageRequest", "setMySystem");
        intent.putExtra("messageParams", str3);
        intent.putExtra("doSanitising", false);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        Collection<DataAirconSystem> values = dataMaster.aircons.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        ArrayList<DataAirconSystem> arrayList = new ArrayList();
        for (DataAirconSystem dataAirconSystem : values) {
            if (dataAirconSystem != null) {
                arrayList.add(dataAirconSystem);
            }
        }
        for (DataAirconSystem dataAirconSystem2 : arrayList) {
            for (String str4 : masterData.aircons.keySet()) {
                DataAirconSystem dataAirconSystem3 = masterData.aircons.get(str4);
                Intrinsics.checkNotNull(dataAirconSystem3);
                if (Intrinsics.areEqual(dataAirconSystem3.info.uid, dataAirconSystem2.info.uid)) {
                    String str5 = "json={\"" + str4 + "\":" + this.gson.toJson(dataAirconSystem2) + "}";
                    Intent intent2 = new Intent(UartConstants.SEND_JSON_MESSAGE_REQUEST);
                    intent2.putExtra("messageRequest", "setAircon");
                    intent2.putExtra("messageParams", str5);
                    intent2.putExtra("doSanitising", true);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);
                }
            }
        }
        Iterator<String> it = dataMaster.snapshots.keySet().iterator();
        while (it.hasNext()) {
            SnapShot snapShot = dataMaster.snapshots.get(it.next());
            TreeMap<String, DataAirconSystem> treeMap = new TreeMap<>();
            Intrinsics.checkNotNull(snapShot);
            TreeMap<String, DataAirconSystem> treeMap2 = snapShot.aircons;
            Intrinsics.checkNotNull(treeMap2);
            for (String str6 : treeMap2.keySet()) {
                TreeMap<String, DataAirconSystem> treeMap3 = snapShot.aircons;
                Intrinsics.checkNotNull(treeMap3);
                DataAirconSystem dataAirconSystem4 = treeMap3.get(str6);
                Intrinsics.checkNotNull(dataAirconSystem4);
                String str7 = dataAirconSystem4.info.uid;
                if (str7 != null) {
                    DataAirconSystem dataAirconSystem5 = new DataAirconSystem(str7);
                    DataAirconSystem airconByUid = masterData.getAirconByUid(str7);
                    if (airconByUid != null) {
                        DataAirconSystem.update$default(dataAirconSystem5, str6, airconByUid, null, null, false, 16, null);
                        dataAirconSystem5.updateForSnapshot(dataAirconSystem4);
                        DataAirconSystem dataAirconSystem6 = dataMaster.aircons.get(str7);
                        if (dataAirconSystem6 != null && (str = dataAirconSystem6.info.name) != null) {
                            dataAirconSystem5.info.name = str;
                        }
                        treeMap.put(str6, dataAirconSystem5);
                    }
                }
            }
            snapShot.aircons = treeMap;
            String str8 = "json=" + this.gson.toJson(snapShot);
            Intent intent3 = new Intent(UartConstants.SEND_JSON_MESSAGE_REQUEST);
            intent3.putExtra("messageRequest", "setAirconSnapShot");
            intent3.putExtra("messageParams", str8);
            intent3.putExtra("doSanitising", false);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent3);
        }
        processBackup(context, masterData);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    public final int q(@NotNull Context context, @Nullable String str, @NotNull Map<String, String> params, boolean z7) throws ExceptionUart {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(params, "params");
        if (str != null) {
            switch (str.hashCode()) {
                case -1922527473:
                    if (str.equals("setAllZoneSensorData")) {
                        HandlerAircon.Companion.getInstance().setAllZoneSensorData(context);
                        return WebServer.ACK;
                    }
                    break;
                case -1777367272:
                    if (str.equals("setActivation")) {
                        HandlerAircon.Companion.getInstance().setActivation(context, params.get("json"));
                        return WebServer.ACK;
                    }
                    break;
                case -1700338558:
                    if (str.equals("setAddHueBridge")) {
                        HandlerHue.Companion.getInstance().setAddHueBridge(context, params.get("json"));
                        return WebServer.ACK;
                    }
                    break;
                case -1572726572:
                    if (str.equals("setRemoveRFThing")) {
                        String str2 = params.get("json");
                        Timber.forest.d("Processing setRemoveRFThing", new Object[0]);
                        HandlerLights.Companion.getInstance().setRemoveRFThing(str2, context);
                        return WebServer.ACK;
                    }
                    break;
                case -1531416133:
                    if (str.equals("setRemoveHueBridge")) {
                        HandlerHue.Companion.getInstance().setRemoveHueBridge(context);
                        return WebServer.ACK;
                    }
                    break;
                case -531149086:
                    if (str.equals("setCancelCB10ZonePairing")) {
                        HandlerAircon.Companion.getInstance().setCancelCB10ZonePairing(context, params.get("json"), false);
                        return WebServer.ACK;
                    }
                    break;
                case -435357560:
                    if (str.equals("setZoneSensor")) {
                        HandlerAircon.Companion.getInstance().setZoneSensor(params.get("json"));
                        return WebServer.ACK;
                    }
                    break;
                case -84982120:
                    if (str.equals("setMonitor")) {
                        String str3 = params.get("json");
                        Timber.forest.d("Processing setMonitor json - " + str3, new Object[0]);
                        return HandlerMonitor.Companion.b().setMonitor(context, str3, Boolean.valueOf(z7));
                    }
                    break;
                case -45970478:
                    if (str.equals("setRFPairing")) {
                        HandlerAircon.Companion.getInstance().setRFPairing(context, params.get("json"));
                        return WebServer.ACK;
                    }
                    break;
                case 237465355:
                    if (str.equals("setFcmToken")) {
                        HandlerAircon.Companion.getInstance().setFcmToken(params);
                        return WebServer.ACK;
                    }
                    break;
                case 1187387773:
                    if (str.equals("setMySystem")) {
                        String str4 = params.get("json");
                        Timber.forest.d("Processing aircon setMySystem json - " + str4, new Object[0]);
                        HandlerAircon.Companion.getInstance().setMySystem(str4, false);
                        return WebServer.ACK;
                    }
                    break;
                case 1240669564:
                    if (str.equals("setRemoveDevice")) {
                        Timber.forest.d("Processing setRemoveDevice", new Object[0]);
                        HandlerAircon.Companion.getInstance().setRemoveDevice(params);
                        return WebServer.ACK;
                    }
                    break;
                case 1469493052:
                    if (str.equals("setCB10ZonePairing")) {
                        HandlerAircon.Companion.getInstance().setCancelCB10ZonePairing(context, params.get("json"), true);
                        return WebServer.ACK;
                    }
                    break;
                case 1497596404:
                    if (str.equals("setRFCalibration")) {
                        HandlerAircon.Companion.getInstance().setRFCalibration(context, params.get("json"));
                        return WebServer.ACK;
                    }
                    break;
                case 2053472241:
                    if (str.equals("setZoneGroup")) {
                        HandlerAircon.Companion.getInstance().setZoneGroup(params.get("json"));
                        return WebServer.ACK;
                    }
                    break;
            }
        }
        return s(context, str, params, z7);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x02e0, code lost:
    
        if (r24.equals("runLightScene") == false) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x03e3, code lost:
    
        if (r24.equals("setLightScene") == false) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x016b, code lost:
    
        if (r24.equals("setScene") == false) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x03e7, code lost:
    
        r0 = r25.get("json");
        r5.d("Processing setScene json - " + r0, new java.lang.Object[0]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:?, code lost:
    
        return com.air.advantage.uart.HandlerLights.Companion.getInstance().setLightScene(r23, r0, r26);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x01fd, code lost:
    
        if (r24.equals("runScene") == false) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x02e8, code lost:
    
        if (n(r23) != false) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x02ea, code lost:
    
        r0 = r25.get("json");
        kotlin.jvm.internal.Intrinsics.checkNotNull(r0);
        r0 = kotlin.text.StringsKt__StringsJVMKt.replace$default(r0, "\\", "", false, 4, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0307, code lost:
    
        if (kotlin.text.StringsKt__StringsJVMKt.startsWith(r0, "\"", false, 2, null) == false) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x030d, code lost:
    
        if (kotlin.text.StringsKt__StringsJVMKt.J1(r0, "\"", false, 2, null) == false) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x030f, code lost:
    
        r0 = r0.substring(1);
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, "this as java.lang.String).substring(startIndex)");
        r0 = r0.substring(0, r0.length() - 1);
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, "this as java.lang.String…ing(startIndex, endIndex)");
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0323, code lost:
    
        r5.d("Processing runLightScene json - " + r0, new java.lang.Object[0]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:?, code lost:
    
        return com.air.advantage.uart.HandlerLights.Companion.getInstance().k0(r23, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x034a, code lost:
    
        throw new com.air.advantage.uart.ExceptionUart("Please check Touch screen");
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int s(@NotNull Context context, @Nullable String str, @NotNull Map<String, String> params, boolean z7) throws ExceptionUart {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(params, "params");
        Timber.Forest forest = Timber.forest;
        forest.d("Looking at request : " + str + " params : " + params, new Object[0]);
        if (str != null) {
            DataSensor dataSensor = null;
            switch (str.hashCode()) {
                case -1670383536:
                    if (str.equals("setLightToGroup")) {
                        return HandlerLights.Companion.getInstance().setLightToGroup(context, params);
                    }
                    break;
                case -833848984:
                    if (str.equals("setThingToNewGroupThing")) {
                        return HandlerLights.Companion.getInstance().setThingToNewGroupThing(context, params);
                    }
                    break;
                case -616981339:
                    if (str.equals("setBackupDataToRestore")) {
                        if (!AppFeatures.CheckFeature(AppFeatures.OptionsEnum.DATA_BACKUP)) {
                            return WebServer.ERR;
                        }
                        if (n(context)) {
                            throw new ExceptionUart("Please check Touch screen");
                        }
                        String str2 = params.get("json");
                        forest.d("Processing setBackupDataToRestore json - " + str2, new Object[0]);
                        if (str2 == null) {
                            return WebServer.NACK;
                        }
                        if (!z7) {
                            return v(context, str2);
                        }
                        Intent intent = new Intent(UartConstants.RESTORE_BACKUP_MESSAGE_RECEIVED);
                        intent.putExtra("backupMasterDataJson", str2);
                        ((LocalBroadcaster) KoinJavaComponent.get$default(LocalBroadcaster.class, null, null, 6, null)).sendBroadcast(intent);
                        return WebServer.ACK;
                    }
                    break;
                case -55983794:
                    if (str.equals("setLightToNewGroup")) {
                        return HandlerLights.Companion.getInstance().setLightToNewGroup(context, params);
                    }
                    break;
                case 63922938:
                    if (str.equals("setAircon")) {
                        if (n(context)) {
                            throw new ExceptionUart("Please check Touch screen");
                        }
                        String str3 = params.get("json");
                        Intrinsics.checkNotNull(str3);
                        String replace$default = StringsKt__StringsJVMKt.replace$default(str3, "\\", "", false, 4, null);
                        if (StringsKt__StringsJVMKt.startsWith(replace$default, "\"", false, 2, null) && StringsKt__StringsJVMKt.J1(replace$default, "\"", false, 2, null)) {
                            String substring = replace$default.substring(1);
                            Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
                            replace$default = substring.substring(0, substring.length() - 1);
                            Intrinsics.checkNotNullExpressionValue(replace$default, "this as java.lang.String…ing(startIndex, endIndex)");
                        }
                        forest.d("Processing aircon json - " + replace$default, new Object[0]);
                        HandlerAircon.Companion.getInstance().setAircon(replace$default, z7);
                        return WebServer.ACK;
                    }
                    break;
                case 113182296:
                    if (str.equals("setNewGroupThingName")) {
                        return HandlerLights.Companion.getInstance().setNewGroupThingName(context, params);
                    }
                    break;
                case 239868125:
                    if (str.equals("setLightAlarm")) {
                        String str4 = params.get("json");
                        forest.d("Processing setAlarm json - " + str4, new Object[0]);
                        return HandlerLights.Companion.getInstance().setLightAlarm(context, str4);
                    }
                    break;
                case 245601547:
                    if (str.equals("setLightGroup")) {
                        if (n(context)) {
                            throw new ExceptionUart("Please check Touch screen");
                        }
                        String str5 = params.get("json");
                        forest.d("Processing setLightGroup json - " + str5, new Object[0]);
                        return HandlerLights.Companion.getInstance().setLightGroup(context, str5);
                    }
                    break;
                case 256227096:
                    break;
                case 371104342:
                    if (str.equals("setThingToGroupThing")) {
                        return HandlerLights.Companion.getInstance().setThingToGroupThing(context, params);
                    }
                    break;
                case 378520863:
                    if (str.equals("setLights")) {
                        if (n(context)) {
                            throw new ExceptionUart("Please check Touch screen");
                        }
                        String str6 = params.get("json");
                        forest.d("Processing setLights json - " + str6, new Object[0]);
                        return HandlerLights.Companion.getInstance().setLights(context, str6);
                    }
                    break;
                case 495400902:
                    if (str.equals("runLightAlarm")) {
                        if (n(context)) {
                            throw new ExceptionUart("Please check Touch screen");
                        }
                        String str7 = params.get("json");
                        forest.d("Processing runLightAlarm json - " + str7, new Object[0]);
                        return HandlerLights.Companion.getInstance().runLightAlarm(context, str7);
                    }
                    break;
                case 511759873:
                    break;
                case 575449788:
                    if (str.equals("setSensor")) {
                        if (n(context)) {
                            throw new ExceptionUart("Please check Touch screen");
                        }
                        String str8 = params.get("json");
                        forest.d("Processing setThing json - " + str8, new Object[0]);
                        try {
                            dataSensor = (DataSensor) this.gson.fromJson(str8, DataSensor.class);
                        } catch (JsonIOException e7) {
                            AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
                        }
                        if (dataSensor == null || dataSensor.id == null) {
                            throw new ExceptionUart("Invalid json received");
                        }
                        return HandlerHue.Companion.getInstance().setSensor(context, dataSensor);
                    }
                    break;
                case 606695495:
                    if (str.equals("setThings")) {
                        if (n(context)) {
                            throw new ExceptionUart("Please check Touch screen");
                        }
                        String str9 = params.get("json");
                        forest.d("Processing setThings json - " + str9, new Object[0]);
                        return HandlerLights.Companion.getInstance().setThings(context, str9);
                    }
                    break;
                case 716359623:
                    if (str.equals("setLightDimOffset")) {
                        if (n(context)) {
                            throw new ExceptionUart("Please check Touch screen");
                        }
                        String str10 = params.get("json");
                        forest.d("Processing setLightDimOffset json - " + str10, new Object[0]);
                        return HandlerLights.Companion.getInstance().setLightDimOffset(context, str10, true);
                    }
                    break;
                case 804539585:
                    break;
                case 965805622:
                    if (str.equals("setLightGroupName")) {
                        return HandlerLights.Companion.getInstance().setLightGroupName(context, params);
                    }
                    break;
                case 1187387773:
                    if (str.equals("setMySystem")) {
                        String str11 = params.get("json");
                        forest.d("Processing aircon setMySystem json - " + str11, new Object[0]);
                        HandlerAircon.Companion.getInstance().setMySystem(str11, z7);
                        return WebServer.ACK;
                    }
                    break;
                case 1397683668:
                    if (str.equals("setLight")) {
                        if (n(context)) {
                            throw new ExceptionUart("Please check Touch screen");
                        }
                        String str12 = params.get("json");
                        forest.d("Processing setLight json - " + str12, new Object[0]);
                        return HandlerLights.Companion.getInstance().setLight(context, str12, true);
                    }
                    break;
                case 1403967818:
                    break;
                case 1405044140:
                    if (str.equals("setThing")) {
                        if (n(context)) {
                            throw new ExceptionUart("Please check Touch screen");
                        }
                        String str13 = params.get("json");
                        Intrinsics.checkNotNull(str13);
                        String replace$default2 = StringsKt__StringsJVMKt.replace$default(str13, "\\", "", false, 4, null);
                        if (StringsKt__StringsJVMKt.startsWith(replace$default2, "\"", false, 2, null) && StringsKt__StringsJVMKt.J1(replace$default2, "\"", false, 2, null)) {
                            String substring2 = replace$default2.substring(1);
                            Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String).substring(startIndex)");
                            replace$default2 = substring2.substring(0, substring2.length() - 1);
                            Intrinsics.checkNotNullExpressionValue(replace$default2, "this as java.lang.String…ing(startIndex, endIndex)");
                        }
                        forest.d("Processing setThing json - " + replace$default2, new Object[0]);
                        return HandlerLights.Companion.getInstance().setThing(context, replace$default2);
                    }
                    break;
                case 1572047324:
                    if (str.equals("setGroupThingName")) {
                        return HandlerLights.Companion.getInstance().setGroupThingName(context, params);
                    }
                    break;
                case 1748360113:
                    if (str.equals("setGroupThing")) {
                        if (n(context)) {
                            throw new ExceptionUart("Please check Touch screen");
                        }
                        String str14 = params.get("json");
                        forest.d("Processing setGroupThing json - " + str14, new Object[0]);
                        return HandlerLights.Companion.getInstance().setGroupThing(context, str14);
                    }
                    break;
                case 1881328367:
                    if (str.equals("setThingDimOffset")) {
                        if (n(context)) {
                            throw new ExceptionUart("Please check Touch screen");
                        }
                        String str15 = params.get("json");
                        forest.d("Processing setThingDimOffset json - " + str15, new Object[0]);
                        return HandlerLights.Companion.getInstance().setThingDimOffset(context, str15);
                    }
                    break;
                case 2073166814:
                    if (str.equals("setLightNewGroupName")) {
                        return HandlerLights.Companion.getInstance().setLightNewGroupName(context, params);
                    }
                    break;
            }
        }
        forest.d("Failed to process this message - " + str, new Object[0]);
        return WebServer.ERR;
    }

    /* renamed from: t */
    public final void processBackupMessage(@Nullable Context context, @Nullable String str) {
        if (str == null || Intrinsics.areEqual(this.backupJsonString.get(), str)) {
            return;
        }
        this.backupJsonString.set(str);
        ((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).updatePreference(context, SharedPreferencesStore.Companion.getOldAAServiceMessage(), str);
    }

    public final void u() {
        ActivityMain companion = ActivityMain.Companion.getInstance();
        if (companion != null) {
            Handler mainHandler = companion.getMainHandler();
            RunnableSendUpdate runnableSendUpdate = f7155j;
            mainHandler.removeCallbacks(runnableSendUpdate);
            companion.getMainHandler().postDelayed(runnableSendUpdate, 0L);
        }
    }

    public final void w(@Nullable Context context) {
        Handler mainHandler;
        try {
            Intrinsics.checkNotNull(context);
            LocalBroadcastManager.getInstance(context).unregisterReceiver(this.f7160c);
        } catch (IllegalArgumentException e7) {
            AppFeatures.logError(AppFeatures.instance, e7, null, 2, null);
        }
        ActivityMain companion = ActivityMain.Companion.getInstance();
        if (companion == null || (mainHandler = companion.getMainHandler()) == null) {
            return;
        }
        mainHandler.removeCallbacks(f7155j);
    }

    /* JADX WARN: Removed duplicated region for block: B:210:0x02a5  */
    /* renamed from: x */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void processData(@NotNull DataMaster masterData, @NotNull String messageString) {
        String masterData2;
        DataAirconSystem dataAirconSystem;
        Boolean bool;
        DataAirconSystem dataAirconSystem2;
        Boolean bool2;
        Boolean bool3;
        boolean z7;
        DataAirconSystem dataAirconSystem3;
        Boolean bool4;
        String str;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        Intrinsics.checkNotNullParameter(messageString, "messageString");
        ((SystemListener) KoinJavaComponent.get$default(SystemListener.class, null, null, 6, null)).j(masterData.copy());
        if (f7153g.get()) {
            Timber.forest.d("storeAndBroadcastJsonMessage - blocked", new Object[0]);
            return;
        }
        Timber.forest.d("storeAndBroadcastJsonMessage - not blocked", new Object[0]);
        j(masterData, y(masterData));
        DataAirconSystem dataAirconSystem4 = masterData.aircons.get(FragmentEnergyMonitoring.DEFAULT_AIRCON_KEY);
        if (dataAirconSystem4 != null && AirconFunctions.Companion.isNonDualZoneDevice(dataAirconSystem4) && (str = dataAirconSystem4.info.airconErrorCode) != null && Intrinsics.areEqual(str, "AA1")) {
            dataAirconSystem4.info.airconErrorCode = "AA16";
        }
        DataSystem dataSystem = new DataSystem();
        dataSystem.update(masterData.system);
        masterData.system.drawLightsTab = Boolean.valueOf(k(dataSystem));
        masterData.system.drawThingsTab = Boolean.valueOf(l(dataSystem));
        boolean z10 = false;
        for (String str2 : masterData.aircons.keySet()) {
            if (str2 != null && (dataAirconSystem3 = masterData.aircons.get(str2)) != null) {
                Boolean bool5 = dataAirconSystem3.info.quietNightModeIsRunning;
                if (bool5 != null) {
                    Intrinsics.checkNotNull(bool5);
                    if (bool5.booleanValue() && (bool4 = dataAirconSystem3.info.quietNightModeEnabled) != null) {
                        Intrinsics.checkNotNull(bool4);
                        if (bool4.booleanValue()) {
                            z10 = true;
                        }
                    }
                }
                Boolean bool6 = dataAirconSystem3.info.myAutoModeIsRunning;
                if (bool6 != null) {
                    Intrinsics.checkNotNull(bool6);
                    if (bool6.booleanValue()) {
                        z10 = true;
                    }
                }
            }
        }
        if (f7159o) {
            f7159o = false;
            if (AppFeatures.CheckFeature(AppFeatures.OptionsEnum.SCENES)) {
                TreeMap<String, DataScene> treeMap = masterData.myScenes.scenes;
                Intrinsics.checkNotNull(treeMap);
                Collection<DataScene> values = treeMap.values();
                Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                ArrayList<DataScene> arrayList = new ArrayList();
                for (DataScene dataScene : values) {
                    if (dataScene != null) {
                        arrayList.add(dataScene);
                    }
                }
                boolean z11 = false;
                for (DataScene dataScene2 : arrayList) {
                    String str3 = dataScene2.summary;
                    String packageName = ActivityMain.Companion.getPackageName();
                    if (packageName != null && StringsKt__StringsKt.startsWith$default(packageName, "zone10", false, 2, null)) {
                        if (masterData.aircons.size() > 0) {
                            AirconFunctions.Companion companion = AirconFunctions.Companion;
                            TreeMap<String, DataAirconSystem> treeMap2 = masterData.aircons;
                            z7 = companion.isNonDualZoneDevice(treeMap2.get(treeMap2.firstKey()));
                        } else {
                            z7 = false;
                        }
                        dataScene2.summary = dataScene2.generateSummaryForZone10e(masterData, Boolean.valueOf(z7));
                    } else {
                        dataScene2.summary = dataScene2.generateSummary(masterData);
                    }
                    if (str3 != null && !Intrinsics.areEqual(str3, dataScene2.summary)) {
                        z11 = true;
                    }
                }
                if (z11) {
                    d0.Companion.b().e(MyApp.appContextProvider.appContext(), masterData);
                }
            }
            if (AppFeatures.CheckFeature(AppFeatures.OptionsEnum.EVENTS)) {
                HashMap<String, DataMonitor> hashMap = masterData.myMonitors.monitors;
                Intrinsics.checkNotNull(hashMap);
                Collection<DataMonitor> values2 = hashMap.values();
                Intrinsics.checkNotNullExpressionValue(values2, "<get-values>(...)");
                ArrayList<DataMonitor> arrayList2 = new ArrayList();
                for (DataMonitor dataMonitor : values2) {
                    if (dataMonitor != null) {
                        arrayList2.add(dataMonitor);
                    }
                }
                boolean z12 = false;
                for (DataMonitor dataMonitor2 : arrayList2) {
                    String str4 = dataMonitor2.monitorSummary;
                    dataMonitor2.generateSummary(masterData);
                    if (str4 != null && !Intrinsics.areEqual(str4, dataMonitor2.monitorSummary)) {
                        z12 = true;
                    }
                }
                if (z12) {
                    MyMonitors.Companion.b().e(MyApp.appContextProvider.appContext(), masterData);
                }
            }
        }
        masterData.getMasterDataInMemoryAsJsonWithoutLocks();
        if (z10) {
            DataMaster dataMaster = new DataMaster();
            DataMaster.update$default(dataMaster, masterData, null, false, 4, null);
            for (String str5 : dataMaster.aircons.keySet()) {
                if (str5 != null && (dataAirconSystem2 = dataMaster.aircons.get(str5)) != null && (bool2 = dataAirconSystem2.info.quietNightModeIsRunning) != null) {
                    Intrinsics.checkNotNull(bool2);
                    if (bool2.booleanValue() && (bool3 = dataAirconSystem2.info.quietNightModeEnabled) != null) {
                        Intrinsics.checkNotNull(bool3);
                        if (bool3.booleanValue()) {
                            HandlerAircon companion2 = HandlerAircon.Companion.getInstance();
                            if (companion2.getFanSpeedMap().containsKey(dataAirconSystem2.info.uid)) {
                                dataAirconSystem2.updateForAutoModeBackup(companion2.setAirconMode(dataAirconSystem2.info.uid, dataAirconSystem2));
                            }
                        }
                    }
                }
            }
            for (String str6 : dataMaster.aircons.keySet()) {
                if (str6 != null && (dataAirconSystem = dataMaster.aircons.get(str6)) != null && (bool = dataAirconSystem.info.myAutoModeIsRunning) != null) {
                    Intrinsics.checkNotNull(bool);
                    if (bool.booleanValue()) {
                        dataAirconSystem.info.mode = AirconMode.myauto;
                    }
                }
            }
            masterData2 = dataMaster.getMasterDataInMemoryAsJsonWithoutLocks();
        } else {
            masterData2 = masterData.getMasterDataInMemoryAsJsonWithoutLocks();
        }
        if (masterData2 == null) {
            AppFeatures.Error(AppFeatures.instance, new NullPointerException("json from " + messageString + " msg is null or empty."), null, 2, null);
        } else if (masterData2.length() == 0) {
        }
        String str7 = (masterData2 == null || StringsKt__StringsKt.p3(masterData2, "{\"aircons\":{}", 0, false, 6, null) != 0) ? null : messageString + " - empty aircons - " + masterData2;
        if (str7 != null) {
            String str8 = this.masterJsonString;
            if (str8 != null) {
                str7 = str7 + " old json - " + str8;
            }
            AppFeatures.logError(AppFeatures.instance, new NullPointerException(str7), null, 2, null);
        }
        this.masterJsonString = masterData2;
        if (masterData2 != null) {
            jsonString.set(masterData2);
            long currentTimeMillis = System.currentTimeMillis();
            long j10 = f7158n.get() > currentTimeMillis ? f7158n.get() - currentTimeMillis : 0L;
            if (j10 < 500) {
                j10 = 500;
            }
            ActivityMain companion3 = ActivityMain.Companion.getInstance();
            if (companion3 != null) {
                Handler mainHandler = companion3.getMainHandler();
                RunnableSendUpdate runnableSendUpdate = f7155j;
                mainHandler.removeCallbacks(runnableSendUpdate);
                companion3.getMainHandler().postDelayed(runnableSendUpdate, j10);
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private HandlerJson(Context context) {
        this.oldAAServiceMode = new AtomicBoolean(false);
        AtomicReference<String> atomicReference = new AtomicReference<>("");
        this.backupJsonString = atomicReference;
        BroadcastReceiverLocalMessage broadcastReceiverLocalMessage = new BroadcastReceiverLocalMessage();
        this.f7160c = broadcastReceiverLocalMessage;
        this.gson = (Gson) KoinJavaComponent.get$default(Gson.class, null, null, 6, null);
        atomicReference.set(((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).getSharedPreference(context, SharedPreferencesStore.Companion.getOldAAServiceMessage()));
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiverLocalMessage, new IntentFilter(UartConstants.SEND_JSON_MESSAGE_REQUEST));
        f7158n.set(System.currentTimeMillis() + Xml2JsonFunctions.DELAY_MS);
    }
}