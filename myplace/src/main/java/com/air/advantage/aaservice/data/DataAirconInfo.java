package com.air.advantage.aaservice.data;

import com.air.advantage.libraryairconlightjson.AirconMode;
import com.air.advantage.libraryairconlightjson.SystemState;
import com.google.gson.annotations.SerializedName;

import jakarta.annotation.Nullable;

/* compiled from: DataAirconInfo.java */
/* renamed from: com.air.advantage.aaservice.o.c */
/* loaded from: classes.dex */
public class DataAirconInfo {

    @Nullable
    @SerializedName("aaAutoFanModeEnabled")
    public Boolean aaAutoFanModeEnabled;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("activationCodeStatus")
    public DataAircon.CodeStatus activationCodeStatus;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("airconErrorCode")

    public String airconErrorCode;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("cbFWRevMajor")

    public Integer cbFWRevMajor;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("cbFWRevMinor")

    public Integer cbFWRevMinor;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("cbType")

    public Integer cbType;

    @Nullable
    @SerializedName("climateControlModeEnabled")

    public Boolean climateControlModeEnabled;

    @Nullable
    @SerializedName("climateControlModeIsRunning")

    public Boolean climateControlModeIsRunning;

    /* renamed from: constant1 */
    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("constant1")

    public Integer constantZone1;

    /* renamed from: constant2 */
    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("constant2")

    public Integer constantZone2;

    /* renamed from: constant3 */
    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("constant3")

    public Integer constantZone3;

    @Nullable
    @SerializedName("countDownToOff")

    public Integer countDownToOff;

    @Nullable
    @SerializedName("countDownToOn")

    public Integer countDownToOn;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("dbFWRevMajor")

    public Integer dbFWRevMajor;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("dbFWRevMinor")

    public Integer dbFWRevMinor;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("enabled")
    public Boolean enabled;

    @JsonExporter(saveThis = false)
    @Nullable
    public transient Long expireTime;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("fan")
    public DataAircon.FanStatus fan;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("filterCleanStatus")
    public Integer filterCleanStatus;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("freshAirStatus")
    public DataAircon.FreshAirStatus freshAirStatus;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("mode")
    public AirconMode mode;

    @Nullable
    @SerializedName("myAutoCoolTargetTemp")
    public Integer myAutoCoolTargetTemp;

    @Nullable
    @SerializedName("myAutoHeatTargetTemp")
    public Integer myAutoHeatTargetTemp;

    @Nullable
    @SerializedName("myAutoModeCurrentSetMode")
    public AirconMode myAutoModeCurrentSetMode;

    @Nullable
    @SerializedName("myAutoModeEnabled")
    public Boolean myAutoModeEnabled;

    @Nullable
    @SerializedName("myAutoModeIsRunning")
    public Boolean myAutoModeIsRunning;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("myFanSpeedIsRunning")
    public Boolean myFanSpeedIsRunning;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("myZone")
    public Integer myZone;

    @JsonExporter(saveThis = false)
    @Nullable
    private transient String myZoneName;

    @Nullable
    @SerializedName("name")
    public String name;

    /* renamed from: noOfConstants */
    @Nullable
    @SerializedName("noOfConstants")
    public Integer noOfConstantZones;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("noOfZones")
    public Integer noOfZones;

    @Nullable
    @SerializedName("quietNightModeEnabled")
    public Boolean quietNightModeEnabled;

    @Nullable
    @SerializedName("quietNightModeIsRunning")
    public Boolean quietNightModeIsRunning;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("rfFWRevMajor")
    public Integer rfFWRevMajor;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("rfSysID")
    public Integer rfSysID;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("setActivationCode")
    public DataAircon.ActivationCode setActivationCode;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("setActivationTime")
    public Integer setActivationTime;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("setTemp")
    public Float setTemp;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("state")
    public SystemState state;

    @Nullable
    @SerializedName("uid")
    public String uid;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("unitType")
    public Integer unitType;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("unlockCode")
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