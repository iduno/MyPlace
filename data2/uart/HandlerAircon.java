package com.air.advantage.uart;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import androidx.annotation.VisibleForTesting;
import androidx.exifinterface.media.ExifInterface;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.air.advantage.ActivityMain;
import com.air.advantage.AppFeatures;
import com.air.advantage.Constants;
import com.air.advantage.FirebaseComms;
import com.air.advantage.MyApp;
import com.air.advantage.SharedPreferencesStore;
import com.air.advantage.TabletInfo;
import com.air.advantage.ThreadGetVersions;
import com.air.advantage.aircon.AirconFunctions;
import com.air.advantage.data.Aircon;
import com.air.advantage.data.DataAirconInfo;
import com.air.advantage.data.DataAirconSystem;
import com.air.advantage.data.DataAlarm;
import com.air.advantage.data.DataGroupSource;
import com.air.advantage.data.DataLight;
import com.air.advantage.data.DataLightsSystem;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataModuleInfoSource;
import com.air.advantage.data.DataMyGarageController;
import com.air.advantage.data.DataMyScene;
import com.air.advantage.data.DataScene;
import com.air.advantage.data.DataSystem;
import com.air.advantage.data.DataZone;
import com.air.advantage.data.ErrorCodes;
import com.air.advantage.data.RFCalibration;
import com.air.advantage.data.RFParing;
import com.air.advantage.data.SnapShot;
import com.air.advantage.libraryairconlightjson.AirconFunctionsConstants;
import com.air.advantage.libraryairconlightjson.AirconMode;
import com.air.advantage.libraryairconlightjson.CommonFuncs;
import com.air.advantage.libraryairconlightjson.FanStatus;
import com.air.advantage.libraryairconlightjson.SystemState;
import com.air.advantage.libraryairconlightjson.UartConstants;
import com.air.advantage.libraryairconlightjson.ZoneState;
import com.air.advantage.membership.model.MembershipStatus;
import com.air.advantage.notification.NotificationVersion;
import com.bosma.blemodule.common.CommandKey;
import com.bosma.blemodule.dfu.OTAFlashRowModel_v1;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.CharsKt__CharJVMKt;
import kotlin.text.StringsKt__IndentKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

@PurelyImplements({"SMAP\nHandlerAircon.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HandlerAircon.kt\ncom/air/advantage/uart/HandlerAircon\n+ 2 Strings.kt\nkotlin/text/StringsKt__StringsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,4979:1\n107#2:4980\n79#2,22:4981\n107#2:5003\n79#2,22:5004\n107#2:5026\n79#2,22:5027\n1603#3,9:5049\n1855#3:5058\n1856#3:5060\n1612#3:5061\n1603#3,9:5062\n1855#3:5071\n1856#3:5073\n1612#3:5074\n1603#3,9:5075\n1855#3:5084\n1856#3:5086\n1612#3:5087\n1603#3,9:5088\n1855#3:5097\n1856#3:5099\n1612#3:5100\n1603#3,9:5101\n1855#3:5110\n1856#3:5112\n1612#3:5113\n1603#3,9:5114\n1855#3:5123\n1856#3:5125\n1612#3:5126\n1603#3,9:5127\n1855#3:5136\n1856#3:5138\n1612#3:5139\n1603#3,9:5140\n1855#3:5149\n1856#3:5151\n1612#3:5152\n1603#3,9:5153\n1855#3:5162\n1856#3:5164\n1612#3:5165\n1603#3,9:5166\n1855#3:5175\n1856#3:5177\n1612#3:5178\n1603#3,9:5179\n1855#3:5188\n1856#3:5190\n1612#3:5191\n1603#3,9:5192\n1855#3:5201\n1856#3:5203\n1612#3:5204\n1#4:5059\n1#4:5072\n1#4:5085\n1#4:5098\n1#4:5111\n1#4:5124\n1#4:5137\n1#4:5150\n1#4:5163\n1#4:5176\n1#4:5189\n1#4:5202\n*S KotlinDebug\n*F\n+ 1 HandlerAircon.kt\ncom/air/advantage/uart/HandlerAircon\n*L\n696#1:4980\n696#1:4981,22\n702#1:5003\n702#1:5004,22\n705#1:5026\n705#1:5027,22\n1745#1:5049,9\n1745#1:5058\n1745#1:5060\n1745#1:5061\n1749#1:5062,9\n1749#1:5071\n1749#1:5073\n1749#1:5074\n1953#1:5075,9\n1953#1:5084\n1953#1:5086\n1953#1:5087\n2096#1:5088,9\n2096#1:5097\n2096#1:5099\n2096#1:5100\n2151#1:5101,9\n2151#1:5110\n2151#1:5112\n2151#1:5113\n2209#1:5114,9\n2209#1:5123\n2209#1:5125\n2209#1:5126\n2520#1:5127,9\n2520#1:5136\n2520#1:5138\n2520#1:5139\n2611#1:5140,9\n2611#1:5149\n2611#1:5151\n2611#1:5152\n2944#1:5153,9\n2944#1:5162\n2944#1:5164\n2944#1:5165\n3261#1:5166,9\n3261#1:5175\n3261#1:5177\n3261#1:5178\n3733#1:5179,9\n3733#1:5188\n3733#1:5190\n3733#1:5191\n3861#1:5192,9\n3861#1:5201\n3861#1:5203\n3861#1:5204\n1745#1:5059\n1749#1:5072\n1953#1:5085\n2096#1:5098\n2151#1:5111\n2209#1:5124\n2520#1:5137\n2611#1:5150\n2944#1:5163\n3261#1:5176\n3733#1:5189\n3861#1:5202\n*E\n"})
/* renamed from: com.air.advantage.uart.h */
/* loaded from: classes.dex */
public final class HandlerAircon extends FRLParser {

    /* renamed from: d0 */
    public static final int f7102d0 = 1320;

    /* renamed from: e0 */
    public static final int f7103e0 = 360;

    /* renamed from: f0 */
    public static final float f7104f0 = 20.0f;

    /* renamed from: g0 */
    public static final float f7105g0 = 24.0f;

    /* renamed from: j0 */
    private static final float f7107j0 = 24.5f;

    /* renamed from: k0 */
    private static final int f7108k0 = 10;

    /* renamed from: l0 */
    @Nullable
    private static HandlerAircon INSTANCE;

    /* renamed from: m0 */
    private static boolean f7109m0;

    /* renamed from: n0 */
    private static boolean f7110n0;

    /* renamed from: o0 */
    private static boolean f7111o0;

    /* renamed from: p0 */
    private static boolean f7112p0;

    /* renamed from: q0 */
    private static boolean f7113q0;

    /* renamed from: r0 */
    private static boolean f7114r0;

    /* renamed from: U */
    @NotNull
    private final Context context;

    /* renamed from: V */
    @NotNull
    @JvmField
    public final MyLights myLights;

    /* renamed from: W */
    @NotNull
    @JvmField
    public final MyLightsV2 myLightsV2;

    /* renamed from: X */
    @NotNull
    private final TreeMap<String, DataAirconSystem> fanSpeedMap;

    /* renamed from: Y */
    @NotNull
    private final Gson gson;

    /* renamed from: Z */
    @NotNull
    private final ArrayList<String> zone;

    /* renamed from: a0 */
    @Nullable
    private Calendar sunsetCalendar;

    /* renamed from: b0 */
    @NotNull
    public static final Companion Companion = new Companion(null);

    /* renamed from: c0 */
    @NotNull
    @JvmField
    public static final AtomicBoolean lock = new AtomicBoolean(false);

    /* renamed from: h0 */
    @NotNull
    private static final AtomicReference<String> UID = new AtomicReference<>("");

    /* renamed from: i0 */
    private static final String f7106i0 = HandlerAircon.class.getSimpleName();

    /* renamed from: com.air.advantage.uart.h$a */
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.h.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void f() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: i */
        public final int getRoundUpPercentageValue(int i10) {
            if (i10 >= 100) {
                return 100;
            }
            if (i10 <= 0) {
                return 0;
            }
            int i11 = (i10 / 5) * 5;
            return i10 % 5 >= 1 ? i11 + 5 : i11;
        }

        @JvmStatic
        /* renamed from: a */
        public final int getRoundUpAfter15PercentageValue(@Nullable Integer num) {
            Intrinsics.checkNotNull(num);
            if (num.intValue() >= 100) {
                return 100;
            }
            if (num.intValue() <= 0) {
                return 0;
            }
            int intValue = (num.intValue() / 5) * 5;
            if (num.intValue() % 5 >= 3) {
                intValue += 5;
            }
            return intValue;
        }

        @JvmStatic
        /* renamed from: c */
        public final void initialize() {
            HandlerAircon.INSTANCE = null;
            HandlerAircon.lock.set(false);
        }

        /* renamed from: d */
        public final boolean climateControlModeIsRunning(@Nullable DataAirconSystem dataAirconSystem) {
            Boolean bool;
            if (dataAirconSystem == null || (bool = dataAirconSystem.info.climateControlModeEnabled) == null) {
                return false;
            }
            Intrinsics.checkNotNull(bool);
            if (!bool.booleanValue()) {
                return false;
            }
            Boolean bool2 = dataAirconSystem.info.quietNightModeIsRunning;
            if (bool2 != null) {
                Intrinsics.checkNotNull(bool2);
                if (bool2.booleanValue()) {
                    return false;
                }
            }
            AirconMode airconMode = dataAirconSystem.info.mode;
            if (airconMode != null) {
                return airconMode == AirconMode.cool || airconMode == AirconMode.heat;
            }
            return false;
        }

        @NotNull
        /* renamed from: e */
        public final HandlerAircon getInstance() {
            synchronized (HandlerAircon.class) {
                if (HandlerAircon.INSTANCE == null) {
                    Companion companion = HandlerAircon.Companion;
                    HandlerAircon.INSTANCE = (HandlerAircon) KoinJavaComponent.get$default(HandlerAircon.class, null, null, 6, null);
                }
                Unit unit = Unit.INSTANCE;
            }
            HandlerAircon handlerAircon = HandlerAircon.INSTANCE;
            Intrinsics.checkNotNull(handlerAircon);
            return handlerAircon;
        }

        @NotNull
        /* renamed from: g */
        public final AtomicReference<String> getUID() {
            return HandlerAircon.UID;
        }

        @NotNull
        /* renamed from: h */
        public final HandlerAircon getInstance(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            synchronized (HandlerAircon.class) {
                if (HandlerAircon.INSTANCE == null) {
                    Companion companion = HandlerAircon.Companion;
                    HandlerAircon.INSTANCE = new HandlerAircon(context, null);
                }
                Unit unit = Unit.INSTANCE;
            }
            HandlerAircon handlerAircon = HandlerAircon.INSTANCE;
            Intrinsics.checkNotNull(handlerAircon);
            return handlerAircon;
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x003b  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x00d1  */
        /* renamed from: j */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final int getZoneOpenAmount(@Nullable DataAirconSystem dataAirconSystem, @NotNull DataZone zone) {
            float floatValue;
            float floatValue2;
            AirconMode airconMode;
            Integer num;
            Intrinsics.checkNotNullParameter(zone, "zone");
            Intrinsics.checkNotNull(dataAirconSystem);
            DataAirconInfo dataAirconInfo = dataAirconSystem.info;
            Integer num2 = dataAirconInfo.cbFWRevMajor;
            int i10 = 100;
            if (num2 != null && dataAirconInfo.cbFWRevMinor != null) {
                Intrinsics.checkNotNull(num2);
                if (num2.intValue() <= 9) {
                    Integer num3 = dataAirconSystem.info.cbFWRevMajor;
                    if (num3 != null && num3.intValue() == 9) {
                        Integer num4 = dataAirconSystem.info.cbFWRevMinor;
                        Intrinsics.checkNotNull(num4);
                        if (num4.intValue() > 40) {
                        }
                    }
                } else if (dataAirconSystem.info.mode == AirconMode.vent && zone.state == ZoneState.open && ((num = zone.type) == null || num.intValue() != 0)) {
                    return 100;
                }
            }
            if (zone.state != ZoneState.open) {
                return 0;
            }
            Integer num5 = zone.type;
            if (num5 != null && num5.intValue() == 0) {
                Integer num6 = zone.value;
                Intrinsics.checkNotNull(num6);
                return num6.intValue();
            }
            DataAirconInfo dataAirconInfo2 = dataAirconSystem.info;
            AirconMode airconMode2 = dataAirconInfo2.mode;
            AirconMode airconMode3 = AirconMode.heat;
            if (airconMode2 == airconMode3 || (airconMode2 == AirconMode.myauto && (airconMode = dataAirconInfo2.myAutoModeCurrentSetMode) != null && airconMode == airconMode3)) {
                Float f3 = zone.setTemp;
                Intrinsics.checkNotNull(f3);
                floatValue = f3.floatValue() - 0.0f;
                Float f7 = zone.measuredTemp;
                Intrinsics.checkNotNull(f7);
                floatValue2 = f7.floatValue();
            } else {
                Float f10 = zone.measuredTemp;
                Intrinsics.checkNotNull(f10);
                floatValue = f10.floatValue() - 0.0f;
                Float f11 = zone.setTemp;
                Intrinsics.checkNotNull(f11);
                floatValue2 = f11.floatValue();
            }
            float f12 = floatValue - floatValue2;
            Integer num7 = dataAirconSystem.info.myZone;
            if ((num7 == null || !Intrinsics.areEqual(num7, zone.number)) && f12 < 1.0f) {
                if (f12 <= 0.0f || f12 >= 1.0f) {
                    i10 = 0;
                } else {
                    int i11 = (int) (((f12 / 1.0f) * 70.0f) + 30.0f);
                    if (i11 <= 100) {
                        if (i11 >= 0) {
                            i10 = i11;
                        }
                    }
                }
            }
            return i10;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private Companion() {
        }
    }

    /* renamed from: com.air.advantage.uart.h$b */
    public static final class b extends TypeToken<TreeMap<String, Aircon>> {
        b() {
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 android.content.Context) A[MD:(android.content.Context):void (m)] (LINE:1) call: com.air.advantage.uart.h.<init>(android.content.Context):void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ HandlerAircon(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    /* renamed from: A0 */
    private final String UnitTypeMessage(DataAirconInfo dataAirconInfo, String str) {
        String str2;
        String extractUIDValue = extractUIDValue(str, 4);
        if (extractUIDValue == null) {
            str2 = "Rejected CB status message - invalid UID\n";
        } else {
            str2 = "";
        }
        Integer parseHexToInt = parseHexToInt(str, 11);
        if (parseHexToInt == null) {
            str2 = str2 + "Rejected CB status message - invalid unitType\n";
        }
        Integer parseHexToInt2 = parseHexToInt(str, 13);
        if (parseHexToInt2 == null || parseHexToInt2.intValue() < 0 || parseHexToInt2.intValue() > 2) {
            str2 = str2 + "Rejected CB status message - invalid activationCodeStatus\n";
        }
        Integer parseHexToInt3 = parseHexToInt(str, 15);
        if (parseHexToInt3 == null) {
            str2 = str2 + "Rejected CB status message - invalid Dict FW Major\n";
        }
        Integer parseHexToInt4 = parseHexToInt(str, 17);
        if (parseHexToInt4 == null) {
            str2 = str2 + "Rejected CB status message - invalid Dict FW Minor\n";
        }
        if (Intrinsics.areEqual(str2, "")) {
            String str3 = dataAirconInfo.uid;
            if (str3 == null || !Intrinsics.areEqual(str3, extractUIDValue)) {
                dataAirconInfo.uid = extractUIDValue;
            }
            Integer num = dataAirconInfo.unitType;
            if (num == null || !Intrinsics.areEqual(num, parseHexToInt)) {
                dataAirconInfo.unitType = parseHexToInt;
            }
            if (dataAirconInfo.activationCodeStatus == null || !Intrinsics.areEqual(dataAirconInfo.noOfConstantZones, parseHexToInt2)) {
                if (parseHexToInt2 != null && parseHexToInt2.intValue() == 0) {
                    dataAirconInfo.activationCodeStatus = DataAirconSystem.CodeStatus.noCode;
                } else if (parseHexToInt2 != null && parseHexToInt2.intValue() == 1) {
                    dataAirconInfo.activationCodeStatus = DataAirconSystem.CodeStatus.codeEnabled;
                } else {
                    dataAirconInfo.activationCodeStatus = DataAirconSystem.CodeStatus.expired;
                }
            }
            Integer num2 = dataAirconInfo.dbFWRevMajor;
            if (num2 == null || !Intrinsics.areEqual(num2, parseHexToInt3)) {
                dataAirconInfo.dbFWRevMajor = parseHexToInt3;
            }
            Integer num3 = dataAirconInfo.dbFWRevMinor;
            if (num3 == null || !Intrinsics.areEqual(num3, parseHexToInt4)) {
                dataAirconInfo.dbFWRevMinor = parseHexToInt4;
            }
        } else {
            Timber.forest.d(str2, new Object[0]);
        }
        return str2;
    }

    /* renamed from: B */
    private final String postCodeToState(String postcode, String country) {
        try {
            if (Intrinsics.areEqual(country, "Australia")) {
                int parseInt = Integer.parseInt(postcode);
                if ((parseInt >= 1000 && parseInt <= 2599) || ((parseInt >= 2620 && parseInt <= 2899) || (parseInt >= 2921 && parseInt <= 2999))) {
                    return postcode + ",NSW," + country;
                }
                if ((parseInt >= 3000 && parseInt <= 3999) || (parseInt >= 8000 && parseInt <= 8999)) {
                    return postcode + ",VIC," + country;
                }
                if ((parseInt >= 4000 && parseInt <= 4999) || (parseInt >= 9000 && parseInt <= 9999)) {
                    return postcode + ",QLD," + country;
                }
                if (parseInt >= 5000 && parseInt <= 5999) {
                    return postcode + ",SA," + country;
                }
                if (parseInt >= 6000 && parseInt <= 6999) {
                    return postcode + ",WA," + country;
                }
                if (parseInt >= 7000 && parseInt <= 7999) {
                    return postcode + ",TAS," + country;
                }
                if ((parseInt >= 200 && parseInt <= 299) || ((parseInt >= 2600 && parseInt <= 2619) || (parseInt >= 2900 && parseInt <= 2920))) {
                    return postcode + ",ACT," + country;
                }
                if (parseInt >= 800 && parseInt <= 999) {
                    return postcode + ",NT," + country;
                }
            }
        } catch (Exception unused) {
        }
        return postcode + "," + country;
    }

    /* JADX WARN: Removed duplicated region for block: B:341:0x0669  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x0678  */
    /* JADX WARN: Removed duplicated region for block: B:347:0x067e  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x0688  */
    /* JADX WARN: Removed duplicated region for block: B:354:0x06a2  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x0914  */
    /* JADX WARN: Removed duplicated region for block: B:456:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:460:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:468:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01c5  */
    /* renamed from: C */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final ArrayList<String> sendUpdateToCBUnit(DataMaster dataMaster, DataAirconSystem dataAirconSystem, String str, boolean z7) throws ExceptionUart {
        boolean z10;
        boolean z11;
        DataMaster dataMaster2;
        DataAirconSystem dataAirconSystem2;
        DataAirconSystem dataAirconSystem3;
        boolean z12;
        String str2;
        String str3;
        char c10;
        Integer num;
        DataZone dataZone;
        Integer num2;
        Integer num3;
        Integer num4;
        String str4;
        Integer num5;
        Float f3;
        Integer num6;
        ZoneState zoneState;
        DataAirconSystem.FreshAirStatus freshAirStatus;
        Integer num7;
        Float f7;
        FanStatus fanStatus;
        AirconMode airconMode;
        SystemState systemState;
        Boolean bool;
        SystemState systemState2;
        Boolean bool2;
        ZoneState zoneState2;
        Boolean bool3;
        AirconMode airconMode2;
        Integer num8;
        Integer num9;
        Boolean bool4;
        ArrayList<String> arrayList = new ArrayList<>();
        DataAirconSystem dataAirconSystem4 = getDataAirconSystem(dataMaster, str);
        if (dataAirconSystem4 == null || dataAirconSystem4.info.uid == null) {
            if (((TabletInfo) KoinJavaComponent.get$default(TabletInfo.class, null, null, 6, null)).isDemoDevice()) {
                return null;
            }
            Timber.forest.d("No aircon found in memory, can't generate CAN from JSON", new Object[0]);
            throw new ExceptionUart("No aircon found with name " + str + " use ac1-4, uid or check the number of aircons");
        }
        DataAirconSystem dataAirconSystem5 = new DataAirconSystem(dataAirconSystem4.info.uid);
        DataAirconSystem.update$default(dataAirconSystem5, null, dataAirconSystem, null, null, false, 16, null);
        DataAirconSystem dataAirconSystem6 = new DataAirconSystem(dataAirconSystem4.info.uid);
        if (z7) {
            dataAirconSystem5.sanitiseData();
            DataAirconSystem.FreshAirStatus freshAirStatus2 = dataAirconSystem4.info.freshAirStatus;
            if (freshAirStatus2 != null) {
                DataAirconSystem.FreshAirStatus freshAirStatus3 = DataAirconSystem.FreshAirStatus.none;
                if (freshAirStatus2 == freshAirStatus3) {
                    dataAirconSystem5.info.freshAirStatus = null;
                } else {
                    DataAirconInfo dataAirconInfo = dataAirconSystem5.info;
                    DataAirconSystem.FreshAirStatus freshAirStatus4 = dataAirconInfo.freshAirStatus;
                    if (freshAirStatus4 != null && freshAirStatus4 == freshAirStatus3) {
                        dataAirconInfo.freshAirStatus = null;
                    }
                }
            }
            String packageName = ActivityMain.Companion.getPackageName();
            if (packageName != null && StringsKt__StringsKt.startsWith$default(packageName, "zone10", false, 2, null)) {
                dataAirconSystem5.info.mode = null;
            }
            if (AirconFunctions.Companion.isDualZoneDevice(dataAirconSystem4)) {
                dataAirconSystem5.info.state = null;
            }
        }
        DataAirconInfo dataAirconInfo2 = dataAirconSystem5.info;
        DataAirconInfo dataAirconInfo3 = dataAirconSystem4.info;
        dataAirconInfo2.uid = dataAirconInfo3.uid;
        Boolean bool5 = dataAirconInfo2.aaAutoFanModeEnabled;
        if (bool5 != null && !Intrinsics.areEqual(dataAirconInfo3.aaAutoFanModeEnabled, bool5)) {
            dataAirconSystem6.info.aaAutoFanModeEnabled = dataAirconSystem5.info.aaAutoFanModeEnabled;
            arrayList.add("aaAutoFanModeEnabledChange");
        }
        Boolean bool6 = dataAirconSystem5.info.aaAutoFanModeEnabled;
        if (bool6 != null) {
            Intrinsics.checkNotNull(bool6);
            if (bool6.booleanValue()) {
                Boolean bool7 = dataAirconSystem5.info.aaAutoFanModeEnabled;
                if (bool7 != null) {
                    Intrinsics.checkNotNull(bool7);
                    if (!bool7.booleanValue()) {
                        Boolean bool8 = dataAirconSystem4.info.aaAutoFanModeEnabled;
                        if (bool8 != null) {
                            Intrinsics.checkNotNull(bool8);
                            if (bool8.booleanValue()) {
                                if (X(dataAirconSystem5, dataAirconSystem4)) {
                                    FanStatus fanStatus2 = dataAirconSystem5.info.fan;
                                    if (fanStatus2 == null || fanStatus2 == FanStatus.autoAA) {
                                        dataAirconSystem6.info.fan = FanStatus.autoAA;
                                    } else {
                                        dataAirconSystem6.info.fan = fanStatus2;
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (dataAirconSystem4.info.fan == FanStatus.autoAA) {
                if (dataAirconSystem4.isAutoFanAvailable()) {
                    DataAirconInfo dataAirconInfo4 = dataAirconSystem5.info;
                    FanStatus fanStatus3 = FanStatus.auto;
                    dataAirconInfo4.fan = fanStatus3;
                    dataAirconSystem6.info.fan = fanStatus3;
                } else {
                    DataAirconInfo dataAirconInfo5 = dataAirconSystem5.info;
                    FanStatus fanStatus4 = FanStatus.high;
                    dataAirconInfo5.fan = fanStatus4;
                    dataAirconSystem6.info.fan = fanStatus4;
                }
            }
        }
        Boolean bool9 = dataAirconSystem5.info.myAutoModeEnabled;
        if (bool9 != null && ((bool4 = dataAirconSystem4.info.myAutoModeEnabled) == null || !Intrinsics.areEqual(bool4, bool9))) {
            DataAirconInfo dataAirconInfo6 = dataAirconSystem6.info;
            Boolean bool10 = dataAirconSystem5.info.myAutoModeEnabled;
            dataAirconInfo6.myAutoModeEnabled = bool10;
            Intrinsics.checkNotNull(bool10);
            if (!bool10.booleanValue()) {
                Boolean bool11 = dataAirconSystem4.info.quietNightModeIsRunning;
                if (bool11 != null) {
                    Intrinsics.checkNotNull(bool11);
                    if (bool11.booleanValue()) {
                        Boolean bool12 = dataAirconSystem5.info.quietNightModeEnabled;
                        if (bool12 != null) {
                            Intrinsics.checkNotNull(bool12);
                            if (!bool12.booleanValue()) {
                            }
                        }
                        dataAirconSystem6.info.myAutoModeIsRunning = Boolean.FALSE;
                    } else {
                        dataAirconSystem6.info.mode = dataAirconSystem4.info.myAutoModeCurrentSetMode;
                        dataAirconSystem6.info.myAutoModeIsRunning = Boolean.FALSE;
                    }
                }
            }
            arrayList.add("myAutoModeEnabledChanged");
        }
        Integer num10 = dataAirconSystem5.info.myAutoCoolTargetTemp;
        if (num10 != null && ((num9 = dataAirconSystem4.info.myAutoCoolTargetTemp) == null || !Intrinsics.areEqual(num9, num10))) {
            dataAirconSystem6.info.myAutoCoolTargetTemp = dataAirconSystem5.info.myAutoCoolTargetTemp;
            arrayList.add("myAutoCoolTargetTempChanged");
        }
        Integer num11 = dataAirconSystem5.info.myAutoHeatTargetTemp;
        if (num11 != null && ((num8 = dataAirconSystem4.info.myAutoHeatTargetTemp) == null || !Intrinsics.areEqual(num8, num11))) {
            dataAirconSystem6.info.myAutoHeatTargetTemp = dataAirconSystem5.info.myAutoHeatTargetTemp;
            arrayList.add("myAutoHeatTargetTempChanged");
        }
        AirconMode airconMode3 = dataAirconSystem5.info.mode;
        if (airconMode3 != null) {
            AirconMode airconMode4 = AirconMode.myauto;
            if (airconMode3 == airconMode4) {
                Boolean bool13 = dataAirconSystem4.info.myAutoModeIsRunning;
                if (bool13 != null) {
                    Intrinsics.checkNotNull(bool13);
                    if (bool13.booleanValue()) {
                        dataAirconSystem6.info.myAutoModeCurrentSetMode = dataAirconSystem5.info.myAutoModeCurrentSetMode;
                    } else {
                        DataAirconInfo dataAirconInfo7 = dataAirconSystem4.info;
                        dataAirconInfo7.myAutoModeIsRunning = Boolean.TRUE;
                        dataAirconSystem6.info.myAutoModeCurrentSetMode = dataAirconInfo7.mode;
                    }
                    dataAirconSystem6.info.mode = airconMode4;
                }
            } else {
                DataAirconInfo dataAirconInfo8 = dataAirconSystem6.info;
                Boolean bool14 = Boolean.FALSE;
                dataAirconInfo8.myAutoModeIsRunning = bool14;
                DataAirconInfo dataAirconInfo9 = dataAirconSystem4.info;
                dataAirconInfo9.myAutoModeIsRunning = bool14;
                dataAirconInfo8.myAutoModeCurrentSetMode = airconMode3;
                dataAirconInfo8.mode = airconMode3;
                Boolean bool15 = dataAirconInfo9.climateControlModeIsRunning;
                if (bool15 != null) {
                    Intrinsics.checkNotNull(bool15);
                    if (bool15.booleanValue() && (airconMode2 = dataAirconSystem5.info.mode) != AirconMode.cool && airconMode2 != AirconMode.heat) {
                        dataAirconSystem4.info.climateControlModeIsRunning = bool14;
                    }
                }
            }
        }
        Boolean bool16 = dataAirconSystem5.info.quietNightModeEnabled;
        if (bool16 == null || ((bool3 = dataAirconSystem4.info.quietNightModeEnabled) != null && Intrinsics.areEqual(bool3, bool16))) {
            z10 = false;
        } else {
            dataAirconSystem6.info.quietNightModeEnabled = dataAirconSystem5.info.quietNightModeEnabled;
            arrayList.add("nightModeEnabledChange");
            SharedPreferencesStore sharedPreferencesStore = (SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null);
            Context context = this.context;
            String str5 = dataAirconSystem4.info.uid;
            Intrinsics.checkNotNull(str5);
            sharedPreferencesStore.e1(context, str5, false);
            z10 = true;
        }
        Boolean bool17 = dataAirconSystem4.info.quietNightModeIsRunning;
        if (bool17 != null) {
            Intrinsics.checkNotNull(bool17);
            if (bool17.booleanValue()) {
                DataAirconInfo dataAirconInfo10 = dataAirconSystem5.info;
                AirconMode airconMode5 = dataAirconInfo10.mode;
                if ((airconMode5 == null || dataAirconSystem4.info.mode == airconMode5) && (((systemState2 = dataAirconInfo10.state) == null || systemState2 != SystemState.off) && (((bool2 = dataAirconInfo10.quietNightModeIsRunning) == null || Intrinsics.areEqual(dataAirconSystem4.info.quietNightModeIsRunning, bool2)) && !z10))) {
                    TreeMap<String, DataZone> treeMap = dataAirconSystem5.zones;
                    Intrinsics.checkNotNull(treeMap);
                    if (treeMap.size() != 0) {
                        TreeMap<String, DataZone> treeMap2 = dataAirconSystem5.zones;
                        Intrinsics.checkNotNull(treeMap2);
                        for (String str6 : treeMap2.keySet()) {
                            TreeMap<String, DataZone> treeMap3 = dataAirconSystem5.zones;
                            Intrinsics.checkNotNull(treeMap3);
                            DataZone dataZone2 = treeMap3.get(str6);
                            if (dataZone2 != null && (zoneState2 = dataZone2.state) != null && zoneState2 == ZoneState.open) {
                                TreeMap<String, DataZone> treeMap4 = dataAirconSystem4.zones;
                                Intrinsics.checkNotNull(treeMap4);
                                DataZone dataZone3 = treeMap4.get(str6);
                                if (dataZone3 != null) {
                                    Integer num12 = dataZone3.type;
                                    if (num12 == null) {
                                        dataZone2.value = 100;
                                        dataZone2.setTemp = Float.valueOf(16.0f);
                                    } else if (num12 != null && num12.intValue() == 0) {
                                        dataZone2.value = 100;
                                    } else {
                                        dataZone2.setTemp = Float.valueOf(16.0f);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    DataAirconInfo dataAirconInfo11 = dataAirconSystem6.info;
                    Boolean bool18 = Boolean.FALSE;
                    dataAirconInfo11.quietNightModeIsRunning = bool18;
                    DataAirconSystem dataAirconSystem7 = getDataAirconSystem(dataMaster, str);
                    DataAirconInfo dataAirconInfo12 = dataAirconSystem5.info;
                    SystemState systemState3 = dataAirconInfo12.state;
                    DataAirconSystem i02 = (systemState3 == null || systemState3 != SystemState.off) ? i0(dataAirconSystem7, dataAirconInfo12.mode, Boolean.TRUE) : i0(dataAirconSystem7, dataAirconInfo12.mode, bool18);
                    if (i02 != null) {
                        DataAirconSystem.update$default(i02, null, dataAirconSystem5, null, null, false, 16, null);
                        DataAirconSystem.update$default(dataAirconSystem5, null, i02, null, null, false, 16, null);
                    }
                }
            }
        }
        Boolean bool19 = dataAirconSystem5.info.climateControlModeEnabled;
        if (bool19 != null && ((bool = dataAirconSystem4.info.climateControlModeEnabled) == null || !Intrinsics.areEqual(bool, bool19))) {
            DataAirconInfo dataAirconInfo13 = dataAirconSystem6.info;
            Boolean bool20 = dataAirconSystem5.info.climateControlModeEnabled;
            dataAirconInfo13.climateControlModeEnabled = bool20;
            Intrinsics.checkNotNull(bool20);
            if (bool20.booleanValue()) {
                DataAirconInfo dataAirconInfo14 = dataAirconSystem6.info;
                AirconMode airconMode6 = dataAirconSystem4.info.mode;
                dataAirconInfo14.climateControlModeIsRunning = Boolean.valueOf(airconMode6 != null && (airconMode6 == AirconMode.cool || airconMode6 == AirconMode.heat));
            } else {
                dataAirconSystem6.info.climateControlModeIsRunning = Boolean.FALSE;
            }
            arrayList.add("climateControlModeEnabledChange");
        }
        DataAirconSystem.update$default(dataAirconSystem5, null, b0(dataAirconSystem5, dataAirconSystem4), null, null, false, 16, null);
        DataAirconSystem dataAirconSystem8 = new DataAirconSystem(dataAirconSystem5.info.uid);
        DataAirconSystem.update$default(dataAirconSystem8, null, dataAirconSystem5, null, null, false, 16, null);
        DataAirconInfo dataAirconInfo15 = dataAirconSystem8.info;
        SystemState systemState4 = dataAirconInfo15.state;
        if (systemState4 == null || (systemState = dataAirconSystem4.info.state) == null || systemState4 != systemState) {
            dataAirconSystem6.info.state = systemState4;
        } else {
            dataAirconInfo15.state = null;
        }
        AirconMode airconMode7 = dataAirconInfo15.mode;
        if (airconMode7 != null && (airconMode = dataAirconSystem4.info.mode) != null && airconMode7 == airconMode) {
            dataAirconInfo15.mode = null;
        }
        FanStatus fanStatus5 = dataAirconInfo15.fan;
        if (fanStatus5 == null || (fanStatus = dataAirconSystem4.info.fan) == null || fanStatus5 != fanStatus) {
            DataAirconInfo dataAirconInfo16 = dataAirconSystem6.info;
            if (dataAirconInfo16.fan != FanStatus.autoAA) {
                dataAirconInfo16.fan = fanStatus5;
            }
        } else {
            dataAirconInfo15.fan = null;
        }
        Float f10 = dataAirconInfo15.setTemp;
        if (f10 == null || (f7 = dataAirconSystem4.info.setTemp) == null || !Intrinsics.areEqual(f10, f7)) {
            dataAirconSystem6.info.setTemp = dataAirconSystem8.info.setTemp;
        } else {
            dataAirconSystem8.info.setTemp = null;
        }
        Integer num13 = dataAirconSystem8.info.myZone;
        if (num13 == null || (num7 = dataAirconSystem4.info.myZone) == null || !Intrinsics.areEqual(num13, num7)) {
            dataAirconSystem6.info.myZone = dataAirconSystem8.info.myZone;
        } else {
            dataAirconSystem8.info.myZone = null;
        }
        DataAirconInfo dataAirconInfo17 = dataAirconSystem8.info;
        DataAirconSystem.FreshAirStatus freshAirStatus5 = dataAirconInfo17.freshAirStatus;
        if (freshAirStatus5 == null || (freshAirStatus = dataAirconSystem4.info.freshAirStatus) == null || freshAirStatus5 != freshAirStatus) {
            dataAirconSystem6.info.freshAirStatus = freshAirStatus5;
        } else {
            dataAirconInfo17.freshAirStatus = null;
        }
        TreeMap<String, DataZone> treeMap5 = dataAirconSystem8.zones;
        Intrinsics.checkNotNull(treeMap5);
        Collection<DataZone> values = treeMap5.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        ArrayList<DataZone> arrayList2 = new ArrayList();
        for (DataZone dataZone4 : values) {
            if (dataZone4 != null) {
                arrayList2.add(dataZone4);
            }
        }
        for (DataZone dataZone5 : arrayList2) {
            TreeMap<String, DataZone> treeMap6 = dataAirconSystem4.zones;
            Intrinsics.checkNotNull(treeMap6);
            DataZone dataZone6 = treeMap6.get(dataZone5.getZoneKey());
            if (dataZone6 != null) {
                ZoneState zoneState3 = dataZone5.state;
                if (zoneState3 == null || (zoneState = dataZone6.state) == null || zoneState3 != zoneState) {
                    TreeMap<String, DataZone> treeMap7 = dataAirconSystem6.zones;
                    Intrinsics.checkNotNull(treeMap7);
                    DataZone dataZone7 = treeMap7.get(dataZone5.getZoneKey());
                    if (dataZone7 == null) {
                        dataZone7 = new DataZone(dataZone5.getZoneKey());
                        TreeMap<String, DataZone> treeMap8 = dataAirconSystem6.zones;
                        Intrinsics.checkNotNull(treeMap8);
                        treeMap8.put(dataZone7.getZoneKey(), dataZone7);
                    }
                    dataZone7.state = dataZone5.state;
                } else {
                    dataZone5.state = null;
                }
                Integer num14 = dataZone5.value;
                if (num14 == null || (num6 = dataZone6.value) == null || !Intrinsics.areEqual(num14, num6)) {
                    TreeMap<String, DataZone> treeMap9 = dataAirconSystem6.zones;
                    Intrinsics.checkNotNull(treeMap9);
                    DataZone dataZone8 = treeMap9.get(dataZone5.getZoneKey());
                    if (dataZone8 == null) {
                        dataZone8 = new DataZone(dataZone5.getZoneKey());
                        TreeMap<String, DataZone> treeMap10 = dataAirconSystem6.zones;
                        Intrinsics.checkNotNull(treeMap10);
                        treeMap10.put(dataZone8.getZoneKey(), dataZone8);
                    }
                    dataZone8.value = dataZone5.value;
                } else {
                    dataZone5.value = null;
                }
                Float f11 = dataZone5.setTemp;
                if (f11 == null || (f3 = dataZone6.setTemp) == null || !Intrinsics.areEqual(f11, f3)) {
                    TreeMap<String, DataZone> treeMap11 = dataAirconSystem6.zones;
                    Intrinsics.checkNotNull(treeMap11);
                    DataZone dataZone9 = treeMap11.get(dataZone5.getZoneKey());
                    if (dataZone9 == null) {
                        dataZone9 = new DataZone(dataZone5.getZoneKey());
                        TreeMap<String, DataZone> treeMap12 = dataAirconSystem6.zones;
                        Intrinsics.checkNotNull(treeMap12);
                        treeMap12.put(dataZone9.getZoneKey(), dataZone9);
                    }
                    dataZone9.setTemp = dataZone5.setTemp;
                } else {
                    dataZone5.setTemp = null;
                }
                Integer num15 = dataZone5.motionConfig;
                if (num15 == null || (num5 = dataZone6.motionConfig) == null || !Intrinsics.areEqual(num15, num5)) {
                    TreeMap<String, DataZone> treeMap13 = dataAirconSystem6.zones;
                    Intrinsics.checkNotNull(treeMap13);
                    DataZone dataZone10 = treeMap13.get(dataZone5.getZoneKey());
                    if (dataZone10 == null) {
                        dataZone10 = new DataZone(dataZone5.getZoneKey());
                        TreeMap<String, DataZone> treeMap14 = dataAirconSystem6.zones;
                        Intrinsics.checkNotNull(treeMap14);
                        treeMap14.put(dataZone10.getZoneKey(), dataZone10);
                    }
                    dataZone10.motionConfig = dataZone5.motionConfig;
                } else {
                    dataZone5.motionConfig = null;
                }
            }
        }
        Xml2JsonFunctions.Companion.getInstance().sendMessages(this.context, dataAirconSystem8);
        String str7 = dataAirconSystem5.info.name;
        if (str7 == null || ((str4 = dataAirconSystem4.info.name) != null && Intrinsics.areEqual(str4, str7))) {
            z11 = false;
        } else {
            String str8 = dataAirconSystem5.info.name;
            Intrinsics.checkNotNull(str8);
            if (str8.length() > 12) {
                throw new ExceptionUart("Aircon name is too long - maximum length is 12");
            }
            dataAirconSystem6.info.name = dataAirconSystem5.info.name;
            arrayList.add("nameChange");
            if (dataMaster.aircons.size() == 1) {
                dataMaster.system.name = dataAirconSystem5.info.name;
            }
            z11 = true;
        }
        Integer num16 = dataAirconSystem5.info.countDownToOn;
        if (num16 != null && ((num4 = dataAirconSystem4.info.countDownToOn) == null || !Intrinsics.areEqual(num4, num16))) {
            Integer num17 = dataAirconSystem5.info.countDownToOn;
            Intrinsics.checkNotNull(num17);
            if (num17.intValue() < 0) {
                throw new ExceptionUart("Aircon countDownToOn cannot be less than 0");
            }
            Integer num18 = dataAirconSystem5.info.countDownToOn;
            Intrinsics.checkNotNull(num18);
            if (num18.intValue() > 720) {
                throw new ExceptionUart("Aircon countDownToOn cannot be greater than 720 minutes");
            }
            dataAirconSystem6.info.countDownToOn = dataAirconSystem5.info.countDownToOn;
            arrayList.add("countDownToOnChange");
        }
        Integer num19 = dataAirconSystem5.info.countDownToOff;
        if (num19 != null && ((num3 = dataAirconSystem4.info.countDownToOff) == null || !Intrinsics.areEqual(num3, num19))) {
            Integer num20 = dataAirconSystem5.info.countDownToOff;
            Intrinsics.checkNotNull(num20);
            if (num20.intValue() < 0) {
                throw new ExceptionUart("Aircon countDownToOff cannot be less than 0");
            }
            Integer num21 = dataAirconSystem5.info.countDownToOff;
            Intrinsics.checkNotNull(num21);
            if (num21.intValue() > 720) {
                throw new ExceptionUart("Aircon countDownToOff cannot be greater than 720 minutes");
            }
            dataAirconSystem6.info.countDownToOff = dataAirconSystem5.info.countDownToOff;
            arrayList.add("countDownToOffChange");
        }
        DataAirconInfo dataAirconInfo18 = dataAirconSystem5.info;
        if (dataAirconInfo18.setTemp != null) {
            Integer num22 = dataAirconInfo18.myZone;
            if (num22 != null) {
                Intrinsics.checkNotNull(num22);
                if (num22.intValue() > 0) {
                    Integer num23 = dataAirconSystem5.info.myZone;
                    Intrinsics.checkNotNull(num23);
                    if (num23.intValue() <= 10) {
                        num = dataAirconSystem5.info.myZone;
                        if (num == null && (num2 = dataAirconSystem4.info.myZone) != null) {
                            Intrinsics.checkNotNull(num2);
                            if (num2.intValue() > 0) {
                                num = dataAirconSystem4.info.myZone;
                            }
                        }
                        if (num != null && num.intValue() > 0) {
                            if (dataAirconSystem5.zones == null) {
                                dataAirconSystem5.zones = new TreeMap<>();
                            }
                            String zoneKey = DataZone.Companion.getZoneKey(num);
                            TreeMap<String, DataZone> treeMap15 = dataAirconSystem5.zones;
                            Intrinsics.checkNotNull(treeMap15);
                            dataZone = treeMap15.get(zoneKey);
                            if (dataZone == null) {
                                dataZone = new DataZone(num);
                                TreeMap<String, DataZone> treeMap16 = dataAirconSystem5.zones;
                                Intrinsics.checkNotNull(treeMap16);
                                treeMap16.put(zoneKey, dataZone);
                            }
                            dataZone.setTemp = dataAirconSystem5.info.setTemp;
                        }
                    }
                }
                AppFeatures.logError(AppFeatures.instance, new RuntimeException("Invalid myZone supplied myZone should be 1-10"), null, 2, null);
                num = null;
                if (num == null) {
                    Intrinsics.checkNotNull(num2);
                    if (num2.intValue() > 0) {
                    }
                }
                if (num != null) {
                    if (dataAirconSystem5.zones == null) {
                    }
                    String zoneKey2 = DataZone.Companion.getZoneKey(num);
                    TreeMap<String, DataZone> treeMap152 = dataAirconSystem5.zones;
                    Intrinsics.checkNotNull(treeMap152);
                    dataZone = treeMap152.get(zoneKey2);
                    if (dataZone == null) {
                    }
                    dataZone.setTemp = dataAirconSystem5.info.setTemp;
                }
            } else {
                num = null;
                if (num == null) {
                }
                if (num != null) {
                }
            }
        }
        Timber.forest.d("----------Constant: adjustZoneFollowersDueToConstantsAndMyFanIfActive1-------", new Object[0]);
        adjustZoneFollowersDueToConstantsAndMyFanIfActive1(dataAirconSystem4, dataAirconSystem5);
        TreeMap<String, DataZone> treeMap17 = dataAirconSystem5.zones;
        Intrinsics.checkNotNull(treeMap17);
        int size = treeMap17.size();
        String colon = OTAFlashRowModel_v1.Data.DISCRIMINATOR;
        if (size != 0) {
            TreeMap<String, DataZone> treeMap18 = dataAirconSystem5.zones;
            Intrinsics.checkNotNull(treeMap18);
            Iterator<String> it = treeMap18.keySet().iterator();
            while (it.hasNext()) {
                String next = it.next();
                TreeMap<String, DataZone> treeMap19 = dataAirconSystem5.zones;
                Intrinsics.checkNotNull(treeMap19);
                DataZone dataZone11 = treeMap19.get(next);
                Intrinsics.checkNotNull(dataZone11);
                dataZone11.setNumberFromKey(next);
                TreeMap<String, DataZone> treeMap20 = dataAirconSystem4.zones;
                Intrinsics.checkNotNull(treeMap20);
                DataZone dataZone12 = treeMap20.get(next);
                if (dataZone12 == null) {
                    Iterator<String> it2 = it;
                    DataAirconSystem dataAirconSystem9 = dataAirconSystem6;
                    boolean z13 = z11;
                    String str9 = colon;
                    Integer num24 = dataZone11.number;
                    if (num24 != null && dataAirconSystem5.info.noOfZones != null) {
                        Intrinsics.checkNotNull(num24);
                        int intValue = num24.intValue();
                        Integer num25 = dataAirconSystem5.info.noOfZones;
                        Intrinsics.checkNotNull(num25);
                        if (intValue <= num25.intValue()) {
                            String str10 = dataZone11.name;
                            if (str10 != null) {
                                Intrinsics.checkNotNull(str10);
                                c10 = '\f';
                                if (str10.length() > 12) {
                                    throw new ExceptionUart("Zone name for zone " + next + " is too long - maximum length is 12");
                                }
                                DataZone dataZone13 = new DataZone(next);
                                dataZone13.name = dataZone11.name;
                                dataAirconSystem6 = dataAirconSystem9;
                                TreeMap<String, DataZone> treeMap21 = dataAirconSystem6.zones;
                                Intrinsics.checkNotNull(treeMap21);
                                treeMap21.put(next, dataZone13);
                                arrayList.add("zoneNameChange");
                            } else {
                                dataAirconSystem6 = dataAirconSystem9;
                                c10 = '\f';
                                Integer num26 = dataZone11.following;
                                if (num26 == null || (num26 != null && num26.intValue() == 0)) {
                                    ArrayList<String> arrayList3 = dataZone11.followers;
                                    if (arrayList3 != null) {
                                        Intrinsics.checkNotNull(arrayList3);
                                        if (arrayList3.size() > 0) {
                                            DataZone dataZone14 = new DataZone(next);
                                            dataZone14.name = dataZone11.defaultZoneName();
                                            DataZone.update$default(dataZone14, dataZone11, null, null, false, 12, null);
                                            TreeMap<String, DataZone> treeMap22 = dataAirconSystem6.zones;
                                            Intrinsics.checkNotNull(treeMap22);
                                            treeMap22.put(next, dataZone14);
                                            arrayList.add("zoneGrouping");
                                        }
                                    }
                                }
                            }
                            colon = str9;
                            z11 = z13;
                            it = it2;
                        }
                    }
                    throw new ExceptionUart("Zone " + next + " is not found - check the noOfZones for this aircon and use z01-z10");
                }
                Iterator<String> it3 = it;
                String str11 = dataZone11.name;
                if (str11 != null && ((str3 = dataZone12.name) == null || !Intrinsics.areEqual(str3, str11))) {
                    String str12 = dataZone11.name;
                    Intrinsics.checkNotNull(str12);
                    if (str12.length() > 12) {
                        throw new ExceptionUart("Zone name for zone " + next + " is too long - maximum length is 12");
                    }
                    DataZone dataZone15 = new DataZone(next);
                    dataZone15.name = dataZone11.name;
                    TreeMap<String, DataZone> treeMap23 = dataAirconSystem6.zones;
                    Intrinsics.checkNotNull(treeMap23);
                    treeMap23.put(next, dataZone15);
                    arrayList.add("zoneNameChange");
                }
                boolean hasJZ10UpdateToSend = dataZone12.hasJZ10UpdateToSend(dataZone11, false);
                boolean hasJZ12UpdateToSend = dataZone12.hasJZ12UpdateToSend(dataZone11, false);
                if (hasJZ10UpdateToSend || hasJZ12UpdateToSend) {
                    DataZone copy = dataZone12.copy();
                    DataZone.update$default(copy, dataZone11, null, null, false, 12, null);
                    copy.completeZoneData();
                    if (hasJZ10UpdateToSend) {
                        if (this.zone.contains(dataZone11.getZoneKey())) {
                            Timber.forest.d("Constants: removing masterzone from the list due to incoming json!!! zone:" + dataZone11.getZoneKey(), new Object[0]);
                            this.zone.remove(dataZone11.getZoneKey());
                        }
                        String encodeZone_j = HandlerCB.Companion.getInstance().encodeZone_j(dataAirconSystem4.info.uid, copy);
                        HandlerCan.Companion.getInstance().sendCanMessageToCB(this.context, encodeZone_j);
                        arrayList.add(encodeZone_j);
                        Timber.Forest forest = Timber.forest;
                        String str13 = dataAirconSystem4.info.uid;
                        Integer num27 = copy.number;
                        ZoneState zoneState4 = copy.state;
                        z12 = z11;
                        Float f12 = copy.setTemp;
                        Integer num28 = copy.value;
                        dataAirconSystem3 = dataAirconSystem6;
                        StringBuilder sb = new StringBuilder();
                        str2 = colon;
                        sb.append("Sending JZ10(3) for UID - ");
                        sb.append(str13);
                        sb.append(" - zone ");
                        sb.append(num27);
                        sb.append(", ");
                        sb.append(zoneState4);
                        sb.append(", ");
                        sb.append(f12);
                        sb.append(",");
                        sb.append(num28);
                        sb.append("% :");
                        sb.append(encodeZone_j);
                        forest.d(sb.toString(), new Object[0]);
                    } else {
                        dataAirconSystem3 = dataAirconSystem6;
                        z12 = z11;
                        str2 = colon;
                    }
                    if (hasJZ12UpdateToSend) {
                        String encodeZone_k = HandlerCB.Companion.getInstance().encodeZone_k(dataAirconSystem4.info.uid, copy);
                        HandlerCan.Companion.getInstance().sendCanMessageToCB(this.context, encodeZone_k);
                        arrayList.add(encodeZone_k);
                        Timber.Forest forest2 = Timber.forest;
                        String str14 = dataAirconSystem5.info.uid;
                        Integer num29 = copy.number;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Sending JZ12 for UID - ");
                        sb2.append(str14);
                        sb2.append(" - zone ");
                        sb2.append(num29);
                        String str15 = str2;
                        sb2.append(str15);
                        sb2.append(encodeZone_k);
                        forest2.d(sb2.toString(), new Object[0]);
                        it = it3;
                        colon = str15;
                        z11 = z12;
                        dataAirconSystem6 = dataAirconSystem3;
                    } else {
                        it = it3;
                        z11 = z12;
                        dataAirconSystem6 = dataAirconSystem3;
                        colon = str2;
                    }
                } else {
                    it = it3;
                }
            }
        }
        boolean z14 = z11;
        String str16 = colon;
        boolean hasJZ14UpdateToSend = dataAirconSystem4.hasJZ14UpdateToSend(dataAirconSystem5);
        boolean hasJZ6UpdateToSend = dataAirconSystem4.hasJZ6UpdateToSend(dataAirconSystem5);
        if (hasJZ14UpdateToSend || hasJZ6UpdateToSend) {
            DataAirconSystem dataAirconSystem10 = new DataAirconSystem(dataAirconSystem4.info.uid);
            DataAirconSystem dataAirconSystem11 = dataAirconSystem6;
            DataAirconSystem.update$default(dataAirconSystem10, str, dataAirconSystem4, null, null, false, 16, null);
            DataAirconInfo.update$default(dataAirconSystem10.info, dataAirconSystem5.info, null, false, 4, null);
            dataAirconSystem10.info.completeAirconData();
            if (hasJZ6UpdateToSend) {
                String zoneInfoToCB = zoneInfoToCB(dataAirconSystem10.info);
                HandlerCan.Companion.getInstance().sendCanMessageToCB(this.context, zoneInfoToCB);
                arrayList.add(zoneInfoToCB);
                Timber.forest.d("Sending JZ6 to UID - " + dataAirconSystem10.info.uid + str16 + zoneInfoToCB, new Object[0]);
            }
            if (hasJZ14UpdateToSend) {
                String ecodeAirconSource = ecodeAirconSource(dataAirconSystem10.info);
                HandlerCan.Companion.getInstance().sendCanMessageToCB(this.context, ecodeAirconSource);
                arrayList.add(ecodeAirconSource);
                Timber.forest.d("Sending JZ14 to UID - " + dataAirconSystem10.info.uid + str16 + ecodeAirconSource, new Object[0]);
            }
            dataMaster2 = dataMaster;
            dataAirconSystem2 = dataAirconSystem11;
        } else {
            dataMaster2 = dataMaster;
            dataAirconSystem2 = dataAirconSystem6;
        }
        updateAirconDataSaveToDbAndSend(dataMaster2, dataAirconSystem4, dataAirconSystem2);
        if (z14) {
            HandlerJson.Companion.getInstance(this.context).processData(dataMaster2, "snapShotUpdate");
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList;
    }

    private final void D0(DataMyGarageController dataMyGarageController) {
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(this.context);
            dataMaster.myGarageRFControllers = dataMyGarageController;
            HandlerJson.Companion.getInstance(this.context).processData(dataMaster, "updateMyGarageControllers");
            MyGarageController.Companion.b().e(MyApp.appContextProvider.appContext(), dataMaster);
        }
    }

    private final void E0(DataMaster dataMaster) {
        DataSystem dataSystem = dataMaster.system;
        if (dataSystem.showMeasuredTemp == null) {
            dataSystem.showMeasuredTemp = Boolean.FALSE;
        }
    }

    /* renamed from: G0 */
    private final boolean processDataZoneGroupMessageFromJSON(DataMaster dataMaster, DataAirconSystem dataAirconSystem) {
        DataAirconSystem airconByUid = dataMaster.getAirconByUid(dataAirconSystem.info.uid);
        return airconByUid != null && DataAirconSystem.update$default(airconByUid, null, dataAirconSystem, null, null, false, 16, null);
    }

    /* renamed from: H */
    private final String encodeActivationCodeMessage(DataAirconInfo dataAirconInfo) {
        String str = ((("" + (isCBType4Aircon(dataAirconInfo.uid) ? "08" : "07")) + "01") + FRLParser.DEFAULT_UID) + "09";
        DataAirconSystem.ActivationCode activationCode = dataAirconInfo.setActivationCode;
        if (activationCode == null) {
            Timber.forest.d("Error in generating JZ23-setActivationCode is null", new Object[0]);
            return "";
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Locale locale = Locale.ENGLISH;
        Intrinsics.checkNotNull(activationCode);
        String format = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Integer.valueOf(activationCode.value)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(locale, format, *args)");
        String str2 = str + format;
        String str3 = dataAirconInfo.unlockCode;
        if (str3 == null) {
            Timber.forest.d("Error in generating JZ23-unlockCode is null", new Object[0]);
            return "";
        }
        Intrinsics.checkNotNull(str3);
        String substring = str3.substring(0, 2);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        String format2 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Integer.valueOf(Integer.parseInt(substring, CharsKt__CharJVMKt.checkRadix(16)))}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(locale, format, *args)");
        String str4 = str2 + format2;
        String str5 = dataAirconInfo.unlockCode;
        Intrinsics.checkNotNull(str5);
        String substring2 = str5.substring(2, 4);
        Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String…ing(startIndex, endIndex)");
        String format3 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Integer.valueOf(Integer.parseInt(substring2, CharsKt__CharJVMKt.checkRadix(16)))}, 1));
        Intrinsics.checkNotNullExpressionValue(format3, "format(locale, format, *args)");
        String str6 = str4 + format3;
        Integer num = dataAirconInfo.setActivationTime;
        if (num == null) {
            Timber.forest.d("Error in generating JZ23-setActivationTime is null", new Object[0]);
            return "";
        }
        String format4 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{num}, 1));
        Intrinsics.checkNotNullExpressionValue(format4, "format(locale, format, *args)");
        return (str6 + format4) + FRLParser.DEFAULT_SENSORUID;
    }

    /* renamed from: I */
    private final String devicePairingToCB(String uid, int pairingControl, int rfDeviceType, Integer channelNo) {
        String str = ((("" + ((rfDeviceType == 129 || rfDeviceType == 130) ? "07" : "08")) + "01") + uid) + FRLParser.COMMAND_SET_PAIRING;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Locale locale = Locale.ENGLISH;
        String format = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Integer.valueOf(pairingControl)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(locale, format, *args)");
        String str2 = str + format;
        String format2 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Integer.valueOf(rfDeviceType)}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(locale, format, *args)");
        String str3 = str2 + format2;
        String format3 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{channelNo}, 1));
        Intrinsics.checkNotNullExpressionValue(format3, "format(locale, format, *args)");
        String str4 = (str3 + format3) + "00000000";
        Timber.forest.d("JZ55:" + str4, new Object[0]);
        return str4;
    }

    /* renamed from: J */
    private final String rfDeviceCalibrationToCB(String uid, int calibrationControl, int upDownPosition, int channelNo) {
        String str = ((("08") + "01") + uid) + "27";
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Locale locale = Locale.ENGLISH;
        String format = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Integer.valueOf(calibrationControl)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(locale, format, *args)");
        String str2 = str + format;
        String format2 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Integer.valueOf(channelNo)}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(locale, format, *args)");
        String str3 = str2 + format2;
        String format3 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Integer.valueOf(upDownPosition)}, 1));
        Intrinsics.checkNotNullExpressionValue(format3, "format(locale, format, *args)");
        String str4 = (str3 + format3) + "00000000";
        Timber.forest.d("JZ57:" + str4, new Object[0]);
        return str4;
    }

    /* renamed from: K */
    private final String zoneInfoToCB(DataAirconInfo dataAirconInfo) {
        String str = "" + (isCBType4Aircon(dataAirconInfo.uid) ? "08" : "07");
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        String str2 = "01";
        sb.append("01");
        String str3 = ((sb.toString() + dataAirconInfo.uid) + "01") + CommandKey.CMD_RESPONSE_AEGIS_GET_R1;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Locale locale = Locale.ENGLISH;
        String format = String.format(locale, "%02X", Arrays.copyOf(new Object[]{dataAirconInfo.noOfZones}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(locale, format, *args)");
        String str4 = str3 + format;
        String format2 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{dataAirconInfo.noOfConstantZones}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(locale, format, *args)");
        String str5 = str4 + format2;
        String format3 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{dataAirconInfo.constantZone1}, 1));
        Intrinsics.checkNotNullExpressionValue(format3, "format(locale, format, *args)");
        String str6 = str5 + format3;
        String format4 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{dataAirconInfo.constantZone2}, 1));
        Intrinsics.checkNotNullExpressionValue(format4, "format(locale, format, *args)");
        String format5 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{dataAirconInfo.constantZone3}, 1));
        Intrinsics.checkNotNullExpressionValue(format5, "format(locale, format, *args)");
        String str7 = (str6 + format4) + format5;
        Integer num = dataAirconInfo.filterCleanStatus;
        if (num != null && num.intValue() == 0) {
            str2 = "00";
        }
        return str7 + str2;
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x019e  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0141  */
    /* renamed from: M */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void updateMasterFromDataAircon(DataMaster dataMaster, DataAirconSystem dataAirconSystem) throws ExceptionUart {
        String str;
        HandlerAircon handlerAircon;
        Iterator<String> it;
        Integer num;
        if (dataAirconSystem == null || (str = dataAirconSystem.info.uid) == null) {
            Timber.forest.d("Incoming DataAircon is null or has null aircon uid!, cannot process sensor pairing message request", new Object[0]);
            throw new ExceptionUart("Incoming DataAircon is null or has null aircon uid!, cannot process sensor pairing message request");
        }
        DataAirconSystem airconByUid = dataMaster.getAirconByUid(str);
        if (airconByUid == null || airconByUid.info.uid == null) {
            Timber.forest.d("No aircon found in memory, can't generate CAN from JSON", new Object[0]);
            throw new ExceptionUart("No aircon found with uid " + dataAirconSystem.info.uid + " in memory, please check if the uid is correct.");
        }
        DataAirconSystem dataAirconSystem2 = new DataAirconSystem(airconByUid.info.uid);
        TreeMap<String, DataZone> treeMap = dataAirconSystem.zones;
        Intrinsics.checkNotNull(treeMap);
        if (treeMap.size() != 0) {
            StringBuffer stringBuffer = new StringBuffer();
            TreeMap<String, DataZone> treeMap2 = dataAirconSystem.zones;
            Intrinsics.checkNotNull(treeMap2);
            Iterator<String> it2 = treeMap2.keySet().iterator();
            boolean z7 = false;
            while (it2.hasNext()) {
                String next = it2.next();
                TreeMap<String, DataZone> treeMap3 = dataAirconSystem.zones;
                Intrinsics.checkNotNull(treeMap3);
                DataZone dataZone = treeMap3.get(next);
                Intrinsics.checkNotNull(dataZone);
                dataZone.setNumberFromKey(next);
                TreeMap<String, DataZone> treeMap4 = airconByUid.zones;
                Intrinsics.checkNotNull(treeMap4);
                DataZone dataZone2 = treeMap4.get(next);
                if (dataZone2 != null) {
                    String str2 = dataZone.sensorUid;
                    if (str2 == null) {
                        it = it2;
                        dataZone2.clearSensorData();
                        String encodeResponse_l = HandlerCB.Companion.getInstance().encodeResponse_l(airconByUid.info.uid, dataZone);
                        if (z7) {
                            stringBuffer.append(" ");
                        }
                        stringBuffer.append(encodeResponse_l);
                        Timber.forest.d("Generating JZ32 with blank sensorUid for Aircon UID - " + dataAirconSystem.info.uid + " - zone " + dataZone.number + OTAFlashRowModel_v1.Data.DISCRIMINATOR + encodeResponse_l, new Object[0]);
                        if (dataZone.following == null || dataZone.followers != null) {
                            DataZone dataZone3 = new DataZone(next);
                            num = dataZone.following;
                            if (num != null) {
                                dataZone3.following = num;
                            }
                            if (dataZone.followers != null) {
                                dataZone3.followers = new ArrayList<>();
                                ArrayList<String> arrayList = dataZone.followers;
                                Intrinsics.checkNotNull(arrayList);
                                Iterator<String> it3 = arrayList.iterator();
                                while (it3.hasNext()) {
                                    String next2 = it3.next();
                                    ArrayList<String> arrayList2 = dataZone3.followers;
                                    Intrinsics.checkNotNull(arrayList2);
                                    arrayList2.add(next2);
                                }
                            }
                            TreeMap<String, DataZone> treeMap5 = dataAirconSystem2.zones;
                            Intrinsics.checkNotNull(treeMap5);
                            treeMap5.put(next, dataZone3);
                        }
                        it2 = it;
                        z7 = true;
                    } else {
                        if (!(str2 == null || str2.length() == 0)) {
                            if (Intrinsics.areEqual(dataZone.sensorUid, FRLParser.DEFAULT_SENSORUID)) {
                                it = it2;
                            } else {
                                TreeMap<String, DataZone> treeMap6 = airconByUid.zones;
                                Intrinsics.checkNotNull(treeMap6);
                                Iterator<String> it4 = treeMap6.keySet().iterator();
                                while (it4.hasNext()) {
                                    Iterator<String> it5 = it2;
                                    String next3 = it4.next();
                                    Iterator<String> it6 = it4;
                                    if (!Intrinsics.areEqual(next3, dataZone.getZoneKey())) {
                                        TreeMap<String, DataZone> treeMap7 = airconByUid.zones;
                                        Intrinsics.checkNotNull(treeMap7);
                                        DataZone dataZone4 = treeMap7.get(next3);
                                        Intrinsics.checkNotNull(dataZone4);
                                        String str3 = dataZone4.sensorUid;
                                        if (str3 != null && Intrinsics.areEqual(str3, dataZone.sensorUid)) {
                                            dataZone4.clearSensorData();
                                        }
                                    }
                                    it4 = it6;
                                    it2 = it5;
                                }
                                it = it2;
                                DataZone dataZone5 = new DataZone(next);
                                dataZone5.sensorUid = dataZone.sensorUid;
                                dataZone5.sensorMajorRev = dataZone.sensorMajorRev;
                                TreeMap<String, DataZone> treeMap8 = dataAirconSystem2.zones;
                                Intrinsics.checkNotNull(treeMap8);
                                treeMap8.put(next, dataZone5);
                            }
                            String encodeResponse_l2 = HandlerCB.Companion.getInstance().encodeResponse_l(airconByUid.info.uid, dataZone);
                            if (z7) {
                                stringBuffer.append(" ");
                            }
                            stringBuffer.append(encodeResponse_l2);
                            Timber.forest.d("Generating JZ32 for Aircon UID - " + dataAirconSystem.info.uid + " - zone " + dataZone.number + OTAFlashRowModel_v1.Data.DISCRIMINATOR + encodeResponse_l2, new Object[0]);
                            if (Intrinsics.areEqual(dataZone.sensorUid, FRLParser.DEFAULT_SENSORUID)) {
                                dataZone2.clearSensorData();
                            }
                        }
                        if (dataZone.following == null) {
                            DataZone dataZone32 = new DataZone(next);
                            num = dataZone.following;
                            if (num != null) {
                            }
                            if (dataZone.followers != null) {
                            }
                            TreeMap<String, DataZone> treeMap52 = dataAirconSystem2.zones;
                            Intrinsics.checkNotNull(treeMap52);
                            treeMap52.put(next, dataZone32);
                            it2 = it;
                            z7 = true;
                        }
                    }
                }
            }
            if (z7) {
                HandlerCan companion = HandlerCan.Companion.getInstance();
                Timber.forest.d("Sending Sensor Pairing CAN messages (JZ32s) - " + ((Object) stringBuffer), new Object[0]);
                handlerAircon = this;
                companion.sendCanMessageToCB(handlerAircon.context, stringBuffer.toString());
            } else {
                handlerAircon = this;
            }
        } else {
            handlerAircon = this;
        }
        handlerAircon.updateAirconDataSaveToDbAndSend(dataMaster, airconByUid, dataAirconSystem2);
    }

    /* renamed from: O */
    private final DataAirconSystem getDataAirconSystem(DataMaster dataMaster, String str) {
        DataAirconSystem dataAirconSystem = dataMaster.aircons.get(str);
        if (dataAirconSystem == null && (dataAirconSystem = dataMaster.getAirconByUid(str)) != null) {
            Timber.forest.d("Warning getting aircon via uid not ac1-4", new Object[0]);
        }
        return dataAirconSystem;
    }

    @NotNull
    /* renamed from: S */
    public static final HandlerAircon getInstance() {
        return Companion.getInstance();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean V(DataAirconSystem dataAirconSystem, DataZone dataZone) {
        DataAirconInfo dataAirconInfo = dataAirconSystem.info;
        Integer num = dataAirconInfo.cbFWRevMajor;
        if (num != null && dataAirconInfo.cbFWRevMinor != null) {
            Intrinsics.checkNotNull(num);
            if (num.intValue() > 8) {
                Integer num2 = dataAirconSystem.info.myZone;
                if (num2 != null && Intrinsics.areEqual(num2, dataZone.number) && !Intrinsics.areEqual(dataZone.number, dataAirconSystem.info.constantZone1) && !Intrinsics.areEqual(dataZone.number, dataAirconSystem.info.constantZone2) && !Intrinsics.areEqual(dataZone.number, dataAirconSystem.info.constantZone3)) {
                    return true;
                }
            } else {
                Integer num3 = dataAirconSystem.info.cbFWRevMajor;
                if (num3 != null && num3.intValue() == 8) {
                    Integer num4 = dataAirconSystem.info.cbFWRevMinor;
                    Intrinsics.checkNotNull(num4);
                    if (num4.intValue() >= 16) {
                    }
                }
            }
        }
        return false;
    }

    private final boolean X(DataAirconSystem dataAirconSystem, DataAirconSystem dataAirconSystem2) {
        FanStatus fanStatus;
        DataAirconInfo dataAirconInfo = dataAirconSystem.info;
        FanStatus fanStatus2 = dataAirconInfo.fan;
        if (fanStatus2 != null && fanStatus2 == (fanStatus = FanStatus.autoAA) && dataAirconSystem2.info.fan != fanStatus) {
            return true;
        }
        FanStatus fanStatus3 = dataAirconSystem2.info.fan;
        if (fanStatus3 != null && fanStatus3 == FanStatus.autoAA) {
            return true;
        }
        Boolean bool = dataAirconInfo.aaAutoFanModeEnabled;
        if (bool != null) {
            Intrinsics.checkNotNull(bool);
            if (bool.booleanValue() && dataAirconSystem2.info.fan == FanStatus.auto) {
                return true;
            }
        }
        return false;
    }

    @JvmStatic
    public static final int f(@Nullable Integer num) {
        return Companion.getRoundUpAfter15PercentageValue(num);
    }

    /* JADX WARN: Removed duplicated region for block: B:122:0x0383 A[Catch: all -> 0x043e, TryCatch #0 {, blocks: (B:4:0x0013, B:5:0x0069, B:7:0x006f, B:10:0x0077, B:15:0x007b, B:16:0x0080, B:18:0x0088, B:22:0x009b, B:25:0x00dd, B:27:0x00e3, B:29:0x00e7, B:31:0x00f8, B:33:0x00fe, B:34:0x0112, B:36:0x013d, B:37:0x015f, B:39:0x0165, B:41:0x0169, B:43:0x016d, B:45:0x0173, B:47:0x0177, B:53:0x017d, B:55:0x0181, B:57:0x0185, B:59:0x018d, B:60:0x0198, B:62:0x01a7, B:64:0x01b4, B:66:0x01b8, B:68:0x01be, B:71:0x01ca, B:73:0x01ce, B:75:0x01d4, B:78:0x01de, B:80:0x01e2, B:82:0x01e8, B:86:0x01f4, B:88:0x021c, B:90:0x022f, B:92:0x0252, B:94:0x029d, B:96:0x02a7, B:98:0x02ad, B:101:0x0190, B:106:0x0095, B:113:0x030b, B:117:0x031c, B:119:0x032e, B:122:0x0383, B:124:0x03d8, B:126:0x03ea, B:136:0x0316, B:138:0x0333, B:142:0x0342, B:144:0x0354, B:145:0x033c, B:147:0x0359, B:151:0x0368, B:153:0x037a, B:154:0x0362, B:157:0x043a), top: B:3:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0433  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x043a A[ADDED_TO_REGION, EDGE_INSN: B:133:0x043a->B:157:0x043a BREAK  A[LOOP:2: B:113:0x030b->B:130:0x0437], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x042d  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0333 A[Catch: all -> 0x043e, TryCatch #0 {, blocks: (B:4:0x0013, B:5:0x0069, B:7:0x006f, B:10:0x0077, B:15:0x007b, B:16:0x0080, B:18:0x0088, B:22:0x009b, B:25:0x00dd, B:27:0x00e3, B:29:0x00e7, B:31:0x00f8, B:33:0x00fe, B:34:0x0112, B:36:0x013d, B:37:0x015f, B:39:0x0165, B:41:0x0169, B:43:0x016d, B:45:0x0173, B:47:0x0177, B:53:0x017d, B:55:0x0181, B:57:0x0185, B:59:0x018d, B:60:0x0198, B:62:0x01a7, B:64:0x01b4, B:66:0x01b8, B:68:0x01be, B:71:0x01ca, B:73:0x01ce, B:75:0x01d4, B:78:0x01de, B:80:0x01e2, B:82:0x01e8, B:86:0x01f4, B:88:0x021c, B:90:0x022f, B:92:0x0252, B:94:0x029d, B:96:0x02a7, B:98:0x02ad, B:101:0x0190, B:106:0x0095, B:113:0x030b, B:117:0x031c, B:119:0x032e, B:122:0x0383, B:124:0x03d8, B:126:0x03ea, B:136:0x0316, B:138:0x0333, B:142:0x0342, B:144:0x0354, B:145:0x033c, B:147:0x0359, B:151:0x0368, B:153:0x037a, B:154:0x0362, B:157:0x043a), top: B:3:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0359 A[Catch: all -> 0x043e, TryCatch #0 {, blocks: (B:4:0x0013, B:5:0x0069, B:7:0x006f, B:10:0x0077, B:15:0x007b, B:16:0x0080, B:18:0x0088, B:22:0x009b, B:25:0x00dd, B:27:0x00e3, B:29:0x00e7, B:31:0x00f8, B:33:0x00fe, B:34:0x0112, B:36:0x013d, B:37:0x015f, B:39:0x0165, B:41:0x0169, B:43:0x016d, B:45:0x0173, B:47:0x0177, B:53:0x017d, B:55:0x0181, B:57:0x0185, B:59:0x018d, B:60:0x0198, B:62:0x01a7, B:64:0x01b4, B:66:0x01b8, B:68:0x01be, B:71:0x01ca, B:73:0x01ce, B:75:0x01d4, B:78:0x01de, B:80:0x01e2, B:82:0x01e8, B:86:0x01f4, B:88:0x021c, B:90:0x022f, B:92:0x0252, B:94:0x029d, B:96:0x02a7, B:98:0x02ad, B:101:0x0190, B:106:0x0095, B:113:0x030b, B:117:0x031c, B:119:0x032e, B:122:0x0383, B:124:0x03d8, B:126:0x03ea, B:136:0x0316, B:138:0x0333, B:142:0x0342, B:144:0x0354, B:145:0x033c, B:147:0x0359, B:151:0x0368, B:153:0x037a, B:154:0x0362, B:157:0x043a), top: B:3:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x037f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void f0(DataAirconSystem dataAirconSystem, DataAirconSystem dataAirconSystem2) {
        Integer num;
        Integer num2;
        DataAirconSystem dataAirconSystem3;
        boolean z7;
        ZoneState zoneState;
        Integer num3;
        Integer num4;
        Integer num5;
        Integer num6;
        Integer num7;
        ZoneState zoneState2;
        ZoneState zoneState3;
        Integer num8;
        int i10 = 0;
        Timber.forest.d("DBGSS processZoneGrouping start", new Object[0]);
        synchronized (MyMasterData.class) {
            DataAirconSystem airconByUid = MyMasterData.Companion.getDataMaster(this.context).getAirconByUid(dataAirconSystem.info.uid);
            Intrinsics.checkNotNull(airconByUid);
            DataAirconSystem dataAirconSystem4 = new DataAirconSystem(airconByUid.info.uid);
            DataAirconSystem.update$default(dataAirconSystem4, null, airconByUid, null, null, false, 16, null);
            DataAirconSystem dataAirconSystem5 = dataAirconSystem4;
            DataAirconSystem.update$default(dataAirconSystem4, null, dataAirconSystem, null, null, false, 16, null);
            TreeMap<String, DataZone> treeMap = dataAirconSystem5.zones;
            Intrinsics.checkNotNull(treeMap);
            Collection<DataZone> values = treeMap.values();
            Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
            ArrayList<DataZone> arrayList = new ArrayList();
            for (DataZone dataZone : values) {
                if (dataZone != null) {
                    arrayList.add(dataZone);
                }
            }
            boolean z10 = false;
            for (DataZone dataZone2 : arrayList) {
                Integer num9 = dataZone2.following;
                if (num9 != null && (num9 == null || num9.intValue() != 0)) {
                    Timber.Forest forest = Timber.forest;
                    forest.d("DBGSS processZoneGrouping zone following detected, zone:" + dataZone2.number, new Object[i10]);
                    TreeMap<String, DataZone> treeMap2 = dataAirconSystem5.zones;
                    Intrinsics.checkNotNull(treeMap2);
                    DataZone.a aVar = DataZone.Companion;
                    DataZone dataZone3 = treeMap2.get(aVar.getZoneKey(dataZone2.following));
                    TreeMap<String, DataZone> treeMap3 = dataAirconSystem5.zones;
                    Intrinsics.checkNotNull(treeMap3);
                    DataZone dataZone4 = treeMap3.get(aVar.getZoneKey(dataZone2.number));
                    if (dataZone3 != null && dataZone4 != null) {
                        DataAirconInfo dataAirconInfo = dataAirconSystem.info;
                        if (dataAirconInfo.cbFWRevMajor == null && dataAirconInfo.cbFWRevMinor == null) {
                            TreeMap<String, DataZone> treeMap4 = dataAirconSystem.zones;
                            Intrinsics.checkNotNull(treeMap4);
                            if (treeMap4.containsKey(aVar.getZoneKey(dataZone3.number)) || dataAirconSystem.info.myZone != null) {
                                Companion companion = Companion;
                                dataZone3.value = Integer.valueOf(companion.getRoundUpAfter15PercentageValue(Integer.valueOf(companion.getZoneOpenAmount(dataAirconSystem5, dataZone3))));
                            }
                        }
                        forest.d("DBGSS processZoneGrouping zone master detected, zone:" + dataZone3.number, new Object[i10]);
                        TreeMap<String, DataZone> treeMap5 = dataAirconSystem2.zones;
                        Intrinsics.checkNotNull(treeMap5);
                        DataZone dataZone5 = treeMap5.get(aVar.getZoneKey(dataZone4.number));
                        if (dataZone5 == null) {
                            dataZone5 = new DataZone();
                            DataZone.update$default(dataZone5, dataZone4, null, null, false, 12, null);
                            TreeMap<String, DataZone> treeMap6 = dataAirconSystem2.zones;
                            Intrinsics.checkNotNull(treeMap6);
                            treeMap6.put(dataZone5.getZoneKey(), dataZone5);
                        }
                        if ((extraPercentRequired(dataAirconSystem5) > 0 || (((num7 = dataZone3.value) != null && ((num8 = dataZone5.value) == null || !Intrinsics.areEqual(num8, num7))) || ((zoneState2 = dataZone3.state) != null && ((zoneState3 = dataZone5.state) == null || zoneState3 != zoneState2)))) && (zoneState = dataZone3.state) != null && (num3 = dataZone3.value) != null) {
                            dataZone5.state = zoneState;
                            dataZone5.value = num3;
                            Float f3 = dataZone3.setTemp;
                            if (f3 != null) {
                                dataZone5.setTemp = f3;
                            } else {
                                dataZone5.setTemp = Float.valueOf(25.0f);
                            }
                            Integer num10 = dataZone3.number;
                            Intrinsics.checkNotNull(num10);
                            if (dataAirconSystem5.isZoneConstant(num10.intValue())) {
                                forest.d("----------Constant: adjustZoneFollowersDueToConstantsAndMyFanIfActive2-------", new Object[i10]);
                                if (adjustZoneFollowersDueToConstantsAndMyFanIfActive1(dataAirconSystem5, dataAirconSystem2)) {
                                    int i11 = (f7112p0 && (num6 = dataAirconSystem5.info.constantZone1) != null && Intrinsics.areEqual(num6, dataZone3.number)) ? 1 : i10;
                                    if (f7113q0 && (num5 = dataAirconSystem5.info.constantZone2) != null && Intrinsics.areEqual(num5, dataZone3.number)) {
                                        i11 = 1;
                                    }
                                    if (f7114r0 && (num4 = dataAirconSystem5.info.constantZone3) != null && Intrinsics.areEqual(num4, dataZone3.number)) {
                                        i11 = 1;
                                    }
                                    if (i11 != 0) {
                                        forest.d("Constant: z" + dataZone5.number + " value should be:" + dataZone5.value, new Object[i10]);
                                        if (dataZone3.state != ZoneState.open) {
                                            forest.d("Constants: master zone state is NOT OPEN", new Object[i10]);
                                            if (!this.zone.contains(dataZone3.getZoneKey())) {
                                                this.zone.add(dataZone3.getZoneKey());
                                                forest.d("Constants: adding master zone forced open to list, masterzoneno:" + dataZone3.getZoneKey(), new Object[i10]);
                                            }
                                        }
                                    }
                                }
                            }
                            forest.d("DBGSS processZoneGrouping: Zone" + dataZone5.number + " following Z" + dataZone3.number + " value= " + dataZone5.value, new Object[i10]);
                            String encodeZone_j = HandlerCB.Companion.getInstance().encodeZone_j(dataAirconSystem5.info.uid, dataZone5);
                            TreeMap<String, DataZone> treeMap7 = airconByUid.zones;
                            Intrinsics.checkNotNull(treeMap7);
                            DataZone dataZone6 = treeMap7.get(dataZone5.getZoneKey());
                            if (dataZone6 != null && (!Intrinsics.areEqual(dataZone5.value, dataZone6.value) || dataZone5.state != dataZone6.state)) {
                                HandlerCan.Companion.getInstance().sendCanMessageToCB(this.context, encodeZone_j);
                                forest.d("DBGSS processZoneGrouping: GroupZone Sending JZ10(4) for UID - " + dataAirconSystem5.info.uid + " - zone " + dataZone5.number + ", " + dataZone5.state + ", " + dataZone5.setTemp + "," + dataZone5.value + "% :" + encodeZone_j, new Object[0]);
                            }
                            z10 = true;
                        }
                    }
                }
                i10 = 0;
            }
            if (!z10) {
                int i12 = 0;
                while (true) {
                    i12++;
                    Integer num11 = dataAirconSystem5.info.constantZone3;
                    if (num11 == null || (num11 != null && num11.intValue() == 0)) {
                        Integer num12 = dataAirconSystem5.info.constantZone2;
                        if (num12 == null || (num12 != null && num12.intValue() == 0)) {
                            Integer num13 = dataAirconSystem5.info.constantZone1;
                            if (num13 == null || (num13 != null && num13.intValue() == 0)) {
                                num = null;
                                num2 = num;
                                if (num2 == null) {
                                    Timber.Forest forest2 = Timber.forest;
                                    forest2.d("Constants: we have master zone forced open in list", new Object[0]);
                                    DataAirconSystem dataAirconSystem6 = new DataAirconSystem();
                                    DataAirconSystem dataAirconSystem7 = new DataAirconSystem();
                                    DataAirconSystem.update$default(dataAirconSystem6, dataAirconSystem2.info.uid, dataAirconSystem2, null, null, false, 16, null);
                                    dataAirconSystem3 = dataAirconSystem5;
                                    DataAirconSystem.update$default(dataAirconSystem7, dataAirconSystem5.info.uid, dataAirconSystem5, null, null, false, 16, null);
                                    TreeMap<String, DataZone> treeMap8 = dataAirconSystem7.zones;
                                    Intrinsics.checkNotNull(treeMap8);
                                    DataZone.a aVar2 = DataZone.Companion;
                                    DataZone dataZone7 = treeMap8.get(aVar2.getZoneKey(num2));
                                    if (dataZone7 != null) {
                                        dataZone7.state = ZoneState.close;
                                        forest2.d("----------Constant: adjustZoneFollowersDueToConstantsAndMyFanIfActive3-------", new Object[0]);
                                        if (!adjustZoneFollowersDueToConstantsAndMyFanIfActive1(dataAirconSystem7, dataAirconSystem6)) {
                                            String encodeZone_j2 = HandlerCB.Companion.getInstance().encodeZone_j(dataAirconSystem3.info.uid, dataZone7);
                                            HandlerCan.Companion.getInstance().sendCanMessageToCB(this.context, encodeZone_j2);
                                            this.zone.remove(aVar2.getZoneKey(num2));
                                            forest2.d("Constants: removed master zone forced open in list, masterZoneNo = " + num2 + " canMsgJZ10 is " + encodeZone_j2, new Object[0]);
                                            z7 = false;
                                        }
                                        if (z7 || i12 > 3) {
                                            break;
                                        } else {
                                            dataAirconSystem5 = dataAirconSystem3;
                                        }
                                    }
                                } else {
                                    dataAirconSystem3 = dataAirconSystem5;
                                }
                                z7 = true;
                                if (z7) {
                                    break;
                                    break;
                                }
                                dataAirconSystem5 = dataAirconSystem3;
                            } else {
                                if (this.zone.contains(DataZone.Companion.getZoneKey(dataAirconSystem5.info.constantZone1))) {
                                    num = dataAirconSystem5.info.constantZone1;
                                }
                                num2 = num;
                                if (num2 == null) {
                                }
                                z7 = true;
                                if (z7) {
                                }
                            }
                        } else {
                            if (this.zone.contains(DataZone.Companion.getZoneKey(dataAirconSystem5.info.constantZone2))) {
                                num = dataAirconSystem5.info.constantZone2;
                            }
                            num2 = num;
                            if (num2 == null) {
                            }
                            z7 = true;
                            if (z7) {
                            }
                        }
                    } else {
                        if (this.zone.contains(DataZone.Companion.getZoneKey(dataAirconSystem5.info.constantZone3))) {
                            num = dataAirconSystem5.info.constantZone3;
                        }
                        num2 = num;
                        if (num2 == null) {
                        }
                        z7 = true;
                        if (z7) {
                        }
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:209:0x04ad  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x03a7  */
    /* renamed from: k */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean adjustZoneFollowersDueToConstantsAndMyFanIfActive1(DataAirconSystem dataAirconSystem, DataAirconSystem dataAirconSystem2) {
        boolean z7;
        DataZone dataZone;
        DataZone dataZone2;
        DataZone dataZone3;
        Boolean bool;
        FanStatus fanStatus;
        int i10;
        Integer num;
        DataAirconSystem dataAirconSystem3 = new DataAirconSystem();
        DataAirconSystem.update$default(dataAirconSystem3, dataAirconSystem.info.uid, dataAirconSystem, null, null, false, 16, null);
        DataAirconSystem.update$default(dataAirconSystem3, dataAirconSystem.info.uid, dataAirconSystem2, null, null, false, 16, null);
        if (dataAirconSystem3.hasAnyConstants()) {
            TreeMap<String, DataZone> treeMap = dataAirconSystem3.zones;
            Intrinsics.checkNotNull(treeMap);
            z7 = false;
            for (DataZone dataZone4 : treeMap.values()) {
                if (dataZone4 != null && (num = dataZone4.following) != null && (num == null || num.intValue() != 0)) {
                    if (Intrinsics.areEqual(dataZone4.following, dataAirconSystem3.info.constantZone1) || Intrinsics.areEqual(dataZone4.following, dataAirconSystem3.info.constantZone2) || Intrinsics.areEqual(dataZone4.following, dataAirconSystem3.info.constantZone3)) {
                        z7 = true;
                    }
                }
            }
        } else {
            z7 = false;
        }
        if (z7) {
            Timber.Forest forest = Timber.forest;
            forest.d("Constant: thereIsAZoneFollowingConstant", new Object[0]);
            f7112p0 = false;
            f7113q0 = false;
            f7114r0 = false;
            TreeMap<String, DataZone> treeMap2 = dataAirconSystem3.zones;
            Intrinsics.checkNotNull(treeMap2);
            DataZone dataZone5 = treeMap2.get(DataZone.Companion.getZoneKey(1));
            if (dataZone5 != null) {
                forest.d("Constant: zone 1 state is " + dataZone5.state + ", value is: " + dataZone5.value, new Object[0]);
            }
            Iterator<String> it = this.zone.iterator();
            while (it.hasNext()) {
                String next = it.next();
                TreeMap<String, DataZone> treeMap3 = dataAirconSystem3.zones;
                Intrinsics.checkNotNull(treeMap3);
                DataZone dataZone6 = treeMap3.get(next);
                if (dataZone6 != null) {
                    dataZone6.state = ZoneState.close;
                }
            }
            int extraPercentRequired = extraPercentRequired(dataAirconSystem3);
            if (extraPercentRequired > 0) {
                f7112p0 = true;
                Timber.Forest forest2 = Timber.forest;
                forest2.d("Constant: extraPercentRequired = " + extraPercentRequired, new Object[0]);
                Integer num2 = dataAirconSystem3.info.constantZone1;
                if (num2 == null || (num2 != null && num2.intValue() == 0)) {
                    dataZone = null;
                    dataZone2 = null;
                    dataZone3 = null;
                } else {
                    TreeMap<String, DataZone> treeMap4 = dataAirconSystem3.zones;
                    Intrinsics.checkNotNull(treeMap4);
                    DataZone dataZone7 = treeMap4.get(DataZone.Companion.getZoneKey(dataAirconSystem3.info.constantZone1));
                    if (dataZone7 != null) {
                        Companion companion = Companion;
                        int zoneOpenAmount = companion.getZoneOpenAmount(dataAirconSystem3, dataZone7);
                        forest2.d("Constant: percentValueConst1 = " + zoneOpenAmount, new Object[0]);
                        int i11 = 100;
                        if (zoneOpenAmount > 95) {
                            f7113q0 = true;
                        } else {
                            ArrayList<String> arrayList = dataZone7.followers;
                            Intrinsics.checkNotNull(arrayList);
                            int size = (100 - zoneOpenAmount) * (arrayList.size() + 1);
                            forest2.d("Constant: max extraPercentFromConst1AndItsFollowers = " + size, new Object[0]);
                            if (size >= extraPercentRequired) {
                                forest2.d("Constant: Dont need const2 or 3", new Object[0]);
                                f7113q0 = false;
                                ArrayList<String> arrayList2 = dataZone7.followers;
                                Intrinsics.checkNotNull(arrayList2);
                                int roundUpPercentageValue = companion.getRoundUpPercentageValue(extraPercentRequired / (arrayList2.size() + 1));
                                forest2.d("Constant: individualValuePercentRequired =" + roundUpPercentageValue, new Object[0]);
                                ArrayList<String> arrayList3 = dataZone7.followers;
                                if (arrayList3 != null) {
                                    Intrinsics.checkNotNull(arrayList3);
                                    Iterator<String> it2 = arrayList3.iterator();
                                    while (it2.hasNext()) {
                                        String next2 = it2.next();
                                        TreeMap<String, DataZone> treeMap5 = dataAirconSystem3.zones;
                                        Intrinsics.checkNotNull(treeMap5);
                                        DataZone dataZone8 = treeMap5.get(next2);
                                        if (dataZone8 != null) {
                                            Integer valueOf = Integer.valueOf(zoneOpenAmount + roundUpPercentageValue);
                                            dataZone8.value = valueOf;
                                            Intrinsics.checkNotNull(valueOf);
                                            if (valueOf.intValue() > i11) {
                                                dataZone8.value = Integer.valueOf(i11);
                                            }
                                            Timber.forest.d("Constant: adjusting constant 1 followers: zone" + dataZone8.number + " -> percentValueConst1 = " + zoneOpenAmount + ", dataZoneConst1.value = " + dataZone7.value + ", follower.value = " + dataZone8.value, new Object[0]);
                                            i11 = 100;
                                        }
                                    }
                                }
                            } else {
                                f7113q0 = true;
                                ArrayList<String> arrayList4 = dataZone7.followers;
                                if (arrayList4 != null) {
                                    Intrinsics.checkNotNull(arrayList4);
                                    Iterator<String> it3 = arrayList4.iterator();
                                    while (it3.hasNext()) {
                                        String next3 = it3.next();
                                        TreeMap<String, DataZone> treeMap6 = dataAirconSystem3.zones;
                                        Intrinsics.checkNotNull(treeMap6);
                                        DataZone dataZone9 = treeMap6.get(next3);
                                        if (dataZone9 != null) {
                                            dataZone9.value = 100;
                                        }
                                    }
                                }
                            }
                        }
                        if (f7113q0) {
                            Timber.Forest forest3 = Timber.forest;
                            forest3.d("Constant: adjusting const2", new Object[0]);
                            Integer num3 = dataAirconSystem3.info.constantZone2;
                            if (num3 == null || (num3 != null && num3.intValue() == 0)) {
                                dataZone2 = null;
                            } else {
                                TreeMap<String, DataZone> treeMap7 = dataAirconSystem3.zones;
                                Intrinsics.checkNotNull(treeMap7);
                                dataZone2 = treeMap7.get(DataZone.Companion.getZoneKey(dataAirconSystem3.info.constantZone2));
                                if (dataZone2 != null && dataZone2.followers != null) {
                                    Companion companion2 = Companion;
                                    int zoneOpenAmount2 = companion2.getZoneOpenAmount(dataAirconSystem3, dataZone2);
                                    forest3.d("Constant: percentValueConst2 = " + zoneOpenAmount2, new Object[0]);
                                    int i12 = extraPercentRequired + 0;
                                    if (zoneOpenAmount2 > 95) {
                                        f7114r0 = true;
                                    } else {
                                        forest3.d("Constant: Dont need const3", new Object[0]);
                                        ArrayList<String> arrayList5 = dataZone2.followers;
                                        Intrinsics.checkNotNull(arrayList5);
                                        int size2 = (100 - zoneOpenAmount2) * (arrayList5.size() + 1);
                                        forest3.d("Constant: max extraPercentFromConst2AndItsFollowers = " + size2, new Object[0]);
                                        if (size2 >= i12) {
                                            forest3.d("Constant: dont need const3", new Object[0]);
                                            f7114r0 = false;
                                            ArrayList<String> arrayList6 = dataZone2.followers;
                                            Intrinsics.checkNotNull(arrayList6);
                                            int roundUpPercentageValue2 = companion2.getRoundUpPercentageValue(i12 / (arrayList6.size() + 1));
                                            ArrayList<String> arrayList7 = dataZone2.followers;
                                            if (arrayList7 != null) {
                                                Intrinsics.checkNotNull(arrayList7);
                                                Iterator<String> it4 = arrayList7.iterator();
                                                while (it4.hasNext()) {
                                                    String next4 = it4.next();
                                                    TreeMap<String, DataZone> treeMap8 = dataAirconSystem3.zones;
                                                    Intrinsics.checkNotNull(treeMap8);
                                                    DataZone dataZone10 = treeMap8.get(next4);
                                                    if (dataZone10 != null) {
                                                        Integer valueOf2 = Integer.valueOf(zoneOpenAmount2 + roundUpPercentageValue2);
                                                        dataZone10.value = valueOf2;
                                                        Intrinsics.checkNotNull(valueOf2);
                                                        if (valueOf2.intValue() > 100) {
                                                            dataZone10.value = 100;
                                                        }
                                                        Timber.forest.d("Constant: adjusting constant 2 followers: zone" + dataZone10.number + " -> percentValueConst2 = " + zoneOpenAmount2 + ", dataZoneConst2.value = " + dataZone2.value + ", follower.value = " + dataZone10.value, new Object[0]);
                                                        i12 = i12;
                                                    }
                                                }
                                            }
                                        } else {
                                            i10 = i12;
                                            f7114r0 = true;
                                            ArrayList<String> arrayList8 = dataZone2.followers;
                                            if (arrayList8 != null) {
                                                Intrinsics.checkNotNull(arrayList8);
                                                Iterator<String> it5 = arrayList8.iterator();
                                                while (it5.hasNext()) {
                                                    String next5 = it5.next();
                                                    TreeMap<String, DataZone> treeMap9 = dataAirconSystem3.zones;
                                                    Intrinsics.checkNotNull(treeMap9);
                                                    DataZone dataZone11 = treeMap9.get(next5);
                                                    if (dataZone11 != null) {
                                                        dataZone11.value = 100;
                                                    }
                                                }
                                            }
                                            extraPercentRequired = i10;
                                        }
                                    }
                                    i10 = i12;
                                    extraPercentRequired = i10;
                                }
                            }
                            if (f7114r0) {
                                Timber.Forest forest4 = Timber.forest;
                                forest4.d("Constant: adjusting const3", new Object[0]);
                                Integer num4 = dataAirconSystem3.info.constantZone3;
                                if (num4 == null || (num4 != null && num4.intValue() == 0)) {
                                    dataZone3 = dataZone7;
                                    dataZone = null;
                                } else {
                                    TreeMap<String, DataZone> treeMap10 = dataAirconSystem3.zones;
                                    Intrinsics.checkNotNull(treeMap10);
                                    DataZone dataZone12 = treeMap10.get(DataZone.Companion.getZoneKey(dataAirconSystem3.info.constantZone3));
                                    if (dataZone12 != null && dataZone12.followers != null) {
                                        Companion companion3 = Companion;
                                        int zoneOpenAmount3 = companion3.getZoneOpenAmount(dataAirconSystem3, dataZone12);
                                        forest4.d("Constant: percentValueConst3 = " + zoneOpenAmount3, new Object[0]);
                                        int i13 = extraPercentRequired - 0;
                                        if (zoneOpenAmount3 > 95) {
                                            forest4.d("Constant: IMPOSSIBLE!!!!!!!!!!!!!!!!!!!", new Object[0]);
                                        } else {
                                            ArrayList<String> arrayList9 = dataZone12.followers;
                                            Intrinsics.checkNotNull(arrayList9);
                                            if ((100 - zoneOpenAmount3) * (arrayList9.size() + 1) >= i13) {
                                                ArrayList<String> arrayList10 = dataZone12.followers;
                                                Intrinsics.checkNotNull(arrayList10);
                                                int roundUpPercentageValue3 = companion3.getRoundUpPercentageValue(i13 / (arrayList10.size() + 1));
                                                ArrayList<String> arrayList11 = dataZone12.followers;
                                                if (arrayList11 != null) {
                                                    Intrinsics.checkNotNull(arrayList11);
                                                    Iterator<String> it6 = arrayList11.iterator();
                                                    while (it6.hasNext()) {
                                                        String next6 = it6.next();
                                                        TreeMap<String, DataZone> treeMap11 = dataAirconSystem3.zones;
                                                        Intrinsics.checkNotNull(treeMap11);
                                                        DataZone dataZone13 = treeMap11.get(next6);
                                                        if (dataZone13 != null) {
                                                            Integer valueOf3 = Integer.valueOf(zoneOpenAmount3 + roundUpPercentageValue3);
                                                            dataZone13.value = valueOf3;
                                                            Intrinsics.checkNotNull(valueOf3);
                                                            if (valueOf3.intValue() > 100) {
                                                                dataZone13.value = 100;
                                                            }
                                                            Timber.forest.d("Constant: adjusting constant 3 followers: zone" + dataZone13.number + " -> percentValueConst3 = " + zoneOpenAmount3 + ", dataZoneConst3.value = " + dataZone12.value + ", follower.value = " + dataZone13.value, new Object[0]);
                                                            roundUpPercentageValue3 = roundUpPercentageValue3;
                                                        }
                                                    }
                                                }
                                            } else {
                                                forest4.d("Constant: IMPOSSIBLE!!!!!!!!!!!!!!!", new Object[0]);
                                            }
                                        }
                                    }
                                    dataZone = dataZone12;
                                    dataZone3 = dataZone7;
                                }
                            }
                        }
                    } else {
                        dataZone3 = dataZone7;
                        dataZone = null;
                        dataZone2 = null;
                    }
                }
                TreeMap<String, DataZone> treeMap12 = dataAirconSystem3.zones;
                Intrinsics.checkNotNull(treeMap12);
                Collection<DataZone> values = treeMap12.values();
                Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                ArrayList<DataZone> arrayList12 = new ArrayList();
                for (DataZone dataZone14 : values) {
                    if (dataZone14 != null) {
                        arrayList12.add(dataZone14);
                    }
                }
                for (DataZone dataZone15 : arrayList12) {
                    Integer num5 = dataZone15.following;
                    if (num5 != null && (num5 == null || num5.intValue() != 0)) {
                        if ((dataZone3 != null && Intrinsics.areEqual(dataZone15.following, dataZone3.number)) || (f7113q0 && dataZone2 != null && Intrinsics.areEqual(dataZone15.following, dataZone2.number)) || (f7114r0 && dataZone != null && Intrinsics.areEqual(dataZone15.following, dataZone.number))) {
                            TreeMap<String, DataZone> treeMap13 = dataAirconSystem2.zones;
                            Intrinsics.checkNotNull(treeMap13);
                            DataZone dataZone16 = treeMap13.get(dataZone15.getZoneKey());
                            if (dataZone16 == null) {
                                dataZone16 = new DataZone(dataZone15.getZoneKey());
                            }
                            Integer num6 = dataZone15.value;
                            dataZone16.value = num6;
                            ZoneState zoneState = ZoneState.open;
                            dataZone16.state = zoneState;
                            Timber.forest.d("Constant: updating dataZoneJson z" + dataZone16.number + " value = " + num6 + ", state is " + zoneState, new Object[0]);
                        }
                    }
                }
                DataAirconInfo dataAirconInfo = dataAirconSystem.info;
                FanStatus fanStatus2 = dataAirconInfo.fan;
                if (fanStatus2 == null || fanStatus2 != FanStatus.autoAA || (bool = dataAirconInfo.aaAutoFanModeEnabled) == null) {
                    return true;
                }
                Intrinsics.checkNotNull(bool);
                if (!bool.booleanValue() || (fanStatus = getFanStatus(dataAirconSystem3)) == null) {
                    return true;
                }
                dataAirconSystem2.info.fan = fanStatus;
                Timber.forest.d("Constant: adjusting fan mode value of myFan", new Object[0]);
                return true;
            }
        }
        Timber.forest.d("Constant: No need to trick CB", new Object[0]);
        return false;
    }

    private final boolean l(DataAirconSystem dataAirconSystem) {
        String str;
        Long l8;
        if (dataAirconSystem == null || (str = dataAirconSystem.info.uid) == null) {
            return true;
        }
        if ((str == null || str.length() == 0) || (l8 = dataAirconSystem.info.expireTime) == null) {
            return true;
        }
        Intrinsics.checkNotNull(l8);
        return l8.longValue() < CommonFuncs.getUptime();
    }

    private final boolean r0(DataAirconSystem dataAirconSystem) {
        TreeMap<String, DataZone> treeMap;
        if (dataAirconSystem == null || (treeMap = dataAirconSystem.zones) == null) {
            return false;
        }
        Intrinsics.checkNotNull(treeMap);
        for (DataZone dataZone : treeMap.values()) {
            if (dataZone != null && dataZone.following != null) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: s0 */
    private final void updateAirconDataSaveToDbAndSend(DataMaster dataMaster, DataAirconSystem dataAirconSystem, DataAirconSystem dataAirconSystem2) {
        DataAirconInfo dataAirconInfo = dataAirconSystem.info;
        Intrinsics.checkNotNull(dataAirconSystem2);
        boolean update$default = DataAirconInfo.update$default(dataAirconInfo, dataAirconSystem2.info, null, false, 4, null);
        TreeMap<String, DataZone> treeMap = dataAirconSystem2.zones;
        Intrinsics.checkNotNull(treeMap);
        for (String str : treeMap.keySet()) {
            TreeMap<String, DataZone> treeMap2 = dataAirconSystem2.zones;
            Intrinsics.checkNotNull(treeMap2);
            if (DataZone.update$default(dataAirconSystem.getOrMakeDataZone(str), treeMap2.get(str), null, null, false, 12, null)) {
                update$default = true;
            }
        }
        if (update$default) {
            AirconDBStore.Companion.getInstance(this.context).updateStore(this.context, dataAirconSystem.info.uid, dataAirconSystem);
            HandlerJson.Companion.getInstance(this.context).processData(dataMaster, "updateAirconDataSaveToDbAndSend");
        }
    }

    /* renamed from: t0 */
    private final void updateAndBroadcastNumberOfSnapshots(DataMaster dataMaster) {
        DataSystem dataSystem = new DataSystem();
        dataSystem.noOfSnapshots = Integer.valueOf(dataMaster.snapshots.size());
        if (update(dataMaster, dataSystem)) {
            HandlerJson.Companion.getInstance(this.context).processData(dataMaster, "updateAndBroadcastNumberOfSnapshots");
        }
    }

    /* renamed from: x0 */
    private final String CBStatusMessage(DataAirconInfo dataAirconInfo, String str) {
        String str2;
        String extractUIDValue = extractUIDValue(str, 4);
        if (extractUIDValue == null) {
            str2 = "Rejected CB status message - invalid UID\n";
        } else {
            str2 = "";
        }
        Integer parseHexToInt = parseHexToInt(str, 11);
        if (parseHexToInt == null) {
            str2 = str2 + "Rejected CB status message - invalid errChar0\n";
        }
        Integer parseHexToInt2 = parseHexToInt(str, 13);
        if (parseHexToInt2 == null) {
            str2 = str2 + "Rejected CB status message - invalid errChar1\n";
        }
        Integer parseHexToInt3 = parseHexToInt(str, 15);
        if (parseHexToInt3 == null) {
            str2 = str2 + "Rejected CB status message - invalid errChar2\n";
        }
        Integer parseHexToInt4 = parseHexToInt(str, 17);
        if (parseHexToInt4 == null) {
            str2 = str2 + "Rejected CB status message - invalid errChar3\n";
        }
        Integer parseHexToInt5 = parseHexToInt(str, 19);
        if (parseHexToInt5 == null) {
            str2 = str2 + "Rejected CB status message - invalid errChar4\n";
        }
        if (!Intrinsics.areEqual(str2, "")) {
            Timber.forest.d(str2, new Object[0]);
            return str2;
        }
        if (parseHexToInt == null || parseHexToInt2 == null || parseHexToInt3 == null || parseHexToInt4 == null || parseHexToInt5 == null) {
            return "Rejected CB status message - Invalid aircon error code\n";
        }
        String str3 = dataAirconInfo.uid;
        if (str3 == null || !Intrinsics.areEqual(str3, extractUIDValue)) {
            dataAirconInfo.uid = extractUIDValue;
        }
        String str4 = Character.toString((char) parseHexToInt.intValue()) + ((char) parseHexToInt2.intValue()) + ((char) parseHexToInt3.intValue()) + ((char) parseHexToInt4.intValue()) + ((char) parseHexToInt5.intValue());
        int length = str4.length() - 1;
        int i10 = 0;
        boolean z7 = false;
        while (i10 <= length) {
            boolean z10 = Intrinsics.compare(str4.charAt(!z7 ? i10 : length), 32) <= 0;
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
        String obj = str4.subSequence(i10, length + 1).toString();
        if (Intrinsics.areEqual(obj, "AA2")) {
            AppFeatures appFeatures = AppFeatures.instance;
            if (appFeatures.z() || appFeatures.v()) {
                return str2;
            }
        }
        String str5 = dataAirconInfo.airconErrorCode;
        if (str5 != null && Intrinsics.areEqual(str5, obj)) {
            return str2;
        }
        if (!AppFeatures.isAnywair()) {
            dataAirconInfo.airconErrorCode = obj;
            return str2;
        }
        if (StringsKt__StringsJVMKt.startsWith(obj, "AA", false, 2, null)) {
            String substring = obj.substring(2);
            Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
            int length2 = substring.length() - 1;
            int i11 = 0;
            boolean z11 = false;
            while (i11 <= length2) {
                boolean z12 = Intrinsics.compare(substring.charAt(!z11 ? i11 : length2), 32) <= 0;
                if (z11) {
                    if (!z12) {
                        break;
                    }
                    length2--;
                } else if (z12) {
                    i11++;
                } else {
                    z11 = true;
                }
            }
            dataAirconInfo.airconErrorCode = "FG" + substring.subSequence(i11, length2 + 1).toString();
            return str2;
        }
        if (!StringsKt__StringsJVMKt.startsWith(obj, "EE", false, 2, null)) {
            if (Intrinsics.areEqual(obj, "")) {
                dataAirconInfo.airconErrorCode = obj;
                return str2;
            }
            dataAirconInfo.airconErrorCode = "ER" + obj;
            return str2;
        }
        String substring2 = obj.substring(2);
        Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String).substring(startIndex)");
        int length3 = substring2.length() - 1;
        int i12 = 0;
        boolean z13 = false;
        while (i12 <= length3) {
            boolean z14 = Intrinsics.compare(substring2.charAt(!z13 ? i12 : length3), 32) <= 0;
            if (z13) {
                if (!z14) {
                    break;
                }
                length3--;
            } else if (z14) {
                i12++;
            } else {
                z13 = true;
            }
        }
        dataAirconInfo.airconErrorCode = "ER" + substring2.subSequence(i12, length3 + 1).toString();
        return str2;
    }

    /* renamed from: y0 */
    private final String MIDMessage(DataSystem dataSystem, String str) {
        String str2;
        String extractUIDValue = extractUIDValue(str, 4);
        if (extractUIDValue == null) {
            str2 = "Rejected CB status message - invalid UID\n";
        } else {
            str2 = "";
        }
        if (Intrinsics.areEqual(str2, "")) {
            dataSystem.mid = extractUIDValue;
        } else {
            Timber.forest.d(str2, new Object[0]);
        }
        return str2;
    }

    @JvmStatic
    public static final void z() {
        Companion.initialize();
    }

    @NotNull
    /* renamed from: A */
    public final ArrayList<String> getCANMessages(@Nullable TreeMap<String, DataAirconSystem> treeMap) {
        ArrayList<String> messageList = new ArrayList<>();
        Timber.Forest forest = Timber.forest;
        Intrinsics.checkNotNull(treeMap);
        forest.d("DBG DB no of aircons while collecting CAN:" + treeMap.size(), new Object[0]);
        Collection<DataAirconSystem> values = treeMap.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        ArrayList<DataAirconSystem> arrayList = new ArrayList();
        for (DataAirconSystem dataAirconSystem : values) {
            if (dataAirconSystem != null) {
                arrayList.add(dataAirconSystem);
            }
        }
        int i10 = 0;
        for (DataAirconSystem dataAirconSystem2 : arrayList) {
            i10++;
            messageList.add(ecodeAirconSource(dataAirconSystem2.info));
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
            for (DataZone dataZone2 : arrayList2) {
                HandlerCB companion = HandlerCB.Companion.getInstance();
                String str = dataAirconSystem2.info.uid;
                Intrinsics.checkNotNull(dataZone2);
                messageList.add(companion.encodeZone_j(str, dataZone2));
            }
        }
        Timber.forest.d("DBG DB no of active aircons while collecting CAN:" + i10, new Object[0]);
        return messageList;
    }

    /* renamed from: B0 */
    public final void setFcmToken(@NotNull Map<String, String> params) {
        Intrinsics.checkNotNullParameter(params, "params");
        String str = params.get(ActivityMain.UID);
        String str2 = params.get("fcmToken");
        String str3 = params.get("notificationVersion");
        if (str == null || str2 == null) {
            return;
        }
        if (str.length() == 0) {
            return;
        }
        if (str2.length() == 0) {
            return;
        }
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(this.context);
            DataSystem dataSystem = new DataSystem();
            dataSystem.update(dataMaster.system);
            HashMap<String, String> hashMap = dataSystem.deviceIdsV2;
            Intrinsics.checkNotNull(hashMap);
            if (hashMap.containsKey(str)) {
                HashMap<String, String> hashMap2 = dataSystem.deviceIdsV2;
                Intrinsics.checkNotNull(hashMap2);
                hashMap2.put(str, str2);
                HashMap<String, String> hashMap3 = dataSystem.deviceNotificationVersion;
                Intrinsics.checkNotNull(hashMap3);
                hashMap3.put(str, str3);
                if (update(dataMaster, dataSystem)) {
                    HandlerJson.Companion.getInstance(this.context).processData(dataMaster, "updateFcmToken");
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    /* renamed from: C0 */
    public final void updateMembershipStatus(@NotNull MembershipStatus state) {
        Intrinsics.checkNotNullParameter(state, "state");
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(this.context);
            DataSystem dataSystem = new DataSystem();
            dataSystem.update(dataMaster.system);
            dataSystem.membershipStatus = state.toString();
            if (update(dataMaster, dataSystem)) {
                HandlerJson.Companion.getInstance(this.context).processData(dataMaster, "updateMembershipStatus");
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    /* renamed from: D */
    public final boolean checkAndRunSceneSchedule(@Nullable DataScene dataScene) {
        HashMap<String, DataAirconSystem> hashMap = dataScene != null ? dataScene.aircons : null;
        if (hashMap == null || hashMap.size() <= 0) {
            return false;
        }
        Set<String> keySet = hashMap.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet, "<get-keys>(...)");
        ArrayList arrayList = new ArrayList();
        for (String str : keySet) {
            if (str != null) {
                arrayList.add(str);
            }
        }
        ArrayList arrayList2 = new ArrayList(arrayList);
        if (arrayList2.size() > 1) {
            Collections.sort(arrayList2);
        }
        DataAirconSystem dataAirconSystem = hashMap.get(arrayList2.get(0));
        if (dataAirconSystem == null) {
            return false;
        }
        Xml2JsonFunctions.Companion.getInstance().sendMessages(this.context, dataAirconSystem);
        Timber.forest.d("checkAndRunSceneSchedule - running scene id:" + dataScene.id, new Object[0]);
        return true;
    }

    @NotNull
    /* renamed from: E */
    public final String generateCanAirconRequest() {
        Timber.forest.d("generateCanAirconRequest JZ16", new Object[0]);
        return "0701000000600000000000000";
    }

    @NotNull
    /* renamed from: F */
    public final String ecodeAirconSource(@NotNull DataAirconInfo airconSource) {
        Intrinsics.checkNotNullParameter(airconSource, "airconSource");
        SystemState systemState = airconSource.state;
        if (systemState != null) {
            int value = systemState.getValue();
            AirconMode airconMode = airconSource.mode;
            if (airconMode != null) {
                int value2 = airconMode.getValue();
                FanStatus fanStatus = airconSource.fan;
                if (fanStatus != null) {
                    int value3 = fanStatus.getValue();
                    Float f3 = airconSource.setTemp;
                    int floatValue = (int) ((f3 != null ? f3.floatValue() : 0.0f) * 2.0f);
                    Integer num = airconSource.myZone;
                    int intValue = num != null ? num.intValue() : 0;
                    DataAirconSystem.FreshAirStatus freshAirStatus = airconSource.freshAirStatus;
                    int i10 = freshAirStatus != null ? freshAirStatus.value : 0;
                    String str = ((("" + (isCBType4Aircon(airconSource.uid) ? "08" : "07")) + "01") + airconSource.uid) + "05";
                    StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                    Locale locale = Locale.ENGLISH;
                    String format = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Integer.valueOf(value)}, 1));
                    Intrinsics.checkNotNullExpressionValue(format, "format(locale, format, *args)");
                    String str2 = str + format;
                    String format2 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Integer.valueOf(value2)}, 1));
                    Intrinsics.checkNotNullExpressionValue(format2, "format(locale, format, *args)");
                    String str3 = str2 + format2;
                    String format3 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Integer.valueOf(value3)}, 1));
                    Intrinsics.checkNotNullExpressionValue(format3, "format(locale, format, *args)");
                    String str4 = str3 + format3;
                    String format4 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Integer.valueOf(floatValue)}, 1));
                    Intrinsics.checkNotNullExpressionValue(format4, "format(locale, format, *args)");
                    String str5 = str4 + format4;
                    String format5 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Integer.valueOf(intValue)}, 1));
                    Intrinsics.checkNotNullExpressionValue(format5, "format(locale, format, *args)");
                    String str6 = str5 + format5;
                    String format6 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Integer.valueOf(i10)}, 1));
                    Intrinsics.checkNotNullExpressionValue(format6, "format(locale, format, *args)");
                    return (str6 + format6) + "00";
                }
            }
        }
        return "";
    }

    /* renamed from: F0 */
    public final boolean update(@NotNull DataMaster masterData, @Nullable DataSystem dataSystem) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (dataSystem == null || !DataSystem.update$default(masterData.system, dataSystem, null, false, 4, null)) {
            return false;
        }
        AirconDBStore.Companion.getInstance(this.context).update(this.context, masterData.system);
        return true;
    }

    @Nullable
    @VisibleForTesting(otherwise = 2)
    public final String G(@NotNull DataAirconInfo aircon, boolean z7) {
        Intrinsics.checkNotNullParameter(aircon, "aircon");
        String str = aircon.uid;
        if (str == null) {
            return null;
        }
        if (z7) {
            return "0801" + str + "0700000000000000";
        }
        return "0701" + str + "0700000000000000";
    }

    /* renamed from: H0 */
    public final void updateStoreAndSendSystemRid(@Nullable Context context, @Nullable String str) {
        if (str == null) {
            return;
        }
        DataSystem dataSystem = new DataSystem();
        dataSystem.rid = str;
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            if (update(dataMaster, dataSystem)) {
                HandlerJson.Companion.getInstance(context).processData(dataMaster, "updateStoreAndSendSystemRid");
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    /* renamed from: I0 */
    public final void lightSystemUpdate(@NotNull DataMaster masterData) {
        String str;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        DataSystem dataSystem = masterData.system;
        Double d3 = dataSystem.latitude;
        if (d3 == null || dataSystem.longitude == null) {
            Timber.forest.d("DBG location null", new Object[0]);
            str = "";
        } else {
            Intrinsics.checkNotNull(d3);
            double doubleValue = d3.doubleValue();
            Double d10 = masterData.system.longitude;
            Intrinsics.checkNotNull(d10);
            this.sunsetCalendar = new SunriseSunsetCalculator(new Location(doubleValue, d10.doubleValue()), TimeZone.getDefault()).getOfficialSunsetCalendarForDate(Calendar.getInstance());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
            Calendar calendar = this.sunsetCalendar;
            Intrinsics.checkNotNull(calendar);
            str = simpleDateFormat.format(calendar.getTime());
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        }
        DataLightsSystem dataLightsSystem = new DataLightsSystem();
        dataLightsSystem.sunsetTime = str;
        Timber.forest.d("DBG location sunset time: " + str, new Object[0]);
        DataLightsSystem dataLightsSystem2 = masterData.myLights.system;
        Intrinsics.checkNotNull(dataLightsSystem2);
        if (DataLightsSystem.update$default(dataLightsSystem2, dataLightsSystem, null, false, 4, null)) {
            HandlerJson.Companion.getInstance(this.context).processData(masterData, "lightSystemUpdate");
        }
    }

    /* renamed from: J0 */
    public final void processTspIp(@Nullable String str) {
        if (str != null) {
            synchronized (MyMasterData.class) {
                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(this.context);
                DataSystem dataSystem = new DataSystem();
                dataSystem.tspIp = str;
                if (DataSystem.update$default(dataMaster.system, dataSystem, null, false, 4, null)) {
                    HandlerJson.Companion.getInstance(this.context).processData(dataMaster, "processTspIp");
                }
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    @NotNull
    /* renamed from: L */
    public final String generateRFAirconRequest() {
        Timber.forest.d("generateRFAirconRequest JZ16", new Object[0]);
        return "0801000000600000000000000";
    }

    @Nullable
    /* renamed from: N */
    public final Address getAddress(@NotNull Context context, @Nullable String postcode, @Nullable String countryName) {
        Intrinsics.checkNotNullParameter(context, "context");
        Geocoder geocoder = new Geocoder(context);
        try {
        } catch (IOException e7) {
            Timber.forest.d("Exception - Can't geocode:" + e7.getMessage(), new Object[0]);
        }
        if (postcode == null || countryName == null) {
            Timber.forest.d("postCode or countryName supplied", new Object[0]);
            return null;
        }
        String str = postcode + ", " + countryName;
        Timber.Forest forest = Timber.forest;
        forest.d("Geocoder input: " + str, new Object[0]);
        List<Address> fromLocationName = geocoder.getFromLocationName(str, 1);
        Address address = fromLocationName != null ? (Address) CollectionsKt___CollectionsKt.first(fromLocationName) : null;
        if (address == null || !Intrinsics.areEqual(address.getCountryName(), countryName) || !address.hasLatitude() || !address.hasLongitude()) {
            forest.d("Can't geocode", new Object[0]);
        } else {
            if (Intrinsics.areEqual(address.getPostalCode(), postcode) || !Intrinsics.areEqual(countryName, "Australia")) {
                forest.d(address.getAddressLine(0), new Object[0]);
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String format = String.format(Locale.US, "Latitude: %f, Longitude: %f", Arrays.copyOf(new Object[]{Double.valueOf(address.getLatitude()), Double.valueOf(address.getLongitude())}, 2));
                Intrinsics.checkNotNullExpressionValue(format, "format(locale, format, *args)");
                forest.d(format, new Object[0]);
                return address;
            }
            String postCodeToState = postCodeToState(postcode, countryName);
            forest.d("Geocoder input2: " + postCodeToState, new Object[0]);
            List<Address> fromLocationName2 = geocoder.getFromLocationName(postCodeToState, 1);
            Address address2 = fromLocationName2 != null ? (Address) CollectionsKt___CollectionsKt.first(fromLocationName2) : null;
            if (address2 != null && address2.getPostalCode() != null) {
                return address2;
            }
        }
        return null;
    }

    @Nullable
    /* renamed from: P */
    public final DataAirconSystem setAirconMode(@Nullable String str, @Nullable DataAirconSystem dataAirconSystem) {
        DataAirconSystem dataAirconSystem2 = new DataAirconSystem();
        if (this.fanSpeedMap.containsKey(str)) {
            DataAirconSystem.update$default(dataAirconSystem2, null, dataAirconSystem, null, null, false, 16, null);
            DataAirconSystem dataAirconSystem3 = this.fanSpeedMap.get(str);
            if (dataAirconSystem3 != null) {
                dataAirconSystem2.updateForAutoModeBackup(dataAirconSystem3);
                AirconMode airconMode = dataAirconSystem3.info.mode;
                AirconMode airconMode2 = AirconMode.myauto;
                if (airconMode != airconMode2) {
                    dataAirconSystem2.info.mode = AirconMode.cool;
                    return dataAirconSystem2;
                }
                DataAirconInfo dataAirconInfo = dataAirconSystem2.info;
                dataAirconInfo.mode = airconMode2;
                dataAirconInfo.myAutoModeCurrentSetMode = AirconMode.cool;
                return dataAirconSystem2;
            }
        }
        return null;
    }

    @NotNull
    /* renamed from: Q */
    public final TreeMap<String, DataAirconSystem> getFanSpeedMap() {
        return this.fanSpeedMap;
    }

    /* JADX WARN: Removed duplicated region for block: B:131:0x003c  */
    @NotNull
    /* renamed from: R */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final FanStatus getFanStatus(@NotNull DataAirconSystem aircon) {
        int i10;
        Intrinsics.checkNotNullParameter(aircon, "aircon");
        FanStatus fanStatus = FanStatus.low;
        Integer num = aircon.info.cbFWRevMajor;
        Intrinsics.checkNotNull(num);
        int i11 = 0;
        if (num.intValue() <= 9) {
            Integer num2 = aircon.info.cbFWRevMajor;
            if (num2 != null && num2.intValue() == 9) {
                Integer num3 = aircon.info.cbFWRevMinor;
                Intrinsics.checkNotNull(num3);
                if (num3.intValue() > 40) {
                }
            }
        } else if (aircon.info.mode == AirconMode.vent) {
            TreeMap<String, DataZone> treeMap = aircon.zones;
            Intrinsics.checkNotNull(treeMap);
            if (treeMap.size() <= 0) {
                return fanStatus;
            }
            TreeMap<String, DataZone> treeMap2 = aircon.zones;
            Intrinsics.checkNotNull(treeMap2);
            int i12 = 0;
            for (String str : treeMap2.keySet()) {
                TreeMap<String, DataZone> treeMap3 = aircon.zones;
                Intrinsics.checkNotNull(treeMap3);
                DataZone dataZone = treeMap3.get(str);
                if (dataZone != null) {
                    i12++;
                    if (dataZone.state == ZoneState.open) {
                        Integer num4 = dataZone.type;
                        if (num4 != null && num4.intValue() == 0) {
                            Integer num5 = dataZone.value;
                            Intrinsics.checkNotNull(num5);
                            i10 = num5.intValue();
                        } else {
                            i10 = 100;
                        }
                        i11 += i10;
                    }
                }
            }
            float f3 = i11 / i12;
            return f3 <= 49.0f ? FanStatus.low : f3 <= 75.0f ? FanStatus.medium : f3 <= 100.0f ? FanStatus.high : fanStatus;
        }
        TreeMap<String, DataZone> treeMap4 = aircon.zones;
        Intrinsics.checkNotNull(treeMap4);
        if (treeMap4.size() <= 0) {
            return fanStatus;
        }
        int[] iArr = new int[10];
        Arrays.fill(iArr, 0);
        TreeMap<String, DataZone> treeMap5 = aircon.zones;
        Intrinsics.checkNotNull(treeMap5);
        int i13 = 0;
        for (String str2 : treeMap5.keySet()) {
            TreeMap<String, DataZone> treeMap6 = aircon.zones;
            Intrinsics.checkNotNull(treeMap6);
            DataZone dataZone2 = treeMap6.get(str2);
            if (dataZone2 != null) {
                int zoneOpenAmount = Companion.getZoneOpenAmount(aircon, dataZone2);
                if ((!Intrinsics.areEqual(dataZone2.number, aircon.info.constantZone1) || !f7109m0) && ((!Intrinsics.areEqual(dataZone2.number, aircon.info.constantZone2) || !f7110n0) && (!Intrinsics.areEqual(dataZone2.number, aircon.info.constantZone3) || !f7111o0))) {
                    Integer num6 = dataZone2.number;
                    Intrinsics.checkNotNull(num6);
                    iArr[num6.intValue() - 1] = zoneOpenAmount;
                }
                i13++;
            }
        }
        if (f7109m0) {
            Timber.forest.d("constant1 forced open", new Object[0]);
        }
        if (f7110n0) {
            Timber.forest.d("constant2 forced open", new Object[0]);
        }
        if (f7111o0) {
            Timber.forest.d("constant3 forced open", new Object[0]);
        }
        Integer num7 = aircon.info.noOfConstantZones;
        Intrinsics.checkNotNull(num7);
        if (num7.intValue() > 0) {
            TreeMap<String, DataZone> treeMap7 = aircon.zones;
            Intrinsics.checkNotNull(treeMap7);
            int i14 = 0;
            for (String str3 : treeMap7.keySet()) {
                TreeMap<String, DataZone> treeMap8 = aircon.zones;
                Intrinsics.checkNotNull(treeMap8);
                DataZone dataZone3 = treeMap8.get(str3);
                if (dataZone3 != null && !V(aircon, dataZone3)) {
                    Integer num8 = dataZone3.number;
                    Intrinsics.checkNotNull(num8);
                    i14 += iArr[num8.intValue() - 1];
                }
            }
            Integer num9 = aircon.info.noOfConstantZones;
            Intrinsics.checkNotNull(num9);
            int intValue = num9.intValue() * 100;
            if (i14 <= intValue) {
                Integer num10 = aircon.info.constantZone1;
                Intrinsics.checkNotNull(num10);
                if (iArr[num10.intValue() - 1] < 100) {
                    Integer num11 = aircon.info.constantZone1;
                    Intrinsics.checkNotNull(num11);
                    int i15 = intValue - (i14 - iArr[num11.intValue() - 1]);
                    if (i15 > 100) {
                        i15 = 100;
                    }
                    Integer num12 = aircon.info.constantZone1;
                    Intrinsics.checkNotNull(num12);
                    if (i15 > iArr[num12.intValue() - 1]) {
                        f7109m0 = true;
                        Integer num13 = aircon.info.constantZone1;
                        Intrinsics.checkNotNull(num13);
                        int i16 = i15 - iArr[num13.intValue() - 1];
                        Integer num14 = aircon.info.constantZone1;
                        Intrinsics.checkNotNull(num14);
                        iArr[num14.intValue() - 1] = i15;
                        i14 += i16;
                    } else {
                        f7109m0 = false;
                    }
                }
                if (i14 <= intValue) {
                    Integer num15 = aircon.info.noOfConstantZones;
                    Intrinsics.checkNotNull(num15);
                    if (num15.intValue() >= 2) {
                        Integer num16 = aircon.info.constantZone2;
                        Intrinsics.checkNotNull(num16);
                        if (iArr[num16.intValue() - 1] < 100) {
                            Integer num17 = aircon.info.constantZone2;
                            Intrinsics.checkNotNull(num17);
                            int i17 = intValue - (i14 - iArr[num17.intValue() - 1]);
                            if (i17 > 100) {
                                i17 = 100;
                            }
                            Integer num18 = aircon.info.constantZone2;
                            Intrinsics.checkNotNull(num18);
                            if (i17 > iArr[num18.intValue() - 1]) {
                                f7110n0 = true;
                                Integer num19 = aircon.info.constantZone2;
                                Intrinsics.checkNotNull(num19);
                                int i18 = i17 - iArr[num19.intValue() - 1];
                                Integer num20 = aircon.info.constantZone2;
                                Intrinsics.checkNotNull(num20);
                                iArr[num20.intValue() - 1] = i17;
                                i14 += i18;
                            } else {
                                f7110n0 = false;
                            }
                        }
                    }
                } else {
                    f7110n0 = false;
                }
                if (i14 <= intValue) {
                    Integer num21 = aircon.info.noOfConstantZones;
                    Intrinsics.checkNotNull(num21);
                    if (num21.intValue() >= 3) {
                        Integer num22 = aircon.info.constantZone3;
                        Intrinsics.checkNotNull(num22);
                        if (iArr[num22.intValue() - 1] < 100) {
                            Integer num23 = aircon.info.constantZone3;
                            Intrinsics.checkNotNull(num23);
                            int i19 = intValue - (i14 - iArr[num23.intValue() - 1]);
                            int i20 = i19 <= 100 ? i19 : 100;
                            Integer num24 = aircon.info.constantZone3;
                            Intrinsics.checkNotNull(num24);
                            if (i20 > iArr[num24.intValue() - 1]) {
                                f7111o0 = true;
                                Integer num25 = aircon.info.constantZone3;
                                Intrinsics.checkNotNull(num25);
                                int i21 = iArr[num25.intValue() - 1];
                                Integer num26 = aircon.info.constantZone3;
                                Intrinsics.checkNotNull(num26);
                                iArr[num26.intValue() - 1] = i20;
                            } else {
                                f7111o0 = false;
                            }
                        }
                    }
                } else {
                    f7111o0 = false;
                }
            } else {
                f7109m0 = false;
                f7110n0 = false;
                f7111o0 = false;
            }
        }
        TreeMap<String, DataZone> treeMap9 = aircon.zones;
        Intrinsics.checkNotNull(treeMap9);
        String str4 = "";
        for (String str5 : treeMap9.keySet()) {
            TreeMap<String, DataZone> treeMap10 = aircon.zones;
            Intrinsics.checkNotNull(treeMap10);
            DataZone dataZone4 = treeMap10.get(str5);
            if (dataZone4 != null) {
                Integer num27 = dataZone4.number;
                Intrinsics.checkNotNull(num27);
                i11 += iArr[num27.intValue() - 1];
                Integer num28 = dataZone4.number;
                Intrinsics.checkNotNull(num28);
                str4 = str4 + num28 + OTAFlashRowModel_v1.Data.DISCRIMINATOR + iArr[num28.intValue() - 1] + " ";
            }
        }
        float f7 = i11 / i13;
        String str6 = str4 + " " + f7 + " ";
        if (f7109m0) {
            str6 = str6 + "c1 ";
        }
        if (f7110n0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str6);
            sb.append("c2 ");
        }
        return f7 <= 49.0f ? FanStatus.low : f7 <= 75.0f ? FanStatus.medium : f7 <= 100.0f ? FanStatus.high : fanStatus;
    }

    /* JADX WARN: Removed duplicated region for block: B:134:0x0188  */
    /* renamed from: T */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateMasterData(@NotNull DataMaster masterData) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        AirconDBStore companion = AirconDBStore.Companion.getInstance(this.context);
        DataSystem.update$default(masterData.system, companion.getDataSystem(this.context), null, false, 4, null);
        String str = masterData.system.name;
        if (str == null || str.length() == 0) {
            ActivityMain.Companion companion2 = ActivityMain.Companion;
            String packageName = companion2.getPackageName();
            if (packageName != null && StringsKt__StringsKt.startsWith$default(packageName, ActivityMain.MYAIR5, false, 2, null)) {
                masterData.system.name = "MyPlace";
            } else {
                String packageName2 = companion2.getPackageName();
                if (packageName2 != null && StringsKt__StringsKt.startsWith$default(packageName2, ActivityMain.EZONE, false, 2, null)) {
                    masterData.system.name = "e-zone";
                } else {
                    String packageName3 = companion2.getPackageName();
                    if (packageName3 != null && StringsKt__StringsKt.startsWith$default(packageName3, ActivityMain.MYAIR4, false, 2, null)) {
                        masterData.system.name = "MyAir4";
                    } else {
                        String packageName4 = companion2.getPackageName();
                        if (packageName4 != null && StringsKt__StringsKt.startsWith$default(packageName4, "zone10", false, 2, null)) {
                            masterData.system.name = "zone10e";
                        } else {
                            masterData.system.name = AirconFunctionsConstants.AIRCON;
                        }
                    }
                }
            }
        }
        masterData.system.myAppRev = Constants.VERSION;
        if (com.air.advantage.libraryairconlightjson.TabletInfo.isMyAir5Tablet()) {
            masterData.system.sysType = DataAirconSystem.SYSTEM_TYPE_MYAIR5;
        } else if (com.air.advantage.libraryairconlightjson.TabletInfo.isMyAir4Tablet()) {
            masterData.system.sysType = "MyAir4";
        } else if (com.air.advantage.libraryairconlightjson.TabletInfo.isEZoneTablet()) {
            if (AppFeatures.isAnywair()) {
                masterData.system.sysType = DataAirconSystem.SYSTEM_TYPE_ANYWAIR;
            } else {
                masterData.system.sysType = "e-zone";
            }
        } else if (com.air.advantage.libraryairconlightjson.TabletInfo.isZone10ETablet()) {
            masterData.system.sysType = "zone10e";
        } else if (com.air.advantage.libraryairconlightjson.TabletInfo.isVamsTablet()) {
            masterData.system.sysType = "vams";
        } else {
            masterData.system.sysType = "";
        }
        DataSystem dataSystem = masterData.system;
        dataSystem.tspErrorCode = ErrorCodes.noError;
        if (dataSystem.hasAircons == null) {
            dataSystem.hasAircons = Boolean.FALSE;
        }
        if (dataSystem.hasLights == null) {
            dataSystem.hasLights = Boolean.FALSE;
        }
        if (dataSystem.hasThings == null) {
            dataSystem.hasThings = Boolean.FALSE;
        }
        if (dataSystem.hasThingsLight == null) {
            dataSystem.hasThingsLight = Boolean.FALSE;
        }
        if (dataSystem.hasThingsBOG == null) {
            dataSystem.hasThingsBOG = Boolean.FALSE;
        }
        if (dataSystem.hasSensors == null) {
            dataSystem.hasSensors = Boolean.FALSE;
        }
        if (dataSystem.hasLocks == null) {
            dataSystem.hasLocks = Boolean.FALSE;
        }
        Boolean bool = Boolean.FALSE;
        dataSystem.drawThingsTab = bool;
        dataSystem.drawLightsTab = bool;
        HashMap<String, DataModuleInfoSource> hashMap = dataSystem.versions;
        if (hashMap == null) {
            dataSystem.versions = new HashMap<>();
        } else {
            Intrinsics.checkNotNull(hashMap);
            hashMap.clear();
        }
        DataSystem dataSystem2 = masterData.system;
        if (dataSystem2.lockDoorReminderWaitTime == null) {
            dataSystem2.lockDoorReminderWaitTime = 2;
        }
        DataSystem dataSystem3 = masterData.system;
        if (dataSystem3.garageDoorReminderWaitTime == null) {
            dataSystem3.garageDoorReminderWaitTime = 2;
        }
        DataSystem dataSystem4 = masterData.system;
        if (dataSystem4.garageDoorSecurityPinEnabled == null) {
            dataSystem4.garageDoorSecurityPinEnabled = Boolean.TRUE;
        }
        if (dataSystem4.remoteAccessPairingEnabled == null) {
            dataSystem4.remoteAccessPairingEnabled = Boolean.TRUE;
        }
        String str2 = dataSystem4.backupId;
        if (str2 == null) {
            masterData.system.backupId = UUID.randomUUID().toString();
        } else if (str2 == null || str2.length() == 0) {
        }
        masterData.system.tspModel = Build.MODEL;
        CommonFuncs commonFuncs = new CommonFuncs();
        if (AppFeatures.isAnywair()) {
            masterData.system.aaServiceRev = commonFuncs.getPackageVersionName(this.context, "com.dair.fgassist");
        } else {
            masterData.system.aaServiceRev = commonFuncs.getPackageVersionName(this.context, "com.air.advantage.aaservice2");
            String str3 = masterData.system.aaServiceRev;
            if (str3 == null || str3.length() == 0) {
                masterData.system.aaServiceRev = commonFuncs.getPackageVersionName(this.context, "com.air.advantage.aaservice");
            }
        }
        masterData.system.tspIp = AppFeatures.instance.getDeviceIp(true);
        ArrayList<String> arrayList = masterData.system.deviceIds;
        if (arrayList != null) {
            Intrinsics.checkNotNull(arrayList);
            if (arrayList.size() > 0) {
                DataSystem dataSystem5 = masterData.system;
                if (dataSystem5.deviceIdsV2 == null) {
                    dataSystem5.deviceIdsV2 = new HashMap<>();
                    ArrayList<String> arrayList2 = masterData.system.deviceIds;
                    Intrinsics.checkNotNull(arrayList2);
                    Iterator<String> it = arrayList2.iterator();
                    while (it.hasNext()) {
                        String next = it.next();
                        HashMap<String, String> hashMap2 = masterData.system.deviceIdsV2;
                        Intrinsics.checkNotNull(hashMap2);
                        Intrinsics.checkNotNull(next);
                        hashMap2.put(next, "");
                    }
                }
            }
        }
        ArrayList<String> arrayList3 = masterData.system.deviceIds;
        if (arrayList3 != null) {
            Intrinsics.checkNotNull(arrayList3);
            if (arrayList3.size() > 0) {
                DataSystem dataSystem6 = masterData.system;
                if (dataSystem6.deviceNames == null) {
                    dataSystem6.deviceNames = new HashMap<>();
                    ArrayList<String> arrayList4 = masterData.system.deviceIds;
                    Intrinsics.checkNotNull(arrayList4);
                    Iterator<String> it2 = arrayList4.iterator();
                    while (it2.hasNext()) {
                        String next2 = it2.next();
                        HashMap<String, String> hashMap3 = masterData.system.deviceNames;
                        Intrinsics.checkNotNull(hashMap3);
                        Intrinsics.checkNotNull(next2);
                        hashMap3.put(next2, "");
                    }
                }
            }
        }
        HashMap<String, String> hashMap4 = masterData.system.deviceIdsV2;
        if (hashMap4 != null) {
            Intrinsics.checkNotNull(hashMap4);
            if (hashMap4.size() > 0) {
                DataSystem dataSystem7 = masterData.system;
                if (dataSystem7.deviceNotificationVersion == null) {
                    dataSystem7.deviceNotificationVersion = new HashMap<>();
                    HashMap<String, String> hashMap5 = masterData.system.deviceIdsV2;
                    Intrinsics.checkNotNull(hashMap5);
                    for (String str4 : hashMap5.keySet()) {
                        HashMap<String, String> hashMap6 = masterData.system.deviceNotificationVersion;
                        Intrinsics.checkNotNull(hashMap6);
                        Intrinsics.checkNotNull(str4);
                        hashMap6.put(str4, NotificationVersion.V2.getString());
                    }
                }
            }
        }
        masterData.system.mid = null;
        E0(masterData);
        companion.updateSnapShots(this.context, masterData);
        lightSystemUpdate(masterData);
    }

    /* renamed from: U */
    public final boolean isCBType4Aircon(@Nullable String str) {
        boolean z7;
        Integer num;
        synchronized (MyMasterData.class) {
            DataAirconSystem airconByUid = MyMasterData.Companion.getDataMaster(MyApp.appContextProvider.appContext()).getAirconByUid(str);
            z7 = false;
            if (airconByUid != null && (num = airconByUid.info.cbType) != null && num != null) {
                if (num.intValue() == 4) {
                    z7 = true;
                }
            }
        }
        return z7;
    }

    /* JADX WARN: Removed duplicated region for block: B:85:0x0031 A[Catch: Exception -> 0x0187, TryCatch #0 {Exception -> 0x0187, blocks: (B:3:0x0006, B:5:0x0015, B:8:0x0085, B:10:0x0090, B:11:0x00a4, B:13:0x00aa, B:16:0x00bd, B:20:0x00c4, B:23:0x00ca, B:33:0x00de, B:34:0x00eb, B:36:0x00f1, B:39:0x0104, B:46:0x0111, B:43:0x010b, B:55:0x012c, B:57:0x0139, B:58:0x0147, B:60:0x014d, B:63:0x0160, B:66:0x0166, B:72:0x0175, B:81:0x001c, B:83:0x0022, B:85:0x0031, B:87:0x0039, B:89:0x0044, B:90:0x0051, B:92:0x0057, B:95:0x006a, B:98:0x0070, B:101:0x0075, B:104:0x007c), top: B:2:0x0006 }] */
    /* renamed from: W */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int extraPercentRequired(@NotNull DataAirconSystem aircon) {
        Integer num;
        Integer num2;
        Integer num3;
        Intrinsics.checkNotNullParameter(aircon, "aircon");
        try {
            Integer num4 = aircon.info.cbFWRevMajor;
            Intrinsics.checkNotNull(num4);
            if (num4.intValue() <= 9) {
                Integer num5 = aircon.info.cbFWRevMajor;
                if (num5 != null && num5.intValue() == 9) {
                    Integer num6 = aircon.info.cbFWRevMinor;
                    Intrinsics.checkNotNull(num6);
                    if (num6.intValue() > 40) {
                    }
                }
            } else if (aircon.info.mode == AirconMode.vent) {
                TreeMap<String, DataZone> treeMap = aircon.zones;
                Intrinsics.checkNotNull(treeMap);
                if (treeMap.size() <= 0) {
                    return 0;
                }
                TreeMap<String, DataZone> treeMap2 = aircon.zones;
                Intrinsics.checkNotNull(treeMap2);
                for (String str : treeMap2.keySet()) {
                    TreeMap<String, DataZone> treeMap3 = aircon.zones;
                    Intrinsics.checkNotNull(treeMap3);
                    DataZone dataZone = treeMap3.get(str);
                    if (dataZone != null && dataZone.state == ZoneState.open && (num = dataZone.type) != null && num.intValue() == 0) {
                        Integer num7 = dataZone.value;
                        Intrinsics.checkNotNull(num7);
                        num7.intValue();
                    }
                }
                return 0;
            }
            TreeMap<String, DataZone> treeMap4 = aircon.zones;
            Intrinsics.checkNotNull(treeMap4);
            if (treeMap4.size() <= 0) {
                return 0;
            }
            int[] iArr = new int[10];
            Arrays.fill(iArr, 0);
            TreeMap<String, DataZone> treeMap5 = aircon.zones;
            Intrinsics.checkNotNull(treeMap5);
            for (String str2 : treeMap5.keySet()) {
                TreeMap<String, DataZone> treeMap6 = aircon.zones;
                Intrinsics.checkNotNull(treeMap6);
                DataZone dataZone2 = treeMap6.get(str2);
                if (dataZone2 != null && ((num3 = dataZone2.following) == null || (num3 != null && num3.intValue() == 0))) {
                    int zoneOpenAmount = Companion.getZoneOpenAmount(aircon, dataZone2);
                    Intrinsics.checkNotNull(dataZone2.number);
                    iArr[r3.intValue() - 1] = zoneOpenAmount;
                }
            }
            TreeMap<String, DataZone> treeMap7 = aircon.zones;
            Intrinsics.checkNotNull(treeMap7);
            for (String str3 : treeMap7.keySet()) {
                TreeMap<String, DataZone> treeMap8 = aircon.zones;
                Intrinsics.checkNotNull(treeMap8);
                DataZone dataZone3 = treeMap8.get(str3);
                if (dataZone3 != null && (num2 = dataZone3.following) != null && (num2 == null || num2.intValue() != 0)) {
                    Intrinsics.checkNotNull(dataZone3.following);
                    int i10 = iArr[r4.intValue() - 1];
                    Intrinsics.checkNotNull(dataZone3.number);
                    iArr[r3.intValue() - 1] = i10;
                }
            }
            Integer num8 = aircon.info.noOfConstantZones;
            Intrinsics.checkNotNull(num8);
            if (num8.intValue() <= 0) {
                return 0;
            }
            TreeMap<String, DataZone> treeMap9 = aircon.zones;
            Intrinsics.checkNotNull(treeMap9);
            int i11 = 0;
            for (String str4 : treeMap9.keySet()) {
                TreeMap<String, DataZone> treeMap10 = aircon.zones;
                Intrinsics.checkNotNull(treeMap10);
                DataZone dataZone4 = treeMap10.get(str4);
                if (dataZone4 != null && !V(aircon, dataZone4)) {
                    Intrinsics.checkNotNull(dataZone4.number);
                    i11 += iArr[r4.intValue() - 1];
                }
            }
            Integer num9 = aircon.info.noOfConstantZones;
            Intrinsics.checkNotNull(num9);
            int intValue = num9.intValue() * 100;
            if (i11 <= intValue) {
                return intValue - i11;
            }
            return 0;
        } catch (Exception e7) {
            AppFeatures.instance.logCriticalException(e7, "Error in needToOverwriteConstants calculation");
            return 0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:116:0x09d8 A[Catch: all -> 0x0a30, TryCatch #0 {all -> 0x0a30, blocks: (B:116:0x09d8, B:118:0x09e5, B:120:0x0a03, B:121:0x0a12, B:127:0x0a20, B:132:0x09eb, B:141:0x0311, B:143:0x0318, B:145:0x0326, B:147:0x0332, B:148:0x0336, B:150:0x033a, B:151:0x033e, B:153:0x0342, B:154:0x0346, B:156:0x034a, B:157:0x034e, B:161:0x0376, B:164:0x0382, B:167:0x038e, B:169:0x0394, B:170:0x0387, B:173:0x039b, B:174:0x037b, B:176:0x0355, B:178:0x035b, B:180:0x0361, B:181:0x0368, B:182:0x039f, B:185:0x03af, B:190:0x03bb, B:193:0x03c8, B:194:0x03d3, B:198:0x0428, B:201:0x0432, B:205:0x0439, B:207:0x0440, B:208:0x0464, B:211:0x046c, B:213:0x0493, B:214:0x04b8, B:217:0x03cf, B:218:0x04c8, B:222:0x04ea, B:229:0x0508, B:231:0x0510, B:233:0x0514, B:236:0x0526, B:238:0x0533, B:241:0x0520, B:253:0x05d2, B:255:0x05d8, B:257:0x05e6, B:258:0x061e, B:262:0x063b, B:264:0x0645, B:267:0x0653, B:271:0x065e, B:273:0x0664, B:275:0x066a, B:276:0x0671, B:278:0x06a0, B:282:0x06bd, B:284:0x06c7, B:286:0x06d8, B:288:0x06e0, B:292:0x06ef, B:295:0x070d, B:297:0x0719, B:300:0x0727, B:302:0x072d, B:304:0x073e, B:306:0x0747, B:308:0x0797, B:311:0x07a3, B:314:0x07af, B:317:0x07bb, B:319:0x07c5, B:321:0x07cb, B:324:0x07f2, B:325:0x0800, B:327:0x080c, B:336:0x0927, B:337:0x093e, B:338:0x0917, B:340:0x091e, B:341:0x0903, B:343:0x090b, B:344:0x08cc, B:346:0x08d3, B:349:0x08de, B:351:0x08e5, B:353:0x08f2, B:354:0x089c, B:356:0x08a3, B:357:0x0968, B:359:0x0974), top: B:140:0x0311 }] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0a18  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0a1d A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0a20 A[Catch: all -> 0x0a30, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x0a30, blocks: (B:116:0x09d8, B:118:0x09e5, B:120:0x0a03, B:121:0x0a12, B:127:0x0a20, B:132:0x09eb, B:141:0x0311, B:143:0x0318, B:145:0x0326, B:147:0x0332, B:148:0x0336, B:150:0x033a, B:151:0x033e, B:153:0x0342, B:154:0x0346, B:156:0x034a, B:157:0x034e, B:161:0x0376, B:164:0x0382, B:167:0x038e, B:169:0x0394, B:170:0x0387, B:173:0x039b, B:174:0x037b, B:176:0x0355, B:178:0x035b, B:180:0x0361, B:181:0x0368, B:182:0x039f, B:185:0x03af, B:190:0x03bb, B:193:0x03c8, B:194:0x03d3, B:198:0x0428, B:201:0x0432, B:205:0x0439, B:207:0x0440, B:208:0x0464, B:211:0x046c, B:213:0x0493, B:214:0x04b8, B:217:0x03cf, B:218:0x04c8, B:222:0x04ea, B:229:0x0508, B:231:0x0510, B:233:0x0514, B:236:0x0526, B:238:0x0533, B:241:0x0520, B:253:0x05d2, B:255:0x05d8, B:257:0x05e6, B:258:0x061e, B:262:0x063b, B:264:0x0645, B:267:0x0653, B:271:0x065e, B:273:0x0664, B:275:0x066a, B:276:0x0671, B:278:0x06a0, B:282:0x06bd, B:284:0x06c7, B:286:0x06d8, B:288:0x06e0, B:292:0x06ef, B:295:0x070d, B:297:0x0719, B:300:0x0727, B:302:0x072d, B:304:0x073e, B:306:0x0747, B:308:0x0797, B:311:0x07a3, B:314:0x07af, B:317:0x07bb, B:319:0x07c5, B:321:0x07cb, B:324:0x07f2, B:325:0x0800, B:327:0x080c, B:336:0x0927, B:337:0x093e, B:338:0x0917, B:340:0x091e, B:341:0x0903, B:343:0x090b, B:344:0x08cc, B:346:0x08d3, B:349:0x08de, B:351:0x08e5, B:353:0x08f2, B:354:0x089c, B:356:0x08a3, B:357:0x0968, B:359:0x0974), top: B:140:0x0311 }] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0a1a  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0a11  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x046a  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x04b8 A[Catch: all -> 0x0a30, TryCatch #0 {all -> 0x0a30, blocks: (B:116:0x09d8, B:118:0x09e5, B:120:0x0a03, B:121:0x0a12, B:127:0x0a20, B:132:0x09eb, B:141:0x0311, B:143:0x0318, B:145:0x0326, B:147:0x0332, B:148:0x0336, B:150:0x033a, B:151:0x033e, B:153:0x0342, B:154:0x0346, B:156:0x034a, B:157:0x034e, B:161:0x0376, B:164:0x0382, B:167:0x038e, B:169:0x0394, B:170:0x0387, B:173:0x039b, B:174:0x037b, B:176:0x0355, B:178:0x035b, B:180:0x0361, B:181:0x0368, B:182:0x039f, B:185:0x03af, B:190:0x03bb, B:193:0x03c8, B:194:0x03d3, B:198:0x0428, B:201:0x0432, B:205:0x0439, B:207:0x0440, B:208:0x0464, B:211:0x046c, B:213:0x0493, B:214:0x04b8, B:217:0x03cf, B:218:0x04c8, B:222:0x04ea, B:229:0x0508, B:231:0x0510, B:233:0x0514, B:236:0x0526, B:238:0x0533, B:241:0x0520, B:253:0x05d2, B:255:0x05d8, B:257:0x05e6, B:258:0x061e, B:262:0x063b, B:264:0x0645, B:267:0x0653, B:271:0x065e, B:273:0x0664, B:275:0x066a, B:276:0x0671, B:278:0x06a0, B:282:0x06bd, B:284:0x06c7, B:286:0x06d8, B:288:0x06e0, B:292:0x06ef, B:295:0x070d, B:297:0x0719, B:300:0x0727, B:302:0x072d, B:304:0x073e, B:306:0x0747, B:308:0x0797, B:311:0x07a3, B:314:0x07af, B:317:0x07bb, B:319:0x07c5, B:321:0x07cb, B:324:0x07f2, B:325:0x0800, B:327:0x080c, B:336:0x0927, B:337:0x093e, B:338:0x0917, B:340:0x091e, B:341:0x0903, B:343:0x090b, B:344:0x08cc, B:346:0x08d3, B:349:0x08de, B:351:0x08e5, B:353:0x08f2, B:354:0x089c, B:356:0x08a3, B:357:0x0968, B:359:0x0974), top: B:140:0x0311 }] */
    /* JADX WARN: Removed duplicated region for block: B:215:0x0431  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x03cf A[Catch: all -> 0x0a30, TryCatch #0 {all -> 0x0a30, blocks: (B:116:0x09d8, B:118:0x09e5, B:120:0x0a03, B:121:0x0a12, B:127:0x0a20, B:132:0x09eb, B:141:0x0311, B:143:0x0318, B:145:0x0326, B:147:0x0332, B:148:0x0336, B:150:0x033a, B:151:0x033e, B:153:0x0342, B:154:0x0346, B:156:0x034a, B:157:0x034e, B:161:0x0376, B:164:0x0382, B:167:0x038e, B:169:0x0394, B:170:0x0387, B:173:0x039b, B:174:0x037b, B:176:0x0355, B:178:0x035b, B:180:0x0361, B:181:0x0368, B:182:0x039f, B:185:0x03af, B:190:0x03bb, B:193:0x03c8, B:194:0x03d3, B:198:0x0428, B:201:0x0432, B:205:0x0439, B:207:0x0440, B:208:0x0464, B:211:0x046c, B:213:0x0493, B:214:0x04b8, B:217:0x03cf, B:218:0x04c8, B:222:0x04ea, B:229:0x0508, B:231:0x0510, B:233:0x0514, B:236:0x0526, B:238:0x0533, B:241:0x0520, B:253:0x05d2, B:255:0x05d8, B:257:0x05e6, B:258:0x061e, B:262:0x063b, B:264:0x0645, B:267:0x0653, B:271:0x065e, B:273:0x0664, B:275:0x066a, B:276:0x0671, B:278:0x06a0, B:282:0x06bd, B:284:0x06c7, B:286:0x06d8, B:288:0x06e0, B:292:0x06ef, B:295:0x070d, B:297:0x0719, B:300:0x0727, B:302:0x072d, B:304:0x073e, B:306:0x0747, B:308:0x0797, B:311:0x07a3, B:314:0x07af, B:317:0x07bb, B:319:0x07c5, B:321:0x07cb, B:324:0x07f2, B:325:0x0800, B:327:0x080c, B:336:0x0927, B:337:0x093e, B:338:0x0917, B:340:0x091e, B:341:0x0903, B:343:0x090b, B:344:0x08cc, B:346:0x08d3, B:349:0x08de, B:351:0x08e5, B:353:0x08f2, B:354:0x089c, B:356:0x08a3, B:357:0x0968, B:359:0x0974), top: B:140:0x0311 }] */
    /* renamed from: Y */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean parseMessage(@NotNull String message) {
        HandlerAircon handlerAircon;
        String str;
        DataAirconSystem dataAirconSystem;
        DataAirconInfo dataAirconInfo;
        DataAirconSystem dataAirconSystem2;
        DataMaster dataMaster;
        String str2;
        String str3;
        boolean z7;
        boolean z10;
        DataAirconSystem dataAirconSystem3;
        TreeMap<String, DataZone> treeMap;
        Integer num;
        Timber.Forest forest;
        Integer num2;
        String G;
        boolean z11;
        boolean u02;
        Integer num3;
        String str4;
        Intrinsics.checkNotNullParameter(message, "message");
        if (!compareFRLValue(message, 0, "07") && !compareFRLValue(message, 0, "08")) {
            Timber.forest.d("Rejected can message - incorrect system type " + message, new Object[0]);
            return false;
        }
        if (!compareFRLValue(message, 2, "03") && !compareFRLValue(message, 2, "04")) {
            Timber.forest.d("Rejected can message - incorrect device type " + message, new Object[0]);
            return false;
        }
        if (compareFRLValue(message, 4, "00") && compareFRLValue(message, 6, "00") && compareFRLValue(message, 7, "00")) {
            Timber.forest.d("Rejected can message - UID is zero! - " + message, new Object[0]);
            return false;
        }
        String uid = extractUIDValue(message, 4);
        if (uid == null) {
            Timber.forest.d("Rejected CB status message - invalid UID", new Object[0]);
            return false;
        }
        if (compareFRLValue(message, 9, "08")) {
            Timber.forest.d("Error message received", new Object[0]);
        }
        boolean isRFSystem = compareFRLValue(message, 0, "08");
        synchronized (MyMasterData.class) {
            try {
                DataMaster dataMaster2 = MyMasterData.Companion.getDataMaster(this.context);
                DataAirconInfo dataAirconInfo2 = new DataAirconInfo();
                dataMaster2.oneAirconOnly = !com.air.advantage.libraryairconlightjson.TabletInfo.isMyAir5Tablet();
                if (compareFRLValue(message, 9, "0a")) {
                    DataSystem dataSystem = new DataSystem();
                    if (Intrinsics.areEqual(MIDMessage(dataSystem, message), "")) {
                        dataMaster2.system.mid = dataSystem.mid;
                    }
                }
                dataMaster2.multipleAirconDetectedOnOneAirconOnlySystem = false;
                if (dataMaster2.oneAirconOnly && (str4 = dataMaster2.system.mid) != null && !Intrinsics.areEqual(str4, uid)) {
                    dataMaster2.multipleAirconDetectedOnOneAirconOnlySystem = true;
                    Timber.forest.d("Warning extra cb7 detected but configured to only show one", new Object[0]);
                    return true;
                }
                DataAirconSystem airconByUid = dataMaster2.getAirconByUid(uid);
                if (airconByUid == null && compareFRLValue(message, 9, "06") && Intrinsics.areEqual(updateVersionInfo(dataAirconInfo2, message), "")) {
                    Integer num4 = dataAirconInfo2.cbType;
                    if (num4 != null && num4.intValue() == 5) {
                        airconByUid = new DataAirconSystem(uid);
                        UID.set(uid);
                        isRFSystem = true;
                    } else {
                        Timber.forest.d("DBG DB dataAircon mem object is created by JZ17, for uid " + uid, new Object[0]);
                        airconByUid = AirconDBStore.Companion.getInstance(this.context).getAircon(this.context, uid);
                        dataMaster2.aircons.put(uid, airconByUid);
                        dataMaster2.sortAircons(false);
                        setAllZoneSensorData(this.context);
                        Boolean bool = airconByUid.info.quietNightModeIsRunning;
                        if (bool != null) {
                            Intrinsics.checkNotNull(bool);
                            if (bool.booleanValue()) {
                                this.fanSpeedMap.put(uid, ((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).getAircon(this.context, uid));
                            }
                        }
                        Boolean bool2 = airconByUid.info.myFanSpeedIsRunning;
                        if (bool2 != null) {
                            Intrinsics.checkNotNull(bool2);
                            if (bool2.booleanValue()) {
                                airconByUid.info.fan = FanStatus.autoAA;
                            }
                        }
                    }
                }
                if (airconByUid == null && !isRFSystem) {
                    Timber.forest.d("DBG DB dataAircon mem object is null, for uid " + uid, new Object[0]);
                    return true;
                }
                Xml2JsonFunctions.Companion.getIsProcessing().set(false);
                if (airconByUid != null) {
                    if (isCBType4Aircon(airconByUid.info.uid)) {
                        airconByUid.info.expireTime = Long.valueOf(CommonFuncs.getUptime() + 260);
                    } else {
                        airconByUid.info.expireTime = Long.valueOf(CommonFuncs.getUptime() + 80);
                    }
                }
                try {
                    if (compareFRLValue(message, 9, "05")) {
                        str3 = updateAirconState(dataAirconInfo2, message);
                        if (!Intrinsics.areEqual(str3, "")) {
                            Timber.forest.d("Invalid CB JZ15 msg - " + str3, new Object[0]);
                            return false;
                        }
                        if (dataAirconInfo2.state == SystemState.on) {
                            dataAirconInfo2.countDownToOn = 0;
                        }
                        if (dataAirconInfo2.state == SystemState.off) {
                            dataAirconInfo2.countDownToOff = 0;
                        }
                        if (airconByUid != null) {
                            DataAirconInfo dataAirconInfo3 = airconByUid.info;
                            FanStatus fanStatus = dataAirconInfo3.fan;
                            FanStatus fanStatus2 = FanStatus.autoAA;
                            if (fanStatus == fanStatus2) {
                                dataAirconInfo2.fan = fanStatus2;
                            }
                            Boolean bool3 = dataAirconInfo3.myAutoModeIsRunning;
                            if (bool3 != null) {
                                Intrinsics.checkNotNull(bool3);
                                if (bool3.booleanValue()) {
                                    AirconMode airconMode = dataAirconInfo2.mode;
                                    if (airconMode != null) {
                                        dataAirconInfo2.myAutoModeCurrentSetMode = airconMode;
                                    }
                                    dataAirconInfo2.mode = AirconMode.myauto;
                                }
                            }
                        }
                        if (airconByUid != null && (num3 = airconByUid.info.cbType) != null && num3 != null && num3.intValue() == 4) {
                            HandlerJson.Companion.getIsProcessing().set(false);
                        }
                        DataAirconSystem dataAirconSystem4 = airconByUid;
                        Timber.forest.v(f7106i0, "Valid CB JZ15 message. UID - " + dataAirconInfo2.uid + " systemOn - " + dataAirconInfo2.state + " systemMode - " + dataAirconInfo2.mode + " systemFAN - " + dataAirconInfo2.fan + " setTemp - " + dataAirconInfo2.setTemp + " unitControlTempSet - " + dataAirconInfo2.myZone + " freshAirStatus - " + dataAirconInfo2.freshAirStatus + " rfSysID - " + dataAirconInfo2.rfSysID);
                        handlerAircon = this;
                        str = message;
                        dataAirconSystem2 = dataAirconSystem4;
                        dataMaster = dataMaster2;
                        dataAirconInfo = dataAirconInfo2;
                    } else {
                        DataAirconSystem dataAirconSystem5 = airconByUid;
                        handlerAircon = this;
                        try {
                            if (handlerAircon.compareFRLValue(message, 9, "06")) {
                                dataAirconInfo = dataAirconInfo2;
                                str3 = handlerAircon.updateVersionInfo(dataAirconInfo, message);
                                if (!Intrinsics.areEqual(str3, "")) {
                                    Timber.forest.d("Invalid CB JZ17 msg - " + str3, new Object[0]);
                                    return false;
                                }
                                DataSystem dataSystem2 = dataMaster2.system;
                                Boolean bool4 = Boolean.TRUE;
                                dataSystem2.hasAircons = bool4;
                                if (dataSystem2.hasLights == null) {
                                    dataSystem2.hasLights = Boolean.FALSE;
                                }
                                if (dataSystem2.hasThings == null) {
                                    dataSystem2.hasThings = Boolean.FALSE;
                                }
                                if (dataSystem2.hasThingsLight == null) {
                                    dataSystem2.hasThingsLight = Boolean.FALSE;
                                }
                                if (dataSystem2.hasThingsBOG == null) {
                                    dataSystem2.hasThingsBOG = Boolean.FALSE;
                                }
                                Integer num5 = dataAirconInfo.cbType;
                                if (num5 != null) {
                                    if (num5 != null && num5.intValue() == 3) {
                                        if (com.air.advantage.libraryairconlightjson.TabletInfo.isMyAir5Tablet()) {
                                            dataMaster2.system.hasAircons = Boolean.FALSE;
                                        } else {
                                            DataSystem dataSystem3 = dataMaster2.system;
                                            dataSystem3.hasAircons = bool4;
                                            Boolean bool5 = Boolean.FALSE;
                                            dataSystem3.hasThings = bool5;
                                            dataSystem3.hasLights = bool5;
                                            dataSystem3.hasThingsLight = bool5;
                                            dataSystem3.hasThingsBOG = bool5;
                                        }
                                    }
                                    Integer num6 = dataAirconInfo.cbType;
                                    if (num6 != null && num6.intValue() == 4) {
                                        dataMaster2.system.splitTypeSystem = bool4;
                                    } else {
                                        Integer num7 = dataAirconInfo.cbType;
                                        if (num7 != null && num7.intValue() == 5) {
                                            dataMaster2.system.splitTypeSystem = bool4;
                                        }
                                        if (!dataMaster2.containsSplitAircon()) {
                                            dataMaster2.system.splitTypeSystem = Boolean.FALSE;
                                        }
                                    }
                                    if (z7) {
                                        z11 = false;
                                    } else {
                                        Boolean bool6 = dataMaster.system.hasAircons;
                                        Intrinsics.checkNotNull(bool6);
                                        if (bool6.booleanValue()) {
                                            u02 = handlerAircon.u0(dataAirconSystem2, dataAirconInfo);
                                            z11 = false;
                                        } else {
                                            DataAirconInfo dataAirconInfo4 = new DataAirconInfo();
                                            dataAirconInfo4.updateForCBZL(dataAirconInfo);
                                            u02 = handlerAircon.u0(dataAirconSystem2, dataAirconInfo4);
                                            z11 = false;
                                            HandlerJson.Companion.getIsProcessing().set(false);
                                        }
                                        if (u02) {
                                            HandlerJson.Companion.getInstance(handlerAircon.context).processData(dataMaster, "handlerAirconCan");
                                        }
                                    }
                                    if (!(str3.length() != 0 ? true : z11)) {
                                        return true;
                                    }
                                    Unit unit = Unit.INSTANCE;
                                    return HandlerCB.Companion.getInstance().parseMessage(handlerAircon.context, str);
                                }
                                handlerAircon.v(dataMaster2);
                                Intrinsics.checkNotNull(dataAirconSystem5);
                                String str5 = dataAirconSystem5.info.name;
                                if (str5 == null) {
                                    dataAirconInfo.name = AirconFunctionsConstants.AC;
                                    dataAirconInfo.enabled = bool4;
                                    forest = Timber.forest;
                                    forest.v(f7106i0, "Valid CB JZ17 message. UID - " + dataAirconInfo.uid + " CB FW Major - " + dataAirconInfo.cbFWRevMajor + " CB FW Minor - " + dataAirconInfo.cbFWRevMinor + " CB Type - " + dataAirconInfo.cbType + " rfFWMajorRev - " + dataAirconInfo.rfFWRevMajor);
                                    Integer num8 = dataAirconInfo.cbType;
                                    boolean z12 = num8 == null && num8 != null && num8.intValue() == 4;
                                    num2 = dataAirconInfo.cbType;
                                    if (num2 != null && num2 != null && num2.intValue() == 5) {
                                        forest.d("RFDeviceUpdate broadcast", new Object[0]);
                                        Intent intent = new Intent(UartConstants.RF_DEVICE_UPDATE);
                                        intent.putExtra(ActivityMain.UID, uid);
                                        intent.putExtra("cbType", dataAirconInfo.cbType);
                                        LocalBroadcastManager.getInstance(handlerAircon.context).sendBroadcast(intent);
                                    }
                                    G = handlerAircon.G(dataAirconInfo, z12);
                                    if (G != null) {
                                        forest.d("JZ18 msg is null!!!", new Object[0]);
                                    } else if (z12) {
                                        forest.d("Now queueing JZ18 to this aircon " + dataAirconInfo.uid, new Object[0]);
                                        ((SendMessageToCB) KoinJavaComponent.get$default(SendMessageToCB.class, null, null, 6, null)).addCBMessage(G);
                                    } else {
                                        forest.d("Now sending JZ18 to this aircon " + dataAirconInfo.uid, new Object[0]);
                                        HandlerCan.Companion.getInstance().sendCanMessageToCB(handlerAircon.context, G);
                                    }
                                    str = message;
                                    dataMaster = dataMaster2;
                                    dataAirconSystem2 = dataAirconSystem5;
                                } else {
                                    if (!(str5 == null || str5.length() == 0) && !Intrinsics.areEqual(dataAirconSystem5.info.name, "AC??")) {
                                        dataAirconInfo.name = dataAirconSystem5.info.name;
                                    }
                                    dataAirconInfo.enabled = bool4;
                                    forest = Timber.forest;
                                    forest.v(f7106i0, "Valid CB JZ17 message. UID - " + dataAirconInfo.uid + " CB FW Major - " + dataAirconInfo.cbFWRevMajor + " CB FW Minor - " + dataAirconInfo.cbFWRevMinor + " CB Type - " + dataAirconInfo.cbType + " rfFWMajorRev - " + dataAirconInfo.rfFWRevMajor);
                                    Integer num82 = dataAirconInfo.cbType;
                                    if (num82 == null) {
                                        num2 = dataAirconInfo.cbType;
                                        if (num2 != null) {
                                            forest.d("RFDeviceUpdate broadcast", new Object[0]);
                                            Intent intent2 = new Intent(UartConstants.RF_DEVICE_UPDATE);
                                            intent2.putExtra(ActivityMain.UID, uid);
                                            intent2.putExtra("cbType", dataAirconInfo.cbType);
                                            LocalBroadcastManager.getInstance(handlerAircon.context).sendBroadcast(intent2);
                                        }
                                        G = handlerAircon.G(dataAirconInfo, z12);
                                        if (G != null) {
                                        }
                                        str = message;
                                        dataMaster = dataMaster2;
                                        dataAirconSystem2 = dataAirconSystem5;
                                    }
                                }
                                if (z7) {
                                }
                                if (!(str3.length() != 0 ? true : z11)) {
                                }
                            } else {
                                str = message;
                                if (handlerAircon.compareFRLValue(str, 9, "01")) {
                                    str3 = handlerAircon.ZoneMessage(dataAirconInfo2, str);
                                    if (!Intrinsics.areEqual(str3, "")) {
                                        Timber.forest.d("Invalid CB JZ7 msg - " + str3, new Object[0]);
                                        return false;
                                    }
                                    if (dataAirconSystem5 != null) {
                                        dataAirconSystem3 = dataAirconSystem5;
                                        treeMap = dataAirconSystem3.zones;
                                    } else {
                                        dataAirconSystem3 = dataAirconSystem5;
                                        treeMap = null;
                                    }
                                    if (treeMap != null && (num = dataAirconInfo2.noOfZones) != null) {
                                        TreeMap<String, DataZone> treeMap2 = dataAirconSystem3.zones;
                                        Intrinsics.checkNotNull(treeMap2);
                                        int size = treeMap2.size();
                                        if (num == null || num.intValue() != size) {
                                            Integer num9 = dataAirconInfo2.noOfZones;
                                            Intrinsics.checkNotNull(num9);
                                            for (int intValue = num9.intValue() + 1; intValue < 11; intValue++) {
                                                TreeMap<String, DataZone> treeMap3 = dataAirconSystem3.zones;
                                                Intrinsics.checkNotNull(treeMap3);
                                                treeMap3.remove(DataZone.Companion.getZoneKey(Integer.valueOf(intValue)));
                                            }
                                        }
                                    }
                                    dataAirconSystem = dataAirconSystem3;
                                    Timber.forest.v(f7106i0, "Valid CB JZ7 message. UID - " + dataAirconInfo2.uid + " noOfZones - " + dataAirconInfo2.noOfZones + " noOfConstants - " + dataAirconInfo2.noOfConstantZones + " constant1 - " + dataAirconInfo2.constantZone1 + " constant2 - " + dataAirconInfo2.constantZone2 + " constant3 - " + dataAirconInfo2.constantZone3 + " filterCleanStatus - " + dataAirconInfo2.filterCleanStatus);
                                    handlerAircon = this;
                                    dataAirconInfo = dataAirconInfo2;
                                } else {
                                    dataAirconSystem = dataAirconSystem5;
                                    handlerAircon = this;
                                    if (handlerAircon.compareFRLValue(str, 9, "02")) {
                                        dataAirconInfo = dataAirconInfo2;
                                        str3 = handlerAircon.UnitTypeMessage(dataAirconInfo, str);
                                        if (!Intrinsics.areEqual(str3, "")) {
                                            Timber.forest.d("Invalid CB JZ9 msg - " + str3, new Object[0]);
                                            return false;
                                        }
                                        Timber.forest.v(f7106i0, "Valid CB JZ9 message. UID - " + dataAirconInfo.uid + " unitType - " + dataAirconInfo.unitType + " activationCodeStatus - " + dataAirconInfo.activationCodeStatus);
                                    } else {
                                        dataAirconInfo = dataAirconInfo2;
                                        if (handlerAircon.compareFRLValue(str, 9, "08")) {
                                            str3 = handlerAircon.CBStatusMessage(dataAirconInfo, str);
                                            if (!Intrinsics.areEqual(str3, "")) {
                                                Timber.forest.d("Invalid CB JZ22 msg - " + str3, new Object[0]);
                                                return false;
                                            }
                                            if (dataAirconSystem != null) {
                                                dataAirconSystem2 = dataAirconSystem;
                                                Integer num10 = dataAirconSystem2.info.cbType;
                                                if (num10 != null && num10 != null && num10.intValue() == 3 && com.air.advantage.libraryairconlightjson.TabletInfo.isMyAir5Tablet()) {
                                                    dataAirconInfo.airconErrorCode = "";
                                                }
                                            } else {
                                                dataAirconSystem2 = dataAirconSystem;
                                            }
                                            Timber.forest.v(f7106i0, "Valid CB JZ22 message. UID - " + dataAirconInfo.uid + " error code - " + dataAirconInfo.airconErrorCode);
                                            dataMaster = dataMaster2;
                                        } else {
                                            dataAirconSystem2 = dataAirconSystem;
                                            if (handlerAircon.compareFRLValue(str, 9, "0a")) {
                                                DataSystem dataSystem4 = new DataSystem();
                                                String MIDMessage = handlerAircon.MIDMessage(dataSystem4, str);
                                                if (Intrinsics.areEqual(MIDMessage, "")) {
                                                    if (handlerAircon.update(dataMaster2, dataSystem4)) {
                                                        HandlerJson.Companion.getInstance(handlerAircon.context).processData(dataMaster2, "handlerAirconCan");
                                                    }
                                                    z10 = true;
                                                } else {
                                                    Timber.forest.d("Invalid CB JZ24 msg - " + MIDMessage, new Object[0]);
                                                    z10 = false;
                                                }
                                                return z10;
                                            }
                                            dataMaster = dataMaster2;
                                            if (handlerAircon.compareFRLValue(str, 9, "12")) {
                                                Integer parseHexToInt = handlerAircon.parseHexToInt(str, 17);
                                                Integer parseHexToInt2 = handlerAircon.parseHexToInt(str, 19);
                                                if (parseHexToInt == null || parseHexToInt2 == null) {
                                                    Timber.forest.d("Invalid CB JZ33 message - unable to parse infoByte or sensorMajorRev", new Object[0]);
                                                    return false;
                                                }
                                                if (message.length() <= 17) {
                                                    Timber.forest.d("Invalid CB JZ33 message, message too short! unable to get sensor UID", new Object[0]);
                                                    return false;
                                                }
                                                String substring = str.substring(11, 17);
                                                Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
                                                if (Intrinsics.areEqual(substring, FRLParser.DEFAULT_SENSORUID) || (parseHexToInt.intValue() & 64) != 64) {
                                                    Timber.forest.d("Invalid CB JZ33 message, sensor UID is 000000!!", new Object[0]);
                                                    return false;
                                                }
                                                Timber.forest.d("Valid CB JZ33 message. Sensor uid: " + substring + ", info byte: " + Integer.toHexString(parseHexToInt.intValue()), new Object[0]);
                                                Intent intent3 = new Intent(UartConstants.ZONE_SENSOR_PAIRING_MESSAGE_RECEIVED);
                                                intent3.putExtra("airconUid", uid);
                                                intent3.putExtra("sensorUid", substring);
                                                intent3.putExtra("sensorMajorRev", parseHexToInt2.intValue());
                                                LocalBroadcastManager.getInstance(handlerAircon.context).sendBroadcast(intent3);
                                                str3 = "";
                                            } else {
                                                if (handlerAircon.compareFRLValue(str, 9, "13")) {
                                                    Integer parseHexToInt3 = handlerAircon.parseHexToInt(str, 11);
                                                    if (parseHexToInt3 != null) {
                                                        Timber.forest.d("Valid CB JZ35 message received, info byte (hex): " + Integer.toHexString(parseHexToInt3.intValue()), new Object[0]);
                                                        handlerAircon.setAllZoneSensorData(handlerAircon.context);
                                                    } else {
                                                        Timber.forest.d("Invalid CB JZ35 message - unable to parse infoByte!", new Object[0]);
                                                    }
                                                    str2 = "Invalid";
                                                } else if (handlerAircon.compareFRLValue(str, 9, FRLParser.COMMAND_SET_PAIRING)) {
                                                    Timber.Forest forest2 = Timber.forest;
                                                    forest2.d("JZ56 received", new Object[0]);
                                                    Integer parseHexToInt4 = handlerAircon.parseHexToInt(str, 11);
                                                    forest2.d("pairingStatus = " + parseHexToInt4, new Object[0]);
                                                    Integer parseHexToInt5 = handlerAircon.parseHexToInt(str, 13);
                                                    forest2.d("rfDeviceType = " + parseHexToInt5, new Object[0]);
                                                    Integer parseHexToInt6 = handlerAircon.parseHexToInt(str, 15);
                                                    forest2.d("channelNo = " + parseHexToInt6, new Object[0]);
                                                    String substring2 = str.substring(17, 23);
                                                    Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String…ing(startIndex, endIndex)");
                                                    String substring3 = substring2.substring(1);
                                                    Intrinsics.checkNotNullExpressionValue(substring3, "this as java.lang.String).substring(startIndex)");
                                                    str2 = "Invalid";
                                                    forest2.d("deviceUID = " + substring3, new Object[0]);
                                                    if (parseHexToInt5 != null && parseHexToInt5.intValue() == 4) {
                                                        Intent intent4 = new Intent(UartConstants.ZONE_SENSOR_RF_PAIRING_MESSAGE_RECEIVED);
                                                        intent4.putExtra("airconUid", uid);
                                                        intent4.putExtra("pairingStatus", parseHexToInt4);
                                                        intent4.putExtra("channelNo", parseHexToInt6);
                                                        intent4.putExtra("sensorUid", substring3);
                                                        LocalBroadcastManager.getInstance(handlerAircon.context).sendBroadcast(intent4);
                                                    } else {
                                                        if (parseHexToInt5 != null && parseHexToInt5.intValue() == 2) {
                                                            forest2.d("JZ56 from RF Garage Controller rxed", new Object[0]);
                                                            if (parseHexToInt4 != null && parseHexToInt4.intValue() == 2 && dataMaster.myGarageRFControllers.addGarageController(new DataGroupSource(substring3))) {
                                                                forest2.d("update due to garage controller added", new Object[0]);
                                                                handlerAircon.D0(dataMaster.myGarageRFControllers);
                                                            }
                                                        } else if (parseHexToInt5 != null && parseHexToInt5.intValue() == 128) {
                                                            forest2.d("JZ56 from RF Garage Sensor rxed", new Object[0]);
                                                        } else if (parseHexToInt5 != null && parseHexToInt5.intValue() == 3) {
                                                            forest2.d("JZ56 from RF Aircon Split rxed", new Object[0]);
                                                        } else {
                                                            forest2.d("JZ56 rxed from rfDeviceType=" + parseHexToInt5, new Object[0]);
                                                        }
                                                        Intent intent5 = new Intent(UartConstants.RF_DEVICE_PAIRING_MESSAGE_RECEIVED);
                                                        intent5.putExtra("RFDeviceOriginUid", uid);
                                                        intent5.putExtra("pairingStatus", parseHexToInt4);
                                                        intent5.putExtra("rfDeviceType", parseHexToInt5);
                                                        intent5.putExtra("channelNo", parseHexToInt6);
                                                        intent5.putExtra("RFDeviceUid", substring3);
                                                        LocalBroadcastManager.getInstance(handlerAircon.context).sendBroadcast(intent5);
                                                    }
                                                } else {
                                                    str2 = "Invalid";
                                                    if (handlerAircon.compareFRLValue(str, 9, "27")) {
                                                        Timber.Forest forest3 = Timber.forest;
                                                        forest3.d("JZ58 received", new Object[0]);
                                                        Integer parseHexToInt7 = handlerAircon.parseHexToInt(str, 11);
                                                        forest3.d("calibrationStatus = " + parseHexToInt7, new Object[0]);
                                                        Integer parseHexToInt8 = handlerAircon.parseHexToInt(str, 13);
                                                        forest3.d("rfDeviceType = " + parseHexToInt8, new Object[0]);
                                                        Intent intent6 = new Intent(UartConstants.RF_DEVICE_CALIBRATION_MESSAGE_RECEIVED);
                                                        intent6.putExtra("RFDeviceOriginUid", uid);
                                                        intent6.putExtra("calibrationStatus", parseHexToInt7);
                                                        intent6.putExtra("channelNo", parseHexToInt8);
                                                        LocalBroadcastManager.getInstance(handlerAircon.context).sendBroadcast(intent6);
                                                    }
                                                }
                                                str3 = str2;
                                            }
                                            z7 = false;
                                            if (z7) {
                                            }
                                            if (!(str3.length() != 0 ? true : z11)) {
                                            }
                                        }
                                    }
                                }
                                dataAirconSystem2 = dataAirconSystem;
                                dataMaster = dataMaster2;
                            }
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    }
                    z7 = true;
                    if (z7) {
                    }
                    if (!(str3.length() != 0 ? true : z11)) {
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
    }

    @Nullable
    /* renamed from: Z */
    public final ArrayList<String> setAircon(@Nullable String str, boolean z7) throws ExceptionUart {
        Object obj;
        ArrayList<String> sendUpdateToCBUnit;
        TreeMap<String, DataZone> treeMap;
        DataAirconSystem dataAirconSystem = new DataAirconSystem("dummy");
        try {
            TreeMap treeMap2 = (TreeMap) this.gson.fromJson(str, new b().getType());
            DataAirconSystem dataAirconSystem2 = null;
            r1 = null;
            Collection<DataZone> collection = null;
            dataAirconSystem2 = null;
            if (treeMap2 == null || treeMap2.size() <= 0) {
                obj = null;
            } else {
                obj = treeMap2.firstKey();
                if (obj != null) {
                    Aircon aircon = (Aircon) treeMap2.get(obj);
                    DataAirconSystem dataAircon = aircon != null ? aircon.getDataAircon() : null;
                    if (r0(dataAircon)) {
                        synchronized (MyMasterData.class) {
                            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(this.context);
                            DataAirconSystem dataAirconSystem3 = dataMaster.aircons.get(obj);
                            if (dataAirconSystem3 != null) {
                                DataAirconSystem dataAirconSystem4 = new DataAirconSystem(dataAirconSystem3.info.uid);
                                if (dataAircon != null && (treeMap = dataAircon.zones) != null) {
                                    collection = treeMap.values();
                                }
                                if (collection == null) {
                                    collection = SetsKt__SetsKt.emptySet();
                                } else {
                                    Intrinsics.checkNotNull(collection);
                                }
                                ArrayList<DataZone> arrayList = new ArrayList();
                                for (DataZone dataZone : collection) {
                                    if (dataZone != null) {
                                        arrayList.add(dataZone);
                                    }
                                }
                                for (DataZone dataZone2 : arrayList) {
                                    DataZone dataZone3 = new DataZone(DataZone.Companion.getZoneKey(dataZone2.number));
                                    Intrinsics.checkNotNull(dataZone2);
                                    dataZone3.updateZoneGroupingOnly(dataZone2);
                                    TreeMap<String, DataZone> treeMap3 = dataAirconSystem4.zones;
                                    if (treeMap3 != null) {
                                        treeMap3.put(dataZone3.getZoneKey(), dataZone3);
                                    }
                                }
                                if (processDataZoneGroupMessageFromJSON(dataMaster, dataAirconSystem4)) {
                                    HandlerJson.Companion.getInstance(this.context).processData(dataMaster, "processDataZoneGroupMessageFromJSON");
                                }
                            }
                            Unit unit = Unit.INSTANCE;
                        }
                    }
                    if (!DataAirconSystem.update$default(dataAirconSystem, null, dataAircon, null, null, false, 16, null)) {
                        Timber.forest.d("Valid JSON, but nothing useful there.", new Object[0]);
                        throw new ExceptionUart("Valid JSON, but nothing useful there.");
                    }
                    dataAirconSystem2 = dataAircon;
                }
            }
            synchronized (MyMasterData.class) {
                sendUpdateToCBUnit = sendUpdateToCBUnit(MyMasterData.Companion.getDataMaster(this.context), dataAirconSystem2, (String) obj, z7);
            }
            return sendUpdateToCBUnit;
        } catch (JsonIOException unused) {
            Timber.forest.d("Failed to parse message", new Object[0]);
            throw new ExceptionUart("Failed to parse message");
        }
    }

    /* renamed from: a0 */
    public final void setMySystem(@Nullable String str, boolean z7) {
        try {
            Object fromJson = this.gson.fromJson(str, DataSystem.class);
            Intrinsics.checkNotNull(fromJson);
            DataSystem dataSystem = (DataSystem) fromJson;
            if (z7) {
                dataSystem.sanitiseData();
            }
            synchronized (MyMasterData.class) {
                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(this.context);
                if (update(dataMaster, dataSystem)) {
                    HandlerJson.Companion.getInstance(this.context).processData(dataMaster, "processDataSystemMessageFromJSON");
                }
                Unit unit = Unit.INSTANCE;
            }
        } catch (JsonIOException e7) {
            AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00cc  */
    @Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final DataAirconSystem b0(@Nullable DataAirconSystem dataAirconSystem, @Nullable DataAirconSystem dataAirconSystem2) {
        AirconMode airconMode;
        AirconMode airconMode2;
        if (dataAirconSystem == null || dataAirconSystem2 == null) {
            return null;
        }
        DataAirconSystem dataAirconSystem3 = new DataAirconSystem(dataAirconSystem.info.uid);
        DataAirconSystem.update$default(dataAirconSystem3, null, dataAirconSystem, null, null, false, 16, null);
        if (dataAirconSystem.info.fan != null) {
            DataAirconSystem dataAirconSystem4 = new DataAirconSystem();
            DataAirconSystem.update$default(dataAirconSystem4, null, dataAirconSystem2, null, null, false, 16, null);
            dataAirconSystem4.info.myFanSpeedIsRunning = Boolean.valueOf(dataAirconSystem.info.fan == FanStatus.autoAA);
            u0(dataAirconSystem2, dataAirconSystem4.info);
        }
        DataAirconSystem dataAirconSystem5 = new DataAirconSystem(dataAirconSystem2.info.uid);
        DataAirconSystem.update$default(dataAirconSystem5, null, dataAirconSystem2, null, null, false, 16, null);
        DataAirconSystem.update$default(dataAirconSystem5, null, dataAirconSystem, null, null, false, 16, null);
        DataAirconInfo dataAirconInfo = dataAirconSystem5.info;
        FanStatus fanStatus = dataAirconInfo.fan;
        if (fanStatus != null && fanStatus == FanStatus.autoAA) {
            Boolean bool = dataAirconInfo.aaAutoFanModeEnabled;
            if (bool != null) {
                Intrinsics.checkNotNull(bool);
                if (bool.booleanValue()) {
                    dataAirconSystem3.info.fan = getFanStatus(dataAirconSystem5);
                    if (dataAirconSystem3.info.fan == null) {
                        return null;
                    }
                } else if (dataAirconSystem5.isAutoFanAvailable()) {
                    dataAirconSystem3.info.fan = FanStatus.auto;
                } else {
                    dataAirconSystem3.info.fan = FanStatus.high;
                }
            }
        }
        DataAirconInfo dataAirconInfo2 = dataAirconSystem5.info;
        AirconMode airconMode3 = dataAirconInfo2.mode;
        if (airconMode3 != null && airconMode3 == (airconMode = AirconMode.myauto)) {
            Boolean bool2 = dataAirconInfo2.myAutoModeIsRunning;
            if (bool2 != null) {
                Intrinsics.checkNotNull(bool2);
                if (!bool2.booleanValue() || (airconMode2 = dataAirconSystem5.info.myAutoModeCurrentSetMode) == null) {
                    dataAirconSystem3.info.mode = AirconMode.cool;
                } else if (airconMode2 == airconMode) {
                    dataAirconSystem3.info.mode = AirconMode.cool;
                } else {
                    dataAirconSystem3.info.mode = airconMode2;
                }
            }
        }
        f0(dataAirconSystem, dataAirconSystem3);
        return dataAirconSystem3;
    }

    /* renamed from: c0 */
    public final void setZoneSensor(@Nullable String str) throws ExceptionUart {
        try {
            Object fromJson = this.gson.fromJson(str, DataAirconSystem.class);
            Intrinsics.checkNotNull(fromJson);
            DataAirconSystem dataAirconSystem = (DataAirconSystem) fromJson;
            synchronized (MyMasterData.class) {
                updateMasterFromDataAircon(MyMasterData.Companion.getDataMaster(this.context), dataAirconSystem);
                Unit unit = Unit.INSTANCE;
            }
        } catch (JsonIOException unused) {
            Timber.forest.d("Failed to parse message", new Object[0]);
            throw new ExceptionUart("Failed to parse message");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0086 A[Catch: all -> 0x0265, TryCatch #0 {, blocks: (B:10:0x0029, B:12:0x003d, B:14:0x0041, B:17:0x004b, B:21:0x0059, B:23:0x0070, B:25:0x0079, B:26:0x0093, B:28:0x009b, B:32:0x00a9, B:34:0x00b1, B:35:0x00c5, B:36:0x00d6, B:38:0x00da, B:39:0x00e2, B:40:0x022d, B:42:0x0239, B:43:0x0261, B:47:0x00d0, B:49:0x0086, B:50:0x0112, B:52:0x0116, B:54:0x011f, B:57:0x0169, B:59:0x016d, B:61:0x0176, B:63:0x017a, B:64:0x01aa, B:66:0x01b0, B:68:0x01f2, B:70:0x0208, B:71:0x01da), top: B:9:0x0029 }] */
    @Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ArrayList<String> d0(@Nullable String str, boolean z7) {
        boolean z10;
        SnapShot snapShot = (SnapShot) this.gson.fromJson(str, SnapShot.class);
        Timber.Forest forest = Timber.forest;
        forest.d("DBG DB processSnapShotMessageFromJSON", new Object[0]);
        if (snapShot == null || snapShot.snapshotId == null) {
            return null;
        }
        if (z7) {
            snapShot.CANmsgs = null;
            snapShot.aircons = null;
        }
        snapShot.summary = null;
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(this.context);
            SnapShot snapShot2 = dataMaster.snapshots.get(snapShot.snapshotId);
            if (snapShot2 == null) {
                Boolean bool = snapShot.delete;
                if (bool != null) {
                    Intrinsics.checkNotNull(bool);
                    if (bool.booleanValue()) {
                        forest.d("DBG DB snapshot doesn't exist anyway, no need to delete anymore", new Object[0]);
                        return new ArrayList<>();
                    }
                }
                forest.d("DBG DB create a new snapshot and add to memory", new Object[0]);
                snapShot2 = new SnapShot();
                dataMaster.snapshots.put(snapShot.snapshotId, snapShot2);
                TreeMap<String, DataAirconSystem> treeMap = snapShot.aircons;
                if (treeMap != null) {
                    Intrinsics.checkNotNull(treeMap);
                    if (treeMap.size() > 0) {
                        snapShot2.CANmsgs = getCANMessages(snapShot.aircons);
                        snapShot2.aircons = snapShot.aircons;
                    } else {
                        snapShot2.CANmsgs = getCANMessages(dataMaster.aircons);
                        snapShot2.initialiseAirconsCollection(dataMaster.aircons);
                    }
                    String packageName = ActivityMain.Companion.getPackageName();
                    if (packageName != null) {
                        boolean z11 = true;
                        if (!StringsKt__StringsKt.startsWith$default(packageName, "zone10", false, 2, null)) {
                            z11 = false;
                        }
                        if (z11) {
                            if (dataMaster.aircons.size() > 0) {
                                AirconFunctions.Companion companion = AirconFunctions.Companion;
                                TreeMap<String, DataAirconSystem> treeMap2 = dataMaster.aircons;
                                z10 = companion.isNonDualZoneDevice(treeMap2.get(treeMap2.firstKey()));
                            } else {
                                z10 = false;
                            }
                            snapShot.summary = snapShot2.generateSummaryForZone10e(Boolean.valueOf(z10));
                        } else {
                            snapShot.summary = snapShot2.generateSummary();
                        }
                        ArrayList<String> arrayList = snapShot2.CANmsgs;
                        forest.d("DBG DB no of can messages:" + (arrayList != null ? Integer.valueOf(arrayList.size()) : null), new Object[0]);
                        forest.d("DBG DB canMsg:" + snapShot2.CANmsgs, new Object[0]);
                    }
                }
            } else {
                Boolean bool2 = snapShot.delete;
                if (bool2 != null) {
                    Intrinsics.checkNotNull(bool2);
                    if (bool2.booleanValue()) {
                        forest.d("DBG DB delete snapshot: " + snapShot.snapshotId + " from memory", new Object[0]);
                        dataMaster.snapshots.remove(snapShot.snapshotId);
                        AirconDBStore.Companion.getInstance(this.context).updateSnapshot(this.context, snapShot.snapshotId);
                        HandlerJson.Companion.getInstance(this.context).processData(dataMaster, "snapshotDelete");
                        updateAndBroadcastNumberOfSnapshots(dataMaster);
                        return new ArrayList<>();
                    }
                }
                Boolean bool3 = snapShot.runNow;
                if (bool3 != null) {
                    Intrinsics.checkNotNull(bool3);
                    if (bool3.booleanValue()) {
                        ArrayList<String> arrayList2 = snapShot2.CANmsgs;
                        if (arrayList2 != null) {
                            String str2 = snapShot.snapshotId;
                            Intrinsics.checkNotNull(arrayList2);
                            forest.d("DBG DB runNow id:" + str2 + ", CAN msgs size:" + arrayList2.size(), new Object[0]);
                            ArrayList<String> arrayList3 = snapShot2.CANmsgs;
                            Intrinsics.checkNotNull(arrayList3);
                            Iterator<String> it = arrayList3.iterator();
                            while (it.hasNext()) {
                                String next = it.next();
                                HandlerCan.Companion.getInstance().sendCanMessageToCB(this.context, next);
                                Timber.forest.d("DBG DB canMsg:" + next, new Object[0]);
                            }
                        } else {
                            forest.d("DBG DB null CANmsgs @ runNow from id:" + snapShot.snapshotId, new Object[0]);
                        }
                        TreeMap<String, DataAirconSystem> treeMap3 = snapShot2.aircons;
                        Intrinsics.checkNotNull(treeMap3);
                        TreeMap<String, DataAirconSystem> treeMap4 = snapShot2.aircons;
                        Intrinsics.checkNotNull(treeMap4);
                        DataAirconSystem dataAirconSystem = treeMap3.get(treeMap4.firstKey());
                        if (dataAirconSystem != null) {
                            Xml2JsonFunctions.Companion.getInstance().sendMessages(this.context, dataAirconSystem);
                            Timber.forest.d("DBG DB (runNow) alarm event running snapshot id:" + snapShot2.snapshotId, new Object[0]);
                        }
                    }
                }
            }
            SnapShot snapShot3 = snapShot2;
            if (SnapShot.update$default(snapShot3, snapShot, null, false, 4, null)) {
                Timber.forest.d("DBG DB do broadcast in processSnapshotMessageFromJSON", new Object[0]);
                AirconDBStore.Companion.getInstance(this.context).updateSnapShot(this.context, snapShot3.snapshotId, snapShot3);
                HandlerJson.Companion.getInstance(this.context).processData(dataMaster, "snapShotUpdate");
                updateAndBroadcastNumberOfSnapshots(dataMaster);
            }
            return snapShot3.CANmsgs;
        }
    }

    /* renamed from: e0 */
    public final void setZoneGroup(@Nullable String str) {
        try {
            Object fromJson = this.gson.fromJson(str, DataAirconSystem.class);
            Intrinsics.checkNotNull(fromJson);
            DataAirconSystem dataAirconSystem = (DataAirconSystem) fromJson;
            synchronized (MyMasterData.class) {
                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(this.context);
                if (processDataZoneGroupMessageFromJSON(dataMaster, dataAirconSystem)) {
                    HandlerJson.Companion.getInstance(this.context).processData(dataMaster, "processDataZoneGroupMessageFromJSON");
                    LocalBroadcastManager.getInstance(this.context).sendBroadcast(new Intent(UartConstants.ZONE_GROUPING_UPDATE));
                }
                Unit unit = Unit.INSTANCE;
            }
        } catch (JsonIOException unused) {
            Timber.forest.d("Failed to parse message", new Object[0]);
            throw new ExceptionUart("Failed to parse message");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0078 A[Catch: all -> 0x01b0, TryCatch #0 {, blocks: (B:10:0x0036, B:12:0x0053, B:14:0x006f, B:16:0x0199, B:18:0x019f, B:19:0x0078, B:21:0x007c, B:23:0x0087, B:25:0x00a6, B:26:0x00b1, B:28:0x00b7, B:31:0x00c3, B:36:0x00da, B:38:0x00e5, B:39:0x00fb, B:40:0x0110, B:42:0x0116, B:45:0x0135, B:48:0x0152, B:53:0x011f, B:56:0x0127, B:58:0x015f, B:59:0x0180, B:60:0x01ac), top: B:9:0x0036 }] */
    /* renamed from: g0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setRemoveDevice(@NotNull Map<String, String> params) {
        Intrinsics.checkNotNullParameter(params, "params");
        String str = params.get("id");
        if (str != null) {
            if (str.length() == 0) {
                return;
            }
            Timber.forest.d("removeDevice received, id: " + str, new Object[0]);
            synchronized (MyMasterData.class) {
                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(this.context);
                DataSystem dataSystem = new DataSystem();
                dataSystem.update(dataMaster.system);
                ArrayList<String> arrayList = dataSystem.deviceIds;
                Intrinsics.checkNotNull(arrayList);
                if (arrayList.contains(str)) {
                    ArrayList<String> arrayList2 = dataSystem.deviceIds;
                    Intrinsics.checkNotNull(arrayList2);
                    arrayList2.remove(str);
                    HashMap<String, String> hashMap = dataSystem.deviceIdsV2;
                    Intrinsics.checkNotNull(hashMap);
                    hashMap.remove(str);
                    HashMap<String, String> hashMap2 = dataSystem.deviceNames;
                    Intrinsics.checkNotNull(hashMap2);
                    hashMap2.remove(str);
                    HashMap<String, Long> hashMap3 = dataSystem.deletedDevices;
                    if (hashMap3 != null) {
                        Intrinsics.checkNotNull(hashMap3);
                        if (!hashMap3.containsKey(str)) {
                            HashMap<String, Long> hashMap4 = dataSystem.deletedDevices;
                            if (hashMap4 != null) {
                                Intrinsics.checkNotNull(hashMap4);
                                if (hashMap4.size() < 10) {
                                    HashMap<String, Long> hashMap5 = new HashMap<>();
                                    dataSystem.deletedDevices = hashMap5;
                                    Intrinsics.checkNotNull(hashMap5);
                                    hashMap5.put(str, Long.valueOf(Calendar.getInstance().getTimeInMillis()));
                                    HashMap<String, Long> hashMap6 = dataMaster.system.deletedDevices;
                                    if (hashMap6 != null) {
                                        Intrinsics.checkNotNull(hashMap6);
                                        for (String str2 : hashMap6.keySet()) {
                                            if (!Intrinsics.areEqual(str2, str)) {
                                                HashMap<String, Long> hashMap7 = dataSystem.deletedDevices;
                                                Intrinsics.checkNotNull(hashMap7);
                                                Intrinsics.checkNotNull(str2);
                                                HashMap<String, Long> hashMap8 = dataMaster.system.deletedDevices;
                                                Intrinsics.checkNotNull(hashMap8);
                                                hashMap7.put(str2, hashMap8.get(str2));
                                            }
                                        }
                                    }
                                } else {
                                    HashMap<String, Long> hashMap9 = dataSystem.deletedDevices;
                                    Intrinsics.checkNotNull(hashMap9);
                                    if (hashMap9.containsKey(str)) {
                                        HashMap<String, Long> hashMap10 = dataSystem.deletedDevices;
                                        Intrinsics.checkNotNull(hashMap10);
                                        hashMap10.put(str, Long.valueOf(Calendar.getInstance().getTimeInMillis()));
                                    } else {
                                        String str3 = "";
                                        Long l8 = 0L;
                                        HashMap<String, Long> hashMap11 = dataSystem.deletedDevices;
                                        Intrinsics.checkNotNull(hashMap11);
                                        for (String str4 : hashMap11.keySet()) {
                                            if (l8 != null && l8.longValue() == 0) {
                                                HashMap<String, Long> hashMap12 = dataSystem.deletedDevices;
                                                Intrinsics.checkNotNull(hashMap12);
                                                l8 = hashMap12.get(str4);
                                            } else {
                                                HashMap<String, Long> hashMap13 = dataSystem.deletedDevices;
                                                Intrinsics.checkNotNull(hashMap13);
                                                Long l10 = hashMap13.get(str4);
                                                Intrinsics.checkNotNull(l10);
                                                long longValue = l10.longValue();
                                                Intrinsics.checkNotNull(l8);
                                                if (longValue < l8.longValue()) {
                                                    HashMap<String, Long> hashMap14 = dataSystem.deletedDevices;
                                                    Intrinsics.checkNotNull(hashMap14);
                                                    l8 = hashMap14.get(str4);
                                                }
                                            }
                                            str3 = str4;
                                        }
                                        HashMap<String, Long> hashMap15 = dataSystem.deletedDevices;
                                        Intrinsics.checkNotNull(hashMap15);
                                        TypeIntrinsics.k(hashMap15).remove(str3);
                                        HashMap<String, Long> hashMap16 = dataSystem.deletedDevices;
                                        Intrinsics.checkNotNull(hashMap16);
                                        hashMap16.put(str, Long.valueOf(Calendar.getInstance().getTimeInMillis()));
                                    }
                                }
                            } else {
                                HashMap<String, Long> hashMap17 = new HashMap<>();
                                dataSystem.deletedDevices = hashMap17;
                                Intrinsics.checkNotNull(hashMap17);
                                hashMap17.put(str, Long.valueOf(Calendar.getInstance().getTimeInMillis()));
                            }
                        }
                        if (update(dataMaster, dataSystem)) {
                            HandlerJson.Companion.getInstance(this.context).processData(dataMaster, "updateDeviceList");
                        }
                    }
                }
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x013c A[Catch: all -> 0x0249, TryCatch #0 {, blocks: (B:4:0x0008, B:5:0x001a, B:7:0x0020, B:10:0x0038, B:12:0x0049, B:14:0x004d, B:15:0x0058, B:17:0x005e, B:20:0x0066, B:27:0x0070, B:23:0x0077, B:31:0x01ee, B:35:0x01fe, B:38:0x020c, B:41:0x021b, B:44:0x0222, B:46:0x0228, B:47:0x0213, B:49:0x0205, B:51:0x01f7, B:53:0x0238, B:56:0x007f, B:58:0x0083, B:59:0x008e, B:61:0x0094, B:64:0x00a7, B:66:0x00ab, B:68:0x00b8, B:69:0x00bb, B:73:0x00c8, B:75:0x00cc, B:76:0x00d4, B:78:0x00da, B:80:0x00e2, B:81:0x00e8, B:83:0x00ee, B:85:0x00f6, B:86:0x00fc, B:88:0x0102, B:90:0x010a, B:91:0x0110, B:93:0x011d, B:96:0x0123, B:101:0x013c, B:103:0x012f, B:107:0x0136, B:109:0x00c2, B:111:0x0142, B:115:0x014a, B:118:0x0150, B:128:0x0158, B:129:0x0165, B:131:0x016b, B:134:0x0173, B:141:0x0177, B:137:0x017e, B:145:0x0186, B:147:0x018a, B:148:0x0195, B:150:0x019b, B:153:0x01ae, B:160:0x01bb, B:163:0x01d0, B:166:0x01d4, B:169:0x01e1, B:157:0x01b5, B:183:0x0242), top: B:3:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:103:0x012f A[Catch: all -> 0x0249, TryCatch #0 {, blocks: (B:4:0x0008, B:5:0x001a, B:7:0x0020, B:10:0x0038, B:12:0x0049, B:14:0x004d, B:15:0x0058, B:17:0x005e, B:20:0x0066, B:27:0x0070, B:23:0x0077, B:31:0x01ee, B:35:0x01fe, B:38:0x020c, B:41:0x021b, B:44:0x0222, B:46:0x0228, B:47:0x0213, B:49:0x0205, B:51:0x01f7, B:53:0x0238, B:56:0x007f, B:58:0x0083, B:59:0x008e, B:61:0x0094, B:64:0x00a7, B:66:0x00ab, B:68:0x00b8, B:69:0x00bb, B:73:0x00c8, B:75:0x00cc, B:76:0x00d4, B:78:0x00da, B:80:0x00e2, B:81:0x00e8, B:83:0x00ee, B:85:0x00f6, B:86:0x00fc, B:88:0x0102, B:90:0x010a, B:91:0x0110, B:93:0x011d, B:96:0x0123, B:101:0x013c, B:103:0x012f, B:107:0x0136, B:109:0x00c2, B:111:0x0142, B:115:0x014a, B:118:0x0150, B:128:0x0158, B:129:0x0165, B:131:0x016b, B:134:0x0173, B:141:0x0177, B:137:0x017e, B:145:0x0186, B:147:0x018a, B:148:0x0195, B:150:0x019b, B:153:0x01ae, B:160:0x01bb, B:163:0x01d0, B:166:0x01d4, B:169:0x01e1, B:157:0x01b5, B:183:0x0242), top: B:3:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0228 A[Catch: all -> 0x0249, TryCatch #0 {, blocks: (B:4:0x0008, B:5:0x001a, B:7:0x0020, B:10:0x0038, B:12:0x0049, B:14:0x004d, B:15:0x0058, B:17:0x005e, B:20:0x0066, B:27:0x0070, B:23:0x0077, B:31:0x01ee, B:35:0x01fe, B:38:0x020c, B:41:0x021b, B:44:0x0222, B:46:0x0228, B:47:0x0213, B:49:0x0205, B:51:0x01f7, B:53:0x0238, B:56:0x007f, B:58:0x0083, B:59:0x008e, B:61:0x0094, B:64:0x00a7, B:66:0x00ab, B:68:0x00b8, B:69:0x00bb, B:73:0x00c8, B:75:0x00cc, B:76:0x00d4, B:78:0x00da, B:80:0x00e2, B:81:0x00e8, B:83:0x00ee, B:85:0x00f6, B:86:0x00fc, B:88:0x0102, B:90:0x010a, B:91:0x0110, B:93:0x011d, B:96:0x0123, B:101:0x013c, B:103:0x012f, B:107:0x0136, B:109:0x00c2, B:111:0x0142, B:115:0x014a, B:118:0x0150, B:128:0x0158, B:129:0x0165, B:131:0x016b, B:134:0x0173, B:141:0x0177, B:137:0x017e, B:145:0x0186, B:147:0x018a, B:148:0x0195, B:150:0x019b, B:153:0x01ae, B:160:0x01bb, B:163:0x01d0, B:166:0x01d4, B:169:0x01e1, B:157:0x01b5, B:183:0x0242), top: B:3:0x0008 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void h0(@NotNull DataMaster sourceMasterData) {
        Integer num;
        ArrayList<String> arrayList;
        Intrinsics.checkNotNullParameter(sourceMasterData, "sourceMasterData");
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(this.context);
            for (String str : sourceMasterData.aircons.keySet()) {
                DataAirconSystem dataAirconSystem = sourceMasterData.aircons.get(str);
                DataAirconSystem dataAirconSystem2 = dataMaster.aircons.get(str);
                if (dataAirconSystem2 != null) {
                    Intrinsics.checkNotNull(dataAirconSystem);
                    dataAirconSystem.info.uid = null;
                    if (AirconFunctions.Companion.isZone10Compatible(dataAirconSystem2)) {
                        TreeMap<String, DataZone> treeMap = dataAirconSystem.zones;
                        if (treeMap != null) {
                            Intrinsics.checkNotNull(treeMap);
                            for (String str2 : treeMap.keySet()) {
                                TreeMap<String, DataZone> treeMap2 = dataAirconSystem.zones;
                                Intrinsics.checkNotNull(treeMap2);
                                DataZone dataZone = treeMap2.get(str2);
                                if (dataZone != null) {
                                    if (dataZone.sensorUid == null) {
                                        TreeMap<String, DataZone> treeMap3 = dataAirconSystem2.zones;
                                        Intrinsics.checkNotNull(treeMap3);
                                        DataZone dataZone2 = treeMap3.get(str2);
                                        if (dataZone2 != null) {
                                            dataZone2.clearSensorData();
                                        }
                                    }
                                    Integer num2 = dataZone.following;
                                    if (num2 != null && (num2 == null || num2.intValue() != 0)) {
                                        if (dataZone.number == null) {
                                            dataZone.number = DataZone.Companion.getZoneNumberFromKey(str2);
                                        }
                                        Integer num3 = dataAirconSystem2.info.constantZone1;
                                        if (num3 != null && Intrinsics.areEqual(num3, dataZone.number)) {
                                            dataZone.following = 0;
                                        }
                                        Integer num4 = dataAirconSystem2.info.constantZone2;
                                        if (num4 != null && Intrinsics.areEqual(num4, dataZone.number)) {
                                            dataZone.following = 0;
                                        }
                                        Integer num5 = dataAirconSystem2.info.constantZone3;
                                        if (num5 != null && Intrinsics.areEqual(num5, dataZone.number)) {
                                            dataZone.following = 0;
                                        }
                                        TreeMap<String, DataZone> treeMap4 = dataAirconSystem2.zones;
                                        Intrinsics.checkNotNull(treeMap4);
                                        DataZone dataZone3 = treeMap4.get(str2);
                                        if (dataZone3 != null) {
                                            String str3 = dataZone3.sensorUid;
                                            if (str3 == null) {
                                                Integer num6 = dataZone3.type;
                                                if (num6 != null && (num6 == null || num6.intValue() != 0)) {
                                                    dataZone.following = 0;
                                                }
                                            } else if (str3 == null || str3.length() == 0) {
                                            }
                                        }
                                    }
                                    Integer num7 = dataZone.following;
                                    if (num7 == null || (num7 != null && num7.intValue() == 0)) {
                                        dataZone.following = 0;
                                    }
                                }
                            }
                        }
                        TreeMap<String, DataZone> treeMap5 = dataAirconSystem.zones;
                        Intrinsics.checkNotNull(treeMap5);
                        for (DataZone dataZone4 : treeMap5.values()) {
                            if (dataZone4 != null) {
                                ArrayList<String> arrayList2 = dataZone4.followers;
                                if (arrayList2 != null) {
                                    Intrinsics.checkNotNull(arrayList2);
                                    arrayList2.clear();
                                } else {
                                    dataZone4.followers = new ArrayList<>();
                                }
                            }
                        }
                        TreeMap<String, DataZone> treeMap6 = dataAirconSystem.zones;
                        if (treeMap6 != null) {
                            Intrinsics.checkNotNull(treeMap6);
                            for (String str4 : treeMap6.keySet()) {
                                TreeMap<String, DataZone> treeMap7 = dataAirconSystem.zones;
                                Intrinsics.checkNotNull(treeMap7);
                                DataZone dataZone5 = treeMap7.get(str4);
                                if (dataZone5 != null && (num = dataZone5.following) != null && (num == null || num.intValue() != 0)) {
                                    TreeMap<String, DataZone> treeMap8 = dataAirconSystem.zones;
                                    Intrinsics.checkNotNull(treeMap8);
                                    DataZone dataZone6 = treeMap8.get(DataZone.Companion.getZoneKey(dataZone5.following));
                                    if (dataZone6 != null && (arrayList = dataZone6.followers) != null) {
                                        Intrinsics.checkNotNull(arrayList);
                                        if (!arrayList.contains(dataZone5.getZoneKey())) {
                                            ArrayList<String> arrayList3 = dataZone6.followers;
                                            Intrinsics.checkNotNull(arrayList3);
                                            arrayList3.add(dataZone5.getZoneKey());
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        TreeMap<String, DataZone> treeMap9 = dataAirconSystem.zones;
                        if (treeMap9 != null) {
                            Intrinsics.checkNotNull(treeMap9);
                            for (DataZone dataZone7 : treeMap9.values()) {
                                if (dataZone7 != null) {
                                    dataZone7.following = 0;
                                    ArrayList<String> arrayList4 = dataZone7.followers;
                                    if (arrayList4 != null) {
                                        Intrinsics.checkNotNull(arrayList4);
                                        arrayList4.clear();
                                    } else {
                                        dataZone7.followers = new ArrayList<>();
                                    }
                                }
                            }
                        }
                    }
                    Integer num8 = dataAirconSystem2.info.cbType;
                    if (num8 != null) {
                        if (num8 != null && num8.intValue() == 2) {
                            DataAirconInfo dataAirconInfo = dataAirconSystem.info;
                            dataAirconInfo.climateControlModeEnabled = null;
                            dataAirconInfo.climateControlModeIsRunning = null;
                            dataAirconInfo.myAutoModeEnabled = null;
                            dataAirconInfo.myAutoModeIsRunning = null;
                            dataAirconInfo.myAutoModeCurrentSetMode = null;
                            dataAirconInfo.myAutoCoolTargetTemp = null;
                            dataAirconInfo.myAutoHeatTargetTemp = null;
                        } else {
                            Integer num9 = dataAirconSystem2.info.cbType;
                            if (num9 == null || num9.intValue() != 3) {
                                Integer num10 = dataAirconSystem2.info.cbType;
                                if (num10 == null || num10.intValue() != 51) {
                                    Integer num11 = dataAirconSystem2.info.cbType;
                                    if (num11 != null && num11.intValue() == 0) {
                                    }
                                }
                            }
                        }
                    }
                    updateAirconDataSaveToDbAndSend(dataMaster, dataAirconSystem2, dataAirconSystem);
                    setAllZoneSensorData(this.context);
                }
            }
            update(dataMaster, sourceMasterData.system);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
    @Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final DataAirconSystem i0(@Nullable DataAirconSystem dataAirconSystem, @Nullable AirconMode airconMode, @Nullable Boolean bool) {
        AirconMode airconMode2;
        Intrinsics.checkNotNull(dataAirconSystem);
        DataAirconSystem airconMode3 = setAirconMode(dataAirconSystem.info.uid, dataAirconSystem);
        if (airconMode3 != null) {
            if (airconMode != null) {
                airconMode3.info.mode = airconMode;
            } else {
                AirconMode airconMode4 = airconMode3.info.mode;
                if (airconMode4 != null && airconMode4 == (airconMode2 = AirconMode.myauto)) {
                    Boolean bool2 = dataAirconSystem.info.myAutoModeEnabled;
                    if (bool2 != null) {
                        Intrinsics.checkNotNull(bool2);
                        if (bool2.booleanValue()) {
                            DataAirconInfo dataAirconInfo = dataAirconSystem.info;
                            dataAirconInfo.mode = airconMode2;
                            dataAirconInfo.myAutoModeIsRunning = Boolean.TRUE;
                            dataAirconInfo.myAutoModeCurrentSetMode = AirconMode.cool;
                        } else {
                            airconMode3.info.mode = AirconMode.cool;
                        }
                    }
                }
            }
            Integer num = airconMode3.info.myZone;
            if (num != null) {
                Intrinsics.checkNotNull(num);
                if (num.intValue() > 0) {
                    airconMode3.info.setTemp = null;
                }
            }
            SharedPreferencesStore sharedPreferencesStore = (SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null);
            Context context = this.context;
            String str = dataAirconSystem.info.uid;
            Intrinsics.checkNotNull(str);
            sharedPreferencesStore.deleteAircon(context, str, null);
            SharedPreferencesStore sharedPreferencesStore2 = (SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null);
            Context context2 = this.context;
            String str2 = dataAirconSystem.info.uid;
            Intrinsics.checkNotNull(str2);
            Intrinsics.checkNotNull(bool);
            sharedPreferencesStore2.e1(context2, str2, bool.booleanValue());
        }
        return airconMode3;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0060 A[Catch: all -> 0x020f, TryCatch #0 {, blocks: (B:4:0x0017, B:6:0x002b, B:10:0x005a, B:12:0x0060, B:13:0x0067, B:15:0x0074, B:17:0x0081, B:19:0x00e0, B:21:0x00e6, B:23:0x00ef, B:25:0x00fc, B:27:0x0155, B:29:0x015b, B:31:0x0164, B:32:0x016f, B:34:0x0175, B:36:0x017e, B:38:0x018b, B:43:0x01e8, B:45:0x01ee, B:46:0x020b, B:51:0x019f, B:53:0x01b2, B:54:0x01bd, B:56:0x01c3, B:59:0x01cf, B:64:0x010d, B:66:0x0120, B:67:0x012b, B:69:0x0131, B:72:0x013d, B:78:0x0092, B:80:0x0096, B:81:0x009d, B:83:0x00ab, B:84:0x00b6, B:86:0x00bc, B:89:0x00c8, B:95:0x0037, B:97:0x004a), top: B:3:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0074 A[Catch: all -> 0x020f, TryCatch #0 {, blocks: (B:4:0x0017, B:6:0x002b, B:10:0x005a, B:12:0x0060, B:13:0x0067, B:15:0x0074, B:17:0x0081, B:19:0x00e0, B:21:0x00e6, B:23:0x00ef, B:25:0x00fc, B:27:0x0155, B:29:0x015b, B:31:0x0164, B:32:0x016f, B:34:0x0175, B:36:0x017e, B:38:0x018b, B:43:0x01e8, B:45:0x01ee, B:46:0x020b, B:51:0x019f, B:53:0x01b2, B:54:0x01bd, B:56:0x01c3, B:59:0x01cf, B:64:0x010d, B:66:0x0120, B:67:0x012b, B:69:0x0131, B:72:0x013d, B:78:0x0092, B:80:0x0096, B:81:0x009d, B:83:0x00ab, B:84:0x00b6, B:86:0x00bc, B:89:0x00c8, B:95:0x0037, B:97:0x004a), top: B:3:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01e8 A[Catch: all -> 0x020f, TryCatch #0 {, blocks: (B:4:0x0017, B:6:0x002b, B:10:0x005a, B:12:0x0060, B:13:0x0067, B:15:0x0074, B:17:0x0081, B:19:0x00e0, B:21:0x00e6, B:23:0x00ef, B:25:0x00fc, B:27:0x0155, B:29:0x015b, B:31:0x0164, B:32:0x016f, B:34:0x0175, B:36:0x017e, B:38:0x018b, B:43:0x01e8, B:45:0x01ee, B:46:0x020b, B:51:0x019f, B:53:0x01b2, B:54:0x01bd, B:56:0x01c3, B:59:0x01cf, B:64:0x010d, B:66:0x0120, B:67:0x012b, B:69:0x0131, B:72:0x013d, B:78:0x0092, B:80:0x0096, B:81:0x009d, B:83:0x00ab, B:84:0x00b6, B:86:0x00bc, B:89:0x00c8, B:95:0x0037, B:97:0x004a), top: B:3:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x019f A[Catch: all -> 0x020f, TryCatch #0 {, blocks: (B:4:0x0017, B:6:0x002b, B:10:0x005a, B:12:0x0060, B:13:0x0067, B:15:0x0074, B:17:0x0081, B:19:0x00e0, B:21:0x00e6, B:23:0x00ef, B:25:0x00fc, B:27:0x0155, B:29:0x015b, B:31:0x0164, B:32:0x016f, B:34:0x0175, B:36:0x017e, B:38:0x018b, B:43:0x01e8, B:45:0x01ee, B:46:0x020b, B:51:0x019f, B:53:0x01b2, B:54:0x01bd, B:56:0x01c3, B:59:0x01cf, B:64:0x010d, B:66:0x0120, B:67:0x012b, B:69:0x0131, B:72:0x013d, B:78:0x0092, B:80:0x0096, B:81:0x009d, B:83:0x00ab, B:84:0x00b6, B:86:0x00bc, B:89:0x00c8, B:95:0x0037, B:97:0x004a), top: B:3:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01b2 A[Catch: all -> 0x020f, TryCatch #0 {, blocks: (B:4:0x0017, B:6:0x002b, B:10:0x005a, B:12:0x0060, B:13:0x0067, B:15:0x0074, B:17:0x0081, B:19:0x00e0, B:21:0x00e6, B:23:0x00ef, B:25:0x00fc, B:27:0x0155, B:29:0x015b, B:31:0x0164, B:32:0x016f, B:34:0x0175, B:36:0x017e, B:38:0x018b, B:43:0x01e8, B:45:0x01ee, B:46:0x020b, B:51:0x019f, B:53:0x01b2, B:54:0x01bd, B:56:0x01c3, B:59:0x01cf, B:64:0x010d, B:66:0x0120, B:67:0x012b, B:69:0x0131, B:72:0x013d, B:78:0x0092, B:80:0x0096, B:81:0x009d, B:83:0x00ab, B:84:0x00b6, B:86:0x00bc, B:89:0x00c8, B:95:0x0037, B:97:0x004a), top: B:3:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x010d A[Catch: all -> 0x020f, TryCatch #0 {, blocks: (B:4:0x0017, B:6:0x002b, B:10:0x005a, B:12:0x0060, B:13:0x0067, B:15:0x0074, B:17:0x0081, B:19:0x00e0, B:21:0x00e6, B:23:0x00ef, B:25:0x00fc, B:27:0x0155, B:29:0x015b, B:31:0x0164, B:32:0x016f, B:34:0x0175, B:36:0x017e, B:38:0x018b, B:43:0x01e8, B:45:0x01ee, B:46:0x020b, B:51:0x019f, B:53:0x01b2, B:54:0x01bd, B:56:0x01c3, B:59:0x01cf, B:64:0x010d, B:66:0x0120, B:67:0x012b, B:69:0x0131, B:72:0x013d, B:78:0x0092, B:80:0x0096, B:81:0x009d, B:83:0x00ab, B:84:0x00b6, B:86:0x00bc, B:89:0x00c8, B:95:0x0037, B:97:0x004a), top: B:3:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0092 A[Catch: all -> 0x020f, TryCatch #0 {, blocks: (B:4:0x0017, B:6:0x002b, B:10:0x005a, B:12:0x0060, B:13:0x0067, B:15:0x0074, B:17:0x0081, B:19:0x00e0, B:21:0x00e6, B:23:0x00ef, B:25:0x00fc, B:27:0x0155, B:29:0x015b, B:31:0x0164, B:32:0x016f, B:34:0x0175, B:36:0x017e, B:38:0x018b, B:43:0x01e8, B:45:0x01ee, B:46:0x020b, B:51:0x019f, B:53:0x01b2, B:54:0x01bd, B:56:0x01c3, B:59:0x01cf, B:64:0x010d, B:66:0x0120, B:67:0x012b, B:69:0x0131, B:72:0x013d, B:78:0x0092, B:80:0x0096, B:81:0x009d, B:83:0x00ab, B:84:0x00b6, B:86:0x00bc, B:89:0x00c8, B:95:0x0037, B:97:0x004a), top: B:3:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x004a A[Catch: all -> 0x020f, TryCatch #0 {, blocks: (B:4:0x0017, B:6:0x002b, B:10:0x005a, B:12:0x0060, B:13:0x0067, B:15:0x0074, B:17:0x0081, B:19:0x00e0, B:21:0x00e6, B:23:0x00ef, B:25:0x00fc, B:27:0x0155, B:29:0x015b, B:31:0x0164, B:32:0x016f, B:34:0x0175, B:36:0x017e, B:38:0x018b, B:43:0x01e8, B:45:0x01ee, B:46:0x020b, B:51:0x019f, B:53:0x01b2, B:54:0x01bd, B:56:0x01c3, B:59:0x01cf, B:64:0x010d, B:66:0x0120, B:67:0x012b, B:69:0x0131, B:72:0x013d, B:78:0x0092, B:80:0x0096, B:81:0x009d, B:83:0x00ab, B:84:0x00b6, B:86:0x00bc, B:89:0x00c8, B:95:0x0037, B:97:0x004a), top: B:3:0x0017 }] */
    /* renamed from: j */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateDataMaster(@NotNull String uid, @NotNull String fcmToken, @NotNull String deviceName, @NotNull String notificationVersion) {
        boolean z7;
        DataSystem dataSystem;
        HashMap<String, String> hashMap;
        HashMap<String, String> hashMap2;
        Intrinsics.checkNotNullParameter(uid, "uid");
        Intrinsics.checkNotNullParameter(fcmToken, "fcmToken");
        Intrinsics.checkNotNullParameter(deviceName, "deviceName");
        Intrinsics.checkNotNullParameter(notificationVersion, "notificationVersion");
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(this.context);
            DataSystem dataSystem2 = new DataSystem();
            ArrayList<String> arrayList = dataMaster.system.deviceIds;
            boolean z10 = true;
            if (arrayList != null) {
                Intrinsics.checkNotNull(arrayList);
                if (arrayList.contains(uid)) {
                    z7 = false;
                    dataSystem = dataMaster.system;
                    if (dataSystem.deviceIdsV2 == null) {
                    }
                    hashMap = dataMaster.system.deviceIdsV2;
                    Intrinsics.checkNotNull(hashMap);
                    if (hashMap.containsKey(uid)) {
                    }
                } else {
                    ArrayList<String> arrayList2 = new ArrayList<>();
                    dataSystem2.deviceIds = arrayList2;
                    Intrinsics.checkNotNull(arrayList2);
                    arrayList2.add(uid);
                    if (dataMaster.system.deviceIds != null) {
                        ArrayList<String> arrayList3 = dataSystem2.deviceIds;
                        Intrinsics.checkNotNull(arrayList3);
                        ArrayList<String> arrayList4 = dataMaster.system.deviceIds;
                        Intrinsics.checkNotNull(arrayList4);
                        arrayList3.addAll(arrayList4);
                    }
                    z7 = true;
                    dataSystem = dataMaster.system;
                    if (dataSystem.deviceIdsV2 == null) {
                        dataSystem.deviceIdsV2 = new HashMap<>();
                    }
                    hashMap = dataMaster.system.deviceIdsV2;
                    Intrinsics.checkNotNull(hashMap);
                    if (hashMap.containsKey(uid)) {
                        HashMap<String, String> hashMap3 = dataMaster.system.deviceIdsV2;
                        Intrinsics.checkNotNull(hashMap3);
                        if (hashMap3.containsKey(uid)) {
                            HashMap<String, String> hashMap4 = dataMaster.system.deviceIdsV2;
                            Intrinsics.checkNotNull(hashMap4);
                            if (!Intrinsics.areEqual(hashMap4.get(uid), fcmToken)) {
                                if (dataSystem2.deviceIdsV2 == null) {
                                    dataSystem2.deviceIdsV2 = new HashMap<>();
                                }
                                HashMap<String, String> hashMap5 = dataSystem2.deviceIdsV2;
                                Intrinsics.checkNotNull(hashMap5);
                                hashMap5.put(uid, fcmToken);
                                HashMap<String, String> hashMap6 = dataMaster.system.deviceIdsV2;
                                if (hashMap6 != null) {
                                    Intrinsics.checkNotNull(hashMap6);
                                    for (String str : hashMap6.keySet()) {
                                        if (!Intrinsics.areEqual(str, uid)) {
                                            HashMap<String, String> hashMap7 = dataSystem2.deviceIdsV2;
                                            Intrinsics.checkNotNull(hashMap7);
                                            Intrinsics.checkNotNull(str);
                                            HashMap<String, String> hashMap8 = dataMaster.system.deviceIdsV2;
                                            Intrinsics.checkNotNull(hashMap8);
                                            hashMap7.put(str, hashMap8.get(str));
                                        }
                                    }
                                }
                                z7 = true;
                            }
                        }
                        HashMap<String, String> hashMap9 = dataMaster.system.deviceNames;
                        if (hashMap9 != null) {
                            Intrinsics.checkNotNull(hashMap9);
                            if (hashMap9.containsKey(uid)) {
                                HashMap<String, String> hashMap10 = dataMaster.system.deviceNames;
                                Intrinsics.checkNotNull(hashMap10);
                                if (hashMap10.containsKey(uid)) {
                                    HashMap<String, String> hashMap11 = dataMaster.system.deviceNames;
                                    Intrinsics.checkNotNull(hashMap11);
                                    if (!Intrinsics.areEqual(hashMap11.get(uid), deviceName)) {
                                        HashMap<String, String> hashMap12 = new HashMap<>();
                                        dataSystem2.deviceNames = hashMap12;
                                        Intrinsics.checkNotNull(hashMap12);
                                        hashMap12.put(uid, deviceName);
                                        HashMap<String, String> hashMap13 = dataMaster.system.deviceNames;
                                        if (hashMap13 != null) {
                                            Intrinsics.checkNotNull(hashMap13);
                                            for (String str2 : hashMap13.keySet()) {
                                                if (!Intrinsics.areEqual(str2, uid)) {
                                                    HashMap<String, String> hashMap14 = dataSystem2.deviceNames;
                                                    Intrinsics.checkNotNull(hashMap14);
                                                    Intrinsics.checkNotNull(str2);
                                                    HashMap<String, String> hashMap15 = dataMaster.system.deviceNames;
                                                    Intrinsics.checkNotNull(hashMap15);
                                                    hashMap14.put(str2, hashMap15.get(str2));
                                                }
                                            }
                                        }
                                        z7 = true;
                                    }
                                }
                                HashMap<String, Long> hashMap16 = dataMaster.system.deletedDevices;
                                if (hashMap16 != null) {
                                    Intrinsics.checkNotNull(hashMap16);
                                    if (hashMap16.containsKey(uid)) {
                                        HashMap<String, Long> hashMap17 = dataMaster.system.deletedDevices;
                                        Intrinsics.checkNotNull(hashMap17);
                                        hashMap17.remove(uid);
                                        z7 = true;
                                    }
                                }
                                HashMap<String, String> hashMap18 = dataMaster.system.deviceNotificationVersion;
                                if (hashMap18 != null) {
                                    Intrinsics.checkNotNull(hashMap18);
                                    if (hashMap18.containsKey(uid)) {
                                        HashMap<String, String> hashMap19 = dataMaster.system.deviceNotificationVersion;
                                        Intrinsics.checkNotNull(hashMap19);
                                        if (hashMap19.containsKey(uid)) {
                                            HashMap<String, String> hashMap20 = dataMaster.system.deviceNotificationVersion;
                                            Intrinsics.checkNotNull(hashMap20);
                                            if (!Intrinsics.areEqual(hashMap20.get(uid), notificationVersion)) {
                                                HashMap<String, String> hashMap21 = new HashMap<>();
                                                dataSystem2.deviceNotificationVersion = hashMap21;
                                                Intrinsics.checkNotNull(hashMap21);
                                                hashMap21.put(uid, notificationVersion);
                                                hashMap2 = dataMaster.system.deviceNotificationVersion;
                                                if (hashMap2 != null) {
                                                    Intrinsics.checkNotNull(hashMap2);
                                                    for (String str3 : hashMap2.keySet()) {
                                                        if (!Intrinsics.areEqual(str3, uid)) {
                                                            HashMap<String, String> hashMap22 = dataSystem2.deviceNotificationVersion;
                                                            Intrinsics.checkNotNull(hashMap22);
                                                            Intrinsics.checkNotNull(str3);
                                                            HashMap<String, String> hashMap23 = dataMaster.system.deviceNotificationVersion;
                                                            Intrinsics.checkNotNull(hashMap23);
                                                            hashMap22.put(str3, hashMap23.get(str3));
                                                        }
                                                    }
                                                }
                                                if (z10 && update(dataMaster, dataSystem2)) {
                                                    HandlerJson.Companion.getInstance(this.context).processData(dataMaster, "addClientRecord");
                                                    LocalBroadcastManager.getInstance(this.context).sendBroadcast(new Intent(UartConstants.REMOTE_DATA_UPDATE));
                                                }
                                                Unit unit = Unit.INSTANCE;
                                            }
                                        }
                                        z10 = z7;
                                        if (z10) {
                                            HandlerJson.Companion.getInstance(this.context).processData(dataMaster, "addClientRecord");
                                            LocalBroadcastManager.getInstance(this.context).sendBroadcast(new Intent(UartConstants.REMOTE_DATA_UPDATE));
                                        }
                                        Unit unit2 = Unit.INSTANCE;
                                    } else {
                                        HashMap<String, String> hashMap212 = new HashMap<>();
                                        dataSystem2.deviceNotificationVersion = hashMap212;
                                        Intrinsics.checkNotNull(hashMap212);
                                        hashMap212.put(uid, notificationVersion);
                                        hashMap2 = dataMaster.system.deviceNotificationVersion;
                                        if (hashMap2 != null) {
                                        }
                                        if (z10) {
                                        }
                                        Unit unit22 = Unit.INSTANCE;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                ArrayList<String> arrayList22 = new ArrayList<>();
                dataSystem2.deviceIds = arrayList22;
                Intrinsics.checkNotNull(arrayList22);
                arrayList22.add(uid);
                if (dataMaster.system.deviceIds != null) {
                }
                z7 = true;
                dataSystem = dataMaster.system;
                if (dataSystem.deviceIdsV2 == null) {
                }
                hashMap = dataMaster.system.deviceIdsV2;
                Intrinsics.checkNotNull(hashMap);
                if (hashMap.containsKey(uid)) {
                }
            }
        }
    }

    @NotNull
    /* renamed from: j0 */
    public final Calendar roundToNearest15Minutes(@NotNull Date baseTime) {
        Intrinsics.checkNotNullParameter(baseTime, "baseTime");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseTime);
        int i10 = calendar.get(12) % 15;
        calendar.add(12, i10 < 8 ? -i10 : 15 - i10);
        calendar.set(13, 0);
        calendar.set(14, 0);
        Intrinsics.checkNotNull(calendar);
        return calendar;
    }

    @NotNull
    /* renamed from: k0 */
    public final String setActivation(@NotNull Context context, @Nullable String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        DataAirconSystem dataAirconSystem = (DataAirconSystem) this.gson.fromJson(str, DataAirconSystem.class);
        String encodeActivationCode = encodeActivationCodeMessage(dataAirconSystem.info);
        if (encodeActivationCode.length() == 0) {
            Timber.forest.d("Invalid JZ23 generated, ignoring request to send", new Object[0]);
        } else {
            HandlerCan.Companion.getInstance().sendCanMessageToCB(context, encodeActivationCode);
            Timber.forest.d("Sending JZ23 to UID - " + dataAirconSystem.info.uid + OTAFlashRowModel_v1.Data.DISCRIMINATOR + encodeActivationCode, new Object[0]);
        }
        Xml2JsonFunctions.Companion.getInstance().sendMessages(context, dataAirconSystem);
        return encodeActivationCode;
    }

    /* renamed from: l0 */
    public final void setAllZoneSensorData(@Nullable Context context) throws ExceptionUart {
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            Iterator<DataAirconSystem> it = dataMaster.aircons.values().iterator();
            while (it.hasNext()) {
                updateMasterFromDataAircon(dataMaster, it.next());
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    @Nullable
    public final String m(@NotNull Map<String, DataAirconSystem> allKnownAircons) {
        Intrinsics.checkNotNullParameter(allKnownAircons, "allKnownAircons");
        boolean z7 = false;
        int i10 = 101;
        while (true) {
            if (i10 <= 0 || z7) {
                break;
            }
            i10--;
            Iterator<DataAirconSystem> it = allKnownAircons.values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String str = it.next().info.name;
                if (str != null) {
                    if (Intrinsics.areEqual(str, "Aircon " + i10)) {
                        z7 = true;
                        break;
                    }
                }
            }
        }
        int i11 = z7 ? 1 + i10 : 1;
        if (i11 == 101) {
            return null;
        }
        return "Aircon " + i11;
    }

    @NotNull
    /* renamed from: m0 */
    public final String setRFCalibration(@NotNull Context context, @Nullable String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        StringBuffer stringBuffer = new StringBuffer();
        RFCalibration rFCalibration = (RFCalibration) this.gson.fromJson(str, RFCalibration.class);
        boolean z7 = false;
        if (rFCalibration != null) {
            String rfDeviceCalibrationToCB = rfDeviceCalibrationToCB(rFCalibration.uid, rFCalibration.calibrationControl, rFCalibration.upDownPosition, rFCalibration.channelNo);
            if (rfDeviceCalibrationToCB.length() == 0) {
                Timber.forest.d("Invalid JZ57(2) generated, ignoring request to send", new Object[0]);
            } else {
                stringBuffer.append(rfDeviceCalibrationToCB);
                Timber.forest.d("Sending JZ57(2) to UID - " + rFCalibration.uid + OTAFlashRowModel_v1.Data.DISCRIMINATOR + rfDeviceCalibrationToCB, new Object[0]);
                z7 = true;
            }
        }
        if (z7) {
            HandlerCan.Companion.getInstance().sendCanMessageToCB(context, stringBuffer.toString());
        }
        String stringBuffer2 = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(stringBuffer2, "toString(...)");
        return stringBuffer2;
    }

    @NotNull
    public final String n(double d3, double d10) {
        Calendar officialSunsetCalendarForDate = new SunriseSunsetCalculator(new Location(d3, d10), TimeZone.getDefault()).getOfficialSunsetCalendarForDate(Calendar.getInstance());
        Intrinsics.checkNotNullExpressionValue(officialSunsetCalendarForDate, "getOfficialSunsetCalendarForDate(...)");
        String format = new SimpleDateFormat("hh:mm a", Locale.US).format(officialSunsetCalendarForDate.getTime());
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    @NotNull
    /* renamed from: n0 */
    public final String setRFPairing(@NotNull Context context, @Nullable String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        StringBuffer stringBuffer = new StringBuffer();
        RFParing rFParing = (RFParing) this.gson.fromJson(str, RFParing.class);
        boolean z7 = false;
        if (rFParing != null) {
            String devicePairingToCB = devicePairingToCB(rFParing.uid, rFParing.pairingControl, rFParing.rfDeviceType, Integer.valueOf(rFParing.zoneChannelNo));
            if (devicePairingToCB == null || devicePairingToCB.length() == 0) {
                Timber.forest.d("Invalid JZ55(2) generated, ignoring request to send", new Object[0]);
            } else {
                stringBuffer.append(devicePairingToCB);
                Timber.forest.d("Sending JZ55(2) to UID - " + rFParing.uid + OTAFlashRowModel_v1.Data.DISCRIMINATOR + devicePairingToCB, new Object[0]);
                z7 = true;
            }
        }
        if (z7) {
            HandlerCan.Companion.getInstance().sendCanMessageToCB(context, stringBuffer.toString());
        }
        String stringBuffer2 = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(stringBuffer2, "toString(...)");
        return stringBuffer2;
    }

    public final void o(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            Timber.forest.d("DBG checkTimer called", new Object[0]);
            boolean z7 = false;
            for (String str : dataMaster.aircons.keySet()) {
                DataAirconSystem dataAirconSystem = dataMaster.aircons.get(str);
                if (l(dataAirconSystem)) {
                    Timber.Forest forest = Timber.forest;
                    Intrinsics.checkNotNull(dataAirconSystem);
                    forest.d(StringsKt__IndentKt.trimIndent("\n    \n    \n    !!!!!!!!!!!!!!!!!!!Aircon is expired!!!!!!!!!!!!!!!!!!!!!!!!!! key:" + str + "(" + dataAirconSystem.info.uid + ")\n    \n    \n    "), new Object[0]);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Aircon is expired - ");
                    sb.append(str);
                    forest.d(sb.toString(), new Object[0]);
                    AirconDBStore.Companion.getInstance(context).disableAirConSystem(context, dataAirconSystem.info.uid, dataAirconSystem);
                    dataAirconSystem.info.enabled = Boolean.FALSE;
                    z7 = true;
                }
            }
            if (z7) {
                dataMaster.sortAircons(false);
                HandlerJson.Companion.getInstance(context).processData(dataMaster, "Timer");
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    @NotNull
    /* renamed from: o0 */
    public final String setCancelCB10ZonePairing(@NotNull Context context, @Nullable String str, boolean z7) {
        Intrinsics.checkNotNullParameter(context, "context");
        StringBuffer stringBuffer = new StringBuffer();
        DataAirconSystem dataAirconSystem = (DataAirconSystem) this.gson.fromJson(str, DataAirconSystem.class);
        TreeMap<String, DataZone> treeMap = dataAirconSystem.zones;
        Intrinsics.checkNotNull(treeMap);
        boolean z10 = false;
        for (String str2 : treeMap.keySet()) {
            TreeMap<String, DataZone> treeMap2 = dataAirconSystem.zones;
            Intrinsics.checkNotNull(treeMap2);
            DataZone dataZone = treeMap2.get(str2);
            if (dataZone != null) {
                String devicePairingToCB = z7 ? devicePairingToCB(dataAirconSystem.info.uid, 1, AirconFunctionsConstants.B, dataZone.number) : devicePairingToCB(dataAirconSystem.info.uid, 0, AirconFunctionsConstants.B, dataZone.number);
                if (devicePairingToCB == null || devicePairingToCB.length() == 0) {
                    Timber.forest.d("Invalid JZ55 generated, ignoring request to send", new Object[0]);
                } else {
                    stringBuffer.append(devicePairingToCB);
                    Timber.forest.d("Sending JZ55 to UID - " + dataAirconSystem.info.uid + OTAFlashRowModel_v1.Data.DISCRIMINATOR + devicePairingToCB, new Object[0]);
                    z10 = true;
                }
            }
        }
        if (z10) {
            HandlerCan.Companion.getInstance().sendCanMessageToCB(context, stringBuffer.toString());
        }
        String stringBuffer2 = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(stringBuffer2, "toString(...)");
        return stringBuffer2;
    }

    public final boolean p(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Calendar calendar = Calendar.getInstance();
        SnapShot.a aVar = SnapShot.Companion;
        Intrinsics.checkNotNull(calendar);
        int currentMinutesOfTheDay = aVar.getCurrentMinutesOfTheDay(calendar);
        int i10 = calendar.get(7);
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            ArrayList<String> arrayList = dataMaster.myLights.alarmsOrder;
            Intrinsics.checkNotNull(arrayList);
            Iterator<String> it = arrayList.iterator();
            boolean z7 = false;
            while (it.hasNext()) {
                DataAlarm alarm = dataMaster.myLights.getAlarm(it.next());
                if (DataScene.Companion.sceneIsScheduledAtGivenTime(alarm, i10, currentMinutesOfTheDay) == SnapShot.b.startTimeIsScheduled) {
                    Intrinsics.checkNotNull(alarm);
                    HashMap<String, DataLight> hashMap = alarm.lights;
                    Intrinsics.checkNotNull(hashMap);
                    Collection<DataLight> values = hashMap.values();
                    Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                    ArrayList arrayList2 = new ArrayList();
                    for (DataLight dataLight : values) {
                        if (dataLight != null) {
                            arrayList2.add(dataLight);
                        }
                    }
                    Iterator it2 = arrayList2.iterator();
                    while (it2.hasNext()) {
                        this.myLights.a(((DataLight) it2.next()).id);
                        z7 = true;
                    }
                    alarm.timerEnabled = Boolean.FALSE;
                    try {
                        HandlerLights.Companion.getInstance().setLightAlarm(context, new Gson().toJson(alarm));
                    } catch (ExceptionUart e7) {
                        AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
                    }
                    ((FirebaseComms) KoinJavaComponent.get$default(FirebaseComms.class, null, null, 6, null)).parseUrl("Light alarm start, " + alarm.id + " - " + alarm.name, ExifInterface.GPS_DIRECTION_TRUE);
                }
            }
            if (z7 && this.myLights.c(context)) {
                return true;
            }
            Unit unit = Unit.INSTANCE;
            return false;
        }
    }

    public final void p0(@NotNull DataMaster masterData) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        masterData.clear();
        masterData.oneAirconOnly = false;
        masterData.multipleAirconDetectedOnOneAirconOnlySystem = false;
        masterData.system.mid = "11111";
        lock.set(true);
    }

    /* JADX WARN: Removed duplicated region for block: B:86:0x00d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void q(@Nullable Context context) {
        int i10;
        TreeMap<String, DataZone> treeMap;
        Integer num;
        float floatValue;
        float floatValue2;
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            for (String str : dataMaster.aircons.keySet()) {
                DataAirconSystem dataAirconSystem = dataMaster.aircons.get(str);
                if (dataAirconSystem != null) {
                    if (Companion.climateControlModeIsRunning(dataAirconSystem)) {
                        DataAirconInfo dataAirconInfo = dataAirconSystem.info;
                        dataAirconInfo.climateControlModeIsRunning = Boolean.TRUE;
                        if (dataAirconInfo.setTemp == null || (treeMap = dataAirconSystem.zones) == null) {
                            i10 = 0;
                            if (i10 != 0 && i10 != dataAirconSystem.info.myZone.intValue()) {
                                DataAirconSystem dataAirconSystem2 = new DataAirconSystem();
                                dataAirconSystem2.info.myZone = Integer.valueOf(i10);
                                sendUpdateToCBUnit(dataMaster, dataAirconSystem2, str, false);
                                Timber.forest.d("Climate Control: Change MyZone from " + dataAirconSystem.info.myZone + " to " + i10, new Object[0]);
                            }
                        } else {
                            Intrinsics.checkNotNull(treeMap);
                            if (treeMap.size() > 0) {
                                TreeMap<String, DataZone> treeMap2 = dataAirconSystem.zones;
                                Intrinsics.checkNotNull(treeMap2);
                                float f3 = 0.0f;
                                i10 = 0;
                                for (String str2 : treeMap2.keySet()) {
                                    TreeMap<String, DataZone> treeMap3 = dataAirconSystem.zones;
                                    Intrinsics.checkNotNull(treeMap3);
                                    DataZone dataZone = treeMap3.get(str2);
                                    if (dataZone != null && (num = dataZone.type) != null && dataZone.state != null && dataZone.setTemp != null && dataZone.measuredTemp != null && dataZone.number != null && dataAirconSystem.info.mode != null && (num == null || num.intValue() != 0)) {
                                        if (dataZone.state == ZoneState.open) {
                                            if (dataAirconSystem.info.mode == AirconMode.heat) {
                                                Float f7 = dataZone.setTemp;
                                                Intrinsics.checkNotNull(f7);
                                                floatValue = f7.floatValue();
                                                Float f10 = dataZone.measuredTemp;
                                                Intrinsics.checkNotNull(f10);
                                                floatValue2 = f10.floatValue();
                                            } else {
                                                Float f11 = dataZone.measuredTemp;
                                                Intrinsics.checkNotNull(f11);
                                                floatValue = f11.floatValue();
                                                Float f12 = dataZone.setTemp;
                                                Intrinsics.checkNotNull(f12);
                                                floatValue2 = f12.floatValue();
                                            }
                                            float f13 = floatValue - floatValue2;
                                            if (f13 > f3) {
                                                Integer num2 = dataZone.number;
                                                Intrinsics.checkNotNull(num2);
                                                i10 = num2.intValue();
                                                f3 = f13;
                                            }
                                        }
                                    }
                                }
                            }
                            if (i10 != 0) {
                                DataAirconSystem dataAirconSystem22 = new DataAirconSystem();
                                dataAirconSystem22.info.myZone = Integer.valueOf(i10);
                                sendUpdateToCBUnit(dataMaster, dataAirconSystem22, str, false);
                                Timber.forest.d("Climate Control: Change MyZone from " + dataAirconSystem.info.myZone + " to " + i10, new Object[0]);
                            }
                        }
                    } else {
                        dataAirconSystem.info.climateControlModeIsRunning = Boolean.FALSE;
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void q0(@Nullable Context context, @Nullable String str, @Nullable String str2, @Nullable Double d3, @Nullable Double d10, @Nullable String str3) {
        if (str3 != null) {
            boolean z7 = true;
            if (str3.length() == 0) {
                return;
            }
            DataLightsSystem dataLightsSystem = new DataLightsSystem();
            dataLightsSystem.sunsetTime = str3;
            DataSystem dataSystem = new DataSystem();
            dataSystem.country = str2;
            dataSystem.postCode = str;
            dataSystem.latitude = d3;
            dataSystem.longitude = d10;
            Timber.forest.d("DBG location sunset time: " + str3, new Object[0]);
            synchronized (MyMasterData.class) {
                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
                DataLightsSystem dataLightsSystem2 = dataMaster.myLights.system;
                Intrinsics.checkNotNull(dataLightsSystem2);
                boolean update$default = DataLightsSystem.update$default(dataLightsSystem2, dataLightsSystem, null, false, 4, null);
                if (!update(dataMaster, dataSystem)) {
                    z7 = update$default;
                }
                if (z7) {
                    HandlerJson.Companion.getInstance(context).processData(dataMaster, "storeSunsetTime");
                }
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final void r(@NotNull Context context) {
        DataAirconInfo dataAirconInfo;
        AirconMode airconMode;
        Integer num;
        Integer num2;
        Intrinsics.checkNotNullParameter(context, "context");
        synchronized (MyMasterData.class) {
            try {
                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
                for (String str : dataMaster.aircons.keySet()) {
                    DataAirconSystem dataAirconSystem = dataMaster.aircons.get(str);
                    if (dataAirconSystem != null) {
                        Boolean bool = dataAirconSystem.info.myAutoModeEnabled;
                        if (bool != null) {
                            Intrinsics.checkNotNull(bool);
                            if (bool.booleanValue() && (airconMode = (dataAirconInfo = dataAirconSystem.info).mode) != null && airconMode == AirconMode.myauto) {
                                Boolean bool2 = dataAirconInfo.quietNightModeIsRunning;
                                if (bool2 != null) {
                                    Intrinsics.checkNotNull(bool2);
                                    if (!bool2.booleanValue()) {
                                    }
                                }
                                DataAirconInfo dataAirconInfo2 = dataAirconSystem.info;
                                dataAirconInfo2.myAutoModeIsRunning = Boolean.TRUE;
                                if (!DataAirconSystem.Companion.isMyAutoRangeValid(dataAirconInfo2.myAutoCoolTargetTemp, dataAirconInfo2.myAutoHeatTargetTemp)) {
                                    dataAirconSystem.info.myAutoCoolTargetTemp = 24;
                                    dataAirconSystem.info.myAutoHeatTargetTemp = 20;
                                }
                                Integer num3 = dataAirconSystem.info.myZone;
                                Intrinsics.checkNotNull(num3);
                                int intValue = num3.intValue();
                                Integer num4 = dataAirconSystem.info.myZone;
                                Intrinsics.checkNotNull(num4);
                                int intValue2 = num4.intValue();
                                TreeMap<String, DataZone> treeMap = dataAirconSystem.zones;
                                Intrinsics.checkNotNull(treeMap);
                                if (treeMap.size() > 0) {
                                    TreeMap<String, DataZone> treeMap2 = dataAirconSystem.zones;
                                    Intrinsics.checkNotNull(treeMap2);
                                    float f3 = 0.0f;
                                    float f7 = 0.0f;
                                    int i10 = 0;
                                    int i11 = 0;
                                    for (String str2 : treeMap2.keySet()) {
                                        TreeMap<String, DataZone> treeMap3 = dataAirconSystem.zones;
                                        Intrinsics.checkNotNull(treeMap3);
                                        DataZone dataZone = treeMap3.get(str2);
                                        if (dataZone != null && (num2 = dataZone.type) != null && dataZone.state != null && dataZone.measuredTemp != null && dataZone.number != null && (num2 == null || num2.intValue() != 0)) {
                                            if (dataZone.state == ZoneState.open) {
                                                Float f10 = dataZone.measuredTemp;
                                                Intrinsics.checkNotNull(f10);
                                                float floatValue = f10.floatValue();
                                                Intrinsics.checkNotNull(dataAirconSystem.info.myAutoCoolTargetTemp);
                                                if (floatValue >= r15.intValue() + 0.5f) {
                                                    i10++;
                                                    Float f11 = dataZone.measuredTemp;
                                                    Intrinsics.checkNotNull(f11);
                                                    float floatValue2 = f11.floatValue();
                                                    Intrinsics.checkNotNull(dataAirconSystem.info.myAutoCoolTargetTemp);
                                                    float intValue3 = floatValue2 - (r15.intValue() + 0.5f);
                                                    if (intValue3 > f3) {
                                                        Integer num5 = dataZone.number;
                                                        Intrinsics.checkNotNull(num5);
                                                        intValue2 = num5.intValue();
                                                        f3 = intValue3;
                                                    }
                                                } else {
                                                    Float f12 = dataZone.measuredTemp;
                                                    Intrinsics.checkNotNull(f12);
                                                    float floatValue3 = f12.floatValue();
                                                    Intrinsics.checkNotNull(dataAirconSystem.info.myAutoHeatTargetTemp);
                                                    if (floatValue3 < r15.intValue()) {
                                                        i11++;
                                                        Integer num6 = dataAirconSystem.info.myAutoHeatTargetTemp;
                                                        Intrinsics.checkNotNull(num6);
                                                        float intValue4 = num6.intValue();
                                                        Float f13 = dataZone.measuredTemp;
                                                        Intrinsics.checkNotNull(f13);
                                                        float floatValue4 = intValue4 - f13.floatValue();
                                                        if (floatValue4 > f7) {
                                                            Integer num7 = dataZone.number;
                                                            Intrinsics.checkNotNull(num7);
                                                            intValue = num7.intValue();
                                                            f7 = floatValue4;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    DataAirconSystem dataAirconSystem2 = new DataAirconSystem();
                                    dataAirconSystem2.updateForSnapshot(dataAirconSystem);
                                    if (i10 <= 0 && i11 <= 0) {
                                        AirconMode airconMode2 = dataAirconSystem.info.myAutoModeCurrentSetMode;
                                        if (airconMode2 == null || airconMode2 != AirconMode.heat) {
                                            DataAirconInfo dataAirconInfo3 = dataAirconSystem2.info;
                                            dataAirconInfo3.myAutoModeCurrentSetMode = AirconMode.cool;
                                            dataAirconInfo3.myZone = Integer.valueOf(intValue2);
                                            DataAirconInfo dataAirconInfo4 = dataAirconSystem2.info;
                                            Intrinsics.checkNotNull(dataAirconSystem.info.myAutoCoolTargetTemp);
                                            dataAirconInfo4.setTemp = Float.valueOf(r6.intValue());
                                        } else {
                                            dataAirconSystem2.info.myZone = Integer.valueOf(intValue);
                                            DataAirconInfo dataAirconInfo5 = dataAirconSystem2.info;
                                            Intrinsics.checkNotNull(dataAirconSystem.info.myAutoHeatTargetTemp);
                                            dataAirconInfo5.setTemp = Float.valueOf(r6.intValue());
                                        }
                                    } else if (i11 > i10) {
                                        DataAirconInfo dataAirconInfo6 = dataAirconSystem2.info;
                                        dataAirconInfo6.myAutoModeCurrentSetMode = AirconMode.heat;
                                        dataAirconInfo6.myZone = Integer.valueOf(intValue);
                                        DataAirconInfo dataAirconInfo7 = dataAirconSystem2.info;
                                        Intrinsics.checkNotNull(dataAirconSystem.info.myAutoHeatTargetTemp);
                                        dataAirconInfo7.setTemp = Float.valueOf(r6.intValue());
                                    } else {
                                        DataAirconInfo dataAirconInfo8 = dataAirconSystem2.info;
                                        dataAirconInfo8.myAutoModeCurrentSetMode = AirconMode.cool;
                                        dataAirconInfo8.myZone = Integer.valueOf(intValue2);
                                        DataAirconInfo dataAirconInfo9 = dataAirconSystem2.info;
                                        Intrinsics.checkNotNull(dataAirconSystem.info.myAutoCoolTargetTemp);
                                        dataAirconInfo9.setTemp = Float.valueOf(r6.intValue());
                                    }
                                    TreeMap<String, DataZone> treeMap4 = dataAirconSystem.zones;
                                    Intrinsics.checkNotNull(treeMap4);
                                    for (String str3 : treeMap4.keySet()) {
                                        TreeMap<String, DataZone> treeMap5 = dataAirconSystem.zones;
                                        Intrinsics.checkNotNull(treeMap5);
                                        DataZone dataZone2 = treeMap5.get(str3);
                                        if (dataZone2 != null && (num = dataZone2.type) != null && (num == null || num.intValue() != 0)) {
                                            TreeMap<String, DataZone> treeMap6 = dataAirconSystem2.zones;
                                            Intrinsics.checkNotNull(treeMap6);
                                            DataZone dataZone3 = treeMap6.get(str3);
                                            if (dataZone3 != null) {
                                                dataZone3.setTemp = dataAirconSystem2.info.setTemp;
                                            }
                                        }
                                    }
                                    Integer num8 = dataAirconSystem2.info.myZone;
                                    if (num8 != null && num8 != null && num8.intValue() == 0) {
                                        dataAirconSystem2.info.myZone = null;
                                    }
                                    try {
                                        sendUpdateToCBUnit(dataMaster, dataAirconSystem2, str, false);
                                    } catch (Throwable th) {
                                        th = th;
                                        throw th;
                                    }
                                }
                            }
                        }
                        DataAirconInfo dataAirconInfo10 = dataAirconSystem.info;
                        dataAirconInfo10.myAutoModeIsRunning = Boolean.FALSE;
                        dataAirconInfo10.myAutoModeCurrentSetMode = dataAirconInfo10.mode;
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    public final void s(@NotNull Context context, @NotNull DataAirconSystem airconInMemory) {
        DataAirconSystem.FreshAirStatus freshAirStatus;
        Integer num;
        Float f3;
        FanStatus fanStatus;
        AirconMode airconMode;
        SystemState systemState;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(airconInMemory, "airconInMemory");
        synchronized (MyMasterData.class) {
            HandlerAircon companion = Companion.getInstance();
            DataAirconSystem dataAirconSystem = new DataAirconSystem();
            DataAirconSystem.update$default(dataAirconSystem, null, airconInMemory, null, null, false, 16, null);
            DataAirconSystem b02 = companion.b0(dataAirconSystem, airconInMemory);
            if (b02 != null) {
                DataAirconInfo dataAirconInfo = b02.info;
                if (dataAirconInfo.state != null && dataAirconInfo.mode != null) {
                    String ecodeAirconSource = companion.ecodeAirconSource(dataAirconInfo);
                    HandlerCan companion2 = HandlerCan.Companion.getInstance();
                    if (companion2 != null) {
                        companion2.sendCanMessageToCB(context, ecodeAirconSource);
                    }
                    DataAirconInfo dataAirconInfo2 = b02.info;
                    SystemState systemState2 = dataAirconInfo2.state;
                    if (systemState2 != null && (systemState = airconInMemory.info.state) != null && systemState2 == systemState) {
                        dataAirconInfo2.state = null;
                    }
                    AirconMode airconMode2 = dataAirconInfo2.mode;
                    if (airconMode2 != null && (airconMode = airconInMemory.info.mode) != null && airconMode2 == airconMode) {
                        dataAirconInfo2.mode = null;
                    }
                    FanStatus fanStatus2 = dataAirconInfo2.fan;
                    if (fanStatus2 != null && (fanStatus = airconInMemory.info.fan) != null && fanStatus2 == fanStatus) {
                        dataAirconInfo2.fan = null;
                    }
                    Float f7 = dataAirconInfo2.setTemp;
                    if (f7 != null && (f3 = airconInMemory.info.setTemp) != null && Intrinsics.areEqual(f7, f3)) {
                        b02.info.setTemp = null;
                    }
                    Integer num2 = b02.info.myZone;
                    if (num2 != null && (num = airconInMemory.info.myZone) != null && Intrinsics.areEqual(num2, num)) {
                        b02.info.myZone = null;
                    }
                    DataAirconInfo dataAirconInfo3 = b02.info;
                    DataAirconSystem.FreshAirStatus freshAirStatus2 = dataAirconInfo3.freshAirStatus;
                    if (freshAirStatus2 != null && (freshAirStatus = airconInMemory.info.freshAirStatus) != null && freshAirStatus2 == freshAirStatus) {
                        dataAirconInfo3.freshAirStatus = null;
                    }
                    dataAirconInfo3.noOfZones = null;
                    dataAirconInfo3.noOfConstantZones = null;
                    dataAirconInfo3.constantZone1 = null;
                    dataAirconInfo3.constantZone2 = null;
                    dataAirconInfo3.constantZone3 = null;
                    TreeMap<String, DataZone> treeMap = b02.zones;
                    Intrinsics.checkNotNull(treeMap);
                    Collection<DataZone> values = treeMap.values();
                    Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                    ArrayList<DataZone> arrayList = new ArrayList();
                    for (DataZone dataZone : values) {
                        if (dataZone != null) {
                            arrayList.add(dataZone);
                        }
                    }
                    for (DataZone dataZone2 : arrayList) {
                        dataZone2.maxDamper = null;
                        dataZone2.minDamper = null;
                        dataZone2.motionConfig = null;
                        dataZone2.state = null;
                        dataZone2.setTemp = null;
                        dataZone2.value = null;
                    }
                    b02.info.uid = airconInMemory.info.uid;
                    Xml2JsonFunctions companion3 = Xml2JsonFunctions.Companion.getInstance();
                    if (companion3 != null) {
                        companion3.sendMessages(context, b02);
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0390 A[Catch: all -> 0x03a0, TryCatch #0 {, blocks: (B:4:0x0019, B:5:0x0029, B:7:0x002f, B:9:0x0046, B:11:0x004f, B:13:0x006b, B:17:0x0078, B:19:0x0084, B:22:0x02e2, B:24:0x02ed, B:25:0x02fa, B:27:0x0300, B:30:0x0313, B:33:0x0317, B:39:0x0368, B:43:0x00b9, B:45:0x00bf, B:47:0x00c3, B:48:0x00f4, B:52:0x00fd, B:54:0x0103, B:57:0x0111, B:58:0x012d, B:59:0x0138, B:60:0x0145, B:62:0x014b, B:64:0x015f, B:68:0x01b1, B:71:0x01bc, B:73:0x01c0, B:76:0x01ca, B:77:0x01e6, B:79:0x01f3, B:85:0x01b6, B:88:0x0167, B:90:0x016d, B:95:0x017c, B:96:0x019d, B:98:0x01aa, B:102:0x0174, B:108:0x0201, B:116:0x0212, B:118:0x0229, B:120:0x022f, B:122:0x0233, B:124:0x0237, B:126:0x023b, B:128:0x023f, B:131:0x0245, B:133:0x029c, B:134:0x02a9, B:136:0x02af, B:139:0x02c2, B:151:0x02d8, B:143:0x02cb, B:146:0x02d1, B:158:0x0390, B:161:0x039c), top: B:3:0x0019 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void t(@NotNull Context context) {
        DataMaster dataMaster;
        DataAirconInfo dataAirconInfo;
        SystemState systemState;
        AirconMode airconMode;
        AirconMode airconMode2;
        Integer num;
        AirconMode airconMode3;
        int i10;
        Integer num2;
        Integer num3;
        String str;
        AirconMode airconMode4;
        Intrinsics.checkNotNullParameter(context, "context");
        Calendar calendar = Calendar.getInstance();
        SnapShot.a aVar = SnapShot.Companion;
        Intrinsics.checkNotNull(calendar);
        int currentMinutesOfTheDay = aVar.getCurrentMinutesOfTheDay(calendar);
        synchronized (MyMasterData.class) {
            DataMaster dataMaster2 = MyMasterData.Companion.getDataMaster(context);
            Iterator<String> it = dataMaster2.aircons.keySet().iterator();
            while (it.hasNext()) {
                DataAirconSystem dataAirconSystem = dataMaster2.aircons.get(it.next());
                Intrinsics.checkNotNull(dataAirconSystem);
                Boolean bool = dataAirconSystem.info.quietNightModeEnabled;
                if (bool != null) {
                    Intrinsics.checkNotNull(bool);
                    if (bool.booleanValue()) {
                        SharedPreferencesStore.Companion companion = SharedPreferencesStore.Companion;
                        String str2 = dataAirconSystem.info.uid;
                        Intrinsics.checkNotNull(str2);
                        boolean quietNightModeHasBeenCancelled = companion.setQuietNightModeHasBeenCancelled(context, str2);
                        Boolean bool2 = dataAirconSystem.info.quietNightModeIsRunning;
                        DataAirconSystem dataAirconSystem2 = null;
                        if (bool2 != null) {
                            Intrinsics.checkNotNull(bool2);
                            if (!bool2.booleanValue()) {
                                dataMaster = dataMaster2;
                                if (currentMinutesOfTheDay >= 1320 || currentMinutesOfTheDay <= 360) {
                                    if (!quietNightModeHasBeenCancelled && (systemState = (dataAirconInfo = dataAirconSystem.info).state) != null && (airconMode = dataAirconInfo.mode) != null && systemState == SystemState.on && (airconMode == (airconMode2 = AirconMode.cool) || (airconMode == AirconMode.myauto && (airconMode3 = dataAirconInfo.myAutoModeCurrentSetMode) != null && airconMode3 == airconMode2))) {
                                        dataAirconInfo.quietNightModeIsRunning = Boolean.TRUE;
                                        DataAirconSystem dataAirconSystem3 = new DataAirconSystem();
                                        dataAirconSystem3.updateForAutoModeBackup(dataAirconSystem);
                                        DataAirconInfo dataAirconInfo2 = dataAirconSystem3.info;
                                        DataAirconInfo dataAirconInfo3 = dataAirconSystem.info;
                                        dataAirconInfo2.mode = dataAirconInfo3.mode;
                                        this.fanSpeedMap.put(dataAirconInfo3.uid, dataAirconSystem3);
                                        SharedPreferencesStore sharedPreferencesStore = (SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null);
                                        String str3 = dataAirconSystem.info.uid;
                                        Intrinsics.checkNotNull(str3);
                                        sharedPreferencesStore.deleteAircon(context, str3, dataAirconSystem3);
                                        DataAirconSystem dataAirconSystem4 = new DataAirconSystem();
                                        DataAirconSystem.update$default(dataAirconSystem4, null, dataAirconSystem, null, null, false, 16, null);
                                        DataAirconInfo dataAirconInfo4 = dataAirconSystem4.info;
                                        dataAirconInfo4.mode = AirconMode.vent;
                                        dataAirconInfo4.setTemp = Float.valueOf(16.0f);
                                        TreeMap<String, DataZone> treeMap = dataAirconSystem4.zones;
                                        Intrinsics.checkNotNull(treeMap);
                                        if (treeMap.size() > 0) {
                                            TreeMap<String, DataZone> treeMap2 = dataAirconSystem4.zones;
                                            Intrinsics.checkNotNull(treeMap2);
                                            for (String str4 : treeMap2.keySet()) {
                                                TreeMap<String, DataZone> treeMap3 = dataAirconSystem4.zones;
                                                Intrinsics.checkNotNull(treeMap3);
                                                DataZone dataZone = treeMap3.get(str4);
                                                if (dataZone != null && (num = dataZone.type) != null) {
                                                    if (num != null && num.intValue() == 0) {
                                                        dataZone.value = 100;
                                                    } else {
                                                        dataZone.setTemp = Float.valueOf(16.0f);
                                                    }
                                                }
                                            }
                                        }
                                        dataAirconSystem2 = dataAirconSystem4;
                                    }
                                } else if (quietNightModeHasBeenCancelled) {
                                    SharedPreferencesStore sharedPreferencesStore2 = (SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null);
                                    String str5 = dataAirconSystem.info.uid;
                                    Intrinsics.checkNotNull(str5);
                                    sharedPreferencesStore2.e1(context, str5, false);
                                }
                            } else if (currentMinutesOfTheDay >= 1320 || currentMinutesOfTheDay < 360) {
                                AirconMode airconMode5 = dataAirconSystem.info.mode;
                                if (airconMode5 == null || airconMode5 == (airconMode4 = AirconMode.vent)) {
                                    dataMaster = dataMaster2;
                                    i10 = 100;
                                } else {
                                    DataAirconSystem dataAirconSystem5 = new DataAirconSystem(dataAirconSystem.info.uid);
                                    dataMaster = dataMaster2;
                                    i10 = 100;
                                    DataAirconSystem.update$default(dataAirconSystem5, null, dataAirconSystem, null, null, false, 16, null);
                                    dataAirconSystem5.info.mode = airconMode4;
                                    dataAirconSystem2 = dataAirconSystem5;
                                }
                                Integer num4 = dataAirconSystem.info.myZone;
                                if (num4 != null && num4 != null && num4.intValue() == 0) {
                                    if (!Intrinsics.areEqual(dataAirconSystem.info.setTemp, 16.0f)) {
                                        if (dataAirconSystem2 == null) {
                                            DataAirconSystem dataAirconSystem6 = new DataAirconSystem(dataAirconSystem.info.uid);
                                            DataAirconSystem.update$default(dataAirconSystem6, null, dataAirconSystem, null, null, false, 16, null);
                                            dataAirconSystem2 = dataAirconSystem6;
                                        }
                                        dataAirconSystem2.info.setTemp = Float.valueOf(16.0f);
                                    }
                                }
                                TreeMap<String, DataZone> treeMap4 = dataAirconSystem.zones;
                                Intrinsics.checkNotNull(treeMap4);
                                for (String str6 : treeMap4.keySet()) {
                                    TreeMap<String, DataZone> treeMap5 = dataAirconSystem.zones;
                                    Intrinsics.checkNotNull(treeMap5);
                                    DataZone dataZone2 = treeMap5.get(str6);
                                    if (dataZone2 != null && (num2 = dataZone2.type) != null) {
                                        if (num2 != null && num2.intValue() == 0 && (num3 = dataZone2.value) != null && (num3 == null || num3.intValue() != i10)) {
                                            if (dataAirconSystem2 == null) {
                                                DataAirconSystem dataAirconSystem7 = new DataAirconSystem(dataAirconSystem.info.uid);
                                                str = str6;
                                                DataAirconSystem.update$default(dataAirconSystem7, null, dataAirconSystem, null, null, false, 16, null);
                                                dataAirconSystem2 = dataAirconSystem7;
                                            } else {
                                                str = str6;
                                            }
                                            TreeMap<String, DataZone> treeMap6 = dataAirconSystem2.zones;
                                            Intrinsics.checkNotNull(treeMap6);
                                            DataZone dataZone3 = treeMap6.get(str);
                                            if (dataZone3 != null) {
                                                dataZone3.value = Integer.valueOf(i10);
                                            }
                                        } else {
                                            Integer num5 = dataZone2.type;
                                            if (num5 == null || num5.intValue() != 0) {
                                                Float f3 = dataZone2.setTemp;
                                                if (f3 != null && !Intrinsics.areEqual(f3, 16.0f)) {
                                                    if (dataAirconSystem2 == null) {
                                                        DataAirconSystem dataAirconSystem8 = new DataAirconSystem(dataAirconSystem.info.uid);
                                                        DataAirconSystem.update$default(dataAirconSystem8, null, dataAirconSystem, null, null, false, 16, null);
                                                        dataAirconSystem2 = dataAirconSystem8;
                                                    }
                                                    TreeMap<String, DataZone> treeMap7 = dataAirconSystem2.zones;
                                                    Intrinsics.checkNotNull(treeMap7);
                                                    DataZone dataZone4 = treeMap7.get(str6);
                                                    if (dataZone4 != null) {
                                                        dataZone4.setTemp = Float.valueOf(16.0f);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (dataAirconSystem2 != null) {
                                    dataAirconSystem2 = b0(dataAirconSystem2, dataAirconSystem);
                                }
                            } else {
                                DataAirconInfo dataAirconInfo5 = dataAirconSystem.info;
                                Boolean bool3 = Boolean.FALSE;
                                dataAirconInfo5.quietNightModeIsRunning = bool3;
                                DataAirconSystem i02 = i0(dataAirconSystem, null, bool3);
                                if (i02 != null) {
                                    DataAirconSystem dataAirconSystem9 = new DataAirconSystem(dataAirconSystem.info.uid);
                                    DataAirconSystem.update$default(dataAirconSystem9, null, dataAirconSystem, null, null, false, 16, null);
                                    DataAirconSystem.update$default(dataAirconSystem9, null, i02, null, null, false, 16, null);
                                    dataAirconSystem2 = b0(dataAirconSystem9, dataAirconSystem);
                                }
                                dataMaster = dataMaster2;
                            }
                            if (dataAirconSystem2 != null) {
                                TreeMap<String, DataZone> treeMap8 = dataAirconSystem2.zones;
                                Intrinsics.checkNotNull(treeMap8);
                                if (treeMap8.size() > 0) {
                                    TreeMap<String, DataZone> treeMap9 = dataAirconSystem2.zones;
                                    Intrinsics.checkNotNull(treeMap9);
                                    for (String str7 : treeMap9.keySet()) {
                                        TreeMap<String, DataZone> treeMap10 = dataAirconSystem2.zones;
                                        Intrinsics.checkNotNull(treeMap10);
                                        DataZone dataZone5 = treeMap10.get(str7);
                                        if (dataZone5 != null && dataZone5.value != null) {
                                            String encodeZone_j = HandlerCB.Companion.getInstance().encodeZone_j(dataAirconSystem.info.uid, dataZone5);
                                            HandlerCan.Companion.getInstance().sendCanMessageToCB(context, encodeZone_j);
                                            Timber.forest.d("Sending JZ10(2) for UID - " + dataAirconSystem.info.uid + " - zone " + dataZone5.number + ", " + dataZone5.value + "% :" + encodeZone_j, new Object[0]);
                                        }
                                    }
                                }
                                DataAirconSystem b02 = b0(dataAirconSystem2, dataAirconSystem);
                                Intrinsics.checkNotNull(b02);
                                HandlerCan.Companion.getInstance().sendCanMessageToCB(context, ecodeAirconSource(b02.info));
                                b02.info.uid = dataAirconSystem.info.uid;
                                Xml2JsonFunctions.Companion.getInstance().sendMessages(context, b02);
                            }
                        }
                    } else {
                        dataMaster = dataMaster2;
                        dataAirconSystem.info.quietNightModeIsRunning = Boolean.FALSE;
                    }
                }
                dataMaster2 = dataMaster;
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:77:0x00e3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void u(@NotNull Context context) {
        boolean z7;
        int i10;
        List emptyList;
        HandlerAircon handlerAircon;
        boolean z10;
        DataAirconSystem dataAirconSystem;
        HandlerAircon handlerAircon2 = this;
        Intrinsics.checkNotNullParameter(context, "context");
        Calendar calendar = Calendar.getInstance();
        SnapShot.a aVar = SnapShot.Companion;
        Intrinsics.checkNotNull(calendar);
        int currentMinutesOfTheDay = aVar.getCurrentMinutesOfTheDay(calendar);
        int i11 = calendar.get(7);
        synchronized (MyMasterData.class) {
            try {
                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
                AirconMode airconMode = null;
                FirebaseComms firebaseComms = (FirebaseComms) KoinJavaComponent.get$default(FirebaseComms.class, null, null, 6, null);
                if (dataMaster.aircons.size() > 0) {
                    String firstKey = dataMaster.aircons.firstKey();
                    Intrinsics.checkNotNull(firstKey);
                    z7 = AirconFunctions.Companion.isDualZoneDevice(dataMaster.aircons.get(firstKey));
                } else {
                    z7 = false;
                }
                StringBuilder sb = new StringBuilder();
                ArrayList<String> arrayList = dataMaster.myScenes.scenesOrder;
                Intrinsics.checkNotNull(arrayList);
                Iterator<String> it = arrayList.iterator();
                boolean z11 = false;
                boolean z12 = true;
                while (it.hasNext()) {
                    String next = it.next();
                    if (DataScene.Companion.sceneIsScheduledAtGivenTime(dataMaster.myScenes.getScene(next), i11, currentMinutesOfTheDay) == SnapShot.b.stopTimeIsScheduled) {
                        if (z12) {
                            z12 = false;
                        } else {
                            sb.append(", ");
                        }
                        sb.append(next);
                        z11 = true;
                    }
                }
                if (!z11 || z7) {
                    i10 = i11;
                } else {
                    try {
                        Collection<DataAirconSystem> values = dataMaster.aircons.values();
                        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                        ArrayList arrayList2 = new ArrayList();
                        for (DataAirconSystem dataAirconSystem2 : values) {
                            if (dataAirconSystem2 != null) {
                                arrayList2.add(dataAirconSystem2);
                            }
                        }
                        Iterator it2 = arrayList2.iterator();
                        while (it2.hasNext()) {
                            DataAirconSystem dataAirconSystem3 = (DataAirconSystem) it2.next();
                            Boolean bool = dataAirconSystem3.info.quietNightModeIsRunning;
                            if (bool != null) {
                                Intrinsics.checkNotNull(bool);
                                if (bool.booleanValue()) {
                                    DataAirconInfo dataAirconInfo = dataAirconSystem3.info;
                                    Boolean bool2 = Boolean.FALSE;
                                    dataAirconInfo.quietNightModeIsRunning = bool2;
                                    dataAirconSystem = handlerAircon2.i0(dataAirconSystem3, airconMode, bool2);
                                } else {
                                    dataAirconSystem = airconMode;
                                }
                            }
                            DataAirconSystem dataAirconSystem4 = new DataAirconSystem();
                            DataAirconSystem.update$default(dataAirconSystem4, null, dataAirconSystem3, null, null, false, 16, null);
                            if (dataAirconSystem != null) {
                                DataAirconSystem.update$default(dataAirconSystem4, null, dataAirconSystem, null, null, false, 16, null);
                            }
                            dataAirconSystem4.info.state = SystemState.off;
                            DataAirconSystem b02 = handlerAircon2.b0(dataAirconSystem4, dataAirconSystem3);
                            Intrinsics.checkNotNull(b02);
                            String ecodeAirconSource = handlerAircon2.ecodeAirconSource(b02.info);
                            HandlerCan companion = HandlerCan.Companion.getInstance();
                            companion.sendCanMessageToCB(context, ecodeAirconSource);
                            if (dataAirconSystem != null) {
                                TreeMap<String, DataZone> treeMap = dataAirconSystem4.zones;
                                Intrinsics.checkNotNull(treeMap);
                                if (treeMap.size() > 0) {
                                    TreeMap<String, DataZone> treeMap2 = dataAirconSystem4.zones;
                                    Intrinsics.checkNotNull(treeMap2);
                                    Iterator<String> it3 = treeMap2.keySet().iterator();
                                    while (it3.hasNext()) {
                                        String next2 = it3.next();
                                        Iterator it4 = it2;
                                        TreeMap<String, DataZone> treeMap3 = dataAirconSystem4.zones;
                                        Intrinsics.checkNotNull(treeMap3);
                                        DataZone dataZone = treeMap3.get(next2);
                                        if (dataZone != null) {
                                            Integer num = dataZone.type;
                                            if (num != null) {
                                                if (num != null && num.intValue() == 0) {
                                                    dataZone.value = 100;
                                                } else {
                                                    dataZone.setTemp = Float.valueOf(16.0f);
                                                }
                                            }
                                            DataAirconSystem dataAirconSystem5 = dataAirconSystem4;
                                            String encodeZone_j = HandlerCB.Companion.getInstance().encodeZone_j(dataAirconSystem3.info.uid, dataZone);
                                            companion.sendCanMessageToCB(context, encodeZone_j);
                                            Iterator<String> it5 = it3;
                                            HandlerCan handlerCan = companion;
                                            Timber.forest.d("Sending JZ10(1) for UID - " + dataAirconSystem3.info.uid + " - zone " + dataZone.number + ", " + dataZone.value + "% :" + encodeZone_j, new Object[0]);
                                            dataAirconSystem4 = dataAirconSystem5;
                                            it3 = it5;
                                            companion = handlerCan;
                                            it2 = it4;
                                            i11 = i11;
                                        } else {
                                            it2 = it4;
                                        }
                                    }
                                }
                            }
                            int i12 = i11;
                            Iterator it6 = it2;
                            DataAirconSystem dataAirconSystem6 = new DataAirconSystem();
                            if (dataAirconSystem != null) {
                                DataAirconSystem.update$default(dataAirconSystem6, null, b02, null, null, false, 16, null);
                            }
                            DataAirconInfo dataAirconInfo2 = dataAirconSystem6.info;
                            dataAirconInfo2.uid = dataAirconSystem3.info.uid;
                            dataAirconInfo2.state = SystemState.off;
                            Xml2JsonFunctions.Companion.getInstance().sendMessages(context, dataAirconSystem6);
                            handlerAircon2 = this;
                            it2 = it6;
                            i11 = i12;
                            airconMode = null;
                        }
                        i10 = i11;
                        firebaseComms.parseUrl("Scene stop time processed for sceneId: " + ((Object) sb), ExifInterface.GPS_DIRECTION_TRUE);
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                }
                ArrayList<String> arrayList3 = dataMaster.myScenes.scenesOrder;
                if (arrayList3 != null) {
                    emptyList = new ArrayList();
                    for (String str : arrayList3) {
                        if (str != null) {
                            emptyList.add(str);
                        }
                    }
                } else {
                    emptyList = CollectionsKt.emptyList();
                }
                Iterator it7 = emptyList.iterator();
                while (it7.hasNext()) {
                    DataScene scene = dataMaster.myScenes.getScene((String) it7.next());
                    int i13 = i10;
                    if (DataScene.Companion.sceneIsScheduledAtGivenTime(scene, i13, currentMinutesOfTheDay) == SnapShot.b.startTimeIsScheduled) {
                        HandlerLights companion2 = HandlerLights.Companion.getInstance();
                        DataScene V = companion2.V(context, companion2.f0(context, scene, dataMaster), dataMaster);
                        String L = companion2.L(context, V);
                        if (companion2.o0(context, L, V, dataMaster)) {
                            Timber.forest.d("checkAndRunSceneSchedule - sending out scene CAN msgs from Scheduler: " + L, new Object[0]);
                            z10 = true;
                            handlerAircon = this;
                        } else {
                            handlerAircon = this;
                            z10 = false;
                        }
                        try {
                            boolean checkAndRunSceneSchedule = handlerAircon.checkAndRunSceneSchedule(V);
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Scene start, ");
                            if ((V != null ? V.id : null) != null) {
                                sb2.append(V.id);
                                sb2.append(" - ");
                            } else {
                                sb2.append("null scene.id - ");
                            }
                            if ((V != null ? V.name : null) != null) {
                                sb2.append(V.name);
                            } else {
                                sb2.append("null scene.name");
                            }
                            if (z10) {
                                sb2.append(", sent CAN");
                            }
                            if (checkAndRunSceneSchedule) {
                                sb2.append(", sent XML");
                            }
                            String sb3 = sb2.toString();
                            Intrinsics.checkNotNullExpressionValue(sb3, "toString(...)");
                            firebaseComms.parseUrl(sb3, ExifInterface.GPS_DIRECTION_TRUE);
                        } catch (Throwable th2) {
                            th = th2;
                            throw th;
                        }
                    }
                    i10 = i13;
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th3) {
                th = th3;
            }
        }
    }

    public final boolean u0(@Nullable DataAirconSystem dataAirconSystem, @Nullable DataAirconInfo dataAirconInfo) {
        if (dataAirconSystem == null) {
            return false;
        }
        DataAirconInfo dataAirconInfo2 = dataAirconSystem.info;
        Intrinsics.checkNotNull(dataAirconInfo);
        if (!DataAirconInfo.update$default(dataAirconInfo2, dataAirconInfo, null, false, 4, null)) {
            return false;
        }
        AirconDBStore.Companion.getInstance(this.context).updateStore(this.context, dataAirconSystem.info.uid, dataAirconSystem);
        return true;
    }

    public final boolean v(@NotNull DataMaster masterData) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        DataSystem dataSystem = new DataSystem();
        dataSystem.needsUpdate = Boolean.valueOf(!lock.get() && ThreadGetVersions.Companion.getThreadGetVersions(this.context).I(this.context));
        return update(masterData, dataSystem);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01a7  */
    @VisibleForTesting(otherwise = 2)
    @NotNull
    /* renamed from: v0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String updateAirconState(@NotNull DataAirconInfo dataAirconInfo, @Nullable String str) {
        String str2;
        Intrinsics.checkNotNullParameter(dataAirconInfo, "dataAirconInfo");
        Intrinsics.checkNotNull(str);
        String extractUIDValue = extractUIDValue(str, 4);
        if (extractUIDValue == null) {
            str2 = "Rejected CB status message - invalid UID\n";
        } else {
            str2 = "";
        }
        Integer parseHexToInt = parseHexToInt(str, 11);
        if (parseHexToInt == null || parseHexToInt.intValue() > 1 || parseHexToInt.intValue() < 0) {
            str2 = str2 + "Rejected CB status message - invalid systemON\n";
        }
        Integer parseHexToInt2 = parseHexToInt(str, 13);
        if (parseHexToInt2 == null || parseHexToInt2.intValue() > 5 || parseHexToInt2.intValue() < 0) {
            str2 = str2 + "Rejected CB status message - invalid systemMODE\n";
        }
        Integer parseHexToInt3 = parseHexToInt(str, 15);
        if (parseHexToInt3 == null || parseHexToInt3.intValue() > 5) {
            str2 = str2 + "Rejected CB status message - invalid systemFAN\n";
        }
        Integer parseHexToInt4 = parseHexToInt(str, 17);
        float intValue = parseHexToInt4 != null ? parseHexToInt4.intValue() / 2 : 0.0f;
        if (parseHexToInt4 == null || parseHexToInt4.intValue() > 80) {
            str2 = str2 + "Rejected CB status message - invalid setTemp\n";
        }
        Integer parseHexToInt5 = parseHexToInt(str, 19);
        if (parseHexToInt5 == null || parseHexToInt5.intValue() > 10) {
            str2 = str2 + "Rejected CB status message - invalid unitControlTempSet\n";
        }
        Integer parseHexToInt6 = parseHexToInt(str, 21);
        if (parseHexToInt6 == null || parseHexToInt6.intValue() > 2) {
            str2 = str2 + "Rejected CB status message - invalid freshAirStatus\n";
        }
        Integer parseHexToInt7 = parseHexToInt(str, 23);
        if (parseHexToInt7 == null || parseHexToInt7.intValue() > 16) {
            str2 = str2 + "Rejected CB status message - invalid rfSysID\n";
        }
        if (Intrinsics.areEqual(str2, "")) {
            String str3 = dataAirconInfo.uid;
            if (str3 == null || !Intrinsics.areEqual(str3, extractUIDValue)) {
                dataAirconInfo.uid = extractUIDValue;
            }
            if (parseHexToInt != null) {
                SystemState systemState = dataAirconInfo.state;
                if (systemState != null) {
                    Intrinsics.checkNotNull(systemState);
                    if (parseHexToInt.intValue() != systemState.getValue()) {
                        SystemState systemState2 = SystemState.off;
                        if (parseHexToInt.intValue() == systemState2.getValue()) {
                            dataAirconInfo.state = systemState2;
                        } else {
                            dataAirconInfo.state = SystemState.on;
                        }
                    }
                }
            }
            if (parseHexToInt2 != null) {
                AirconMode airconMode = dataAirconInfo.mode;
                if (airconMode != null) {
                    Intrinsics.checkNotNull(airconMode);
                    if (parseHexToInt2.intValue() != airconMode.getValue()) {
                        int intValue2 = parseHexToInt2.intValue();
                        if (intValue2 == 1) {
                            dataAirconInfo.mode = AirconMode.cool;
                        } else if (intValue2 == 2) {
                            dataAirconInfo.mode = AirconMode.heat;
                        } else if (intValue2 == 3) {
                            dataAirconInfo.mode = AirconMode.vent;
                        } else if (intValue2 != 5) {
                            dataAirconInfo.mode = AirconMode.auto;
                        } else {
                            dataAirconInfo.mode = AirconMode.dry;
                        }
                    }
                }
            }
            if (parseHexToInt3 != null) {
                FanStatus fanStatus = dataAirconInfo.fan;
                if (fanStatus != null) {
                    Intrinsics.checkNotNull(fanStatus);
                    if (parseHexToInt3.intValue() != fanStatus.getValue()) {
                        int intValue3 = parseHexToInt3.intValue();
                        if (intValue3 == 0) {
                            dataAirconInfo.fan = FanStatus.off;
                        } else if (intValue3 == 1) {
                            dataAirconInfo.fan = FanStatus.low;
                        } else if (intValue3 == 2) {
                            dataAirconInfo.fan = FanStatus.medium;
                        } else if (intValue3 == 3) {
                            dataAirconInfo.fan = FanStatus.high;
                        } else if (intValue3 != 4) {
                            dataAirconInfo.fan = FanStatus.autoAA;
                        } else {
                            dataAirconInfo.fan = FanStatus.auto;
                        }
                    }
                }
            }
            if (!Intrinsics.areEqual(intValue, dataAirconInfo.setTemp)) {
                dataAirconInfo.setTemp = Float.valueOf(intValue);
            }
            Integer num = dataAirconInfo.myZone;
            if (num == null || !Intrinsics.areEqual(num, parseHexToInt5)) {
                dataAirconInfo.myZone = parseHexToInt5;
            }
            if (parseHexToInt6 != null) {
                DataAirconSystem.FreshAirStatus freshAirStatus = dataAirconInfo.freshAirStatus;
                if (freshAirStatus != null) {
                    Intrinsics.checkNotNull(freshAirStatus);
                    if (freshAirStatus.value != parseHexToInt6.intValue()) {
                        int intValue4 = parseHexToInt6.intValue();
                        if (intValue4 == 0) {
                            dataAirconInfo.freshAirStatus = DataAirconSystem.FreshAirStatus.none;
                        } else if (intValue4 != 1) {
                            dataAirconInfo.freshAirStatus = DataAirconSystem.FreshAirStatus.on;
                        } else {
                            dataAirconInfo.freshAirStatus = DataAirconSystem.FreshAirStatus.off;
                        }
                    }
                }
            }
            Integer num2 = dataAirconInfo.rfSysID;
            if (num2 == null || !Intrinsics.areEqual(num2, parseHexToInt7)) {
                dataAirconInfo.rfSysID = parseHexToInt7;
            }
        } else {
            Timber.forest.d(str2, new Object[0]);
        }
        return str2;
    }

    public final void w(@NotNull Context context) {
        boolean z7;
        Intrinsics.checkNotNullParameter(context, "context");
        Calendar calendar = Calendar.getInstance();
        SnapShot.a aVar = SnapShot.Companion;
        Intrinsics.checkNotNull(calendar);
        int currentMinutesOfTheDay = aVar.getCurrentMinutesOfTheDay(calendar);
        int i10 = calendar.get(7);
        synchronized (MyMasterData.class) {
            try {
                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
                DataAirconSystem dataAirconSystem = null;
                FirebaseComms firebaseComms = (FirebaseComms) KoinJavaComponent.get$default(FirebaseComms.class, null, null, 6, null);
                boolean z10 = false;
                if (dataMaster.aircons.size() > 0) {
                    String firstKey = dataMaster.aircons.firstKey();
                    Intrinsics.checkNotNull(firstKey);
                    z7 = AirconFunctions.Companion.isDualZoneDevice(dataMaster.aircons.get(firstKey));
                } else {
                    z7 = false;
                }
                int i11 = SnapShot.MAX_NO_SNAPSHOTS;
                if (1 <= i11) {
                    boolean z11 = false;
                    int i12 = 1;
                    while (true) {
                        SnapShot snapShot = dataMaster.snapshots.get("p" + i12);
                        if (snapShot != null) {
                            Intrinsics.checkNotNull(snapShot);
                            SnapShot.b snapshotIsScheduledAtGivenTime = snapShot.snapshotIsScheduledAtGivenTime(i10, currentMinutesOfTheDay);
                            if (snapshotIsScheduledAtGivenTime == SnapShot.b.startTimeIsScheduled) {
                                Timber.forest.d("DBG DB sending out CAN msgs from Scheduler", new Object[0]);
                                ArrayList<String> arrayList = snapShot.CANmsgs;
                                if (arrayList != null) {
                                    Intrinsics.checkNotNull(arrayList);
                                    Iterator<String> it = arrayList.iterator();
                                    while (it.hasNext()) {
                                        HandlerCan.Companion.getInstance().sendCanMessageToCB(context, it.next());
                                    }
                                }
                                TreeMap<String, DataAirconSystem> treeMap = snapShot.aircons;
                                Intrinsics.checkNotNull(treeMap);
                                if (treeMap.size() > 0) {
                                    TreeMap<String, DataAirconSystem> treeMap2 = snapShot.aircons;
                                    if (treeMap2 != null) {
                                        Intrinsics.checkNotNull(treeMap2);
                                        dataAirconSystem = treeMap2.get(treeMap2.firstKey());
                                    }
                                    if (dataAirconSystem != null) {
                                        Xml2JsonFunctions.Companion.getInstance().sendMessages(context, dataAirconSystem);
                                        Timber.forest.d("DBG DB alarm event running snapshot id:" + snapShot.snapshotId, new Object[0]);
                                    }
                                    firebaseComms.parseUrl("Aircon plan start, " + snapShot.snapshotId + " - " + snapShot.name, ExifInterface.GPS_DIRECTION_TRUE);
                                } else {
                                    firebaseComms.parseUrl("WARNING! Aircon plan start but aircons size is 0 (XML), " + snapShot.snapshotId + " - " + snapShot.name, ExifInterface.GPS_DIRECTION_TRUE);
                                }
                                return;
                            }
                            if (snapshotIsScheduledAtGivenTime == SnapShot.b.stopTimeIsScheduled) {
                                if (!z7) {
                                    firebaseComms.parseUrl("Aircon plan stop, " + snapShot.snapshotId + " - " + snapShot.name, ExifInterface.GPS_DIRECTION_TRUE);
                                }
                                z11 = true;
                            }
                        }
                        if (i12 == i11) {
                            z10 = z11;
                            break;
                        }
                        i12++;
                    }
                }
                if (z10 && !z7) {
                    Collection<DataAirconSystem> values = dataMaster.aircons.values();
                    Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                    ArrayList<DataAirconSystem> arrayList2 = new ArrayList();
                    for (DataAirconSystem dataAirconSystem2 : values) {
                        if (dataAirconSystem2 != null) {
                            arrayList2.add(dataAirconSystem2);
                        }
                    }
                    for (DataAirconSystem dataAirconSystem3 : arrayList2) {
                        DataAirconSystem dataAirconSystem4 = new DataAirconSystem();
                        DataAirconSystem.update$default(dataAirconSystem4, null, dataAirconSystem4, null, null, false, 16, null);
                        DataAirconInfo dataAirconInfo = dataAirconSystem4.info;
                        SystemState systemState = SystemState.off;
                        dataAirconInfo.state = systemState;
                        try {
                            HandlerCan.Companion.getInstance().sendCanMessageToCB(context, ecodeAirconSource(dataAirconInfo));
                            DataAirconSystem dataAirconSystem5 = new DataAirconSystem();
                            DataAirconInfo dataAirconInfo2 = dataAirconSystem5.info;
                            dataAirconInfo2.uid = dataAirconInfo2.uid;
                            dataAirconInfo2.state = systemState;
                            Xml2JsonFunctions.Companion.getInstance().sendMessages(context, dataAirconSystem5);
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    @VisibleForTesting(otherwise = 2)
    @NotNull
    /* renamed from: w0 */
    public final String updateVersionInfo(@NotNull DataAirconInfo airconToUpdate, @NotNull String message) {
        String str;
        Intrinsics.checkNotNullParameter(airconToUpdate, "airconToUpdate");
        Intrinsics.checkNotNullParameter(message, "message");
        String extractUIDValue = extractUIDValue(message, 4);
        if (extractUIDValue == null) {
            str = "Rejected CB status message - invalid UID\n";
        } else {
            str = "";
        }
        Integer parseHexToInt = parseHexToInt(message, 11);
        if (parseHexToInt == null) {
            str = str + "Rejected CB status message - invalid CB FW Major\n";
        }
        Integer parseHexToInt2 = parseHexToInt(message, 13);
        if (parseHexToInt2 == null) {
            str = str + "Rejected CB status message - invalid CB FW Minor\n";
        }
        Integer parseHexToInt3 = parseHexToInt(message, 15);
        if (parseHexToInt3 == null) {
            str = str + "Rejected CB status message - invalid CB Type\n";
        }
        Integer parseHexToInt4 = parseHexToInt(message, 17);
        if (parseHexToInt4 == null) {
            str = str + "Rejected CB status message - invalid RF FW Major\n";
        }
        if (Intrinsics.areEqual(str, "")) {
            String str2 = airconToUpdate.uid;
            if (str2 == null || !Intrinsics.areEqual(str2, extractUIDValue)) {
                airconToUpdate.uid = extractUIDValue;
            }
            Integer num = airconToUpdate.cbFWRevMajor;
            if (num == null || !Intrinsics.areEqual(num, parseHexToInt)) {
                airconToUpdate.cbFWRevMajor = parseHexToInt;
            }
            Integer num2 = airconToUpdate.cbFWRevMinor;
            if (num2 == null || !Intrinsics.areEqual(num2, parseHexToInt2)) {
                airconToUpdate.cbFWRevMinor = parseHexToInt2;
            }
            Integer num3 = airconToUpdate.cbType;
            if (num3 == null || !Intrinsics.areEqual(num3, parseHexToInt3)) {
                airconToUpdate.cbType = parseHexToInt3;
            }
            Integer num4 = airconToUpdate.rfFWRevMajor;
            if (num4 == null || !Intrinsics.areEqual(num4, parseHexToInt4)) {
                airconToUpdate.rfFWRevMajor = parseHexToInt4;
            }
        } else {
            Timber.forest.d(str, new Object[0]);
        }
        return str;
    }

    public final void x(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Calendar calendar = Calendar.getInstance();
        SnapShot.a aVar = SnapShot.Companion;
        Intrinsics.checkNotNull(calendar);
        int currentMinutesOfTheDay = aVar.getCurrentMinutesOfTheDay(calendar);
        int i10 = calendar.get(7);
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            if (this.sunsetCalendar != null) {
                FirebaseComms firebaseComms = (FirebaseComms) KoinJavaComponent.get$default(FirebaseComms.class, null, null, 6, null);
                DataScene scene = dataMaster.myScenes.getScene(DataMyScene.SCENE_MY_SUNSET.id);
                if (scene != null) {
                    Calendar calendar2 = this.sunsetCalendar;
                    Intrinsics.checkNotNull(calendar2);
                    scene.startTime = Integer.valueOf(aVar.getCurrentMinutesOfTheDay(calendar2));
                    scene.activeDays = 127;
                    if (DataScene.Companion.sceneIsScheduledAtGivenTime(scene, i10, currentMinutesOfTheDay) == SnapShot.b.startTimeIsScheduled) {
                        HandlerLights companion = HandlerLights.Companion.getInstance();
                        DataScene V = companion.V(context, companion.f0(context, scene, dataMaster), dataMaster);
                        String L = companion.L(context, V);
                        boolean o02 = companion.o0(context, L, V, dataMaster);
                        boolean z7 = false;
                        if (o02) {
                            Timber.forest.d("checkSunsetSchedule - sending out scene CAN msgs from Scheduler: " + L, new Object[0]);
                            z7 = true;
                        }
                        boolean checkAndRunSceneSchedule = checkAndRunSceneSchedule(V);
                        StringBuilder sb = new StringBuilder();
                        sb.append("Sunset Scene start");
                        if (z7) {
                            sb.append(", CAN msg sent");
                        }
                        if (checkAndRunSceneSchedule) {
                            sb.append(", XML msg sent");
                        }
                        String sb2 = sb.toString();
                        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
                        firebaseComms.parseUrl(sb2, ExifInterface.GPS_DIRECTION_TRUE);
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void y(@Nullable Context context) {
        boolean z7;
        boolean z10;
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            Timber.forest.d("DBG checkTimer called", new Object[0]);
            boolean z11 = false;
            for (String str : dataMaster.aircons.keySet()) {
                DataAirconSystem dataAirconSystem = dataMaster.aircons.get(str);
                Intrinsics.checkNotNull(dataAirconSystem);
                DataAirconSystem dataAirconSystem2 = new DataAirconSystem(dataAirconSystem.info.uid);
                Integer num = dataAirconSystem.info.countDownToOn;
                if (num != null) {
                    Intrinsics.checkNotNull(num);
                    if (num.intValue() > 0) {
                        Integer num2 = dataAirconSystem.info.countDownToOn;
                        if (num2 != null && num2.intValue() == 1) {
                            dataAirconSystem2.info.state = SystemState.on;
                            z10 = true;
                        } else {
                            z10 = false;
                        }
                        DataAirconInfo dataAirconInfo = dataAirconSystem2.info;
                        Integer num3 = dataAirconSystem.info.countDownToOn;
                        Intrinsics.checkNotNull(num3);
                        dataAirconInfo.countDownToOn = Integer.valueOf(num3.intValue() - 1);
                        z7 = z10;
                        z11 = true;
                    } else {
                        z7 = false;
                    }
                    Integer num4 = dataAirconSystem.info.countDownToOff;
                    if (num4 != null) {
                        Intrinsics.checkNotNull(num4);
                        if (num4.intValue() > 0) {
                            Integer num5 = dataAirconSystem.info.countDownToOff;
                            if (num5 != null && num5.intValue() == 1) {
                                dataAirconSystem2.info.state = SystemState.off;
                                z7 = true;
                            }
                            DataAirconInfo dataAirconInfo2 = dataAirconSystem2.info;
                            Integer num6 = dataAirconSystem.info.countDownToOff;
                            Intrinsics.checkNotNull(num6);
                            dataAirconInfo2.countDownToOff = Integer.valueOf(num6.intValue() - 1);
                            z11 = true;
                        }
                    }
                    if (z7) {
                        try {
                            sendUpdateToCBUnit(dataMaster, dataAirconSystem2, str, false);
                        } catch (ExceptionUart e7) {
                            AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
                        }
                        ((FirebaseComms) KoinJavaComponent.get$default(FirebaseComms.class, null, null, 6, null)).parseUrl("Aircon timer triggered.", ExifInterface.GPS_DIRECTION_TRUE);
                    }
                    DataAirconInfo.update$default(dataAirconSystem.info, dataAirconSystem2.info, null, false, 4, null);
                }
            }
            if (z11) {
                HandlerJson.Companion.getInstance(context).processData(dataMaster, "Timer");
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    @VisibleForTesting(otherwise = 2)
    @NotNull
    /* renamed from: z0 */
    public final String ZoneMessage(@NotNull DataAirconInfo airconToUpdate, @Nullable String str) {
        String str2;
        Intrinsics.checkNotNullParameter(airconToUpdate, "airconToUpdate");
        Intrinsics.checkNotNull(str);
        String extractUIDValue = extractUIDValue(str, 4);
        if (extractUIDValue == null) {
            str2 = "Rejected CB status message - invalid UID\n";
        } else {
            str2 = "";
        }
        Integer parseHexToInt = parseHexToInt(str, 13);
        if (parseHexToInt == null || parseHexToInt.intValue() > 10 || parseHexToInt.intValue() < 0) {
            str2 = str2 + "Rejected CB status message - invalid number of Zones\n";
        }
        Integer parseHexToInt2 = parseHexToInt(str, 15);
        if (parseHexToInt2 == null || parseHexToInt2.intValue() > 3 || parseHexToInt2.intValue() < 0) {
            str2 = str2 + "Rejected CB status message - invalid number of constants\n";
        }
        Integer parseHexToInt3 = parseHexToInt(str, 17);
        if (parseHexToInt3 == null || parseHexToInt3.intValue() > 10 || parseHexToInt3.intValue() < 0) {
            str2 = str2 + "Rejected CB status message - invalid constant1 value\n";
        }
        Integer parseHexToInt4 = parseHexToInt(str, 19);
        if (parseHexToInt4 == null || parseHexToInt4.intValue() > 10 || parseHexToInt4.intValue() < 0) {
            str2 = str2 + "Rejected CB status message - invalid constant2 value\n";
        }
        Integer parseHexToInt5 = parseHexToInt(str, 21);
        if (parseHexToInt5 == null || parseHexToInt5.intValue() > 10 || parseHexToInt5.intValue() < 0) {
            str2 = str2 + "Rejected CB status message - invalid constant3 value\n";
        }
        if (Intrinsics.areEqual(str2, "")) {
            String str3 = airconToUpdate.uid;
            if (str3 == null || !Intrinsics.areEqual(str3, extractUIDValue)) {
                airconToUpdate.uid = extractUIDValue;
            }
            Integer num = airconToUpdate.noOfZones;
            if (num == null || !Intrinsics.areEqual(num, parseHexToInt)) {
                airconToUpdate.noOfZones = parseHexToInt;
            }
            Integer num2 = airconToUpdate.noOfConstantZones;
            if (num2 == null || !Intrinsics.areEqual(num2, parseHexToInt2)) {
                airconToUpdate.noOfConstantZones = parseHexToInt2;
            }
            Integer num3 = airconToUpdate.constantZone1;
            if (num3 == null || !Intrinsics.areEqual(num3, parseHexToInt3)) {
                airconToUpdate.constantZone1 = parseHexToInt3;
            }
            Integer num4 = airconToUpdate.constantZone2;
            if (num4 == null || !Intrinsics.areEqual(num4, parseHexToInt4)) {
                airconToUpdate.constantZone2 = parseHexToInt4;
            }
            Integer num5 = airconToUpdate.constantZone3;
            if (num5 == null || !Intrinsics.areEqual(num5, parseHexToInt5)) {
                airconToUpdate.constantZone3 = parseHexToInt5;
            }
            Integer num6 = airconToUpdate.filterCleanStatus;
            if (num6 == null || num6 == null || num6.intValue() != 0) {
                airconToUpdate.filterCleanStatus = 0;
            }
        } else {
            Timber.forest.d(str2, new Object[0]);
        }
        return str2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private HandlerAircon(Context context) {
        this.context = context;
        this.myLights = new MyLights();
        this.myLightsV2 = new MyLightsV2();
        this.fanSpeedMap = new TreeMap<>();
        this.gson = (Gson) KoinJavaComponent.get$default(Gson.class, null, null, 6, null);
        this.zone = new ArrayList<>();
    }
}