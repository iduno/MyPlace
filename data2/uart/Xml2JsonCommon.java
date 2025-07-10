package com.air.advantage.uart;

import android.net.Uri;
import com.air.advantage.XMLParser;
import com.air.advantage.data.DataAirconInfo;
import com.air.advantage.data.DataAirconSystem;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataSystem;
import com.air.advantage.data.DataZone;
import com.air.advantage.energymonitoring.FragmentEnergyMonitoring;
import com.air.advantage.libraryairconlightjson.AirconMode;
import com.air.advantage.libraryairconlightjson.FanStatus;
import com.air.advantage.libraryairconlightjson.SystemState;
import com.air.advantage.libraryairconlightjson.TabletInfo;
import com.air.advantage.libraryairconlightjson.ZoneState;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeMap;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

@PurelyImplements({"SMAP\nXml2JsonCommon.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Xml2JsonCommon.kt\ncom/air/advantage/uart/Xml2JsonCommon\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,420:1\n731#2,9:421\n37#3,2:430\n*S KotlinDebug\n*F\n+ 1 Xml2JsonCommon.kt\ncom/air/advantage/uart/Xml2JsonCommon\n*L\n47#1:421,9\n47#1:430,2\n*E\n"})
/* renamed from: com.air.advantage.uart.i0 */
/* loaded from: classes.dex */
public final class Xml2JsonCommon {

    @NotNull
    public static final a a = new a(null);

    /* renamed from: b */
    private static final String f7133b = Xml2JsonCommon.class.getSimpleName();

    /* renamed from: c */
    private static final int f7134c = 2;

    /* renamed from: d */
    private static final int f7135d = 4;

    /* renamed from: e */
    private static final int f7136e = 1;

    /* renamed from: f */
    private static boolean tempSensorNotConfigured;

    /* renamed from: com.air.advantage.uart.i0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.i0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    @NotNull
    /* renamed from: a */
    public final ArrayList<String> getMessageList(@Nullable DataAirconSystem dataAirconSystem, @Nullable Integer num, boolean z7) {
        String str;
        String str2;
        Integer num2;
        ArrayList<String> arrayList = new ArrayList<>();
        if (dataAirconSystem == null) {
            return arrayList;
        }
        DataAirconInfo dataAirconInfo = dataAirconSystem.info;
        SystemState systemState = dataAirconInfo.state;
        if (systemState != null) {
            Intrinsics.checkNotNull(systemState);
            arrayList.add("setSystemData?airconOnOff=" + systemState.getValue());
        }
        AirconMode airconMode = dataAirconInfo.mode;
        if (airconMode != null) {
            Intrinsics.checkNotNull(airconMode);
            arrayList.add("setSystemData?mode=" + airconMode.getValue());
        }
        FanStatus fanStatus = dataAirconInfo.fan;
        if (fanStatus != null) {
            Intrinsics.checkNotNull(fanStatus);
            arrayList.add("setSystemData?fanSpeed=" + fanStatus.getValue());
        }
        Float f3 = dataAirconInfo.setTemp;
        if (f3 != null) {
            arrayList.add("setSystemData?centralDesiredTemp=" + f3);
        }
        Integer num3 = dataAirconInfo.myZone;
        if (num3 != null) {
            arrayList.add("setSystemData?unitControlTempsSetting=" + num3);
        }
        DataAirconSystem.FreshAirStatus freshAirStatus = dataAirconInfo.freshAirStatus;
        if (freshAirStatus != null) {
            Intrinsics.checkNotNull(freshAirStatus);
            arrayList.add("setSystemData?FAstatus=" + freshAirStatus.value);
        }
        if (dataAirconInfo.constantZone1 != null && dataAirconInfo.constantZone2 != null && dataAirconInfo.constantZone3 != null && dataAirconInfo.noOfConstantZones != null && (num2 = dataAirconInfo.noOfZones) != null) {
            arrayList.add("setSystemData?" + ((((("numberOfZones=" + num2) + "&numberOfConstantZones=" + dataAirconInfo.noOfConstantZones) + "&constZone1=" + dataAirconInfo.constantZone1) + "&constZone2=" + dataAirconInfo.constantZone2) + "&constZone3=" + dataAirconInfo.constantZone3));
        }
        TreeMap<String, DataZone> treeMap = dataAirconSystem.zones;
        Intrinsics.checkNotNull(treeMap);
        if (treeMap.size() > 0) {
            TreeMap<String, DataZone> treeMap2 = dataAirconSystem.zones;
            Intrinsics.checkNotNull(treeMap2);
            for (String str3 : treeMap2.keySet()) {
                TreeMap<String, DataZone> treeMap3 = dataAirconSystem.zones;
                Intrinsics.checkNotNull(treeMap3);
                DataZone dataZone = treeMap3.get(str3);
                DataZone dataZone2 = new DataZone(str3);
                DataZone.update$default(dataZone2, dataZone, null, null, false, 12, null);
                if (z7 && (str2 = dataZone2.name) != null) {
                    Intrinsics.checkNotNull(str2);
                    String replace = new Regex("&").replace(new Regex("[^A-Za-z0-9 .&]").replace(str2, ""), "\\+");
                    arrayList.add("setZoneData?zone=" + dataZone2.number + "&name=" + Uri.encode(replace));
                }
                ZoneState zoneState = dataZone2.state;
                if (zoneState != null) {
                    Integer num4 = dataZone2.number;
                    Intrinsics.checkNotNull(zoneState);
                    arrayList.add("setZoneData?zone=" + num4 + "&zoneSetting=" + zoneState.getValue());
                }
                Float f7 = dataZone2.setTemp;
                if (f7 != null) {
                    arrayList.add("setZoneData?zone=" + dataZone2.number + "&desiredTemp=" + f7);
                }
                Integer num5 = dataZone2.value;
                if (num5 != null) {
                    arrayList.add("setZoneData?zone=" + dataZone2.number + "&userPercentSetting=" + num5);
                }
                Integer num6 = dataZone2.motionConfig;
                if (num6 != null) {
                    arrayList.add("setZoneData?zone=" + dataZone2.number + "&isMotionEnabled=" + num6);
                }
                Integer num7 = dataZone2.minDamper;
                boolean z10 = num7 != null;
                Integer num8 = dataZone2.maxDamper;
                if ((num8 != null) & z10) {
                    arrayList.add("setZoneData?zone=" + dataZone2.number + "&maxDamper=" + num8 + "&minDamper=" + num7);
                }
            }
        }
        DataAirconSystem.ActivationCode activationCode = dataAirconInfo.setActivationCode;
        if (activationCode != null && (str = dataAirconInfo.unlockCode) != null) {
            if (activationCode == DataAirconSystem.ActivationCode.setNewCode) {
                arrayList.add("setSystemData?activationCode=" + str + "&activationCodeStatus=1");
            } else if (activationCode == DataAirconSystem.ActivationCode.unlock) {
                arrayList.add("setSystemData?activationCode=" + str + "&activationCodeStatus=0");
            }
        }
        return arrayList;
    }

    @NotNull
    /* renamed from: b */
    public final ArrayList<String> getSystemDataMessages(@Nullable DataSystem dataSystem) {
        String str;
        Timber.forest.d("generating system xml from json", new Object[0]);
        ArrayList<String> arrayList = new ArrayList<>();
        if (dataSystem != null && (str = dataSystem.name) != null) {
            Intrinsics.checkNotNull(str);
            arrayList.add("changeName?name=" + Uri.encode(new Regex("&").replace(new Regex("[^A-Za-z0-9 .&]").replace(str, ""), "\\+")));
        }
        return arrayList;
    }

    @NotNull
    /* renamed from: c */
    public final DataMaster dataMasterFromXml(@Nullable String str, @NotNull byte[] message) {
        String str2;
        Integer num;
        List emptyList;
        Intrinsics.checkNotNullParameter(message, "message");
        DataAirconInfo dataAirconInfo = new DataAirconInfo();
        DataSystem dataSystem = new DataSystem();
        XMLParser xMLParser = new XMLParser();
        dataAirconInfo.uid = str;
        dataSystem.mid = str;
        Charset charset = Charsets.UTF_8;
        byte[] bytes = AppMeasurementSdk.ConditionalUserProperty.NAME.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        String stringElement = xMLParser.getStringElement(message, bytes);
        dataAirconInfo.name = stringElement;
        Intrinsics.checkNotNull(stringElement);
        String replace = new Regex("\\+").replace(stringElement, "&");
        dataAirconInfo.name = replace;
        dataSystem.name = replace;
        try {
            byte[] bytes2 = "CBrev".getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(bytes2, "this as java.lang.String).getBytes(charset)");
            str2 = xMLParser.getStringElement(message, bytes2);
        } catch (IllegalArgumentException unused) {
            Timber.forest.d("Failed to get cb version", new Object[0]);
            str2 = null;
        }
        if (str2 != null) {
            List<String> split = new Regex("\\.").split(str2, 0);
            if (split.isEmpty()) {
                emptyList = CollectionsKt.emptyList();
                String[] strArr = (String[]) emptyList.toArray(new String[0]);
                dataAirconInfo.cbFWRevMajor = Integer.valueOf(strArr[0]);
                dataAirconInfo.cbFWRevMinor = Integer.valueOf(strArr[1]);
            } else {
                ListIterator<String> listIterator = split.listIterator(split.size());
                while (listIterator.hasPrevious()) {
                    if (!(listIterator.previous().length() == 0)) {
                        emptyList = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                        break;
                    }
                }
                emptyList = CollectionsKt.emptyList();
                String[] strArr2 = (String[]) emptyList.toArray(new String[0]);
                try {
                    dataAirconInfo.cbFWRevMajor = Integer.valueOf(strArr2[0]);
                    dataAirconInfo.cbFWRevMinor = Integer.valueOf(strArr2[1]);
                } catch (NumberFormatException unused2) {
                }
            }
        }
        Charset charset2 = Charsets.UTF_8;
        byte[] bytes3 = "zoneStationHasUnitControl".getBytes(charset2);
        Intrinsics.checkNotNullExpressionValue(bytes3, "this as java.lang.String).getBytes(charset)");
        dataAirconInfo.unitType = Integer.valueOf(xMLParser.getIntElement(message, bytes3));
        byte[] bytes4 = "airconOnOff".getBytes(charset2);
        Intrinsics.checkNotNullExpressionValue(bytes4, "this as java.lang.String).getBytes(charset)");
        if (xMLParser.getBooleanElement(message, bytes4)) {
            dataAirconInfo.state = SystemState.on;
        } else {
            dataAirconInfo.state = SystemState.off;
        }
        byte[] bytes5 = "fanSpeed".getBytes(charset2);
        Intrinsics.checkNotNullExpressionValue(bytes5, "this as java.lang.String).getBytes(charset)");
        int intElement = xMLParser.getIntElement(message, bytes5);
        if (intElement == 0) {
            dataAirconInfo.fan = FanStatus.off;
        } else if (intElement == 1) {
            dataAirconInfo.fan = FanStatus.low;
        } else if (intElement == 2) {
            dataAirconInfo.fan = FanStatus.medium;
        } else if (intElement == 3) {
            dataAirconInfo.fan = FanStatus.high;
        } else if (intElement != 4) {
            dataAirconInfo.fan = DataAirconSystem.DEFAULT_FAN;
        } else {
            dataAirconInfo.fan = FanStatus.auto;
        }
        byte[] bytes6 = "mode".getBytes(charset2);
        Intrinsics.checkNotNullExpressionValue(bytes6, "this as java.lang.String).getBytes(charset)");
        int intElement2 = xMLParser.getIntElement(message, bytes6);
        if (intElement2 == 1) {
            dataAirconInfo.mode = AirconMode.cool;
        } else if (intElement2 == 2) {
            dataAirconInfo.mode = AirconMode.heat;
        } else if (intElement2 == 3) {
            dataAirconInfo.mode = AirconMode.vent;
        } else if (intElement2 == 4) {
            dataAirconInfo.mode = AirconMode.auto;
        } else if (intElement2 != 5) {
            dataAirconInfo.mode = DataAirconSystem.DEFAULT_MODE;
        } else {
            dataAirconInfo.mode = AirconMode.dry;
        }
        try {
            byte[] bytes7 = "ACinfo".getBytes(charset2);
            Intrinsics.checkNotNullExpressionValue(bytes7, "this as java.lang.String).getBytes(charset)");
            num = Integer.valueOf(xMLParser.getIntElement(message, bytes7));
        } catch (IllegalArgumentException unused3) {
            Timber.forest.d("Failed to get acInfo", new Object[0]);
            num = null;
        }
        if (num == null) {
            dataAirconInfo.myZone = 0;
        } else if (num.intValue() == 0) {
            dataAirconInfo.myZone = 0;
        } else {
            byte[] bytes8 = "unitControlTempsSetting".getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes8, "this as java.lang.String).getBytes(charset)");
            Integer valueOf = Integer.valueOf(xMLParser.getIntElement(message, bytes8));
            dataAirconInfo.myZone = valueOf;
            if (valueOf != null && valueOf.intValue() == 0) {
                dataAirconInfo.myZone = 1;
            }
        }
        Charset charset3 = Charsets.UTF_8;
        byte[] bytes9 = "centralDesiredTemp".getBytes(charset3);
        Intrinsics.checkNotNullExpressionValue(bytes9, "this as java.lang.String).getBytes(charset)");
        dataAirconInfo.setTemp = Float.valueOf(xMLParser.getFloatElement(message, bytes9));
        try {
            byte[] bytes10 = "airConErrorCode".getBytes(charset3);
            Intrinsics.checkNotNullExpressionValue(bytes10, "this as java.lang.String).getBytes(charset)");
            dataAirconInfo.airconErrorCode = xMLParser.getStringElement(message, bytes10);
        } catch (IllegalArgumentException unused4) {
            Timber.forest.d("Failed to get airconErrorCode", new Object[0]);
        }
        try {
            byte[] bytes11 = "activationCodeStatus".getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes11, "this as java.lang.String).getBytes(charset)");
            int intElement3 = xMLParser.getIntElement(message, bytes11);
            if (intElement3 == 0) {
                dataAirconInfo.activationCodeStatus = DataAirconSystem.CodeStatus.noCode;
            } else if (intElement3 == 1) {
                dataAirconInfo.activationCodeStatus = DataAirconSystem.CodeStatus.codeEnabled;
            } else if (intElement3 == 2) {
                dataAirconInfo.activationCodeStatus = DataAirconSystem.CodeStatus.expired;
            }
        } catch (IllegalArgumentException unused5) {
            Timber.forest.d("Failed to get activationCodeStatus", new Object[0]);
        }
        Charset charset4 = Charsets.UTF_8;
        byte[] bytes12 = "numberOfZones".getBytes(charset4);
        Intrinsics.checkNotNullExpressionValue(bytes12, "this as java.lang.String).getBytes(charset)");
        dataAirconInfo.noOfZones = Integer.valueOf(xMLParser.getIntElement(message, bytes12));
        dataAirconInfo.filterCleanStatus = 0;
        byte[] bytes13 = "numberofConstantZones".getBytes(charset4);
        Intrinsics.checkNotNullExpressionValue(bytes13, "this as java.lang.String).getBytes(charset)");
        dataAirconInfo.noOfConstantZones = Integer.valueOf(xMLParser.getIntElement(message, bytes13));
        byte[] bytes14 = "zsConstantZone1".getBytes(charset4);
        Intrinsics.checkNotNullExpressionValue(bytes14, "this as java.lang.String).getBytes(charset)");
        dataAirconInfo.constantZone1 = Integer.valueOf(xMLParser.getIntElement(message, bytes14));
        byte[] bytes15 = "zsConstantZone2".getBytes(charset4);
        Intrinsics.checkNotNullExpressionValue(bytes15, "this as java.lang.String).getBytes(charset)");
        dataAirconInfo.constantZone2 = Integer.valueOf(xMLParser.getIntElement(message, bytes15));
        byte[] bytes16 = "zsConstantZone3".getBytes(charset4);
        Intrinsics.checkNotNullExpressionValue(bytes16, "this as java.lang.String).getBytes(charset)");
        dataAirconInfo.constantZone3 = Integer.valueOf(xMLParser.getIntElement(message, bytes16));
        try {
            byte[] bytes17 = "logoPIN".getBytes(charset4);
            Intrinsics.checkNotNullExpressionValue(bytes17, "this as java.lang.String).getBytes(charset)");
            dataSystem.logoPIN = xMLParser.getStringElement(message, bytes17);
        } catch (IllegalArgumentException unused6) {
            Timber.forest.d("Failed to get logoPIN", new Object[0]);
        }
        try {
            byte[] bytes18 = "dealerPhoneNumber".getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes18, "this as java.lang.String).getBytes(charset)");
            dataSystem.dealerPhoneNumber = xMLParser.getStringElement(message, bytes18);
        } catch (IllegalArgumentException unused7) {
            Timber.forest.d("Failed to get dealerPhoneNumber", new Object[0]);
        }
        try {
            byte[] bytes19 = "systemID".getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes19, "this as java.lang.String).getBytes(charset)");
            dataAirconInfo.rfSysID = Integer.valueOf(xMLParser.getIntElement(message, bytes19));
        } catch (IllegalArgumentException unused8) {
            Timber.forest.d("Failed to get rfSysID", new Object[0]);
        }
        try {
            byte[] bytes20 = "tempSensorNotConfigured".getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes20, "this as java.lang.String).getBytes(charset)");
            tempSensorNotConfigured = xMLParser.getBooleanElement(message, bytes20);
        } catch (IllegalArgumentException unused9) {
            Timber.forest.d("Failed to get tempSensorNotConfigured", new Object[0]);
        }
        try {
            byte[] bytes21 = "FAstatus".getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes21, "this as java.lang.String).getBytes(charset)");
            int intElement4 = xMLParser.getIntElement(message, bytes21);
            if (intElement4 == 0) {
                dataAirconInfo.freshAirStatus = DataAirconSystem.FreshAirStatus.none;
            } else if (intElement4 == 1) {
                dataAirconInfo.freshAirStatus = DataAirconSystem.FreshAirStatus.off;
            } else if (intElement4 == 2) {
                dataAirconInfo.freshAirStatus = DataAirconSystem.FreshAirStatus.on;
            }
        } catch (Exception unused10) {
            dataAirconInfo.freshAirStatus = DataAirconSystem.FreshAirStatus.none;
        }
        try {
            byte[] bytes22 = "cbType".getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes22, "this as java.lang.String).getBytes(charset)");
            int intElement5 = xMLParser.getIntElement(message, bytes22);
            dataAirconInfo.cbType = Integer.valueOf(intElement5);
            if (intElement5 != 2) {
                dataSystem.hasAircons = Boolean.TRUE;
            } else if (TabletInfo.isMyAir5Tablet()) {
                dataSystem.hasAircons = Boolean.FALSE;
            } else {
                dataSystem.hasLights = Boolean.FALSE;
                dataSystem.hasAircons = Boolean.TRUE;
            }
        } catch (IllegalArgumentException unused11) {
            dataSystem.hasAircons = Boolean.TRUE;
            dataAirconInfo.cbType = 0;
        }
        if (!TabletInfo.isMyAir5Tablet()) {
            dataSystem.hasLights = Boolean.FALSE;
        }
        dataSystem.rid = null;
        DataMaster dataMaster = new DataMaster();
        DataAirconSystem dataAirconSystem = new DataAirconSystem();
        Boolean bool = dataSystem.hasAircons;
        Intrinsics.checkNotNull(bool);
        if (bool.booleanValue()) {
            DataAirconInfo.update$default(dataAirconSystem.info, dataAirconInfo, null, false, 4, null);
        } else {
            dataAirconSystem.info.updateForCBZL(dataAirconInfo);
        }
        dataMaster.aircons.put(FragmentEnergyMonitoring.DEFAULT_AIRCON_KEY, dataAirconSystem);
        dataMaster.system.update(dataSystem);
        return dataMaster;
    }

    @Nullable
    /* renamed from: d */
    public final DataZone getZoneData(@NotNull byte[] message) {
        Intrinsics.checkNotNullParameter(message, "message");
        DataZone dataZone = new DataZone((Integer) 0);
        XMLParser xMLParser = new XMLParser();
        try {
            Charset charset = Charsets.UTF_8;
            byte[] bytes = "zone".getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
            dataZone.number = Integer.valueOf(xMLParser.getIntAttribute(message, bytes));
            byte[] bytes2 = AppMeasurementSdk.ConditionalUserProperty.NAME.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(bytes2, "this as java.lang.String).getBytes(charset)");
            String stringElement = xMLParser.getStringElement(message, bytes2);
            dataZone.name = stringElement;
            Intrinsics.checkNotNull(stringElement);
            dataZone.name = new Regex("\\+").replace(stringElement, "&");
            byte[] bytes3 = "setting".getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(bytes3, "this as java.lang.String).getBytes(charset)");
            if (xMLParser.getBooleanElement(message, bytes3)) {
                dataZone.state = ZoneState.open;
            } else {
                dataZone.state = ZoneState.close;
            }
            byte[] bytes4 = "userPercentSetting".getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(bytes4, "this as java.lang.String).getBytes(charset)");
            dataZone.value = Integer.valueOf(xMLParser.getIntElement(message, bytes4));
            byte[] bytes5 = "maxDamper".getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(bytes5, "this as java.lang.String).getBytes(charset)");
            dataZone.maxDamper = Integer.valueOf(xMLParser.getIntElement(message, bytes5));
            byte[] bytes6 = "minDamper".getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(bytes6, "this as java.lang.String).getBytes(charset)");
            dataZone.minDamper = Integer.valueOf(xMLParser.getIntElement(message, bytes6));
            try {
                byte[] bytes7 = "actualTemp".getBytes(charset);
                Intrinsics.checkNotNullExpressionValue(bytes7, "this as java.lang.String).getBytes(charset)");
                dataZone.measuredTemp = Float.valueOf(xMLParser.getFloatElement(message, bytes7));
            } catch (IllegalArgumentException unused) {
                Timber.forest.d("Failed to get actualTemp", new Object[0]);
            }
            try {
                byte[] bytes8 = "desiredTemp".getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes8, "this as java.lang.String).getBytes(charset)");
                dataZone.setTemp = Float.valueOf(xMLParser.getFloatElement(message, bytes8));
            } catch (IllegalArgumentException unused2) {
                Timber.forest.d("Failed to get desiredTemp", new Object[0]);
            }
            try {
                byte[] bytes9 = "RFstrength".getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes9, "this as java.lang.String).getBytes(charset)");
                dataZone.rssi = Integer.valueOf(xMLParser.getIntElement(message, bytes9));
            } catch (IllegalArgumentException unused3) {
                Timber.forest.d("Failed to get RFstrength", new Object[0]);
            }
            dataZone.error = 0;
            try {
                byte[] bytes10 = "hasLowBatt".getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes10, "this as java.lang.String).getBytes(charset)");
                if (xMLParser.getBooleanElement(message, bytes10)) {
                    Integer num = dataZone.error;
                    Intrinsics.checkNotNull(num);
                    dataZone.error = Integer.valueOf(num.intValue() | 2);
                }
            } catch (IllegalArgumentException unused4) {
                Timber.forest.d("Failed to get hasLowBatt", new Object[0]);
            }
            try {
                byte[] bytes11 = "tempSensorClash".getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes11, "this as java.lang.String).getBytes(charset)");
                dataZone.tempSensorClash = Boolean.valueOf(xMLParser.getBooleanElement(message, bytes11));
            } catch (IllegalArgumentException unused5) {
                Timber.forest.d("Failed to get tempSensorClash", new Object[0]);
            }
            try {
                byte[] bytes12 = "tempSensorClash".getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes12, "this as java.lang.String).getBytes(charset)");
                if (xMLParser.getBooleanElement(message, bytes12)) {
                    Integer num2 = dataZone.error;
                    Intrinsics.checkNotNull(num2);
                    dataZone.error = Integer.valueOf(num2.intValue() | 1);
                }
            } catch (IllegalArgumentException unused6) {
                Timber.forest.d("Failed to get tempSensorClash", new Object[0]);
            }
            if (tempSensorNotConfigured) {
                Integer num3 = dataZone.error;
                Intrinsics.checkNotNull(num3);
                dataZone.error = Integer.valueOf(num3.intValue() | 4);
            }
            try {
                Timber.forest.v("parseZoneData - xml : " + message, new Object[0]);
                byte[] bytes13 = "motionCurrentState".getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes13, "this as java.lang.String).getBytes(charset)");
                Integer valueOf = Integer.valueOf(xMLParser.getIntElement(message, bytes13));
                dataZone.motion = valueOf;
                Intrinsics.checkNotNull(valueOf);
                if (valueOf.intValue() >= 2) {
                    dataZone.motionConfig = 2;
                } else {
                    dataZone.motionConfig = dataZone.motion;
                }
            } catch (IllegalArgumentException unused7) {
                Timber.forest.d("Failed to get motionCurrentState", new Object[0]);
            }
            try {
                byte[] bytes14 = "hasClimateControl".getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes14, "this as java.lang.String).getBytes(charset)");
                if (xMLParser.getBooleanElement(message, bytes14)) {
                    dataZone.type = 1;
                } else {
                    dataZone.type = 0;
                }
            } catch (Exception unused8) {
                dataZone.type = 0;
            }
            return dataZone;
        } catch (Exception unused9) {
            return null;
        }
    }
}