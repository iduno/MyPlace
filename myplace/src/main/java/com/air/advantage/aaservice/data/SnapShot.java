package com.air.advantage.aaservice.data;

import java.util.TreeMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/* compiled from: SnapShot.java */
/* renamed from: com.air.advantage.aaservice.o.p */
/* loaded from: classes.dex */
public class SnapShot {

    /* renamed from: a */
    @SerializedName("name")
    public String name;

    /* renamed from: b */
    @SerializedName("enabled")
    public Boolean enabled;

    /* renamed from: c */
    @SerializedName("snapshotId")
    @Expose(serialize = false)
    public String snapshotId;

    /* renamed from: d */
    @SerializedName("activeDays")
    public Integer activeDays;

    /* renamed from: e */
    @SerializedName("startTime")
    public Integer startTime;

    /* renamed from: f */
    @SerializedName("stopTime")
    public Integer stopTime;

    /* renamed from: g */
    @SerializedName("aircons")
    public TreeMap<String, DataAircon> aircons = new TreeMap<>();
    
    @SerializedName("lights")
    public TreeMap<String, DataLight> lights;
    
    @SerializedName("nextStartTime")
    public Long nextStartTime;
    
    @SerializedName("nextStopTime")
    public Long nextStopTime;
    
    @SerializedName("isRunning")
    public Boolean isRunning;
    
    @SerializedName("type")
    public Integer type;
    
    public SnapShot() {
        this.lights = new TreeMap<>();
        this.isRunning = false;
    }

    public void copyFrom(SnapShot other) {
        if (other == null) return;
        if (other.name != null) this.name = other.name;
        if (other.enabled != null) this.enabled = other.enabled;
        if (other.snapshotId != null) this.snapshotId = other.snapshotId;
        if (other.activeDays != null) this.activeDays = other.activeDays;
        if (other.startTime != null) this.startTime = other.startTime;
        if (other.stopTime != null) this.stopTime = other.stopTime;
        if (other.aircons != null) {
            if (this.aircons == null) this.aircons = new TreeMap<>();
            for (String key : other.aircons.keySet()) {
                DataAircon aircon = other.aircons.get(key);
                if (aircon != null) {
                    DataAircon newAircon = this.aircons.get(key);
                    if (newAircon != null) {
                        newAircon.copyFrom(aircon);
                    } else {
                        newAircon = new DataAircon();
                        newAircon.copyFrom(aircon);
                    }
                    this.aircons.put(key, newAircon);
                }
            }
        }
        if (other.lights != null) {
            if (this.lights == null) this.lights = new TreeMap<>();
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
        if (other.nextStartTime != null) this.nextStartTime = other.nextStartTime;
        if (other.nextStopTime != null) this.nextStopTime = other.nextStopTime;
        if (other.isRunning != null) this.isRunning = other.isRunning;
        if (other.type != null) this.type = other.type;
    }
}