package com.air.advantage.aaservice.data;

import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.annotations.SerializedName;

import jakarta.annotation.Nullable;

/* compiled from: MasterData.java */
/* renamed from: com.air.advantage.aaservice.o.n */
/* loaded from: classes.dex */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MasterData {

    @Nullable
    @SerializedName("online")
    @JsonProperty("online")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    private Boolean online;

    /* renamed from: a */
    @SerializedName("aircons")
    @JsonProperty("aircons")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public final TreeMap<String, DataAircon> aircons = new TreeMap<>();

    /* renamed from: b */
    @SerializedName("snapshots")
    @JsonProperty("snapshots")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public TreeMap<String, SnapShot> snapshots = new TreeMap<>(new SnapshotComparator());

    /* renamed from: c */
    @SerializedName("system")
    @JsonProperty("system")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public DataSystem system = new DataSystem();

    /* renamed from: d */
    @SerializedName("myLights")
    @JsonProperty("myLights")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public DataLightsAll myLights = new DataLightsAll();

    public DataAircon getAirconByUID(String uid) {
        if (uid == null || uid.isEmpty()) return null;
        if (uid.equals("00000")) {
            return this.aircons.get("ac1");
        }
        for (String key : this.aircons.keySet()) {
            DataAircon aircon = this.aircons.get(key);
            if (aircon == null) continue;
            if (uid.equals(aircon.airconInfo.uid)) {
                return aircon;
            }
            
        }
        DataAircon aircon = DataAircon.create();
        aircon.airconInfo.uid = uid;
        int airconId = this.aircons.size() + 1; // Generate a new ID

        this.aircons.put("ac"+airconId, aircon);
        return aircon;
    }

    public void copyFrom(MasterData other) {
        if (other == null) return;
        this.online = other.online;
        // Deep copy aircons
        for (String key : other.aircons.keySet()) {
            DataAircon aircon = other.aircons.get(key);
            DataAircon existingAircon = this.aircons.get(key);
            if (aircon != null) {
                if (existingAircon != null) {
                    existingAircon.copyFrom(aircon);
                } else {
                    DataAircon newAircon = new DataAircon();
                    newAircon.copyFrom(aircon);
                    this.aircons.put(key, newAircon);
                }
            }
        }
        // Deep copy snapshots
        for (String key : other.snapshots.keySet()) {
            SnapShot snapshot = other.snapshots.get(key);
            SnapShot existingSnapshot = this.snapshots.get(key);
            if (snapshot != null) {
                if (existingSnapshot != null) {
                    existingSnapshot.copyFrom(snapshot);
                } else {
                    SnapShot newSnapshot = new SnapShot();
                    newSnapshot.copyFrom(snapshot);
                    this.snapshots.put(key, newSnapshot);
                }
            }
        }
        this.system.copyFrom(other.system); 
        this.myLights.copyFrom(other.myLights);

    }
}