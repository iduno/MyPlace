package com.air.advantage.aaservice.data;

import java.util.TreeMap;

import com.google.gson.annotations.SerializedName;

import jakarta.annotation.Nullable;

/* compiled from: MasterData.java */
/* renamed from: com.air.advantage.aaservice.o.n */
/* loaded from: classes.dex */
public class MasterData {

    @Nullable
    @SerializedName("online")
    private Boolean online;

    /* renamed from: a */
    @SerializedName("aircons")
    public final TreeMap<String, DataAircon> aircons = new TreeMap<>();

    /* renamed from: b */
    @SerializedName("snapshots")
    public TreeMap<String, SnapShot> snapshots = new TreeMap<>(new SnapshotComparator());

    /* renamed from: c */
    @SerializedName("system")
    public DataSystem system = new DataSystem();

    /* renamed from: d */
    @SerializedName("myLights")
    public DataLightsAll myLights = new DataLightsAll();

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