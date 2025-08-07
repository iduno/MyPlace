package com.air.advantage.aaservice.data;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.air.advantage.aaservice.data.DataAircon.ZoneState;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.annotations.SerializedName;

import jakarta.annotation.Nullable;

/* compiled from: DataZone.java */
/* renamed from: com.air.advantage.aaservice.o.l */
/* loaded from: classes.dex */
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private static DecimalFormat decimalFormat;

    @Nullable
    @JsonExporter(saveThis = false)
    @SerializedName("error")
    @JsonView(JsonExporterViews.Export.class)
    public Integer error;

    @Nullable
    @SerializedName("followers")
    @JsonProperty("followers")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public ArrayList<String> followers;

    @Nullable
    @SerializedName("following")
    @JsonProperty("following")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer following;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("maxDamper")
    @JsonView(JsonExporterViews.Export.class)
    public Integer maxDamper;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("measuredTemp")
    @JsonView(JsonExporterViews.Export.class)
    public Float measuredTemp;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("minDamper")
    @JsonView(JsonExporterViews.Export.class)
    public Integer minDamper;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("motion")
    @JsonView(JsonExporterViews.Export.class)
    public Integer motion;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("motionConfig")
    @JsonView(JsonExporterViews.Export.class)
    public Integer motionConfig;

    @SerializedName("name")
    @JsonProperty("name")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String name;

    @Nullable
    @SerializedName("number")
    @JsonProperty("number")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer number;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("rssi")
    @JsonView(JsonExporterViews.Export.class)
    public Integer rssi;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("SensorMajorRev")
    @JsonView(JsonExporterViews.Export.class)
    public Integer sensorMajorRev;

    @Nullable
    @SerializedName("sensorUid")
    @JsonProperty("sensorUid")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String sensorUid;

    /* renamed from: b */
    @SerializedName("setTemp")
    @JsonProperty("setTemp")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Float setTemp;

    /* renamed from: c */
    @SerializedName("state")
    @JsonProperty("state")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public ZoneState state;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("tempSensorClash")
    @JsonView(JsonExporterViews.Export.class)
    public Boolean tempSensorClash = Boolean.FALSE;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("type")
    @JsonView(JsonExporterViews.Export.class)
    public Integer type;

    /* renamed from: d */
    @SerializedName("value")
    @JsonProperty("value")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer value;


    public void copyFrom(DataZone other) {
        if (other == null) return;
        if (other.error != null) this.error = other.error;
        if (other.followers != null) {
            if (this.followers == null) this.followers = new java.util.ArrayList<>();
            this.followers.clear();
            this.followers.addAll(other.followers);
        }
        if (other.following != null) this.following = other.following;
        if (other.maxDamper != null) this.maxDamper = other.maxDamper;
        if (other.measuredTemp != null) this.measuredTemp = other.measuredTemp;
        if (other.minDamper != null) this.minDamper = other.minDamper;
        if (other.motion != null) this.motion = other.motion;
        if (other.motionConfig != null) this.motionConfig = other.motionConfig;
        if (other.name != null) this.name = other.name;
        if (other.number != null) this.number = other.number;
        if (other.rssi != null) this.rssi = other.rssi;
        if (other.sensorMajorRev != null) this.sensorMajorRev = other.sensorMajorRev;
        if (other.sensorUid != null) this.sensorUid = other.sensorUid;
        if (other.setTemp != null) this.setTemp = other.setTemp;
        if (other.state != null) this.state = other.state;
        if (other.value != null) this.value = other.value;
        if (other.tempSensorClash != null) this.tempSensorClash = other.tempSensorClash;
        if (other.type != null) this.type = other.type;
    }
}