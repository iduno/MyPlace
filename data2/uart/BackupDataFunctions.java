package com.air.advantage.uart;

import com.air.advantage.backup.Aircon;
import com.air.advantage.backup.Alarm;
import com.air.advantage.backup.Backup;
import com.air.advantage.backup.Group;
import com.air.advantage.backup.Zone;
import com.air.advantage.data.DataAirconInfo;
import com.air.advantage.data.DataAirconSystem;
import com.air.advantage.data.DataAlarm;
import com.air.advantage.data.DataGroup;
import com.air.advantage.data.DataLight;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataMyLights;
import com.air.advantage.data.DataScene;
import com.air.advantage.data.DataSystem;
import com.air.advantage.data.DataZone;
import com.air.advantage.data.SnapShot;
import com.air.advantage.libraryairconlightjson.AirconFunctionsConstants;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.uart.e */
/* loaded from: classes.dex */
public final class BackupDataFunctions {

    /* renamed from: c */
    @NotNull
    public static final Companion Companion = new Companion(null);

    /* renamed from: d */
    @NotNull
    private static final String LOG_NAME = "BackupDataFunctions";

    /* renamed from: a */
    @NotNull
    private final ArrayList messageList = new ArrayList();

    /* renamed from: b */
    @NotNull
    private final Gson gson = new Gson();

    /* renamed from: com.air.advantage.uart.e$a */
    public static final class Companion {
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private Companion() {
        }
    }

    @NotNull
    public final String a(@NotNull DataMaster masterData) {
        ArrayList<String> arrayList;
        ArrayList<String> arrayList2;
        ArrayList<String> arrayList3;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        Backup backup = new Backup();
        backup.version = 1;
        DataSystem dataSystem = masterData.system;
        String str = dataSystem.logoPIN;
        if (str != null) {
            backup.logoPin = str;
        }
        String str2 = dataSystem.dealerPhoneNumber;
        if (str2 != null) {
            backup.phoneNumber = str2;
        }
        String str3 = dataSystem.postCode;
        if (str3 != null) {
            backup.postCode = str3;
        }
        String str4 = dataSystem.name;
        if (str4 != null) {
            backup.systemName = str4;
        }
        for (String str5 : masterData.aircons.keySet()) {
            DataAirconSystem dataAirconSystem = masterData.aircons.get(str5);
            Intrinsics.checkNotNull(dataAirconSystem);
            String str6 = dataAirconSystem.info.uid;
            if (str6 == null) {
                Timber.forest.d("Waring null uid for " + str5, new Object[0]);
            } else if (Intrinsics.areEqual(str6, Xml2JsonFunctions.DEFAULT_UID)) {
                Timber.forest.d("Found mac zeros", new Object[0]);
            } else {
                Aircon aircon = new Aircon();
                if (!Intrinsics.areEqual(dataAirconSystem.info.name, AirconFunctionsConstants.AC)) {
                    aircon.airconName = dataAirconSystem.info.name;
                }
                TreeMap<String, DataZone> treeMap = dataAirconSystem.zones;
                Intrinsics.checkNotNull(treeMap);
                int i10 = 1;
                for (String str7 : treeMap.keySet()) {
                    TreeMap<String, DataZone> treeMap2 = dataAirconSystem.zones;
                    Intrinsics.checkNotNull(treeMap2);
                    DataZone dataZone = treeMap2.get(str7);
                    Intrinsics.checkNotNull(dataZone);
                    if (!Intrinsics.areEqual(dataZone.name, "Zone" + i10)) {
                        Zone zone = new Zone();
                        zone.name = dataZone.name;
                        if (aircon.zonesBackup == null) {
                            aircon.zonesBackup = new TreeMap<>();
                        }
                        TreeMap<String, Zone> zonesBackup = aircon.zonesBackup;
                        Intrinsics.checkNotNullExpressionValue(zonesBackup, "zonesBackup");
                        zonesBackup.put(str7, zone);
                    }
                    i10++;
                }
                if (backup.airconBackup == null) {
                    backup.airconBackup = new TreeMap<>();
                }
                TreeMap<String, Aircon> airconBackup = backup.airconBackup;
                Intrinsics.checkNotNullExpressionValue(airconBackup, "airconBackup");
                airconBackup.put(dataAirconSystem.info.uid, aircon);
            }
        }
        for (String str8 : masterData.snapshots.keySet()) {
            SnapShot snapShot = masterData.snapshots.get(str8);
            Alarm backup2 = new Alarm();
            Intrinsics.checkNotNull(snapShot);
            backup2.activeDays = snapShot.activeDays;
            backup2.enabled = snapShot.enabled;
            backup2.name = snapShot.name;
            backup2.startTime = snapShot.startTime;
            backup2.stopTime = snapShot.stopTime;
            TreeMap treeMap3 = snapShot.aircons;
            Intrinsics.checkNotNull(treeMap3);
            Iterator it = treeMap3.keySet().iterator();
            while (it.hasNext()) {
                DataAirconSystem dataAirconSystem2 = masterData.aircons.get((String) it.next());
                Aircon aircon2 = new Aircon();
                Intrinsics.checkNotNull(dataAirconSystem2);
                DataAirconInfo dataAirconInfo = dataAirconSystem2.info;
                aircon2.myZone = dataAirconInfo.myZone;
                aircon2.mode = dataAirconInfo.mode;
                aircon2.fan = dataAirconInfo.fan;
                aircon2.setTemp = dataAirconInfo.setTemp;
                aircon2.state = dataAirconInfo.state;
                TreeMap<String, DataZone> treeMap4 = dataAirconSystem2.zones;
                Intrinsics.checkNotNull(treeMap4);
                for (String str9 : treeMap4.keySet()) {
                    TreeMap<String, DataZone> treeMap5 = dataAirconSystem2.zones;
                    Intrinsics.checkNotNull(treeMap5);
                    DataZone dataZone2 = treeMap5.get(str9);
                    Zone zone2 = new Zone();
                    Intrinsics.checkNotNull(dataZone2);
                    zone2.state = dataZone2.state;
                    zone2.value = dataZone2.value;
                    zone2.setTemp = dataZone2.setTemp;
                    if (aircon2.zonesBackup == null) {
                        aircon2.zonesBackup = new TreeMap<>();
                    }
                    TreeMap<String, Zone> zonesBackup2 = aircon2.zonesBackup;
                    Intrinsics.checkNotNullExpressionValue(zonesBackup2, "zonesBackup");
                    zonesBackup2.put(str9, zone2);
                }
                if (backup2.backupAircons == null) {
                    backup2.backupAircons = new TreeMap<>();
                }
                TreeMap<String, Aircon> backupAircons = backup2.backupAircons;
                Intrinsics.checkNotNullExpressionValue(backupAircons, "backupAircons");
                backupAircons.put(dataAirconSystem2.info.uid, aircon2);
            }
            if (backup.plansBackup == null) {
                backup.plansBackup = new TreeMap<>();
            }
            TreeMap<String, Alarm> plansBackup = backup.plansBackup;
            Intrinsics.checkNotNullExpressionValue(plansBackup, "plansBackup");
            plansBackup.put(str8, backup2);
        }
        DataMyLights dataMyLights = masterData.myLights;
        if (dataMyLights != null) {
            TreeMap<String, DataGroup> treeMap6 = dataMyLights.groups;
            if (treeMap6 != null) {
                Intrinsics.checkNotNull(treeMap6);
                if (treeMap6.size() > 0 && (arrayList3 = masterData.myLights.groupsOrder) != null) {
                    Intrinsics.checkNotNull(arrayList3);
                    if (arrayList3.size() > 0) {
                        TreeMap<String, DataGroup> treeMap7 = masterData.myLights.groups;
                        Intrinsics.checkNotNull(treeMap7);
                        for (String str10 : treeMap7.keySet()) {
                            TreeMap<String, DataGroup> treeMap8 = masterData.myLights.groups;
                            Intrinsics.checkNotNull(treeMap8);
                            DataGroup dataGroup = treeMap8.get(str10);
                            Group group = new Group();
                            group.id = str10;
                            Intrinsics.checkNotNull(dataGroup);
                            group.name = dataGroup.name;
                            group.lights = new ArrayList<>();
                            Iterator<String> it2 = dataGroup.lightsOrder.iterator();
                            while (it2.hasNext()) {
                                String next = it2.next();
                                TreeMap<String, DataLight> treeMap9 = masterData.myLights.lights;
                                Intrinsics.checkNotNull(treeMap9);
                                DataLight dataLight = treeMap9.get(next);
                                Group group2 = new Group();
                                group2.id = next;
                                Intrinsics.checkNotNull(dataLight);
                                group2.name = dataLight.name;
                                group.lights.add(group2);
                            }
                            if (backup.groupsBackup == null) {
                                backup.groupsBackup = new ArrayList<>();
                            }
                            backup.groupsBackup.add(group);
                        }
                    }
                }
            }
            TreeMap<String, DataScene> treeMap10 = masterData.myScenes.scenes;
            if (treeMap10 != null) {
                Intrinsics.checkNotNull(treeMap10);
                if (treeMap10.size() > 0 && (arrayList2 = masterData.myScenes.scenesOrder) != null) {
                    Intrinsics.checkNotNull(arrayList2);
                    if (arrayList2.size() > 0) {
                        backup.scenesOrder = new ArrayList<>(masterData.myScenes.scenesOrder);
                        TreeMap<String, DataScene> treeMap11 = masterData.myScenes.scenes;
                        Intrinsics.checkNotNull(treeMap11);
                        for (String str11 : treeMap11.keySet()) {
                            TreeMap<String, DataScene> treeMap12 = masterData.myScenes.scenes;
                            Intrinsics.checkNotNull(treeMap12);
                            DataScene dataScene = treeMap12.get(str11);
                            Alarm alarm = new Alarm();
                            Intrinsics.checkNotNull(dataScene);
                            alarm.name = dataScene.name;
                            alarm.activeDays = dataScene.activeDays;
                            alarm.startTime = dataScene.startTime;
                            alarm.enabled = dataScene.timerEnabled;
                            HashMap hashMap = dataScene.lights;
                            if (hashMap != null) {
                                Intrinsics.checkNotNull(hashMap);
                                for (String str12 : hashMap.keySet()) {
                                    HashMap hashMap2 = dataScene.lights;
                                    Intrinsics.checkNotNull(hashMap2);
                                    DataLight dataLight2 = (DataLight) hashMap2.get(str12);
                                    Group group3 = new Group();
                                    Intrinsics.checkNotNull(dataLight2);
                                    Boolean bool = dataLight2.enableInScene;
                                    Intrinsics.checkNotNull(bool);
                                    if (bool.booleanValue()) {
                                        group3.value = dataLight2.value;
                                        group3.state = dataLight2.state;
                                        if (alarm.lights == null) {
                                            alarm.lights = new TreeMap<>();
                                        }
                                        TreeMap<String, Group> lights = alarm.lights;
                                        Intrinsics.checkNotNullExpressionValue(lights, "lights");
                                        lights.put(str12, group3);
                                    }
                                }
                                if (backup.scenesBackup == null) {
                                    backup.scenesBackup = new TreeMap<>();
                                }
                                TreeMap<String, Alarm> scenesBackup = backup.scenesBackup;
                                Intrinsics.checkNotNullExpressionValue(scenesBackup, "scenesBackup");
                                scenesBackup.put(str11, alarm);
                            }
                        }
                    }
                }
            }
            TreeMap<String, DataAlarm> treeMap13 = masterData.myLights.alarms;
            if (treeMap13 != null) {
                Intrinsics.checkNotNull(treeMap13);
                if (treeMap13.size() > 0 && (arrayList = masterData.myLights.alarmsOrder) != null) {
                    Intrinsics.checkNotNull(arrayList);
                    if (arrayList.size() > 0) {
                        backup.alarmsOrder = new ArrayList<>(masterData.myLights.alarmsOrder);
                        TreeMap<String, DataAlarm> treeMap14 = masterData.myLights.alarms;
                        Intrinsics.checkNotNull(treeMap14);
                        for (String str13 : treeMap14.keySet()) {
                            TreeMap<String, DataAlarm> treeMap15 = masterData.myLights.alarms;
                            Intrinsics.checkNotNull(treeMap15);
                            DataAlarm dataAlarm = treeMap15.get(str13);
                            Alarm alarm2 = new Alarm();
                            Intrinsics.checkNotNull(dataAlarm);
                            alarm2.enabled = dataAlarm.timerEnabled;
                            alarm2.startTime = dataAlarm.startTime;
                            HashMap hashMap3 = dataAlarm.lights;
                            Intrinsics.checkNotNull(hashMap3);
                            for (String str14 : hashMap3.keySet()) {
                                HashMap hashMap4 = dataAlarm.lights;
                                Intrinsics.checkNotNull(hashMap4);
                                DataLight dataLight3 = (DataLight) hashMap4.get(str14);
                                Group group4 = new Group();
                                Intrinsics.checkNotNull(dataLight3);
                                group4.value = dataLight3.value;
                                if (alarm2.lights == null) {
                                    alarm2.lights = new TreeMap<>();
                                }
                                TreeMap<String, Group> lights2 = alarm2.lights;
                                Intrinsics.checkNotNullExpressionValue(lights2, "lights");
                                lights2.put(str14, group4);
                            }
                            if (backup.alarmsBackup == null) {
                                backup.alarmsBackup = new TreeMap<>();
                            }
                            TreeMap<String, Alarm> alarmsBackup = backup.alarmsBackup;
                            Intrinsics.checkNotNullExpressionValue(alarmsBackup, "alarmsBackup");
                            alarmsBackup.put(str13, alarm2);
                        }
                    }
                }
            }
        }
        String json = this.gson.toJson(backup);
        Timber.forest.d("Final json - " + json, new Object[0]);
        Intrinsics.checkNotNull(json);
        return json;
    }

    @NotNull
    /* renamed from: b */
    public final ArrayList getMessages() {
        return this.messageList;
    }

    public final void c(@NotNull DataMaster masterData, @Nullable String str) throws ExceptionUart, JsonParseException {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        Object fromJson = this.gson.fromJson(str, Backup.class);
        Intrinsics.checkNotNullExpressionValue(fromJson, "fromJson(...)");
        Backup backup = (Backup) fromJson;
        String str2 = backup.logoPin;
        if (str2 != null) {
            masterData.system.logoPIN = str2;
        }
        String str3 = backup.phoneNumber;
        if (str3 != null) {
            masterData.system.dealerPhoneNumber = str3;
        }
        String str4 = backup.postCode;
        if (str4 != null) {
            masterData.system.postCode = str4;
        }
        String str5 = backup.systemName;
        if (str5 != null && !Intrinsics.areEqual(str5, AirconFunctionsConstants.AIRCON)) {
            masterData.system.name = backup.systemName;
        }
        TreeMap<String, Aircon> treeMap = backup.airconBackup;
        if (treeMap == null) {
            Timber.forest.d("No aircon data!", new Object[0]);
        } else {
            for (String str6 : treeMap.keySet()) {
                DataAirconSystem airconByUid = masterData.getAirconByUid(str6);
                if (airconByUid == null) {
                    Timber.forest.d("Creating aircon " + str6, new Object[0]);
                    airconByUid = new DataAirconSystem(str6);
                    masterData.aircons.put(str6, airconByUid);
                } else {
                    Timber.forest.d("Got aircon " + str6, new Object[0]);
                }
                Aircon aircon = backup.airconBackup.get(str6);
                airconByUid.info.uid = str6;
                Intrinsics.checkNotNull(aircon);
                String str7 = aircon.airconName;
                if (str7 != null) {
                    airconByUid.info.name = str7;
                }
                TreeMap<String, Zone> treeMap2 = aircon.zonesBackup;
                if (treeMap2 == null) {
                    Timber.forest.d("Aircon " + str6 + " has no zones.", new Object[0]);
                } else {
                    for (String str8 : treeMap2.keySet()) {
                        TreeMap<String, DataZone> treeMap3 = airconByUid.zones;
                        Intrinsics.checkNotNull(treeMap3);
                        DataZone dataZone = treeMap3.get(str8);
                        Zone zone = aircon.zonesBackup.get(str8);
                        if (dataZone == null) {
                            Timber.forest.d("Making zone " + str8, new Object[0]);
                            dataZone = new DataZone(str8);
                            TreeMap<String, DataZone> treeMap4 = airconByUid.zones;
                            Intrinsics.checkNotNull(treeMap4);
                            treeMap4.put(str8, dataZone);
                        } else {
                            Timber.forest.d("Updating zone " + str8, new Object[0]);
                        }
                        Intrinsics.checkNotNull(zone);
                        String str9 = zone.name;
                        if (str9 != null) {
                            dataZone.name = str9;
                        }
                    }
                }
            }
        }
        TreeMap<String, Alarm> treeMap5 = backup.plansBackup;
        if (treeMap5 != null) {
            for (String str10 : treeMap5.keySet()) {
                SnapShot snapShot = masterData.snapshots.get(str10);
                if (snapShot == null) {
                    snapShot = new SnapShot();
                    masterData.snapshots.put(str10, snapShot);
                }
                snapShot.snapshotId = str10;
                Alarm alarm = backup.plansBackup.get(str10);
                Intrinsics.checkNotNull(alarm);
                Integer num = alarm.activeDays;
                if (num != null) {
                    snapShot.activeDays = num;
                }
                Boolean bool = alarm.enabled;
                if (bool != null) {
                    snapShot.enabled = bool;
                }
                String str11 = alarm.name;
                if (str11 != null) {
                    snapShot.name = str11;
                }
                Integer num2 = alarm.startTime;
                if (num2 != null) {
                    snapShot.startTime = num2;
                }
                Integer num3 = alarm.stopTime;
                if (num3 != null) {
                    snapShot.stopTime = num3;
                }
                TreeMap<String, Aircon> treeMap6 = alarm.backupAircons;
                if (treeMap6 == null) {
                    Timber.forest.d("Corrupt snapshot", new Object[0]);
                } else {
                    for (String str12 : treeMap6.keySet()) {
                        TreeMap treeMap7 = snapShot.aircons;
                        Intrinsics.checkNotNull(treeMap7);
                        DataAirconSystem dataAirconSystem = (DataAirconSystem) treeMap7.get(str12);
                        if (dataAirconSystem == null) {
                            dataAirconSystem = new DataAirconSystem(str12);
                            TreeMap treeMap8 = snapShot.aircons;
                            Intrinsics.checkNotNull(treeMap8);
                            treeMap8.put(str12, dataAirconSystem);
                        }
                        Aircon aircon2 = alarm.backupAircons.get(str12);
                        DataAirconInfo dataAirconInfo = dataAirconSystem.info;
                        Intrinsics.checkNotNull(aircon2);
                        dataAirconInfo.state = aircon2.state;
                        DataAirconInfo dataAirconInfo2 = dataAirconSystem.info;
                        dataAirconInfo2.myZone = aircon2.myZone;
                        dataAirconInfo2.mode = aircon2.mode;
                        dataAirconInfo2.fan = aircon2.fan;
                        dataAirconInfo2.setTemp = aircon2.setTemp;
                        dataAirconInfo2.freshAirStatus = DataAirconSystem.FreshAirStatus.none;
                        Integer num4 = aircon2.freshAir;
                        if (num4 != null) {
                            if (num4 != null && num4.intValue() == 1) {
                                dataAirconSystem.info.freshAirStatus = DataAirconSystem.FreshAirStatus.off;
                            } else if (num4 != null && num4.intValue() == 2) {
                                dataAirconSystem.info.freshAirStatus = DataAirconSystem.FreshAirStatus.on;
                            }
                        }
                        for (String str13 : aircon2.zonesBackup.keySet()) {
                            TreeMap<String, DataZone> treeMap9 = dataAirconSystem.zones;
                            Intrinsics.checkNotNull(treeMap9);
                            DataZone dataZone2 = treeMap9.get(str13);
                            if (dataZone2 == null) {
                                dataZone2 = new DataZone();
                                TreeMap<String, DataZone> treeMap10 = dataAirconSystem.zones;
                                Intrinsics.checkNotNull(treeMap10);
                                treeMap10.put(str13, dataZone2);
                            }
                            Zone zone2 = aircon2.zonesBackup.get(str13);
                            Intrinsics.checkNotNull(zone2);
                            dataZone2.state = zone2.state;
                            dataZone2.value = zone2.value;
                            dataZone2.setTemp = zone2.setTemp;
                            dataZone2.setNumberFromKey(str13);
                        }
                    }
                }
            }
        }
    }

    /* renamed from: d */
    public final void processBackupJson(@NotNull DataMaster masterData, @Nullable String str) throws ExceptionUart, JsonParseException {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        Object fromJson = this.gson.fromJson(str, Backup.class);
        Intrinsics.checkNotNullExpressionValue(fromJson, "fromJson(...)");
        Backup backup = (Backup) fromJson;
        ArrayList<Group> arrayList = backup.groupsBackup;
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        Iterator<Group> it = backup.groupsBackup.iterator();
        while (it.hasNext()) {
            Group next = it.next();
            String str2 = next.id;
            if (str2 != null) {
                String str3 = "id=" + str2 + "&name=" + next.name;
                TreeMap<String, DataGroup> treeMap = masterData.myLights.groups;
                Intrinsics.checkNotNull(treeMap);
                if (treeMap.containsKey(next.id)) {
                    this.messageList.add("setLightGroupName?" + str3);
                } else {
                    this.messageList.add("setLightNewGroupName?" + str3);
                }
                Iterator<Group> it2 = next.lights.iterator();
                while (it2.hasNext()) {
                    Group next2 = it2.next();
                    String str4 = next2.id;
                    if (str4 == null) {
                        Timber.forest.d("Null light id", new Object[0]);
                    } else {
                        String str5 = "json={\"id\":\"" + str4 + "\",\"name\":\"" + next2.name + "\"}";
                        this.messageList.add("setLight?" + str5);
                        String str6 = "id=" + next2.id + "&groupId=" + next.id + "&position=99";
                        this.messageList.add("setLightToGroup?" + str6);
                    }
                }
            }
        }
    }
}