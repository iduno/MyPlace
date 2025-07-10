package com.air.advantage.uart;

import android.content.Context;
import com.air.advantage.ActivityMain;
import com.air.advantage.AppFeatures;
import com.air.advantage.data.DataAirconSystem;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataZone;
import com.air.advantage.libraryairconlightjson.CommonFuncs;
import com.air.advantage.libraryairconlightjson.ZoneState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.TreeMap;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt__IndentKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.uart.u */
/* loaded from: classes.dex */
public final class HandlerCB extends FRLParser {

    /* renamed from: U */
    @NotNull
    public static final Companion Companion = new Companion(null);

    /* renamed from: V */
    @Nullable
    private static HandlerCB instance;

    /* renamed from: com.air.advantage.uart.u$a */
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.u.a.<init>():void type: THIS */
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
            HandlerCB.instance = null;
        }

        @NotNull
        /* renamed from: b */
        public final HandlerCB getInstance() {
            if (HandlerCB.instance == null) {
                synchronized (HandlerCB.class) {
                    if (HandlerCB.instance == null) {
                        Companion companion = HandlerCB.Companion;
                        HandlerCB.instance = new HandlerCB(null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            HandlerCB handlerCB = HandlerCB.instance;
            Intrinsics.checkNotNull(handlerCB);
            return handlerCB;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private Companion() {
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.u.<init>():void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ HandlerCB(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* renamed from: h */
    private final String getErrorText(int i10, String str, DataZone dataZone) {
        String str2 = "";
        if (extractUIDValue(str, 4) == null) {
            str2 = "Rejected CB status message - invalid UID\n";
        }
        Integer parseHexToInt = parseHexToInt(str, 9);
        if (parseHexToInt == null || parseHexToInt.intValue() != i10) {
            str2 = str2 + "Rejected CB status message - invalid message type\n";
        }
        Integer parseHexToInt2 = parseHexToInt(str, 11);
        dataZone.number = parseHexToInt2;
        if (parseHexToInt2 != null) {
            Intrinsics.checkNotNull(parseHexToInt2);
            if (parseHexToInt2.intValue() >= 1) {
                Integer num = dataZone.number;
                Intrinsics.checkNotNull(num);
                if (num.intValue() <= 10) {
                    return str2;
                }
            }
        }
        return str2 + "Rejected CB status message - invalid zoneNumber\n";
    }

    @JvmStatic
    public static final void i() {
        Companion.destroy();
    }

    @NotNull
    /* renamed from: m */
    public static final HandlerCB getInstance() {
        return Companion.getInstance();
    }

    @NotNull
    /* renamed from: j */
    public final String encodeZone_j(@Nullable String str, @NotNull DataZone zoneSource) {
        Intrinsics.checkNotNullParameter(zoneSource, "zoneSource");
        String str2 = ((("" + (HandlerAircon.Companion.getInstance().isCBType4Aircon(str) ? "08" : "07")) + "01") + str) + "03";
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Locale locale = Locale.ENGLISH;
        String format = String.format(locale, "%02X", Arrays.copyOf(new Object[]{zoneSource.number}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(locale, format, *args)");
        String str3 = str2 + format;
        byte b10 = zoneSource.state == ZoneState.open ? (byte) 128 : (byte) 0;
        Integer num = zoneSource.value;
        Intrinsics.checkNotNull(num);
        String format2 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Byte.valueOf((byte) (b10 | num.intValue()))}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(locale, format, *args)");
        Float f3 = zoneSource.setTemp;
        Intrinsics.checkNotNull(f3);
        String format3 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{Integer.valueOf((int) (f3.floatValue() * 2))}, 1));
        Intrinsics.checkNotNullExpressionValue(format3, "format(locale, format, *args)");
        return (((str3 + format2) + "00") + format3) + FRLParser.DEFAULT_SENSORUID;
    }

    @NotNull
    /* renamed from: k */
    public final String encodeZone_k(@Nullable String str, @NotNull DataZone zoneSource) {
        Intrinsics.checkNotNullParameter(zoneSource, "zoneSource");
        String str2 = ((("" + (HandlerAircon.Companion.getInstance().isCBType4Aircon(str) ? "08" : "07")) + "01") + str) + "04";
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Locale locale = Locale.ENGLISH;
        String format = String.format(locale, "%02X", Arrays.copyOf(new Object[]{zoneSource.number}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(locale, format, *args)");
        String str3 = str2 + format;
        String format2 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{zoneSource.minDamper}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(locale, format, *args)");
        String str4 = str3 + format2;
        String format3 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{zoneSource.maxDamper}, 1));
        Intrinsics.checkNotNullExpressionValue(format3, "format(locale, format, *args)");
        String format4 = String.format(locale, "%02X", Arrays.copyOf(new Object[]{zoneSource.motionConfig}, 1));
        Intrinsics.checkNotNullExpressionValue(format4, "format(locale, format, *args)");
        return (((str4 + format3) + "00") + format4) + "0000";
    }

    @NotNull
    /* renamed from: l */
    public final String encodeResponse_l(@Nullable String str, @Nullable DataZone dataZone) {
        String str2;
        if (str == null || dataZone == null || dataZone.number == null) {
            return "";
        }
        String str3 = ((("" + (HandlerAircon.Companion.getInstance().isCBType4Aircon(str) ? "08" : "07")) + "01") + str) + "12";
        String str4 = dataZone.sensorUid;
        if (str4 != null) {
            str2 = str3 + str4;
        } else {
            str2 = str3 + FRLParser.DEFAULT_SENSORUID;
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.ENGLISH, "%02X", Arrays.copyOf(new Object[]{dataZone.number}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(locale, format, *args)");
        return (str2 + format) + FRLParser.DEFAULT_SENSORUID;
    }

    /* JADX WARN: Removed duplicated region for block: B:121:0x012c A[Catch: all -> 0x0323, TryCatch #0 {, blocks: (B:25:0x008c, B:27:0x0099, B:30:0x00b3, B:32:0x00ca, B:34:0x00d0, B:37:0x00da, B:39:0x00e0, B:42:0x00f3, B:44:0x0109, B:45:0x011a, B:48:0x0120, B:53:0x0132, B:55:0x0136, B:57:0x0140, B:60:0x014f, B:62:0x0159, B:64:0x0165, B:68:0x0306, B:71:0x0253, B:74:0x02d9, B:76:0x02dd, B:78:0x02e1, B:80:0x02ef, B:82:0x02e5, B:84:0x02fb, B:86:0x0303, B:87:0x0262, B:91:0x026f, B:95:0x027c, B:97:0x02ac, B:99:0x02b0, B:100:0x02ba, B:101:0x0276, B:103:0x0269, B:105:0x02c1, B:106:0x01c1, B:110:0x01cf, B:112:0x01d7, B:114:0x01e3, B:115:0x0244, B:121:0x012c, B:122:0x030b, B:125:0x0317), top: B:24:0x008c }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0253 A[Catch: all -> 0x0323, TRY_ENTER, TryCatch #0 {, blocks: (B:25:0x008c, B:27:0x0099, B:30:0x00b3, B:32:0x00ca, B:34:0x00d0, B:37:0x00da, B:39:0x00e0, B:42:0x00f3, B:44:0x0109, B:45:0x011a, B:48:0x0120, B:53:0x0132, B:55:0x0136, B:57:0x0140, B:60:0x014f, B:62:0x0159, B:64:0x0165, B:68:0x0306, B:71:0x0253, B:74:0x02d9, B:76:0x02dd, B:78:0x02e1, B:80:0x02ef, B:82:0x02e5, B:84:0x02fb, B:86:0x0303, B:87:0x0262, B:91:0x026f, B:95:0x027c, B:97:0x02ac, B:99:0x02b0, B:100:0x02ba, B:101:0x0276, B:103:0x0269, B:105:0x02c1, B:106:0x01c1, B:110:0x01cf, B:112:0x01d7, B:114:0x01e3, B:115:0x0244, B:121:0x012c, B:122:0x030b, B:125:0x0317), top: B:24:0x008c }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0262 A[Catch: all -> 0x0323, TryCatch #0 {, blocks: (B:25:0x008c, B:27:0x0099, B:30:0x00b3, B:32:0x00ca, B:34:0x00d0, B:37:0x00da, B:39:0x00e0, B:42:0x00f3, B:44:0x0109, B:45:0x011a, B:48:0x0120, B:53:0x0132, B:55:0x0136, B:57:0x0140, B:60:0x014f, B:62:0x0159, B:64:0x0165, B:68:0x0306, B:71:0x0253, B:74:0x02d9, B:76:0x02dd, B:78:0x02e1, B:80:0x02ef, B:82:0x02e5, B:84:0x02fb, B:86:0x0303, B:87:0x0262, B:91:0x026f, B:95:0x027c, B:97:0x02ac, B:99:0x02b0, B:100:0x02ba, B:101:0x0276, B:103:0x0269, B:105:0x02c1, B:106:0x01c1, B:110:0x01cf, B:112:0x01d7, B:114:0x01e3, B:115:0x0244, B:121:0x012c, B:122:0x030b, B:125:0x0317), top: B:24:0x008c }] */
    /* renamed from: n */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean parseMessage(@NotNull Context context, @NotNull String message) {
        DataMaster dataMaster;
        Integer num;
        boolean z7;
        DataZone.DataChanged dataChanged;
        Integer num2;
        Integer num3;
        DataZone dataZone;
        ArrayList<String> arrayList;
        ActivityMain companion;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(message, "message");
        if (!compareFRLValue(message, 0, "07")) {
            Timber.forest.d("Rejected can message - incorrect system type " + message, new Object[0]);
            return false;
        }
        if (!compareFRLValue(message, 2, "03")) {
            Timber.forest.d("Rejected can message - incorrect device type", new Object[0]);
            return false;
        }
        if (compareFRLValue(message, 4, "00") && compareFRLValue(message, 6, "00") && compareFRLValue(message, 7, "00")) {
            Timber.forest.d("Rejected can message - UID is zero! - " + message, new Object[0]);
            return false;
        }
        String extractUIDValue = extractUIDValue(message, 4);
        if (extractUIDValue == null) {
            Timber.forest.d("Rejected CB status message - invalid UID", new Object[0]);
            return false;
        }
        synchronized (MyMasterData.class) {
            DataMaster dataMaster2 = MyMasterData.Companion.getDataMaster(context);
            DataAirconSystem airconByUid = dataMaster2.getAirconByUid(extractUIDValue);
            if (airconByUid == null) {
                Timber.forest.d("no aircon available with this uid " + extractUIDValue, new Object[0]);
                return true;
            }
            airconByUid.info.expireTime = Long.valueOf(CommonFuncs.getUptime() + 80);
            Integer zoneNumber = parseHexToInt(message, 11);
            if (zoneNumber != null && zoneNumber.intValue() != 0 && zoneNumber.intValue() <= 10) {
                if (airconByUid.info.noOfZones != null) {
                    int intValue = zoneNumber.intValue();
                    Integer num4 = airconByUid.info.noOfZones;
                    Intrinsics.checkNotNull(num4);
                    if (intValue <= num4.intValue()) {
                        DataZone dataZone2 = new DataZone(zoneNumber);
                        TreeMap<String, DataZone> treeMap = airconByUid.zones;
                        Intrinsics.checkNotNull(treeMap);
                        DataZone dataZone3 = treeMap.get(dataZone2.getZoneKey());
                        if (dataZone3 == null) {
                            dataZone3 = new DataZone(zoneNumber);
                            TreeMap<String, DataZone> treeMap2 = airconByUid.zones;
                            Intrinsics.checkNotNull(treeMap2);
                            treeMap2.put(dataZone2.getZoneKey(), dataZone3);
                        }
                        String str = dataZone3.name;
                        if (str == null) {
                            dataZone2.name = dataZone2.defaultZoneName();
                        } else {
                            if (str == null || str.length() == 0) {
                            }
                        }
                        Integer num5 = dataZone2.number;
                        boolean z10 = num5 != null && Intrinsics.areEqual(num5, airconByUid.info.noOfZones) && HandlerJson.Companion.getIsProcessing().compareAndSet(true, false);
                        if (compareFRLValue(message, 9, "03")) {
                            String updateZoneState = updateZoneState(dataZone2, message);
                            if (!Intrinsics.areEqual(updateZoneState, "")) {
                                Timber.forest.d(updateZoneState, new Object[0]);
                                return false;
                            }
                            num = zoneNumber;
                            dataMaster = dataMaster2;
                            Timber.forest.v("Valid CB JZ11 message. UID - " + extractUIDValue + " zoneNumber - " + dataZone2.number + " zoneOpen - " + dataZone2.state + " zoneMotorSet - " + dataZone2.value + " tempSensorType - " + dataZone2.type + " tempSet - " + dataZone2.setTemp + " measuredTemp - " + dataZone2.measuredTemp, new Object[0]);
                        } else {
                            dataMaster = dataMaster2;
                            num = zoneNumber;
                            if (!compareFRLValue(message, 9, "04")) {
                                z7 = false;
                                if (!z7 || z10) {
                                    dataChanged = new DataZone.DataChanged();
                                    if (!dataZone3.update(dataZone2, null, dataChanged, false) || z10) {
                                        num2 = dataZone3.type;
                                        if (num2 != null && (num2 == null || num2.intValue() != 0)) {
                                            num3 = dataZone3.following;
                                            if (num3 != null && (num3 == null || num3.intValue() != 0)) {
                                                Timber.forest.d("DBGSS we need to cancel the following for zone " + dataZone3.number, new Object[0]);
                                                TreeMap<String, DataZone> treeMap3 = airconByUid.zones;
                                                Intrinsics.checkNotNull(treeMap3);
                                                dataZone = treeMap3.get(DataZone.Companion.getZoneKey(dataZone3.following));
                                                if (dataZone != null && (arrayList = dataZone.followers) != null) {
                                                    Intrinsics.checkNotNull(arrayList);
                                                    arrayList.remove(dataZone3.getZoneKey());
                                                }
                                                dataZone3.following = 0;
                                            }
                                        }
                                        AirconDBStore.Companion.getInstance(context).updateStore(context, extractUIDValue, airconByUid);
                                        HandlerJson.Companion.getInstance(context).processData(dataMaster, "canUpdate");
                                    }
                                    if ((dataChanged.measuredTempHasChanged || (((dataChanged.stateHasChanged || dataChanged.valueHasChanged) && airconByUid.isZoneConstant(num.intValue())) || HandlerAircon.Companion.getInstance().extraPercentRequired(airconByUid) > 0)) && (companion = ActivityMain.Companion.getInstance()) != null) {
                                        companion.t2(extractUIDValue);
                                    }
                                }
                                Unit unit = Unit.INSTANCE;
                                return true;
                            }
                            String updateZoneConfig = updateZoneConfig(dataZone2, message);
                            if (!Intrinsics.areEqual(updateZoneConfig, "")) {
                                Timber.forest.d(updateZoneConfig, new Object[0]);
                                return false;
                            }
                            Timber.forest.v("Valid CB JZ13 message. UID - " + extractUIDValue + " zoneNumber - " + dataZone2.number + " Min Damper - " + dataZone2.minDamper + " Max Damper - " + dataZone2.maxDamper + " motionStatus - " + dataZone2.motion + " motionConfig - " + dataZone2.motionConfig + " zoneError - " + dataZone2.error + " RSSI - " + dataZone2.rssi, new Object[0]);
                        }
                        z7 = true;
                        if (!z7) {
                            dataChanged = new DataZone.DataChanged();
                            if (!dataZone3.update(dataZone2, null, dataChanged, false)) {
                                num2 = dataZone3.type;
                                if (num2 != null) {
                                    num3 = dataZone3.following;
                                    if (num3 != null) {
                                        Timber.forest.d("DBGSS we need to cancel the following for zone " + dataZone3.number, new Object[0]);
                                        TreeMap<String, DataZone> treeMap32 = airconByUid.zones;
                                        Intrinsics.checkNotNull(treeMap32);
                                        dataZone = treeMap32.get(DataZone.Companion.getZoneKey(dataZone3.following));
                                        if (dataZone != null) {
                                            Intrinsics.checkNotNull(arrayList);
                                            arrayList.remove(dataZone3.getZoneKey());
                                        }
                                        dataZone3.following = 0;
                                    }
                                }
                                AirconDBStore.Companion.getInstance(context).updateStore(context, extractUIDValue, airconByUid);
                                HandlerJson.Companion.getInstance(context).processData(dataMaster, "canUpdate");
                                if (dataChanged.measuredTempHasChanged) {
                                    companion.t2(extractUIDValue);
                                } else {
                                    companion.t2(extractUIDValue);
                                }
                            }
                        }
                        Unit unit2 = Unit.INSTANCE;
                        return true;
                    }
                }
                Timber.forest.d("Rejected CB status message - zoneNumber is too high", new Object[0]);
                return false;
            }
            Timber.forest.d("Rejected CB status message - invalid zoneNumber", new Object[0]);
            return false;
        }
    }

    @NotNull
    /* renamed from: o */
    public final String updateZoneState(@NotNull DataZone zoneToUpdate, @NotNull String message) {
        Float f3;
        Intrinsics.checkNotNullParameter(zoneToUpdate, "zoneToUpdate");
        Intrinsics.checkNotNullParameter(message, "message");
        DataZone dataZone = new DataZone(zoneToUpdate.getZoneKey());
        String errorText = getErrorText(3, message, dataZone);
        if (!(errorText.length() == 0)) {
            Timber.forest.d(errorText, new Object[0]);
            return errorText;
        }
        Integer parseHexToInt = parseHexToInt(message, 13);
        if (parseHexToInt == null) {
            errorText = errorText + "Rejected CB status message - invalid zone state\n";
        } else {
            dataZone.state = ZoneState.close;
            if ((parseHexToInt.intValue() & 128) == 128) {
                dataZone.state = ZoneState.open;
            }
            int intValue = parseHexToInt.intValue() & 127;
            if (intValue > 100) {
                errorText = errorText + "Rejected CB status message - invalid zone motor value\n";
            }
            dataZone.value = Integer.valueOf(intValue);
        }
        Integer parseHexToInt2 = parseHexToInt(message, 15);
        dataZone.type = parseHexToInt2;
        if (parseHexToInt2 == null) {
            errorText = errorText + "Rejected CB status message - invalid sensor type\n";
        }
        Integer parseHexToInt3 = parseHexToInt(message, 17);
        if (parseHexToInt3 == null || parseHexToInt3.intValue() > 80) {
            errorText = errorText + "Rejected CB status message - invalid zone setTemp\n";
        } else {
            dataZone.setTemp = Float.valueOf(parseHexToInt3.intValue() / 2.0f);
        }
        Integer parseHexToInt4 = parseHexToInt(message, 19);
        if (parseHexToInt4 == null) {
            errorText = errorText + "Rejected CB status message - invalid measured Temp Int portion\n";
        } else {
            Integer parseHexToInt5 = parseHexToInt(message, 21);
            if (parseHexToInt5 == null || parseHexToInt5.intValue() > 9) {
                errorText = errorText + "Rejected CB status message - invalid measured Temp Decimal portion\n";
            } else {
                try {
                    f3 = Float.valueOf(Float.parseFloat(parseHexToInt4 + "." + parseHexToInt5));
                } catch (NumberFormatException e7) {
                    AppFeatures.instance.logCriticalException(e7, "failed to parse measured temperature");
                    f3 = null;
                }
                if (f3 == null) {
                    errorText = errorText + "Rejected CB status message - failed to parse measured temperature\n";
                } else {
                    dataZone.measuredTemp = f3;
                }
            }
        }
        if (Intrinsics.areEqual(errorText, "")) {
            DataZone.update$default(zoneToUpdate, dataZone, null, null, false, 12, null);
        } else {
            Timber.forest.d(errorText, new Object[0]);
        }
        return errorText;
    }

    @NotNull
    /* renamed from: p */
    public final String updateZoneConfig(@NotNull DataZone zoneToUpdate, @NotNull String message) {
        Intrinsics.checkNotNullParameter(zoneToUpdate, "zoneToUpdate");
        Intrinsics.checkNotNullParameter(message, "message");
        DataZone dataZone = new DataZone(zoneToUpdate.getZoneKey());
        String errorText = getErrorText(4, message, dataZone);
        if (!(errorText.length() == 0)) {
            Timber.forest.d(errorText, new Object[0]);
            return errorText;
        }
        Integer parseHexToInt = parseHexToInt(message, 13);
        dataZone.minDamper = parseHexToInt;
        if (parseHexToInt == null) {
            errorText = errorText + "Rejected CB status message - invalid Min Damper\n";
        }
        Integer parseHexToInt2 = parseHexToInt(message, 15);
        dataZone.maxDamper = parseHexToInt2;
        if (parseHexToInt2 == null) {
            errorText = errorText + "Rejected CB status message - invalid Max Damper\n";
        }
        Integer parseHexToInt3 = parseHexToInt(message, 17);
        dataZone.motion = parseHexToInt3;
        if (parseHexToInt3 == null) {
            errorText = errorText + "Rejected CB status message - null CB Motion Status\n";
        } else {
            Intrinsics.checkNotNull(parseHexToInt3);
            if (parseHexToInt3.intValue() > 22) {
                String trimIndent = StringsKt__IndentKt.trimIndent("\n                Rejected CB status message - invalid CB Motion Status " + dataZone.motion + "\n                \n                ");
                StringBuilder sb = new StringBuilder();
                sb.append(errorText);
                sb.append(trimIndent);
                errorText = sb.toString();
            }
        }
        Integer parseHexToInt4 = parseHexToInt(message, 19);
        dataZone.motionConfig = parseHexToInt4;
        if (parseHexToInt4 == null) {
            errorText = errorText + "Rejected CB status message - null CB Motion Config\n";
        } else {
            Intrinsics.checkNotNull(parseHexToInt4);
            if (parseHexToInt4.intValue() > 2) {
                errorText = errorText + "Rejected CB status message - invalid CB Motion Config " + dataZone.motionConfig + " " + message + "\n";
            }
        }
        Integer parseHexToInt5 = parseHexToInt(message, 21);
        dataZone.error = parseHexToInt5;
        if (parseHexToInt5 == null) {
            errorText = errorText + "Rejected CB status message - invalid CB Zone Error\n";
        }
        Integer parseHexToInt6 = parseHexToInt(message, 23);
        dataZone.rssi = parseHexToInt6;
        if (parseHexToInt6 == null) {
            errorText = errorText + "Rejected CB status message - invalid CB RSSI\n";
        }
        if (Intrinsics.areEqual(errorText, "")) {
            DataZone.update$default(zoneToUpdate, dataZone, null, null, false, 12, null);
        } else {
            Timber.forest.d(errorText, new Object[0]);
        }
        return errorText;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private HandlerCB() {
    }
}