package com.air.advantage.data;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.s0 */
/* loaded from: classes.dex */
public final class DataSystem {

    @Nullable
    @SerializedName("aaServiceRev")
    @JvmField
    public String aaServiceRev;

    @Nullable
    @SerializedName("allTspErrorCodes")
    @JvmField
    public HashMap<String, String> allTspErrorCodes = new HashMap<>();

    @Nullable
    @SerializedName("backupId")
    @JvmField
    public String backupId;

    @Nullable
    @SerializedName("country")
    @JvmField
    public String country;

    @Nullable
    @SerializedName("dealerPhoneNumber")
    @JvmField
    public String dealerPhoneNumber;

    @Nullable
    @SerializedName("deletedDevices")
    @JvmField
    public HashMap<String, Long> deletedDevices;

    @Nullable
    @SerializedName("deviceIds")
    @JvmField
    public ArrayList<String> deviceIds;

    @Nullable
    @SerializedName("deviceIdsV2")
    @JvmField
    public HashMap<String, String> deviceIdsV2;

    @Nullable
    @SerializedName("deviceNames")
    @JvmField
    public HashMap<String, String> deviceNames;

    @Nullable
    @SerializedName("deviceNotificationVersion")
    @JvmField
    public HashMap<String, String> deviceNotificationVersion;

    @Nullable
    @SerializedName("drawLightsTab")
    @JvmField
    public Boolean drawLightsTab;

    @Nullable
    @SerializedName("drawThingsTab")
    @JvmField
    public Boolean drawThingsTab;

    @Nullable
    @SerializedName("garageDoorReminderWaitTime")
    @JvmField
    public Integer garageDoorReminderWaitTime;

    @Nullable
    @SerializedName("garageDoorSecurityPin")
    @JvmField
    public String garageDoorSecurityPin;

    @Nullable
    @SerializedName("garageDoorSecurityPinEnabled")
    @JvmField
    public Boolean garageDoorSecurityPinEnabled;

    @Nullable
    @SerializedName("hasAircons")
    @JvmField
    public Boolean hasAircons;

    @Nullable
    @SerializedName("hasLights")
    @JvmField
    public Boolean hasLights;

    @Nullable
    @SerializedName("hasLocks")
    @JvmField
    public Boolean hasLocks;

    @Nullable
    @SerializedName("hasSensors")
    @JvmField
    public Boolean hasSensors;

    @Nullable
    @SerializedName("hasThings")
    @JvmField
    public Boolean hasThings;

    @Nullable
    @SerializedName("hasThingsBOG")
    @JvmField
    public Boolean hasThingsBOG;

    @Nullable
    @SerializedName("hasThingsLight")
    @JvmField
    public Boolean hasThingsLight;

    @Nullable
    @SerializedName("isValidSuburbTemp")
    @JvmField
    public Boolean isValidSuburbTemp;

    @Nullable
    @SerializedName("latitude")
    @JvmField
    public Double latitude;

    @Nullable
    @SerializedName("lockDoorReminderWaitTime")
    @JvmField
    public Integer lockDoorReminderWaitTime;

    @Nullable
    @SerializedName("logoPIN")
    @JvmField
    public String logoPIN;

    @Nullable
    @SerializedName("longitude")
    @JvmField
    public Double longitude;

    @Nullable
    @SerializedName("membershipStatus")
    @JvmField
    public String membershipStatus;

    @Nullable
    @SerializedName("mid")
    @JvmField
    public String mid;

    @Nullable
    @SerializedName("myAppRev")
    @JvmField
    public String myAppRev;

    @Nullable
    @SerializedName("myLightsDealerPhoneNumber")
    @JvmField
    public String myLightsDealerPhoneNumber;

    @Nullable
    @SerializedName("myLightsLogoPIN")
    @JvmField
    public String myLightsLogoPIN;

    @Nullable
    @SerializedName("myPlaceDealerPhoneNumber")
    @JvmField
    public String myPlaceDealerPhoneNumber;

    @Nullable
    @SerializedName("myPlaceLogoPIN")
    @JvmField
    public String myPlaceLogoPIN;

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    @JvmField
    public String name;

    @Nullable
    @SerializedName("needsUpdate")
    @JvmField
    public Boolean needsUpdate;

    @Nullable
    @SerializedName("noOfAircons")
    @JvmField
    public Integer noOfAircons;

    @Nullable
    @SerializedName("noOfSnapshots")
    @JvmField
    public Integer noOfSnapshots;

    @Nullable
    @SerializedName("postCode")
    @JvmField
    public String postCode;

    @Nullable
    @SerializedName("remoteAccessPairingEnabled")
    @JvmField
    public Boolean remoteAccessPairingEnabled;

    @Nullable
    @SerializedName("rid")
    @JvmField
    public String rid;

    @Nullable
    @SerializedName("showMeasuredTemp")
    @JvmField
    public Boolean showMeasuredTemp;

    @Nullable
    @SerializedName("splitTypeSystem")
    @JvmField
    public Boolean splitTypeSystem;

    @Nullable
    @SerializedName("suburbTemp")
    @JvmField
    public Double suburbTemp;

    @Nullable
    @SerializedName("sysType")
    @JvmField
    public String sysType;

    @Nullable
    @SerializedName("tspErrorCode")
    @JvmField
    public ErrorCodes tspErrorCode;

    @Nullable
    @SerializedName("tspIp")
    @JvmField
    public String tspIp;

    @Nullable
    @SerializedName("tspModel")
    @JvmField
    public String tspModel;

    @Nullable
    @SerializedName("versions")
    @JvmField
    public HashMap<String, DataModuleInfoSource> versions;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public DataSystem() {
    }

    public static /* synthetic */ boolean update$default(DataSystem dataSystem, DataSystem dataSystem2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 4) != 0) {
            z7 = false;
        }
        return dataSystem.update(dataSystem2, dataManager, z7);
    }

    public final void clear() {
        this.dealerPhoneNumber = null;
        this.hasAircons = null;
        this.hasLights = null;
        this.hasThings = null;
        this.hasThingsBOG = null;
        this.hasSensors = null;
        this.hasThingsLight = null;
        this.drawLightsTab = null;
        this.drawThingsTab = null;
        this.needsUpdate = null;
        this.logoPIN = null;
        this.myLightsDealerPhoneNumber = null;
        this.myLightsLogoPIN = null;
        this.myPlaceDealerPhoneNumber = null;
        this.myPlaceLogoPIN = null;
        this.mid = null;
        this.myAppRev = null;
        this.name = null;
        this.noOfAircons = null;
        this.noOfSnapshots = null;
        this.rid = null;
        this.sysType = null;
        this.deviceIds = null;
        this.postCode = null;
        this.country = null;
        this.latitude = null;
        this.longitude = null;
        this.aaServiceRev = null;
        this.tspModel = null;
        this.showMeasuredTemp = null;
        this.tspIp = null;
        this.tspErrorCode = null;
        this.allTspErrorCodes = new HashMap<>();
        this.deviceIdsV2 = null;
        this.deviceNotificationVersion = null;
        this.deviceNames = null;
        this.deletedDevices = null;
        this.versions = null;
        this.lockDoorReminderWaitTime = null;
        this.garageDoorReminderWaitTime = null;
        this.garageDoorSecurityPin = null;
        this.garageDoorSecurityPinEnabled = null;
        this.remoteAccessPairingEnabled = null;
        this.backupId = null;
        this.suburbTemp = null;
        this.isValidSuburbTemp = null;
    }

    public final void clearDataForBackup() {
        this.aaServiceRev = null;
        this.allTspErrorCodes = new HashMap<>();
        this.deletedDevices = null;
        this.deviceIds = null;
        this.deviceIdsV2 = null;
        this.deviceNotificationVersion = null;
        this.deviceNames = null;
        this.drawLightsTab = null;
        this.drawThingsTab = null;
        this.lockDoorReminderWaitTime = null;
        this.garageDoorReminderWaitTime = null;
        this.garageDoorSecurityPin = null;
        this.garageDoorSecurityPinEnabled = null;
        this.hasAircons = null;
        this.hasLights = null;
        this.hasSensors = null;
        this.hasThings = null;
        this.hasThingsBOG = null;
        this.hasThingsLight = null;
        this.mid = null;
        this.myAppRev = null;
        this.needsUpdate = null;
        this.noOfAircons = null;
        this.noOfSnapshots = null;
        this.remoteAccessPairingEnabled = null;
        this.rid = null;
        this.sysType = null;
        this.tspErrorCode = null;
        this.tspIp = null;
        this.tspModel = null;
        this.versions = null;
        this.suburbTemp = null;
        this.isValidSuburbTemp = null;
    }

    @NotNull
    public final DataSystem copy() {
        DataSystem dataSystem = new DataSystem();
        dataSystem.update(this);
        return dataSystem;
    }

    @NotNull
    public final String generateJSONString() {
        String json = new Gson().toJson(this);
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        return json;
    }

    public final void sanitiseData() {
        this.dealerPhoneNumber = null;
        this.hasAircons = null;
        this.hasLights = null;
        this.hasThings = null;
        this.hasThingsBOG = null;
        this.hasSensors = null;
        this.hasThingsLight = null;
        this.drawLightsTab = null;
        this.drawThingsTab = null;
        this.needsUpdate = null;
        this.logoPIN = null;
        this.mid = null;
        this.myAppRev = null;
        this.myLightsDealerPhoneNumber = null;
        this.myLightsLogoPIN = null;
        this.myPlaceDealerPhoneNumber = null;
        this.myPlaceLogoPIN = null;
        this.name = null;
        this.noOfAircons = null;
        this.noOfSnapshots = null;
        this.rid = null;
        this.sysType = null;
        this.deviceIds = null;
        this.postCode = null;
        this.country = null;
        this.latitude = null;
        this.longitude = null;
        this.aaServiceRev = null;
        this.tspModel = null;
        this.tspIp = null;
        this.tspErrorCode = null;
        this.allTspErrorCodes = null;
        this.deviceIdsV2 = null;
        this.deviceNotificationVersion = null;
        this.deviceNames = null;
        this.deletedDevices = null;
        this.versions = null;
        this.lockDoorReminderWaitTime = null;
        this.garageDoorReminderWaitTime = null;
        this.garageDoorSecurityPin = null;
        this.garageDoorSecurityPinEnabled = null;
        this.remoteAccessPairingEnabled = null;
        this.backupId = null;
        this.suburbTemp = null;
        this.isValidSuburbTemp = null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@NotNull DataSystem dataSystemSource, @Nullable DataManager dataManager) {
        Intrinsics.checkNotNullParameter(dataSystemSource, "dataSystemSource");
        return update$default(this, dataSystemSource, dataManager, false, 4, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    @JvmOverloads
    public final boolean update(@NotNull DataSystem dataSystemSource, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        Intrinsics.checkNotNullParameter(dataSystemSource, "dataSystemSource");
        String str = dataSystemSource.dealerPhoneNumber;
        if (str != null) {
            String str2 = this.dealerPhoneNumber;
            if (str2 == null || !Intrinsics.areEqual(str2, str)) {
                this.dealerPhoneNumber = dataSystemSource.dealerPhoneNumber;
                if (dataManager != null) {
                    dataManager.add("dealerPhoneNumber", dataSystemSource.dealerPhoneNumber);
                    Unit unit = Unit.INSTANCE;
                }
                z10 = true;
            }
            z10 = false;
        } else {
            if (z7 && this.dealerPhoneNumber != null) {
                if (dataManager != null) {
                    dataManager.add("dealerPhoneNumber", null);
                    Unit unit2 = Unit.INSTANCE;
                }
                z10 = true;
            }
            z10 = false;
        }
        Boolean bool = dataSystemSource.hasAircons;
        if (bool != null) {
            Boolean bool2 = this.hasAircons;
            if (bool2 == null || !Intrinsics.areEqual(bool2, bool)) {
                this.hasAircons = dataSystemSource.hasAircons;
                if (dataManager != null) {
                    dataManager.add("hasAircons", dataSystemSource.hasAircons);
                    Unit unit3 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.hasAircons != null) {
            if (dataManager != null) {
                dataManager.add("hasAircons", null);
                Unit unit4 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Boolean bool3 = dataSystemSource.hasLights;
        if (bool3 != null) {
            Boolean bool4 = this.hasLights;
            if (bool4 == null || !Intrinsics.areEqual(bool4, bool3)) {
                this.hasLights = dataSystemSource.hasLights;
                if (dataManager != null) {
                    dataManager.add("hasLights", dataSystemSource.hasLights);
                    Unit unit5 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.hasLights != null) {
            if (dataManager != null) {
                dataManager.add("hasLights", null);
                Unit unit6 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Boolean bool5 = dataSystemSource.hasThings;
        if (bool5 != null) {
            Boolean bool6 = this.hasThings;
            if (bool6 == null || !Intrinsics.areEqual(bool6, bool5)) {
                this.hasThings = dataSystemSource.hasThings;
                if (dataManager != null) {
                    dataManager.add("hasThings", dataSystemSource.hasThings);
                    Unit unit7 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.hasThings != null) {
            if (dataManager != null) {
                dataManager.add("hasThings", null);
                Unit unit8 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Boolean bool7 = dataSystemSource.hasThingsBOG;
        if (bool7 != null) {
            Boolean bool8 = this.hasThingsBOG;
            if (bool8 == null || !Intrinsics.areEqual(bool8, bool7)) {
                this.hasThingsBOG = dataSystemSource.hasThingsBOG;
                if (dataManager != null) {
                    dataManager.add("hasThingsBOG", dataSystemSource.hasThingsBOG);
                    Unit unit9 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.hasThingsBOG != null) {
            if (dataManager != null) {
                dataManager.add("hasThingsBOG", null);
                Unit unit10 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Boolean bool9 = dataSystemSource.hasSensors;
        if (bool9 != null) {
            Boolean bool10 = this.hasSensors;
            if (bool10 == null || !Intrinsics.areEqual(bool10, bool9)) {
                this.hasSensors = dataSystemSource.hasSensors;
                if (dataManager != null) {
                    dataManager.add("hasSensors", dataSystemSource.hasSensors);
                    Unit unit11 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.hasSensors != null) {
            if (dataManager != null) {
                dataManager.add("hasSensors", null);
                Unit unit12 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Boolean bool11 = dataSystemSource.hasLocks;
        if (bool11 != null) {
            Boolean bool12 = this.hasLocks;
            if (bool12 == null || !Intrinsics.areEqual(bool12, bool11)) {
                this.hasLocks = dataSystemSource.hasLocks;
                if (dataManager != null) {
                    dataManager.add("hasLocks", dataSystemSource.hasLocks);
                    Unit unit13 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.hasLocks != null) {
            if (dataManager != null) {
                dataManager.add("hasLocks", null);
                Unit unit14 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Boolean bool13 = dataSystemSource.hasThingsLight;
        if (bool13 != null) {
            Boolean bool14 = this.hasThingsLight;
            if (bool14 == null || !Intrinsics.areEqual(bool14, bool13)) {
                this.hasThingsLight = dataSystemSource.hasThingsLight;
                if (dataManager != null) {
                    dataManager.add("hasThingsLight", dataSystemSource.hasThingsLight);
                    Unit unit15 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.hasThingsLight != null) {
            if (dataManager != null) {
                dataManager.add("hasThingsLight", null);
                Unit unit16 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Boolean bool15 = dataSystemSource.drawLightsTab;
        if (bool15 != null) {
            Boolean bool16 = this.drawLightsTab;
            if (bool16 == null || !Intrinsics.areEqual(bool16, bool15)) {
                this.drawLightsTab = dataSystemSource.drawLightsTab;
                if (dataManager != null) {
                    dataManager.add("drawLightsTab", dataSystemSource.drawLightsTab);
                    Unit unit17 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.drawLightsTab != null) {
            if (dataManager != null) {
                dataManager.add("drawLightsTab", null);
                Unit unit18 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Boolean bool17 = dataSystemSource.drawThingsTab;
        if (bool17 != null) {
            Boolean bool18 = this.drawThingsTab;
            if (bool18 == null || !Intrinsics.areEqual(bool18, bool17)) {
                this.drawThingsTab = dataSystemSource.drawThingsTab;
                if (dataManager != null) {
                    dataManager.add("drawThingsTab", dataSystemSource.drawThingsTab);
                    Unit unit19 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.drawThingsTab != null) {
            if (dataManager != null) {
                dataManager.add("drawThingsTab", null);
                Unit unit20 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str3 = dataSystemSource.logoPIN;
        if (str3 != null) {
            String str4 = this.logoPIN;
            if (str4 == null || !Intrinsics.areEqual(str4, str3)) {
                this.logoPIN = dataSystemSource.logoPIN;
                if (dataManager != null) {
                    dataManager.add("logoPIN", dataSystemSource.logoPIN);
                    Unit unit21 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.logoPIN != null) {
            if (dataManager != null) {
                dataManager.add("logoPIN", null);
                Unit unit22 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str5 = dataSystemSource.mid;
        if (str5 != null) {
            String str6 = this.mid;
            if (str6 == null || !Intrinsics.areEqual(str6, str5)) {
                this.mid = dataSystemSource.mid;
                if (dataManager != null) {
                    dataManager.add("mid", dataSystemSource.mid);
                    Unit unit23 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.mid != null) {
            if (dataManager != null) {
                dataManager.add("mid", null);
                Unit unit24 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str7 = dataSystemSource.myAppRev;
        if (str7 != null) {
            String str8 = this.myAppRev;
            if (str8 == null || !Intrinsics.areEqual(str8, str7)) {
                this.myAppRev = dataSystemSource.myAppRev;
                if (dataManager != null) {
                    dataManager.add("myAppRev", dataSystemSource.myAppRev);
                    Unit unit25 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.myAppRev != null) {
            if (dataManager != null) {
                dataManager.add("myAppRev", null);
                Unit unit26 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str9 = dataSystemSource.myLightsDealerPhoneNumber;
        if (str9 != null) {
            String str10 = this.myLightsDealerPhoneNumber;
            if (str10 == null || !Intrinsics.areEqual(str10, str9)) {
                this.myLightsDealerPhoneNumber = dataSystemSource.myLightsDealerPhoneNumber;
                if (dataManager != null) {
                    dataManager.add("myLightsDealerPhoneNumber", dataSystemSource.myLightsDealerPhoneNumber);
                    Unit unit27 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.myLightsDealerPhoneNumber != null) {
            if (dataManager != null) {
                dataManager.add("myLightsDealerPhoneNumber", null);
                Unit unit28 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str11 = dataSystemSource.myLightsLogoPIN;
        if (str11 != null) {
            String str12 = this.myLightsLogoPIN;
            if (str12 == null || !Intrinsics.areEqual(str12, str11)) {
                this.myLightsLogoPIN = dataSystemSource.myLightsLogoPIN;
                if (dataManager != null) {
                    dataManager.add("myLightsLogoPIN", dataSystemSource.myLightsLogoPIN);
                    Unit unit29 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.myLightsLogoPIN != null) {
            if (dataManager != null) {
                dataManager.add("myLightsLogoPIN", null);
                Unit unit30 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str13 = dataSystemSource.myPlaceDealerPhoneNumber;
        if (str13 != null) {
            String str14 = this.myPlaceDealerPhoneNumber;
            if (str14 == null || !Intrinsics.areEqual(str14, str13)) {
                this.myPlaceDealerPhoneNumber = dataSystemSource.myPlaceDealerPhoneNumber;
                if (dataManager != null) {
                    dataManager.add("myPlaceDealerPhoneNumber", dataSystemSource.myPlaceDealerPhoneNumber);
                    Unit unit31 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.myPlaceDealerPhoneNumber != null) {
            if (dataManager != null) {
                dataManager.add("myPlaceDealerPhoneNumber", null);
                Unit unit32 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str15 = dataSystemSource.myPlaceLogoPIN;
        if (str15 != null) {
            String str16 = this.myPlaceLogoPIN;
            if (str16 == null || !Intrinsics.areEqual(str16, str15)) {
                this.myPlaceLogoPIN = dataSystemSource.myPlaceLogoPIN;
                if (dataManager != null) {
                    dataManager.add("myPlaceLogoPIN", dataSystemSource.myPlaceLogoPIN);
                    Unit unit33 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.myPlaceLogoPIN != null) {
            if (dataManager != null) {
                dataManager.add("myPlaceLogoPIN", null);
                Unit unit34 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str17 = dataSystemSource.name;
        if (str17 != null) {
            String str18 = this.name;
            if (str18 == null || !Intrinsics.areEqual(str18, str17)) {
                this.name = dataSystemSource.name;
                if (dataManager != null) {
                    dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, dataSystemSource.name);
                    Unit unit35 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.name != null) {
            if (dataManager != null) {
                dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, null);
                Unit unit36 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Boolean bool19 = dataSystemSource.needsUpdate;
        if (bool19 != null) {
            Boolean bool20 = this.needsUpdate;
            if (bool20 == null || !Intrinsics.areEqual(bool20, bool19)) {
                this.needsUpdate = dataSystemSource.needsUpdate;
                if (dataManager != null) {
                    dataManager.add("needsUpdate", dataSystemSource.needsUpdate);
                    Unit unit37 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.needsUpdate != null) {
            if (dataManager != null) {
                dataManager.add("needsUpdate", null);
                Unit unit38 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Integer num = dataSystemSource.noOfAircons;
        if (num != null) {
            Integer num2 = this.noOfAircons;
            if (num2 == null || !Intrinsics.areEqual(num2, num)) {
                this.noOfAircons = dataSystemSource.noOfAircons;
                if (dataManager != null) {
                    dataManager.add("noOfAircons", dataSystemSource.noOfAircons);
                    Unit unit39 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.noOfAircons != null) {
            if (dataManager != null) {
                dataManager.add("noOfAircons", null);
                Unit unit40 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Integer num3 = dataSystemSource.noOfSnapshots;
        if (num3 != null) {
            Integer num4 = this.noOfSnapshots;
            if (num4 == null || !Intrinsics.areEqual(num4, num3)) {
                this.noOfSnapshots = dataSystemSource.noOfSnapshots;
                if (dataManager != null) {
                    dataManager.add("noOfSnapshots", dataSystemSource.noOfSnapshots);
                    Unit unit41 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.noOfSnapshots != null) {
            if (dataManager != null) {
                dataManager.add("noOfSnapshots", null);
                Unit unit42 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str19 = dataSystemSource.rid;
        if (str19 != null) {
            String str20 = this.rid;
            if (str20 == null || !Intrinsics.areEqual(str20, str19)) {
                this.rid = dataSystemSource.rid;
                if (dataManager != null) {
                    dataManager.add("rid", dataSystemSource.rid);
                    Unit unit43 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.rid != null) {
            if (dataManager != null) {
                dataManager.add("rid", null);
                Unit unit44 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str21 = dataSystemSource.sysType;
        if (str21 != null) {
            String str22 = this.sysType;
            if (str22 == null || !Intrinsics.areEqual(str22, str21)) {
                this.sysType = dataSystemSource.sysType;
                if (dataManager != null) {
                    dataManager.add("sysType", dataSystemSource.sysType);
                    Unit unit45 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.sysType != null) {
            if (dataManager != null) {
                dataManager.add("sysType", null);
                Unit unit46 = Unit.INSTANCE;
            }
            z10 = true;
        }
        if (dataSystemSource.deviceIds != null) {
            if (this.deviceIds == null) {
                this.deviceIds = new ArrayList<>();
            }
            if (!Intrinsics.areEqual(this.deviceIds, dataSystemSource.deviceIds)) {
                ArrayList<String> arrayList = this.deviceIds;
                Intrinsics.checkNotNull(arrayList);
                arrayList.clear();
                ArrayList<String> arrayList2 = dataSystemSource.deviceIds;
                Intrinsics.checkNotNull(arrayList2);
                Iterator<String> it = arrayList2.iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    ArrayList<String> arrayList3 = this.deviceIds;
                    Intrinsics.checkNotNull(arrayList3);
                    if (!arrayList3.contains(next)) {
                        ArrayList<String> arrayList4 = this.deviceIds;
                        Intrinsics.checkNotNull(arrayList4);
                        arrayList4.add(next);
                    }
                }
                if (dataManager != null) {
                    dataManager.add("deviceIds", dataSystemSource.deviceIds);
                    Unit unit47 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.deviceIds != null) {
            if (dataManager != null) {
                dataManager.add("deviceIds", null);
                Unit unit48 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str23 = dataSystemSource.postCode;
        if (str23 != null) {
            String str24 = this.postCode;
            if (str24 == null || !Intrinsics.areEqual(str24, str23)) {
                this.postCode = dataSystemSource.postCode;
                if (dataManager != null) {
                    dataManager.add("postCode", dataSystemSource.postCode);
                    Unit unit49 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.postCode != null) {
            if (dataManager != null) {
                dataManager.add("postCode", null);
                Unit unit50 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str25 = dataSystemSource.country;
        if (str25 != null) {
            String str26 = this.country;
            if (str26 == null || !Intrinsics.areEqual(str26, str25)) {
                this.country = dataSystemSource.country;
                if (dataManager != null) {
                    dataManager.add("country", dataSystemSource.country);
                    Unit unit51 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.country != null) {
            if (dataManager != null) {
                dataManager.add("country", null);
                Unit unit52 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Double d3 = dataSystemSource.latitude;
        if (d3 != null) {
            Double d10 = this.latitude;
            if (d10 == null || !Intrinsics.areEqual(d10, d3)) {
                this.latitude = dataSystemSource.latitude;
                if (dataManager != null) {
                    dataManager.add("latitude", dataSystemSource.latitude);
                    Unit unit53 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.latitude != null) {
            if (dataManager != null) {
                dataManager.add("latitude", null);
                Unit unit54 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Double d11 = dataSystemSource.longitude;
        if (d11 != null) {
            Double d12 = this.longitude;
            if (d12 == null || !Intrinsics.areEqual(d12, d11)) {
                this.longitude = dataSystemSource.longitude;
                if (dataManager != null) {
                    dataManager.add("longitude", dataSystemSource.longitude);
                    Unit unit55 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.longitude != null) {
            if (dataManager != null) {
                dataManager.add("longitude", null);
                Unit unit56 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str27 = dataSystemSource.aaServiceRev;
        if (str27 != null) {
            String str28 = this.aaServiceRev;
            if (str28 == null || !Intrinsics.areEqual(str28, str27)) {
                this.aaServiceRev = dataSystemSource.aaServiceRev;
                if (dataManager != null) {
                    dataManager.add("aaServiceRev", dataSystemSource.aaServiceRev);
                    Unit unit57 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.aaServiceRev != null) {
            if (dataManager != null) {
                dataManager.add("aaServiceRev", null);
                Unit unit58 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str29 = dataSystemSource.tspModel;
        if (str29 != null) {
            String str30 = this.tspModel;
            if (str30 == null || !Intrinsics.areEqual(str30, str29)) {
                this.tspModel = dataSystemSource.tspModel;
                if (dataManager != null) {
                    dataManager.add("tspModel", dataSystemSource.tspModel);
                    Unit unit59 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.tspModel != null) {
            if (dataManager != null) {
                dataManager.add("tspModel", null);
                Unit unit60 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Boolean bool21 = dataSystemSource.showMeasuredTemp;
        if (bool21 != null) {
            Boolean bool22 = this.showMeasuredTemp;
            if (bool22 == null || !Intrinsics.areEqual(bool22, bool21)) {
                this.showMeasuredTemp = dataSystemSource.showMeasuredTemp;
                if (dataManager != null) {
                    dataManager.add("showMeasuredTemp", dataSystemSource.showMeasuredTemp);
                    Unit unit61 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.showMeasuredTemp != null) {
            if (dataManager != null) {
                dataManager.add("showMeasuredTemp", null);
                Unit unit62 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str31 = dataSystemSource.tspIp;
        if (str31 != null) {
            String str32 = this.tspIp;
            if (str32 == null || !Intrinsics.areEqual(str32, str31)) {
                this.tspIp = dataSystemSource.tspIp;
                if (dataManager != null) {
                    dataManager.add("tspIp", dataSystemSource.tspIp);
                    Unit unit63 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.tspIp != null) {
            if (dataManager != null) {
                dataManager.add("tspIp", null);
                Unit unit64 = Unit.INSTANCE;
            }
            z10 = true;
        }
        ErrorCodes errorCodes = dataSystemSource.tspErrorCode;
        if (errorCodes != null) {
            ErrorCodes errorCodes2 = this.tspErrorCode;
            if (errorCodes2 == null || errorCodes2 != errorCodes) {
                this.tspErrorCode = errorCodes;
                if (dataManager != null) {
                    dataManager.add("tspErrorCode", dataSystemSource.tspErrorCode);
                    Unit unit65 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.tspErrorCode != null) {
            if (dataManager != null) {
                dataManager.add("tspErrorCode", null);
                Unit unit66 = Unit.INSTANCE;
            }
            z10 = true;
        }
        if (dataSystemSource.allTspErrorCodes != null) {
            if (this.allTspErrorCodes == null) {
                this.allTspErrorCodes = new HashMap<>();
            }
            if (!Intrinsics.areEqual(this.allTspErrorCodes, dataSystemSource.allTspErrorCodes)) {
                HashMap<String, String> hashMap = this.allTspErrorCodes;
                Intrinsics.checkNotNull(hashMap);
                hashMap.clear();
                HashMap<String, String> hashMap2 = dataSystemSource.allTspErrorCodes;
                Intrinsics.checkNotNull(hashMap2);
                for (String str33 : hashMap2.keySet()) {
                    HashMap<String, String> hashMap3 = this.allTspErrorCodes;
                    Intrinsics.checkNotNull(hashMap3);
                    Intrinsics.checkNotNull(str33);
                    HashMap<String, String> hashMap4 = dataSystemSource.allTspErrorCodes;
                    Intrinsics.checkNotNull(hashMap4);
                    hashMap3.put(str33, hashMap4.get(str33));
                }
                if (dataManager != null) {
                    dataManager.add("allTspErrorCodes", dataSystemSource.allTspErrorCodes);
                    Unit unit67 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.allTspErrorCodes != null) {
            if (dataManager != null) {
                dataManager.add("allTspErrorCodes", null);
                Unit unit68 = Unit.INSTANCE;
            }
            z10 = true;
        }
        if (dataSystemSource.deviceIdsV2 != null) {
            if (this.deviceIdsV2 == null) {
                this.deviceIdsV2 = new HashMap<>();
            }
            if (!Intrinsics.areEqual(this.deviceIdsV2, dataSystemSource.deviceIdsV2)) {
                HashMap<String, String> hashMap5 = this.deviceIdsV2;
                Intrinsics.checkNotNull(hashMap5);
                hashMap5.clear();
                HashMap<String, String> hashMap6 = dataSystemSource.deviceIdsV2;
                Intrinsics.checkNotNull(hashMap6);
                for (String str34 : hashMap6.keySet()) {
                    HashMap<String, String> hashMap7 = this.deviceIdsV2;
                    Intrinsics.checkNotNull(hashMap7);
                    Intrinsics.checkNotNull(str34);
                    HashMap<String, String> hashMap8 = dataSystemSource.deviceIdsV2;
                    Intrinsics.checkNotNull(hashMap8);
                    hashMap7.put(str34, hashMap8.get(str34));
                }
                if (dataManager != null) {
                    dataManager.add("deviceIdsV2", dataSystemSource.deviceIdsV2);
                    Unit unit69 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.deviceIdsV2 != null) {
            if (dataManager != null) {
                dataManager.add("deviceIdsV2", null);
                Unit unit70 = Unit.INSTANCE;
            }
            z10 = true;
        }
        if (dataSystemSource.deviceNotificationVersion != null) {
            if (this.deviceNotificationVersion == null) {
                this.deviceNotificationVersion = new HashMap<>();
            }
            if (!Intrinsics.areEqual(this.deviceNotificationVersion, dataSystemSource.deviceNotificationVersion)) {
                HashMap<String, String> hashMap9 = this.deviceNotificationVersion;
                Intrinsics.checkNotNull(hashMap9);
                hashMap9.clear();
                HashMap<String, String> hashMap10 = dataSystemSource.deviceNotificationVersion;
                Intrinsics.checkNotNull(hashMap10);
                for (String str35 : hashMap10.keySet()) {
                    HashMap<String, String> hashMap11 = this.deviceNotificationVersion;
                    Intrinsics.checkNotNull(hashMap11);
                    Intrinsics.checkNotNull(str35);
                    HashMap<String, String> hashMap12 = dataSystemSource.deviceNotificationVersion;
                    Intrinsics.checkNotNull(hashMap12);
                    hashMap11.put(str35, hashMap12.get(str35));
                }
                if (dataManager != null) {
                    dataManager.add("deviceNotificationVersion", dataSystemSource.deviceNotificationVersion);
                    Unit unit71 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.deviceNotificationVersion != null) {
            if (dataManager != null) {
                dataManager.add("deviceNotificationVersion", null);
                Unit unit72 = Unit.INSTANCE;
            }
            z10 = true;
        }
        if (dataSystemSource.deviceNames != null) {
            if (this.deviceNames == null) {
                this.deviceNames = new HashMap<>();
            }
            if (!Intrinsics.areEqual(this.deviceNames, dataSystemSource.deviceNames)) {
                HashMap<String, String> hashMap13 = this.deviceNames;
                Intrinsics.checkNotNull(hashMap13);
                hashMap13.clear();
                HashMap<String, String> hashMap14 = dataSystemSource.deviceNames;
                Intrinsics.checkNotNull(hashMap14);
                for (String str36 : hashMap14.keySet()) {
                    HashMap<String, String> hashMap15 = this.deviceNames;
                    Intrinsics.checkNotNull(hashMap15);
                    Intrinsics.checkNotNull(str36);
                    HashMap<String, String> hashMap16 = dataSystemSource.deviceNames;
                    Intrinsics.checkNotNull(hashMap16);
                    hashMap15.put(str36, hashMap16.get(str36));
                }
                if (dataManager != null) {
                    dataManager.add("deviceNames", dataSystemSource.deviceNames);
                    Unit unit73 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.deviceNames != null) {
            if (dataManager != null) {
                dataManager.add("deviceNames", null);
                Unit unit74 = Unit.INSTANCE;
            }
            z10 = true;
        }
        if (dataSystemSource.deletedDevices != null) {
            if (this.deletedDevices == null) {
                this.deletedDevices = new HashMap<>();
            }
            if (!Intrinsics.areEqual(this.deletedDevices, dataSystemSource.deletedDevices)) {
                HashMap<String, Long> hashMap17 = this.deletedDevices;
                Intrinsics.checkNotNull(hashMap17);
                hashMap17.clear();
                HashMap<String, Long> hashMap18 = dataSystemSource.deletedDevices;
                Intrinsics.checkNotNull(hashMap18);
                for (String str37 : hashMap18.keySet()) {
                    HashMap<String, Long> hashMap19 = this.deletedDevices;
                    Intrinsics.checkNotNull(hashMap19);
                    Intrinsics.checkNotNull(str37);
                    HashMap<String, Long> hashMap20 = dataSystemSource.deletedDevices;
                    Intrinsics.checkNotNull(hashMap20);
                    hashMap19.put(str37, hashMap20.get(str37));
                }
                if (dataManager != null) {
                    dataManager.add("deletedDevices", dataSystemSource.deletedDevices);
                    Unit unit75 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.deletedDevices != null) {
            if (dataManager != null) {
                dataManager.add("deletedDevices", null);
                Unit unit76 = Unit.INSTANCE;
            }
            z10 = true;
        }
        if (dataSystemSource.versions != null) {
            if (this.versions == null) {
                this.versions = new HashMap<>();
            }
            if (!Intrinsics.areEqual(this.versions, dataSystemSource.versions)) {
                HashMap<String, DataModuleInfoSource> hashMap21 = this.versions;
                Intrinsics.checkNotNull(hashMap21);
                hashMap21.clear();
                HashMap<String, DataModuleInfoSource> hashMap22 = dataSystemSource.versions;
                Intrinsics.checkNotNull(hashMap22);
                for (String str38 : hashMap22.keySet()) {
                    HashMap<String, DataModuleInfoSource> hashMap23 = this.versions;
                    Intrinsics.checkNotNull(hashMap23);
                    Intrinsics.checkNotNull(str38);
                    HashMap<String, DataModuleInfoSource> hashMap24 = dataSystemSource.versions;
                    Intrinsics.checkNotNull(hashMap24);
                    hashMap23.put(str38, hashMap24.get(str38));
                }
                if (dataManager != null) {
                    dataManager.add("versions", dataSystemSource.versions);
                    Unit unit77 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.versions != null) {
            if (dataManager != null) {
                dataManager.add("versions", null);
                Unit unit78 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Integer num5 = dataSystemSource.lockDoorReminderWaitTime;
        if (num5 != null) {
            Integer num6 = this.lockDoorReminderWaitTime;
            if (num6 == null || !Intrinsics.areEqual(num6, num5)) {
                this.lockDoorReminderWaitTime = dataSystemSource.lockDoorReminderWaitTime;
                if (dataManager != null) {
                    dataManager.add("lockDoorReminderWaitTime", dataSystemSource.lockDoorReminderWaitTime);
                    Unit unit79 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.lockDoorReminderWaitTime != null) {
            if (dataManager != null) {
                dataManager.add("lockDoorReminderWaitTime", null);
                Unit unit80 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Integer num7 = dataSystemSource.garageDoorReminderWaitTime;
        if (num7 != null) {
            Integer num8 = this.garageDoorReminderWaitTime;
            if (num8 == null || !Intrinsics.areEqual(num8, num7)) {
                this.garageDoorReminderWaitTime = dataSystemSource.garageDoorReminderWaitTime;
                if (dataManager != null) {
                    dataManager.add("garageDoorReminderWaitTime", dataSystemSource.garageDoorReminderWaitTime);
                    Unit unit81 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.garageDoorReminderWaitTime != null) {
            if (dataManager != null) {
                dataManager.add("garageDoorReminderWaitTime", null);
                Unit unit82 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str39 = dataSystemSource.garageDoorSecurityPin;
        if (str39 != null) {
            String str40 = this.garageDoorSecurityPin;
            if (str40 == null || !Intrinsics.areEqual(str40, str39)) {
                this.garageDoorSecurityPin = dataSystemSource.garageDoorSecurityPin;
                if (dataManager != null) {
                    dataManager.add("garageDoorSecurityPin", dataSystemSource.garageDoorSecurityPin);
                    Unit unit83 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.garageDoorSecurityPin != null) {
            if (dataManager != null) {
                dataManager.add("garageDoorSecurityPin", null);
                Unit unit84 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Boolean bool23 = dataSystemSource.garageDoorSecurityPinEnabled;
        if (bool23 != null) {
            Boolean bool24 = this.garageDoorSecurityPinEnabled;
            if (bool24 == null || !Intrinsics.areEqual(bool24, bool23)) {
                this.garageDoorSecurityPinEnabled = dataSystemSource.garageDoorSecurityPinEnabled;
                if (dataManager != null) {
                    dataManager.add("garageDoorSecurityPinEnabled", dataSystemSource.garageDoorSecurityPinEnabled);
                    Unit unit85 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.garageDoorSecurityPinEnabled != null) {
            if (dataManager != null) {
                dataManager.add("garageDoorSecurityPinEnabled", null);
                Unit unit86 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Boolean bool25 = dataSystemSource.remoteAccessPairingEnabled;
        if (bool25 != null) {
            Boolean bool26 = this.remoteAccessPairingEnabled;
            if (bool26 == null || !Intrinsics.areEqual(bool26, bool25)) {
                this.remoteAccessPairingEnabled = dataSystemSource.remoteAccessPairingEnabled;
                if (dataManager != null) {
                    dataManager.add("remoteAccessPairingEnabled", dataSystemSource.remoteAccessPairingEnabled);
                    Unit unit87 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.remoteAccessPairingEnabled != null) {
            if (dataManager != null) {
                dataManager.add("remoteAccessPairingEnabled", null);
                Unit unit88 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str41 = dataSystemSource.backupId;
        if (str41 != null) {
            String str42 = this.backupId;
            if (str42 == null || !Intrinsics.areEqual(str42, str41)) {
                this.backupId = dataSystemSource.backupId;
                if (dataManager != null) {
                    dataManager.add("backupId", dataSystemSource.backupId);
                    Unit unit89 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.backupId != null) {
            if (dataManager != null) {
                dataManager.add("backupId", null);
                Unit unit90 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Boolean bool27 = dataSystemSource.splitTypeSystem;
        if (bool27 != null) {
            Boolean bool28 = this.splitTypeSystem;
            if (bool28 == null || !Intrinsics.areEqual(bool28, bool27)) {
                this.splitTypeSystem = dataSystemSource.splitTypeSystem;
                if (dataManager != null) {
                    dataManager.add("splitTypeSystem", dataSystemSource.splitTypeSystem);
                    Unit unit91 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.splitTypeSystem != null) {
            if (dataManager != null) {
                dataManager.add("splitTypeSystem", null);
                Unit unit92 = Unit.INSTANCE;
            }
            z10 = true;
        }
        String str43 = dataSystemSource.membershipStatus;
        if (str43 != null) {
            String str44 = this.membershipStatus;
            if (str44 == null || !Intrinsics.areEqual(str44, str43)) {
                this.membershipStatus = dataSystemSource.membershipStatus;
                if (dataManager != null) {
                    dataManager.add("membershipStatus", dataSystemSource.membershipStatus);
                    Unit unit93 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.membershipStatus != null) {
            if (dataManager != null) {
                dataManager.add("membershipStatus", null);
                Unit unit94 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Double d13 = dataSystemSource.suburbTemp;
        if (d13 != null) {
            Double d14 = this.suburbTemp;
            if (d14 == null || !Intrinsics.areEqual(d14, d13)) {
                this.suburbTemp = dataSystemSource.suburbTemp;
                if (dataManager != null) {
                    dataManager.add("suburbTemp", dataSystemSource.suburbTemp);
                    Unit unit95 = Unit.INSTANCE;
                }
                z10 = true;
            }
        } else if (z7 && this.suburbTemp != null) {
            if (dataManager != null) {
                dataManager.add("suburbTemp", null);
                Unit unit96 = Unit.INSTANCE;
            }
            z10 = true;
        }
        Boolean bool29 = dataSystemSource.isValidSuburbTemp;
        if (bool29 != null) {
            Boolean bool30 = this.isValidSuburbTemp;
            if (bool30 == null || !Intrinsics.areEqual(bool30, bool29)) {
                this.isValidSuburbTemp = dataSystemSource.isValidSuburbTemp;
                if (dataManager == null) {
                    return true;
                }
                dataManager.add("isValidSuburbTemp", dataSystemSource.isValidSuburbTemp);
                Unit unit97 = Unit.INSTANCE;
                return true;
            }
        } else if (z7 && this.isValidSuburbTemp != null) {
            if (dataManager == null) {
                return true;
            }
            dataManager.add("isValidSuburbTemp", null);
            Unit unit98 = Unit.INSTANCE;
            return true;
        }
        return z10;
    }

    public DataSystem(@Nullable String str) {
        this.name = str;
    }

    public final void update(@NotNull DataSystem system) {
        Intrinsics.checkNotNullParameter(system, "system");
        update$default(this, system, null, false, 4, null);
    }
}