package com.air.advantage.aaservice.data;

import com.air.advantage.aaservice.data.DataAircon.AirconMode;
import com.air.advantage.aaservice.data.DataAircon.SystemState;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;


import jakarta.annotation.Nullable;

/* compiled from: DataAirconInfo.java */
/* renamed from: com.air.advantage.aaservice.o.c */
/* loaded from: classes.dex */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataAirconInfo {

    @Nullable

    @JsonProperty("aaAutoFanModeEnabled")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean aaAutoFanModeEnabled;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("activationCodeStatus")
    @JsonView(JsonExporterViews.Export.class)
    public DataAircon.CodeStatus activationCodeStatus;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("airconErrorCode")
    @JsonView(JsonExporterViews.Export.class)
    public String airconErrorCode;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("cbFWRevMajor")
    @JsonView(JsonExporterViews.Export.class)
    public Integer cbFWRevMajor;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("cbFWRevMinor")
    @JsonView(JsonExporterViews.Export.class)
    public Integer cbFWRevMinor;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("cbType")
    @JsonView(JsonExporterViews.Export.class)
    public Integer cbType;

    @Nullable

    @JsonProperty("climateControlModeEnabled")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean climateControlModeEnabled;

    @Nullable

    @JsonProperty("climateControlModeIsRunning")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean climateControlModeIsRunning;

    /* renamed from: constant1 */
    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("constant1")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer constantZone1;

    /* renamed from: constant2 */
    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("constant2")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer constantZone2;

    /* renamed from: constant3 */
    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("constant3")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer constantZone3;

    @Nullable

    @JsonProperty("countDownToOff")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer countDownToOff;

    @Nullable

    @JsonProperty("countDownToOn")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer countDownToOn;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("dbFWRevMajor")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer dbFWRevMajor;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("dbFWRevMinor")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer dbFWRevMinor;

    @JsonExporter(export = false)
    @Nullable

    @JsonProperty("enabled")
    @JsonView(JsonExporterViews.Export.class)
    public Boolean enabled;

    @JsonExporter(saveThis = false)
    @Nullable
    public transient Long expireTime;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("fan")
    @JsonView(JsonExporterViews.Export.class)
    public DataAircon.FanStatus fan;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("filterCleanStatus")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer filterCleanStatus;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("freshAirStatus")
    @JsonView(JsonExporterViews.Export.class)
    public DataAircon.FreshAirStatus freshAirStatus;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("mode")
    @JsonView(JsonExporterViews.Export.class)
    public AirconMode mode;

    @Nullable

    @JsonProperty("myAutoCoolTargetTemp")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer myAutoCoolTargetTemp;

    @Nullable

    @JsonProperty("myAutoHeatTargetTemp")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer myAutoHeatTargetTemp;

    @Nullable

    @JsonProperty("myAutoModeCurrentSetMode")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public AirconMode myAutoModeCurrentSetMode;

    @Nullable

    @JsonProperty("myAutoModeEnabled")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean myAutoModeEnabled;

    @Nullable

    @JsonProperty("myAutoModeIsRunning")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean myAutoModeIsRunning;

    @JsonExporter(export = false)
    @Nullable

    @JsonProperty("myFanSpeedIsRunning")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean myFanSpeedIsRunning;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("myZone")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer myZone;

    @JsonExporter(saveThis = false)
    @Nullable
    private transient String myZoneName;

    @Nullable

    @JsonProperty("name")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String name;

    /* renamed from: noOfConstants */
    @Nullable

    @JsonProperty("noOfConstants")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer noOfConstantZones;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("noOfZones")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer noOfZones;

    @Nullable

    @JsonProperty("quietNightModeEnabled")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean quietNightModeEnabled;

    @Nullable

    @JsonProperty("quietNightModeIsRunning")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean quietNightModeIsRunning;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("rfFWRevMajor")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer rfFWRevMajor;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("rfSysID")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer rfSysID;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("setActivationCode")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public DataAircon.ActivationCode setActivationCode;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("setActivationTime")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer setActivationTime;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("setTemp")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Float setTemp;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("state")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public SystemState state;

    @Nullable

    @JsonProperty("uid")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String uid;

    @JsonExporter(saveThis = false)
    @Nullable

    @JsonProperty("unitType")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer unitType;

    @JsonExporter(saveThis = false)
    @Nullable

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