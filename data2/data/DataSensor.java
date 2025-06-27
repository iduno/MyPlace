package com.air.advantage.data;

import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.o0 */
/* loaded from: classes.dex */
public final class DataSensor {
    public static final int LIGHT_LEVEL_COSY_THRESHOLD_VALUE = 17000;
    public static final int LIGHT_LEVEL_DARK_THRESHOLD_VALUE = 3000;
    public static final int LIGHT_LEVEL_DIM_THRESHOLD_VALUE = 10000;
    public static final int LIGHT_LEVEL_NORMAL_THRESHOLD_VALUE = 23000;
    public static final int MAX_HUE_SENSOR_NAME_LENGTH = 12;

    @NotNull
    public static final String TYPE_STRING_HUE_SENSOR = "HueSensor";

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("battery")
    private Integer battery;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName(LIGHT_LEVEL_STRING_DARK)
    private Boolean dark;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("daylight")
    private Boolean daylight;

    @Nullable
    @SerializedName("enabled")
    @JvmField
    public Boolean enabled;

    @Exclude
    @Nullable
    @JvmField
    public transient Long expiryTime;

    @Nullable
    @SerializedName("id")
    @JvmField
    public String id;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("idOnHueBridge")
    @JvmField
    public String idOnHueBridge;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("lastPresenceTimestamp")
    @JvmField
    public Long lastPresenceTimestamp;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("lightLevel")
    @JvmField
    public Integer lightLevel;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("lightLevelString")
    @JvmField
    public String lightLevelString;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("minutesFromLastPresence")
    @JvmField
    public Integer minutesFromLastPresence;

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    @JvmField
    public String name;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("presence")
    @JvmField
    public Boolean presence;

    @Nullable
    @SerializedName("reachable")
    @JvmField
    public Boolean reachable;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("temperature")
    @JvmField
    public Float temperature;

    @Nullable
    @SerializedName("type")
    private String type;

    @NotNull
    public static final a Companion = new a(null);

    @NotNull
    public static final String LIGHT_LEVEL_STRING_DARK = "dark";

    @NotNull
    public static final String LIGHT_LEVEL_STRING_DIM = "dim";

    @NotNull
    public static final String LIGHT_LEVEL_STRING_COSY = "cosy";

    @NotNull
    public static final String LIGHT_LEVEL_STRING_NORMAL = "normal";

    @NotNull
    public static final String LIGHT_LEVEL_STRING_BRIGHT = "bright";

    @NotNull
    private static final String[] LIGHT_LEVELS = {LIGHT_LEVEL_STRING_DARK, LIGHT_LEVEL_STRING_DIM, LIGHT_LEVEL_STRING_COSY, LIGHT_LEVEL_STRING_NORMAL, LIGHT_LEVEL_STRING_BRIGHT};

    /* renamed from: com.air.advantage.data.o0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.o0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    private final int getLightLevelPosition(String str) {
        int length = LIGHT_LEVELS.length;
        for (int i10 = 0; i10 < length; i10++) {
            if (Intrinsics.areEqual(LIGHT_LEVELS[i10], str)) {
                return i10;
            }
        }
        return -1;
    }

    public static /* synthetic */ boolean update$default(DataSensor dataSensor, DataSensor dataSensor2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 4) != 0) {
            z7 = false;
        }
        return dataSensor.update(dataSensor2, dataManager, z7);
    }

    private final String workoutLightLevelString(Integer num) {
        if (num != null) {
            return num.intValue() < 3000 ? LIGHT_LEVEL_STRING_DARK : num.intValue() < 10000 ? LIGHT_LEVEL_STRING_DIM : num.intValue() < 17000 ? LIGHT_LEVEL_STRING_COSY : num.intValue() < 23000 ? LIGHT_LEVEL_STRING_NORMAL : LIGHT_LEVEL_STRING_BRIGHT;
        }
        return null;
    }

    public final int compareLightLevelPosition(@Nullable String str) {
        String str2 = this.lightLevelString;
        if (str2 == null || str == null) {
            return -2;
        }
        int lightLevelPosition = getLightLevelPosition(str2);
        int lightLevelPosition2 = getLightLevelPosition(str);
        if (lightLevelPosition == -1 || lightLevelPosition2 == -1) {
            return -2;
        }
        if (lightLevelPosition < lightLevelPosition2) {
            return -1;
        }
        return lightLevelPosition > lightLevelPosition2 ? 1 : 0;
    }

    @Nullable
    public final Integer getBattery() {
        return this.battery;
    }

    @Nullable
    public final Boolean getDark() {
        return this.dark;
    }

    @Nullable
    public final Boolean getDaylight() {
        return this.daylight;
    }

    @Nullable
    public final String getType() {
        return this.type;
    }

    public final void setBattery(@Nullable Integer num) {
        this.battery = num;
    }

    public final void setDark(@Nullable Boolean bool) {
        this.dark = bool;
    }

    public final void setDaylight(@Nullable Boolean bool) {
        this.daylight = bool;
    }

    public final void setType(@Nullable String str) {
        this.type = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@Nullable DataSensor dataSensor, @Nullable DataManager dataManager) {
        return update$default(this, dataSensor, dataManager, false, 4, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public final boolean update(@Nullable String str, @NotNull com.air.advantage.thirdparty.hue.a hueDeviceSource) {
        String str2;
        Intrinsics.checkNotNullParameter(hueDeviceSource, "hueDeviceSource");
        String str3 = hueDeviceSource.uniqueid;
        Intrinsics.checkNotNull(str3);
        int C3 = StringsKt__StringsKt.C3(str3, '-', 0, false, 6, null);
        if (C3 == -1) {
            return false;
        }
        String substring = hueDeviceSource.uniqueid.substring(0, C3);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        this.id = substring;
        this.type = TYPE_STRING_HUE_SENSOR;
        com.air.advantage.thirdparty.hue.b bVar = hueDeviceSource.config;
        Intrinsics.checkNotNull(bVar);
        this.battery = bVar.getBattery();
        if (Intrinsics.areEqual(hueDeviceSource.type, com.air.advantage.thirdparty.hue.a.TYPE_STRING_ZLLPRESENCE)) {
            String str4 = hueDeviceSource.name;
            Intrinsics.checkNotNull(str4);
            if (str4.length() > 12) {
                str2 = hueDeviceSource.name.substring(0, 12);
                Intrinsics.checkNotNullExpressionValue(str2, "this as java.lang.String…ing(startIndex, endIndex)");
            } else {
                str2 = hueDeviceSource.name;
            }
            this.name = str2;
            com.air.advantage.thirdparty.hue.c cVar = hueDeviceSource.state;
            Intrinsics.checkNotNull(cVar);
            this.presence = cVar.getPresence();
            com.air.advantage.thirdparty.hue.b bVar2 = hueDeviceSource.config;
            this.reachable = bVar2.reachable;
            this.enabled = bVar2.on;
            this.idOnHueBridge = str;
            return true;
        }
        if (Intrinsics.areEqual(hueDeviceSource.type, com.air.advantage.thirdparty.hue.a.TYPE_STRING_ZLLTEMPERATURE)) {
            com.air.advantage.thirdparty.hue.c cVar2 = hueDeviceSource.state;
            Intrinsics.checkNotNull(cVar2);
            if (cVar2.getTemperature() == null) {
                return true;
            }
            Intrinsics.checkNotNull(hueDeviceSource.state.getTemperature());
            this.temperature = Float.valueOf(r8.intValue() / 100.0f);
            return true;
        }
        if (!Intrinsics.areEqual(hueDeviceSource.type, com.air.advantage.thirdparty.hue.a.TYPE_STRING_ZLLLIGHTLEVEL)) {
            return true;
        }
        com.air.advantage.thirdparty.hue.c cVar3 = hueDeviceSource.state;
        Intrinsics.checkNotNull(cVar3);
        this.lightLevel = cVar3.getLightlevel();
        this.lightLevelString = workoutLightLevelString(hueDeviceSource.state.getLightlevel());
        this.dark = hueDeviceSource.state.getDark();
        this.daylight = hueDeviceSource.state.getDaylight();
        return true;
    }

    @JvmOverloads
    public final boolean update(@Nullable DataSensor dataSensor, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        String str;
        if (dataManager != null) {
            Intrinsics.checkNotNull(dataSensor);
            String str2 = dataSensor.id;
            if (str2 != null) {
                dataManager.updateSensorPath(str2);
            }
        }
        Intrinsics.checkNotNull(dataSensor);
        Integer num = dataSensor.battery;
        if (num != null) {
            Integer num2 = this.battery;
            if (num2 == null || !Intrinsics.areEqual(num2, num)) {
                this.battery = dataSensor.battery;
                if (dataManager != null) {
                    dataManager.add("battery", dataSensor.battery);
                }
                z10 = true;
            }
            z10 = false;
        } else {
            if (z7 && this.battery != null) {
                if (dataManager != null) {
                    dataManager.add("battery", null);
                }
                z10 = true;
            }
            z10 = false;
        }
        Boolean bool = dataSensor.daylight;
        if (bool != null) {
            Boolean bool2 = this.daylight;
            if (bool2 == null || !Intrinsics.areEqual(bool2, bool)) {
                this.daylight = dataSensor.daylight;
                if (dataManager != null) {
                    dataManager.add("daylight", dataSensor.daylight);
                }
                z10 = true;
            }
        } else if (z7 && this.daylight != null) {
            if (dataManager != null) {
                dataManager.add("daylight", null);
            }
            z10 = true;
        }
        Boolean bool3 = dataSensor.dark;
        if (bool3 != null) {
            Boolean bool4 = this.dark;
            if (bool4 == null || !Intrinsics.areEqual(bool4, bool3)) {
                this.dark = dataSensor.dark;
                if (dataManager != null) {
                    dataManager.add(LIGHT_LEVEL_STRING_DARK, dataSensor.dark);
                }
                z10 = true;
            }
        } else if (z7 && this.dark != null) {
            if (dataManager != null) {
                dataManager.add(LIGHT_LEVEL_STRING_DARK, null);
            }
            z10 = true;
        }
        Boolean bool5 = dataSensor.enabled;
        if (bool5 != null) {
            Boolean bool6 = this.enabled;
            if (bool6 == null || !Intrinsics.areEqual(bool6, bool5)) {
                this.enabled = dataSensor.enabled;
                if (dataManager != null) {
                    dataManager.add("enabled", dataSensor.enabled);
                }
                z10 = true;
            }
        } else if (z7 && this.enabled != null) {
            if (dataManager != null) {
                dataManager.add("enabled", null);
            }
            z10 = true;
        }
        String str3 = dataSensor.id;
        if (str3 != null && ((str = this.id) == null || !Intrinsics.areEqual(str, str3))) {
            this.id = dataSensor.id;
            if (dataManager != null) {
                dataManager.add("id", dataSensor.id);
            }
            z10 = true;
        }
        String str4 = dataSensor.idOnHueBridge;
        if (str4 != null) {
            String str5 = this.idOnHueBridge;
            if (str5 == null || !Intrinsics.areEqual(str5, str4)) {
                this.idOnHueBridge = dataSensor.idOnHueBridge;
                z10 = true;
            }
        } else if (z7 && this.idOnHueBridge != null) {
            if (dataManager != null) {
                dataManager.add("idOnHueBridge", null);
            }
            z10 = true;
        }
        Long l8 = dataSensor.lastPresenceTimestamp;
        if (l8 != null) {
            Long l10 = this.lastPresenceTimestamp;
            if (l10 == null || !Intrinsics.areEqual(l10, l8)) {
                this.lastPresenceTimestamp = dataSensor.lastPresenceTimestamp;
                z10 = true;
            }
        } else if (z7 && this.lastPresenceTimestamp != null) {
            if (dataManager != null) {
                dataManager.add("lastPresenceTimestamp", null);
            }
            z10 = true;
        }
        Integer num3 = dataSensor.lightLevel;
        if (num3 != null) {
            Integer num4 = this.lightLevel;
            if (num4 == null || !Intrinsics.areEqual(num4, num3)) {
                this.lightLevel = dataSensor.lightLevel;
                if (dataManager != null) {
                    dataManager.add("lightLevel", dataSensor.lightLevel);
                }
                z10 = true;
            }
        } else if (z7 && this.lightLevel != null) {
            if (dataManager != null) {
                dataManager.add("lightLevel", null);
            }
            z10 = true;
        }
        String str6 = dataSensor.lightLevelString;
        if (str6 != null) {
            String str7 = this.lightLevelString;
            if (str7 == null || !Intrinsics.areEqual(str7, str6)) {
                this.lightLevelString = dataSensor.lightLevelString;
                if (dataManager != null) {
                    dataManager.add("lightLevelString", dataSensor.lightLevelString);
                }
                z10 = true;
            }
        } else if (z7 && this.lightLevelString != null) {
            if (dataManager != null) {
                dataManager.add("lightLevelString", null);
            }
            z10 = true;
        }
        Integer num5 = dataSensor.minutesFromLastPresence;
        if (num5 != null) {
            Integer num6 = this.minutesFromLastPresence;
            if (num6 == null || !Intrinsics.areEqual(num6, num5)) {
                this.minutesFromLastPresence = dataSensor.minutesFromLastPresence;
                if (dataManager != null) {
                    dataManager.add("minutesFromLastPresence", dataSensor.minutesFromLastPresence);
                }
                z10 = true;
            }
        } else if (z7 && this.minutesFromLastPresence != null) {
            if (dataManager != null) {
                dataManager.add("minutesFromLastPresence", null);
            }
            z10 = true;
        }
        String str8 = dataSensor.name;
        if (str8 != null) {
            String str9 = this.name;
            if (str9 == null || !Intrinsics.areEqual(str9, str8)) {
                this.name = dataSensor.name;
                if (dataManager != null) {
                    dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, dataSensor.name);
                }
                z10 = true;
            }
        } else if (z7 && this.name != null) {
            if (dataManager != null) {
                dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, null);
            }
            z10 = true;
        }
        Boolean bool7 = dataSensor.presence;
        if (bool7 != null) {
            Boolean bool8 = this.presence;
            if (bool8 == null || !Intrinsics.areEqual(bool8, bool7)) {
                this.presence = dataSensor.presence;
                if (dataManager != null) {
                    dataManager.add("presence", dataSensor.presence);
                }
                z10 = true;
            }
        } else if (z7 && this.presence != null) {
            if (dataManager != null) {
                dataManager.add("presence", null);
            }
            z10 = true;
        }
        Float f3 = dataSensor.temperature;
        if (f3 != null) {
            Float f7 = this.temperature;
            if (f7 == null || !Intrinsics.areEqual(f7, f3)) {
                this.temperature = dataSensor.temperature;
                if (dataManager != null) {
                    dataManager.add("temperature", dataSensor.temperature);
                }
                z10 = true;
            }
        } else if (z7 && this.temperature != null) {
            if (dataManager != null) {
                dataManager.add("temperature", null);
            }
            z10 = true;
        }
        String str10 = dataSensor.type;
        if (str10 != null) {
            String str11 = this.type;
            if (str11 == null || !Intrinsics.areEqual(str11, str10)) {
                this.type = dataSensor.type;
                if (dataManager != null) {
                    dataManager.add("type", dataSensor.type);
                }
                z10 = true;
            }
        } else if (z7 && this.type != null) {
            if (dataManager != null) {
                dataManager.add("type", null);
            }
            z10 = true;
        }
        Boolean bool9 = dataSensor.reachable;
        if (bool9 != null) {
            Boolean bool10 = this.reachable;
            if (bool10 == null || !Intrinsics.areEqual(bool10, bool9)) {
                this.reachable = dataSensor.reachable;
                if (dataManager == null) {
                    return true;
                }
                dataManager.add("reachable", dataSensor.reachable);
                return true;
            }
        } else if (z7 && this.reachable != null) {
            if (dataManager == null) {
                return true;
            }
            dataManager.add("reachable", null);
            return true;
        }
        return z10;
    }
}