package com.air.advantage.aaservice.data;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/* compiled from: DataScene.java */
/* renamed from: com.air.advantage.aaservice.o.j */
/* loaded from: classes.dex */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataScene {


    // --- FIELDS COPIED FROM REFERENCE DataScene.java ---
    public static final int MAXIMUM_START_AND_STOP_TIME_VALUE = 1440;

    @SerializedName("activeDays")
    public Integer activeDays;

    @SerializedName("airconStopTime")
    public Integer airconStopTime;

    @SerializedName("airconStopTimeEnabled")
    public Boolean airconStopTimeEnabled;

    // @SerializedName("aircons")
    // public HashMap<String, DataAirconSystem> aircons;

    @SerializedName("canMessages")
    public String canMessages;

    @SerializedName("id")
    public String id;

    @SerializedName("lights")
    public HashMap<String, DataLight> lights;

    // @SerializedName("monitors")
    // public HashMap<String, DataMonitor> monitors;

    @SerializedName("myTimeEnabled")
    public Boolean myTimeEnabled;

    @SerializedName("name")
    public String name;

    @SerializedName("runNow")
    public Boolean runNow;

    // @SerializedName("sonos")
    // public HashMap<String, Sonos> sonos;

    @SerializedName("startTime")
    public Integer startTime;

    @SerializedName("summary")
    public String summary;

    // @SerializedName("things")
    // public HashMap<String, DataMyThing> things;

    @SerializedName("timerEnabled")
    public Boolean timerEnabled;

    public void copyFrom(DataScene other) {
        if (other == null) return;
        if (other.id != null) this.id = other.id;
        if (other.lights != null) {
            if (this.lights == null) this.lights = new java.util.HashMap<>();
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
        if (other.canMessages != null) this.canMessages = other.canMessages;
    }
}