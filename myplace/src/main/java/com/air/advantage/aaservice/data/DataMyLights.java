package com.air.advantage.aaservice.data;

import java.util.ArrayList;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.annotations.SerializedName;

/* compiled from: DataLightsAll.java */
/* renamed from: com.air.advantage.aaservice.o.h */
/* loaded from: classes.dex */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataMyLights {

    @SerializedName("lights")
    @JsonProperty("lights")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public TreeMap<String, DataLight> lights = new TreeMap<>();

    @SerializedName("groups")
    @JsonProperty("groups")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public TreeMap<String, DataGroup> groups;

    @SerializedName("groupsOrder")
    @JsonProperty("groupsOrder")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public ArrayList<String> groupsOrder;

    @SerializedName("scenes")
    @JsonProperty("scenes")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public TreeMap<String, DataScene> scenes;

    @SerializedName("scenesOrder")
    @JsonProperty("scenesOrder")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public ArrayList<String> scenesOrder;

    @SerializedName("alarmsOrder")
    @JsonProperty("alarmsOrder")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public ArrayList<String> alarmsOrder;

    @SerializedName("backupLights")
    @JsonProperty("backupLights")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public ArrayList<DataMyLights.BackupLight> backupLights;

    public static final String DEFAULT_GROUP = "g0";
    public static final int MAX_NO_OF_ALARMS = 4;

    /* renamed from: com.air.advantage.data.t$b */
    public static final class BackupLight {

        @SerializedName("groupId")
        @JsonProperty("groupId")
        @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
        public String groupId;

        @SerializedName("id")
        @JsonProperty("id")
        @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
        public String id;

        @SerializedName("name")
        @JsonProperty("name")
        @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
        public String name;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public DataMyLights() {
        new ArrayList();
        this.groups = new TreeMap<>();
        this.groupsOrder = new ArrayList<>();
        this.scenes = new TreeMap<>();
        this.scenesOrder = new ArrayList<>();
        this.alarmsOrder = new ArrayList<>();
        this.backupLights = new ArrayList<>();
    }

    public void copyFrom(DataMyLights other) {
        if (other == null) return;
        if (other.lights != null) {
            for (String key : other.lights.keySet()) {
                DataLight light = other.lights.get(key);
                if (light != null) {
                    DataLight newLight = this.lights.get(key);
                    if (newLight != null) {
                        newLight.copyFrom(light);
                    } else {
                        newLight = new DataLight();
                        newLight.copyFrom(light);
                    }
                    this.lights.put(key, newLight);
                }
            }
        }
        if (other.groups != null) {
            for (String key : other.groups.keySet()) {
                DataGroup group = other.groups.get(key);
                if (group != null) {
                    DataGroup newGroup = this.groups.get(key);
                    if (newGroup != null) {
                        newGroup.copyFrom(group);
                    } else {
                        newGroup = new DataGroup();
                        newGroup.copyFrom(group);
                    }
                    this.groups.put(key, newGroup);
                }
            }
        }
        this.groupsOrder.clear();
        if (other.groupsOrder != null) this.groupsOrder.addAll(other.groupsOrder);
        
        if (other.scenes != null) {
            for (String key : other.scenes.keySet()) {
                DataScene scene = other.scenes.get(key);
                if (scene != null) {
                    DataScene newScene = this.scenes.get(key);
                    if (newScene != null) {
                        newScene.copyFrom(scene);
                    } else {
                        newScene = new DataScene();
                        newScene.copyFrom(scene);
                    }
                    this.scenes.put(key, newScene);
                }
            }
        }
        this.scenesOrder.clear();
        if (other.scenesOrder != null) this.scenesOrder.addAll(other.scenesOrder);

        // if (other.alarms != null) {
        //     for (String key : other.alarms.keySet()) {
        //         DataAlarm alarm = other.alarms.get(key);
        //         if (alarm != null) {
        //             DataAlarm newAlarm = this.alarms.get(key);
        //             if (newAlarm != null) {
        //                 newAlarm.copyFrom(alarm);
        //             } else {
        //                 newAlarm = new DataAlarm();
        //                 newAlarm.copyFrom(alarm);
        //             }
        //             this.alarms.put(key, newAlarm);
        //         }
        //     }
        // }
        this.alarmsOrder.clear();
        if (other.alarmsOrder != null) this.alarmsOrder.addAll(other.alarmsOrder);

        // if (other.backupLights != null) {
        //     for (DataMyLights.BackupLight backupLight : other.backupLights) {
        //         DataMyLights.BackupLight newBackupLight = this.backupLights.get(backupLight.id);
        //         if (newBackupLight != null) {
        //             newBackupLight.copyFrom(backupLight);
        //         } else {
        //             newBackupLight = new DataMyLights.BackupLight();
        //             newBackupLight.copyFrom(backupLight);
        //         }
        //         this.backupLights.put(backupLight.id, newBackupLight);
        //     }
        // }
        // this.backupLights.clear();
        // if (other.backupLights != null) this.backupLights.addAll(other.backupLights);
    }

    // --- Functions ported from DataMyLights.java ---

    public DataGroup getGroupFromLightId(String lightId) {
        if (groups == null) return null;
        for (DataGroup group : groups.values()) {
            if (group == null) continue;
            for (String id : group.lightsOrder) {
                if (id != null && id.equals(lightId)) {
                    return group;
                }
            }
        }
        return null;
    }

    // public DataAlarm getAlarm(String id) {
    //     if (alarms == null) return null;
    //     for (DataAlarm alarm : alarms.values()) {
    //         if (alarm != null && id != null && id.equals(alarm.id)) {
    //             return alarm;
    //         }
    //     }
    //     return null;
    // }

    public DataGroup getDataGroup(String id) {
        if (id == null || groups == null) return null;
        return groups.get(id);
    }

    public String getGroupId(String lightId) {
        DataGroup group = getGroupFromLightId(lightId);
        return group != null ? group.id : null;
    }

    public DataLight getLightData(String id) {
        if (id == null || lights == null) return null;
        return lights.get(id);
    }

    public int getLightPosition(String lightId) {
        if (groups == null) return -1;
        for (DataGroup group : groups.values()) {
            if (group == null) continue;
            int i = 0;
            for (String id : group.lightsOrder) {
                if (id != null && id.equals(lightId)) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    public int numberGroups() {
        return groups != null ? groups.size() : 0;
    }

    public int numberLights() {
        return lights != null ? lights.size() : 0;
    }

    // public boolean removeAlarm(String id) {
    //     if (alarms == null) return false;
    //     return alarms.remove(id) != null;
    // }

    public void removeLight(String lightId) {
        if (lightId == null) return;
        DataGroup group = getGroupFromLightId(lightId);
        if (group != null && group.lightsOrder != null) {
            group.lightsOrder.remove(lightId);
        }
        if (lights != null) {
            lights.remove(lightId);
        }
    }

    public boolean updateLightName(String id, String name) {
        if (id == null || name == null) return false;
        DataLight light = getLightData(id);
        if (light == null || name.equals(light.name)) return false;
        light.name = name;
        return true;
    }

    // public boolean addAlarm(DataAlarm dataAlarm) {
    //     if (dataAlarm == null) return false;
    //     DataAlarm alarm = getAlarm(dataAlarm.id);
    //     if (alarm != null) {
    //         alarm.name = dataAlarm.name;
    //         alarm.activeDays = dataAlarm.activeDays;
    //         alarm.timerEnabled = dataAlarm.timerEnabled;
    //         alarm.startTime = dataAlarm.startTime;
    //         alarm.lights = dataAlarm.lights;
    //         alarm.canMessages = dataAlarm.canMessages;
    //         return true;
    //     }
    //     if (alarms != null && alarms.size() >= MAX_NO_OF_ALARMS) {
    //         return false;
    //     }
    //     if (alarms != null) {
    //         alarms.put(dataAlarm.id, dataAlarm);
    //     }
    //     if (alarmsOrder != null) {
    //         alarmsOrder.add(dataAlarm.id);
    //     }
    //     return true;
    // }

    // --- End ported functions ---
}