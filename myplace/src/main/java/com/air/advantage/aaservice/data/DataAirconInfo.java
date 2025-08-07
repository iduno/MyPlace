package com.air.advantage.aaservice.data;

import com.air.advantage.aaservice.data.DataAircon.AirconMode;
import com.air.advantage.aaservice.data.DataAircon.SystemState;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.annotations.SerializedName;

import jakarta.annotation.Nullable;

/* compiled from: DataAirconInfo.java */
/* renamed from: com.air.advantage.aaservice.o.c */
/* loaded from: classes.dex */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataAirconInfo {

    @Nullable
    @SerializedName("aaAutoFanModeEnabled")
    @JsonProperty("aaAutoFanModeEnabled")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean aaAutoFanModeEnabled;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("activationCodeStatus")
    @JsonProperty("activationCodeStatus")
    @JsonView(JsonExporterViews.Export.class)
    public DataAircon.CodeStatus activationCodeStatus;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("airconErrorCode")
    @JsonProperty("airconErrorCode")
    @JsonView(JsonExporterViews.Export.class)
    public String airconErrorCode;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("cbFWRevMajor")
    @JsonProperty("cbFWRevMajor")
    @JsonView(JsonExporterViews.Export.class)
    public Integer cbFWRevMajor;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("cbFWRevMinor")
    @JsonProperty("cbFWRevMinor")
    @JsonView(JsonExporterViews.Export.class)
    public Integer cbFWRevMinor;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("cbType")
    @JsonProperty("cbType")
    @JsonView(JsonExporterViews.Export.class)
    public Integer cbType;

    @Nullable
    @SerializedName("climateControlModeEnabled")
    @JsonProperty("climateControlModeEnabled")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean climateControlModeEnabled;

    @Nullable
    @SerializedName("climateControlModeIsRunning")
    @JsonProperty("climateControlModeIsRunning")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean climateControlModeIsRunning;

    /* renamed from: constant1 */
    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("constant1")
    @JsonProperty("constant1")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer constantZone1;

    /* renamed from: constant2 */
    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("constant2")
    @JsonProperty("constant2")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer constantZone2;

    /* renamed from: constant3 */
    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("constant3")
    @JsonProperty("constant3")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer constantZone3;

    @Nullable
    @SerializedName("countDownToOff")
    @JsonProperty("countDownToOff")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer countDownToOff;

    @Nullable
    @SerializedName("countDownToOn")
    @JsonProperty("countDownToOn")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer countDownToOn;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("dbFWRevMajor")
    @JsonProperty("dbFWRevMajor")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer dbFWRevMajor;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("dbFWRevMinor")
    @JsonProperty("dbFWRevMinor")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer dbFWRevMinor;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("enabled")
    @JsonProperty("enabled")
    public Boolean enabled;

    @JsonExporter(saveThis = false)
    @Nullable
    public transient Long expireTime;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("fan")
    @JsonProperty("fan")
    public DataAircon.FanStatus fan;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("filterCleanStatus")
    @JsonProperty("filterCleanStatus")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer filterCleanStatus;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("freshAirStatus")
    @JsonProperty("freshAirStatus")
    public DataAircon.FreshAirStatus freshAirStatus;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("mode")
    @JsonProperty("mode")
    public AirconMode mode;

    @Nullable
    @SerializedName("myAutoCoolTargetTemp")
    @JsonProperty("myAutoCoolTargetTemp")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer myAutoCoolTargetTemp;

    @Nullable
    @SerializedName("myAutoHeatTargetTemp")
    @JsonProperty("myAutoHeatTargetTemp")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer myAutoHeatTargetTemp;

    @Nullable
    @SerializedName("myAutoModeCurrentSetMode")
    @JsonProperty("myAutoModeCurrentSetMode")
    public AirconMode myAutoModeCurrentSetMode;

    @Nullable
    @SerializedName("myAutoModeEnabled")
    @JsonProperty("myAutoModeEnabled")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean myAutoModeEnabled;

    @Nullable
    @SerializedName("myAutoModeIsRunning")
    @JsonProperty("myAutoModeIsRunning")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean myAutoModeIsRunning;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("myFanSpeedIsRunning")
    @JsonProperty("myFanSpeedIsRunning")
    public Boolean myFanSpeedIsRunning;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("myZone")
    @JsonProperty("myZone")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer myZone;

    @JsonExporter(saveThis = false)
    @Nullable
    private transient String myZoneName;

    @Nullable
    @SerializedName("name")
    @JsonProperty("name")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String name;

    /* renamed from: noOfConstants */
    @Nullable
    @SerializedName("noOfConstants")
    @JsonProperty("noOfConstants")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer noOfConstantZones;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("noOfZones")
    @JsonProperty("noOfZones")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer noOfZones;

    @Nullable
    @SerializedName("quietNightModeEnabled")
    @JsonProperty("quietNightModeEnabled")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean quietNightModeEnabled;

    @Nullable
    @SerializedName("quietNightModeIsRunning")
    @JsonProperty("quietNightModeIsRunning")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean quietNightModeIsRunning;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("rfFWRevMajor")
    @JsonProperty("rfFWRevMajor")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer rfFWRevMajor;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("rfSysID")
    @JsonProperty("rfSysID")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer rfSysID;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("setActivationCode")
    @JsonProperty("setActivationCode")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public DataAircon.ActivationCode setActivationCode;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("setActivationTime")
    @JsonProperty("setActivationTime")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer setActivationTime;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("setTemp")
    @JsonProperty("setTemp")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Float setTemp;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("state")
    @JsonProperty("state")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public SystemState state;

    @Nullable
    @SerializedName("uid")
    @JsonProperty("uid")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String uid;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("unitType")
    @JsonProperty("unitType")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer unitType;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("unlockCode")
    @JsonProperty("unlockCode")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String unlockCode;

    public void copyFrom(DataAirconInfo other) {
        if (other == null) return;
        if (other.aaAutoFanModeEnabled != null) this.aaAutoFanModeEnabled = other.aaAutoFanModeEnabled;
        if (other.activationCodeStatus != null) this.activationCodeStatus = other.activationCodeStatus;
        if (other.airconErrorCode != null) this.airconErrorCode = other.airconErrorCode;
        if (other.cbFWRevMajor != null) this.cbFWRevMajor = other.cbFWRevMajor;
        if (other.cbFWRevMinor != null) this.cbFWRevMinor = other.cbFWRevMinor;
        if (other.cbType != null) this.cbType = other.cbType;
        if (other.climateControlModeEnabled != null) this.climateControlModeEnabled = other.climateControlModeEnabled;
        if (other.climateControlModeIsRunning != null) this.climateControlModeIsRunning = other.climateControlModeIsRunning;
        if (other.constantZone1 != null) this.constantZone1 = other.constantZone1;
        if (other.constantZone2 != null) this.constantZone2 = other.constantZone2;
        if (other.constantZone3 != null) this.constantZone3 = other.constantZone3;
        if (other.countDownToOff != null) this.countDownToOff = other.countDownToOff;
        if (other.countDownToOn != null) this.countDownToOn = other.countDownToOn;
        if (other.dbFWRevMajor != null) this.dbFWRevMajor = other.dbFWRevMajor;
        if (other.dbFWRevMinor != null) this.dbFWRevMinor = other.dbFWRevMinor;
        if (other.enabled != null) this.enabled = other.enabled;
        if (other.expireTime != null) this.expireTime = other.expireTime;
        if (other.fan != null) this.fan = other.fan;
        if (other.filterCleanStatus != null) this.filterCleanStatus = other.filterCleanStatus;
        if (other.freshAirStatus != null) this.freshAirStatus = other.freshAirStatus;
        if (other.mode != null) this.mode = other.mode;
        if (other.myAutoCoolTargetTemp != null) this.myAutoCoolTargetTemp = other.myAutoCoolTargetTemp;
        if (other.myAutoHeatTargetTemp != null) this.myAutoHeatTargetTemp = other.myAutoHeatTargetTemp;
        if (other.myAutoModeCurrentSetMode != null) this.myAutoModeCurrentSetMode = other.myAutoModeCurrentSetMode;
        if (other.myAutoModeEnabled != null) this.myAutoModeEnabled = other.myAutoModeEnabled;
        if (other.myAutoModeIsRunning != null) this.myAutoModeIsRunning = other.myAutoModeIsRunning;
        if (other.myFanSpeedIsRunning != null) this.myFanSpeedIsRunning = other.myFanSpeedIsRunning;
        if (other.myZone != null) this.myZone = other.myZone;
        if (other.myZoneName != null) this.myZoneName = other.myZoneName;
        if (other.name != null) this.name = other.name;
        if (other.noOfConstantZones != null) this.noOfConstantZones = other.noOfConstantZones;
        if (other.noOfZones != null) this.noOfZones = other.noOfZones;
        if (other.quietNightModeEnabled != null) this.quietNightModeEnabled = other.quietNightModeEnabled;
        if (other.quietNightModeIsRunning != null) this.quietNightModeIsRunning = other.quietNightModeIsRunning;
        if (other.rfFWRevMajor != null) this.rfFWRevMajor = other.rfFWRevMajor;
        if (other.rfSysID != null) this.rfSysID = other.rfSysID;
        if (other.setActivationCode != null) this.setActivationCode = other.setActivationCode;
        if (other.setActivationTime != null) this.setActivationTime = other.setActivationTime;
        if (other.setTemp != null) this.setTemp = other.setTemp;
        if (other.state != null) this.state = other.state;
        if (other.uid != null) this.uid = other.uid;
        if (other.unitType != null) this.unitType = other.unitType;
        if (other.unlockCode != null) this.unlockCode = other.unlockCode;
    }
}