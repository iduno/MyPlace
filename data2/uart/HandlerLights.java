package com.air.advantage.uart;

import android.content.Context;
import androidx.core.view.PointerIconCompat;
import com.air.advantage.ActivityMain;
import com.air.advantage.AppFeatures;
import com.air.advantage.MyApp;
import com.air.advantage.aircon.AirconFunctions;
import com.air.advantage.data.DataAirconInfo;
import com.air.advantage.data.DataAirconSystem;
import com.air.advantage.data.DataAlarm;
import com.air.advantage.data.DataGroup;
import com.air.advantage.data.DataGroupSource;
import com.air.advantage.data.DataGroupThing;
import com.air.advantage.data.DataLight;
import com.air.advantage.data.DataLightsSystem;
import com.air.advantage.data.DataManager;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataModuleInfoSource;
import com.air.advantage.data.DataMonitor;
import com.air.advantage.data.DataMyLights;
import com.air.advantage.data.DataMyScene;
import com.air.advantage.data.DataMyThing;
import com.air.advantage.data.DataScene;
import com.air.advantage.data.DataSensor;
import com.air.advantage.data.DataSystem;
import com.air.advantage.data.DataThings;
import com.air.advantage.data.DataThingsSystem;
import com.air.advantage.data.DataZone;
import com.air.advantage.data.Events;
import com.air.advantage.data.State;
import com.air.advantage.firebase.FirebaseAnalyticsLog;
import com.air.advantage.libraryairconlightjson.AirconMode;
import com.air.advantage.libraryairconlightjson.CommonFuncs;
import com.air.advantage.libraryairconlightjson.FanStatus;
import com.air.advantage.libraryairconlightjson.LightState;
import com.air.advantage.libraryairconlightjson.SystemState;
import com.air.advantage.notification.NotificationData;
import com.air.advantage.notification.NotificationService;
import com.air.advantage.notification.NotificationType;
import com.air.advantage.sonos.Sonos;
import com.air.advantage.sonos.SonosRepository;
import com.air.advantage.uart.HandlerHue;
import com.air.advantage.webserver.WebServer;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.database.Exclude;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

@PurelyImplements({"SMAP\nHandlerLights.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HandlerLights.kt\ncom/air/advantage/uart/HandlerLights\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 Strings.kt\nkotlin/text/StringsKt__StringsKt\n+ 5 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,4939:1\n1603#2,9:4940\n1855#2:4949\n1856#2:4951\n1612#2:4952\n1603#2,9:4953\n1855#2:4962\n1856#2:4964\n1612#2:4965\n1603#2,9:4966\n1855#2:4975\n1856#2:4977\n1612#2:4978\n1603#2,9:5002\n1855#2:5011\n1856#2:5013\n1612#2:5014\n1603#2,9:5015\n1855#2:5024\n1856#2:5026\n1612#2:5027\n731#2,9:5097\n1603#2,9:5108\n1855#2:5117\n1856#2:5119\n1612#2:5120\n1603#2,9:5121\n1855#2:5130\n1856#2:5132\n1612#2:5133\n1603#2,9:5134\n1855#2:5143\n1856#2:5145\n1612#2:5146\n1603#2,9:5147\n1855#2:5156\n1856#2:5158\n1612#2:5159\n1603#2,9:5160\n1855#2:5169\n1856#2:5171\n1612#2:5172\n1603#2,9:5173\n1855#2:5182\n1856#2:5184\n1612#2:5185\n1603#2,9:5186\n1855#2:5195\n1856#2:5197\n1612#2:5198\n1603#2,9:5199\n1855#2:5208\n1856#2:5210\n1612#2:5211\n1603#2,9:5212\n1855#2:5221\n1856#2:5223\n1612#2:5224\n731#2,9:5225\n1603#2,9:5236\n1855#2:5245\n1856#2:5247\n1612#2:5248\n731#2,9:5272\n731#2,9:5284\n1603#2,9:5318\n1855#2:5327\n1856#2:5329\n1612#2:5330\n1603#2,9:5331\n1855#2:5340\n1856#2:5342\n1612#2:5343\n1603#2,9:5345\n1855#2:5354\n1856#2:5356\n1612#2:5357\n1603#2,9:5358\n1855#2:5367\n1856#2:5369\n1612#2:5370\n1603#2,9:5395\n1855#2:5404\n1856#2:5406\n1612#2:5407\n1#3:4950\n1#3:4963\n1#3:4976\n1#3:5012\n1#3:5025\n1#3:5118\n1#3:5131\n1#3:5144\n1#3:5157\n1#3:5170\n1#3:5183\n1#3:5196\n1#3:5209\n1#3:5222\n1#3:5246\n1#3:5283\n1#3:5328\n1#3:5341\n1#3:5344\n1#3:5355\n1#3:5368\n1#3:5371\n1#3:5405\n107#4:4979\n79#4,22:4980\n107#4:5028\n79#4,22:5029\n107#4:5051\n79#4,22:5052\n107#4:5074\n79#4,22:5075\n107#4:5249\n79#4,22:5250\n107#4:5295\n79#4,22:5296\n107#4:5372\n79#4,22:5373\n37#5,2:5106\n37#5,2:5234\n37#5,2:5281\n37#5,2:5293\n*S KotlinDebug\n*F\n+ 1 HandlerLights.kt\ncom/air/advantage/uart/HandlerLights\n*L\n191#1:4940,9\n191#1:4949\n191#1:4951\n191#1:4952\n492#1:4953,9\n492#1:4962\n492#1:4964\n492#1:4965\n508#1:4966,9\n508#1:4975\n508#1:4977\n508#1:4978\n524#1:5002,9\n524#1:5011\n524#1:5013\n524#1:5014\n537#1:5015,9\n537#1:5024\n537#1:5026\n537#1:5027\n677#1:5097,9\n696#1:5108,9\n696#1:5117\n696#1:5119\n696#1:5120\n720#1:5121,9\n720#1:5130\n720#1:5132\n720#1:5133\n779#1:5134,9\n779#1:5143\n779#1:5145\n779#1:5146\n787#1:5147,9\n787#1:5156\n787#1:5158\n787#1:5159\n796#1:5160,9\n796#1:5169\n796#1:5171\n796#1:5172\n804#1:5173,9\n804#1:5182\n804#1:5184\n804#1:5185\n1117#1:5186,9\n1117#1:5195\n1117#1:5197\n1117#1:5198\n1125#1:5199,9\n1125#1:5208\n1125#1:5210\n1125#1:5211\n1151#1:5212,9\n1151#1:5221\n1151#1:5223\n1151#1:5224\n1270#1:5225,9\n1376#1:5236,9\n1376#1:5245\n1376#1:5247\n1376#1:5248\n1398#1:5272,9\n1636#1:5284,9\n1907#1:5318,9\n1907#1:5327\n1907#1:5329\n1907#1:5330\n1909#1:5331,9\n1909#1:5340\n1909#1:5342\n1909#1:5343\n3752#1:5345,9\n3752#1:5354\n3752#1:5356\n3752#1:5357\n3897#1:5358,9\n3897#1:5367\n3897#1:5369\n3897#1:5370\n4764#1:5395,9\n4764#1:5404\n4764#1:5406\n4764#1:5407\n191#1:4950\n492#1:4963\n508#1:4976\n524#1:5012\n537#1:5025\n696#1:5118\n720#1:5131\n779#1:5144\n787#1:5157\n796#1:5170\n804#1:5183\n1117#1:5196\n1125#1:5209\n1151#1:5222\n1376#1:5246\n1907#1:5328\n1909#1:5341\n3752#1:5355\n3897#1:5368\n4764#1:5405\n512#1:4979\n512#1:4980,22\n560#1:5028\n560#1:5029,22\n633#1:5051\n633#1:5052,22\n638#1:5074\n638#1:5075,22\n1388#1:5249\n1388#1:5250,22\n1897#1:5295\n1897#1:5296,22\n4427#1:5372\n4427#1:5373,22\n677#1:5106,2\n1270#1:5234,2\n1398#1:5281,2\n1636#1:5293,2\n*E\n"})
/* renamed from: com.air.advantage.uart.q */
/* loaded from: classes.dex */
public final class HandlerLights extends FRLParser {

    /* renamed from: d0 */
    @NotNull
    public static final String hexRegx = "[0-9a-f]+$";

    /* renamed from: e0 */
    public static final int f7162e0 = 70;

    /* renamed from: f0 */
    public static final int nonRFExpiry = 260;

    /* renamed from: g0 */
    public static final int f7163g0 = 30;

    /* renamed from: h0 */
    public static final long f7164h0 = 14;

    /* renamed from: i0 */
    public static final long f7165i0 = 30;

    /* renamed from: k0 */
    private static final int f7166k0 = 0;

    /* renamed from: l0 */
    private static final int f7167l0 = 1;

    /* renamed from: m0 */
    private static final int f7168m0 = 2;

    /* renamed from: n0 */
    private static final int f7169n0 = 3;

    /* renamed from: o0 */
    private static final int f7170o0 = 4;

    /* renamed from: p0 */
    private static final int f7171p0 = 80;

    /* renamed from: q0 */
    private static final int f7172q0 = 0;

    /* renamed from: s0 */
    @NotNull
    private static final String f7173s0 = "36";

    /* renamed from: t0 */
    private static final int f7174t0 = 10;

    /* renamed from: u0 */
    @NotNull
    private static final String f7175u0 = "[0-9a-f:-]+$";

    /* renamed from: v0 */
    private static final int f7176v0 = 12;

    /* renamed from: w0 */
    private static final byte f7177w0 = Byte.MIN_VALUE;

    /* renamed from: x0 */
    private static final byte f7178x0 = 16;

    /* renamed from: y0 */
    @Nullable
    private static HandlerLights INSTANCE;

    @NotNull
    private final HandlerCan U;

    @NotNull
    private final Gson V;

    /* renamed from: W */
    @NotNull
    private final ConcurrentHashMap<String, Integer> thingsMap;

    @NotNull
    private final ConcurrentHashMap<String, Integer> X;

    @NotNull
    private final ConcurrentHashMap<String, Integer> Y;

    @NotNull
    private final ConcurrentHashMap<String, b> Z;

    /* renamed from: a0 */
    @NotNull
    private final SonosRepository f7179a0;

    /* renamed from: b0 */
    @NotNull
    private final FirebaseAnalyticsLog f7180b0;

    /* renamed from: c0 */
    @NotNull
    public static final Companion Companion = new Companion(null);

    /* renamed from: j0 */
    @NotNull
    private static final LightState lightState = LightState.off;

    /* renamed from: r0 */
    private static final String LOG_NAME = HandlerLights.class.getSimpleName();

    /* renamed from: com.air.advantage.uart.q$a */
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.q.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void c() {
        }

        @JvmStatic
        /* renamed from: a */
        public final void destroy() {
            HandlerLights.INSTANCE = null;
        }

        @NotNull
        /* renamed from: b */
        public final HandlerLights getInstance() {
            if (HandlerLights.INSTANCE == null) {
                synchronized (HandlerLights.class) {
                    if (HandlerLights.INSTANCE == null) {
                        Companion companion = HandlerLights.Companion;
                        HandlerLights.INSTANCE = new HandlerLights(HandlerCan.Companion.getInstance(), null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            HandlerLights handlerLights = HandlerLights.INSTANCE;
            Intrinsics.checkNotNull(handlerLights);
            return handlerLights;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private Companion() {
        }
    }

    /* renamed from: com.air.advantage.uart.q$c */
    public static final class c extends TypeToken<HashMap<String, DataLight>> {
        c() {
        }
    }

    /* renamed from: com.air.advantage.uart.q$d */
    public static final class d extends TypeToken<HashMap<String, DataMyThing>> {
        d() {
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 com.air.advantage.uart.j) A[MD:(com.air.advantage.uart.j):void (m)] (LINE:1) call: com.air.advantage.uart.q.<init>(com.air.advantage.uart.j):void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ HandlerLights(HandlerCan handlerCan, DefaultConstructorMarker defaultConstructorMarker) {
        this(handlerCan);
    }

    private final void A(Context context, DataMaster dataMaster) {
        long uptime = CommonFuncs.getUptime();
        ArrayList arrayList = new ArrayList();
        TreeMap<String, DataLight> treeMap = dataMaster.myLights.lights;
        Intrinsics.checkNotNull(treeMap);
        Collection<DataLight> values = treeMap.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        ArrayList<DataLight> arrayList2 = new ArrayList();
        for (DataLight dataLight : values) {
            if (dataLight != null) {
                arrayList2.add(dataLight);
            }
        }
        for (DataLight dataLight2 : arrayList2) {
            Long l8 = dataLight2.expiryTime;
            if (l8 != null && l8.longValue() == 0) {
                dataLight2.expiryTime = 1L;
            } else {
                Long l10 = dataLight2.expiryTime;
                Intrinsics.checkNotNull(l10);
                if (l10.longValue() < uptime) {
                    Timber.forest.d("Light expired - " + dataLight2.id, new Object[0]);
                    arrayList.add(dataLight2.id);
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            isValidLight(dataMaster, (String) it.next());
        }
        if (arrayList.size() > 0) {
            updateLightJson(context, dataMaster);
        }
    }

    private final void B(Context context, DataMaster dataMaster) {
        long uptime = CommonFuncs.getUptime();
        ArrayList arrayList = new ArrayList();
        HashMap<String, DataGroupSource> hashMap = dataMaster.myGarageRFControllers.garageControllers;
        Intrinsics.checkNotNull(hashMap);
        Collection<DataGroupSource> values = hashMap.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        ArrayList<DataGroupSource> arrayList2 = new ArrayList();
        for (DataGroupSource dataGroupSource : values) {
            if (dataGroupSource != null) {
                arrayList2.add(dataGroupSource);
            }
        }
        for (DataGroupSource dataGroupSource2 : arrayList2) {
            long j10 = dataGroupSource2.expiryTime;
            if (j10 == 0) {
                dataGroupSource2.expiryTime = 1L;
            } else if (j10 < uptime) {
                Timber.forest.d("RF Garage Controller expired - " + dataGroupSource2.uid, new Object[0]);
                arrayList.add(dataGroupSource2.uid);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            dataMaster.myGarageRFControllers.removeGarageController((String) it.next());
        }
        if (arrayList.size() > 0) {
            updateThingJson(context, dataMaster);
        }
    }

    private final void C(Context context, DataMaster dataMaster) {
        long uptime = CommonFuncs.getUptime();
        ArrayList arrayList = new ArrayList();
        TreeMap<String, DataMyThing> treeMap = dataMaster.myThings.things;
        Intrinsics.checkNotNull(treeMap);
        Collection<DataMyThing> values = treeMap.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        ArrayList<DataMyThing> arrayList2 = new ArrayList();
        for (DataMyThing dataMyThing : values) {
            if (dataMyThing != null) {
                arrayList2.add(dataMyThing);
            }
        }
        for (DataMyThing dataMyThing2 : arrayList2) {
            Long l8 = dataMyThing2.expiryTime;
            if (l8 != null && l8.longValue() == 0) {
                dataMyThing2.expiryTime = 1L;
            } else {
                Long l10 = dataMyThing2.expiryTime;
                Intrinsics.checkNotNull(l10);
                if (l10.longValue() < uptime) {
                    Timber.forest.d("Thing expired - " + dataMyThing2.id, new Object[0]);
                    arrayList.add(dataMyThing2.id);
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            isValidThing(dataMaster, (String) it.next());
        }
        if (arrayList.size() > 0) {
            updateThingJson(context, dataMaster);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0139  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean G(DataMaster dataMaster, String str, int i10, boolean z7) {
        boolean z10;
        DataMyThing thingData = dataMaster.myThings.getThingData(str);
        boolean z11 = false;
        boolean z12 = true;
        if (thingData != null) {
            Integer num = thingData.channelDipState;
            if (num == null || num == null || num.intValue() != i10) {
                thingData.channelDipState = Integer.valueOf(i10);
                if (i10 != 1 && i10 != 2 && i10 != 3) {
                    switch (i10) {
                        case 8:
                            if (thingData.buttonType == null) {
                                thingData.buttonType = DataMyThing.BUTTON_TYPE_ON_OFF;
                                break;
                            }
                            break;
                        case 9:
                            thingData.buttonType = DataMyThing.BUTTON_TYPE_DIMMABLE;
                            break;
                        case 10:
                            thingData.buttonType = "none";
                            break;
                    }
                } else {
                    thingData.buttonType = DataMyThing.BUTTON_TYPE_UP_DOWN;
                }
                z11 = true;
            }
            if (thingData.value == null) {
                thingData.value = 0;
            } else {
                z12 = z11;
            }
            if (z7) {
                thingData.expiryTime = Long.valueOf(CommonFuncs.getUptime() + nonRFExpiry);
            } else {
                thingData.expiryTime = Long.valueOf(CommonFuncs.getUptime() + 70);
            }
            thingData.thisIsRFDevice = z7;
            return z12;
        }
        DataMyThing dataMyThing = new DataMyThing();
        dataMyThing.id = str;
        dataMyThing.channelDipState = Integer.valueOf(i10);
        dataMyThing.value = 0;
        dataMyThing.thisIsRFDevice = z7;
        Iterator<DataThings.b> it = dataMaster.myThings.backupThings.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (true) {
            if (!it.hasNext()) {
                z10 = false;
                break;
            }
            DataThings.b next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            DataThings.b bVar = next;
            if (Intrinsics.areEqual(bVar.id, str)) {
                dataMyThing.name = bVar.name;
                if (i10 != 1 && i10 != 2 && i10 != 3) {
                    switch (i10) {
                        case 8:
                            if (!Intrinsics.areEqual(bVar.buttonType, DataMyThing.BUTTON_TYPE_UP_DOWN)) {
                                dataMyThing.buttonType = bVar.buttonType;
                                break;
                            } else {
                                dataMyThing.buttonType = DataMyThing.BUTTON_TYPE_ON_OFF;
                                break;
                            }
                        case 9:
                            dataMyThing.buttonType = DataMyThing.BUTTON_TYPE_DIMMABLE;
                            break;
                        case 10:
                            dataMyThing.buttonType = "none";
                            break;
                    }
                } else {
                    dataMyThing.buttonType = DataMyThing.BUTTON_TYPE_UP_DOWN;
                }
                addToGroup(dataMaster, bVar.groupId, dataMyThing, 99);
                it.remove();
                z10 = true;
            }
        }
        if (!z10) {
            if (i10 == 3) {
                dataMyThing.name = "Garage " + (dataMaster.myThings.numberLights() + 1 + dataMaster.myThings.backupThings.size());
                if (i10 == 1 && i10 != 2 && i10 != 3) {
                    switch (i10) {
                        case 8:
                            dataMyThing.buttonType = DataMyThing.BUTTON_TYPE_ON_OFF;
                            break;
                        case 9:
                            dataMyThing.buttonType = DataMyThing.BUTTON_TYPE_DIMMABLE;
                            break;
                        case 10:
                            dataMyThing.buttonType = "none";
                            break;
                    }
                } else {
                    dataMyThing.buttonType = DataMyThing.BUTTON_TYPE_UP_DOWN;
                }
                addToGroup(dataMaster, "", dataMyThing, 99);
            } else {
                if (i10 == 9) {
                    dataMyThing.name = "Fan " + (dataMaster.myThings.numberLights() + 1 + dataMaster.myThings.backupThings.size());
                } else if (i10 != 10) {
                    dataMyThing.name = "Button " + (dataMaster.myThings.numberLights() + 1 + dataMaster.myThings.backupThings.size());
                }
                if (i10 == 1) {
                    dataMyThing.buttonType = DataMyThing.BUTTON_TYPE_UP_DOWN;
                    addToGroup(dataMaster, "", dataMyThing, 99);
                }
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:116:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x023a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0266 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0253  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0119  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean G0(Context context, DataMaster dataMaster, DataMyThing dataMyThing) throws ExceptionUart {
        boolean z7;
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Integer num5;
        Integer num6;
        String str;
        boolean z10;
        boolean z11;
        Integer num7;
        Integer num8;
        Integer num9;
        boolean z12;
        Integer num10;
        boolean z13;
        Integer num11;
        Integer num12;
        Integer num13;
        Integer num14;
        Integer num15;
        Integer num16;
        Integer num17;
        Integer num18;
        Integer num19;
        Integer num20;
        String str2 = dataMyThing.id;
        if (str2 == null) {
            return false;
        }
        DataMyThing thingData = dataMaster.myThings.getThingData(str2);
        if (thingData == null) {
            if (AppFeatures.isDemo()) {
                return false;
            }
            throw new ExceptionUart("Invalid thing id received");
        }
        Integer num21 = dataMyThing.value;
        if (num21 != null && ((num21 == null || num21.intValue() != 0) && (((num17 = dataMyThing.value) == null || num17.intValue() != 100) && (((num18 = dataMyThing.value) == null || num18.intValue() != 50) && (((num19 = dataMyThing.value) == null || num19.intValue() != 75) && ((num20 = dataMyThing.value) == null || num20.intValue() != 25)))))) {
            throw new ExceptionUart("Invalid value received (must be 0 or 100 or 50)");
        }
        Integer num22 = dataMyThing.value;
        if (num22 == null) {
            dataMyThing.value = thingData.value;
        } else {
            if (num22 == null || num22.intValue() != 50 || (((num = thingData.channelDipState) != null && num.intValue() == 2) || (((num2 = thingData.channelDipState) != null && num2.intValue() == 10) || ((num3 = thingData.channelDipState) != null && num3.intValue() == 3)))) {
                z7 = true;
                num4 = dataMyThing.channelDipState;
                if (num4 != null && (((num4 != null && num4.intValue() == 3) || ((num15 = dataMyThing.channelDipState) != null && num15.intValue() == 10)) && (num16 = dataMyThing.value) != null && num16 != null && num16.intValue() == 0)) {
                    this.thingsMap.remove(str2);
                }
                num5 = dataMyThing.channelDipState;
                if (num5 != null && num5 != null && num5.intValue() == 10 && dataMyThing.value != null) {
                    Integer num23 = thingData.value;
                    Intrinsics.checkNotNull(num23);
                    M0(str2, num23.intValue());
                }
                num6 = thingData.channelDipState;
                if (num6 != null && num6 != null && num6.intValue() == 3 && (num14 = thingData.value) != null && Intrinsics.areEqual(num14, dataMyThing.value)) {
                    Timber.forest.d("(JZ37 debug) already the same value no need to send CAN!!!!!!!!!!!!!!!", new Object[0]);
                    z7 = false;
                }
                str = dataMyThing.name;
                if (str == null) {
                    int length = str.length() - 1;
                    int i10 = 0;
                    boolean z14 = false;
                    while (i10 <= length) {
                        boolean z15 = Intrinsics.compare(str.charAt(!z14 ? i10 : length), 32) <= 0;
                        if (z14) {
                            if (!z15) {
                                break;
                            }
                            length--;
                        } else if (z15) {
                            i10++;
                        } else {
                            z14 = true;
                        }
                    }
                    if (Intrinsics.areEqual(str.subSequence(i10, length + 1).toString(), "") || Intrinsics.areEqual(dataMyThing.name, "")) {
                        throw new ExceptionUart("setThing name - name can't be empty");
                    }
                    if (str.length() > 12) {
                        throw new ExceptionUart("setThing name - name too long");
                    }
                    Timber.forest.d("setThing Name - ID : " + str2 + " Name : " + dataMyThing.name, new Object[0]);
                    DataThings dataThings = dataMaster.myThings;
                    String str3 = dataMyThing.name;
                    Intrinsics.checkNotNull(str3);
                    z10 = dataThings.updateThingName(str2, str3);
                }
                if (dataMyThing.buttonType == null && (num13 = thingData.channelDipState) != null && num13.intValue() == 8) {
                    DataThings dataThings2 = dataMaster.myThings;
                    String str4 = dataMyThing.buttonType;
                    Intrinsics.checkNotNull(str4);
                    if (dataThings2.updateThingButtonType(str2, str4)) {
                        z11 = true;
                    }
                } else {
                    z11 = z10;
                }
                num7 = thingData.channelDipState;
                if ((num7 == null && num7.intValue() == 3) || ((num8 = thingData.channelDipState) != null && num8.intValue() == 10)) {
                    Integer num24 = thingData.value;
                    z12 = (num24 != null && num24.intValue() == 0) || ((num9 = thingData.value) != null && num9.intValue() == 25);
                    Integer num25 = thingData.value;
                    z13 = (num25 != null && num25.intValue() == 100) || ((num10 = thingData.value) != null && num10.intValue() == 75);
                    Integer num26 = dataMyThing.value;
                    boolean z16 = (num26 != null && num26.intValue() == 0) || ((num11 = dataMyThing.value) != null && num11.intValue() == 25);
                    if (!z12 && z16) {
                        Timber.forest.d("Rejecting garage message to close as door is already closed or closing", new Object[0]);
                        return false;
                    }
                    Integer num27 = dataMyThing.value;
                    boolean z17 = (num27 != null && num27.intValue() == 100) || ((num12 = dataMyThing.value) != null && num12.intValue() == 75);
                    if (z13 && z17) {
                        Timber.forest.d("Rejecting garage message to open as door is already open or opening", new Object[0]);
                        return false;
                    }
                }
                if (z7) {
                    if (dataMyThing.dimPercent == null) {
                        dataMyThing.dimPercent = 0;
                    }
                    Integer num28 = dataMyThing.value;
                    Intrinsics.checkNotNull(num28);
                    int intValue = num28.intValue();
                    Integer num29 = dataMyThing.dimPercent;
                    Intrinsics.checkNotNull(num29);
                    m(context, str2, intValue, num29.intValue());
                    Integer num30 = dataMyThing.value;
                    Intrinsics.checkNotNull(num30);
                    int intValue2 = num30.intValue();
                    Integer num31 = dataMyThing.dimPercent;
                    Intrinsics.checkNotNull(num31);
                    if (U0(dataMaster, str2, intValue2, num31.intValue(), true, null, null, null, null)) {
                        return true;
                    }
                }
                return z11;
            }
            dataMyThing.value = thingData.value;
        }
        z7 = false;
        num4 = dataMyThing.channelDipState;
        if (num4 != null) {
            this.thingsMap.remove(str2);
        }
        num5 = dataMyThing.channelDipState;
        if (num5 != null) {
            Integer num232 = thingData.value;
            Intrinsics.checkNotNull(num232);
            M0(str2, num232.intValue());
        }
        num6 = thingData.channelDipState;
        if (num6 != null) {
            Timber.forest.d("(JZ37 debug) already the same value no need to send CAN!!!!!!!!!!!!!!!", new Object[0]);
            z7 = false;
        }
        str = dataMyThing.name;
        if (str == null) {
        }
        if (dataMyThing.buttonType == null) {
            z11 = z10;
        }
        num7 = thingData.channelDipState;
        if (num7 == null) {
            Integer num242 = thingData.value;
            if (num242 != null) {
                Integer num252 = thingData.value;
                if (num252 != null) {
                    Integer num262 = dataMyThing.value;
                    if (num262 != null) {
                        if (!z12) {
                        }
                        Integer num272 = dataMyThing.value;
                        if (num272 != null) {
                            if (z13) {
                                Timber.forest.d("Rejecting garage message to open as door is already open or opening", new Object[0]);
                                return false;
                            }
                        } else {
                            if (z13) {
                            }
                        }
                    } else {
                        if (!z12) {
                        }
                        Integer num2722 = dataMyThing.value;
                        if (num2722 != null) {
                        }
                    }
                } else {
                    Integer num2622 = dataMyThing.value;
                    if (num2622 != null) {
                    }
                }
            } else {
                Integer num2522 = thingData.value;
                if (num2522 != null) {
                }
            }
        } else {
            Integer num2422 = thingData.value;
            if (num2422 != null) {
            }
        }
        if (z7) {
        }
        return z11;
    }

    /* renamed from: H */
    private final String lmAck(String str) {
        return "0201" + str + "0300000000000000";
    }

    private final String I(String str) {
        return "0801" + str + "0300000000000000";
    }

    private final boolean I0(Context context, DataMaster dataMaster, DataMyThing dataMyThing) throws ExceptionUart {
        boolean z7;
        DataMyThing thingData = dataMaster.myThings.getThingData(dataMyThing.id);
        if (thingData == null) {
            throw new ExceptionUart("Invalid thing id received");
        }
        if (dataMyThing.dimOffset == null) {
            dataMyThing.dimOffset = thingData.dimOffset;
            z7 = false;
        } else {
            z7 = true;
        }
        if (z7) {
            o(context, dataMyThing.id, dataMyThing.dimOffset);
            if (V0(dataMaster, dataMyThing.id, dataMyThing.dimOffset)) {
                return true;
            }
        }
        return false;
    }

    private final void M0(String str, int i10) {
        b bVar = this.Z.get(str);
        if (bVar == null) {
            b bVar2 = new b(this, str, Long.valueOf(CommonFuncs.getUptime()), null, null, null, 0, 32, null);
            bVar2.m(i10);
            this.Z.put(str, bVar2);
            Timber.forest.d("new garageV2 state timeout: %s", bVar2.a());
            return;
        }
        bVar.h(Long.valueOf(CommonFuncs.getUptime()));
        bVar.i(null);
        bVar.l(-1);
        bVar.k(null);
        bVar.m(i10);
        Timber.forest.d("updated garageV2 state timeout: %s", bVar.a());
    }

    private final String N(DataMaster dataMaster, DataLight dataLight) {
        Boolean bool;
        TreeMap<String, DataLight> treeMap = dataMaster.myLights.lights;
        DataLight dataLight2 = treeMap != null ? treeMap.get(dataLight.id) : null;
        if (dataLight2 == null) {
            return "";
        }
        String str = dataLight2.moduleType;
        if (str == null) {
            return encodeDataLight_T(dataLight);
        }
        int hashCode = str.hashCode();
        if (hashCode == 2185) {
            return !str.equals(DataLight.MODULE_TYPE_STRING_DM) ? "" : encodeDataLight_R(dataLight);
        }
        if (hashCode != 71896) {
            if (hashCode != 81239 || !str.equals(DataLight.MODULE_TYPE_STRING_RM2)) {
                return "";
            }
            DataMyThing dataMyThing = new DataMyThing();
            dataMyThing.id = dataLight.id;
            LightState lightState2 = dataLight.state;
            if (lightState2 == LightState.off) {
                dataMyThing.value = 0;
            } else if (lightState2 == LightState.on) {
                dataMyThing.value = 100;
            }
            return generateSetThingsCanString(dataMyThing);
        }
        if (!str.equals(DataLight.MODULE_TYPE_STRING_HUE) || (bool = dataLight2.reachable) == null) {
            return "";
        }
        Intrinsics.checkNotNull(bool);
        if (!bool.booleanValue()) {
            return "";
        }
        HandlerHue companion = HandlerHue.Companion.getInstance();
        DataLight dataLight3 = new DataLight();
        DataLight.update$default(dataLight3, null, dataLight, null, false, 8, null);
        dataLight3.idOnHueBridge = dataLight2.idOnHueBridge;
        companion.B(dataMaster, dataLight3);
        return "";
    }

    private final void N0(String str, String str2, DataMyThing dataMyThing, int i10, boolean z7) {
        b bVar = this.Z.get(str);
        if (bVar != null) {
            bVar.i(str2);
            bVar.k(dataMyThing);
            bVar.l(Integer.valueOf(i10));
            Timber.forest.d("update garageV2 CAN message: %s", bVar.b());
            return;
        }
        if (z7) {
            b bVar2 = new b(this, str, null, dataMyThing, str2, Integer.valueOf(i10), 0, 32, null);
            this.Z.put(str, bVar2);
            Timber.forest.d("new garageV2 CAN message: %s", bVar2.b());
        }
    }

    private final String O0(DataMaster dataMaster, String str, String str2) {
        DataGroup dataGroup = dataMaster.myLights.getDataGroup(str);
        if (dataGroup == null) {
            return null;
        }
        dataGroup.name = str2;
        return str;
    }

    private final String P0(DataMaster dataMaster, String str, String str2) {
        DataGroupThing dataGroupThing = dataMaster.myThings.getDataGroupThing(str);
        if (dataGroupThing == null) {
            return null;
        }
        dataGroupThing.name = str2;
        return str;
    }

    /* renamed from: R */
    private final String encodeDataLight_R(DataLight dataLight) {
        Integer num = dataLight.value;
        int intValue = num != null ? num.intValue() : 100;
        String str = dataLight.id;
        if (str == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("02");
        sb.append("01");
        String substring = str.substring(0, str.length() - 2);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        sb.append(substring);
        sb.append("14");
        String substring2 = str.substring(str.length() - 2);
        Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String).substring(startIndex)");
        sb.append(substring2);
        char[] cArr = new char[6];
        cArr[0] = dataLight.state == LightState.on ? (char) 1 : (char) 0;
        cArr[1] = (char) (intValue / 10);
        sb.append(HandlerCan.Companion.convertToHex(cArr, false));
        Timber.forest.d("generate JZ36 DM:" + ((Object) sb), new Object[0]);
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        return sb2;
    }

    private final boolean R0(DataMaster dataMaster, String str, Integer num) {
        Integer num2;
        DataLight lightData = dataMaster.myLights.getLightData(str);
        if (lightData == null || ((num2 = lightData.dimOffset) != null && Intrinsics.areEqual(num2, num))) {
            return false;
        }
        lightData.dimOffset = num;
        return true;
    }

    /* renamed from: S */
    private final String encodeDataMyThing_S(DataMyThing dataMyThing) {
        StringBuilder sb = new StringBuilder();
        sb.append("02");
        sb.append("01");
        String str = dataMyThing.id;
        Intrinsics.checkNotNull(str);
        Intrinsics.checkNotNull(dataMyThing.id);
        String substring = str.substring(0, r2.length() - 2);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        sb.append(substring);
        sb.append("1d");
        String str2 = dataMyThing.id;
        Intrinsics.checkNotNull(str2);
        Intrinsics.checkNotNull(dataMyThing.id);
        String substring2 = str2.substring(r2.length() - 2);
        Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String).substring(startIndex)");
        sb.append(substring2);
        char[] cArr = new char[6];
        Integer num = dataMyThing.dimOffset;
        Intrinsics.checkNotNull(num);
        cArr[0] = (char) num.intValue();
        sb.append(HandlerCan.Companion.convertToHex(cArr, false));
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        return sb2;
    }

    /* renamed from: T */
    private final String encodeDataLight_T(DataLight dataLight) {
        Integer num = dataLight.value;
        int intValue = num != null ? num.intValue() : 100;
        String str = dataLight.id;
        if (str == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("02");
        sb.append("01");
        String substring = str.substring(0, str.length() - 2);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        sb.append(substring);
        sb.append("01");
        String substring2 = str.substring(str.length() - 2);
        Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String).substring(startIndex)");
        sb.append(substring2);
        char[] cArr = new char[6];
        char brightnessLevel = (char) ((int) LightBrightness.instance.getBrightnessLevel(intValue));
        cArr[0] = brightnessLevel;
        if (dataLight.state == LightState.on) {
            cArr[0] = (char) (brightnessLevel + 128);
        }
        cArr[1] = (char) ((r3 * 10) - (r0 * 10));
        sb.append(HandlerCan.Companion.convertToHex(cArr, false));
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        return sb2;
    }

    /* renamed from: T0 */
    private final void sendCanMessageToCB(Context context, DataMaster dataMaster, String str, LightState lightState2) {
        DataGroup dataGroup = dataMaster.myLights.getDataGroup(str);
        if (dataGroup != null) {
            Iterator<String> it = dataGroup.lightsOrder.iterator();
            while (it.hasNext()) {
                String next = it.next();
                HandlerAircon companion = HandlerAircon.Companion.getInstance();
                companion.myLights.d(next);
                companion.myLightsV2.d(next);
                DataLight lightData = dataMaster.myLights.getLightData(next);
                if (lightData != null) {
                    lightData.state = lightState2;
                    sendCanMessageToCB(context, lightData);
                }
            }
        }
    }

    private final boolean U0(DataMaster dataMaster, String str, int i10, int i11, boolean z7, Integer num, Boolean bool, Boolean bool2, Boolean bool3) {
        Integer num2;
        Integer num3;
        Integer num4;
        Boolean bool4;
        Boolean bool5;
        Boolean bool6;
        DataMyThing thingData = dataMaster.myThings.getThingData(str);
        boolean z10 = false;
        if (thingData != null) {
            Integer num5 = thingData.value;
            if (num5 == null || num5.intValue() != i10) {
                thingData.value = Integer.valueOf(i10);
                if (z7) {
                    DataThingsSystem dataThingsSystem = dataMaster.myThings.system;
                    Intrinsics.checkNotNull(dataThingsSystem);
                    if (dataThingsSystem.numberClicks == null) {
                        DataThingsSystem dataThingsSystem2 = dataMaster.myThings.system;
                        Intrinsics.checkNotNull(dataThingsSystem2);
                        dataThingsSystem2.numberClicks = 0L;
                    }
                    DataThingsSystem dataThingsSystem3 = dataMaster.myThings.system;
                    Intrinsics.checkNotNull(dataThingsSystem3);
                    DataThingsSystem dataThingsSystem4 = dataMaster.myThings.system;
                    Intrinsics.checkNotNull(dataThingsSystem4);
                    Long l8 = dataThingsSystem4.numberClicks;
                    Intrinsics.checkNotNull(l8);
                    dataThingsSystem3.numberClicks = Long.valueOf(l8.longValue() + 1);
                    DataThingsSystem dataThingsSystem5 = dataMaster.myThings.system;
                    Intrinsics.checkNotNull(dataThingsSystem5);
                    dataThingsSystem5.lastUsedThingId = str;
                }
                z10 = true;
            }
            Integer num6 = thingData.dimPercent;
            if (num6 == null || num6 == null || num6.intValue() != i11) {
                thingData.dimPercent = Integer.valueOf(i11);
                z10 = true;
            }
            if (bool != null && ((bool6 = thingData.batteryLow) == null || bool6 != bool)) {
                thingData.batteryLow = bool;
                z10 = true;
            }
            if (bool2 != null && ((bool5 = thingData.RFLinkTimeout) == null || bool5 != bool2)) {
                thingData.RFLinkTimeout = bool2;
                z10 = true;
            }
            if (bool3 != null && ((bool4 = thingData.isCalibrated) == null || bool4 != bool3)) {
                thingData.isCalibrated = bool3;
                z10 = true;
            }
            Integer num7 = thingData.channelDipState;
            if (num7 != null && num7 != null && num7.intValue() == 3 && (num4 = thingData.value) != null && num4 != null && num4.intValue() == 0) {
                TypeIntrinsics.k(this.thingsMap).remove(thingData.id);
                TypeIntrinsics.k(this.X).remove(thingData.id);
            }
            if (z7 && num != null && (num2 = thingData.channelDipState) != null && ((num2 != null && num2.intValue() == 3) || ((num3 = thingData.channelDipState) != null && num3.intValue() == 10))) {
                int intValue = num.intValue();
                if (this.Y.containsKey(str)) {
                    Integer num8 = this.Y.get(str);
                    if (num8 == null || num8.intValue() != intValue) {
                        HandlerMonitor b10 = HandlerMonitor.Companion.b();
                        if (intValue == 0) {
                            b10.y(dataMaster, str, thingData.name, Events.GARAGE_DOOR_TRIGGER_ON_CLOSE);
                            Integer valueOf = Integer.valueOf(intValue);
                            ConcurrentHashMap<String, Integer> concurrentHashMap = this.Y;
                            Intrinsics.checkNotNull(str);
                            concurrentHashMap.put(str, valueOf);
                        } else if (intValue == 1) {
                            b10.y(dataMaster, str, thingData.name, Events.GARAGE_DOOR_TRIGGER_ON_OPEN);
                            Integer valueOf2 = Integer.valueOf(intValue);
                            ConcurrentHashMap<String, Integer> concurrentHashMap2 = this.Y;
                            Intrinsics.checkNotNull(str);
                            concurrentHashMap2.put(str, valueOf2);
                        }
                    }
                } else {
                    Integer valueOf3 = Integer.valueOf(intValue);
                    ConcurrentHashMap<String, Integer> concurrentHashMap3 = this.Y;
                    Intrinsics.checkNotNull(str);
                    concurrentHashMap3.put(str, valueOf3);
                }
            }
        }
        return z10;
    }

    private final boolean V0(DataMaster dataMaster, String str, Integer num) {
        Integer num2;
        DataMyThing thingData = dataMaster.myThings.getThingData(str);
        if (thingData == null || ((num2 = thingData.dimOffset) != null && Intrinsics.areEqual(num2, num))) {
            return false;
        }
        thingData.dimOffset = num;
        return true;
    }

    @NotNull
    public static final HandlerLights W() {
        return Companion.getInstance();
    }

    private final boolean X(DataLight dataLight) {
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(MyApp.appContextProvider.appContext());
            TreeMap<String, DataLight> treeMap = dataMaster.myLights.lights;
            Intrinsics.checkNotNull(treeMap);
            if (treeMap.containsKey(dataLight.id)) {
                TreeMap<String, DataLight> treeMap2 = dataMaster.myLights.lights;
                Intrinsics.checkNotNull(treeMap2);
                DataLight dataLight2 = treeMap2.get(dataLight.id);
                if (dataLight2 != null && dataLight2.thisIsRFDevice) {
                    return true;
                }
            }
            Unit unit = Unit.INSTANCE;
            return false;
        }
    }

    private final void X0(Context context, DataMaster dataMaster, String str, State state) {
        Integer num;
        Integer num2;
        DataGroupThing dataGroupThing = dataMaster.myThings.getDataGroupThing(str);
        if (dataGroupThing != null) {
            Iterator<String> it = dataGroupThing.thingsOrder.iterator();
            while (it.hasNext()) {
                DataMyThing thingData = dataMaster.myThings.getThingData(it.next());
                if (thingData != null) {
                    if (state == State.off) {
                        thingData.value = 0;
                    } else if (state == State.on) {
                        thingData.value = 100;
                    }
                    Integer num3 = thingData.channelDipState;
                    if (num3 != null && (((num3 != null && num3.intValue() == 3) || ((num = thingData.channelDipState) != null && num.intValue() == 10)) && (num2 = thingData.value) != null && num2 != null && num2.intValue() == 0)) {
                        TypeIntrinsics.k(this.thingsMap).remove(thingData.id);
                    }
                    l(context, thingData);
                }
            }
        }
    }

    private final boolean Y(DataMyThing dataMyThing) {
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(MyApp.appContextProvider.appContext());
            TreeMap<String, DataMyThing> treeMap = dataMaster.myThings.things;
            Intrinsics.checkNotNull(treeMap);
            if (treeMap.containsKey(dataMyThing.id)) {
                TreeMap<String, DataMyThing> treeMap2 = dataMaster.myThings.things;
                Intrinsics.checkNotNull(treeMap2);
                DataMyThing dataMyThing2 = treeMap2.get(dataMyThing.id);
                if (dataMyThing2 != null && dataMyThing2.thisIsRFDevice) {
                    return true;
                }
            }
            Unit unit = Unit.INSTANCE;
            return false;
        }
    }

    /* renamed from: Y0 */
    private final boolean isValidAlarmId(String str) {
        if (str != null) {
            return !(str.length() == 0) && StringsKt__StringsJVMKt.startsWith(str, "t", false, 2, null) && getNumberOfHexDigits(str) == 5;
        }
        return false;
    }

    /* renamed from: Z */
    private final boolean makeNewGroup(DataMaster dataMaster, String str, String str2) throws ExceptionUart {
        DataGroup dataGroup = dataMaster.myLights.getDataGroup(str);
        if (dataGroup != null) {
            dataGroup.name = str2;
            return true;
        }
        TreeMap<String, DataGroup> treeMap = dataMaster.myLights.groups;
        Intrinsics.checkNotNull(treeMap);
        if (treeMap.size() >= 10) {
            throw new ExceptionUart("makeNewGroup - to many light groups");
        }
        DataGroup dataGroup2 = new DataGroup(str);
        dataGroup2.name = str2;
        TreeMap<String, DataGroup> treeMap2 = dataMaster.myLights.groups;
        Intrinsics.checkNotNull(treeMap2);
        treeMap2.put(dataGroup2.id, dataGroup2);
        ArrayList<String> arrayList = dataMaster.myLights.groupsOrder;
        Intrinsics.checkNotNull(arrayList);
        ArrayList<String> arrayList2 = dataMaster.myLights.groupsOrder;
        Intrinsics.checkNotNull(arrayList2);
        int size = arrayList2.size();
        String str3 = dataGroup2.id;
        Intrinsics.checkNotNull(str3);
        arrayList.add(size, str3);
        return true;
    }

    /* renamed from: Z0 */
    private final boolean isValidGroupId(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        if (Intrinsics.areEqual(str, DataMyLights.DEFAULT_GROUP) || Intrinsics.areEqual(str, DataThings.DEFAULT_GROUP)) {
            return true;
        }
        return (StringsKt__StringsJVMKt.startsWith(str, "g", false, 2, null) || StringsKt__StringsJVMKt.startsWith(str, "m", false, 2, null)) && getNumberOfHexDigits(str) == 5;
    }

    /* renamed from: a0 */
    private final boolean makeNewGroup_(DataMaster dataMaster, String str, String str2) throws ExceptionUart {
        DataGroupThing dataGroupThing = dataMaster.myThings.getDataGroupThing(str);
        if (dataGroupThing != null) {
            dataGroupThing.name = str2;
            return true;
        }
        TreeMap<String, DataGroupThing> treeMap = dataMaster.myThings.groups;
        Intrinsics.checkNotNull(treeMap);
        if (treeMap.size() >= 10) {
            throw new ExceptionUart("makeNewGroup - to many thing groups");
        }
        DataGroupThing dataGroupThing2 = new DataGroupThing(str);
        dataGroupThing2.name = str2;
        dataGroupThing2.buttonType = "none";
        TreeMap<String, DataGroupThing> treeMap2 = dataMaster.myThings.groups;
        Intrinsics.checkNotNull(treeMap2);
        treeMap2.put(dataGroupThing2.id, dataGroupThing2);
        ArrayList<String> arrayList = dataMaster.myThings.groupsOrder;
        Intrinsics.checkNotNull(arrayList);
        ArrayList<String> arrayList2 = dataMaster.myThings.groupsOrder;
        Intrinsics.checkNotNull(arrayList2);
        int size = arrayList2.size();
        String str3 = dataGroupThing2.id;
        Intrinsics.checkNotNull(str3);
        arrayList.add(size, str3);
        return true;
    }

    /* renamed from: a1 */
    private final boolean isSceneId(String str) {
        if (!(str == null || str.length() == 0) && StringsKt__StringsJVMKt.startsWith(str, "s", false, 2, null)) {
            return Intrinsics.areEqual(str, DataMyScene.SCENE_MY_UNDO.id) || Intrinsics.areEqual(str, DataMyScene.SCENE_MY_GOODBYE.id) || Intrinsics.areEqual(str, DataMyScene.SCENE_MY_WELCOME.id) || Intrinsics.areEqual(str, DataMyScene.SCENE_MY_SUNSET.id) || Intrinsics.areEqual(str, DataMyScene.SCENE_MY_ECO.id) || getNumberOfHexDigits(str) == 5;
        }
        return false;
    }

    private final Integer b0(String str, String str2, Map<String, String> map, int i10, int i11) {
        String str3 = map.get(str2);
        if (str3 == null) {
            Timber.forest.d(str + " problem finding " + str2, new Object[0]);
            return null;
        }
        try {
            Integer valueOf = Integer.valueOf(str3);
            Intrinsics.checkNotNull(valueOf);
            if (valueOf.intValue() >= i10) {
                if (valueOf.intValue() <= i11) {
                    return valueOf;
                }
            }
        } catch (NumberFormatException e7) {
            AppFeatures.instance.logCriticalException(e7, str + " problem parsing value of " + str3);
        }
        return null;
    }

    private final String c0(String str, Map<String, String> map) {
        String str2 = map.get("id");
        if (str2 == null) {
            Timber.forest.d(str + " id is corrupt or not present", new Object[0]);
            return null;
        }
        if (str2.length() == 7) {
            if (!new Regex(hexRegx).matches(str2)) {
                Timber.forest.d(str + " id contains invalid characters " + str2, new Object[0]);
                return null;
            }
        } else {
            if (str2.length() != 26) {
                Timber.forest.d(str + " id is corrupt - wrong length " + str2, new Object[0]);
                return null;
            }
            if (!new Regex(f7175u0).matches(str2)) {
                Timber.forest.d(str + " hue light id contains invalid characters " + str2, new Object[0]);
            }
        }
        return str2;
    }

    private final String e0(String str, Map<String, String> map) {
        String str2 = map.get("id");
        if (str2 == null) {
            Timber.forest.d(str + " id is corrupt or not present", new Object[0]);
            return null;
        }
        if (str2.length() != 7) {
            Timber.forest.d(str + " id is corrupt - wrong length " + str2, new Object[0]);
            return null;
        }
        if (new Regex(hexRegx).matches(str2)) {
            return str2;
        }
        Timber.forest.d(str + " id contains invalid characters " + str2, new Object[0]);
        return null;
    }

    /* renamed from: h */
    private final void sendCanMessageToCB(Context context, DataLight dataLight) {
        synchronized (MyMasterData.class) {
            this.U.sendCanMessageToCB(context, N(MyMasterData.Companion.getDataMaster(context), dataLight));
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void h0(Context context, DataMaster dataMaster, String str, boolean z7, String str2, DataMyThing dataMyThing, int i10) {
        Boolean bool;
        b bVar = this.Z.get(str);
        if ((bVar != null ? bVar.a() : null) != null) {
            long uptime = CommonFuncs.getUptime();
            Long a = bVar.a();
            Intrinsics.checkNotNull(a);
            if (uptime <= a.longValue() + 14) {
                if (z7 || !(i10 == 0 || i10 == 1)) {
                    Timber.forest.d("processIncomingGarageV2CANMessage IGNORING incoming CAN", new Object[0]);
                    return;
                } else {
                    N0(str, str2, dataMyThing, i10, true);
                    Timber.forest.d("processIncomingGarageV2CANMessage UPDATING incoming CAN", new Object[0]);
                    return;
                }
            }
        }
        if (z7) {
            Timber.forest.d("processIncomingGarageV2CANMessage NOT UPDATING saved CAN because it's poll result", new Object[0]);
            bool = null;
        } else {
            Timber.forest.d("processIncomingGarageV2CANMessage UPDATING incoming CAN2", new Object[0]);
            N0(str, str2, dataMyThing, i10, false);
            s(dataMaster, str, false);
            bool = Boolean.FALSE;
        }
        r(dataMaster, str, dataMyThing.batteryLow);
        Integer num = dataMyThing.value;
        int intValue = num != null ? num.intValue() : 0;
        Integer num2 = dataMyThing.dimPercent;
        if (U0(dataMaster, str, intValue, num2 != null ? num2.intValue() : 0, true, Integer.valueOf(i10), dataMyThing.batteryLow, bool, dataMyThing.isCalibrated)) {
            Timber.forest.d("processIncomingGarageV2CANMessage BROADCASTING.....updateThingJson", new Object[0]);
            updateThingJson(context, dataMaster);
        }
    }

    private final void i(Context context, String str, LightState lightState2, Integer num) {
        DataLight dataLight = new DataLight();
        dataLight.id = str;
        dataLight.state = lightState2;
        dataLight.value = num;
        sendCanMessageToCB(context, dataLight);
    }

    private final void j(Context context, DataLight dataLight) {
        synchronized (MyMasterData.class) {
            String M = M(MyMasterData.Companion.getDataMaster(context), dataLight);
            if (M != null) {
                this.U.sendCanMessageToCB(context, M);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void k(Context context, String str, Integer num) {
        DataLight dataLight = new DataLight();
        dataLight.id = str;
        dataLight.dimOffset = num;
        j(context, dataLight);
    }

    private final void l(Context context, DataMyThing dataMyThing) {
        String generateSetThingsCanString = generateSetThingsCanString(dataMyThing);
        Timber.forest.d("JZ36 created: " + generateSetThingsCanString, new Object[0]);
        this.U.sendCanMessageToCB(context, generateSetThingsCanString);
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0128  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean l0(Context context, DataMaster dataMaster, DataScene dataScene) {
        Boolean bool;
        dataScene.lights = new HashMap<>();
        TreeMap<String, DataLight> treeMap = dataMaster.myLights.lights;
        Intrinsics.checkNotNull(treeMap);
        if (treeMap.size() > 0) {
            TreeMap<String, DataLight> treeMap2 = dataMaster.myLights.lights;
            Intrinsics.checkNotNull(treeMap2);
            Collection<DataLight> values = treeMap2.values();
            Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
            ArrayList<DataLight> arrayList = new ArrayList();
            for (DataLight dataLight : values) {
                if (dataLight != null) {
                    arrayList.add(dataLight);
                }
            }
            for (DataLight dataLight2 : arrayList) {
                DataLight dataLight3 = new DataLight();
                Intrinsics.checkNotNull(dataLight2);
                dataLight3.updateLightDataForScene(dataLight2);
                HashMap<String, DataLight> hashMap = dataScene.lights;
                Intrinsics.checkNotNull(hashMap);
                hashMap.put(dataLight3.id, dataLight3);
            }
        }
        dataScene.things = new HashMap<>();
        TreeMap<String, DataMyThing> treeMap3 = dataMaster.myThings.things;
        Intrinsics.checkNotNull(treeMap3);
        if (treeMap3.size() > 0) {
            TreeMap<String, DataMyThing> treeMap4 = dataMaster.myThings.things;
            Intrinsics.checkNotNull(treeMap4);
            Collection<DataMyThing> values2 = treeMap4.values();
            Intrinsics.checkNotNullExpressionValue(values2, "<get-values>(...)");
            ArrayList<DataMyThing> arrayList2 = new ArrayList();
            for (DataMyThing dataMyThing : values2) {
                if (dataMyThing != null) {
                    arrayList2.add(dataMyThing);
                }
            }
            for (DataMyThing dataMyThing2 : arrayList2) {
                DataMyThing dataMyThing3 = new DataMyThing();
                Intrinsics.checkNotNull(dataMyThing2);
                dataMyThing3.updateThingDataForScene(dataMyThing2);
                HashMap<String, DataMyThing> hashMap2 = dataScene.things;
                Intrinsics.checkNotNull(hashMap2);
                hashMap2.put(dataMyThing3.id, dataMyThing3);
            }
        }
        dataScene.aircons = new HashMap<>();
        if (dataMaster.aircons.size() > 0) {
            for (String str : dataMaster.aircons.keySet()) {
                DataAirconSystem dataAirconSystem = dataMaster.aircons.get(str);
                DataAirconSystem dataAirconSystem2 = new DataAirconSystem();
                if (dataAirconSystem != null) {
                    String str2 = dataScene.id;
                    if (str2 == null || !Intrinsics.areEqual(str2, "s0") || (bool = dataAirconSystem.info.quietNightModeIsRunning) == null) {
                        dataAirconSystem2.updateForSnapshot(dataAirconSystem);
                        HashMap<String, DataAirconSystem> hashMap3 = dataScene.aircons;
                        Intrinsics.checkNotNull(hashMap3);
                        hashMap3.put(str, dataAirconSystem2);
                    } else {
                        Intrinsics.checkNotNull(bool);
                        if (bool.booleanValue()) {
                            DataAirconInfo dataAirconInfo = dataAirconSystem2.info;
                            DataAirconInfo dataAirconInfo2 = dataAirconSystem.info;
                            dataAirconInfo.uid = dataAirconInfo2.uid;
                            dataAirconInfo.state = dataAirconInfo2.state;
                        }
                        HashMap<String, DataAirconSystem> hashMap32 = dataScene.aircons;
                        Intrinsics.checkNotNull(hashMap32);
                        hashMap32.put(str, dataAirconSystem2);
                    }
                }
            }
        }
        dataScene.monitors = new HashMap<>();
        HashMap<String, DataMonitor> hashMap4 = dataMaster.myMonitors.monitors;
        Intrinsics.checkNotNull(hashMap4);
        if (hashMap4.size() > 0) {
            HashMap<String, DataMonitor> hashMap5 = dataMaster.myMonitors.monitors;
            Intrinsics.checkNotNull(hashMap5);
            Collection<DataMonitor> values3 = hashMap5.values();
            Intrinsics.checkNotNullExpressionValue(values3, "<get-values>(...)");
            ArrayList<DataMonitor> arrayList3 = new ArrayList();
            for (DataMonitor dataMonitor : values3) {
                if (dataMonitor != null) {
                    arrayList3.add(dataMonitor);
                }
            }
            for (DataMonitor dataMonitor2 : arrayList3) {
                DataMonitor dataMonitor3 = new DataMonitor();
                Intrinsics.checkNotNull(dataMonitor2);
                dataMonitor3.updateMonitorDataForScene(dataMonitor2);
                HashMap<String, DataMonitor> hashMap6 = dataScene.monitors;
                Intrinsics.checkNotNull(hashMap6);
                hashMap6.put(dataMonitor3.id, dataMonitor3);
            }
        }
        dataScene.sonos = new HashMap<>();
        List<Sonos> value = this.f7179a0.u().getValue();
        Intrinsics.checkNotNull(value);
        if (value.size() > 0) {
            List<Sonos> value2 = this.f7179a0.u().getValue();
            Intrinsics.checkNotNull(value2);
            for (Sonos sonos : value2) {
                Sonos sonos2 = new Sonos(sonos.component1(), sonos.component2(), sonos.component3(), sonos.component4(), sonos.component5(), sonos.component6(), sonos.component7(), sonos.component8());
                HashMap<String, Sonos> hashMap7 = dataScene.sonos;
                Intrinsics.checkNotNull(hashMap7);
                hashMap7.put(sonos2.getUdn(), sonos2);
            }
        }
        if (!dataMaster.myScenes.addScene(dataScene)) {
            return false;
        }
        Timber.forest.d("setLightScene - success - saving - " + dataScene.id, new Object[0]);
        d0.Companion.b().e(context, dataMaster);
        return true;
    }

    private final void m(Context context, String str, int i10, int i11) {
        DataMyThing dataMyThing = new DataMyThing();
        dataMyThing.id = str;
        dataMyThing.value = Integer.valueOf(i10);
        dataMyThing.dimPercent = Integer.valueOf(i11);
        l(context, dataMyThing);
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x0168  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean m0(Context context, DataMaster dataMaster, DataAlarm dataAlarm, Boolean bool) {
        List emptyList;
        StringBuilder sb = new StringBuilder();
        HashMap<String, DataLight> hashMap = dataAlarm.lights;
        Intrinsics.checkNotNull(hashMap);
        Collection<DataLight> values = hashMap.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        ArrayList<DataLight> arrayList = new ArrayList();
        for (DataLight dataLight : values) {
            if (dataLight != null) {
                arrayList.add(dataLight);
            }
        }
        for (DataLight dataLight2 : arrayList) {
            dataLight2.state = LightState.on;
            Integer num = dataLight2.type;
            if (num != null && num.intValue() == 2) {
                dataLight2.value = 100;
            }
            Timber.forest.d("setLightAlarm2 - light id: " + dataLight2.id, new Object[0]);
            Intrinsics.checkNotNull(dataLight2);
            String encodeDataLight_T = encodeDataLight_T(dataLight2);
            sb.append(" ");
            sb.append(encodeDataLight_T);
        }
        Timber.forest.d("setLightAlarm2 - " + ((Object) sb), new Object[0]);
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        int length = sb2.length() - 1;
        int i10 = 0;
        boolean z7 = false;
        while (i10 <= length) {
            boolean z10 = Intrinsics.compare(sb2.charAt(!z7 ? i10 : length), 32) <= 0;
            if (z7) {
                if (!z10) {
                    break;
                }
                length--;
            } else if (z10) {
                i10++;
            } else {
                z7 = true;
            }
        }
        dataAlarm.canMessages = sb2.subSequence(i10, length + 1).toString();
        dataAlarm.activeDays = 127;
        if (!dataMaster.myLights.addAlarm(dataAlarm)) {
            return false;
        }
        Timber.forest.d("setLightAlarm2 - success - saving - " + dataAlarm.id, new Object[0]);
        LightDBStore.Companion.b().e(context, dataMaster);
        if (bool != null && bool.booleanValue()) {
            String str = dataAlarm.canMessages;
            Intrinsics.checkNotNull(str);
            List<String> split = new Regex(" ").split(str, 0);
            if (split.isEmpty()) {
                emptyList = CollectionsKt.emptyList();
                while (r13 < r12) {
                }
            } else {
                ListIterator<String> listIterator = split.listIterator(split.size());
                while (listIterator.hasPrevious()) {
                    if (!(listIterator.previous().length() == 0)) {
                        emptyList = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                        break;
                    }
                }
                emptyList = CollectionsKt.emptyList();
                for (String str2 : (String[]) emptyList.toArray(new String[0])) {
                    Timber.forest.d("setLightAlarm2 - sending " + str2, new Object[0]);
                    this.U.sendCanMessageToCB(context, str2);
                    String O = O(str2);
                    if (O != null) {
                        parseMessage(context, O, false);
                    }
                }
            }
        }
        return true;
    }

    private final void n(Context context, DataMyThing dataMyThing) {
        synchronized (MyMasterData.class) {
            String encodeDataMyThing_S = encodeDataMyThing_S(dataMyThing);
            if (encodeDataMyThing_S != null) {
                this.U.sendCanMessageToCB(context, encodeDataMyThing_S);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    private final boolean n0(Context context, DataMaster dataMaster, DataScene dataScene, Boolean bool) {
        boolean z7;
        String packageName = ActivityMain.Companion.getPackageName();
        if (packageName != null && StringsKt__StringsKt.startsWith$default(packageName, "zone10", false, 2, null)) {
            if (dataMaster.aircons.size() > 0) {
                AirconFunctions.Companion companion = AirconFunctions.Companion;
                TreeMap<String, DataAirconSystem> treeMap = dataMaster.aircons;
                z7 = companion.isNonDualZoneDevice(treeMap.get(treeMap.firstKey()));
            } else {
                z7 = false;
            }
            dataScene.summary = dataScene.generateSummaryForZone10e(dataMaster, Boolean.valueOf(z7));
        } else {
            dataScene.summary = dataScene.generateSummary(dataMaster);
        }
        if (!dataMaster.myScenes.addScene(dataScene)) {
            return false;
        }
        Timber.Forest forest = Timber.forest;
        forest.d("saveScene - success - saving - " + dataScene.id, new Object[0]);
        d0.Companion.b().e(context, dataMaster);
        if (bool != null && bool.booleanValue()) {
            DataScene V = V(context, f0(context, dataScene, dataMaster), dataMaster);
            String L = L(context, V);
            if (o0(context, L, V, dataMaster)) {
                forest.d("saveScene - runItNow - sending can: " + L, new Object[0]);
            }
            HandlerAircon.Companion.getInstance().checkAndRunSceneSchedule(V);
        }
        return true;
    }

    private final void o(Context context, String str, Integer num) {
        DataMyThing dataMyThing = new DataMyThing();
        dataMyThing.id = str;
        dataMyThing.dimOffset = num;
        n(context, dataMyThing);
    }

    /* renamed from: p */
    private final void addToGroup(DataMaster dataMaster, String str, DataLight dataLight, int i10) {
        DataGroup dataGroup;
        ArrayList<String> arrayList;
        DataMyLights dataMyLights = dataMaster.myLights;
        String str2 = dataLight.id;
        Intrinsics.checkNotNull(str2);
        String groupId = dataMyLights.getGroupId(str2);
        DataGroup dataGroup2 = dataMaster.myLights.getDataGroup(str);
        if (dataGroup2 == null) {
            if (dataMaster.myLights.numberGroups() == 0) {
                dataGroup2 = new DataGroup(DataMyLights.DEFAULT_GROUP);
                dataGroup2.name = "Living";
                TreeMap<String, DataGroup> treeMap = dataMaster.myLights.groups;
                Intrinsics.checkNotNull(treeMap);
                treeMap.put(dataGroup2.id, dataGroup2);
                ArrayList<String> arrayList2 = dataMaster.myLights.groupsOrder;
                Intrinsics.checkNotNull(arrayList2);
                ArrayList<String> arrayList3 = dataMaster.myLights.groupsOrder;
                Intrinsics.checkNotNull(arrayList3);
                int size = arrayList3.size();
                String str3 = dataGroup2.id;
                Intrinsics.checkNotNull(str3);
                arrayList2.add(size, str3);
            } else {
                dataGroup2 = dataMaster.myLights.getDataGroup(DataMyLights.DEFAULT_GROUP);
            }
        }
        if (dataGroup2 == null) {
            Timber.forest.d("Problem with addToGroup - no group with id " + str, new Object[0]);
            return;
        }
        if (i10 >= dataGroup2.lightsOrder.size()) {
            i10 = dataGroup2.lightsOrder.size();
        }
        if (!Intrinsics.areEqual(dataGroup2.id, groupId)) {
            dataGroup2.lightsOrder.add(i10, dataLight.id);
            TreeMap<String, DataLight> treeMap2 = dataMaster.myLights.lights;
            Intrinsics.checkNotNull(treeMap2);
            treeMap2.put(dataLight.id, dataLight);
            if (groupId == null || (dataGroup = dataMaster.myLights.getDataGroup(groupId)) == null || (arrayList = dataGroup.lightsOrder) == null) {
                return;
            }
            arrayList.remove(dataLight.id);
            return;
        }
        DataMyLights dataMyLights2 = dataMaster.myLights;
        String str4 = dataLight.id;
        Intrinsics.checkNotNull(str4);
        int lightPosition = dataMyLights2.getLightPosition(str4);
        if (i10 >= dataGroup2.lightsOrder.size()) {
            i10 = dataGroup2.lightsOrder.size() - 1;
        }
        if (i10 < 0) {
            Timber.forest.d("addToGroup something has gone wrong with moving", new Object[0]);
        } else {
            new CommonFuncs().moveItem(dataGroup2.lightsOrder, lightPosition, i10);
        }
    }

    /* renamed from: q */
    private final void addToGroup(DataMaster dataMaster, String str, DataMyThing dataMyThing, int i10) {
        DataGroupThing dataGroupThing;
        ArrayList<String> arrayList;
        DataThings dataThings = dataMaster.myThings;
        String str2 = dataMyThing.id;
        Intrinsics.checkNotNull(str2);
        String groupId = dataThings.getGroupId(str2);
        DataGroupThing dataGroupThing2 = dataMaster.myThings.getDataGroupThing(str);
        if (dataGroupThing2 == null) {
            if (dataMaster.myThings.numberGroups() == 0) {
                dataGroupThing2 = new DataGroupThing(DataThings.DEFAULT_GROUP);
                dataGroupThing2.name = "Group 1";
                dataGroupThing2.buttonType = "none";
                TreeMap<String, DataGroupThing> treeMap = dataMaster.myThings.groups;
                Intrinsics.checkNotNull(treeMap);
                treeMap.put(dataGroupThing2.id, dataGroupThing2);
                ArrayList<String> arrayList2 = dataMaster.myThings.groupsOrder;
                Intrinsics.checkNotNull(arrayList2);
                ArrayList<String> arrayList3 = dataMaster.myThings.groupsOrder;
                Intrinsics.checkNotNull(arrayList3);
                int size = arrayList3.size();
                String str3 = dataGroupThing2.id;
                Intrinsics.checkNotNull(str3);
                arrayList2.add(size, str3);
            } else {
                dataGroupThing2 = dataMaster.myThings.getDataGroupThing(DataThings.DEFAULT_GROUP);
            }
        }
        if (dataGroupThing2 == null) {
            Timber.forest.d("Problem with addToGroup - no group with id " + str, new Object[0]);
            return;
        }
        if (i10 >= dataGroupThing2.thingsOrder.size()) {
            i10 = dataGroupThing2.thingsOrder.size();
        }
        if (!Intrinsics.areEqual(dataGroupThing2.id, groupId)) {
            dataGroupThing2.thingsOrder.add(i10, dataMyThing.id);
            TreeMap<String, DataMyThing> treeMap2 = dataMaster.myThings.things;
            Intrinsics.checkNotNull(treeMap2);
            treeMap2.put(dataMyThing.id, dataMyThing);
            if (groupId == null || (dataGroupThing = dataMaster.myThings.getDataGroupThing(groupId)) == null || (arrayList = dataGroupThing.thingsOrder) == null) {
                return;
            }
            arrayList.remove(dataMyThing.id);
            return;
        }
        DataThings dataThings2 = dataMaster.myThings;
        String str4 = dataMyThing.id;
        Intrinsics.checkNotNull(str4);
        int thingPosition = dataThings2.getThingPosition(str4);
        if (i10 >= dataGroupThing2.thingsOrder.size()) {
            i10 = dataGroupThing2.thingsOrder.size() - 1;
        }
        if (i10 < 0) {
            Timber.forest.d("addToGroup something has gone wrong with moving", new Object[0]);
        } else {
            new CommonFuncs().moveItem(dataGroupThing2.thingsOrder, thingPosition, i10);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void r(DataMaster dataMaster, String str, Boolean bool) {
        Long l8;
        String str2;
        DataMyThing thingData = dataMaster.myThings.getThingData(str);
        if (thingData != null) {
            if (thingData.batteryLow == null) {
                Intrinsics.checkNotNull(bool);
                if (!bool.booleanValue()) {
                    Boolean bool2 = thingData.batteryLow;
                    if (bool2 == null || bool2 == bool) {
                        return;
                    }
                }
            }
            Intrinsics.checkNotNull(bool);
            if (bool.booleanValue()) {
                str2 = "AA152 occurred: " + str;
                thingData.lowBatteryTimeStamp = Long.valueOf(System.currentTimeMillis());
                this.f7180b0.logEvent(FirebaseAnalyticsLog.a.RF, "AA152_set", str2, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 8) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 16) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 32) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 64) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 128) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 256) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 512) != 0 ? null : null);
            } else {
                if (thingData.lowBatteryTimeStamp != null) {
                    long currentTimeMillis = System.currentTimeMillis();
                    Long l10 = thingData.lowBatteryTimeStamp;
                    Intrinsics.checkNotNull(l10);
                    l8 = Long.valueOf(currentTimeMillis - l10.longValue());
                } else {
                    l8 = null;
                }
                if (l8 != null) {
                    str2 = "AA152 cleared: " + str + " after " + AppFeatures.formatDuration(l8.longValue());
                } else {
                    str2 = "AA152 cleared: " + str;
                }
                this.f7180b0.logEvent(FirebaseAnalyticsLog.a.RF, "AA152_clear", str2, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 8) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 16) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 32) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 64) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 128) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 256) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 512) != 0 ? null : null);
            }
            Timber.forest.d("%s", str2);
        }
    }

    private final void s(DataMaster dataMaster, String str, boolean z7) {
        Long l8;
        String str2;
        DataMyThing thingData = dataMaster.myThings.getThingData(str);
        if (thingData != null) {
            Boolean bool = thingData.RFLinkTimeout;
            if (!(bool == null && z7) && (bool == null || bool == Boolean.valueOf(z7))) {
                return;
            }
            if (z7) {
                str2 = "AA153 occurred: " + str;
                thingData.RFLinkTimeoutTimeStamp = Long.valueOf(System.currentTimeMillis());
                this.f7180b0.logEvent(FirebaseAnalyticsLog.a.RF, "AA153_set", str2, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 8) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 16) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 32) != 0 ? null : thingData.id, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 64) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 128) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 256) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 512) != 0 ? null : null);
            } else {
                String str3 = null;
                if (thingData.RFLinkTimeoutTimeStamp != null) {
                    long currentTimeMillis = System.currentTimeMillis();
                    Long l10 = thingData.RFLinkTimeoutTimeStamp;
                    Intrinsics.checkNotNull(l10);
                    l8 = Long.valueOf(currentTimeMillis - l10.longValue());
                } else {
                    l8 = null;
                }
                if (l8 != null) {
                    str3 = AppFeatures.formatDuration(l8.longValue());
                    str2 = "AA153 cleared: " + str + " after " + AppFeatures.formatDuration(l8.longValue());
                } else {
                    str2 = "AA153 cleared: " + str;
                }
                this.f7180b0.logEvent(FirebaseAnalyticsLog.a.RF, "AA153_clear", str2, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 8) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 16) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 32) != 0 ? null : thingData.id, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 64) != 0 ? null : str3, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 128) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 256) != 0 ? null : null, (PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW & 512) != 0 ? null : null);
            }
            Timber.forest.d("%s", str2);
        }
    }

    private final boolean t0(Context context, DataMaster dataMaster, DataLight dataLight, boolean z7) throws ExceptionUart {
        boolean z10;
        DataLight lightData = dataMaster.myLights.getLightData(dataLight.id);
        if (lightData == null) {
            throw new ExceptionUart("Invalid light id received");
        }
        boolean z11 = false;
        if (dataLight.state == null) {
            dataLight.state = lightData.state;
            z10 = false;
        } else {
            z10 = true;
        }
        if (dataLight.value == null) {
            dataLight.value = lightData.value;
        } else {
            z10 = true;
        }
        String str = dataLight.name;
        if (str != null) {
            int length = str.length() - 1;
            int i10 = 0;
            boolean z12 = false;
            while (i10 <= length) {
                boolean z13 = Intrinsics.compare(str.charAt(!z12 ? i10 : length), 32) <= 0;
                if (z12) {
                    if (!z13) {
                        break;
                    }
                    length--;
                } else if (z13) {
                    i10++;
                } else {
                    z12 = true;
                }
            }
            if (Intrinsics.areEqual(str.subSequence(i10, length + 1).toString(), "") || Intrinsics.areEqual(str, "")) {
                throw new ExceptionUart("setLightName - name can't be empty");
            }
            if (str.length() > 12) {
                throw new ExceptionUart("setLightName - name too long");
            }
            Timber.forest.d("setLightName - ID : " + dataLight.id + " Name : " + dataLight.name, new Object[0]);
            DataMyLights dataMyLights = dataMaster.myLights;
            String str2 = dataLight.id;
            String str3 = dataLight.name;
            Intrinsics.checkNotNull(str3);
            if (dataMyLights.updateLightName(str2, str3)) {
                TreeMap<String, DataAlarm> treeMap = dataMaster.myLights.alarms;
                if (treeMap != null) {
                    Collection<DataAlarm> values = treeMap.values();
                    Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                    ArrayList<DataAlarm> arrayList = new ArrayList();
                    for (DataAlarm dataAlarm : values) {
                        if (dataAlarm != null) {
                            arrayList.add(dataAlarm);
                        }
                    }
                    for (DataAlarm dataAlarm2 : arrayList) {
                        HashMap<String, DataLight> hashMap = dataAlarm2.lights;
                        if (hashMap != null) {
                            Collection<DataLight> values2 = hashMap.values();
                            Intrinsics.checkNotNullExpressionValue(values2, "<get-values>(...)");
                            ArrayList arrayList2 = new ArrayList();
                            for (DataLight dataLight2 : values2) {
                                if (dataLight2 != null) {
                                    arrayList2.add(dataLight2);
                                }
                            }
                            Iterator it = arrayList2.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                if (Intrinsics.areEqual(((DataLight) it.next()).id, dataLight.id)) {
                                    dataAlarm2.name = dataLight.name;
                                    break;
                                }
                            }
                        }
                    }
                }
                String str4 = lightData.moduleType;
                if (str4 != null && Intrinsics.areEqual(str4, DataLight.MODULE_TYPE_STRING_HUE)) {
                    HandlerHue.Companion.getInstance().A(dataMaster, lightData.idOnHueBridge, dataLight.name, HandlerHue.b.HUE_LIGHT);
                }
                z11 = true;
            }
        }
        if (z10) {
            if (z7) {
                HandlerAircon companion = HandlerAircon.Companion.getInstance();
                companion.myLights.d(dataLight.id);
                companion.myLightsV2.d(dataLight.id);
            }
            i(context, dataLight.id, dataLight.state, dataLight.value);
            if (updateLight(dataMaster, dataLight.id, dataLight.state, dataLight.value, true)) {
                return true;
            }
        }
        return z11;
    }

    @JvmStatic
    public static final void u() {
        Companion.destroy();
    }

    /* renamed from: v */
    private final int getNumberOfHexDigits(String str) {
        List emptyList;
        List<String> split = new Regex("").split(str, 0);
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
        int i10 = 0;
        for (String str2 : (String[]) emptyList.toArray(new String[0])) {
            if (new Regex(hexRegx).matches(str2)) {
                i10++;
            }
        }
        return i10;
    }

    private final boolean w(Context context, DataMaster dataMaster, String str) throws ExceptionUart {
        if (!dataMaster.myLights.removeAlarm(str)) {
            throw new ExceptionUart("Alarm with id " + str + " not found, so can't be deleted");
        }
        LightDBStore.Companion.b().e(context, dataMaster);
        new DataAlarm().id = str;
        ArrayList<String> arrayList = dataMaster.myLights.alarmsOrder;
        Intrinsics.checkNotNull(arrayList);
        if (arrayList.remove(str)) {
            return true;
        }
        Timber.forest.d("Failed to delete alarm from the order, alarm ID possibly not found", new Object[0]);
        return true;
    }

    private final boolean w0(Context context, DataMaster dataMaster, DataLight dataLight) throws ExceptionUart {
        boolean z7;
        DataLight lightData = dataMaster.myLights.getLightData(dataLight.id);
        if (lightData == null) {
            throw new ExceptionUart("Invalid light id received");
        }
        if (dataLight.dimOffset == null) {
            dataLight.dimOffset = lightData.dimOffset;
            z7 = false;
        } else {
            z7 = true;
        }
        if (z7) {
            k(context, dataLight.id, dataLight.dimOffset);
            if (R0(dataMaster, dataLight.id, dataLight.dimOffset)) {
                return true;
            }
        }
        return false;
    }

    private final void x(DataMaster dataMaster, DataGroup dataGroup) {
        if (dataGroup == null || Intrinsics.areEqual(dataGroup.id, DataMyLights.DEFAULT_GROUP)) {
            return;
        }
        DataGroup dataGroup2 = dataMaster.myLights.getDataGroup(DataMyLights.DEFAULT_GROUP);
        Iterator<String> it = dataGroup.lightsOrder.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            String next = it.next();
            Intrinsics.checkNotNull(dataGroup2);
            ArrayList<String> arrayList = dataGroup2.lightsOrder;
            arrayList.add(arrayList.size(), next);
            it.remove();
        }
        TreeMap<String, DataGroup> treeMap = dataMaster.myLights.groups;
        Intrinsics.checkNotNull(treeMap);
        treeMap.remove(dataGroup.id);
        ArrayList<String> arrayList2 = dataMaster.myLights.groupsOrder;
        Intrinsics.checkNotNull(arrayList2);
        String str = dataGroup.id;
        Intrinsics.checkNotNull(str);
        if (arrayList2.remove(str)) {
            return;
        }
        Timber.forest.d("Group was not found during removing process", new Object[0]);
    }

    private final void y(DataMaster dataMaster, DataGroupThing dataGroupThing) {
        ArrayList<String> arrayList;
        if (dataGroupThing == null || Intrinsics.areEqual(dataGroupThing.id, DataThings.DEFAULT_GROUP)) {
            return;
        }
        DataGroupThing dataGroupThing2 = dataMaster.myThings.getDataGroupThing(DataThings.DEFAULT_GROUP);
        Iterator<String> it = dataGroupThing.thingsOrder.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            String next = it.next();
            if (dataGroupThing2 != null && (arrayList = dataGroupThing2.thingsOrder) != null) {
                arrayList.add(arrayList.size(), next);
            }
            it.remove();
        }
        TreeMap<String, DataGroupThing> treeMap = dataMaster.myThings.groups;
        Intrinsics.checkNotNull(treeMap);
        treeMap.remove(dataGroupThing.id);
        ArrayList<String> arrayList2 = dataMaster.myThings.groupsOrder;
        Intrinsics.checkNotNull(arrayList2);
        String str = dataGroupThing.id;
        Intrinsics.checkNotNull(str);
        if (arrayList2.remove(str)) {
            return;
        }
        Timber.forest.d("Group was not found during removing process", new Object[0]);
    }

    private final boolean z(Context context, DataMaster dataMaster, String str) {
        if (!dataMaster.myScenes.removeScene(str)) {
            return false;
        }
        ArrayList<String> arrayList = dataMaster.myScenes.scenesOrder;
        Intrinsics.checkNotNull(arrayList);
        if (!arrayList.remove(str)) {
            Timber.forest.d("Failed to delete scene from the order, scene ID possibly not found", new Object[0]);
        }
        d0.Companion.b().e(context, dataMaster);
        return true;
    }

    /* renamed from: A0 */
    public final int setLightScene(@NotNull Context context, @Nullable String str, boolean z7) throws ExceptionUart {
        Object obj;
        boolean n02;
        Intrinsics.checkNotNullParameter(context, "context");
        Timber.forest.d("setLightScene JSON received", new Object[0]);
        try {
            obj = this.V.fromJson(str, DataScene.class);
        } catch (JsonParseException e7) {
            e7.printStackTrace();
            obj = null;
        }
        if (obj == null) {
            throw new ExceptionUart("Invalid json message");
        }
        if (z7) {
            ((DataScene) obj).sanitiseData();
        }
        Timber.Forest forest = Timber.forest;
        forest.d("setLightScene received", new Object[0]);
        String str2 = ((DataScene) obj).id;
        if (!isSceneId(str2)) {
            throw new ExceptionUart("Invalid scene id");
        }
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            DataScene dataScene = DataMyScene.SCENE_MY_UNDO;
            if (!Intrinsics.areEqual(str2, dataScene.id)) {
                l0(context, dataMaster, dataScene);
            }
            if (z7) {
                if (((DataScene) obj).name != null) {
                    throw new ExceptionUart("name must not be changed");
                }
                DataScene scene = dataMaster.myScenes.getScene(str2);
                if (scene != null) {
                    ((DataScene) obj).name = scene.name;
                }
            }
            String str3 = ((DataScene) obj).name;
            if (str3 == null || str3.length() > 12) {
                if (str3 != null && str3.length() > 12) {
                    throw new ExceptionUart("name too long");
                }
                if (Intrinsics.areEqual(str2, DataMyScene.SCENE_MY_ECO.id) || Intrinsics.areEqual(str2, dataScene.id)) {
                    throw new ExceptionUart("Cannot rename these scenes");
                }
                DataScene dataScene2 = DataMyScene.SCENE_MY_GOODBYE;
                if (Intrinsics.areEqual(str2, dataScene2.id)) {
                    l0(context, dataMaster, dataScene2);
                    return WebServer.ACK;
                }
                DataScene dataScene3 = DataMyScene.SCENE_MY_WELCOME;
                if (Intrinsics.areEqual(str2, dataScene3.id)) {
                    l0(context, dataMaster, dataScene3);
                    return WebServer.ACK;
                }
                DataScene dataScene4 = DataMyScene.SCENE_MY_SUNSET;
                if (!Intrinsics.areEqual(str2, dataScene4.id)) {
                    throw new ExceptionUart("problem with name");
                }
                l0(context, dataMaster, dataScene4);
                return WebServer.ACK;
            }
            if (!(str3.length() == 0) || z7) {
                forest.d("setLightScene - ID : " + str2 + " Name : " + str3, new Object[0]);
                DataScene dataScene5 = new DataScene(str2, str3, "");
                if (z7 && ((DataScene) obj).id != null) {
                    TreeMap<String, DataScene> treeMap = dataMaster.myScenes.scenes;
                    Intrinsics.checkNotNull(treeMap);
                    DataScene dataScene6 = treeMap.get(((DataScene) obj).id);
                    if (dataScene6 == null) {
                        return WebServer.ACK;
                    }
                    DataScene.update$default(dataScene5, null, dataScene6, null, false, 8, null);
                }
                DataScene.update$default(dataScene5, null, (DataScene) obj, null, false, 8, null);
                n02 = n0(context, dataMaster, dataScene5, ((DataScene) obj).runNow);
            } else {
                forest.d("setLightScene - ID : " + str2 + " deleting.", new Object[0]);
                n02 = z(context, dataMaster, str2);
            }
            if (!n02) {
                Unit unit = Unit.INSTANCE;
                return WebServer.NACK;
            }
            updateLightJson(context, dataMaster);
            updateThingJson(context, dataMaster);
            return WebServer.ACK;
        }
    }

    /* renamed from: B0 */
    public final int setLightToGroup(@Nullable Context context, @NotNull Map<String, String> params) throws ExceptionUart {
        int i10;
        Intrinsics.checkNotNullParameter(params, "params");
        Timber.Forest forest = Timber.forest;
        forest.d("setLightToGroup received", new Object[0]);
        String c02 = c0("setLightToGroup", params);
        if (c02 == null) {
            throw new ExceptionUart("setLightToGroup - invalid light id");
        }
        String str = params.get("groupId");
        if (str == null) {
            throw new ExceptionUart("setLightToGroup - invalid id group id");
        }
        Integer b02 = b0("setLightToGroup", "position", params, 1, 100);
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            if (b02 == null) {
                throw new ExceptionUart("setLightToGroup - invalid light position");
            }
            Integer valueOf = Integer.valueOf(b02.intValue() - 1);
            forest.d("setLightToGroup - ID : " + c02 + " Group id : " + str + " Position : " + valueOf, new Object[0]);
            DataLight lightData = dataMaster.myLights.getLightData(c02);
            if (lightData == null) {
                throw new ExceptionUart("setLightToGroup - invalid light id");
            }
            addToGroup(dataMaster, str, lightData, valueOf.intValue());
            updateLightJson(context, dataMaster);
            i10 = WebServer.ACK;
        }
        return i10;
    }

    /* renamed from: C0 */
    public final int setLightToNewGroup(@Nullable Context context, @NotNull Map<String, String> params) throws ExceptionUart {
        Intrinsics.checkNotNullParameter(params, "params");
        Timber.Forest forest = Timber.forest;
        forest.d("setLightToNewGroup received", new Object[0]);
        String c02 = c0("setLightToNewGroup", params);
        if (c02 == null) {
            throw new ExceptionUart("Invalid light id");
        }
        String str = params.get("groupId");
        if (!isValidGroupId(str)) {
            throw new ExceptionUart("Invalid group groupId");
        }
        String str2 = params.get("groupName");
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            if (str2 == null || str2.length() > 12) {
                forest.d("setLightToNewGroup - problem with groupName", new Object[0]);
                Unit unit = Unit.INSTANCE;
                return WebServer.NACK;
            }
            if (!makeNewGroup(dataMaster, str, str2)) {
                throw new ExceptionUart("Failed to create new group");
            }
            forest.d("setLightToNewGroup - ID : " + c02 + " Group id : " + str + " Name : " + str2, new Object[0]);
            DataLight lightData = dataMaster.myLights.getLightData(c02);
            if (lightData == null) {
                throw new ExceptionUart("Can't find light");
            }
            addToGroup(dataMaster, str, lightData, 1);
            updateLightJson(context, dataMaster);
            return WebServer.ACK;
        }
    }

    /* renamed from: D */
    public final boolean isValidLight(@NotNull DataMaster masterData, @Nullable String str) {
        DataMyLights.b bVar;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        DataLight lightData = masterData.myLights.getLightData(str);
        boolean z7 = false;
        if (lightData == null) {
            return false;
        }
        Iterator<DataMyLights.b> it = masterData.myLights.backupLights.iterator();
        while (true) {
            if (!it.hasNext()) {
                bVar = null;
                break;
            }
            bVar = it.next();
            if (Intrinsics.areEqual(bVar.id, str)) {
                break;
            }
        }
        if (bVar == null) {
            bVar = new DataMyLights.b();
            z7 = true;
        }
        bVar.id = str;
        bVar.name = lightData.name;
        DataMyLights dataMyLights = masterData.myLights;
        Intrinsics.checkNotNull(str);
        bVar.groupId = dataMyLights.getGroupId(str);
        if (z7) {
            masterData.myLights.backupLights.add(bVar);
        }
        masterData.myLights.removeLight(str);
        return true;
    }

    /* renamed from: D0 */
    public final int setLights(@NotNull Context context, @Nullable String str) throws ExceptionUart {
        Intrinsics.checkNotNullParameter(context, "context");
        boolean z7 = false;
        Timber.forest.d("setLight JSON received", new Object[0]);
        try {
            HashMap hashMap = (HashMap) this.V.fromJson(str, new c().getType());
            if (hashMap == null || hashMap.size() == 0) {
                throw new ExceptionUart("Invalid json received");
            }
            ArrayList arrayList = new ArrayList();
            synchronized (MyMasterData.class) {
                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
                for (Map.Entry entry : hashMap.entrySet()) {
                    String str2 = (String) entry.getKey();
                    DataLight dataLight = (DataLight) entry.getValue();
                    dataLight.id = str2;
                    try {
                        if (t0(context, dataMaster, dataLight, true)) {
                            z7 = true;
                        }
                    } catch (ExceptionUart e7) {
                        String message = e7.getMessage();
                        if (message != null) {
                            arrayList.add(message);
                        }
                        AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
                    }
                }
                if (z7) {
                    updateLightJson(context, dataMaster);
                }
                if (arrayList.size() > 0) {
                    throw new ExceptionUart(arrayList.toString());
                }
                Unit unit = Unit.INSTANCE;
            }
            return WebServer.ACK;
        } catch (JsonParseException e10) {
            e10.printStackTrace();
            throw new ExceptionUart("Invalid json received");
        }
    }

    /* renamed from: E */
    public final boolean isValidThing(@NotNull DataMaster masterData, @Nullable String str) {
        DataThings.b bVar;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        DataMyThing thingData = masterData.myThings.getThingData(str);
        boolean z7 = false;
        if (thingData == null) {
            return false;
        }
        Iterator<DataThings.b> it = masterData.myThings.backupThings.iterator();
        while (true) {
            if (!it.hasNext()) {
                bVar = null;
                break;
            }
            bVar = it.next();
            if (Intrinsics.areEqual(bVar.id, str)) {
                break;
            }
        }
        if (bVar == null) {
            bVar = new DataThings.b();
            z7 = true;
        }
        bVar.id = str;
        bVar.name = thingData.name;
        DataThings dataThings = masterData.myThings;
        Intrinsics.checkNotNull(str);
        bVar.groupId = dataThings.getGroupId(str);
        bVar.buttonType = thingData.buttonType;
        if (z7) {
            masterData.myThings.backupThings.add(bVar);
        }
        masterData.myThings.removeThing(str);
        if (this.Y.contains(str)) {
            this.Y.remove(str);
        }
        return true;
    }

    /* renamed from: E0 */
    public final int setNewGroupThingName(@Nullable Context context, @NotNull Map<String, String> params) throws ExceptionUart {
        Intrinsics.checkNotNullParameter(params, "params");
        Timber.Forest forest = Timber.forest;
        forest.d("setNewGroupThingName received", new Object[0]);
        String str = params.get("id");
        if (!isValidGroupId(str)) {
            throw new ExceptionUart("setNewGroupThingName? invalid id");
        }
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            String str2 = params.get(AppMeasurementSdk.ConditionalUserProperty.NAME);
            if (str2 != null) {
                if (!(str2.length() == 0) && str2.length() <= 12) {
                    forest.d("setNewGroupThingName - ID : " + str + " Name : " + str2, new Object[0]);
                    if (makeNewGroup_(dataMaster, str, str2)) {
                        updateThingJson(context, dataMaster);
                        return WebServer.ACK;
                    }
                    Unit unit = Unit.INSTANCE;
                    return WebServer.NACK;
                }
            }
            if (str2 == null || str2.length() <= 12) {
                throw new ExceptionUart("setNewGroupThingName - problem with name");
            }
            throw new ExceptionUart("setNewGroupThingName - name too long");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0062  */
    /* renamed from: F */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean registerOrUpdateLight(@NotNull DataMaster masterData, @Nullable String lightId, boolean isRelayModule, @Nullable String moduleType, boolean isRFDevice) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        DataLight lightData = masterData.myLights.getLightData(lightId);
        boolean z7 = false;
        if (lightData != null) {
            if (!isRelayModule || lightData.value == null) {
                if (!isRelayModule && lightData.value == null) {
                    Timber.forest.d("Converting from a relay to a light", new Object[0]);
                    lightData.value = 80;
                    lightData.relay = null;
                }
                lightData.moduleType = moduleType;
                long defaultExpiry = 70;
                lightData.expiryTime = Long.valueOf(CommonFuncs.getUptime() + defaultExpiry);
                if (isRFDevice) {
                    lightData.expiryTime = Long.valueOf(CommonFuncs.getUptime() + defaultExpiry);
                } else {
                    lightData.expiryTime = Long.valueOf(CommonFuncs.getUptime() + nonRFExpiry);
                }
                lightData.thisIsRFDevice = isRFDevice;
                return z7;
            }
            Timber.forest.d("Converting from a light to a relay", new Object[0]);
            lightData.value = null;
            lightData.relay = Boolean.TRUE;
            z7 = true;
            lightData.moduleType = moduleType;
            long defaultExpiry2 = 70;
            lightData.expiryTime = Long.valueOf(CommonFuncs.getUptime() + defaultExpiry2);
            if (isRFDevice) {
            }
            lightData.thisIsRFDevice = isRFDevice;
            return z7;
        }
        DataLight dataLight = new DataLight();
        dataLight.id = lightId;
        dataLight.moduleType = moduleType;
        dataLight.thisIsRFDevice = isRFDevice;
        Iterator<DataMyLights.b> it = masterData.myLights.backupLights.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            DataMyLights.b next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            DataMyLights.b bVar = next;
            if (Intrinsics.areEqual(bVar.id, lightId)) {
                dataLight.name = bVar.name;
                dataLight.state = lightState;
                if (isRelayModule) {
                    dataLight.value = null;
                    dataLight.relay = Boolean.TRUE;
                } else {
                    dataLight.value = 80;
                    dataLight.relay = null;
                }
                addToGroup(masterData, bVar.groupId, dataLight, 99);
                it.remove();
                z7 = true;
            }
        }
        if (!z7) {
            dataLight.name = "Light " + (masterData.myLights.numberLights() + 1 + masterData.myLights.backupLights.size());
            dataLight.state = lightState;
            if (isRelayModule) {
                dataLight.value = null;
                dataLight.relay = Boolean.TRUE;
            } else {
                dataLight.value = 80;
                dataLight.relay = null;
            }
            addToGroup(masterData, "", dataLight, 99);
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0043  */
    /* renamed from: F0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int setThing(@NotNull Context context, @NotNull String incomingJson) throws ExceptionUart {
        Object obj;
        DataMyThing dataMyThing;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(incomingJson, "incomingJson");
        Timber.Forest forest = Timber.forest;
        forest.d("setThing JSON received", new Object[0]);
        try {
            obj = this.V.fromJson(incomingJson, DataMyThing.class);
        } catch (JsonIOException e7) {
            e = e7;
            obj = null;
        }
        try {
            forest.d("setThing json:" + incomingJson, new Object[0]);
        } catch (JsonIOException e10) {
            e = e10;
            AppFeatures.Error(AppFeatures.instance, e, null, 2, null);
            dataMyThing = (DataMyThing) obj;
            if ((dataMyThing != null ? dataMyThing.id : null) != null) {
            }
        }
        dataMyThing = (DataMyThing) obj;
        if ((dataMyThing != null ? dataMyThing.id : null) != null) {
            throw new ExceptionUart("Invalid json received");
        }
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            if (G0(context, dataMaster, (DataMyThing) obj)) {
                updateThingJson(context, dataMaster);
            }
            Unit unit = Unit.INSTANCE;
        }
        return WebServer.ACK;
    }

    /* renamed from: H0 */
    public final int setThingDimOffset(@NotNull Context context, @Nullable String str) throws ExceptionUart {
        Object obj;
        Intrinsics.checkNotNullParameter(context, "context");
        Timber.forest.d("setThingDimOffset JSON received", new Object[0]);
        try {
            obj = this.V.fromJson(str, DataMyThing.class);
        } catch (JsonIOException e7) {
            AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
            obj = null;
        }
        if (obj == null || ((DataMyThing) obj).id == null) {
            throw new ExceptionUart("Invalid json received");
        }
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            if (I0(context, dataMaster, (DataMyThing) obj)) {
                updateLightJson(context, dataMaster);
            }
            Unit unit = Unit.INSTANCE;
        }
        return WebServer.ACK;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0067 A[Catch: all -> 0x018f, TryCatch #0 {, blocks: (B:6:0x0009, B:8:0x002a, B:13:0x0036, B:15:0x0042, B:17:0x004c, B:18:0x0055, B:20:0x005b, B:25:0x0067, B:27:0x006d, B:32:0x0079, B:34:0x0085, B:36:0x008a, B:38:0x0090, B:43:0x009c, B:45:0x00a8, B:47:0x00af, B:48:0x00b8, B:49:0x00cc, B:51:0x00d2, B:54:0x00eb, B:56:0x012f, B:57:0x013a, B:59:0x0142, B:60:0x0148, B:62:0x0150, B:63:0x0156, B:65:0x015e, B:66:0x0164, B:68:0x016c, B:75:0x0177, B:76:0x018b), top: B:5:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00af A[Catch: all -> 0x018f, TryCatch #0 {, blocks: (B:6:0x0009, B:8:0x002a, B:13:0x0036, B:15:0x0042, B:17:0x004c, B:18:0x0055, B:20:0x005b, B:25:0x0067, B:27:0x006d, B:32:0x0079, B:34:0x0085, B:36:0x008a, B:38:0x0090, B:43:0x009c, B:45:0x00a8, B:47:0x00af, B:48:0x00b8, B:49:0x00cc, B:51:0x00d2, B:54:0x00eb, B:56:0x012f, B:57:0x013a, B:59:0x0142, B:60:0x0148, B:62:0x0150, B:63:0x0156, B:65:0x015e, B:66:0x0164, B:68:0x016c, B:75:0x0177, B:76:0x018b), top: B:5:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00d2 A[Catch: all -> 0x018f, TryCatch #0 {, blocks: (B:6:0x0009, B:8:0x002a, B:13:0x0036, B:15:0x0042, B:17:0x004c, B:18:0x0055, B:20:0x005b, B:25:0x0067, B:27:0x006d, B:32:0x0079, B:34:0x0085, B:36:0x008a, B:38:0x0090, B:43:0x009c, B:45:0x00a8, B:47:0x00af, B:48:0x00b8, B:49:0x00cc, B:51:0x00d2, B:54:0x00eb, B:56:0x012f, B:57:0x013a, B:59:0x0142, B:60:0x0148, B:62:0x0150, B:63:0x0156, B:65:0x015e, B:66:0x0164, B:68:0x016c, B:75:0x0177, B:76:0x018b), top: B:5:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0177 A[Catch: all -> 0x018f, TryCatch #0 {, blocks: (B:6:0x0009, B:8:0x002a, B:13:0x0036, B:15:0x0042, B:17:0x004c, B:18:0x0055, B:20:0x005b, B:25:0x0067, B:27:0x006d, B:32:0x0079, B:34:0x0085, B:36:0x008a, B:38:0x0090, B:43:0x009c, B:45:0x00a8, B:47:0x00af, B:48:0x00b8, B:49:0x00cc, B:51:0x00d2, B:54:0x00eb, B:56:0x012f, B:57:0x013a, B:59:0x0142, B:60:0x0148, B:62:0x0150, B:63:0x0156, B:65:0x015e, B:66:0x0164, B:68:0x016c, B:75:0x0177, B:76:0x018b), top: B:5:0x0009 }] */
    @NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String J(@Nullable Context context) {
        boolean z7;
        TreeMap<String, DataMyThing> treeMap;
        TreeMap<String, DataMyThing> treeMap2;
        Iterator it;
        boolean z10;
        if (context != null) {
            synchronized (MyMasterData.class) {
                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
                A(context, dataMaster);
                C(context, dataMaster);
                B(context, dataMaster);
                DataSystem dataSystem = new DataSystem();
                dataSystem.update(dataMaster.system);
                TreeMap<String, DataLight> treeMap3 = dataMaster.myLights.lights;
                int i10 = 1;
                if (treeMap3 == null || treeMap3.isEmpty()) {
                    Boolean bool = dataMaster.system.hasLights;
                    Boolean bool2 = Boolean.TRUE;
                    if (Intrinsics.areEqual(bool, bool2) || Intrinsics.areEqual(dataMaster.system.hasThingsLight, bool2)) {
                        Boolean bool3 = Boolean.FALSE;
                        dataSystem.hasLights = bool3;
                        dataSystem.hasThingsLight = bool3;
                        z7 = true;
                    }
                    treeMap = dataMaster.myThings.things;
                    if (treeMap != null || treeMap.isEmpty()) {
                        HashMap<String, DataSensor> hashMap = dataMaster.mySensors.sensors;
                        if ((hashMap == null || hashMap.isEmpty()) && Intrinsics.areEqual(dataMaster.system.hasThings, Boolean.TRUE)) {
                            dataSystem.hasThings = Boolean.FALSE;
                            z7 = true;
                        }
                    }
                    treeMap2 = dataMaster.myThings.things;
                    if ((treeMap2 != null || treeMap2.isEmpty()) && Intrinsics.areEqual(dataMaster.system.hasThingsBOG, Boolean.TRUE)) {
                        dataSystem.hasThingsBOG = Boolean.FALSE;
                        z7 = true;
                    }
                    if (z7) {
                        AirconDBStore.Companion.getInstance(context).update(context, dataSystem);
                    }
                    ArrayList arrayList = new ArrayList(dataMaster.moduleExpiryTime.keySet());
                    long uptime = CommonFuncs.getUptime();
                    it = arrayList.iterator();
                    z10 = false;
                    while (it.hasNext()) {
                        String str = (String) it.next();
                        Long l8 = dataMaster.moduleExpiryTime.get(str);
                        Intrinsics.checkNotNull(l8);
                        if (l8.longValue() < uptime) {
                            Timber.Forest forest = Timber.forest;
                            String str2 = LOG_NAME;
                            Object[] objArr = new Object[i10];
                            objArr[0] = "module expired, uid: " + str + ", expiry time: " + dataMaster.moduleExpiryTime.get(str) + ", time now: " + uptime;
                            forest.d(str2, objArr);
                            dataMaster.moduleExpiryTime.remove(str);
                            HashMap<String, DataModuleInfoSource> hashMap2 = dataMaster.system.versions;
                            Intrinsics.checkNotNull(hashMap2);
                            if (hashMap2.containsKey(str)) {
                                HashMap<String, DataModuleInfoSource> hashMap3 = dataMaster.system.versions;
                                Intrinsics.checkNotNull(hashMap3);
                                hashMap3.remove(str);
                                z10 = true;
                            }
                            if (dataMaster.listOfRM2WithInvalidDipSetting.contains(str)) {
                                dataMaster.listOfRM2WithInvalidDipSetting.remove(str);
                                z10 = true;
                            }
                            if (dataMaster.listOfNonConfiguredRM2.contains(str)) {
                                dataMaster.listOfNonConfiguredRM2.remove(str);
                                z10 = true;
                            }
                            if (dataMaster.listOfDMWithInvalidDipSetting.contains(str)) {
                                dataMaster.listOfDMWithInvalidDipSetting.remove(str);
                                z10 = true;
                            }
                            if (dataMaster.listOfNonConfiguredDM.contains(str)) {
                                dataMaster.listOfNonConfiguredDM.remove(str);
                                z10 = true;
                            }
                            i10 = 1;
                        }
                    }
                    if (z10) {
                        Timber.forest.d("versions or invalid list or nonconfigured list changed", new Object[0]);
                        HandlerJson.Companion.getInstance(context).processData(dataMaster, "handlerLights - version change");
                    }
                    Unit unit = Unit.INSTANCE;
                }
                z7 = false;
                treeMap = dataMaster.myThings.things;
                if (treeMap != null || treeMap.isEmpty()) {
                }
                treeMap2 = dataMaster.myThings.things;
                if (treeMap2 != null || treeMap2.isEmpty()) {
                    dataSystem.hasThingsBOG = Boolean.FALSE;
                    z7 = true;
                }
                if (z7) {
                }
                ArrayList arrayList2 = new ArrayList(dataMaster.moduleExpiryTime.keySet());
                long uptime2 = CommonFuncs.getUptime();
                it = arrayList2.iterator();
                z10 = false;
                while (it.hasNext()) {
                }
                if (z10) {
                }
                Unit unit2 = Unit.INSTANCE;
            }
        }
        return "0201000000000360000000000 0201000000236000000000000";
    }

    /* renamed from: J0 */
    public final int setThingToGroupThing(@Nullable Context context, @NotNull Map<String, String> params) throws ExceptionUart {
        int i10;
        Intrinsics.checkNotNullParameter(params, "params");
        Timber.Forest forest = Timber.forest;
        forest.d("setThingToGroup received", new Object[0]);
        String e02 = e0("setThingToGroup", params);
        if (e02 == null) {
            throw new ExceptionUart("setThingToGroup - invalid thing id");
        }
        String str = params.get("groupId");
        if (str == null) {
            throw new ExceptionUart("setThingToGroup - invalid id group id");
        }
        Integer b02 = b0("setThingToGroup", "position", params, 1, 100);
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            if (b02 == null) {
                throw new ExceptionUart("setThingToGroup - invalid light position");
            }
            Integer valueOf = Integer.valueOf(b02.intValue() - 1);
            forest.d("setThingToGroup - ID : " + e02 + " Group id : " + str + " Position : " + valueOf, new Object[0]);
            DataMyThing thingData = dataMaster.myThings.getThingData(e02);
            if (thingData == null) {
                throw new ExceptionUart("setThingToGroup - invalid light id");
            }
            addToGroup(dataMaster, str, thingData, valueOf.intValue());
            updateThingJson(context, dataMaster);
            i10 = WebServer.ACK;
        }
        return i10;
    }

    @NotNull
    public final String K(@NotNull String uid) {
        Intrinsics.checkNotNullParameter(uid, "uid");
        if (uid.length() != 5) {
            return "";
        }
        return "0801" + uid + "0236000000000000";
    }

    /* renamed from: K0 */
    public final int setThingToNewGroupThing(@Nullable Context context, @NotNull Map<String, String> params) throws ExceptionUart {
        Intrinsics.checkNotNullParameter(params, "params");
        Timber.Forest forest = Timber.forest;
        forest.d("setThingToNewGroupThing received", new Object[0]);
        String e02 = e0("setThingToNewGroupThing", params);
        if (e02 == null) {
            throw new ExceptionUart("Invalid thing id");
        }
        String str = params.get("groupId");
        if (!isValidGroupId(str)) {
            throw new ExceptionUart("Invalid group groupId");
        }
        String str2 = params.get("groupName");
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            if (str2 == null || str2.length() > 12) {
                forest.d("setThingToNewGroupThing - problem with groupName", new Object[0]);
                Unit unit = Unit.INSTANCE;
                return WebServer.NACK;
            }
            if (!makeNewGroup_(dataMaster, str, str2)) {
                throw new ExceptionUart("Failed to create new group thing");
            }
            forest.d("setThingToNewGroupThing - ID : " + e02 + " Group id : " + str + " Name : " + str2, new Object[0]);
            DataMyThing thingData = dataMaster.myThings.getThingData(e02);
            if (thingData == null) {
                throw new ExceptionUart("Can't find light");
            }
            addToGroup(dataMaster, str, thingData, 1);
            updateThingJson(context, dataMaster);
            return WebServer.ACK;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:191:0x014a  */
    @Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String L(@NotNull Context context, @Nullable DataScene dataScene) {
        String sb;
        Integer num;
        ActivityMain companion;
        Context context2 = context;
        Intrinsics.checkNotNullParameter(context2, "context");
        HandlerAircon companion2 = HandlerAircon.Companion.getInstance();
        HandlerCB companion3 = HandlerCB.Companion.getInstance();
        if (dataScene == null) {
            return null;
        }
        Boolean bool = dataScene.myTimeEnabled;
        int i10 = 1;
        if (bool != null) {
            Intrinsics.checkNotNull(bool);
            if (bool.booleanValue()) {
                StringBuilder sb2 = new StringBuilder();
                HashMap<String, DataLight> hashMap = dataScene.lights;
                if (hashMap != null) {
                    Intrinsics.checkNotNull(hashMap);
                    Collection<DataLight> values = hashMap.values();
                    Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                    ArrayList<DataLight> arrayList = new ArrayList();
                    for (DataLight dataLight : values) {
                        if (dataLight != null) {
                            arrayList.add(dataLight);
                        }
                    }
                    for (DataLight dataLight2 : arrayList) {
                        if (dataLight2.state == LightState.off) {
                            companion2.myLightsV2.d(dataLight2.id);
                            synchronized (MyMasterData.class) {
                                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context2);
                                sb2.append(" ");
                                Intrinsics.checkNotNull(dataLight2);
                                sb2.append(N(dataMaster, dataLight2));
                            }
                        } else {
                            companion2.myLightsV2.a(context2, dataLight2);
                        }
                    }
                }
                HashMap<String, DataMyThing> hashMap2 = dataScene.things;
                if (hashMap2 != null) {
                    Intrinsics.checkNotNull(hashMap2);
                    Collection<DataMyThing> values2 = hashMap2.values();
                    Intrinsics.checkNotNullExpressionValue(values2, "<get-values>(...)");
                    ArrayList<DataMyThing> arrayList2 = new ArrayList();
                    for (DataMyThing dataMyThing : values2) {
                        if (dataMyThing != null) {
                            arrayList2.add(dataMyThing);
                        }
                    }
                    for (DataMyThing dataMyThing2 : arrayList2) {
                        sb2.append(" ");
                        Intrinsics.checkNotNull(dataMyThing2);
                        sb2.append(generateSetThingsCanString(dataMyThing2));
                    }
                }
                String sb3 = sb2.toString();
                Intrinsics.checkNotNullExpressionValue(sb3, "toString(...)");
                int length = sb3.length() - 1;
                int i11 = 0;
                boolean z7 = false;
                while (i11 <= length) {
                    boolean z10 = Intrinsics.compare(sb3.charAt(!z7 ? i11 : length), 32) <= 0;
                    if (z7) {
                        if (!z10) {
                            break;
                        }
                        length--;
                    } else if (z10) {
                        i11++;
                    } else {
                        z7 = true;
                    }
                }
                sb = new StringBuilder(sb3.subSequence(i11, length + 1).toString()).toString();
                Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
                if (companion2.myLightsV2.c(context2) && (companion = ActivityMain.Companion.getInstance()) != null) {
                    companion.E2();
                    Unit unit = Unit.INSTANCE;
                }
            } else {
                StringBuilder sb4 = new StringBuilder();
                HashMap<String, DataLight> hashMap3 = dataScene.lights;
                if (hashMap3 != null) {
                    Intrinsics.checkNotNull(hashMap3);
                    Collection<DataLight> values3 = hashMap3.values();
                    Intrinsics.checkNotNullExpressionValue(values3, "<get-values>(...)");
                    ArrayList<DataLight> arrayList3 = new ArrayList();
                    for (DataLight dataLight3 : values3) {
                        if (dataLight3 != null) {
                            arrayList3.add(dataLight3);
                        }
                    }
                    for (DataLight dataLight4 : arrayList3) {
                        companion2.myLightsV2.d(dataLight4.id);
                        synchronized (MyMasterData.class) {
                            DataMaster dataMaster2 = MyMasterData.Companion.getDataMaster(context2);
                            Intrinsics.checkNotNull(dataLight4);
                            String N = N(dataMaster2, dataLight4);
                            sb4.append(" ");
                            sb4.append(N);
                        }
                    }
                }
                HashMap<String, DataMyThing> hashMap4 = dataScene.things;
                if (hashMap4 != null) {
                    Intrinsics.checkNotNull(hashMap4);
                    Collection<DataMyThing> values4 = hashMap4.values();
                    Intrinsics.checkNotNullExpressionValue(values4, "<get-values>(...)");
                    ArrayList<DataMyThing> arrayList4 = new ArrayList();
                    for (DataMyThing dataMyThing3 : values4) {
                        if (dataMyThing3 != null) {
                            arrayList4.add(dataMyThing3);
                        }
                    }
                    for (DataMyThing dataMyThing4 : arrayList4) {
                        synchronized (MyMasterData.class) {
                            TreeMap<String, DataMyThing> treeMap = MyMasterData.Companion.getDataMaster(context2).myThings.things;
                            DataMyThing dataMyThing5 = treeMap != null ? treeMap.get(dataMyThing4.id) : null;
                            if ((dataMyThing5 != null ? dataMyThing5.channelDipState : null) == null || ((num = dataMyThing5.channelDipState) != null && num.intValue() == 3)) {
                                Timber.forest.d("Ignoring this item/garage door in scene: " + dataMyThing4.id, new Object[0]);
                                Unit unit2 = Unit.INSTANCE;
                            } else {
                                Integer num2 = dataMyThing5.channelDipState;
                                if (num2 != null && num2.intValue() == 10) {
                                    Timber.forest.d("Ignoring this item/garage door in scene: " + dataMyThing4.id, new Object[0]);
                                    Unit unit22 = Unit.INSTANCE;
                                }
                                Timber.Forest forest = Timber.forest;
                                String str = LOG_NAME;
                                Object[] objArr = new Object[i10];
                                objArr[0] = "generateSetThingsCanString scene for " + dataMyThing4.id + " with value of " + dataMyThing4.value;
                                forest.d(str, objArr);
                                Intrinsics.checkNotNull(dataMyThing4);
                                String generateSetThingsCanString = generateSetThingsCanString(dataMyThing4);
                                sb4.append(" ");
                                sb4.append(generateSetThingsCanString);
                            }
                        }
                        i10 = 1;
                    }
                }
                String sb5 = sb4.toString();
                Intrinsics.checkNotNullExpressionValue(sb5, "toString(...)");
                int length2 = sb5.length() - 1;
                int i12 = 0;
                boolean z11 = false;
                while (i12 <= length2) {
                    boolean z12 = Intrinsics.compare(sb5.charAt(!z11 ? i12 : length2), 32) <= 0;
                    if (z11) {
                        if (!z12) {
                            break;
                        }
                        length2--;
                    } else if (z12) {
                        i12++;
                    } else {
                        z11 = true;
                    }
                }
                sb = new StringBuilder(sb5.subSequence(i12, length2 + 1).toString()).toString();
                Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
            }
        }
        String str2 = "";
        HashMap<String, DataAirconSystem> hashMap5 = dataScene.aircons;
        if (hashMap5 != null) {
            Intrinsics.checkNotNull(hashMap5);
            if (hashMap5.size() > 0) {
                StringBuilder sb6 = new StringBuilder();
                HashMap<String, DataAirconSystem> hashMap6 = dataScene.aircons;
                Intrinsics.checkNotNull(hashMap6);
                for (String str3 : hashMap6.keySet()) {
                    synchronized (MyMasterData.class) {
                        DataAirconSystem dataAirconSystem = MyMasterData.Companion.getDataMaster(context2).aircons.get(str3);
                        if (dataAirconSystem != null) {
                            HashMap<String, DataAirconSystem> hashMap7 = dataScene.aircons;
                            Intrinsics.checkNotNull(hashMap7);
                            DataAirconSystem dataAirconSystem2 = hashMap7.get(str3);
                            DataAirconInfo copy = dataAirconSystem.info.copy();
                            Intrinsics.checkNotNull(dataAirconSystem2);
                            DataAirconInfo.update$default(copy, dataAirconSystem2.info, null, false, 4, null);
                            copy.completeAirconData();
                            sb6.append(" ");
                            sb6.append(companion2.ecodeAirconSource(copy));
                            TreeMap<String, DataZone> treeMap2 = dataAirconSystem2.zones;
                            Intrinsics.checkNotNull(treeMap2);
                            for (String str4 : treeMap2.keySet()) {
                                TreeMap<String, DataZone> treeMap3 = dataAirconSystem.zones;
                                Intrinsics.checkNotNull(treeMap3);
                                DataZone dataZone = treeMap3.get(str4);
                                if (dataZone != null) {
                                    TreeMap<String, DataZone> treeMap4 = dataAirconSystem2.zones;
                                    Intrinsics.checkNotNull(treeMap4);
                                    DataZone dataZone2 = treeMap4.get(str4);
                                    DataZone copy2 = dataZone.copy();
                                    DataZone.update$default(copy2, dataZone2, null, null, false, 12, null);
                                    copy2.completeZoneData();
                                    Integer num3 = dataZone.following;
                                    if (num3 != null && (num3 == null || num3.intValue() != 0)) {
                                        TreeMap<String, DataZone> treeMap5 = dataAirconSystem2.zones;
                                        Intrinsics.checkNotNull(treeMap5);
                                        DataZone.a aVar = DataZone.Companion;
                                        DataZone dataZone3 = treeMap5.get(aVar.getZoneKey(dataZone.following));
                                        TreeMap<String, DataZone> treeMap6 = dataAirconSystem.zones;
                                        Intrinsics.checkNotNull(treeMap6);
                                        DataZone dataZone4 = treeMap6.get(aVar.getZoneKey(dataZone.following));
                                        if (dataZone3 != null && dataZone4 != null) {
                                            DataZone dataZone5 = new DataZone();
                                            DataZone.update$default(dataZone5, dataZone4, null, null, false, 12, null);
                                            dataZone5.setTemp = dataZone3.setTemp;
                                            dataZone5.value = dataZone3.value;
                                            DataAirconInfo dataAirconInfo = dataAirconSystem2.info;
                                            if (dataAirconInfo.cbFWRevMajor == null) {
                                                dataAirconInfo.cbFWRevMajor = dataAirconSystem.info.cbFWRevMajor;
                                            }
                                            if (dataAirconInfo.cbFWRevMinor == null) {
                                                dataAirconInfo.cbFWRevMinor = dataAirconSystem.info.cbFWRevMinor;
                                            }
                                            copy2.value = Integer.valueOf(HandlerAircon.Companion.getZoneOpenAmount(dataAirconSystem2, dataZone5));
                                        }
                                    }
                                    sb6.append(" ");
                                    sb6.append(companion3.encodeZone_j(dataAirconSystem.info.uid, copy2));
                                    sb6.append(" ");
                                    sb6.append(companion3.encodeZone_k(dataAirconSystem.info.uid, copy2));
                                }
                            }
                        }
                        Unit unit3 = Unit.INSTANCE;
                    }
                    context2 = context;
                }
                String sb7 = sb6.toString();
                Intrinsics.checkNotNullExpressionValue(sb7, "toString(...)");
                int length3 = sb7.length() - 1;
                boolean z13 = false;
                int i13 = 0;
                while (i13 <= length3) {
                    boolean z14 = Intrinsics.compare(sb7.charAt(!z13 ? i13 : length3), 32) <= 0;
                    if (z13) {
                        if (!z14) {
                            break;
                        }
                        length3--;
                    } else if (z14) {
                        i13++;
                    } else {
                        z13 = true;
                    }
                }
                str2 = new StringBuilder(sb7.subSequence(i13, length3 + 1).toString()).toString();
                Intrinsics.checkNotNullExpressionValue(str2, "toString(...)");
            }
        }
        String str5 = sb + " " + str2;
        int length4 = str5.length() - 1;
        boolean z15 = false;
        int i14 = 0;
        while (i14 <= length4) {
            boolean z16 = Intrinsics.compare(str5.charAt(!z15 ? i14 : length4), 32) <= 0;
            if (z15) {
                if (!z16) {
                    break;
                }
                length4--;
            } else if (z16) {
                i14++;
            } else {
                z15 = true;
            }
        }
        return str5.subSequence(i14, length4 + 1).toString();
    }

    /* renamed from: L0 */
    public final int setThings(@NotNull Context context, @Nullable String str) throws ExceptionUart {
        Intrinsics.checkNotNullParameter(context, "context");
        boolean z7 = false;
        Timber.forest.d("setThings JSON received", new Object[0]);
        try {
            HashMap hashMap = (HashMap) this.V.fromJson(str, new d().getType());
            if (hashMap == null || hashMap.size() == 0) {
                throw new ExceptionUart("setThings - Invalid json received");
            }
            ArrayList arrayList = new ArrayList();
            synchronized (MyMasterData.class) {
                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
                for (String str2 : hashMap.keySet()) {
                    DataMyThing dataMyThing = (DataMyThing) hashMap.get(str2);
                    Intrinsics.checkNotNull(dataMyThing);
                    dataMyThing.id = str2;
                    try {
                        if (G0(context, dataMaster, dataMyThing)) {
                            z7 = true;
                        }
                    } catch (ExceptionUart e7) {
                        arrayList.add(e7.getMessage());
                        AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
                    }
                }
                if (z7) {
                    updateThingJson(context, dataMaster);
                }
                if (arrayList.size() > 0) {
                    throw new ExceptionUart(arrayList.toString());
                }
                Unit unit = Unit.INSTANCE;
            }
            return WebServer.ACK;
        } catch (JsonParseException e10) {
            e10.printStackTrace();
            throw new ExceptionUart("setThings - Invalid json received");
        }
    }

    @Nullable
    public final String M(@NotNull DataMaster masterData, @NotNull DataLight dataLight) {
        String str;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        Intrinsics.checkNotNullParameter(dataLight, "dataLight");
        TreeMap<String, DataLight> treeMap = masterData.myLights.lights;
        Intrinsics.checkNotNull(treeMap);
        DataLight dataLight2 = treeMap.get(dataLight.id);
        if (dataLight2 == null || (str = dataLight2.moduleType) == null || !Intrinsics.areEqual(str, DataLight.MODULE_TYPE_STRING_DM)) {
            return null;
        }
        return encodeDataLight_Q(dataLight);
    }

    @Nullable
    public final String O(@NotNull String canMessage) {
        Intrinsics.checkNotNullParameter(canMessage, "canMessage");
        if (canMessage.length() <= 4) {
            return null;
        }
        String substring = canMessage.substring(4);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
        return "0102" + substring;
    }

    @NotNull
    public final String P() {
        return "0801000000236000000000000";
    }

    @NotNull
    /* renamed from: Q */
    public final String encodeDataLight_Q(@NotNull DataLight dataLight) {
        Intrinsics.checkNotNullParameter(dataLight, "dataLight");
        StringBuilder sb = new StringBuilder();
        sb.append("02");
        sb.append("01");
        String str = dataLight.id;
        Intrinsics.checkNotNull(str);
        Intrinsics.checkNotNull(dataLight.id);
        String substring = str.substring(0, r2.length() - 2);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        sb.append(substring);
        sb.append("1d");
        String str2 = dataLight.id;
        Intrinsics.checkNotNull(str2);
        Intrinsics.checkNotNull(dataLight.id);
        String substring2 = str2.substring(r2.length() - 2);
        Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String).substring(startIndex)");
        sb.append(substring2);
        char[] cArr = new char[6];
        Integer num = dataLight.dimOffset;
        Intrinsics.checkNotNull(num);
        cArr[0] = (char) num.intValue();
        sb.append(HandlerCan.Companion.convertToHex(cArr, false));
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        return sb2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0036, code lost:
    
        if (r9.booleanValue() == false) goto L10;
     */
    /* renamed from: Q0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean updateLight(@NotNull DataMaster masterData, @Nullable String str, @Nullable LightState lightState2, @Nullable Integer num, boolean z7) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        DataLight lightData = masterData.myLights.getLightData(str);
        boolean z10 = false;
        if (lightData == null) {
            return false;
        }
        Integer num2 = lightData.value;
        if (num2 == null) {
            Timber.forest.d("updateLight - not updating relay value for " + str, new Object[0]);
            Boolean bool = lightData.relay;
            if (bool != null) {
                Intrinsics.checkNotNull(bool);
            }
            lightData.relay = Boolean.TRUE;
            z10 = true;
        } else if (!Intrinsics.areEqual(num2, num)) {
            lightData.value = num;
            Boolean bool2 = lightData.relay;
            if (bool2 != null) {
                Intrinsics.checkNotNull(bool2);
                if (bool2.booleanValue()) {
                    lightData.relay = null;
                }
            }
            z10 = true;
        }
        LightState lightState3 = lightData.state;
        if (lightState3 != null && lightState3 == lightState2) {
            return z10;
        }
        lightData.state = lightState2;
        if (z7) {
            DataLightsSystem dataLightsSystem = masterData.myLights.system;
            Intrinsics.checkNotNull(dataLightsSystem);
            if (dataLightsSystem.numberClicks == null) {
                DataLightsSystem dataLightsSystem2 = masterData.myLights.system;
                Intrinsics.checkNotNull(dataLightsSystem2);
                dataLightsSystem2.numberClicks = 0L;
            }
            DataLightsSystem dataLightsSystem3 = masterData.myLights.system;
            Intrinsics.checkNotNull(dataLightsSystem3);
            DataLightsSystem dataLightsSystem4 = masterData.myLights.system;
            Intrinsics.checkNotNull(dataLightsSystem4);
            Long l8 = dataLightsSystem4.numberClicks;
            Intrinsics.checkNotNull(l8);
            dataLightsSystem3.numberClicks = Long.valueOf(l8.longValue() + 1);
            DataLightsSystem dataLightsSystem5 = masterData.myLights.system;
            Intrinsics.checkNotNull(dataLightsSystem5);
            dataLightsSystem5.lastUsedLightId = str;
        }
        HandlerAircon companion = HandlerAircon.Companion.getInstance();
        if (!companion.myLightsV2.e(str)) {
            companion.myLightsV2.d(str);
        }
        return true;
    }

    @NotNull
    /* renamed from: S0 */
    public final String updateLightJson(@Nullable Context context, @NotNull DataMaster masterData) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        Timber.Forest forest = Timber.forest;
        forest.d("updateLightJson", new Object[0]);
        masterData.myLights.updateGroupStates();
        forest.d("updateLightJson save to DB", new Object[0]);
        String e7 = LightDBStore.Companion.b().e(context, masterData);
        if (e7 != null) {
            forest.v("json updateLightJson:" + e7, new Object[0]);
            HandlerJson.Companion.getInstance(context).processData(masterData, "updateLightJson");
        } else {
            forest.d("Warning saveLightToDatabase generate a null json string", new Object[0]);
            AppFeatures.Error(AppFeatures.instance, new NullPointerException("Warning saveLightsToDatabase generate a null json string"), null, 2, null);
        }
        return e7;
    }

    @NotNull
    /* renamed from: U */
    public final String generateSetThingsCanString(@NotNull DataMyThing dataThing) {
        int i10;
        Intrinsics.checkNotNullParameter(dataThing, "dataThing");
        Integer num = dataThing.value;
        Integer num2 = dataThing.dimPercent;
        if (num == null) {
            CommonFuncs.logException(new RuntimeException("generateSetThingsCanString, error we have a null value"));
            return "";
        }
        if (num.intValue() == 0 || num.intValue() == 25) {
            i10 = 0;
        } else if (num.intValue() == 100 || num.intValue() == 75) {
            i10 = 1;
        } else {
            if (num.intValue() != 50) {
                CommonFuncs.logException(new RuntimeException("generateSetThingsCanString, unknown value " + num));
                return "";
            }
            i10 = 2;
        }
        StringBuilder sb = new StringBuilder();
        if (Y(dataThing)) {
            sb.append("08");
        } else {
            sb.append("02");
        }
        sb.append("01");
        String str = dataThing.id;
        Intrinsics.checkNotNull(str);
        String str2 = dataThing.id;
        Intrinsics.checkNotNull(str2);
        String substring = str.substring(0, str2.length() - 2);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        sb.append(substring);
        sb.append("14");
        String str3 = dataThing.id;
        Intrinsics.checkNotNull(str3);
        String str4 = dataThing.id;
        Intrinsics.checkNotNull(str4);
        String substring2 = str3.substring(str4.length() - 2);
        Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String).substring(startIndex)");
        sb.append(substring2);
        char[] cArr = new char[6];
        cArr[0] = (char) i10;
        if (num2 != null) {
            cArr[1] = (char) (num2.intValue() / 10);
        }
        sb.append(HandlerCan.Companion.convertToHex(cArr, false));
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        return sb2;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x011d  */
    @Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final DataScene V(@Nullable Context context, @Nullable DataScene dataScene, @NotNull DataMaster masterData) {
        HashMap<String, DataAirconSystem> hashMap;
        AirconMode airconMode;
        SystemState systemState;
        DataScene dataScene2 = dataScene;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (dataScene2 != null && (hashMap = dataScene2.aircons) != null) {
            Intrinsics.checkNotNull(hashMap);
            if (hashMap.size() > 0) {
                dataScene2 = dataScene.copy();
                HashMap<String, DataAirconSystem> hashMap2 = dataScene2.aircons;
                Intrinsics.checkNotNull(hashMap2);
                Iterator it = new ArrayList(hashMap2.keySet()).iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    DataAirconSystem dataAirconSystem = masterData.aircons.get(str);
                    HashMap<String, DataAirconSystem> hashMap3 = dataScene2.aircons;
                    Intrinsics.checkNotNull(hashMap3);
                    DataAirconSystem dataAirconSystem2 = hashMap3.get(str);
                    if (dataAirconSystem != null && dataAirconSystem2 != null) {
                        Boolean bool = dataAirconSystem.info.quietNightModeIsRunning;
                        if (bool != null) {
                            Intrinsics.checkNotNull(bool);
                            if (bool.booleanValue()) {
                                SystemState systemState2 = dataAirconSystem2.info.state;
                                if (systemState2 == null || systemState2 != (systemState = SystemState.off)) {
                                    HashMap<String, DataAirconSystem> hashMap4 = dataScene2.aircons;
                                    Intrinsics.checkNotNull(hashMap4);
                                    hashMap4.remove(str);
                                } else {
                                    HandlerAircon companion = HandlerAircon.Companion.getInstance();
                                    Boolean bool2 = Boolean.FALSE;
                                    DataAirconSystem i02 = companion.i0(dataAirconSystem, null, bool2);
                                    if (i02 != null) {
                                        DataAirconSystem.update$default(dataAirconSystem2, null, i02, null, null, false, 16, null);
                                        dataAirconSystem2.info.state = systemState;
                                        dataAirconSystem.info.quietNightModeIsRunning = bool2;
                                        DataAirconSystem.update$default(dataAirconSystem2, null, companion.b0(dataAirconSystem2, dataAirconSystem), null, null, false, 16, null);
                                    }
                                }
                            }
                        }
                        if (dataAirconSystem2.info.mode != null) {
                            Boolean bool3 = dataAirconSystem.info.climateControlModeEnabled;
                            if (bool3 != null) {
                                Intrinsics.checkNotNull(bool3);
                                if (!bool3.booleanValue() || ((airconMode = dataAirconSystem2.info.mode) != AirconMode.cool && airconMode != AirconMode.heat)) {
                                    AirconMode airconMode2 = dataAirconSystem2.info.mode;
                                    AirconMode airconMode3 = AirconMode.myauto;
                                    if (airconMode2 == airconMode3) {
                                        Boolean bool4 = dataAirconSystem.info.myAutoModeEnabled;
                                        if (bool4 != null) {
                                            Intrinsics.checkNotNull(bool4);
                                            if (bool4.booleanValue()) {
                                                DataAirconInfo dataAirconInfo = dataAirconSystem.info;
                                                dataAirconInfo.myAutoModeIsRunning = Boolean.TRUE;
                                                dataAirconInfo.mode = airconMode3;
                                            } else {
                                                DataAirconInfo dataAirconInfo2 = dataAirconSystem2.info;
                                                DataAirconInfo dataAirconInfo3 = dataAirconSystem.info;
                                                dataAirconInfo2.mode = dataAirconInfo3.mode;
                                                dataAirconInfo3.myAutoModeIsRunning = Boolean.FALSE;
                                            }
                                        }
                                    } else {
                                        DataAirconInfo dataAirconInfo4 = dataAirconSystem.info;
                                        dataAirconInfo4.myAutoModeIsRunning = Boolean.FALSE;
                                        AirconMode airconMode4 = dataAirconInfo4.mode;
                                        if (airconMode4 != null && airconMode4 == airconMode3) {
                                            dataAirconInfo4.mode = airconMode2;
                                        }
                                    }
                                }
                            }
                        }
                        FanStatus fanStatus = dataAirconSystem2.info.fan;
                        if (fanStatus != null) {
                            FanStatus fanStatus2 = FanStatus.autoAA;
                            if (fanStatus == fanStatus2) {
                                Boolean bool5 = dataAirconSystem.info.aaAutoFanModeEnabled;
                                if (bool5 != null) {
                                    Intrinsics.checkNotNull(bool5);
                                    if (bool5.booleanValue()) {
                                        DataAirconInfo dataAirconInfo5 = dataAirconSystem.info;
                                        dataAirconInfo5.myFanSpeedIsRunning = Boolean.TRUE;
                                        dataAirconInfo5.fan = fanStatus2;
                                    } else {
                                        DataAirconInfo dataAirconInfo6 = dataAirconSystem2.info;
                                        DataAirconInfo dataAirconInfo7 = dataAirconSystem.info;
                                        dataAirconInfo6.fan = dataAirconInfo7.fan;
                                        dataAirconInfo7.myFanSpeedIsRunning = Boolean.FALSE;
                                    }
                                }
                            } else {
                                DataAirconInfo dataAirconInfo8 = dataAirconSystem.info;
                                dataAirconInfo8.myFanSpeedIsRunning = Boolean.FALSE;
                                dataAirconInfo8.fan = fanStatus;
                            }
                        }
                        DataAirconSystem.update$default(dataAirconSystem2, null, HandlerAircon.Companion.getInstance().b0(dataAirconSystem2, dataAirconSystem), null, null, false, 16, null);
                    }
                }
            }
        }
        return dataScene2;
    }

    @Nullable
    /* renamed from: W0 */
    public final String updateThingJson(@Nullable Context context, @NotNull DataMaster masterData) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        Timber.Forest forest = Timber.forest;
        forest.d("updateThingJson", new Object[0]);
        masterData.myThings.updateGroupStates();
        String masterDataInMemoryAsJson = masterData.getMasterDataInMemoryAsJson();
        if (masterDataInMemoryAsJson != null) {
            forest.d("updateThingJson save to DB", new Object[0]);
            ThingDBStore.Companion.b().e(context, masterData);
        } else {
            forest.d("Warning blank json generated.", new Object[0]);
        }
        if (masterDataInMemoryAsJson != null) {
            forest.v("json updateThingJson:" + masterDataInMemoryAsJson, new Object[0]);
            HandlerJson.Companion.getInstance(context).processData(masterData, "updateThingJson");
        }
        return masterDataInMemoryAsJson;
    }

    /* JADX WARN: Code restructure failed: missing block: B:481:0x075f, code lost:
    
        if (r7.intValue() == 2) goto L308;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x1321  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x12d1 A[Catch: all -> 0x1381, TryCatch #0 {all -> 0x1381, blocks: (B:61:0x1270, B:67:0x1287, B:69:0x128d, B:71:0x1293, B:75:0x129f, B:77:0x12ae, B:79:0x12bb, B:81:0x131a, B:86:0x1327, B:87:0x1339, B:88:0x134a, B:90:0x1356, B:95:0x137a, B:96:0x137d, B:101:0x1373, B:103:0x12d1, B:105:0x12e4, B:106:0x12ef, B:108:0x12f5, B:111:0x1301, B:118:0x1281, B:119:0x1279, B:370:0x0a70, B:378:0x0a65, B:493:0x0a9e, B:506:0x0aab, B:510:0x0abc, B:512:0x0aca, B:514:0x0ad1, B:517:0x0aec, B:519:0x0af7, B:522:0x0b03, B:524:0x0b0c, B:527:0x0b18, B:529:0x0b21, B:532:0x0b2d, B:534:0x0b38, B:537:0x0b44, B:539:0x0b4f, B:542:0x0b5b, B:544:0x0b66, B:547:0x0b72, B:549:0x0b7d, B:552:0x0b89, B:554:0x0bb9, B:563:0x0bdf, B:566:0x0c23, B:569:0x0c31, B:570:0x0c3b, B:574:0x1013, B:575:0x0c48, B:577:0x0c80, B:581:0x0c9f, B:585:0x0cc0, B:587:0x0cc4, B:589:0x0cf9, B:593:0x0d18, B:597:0x0d39, B:598:0x0d40, B:600:0x0d74, B:604:0x0d95, B:608:0x0db6, B:609:0x0dba, B:612:0x0e08, B:615:0x0e16, B:616:0x0e20, B:631:0x0e4a, B:634:0x0e8e, B:637:0x0e9c, B:638:0x0ea6, B:640:0x0f53, B:642:0x0f7f, B:646:0x0fa0, B:650:0x0fc1, B:653:0x0ec5, B:655:0x0eff, B:659:0x0f20, B:663:0x0f41, B:668:0x0fc5, B:675:0x1011, B:680:0x102a, B:682:0x1035, B:684:0x103d, B:688:0x1081, B:690:0x108c, B:692:0x1094, B:695:0x10d4, B:696:0x110f, B:697:0x10f2, B:698:0x109a, B:700:0x10a2, B:701:0x10a8, B:703:0x10b3, B:705:0x10bb, B:706:0x10c1, B:708:0x10c9, B:710:0x1043, B:712:0x104b, B:713:0x1051, B:715:0x105c, B:717:0x1064, B:718:0x106a, B:720:0x1072, B:721:0x1124, B:723:0x1134, B:725:0x113b, B:728:0x1156, B:730:0x115c, B:733:0x1168, B:735:0x116e, B:738:0x117a, B:740:0x1180, B:743:0x118c, B:745:0x11e4, B:747:0x1222, B:749:0x123a, B:751:0x1242, B:754:0x11eb, B:756:0x11f7, B:758:0x11fd, B:760:0x120f, B:761:0x121c, B:762:0x125a), top: B:11:0x0055 }] */
    /* JADX WARN: Removed duplicated region for block: B:117:0x1318  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x1281 A[Catch: all -> 0x1381, TryCatch #0 {all -> 0x1381, blocks: (B:61:0x1270, B:67:0x1287, B:69:0x128d, B:71:0x1293, B:75:0x129f, B:77:0x12ae, B:79:0x12bb, B:81:0x131a, B:86:0x1327, B:87:0x1339, B:88:0x134a, B:90:0x1356, B:95:0x137a, B:96:0x137d, B:101:0x1373, B:103:0x12d1, B:105:0x12e4, B:106:0x12ef, B:108:0x12f5, B:111:0x1301, B:118:0x1281, B:119:0x1279, B:370:0x0a70, B:378:0x0a65, B:493:0x0a9e, B:506:0x0aab, B:510:0x0abc, B:512:0x0aca, B:514:0x0ad1, B:517:0x0aec, B:519:0x0af7, B:522:0x0b03, B:524:0x0b0c, B:527:0x0b18, B:529:0x0b21, B:532:0x0b2d, B:534:0x0b38, B:537:0x0b44, B:539:0x0b4f, B:542:0x0b5b, B:544:0x0b66, B:547:0x0b72, B:549:0x0b7d, B:552:0x0b89, B:554:0x0bb9, B:563:0x0bdf, B:566:0x0c23, B:569:0x0c31, B:570:0x0c3b, B:574:0x1013, B:575:0x0c48, B:577:0x0c80, B:581:0x0c9f, B:585:0x0cc0, B:587:0x0cc4, B:589:0x0cf9, B:593:0x0d18, B:597:0x0d39, B:598:0x0d40, B:600:0x0d74, B:604:0x0d95, B:608:0x0db6, B:609:0x0dba, B:612:0x0e08, B:615:0x0e16, B:616:0x0e20, B:631:0x0e4a, B:634:0x0e8e, B:637:0x0e9c, B:638:0x0ea6, B:640:0x0f53, B:642:0x0f7f, B:646:0x0fa0, B:650:0x0fc1, B:653:0x0ec5, B:655:0x0eff, B:659:0x0f20, B:663:0x0f41, B:668:0x0fc5, B:675:0x1011, B:680:0x102a, B:682:0x1035, B:684:0x103d, B:688:0x1081, B:690:0x108c, B:692:0x1094, B:695:0x10d4, B:696:0x110f, B:697:0x10f2, B:698:0x109a, B:700:0x10a2, B:701:0x10a8, B:703:0x10b3, B:705:0x10bb, B:706:0x10c1, B:708:0x10c9, B:710:0x1043, B:712:0x104b, B:713:0x1051, B:715:0x105c, B:717:0x1064, B:718:0x106a, B:720:0x1072, B:721:0x1124, B:723:0x1134, B:725:0x113b, B:728:0x1156, B:730:0x115c, B:733:0x1168, B:735:0x116e, B:738:0x117a, B:740:0x1180, B:743:0x118c, B:745:0x11e4, B:747:0x1222, B:749:0x123a, B:751:0x1242, B:754:0x11eb, B:756:0x11f7, B:758:0x11fd, B:760:0x120f, B:761:0x121c, B:762:0x125a), top: B:11:0x0055 }] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x1279 A[Catch: all -> 0x1381, TryCatch #0 {all -> 0x1381, blocks: (B:61:0x1270, B:67:0x1287, B:69:0x128d, B:71:0x1293, B:75:0x129f, B:77:0x12ae, B:79:0x12bb, B:81:0x131a, B:86:0x1327, B:87:0x1339, B:88:0x134a, B:90:0x1356, B:95:0x137a, B:96:0x137d, B:101:0x1373, B:103:0x12d1, B:105:0x12e4, B:106:0x12ef, B:108:0x12f5, B:111:0x1301, B:118:0x1281, B:119:0x1279, B:370:0x0a70, B:378:0x0a65, B:493:0x0a9e, B:506:0x0aab, B:510:0x0abc, B:512:0x0aca, B:514:0x0ad1, B:517:0x0aec, B:519:0x0af7, B:522:0x0b03, B:524:0x0b0c, B:527:0x0b18, B:529:0x0b21, B:532:0x0b2d, B:534:0x0b38, B:537:0x0b44, B:539:0x0b4f, B:542:0x0b5b, B:544:0x0b66, B:547:0x0b72, B:549:0x0b7d, B:552:0x0b89, B:554:0x0bb9, B:563:0x0bdf, B:566:0x0c23, B:569:0x0c31, B:570:0x0c3b, B:574:0x1013, B:575:0x0c48, B:577:0x0c80, B:581:0x0c9f, B:585:0x0cc0, B:587:0x0cc4, B:589:0x0cf9, B:593:0x0d18, B:597:0x0d39, B:598:0x0d40, B:600:0x0d74, B:604:0x0d95, B:608:0x0db6, B:609:0x0dba, B:612:0x0e08, B:615:0x0e16, B:616:0x0e20, B:631:0x0e4a, B:634:0x0e8e, B:637:0x0e9c, B:638:0x0ea6, B:640:0x0f53, B:642:0x0f7f, B:646:0x0fa0, B:650:0x0fc1, B:653:0x0ec5, B:655:0x0eff, B:659:0x0f20, B:663:0x0f41, B:668:0x0fc5, B:675:0x1011, B:680:0x102a, B:682:0x1035, B:684:0x103d, B:688:0x1081, B:690:0x108c, B:692:0x1094, B:695:0x10d4, B:696:0x110f, B:697:0x10f2, B:698:0x109a, B:700:0x10a2, B:701:0x10a8, B:703:0x10b3, B:705:0x10bb, B:706:0x10c1, B:708:0x10c9, B:710:0x1043, B:712:0x104b, B:713:0x1051, B:715:0x105c, B:717:0x1064, B:718:0x106a, B:720:0x1072, B:721:0x1124, B:723:0x1134, B:725:0x113b, B:728:0x1156, B:730:0x115c, B:733:0x1168, B:735:0x116e, B:738:0x117a, B:740:0x1180, B:743:0x118c, B:745:0x11e4, B:747:0x1222, B:749:0x123a, B:751:0x1242, B:754:0x11eb, B:756:0x11f7, B:758:0x11fd, B:760:0x120f, B:761:0x121c, B:762:0x125a), top: B:11:0x0055 }] */
    /* JADX WARN: Removed duplicated region for block: B:309:0x0686 A[Catch: all -> 0x01c1, TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x01c1, blocks: (B:13:0x0057, B:15:0x005d, B:18:0x0077, B:20:0x007d, B:23:0x0085, B:25:0x008b, B:28:0x0093, B:30:0x0099, B:33:0x00a4, B:35:0x00f0, B:37:0x00f4, B:40:0x00fd, B:42:0x0121, B:48:0x017a, B:51:0x013e, B:53:0x015c, B:120:0x018d, B:123:0x01a7, B:129:0x01d2, B:131:0x01d8, B:134:0x01f2, B:136:0x01f8, B:139:0x0203, B:141:0x0209, B:144:0x0214, B:146:0x021a, B:149:0x0222, B:151:0x0228, B:154:0x0230, B:156:0x0238, B:159:0x0240, B:161:0x0248, B:164:0x0262, B:166:0x02da, B:168:0x02de, B:171:0x02e7, B:173:0x02ea, B:176:0x02f5, B:178:0x031a, B:184:0x0377, B:188:0x0338, B:190:0x0358, B:196:0x037e, B:198:0x039b, B:201:0x03a4, B:203:0x03f0, B:206:0x040b, B:209:0x0426, B:215:0x044e, B:217:0x0454, B:220:0x0460, B:222:0x046a, B:224:0x0471, B:227:0x0479, B:229:0x047f, B:232:0x048b, B:234:0x0494, B:235:0x0496, B:237:0x049d, B:240:0x04a9, B:242:0x04c4, B:243:0x04e8, B:245:0x051d, B:253:0x053b, B:265:0x0566, B:270:0x0578, B:276:0x058c, B:280:0x059e, B:296:0x062f, B:309:0x0686, B:313:0x06b0, B:315:0x06b9, B:317:0x06ca, B:319:0x06d4, B:321:0x06eb, B:323:0x06f3, B:328:0x0718, B:412:0x08c5, B:434:0x07c5, B:436:0x07cc, B:437:0x07d1, B:441:0x07fd, B:445:0x07cf, B:450:0x0783, B:452:0x078a, B:453:0x078f, B:455:0x078d, B:457:0x0720, B:461:0x0728, B:464:0x0732, B:471:0x0743, B:474:0x074b, B:476:0x0752, B:479:0x075a, B:483:0x06ff, B:486:0x06e1, B:488:0x06c4, B:496:0x0638, B:500:0x05f1, B:501:0x05fa, B:502:0x0603, B:503:0x060c), top: B:11:0x0055 }] */
    /* JADX WARN: Removed duplicated region for block: B:312:0x06ae  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x0710 A[Catch: all -> 0x1383, TRY_ENTER, TRY_LEAVE, TryCatch #2 {all -> 0x1383, blocks: (B:9:0x003e, B:126:0x01c6, B:212:0x0441, B:249:0x052b, B:251:0x0535, B:256:0x0547, B:258:0x0551, B:260:0x0558, B:263:0x0560, B:268:0x0572, B:274:0x0586, B:278:0x0598, B:281:0x05a8, B:292:0x061e, B:301:0x0656, B:303:0x065d, B:306:0x0667, B:307:0x067e, B:325:0x0710, B:332:0x0768, B:406:0x0867, B:431:0x07bc, B:447:0x077a, B:469:0x073d, B:492:0x0693, B:497:0x0625, B:504:0x0615), top: B:8:0x003e }] */
    /* JADX WARN: Removed duplicated region for block: B:330:0x071e  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x0779  */
    /* JADX WARN: Removed duplicated region for block: B:336:0x07b3  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x0854  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x097d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:344:0x0988 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:347:0x0994  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x09a4  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x09b5  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x09cb  */
    /* JADX WARN: Removed duplicated region for block: B:367:0x0a54  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x09b9  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x0996 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:399:0x0989 A[Catch: all -> 0x0a99, TryCatch #3 {all -> 0x0a99, blocks: (B:355:0x0a08, B:356:0x0a26, B:358:0x0a2e, B:360:0x0a32, B:361:0x0a3f, B:364:0x09cf, B:366:0x09d5, B:375:0x0a5c, B:384:0x09be, B:390:0x09a9, B:396:0x0999, B:399:0x0989, B:403:0x097e, B:409:0x0871, B:414:0x0930, B:416:0x0938, B:418:0x093c, B:420:0x094d, B:423:0x090d), top: B:337:0x0852 }] */
    /* JADX WARN: Removed duplicated region for block: B:403:0x097e A[Catch: all -> 0x0a99, TryCatch #3 {all -> 0x0a99, blocks: (B:355:0x0a08, B:356:0x0a26, B:358:0x0a2e, B:360:0x0a32, B:361:0x0a3f, B:364:0x09cf, B:366:0x09d5, B:375:0x0a5c, B:384:0x09be, B:390:0x09a9, B:396:0x0999, B:399:0x0989, B:403:0x097e, B:409:0x0871, B:414:0x0930, B:416:0x0938, B:418:0x093c, B:420:0x094d, B:423:0x090d), top: B:337:0x0852 }] */
    /* JADX WARN: Removed duplicated region for block: B:405:0x0865  */
    /* JADX WARN: Removed duplicated region for block: B:431:0x07bc A[Catch: all -> 0x1383, TRY_ENTER, TRY_LEAVE, TryCatch #2 {all -> 0x1383, blocks: (B:9:0x003e, B:126:0x01c6, B:212:0x0441, B:249:0x052b, B:251:0x0535, B:256:0x0547, B:258:0x0551, B:260:0x0558, B:263:0x0560, B:268:0x0572, B:274:0x0586, B:278:0x0598, B:281:0x05a8, B:292:0x061e, B:301:0x0656, B:303:0x065d, B:306:0x0667, B:307:0x067e, B:325:0x0710, B:332:0x0768, B:406:0x0867, B:431:0x07bc, B:447:0x077a, B:469:0x073d, B:492:0x0693, B:497:0x0625, B:504:0x0615), top: B:8:0x003e }] */
    /* JADX WARN: Removed duplicated region for block: B:447:0x077a A[Catch: all -> 0x1383, TRY_LEAVE, TryCatch #2 {all -> 0x1383, blocks: (B:9:0x003e, B:126:0x01c6, B:212:0x0441, B:249:0x052b, B:251:0x0535, B:256:0x0547, B:258:0x0551, B:260:0x0558, B:263:0x0560, B:268:0x0572, B:274:0x0586, B:278:0x0598, B:281:0x05a8, B:292:0x061e, B:301:0x0656, B:303:0x065d, B:306:0x0667, B:307:0x067e, B:325:0x0710, B:332:0x0768, B:406:0x0867, B:431:0x07bc, B:447:0x077a, B:469:0x073d, B:492:0x0693, B:497:0x0625, B:504:0x0615), top: B:8:0x003e }] */
    /* JADX WARN: Removed duplicated region for block: B:457:0x0720 A[Catch: all -> 0x01c1, TryCatch #1 {all -> 0x01c1, blocks: (B:13:0x0057, B:15:0x005d, B:18:0x0077, B:20:0x007d, B:23:0x0085, B:25:0x008b, B:28:0x0093, B:30:0x0099, B:33:0x00a4, B:35:0x00f0, B:37:0x00f4, B:40:0x00fd, B:42:0x0121, B:48:0x017a, B:51:0x013e, B:53:0x015c, B:120:0x018d, B:123:0x01a7, B:129:0x01d2, B:131:0x01d8, B:134:0x01f2, B:136:0x01f8, B:139:0x0203, B:141:0x0209, B:144:0x0214, B:146:0x021a, B:149:0x0222, B:151:0x0228, B:154:0x0230, B:156:0x0238, B:159:0x0240, B:161:0x0248, B:164:0x0262, B:166:0x02da, B:168:0x02de, B:171:0x02e7, B:173:0x02ea, B:176:0x02f5, B:178:0x031a, B:184:0x0377, B:188:0x0338, B:190:0x0358, B:196:0x037e, B:198:0x039b, B:201:0x03a4, B:203:0x03f0, B:206:0x040b, B:209:0x0426, B:215:0x044e, B:217:0x0454, B:220:0x0460, B:222:0x046a, B:224:0x0471, B:227:0x0479, B:229:0x047f, B:232:0x048b, B:234:0x0494, B:235:0x0496, B:237:0x049d, B:240:0x04a9, B:242:0x04c4, B:243:0x04e8, B:245:0x051d, B:253:0x053b, B:265:0x0566, B:270:0x0578, B:276:0x058c, B:280:0x059e, B:296:0x062f, B:309:0x0686, B:313:0x06b0, B:315:0x06b9, B:317:0x06ca, B:319:0x06d4, B:321:0x06eb, B:323:0x06f3, B:328:0x0718, B:412:0x08c5, B:434:0x07c5, B:436:0x07cc, B:437:0x07d1, B:441:0x07fd, B:445:0x07cf, B:450:0x0783, B:452:0x078a, B:453:0x078f, B:455:0x078d, B:457:0x0720, B:461:0x0728, B:464:0x0732, B:471:0x0743, B:474:0x074b, B:476:0x0752, B:479:0x075a, B:483:0x06ff, B:486:0x06e1, B:488:0x06c4, B:496:0x0638, B:500:0x05f1, B:501:0x05fa, B:502:0x0603, B:503:0x060c), top: B:11:0x0055 }] */
    /* JADX WARN: Removed duplicated region for block: B:469:0x073d A[Catch: all -> 0x1383, TRY_ENTER, TRY_LEAVE, TryCatch #2 {all -> 0x1383, blocks: (B:9:0x003e, B:126:0x01c6, B:212:0x0441, B:249:0x052b, B:251:0x0535, B:256:0x0547, B:258:0x0551, B:260:0x0558, B:263:0x0560, B:268:0x0572, B:274:0x0586, B:278:0x0598, B:281:0x05a8, B:292:0x061e, B:301:0x0656, B:303:0x065d, B:306:0x0667, B:307:0x067e, B:325:0x0710, B:332:0x0768, B:406:0x0867, B:431:0x07bc, B:447:0x077a, B:469:0x073d, B:492:0x0693, B:497:0x0625, B:504:0x0615), top: B:8:0x003e }] */
    /* JADX WARN: Removed duplicated region for block: B:490:0x070a  */
    /* JADX WARN: Removed duplicated region for block: B:491:0x0691  */
    /* JADX WARN: Removed duplicated region for block: B:642:0x0f7f A[Catch: all -> 0x1381, TryCatch #0 {all -> 0x1381, blocks: (B:61:0x1270, B:67:0x1287, B:69:0x128d, B:71:0x1293, B:75:0x129f, B:77:0x12ae, B:79:0x12bb, B:81:0x131a, B:86:0x1327, B:87:0x1339, B:88:0x134a, B:90:0x1356, B:95:0x137a, B:96:0x137d, B:101:0x1373, B:103:0x12d1, B:105:0x12e4, B:106:0x12ef, B:108:0x12f5, B:111:0x1301, B:118:0x1281, B:119:0x1279, B:370:0x0a70, B:378:0x0a65, B:493:0x0a9e, B:506:0x0aab, B:510:0x0abc, B:512:0x0aca, B:514:0x0ad1, B:517:0x0aec, B:519:0x0af7, B:522:0x0b03, B:524:0x0b0c, B:527:0x0b18, B:529:0x0b21, B:532:0x0b2d, B:534:0x0b38, B:537:0x0b44, B:539:0x0b4f, B:542:0x0b5b, B:544:0x0b66, B:547:0x0b72, B:549:0x0b7d, B:552:0x0b89, B:554:0x0bb9, B:563:0x0bdf, B:566:0x0c23, B:569:0x0c31, B:570:0x0c3b, B:574:0x1013, B:575:0x0c48, B:577:0x0c80, B:581:0x0c9f, B:585:0x0cc0, B:587:0x0cc4, B:589:0x0cf9, B:593:0x0d18, B:597:0x0d39, B:598:0x0d40, B:600:0x0d74, B:604:0x0d95, B:608:0x0db6, B:609:0x0dba, B:612:0x0e08, B:615:0x0e16, B:616:0x0e20, B:631:0x0e4a, B:634:0x0e8e, B:637:0x0e9c, B:638:0x0ea6, B:640:0x0f53, B:642:0x0f7f, B:646:0x0fa0, B:650:0x0fc1, B:653:0x0ec5, B:655:0x0eff, B:659:0x0f20, B:663:0x0f41, B:668:0x0fc5, B:675:0x1011, B:680:0x102a, B:682:0x1035, B:684:0x103d, B:688:0x1081, B:690:0x108c, B:692:0x1094, B:695:0x10d4, B:696:0x110f, B:697:0x10f2, B:698:0x109a, B:700:0x10a2, B:701:0x10a8, B:703:0x10b3, B:705:0x10bb, B:706:0x10c1, B:708:0x10c9, B:710:0x1043, B:712:0x104b, B:713:0x1051, B:715:0x105c, B:717:0x1064, B:718:0x106a, B:720:0x1072, B:721:0x1124, B:723:0x1134, B:725:0x113b, B:728:0x1156, B:730:0x115c, B:733:0x1168, B:735:0x116e, B:738:0x117a, B:740:0x1180, B:743:0x118c, B:745:0x11e4, B:747:0x1222, B:749:0x123a, B:751:0x1242, B:754:0x11eb, B:756:0x11f7, B:758:0x11fd, B:760:0x120f, B:761:0x121c, B:762:0x125a), top: B:11:0x0055 }] */
    /* JADX WARN: Removed duplicated region for block: B:644:0x0f9c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:648:0x0fbd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:664:0x0ebb  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x1287 A[Catch: all -> 0x1381, TryCatch #0 {all -> 0x1381, blocks: (B:61:0x1270, B:67:0x1287, B:69:0x128d, B:71:0x1293, B:75:0x129f, B:77:0x12ae, B:79:0x12bb, B:81:0x131a, B:86:0x1327, B:87:0x1339, B:88:0x134a, B:90:0x1356, B:95:0x137a, B:96:0x137d, B:101:0x1373, B:103:0x12d1, B:105:0x12e4, B:106:0x12ef, B:108:0x12f5, B:111:0x1301, B:118:0x1281, B:119:0x1279, B:370:0x0a70, B:378:0x0a65, B:493:0x0a9e, B:506:0x0aab, B:510:0x0abc, B:512:0x0aca, B:514:0x0ad1, B:517:0x0aec, B:519:0x0af7, B:522:0x0b03, B:524:0x0b0c, B:527:0x0b18, B:529:0x0b21, B:532:0x0b2d, B:534:0x0b38, B:537:0x0b44, B:539:0x0b4f, B:542:0x0b5b, B:544:0x0b66, B:547:0x0b72, B:549:0x0b7d, B:552:0x0b89, B:554:0x0bb9, B:563:0x0bdf, B:566:0x0c23, B:569:0x0c31, B:570:0x0c3b, B:574:0x1013, B:575:0x0c48, B:577:0x0c80, B:581:0x0c9f, B:585:0x0cc0, B:587:0x0cc4, B:589:0x0cf9, B:593:0x0d18, B:597:0x0d39, B:598:0x0d40, B:600:0x0d74, B:604:0x0d95, B:608:0x0db6, B:609:0x0dba, B:612:0x0e08, B:615:0x0e16, B:616:0x0e20, B:631:0x0e4a, B:634:0x0e8e, B:637:0x0e9c, B:638:0x0ea6, B:640:0x0f53, B:642:0x0f7f, B:646:0x0fa0, B:650:0x0fc1, B:653:0x0ec5, B:655:0x0eff, B:659:0x0f20, B:663:0x0f41, B:668:0x0fc5, B:675:0x1011, B:680:0x102a, B:682:0x1035, B:684:0x103d, B:688:0x1081, B:690:0x108c, B:692:0x1094, B:695:0x10d4, B:696:0x110f, B:697:0x10f2, B:698:0x109a, B:700:0x10a2, B:701:0x10a8, B:703:0x10b3, B:705:0x10bb, B:706:0x10c1, B:708:0x10c9, B:710:0x1043, B:712:0x104b, B:713:0x1051, B:715:0x105c, B:717:0x1064, B:718:0x106a, B:720:0x1072, B:721:0x1124, B:723:0x1134, B:725:0x113b, B:728:0x1156, B:730:0x115c, B:733:0x1168, B:735:0x116e, B:738:0x117a, B:740:0x1180, B:743:0x118c, B:745:0x11e4, B:747:0x1222, B:749:0x123a, B:751:0x1242, B:754:0x11eb, B:756:0x11f7, B:758:0x11fd, B:760:0x120f, B:761:0x121c, B:762:0x125a), top: B:11:0x0055 }] */
    /* JADX WARN: Removed duplicated region for block: B:695:0x10d4 A[Catch: all -> 0x1381, TryCatch #0 {all -> 0x1381, blocks: (B:61:0x1270, B:67:0x1287, B:69:0x128d, B:71:0x1293, B:75:0x129f, B:77:0x12ae, B:79:0x12bb, B:81:0x131a, B:86:0x1327, B:87:0x1339, B:88:0x134a, B:90:0x1356, B:95:0x137a, B:96:0x137d, B:101:0x1373, B:103:0x12d1, B:105:0x12e4, B:106:0x12ef, B:108:0x12f5, B:111:0x1301, B:118:0x1281, B:119:0x1279, B:370:0x0a70, B:378:0x0a65, B:493:0x0a9e, B:506:0x0aab, B:510:0x0abc, B:512:0x0aca, B:514:0x0ad1, B:517:0x0aec, B:519:0x0af7, B:522:0x0b03, B:524:0x0b0c, B:527:0x0b18, B:529:0x0b21, B:532:0x0b2d, B:534:0x0b38, B:537:0x0b44, B:539:0x0b4f, B:542:0x0b5b, B:544:0x0b66, B:547:0x0b72, B:549:0x0b7d, B:552:0x0b89, B:554:0x0bb9, B:563:0x0bdf, B:566:0x0c23, B:569:0x0c31, B:570:0x0c3b, B:574:0x1013, B:575:0x0c48, B:577:0x0c80, B:581:0x0c9f, B:585:0x0cc0, B:587:0x0cc4, B:589:0x0cf9, B:593:0x0d18, B:597:0x0d39, B:598:0x0d40, B:600:0x0d74, B:604:0x0d95, B:608:0x0db6, B:609:0x0dba, B:612:0x0e08, B:615:0x0e16, B:616:0x0e20, B:631:0x0e4a, B:634:0x0e8e, B:637:0x0e9c, B:638:0x0ea6, B:640:0x0f53, B:642:0x0f7f, B:646:0x0fa0, B:650:0x0fc1, B:653:0x0ec5, B:655:0x0eff, B:659:0x0f20, B:663:0x0f41, B:668:0x0fc5, B:675:0x1011, B:680:0x102a, B:682:0x1035, B:684:0x103d, B:688:0x1081, B:690:0x108c, B:692:0x1094, B:695:0x10d4, B:696:0x110f, B:697:0x10f2, B:698:0x109a, B:700:0x10a2, B:701:0x10a8, B:703:0x10b3, B:705:0x10bb, B:706:0x10c1, B:708:0x10c9, B:710:0x1043, B:712:0x104b, B:713:0x1051, B:715:0x105c, B:717:0x1064, B:718:0x106a, B:720:0x1072, B:721:0x1124, B:723:0x1134, B:725:0x113b, B:728:0x1156, B:730:0x115c, B:733:0x1168, B:735:0x116e, B:738:0x117a, B:740:0x1180, B:743:0x118c, B:745:0x11e4, B:747:0x1222, B:749:0x123a, B:751:0x1242, B:754:0x11eb, B:756:0x11f7, B:758:0x11fd, B:760:0x120f, B:761:0x121c, B:762:0x125a), top: B:11:0x0055 }] */
    /* JADX WARN: Removed duplicated region for block: B:697:0x10f2 A[Catch: all -> 0x1381, TryCatch #0 {all -> 0x1381, blocks: (B:61:0x1270, B:67:0x1287, B:69:0x128d, B:71:0x1293, B:75:0x129f, B:77:0x12ae, B:79:0x12bb, B:81:0x131a, B:86:0x1327, B:87:0x1339, B:88:0x134a, B:90:0x1356, B:95:0x137a, B:96:0x137d, B:101:0x1373, B:103:0x12d1, B:105:0x12e4, B:106:0x12ef, B:108:0x12f5, B:111:0x1301, B:118:0x1281, B:119:0x1279, B:370:0x0a70, B:378:0x0a65, B:493:0x0a9e, B:506:0x0aab, B:510:0x0abc, B:512:0x0aca, B:514:0x0ad1, B:517:0x0aec, B:519:0x0af7, B:522:0x0b03, B:524:0x0b0c, B:527:0x0b18, B:529:0x0b21, B:532:0x0b2d, B:534:0x0b38, B:537:0x0b44, B:539:0x0b4f, B:542:0x0b5b, B:544:0x0b66, B:547:0x0b72, B:549:0x0b7d, B:552:0x0b89, B:554:0x0bb9, B:563:0x0bdf, B:566:0x0c23, B:569:0x0c31, B:570:0x0c3b, B:574:0x1013, B:575:0x0c48, B:577:0x0c80, B:581:0x0c9f, B:585:0x0cc0, B:587:0x0cc4, B:589:0x0cf9, B:593:0x0d18, B:597:0x0d39, B:598:0x0d40, B:600:0x0d74, B:604:0x0d95, B:608:0x0db6, B:609:0x0dba, B:612:0x0e08, B:615:0x0e16, B:616:0x0e20, B:631:0x0e4a, B:634:0x0e8e, B:637:0x0e9c, B:638:0x0ea6, B:640:0x0f53, B:642:0x0f7f, B:646:0x0fa0, B:650:0x0fc1, B:653:0x0ec5, B:655:0x0eff, B:659:0x0f20, B:663:0x0f41, B:668:0x0fc5, B:675:0x1011, B:680:0x102a, B:682:0x1035, B:684:0x103d, B:688:0x1081, B:690:0x108c, B:692:0x1094, B:695:0x10d4, B:696:0x110f, B:697:0x10f2, B:698:0x109a, B:700:0x10a2, B:701:0x10a8, B:703:0x10b3, B:705:0x10bb, B:706:0x10c1, B:708:0x10c9, B:710:0x1043, B:712:0x104b, B:713:0x1051, B:715:0x105c, B:717:0x1064, B:718:0x106a, B:720:0x1072, B:721:0x1124, B:723:0x1134, B:725:0x113b, B:728:0x1156, B:730:0x115c, B:733:0x1168, B:735:0x116e, B:738:0x117a, B:740:0x1180, B:743:0x118c, B:745:0x11e4, B:747:0x1222, B:749:0x123a, B:751:0x1242, B:754:0x11eb, B:756:0x11f7, B:758:0x11fd, B:760:0x120f, B:761:0x121c, B:762:0x125a), top: B:11:0x0055 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x128d A[Catch: all -> 0x1381, TryCatch #0 {all -> 0x1381, blocks: (B:61:0x1270, B:67:0x1287, B:69:0x128d, B:71:0x1293, B:75:0x129f, B:77:0x12ae, B:79:0x12bb, B:81:0x131a, B:86:0x1327, B:87:0x1339, B:88:0x134a, B:90:0x1356, B:95:0x137a, B:96:0x137d, B:101:0x1373, B:103:0x12d1, B:105:0x12e4, B:106:0x12ef, B:108:0x12f5, B:111:0x1301, B:118:0x1281, B:119:0x1279, B:370:0x0a70, B:378:0x0a65, B:493:0x0a9e, B:506:0x0aab, B:510:0x0abc, B:512:0x0aca, B:514:0x0ad1, B:517:0x0aec, B:519:0x0af7, B:522:0x0b03, B:524:0x0b0c, B:527:0x0b18, B:529:0x0b21, B:532:0x0b2d, B:534:0x0b38, B:537:0x0b44, B:539:0x0b4f, B:542:0x0b5b, B:544:0x0b66, B:547:0x0b72, B:549:0x0b7d, B:552:0x0b89, B:554:0x0bb9, B:563:0x0bdf, B:566:0x0c23, B:569:0x0c31, B:570:0x0c3b, B:574:0x1013, B:575:0x0c48, B:577:0x0c80, B:581:0x0c9f, B:585:0x0cc0, B:587:0x0cc4, B:589:0x0cf9, B:593:0x0d18, B:597:0x0d39, B:598:0x0d40, B:600:0x0d74, B:604:0x0d95, B:608:0x0db6, B:609:0x0dba, B:612:0x0e08, B:615:0x0e16, B:616:0x0e20, B:631:0x0e4a, B:634:0x0e8e, B:637:0x0e9c, B:638:0x0ea6, B:640:0x0f53, B:642:0x0f7f, B:646:0x0fa0, B:650:0x0fc1, B:653:0x0ec5, B:655:0x0eff, B:659:0x0f20, B:663:0x0f41, B:668:0x0fc5, B:675:0x1011, B:680:0x102a, B:682:0x1035, B:684:0x103d, B:688:0x1081, B:690:0x108c, B:692:0x1094, B:695:0x10d4, B:696:0x110f, B:697:0x10f2, B:698:0x109a, B:700:0x10a2, B:701:0x10a8, B:703:0x10b3, B:705:0x10bb, B:706:0x10c1, B:708:0x10c9, B:710:0x1043, B:712:0x104b, B:713:0x1051, B:715:0x105c, B:717:0x1064, B:718:0x106a, B:720:0x1072, B:721:0x1124, B:723:0x1134, B:725:0x113b, B:728:0x1156, B:730:0x115c, B:733:0x1168, B:735:0x116e, B:738:0x117a, B:740:0x1180, B:743:0x118c, B:745:0x11e4, B:747:0x1222, B:749:0x123a, B:751:0x1242, B:754:0x11eb, B:756:0x11f7, B:758:0x11fd, B:760:0x120f, B:761:0x121c, B:762:0x125a), top: B:11:0x0055 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x1293 A[Catch: all -> 0x1381, TryCatch #0 {all -> 0x1381, blocks: (B:61:0x1270, B:67:0x1287, B:69:0x128d, B:71:0x1293, B:75:0x129f, B:77:0x12ae, B:79:0x12bb, B:81:0x131a, B:86:0x1327, B:87:0x1339, B:88:0x134a, B:90:0x1356, B:95:0x137a, B:96:0x137d, B:101:0x1373, B:103:0x12d1, B:105:0x12e4, B:106:0x12ef, B:108:0x12f5, B:111:0x1301, B:118:0x1281, B:119:0x1279, B:370:0x0a70, B:378:0x0a65, B:493:0x0a9e, B:506:0x0aab, B:510:0x0abc, B:512:0x0aca, B:514:0x0ad1, B:517:0x0aec, B:519:0x0af7, B:522:0x0b03, B:524:0x0b0c, B:527:0x0b18, B:529:0x0b21, B:532:0x0b2d, B:534:0x0b38, B:537:0x0b44, B:539:0x0b4f, B:542:0x0b5b, B:544:0x0b66, B:547:0x0b72, B:549:0x0b7d, B:552:0x0b89, B:554:0x0bb9, B:563:0x0bdf, B:566:0x0c23, B:569:0x0c31, B:570:0x0c3b, B:574:0x1013, B:575:0x0c48, B:577:0x0c80, B:581:0x0c9f, B:585:0x0cc0, B:587:0x0cc4, B:589:0x0cf9, B:593:0x0d18, B:597:0x0d39, B:598:0x0d40, B:600:0x0d74, B:604:0x0d95, B:608:0x0db6, B:609:0x0dba, B:612:0x0e08, B:615:0x0e16, B:616:0x0e20, B:631:0x0e4a, B:634:0x0e8e, B:637:0x0e9c, B:638:0x0ea6, B:640:0x0f53, B:642:0x0f7f, B:646:0x0fa0, B:650:0x0fc1, B:653:0x0ec5, B:655:0x0eff, B:659:0x0f20, B:663:0x0f41, B:668:0x0fc5, B:675:0x1011, B:680:0x102a, B:682:0x1035, B:684:0x103d, B:688:0x1081, B:690:0x108c, B:692:0x1094, B:695:0x10d4, B:696:0x110f, B:697:0x10f2, B:698:0x109a, B:700:0x10a2, B:701:0x10a8, B:703:0x10b3, B:705:0x10bb, B:706:0x10c1, B:708:0x10c9, B:710:0x1043, B:712:0x104b, B:713:0x1051, B:715:0x105c, B:717:0x1064, B:718:0x106a, B:720:0x1072, B:721:0x1124, B:723:0x1134, B:725:0x113b, B:728:0x1156, B:730:0x115c, B:733:0x1168, B:735:0x116e, B:738:0x117a, B:740:0x1180, B:743:0x118c, B:745:0x11e4, B:747:0x1222, B:749:0x123a, B:751:0x1242, B:754:0x11eb, B:756:0x11f7, B:758:0x11fd, B:760:0x120f, B:761:0x121c, B:762:0x125a), top: B:11:0x0055 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x1320  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x1325  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x1356 A[Catch: all -> 0x1381, TryCatch #0 {all -> 0x1381, blocks: (B:61:0x1270, B:67:0x1287, B:69:0x128d, B:71:0x1293, B:75:0x129f, B:77:0x12ae, B:79:0x12bb, B:81:0x131a, B:86:0x1327, B:87:0x1339, B:88:0x134a, B:90:0x1356, B:95:0x137a, B:96:0x137d, B:101:0x1373, B:103:0x12d1, B:105:0x12e4, B:106:0x12ef, B:108:0x12f5, B:111:0x1301, B:118:0x1281, B:119:0x1279, B:370:0x0a70, B:378:0x0a65, B:493:0x0a9e, B:506:0x0aab, B:510:0x0abc, B:512:0x0aca, B:514:0x0ad1, B:517:0x0aec, B:519:0x0af7, B:522:0x0b03, B:524:0x0b0c, B:527:0x0b18, B:529:0x0b21, B:532:0x0b2d, B:534:0x0b38, B:537:0x0b44, B:539:0x0b4f, B:542:0x0b5b, B:544:0x0b66, B:547:0x0b72, B:549:0x0b7d, B:552:0x0b89, B:554:0x0bb9, B:563:0x0bdf, B:566:0x0c23, B:569:0x0c31, B:570:0x0c3b, B:574:0x1013, B:575:0x0c48, B:577:0x0c80, B:581:0x0c9f, B:585:0x0cc0, B:587:0x0cc4, B:589:0x0cf9, B:593:0x0d18, B:597:0x0d39, B:598:0x0d40, B:600:0x0d74, B:604:0x0d95, B:608:0x0db6, B:609:0x0dba, B:612:0x0e08, B:615:0x0e16, B:616:0x0e20, B:631:0x0e4a, B:634:0x0e8e, B:637:0x0e9c, B:638:0x0ea6, B:640:0x0f53, B:642:0x0f7f, B:646:0x0fa0, B:650:0x0fc1, B:653:0x0ec5, B:655:0x0eff, B:659:0x0f20, B:663:0x0f41, B:668:0x0fc5, B:675:0x1011, B:680:0x102a, B:682:0x1035, B:684:0x103d, B:688:0x1081, B:690:0x108c, B:692:0x1094, B:695:0x10d4, B:696:0x110f, B:697:0x10f2, B:698:0x109a, B:700:0x10a2, B:701:0x10a8, B:703:0x10b3, B:705:0x10bb, B:706:0x10c1, B:708:0x10c9, B:710:0x1043, B:712:0x104b, B:713:0x1051, B:715:0x105c, B:717:0x1064, B:718:0x106a, B:720:0x1072, B:721:0x1124, B:723:0x1134, B:725:0x113b, B:728:0x1156, B:730:0x115c, B:733:0x1168, B:735:0x116e, B:738:0x117a, B:740:0x1180, B:743:0x118c, B:745:0x11e4, B:747:0x1222, B:749:0x123a, B:751:0x1242, B:754:0x11eb, B:756:0x11f7, B:758:0x11fd, B:760:0x120f, B:761:0x121c, B:762:0x125a), top: B:11:0x0055 }] */
    /* renamed from: d0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void parseMessage(@NotNull Context context, @NotNull String message, boolean z7) {
        Throwable th;
        DataMaster dataMaster;
        HandlerLights handlerLights;
        Context context2;
        boolean z10;
        boolean z11;
        DataMaster dataMaster2;
        DataModuleInfoSource dataModuleInfoSource;
        boolean z12;
        boolean z13;
        boolean z14;
        boolean sendBroadcastThing;
        boolean validLight;
        String K;
        boolean z15;
        String lmAck;
        int i10;
        ArrayList dipState_;
        boolean z16;
        boolean z17;
        boolean G;
        int i11;
        Integer num;
        Integer parseHexToInt;
        Integer num2;
        Boolean bool;
        Boolean bool2;
        boolean z18;
        int i12;
        boolean updateLight;
        String str;
        boolean z19;
        boolean z20;
        boolean z21;
        Integer num3;
        Timber.Forest forest;
        Integer num4;
        Integer num5;
        String str2;
        boolean z22;
        Integer num6;
        Boolean bool3;
        String str3;
        DataMaster dataMaster3;
        int i13;
        int i14;
        int i15;
        boolean z23;
        String str4;
        boolean z24;
        DataMaster dataMaster4;
        boolean U0;
        char c10;
        char c11;
        int i16;
        int i17;
        int i18;
        boolean isValidThing;
        int i19;
        int i20;
        boolean z25;
        int i21;
        Boolean bool4;
        Boolean bool5;
        int i22;
        char[] cArr;
        int i23;
        DataSystem dataSystem;
        DataMaster dataMaster5;
        int roomNumber_;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(message, "message");
        boolean z26 = false;
        if (!compareFRLValue(message, 2, "02")) {
            Timber.forest.d("Rejected can message - incorrect device type " + message, new Object[0]);
            return;
        }
        boolean systemTypeIs08 = compareFRLValue(message, 0, "08");
        String str5 = "";
        synchronized (MyMasterData.class) {
            try {
                dataMaster = MyMasterData.Companion.getDataMaster(context);
                try {
                    try {
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Throwable th4) {
                th = th4;
            }
            if (compareFRLValue(message, 9, "00")) {
                String extractUIDValue = extractUIDValue(message, 4);
                if (extractUIDValue == null) {
                    Timber.forest.d("Rejected LM status message - invalid UID " + message, new Object[0]);
                    return;
                }
                Integer roomsExist = parseHexToInt(message, 13);
                if (roomsExist != null && roomsExist.intValue() <= 63) {
                    Integer validRooms = parseHexToInt(message, 15);
                    if (validRooms != null && validRooms.intValue() <= 63) {
                        Integer version = parseHexToInt(message, 17);
                        if (version == null) {
                            Timber.forest.d("Rejected LM status message - invalid lightModuleVersion", new Object[0]);
                            return;
                        }
                        Timber.forest.d("Valid LM setup message old. UID - " + extractUIDValue + " roomsExist - " + roomsExist + " validRooms - " + validRooms + " version - " + version, new Object[0]);
                        String binaryString = Integer.toBinaryString(roomsExist.intValue());
                        Intrinsics.checkNotNullExpressionValue(binaryString, "toBinaryString(...)");
                        char[] charArray = binaryString.toCharArray();
                        Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
                        validLight = false;
                        int roomNumber = 1;
                        for (int maxRoomNumber = 7; roomNumber < maxRoomNumber; maxRoomNumber = 7) {
                            if (charArray.length - roomNumber < 0 || charArray[charArray.length - roomNumber] == '0') {
                                roomNumber_ = roomNumber;
                                boolean isValidLight = isValidLight(dataMaster, extractUIDValue + formatIntToHex(Integer.valueOf(roomNumber_)));
                                if (isValidLight) {
                                    Timber.forest.d("Disabled room " + roomNumber_, new Object[0]);
                                }
                                if (!validLight && isValidLight) {
                                    validLight = true;
                                }
                            } else {
                                roomNumber_ = roomNumber;
                                boolean registerOrUpdateLight = registerOrUpdateLight(dataMaster, extractUIDValue + formatIntToHex(Integer.valueOf(roomNumber)), false, null, systemTypeIs08);
                                if (registerOrUpdateLight) {
                                    Timber.forest.d("Enabled room " + roomNumber_, new Object[0]);
                                }
                                if (!validLight && registerOrUpdateLight) {
                                    validLight = true;
                                }
                            }
                            roomNumber = roomNumber_ + 1;
                        }
                        str5 = extractUIDValue;
                        dataMaster2 = dataMaster;
                        handlerLights = this;
                        context2 = context;
                        z12 = false;
                        z13 = false;
                        sendBroadcastThing = false;
                        z10 = systemTypeIs08;
                        dataModuleInfoSource = null;
                        z14 = true;
                    }
                    Timber.forest.d("Rejected LM status message - invalid validRooms " + message, new Object[0]);
                    return;
                }
                Timber.forest.d("Rejected LM status message - invalid roomsExist " + message, new Object[0]);
                return;
            }
            if (compareFRLValue(message, 9, "02")) {
                String extractUIDValue2 = extractUIDValue(message, 4);
                if (extractUIDValue2 == null) {
                    Timber.forest.d("Rejected LM status message - invalid UID " + message, new Object[0]);
                    return;
                }
                Integer majorFirmwareVersion = parseHexToInt(message, 11);
                if (majorFirmwareVersion == null) {
                    Timber.forest.d("Rejected LM status message - invalid major firmware version", new Object[0]);
                    return;
                }
                Integer minorFirmwareVersion = parseHexToInt(message, 13);
                if (minorFirmwareVersion == null) {
                    Timber.forest.d("Rejected LM status message - invalid major firmware version", new Object[0]);
                    return;
                }
                Integer roomExists = parseHexToInt(message, 15);
                if (roomExists != null && roomExists.intValue() <= 63) {
                    Integer validRooms2 = parseHexToInt(message, 17);
                    if (validRooms2 != null && validRooms2.intValue() <= 63) {
                        Integer relayRooms = parseHexToInt(message, 19);
                        if (relayRooms != null && relayRooms.intValue() <= 63) {
                            Integer parseHexToInt2 = parseHexToInt(message, 21);
                            if (parseHexToInt2 == null) {
                                Timber.forest.d("Rejected LM status message - invalid infoByte " + message, new Object[0]);
                                return;
                            }
                            Timber.forest.d(LOG_NAME, "Valid LM setup message JZ4. UID - " + extractUIDValue2 + " roomsExist - " + roomExists + " validRooms - " + validRooms2 + " relayRooms - " + relayRooms + " version - " + majorFirmwareVersion + "." + minorFirmwareVersion);
                            String binaryString2 = Integer.toBinaryString(roomExists.intValue());
                            Intrinsics.checkNotNullExpressionValue(binaryString2, "toBinaryString(...)");
                            char[] roomExistsArray = binaryString2.toCharArray();
                            Intrinsics.checkNotNullExpressionValue(roomExistsArray, "this as java.lang.String).toCharArray()");
                            String binaryString3 = Integer.toBinaryString(relayRooms.intValue());
                            Intrinsics.checkNotNullExpressionValue(binaryString3, "toBinaryString(...)");
                            char[] RelayRoomsArray = binaryString3.toCharArray();
                            Intrinsics.checkNotNullExpressionValue(RelayRoomsArray, "this as java.lang.String).toCharArray()");
                            int i24 = 1;
                            validLight = false;
                            for (int i25 = 7; i24 < i25; i25 = 7) {
                                if (roomExistsArray.length - i24 < 0 || roomExistsArray[roomExistsArray.length - i24] == '0') {
                                    cArr = roomExistsArray;
                                    i23 = i24;
                                    boolean isValidLight2 = isValidLight(dataMaster, extractUIDValue2 + formatIntToHex(Integer.valueOf(i23)));
                                    if (isValidLight2) {
                                        Timber.forest.d("Disabled room " + i23, new Object[0]);
                                    }
                                    if (!validLight && isValidLight2) {
                                        validLight = true;
                                    }
                                } else {
                                    boolean z27 = RelayRoomsArray.length >= i24 && RelayRoomsArray[RelayRoomsArray.length - i24] == '1';
                                    cArr = roomExistsArray;
                                    i23 = i24;
                                    boolean registerOrUpdateLight2 = registerOrUpdateLight(dataMaster, extractUIDValue2 + formatIntToHex(Integer.valueOf(i24)), z27, null, systemTypeIs08);
                                    if (registerOrUpdateLight2) {
                                        Timber.forest.d("Enabled room " + i23, new Object[0]);
                                    }
                                    if (!validLight && registerOrUpdateLight2) {
                                        validLight = true;
                                    }
                                }
                                i24 = i23 + 1;
                                roomExistsArray = cArr;
                            }
                            String binaryString4 = Integer.toBinaryString(parseHexToInt2.intValue());
                            Intrinsics.checkNotNullExpressionValue(binaryString4, "toBinaryString(...)");
                            char[] charArray2 = binaryString4.toCharArray();
                            Intrinsics.checkNotNullExpressionValue(charArray2, "this as java.lang.String).toCharArray()");
                            String str6 = "LM";
                            if (charArray2.length == 8 && charArray2[0] == '1') {
                                str6 = "RM";
                            }
                            DataModuleInfoSource dataModuleInfoSource2 = new DataModuleInfoSource();
                            dataModuleInfoSource2.moduleType = str6;
                            dataModuleInfoSource2.firmwareVersion = majorFirmwareVersion + "." + minorFirmwareVersion;
                            String lmAck2 = lmAck(extractUIDValue2);
                            Timber.forest.d("Sending JZ19(1):" + lmAck2, new Object[0]);
                            this.U.sendCanMessageToCB(context, lmAck2);
                            dataModuleInfoSource = dataModuleInfoSource2;
                            str5 = extractUIDValue2;
                            dataMaster2 = dataMaster;
                            handlerLights = this;
                            context2 = context;
                            z10 = systemTypeIs08;
                            z12 = false;
                            z13 = false;
                        }
                        Timber.forest.d("Rejected LM status message - invalid relayRooms " + message, new Object[0]);
                        return;
                    }
                    Timber.forest.d("Rejected LM status message - invalid validRooms " + message, new Object[0]);
                    return;
                }
                Timber.forest.d("Rejected LM status message - invalid roomsExist " + message, new Object[0]);
                return;
            }
            if (compareFRLValue(message, 9, "01")) {
                String extractUIDValue3 = extractUIDValue(message, 4);
                if (extractUIDValue3 == null) {
                    Timber.forest.d("Rejected LM control message - invalid UID", new Object[0]);
                    return;
                }
                Integer parseHexToInt3 = parseHexToInt(message, 11);
                String extractHexString = extractHexString(message, 11);
                if (parseHexToInt3 != null && parseHexToInt3.intValue() >= 1 && parseHexToInt3.intValue() <= 6) {
                    Integer parseHexToInt4 = parseHexToInt(message, 13);
                    if (parseHexToInt4 == null) {
                        Timber.forest.d("Rejected LM control message - invalid state and value", new Object[0]);
                        return;
                    }
                    LightState lightState2 = LightState.off;
                    if ((parseHexToInt4.intValue() & 128) == 128) {
                        lightState2 = LightState.on;
                    }
                    LightState lightState3 = lightState2;
                    if (parseHexToInt(message, 15) == null) {
                        Timber.forest.d("Rejected LM control message - invalid decimal value", new Object[0]);
                        return;
                    }
                    int brightnessLevelIndex = LightBrightness.instance.getBrightnessLevelIndex((parseHexToInt4.intValue() & 127) + (r3.intValue() / 10.0d));
                    if (brightnessLevelIndex == 0) {
                        Timber.forest.d("Received a value 0 message", new Object[0]);
                        i(context, extractUIDValue3 + extractHexString, lightState3, 5);
                        i22 = 5;
                    } else {
                        i22 = brightnessLevelIndex;
                    }
                    String str7 = extractUIDValue3 + extractHexString;
                    new DataLight().id = str7;
                    validLight = updateLight(dataMaster, str7, lightState3, Integer.valueOf(i22), z7);
                    str5 = extractUIDValue3;
                    dataMaster2 = dataMaster;
                    handlerLights = this;
                    context2 = context;
                    z10 = systemTypeIs08;
                    z12 = false;
                    z13 = false;
                    dataModuleInfoSource = null;
                }
                Timber.forest.d("Rejected LM control message - invalid room number", new Object[0]);
                return;
            }
            boolean systemTypeIs08_ = systemTypeIs08;
            if (compareFRLValue(message, 9, "15")) {
                String extractUIDValue4 = extractUIDValue(message, 4);
                if (extractUIDValue4 == null) {
                    Timber.forest.d("Rejected RM2 control message - invalid UID", new Object[0]);
                    return;
                }
                Integer parseHexToInt5 = parseHexToInt(message, 11);
                String extractHexString2 = extractHexString(message, 11);
                if (parseHexToInt5 != null && parseHexToInt5.intValue() >= 1 && parseHexToInt5.intValue() <= 6) {
                    Integer parseHexToInt6 = parseHexToInt(message, 13);
                    if (parseHexToInt6 == null) {
                        Timber.forest.d("Rejected RM2 control message - invalid state", new Object[0]);
                        return;
                    }
                    Integer parseHexToInt7 = parseHexToInt(message, 15);
                    if (parseHexToInt7 == null) {
                        Timber.forest.d("Rejected RM2 control message - invalid dim percent value", new Object[0]);
                        return;
                    }
                    if (parseHexToInt(message, 17) == null) {
                        Timber.forest.d("Rejected RM2 control message - invalid wall switch state value", new Object[0]);
                    }
                    Integer parseHexToInt8 = parseHexToInt(message, 19);
                    if (parseHexToInt8 == null) {
                        Timber.forest.d("Rejected RM2 control message - invalid channel dip state value", new Object[0]);
                    }
                    Timber.Forest forest2 = Timber.forest;
                    forest2.d("Received JZ37 (" + message + ") id:" + extractUIDValue4 + extractHexString2 + " state:" + parseHexToInt6 + " percent:" + parseHexToInt7, new Object[0]);
                    int intValue = parseHexToInt6.intValue();
                    if (intValue == 0) {
                        forest2.d("JZ37 state : THING_DOWN_OFF_CLOSE_STATE", new Object[0]);
                    } else if (intValue == 1) {
                        forest2.d("JZ37 state : THING_UP_ON_OPEN_STATE", new Object[0]);
                    } else if (intValue == 2) {
                        forest2.d("JZ37 state : THING_STOP_STATE", new Object[0]);
                    } else if (intValue == 3) {
                        forest2.d("JZ37 state : THING_IN_PROGRESS_DOWN_STATE", new Object[0]);
                    } else if (intValue == 4) {
                        forest2.d("JZ37 state : THING_IN_PROGRESS_UP_STATE", new Object[0]);
                    }
                    Integer parseHexToInt9 = parseHexToInt(message, 21);
                    if (parseHexToInt8 != null && parseHexToInt8.intValue() == 5) {
                        num = parseHexToInt5;
                        if (parseHexToInt9 != null && parseHexToInt9.intValue() >= 1 && parseHexToInt9.intValue() <= 10) {
                            forest2.d("dim offset:" + parseHexToInt9, new Object[0]);
                            parseHexToInt = parseHexToInt(message, 23);
                            if (parseHexToInt == null) {
                                num2 = parseHexToInt9;
                                forest2.d("Rejected RM2 control message - invalid Status byte value", new Object[0]);
                            } else {
                                num2 = parseHexToInt9;
                                forest2.d("Statusbyte value is " + parseHexToInt, new Object[0]);
                            }
                            if (systemTypeIs08_) {
                                if (parseHexToInt == null) {
                                    bool4 = null;
                                } else if ((parseHexToInt.intValue() & 128) == 128) {
                                    forest2.d("JZ37 Statusbyte showing lowBatt", new Object[0]);
                                    bool4 = Boolean.TRUE;
                                } else {
                                    bool4 = Boolean.FALSE;
                                }
                                if (parseHexToInt == null) {
                                    bool5 = bool4;
                                    bool = null;
                                } else if ((parseHexToInt.intValue() & 64) == 64) {
                                    bool5 = bool4;
                                    forest2.d("Statusbyte showing it calibrated", new Object[0]);
                                    bool = Boolean.TRUE;
                                } else {
                                    bool5 = bool4;
                                    bool = Boolean.FALSE;
                                }
                                if (parseHexToInt != null) {
                                    if ((parseHexToInt.intValue() & 2) == 2) {
                                        forest2.d("JZ37 status ispoll: TRUE", new Object[0]);
                                        bool2 = bool5;
                                        z18 = true;
                                        try {
                                            if (parseHexToInt8 != null && parseHexToInt8.intValue() == 10) {
                                                i21 = parseHexToInt6.intValue() != 0 ? 0 : parseHexToInt6.intValue() == 1 ? 100 : parseHexToInt6.intValue() == 3 ? 25 : parseHexToInt6.intValue() == 4 ? 75 : 50;
                                            } else {
                                                if (parseHexToInt6.intValue() != 0 && parseHexToInt6.intValue() != 3) {
                                                    if (parseHexToInt6.intValue() != 1 && parseHexToInt6.intValue() != 4) {
                                                    }
                                                }
                                                i12 = 0;
                                                String str8 = extractUIDValue4 + extractHexString2;
                                                if (parseHexToInt8 != null) {
                                                    if (parseHexToInt8.intValue() == 4) {
                                                        updateLight = updateLight(dataMaster, str8, parseHexToInt6.intValue() == 1 ? LightState.on : LightState.off, 0, z7);
                                                        str = extractUIDValue4;
                                                        handlerLights = this;
                                                        z19 = false;
                                                        z20 = false;
                                                        z21 = true;
                                                        context2 = context;
                                                        z26 = z19;
                                                        sendBroadcastThing = z20;
                                                        validLight = updateLight;
                                                        z13 = z21;
                                                        str5 = str;
                                                        z10 = systemTypeIs08_;
                                                        z12 = false;
                                                        dataModuleInfoSource = null;
                                                        z14 = false;
                                                        z11 = true;
                                                        dataMaster2 = dataMaster;
                                                    } else {
                                                        extractUIDValue4 = extractUIDValue4;
                                                    }
                                                }
                                                if (parseHexToInt8 == null) {
                                                    num3 = parseHexToInt8;
                                                    num4 = num;
                                                    str2 = extractUIDValue4;
                                                    forest = forest2;
                                                    num5 = num2;
                                                } else {
                                                    String str9 = extractUIDValue4;
                                                    if (parseHexToInt8.intValue() == 5) {
                                                        Integer num7 = num;
                                                        Integer num8 = num2;
                                                        boolean updateLight2 = updateLight(dataMaster, str8, parseHexToInt6.intValue() == 1 ? LightState.on : LightState.off, Integer.valueOf(parseHexToInt7.intValue() * 10), z7);
                                                        boolean z28 = (updateLight2 || !R0(dataMaster, str8, num8)) ? updateLight2 : true;
                                                        forest2.d(LOG_NAME, "JZ37 UID: " + str9 + ", Room no: " + num7 + ", Light state: " + parseHexToInt6 + ", dim level: " + parseHexToInt7 + " ,node dip state: " + parseHexToInt8 + ", dim offset: " + num8);
                                                        updateLight = z28;
                                                        str = str9;
                                                        handlerLights = this;
                                                        z19 = false;
                                                        z20 = false;
                                                        z21 = true;
                                                        context2 = context;
                                                        z26 = z19;
                                                        sendBroadcastThing = z20;
                                                        validLight = updateLight;
                                                        z13 = z21;
                                                        str5 = str;
                                                        z10 = systemTypeIs08_;
                                                        z12 = false;
                                                        dataModuleInfoSource = null;
                                                        z14 = false;
                                                        z11 = true;
                                                        dataMaster2 = dataMaster;
                                                    } else {
                                                        num3 = parseHexToInt8;
                                                        forest = forest2;
                                                        num4 = num;
                                                        num5 = num2;
                                                        str2 = str9;
                                                    }
                                                }
                                                if (num3 == null) {
                                                    z22 = z18;
                                                    num6 = parseHexToInt6;
                                                    str = str2;
                                                    str3 = str8;
                                                    dataMaster3 = dataMaster;
                                                    i13 = 1;
                                                    i14 = 70;
                                                    i15 = nonRFExpiry;
                                                    bool3 = bool;
                                                } else {
                                                    z22 = z18;
                                                    num6 = parseHexToInt6;
                                                    if (num3.intValue() == 9) {
                                                        String str10 = LOG_NAME;
                                                        Boolean bool6 = bool;
                                                        StringBuilder sb = new StringBuilder();
                                                        Boolean bool7 = bool2;
                                                        sb.append("JZ37 UID: ");
                                                        sb.append(str2);
                                                        sb.append(", Room no: ");
                                                        sb.append(num4);
                                                        sb.append(", fan value: ");
                                                        sb.append(i12);
                                                        sb.append(", dim level: ");
                                                        sb.append(parseHexToInt7);
                                                        sb.append(" ,node dip state: ");
                                                        sb.append(num3);
                                                        sb.append(", dim offset: ");
                                                        sb.append(num5);
                                                        forest.d(str10, sb.toString());
                                                        boolean V0 = V0(dataMaster, str8, num5);
                                                        if (num3.intValue() == 10) {
                                                            DataMyThing dataMyThing = new DataMyThing();
                                                            dataMyThing.id = str8;
                                                            dataMyThing.dimPercent = Integer.valueOf(parseHexToInt7.intValue() * 10);
                                                            dataMyThing.value = Integer.valueOf(i12);
                                                            dataMyThing.batteryLow = bool7;
                                                            dataMyThing.isCalibrated = bool6;
                                                            str = str2;
                                                            h0(context, dataMaster, str8, z22, message, dataMyThing, num6.intValue());
                                                            dataMaster4 = dataMaster;
                                                            z23 = V0;
                                                            z24 = true;
                                                            U0 = false;
                                                            str4 = str8;
                                                        } else {
                                                            str = str2;
                                                            int i26 = i12;
                                                            z23 = V0;
                                                            str4 = str8;
                                                            z24 = true;
                                                            dataMaster4 = dataMaster;
                                                            U0 = U0(dataMaster, str8, i26, parseHexToInt7.intValue() * 10, z7, num6, bool7, null, bool6);
                                                        }
                                                        DataMyThing thingData = dataMaster4.myThings.getThingData(str4);
                                                        if (thingData == null) {
                                                            c10 = 'F';
                                                            c11 = 260;
                                                        } else if (thingData.thisIsRFDevice) {
                                                            c11 = 260;
                                                            thingData.expiryTime = Long.valueOf(CommonFuncs.getUptime() + nonRFExpiry);
                                                            c10 = 'F';
                                                        } else {
                                                            c11 = 260;
                                                            c10 = 'F';
                                                            thingData.expiryTime = Long.valueOf(CommonFuncs.getUptime() + 70);
                                                        }
                                                        handlerLights = this;
                                                        z20 = U0;
                                                        dataMaster = dataMaster4;
                                                        z19 = z24;
                                                        z21 = false;
                                                        updateLight = z23;
                                                        context2 = context;
                                                        z26 = z19;
                                                        sendBroadcastThing = z20;
                                                        validLight = updateLight;
                                                        z13 = z21;
                                                        str5 = str;
                                                        z10 = systemTypeIs08_;
                                                        z12 = false;
                                                        dataModuleInfoSource = null;
                                                        z14 = false;
                                                        z11 = true;
                                                        dataMaster2 = dataMaster;
                                                    } else {
                                                        bool3 = bool;
                                                        str = str2;
                                                        str3 = str8;
                                                        dataMaster3 = dataMaster;
                                                        i13 = 1;
                                                        i14 = 70;
                                                        i15 = nonRFExpiry;
                                                    }
                                                }
                                                Boolean bool8 = bool2;
                                                if (num3 == null && num3.intValue() == i13) {
                                                    i16 = i13;
                                                    if (((((i16 != 0 && (num3 == null || num3.intValue() != 3)) ? 0 : i13) == 0 && (num3 == null || num3.intValue() != 8)) ? 0 : i13) == 0) {
                                                        i18 = i13;
                                                        i17 = 10;
                                                    } else {
                                                        if (num3 == null) {
                                                            i17 = 10;
                                                        } else {
                                                            i17 = 10;
                                                            if (num3.intValue() == 10) {
                                                                i18 = i13;
                                                            }
                                                        }
                                                        i18 = 0;
                                                    }
                                                    if (i18 == 0) {
                                                        if (num3 != null && num3.intValue() == i17) {
                                                            DataMyThing dataMyThing2 = new DataMyThing();
                                                            dataMyThing2.id = str3;
                                                            dataMyThing2.dimPercent = Integer.valueOf(parseHexToInt7.intValue() * i17);
                                                            dataMyThing2.value = Integer.valueOf(i12);
                                                            dataMyThing2.batteryLow = bool8;
                                                            dataMyThing2.isCalibrated = bool3;
                                                            h0(context, dataMaster3, str3, z22, message, dataMyThing2, num6.intValue());
                                                            i19 = i14;
                                                            i20 = i15;
                                                            z25 = false;
                                                        } else {
                                                            i19 = i14;
                                                            i20 = i15;
                                                            z25 = U0(dataMaster3, str3, i12, parseHexToInt7.intValue() * 10, z7, num6, bool8, null, bool3);
                                                        }
                                                        DataMyThing thingData2 = dataMaster3.myThings.getThingData(str3);
                                                        if (thingData2 != null) {
                                                            if (thingData2.thisIsRFDevice) {
                                                                thingData2.expiryTime = Long.valueOf(CommonFuncs.getUptime() + i20);
                                                            } else {
                                                                thingData2.expiryTime = Long.valueOf(CommonFuncs.getUptime() + i19);
                                                            }
                                                        }
                                                        updateLight = false;
                                                        z21 = false;
                                                        handlerLights = this;
                                                        z20 = z25;
                                                        dataMaster = dataMaster3;
                                                        z19 = true;
                                                    } else {
                                                        if (num3 != null && num3.intValue() == 0) {
                                                            handlerLights = this;
                                                            dataMaster = dataMaster3;
                                                            isValidThing = handlerLights.isValidThing(dataMaster, str3);
                                                            if (handlerLights.isValidLight(dataMaster, str3)) {
                                                                z20 = isValidThing;
                                                                z19 = false;
                                                                updateLight = true;
                                                            }
                                                            z20 = isValidThing;
                                                            z19 = false;
                                                            updateLight = false;
                                                        } else {
                                                            handlerLights = this;
                                                            dataMaster = dataMaster3;
                                                            isValidThing = handlerLights.isValidThing(dataMaster, str3);
                                                            if (handlerLights.isValidLight(dataMaster, str3)) {
                                                                z20 = isValidThing;
                                                                z19 = false;
                                                                updateLight = true;
                                                            } else {
                                                                z20 = isValidThing;
                                                                z19 = false;
                                                                updateLight = false;
                                                            }
                                                        }
                                                        z21 = false;
                                                    }
                                                    context2 = context;
                                                    z26 = z19;
                                                    sendBroadcastThing = z20;
                                                    validLight = updateLight;
                                                    z13 = z21;
                                                    str5 = str;
                                                    z10 = systemTypeIs08_;
                                                    z12 = false;
                                                    dataModuleInfoSource = null;
                                                    z14 = false;
                                                    z11 = true;
                                                    dataMaster2 = dataMaster;
                                                } else {
                                                    if (num3 == null && num3.intValue() == 2) {
                                                        i16 = i13;
                                                        if (i16 != 0) {
                                                            if (((i16 != 0 && (num3 == null || num3.intValue() != 3)) ? 0 : i13) == 0) {
                                                                if (((((i16 != 0 && (num3 == null || num3.intValue() != 3)) ? 0 : i13) == 0 && (num3 == null || num3.intValue() != 8)) ? 0 : i13) == 0) {
                                                                }
                                                                if (i18 == 0) {
                                                                }
                                                                context2 = context;
                                                                z26 = z19;
                                                                sendBroadcastThing = z20;
                                                                validLight = updateLight;
                                                                z13 = z21;
                                                                str5 = str;
                                                                z10 = systemTypeIs08_;
                                                                z12 = false;
                                                                dataModuleInfoSource = null;
                                                                z14 = false;
                                                                z11 = true;
                                                                dataMaster2 = dataMaster;
                                                            }
                                                        }
                                                    }
                                                    i16 = 0;
                                                }
                                            }
                                            if (num3 == null) {
                                            }
                                            Boolean bool82 = bool2;
                                            if (num3 == null) {
                                                i16 = i13;
                                            }
                                            if (num3 == null) {
                                                i16 = i13;
                                            }
                                            i16 = 0;
                                        } catch (Throwable th5) {
                                            th = th5;
                                            th = th;
                                            throw th;
                                        }
                                        i12 = i21;
                                        String str82 = extractUIDValue4 + extractHexString2;
                                        if (parseHexToInt8 != null) {
                                        }
                                        if (parseHexToInt8 == null) {
                                        }
                                    } else {
                                        forest2.d("JZ37 status ispoll: FALSE", new Object[0]);
                                    }
                                }
                                bool2 = bool5;
                            } else {
                                bool = null;
                                bool2 = null;
                            }
                            z18 = false;
                            if (parseHexToInt8 != null) {
                                if (parseHexToInt6.intValue() != 0) {
                                }
                                i12 = i21;
                                String str822 = extractUIDValue4 + extractHexString2;
                                if (parseHexToInt8 != null) {
                                }
                                if (parseHexToInt8 == null) {
                                }
                                if (num3 == null) {
                                }
                                Boolean bool822 = bool2;
                                if (num3 == null) {
                                }
                                if (num3 == null) {
                                }
                                i16 = 0;
                            }
                        }
                        forest2.d("Rejected RM2 control message - invalid dim offset", new Object[0]);
                        return;
                    }
                    if (parseHexToInt8 != null && parseHexToInt8.intValue() == 9) {
                        num = parseHexToInt5;
                        if (parseHexToInt9 != null) {
                            forest2.d("dim offset:" + parseHexToInt9, new Object[0]);
                            parseHexToInt = parseHexToInt(message, 23);
                            if (parseHexToInt == null) {
                            }
                            if (systemTypeIs08_) {
                            }
                            z18 = false;
                            if (parseHexToInt8 != null) {
                            }
                        }
                        forest2.d("Rejected RM2 control message - invalid dim offset", new Object[0]);
                        return;
                    }
                    num = parseHexToInt5;
                    forest2.d("RM2 dim offset check ignored, value is " + parseHexToInt9, new Object[0]);
                    parseHexToInt = parseHexToInt(message, 23);
                    if (parseHexToInt == null) {
                    }
                    if (systemTypeIs08_) {
                    }
                    z18 = false;
                    if (parseHexToInt8 != null) {
                    }
                    throw th;
                }
                Timber.forest.d("Rejected RM2 control message - invalid room number", new Object[0]);
                return;
            }
            handlerLights = this;
            byte b10 = Byte.MIN_VALUE;
            if (handlerLights.compareFRLValue(message, 9, "16")) {
                String extractUIDValue5 = handlerLights.extractUIDValue(message, 4);
                if (extractUIDValue5 == null) {
                    Timber.forest.d("Rejected RM2 status message - invalid UID " + message, new Object[0]);
                    return;
                }
                ArrayList dipState = new ArrayList(6);
                Integer dip1State = handlerLights.parseHexToInt(message, 11);
                if (dip1State == null) {
                    Timber.forest.d("Rejected RM2 status message - invalid channel DIP1 state", new Object[0]);
                    return;
                }
                dipState.add(dip1State);
                Integer dip2State = handlerLights.parseHexToInt(message, 13);
                if (dip2State == null) {
                    Timber.forest.d("Rejected RM2 status message - invalid channel DIP2 state", new Object[0]);
                    return;
                }
                dipState.add(dip2State);
                Integer dip3State = handlerLights.parseHexToInt(message, 15);
                if (dip3State == null) {
                    Timber.forest.d("Rejected RM2 status message - invalid channel DIP3 state", new Object[0]);
                    return;
                }
                dipState.add(dip3State);
                Integer dip4State = handlerLights.parseHexToInt(message, 17);
                if (dip4State == null) {
                    Timber.forest.d("Rejected RM2 status message - invalid channel DIP4 state", new Object[0]);
                    return;
                }
                dipState.add(dip4State);
                Integer dip5State = handlerLights.parseHexToInt(message, 19);
                if (dip5State == null) {
                    Timber.forest.d("Rejected RM2 status message - invalid channel DIP5 state", new Object[0]);
                    return;
                }
                dipState.add(dip5State);
                Integer dip6State = handlerLights.parseHexToInt(message, 21);
                if (dip6State == null) {
                    Timber.forest.d("Rejected RM2 status message - invalid channel DIP6 state", new Object[0]);
                    return;
                }
                dipState.add(dip6State);
                Integer infoByte = handlerLights.parseHexToInt(message, 23);
                if (infoByte == null) {
                    Timber.forest.d("Rejected RM2 status message - RM2 Info byte", new Object[0]);
                    return;
                }
                Timber.forest.d("JZ38 info byte: " + infoByte + " for uid:" + extractUIDValue5, new Object[0]);
                int i27 = 7;
                int i28 = 0;
                int i29 = 1;
                boolean z29 = false;
                sendBroadcastThing = false;
                boolean sendBroadcast = false;
                boolean z30 = false;
                boolean z31 = false;
                while (i29 < i27) {
                    int i30 = i29 - 1;
                    Object obj = dipState.get(i30);
                    Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
                    int intValue2 = ((Number) obj).intValue();
                    if (((byte) (((byte) infoByte.intValue()) & b10)) != b10) {
                        i10 = i27;
                        int i31 = i28;
                        dipState_ = dipState;
                        z16 = systemTypeIs08_;
                        if (intValue2 != 0) {
                            if (intValue2 == 1) {
                                z17 = true;
                            } else if (intValue2 == 2) {
                                z17 = true;
                            } else if (intValue2 == 3) {
                                z17 = true;
                                String str11 = extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29));
                                Object obj2 = dipState_.get(i30);
                                Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
                                G = handlerLights.G(dataMaster, str11, ((Number) obj2).intValue(), z16);
                                if (G) {
                                    Timber.forest.d("Enabled thing - channel " + i29, new Object[0]);
                                    z31 = z17;
                                }
                                if (!sendBroadcastThing && G) {
                                    sendBroadcastThing = z17;
                                }
                                boolean isValidLight3 = handlerLights.isValidLight(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)));
                                if (!sendBroadcast && isValidLight3) {
                                    sendBroadcast = z17;
                                }
                                Unit unit = Unit.INSTANCE;
                                i28 = i31;
                            } else if (intValue2 != 4) {
                                if (intValue2 != 8) {
                                    if (intValue2 != 10) {
                                        Timber.Forest forest3 = Timber.forest;
                                        forest3.d("DBG-Invalid DIP RM2", new Object[0]);
                                        boolean isValidThing2 = handlerLights.isValidThing(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)));
                                        boolean isValidLight4 = handlerLights.isValidLight(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)));
                                        if (!sendBroadcastThing && isValidThing2) {
                                            forest3.d("DBG-Invalid DIP RM2: sendBroadcastThing", new Object[0]);
                                            sendBroadcastThing = true;
                                        }
                                        if (!sendBroadcast && isValidLight4) {
                                            forest3.d("DBG-Invalid DIP RM2: sendBroadcast", new Object[0]);
                                            sendBroadcast = true;
                                        }
                                        Unit unit2 = Unit.INSTANCE;
                                        z29 = true;
                                        i28 = i31;
                                    } else {
                                        z17 = true;
                                    }
                                }
                                String str112 = extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29));
                                Object obj22 = dipState_.get(i30);
                                Intrinsics.checkNotNullExpressionValue(obj22, "get(...)");
                                G = handlerLights.G(dataMaster, str112, ((Number) obj22).intValue(), z16);
                                if (G) {
                                }
                                if (!sendBroadcastThing) {
                                    sendBroadcastThing = z17;
                                }
                                boolean isValidLight32 = handlerLights.isValidLight(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)));
                                if (!sendBroadcast) {
                                    sendBroadcast = z17;
                                }
                                Unit unit3 = Unit.INSTANCE;
                                i28 = i31;
                            } else {
                                Timber.Forest forest4 = Timber.forest;
                                forest4.d("RM2 module infobyte", new Object[0]);
                                boolean registerOrUpdateLight3 = registerOrUpdateLight(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)), true, DataLight.MODULE_TYPE_STRING_RM2, z16);
                                if (registerOrUpdateLight3) {
                                    forest4.d("Enabled thing light - channel " + i29, new Object[0]);
                                    z30 = true;
                                }
                                if (!sendBroadcast && registerOrUpdateLight3) {
                                    sendBroadcast = true;
                                }
                                boolean isValidThing3 = handlerLights.isValidThing(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)));
                                if (!sendBroadcastThing && isValidThing3) {
                                    sendBroadcastThing = true;
                                }
                                Unit unit4 = Unit.INSTANCE;
                                i28 = i31;
                            }
                            String str1122 = extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29));
                            Object obj222 = dipState_.get(i30);
                            Intrinsics.checkNotNullExpressionValue(obj222, "get(...)");
                            G = handlerLights.G(dataMaster, str1122, ((Number) obj222).intValue(), z16);
                            if (G) {
                            }
                            if (!sendBroadcastThing) {
                            }
                            boolean isValidLight322 = handlerLights.isValidLight(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)));
                            if (!sendBroadcast) {
                            }
                            Unit unit32 = Unit.INSTANCE;
                            i28 = i31;
                        } else {
                            i28 = i31 + 1;
                            boolean isValidThing4 = handlerLights.isValidThing(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)));
                            boolean isValidLight5 = handlerLights.isValidLight(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)));
                            if (!sendBroadcastThing && isValidThing4) {
                                sendBroadcastThing = true;
                            }
                            if (!sendBroadcast && isValidLight5) {
                                sendBroadcast = true;
                            }
                            Unit unit5 = Unit.INSTANCE;
                        }
                        i29++;
                        dipState = dipState_;
                        systemTypeIs08_ = z16;
                        i27 = i10;
                        b10 = Byte.MIN_VALUE;
                    } else if (intValue2 == 0) {
                        i10 = i27;
                        dipState_ = dipState;
                        z16 = systemTypeIs08_;
                        Timber.Forest forest5 = Timber.forest;
                        forest5.d("DBG-Channel not used DM", new Object[0]);
                        i28++;
                        boolean isValidThing5 = handlerLights.isValidThing(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)));
                        boolean isValidLight6 = handlerLights.isValidLight(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)));
                        if (!sendBroadcastThing && isValidThing5) {
                            forest5.d("DBG-Channel not used DM: sendBroadcastThing", new Object[0]);
                            sendBroadcastThing = true;
                        }
                        if (!sendBroadcast && isValidLight6) {
                            forest5.d("DBG-Channel not used DM: sendBroadcast", new Object[0]);
                            sendBroadcast = true;
                        }
                        Unit unit6 = Unit.INSTANCE;
                    } else if (intValue2 != 9) {
                        if (intValue2 == 4) {
                            i10 = i27;
                            i11 = i28;
                            dipState_ = dipState;
                            Timber.Forest forest6 = Timber.forest;
                            forest6.d("DM module infobyte", new Object[0]);
                            boolean registerOrUpdateLight4 = registerOrUpdateLight(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)), true, DataLight.MODULE_TYPE_STRING_DM, systemTypeIs08_);
                            if (registerOrUpdateLight4) {
                                forest6.d("Enabled thing light - channel " + i29, new Object[0]);
                                z30 = true;
                            }
                            if (!sendBroadcast && registerOrUpdateLight4) {
                                sendBroadcast = true;
                            }
                            boolean isValidThing6 = handlerLights.isValidThing(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)));
                            if (!sendBroadcastThing && isValidThing6) {
                                sendBroadcastThing = true;
                            }
                            Unit unit7 = Unit.INSTANCE;
                        } else if (intValue2 != 5) {
                            Timber.Forest forest7 = Timber.forest;
                            forest7.d("DBG-Invalid DIP DM", new Object[0]);
                            boolean isValidThing7 = handlerLights.isValidThing(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)));
                            boolean isValidLight7 = handlerLights.isValidLight(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)));
                            if (!sendBroadcastThing && isValidThing7) {
                                forest7.d("DBG-Invalid DIP DM: sendBroadcastThing", new Object[0]);
                                sendBroadcastThing = true;
                            }
                            if (!sendBroadcast && isValidLight7) {
                                forest7.d("DBG-Invalid DIP DM: sendBroadcast", new Object[0]);
                                sendBroadcast = true;
                            }
                            Unit unit8 = Unit.INSTANCE;
                            dipState_ = dipState;
                            z16 = systemTypeIs08_;
                            z29 = true;
                            i10 = 7;
                        } else {
                            Timber.Forest forest8 = Timber.forest;
                            forest8.d("DM light detected!!!", new Object[0]);
                            i10 = 7;
                            i11 = i28;
                            dipState_ = dipState;
                            boolean registerOrUpdateLight5 = registerOrUpdateLight(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)), false, DataLight.MODULE_TYPE_STRING_DM, systemTypeIs08_);
                            if (registerOrUpdateLight5) {
                                forest8.d("Enabled thing light - channel " + i29, new Object[0]);
                                z30 = true;
                            }
                            if (!sendBroadcast && registerOrUpdateLight5) {
                                sendBroadcast = true;
                            }
                            boolean isValidThing8 = handlerLights.isValidThing(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)));
                            if (!sendBroadcastThing && isValidThing8) {
                                sendBroadcastThing = true;
                            }
                            Unit unit9 = Unit.INSTANCE;
                        }
                        i28 = i11;
                        z16 = systemTypeIs08_;
                    } else {
                        i10 = i27;
                        int i32 = i28;
                        dipState_ = dipState;
                        String str12 = extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29));
                        Object obj3 = dipState_.get(i30);
                        Intrinsics.checkNotNullExpressionValue(obj3, "get(...)");
                        z16 = systemTypeIs08_;
                        boolean G2 = handlerLights.G(dataMaster, str12, ((Number) obj3).intValue(), z16);
                        if (G2) {
                            Timber.forest.d("Enabled thing - channel " + i29, new Object[0]);
                            z31 = true;
                        }
                        if (!sendBroadcastThing && G2) {
                            sendBroadcastThing = true;
                        }
                        boolean isValidLight8 = handlerLights.isValidLight(dataMaster, extractUIDValue5 + handlerLights.formatIntToHex(Integer.valueOf(i29)));
                        if (!sendBroadcast && isValidLight8) {
                            sendBroadcast = true;
                        }
                        Unit unit10 = Unit.INSTANCE;
                        i28 = i32;
                    }
                    i29++;
                    dipState = dipState_;
                    systemTypeIs08_ = z16;
                    i27 = i10;
                    b10 = Byte.MIN_VALUE;
                }
                int i33 = i28;
                z10 = systemTypeIs08_;
                z11 = true;
                if (z29) {
                    if (((byte) (((byte) infoByte.intValue()) & Byte.MIN_VALUE)) == Byte.MIN_VALUE) {
                        if (!dataMaster.listOfDMWithInvalidDipSetting.contains(extractUIDValue5)) {
                            dataMaster.listOfDMWithInvalidDipSetting.add(extractUIDValue5);
                            z15 = true;
                            sendBroadcastThing = true;
                        }
                        z15 = sendBroadcast;
                    } else {
                        if (!dataMaster.listOfRM2WithInvalidDipSetting.contains(extractUIDValue5)) {
                            dataMaster.listOfRM2WithInvalidDipSetting.add(extractUIDValue5);
                            z15 = true;
                            sendBroadcastThing = true;
                        }
                        z15 = sendBroadcast;
                    }
                } else if (((byte) (((byte) infoByte.intValue()) & Byte.MIN_VALUE)) == Byte.MIN_VALUE) {
                    if (dataMaster.listOfDMWithInvalidDipSetting.contains(extractUIDValue5)) {
                        dataMaster.listOfDMWithInvalidDipSetting.remove(extractUIDValue5);
                        z15 = true;
                        sendBroadcastThing = true;
                    }
                    z15 = sendBroadcast;
                } else {
                    if (dataMaster.listOfRM2WithInvalidDipSetting.contains(extractUIDValue5)) {
                        dataMaster.listOfRM2WithInvalidDipSetting.remove(extractUIDValue5);
                        z15 = true;
                        sendBroadcastThing = true;
                    }
                    z15 = sendBroadcast;
                }
                if (i33 == 6) {
                    if (((byte) (((byte) infoByte.intValue()) & Byte.MIN_VALUE)) == Byte.MIN_VALUE) {
                        if (!dataMaster.listOfNonConfiguredDM.contains(extractUIDValue5)) {
                            dataMaster.listOfNonConfiguredDM.add(extractUIDValue5);
                            z15 = true;
                            sendBroadcastThing = true;
                        }
                        if (z10) {
                            lmAck = handlerLights.lmAck(extractUIDValue5);
                            Timber.forest.d("sending JZ19(2):" + lmAck, new Object[0]);
                        } else {
                            lmAck = handlerLights.I(extractUIDValue5);
                            Timber.forest.d("sending JZ19(RF2):" + lmAck, new Object[0]);
                        }
                        context2 = context;
                        handlerLights.U.sendCanMessageToCB(context2, lmAck);
                        validLight = z15;
                        dataMaster2 = dataMaster;
                        str5 = extractUIDValue5;
                        z13 = z30;
                        z26 = z31;
                        z12 = false;
                        dataModuleInfoSource = null;
                        z14 = false;
                    } else {
                        if (!dataMaster.listOfNonConfiguredRM2.contains(extractUIDValue5)) {
                            dataMaster.listOfNonConfiguredRM2.add(extractUIDValue5);
                            z15 = true;
                            sendBroadcastThing = true;
                        }
                        if (z10) {
                        }
                        context2 = context;
                        handlerLights.U.sendCanMessageToCB(context2, lmAck);
                        validLight = z15;
                        dataMaster2 = dataMaster;
                        str5 = extractUIDValue5;
                        z13 = z30;
                        z26 = z31;
                        z12 = false;
                        dataModuleInfoSource = null;
                        z14 = false;
                    }
                } else if (((byte) (((byte) infoByte.intValue()) & Byte.MIN_VALUE)) == Byte.MIN_VALUE) {
                    if (dataMaster.listOfNonConfiguredDM.contains(extractUIDValue5)) {
                        dataMaster.listOfNonConfiguredDM.remove(extractUIDValue5);
                        z15 = true;
                        sendBroadcastThing = true;
                    }
                    if (z10) {
                    }
                    context2 = context;
                    handlerLights.U.sendCanMessageToCB(context2, lmAck);
                    validLight = z15;
                    dataMaster2 = dataMaster;
                    str5 = extractUIDValue5;
                    z13 = z30;
                    z26 = z31;
                    z12 = false;
                    dataModuleInfoSource = null;
                    z14 = false;
                } else {
                    if (dataMaster.listOfNonConfiguredRM2.contains(extractUIDValue5)) {
                        dataMaster.listOfNonConfiguredRM2.remove(extractUIDValue5);
                        z15 = true;
                        sendBroadcastThing = true;
                    }
                    if (z10) {
                    }
                    context2 = context;
                    handlerLights.U.sendCanMessageToCB(context2, lmAck);
                    validLight = z15;
                    dataMaster2 = dataMaster;
                    str5 = extractUIDValue5;
                    z13 = z30;
                    z26 = z31;
                    z12 = false;
                    dataModuleInfoSource = null;
                    z14 = false;
                }
            } else {
                context2 = context;
                z10 = systemTypeIs08_;
                z11 = true;
                if (handlerLights.compareFRLValue(message, 9, FRLParser.B)) {
                    str5 = handlerLights.extractUIDValue(message, 4);
                    if (str5 == null) {
                        Timber.forest.d("Rejected RM2 status message - invalid UID " + message, new Object[0]);
                        return;
                    }
                    Integer parseHexToInt10 = handlerLights.parseHexToInt(message, 11);
                    if (parseHexToInt10 == null) {
                        Timber.forest.d("Rejected RM2 status message - RM2 Firmware Rev Major", new Object[0]);
                        return;
                    }
                    Integer parseHexToInt11 = handlerLights.parseHexToInt(message, 13);
                    if (parseHexToInt11 == null) {
                        Timber.forest.d("Rejected RM2 status message - RM2 Firmware Rev Minor", new Object[0]);
                        return;
                    }
                    Integer parseHexToInt12 = handlerLights.parseHexToInt(message, 15);
                    if (parseHexToInt12 == null) {
                        Timber.forest.d("Rejected RM2 status message - RM2 Info byte", new Object[0]);
                        return;
                    }
                    Timber.Forest forest9 = Timber.forest;
                    forest9.d("JZ39 info byte: " + parseHexToInt12 + " for uid:" + str5, new Object[0]);
                    forest9.d("Valid RM2 setup message JZ39. UID - " + str5 + " version - " + parseHexToInt10 + "." + parseHexToInt11, new Object[0]);
                    dataModuleInfoSource = new DataModuleInfoSource();
                    if (((byte) (((byte) parseHexToInt12.intValue()) & Byte.MIN_VALUE)) == Byte.MIN_VALUE) {
                        dataModuleInfoSource.moduleType = DataLight.MODULE_TYPE_STRING_DM;
                    } else {
                        if (((byte) (((byte) parseHexToInt12.intValue()) & 16)) == 16) {
                            dataModuleInfoSource.moduleType = "GDM";
                            if (z10) {
                                dataMaster.myGarageRFControllers.addGarageController(new DataGroupSource(str5));
                                DataGroupSource garageController = dataMaster.myGarageRFControllers.getGarageController(str5);
                                if (garageController != null) {
                                    dataMaster2 = dataMaster;
                                    garageController.expiryTime = CommonFuncs.getUptime() + nonRFExpiry;
                                }
                            }
                        } else {
                            dataMaster2 = dataMaster;
                            dataModuleInfoSource.moduleType = DataLight.MODULE_TYPE_STRING_RM2;
                        }
                        dataModuleInfoSource.firmwareVersion = parseHexToInt10 + "." + parseHexToInt11;
                        if (z10 && (K = handlerLights.K(str5)) != "") {
                            forest9.d("Adding RF JZ3 with Uid to RF devices to queue", new Object[0]);
                            ((SendMessageToCB) KoinJavaComponent.get$default(SendMessageToCB.class, null, null, 6, null)).addCBMessage(K);
                        }
                        z12 = true;
                    }
                    dataMaster2 = dataMaster;
                    dataModuleInfoSource.firmwareVersion = parseHexToInt10 + "." + parseHexToInt11;
                    if (z10) {
                        forest9.d("Adding RF JZ3 with Uid to RF devices to queue", new Object[0]);
                        ((SendMessageToCB) KoinJavaComponent.get$default(SendMessageToCB.class, null, null, 6, null)).addCBMessage(K);
                    }
                    z12 = true;
                } else {
                    dataMaster2 = dataMaster;
                    Timber.forest.d("Rejected can message - incorrect message type", new Object[0]);
                    dataModuleInfoSource = null;
                    z12 = false;
                }
                z13 = false;
                z14 = false;
                z26 = false;
                sendBroadcastThing = false;
                validLight = false;
            }
            dataSystem = new DataSystem();
            if (!z14 || z13) {
                dataSystem.hasLights = Boolean.TRUE;
            }
            if (!z12 || z26) {
                dataSystem.hasThings = Boolean.TRUE;
            }
            if (z13) {
                dataSystem.hasThingsLight = Boolean.TRUE;
            }
            if (z26) {
                dataSystem.hasThingsBOG = Boolean.TRUE;
            }
            if (dataModuleInfoSource == null) {
                dataMaster5 = dataMaster2;
            } else if (!(str5.length() == 0 ? z11 : false)) {
                dataMaster5 = dataMaster2;
                HashMap<String, DataModuleInfoSource> hashMap = dataMaster5.system.versions;
                Intrinsics.checkNotNull(hashMap);
                if (hashMap.containsKey(str5)) {
                    HashMap<String, DataModuleInfoSource> hashMap2 = dataMaster5.system.versions;
                    Intrinsics.checkNotNull(hashMap2);
                    if (hashMap2.containsKey(str5)) {
                        HashMap<String, DataModuleInfoSource> hashMap3 = dataMaster5.system.versions;
                        Intrinsics.checkNotNull(hashMap3);
                        DataModuleInfoSource dataModuleInfoSource3 = hashMap3.get(str5);
                        Intrinsics.checkNotNull(dataModuleInfoSource3);
                        if (!dataModuleInfoSource3.equals(dataModuleInfoSource)) {
                            HashMap<String, DataModuleInfoSource> hashMap4 = new HashMap<>();
                            dataSystem.versions = hashMap4;
                            Intrinsics.checkNotNull(hashMap4);
                            hashMap4.put(str5, dataModuleInfoSource);
                            HashMap<String, DataModuleInfoSource> hashMap5 = dataMaster5.system.versions;
                            if (hashMap5 != null) {
                                Intrinsics.checkNotNull(hashMap5);
                                for (String str13 : hashMap5.keySet()) {
                                    if (!Intrinsics.areEqual(str13, str5)) {
                                        HashMap<String, DataModuleInfoSource> hashMap6 = dataSystem.versions;
                                        Intrinsics.checkNotNull(hashMap6);
                                        Intrinsics.checkNotNull(str13);
                                        HashMap<String, DataModuleInfoSource> hashMap7 = dataMaster5.system.versions;
                                        Intrinsics.checkNotNull(hashMap7);
                                        hashMap6.put(str13, hashMap7.get(str13));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (str5.length() == 0) {
                z11 = false;
            }
            if (!z11) {
                if (z10) {
                    dataMaster5.moduleExpiryTime.put(str5, Long.valueOf(CommonFuncs.getUptime() + nonRFExpiry));
                } else {
                    dataMaster5.moduleExpiryTime.put(str5, Long.valueOf(CommonFuncs.getUptime() + 70));
                }
            }
            if (HandlerAircon.Companion.getInstance().update(dataMaster5, dataSystem)) {
                Timber.forest.d("hasLights or hasThings or hasThingsLight changed to true", new Object[0]);
                HandlerJson.Companion.getInstance(context2).processData(dataMaster5, "handlerLights");
            }
            if ((z14 || z13) && z7 && validLight) {
                handlerLights.updateLightJson(context2, dataMaster5);
            }
            if (z7 && sendBroadcastThing) {
                handlerLights.updateThingJson(context2, dataMaster5);
            }
            Unit unit11 = Unit.INSTANCE;
            z14 = true;
            z26 = false;
            sendBroadcastThing = false;
            z11 = true;
            dataSystem = new DataSystem();
            if (!z14) {
                dataSystem.hasLights = Boolean.TRUE;
            }
            if (!z12) {
                dataSystem.hasThings = Boolean.TRUE;
            }
            if (z13) {
            }
            if (z26) {
            }
            if (dataModuleInfoSource == null) {
            }
            if (str5.length() == 0) {
            }
            if (!z11) {
            }
            if (HandlerAircon.Companion.getInstance().update(dataMaster5, dataSystem)) {
            }
            if (z14) {
                handlerLights.updateLightJson(context2, dataMaster5);
            } else {
                handlerLights.updateLightJson(context2, dataMaster5);
            }
            if (z7) {
                handlerLights.updateThingJson(context2, dataMaster5);
            }
            Unit unit112 = Unit.INSTANCE;
        }
    }

    @Nullable
    public final DataScene f0(@Nullable Context context, @Nullable DataScene dataScene, @NotNull DataMaster masterData) {
        HashMap<String, DataAirconSystem> hashMap;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (dataScene != null && (hashMap = dataScene.aircons) != null) {
            Intrinsics.checkNotNull(hashMap);
            if (hashMap.size() > 0) {
                DataScene copy = dataScene.copy();
                HashMap<String, DataAirconSystem> hashMap2 = copy.aircons;
                Intrinsics.checkNotNull(hashMap2);
                for (String str : hashMap2.keySet()) {
                    DataAirconSystem dataAirconSystem = masterData.aircons.get(str);
                    HashMap<String, DataAirconSystem> hashMap3 = copy.aircons;
                    Intrinsics.checkNotNull(hashMap3);
                    DataAirconSystem dataAirconSystem2 = hashMap3.get(str);
                    if (dataAirconSystem != null && dataAirconSystem2 != null) {
                        DataAirconInfo dataAirconInfo = dataAirconSystem2.info;
                        DataAirconInfo dataAirconInfo2 = dataAirconSystem.info;
                        dataAirconInfo.uid = dataAirconInfo2.uid;
                        DataAirconSystem.FreshAirStatus freshAirStatus = dataAirconInfo2.freshAirStatus;
                        DataAirconSystem.FreshAirStatus freshAirStatus2 = DataAirconSystem.FreshAirStatus.none;
                        if (freshAirStatus == freshAirStatus2) {
                            dataAirconInfo.freshAirStatus = freshAirStatus2;
                        } else if (dataAirconInfo.freshAirStatus == freshAirStatus2) {
                            dataAirconInfo.freshAirStatus = freshAirStatus;
                        }
                    }
                }
                return copy;
            }
        }
        return dataScene;
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:54:0x01b0 */
    /* JADX DEBUG: Multi-variable search result rejected for r10v1, resolved type: int */
    /* JADX DEBUG: Multi-variable search result rejected for r15v3, resolved type: int */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e1 A[Catch: all -> 0x01e1, TryCatch #0 {, blocks: (B:4:0x0007, B:5:0x001f, B:7:0x0025, B:9:0x0036, B:11:0x004d, B:13:0x0061, B:15:0x0067, B:17:0x006d, B:19:0x008e, B:20:0x0095, B:22:0x0099, B:23:0x00a0, B:26:0x00c1, B:33:0x00e1, B:35:0x00eb, B:37:0x00ff, B:39:0x0105, B:42:0x010c, B:45:0x015e, B:46:0x0166, B:48:0x0174, B:50:0x0187, B:51:0x018e, B:53:0x01a2, B:57:0x01b4, B:58:0x01b9, B:60:0x01bf, B:63:0x01d1, B:64:0x01dd), top: B:3:0x0007 }] */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void g0(@Nullable Context context) {
        Iterator<b> it;
        boolean z7;
        boolean z10;
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            ArrayList arrayList = new ArrayList();
            Iterator<b> it2 = this.Z.values().iterator();
            int i10 = 0;
            boolean z11 = false;
            while (it2.hasNext()) {
                b next = it2.next();
                if (next.a() != null) {
                    long uptime = CommonFuncs.getUptime();
                    Long a = next.a();
                    Intrinsics.checkNotNull(a);
                    if (uptime > a.longValue() + 14) {
                        long uptime2 = CommonFuncs.getUptime();
                        Long a2 = next.a();
                        Intrinsics.checkNotNull(a2);
                        if (uptime2 <= a2.longValue() + 30) {
                            DataMyThing d3 = next.d();
                            if (d3 == null || next.e() == null) {
                                it = it2;
                                z10 = i10;
                            } else {
                                Timber.forest.d("garageV2 we're not blocking update and going to remove from the filter table1", new Object[i10]);
                                r(dataMaster, next.g(), d3.batteryLow);
                                s(dataMaster, next.g(), i10);
                                String g8 = next.g();
                                Integer num = d3.value;
                                int intValue = num != null ? num.intValue() : i10;
                                Integer num2 = d3.dimPercent;
                                it = it2;
                                z10 = i10;
                                if (U0(dataMaster, g8, intValue, num2 != null ? num2.intValue() : i10, true, next.e(), d3.batteryLow, Boolean.FALSE, d3.isCalibrated)) {
                                    z11 = true;
                                }
                                next.k(new DataMyThing());
                                next.l(-1);
                                arrayList.add(next.g());
                            }
                        } else {
                            it = it2;
                            int i11 = i10;
                            z10 = i11;
                            if (next.a() != null) {
                                long uptime3 = CommonFuncs.getUptime();
                                Long a10 = next.a();
                                Intrinsics.checkNotNull(a10);
                                z10 = i11;
                                if (uptime3 > a10.longValue() + 30) {
                                    if (next.d() == null || next.e() == null) {
                                        DataMyThing thingData = dataMaster.myThings.getThingData(next.g());
                                        z10 = i11;
                                        if (thingData != null) {
                                            s(dataMaster, next.g(), i11);
                                            String g10 = next.g();
                                            int f3 = next.f();
                                            Integer num3 = thingData.dimPercent;
                                            int intValue2 = num3 != null ? num3.intValue() : i11;
                                            z10 = i11;
                                            if (U0(dataMaster, g10, f3, intValue2, true, next.e(), thingData.batteryLow, Boolean.FALSE, thingData.isCalibrated)) {
                                                Timber.forest.d("processGarageV2State AA153 detected and broadcasted", new Object[i11]);
                                                z7 = i11;
                                                z11 = true;
                                            }
                                        }
                                    } else {
                                        Timber.forest.d("processGarageV2State there's an update", new Object[i11]);
                                        DataMyThing d10 = next.d();
                                        Intrinsics.checkNotNull(d10);
                                        r(dataMaster, next.g(), d10.batteryLow);
                                        s(dataMaster, next.g(), i11);
                                        String g11 = next.g();
                                        Integer num4 = d10.value;
                                        Intrinsics.checkNotNull(num4);
                                        int intValue3 = num4.intValue();
                                        Integer num5 = d10.dimPercent;
                                        Intrinsics.checkNotNull(num5);
                                        if (U0(dataMaster, g11, intValue3, num5.intValue(), true, next.e(), d10.batteryLow, Boolean.FALSE, d10.isCalibrated)) {
                                            z11 = true;
                                        }
                                        arrayList.add(next.g());
                                        z10 = i11;
                                    }
                                }
                            }
                        }
                        z7 = z10;
                    }
                }
                it2 = it;
                i10 = z7;
            }
            int i12 = i10;
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                TypeIntrinsics.k(this.Z).remove((String) it3.next());
            }
            if (z11) {
                Timber.forest.d("processGarageV2State garageV2 broadcasting.....updateThingJson", new Object[i12]);
                updateThingJson(context, dataMaster);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    /* renamed from: i0 */
    public final void setRemoveRFThing(@Nullable String str, @NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (str != null) {
            if (str.length() == 0) {
                return;
            }
            Timber.forest.d("removeRFDevice received, id: " + str, new Object[0]);
            synchronized (MyMasterData.class) {
                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
                TreeMap<String, DataMyThing> treeMap = dataMaster.myThings.things;
                Intrinsics.checkNotNull(treeMap);
                DataMyThing dataMyThing = treeMap.get(str);
                if (dataMyThing != null) {
                    dataMyThing.expiryTime = Long.valueOf(CommonFuncs.getUptime() - 1);
                    C(context, dataMaster);
                }
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00af A[Catch: all -> 0x00fb, TryCatch #0 {, blocks: (B:8:0x002d, B:10:0x003b, B:12:0x0071, B:13:0x0079, B:15:0x007f, B:21:0x0091, B:22:0x00a1, B:24:0x00af, B:26:0x00d4, B:28:0x00d7, B:31:0x00da, B:32:0x00f4, B:42:0x009d, B:43:0x00de), top: B:7:0x002d }] */
    /* renamed from: j0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int runLightAlarm(@NotNull Context context, @Nullable String str) throws ExceptionUart {
        List emptyList;
        Intrinsics.checkNotNullParameter(context, "context");
        Timber.Forest forest = Timber.forest;
        forest.d("runLightAlarm JSON received", new Object[0]);
        DataAlarm dataAlarm = (DataAlarm) this.V.fromJson(str, DataAlarm.class);
        if (dataAlarm == null) {
            throw new ExceptionUart("Invalid json received");
        }
        forest.d("runLightAlarm received", new Object[0]);
        String str2 = dataAlarm.id;
        if (!isValidAlarmId(str2)) {
            throw new ExceptionUart("Invalid alarm id");
        }
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            DataAlarm alarm = dataMaster.myLights.getAlarm(str2);
            if (alarm != null) {
                forest.d("runLightAlarm running " + str2 + " with " + alarm.canMessages, new Object[0]);
                String str3 = alarm.canMessages;
                Intrinsics.checkNotNull(str3);
                List<String> split = new Regex(" ").split(str3, 0);
                if (split.isEmpty()) {
                    emptyList = CollectionsKt.emptyList();
                    while (r4 < r0) {
                    }
                    updateLightJson(context, dataMaster);
                } else {
                    ListIterator<String> listIterator = split.listIterator(split.size());
                    while (listIterator.hasPrevious()) {
                        if (!(listIterator.previous().length() == 0)) {
                            emptyList = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                            break;
                        }
                    }
                    emptyList = CollectionsKt.emptyList();
                    for (String str4 : (String[]) emptyList.toArray(new String[0])) {
                        Timber.forest.d("runLightAlarm - sending " + str4, new Object[0]);
                        this.U.sendCanMessageToCB(context, str4);
                        String O = O(str4);
                        if (O != null) {
                            parseMessage(context, O, false);
                        }
                    }
                    updateLightJson(context, dataMaster);
                }
            }
            forest.d("runLightAlarm? unknown id " + str2, new Object[0]);
            updateLightJson(context, dataMaster);
        }
        return WebServer.ACK;
    }

    public final int k0(@NotNull Context context, @Nullable String str) throws ExceptionUart {
        Intrinsics.checkNotNullParameter(context, "context");
        Timber.Forest forest = Timber.forest;
        forest.d("runLightScene JSON received", new Object[0]);
        DataScene dataScene = (DataScene) this.V.fromJson(str, DataScene.class);
        if (dataScene == null) {
            throw new ExceptionUart("Invalid json message");
        }
        forest.d("runLightScene received", new Object[0]);
        String str2 = dataScene.id;
        if (!isSceneId(str2)) {
            throw new ExceptionUart("setLightScene invalid scene id");
        }
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            DataScene dataScene2 = DataMyScene.SCENE_MY_UNDO;
            if (!Intrinsics.areEqual(str2, dataScene2.id)) {
                l0(context, dataMaster, dataScene2);
            }
            DataScene scene = dataMaster.myScenes.getScene(str2);
            if (scene != null) {
                DataScene V = V(context, f0(context, scene, dataMaster), dataMaster);
                String L = L(context, V);
                if (o0(context, L, V, dataMaster)) {
                    forest.d("runLightScene running " + str2 + " with " + L, new Object[0]);
                }
                HandlerAircon.Companion.getInstance().checkAndRunSceneSchedule(V);
            } else if (Intrinsics.areEqual(DataMyScene.SCENE_MY_GOODBYE.id, str2)) {
                TreeMap<String, DataGroup> treeMap = dataMaster.myLights.groups;
                Intrinsics.checkNotNull(treeMap);
                Collection<DataGroup> values = treeMap.values();
                Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                ArrayList arrayList = new ArrayList();
                for (DataGroup dataGroup : values) {
                    if (dataGroup != null) {
                        arrayList.add(dataGroup);
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    sendCanMessageToCB(context, dataMaster, ((DataGroup) it.next()).id, LightState.off);
                }
                updateLightJson(context, dataMaster);
            } else if (Intrinsics.areEqual(DataMyScene.SCENE_MY_WELCOME.id, str2)) {
                TreeMap<String, DataGroup> treeMap2 = dataMaster.myLights.groups;
                Intrinsics.checkNotNull(treeMap2);
                Collection<DataGroup> values2 = treeMap2.values();
                Intrinsics.checkNotNullExpressionValue(values2, "<get-values>(...)");
                ArrayList arrayList2 = new ArrayList();
                for (DataGroup dataGroup2 : values2) {
                    if (dataGroup2 != null) {
                        arrayList2.add(dataGroup2);
                    }
                }
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    sendCanMessageToCB(context, dataMaster, ((DataGroup) it2.next()).id, LightState.on);
                }
                updateLightJson(context, dataMaster);
            } else if (Intrinsics.areEqual(DataMyScene.SCENE_MY_SUNSET.id, str2)) {
                TreeMap<String, DataGroup> treeMap3 = dataMaster.myLights.groups;
                Intrinsics.checkNotNull(treeMap3);
                Collection<DataGroup> values3 = treeMap3.values();
                Intrinsics.checkNotNullExpressionValue(values3, "<get-values>(...)");
                ArrayList arrayList3 = new ArrayList();
                for (DataGroup dataGroup3 : values3) {
                    if (dataGroup3 != null) {
                        arrayList3.add(dataGroup3);
                    }
                }
                Iterator it3 = arrayList3.iterator();
                while (it3.hasNext()) {
                    sendCanMessageToCB(context, dataMaster, ((DataGroup) it3.next()).id, LightState.on);
                }
                updateLightJson(context, dataMaster);
            } else {
                if (!Intrinsics.areEqual(DataMyScene.SCENE_MY_ECO.id, str2)) {
                    throw new ExceptionUart("runLightScene? unknown id " + str2);
                }
                TreeMap<String, DataLight> treeMap4 = dataMaster.myLights.lights;
                Intrinsics.checkNotNull(treeMap4);
                Collection<DataLight> values4 = treeMap4.values();
                Intrinsics.checkNotNullExpressionValue(values4, "<get-values>(...)");
                ArrayList<DataLight> arrayList4 = new ArrayList();
                for (DataLight dataLight : values4) {
                    if (dataLight != null) {
                        arrayList4.add(dataLight);
                    }
                }
                for (DataLight dataLight2 : arrayList4) {
                    Integer num = dataLight2.value;
                    if (num != null) {
                        Intrinsics.checkNotNull(num);
                        if (num.intValue() > 80) {
                            dataLight2.value = 80;
                            Intrinsics.checkNotNull(dataLight2);
                            sendCanMessageToCB(context, dataLight2);
                        }
                    }
                }
                updateLightJson(context, dataMaster);
            }
        }
        return WebServer.ACK;
    }

    public final boolean o0(@NotNull Context context, @Nullable String str, @Nullable DataScene dataScene, @NotNull DataMaster masterData) {
        List emptyList;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        Intrinsics.checkNotNull(dataScene);
        if (dataScene.monitors != null) {
            HandlerMonitor.Companion.b().F(context, masterData, dataScene.monitors);
        }
        HashMap<String, Sonos> hashMap = dataScene.sonos;
        if (hashMap != null) {
            Intrinsics.checkNotNull(hashMap);
            if (hashMap.size() > 0 && this.f7179a0.u().getValue() != null) {
                List<Sonos> value = this.f7179a0.u().getValue();
                Intrinsics.checkNotNull(value);
                if (value.size() > 0) {
                    HashMap<String, Sonos> hashMap2 = dataScene.sonos;
                    Intrinsics.checkNotNull(hashMap2);
                    for (Sonos sonos : hashMap2.values()) {
                        String component1 = sonos.component1();
                        boolean component8 = sonos.component8();
                        List<Sonos> value2 = this.f7179a0.u().getValue();
                        Intrinsics.checkNotNull(value2);
                        for (Sonos sonos2 : value2) {
                            if (Intrinsics.areEqual(component1, sonos2.getUdn())) {
                                if (component8) {
                                    this.f7179a0.F(sonos2);
                                } else {
                                    this.f7179a0.E(sonos2);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (str == null || str.length() == 0) {
            return false;
        }
        List<String> split = new Regex(" ").split(str, 0);
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
        for (String str2 : (String[]) emptyList.toArray(new String[0])) {
            Timber.forest.d("sendSceneCanMessagesAndBroadcastJsonUpdate - sending " + str2, new Object[0]);
            this.U.sendCanMessageToCB(context, str2);
        }
        HashMap<String, DataLight> hashMap3 = dataScene.lights;
        if (hashMap3 != null) {
            Intrinsics.checkNotNull(hashMap3);
            Collection<DataLight> values = hashMap3.values();
            Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
            ArrayList<DataLight> arrayList = new ArrayList();
            for (DataLight dataLight : values) {
                if (dataLight != null) {
                    arrayList.add(dataLight);
                }
            }
            for (DataLight dataLight2 : arrayList) {
                TreeMap<String, DataLight> treeMap = masterData.myLights.lights;
                Intrinsics.checkNotNull(treeMap);
                DataLight dataLight3 = treeMap.get(dataLight2.id);
                if (dataLight3 != null) {
                    Boolean bool = dataScene.myTimeEnabled;
                    if (bool != null) {
                        Intrinsics.checkNotNull(bool);
                        if (bool.booleanValue()) {
                            Boolean bool2 = dataLight3.relay;
                            if (bool2 != null) {
                                Intrinsics.checkNotNull(bool2);
                                if (bool2.booleanValue()) {
                                    if (dataLight2.value != null) {
                                        dataLight2.value = null;
                                    }
                                    DataLight.update$default(dataLight3, null, dataLight2, null, false, 8, null);
                                }
                            }
                        }
                    }
                    Boolean bool3 = dataLight3.relay;
                    if (bool3 != null) {
                        Intrinsics.checkNotNull(bool3);
                        if (bool3.booleanValue() && dataLight2.value != null) {
                            dataLight2.value = null;
                        }
                    }
                    DataLight.update$default(dataLight3, null, dataLight2, null, false, 8, null);
                }
            }
        }
        HashMap<String, DataMyThing> hashMap4 = dataScene.things;
        if (hashMap4 != null) {
            Intrinsics.checkNotNull(hashMap4);
            Collection<DataMyThing> values2 = hashMap4.values();
            Intrinsics.checkNotNullExpressionValue(values2, "<get-values>(...)");
            ArrayList<DataMyThing> arrayList2 = new ArrayList();
            for (DataMyThing dataMyThing : values2) {
                if (dataMyThing != null) {
                    arrayList2.add(dataMyThing);
                }
            }
            for (DataMyThing dataMyThing2 : arrayList2) {
                TreeMap<String, DataMyThing> treeMap2 = masterData.myThings.things;
                Intrinsics.checkNotNull(treeMap2);
                DataMyThing dataMyThing3 = treeMap2.get(dataMyThing2.id);
                if (dataMyThing3 != null) {
                    DataMyThing.update$default(dataMyThing3, null, dataMyThing2, null, false, 8, null);
                }
            }
        }
        updateLightJson(context, masterData);
        updateThingJson(context, masterData);
        return true;
    }

    /* renamed from: p0 */
    public final int setGroupThing(@NotNull Context context, @Nullable String str) throws ExceptionUart {
        Object obj;
        Intrinsics.checkNotNullParameter(context, "context");
        Timber.forest.d("setThing JSON received", new Object[0]);
        try {
            obj = this.V.fromJson(str, DataGroupThing.class);
        } catch (JsonParseException e7) {
            AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
            obj = null;
        }
        if (obj == null) {
            throw new ExceptionUart("setGroupThing - invalid message.");
        }
        DataGroupThing dataGroupThing = (DataGroupThing) obj;
        if (dataGroupThing.id == null) {
            throw new ExceptionUart("setGroupThing - invalid id.");
        }
        boolean z7 = true;
        boolean z10 = dataGroupThing.state != null;
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            if (((DataGroupThing) obj).name != null) {
                String str2 = ((DataGroupThing) obj).name;
                Intrinsics.checkNotNull(str2);
                if (str2.length() > 12) {
                    throw new ExceptionUart("setGroupThing - name too long");
                }
                String str3 = ((DataGroupThing) obj).name;
                if (str3 == null || str3.length() == 0) {
                    Timber.forest.d("setGroupThing - deleting group " + ((DataGroupThing) obj).id, new Object[0]);
                    DataGroupThing dataGroupThing2 = dataMaster.myThings.getDataGroupThing(((DataGroupThing) obj).id);
                    if (dataGroupThing2 == null) {
                        throw new ExceptionUart("setGroupThing - group id doesn't exist");
                    }
                    y(dataMaster, dataGroupThing2);
                    updateThingJson(context, dataMaster);
                } else {
                    if (makeNewGroup_(dataMaster, ((DataGroupThing) obj).id, ((DataGroupThing) obj).name)) {
                        z10 = true;
                    } else {
                        Timber.forest.d("setGroupThing - too many groups or groups already exists", new Object[0]);
                    }
                    if (P0(dataMaster, ((DataGroupThing) obj).id, ((DataGroupThing) obj).name) != null) {
                    }
                }
                z7 = z10;
            } else {
                z7 = z10;
            }
            if (z7) {
                if (((DataGroupThing) obj).state != null) {
                    X0(context, dataMaster, ((DataGroupThing) obj).id, ((DataGroupThing) obj).state);
                }
                updateThingJson(context, dataMaster);
            }
            Unit unit = Unit.INSTANCE;
        }
        return WebServer.ACK;
    }

    /* renamed from: q0 */
    public final int setGroupThingName(@Nullable Context context, @NotNull Map<String, String> params) throws ExceptionUart {
        Intrinsics.checkNotNullParameter(params, "params");
        Timber.Forest forest = Timber.forest;
        forest.d("setGroupThingName received", new Object[0]);
        String str = params.get("id");
        if (!isValidGroupId(str)) {
            throw new ExceptionUart("setGroupThingName? invalid id");
        }
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            String str2 = params.get(AppMeasurementSdk.ConditionalUserProperty.NAME);
            if (str2 == null || str2.length() > 12) {
                if (str2 == null || str2.length() <= 12) {
                    forest.d("setGroupThingName - problem with name", new Object[0]);
                } else {
                    forest.d("setGroupThingName - name too long", new Object[0]);
                }
                Unit unit = Unit.INSTANCE;
                return WebServer.NACK;
            }
            forest.d("setGroupThingName - ID : " + str + " Name : " + str2, new Object[0]);
            if (str2.length() == 0) {
                forest.d("setGroupThingName - deleting group " + str, new Object[0]);
                DataGroupThing dataGroupThing = dataMaster.myThings.getDataGroupThing(str);
                if (dataGroupThing != null) {
                    y(dataMaster, dataGroupThing);
                }
            } else {
                P0(dataMaster, str, str2);
            }
            updateThingJson(context, dataMaster);
            return WebServer.ACK;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0082  */
    /* renamed from: r0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int setLight(@NotNull Context context, @Nullable String str, boolean z7) throws ExceptionUart {
        Object obj;
        DataLight dataLight;
        Intrinsics.checkNotNullParameter(context, "context");
        Timber.Forest forest = Timber.forest;
        forest.d("setLight JSON received", new Object[0]);
        try {
            obj = this.V.fromJson(str, DataLight.class);
        } catch (JsonIOException e7) {
            e = e7;
            obj = null;
        }
        try {
            Integer num = ((DataLight) obj).value;
            if (num != null) {
                RangesKt___RangesKt.coerceIn(num.intValue(), 10, 100);
                Integer valueOf = Integer.valueOf(((num.intValue() + 5) / 10) * 10);
                forest.d("json dataLight value: " + valueOf, new Object[0]);
                ((DataLight) obj).value = valueOf;
            }
        } catch (JsonIOException e10) {
            e = e10;
            AppFeatures.Error(AppFeatures.instance, e, null, 2, null);
            dataLight = (DataLight) obj;
            if ((dataLight != null ? dataLight.id : null) != null) {
            }
        }
        dataLight = (DataLight) obj;
        if ((dataLight != null ? dataLight.id : null) != null) {
            throw new ExceptionUart("Invalid json received");
        }
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            if (t0(context, dataMaster, (DataLight) obj, z7)) {
                updateLightJson(context, dataMaster);
            }
            Unit unit = Unit.INSTANCE;
        }
        return WebServer.ACK;
    }

    public final void s0(@NotNull Context context, @Nullable DataLight dataLight) {
        String str;
        LightState lightState2;
        Integer num;
        Intrinsics.checkNotNullParameter(context, "context");
        Timber.forest.d("setLight received", new Object[0]);
        if (dataLight == null || (str = dataLight.id) == null || (lightState2 = dataLight.state) == null || (num = dataLight.value) == null) {
            return;
        }
        i(context, str, lightState2, num);
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            if (updateLight(dataMaster, dataLight.id, dataLight.state, dataLight.value, true)) {
                updateLightJson(context, dataMaster);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0156 A[Catch: all -> 0x02d7, TryCatch #1 {, blocks: (B:6:0x0013, B:7:0x0034, B:9:0x003a, B:12:0x0042, B:17:0x0046, B:18:0x004a, B:20:0x0050, B:24:0x0068, B:27:0x006e, B:29:0x0074, B:32:0x007a, B:34:0x0080, B:36:0x008b, B:38:0x0098, B:39:0x00a6, B:41:0x00ba, B:43:0x00c6, B:44:0x00d4, B:46:0x00e6, B:47:0x0177, B:52:0x0184, B:55:0x018a, B:58:0x0190, B:61:0x0196, B:64:0x019c, B:67:0x01a0, B:70:0x01a9, B:83:0x017e, B:87:0x00d0, B:88:0x00fd, B:90:0x010e, B:91:0x00a2, B:92:0x0124, B:93:0x0062, B:95:0x013e, B:99:0x014b, B:102:0x0150, B:104:0x0156, B:107:0x015b, B:109:0x0161, B:110:0x0145, B:113:0x01c1, B:114:0x01d0, B:116:0x01d6, B:119:0x01e9, B:124:0x01fc), top: B:5:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0074 A[Catch: all -> 0x02d7, TryCatch #1 {, blocks: (B:6:0x0013, B:7:0x0034, B:9:0x003a, B:12:0x0042, B:17:0x0046, B:18:0x004a, B:20:0x0050, B:24:0x0068, B:27:0x006e, B:29:0x0074, B:32:0x007a, B:34:0x0080, B:36:0x008b, B:38:0x0098, B:39:0x00a6, B:41:0x00ba, B:43:0x00c6, B:44:0x00d4, B:46:0x00e6, B:47:0x0177, B:52:0x0184, B:55:0x018a, B:58:0x0190, B:61:0x0196, B:64:0x019c, B:67:0x01a0, B:70:0x01a9, B:83:0x017e, B:87:0x00d0, B:88:0x00fd, B:90:0x010e, B:91:0x00a2, B:92:0x0124, B:93:0x0062, B:95:0x013e, B:99:0x014b, B:102:0x0150, B:104:0x0156, B:107:0x015b, B:109:0x0161, B:110:0x0145, B:113:0x01c1, B:114:0x01d0, B:116:0x01d6, B:119:0x01e9, B:124:0x01fc), top: B:5:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x013e A[Catch: all -> 0x02d7, TryCatch #1 {, blocks: (B:6:0x0013, B:7:0x0034, B:9:0x003a, B:12:0x0042, B:17:0x0046, B:18:0x004a, B:20:0x0050, B:24:0x0068, B:27:0x006e, B:29:0x0074, B:32:0x007a, B:34:0x0080, B:36:0x008b, B:38:0x0098, B:39:0x00a6, B:41:0x00ba, B:43:0x00c6, B:44:0x00d4, B:46:0x00e6, B:47:0x0177, B:52:0x0184, B:55:0x018a, B:58:0x0190, B:61:0x0196, B:64:0x019c, B:67:0x01a0, B:70:0x01a9, B:83:0x017e, B:87:0x00d0, B:88:0x00fd, B:90:0x010e, B:91:0x00a2, B:92:0x0124, B:93:0x0062, B:95:0x013e, B:99:0x014b, B:102:0x0150, B:104:0x0156, B:107:0x015b, B:109:0x0161, B:110:0x0145, B:113:0x01c1, B:114:0x01d0, B:116:0x01d6, B:119:0x01e9, B:124:0x01fc), top: B:5:0x0013 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void t(@Nullable ActivityMain activityMain) {
        String str;
        Boolean bool;
        if (activityMain != null) {
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            synchronized (MyMasterData.class) {
                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(activityMain);
                TreeMap<String, DataMyThing> treeMap = dataMaster.myThings.things;
                Intrinsics.checkNotNull(treeMap);
                Collection<DataMyThing> values = treeMap.values();
                Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                ArrayList<DataMyThing> arrayList = new ArrayList();
                for (DataMyThing dataMyThing : values) {
                    if (dataMyThing != null) {
                        arrayList.add(dataMyThing);
                    }
                }
                for (DataMyThing dataMyThing2 : arrayList) {
                    Integer num = dataMyThing2.channelDipState;
                    if (num == null) {
                        Integer num2 = dataMyThing2.channelDipState;
                        if (num2 != null) {
                            if (num2 != null && num2.intValue() == 3) {
                                Integer num3 = dataMyThing2.value;
                                if (num3 != null && num3.intValue() == 0) {
                                    TypeIntrinsics.k(this.thingsMap).remove(dataMyThing2.id);
                                    TypeIntrinsics.k(this.X).remove(dataMyThing2.id);
                                }
                            } else {
                                Integer num4 = dataMyThing2.channelDipState;
                                if (num4 != null && num4.intValue() == 10) {
                                }
                            }
                        }
                    } else if (num != null && num.intValue() == 3) {
                        Integer num5 = dataMyThing2.value;
                        if (num5 != null && num5.intValue() == 100) {
                            if (this.thingsMap.containsKey(dataMyThing2.id)) {
                                Integer num6 = this.thingsMap.get(dataMyThing2.id);
                                Integer valueOf = num6 != null ? Integer.valueOf(num6.intValue() + 1) : 1;
                                ConcurrentHashMap<String, Integer> concurrentHashMap = this.thingsMap;
                                String str2 = dataMyThing2.id;
                                Intrinsics.checkNotNull(str2);
                                concurrentHashMap.put(str2, valueOf);
                                if (this.X.containsKey(dataMyThing2.id)) {
                                    Integer num7 = this.X.get(dataMyThing2.id);
                                    Integer valueOf2 = num7 != null ? Integer.valueOf(num7.intValue() + 1) : 1;
                                    ConcurrentHashMap<String, Integer> concurrentHashMap2 = this.X;
                                    String str3 = dataMyThing2.id;
                                    Intrinsics.checkNotNull(str3);
                                    concurrentHashMap2.put(str3, valueOf2);
                                    if (valueOf2.intValue() >= 30) {
                                        hashMap2.put(dataMyThing2.id, dataMyThing2.name);
                                        ConcurrentHashMap<String, Integer> concurrentHashMap3 = this.X;
                                        String str4 = dataMyThing2.id;
                                        Intrinsics.checkNotNull(str4);
                                        concurrentHashMap3.put(str4, 0);
                                    }
                                } else {
                                    int intValue = valueOf.intValue();
                                    Integer num8 = dataMaster.system.garageDoorReminderWaitTime;
                                    Intrinsics.checkNotNull(num8);
                                    if (intValue >= num8.intValue()) {
                                        hashMap.put(dataMyThing2.id, dataMyThing2.name);
                                        ConcurrentHashMap<String, Integer> concurrentHashMap4 = this.X;
                                        String str5 = dataMyThing2.id;
                                        Intrinsics.checkNotNull(str5);
                                        concurrentHashMap4.put(str5, 0);
                                    }
                                }
                            } else {
                                ConcurrentHashMap<String, Integer> concurrentHashMap5 = this.thingsMap;
                                String str6 = dataMyThing2.id;
                                Intrinsics.checkNotNull(str6);
                                concurrentHashMap5.put(str6, 0);
                                TypeIntrinsics.k(this.X).remove(dataMyThing2.id);
                            }
                        }
                    } else {
                        Integer num9 = dataMyThing2.channelDipState;
                        if (num9 != null && num9.intValue() == 10) {
                        }
                    }
                    Integer num10 = dataMyThing2.channelDipState;
                    if (num10 != null) {
                        if (num10 == null || num10.intValue() != 3) {
                            Integer num11 = dataMyThing2.channelDipState;
                            if (num11 != null && num11.intValue() == 10) {
                            }
                        }
                        Integer num12 = dataMyThing2.value;
                        if (num12 != null && num12.intValue() == 100 && (bool = dataMyThing2.RFLinkTimeout) != null) {
                            Intrinsics.checkNotNull(bool);
                            if (bool.booleanValue()) {
                                TypeIntrinsics.k(this.thingsMap).remove(dataMyThing2.id);
                                TypeIntrinsics.k(this.X).remove(dataMyThing2.id);
                            }
                        }
                    }
                }
                Iterator it = new ArrayList(this.thingsMap.keySet()).iterator();
                while (it.hasNext()) {
                    String str7 = (String) it.next();
                    TreeMap<String, DataMyThing> treeMap2 = dataMaster.myThings.things;
                    Intrinsics.checkNotNull(treeMap2);
                    if (!treeMap2.containsKey(str7)) {
                        TypeIntrinsics.k(this.thingsMap).remove(str7);
                        TypeIntrinsics.k(this.X).remove(str7);
                    }
                }
                Unit unit = Unit.INSTANCE;
            }
            synchronized (MyMasterData.class) {
                String str8 = MyMasterData.Companion.getDataMaster(activityMain).system.name;
                str = str8 != null ? str8 + " Garage Door Alert!" : "Garage Door Alert!";
            }
            String format = new SimpleDateFormat("h:mm a", Locale.US).format(new Date());
            NotificationService notificationService = (NotificationService) KoinJavaComponent.get$default(NotificationService.class, null, null, 6, null);
            for (String str9 : hashMap.keySet()) {
                String str10 = ((String) hashMap.get(str9)) + " was open at " + format;
                NotificationType notificationType = NotificationType.GARAGE_DOOR;
                Intrinsics.checkNotNull(str9);
                NotificationData notificationData = new NotificationData(notificationType, str9, str, str10, null, null, null, Boolean.TRUE, null);
                notificationService.sendNotification(notificationData);
                notificationService.parse(notificationData);
            }
            for (String str11 : hashMap2.keySet()) {
                String str12 = ((String) hashMap2.get(str11)) + " was still open at " + format;
                NotificationType notificationType2 = NotificationType.GARAGE_DOOR;
                Intrinsics.checkNotNull(str11);
                NotificationData notificationData2 = new NotificationData(notificationType2, str11, str, str12, null, null, null, Boolean.TRUE, null);
                notificationService.sendNotification(notificationData2);
                notificationService.parse(notificationData2);
            }
        }
    }

    /* renamed from: u0 */
    public final int setLightAlarm(@NotNull Context context, @Nullable String str) throws ExceptionUart {
        Object obj;
        boolean m02;
        Intrinsics.checkNotNullParameter(context, "context");
        Timber.forest.d("setLightAlarm JSON received", new Object[0]);
        try {
            obj = this.V.fromJson(str, DataAlarm.class);
        } catch (JsonParseException e7) {
            AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
            obj = null;
        }
        if (obj != null) {
            DataAlarm dataAlarm = (DataAlarm) obj;
            if (dataAlarm.id != null) {
                Timber.Forest forest = Timber.forest;
                forest.d("setLightAlarm received", new Object[0]);
                String str2 = dataAlarm.id;
                if (!isValidAlarmId(str2)) {
                    throw new ExceptionUart("Invalid alarm id");
                }
                synchronized (MyMasterData.class) {
                    DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
                    String str3 = ((DataAlarm) obj).name;
                    if (str3 == null || str3.length() > 12) {
                        if (str3 == null || str3.length() <= 12) {
                            throw new ExceptionUart("setLightAlarm - problem with name");
                        }
                        throw new ExceptionUart("setLightAlarm - name too long");
                    }
                    if (str3.length() == 0) {
                        forest.d("setLightAlarm - ID : " + str2 + " deleting.", new Object[0]);
                        m02 = w(context, dataMaster, str2);
                    } else {
                        forest.d("setLightAlarm - ID : " + str2 + " Name : " + str3, new Object[0]);
                        DataAlarm dataAlarm2 = new DataAlarm(str2, str3, "");
                        dataAlarm2.update((Context) null, (DataAlarm) obj, (DataManager) null);
                        m02 = m0(context, dataMaster, dataAlarm2, ((DataAlarm) obj).runNow);
                    }
                    if (m02) {
                        updateLightJson(context, dataMaster);
                        return WebServer.ACK;
                    }
                    Unit unit = Unit.INSTANCE;
                    return WebServer.NACK;
                }
            }
        }
        throw new ExceptionUart("Invalid json received");
    }

    /* renamed from: v0 */
    public final int setLightDimOffset(@NotNull Context context, @Nullable String str, boolean z7) throws ExceptionUart {
        Object obj;
        Intrinsics.checkNotNullParameter(context, "context");
        Timber.forest.d("setLightDimOffset JSON received", new Object[0]);
        try {
            obj = this.V.fromJson(str, DataLight.class);
        } catch (JsonIOException e7) {
            AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
            obj = null;
        }
        if (obj == null || ((DataLight) obj).id == null) {
            throw new ExceptionUart("Invalid json received");
        }
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            if (w0(context, dataMaster, (DataLight) obj)) {
                updateLightJson(context, dataMaster);
            }
            Unit unit = Unit.INSTANCE;
        }
        return WebServer.ACK;
    }

    /* renamed from: x0 */
    public final int setLightGroup(@NotNull Context context, @Nullable String str) throws ExceptionUart {
        Object obj;
        Intrinsics.checkNotNullParameter(context, "context");
        Timber.forest.d("setLight JSON received", new Object[0]);
        try {
            obj = this.V.fromJson(str, DataGroup.class);
        } catch (JsonParseException e7) {
            AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
            obj = null;
        }
        if (obj == null) {
            throw new ExceptionUart("setLightGroup - invalid message.");
        }
        DataGroup dataGroup = (DataGroup) obj;
        if (dataGroup.id == null) {
            throw new ExceptionUart("setLightGroup - invalid id.");
        }
        boolean z7 = true;
        boolean z10 = dataGroup.state != null;
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            if (((DataGroup) obj).name != null) {
                String str2 = ((DataGroup) obj).name;
                Intrinsics.checkNotNull(str2);
                if (str2.length() > 12) {
                    throw new ExceptionUart("setLightGroup - name too long");
                }
                String str3 = ((DataGroup) obj).name;
                if (str3 == null || str3.length() == 0) {
                    Timber.forest.d("setLightGroupName - deleting group " + ((DataGroup) obj).id, new Object[0]);
                    DataGroup dataGroup2 = dataMaster.myLights.getDataGroup(((DataGroup) obj).id);
                    if (dataGroup2 == null) {
                        throw new ExceptionUart("setLightGroup - group id doesn't exist");
                    }
                    x(dataMaster, dataGroup2);
                    updateLightJson(context, dataMaster);
                } else {
                    if (makeNewGroup(dataMaster, ((DataGroup) obj).id, ((DataGroup) obj).name)) {
                        z10 = true;
                    } else {
                        Timber.forest.d("setLightGroup - too many groups or groups already exists", new Object[0]);
                    }
                    if (O0(dataMaster, ((DataGroup) obj).id, ((DataGroup) obj).name) != null) {
                    }
                }
                z7 = z10;
            } else {
                z7 = z10;
            }
            if (z7) {
                if (((DataGroup) obj).state != null) {
                    sendCanMessageToCB(context, dataMaster, ((DataGroup) obj).id, ((DataGroup) obj).state);
                }
                updateLightJson(context, dataMaster);
            }
            Unit unit = Unit.INSTANCE;
        }
        return WebServer.ACK;
    }

    /* renamed from: y0 */
    public final int setLightGroupName(@Nullable Context context, @NotNull Map<String, String> params) throws ExceptionUart {
        Intrinsics.checkNotNullParameter(params, "params");
        Timber.Forest forest = Timber.forest;
        forest.d("setLightGroupName received", new Object[0]);
        String str = params.get("id");
        if (!isValidGroupId(str)) {
            throw new ExceptionUart("setLightGroupName? invalid id");
        }
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            String str2 = params.get(AppMeasurementSdk.ConditionalUserProperty.NAME);
            if (str2 == null || str2.length() > 12) {
                if (str2 == null || str2.length() <= 12) {
                    forest.d("setLightGroupName - problem with name", new Object[0]);
                } else {
                    forest.d("setLightGroupName - name too long", new Object[0]);
                }
                Unit unit = Unit.INSTANCE;
                return WebServer.NACK;
            }
            forest.d("setLightGroupName - ID : " + str + " Name : " + str2, new Object[0]);
            if (str2.length() == 0) {
                forest.d("setLightGroupName - deleting group " + str, new Object[0]);
                DataGroup dataGroup = dataMaster.myLights.getDataGroup(str);
                if (dataGroup != null) {
                    x(dataMaster, dataGroup);
                }
            } else {
                O0(dataMaster, str, str2);
            }
            updateLightJson(context, dataMaster);
            return WebServer.ACK;
        }
    }

    /* renamed from: z0 */
    public final int setLightNewGroupName(@Nullable Context context, @NotNull Map<String, String> params) throws ExceptionUart {
        Intrinsics.checkNotNullParameter(params, "params");
        Timber.Forest forest = Timber.forest;
        forest.d("setLightNewGroupName received", new Object[0]);
        String str = params.get("id");
        if (!isValidGroupId(str)) {
            throw new ExceptionUart("setLightNewGroupName? invalid id");
        }
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            String str2 = params.get(AppMeasurementSdk.ConditionalUserProperty.NAME);
            if (str2 != null) {
                if (!(str2.length() == 0) && str2.length() <= 12) {
                    forest.d("setLightNewGroupName - ID : " + str + " Name : " + str2, new Object[0]);
                    if (makeNewGroup(dataMaster, str, str2)) {
                        updateLightJson(context, dataMaster);
                        return WebServer.ACK;
                    }
                    Unit unit = Unit.INSTANCE;
                    return WebServer.NACK;
                }
            }
            if (str2 == null || str2.length() <= 12) {
                throw new ExceptionUart("setLightNewGroupName - problem with name");
            }
            throw new ExceptionUart("setLightNewGroupName - name too long");
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private HandlerLights(HandlerCan handlerCan) {
        this.U = handlerCan;
        this.V = new Gson();
        this.thingsMap = new ConcurrentHashMap<>();
        this.X = new ConcurrentHashMap<>();
        this.Y = new ConcurrentHashMap<>();
        this.Z = new ConcurrentHashMap<>();
        this.f7179a0 = (SonosRepository) KoinJavaComponent.get$default(SonosRepository.class, null, null, 6, null);
        this.f7180b0 = (FirebaseAnalyticsLog) KoinJavaComponent.get$default(FirebaseAnalyticsLog.class, null, null, 6, null);
    }

    /* renamed from: com.air.advantage.uart.q$b */
    public final class b {

        @Exclude
        @Nullable
        private transient String a;

        /* renamed from: b */
        @Exclude
        @Nullable
        private transient Long f7181b;

        /* renamed from: c */
        @Nullable
        private DataMyThing f7182c;

        /* renamed from: d */
        @Exclude
        @Nullable
        private transient String f7183d;

        /* renamed from: e */
        @Exclude
        @Nullable
        private transient Integer f7184e;

        /* renamed from: f */
        @Exclude
        private transient int f7185f;

        /* renamed from: g */
        @Exclude
        @Nullable
        private transient DataMyThing f7186g;

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public b(@Nullable String str, @Nullable Long l8, @Nullable DataMyThing dataMyThing, @Nullable String str2, @Nullable Integer num, int i10) {
            this.a = str;
            this.f7181b = l8;
            this.f7182c = dataMyThing;
            this.f7183d = str2;
            this.f7184e = num;
            this.f7185f = i10;
            this.f7186g = dataMyThing;
        }

        @Nullable
        public final Long a() {
            return this.f7181b;
        }

        @Nullable
        public final String b() {
            return this.f7183d;
        }

        @Nullable
        public final DataMyThing c() {
            return this.f7182c;
        }

        @Nullable
        public final DataMyThing d() {
            return this.f7186g;
        }

        @Nullable
        public final Integer e() {
            return this.f7184e;
        }

        public final int f() {
            return this.f7185f;
        }

        @Nullable
        public final String g() {
            return this.a;
        }

        public final void h(@Nullable Long l8) {
            this.f7181b = l8;
        }

        public final void i(@Nullable String str) {
            this.f7183d = str;
        }

        public final void j(@Nullable DataMyThing dataMyThing) {
            this.f7182c = dataMyThing;
        }

        public final void k(@Nullable DataMyThing dataMyThing) {
            this.f7186g = dataMyThing;
        }

        public final void l(@Nullable Integer num) {
            this.f7184e = num;
        }

        public final void m(int i10) {
            this.f7185f = i10;
        }

        public final void n(@Nullable String str) {
            this.a = str;
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0017: CONSTRUCTOR 
          (r10v0 com.air.advantage.uart.q)
          (r11v0 java.lang.String)
          (r12v0 java.lang.Long)
          (r13v0 com.air.advantage.data.u0)
          (r14v0 java.lang.String)
          (wrap:java.lang.Integer:?: TERNARY null = ((wrap:int:0x0000: ARITH (r17v0 int) & (16 int) A[WRAPPED]) != (0 int)) ? (null java.lang.Integer) : (r15v0 java.lang.Integer))
          (wrap:int:?: TERNARY null = ((wrap:int:0x0008: ARITH (r17v0 int) & (32 int) A[WRAPPED]) != (0 int)) ? (0 int) : (r16v0 int))
         A[MD:(com.air.advantage.uart.q, java.lang.String, java.lang.Long, com.air.advantage.data.u0, java.lang.String, java.lang.Integer, int):void (m)] (LINE:9) call: com.air.advantage.uart.q.b.<init>(com.air.advantage.uart.q, java.lang.String, java.lang.Long, com.air.advantage.data.u0, java.lang.String, java.lang.Integer, int):void type: THIS */
        public /* synthetic */ b(HandlerLights handlerLights, String str, Long l8, DataMyThing dataMyThing, String str2, Integer num, int i10, int i11, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, l8, dataMyThing, str2, (i11 & 16) != 0 ? null : num, (i11 & 32) != 0 ? 0 : i10);
        }
    }
}