package com.air.advantage.aaservice.data;

import java.util.ArrayList;
import java.util.TreeMap;

import com.google.gson.annotations.SerializedName;

/* compiled from: DataLightsAll.java */
/* renamed from: com.air.advantage.aaservice.o.h */
/* loaded from: classes.dex */
public class DataLightsAll {

    /* renamed from: a */
    @SerializedName("lights")
    public TreeMap<String, DataLight> lights = new TreeMap<>();

    /* renamed from: b */
    @SerializedName("groups")
    public TreeMap<String, DataGroup> groups;

    /* renamed from: c */
    @SerializedName("groupsOrder")
    public ArrayList<String> groupsOrder;

    /* renamed from: d */
    @SerializedName("scenes")
    public TreeMap<String, DataScene> scenes;

    /* renamed from: e */
    @SerializedName("scenesOrder")
    public ArrayList<String> scenesOrder;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public DataLightsAll() {
        new ArrayList();
        this.groups = new TreeMap<>();
        this.groupsOrder = new ArrayList<>();
        this.scenes = new TreeMap<>();
        this.scenesOrder = new ArrayList<>();
    }

    public void copyFrom(DataLightsAll other) {
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
    }
}