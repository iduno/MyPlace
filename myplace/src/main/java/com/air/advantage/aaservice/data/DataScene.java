package com.air.advantage.aaservice.data;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;


/* compiled from: DataScene.java */
/* renamed from: com.air.advantage.aaservice.o.j */
/* loaded from: classes.dex */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataScene {


    // --- FIELDS COPIED FROM REFERENCE DataScene.java ---
    public static final int MAXIMUM_START_AND_STOP_TIME_VALUE = 1440;


    public Integer activeDays;


    public Integer airconStopTime;


    public Boolean airconStopTimeEnabled;


    // public HashMap<String, DataAirconSystem> aircons;


    public String canMessages;


    public String id;


    public HashMap<String, DataLight> lights;


    // public HashMap<String, DataMonitor> monitors;


    public Boolean myTimeEnabled;


    public String name;


    public Boolean runNow;


    // public HashMap<String, Sonos> sonos;


    public Integer startTime;


    public String summary;


    // public HashMap<String, DataMyThing> things;


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