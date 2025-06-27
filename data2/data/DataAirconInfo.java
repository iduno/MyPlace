package com.air.advantage.data;

import com.air.advantage.ActivityMain;
import com.air.advantage.data.DataAirconSystem;
import com.air.advantage.libraryairconlightjson.AirconFunctionsConstants;
import com.air.advantage.libraryairconlightjson.AirconMode;
import com.air.advantage.libraryairconlightjson.FanStatus;
import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.air.advantage.libraryairconlightjson.SystemState;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.e */
/* loaded from: classes.dex */
public final class DataAirconInfo {

    @Nullable
    @SerializedName("aaAutoFanModeEnabled")
    @JvmField
    public Boolean aaAutoFanModeEnabled;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("activationCodeStatus")
    @JvmField
    public DataAirconSystem.CodeStatus activationCodeStatus;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("airconErrorCode")
    @JvmField
    public String airconErrorCode;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("cbFWRevMajor")
    @JvmField
    public Integer cbFWRevMajor;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("cbFWRevMinor")
    @JvmField
    public Integer cbFWRevMinor;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("cbType")
    @JvmField
    public Integer cbType;

    @Nullable
    @SerializedName("climateControlModeEnabled")
    @JvmField
    public Boolean climateControlModeEnabled;

    @Nullable
    @SerializedName("climateControlModeIsRunning")
    @JvmField
    public Boolean climateControlModeIsRunning;

    /* renamed from: constant1 */
    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("constant1")
    @JvmField
    public Integer constantZone1;

    /* renamed from: constant2 */
    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("constant2")
    @JvmField
    public Integer constantZone2;

    /* renamed from: constant3 */
    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("constant3")
    @JvmField
    public Integer constantZone3;

    @Nullable
    @SerializedName("countDownToOff")
    @JvmField
    public Integer countDownToOff;

    @Nullable
    @SerializedName("countDownToOn")
    @JvmField
    public Integer countDownToOn;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("dbFWRevMajor")
    @JvmField
    public Integer dbFWRevMajor;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("dbFWRevMinor")
    @JvmField
    public Integer dbFWRevMinor;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("enabled")
    @JvmField
    public Boolean enabled;

    @Exclude
    @Nullable
    @JvmField
    public transient Long expireTime;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("fan")
    @JvmField
    public FanStatus fan;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("filterCleanStatus")
    @JvmField
    public Integer filterCleanStatus;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("freshAirStatus")
    @JvmField
    public DataAirconSystem.FreshAirStatus freshAirStatus;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("mode")
    @JvmField
    public AirconMode mode;

    @Nullable
    @SerializedName("myAutoCoolTargetTemp")
    @JvmField
    public Integer myAutoCoolTargetTemp;

    @Nullable
    @SerializedName("myAutoHeatTargetTemp")
    @JvmField
    public Integer myAutoHeatTargetTemp;

    @Nullable
    @SerializedName("myAutoModeCurrentSetMode")
    @JvmField
    public AirconMode myAutoModeCurrentSetMode;

    @Nullable
    @SerializedName("myAutoModeEnabled")
    @JvmField
    public Boolean myAutoModeEnabled;

    @Nullable
    @SerializedName("myAutoModeIsRunning")
    @JvmField
    public Boolean myAutoModeIsRunning;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("myFanSpeedIsRunning")
    @JvmField
    public Boolean myFanSpeedIsRunning;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("myZone")
    @JvmField
    public Integer myZone;

    @Exclude
    @Nullable
    private transient String myZoneName;

    @Nullable
    @SerializedName("name")
    @JvmField
    public String name;

    /* renamed from: noOfConstants */
    @Nullable
    @SerializedName("noOfConstants")
    @JvmField
    public Integer noOfConstantZones;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("noOfZones")
    @JvmField
    public Integer noOfZones;

    @Nullable
    @SerializedName("quietNightModeEnabled")
    @JvmField
    public Boolean quietNightModeEnabled;

    @Nullable
    @SerializedName("quietNightModeIsRunning")
    @JvmField
    public Boolean quietNightModeIsRunning;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("rfFWRevMajor")
    @JvmField
    public Integer rfFWRevMajor;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("rfSysID")
    @JvmField
    public Integer rfSysID;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("setActivationCode")
    @JvmField
    public DataAirconSystem.ActivationCode setActivationCode;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("setActivationTime")
    @JvmField
    public Integer setActivationTime;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("setTemp")
    @JvmField
    public Float setTemp;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("state")
    @JvmField
    public SystemState state;

    @Nullable
    @SerializedName(ActivityMain.UID)
    @JvmField
    public String uid;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("unitType")
    @JvmField
    public Integer unitType;

    @JsonExporter(saveThis = false)
    @Nullable
    @SerializedName("unlockCode")
    @JvmField
    public String unlockCode;

    @Exclude
    @Nullable
    @JvmField
    public transient Float myAirTargetTemperature = Float.valueOf(0.0f);

    @Exclude
    @Nullable
    private transient Boolean onAAHardware = Boolean.TRUE;

    public static /* synthetic */ boolean update$default(DataAirconInfo dataAirconInfo, DataAirconInfo dataAirconInfo2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 4) != 0) {
            z7 = false;
        }
        return dataAirconInfo.update(dataAirconInfo2, dataManager, z7);
    }

    public final void Initialisation() {
        this.countDownToOn = 0;
        this.countDownToOff = 0;
        this.airconErrorCode = "";
    }

    public final void clearDataForBackup() {
        this.activationCodeStatus = null;
        this.airconErrorCode = null;
        this.cbFWRevMajor = null;
        this.cbFWRevMinor = null;
        this.cbType = null;
        this.climateControlModeIsRunning = null;
        this.constantZone1 = null;
        this.constantZone2 = null;
        this.constantZone3 = null;
        this.countDownToOff = null;
        this.countDownToOn = null;
        this.enabled = null;
        this.expireTime = null;
        this.fan = null;
        this.filterCleanStatus = null;
        this.freshAirStatus = null;
        this.mode = null;
        this.myAirTargetTemperature = null;
        this.myAutoModeCurrentSetMode = null;
        this.myAutoModeIsRunning = null;
        this.myFanSpeedIsRunning = null;
        this.myZone = null;
        this.myZoneName = null;
        this.noOfConstantZones = null;
        this.noOfZones = null;
        this.onAAHardware = null;
        this.quietNightModeIsRunning = null;
        this.rfFWRevMajor = null;
        this.rfSysID = null;
        this.setActivationCode = null;
        this.setActivationTime = null;
        this.setTemp = null;
        this.state = null;
        this.unitType = null;
        this.unlockCode = null;
    }

    public final void completeAirconData() {
        if (this.noOfZones == null) {
            this.noOfZones = 0;
        }
        if (this.noOfConstantZones == null) {
            this.noOfConstantZones = 0;
        }
        if (this.constantZone1 == null) {
            this.constantZone1 = 0;
        }
        if (this.constantZone2 == null) {
            this.constantZone2 = 0;
        }
        if (this.constantZone3 == null) {
            this.constantZone3 = 0;
        }
        Integer num = this.noOfConstantZones;
        if (num != null && num.intValue() == 0) {
            this.constantZone1 = 0;
            this.constantZone2 = 0;
            this.constantZone3 = 0;
        } else if (num != null && num.intValue() == 1) {
            this.constantZone2 = 0;
            this.constantZone3 = 0;
        } else if (num != null && num.intValue() == 2) {
            this.constantZone3 = 0;
        }
        if (this.filterCleanStatus == null) {
            this.filterCleanStatus = 1;
        }
        if (this.state == null) {
            this.state = DataAirconSystem.Companion.getDEFAULT_STATE();
        }
        if (this.mode == null) {
            this.mode = DataAirconSystem.DEFAULT_MODE;
        }
        if (this.fan == null) {
            this.fan = DataAirconSystem.DEFAULT_FAN;
        }
        if (this.setTemp == null) {
            this.setTemp = Float.valueOf(25.0f);
        }
        if (this.myZone == null) {
            this.myZone = 0;
        }
        if (this.freshAirStatus == null) {
            this.freshAirStatus = DataAirconSystem.Companion.getDEFAULT_FRESHAIRSTATUS();
        }
        if (this.rfSysID == null) {
            this.rfSysID = 0;
        }
    }

    @NotNull
    public final DataAirconInfo copy() {
        DataAirconInfo dataAirconInfo = new DataAirconInfo();
        update$default(dataAirconInfo, this, null, false, 4, null);
        return dataAirconInfo;
    }

    @Exclude
    @Nullable
    public final String getAirconName() {
        String str = this.name;
        if (str != null) {
            if (str == null || str.length() == 0) {
                return getDefaultAirconName();
            }
        }
        return this.name;
    }

    @Exclude
    @NotNull
    public final String getDefaultAirconName() {
        String str = this.uid;
        if (str == null) {
            return AirconFunctionsConstants.AIRCON;
        }
        return "Aircon " + str;
    }

    @Nullable
    public final String getMyZoneName() {
        return this.myZoneName;
    }

    @Nullable
    public final Boolean getOnAAHardware() {
        return this.onAAHardware;
    }

    public final boolean isZoneConstant(int i10) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4 = this.noOfConstantZones;
        Intrinsics.checkNotNull(num4);
        if (num4.intValue() >= 1 && (num3 = this.constantZone1) != null && i10 == num3.intValue()) {
            return true;
        }
        Integer num5 = this.noOfConstantZones;
        Intrinsics.checkNotNull(num5);
        if (num5.intValue() >= 2 && (num2 = this.constantZone2) != null && i10 == num2.intValue()) {
            return true;
        }
        Integer num6 = this.noOfConstantZones;
        Intrinsics.checkNotNull(num6);
        return num6.intValue() >= 3 && (num = this.constantZone3) != null && i10 == num.intValue();
    }

    public final void sanitiseData() {
        this.activationCodeStatus = null;
        this.airconErrorCode = null;
        this.cbFWRevMajor = null;
        this.cbFWRevMinor = null;
        this.rfFWRevMajor = null;
        this.constantZone1 = null;
        this.constantZone2 = null;
        this.constantZone3 = null;
        this.enabled = null;
        this.expireTime = null;
        this.filterCleanStatus = null;
        this.myAirTargetTemperature = null;
        this.myZoneName = null;
        this.noOfConstantZones = null;
        this.noOfZones = null;
        this.onAAHardware = null;
        this.rfSysID = null;
        this.setActivationCode = null;
        this.setActivationTime = null;
        this.uid = null;
        this.unitType = null;
        this.unlockCode = null;
        this.cbType = null;
    }

    public final void setAirconName(@Nullable String str) {
        if (str != null) {
            if (str.length() == 0) {
                this.name = getDefaultAirconName();
            } else {
                this.name = str;
            }
        }
    }

    public final void setMyZoneName(@Nullable String str) {
        this.myZoneName = str;
    }

    public final void setOnAAHardware(@Nullable Boolean bool) {
        this.onAAHardware = bool;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@NotNull DataAirconInfo airconSource, @Nullable DataManager dataManager) {
        Intrinsics.checkNotNullParameter(airconSource, "airconSource");
        return update$default(this, airconSource, dataManager, false, 4, null);
    }

    public final boolean updateForCBZL(@NotNull DataAirconInfo airconSource) {
        boolean z7;
        String str;
        Long l8;
        Boolean bool;
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        DataAirconSystem.CodeStatus codeStatus;
        Intrinsics.checkNotNullParameter(airconSource, "airconSource");
        DataAirconSystem.CodeStatus codeStatus2 = airconSource.activationCodeStatus;
        if (codeStatus2 == null || ((codeStatus = this.activationCodeStatus) != null && codeStatus == codeStatus2)) {
            z7 = false;
        } else {
            this.activationCodeStatus = codeStatus2;
            z7 = true;
        }
        Integer num5 = airconSource.cbFWRevMajor;
        if (num5 != null && ((num4 = this.cbFWRevMajor) == null || !Intrinsics.areEqual(num4, num5))) {
            this.cbFWRevMajor = airconSource.cbFWRevMajor;
            z7 = true;
        }
        Integer num6 = airconSource.cbFWRevMinor;
        if (num6 != null && ((num3 = this.cbFWRevMinor) == null || !Intrinsics.areEqual(num3, num6))) {
            this.cbFWRevMinor = airconSource.cbFWRevMinor;
            z7 = true;
        }
        Integer num7 = airconSource.rfFWRevMajor;
        if (num7 != null && ((num2 = this.rfFWRevMajor) == null || !Intrinsics.areEqual(num2, num7))) {
            this.rfFWRevMajor = airconSource.rfFWRevMajor;
            z7 = true;
        }
        Integer num8 = airconSource.cbType;
        if (num8 != null && ((num = this.cbType) == null || !Intrinsics.areEqual(num, num8))) {
            this.cbType = airconSource.cbType;
            z7 = true;
        }
        Boolean bool2 = airconSource.enabled;
        if (bool2 != null && ((bool = this.enabled) == null || !Intrinsics.areEqual(bool, bool2))) {
            this.enabled = airconSource.enabled;
            z7 = true;
        }
        Long l10 = airconSource.expireTime;
        if (l10 != null && ((l8 = this.expireTime) == null || !Intrinsics.areEqual(l8, l10))) {
            this.expireTime = airconSource.expireTime;
        }
        String str2 = airconSource.uid;
        if (str2 == null || ((str = this.uid) != null && Intrinsics.areEqual(str, str2))) {
            return z7;
        }
        this.uid = airconSource.uid;
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    /* JADX WARN: Removed duplicated region for block: B:242:0x060f  */
    @JvmOverloads
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean update(@NotNull DataAirconInfo airconSource, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        String str;
        Long l8;
        Boolean bool;
        Intrinsics.checkNotNullParameter(airconSource, "airconSource");
        Boolean bool2 = airconSource.aaAutoFanModeEnabled;
        boolean z11 = true;
        if (bool2 != null) {
            Boolean bool3 = this.aaAutoFanModeEnabled;
            if (bool3 == null || !Intrinsics.areEqual(bool3, bool2)) {
                this.aaAutoFanModeEnabled = airconSource.aaAutoFanModeEnabled;
                if (dataManager != null) {
                    dataManager.add("aaAutoFanModeEnabled", airconSource.aaAutoFanModeEnabled);
                }
                z10 = true;
            }
            z10 = false;
        } else {
            if (z7 && this.aaAutoFanModeEnabled != null) {
                if (dataManager != null) {
                    dataManager.add("aaAutoFanModeEnabled", null);
                }
                z10 = true;
            }
            z10 = false;
        }
        Boolean bool4 = airconSource.climateControlModeEnabled;
        if (bool4 != null) {
            Boolean bool5 = this.climateControlModeEnabled;
            if (bool5 == null || !Intrinsics.areEqual(bool5, bool4)) {
                this.climateControlModeEnabled = airconSource.climateControlModeEnabled;
                if (dataManager != null) {
                    dataManager.add("climateControlModeEnabled", airconSource.climateControlModeEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.climateControlModeEnabled != null) {
            if (dataManager != null) {
                dataManager.add("climateControlModeEnabled", null);
            }
            z10 = true;
        }
        Boolean bool6 = airconSource.climateControlModeIsRunning;
        if (bool6 != null) {
            Boolean bool7 = this.climateControlModeIsRunning;
            if (bool7 == null || !Intrinsics.areEqual(bool7, bool6)) {
                this.climateControlModeIsRunning = airconSource.climateControlModeIsRunning;
                if (dataManager != null) {
                    dataManager.add("climateControlModeIsRunning", airconSource.climateControlModeIsRunning);
                }
                z10 = true;
            }
        } else if (z7 && this.climateControlModeIsRunning != null) {
            if (dataManager != null) {
                dataManager.add("climateControlModeIsRunning", null);
            }
            z10 = true;
        }
        Boolean bool8 = airconSource.myAutoModeEnabled;
        if (bool8 != null) {
            Boolean bool9 = this.myAutoModeEnabled;
            if (bool9 == null || !Intrinsics.areEqual(bool9, bool8)) {
                this.myAutoModeEnabled = airconSource.myAutoModeEnabled;
                if (dataManager != null) {
                    dataManager.add("myAutoModeEnabled", airconSource.myAutoModeEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.myAutoModeEnabled != null) {
            if (dataManager != null) {
                dataManager.add("myAutoModeEnabled", null);
            }
            z10 = true;
        }
        AirconMode airconMode = airconSource.myAutoModeCurrentSetMode;
        if (airconMode != null) {
            AirconMode airconMode2 = this.myAutoModeCurrentSetMode;
            if (airconMode2 == null || airconMode2 != airconMode) {
                this.myAutoModeCurrentSetMode = airconMode;
                if (dataManager != null) {
                    dataManager.add("myAutoModeCurrentSetMode", airconSource.myAutoModeCurrentSetMode);
                }
                z10 = true;
            }
        } else if (z7 && this.myAutoModeCurrentSetMode != null) {
            if (dataManager != null) {
                dataManager.add("myAutoModeCurrentSetMode", null);
            }
            z10 = true;
        }
        Boolean bool10 = airconSource.myAutoModeIsRunning;
        if (bool10 != null) {
            Boolean bool11 = this.myAutoModeIsRunning;
            if (bool11 == null || !Intrinsics.areEqual(bool11, bool10)) {
                this.myAutoModeIsRunning = airconSource.myAutoModeIsRunning;
                if (dataManager != null) {
                    dataManager.add("myAutoModeIsRunning", airconSource.myAutoModeIsRunning);
                }
                z10 = true;
            }
        } else if (z7 && this.myAutoModeIsRunning != null) {
            if (dataManager != null) {
                dataManager.add("myAutoModeIsRunning", null);
            }
            z10 = true;
        }
        Boolean bool12 = airconSource.myFanSpeedIsRunning;
        if (bool12 != null) {
            Boolean bool13 = this.myFanSpeedIsRunning;
            if (bool13 == null || !Intrinsics.areEqual(bool13, bool12)) {
                this.myFanSpeedIsRunning = airconSource.myFanSpeedIsRunning;
                z10 = true;
            }
        } else if (z7 && this.myFanSpeedIsRunning != null) {
            if (dataManager != null) {
                dataManager.add("myFanSpeedIsRunning", null);
            }
            z10 = true;
        }
        DataAirconSystem.CodeStatus codeStatus = airconSource.activationCodeStatus;
        if (codeStatus != null) {
            DataAirconSystem.CodeStatus codeStatus2 = this.activationCodeStatus;
            if (codeStatus2 == null || codeStatus2 != codeStatus) {
                this.activationCodeStatus = codeStatus;
                if (dataManager != null) {
                    dataManager.add("activationCodeStatus", airconSource.activationCodeStatus);
                }
                z10 = true;
            }
        } else if (z7 && this.activationCodeStatus != null) {
            if (dataManager != null) {
                dataManager.add("activationCodeStatus", null);
            }
            z10 = true;
        }
        String str2 = airconSource.airconErrorCode;
        if (str2 != null) {
            String str3 = this.airconErrorCode;
            if (str3 == null || !Intrinsics.areEqual(str3, str2)) {
                this.airconErrorCode = airconSource.airconErrorCode;
                if (dataManager != null) {
                    dataManager.add("airconErrorCode", airconSource.airconErrorCode);
                }
                z10 = true;
            }
        } else if (z7 && this.airconErrorCode != null) {
            if (dataManager != null) {
                dataManager.add("airconErrorCode", null);
            }
            z10 = true;
        }
        Integer num = airconSource.cbFWRevMajor;
        if (num != null) {
            Integer num2 = this.cbFWRevMajor;
            if (num2 == null || !Intrinsics.areEqual(num2, num)) {
                this.cbFWRevMajor = airconSource.cbFWRevMajor;
                if (dataManager != null) {
                    dataManager.add("cbFWRevMajor", airconSource.cbFWRevMajor);
                }
                z10 = true;
            }
        } else if (z7 && this.cbFWRevMajor != null) {
            if (dataManager != null) {
                dataManager.add("cbFWRevMajor", null);
            }
            z10 = true;
        }
        Integer num3 = airconSource.cbFWRevMinor;
        if (num3 != null) {
            Integer num4 = this.cbFWRevMinor;
            if (num4 == null || !Intrinsics.areEqual(num4, num3)) {
                this.cbFWRevMinor = airconSource.cbFWRevMinor;
                if (dataManager != null) {
                    dataManager.add("cbFWRevMinor", airconSource.cbFWRevMinor);
                }
                z10 = true;
            }
        } else if (z7 && this.cbFWRevMinor != null) {
            if (dataManager != null) {
                dataManager.add("cbFWRevMinor", null);
            }
            z10 = true;
        }
        Integer num5 = airconSource.rfFWRevMajor;
        if (num5 != null) {
            Integer num6 = this.rfFWRevMajor;
            if (num6 == null || !Intrinsics.areEqual(num6, num5)) {
                this.rfFWRevMajor = airconSource.rfFWRevMajor;
                if (dataManager != null) {
                    dataManager.add("rfFWRevMajor", airconSource.rfFWRevMajor);
                }
                z10 = true;
            }
        } else if (z7 && this.rfFWRevMajor != null) {
            if (dataManager != null) {
                dataManager.add("rfFWRevMajor", null);
            }
            z10 = true;
        }
        Integer num7 = airconSource.constantZone1;
        if (num7 != null) {
            Integer num8 = this.constantZone1;
            if (num8 == null || !Intrinsics.areEqual(num8, num7)) {
                this.constantZone1 = airconSource.constantZone1;
                if (dataManager != null) {
                    dataManager.add("constant1", airconSource.constantZone1);
                }
                z10 = true;
            }
        } else if (z7 && this.constantZone1 != null) {
            if (dataManager != null) {
                dataManager.add("constant1", null);
            }
            z10 = true;
        }
        Integer num9 = airconSource.constantZone2;
        if (num9 != null) {
            Integer num10 = this.constantZone2;
            if (num10 == null || !Intrinsics.areEqual(num10, num9)) {
                this.constantZone2 = airconSource.constantZone2;
                if (dataManager != null) {
                    dataManager.add("constant2", airconSource.constantZone2);
                }
                z10 = true;
            }
        } else if (z7 && this.constantZone2 != null) {
            if (dataManager != null) {
                dataManager.add("constant2", null);
            }
            z10 = true;
        }
        Integer num11 = airconSource.constantZone3;
        if (num11 != null) {
            Integer num12 = this.constantZone3;
            if (num12 == null || !Intrinsics.areEqual(num12, num11)) {
                this.constantZone3 = airconSource.constantZone3;
                if (dataManager != null) {
                    dataManager.add("constant3", airconSource.constantZone3);
                }
                z10 = true;
            }
        } else if (z7 && this.constantZone3 != null) {
            if (dataManager != null) {
                dataManager.add("constant3", null);
            }
            z10 = true;
        }
        Integer num13 = airconSource.countDownToOff;
        if (num13 != null) {
            Integer num14 = this.countDownToOff;
            if (num14 == null || !Intrinsics.areEqual(num14, num13)) {
                Integer num15 = airconSource.countDownToOff;
                this.countDownToOff = num15;
                if (dataManager != null) {
                    dataManager.add("countDownToOff", num15);
                }
                z10 = true;
            }
        } else if (z7 && this.countDownToOff != null) {
            if (dataManager != null) {
                dataManager.add("countDownToOff", null);
            }
            z10 = true;
        }
        Integer num16 = airconSource.countDownToOn;
        if (num16 != null) {
            Integer num17 = this.countDownToOn;
            if (num17 == null || !Intrinsics.areEqual(num17, num16)) {
                Integer num18 = airconSource.countDownToOn;
                this.countDownToOn = num18;
                if (dataManager != null) {
                    dataManager.add("countDownToOn", num18);
                }
                z10 = true;
            }
        } else if (z7 && this.countDownToOn != null) {
            if (dataManager != null) {
                dataManager.add("countDownToOn", null);
            }
            z10 = true;
        }
        Boolean bool14 = airconSource.enabled;
        if (bool14 != null && ((bool = this.enabled) == null || !Intrinsics.areEqual(bool, bool14))) {
            this.enabled = airconSource.enabled;
            z10 = true;
        }
        Long l10 = airconSource.expireTime;
        if (l10 != null && ((l8 = this.expireTime) == null || !Intrinsics.areEqual(l8, l10))) {
            this.expireTime = airconSource.expireTime;
        }
        FanStatus fanStatus = airconSource.fan;
        if (fanStatus != null) {
            FanStatus fanStatus2 = this.fan;
            if (fanStatus2 == null || fanStatus2 != fanStatus) {
                this.fan = fanStatus;
                if (dataManager != null) {
                    dataManager.add("fan", fanStatus);
                }
                z10 = true;
            }
        } else if (z7 && this.fan != null) {
            if (dataManager != null) {
                dataManager.add("fan", null);
            }
            z10 = true;
        }
        Integer num19 = airconSource.filterCleanStatus;
        if (num19 != null) {
            Integer num20 = this.filterCleanStatus;
            if (num20 == null || !Intrinsics.areEqual(num20, num19)) {
                this.filterCleanStatus = airconSource.filterCleanStatus;
                if (dataManager != null) {
                    dataManager.add("filterCleanStatus", airconSource.filterCleanStatus);
                }
                z10 = true;
            }
        } else if (z7 && this.filterCleanStatus != null) {
            if (dataManager != null) {
                dataManager.add("filterCleanStatus", null);
            }
            z10 = true;
        }
        DataAirconSystem.FreshAirStatus freshAirStatus = airconSource.freshAirStatus;
        if (freshAirStatus != null) {
            DataAirconSystem.FreshAirStatus freshAirStatus2 = this.freshAirStatus;
            if (freshAirStatus2 == null || freshAirStatus2 != freshAirStatus) {
                this.freshAirStatus = freshAirStatus;
                if (dataManager != null) {
                    dataManager.add("freshAirStatus", freshAirStatus);
                }
                z10 = true;
            }
        } else if (z7 && this.freshAirStatus != null) {
            if (dataManager != null) {
                dataManager.add("freshAirStatus", null);
            }
            z10 = true;
        }
        AirconMode airconMode3 = airconSource.mode;
        if (airconMode3 != null) {
            AirconMode airconMode4 = this.mode;
            if (airconMode4 == null || airconMode4 != airconMode3) {
                this.mode = airconMode3;
                if (dataManager != null) {
                    dataManager.add("mode", airconMode3);
                }
                z10 = true;
            }
        } else if (z7 && this.mode != null) {
            if (dataManager != null) {
                dataManager.add("mode", null);
            }
            z10 = true;
        }
        Integer num21 = airconSource.myZone;
        if (num21 != null) {
            Integer num22 = this.myZone;
            if (num22 == null || !Intrinsics.areEqual(num22, num21)) {
                Integer num23 = airconSource.myZone;
                this.myZone = num23;
                if (dataManager != null) {
                    dataManager.add("myZone", num23);
                }
                z10 = true;
            }
        } else if (z7 && this.myZone != null) {
            if (dataManager != null) {
                dataManager.add("myZone", null);
            }
            z10 = true;
        }
        String str4 = airconSource.myZoneName;
        if (str4 != null) {
            String str5 = this.myZoneName;
            if (str5 == null || !Intrinsics.areEqual(str5, str4)) {
                this.myZoneName = airconSource.myZoneName;
                z10 = true;
            }
        } else if (z7 && this.myZoneName != null) {
            if (dataManager != null) {
                dataManager.add("myZoneName", null);
            }
            z10 = true;
        }
        String str6 = airconSource.name;
        if (str6 != null) {
            String str7 = this.name;
            if (str7 == null || !Intrinsics.areEqual(str7, str6)) {
                this.name = airconSource.name;
                if (dataManager != null) {
                    dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, airconSource.name);
                }
                z10 = true;
            }
        } else if (z7 && this.name != null) {
            if (dataManager != null) {
                dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, null);
            }
            z10 = true;
        }
        Boolean bool15 = airconSource.quietNightModeEnabled;
        if (bool15 != null) {
            Boolean bool16 = this.quietNightModeEnabled;
            if (bool16 == null || !Intrinsics.areEqual(bool16, bool15)) {
                this.quietNightModeEnabled = airconSource.quietNightModeEnabled;
                if (dataManager != null) {
                    dataManager.add("quietNightModeEnabled", airconSource.quietNightModeEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.quietNightModeEnabled != null) {
            if (dataManager != null) {
                dataManager.add("quietNightModeEnabled", null);
            }
            z10 = true;
        }
        Boolean bool17 = airconSource.quietNightModeIsRunning;
        if (bool17 != null) {
            Boolean bool18 = this.quietNightModeIsRunning;
            if (bool18 == null || !Intrinsics.areEqual(bool18, bool17)) {
                this.quietNightModeIsRunning = airconSource.quietNightModeIsRunning;
                if (dataManager != null) {
                    dataManager.add("quietNightModeIsRunning", airconSource.quietNightModeIsRunning);
                }
                z10 = true;
            }
        } else if (z7 && this.quietNightModeIsRunning != null) {
            if (dataManager != null) {
                dataManager.add("quietNightModeIsRunning", null);
            }
            z10 = true;
        }
        Integer num24 = airconSource.noOfConstantZones;
        if (num24 != null) {
            Integer num25 = this.noOfConstantZones;
            if (num25 == null || !Intrinsics.areEqual(num25, num24)) {
                this.noOfConstantZones = airconSource.noOfConstantZones;
                if (dataManager != null) {
                    dataManager.add("noOfConstants", airconSource.noOfConstantZones);
                }
                z10 = true;
            }
        } else if (z7 && this.noOfConstantZones != null) {
            if (dataManager != null) {
                dataManager.add("noOfConstants", null);
            }
            z10 = true;
        }
        Integer num26 = airconSource.noOfZones;
        if (num26 != null) {
            Integer num27 = this.noOfZones;
            if (num27 == null || !Intrinsics.areEqual(num27, num26)) {
                this.noOfZones = airconSource.noOfZones;
                if (dataManager != null) {
                    dataManager.add("noOfZones", airconSource.noOfZones);
                }
                z10 = true;
            }
        } else if (z7 && this.noOfZones != null) {
            if (dataManager != null) {
                dataManager.add("noOfZones", null);
            }
            z10 = true;
        }
        Integer num28 = airconSource.rfSysID;
        if (num28 != null) {
            Integer num29 = this.rfSysID;
            if (num29 == null || !Intrinsics.areEqual(num29, num28)) {
                Integer num30 = airconSource.rfSysID;
                this.rfSysID = num30;
                if (dataManager != null) {
                    dataManager.add("rfSysID", num30);
                }
                z10 = true;
            }
        } else if (z7 && this.rfSysID != null) {
            if (dataManager != null) {
                dataManager.add("rfSysID", null);
            }
            z10 = true;
        }
        Float f3 = airconSource.setTemp;
        if (f3 != null) {
            Float f7 = this.setTemp;
            if (f7 == null || !Intrinsics.areEqual(f7, f3)) {
                Float f10 = airconSource.setTemp;
                this.setTemp = f10;
                if (dataManager != null) {
                    dataManager.add("setTemp", f10);
                }
                z10 = true;
            }
        } else if (z7 && this.setTemp != null) {
            if (dataManager != null) {
                dataManager.add("setTemp", null);
            }
            z10 = true;
        }
        SystemState systemState = airconSource.state;
        if (systemState != null) {
            SystemState systemState2 = this.state;
            if (systemState2 == null || systemState2 != systemState) {
                this.state = systemState;
                if (dataManager != null) {
                    dataManager.add("state", systemState);
                }
                z10 = true;
            }
        } else if (z7 && this.state != null) {
            if (dataManager != null) {
                dataManager.add("state", null);
            }
            z10 = true;
        }
        String str8 = airconSource.uid;
        if (str8 != null && ((str = this.uid) == null || !Intrinsics.areEqual(str, str8))) {
            this.uid = airconSource.uid;
            if (dataManager != null) {
                dataManager.add(ActivityMain.UID, airconSource.uid);
            }
            z10 = true;
        }
        Integer num31 = airconSource.unitType;
        if (num31 != null) {
            Integer num32 = this.unitType;
            if (num32 == null || !Intrinsics.areEqual(num32, num31)) {
                this.unitType = airconSource.unitType;
                if (dataManager != null) {
                    dataManager.add("unitType", airconSource.unitType);
                }
                z10 = true;
            }
        } else if (z7 && this.unitType != null) {
            if (dataManager != null) {
                dataManager.add("unitType", null);
            }
            z10 = true;
        }
        Integer num33 = airconSource.cbType;
        if (num33 != null) {
            Integer num34 = this.cbType;
            if (num34 == null || !Intrinsics.areEqual(num34, num33)) {
                this.cbType = airconSource.cbType;
                if (dataManager != null) {
                    dataManager.add("cbType", airconSource.cbType);
                }
                z10 = true;
            }
        } else if (z7 && this.cbType != null) {
            if (dataManager != null) {
                dataManager.add("cbType", null);
            }
            z10 = true;
        }
        Integer num35 = airconSource.myAutoCoolTargetTemp;
        if (num35 != null) {
            Integer num36 = this.myAutoCoolTargetTemp;
            if (num36 == null || !Intrinsics.areEqual(num36, num35)) {
                this.myAutoCoolTargetTemp = airconSource.myAutoCoolTargetTemp;
                if (dataManager != null) {
                    dataManager.add("myAutoCoolTargetTemp", airconSource.myAutoCoolTargetTemp);
                }
                z10 = true;
            }
        } else if (z7 && this.myAutoCoolTargetTemp != null) {
            if (dataManager != null) {
                dataManager.add("myAutoCoolTargetTemp", null);
            }
            z10 = true;
        }
        Integer num37 = airconSource.myAutoHeatTargetTemp;
        if (num37 != null) {
            Integer num38 = this.myAutoHeatTargetTemp;
            if (num38 == null || !Intrinsics.areEqual(num38, num37)) {
                this.myAutoHeatTargetTemp = airconSource.myAutoHeatTargetTemp;
                if (dataManager != null) {
                    dataManager.add("myAutoHeatTargetTemp", airconSource.myAutoHeatTargetTemp);
                }
                z10 = true;
            }
        } else if (z7 && this.myAutoHeatTargetTemp != null) {
            if (dataManager != null) {
                dataManager.add("myAutoHeatTargetTemp", null);
            }
            z10 = true;
        }
        Integer num39 = airconSource.dbFWRevMajor;
        if (num39 != null) {
            Integer num40 = this.dbFWRevMajor;
            if (num40 == null || !Intrinsics.areEqual(num40, num39)) {
                this.dbFWRevMajor = airconSource.dbFWRevMajor;
                if (dataManager != null) {
                    dataManager.add("dbFWRevMajor", airconSource.dbFWRevMajor);
                }
                z10 = true;
            }
        } else if (z7 && this.dbFWRevMajor != null) {
            if (dataManager != null) {
                dataManager.add("dbFWRevMajor", null);
            }
            z10 = true;
        }
        Integer num41 = airconSource.dbFWRevMinor;
        if (num41 != null) {
            Integer num42 = this.dbFWRevMinor;
            if (num42 == null || !Intrinsics.areEqual(num42, num41)) {
                this.dbFWRevMinor = airconSource.dbFWRevMinor;
                if (dataManager != null) {
                    dataManager.add("dbFWRevMinor", airconSource.dbFWRevMinor);
                }
            } else {
                z11 = z10;
            }
        } else if (z7 && this.dbFWRevMinor != null) {
            if (dataManager != null) {
                dataManager.add("dbFWRevMinor", null);
            }
        }
        if (this.uid != null) {
            return z11;
        }
        throw new NullPointerException("Uid null throwing");
    }
}