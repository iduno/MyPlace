package com.air.advantage.aaservice.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.jboss.logmanager.Level;

import com.air.advantage.aaservice.data.DataAircon.FanStatus;
import com.air.advantage.libraryairconlightjson.AirconMode;
import com.air.advantage.libraryairconlightjson.SystemState;
import com.air.advantage.libraryairconlightjson.ZoneState;
import com.air.advantage.libraryairconlightjson.backup.BackupAirconV1;
import com.air.advantage.libraryairconlightjson.backup.BackupDataV1;
import com.air.advantage.libraryairconlightjson.backup.BackupLightV1;
import com.air.advantage.libraryairconlightjson.backup.BackupSnapshotSceneV1;
import com.air.advantage.libraryairconlightjson.backup.BackupZoneV1;
import com.google.gson.Gson;

/* compiled from: BackupDataFunctions.java */
/* renamed from: com.air.advantage.aaservice.o.a */
/* loaded from: classes.dex */
public class BackupDataFunctions {

    private static final Logger LOGGER = Logger.getLogger(BackupDataFunctions.class.getName());
    private final Gson gson = new Gson();

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: a */
    public String toJson(MasterData masterData) {
        TreeMap<String, DataGroup> dataGroupMap;
        ArrayList<String> stringList;
        BackupDataV1 backupDataV1 = new BackupDataV1();
        String masterDataId = masterData.system.logoPIN;
        String masterDataName = masterData.system.dealerPhoneNumber;
        String masterDataType = masterData.system.postCode;
        String masterDataDescription = masterData.system.name;
        for (String airconKey : masterData.aircons.keySet()) {
            DataAircon dataAircon = masterData.aircons.get(airconKey);
            String airconUid = dataAircon.airconInfo.uid;
            if (airconUid == null) {
                
                LOGGER.log(Level.DEBUG,"BackupDataFunctions", "Waring null uid for " + airconKey);
            } else if (airconUid.equals("000000000000")) {
                LOGGER.log(Level.DEBUG,"BackupDataFunctions", "Found mac zeros");
            } else {
                BackupAirconV1 backupAirconV1 = new BackupAirconV1();
                String airconType = dataAircon.airconInfo.name;
                if (airconType != null && !airconType.equals("AC")) {
                    String airconTypeCheck = dataAircon.airconInfo.name;
                }
                int zoneIndex = 1;
                for (String zoneKey : dataAircon.zones.keySet()) {
                    DataZone dataZone = dataAircon.zones.get(zoneKey);
                    String zoneName = dataZone.name;
                    if (zoneName != null) {
                        if (!zoneName.equals("Zone" + zoneIndex)) {
                            BackupZoneV1 backupZoneV1 = new BackupZoneV1();
                            String zoneNameCheck = dataZone.name;
                            if (backupAirconV1.zonesBackup == null) {
                                backupAirconV1.zonesBackup = new TreeMap<>();
                            }
                            backupAirconV1.zonesBackup.put(zoneKey, backupZoneV1);
                        }
                    }
                    zoneIndex++;
                }
                if (backupDataV1.airconBackup == null) {
                    backupDataV1.airconBackup = new TreeMap<>();
                }
                backupDataV1.airconBackup.put(dataAircon.airconInfo.uid, backupAirconV1);
            }
        }
        for (String str : masterData.snapshots.keySet()) {
            SnapShot snapShot = masterData.snapshots.get(str);
            BackupSnapshotSceneV1 backupSnapshotSceneV1 = new BackupSnapshotSceneV1();
            Integer num = snapShot.activeDays;
            Boolean bool = snapShot.enabled;
            String str2 = snapShot.name;
            Integer num2 = snapShot.startTime;
            Integer num3 = snapShot.stopTime;
            for (String str3 : snapShot.aircons.keySet()) {
                DataAircon dataAircon2 = snapShot.aircons.get(str3);
                BackupAirconV1 backupAirconV12 = new BackupAirconV1();
                DataAirconInfo dataAirconInfo = dataAircon2.airconInfo;
                Integer num4 = dataAirconInfo.myZone;
                AirconMode airconMode = dataAirconInfo.mode;
                FanStatus fanMode = dataAirconInfo.fan;
                Float f2 = dataAirconInfo.setTemp;
                SystemState systemState = dataAirconInfo.state;
                dataAirconInfo.freshAirStatus.getValue();
                for (String str4 : dataAircon2.zones.keySet()) {
                    DataZone dataZone2 = dataAircon2.zones.get(str4);
                    BackupZoneV1 backupZoneV12 = new BackupZoneV1();
                    ZoneState zoneState = dataZone2.state;
                    Integer num5 = dataZone2.value;
                    Float f3 = dataZone2.setTemp;
                    if (backupAirconV12.zonesBackup == null) {
                        backupAirconV12.zonesBackup = new TreeMap<>();
                    }
                    backupAirconV12.zonesBackup.put(str4, backupZoneV12);
                }
                if (backupSnapshotSceneV1.backupAircons == null) {
                    backupSnapshotSceneV1.backupAircons = new TreeMap<>();
                }
                backupSnapshotSceneV1.backupAircons.put(str3, backupAirconV12);
            }
            if (backupDataV1.plansBackup == null) {
                backupDataV1.plansBackup = new TreeMap<>();
            }
            backupDataV1.plansBackup.put(str, backupSnapshotSceneV1);
        }
        DataLightsAll dataLightsAll = masterData.myLights;
        if (dataLightsAll != null && (dataGroupMap = dataLightsAll.groups) != null && dataGroupMap.size() > 0 && (stringList = masterData.myLights.groupsOrder) != null && stringList.size() > 0) {
            Iterator<String> it = masterData.myLights.groupsOrder.iterator();
            while (it.hasNext()) {
                DataGroup dataGroup = masterData.myLights.groups.get(it.next());
                if (dataGroup != null) {
                    BackupLightV1 backupLightV1 = new BackupLightV1();
                    String str5 = dataGroup.name;
                    backupLightV1.lights = new ArrayList<>();
                    Iterator<String> it2 = dataGroup.lightsOrder.iterator();
                    while (it2.hasNext()) {
                        DataLight dataLight = masterData.myLights.lights.get(it2.next());
                        if (dataLight != null) {
                            BackupLightV1 backupLightV12 = new BackupLightV1();
                            String str6 = dataLight.name;
                            backupLightV1.lights.add(backupLightV12);
                        }
                    }
                    if (backupDataV1.groupsBackup == null) {
                        backupDataV1.groupsBackup = new ArrayList<>();
                    }
                    backupDataV1.groupsBackup.add(backupLightV1);
                }
            }
        }
        String a2 = this.gson.toJson(backupDataV1);
        LOGGER.log(Level.DEBUG,"BackupDataFunctions", "Final json - " + a2);
        return a2;
    }
}