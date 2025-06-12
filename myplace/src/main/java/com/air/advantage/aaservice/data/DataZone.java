package com.air.advantage.aaservice.data;

import java.util.ArrayList;

import com.air.advantage.libraryairconlightjson.ZoneState;
import com.google.gson.annotations.SerializedName;

import jakarta.annotation.Nullable;

/* compiled from: DataZone.java */
/* renamed from: com.air.advantage.aaservice.o.l */
/* loaded from: classes.dex */
public class DataZone {
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
    @JsonExporter(saveThis = false)
    @SerializedName("error")
    public Integer error;

    @Nullable
    @SerializedName("followers")
    public ArrayList<String> followers;

    @Nullable
    @SerializedName("following")
    public Integer following;

    @Nullable
    @JsonExporter(saveThis = false)
    @SerializedName("maxDamper")
    public Integer maxDamper;

    @Nullable
    @JsonExporter(saveThis = false)
    @SerializedName("measuredTemp")
    public Float measuredTemp;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("minDamper")
    public Integer minDamper;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("motion")
    public Integer motion;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("motionConfig")
    public Integer motionConfig;

    @SerializedName("name")
    public String name;

    @Nullable
    @SerializedName("number")
    public Integer number;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("rssi")
    public Integer rssi;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("SensorMajorRev")
    public Integer sensorMajorRev;

    @Nullable
    @SerializedName("SensorUid")
    public String sensorUid;

    /* renamed from: b */
    @SerializedName("setTemp")
    public Float setTemp;

    /* renamed from: c */
    @SerializedName("state")
    public ZoneState state;

    /* renamed from: d */
    @SerializedName("value")
    public Integer value;
}