package com.air.advantage.aaservice.data;

import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.annotations.SerializedName;

/* compiled from: SnapShot.java */
/* renamed from: com.air.advantage.aaservice.o.p */
/* loaded from: classes.dex */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SnapShot {

    @SerializedName("name")
    @JsonProperty("name")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String name;

    @SerializedName("enabled")
    @JsonProperty("enabled")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean enabled;

    @SerializedName("snapshotId")
    @JsonProperty("snapshotId")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String snapshotId;

    @SerializedName("activeDays")
    @JsonProperty("activeDays")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer activeDays;

    @SerializedName("startTime")
    @JsonProperty("startTime")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer startTime;

    @SerializedName("stopTime")
    @JsonProperty("stopTime")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer stopTime;

    @SerializedName("aircons")
    @JsonProperty("aircons")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public TreeMap<String, DataAircon> aircons = new TreeMap<>();
    
    @SerializedName("lights")
    @JsonProperty("lights")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public TreeMap<String, DataLight> lights;
    
    @SerializedName("nextStartTime")
    @JsonProperty("nextStartTime")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Long nextStartTime;
    
    @SerializedName("nextStopTime")
    @JsonProperty("nextStopTime")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Long nextStopTime;
    
    @SerializedName("isRunning")
    @JsonProperty("isRunning")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Boolean isRunning;
    
    @SerializedName("type")
    @JsonProperty("type")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
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