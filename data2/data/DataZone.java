package com.air.advantage.data;

import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.air.advantage.libraryairconlightjson.ZoneState;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.database.Exclude;
import com.google.firebase.messaging.Constants;
import com.google.gson.annotations.SerializedName;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.z0 */
/* loaded from: classes.dex */
public final class DataZone {
    public static final float ABOVE_TARGET_MOTOR_PERCENT_DELTA = 70.0f;
    public static final float ABOVE_TARGET_TEMPERATURE_DELTA = 1.0f;
    public static final int DEFAULT_MAXDAMPER = 80;
    public static final int DEFAULT_MINDAMPER = 20;
    public static final int DEFAULT_SETMOTIONCFG = 0;
    public static final float DEFAULT_SETTEMP = 25.0f;
    public static final int DEFAULT_SETTEMP_INT = 25;
    private static final int DEFAULT_ZONEVALUE = 100;
    public static final int MOTION_1LEAF = 2;
    public static final int MOTION_1LEAF_STAGE1 = 21;
    public static final float MOTION_1LEAF_STAGE1_ADJUST = 1.0f;
    public static final int MOTION_1LEAF_STAGE2 = 22;
    public static final float MOTION_1LEAF_STAGE2_ADJUST = 2.0f;
    public static final int MOTION_STATE_DISABLED_USER = 1;
    public static final int MOTION_STATE_ENABLED = 2;
    public static final int MOTION_STATE_NO_SENSOR = 0;
    public static final float MOTOR_PERCENT_AT_TARGET = 30.0f;
    public static final float MOTOR_PERCENT_FULL_OPEN = 100.0f;
    public static final int SENSOR_TYPE_NO_SENSOR = 0;
    public static final int SENSOR_TYPE_RF = 1;
    public static final int SENSOR_TYPE_RF2CAN_BOOSTER = 3;
    public static final int SENSOR_TYPE_RF_X = 4;
    public static final int SENSOR_TYPE_VALUE_FOR_PERCENT_CONTROL = 0;
    public static final int SENSOR_TYPE_WIRED = 2;
    public static final float TEMPERATURE_DIFFERENCE_FULL_OPEN = 1.0f;
    public static final float TEMPERATURE_DIFFERENCE_TARGET = 0.0f;
    public static final int ZONE_CONTROL_TYPE_PERCENT = 1;
    public static final int ZONE_CONTROL_TYPE_VAV = 2;

    @Nullable
    private static DecimalFormat decimalFormat;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName(Constants.IPC_BUNDLE_KEY_SEND_ERROR)
    @JvmField
    public Integer error;

    @Nullable
    @SerializedName("followers")
    @JvmField
    public ArrayList<String> followers;

    @Nullable
    @SerializedName("following")
    @JvmField
    public Integer following;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("maxDamper")
    @JvmField
    public Integer maxDamper;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("measuredTemp")
    @JvmField
    public Float measuredTemp;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("minDamper")
    @JvmField
    public Integer minDamper;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("motion")
    @JvmField
    public Integer motion;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("motionConfig")
    @JvmField
    public Integer motionConfig;

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    @JvmField
    public String name;

    @Nullable
    @SerializedName("number")
    @JvmField
    public Integer number;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("rssi")
    @JvmField
    public Integer rssi;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("SensorMajorRev")
    @JvmField
    public Integer sensorMajorRev;

    @Nullable
    @SerializedName("SensorUid")
    @JvmField
    public String sensorUid;

    @Nullable
    @SerializedName("setTemp")
    @JvmField
    public Float setTemp;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("state")
    @JvmField
    public ZoneState state;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("tempSensorClash")
    @JvmField
    public Boolean tempSensorClash = Boolean.FALSE;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("type")
    @JvmField
    public Integer type;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("value")
    @JvmField
    public Integer value;

    @NotNull
    public static final a Companion = new a(null);

    @NotNull
    private static final ZoneState DEFAULT_STATE = ZoneState.open;
    private static final String LOG_TAG = DataAirconSystem.class.getSimpleName();

    /* renamed from: com.air.advantage.data.z0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.z0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Exclude
        @NotNull
        @JvmStatic
        public final String getZoneKey(@Nullable Integer num) {
            if (String.valueOf(num).length() > 1) {
                return "z" + num;
            }
            return "z0" + num;
        }

        @Exclude
        @Nullable
        @JvmStatic
        public final Integer getZoneNumberFromKey(@Nullable String str) {
            if (str != null && str.length() == 3 && StringsKt__StringsJVMKt.startsWith(str, "z", false, 2, null)) {
                try {
                    String substring = str.substring(1);
                    Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
                    int parseInt = Integer.parseInt(substring);
                    if (parseInt >= 1 && parseInt <= 10) {
                        return Integer.valueOf(parseInt);
                    }
                } catch (NumberFormatException unused) {
                }
            }
            return null;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* renamed from: com.air.advantage.data.z0$b */
    public static final class DataChanged {

        @JvmField
        public boolean measuredTempHasChanged;

        @JvmField
        public boolean stateHasChanged;

        @JvmField
        public boolean valueHasChanged;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public DataZone() {
        if (decimalFormat == null) {
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.US);
            decimalFormatSymbols.setDecimalSeparator('.');
            decimalFormatSymbols.setGroupingSeparator(',');
            decimalFormat = new DecimalFormat("#0.0", decimalFormatSymbols);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Exclude
    @NotNull
    @JvmStatic
    public static final String getZoneKey(@Nullable Integer num) {
        return Companion.getZoneKey(num);
    }

    @Exclude
    @Nullable
    @JvmStatic
    public static final Integer getZoneNumberFromKey(@Nullable String str) {
        return Companion.getZoneNumberFromKey(str);
    }

    public static /* synthetic */ boolean update$default(DataZone dataZone, DataZone dataZone2, DataManager dataManager, DataChanged dataChanged, boolean z7, int i10, Object obj) {
        if ((i10 & 4) != 0) {
            dataChanged = null;
        }
        if ((i10 & 8) != 0) {
            z7 = false;
        }
        return dataZone.update(dataZone2, dataManager, dataChanged, z7);
    }

    public final void clearDataForBackup() {
        this.error = null;
        this.measuredTemp = null;
        this.motion = null;
        this.motionConfig = null;
        this.number = null;
        this.rssi = null;
        this.sensorMajorRev = null;
        this.setTemp = null;
        this.state = null;
        this.tempSensorClash = null;
        this.type = null;
        this.value = null;
    }

    public final void clearSensorData() {
        this.sensorUid = null;
        this.sensorMajorRev = null;
        this.type = 0;
    }

    public final void completeZoneData() {
        if (this.state == null) {
            this.state = DEFAULT_STATE;
        }
        if (this.value == null) {
            this.value = 100;
        }
        if (this.setTemp == null) {
            this.setTemp = Float.valueOf(25.0f);
        }
        if (this.minDamper == null) {
            this.minDamper = 20;
        }
        if (this.maxDamper == null) {
            this.maxDamper = 80;
        }
        if (this.motionConfig == null) {
            this.motionConfig = 0;
        }
    }

    @NotNull
    public final DataZone copy() {
        DataZone dataZone = new DataZone(this.number);
        update$default(dataZone, this, null, null, false, 12, null);
        return dataZone;
    }

    @Exclude
    @NotNull
    public final String defaultZoneName() {
        Integer num = this.number;
        if (num == null) {
            return "Zone";
        }
        return "Zone" + num;
    }

    public final boolean hasJZ10UpdateToSend(@Nullable DataZone dataZone, boolean z7) {
        boolean z10;
        Float f3;
        Integer num;
        ZoneState zoneState;
        Intrinsics.checkNotNull(dataZone);
        ZoneState zoneState2 = dataZone.state;
        if (zoneState2 == null || ((zoneState = this.state) != null && zoneState == zoneState2)) {
            z10 = false;
        } else {
            if (z7) {
                this.state = zoneState2;
            }
            z10 = true;
        }
        Integer num2 = dataZone.value;
        if (num2 != null && ((num = this.value) == null || !Intrinsics.areEqual(num, num2))) {
            if (z7) {
                this.value = dataZone.value;
            }
            z10 = true;
        }
        Float f7 = dataZone.setTemp;
        if (f7 == null || ((f3 = this.setTemp) != null && Intrinsics.areEqual(f3, f7))) {
            return z10;
        }
        if (!z7) {
            return true;
        }
        this.setTemp = dataZone.setTemp;
        return true;
    }

    public final boolean hasJZ12UpdateToSend(@NotNull DataZone zoneSource, boolean z7) {
        boolean z10;
        Integer num;
        Integer num2;
        Integer num3;
        Intrinsics.checkNotNullParameter(zoneSource, "zoneSource");
        Integer num4 = zoneSource.minDamper;
        if (num4 == null || ((num3 = this.minDamper) != null && Intrinsics.areEqual(num3, num4))) {
            z10 = false;
        } else {
            if (z7) {
                this.minDamper = zoneSource.minDamper;
            }
            z10 = true;
        }
        Integer num5 = zoneSource.maxDamper;
        if (num5 != null && ((num2 = this.maxDamper) == null || !Intrinsics.areEqual(num2, num5))) {
            if (z7) {
                this.maxDamper = zoneSource.maxDamper;
            }
            z10 = true;
        }
        Integer num6 = zoneSource.motionConfig;
        if (num6 == null || ((num = this.motionConfig) != null && Intrinsics.areEqual(num, num6))) {
            return z10;
        }
        if (!z7) {
            return true;
        }
        this.motionConfig = zoneSource.motionConfig;
        return true;
    }

    public final void sanitiseData() {
        this.error = null;
        this.maxDamper = null;
        this.measuredTemp = null;
        this.minDamper = null;
        this.motion = null;
        this.number = null;
        this.rssi = null;
        this.type = null;
        this.sensorUid = null;
        this.sensorMajorRev = null;
    }

    public final void setNumberFromKey(@Nullable String str) {
        Intrinsics.checkNotNull(str);
        String substring = str.substring(1);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String).substring(startIndex)");
        try {
            this.number = Integer.valueOf(substring);
        } catch (NumberFormatException unused) {
            this.number = 0;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@Nullable DataZone dataZone, @Nullable DataManager dataManager) {
        return update$default(this, dataZone, dataManager, null, false, 12, null);
    }

    public final boolean updateForAutoModeBackup(@Nullable DataZone dataZone) {
        boolean z7;
        Integer num;
        Float f3;
        Integer num2;
        Integer num3;
        Intrinsics.checkNotNull(dataZone);
        Integer num4 = dataZone.number;
        if (num4 == null || ((num3 = this.number) != null && Intrinsics.areEqual(num3, num4))) {
            z7 = false;
        } else {
            this.number = dataZone.number;
            z7 = true;
        }
        Integer num5 = dataZone.value;
        if (num5 != null && ((num2 = this.value) == null || !Intrinsics.areEqual(num2, num5))) {
            this.value = dataZone.value;
            z7 = true;
        }
        Float f7 = dataZone.setTemp;
        if (f7 != null && ((f3 = this.setTemp) == null || !Intrinsics.areEqual(f3, f7))) {
            this.setTemp = dataZone.setTemp;
            z7 = true;
        }
        Integer num6 = dataZone.motionConfig;
        if (num6 != null && ((num = this.motionConfig) == null || !Intrinsics.areEqual(num, num6))) {
            this.motionConfig = dataZone.motionConfig;
            z7 = true;
        }
        Integer num7 = dataZone.motion;
        if (num7 != null) {
            Intrinsics.checkNotNull(num7);
            if (num7.intValue() >= 2) {
                this.motionConfig = 2;
                return true;
            }
        }
        return z7;
    }

    public final boolean updateForSnapshot(@Nullable DataZone dataZone) {
        boolean z7;
        Integer num;
        Integer num2;
        Intrinsics.checkNotNull(dataZone);
        Integer num3 = dataZone.number;
        if (num3 == null || ((num2 = this.number) != null && Intrinsics.areEqual(num2, num3))) {
            z7 = false;
        } else {
            this.number = dataZone.number;
            z7 = true;
        }
        if (hasJZ10UpdateToSend(dataZone, true)) {
            z7 = true;
        }
        Integer num4 = dataZone.motionConfig;
        if (num4 != null && ((num = this.motionConfig) == null || !Intrinsics.areEqual(num, num4))) {
            this.motionConfig = dataZone.motionConfig;
            z7 = true;
        }
        Integer num5 = dataZone.motion;
        if (num5 != null) {
            Intrinsics.checkNotNull(num5);
            if (num5.intValue() >= 2) {
                this.motionConfig = 2;
                return true;
            }
        }
        return z7;
    }

    public final void updateZoneGroupingOnly(@NotNull DataZone zoneSource) {
        Integer num;
        Intrinsics.checkNotNullParameter(zoneSource, "zoneSource");
        Integer num2 = zoneSource.following;
        if (num2 != null && ((num = this.following) == null || !Intrinsics.areEqual(num, num2))) {
            this.following = zoneSource.following;
        }
        if (zoneSource.followers != null) {
            if (this.followers == null) {
                this.followers = new ArrayList<>();
            }
            if (Intrinsics.areEqual(this.followers, zoneSource.followers)) {
                return;
            }
            ArrayList<String> arrayList = this.followers;
            Intrinsics.checkNotNull(arrayList);
            arrayList.clear();
            ArrayList<String> arrayList2 = zoneSource.followers;
            Intrinsics.checkNotNull(arrayList2);
            Iterator<String> it = arrayList2.iterator();
            while (it.hasNext()) {
                String next = it.next();
                ArrayList<String> arrayList3 = this.followers;
                Intrinsics.checkNotNull(arrayList3);
                if (!arrayList3.contains(next)) {
                    ArrayList<String> arrayList4 = this.followers;
                    Intrinsics.checkNotNull(arrayList4);
                    arrayList4.add(next);
                }
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    @Exclude
    @NotNull
    public final String getZoneKey() {
        return Companion.getZoneKey(this.number);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    @JvmOverloads
    public final boolean update(@Nullable DataZone dataZone, @Nullable DataManager dataManager, @Nullable DataChanged dataChanged) {
        return update$default(this, dataZone, dataManager, dataChanged, false, 8, null);
    }

    @JvmOverloads
    public final boolean update(@Nullable DataZone dataZone, @Nullable DataManager dataManager, @Nullable DataChanged dataChanged, boolean z7) {
        boolean z10;
        Boolean bool;
        Intrinsics.checkNotNull(dataZone);
        Integer num = dataZone.error;
        if (num != null) {
            Integer num2 = this.error;
            if (num2 == null || !Intrinsics.areEqual(num2, num)) {
                this.error = dataZone.error;
                if (dataManager != null) {
                    dataManager.add(Constants.IPC_BUNDLE_KEY_SEND_ERROR, dataZone.error);
                }
                z10 = true;
            }
            z10 = false;
        } else {
            if (z7 && this.error != null) {
                if (dataManager != null) {
                    dataManager.add(Constants.IPC_BUNDLE_KEY_SEND_ERROR, null);
                }
                z10 = true;
            }
            z10 = false;
        }
        Integer num3 = dataZone.maxDamper;
        if (num3 != null) {
            Integer num4 = this.maxDamper;
            if (num4 == null || !Intrinsics.areEqual(num4, num3)) {
                this.maxDamper = dataZone.maxDamper;
                if (dataManager != null) {
                    dataManager.add("maxDamper", dataZone.maxDamper);
                }
                z10 = true;
            }
        } else if (z7 && this.maxDamper != null) {
            if (dataManager != null) {
                dataManager.add("maxDamper", null);
            }
            z10 = true;
        }
        Float f3 = dataZone.measuredTemp;
        if (f3 != null) {
            Float f7 = this.measuredTemp;
            if (f7 == null || !Intrinsics.areEqual(f7, f3)) {
                this.measuredTemp = dataZone.measuredTemp;
                if (dataChanged != null) {
                    dataChanged.measuredTempHasChanged = true;
                }
                if (dataManager != null) {
                    DecimalFormat decimalFormat2 = decimalFormat;
                    Intrinsics.checkNotNull(decimalFormat2);
                    dataManager.add("measuredTemp", Double.valueOf(decimalFormat2.format(dataZone.measuredTemp)));
                }
                z10 = true;
            } else if (dataChanged != null) {
                dataChanged.measuredTempHasChanged = false;
            }
        } else if (z7 && this.measuredTemp != null) {
            if (dataManager != null) {
                dataManager.add("measuredTemp", null);
            }
            z10 = true;
        }
        Integer num5 = dataZone.minDamper;
        if (num5 != null) {
            Integer num6 = this.minDamper;
            if (num6 == null || !Intrinsics.areEqual(num6, num5)) {
                this.minDamper = dataZone.minDamper;
                if (dataManager != null) {
                    dataManager.add("minDamper", dataZone.minDamper);
                }
                z10 = true;
            }
        } else if (z7 && this.minDamper != null) {
            if (dataManager != null) {
                dataManager.add("minDamper", null);
            }
            z10 = true;
        }
        Integer num7 = dataZone.motion;
        if (num7 != null) {
            Integer num8 = this.motion;
            if (num8 == null || !Intrinsics.areEqual(num8, num7)) {
                this.motion = dataZone.motion;
                if (dataManager != null) {
                    dataManager.add("motion", dataZone.motion);
                }
            }
        } else if (z7 && this.motion != null) {
            if (dataManager != null) {
                dataManager.add("motion", null);
            }
            z10 = true;
        }
        Integer num9 = dataZone.motionConfig;
        if (num9 != null) {
            Integer num10 = this.motionConfig;
            if (num10 == null || !Intrinsics.areEqual(num10, num9)) {
                this.motionConfig = dataZone.motionConfig;
                if (dataManager != null) {
                    dataManager.add("motionConfig", dataZone.motionConfig);
                }
                z10 = true;
            }
        } else if (z7 && this.motionConfig != null) {
            if (dataManager != null) {
                dataManager.add("motionConfig", null);
            }
            z10 = true;
        }
        String str = dataZone.name;
        if (str != null) {
            String str2 = this.name;
            if (str2 == null || !Intrinsics.areEqual(str2, str)) {
                this.name = dataZone.name;
                if (dataManager != null) {
                    dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, dataZone.name);
                }
                z10 = true;
            }
        } else if (z7 && this.name != null) {
            if (dataManager != null) {
                dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, null);
            }
            z10 = true;
        }
        Integer num11 = dataZone.number;
        if (num11 != null) {
            Integer num12 = this.number;
            if (num12 == null || !Intrinsics.areEqual(num12, num11)) {
                this.number = dataZone.number;
                if (dataManager != null && !z7) {
                    dataManager.add("number", dataZone.number);
                }
                z10 = true;
            }
        } else if (z7 && this.number != null) {
            if (dataManager != null) {
                dataManager.add("number", null);
            }
            z10 = true;
        }
        Integer num13 = dataZone.rssi;
        if (num13 != null) {
            Integer num14 = this.rssi;
            if (num14 == null || !Intrinsics.areEqual(num14, num13)) {
                this.rssi = dataZone.rssi;
                if (dataManager != null) {
                    dataManager.add("rssi", dataZone.rssi);
                }
                z10 = true;
            }
        } else if (z7 && this.rssi != null) {
            if (dataManager != null) {
                dataManager.add("rssi", null);
            }
            z10 = true;
        }
        Integer num15 = dataZone.sensorMajorRev;
        if (num15 != null) {
            Integer num16 = this.sensorMajorRev;
            if (num16 == null || !Intrinsics.areEqual(num16, num15)) {
                this.sensorMajorRev = dataZone.sensorMajorRev;
                if (dataManager != null) {
                    dataManager.add("sensorMajorRev", dataZone.sensorMajorRev);
                }
                z10 = true;
            }
        } else if (z7 && this.sensorMajorRev != null) {
            if (dataManager != null) {
                dataManager.add("sensorMajorRev", null);
            }
            z10 = true;
        }
        String str3 = dataZone.sensorUid;
        if (str3 != null) {
            String str4 = this.sensorUid;
            if (str4 == null || !Intrinsics.areEqual(str4, str3)) {
                this.sensorUid = dataZone.sensorUid;
                if (dataManager != null) {
                    dataManager.add("sensorUid", dataZone.sensorUid);
                }
                z10 = true;
            }
        } else if (z7 && this.sensorUid != null) {
            if (dataManager != null) {
                dataManager.add("sensorUid", null);
            }
            z10 = true;
        }
        Float f10 = dataZone.setTemp;
        if (f10 != null) {
            Float f11 = this.setTemp;
            if (f11 == null || !Intrinsics.areEqual(f11, f10)) {
                this.setTemp = dataZone.setTemp;
                if (dataManager != null) {
                    DecimalFormat decimalFormat3 = decimalFormat;
                    Intrinsics.checkNotNull(decimalFormat3);
                    dataManager.add("setTemp", Double.valueOf(decimalFormat3.format(dataZone.setTemp)));
                }
                z10 = true;
            }
        } else if (z7 && this.setTemp != null) {
            if (dataManager != null) {
                dataManager.add("setTemp", null);
            }
            z10 = true;
        }
        ZoneState zoneState = dataZone.state;
        if (zoneState != null) {
            ZoneState zoneState2 = this.state;
            if (zoneState2 == null || zoneState2 != zoneState) {
                this.state = zoneState;
                if (dataChanged != null) {
                    dataChanged.stateHasChanged = true;
                }
                if (dataManager != null) {
                    dataManager.add("state", dataZone.state);
                }
                z10 = true;
            } else if (dataChanged != null) {
                dataChanged.stateHasChanged = false;
            }
        } else if (z7 && this.state != null) {
            if (dataManager != null) {
                dataManager.add("state", null);
            }
            z10 = true;
        }
        Integer num17 = dataZone.type;
        if (num17 != null) {
            Integer num18 = this.type;
            if (num18 == null || !Intrinsics.areEqual(num18, num17)) {
                this.type = dataZone.type;
                if (dataManager != null) {
                    dataManager.add("type", dataZone.type);
                }
                z10 = true;
            }
        } else if (z7 && this.type != null) {
            if (dataManager != null) {
                dataManager.add("type", null);
            }
            z10 = true;
        }
        Integer num19 = dataZone.value;
        if (num19 != null) {
            Integer num20 = this.value;
            if (num20 == null || !Intrinsics.areEqual(num20, num19)) {
                this.value = dataZone.value;
                if (dataChanged != null) {
                    dataChanged.valueHasChanged = true;
                }
                if (dataManager != null) {
                    dataManager.add("value", dataZone.value);
                }
                z10 = true;
            } else if (dataChanged != null) {
                dataChanged.valueHasChanged = false;
            }
        } else if (z7 && this.value != null) {
            if (dataManager != null) {
                dataManager.add("value", null);
            }
            z10 = true;
        }
        Integer num21 = dataZone.following;
        if (num21 != null) {
            Integer num22 = this.following;
            if (num22 == null || !Intrinsics.areEqual(num22, num21)) {
                this.following = dataZone.following;
                if (dataManager != null) {
                    dataManager.add("following", dataZone.following);
                }
                z10 = true;
            }
        } else if (z7 && this.following != null) {
            if (dataManager != null) {
                dataManager.add("following", null);
            }
            z10 = true;
        }
        if (dataZone.followers != null) {
            if (this.followers == null) {
                this.followers = new ArrayList<>();
            }
            if (!Intrinsics.areEqual(this.followers, dataZone.followers)) {
                ArrayList<String> arrayList = this.followers;
                Intrinsics.checkNotNull(arrayList);
                arrayList.clear();
                ArrayList<String> arrayList2 = dataZone.followers;
                Intrinsics.checkNotNull(arrayList2);
                Iterator<String> it = arrayList2.iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    ArrayList<String> arrayList3 = this.followers;
                    Intrinsics.checkNotNull(arrayList3);
                    if (!arrayList3.contains(next)) {
                        ArrayList<String> arrayList4 = this.followers;
                        Intrinsics.checkNotNull(arrayList4);
                        arrayList4.add(next);
                    }
                }
                if (dataManager != null) {
                    dataManager.add("followers", dataZone.followers);
                }
                z10 = true;
            }
        } else if (z7 && this.followers != null) {
            if (dataManager != null) {
                dataManager.add("followers", null);
            }
            z10 = true;
        }
        Boolean bool2 = dataZone.tempSensorClash;
        if (bool2 == null || ((bool = this.tempSensorClash) != null && Intrinsics.areEqual(bool, bool2))) {
            return z10;
        }
        this.tempSensorClash = dataZone.tempSensorClash;
        return true;
    }

    public DataZone(@Nullable String str) {
        setNumberFromKey(str);
    }

    public DataZone(@Nullable Integer num) {
        this.number = num;
    }
}