package com.air.advantage.aaservice.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.annotations.SerializedName;

/* compiled from: DataSystem.java */
/* renamed from: com.air.advantage.aaservice.o.k */
/* loaded from: classes.dex */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataSystem {

    @SerializedName("dealerPhoneNumber")
    @JsonProperty("dealerPhoneNumber")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String dealerPhoneNumber;

    @SerializedName("hasAircons")
    @JsonProperty("hasAircons")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean hasAircons;

    @SerializedName("hasLights")
    @JsonProperty("hasLights")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean hasLights;

    @SerializedName("needsUpdate")
    @JsonProperty("needsUpdate")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean needsUpdate;

    @SerializedName("logoPIN")
    @JsonProperty("logoPIN")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String logoPIN;

    @SerializedName("mid")
    @JsonProperty("mid")
    @JsonView({JsonExporterViews.Export.class})
    public String mid;

    @SerializedName("myAppRev")
    @JsonProperty("myAppRev")
    @JsonView({JsonExporterViews.Export.class})
    public String myAppRev;

    @SerializedName("name")
    @JsonProperty("name")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String name;

    @SerializedName("noOfAircons")
    @JsonProperty("noOfAircons")
    @JsonView({JsonExporterViews.Export.class})
    public Integer noOfAircons;

    @SerializedName("noOfSnapshots")
    @JsonProperty("noOfSnapshots")
    @JsonView({JsonExporterViews.Export.class})
    public Integer noOfSnapshots;

    @SerializedName("rid")
    @JsonProperty("rid")
    @JsonView({JsonExporterViews.Export.class})
    public String rid;

    @SerializedName("sysType")
    @JsonProperty("sysType")
    @JsonView({JsonExporterViews.Export.class})
    public String sysType;

    @SerializedName("deviceIds")
    @JsonProperty("deviceIds")
    @JsonView({JsonExporterViews.Export.class})
    public ArrayList<String> deviceIds;

    @SerializedName("postCode")
    @JsonProperty("postCode")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String postCode;

    @SerializedName("country")
    @JsonProperty("country")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String country;

    @SerializedName("latitude")
    @JsonProperty("latitude")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Double latitude;

    @SerializedName("longitude")
    @JsonProperty("longitude")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Double longitude;

    @SerializedName("aaServiceRev")
    @JsonProperty("aaServiceRev")
    @JsonView({JsonExporterViews.Export.class})
    public String aaServiceRev;
    
    @SerializedName("allTspErrorCodes")
    @JsonProperty("allTspErrorCodes")
    @JsonView({JsonExporterViews.Export.class})
    public HashMap<String, String> allTspErrorCodes;

    @SerializedName("tspErrorCode")
    @JsonProperty("tspErrorCode")
    @JsonView({JsonExporterViews.Export.class})
    public String tspErrorCode;
    
    @SerializedName("backupId")
    @JsonProperty("backupId")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String backupId;
    
    @SerializedName("deletedDevices")
    @JsonProperty("deletedDevices")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public HashMap<String, Long> deletedDevices;
    
    @SerializedName("deviceIdsV2")
    @JsonProperty("deviceIdsV2")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public HashMap<String, String> deviceIdsV2;
    
    @SerializedName("deviceNames")
    @JsonProperty("deviceNames")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public HashMap<String, String> deviceNames;
    
    @SerializedName("deviceNotificationVersion")
    @JsonProperty("deviceNotificationVersion")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public HashMap<String, String> deviceNotificationVersion;
    
    @SerializedName("drawLightsTab")
    @JsonProperty("drawLightsTab")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean drawLightsTab;
    
    @SerializedName("drawThingsTab")
    @JsonProperty("drawThingsTab")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean drawThingsTab;
    
    @SerializedName("garageDoorReminderWaitTime")
    @JsonProperty("garageDoorReminderWaitTime")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer garageDoorReminderWaitTime;
    
    @SerializedName("garageDoorSecurityPin")
    @JsonProperty("garageDoorSecurityPin")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String garageDoorSecurityPin;
    
    @SerializedName("garageDoorSecurityPinEnabled")
    @JsonProperty("garageDoorSecurityPinEnabled")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean garageDoorSecurityPinEnabled;
    
    @SerializedName("hasLocks")
    @JsonProperty("hasLocks")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean hasLocks;
    
    @SerializedName("hasSensors")
    @JsonProperty("hasSensors")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean hasSensors;
    
    @SerializedName("hasThings")
    @JsonProperty("hasThings")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean hasThings;
    
    @SerializedName("hasThingsBOG")
    @JsonProperty("hasThingsBOG")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean hasThingsBOG;
    
    @SerializedName("hasThingsLight")
    @JsonProperty("hasThingsLight")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean hasThingsLight;
    
    @SerializedName("isValidSuburbTemp")
    @JsonProperty("isValidSuburbTemp")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean isValidSuburbTemp;
    
    @SerializedName("lockDoorReminderWaitTime")
    @JsonProperty("lockDoorReminderWaitTime")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer lockDoorReminderWaitTime;
    
    @SerializedName("membershipStatus")
    @JsonProperty("membershipStatus")
    @JsonView({JsonExporterViews.Export.class})
    public String membershipStatus;
    
    @SerializedName("myLightsDealerPhoneNumber")
    @JsonProperty("myLightsDealerPhoneNumber")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String myLightsDealerPhoneNumber;
    
    @SerializedName("myLightsLogoPIN")
    @JsonProperty("myLightsLogoPIN")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String myLightsLogoPIN;
    
    @SerializedName("myPlaceDealerPhoneNumber")
    @JsonProperty("myPlaceDealerPhoneNumber")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String myPlaceDealerPhoneNumber;
    
    @SerializedName("myPlaceLogoPIN")
    @JsonProperty("myPlaceLogoPIN")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String myPlaceLogoPIN;
    
    @SerializedName("remoteAccessPairingEnabled")
    @JsonProperty("remoteAccessPairingEnabled")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean remoteAccessPairingEnabled;
    
    @SerializedName("showMeasuredTemp")
    @JsonProperty("showMeasuredTemp")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean showMeasuredTemp;
    
    @SerializedName("splitTypeSystem")
    @JsonProperty("splitTypeSystem")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean splitTypeSystem;
    
    @SerializedName("suburbTemp")
    @JsonProperty("suburbTemp")
    @JsonView({JsonExporterViews.Export.class})
    public Double suburbTemp;
    
    @SerializedName("tspIp")
    @JsonProperty("tspIp")
    @JsonView({JsonExporterViews.Export.class})
    public String tspIp;
    
    @SerializedName("tspModel")
    @JsonProperty("tspModel")
    @JsonView({JsonExporterViews.Export.class})
    public String tspModel;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    DataSystem() {
        this.allTspErrorCodes = new HashMap<>();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean updateSystem(DataSystem dataSystem) {
        boolean changed = false;

        if (dataSystem.dealerPhoneNumber != null && !dataSystem.dealerPhoneNumber.equals(this.dealerPhoneNumber)) {
            this.dealerPhoneNumber = dataSystem.dealerPhoneNumber;
            changed = true;
        }
        if (dataSystem.hasAircons != null && !dataSystem.hasAircons.equals(this.hasAircons)) {
            this.hasAircons = dataSystem.hasAircons;
            changed = true;
        }
        if (dataSystem.hasLights != null && !dataSystem.hasLights.equals(this.hasLights)) {
            this.hasLights = dataSystem.hasLights;
            changed = true;
        }
        if (dataSystem.logoPIN != null && !dataSystem.logoPIN.equals(this.logoPIN)) {
            this.logoPIN = dataSystem.logoPIN;
            changed = true;
        }
        if (dataSystem.mid != null && !dataSystem.mid.equals(this.mid)) {
            this.mid = dataSystem.mid;
            changed = true;
        }
        if (dataSystem.myAppRev != null && !dataSystem.myAppRev.equals(this.myAppRev)) {
            this.myAppRev = dataSystem.myAppRev;
            changed = true;
        }
        if (dataSystem.name != null && !dataSystem.name.equals(this.name)) {
            this.name = dataSystem.name;
            changed = true;
        }
        if (dataSystem.needsUpdate != null && !dataSystem.needsUpdate.equals(this.needsUpdate)) {
            this.needsUpdate = dataSystem.needsUpdate;
            changed = true;
        }
        if (dataSystem.noOfAircons != null && !dataSystem.noOfAircons.equals(this.noOfAircons)) {
            this.noOfAircons = dataSystem.noOfAircons;
            changed = true;
        }
        if (dataSystem.noOfSnapshots != null && !dataSystem.noOfSnapshots.equals(this.noOfSnapshots)) {
            this.noOfSnapshots = dataSystem.noOfSnapshots;
            changed = true;
        }
        if (dataSystem.rid != null && !dataSystem.rid.equals(this.rid)) {
            this.rid = dataSystem.rid;
            changed = true;
        }
        if (dataSystem.sysType != null && !dataSystem.sysType.equals(this.sysType)) {
            this.sysType = dataSystem.sysType;
            changed = true;
        }
        if (dataSystem.deviceIds != null) {
            if (this.deviceIds == null || !this.deviceIds.equals(dataSystem.deviceIds)) {
                this.deviceIds = new ArrayList<>(dataSystem.deviceIds);
                changed = true;
            }
        }
        if (dataSystem.postCode != null && !dataSystem.postCode.equals(this.postCode)) {
            this.postCode = dataSystem.postCode;
            changed = true;
        }
        if (dataSystem.country != null && !dataSystem.country.equals(this.country)) {
            this.country = dataSystem.country;
            changed = true;
        }
        if (dataSystem.latitude != null && !dataSystem.latitude.equals(this.latitude)) {
            this.latitude = dataSystem.latitude;
            changed = true;
        }
        if (dataSystem.longitude != null && !dataSystem.longitude.equals(this.longitude)) {
            this.longitude = dataSystem.longitude;
            changed = true;
        }
        
        // New fields
        if (dataSystem.aaServiceRev != null && !dataSystem.aaServiceRev.equals(this.aaServiceRev)) {
            this.aaServiceRev = dataSystem.aaServiceRev;
            changed = true;
        }
        if (dataSystem.allTspErrorCodes != null) {
            if (this.allTspErrorCodes == null || !this.allTspErrorCodes.equals(dataSystem.allTspErrorCodes)) {
                this.allTspErrorCodes = new HashMap<>(dataSystem.allTspErrorCodes);
                changed = true;
            }
        }
        if (dataSystem.backupId != null && !dataSystem.backupId.equals(this.backupId)) {
            this.backupId = dataSystem.backupId;
            changed = true;
        }
        if (dataSystem.deletedDevices != null) {
            if (this.deletedDevices == null || !this.deletedDevices.equals(dataSystem.deletedDevices)) {
                this.deletedDevices = new HashMap<>(dataSystem.deletedDevices);
                changed = true;
            }
        }
        if (dataSystem.deviceIdsV2 != null) {
            if (this.deviceIdsV2 == null || !this.deviceIdsV2.equals(dataSystem.deviceIdsV2)) {
                this.deviceIdsV2 = new HashMap<>(dataSystem.deviceIdsV2);
                changed = true;
            }
        }
        if (dataSystem.deviceNames != null) {
            if (this.deviceNames == null || !this.deviceNames.equals(dataSystem.deviceNames)) {
                this.deviceNames = new HashMap<>(dataSystem.deviceNames);
                changed = true;
            }
        }
        if (dataSystem.deviceNotificationVersion != null) {
            if (this.deviceNotificationVersion == null || !this.deviceNotificationVersion.equals(dataSystem.deviceNotificationVersion)) {
                this.deviceNotificationVersion = new HashMap<>(dataSystem.deviceNotificationVersion);
                changed = true;
            }
        }
        if (dataSystem.drawLightsTab != null && !dataSystem.drawLightsTab.equals(this.drawLightsTab)) {
            this.drawLightsTab = dataSystem.drawLightsTab;
            changed = true;
        }
        if (dataSystem.drawThingsTab != null && !dataSystem.drawThingsTab.equals(this.drawThingsTab)) {
            this.drawThingsTab = dataSystem.drawThingsTab;
            changed = true;
        }
        if (dataSystem.garageDoorReminderWaitTime != null && !dataSystem.garageDoorReminderWaitTime.equals(this.garageDoorReminderWaitTime)) {
            this.garageDoorReminderWaitTime = dataSystem.garageDoorReminderWaitTime;
            changed = true;
        }
        if (dataSystem.garageDoorSecurityPin != null && !dataSystem.garageDoorSecurityPin.equals(this.garageDoorSecurityPin)) {
            this.garageDoorSecurityPin = dataSystem.garageDoorSecurityPin;
            changed = true;
        }
        if (dataSystem.garageDoorSecurityPinEnabled != null && !dataSystem.garageDoorSecurityPinEnabled.equals(this.garageDoorSecurityPinEnabled)) {
            this.garageDoorSecurityPinEnabled = dataSystem.garageDoorSecurityPinEnabled;
            changed = true;
        }
        if (dataSystem.hasLocks != null && !dataSystem.hasLocks.equals(this.hasLocks)) {
            this.hasLocks = dataSystem.hasLocks;
            changed = true;
        }
        if (dataSystem.hasSensors != null && !dataSystem.hasSensors.equals(this.hasSensors)) {
            this.hasSensors = dataSystem.hasSensors;
            changed = true;
        }
        if (dataSystem.hasThings != null && !dataSystem.hasThings.equals(this.hasThings)) {
            this.hasThings = dataSystem.hasThings;
            changed = true;
        }
        if (dataSystem.hasThingsBOG != null && !dataSystem.hasThingsBOG.equals(this.hasThingsBOG)) {
            this.hasThingsBOG = dataSystem.hasThingsBOG;
            changed = true;
        }
        if (dataSystem.hasThingsLight != null && !dataSystem.hasThingsLight.equals(this.hasThingsLight)) {
            this.hasThingsLight = dataSystem.hasThingsLight;
            changed = true;
        }
        if (dataSystem.isValidSuburbTemp != null && !dataSystem.isValidSuburbTemp.equals(this.isValidSuburbTemp)) {
            this.isValidSuburbTemp = dataSystem.isValidSuburbTemp;
            changed = true;
        }
        if (dataSystem.lockDoorReminderWaitTime != null && !dataSystem.lockDoorReminderWaitTime.equals(this.lockDoorReminderWaitTime)) {
            this.lockDoorReminderWaitTime = dataSystem.lockDoorReminderWaitTime;
            changed = true;
        }
        if (dataSystem.membershipStatus != null && !dataSystem.membershipStatus.equals(this.membershipStatus)) {
            this.membershipStatus = dataSystem.membershipStatus;
            changed = true;
        }
        if (dataSystem.myLightsDealerPhoneNumber != null && !dataSystem.myLightsDealerPhoneNumber.equals(this.myLightsDealerPhoneNumber)) {
            this.myLightsDealerPhoneNumber = dataSystem.myLightsDealerPhoneNumber;
            changed = true;
        }
        if (dataSystem.myLightsLogoPIN != null && !dataSystem.myLightsLogoPIN.equals(this.myLightsLogoPIN)) {
            this.myLightsLogoPIN = dataSystem.myLightsLogoPIN;
            changed = true;
        }
        if (dataSystem.myPlaceDealerPhoneNumber != null && !dataSystem.myPlaceDealerPhoneNumber.equals(this.myPlaceDealerPhoneNumber)) {
            this.myPlaceDealerPhoneNumber = dataSystem.myPlaceDealerPhoneNumber;
            changed = true;
        }
        if (dataSystem.myPlaceLogoPIN != null && !dataSystem.myPlaceLogoPIN.equals(this.myPlaceLogoPIN)) {
            this.myPlaceLogoPIN = dataSystem.myPlaceLogoPIN;
            changed = true;
        }
        if (dataSystem.remoteAccessPairingEnabled != null && !dataSystem.remoteAccessPairingEnabled.equals(this.remoteAccessPairingEnabled)) {
            this.remoteAccessPairingEnabled = dataSystem.remoteAccessPairingEnabled;
            changed = true;
        }
        if (dataSystem.showMeasuredTemp != null && !dataSystem.showMeasuredTemp.equals(this.showMeasuredTemp)) {
            this.showMeasuredTemp = dataSystem.showMeasuredTemp;
            changed = true;
        }
        if (dataSystem.splitTypeSystem != null && !dataSystem.splitTypeSystem.equals(this.splitTypeSystem)) {
            this.splitTypeSystem = dataSystem.splitTypeSystem;
            changed = true;
        }
        if (dataSystem.suburbTemp != null && !dataSystem.suburbTemp.equals(this.suburbTemp)) {
            this.suburbTemp = dataSystem.suburbTemp;
            changed = true;
        }
        if (dataSystem.tspIp != null && !dataSystem.tspIp.equals(this.tspIp)) {
            this.tspIp = dataSystem.tspIp;
            changed = true;
        }
        if (dataSystem.tspModel != null && !dataSystem.tspModel.equals(this.tspModel)) {
            this.tspModel = dataSystem.tspModel;
            changed = true;
        }

        return changed;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public DataSystem(String str) {
        this.name = str;
    }

    public void copyFrom(DataSystem other) {
        if (other == null) return;
        if (other.dealerPhoneNumber != null) this.dealerPhoneNumber = other.dealerPhoneNumber;
        if (other.hasAircons != null) this.hasAircons = other.hasAircons;
        if (other.hasLights != null) this.hasLights = other.hasLights;
        if (other.needsUpdate != null) this.needsUpdate = other.needsUpdate;
        if (other.logoPIN != null) this.logoPIN = other.logoPIN;
        if (other.mid != null) this.mid = other.mid;
        if (other.myAppRev != null) this.myAppRev = other.myAppRev;
        if (other.name != null) this.name = other.name;
        if (other.noOfAircons != null) this.noOfAircons = other.noOfAircons;
        if (other.noOfSnapshots != null) this.noOfSnapshots = other.noOfSnapshots;
        if (other.rid != null) this.rid = other.rid;
        if (other.sysType != null) this.sysType = other.sysType;
        if (other.deviceIds != null) this.deviceIds = new java.util.ArrayList<>(other.deviceIds);
        if (other.postCode != null) this.postCode = other.postCode;
        if (other.country != null) this.country = other.country;
        if (other.latitude != null) this.latitude = other.latitude;
        if (other.longitude != null) this.longitude = other.longitude;
        
        // New fields
        if (other.aaServiceRev != null) this.aaServiceRev = other.aaServiceRev;
        if (other.allTspErrorCodes != null) this.allTspErrorCodes = new HashMap<>(other.allTspErrorCodes);
        if (other.tspErrorCode != null) this.tspErrorCode = other.tspErrorCode;
        if (other.backupId != null) this.backupId = other.backupId;
        if (other.deletedDevices != null) this.deletedDevices = new HashMap<>(other.deletedDevices);
        if (other.deviceIdsV2 != null) this.deviceIdsV2 = new HashMap<>(other.deviceIdsV2);
        if (other.deviceNames != null) this.deviceNames = new HashMap<>(other.deviceNames);
        if (other.deviceNotificationVersion != null) this.deviceNotificationVersion = new HashMap<>(other.deviceNotificationVersion);
        if (other.drawLightsTab != null) this.drawLightsTab = other.drawLightsTab;
        if (other.drawThingsTab != null) this.drawThingsTab = other.drawThingsTab;
        if (other.garageDoorReminderWaitTime != null) this.garageDoorReminderWaitTime = other.garageDoorReminderWaitTime;
        if (other.garageDoorSecurityPin != null) this.garageDoorSecurityPin = other.garageDoorSecurityPin;
        if (other.garageDoorSecurityPinEnabled != null) this.garageDoorSecurityPinEnabled = other.garageDoorSecurityPinEnabled;
        if (other.hasLocks != null) this.hasLocks = other.hasLocks;
        if (other.hasSensors != null) this.hasSensors = other.hasSensors;
        if (other.hasThings != null) this.hasThings = other.hasThings;
        if (other.hasThingsBOG != null) this.hasThingsBOG = other.hasThingsBOG;
        if (other.hasThingsLight != null) this.hasThingsLight = other.hasThingsLight;
        if (other.isValidSuburbTemp != null) this.isValidSuburbTemp = other.isValidSuburbTemp;
        if (other.lockDoorReminderWaitTime != null) this.lockDoorReminderWaitTime = other.lockDoorReminderWaitTime;
        if (other.membershipStatus != null) this.membershipStatus = other.membershipStatus;
        if (other.myLightsDealerPhoneNumber != null) this.myLightsDealerPhoneNumber = other.myLightsDealerPhoneNumber;
        if (other.myLightsLogoPIN != null) this.myLightsLogoPIN = other.myLightsLogoPIN;
        if (other.myPlaceDealerPhoneNumber != null) this.myPlaceDealerPhoneNumber = other.myPlaceDealerPhoneNumber;
        if (other.myPlaceLogoPIN != null) this.myPlaceLogoPIN = other.myPlaceLogoPIN;
        if (other.remoteAccessPairingEnabled != null) this.remoteAccessPairingEnabled = other.remoteAccessPairingEnabled;
        if (other.showMeasuredTemp != null) this.showMeasuredTemp = other.showMeasuredTemp;
        if (other.splitTypeSystem != null) this.splitTypeSystem = other.splitTypeSystem;
        if (other.suburbTemp != null) this.suburbTemp = other.suburbTemp;
        if (other.tspIp != null) this.tspIp = other.tspIp;
        if (other.tspModel != null) this.tspModel = other.tspModel;
    }
}