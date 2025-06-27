package com.air.advantage.data;

import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.c0 */
/* loaded from: classes.dex */
public final class Events {

    @NotNull
    public static final String GARAGE_DOOR_TRIGGER_ON_CLOSE = "onGarageClosed";

    @NotNull
    public static final String GARAGE_DOOR_TRIGGER_ON_OPEN = "onGarageOpened";

    @NotNull
    public static final String MOTION_SENSOR_TRIGGER_ON_MOTION = "onMotion";

    @NotNull
    public static final String MOTION_SENSOR_TRIGGER_ON_NO_MOTION = "onNoMotion";

    @Nullable
    @SerializedName("garageDoorEnabled")
    @JvmField
    public Boolean garageDoorEnabled;

    @Nullable
    @SerializedName("garageDoorTrigger")
    @JvmField
    public String garageDoorTrigger;

    @Nullable
    @SerializedName("hueTempBelowTrhesholdSelected")
    @JvmField
    public Boolean hueTempBelowThresholdSelected;

    @Nullable
    @SerializedName("hueTempEnabled")
    @JvmField
    public Boolean hueTempEnabled;

    @Nullable
    @SerializedName("hueTempThresholdValue")
    @JvmField
    public Integer hueTempThresholdValue;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("lastTriggeredTimestamp")
    @JvmField
    public Long lastTriggeredTimestamp;

    @Nullable
    @SerializedName("motionSensorEnabled")
    @JvmField
    public Boolean motionSensorEnabled;

    @Nullable
    @SerializedName("motionSensorLightLevel")
    @JvmField
    public String motionSensorLightLevel;

    @Nullable
    @SerializedName("motionSensorLightLevelEnabled")
    @JvmField
    public Boolean motionSensorLightLevelEnabled;

    @Nullable
    @SerializedName("motionSensorLightLevelEqualOrBelowSelected")
    @JvmField
    public Boolean motionSensorLightLevelEqualOrBelowSelected;

    @Nullable
    @SerializedName("motionSensorTrigger")
    @JvmField
    public String motionSensorTrigger;

    @Nullable
    @SerializedName("motionSensorTriggerDelayMinutes")
    @JvmField
    public Integer motionSensorTriggerDelayMinutes;

    @Nullable
    @SerializedName("motionSensorsIdList")
    @JvmField
    public ArrayList<String> motionSensorsIdList;

    @Nullable
    @SerializedName("suburbTempAboveValue")
    @JvmField
    public Integer suburbTempAboveValue;

    @Nullable
    @SerializedName("suburbTempBelowThresholdSelected")
    @JvmField
    public Boolean suburbTempBelowThresholdSelected;

    @Nullable
    @SerializedName("suburbTempEnabled")
    @JvmField
    public Boolean suburbTempEnabled;

    @Nullable
    @SerializedName("suburbTempThresholdValue")
    @JvmField
    public Integer suburbTempThresholdValue;

    @Nullable
    @SerializedName("weatherConditionForPvEnabled")
    @JvmField
    public Boolean weatherConditionForPvEnabled;

    @Nullable
    @SerializedName("zoneTempAirconId")
    @JvmField
    public String zoneTempAirconId;

    @Nullable
    @SerializedName("zoneTempBelowThresholdSelected")
    @JvmField
    public Boolean zoneTempBelowThresholdSelected;

    @Nullable
    @SerializedName("zoneTempEnabled")
    @JvmField
    public Boolean zoneTempEnabled;

    @Nullable
    @SerializedName("zoneTempThresholdValue")
    @JvmField
    public Integer zoneTempThresholdValue;

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = Events.class.getSimpleName();

    /* renamed from: com.air.advantage.data.c0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.c0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    public static /* synthetic */ boolean update$default(Events events, Events events2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 4) != 0) {
            z7 = false;
        }
        return events.update(events2, dataManager, z7);
    }

    public final void clear() {
        this.garageDoorEnabled = null;
        this.garageDoorTrigger = null;
        this.hueTempEnabled = null;
        this.hueTempBelowThresholdSelected = null;
        this.hueTempThresholdValue = null;
        this.lastTriggeredTimestamp = null;
        this.motionSensorLightLevel = null;
        this.motionSensorLightLevelEnabled = null;
        this.motionSensorLightLevelEqualOrBelowSelected = null;
        this.motionSensorEnabled = null;
        this.motionSensorsIdList = null;
        this.motionSensorTrigger = null;
        this.motionSensorTriggerDelayMinutes = null;
        this.suburbTempAboveValue = null;
        this.suburbTempEnabled = null;
        this.suburbTempThresholdValue = null;
        this.suburbTempBelowThresholdSelected = null;
        this.weatherConditionForPvEnabled = null;
        this.zoneTempEnabled = null;
        this.zoneTempAirconId = null;
        this.zoneTempThresholdValue = null;
        this.zoneTempBelowThresholdSelected = null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@Nullable Events events, @Nullable DataManager dataManager) {
        return update$default(this, events, dataManager, false, 4, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    @JvmOverloads
    public final boolean update(@Nullable Events events, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        Intrinsics.checkNotNull(events);
        Boolean bool = events.garageDoorEnabled;
        if (bool != null) {
            Boolean bool2 = this.garageDoorEnabled;
            if (bool2 == null || !Intrinsics.areEqual(bool2, bool)) {
                this.garageDoorEnabled = events.garageDoorEnabled;
                if (dataManager != null) {
                    dataManager.add("garageDoorEnabled", events.garageDoorEnabled);
                }
                z10 = true;
            }
            z10 = false;
        } else {
            if (z7 && this.garageDoorEnabled != null) {
                if (dataManager != null) {
                    dataManager.add("garageDoorEnabled", null);
                }
                z10 = true;
            }
            z10 = false;
        }
        String str = events.garageDoorTrigger;
        if (str != null) {
            String str2 = this.garageDoorTrigger;
            if (str2 == null || !Intrinsics.areEqual(str2, str)) {
                this.garageDoorTrigger = events.garageDoorTrigger;
                if (dataManager != null) {
                    dataManager.add("garageDoorTrigger", events.garageDoorTrigger);
                }
                z10 = true;
            }
        } else if (z7 && this.garageDoorTrigger != null) {
            if (dataManager != null) {
                dataManager.add("garageDoorTrigger", null);
            }
            z10 = true;
        }
        Boolean bool3 = events.hueTempEnabled;
        if (bool3 != null) {
            Boolean bool4 = this.hueTempEnabled;
            if (bool4 == null || !Intrinsics.areEqual(bool4, bool3)) {
                this.hueTempEnabled = events.hueTempEnabled;
                if (dataManager != null) {
                    dataManager.add("hueTempEnabled", events.hueTempEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.hueTempEnabled != null) {
            if (dataManager != null) {
                dataManager.add("hueTempEnabled", null);
            }
            z10 = true;
        }
        Integer num = events.hueTempThresholdValue;
        if (num != null) {
            Integer num2 = this.hueTempThresholdValue;
            if (num2 == null || !Intrinsics.areEqual(num2, num)) {
                this.hueTempThresholdValue = events.hueTempThresholdValue;
                if (dataManager != null) {
                    dataManager.add("hueTempThresholdValue", events.hueTempThresholdValue);
                }
                z10 = true;
            }
        } else if (z7 && this.hueTempThresholdValue != null) {
            if (dataManager != null) {
                dataManager.add("hueTempThresholdValue", null);
            }
            z10 = true;
        }
        Boolean bool5 = events.hueTempBelowThresholdSelected;
        if (bool5 != null) {
            Boolean bool6 = this.hueTempBelowThresholdSelected;
            if (bool6 == null || !Intrinsics.areEqual(bool6, bool5)) {
                this.hueTempBelowThresholdSelected = events.hueTempBelowThresholdSelected;
                if (dataManager != null) {
                    dataManager.add("hueTempBelowThresholdSelected", events.hueTempBelowThresholdSelected);
                }
                z10 = true;
            }
        } else if (z7 && this.hueTempBelowThresholdSelected != null) {
            if (dataManager != null) {
                dataManager.add("hueTempBelowThresholdSelected", null);
            }
            z10 = true;
        }
        Long l8 = events.lastTriggeredTimestamp;
        if (l8 != null) {
            Long l10 = this.lastTriggeredTimestamp;
            if (l10 == null || !Intrinsics.areEqual(l10, l8)) {
                this.lastTriggeredTimestamp = events.lastTriggeredTimestamp;
                if (dataManager != null) {
                    dataManager.add("lastTriggeredTimestamp", events.lastTriggeredTimestamp);
                }
                z10 = true;
            }
        } else if (z7 && this.lastTriggeredTimestamp != null) {
            if (dataManager != null) {
                dataManager.add("lastTriggeredTimestamp", null);
            }
            z10 = true;
        }
        String str3 = events.motionSensorLightLevel;
        if (str3 != null) {
            String str4 = this.motionSensorLightLevel;
            if (str4 == null || !Intrinsics.areEqual(str4, str3)) {
                this.motionSensorLightLevel = events.motionSensorLightLevel;
                if (dataManager != null) {
                    dataManager.add("motionSensorLightLevel", events.motionSensorLightLevel);
                }
                z10 = true;
            }
        } else if (z7 && this.motionSensorLightLevel != null) {
            if (dataManager != null) {
                dataManager.add("motionSensorLightLevel", null);
            }
            z10 = true;
        }
        Boolean bool7 = events.motionSensorLightLevelEnabled;
        if (bool7 != null) {
            Boolean bool8 = this.motionSensorLightLevelEnabled;
            if (bool8 == null || !Intrinsics.areEqual(bool8, bool7)) {
                this.motionSensorLightLevelEnabled = events.motionSensorLightLevelEnabled;
                if (dataManager != null) {
                    dataManager.add("motionSensorLightLevelEnabled", events.motionSensorLightLevelEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.motionSensorLightLevelEnabled != null) {
            if (dataManager != null) {
                dataManager.add("motionSensorLightLevelEnabled", null);
            }
            z10 = true;
        }
        Boolean bool9 = events.motionSensorLightLevelEqualOrBelowSelected;
        if (bool9 != null) {
            Boolean bool10 = this.motionSensorLightLevelEqualOrBelowSelected;
            if (bool10 == null || !Intrinsics.areEqual(bool10, bool9)) {
                this.motionSensorLightLevelEqualOrBelowSelected = events.motionSensorLightLevelEqualOrBelowSelected;
                if (dataManager != null) {
                    dataManager.add("motionSensorLightLevelEqualOrBelowSelected", events.motionSensorLightLevelEqualOrBelowSelected);
                }
                z10 = true;
            }
        } else if (z7 && this.motionSensorLightLevelEqualOrBelowSelected != null) {
            if (dataManager != null) {
                dataManager.add("motionSensorLightLevelEqualOrBelowSelected", null);
            }
            z10 = true;
        }
        Boolean bool11 = events.motionSensorEnabled;
        if (bool11 != null) {
            Boolean bool12 = this.motionSensorEnabled;
            if (bool12 == null || !Intrinsics.areEqual(bool12, bool11)) {
                this.motionSensorEnabled = events.motionSensorEnabled;
                if (dataManager != null) {
                    dataManager.add("motionSensorEnabled", events.motionSensorEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.motionSensorEnabled != null) {
            if (dataManager != null) {
                dataManager.add("motionSensorEnabled", null);
            }
            z10 = true;
        }
        ArrayList<String> arrayList = events.motionSensorsIdList;
        if (arrayList != null) {
            ArrayList<String> arrayList2 = this.motionSensorsIdList;
            if (arrayList2 == null || !Intrinsics.areEqual(arrayList2, arrayList)) {
                ArrayList<String> arrayList3 = this.motionSensorsIdList;
                if (arrayList3 == null) {
                    this.motionSensorsIdList = new ArrayList<>();
                } else {
                    Intrinsics.checkNotNull(arrayList3);
                    arrayList3.clear();
                }
                ArrayList<String> arrayList4 = this.motionSensorsIdList;
                Intrinsics.checkNotNull(arrayList4);
                ArrayList<String> arrayList5 = events.motionSensorsIdList;
                Intrinsics.checkNotNull(arrayList5);
                arrayList4.addAll(arrayList5);
                if (dataManager != null) {
                    dataManager.add("motionSensorIdList", events.motionSensorsIdList);
                }
                z10 = true;
            }
        } else if (z7 && this.motionSensorsIdList != null) {
            if (dataManager != null) {
                dataManager.add("motionSensorsIdList", null);
            }
            z10 = true;
        }
        String str5 = events.motionSensorTrigger;
        if (str5 != null) {
            String str6 = this.motionSensorTrigger;
            if (str6 == null || !Intrinsics.areEqual(str6, str5)) {
                this.motionSensorTrigger = events.motionSensorTrigger;
                if (dataManager != null) {
                    dataManager.add("motionSensorTrigger", events.motionSensorTrigger);
                }
                z10 = true;
            }
        } else if (z7 && this.motionSensorTrigger != null) {
            if (dataManager != null) {
                dataManager.add("motionSensorTrigger", null);
            }
            z10 = true;
        }
        Integer num3 = events.motionSensorTriggerDelayMinutes;
        if (num3 != null) {
            Integer num4 = this.motionSensorTriggerDelayMinutes;
            if (num4 == null || !Intrinsics.areEqual(num4, num3)) {
                this.motionSensorTriggerDelayMinutes = events.motionSensorTriggerDelayMinutes;
                if (dataManager != null) {
                    dataManager.add("motionSensorTriggerDelayMinutes", events.motionSensorTriggerDelayMinutes);
                }
                z10 = true;
            }
        } else if (z7 && this.motionSensorTriggerDelayMinutes != null) {
            if (dataManager != null) {
                dataManager.add("motionSensorTriggerDelayMinutes", null);
            }
            z10 = true;
        }
        Boolean bool13 = events.suburbTempEnabled;
        if (bool13 != null) {
            Boolean bool14 = this.suburbTempEnabled;
            if (bool14 == null || !Intrinsics.areEqual(bool14, bool13)) {
                this.suburbTempEnabled = events.suburbTempEnabled;
                if (dataManager != null) {
                    dataManager.add("suburbTempEnabled", events.suburbTempEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.suburbTempEnabled != null) {
            if (dataManager != null) {
                dataManager.add("suburbTempEnabled", null);
            }
            z10 = true;
        }
        Integer num5 = events.suburbTempAboveValue;
        if (num5 != null) {
            Integer num6 = this.suburbTempAboveValue;
            if (num6 == null || !Intrinsics.areEqual(num6, num5)) {
                this.suburbTempAboveValue = events.suburbTempAboveValue;
                if (dataManager != null) {
                    dataManager.add("suburbTempAboveValue", events.suburbTempAboveValue);
                }
                z10 = true;
            }
        } else if (z7 && this.suburbTempAboveValue != null) {
            if (dataManager != null) {
                dataManager.add("suburbTempAboveValue", null);
            }
            z10 = true;
        }
        Integer num7 = events.suburbTempThresholdValue;
        if (num7 != null) {
            Integer num8 = this.suburbTempThresholdValue;
            if (num8 == null || !Intrinsics.areEqual(num8, num7)) {
                this.suburbTempThresholdValue = events.suburbTempThresholdValue;
                if (dataManager != null) {
                    dataManager.add("suburbTempThresholdValue", events.suburbTempThresholdValue);
                }
                z10 = true;
            }
        } else if (z7 && this.suburbTempThresholdValue != null) {
            if (dataManager != null) {
                dataManager.add("suburbTempThresholdValue", null);
            }
            z10 = true;
        }
        Boolean bool15 = events.suburbTempBelowThresholdSelected;
        if (bool15 != null) {
            Boolean bool16 = this.suburbTempBelowThresholdSelected;
            if (bool16 == null || !Intrinsics.areEqual(bool16, bool15)) {
                this.suburbTempBelowThresholdSelected = events.suburbTempBelowThresholdSelected;
                if (dataManager != null) {
                    dataManager.add("suburbTempBelowThresholdSelected", events.suburbTempBelowThresholdSelected);
                }
                z10 = true;
            }
        } else if (z7 && this.suburbTempBelowThresholdSelected != null) {
            if (dataManager != null) {
                dataManager.add("suburbTempBelowThresholdSelected", null);
            }
            z10 = true;
        }
        Boolean bool17 = events.weatherConditionForPvEnabled;
        if (bool17 != null) {
            Boolean bool18 = this.weatherConditionForPvEnabled;
            if (bool18 == null || !Intrinsics.areEqual(bool18, bool17)) {
                this.weatherConditionForPvEnabled = events.weatherConditionForPvEnabled;
                if (dataManager != null) {
                    dataManager.add("weatherConditionForPvEnabled", events.weatherConditionForPvEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.weatherConditionForPvEnabled != null) {
            if (dataManager != null) {
                dataManager.add("weatherConditionForPvEnabled", null);
            }
            z10 = true;
        }
        Boolean bool19 = events.zoneTempEnabled;
        if (bool19 != null) {
            Boolean bool20 = this.zoneTempEnabled;
            if (bool20 == null || !Intrinsics.areEqual(bool20, bool19)) {
                this.zoneTempEnabled = events.zoneTempEnabled;
                if (dataManager != null) {
                    dataManager.add("zoneTempEnabled", events.zoneTempEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.zoneTempEnabled != null) {
            if (dataManager != null) {
                dataManager.add("zoneTempEnabled", null);
            }
            z10 = true;
        }
        String str7 = events.zoneTempAirconId;
        if (str7 != null) {
            String str8 = this.zoneTempAirconId;
            if (str8 == null || !Intrinsics.areEqual(str8, str7)) {
                this.zoneTempAirconId = events.zoneTempAirconId;
                if (dataManager != null) {
                    dataManager.add("zoneTempAirconId", events.zoneTempAirconId);
                }
                z10 = true;
            }
        } else if (z7 && this.zoneTempAirconId != null) {
            if (dataManager != null) {
                dataManager.add("zoneTempAirconId", null);
            }
            z10 = true;
        }
        Integer num9 = events.zoneTempThresholdValue;
        if (num9 != null) {
            Integer num10 = this.zoneTempThresholdValue;
            if (num10 == null || !Intrinsics.areEqual(num10, num9)) {
                this.zoneTempThresholdValue = events.zoneTempThresholdValue;
                if (dataManager != null) {
                    dataManager.add("zoneTempThresholdValue", events.zoneTempThresholdValue);
                }
                z10 = true;
            }
        } else if (z7 && this.zoneTempThresholdValue != null) {
            if (dataManager != null) {
                dataManager.add("zoneTempThresholdValue", null);
            }
            z10 = true;
        }
        Boolean bool21 = events.zoneTempBelowThresholdSelected;
        if (bool21 != null) {
            Boolean bool22 = this.zoneTempBelowThresholdSelected;
            if (bool22 == null || !Intrinsics.areEqual(bool22, bool21)) {
                this.zoneTempBelowThresholdSelected = events.zoneTempBelowThresholdSelected;
                if (dataManager == null) {
                    return true;
                }
                dataManager.add("zoneTempBelowThresholdSelected", events.zoneTempBelowThresholdSelected);
                return true;
            }
        } else if (z7 && this.zoneTempBelowThresholdSelected != null) {
            if (dataManager == null) {
                return true;
            }
            dataManager.add("zoneTempBelowThresholdSelected", null);
            return true;
        }
        return z10;
    }
}