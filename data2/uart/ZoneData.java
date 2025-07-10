package com.air.advantage.uart;

import android.content.Context;
import android.os.SystemClock;
import com.air.advantage.AppFeatures;
import com.air.advantage.data.DataAirconInfo;
import com.air.advantage.data.DataAirconSystem;
import com.air.advantage.data.DataZone;
import com.air.advantage.libraryairconlightjson.AirconMode;
import com.air.advantage.libraryairconlightjson.SystemState;
import com.air.advantage.libraryairconlightjson.ZoneState;
import com.bosma.api.lab.net.UrlConfig;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.uart.h0 */
/* loaded from: classes.dex */
public final class ZoneData {

    /* renamed from: b */
    public static final int f7114b = 1;

    /* renamed from: c */
    public static final int f7115c = 2;

    /* renamed from: e */
    private static final int UPDATE_TIMEOUT = 300000;

    /* renamed from: f */
    private static final float f7116f = 3.0f;

    /* renamed from: k */
    private static int currentMode;

    /* renamed from: l */
    private static long nextUpdateTime;

    /* renamed from: m */
    private static boolean validRasData;

    /* renamed from: n */
    private static boolean validSasData;

    /* renamed from: o */
    private static boolean zoneConstant1;

    /* renamed from: p */
    private static boolean zoneConstant2;

    /* renamed from: q */
    private static boolean vamsRasConnected;

    /* renamed from: r */
    private static boolean vamsSasConnected;

    /* renamed from: s */
    private static boolean tempSensorZone1;

    /* renamed from: t */
    private static boolean tempSensorZone2;

    /* renamed from: a */
    @NotNull
    public static final ZoneData Companion = new ZoneData();

    /* renamed from: d */
    private static final String LOG_NAME = ZoneData.class.getSimpleName();

    /* renamed from: g */
    @NotNull
    private static final DataZone zone1 = new DataZone((Integer) 1);

    /* renamed from: h */
    @NotNull
    private static final DataZone zone2 = new DataZone((Integer) 2);

    /* renamed from: i */
    @NotNull
    private static final DataAirconInfo info = new DataAirconInfo();

    /* renamed from: j */
    @NotNull
    @JvmField
    public static AtomicInteger detectionMode = new AtomicInteger(1);

    private ZoneData() {
    }

    /* renamed from: a */
    private final boolean setZoneData(Context context, DataZone dataZone, DataZone dataZone2) {
        boolean z7;
        DataZone.update$default(dataZone, dataZone2, null, null, false, 12, null);
        DataAirconInfo dataAirconInfo = info;
        Integer num = dataZone.number;
        Intrinsics.checkNotNull(num);
        if (dataAirconInfo.isZoneConstant(num.intValue())) {
            Integer num2 = dataZone.number;
            if (num2 != null && num2.intValue() == 1) {
                zoneConstant1 = true;
            } else {
                Integer num3 = dataZone.number;
                if (num3 != null && num3.intValue() == 2) {
                    zoneConstant2 = true;
                }
            }
            z7 = true;
        } else {
            Integer num4 = dataZone.number;
            if (num4 != null && num4.intValue() == 1) {
                zoneConstant1 = false;
            } else {
                Integer num5 = dataZone.number;
                if (num5 != null && num5.intValue() == 2) {
                    zoneConstant2 = false;
                }
            }
            z7 = false;
        }
        Integer num6 = dataZone.type;
        if (num6 != null && num6.intValue() == 1) {
            Integer num7 = dataZone.number;
            if (num7 != null && num7.intValue() == 1) {
                vamsRasConnected = false;
            } else {
                Integer num8 = dataZone.number;
                if (num8 != null && num8.intValue() == 2) {
                    vamsSasConnected = false;
                }
            }
        } else {
            Integer num9 = dataZone.number;
            if (num9 != null && num9.intValue() == 1) {
                vamsRasConnected = true;
            } else {
                Integer num10 = dataZone.number;
                if (num10 != null && num10.intValue() == 2) {
                    vamsSasConnected = true;
                }
            }
            z7 = true;
        }
        Boolean bool = dataZone.tempSensorClash;
        Intrinsics.checkNotNull(bool);
        if (bool.booleanValue()) {
            Integer num11 = dataZone.number;
            if (num11 != null && num11.intValue() == 1) {
                tempSensorZone1 = true;
            } else {
                Integer num12 = dataZone.number;
                if (num12 != null && num12.intValue() == 2) {
                    tempSensorZone2 = true;
                }
            }
            z7 = true;
        } else {
            Integer num13 = dataZone.number;
            if (num13 != null && num13.intValue() == 1) {
                tempSensorZone1 = false;
            } else {
                Integer num14 = dataZone.number;
                if (num14 != null && num14.intValue() == 2) {
                    tempSensorZone2 = false;
                }
            }
        }
        setZoneData(context, dataZone, false);
        return !z7;
    }

    /* renamed from: e */
    private final void setZoneData(Context context, DataZone dataZone, boolean z7) {
        ZoneState zoneState = dataZone.state;
        ZoneState zoneState2 = ZoneState.close;
        if (zoneState != zoneState2) {
            dataZone.state = zoneState2;
            String str = z7 ? "1" : UrlConfig.RESULT_OK;
            String str2 = "zone=" + dataZone.number + "&zoneSetting=" + str;
            Timber.forest.d("setZoneData: OpenClose " + str2, new Object[0]);
            Xml2JsonFunctions.Companion.getInstance().sendMessageToCB(context, "setZoneData?" + str2);
        }
    }

    /* renamed from: f */
    private final void setZoneData(Context context, DataZone dataZone, String str) {
        String str2 = dataZone.name;
        if (str2 == null || Intrinsics.areEqual(str2, str)) {
            return;
        }
        String str3 = "zone=" + dataZone.number + "&name=" + str;
        Timber.forest.d("setZoneData: renameZone  " + str3, new Object[0]);
        Xml2JsonFunctions.Companion.getInstance().sendMessageToCB(context, "setZoneData?" + str3);
    }

    /* renamed from: b */
    public final void dataZoneUpdate(@NotNull Context context, @NotNull DataZone dataZone) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(dataZone, "dataZone");
        Integer num = dataZone.number;
        if (num != null && num.intValue() == 1) {
            validRasData = setZoneData(context, zone1, dataZone);
        } else {
            Integer num2 = dataZone.number;
            if (num2 == null || num2.intValue() != 2) {
                return;
            } else {
                validSasData = setZoneData(context, zone2, dataZone);
            }
        }
        if (!validRasData || !validSasData) {
            Timber.Forest forest = Timber.forest;
            forest.d("dataZoneUpdate: validRasData isValid: " + validRasData, new Object[0]);
            forest.d("dataZoneUpdate: validSasData isValid: " + validSasData, new Object[0]);
            return;
        }
        DataZone sasZone = zone2;
        Float f3 = sasZone.measuredTemp;
        Intrinsics.checkNotNull(f3);
        float floatValue = f3.floatValue();
        DataZone rasZone = zone1;
        Float f7 = rasZone.measuredTemp;
        Intrinsics.checkNotNull(f7);
        float abs = Math.abs(floatValue - f7.floatValue());
        Timber.Forest forest2 = Timber.forest;
        forest2.d("dataZoneUpdate: rasZone.measuredTemp: " + rasZone.measuredTemp, new Object[0]);
        forest2.d("dataZoneUpdate: sasZone.measuredTemp: " + sasZone.measuredTemp, new Object[0]);
        forest2.d("dataZoneUpdate: detection mode: " + detectionMode.get(), new Object[0]);
        if (detectionMode.get() == 2) {
            forest2.d("dataZoneUpdate: absolute temp difference: " + abs, new Object[0]);
        }
        if (abs <= 3.0f && detectionMode.get() == 2) {
            forest2.d("dataZoneUpdate: difference below minimum, keep the current mode : " + currentMode, new Object[0]);
            return;
        }
        int value = AirconMode.auto.getValue();
        Float f10 = sasZone.measuredTemp;
        Intrinsics.checkNotNull(f10);
        float floatValue2 = f10.floatValue();
        Float f11 = rasZone.measuredTemp;
        Intrinsics.checkNotNull(f11);
        if (floatValue2 <= f11.floatValue()) {
            forest2.d("Think we are cooling: pre = " + rasZone.measuredTemp + " post = " + sasZone.measuredTemp, new Object[0]);
            value = AirconMode.cool.getValue();
        } else {
            Float f12 = sasZone.measuredTemp;
            Intrinsics.checkNotNull(f12);
            float floatValue3 = f12.floatValue();
            Float f13 = rasZone.measuredTemp;
            Intrinsics.checkNotNull(f13);
            if (floatValue3 > f13.floatValue()) {
                forest2.d("Think we are heating: pre = " + rasZone.measuredTemp + " post = " + sasZone.measuredTemp, new Object[0]);
                value = AirconMode.heat.getValue();
            }
        }
        if (currentMode != value && (SystemClock.elapsedRealtime() > nextUpdateTime || detectionMode.get() == 2)) {
            currentMode = value;
            nextUpdateTime = SystemClock.elapsedRealtime() + 300000;
            Xml2JsonFunctions.Companion.getInstance().sendMessageToCB(context, "setSystemData?mode=" + value);
            forest2.d("dataZoneUpdate: message sent to CB to change mode", new Object[0]);
        }
        forest2.d("dataZoneUpdate: modeWeWant: " + value, new Object[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0082  */
    @NotNull
    /* renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getErrorString() {
        String trimIndent;
        String str = "";
        if (!AppFeatures.hasVams()) {
            return "";
        }
        if (zoneConstant1 || zoneConstant2) {
            str = StringsKt__IndentKt.trimIndent("\n                    AA60 - VAMS is not setup properly, run the wizard in Tech setup.\n                    \n                    ");
        }
        if (!tempSensorZone1) {
            if (vamsRasConnected) {
                trimIndent = StringsKt__IndentKt.trimIndent("\n                    " + str + "AA62 - VAMS-RAS needs to be plugged into CB RAS port.\n                    \n                    ");
            }
            if (!tempSensorZone2) {
                return StringsKt__IndentKt.trimIndent("\n                    " + str + "AA65 - A temperature sensor is set to Zone 2, please remove it.\n                    \n                    ");
            }
            if (!vamsSasConnected) {
                return str;
            }
            return StringsKt__IndentKt.trimIndent("\n                    " + str + "AA63 - VAMS-SAS needs to be plugged into CB SAS port.\n                    \n                    ");
        }
        trimIndent = StringsKt__IndentKt.trimIndent("\n                    " + str + "AA64 - A temperature sensor is set to Zone 1, please remove it.\n                    \n                    ");
        str = trimIndent;
        if (!tempSensorZone2) {
        }
    }

    /* renamed from: d */
    public final boolean defaultZonesConfigured() {
        return zoneConstant1 || zoneConstant2 || vamsRasConnected || vamsSasConnected || tempSensorZone1 || tempSensorZone2;
    }

    /* renamed from: g */
    public final void getInstance(@NotNull Context context, @Nullable DataAirconSystem dataAirconSystem) {
        Intrinsics.checkNotNullParameter(context, "context");
        DataAirconInfo dataAirconInfo = info;
        Intrinsics.checkNotNull(dataAirconSystem);
        DataAirconInfo.update$default(dataAirconInfo, dataAirconSystem.info, null, false, 4, null);
        if (dataAirconSystem.info.state == SystemState.off) {
            Xml2JsonFunctions.Companion.getInstance().sendMessageToCB(context, "setSystemData?airconOnOff=1");
        }
    }
}