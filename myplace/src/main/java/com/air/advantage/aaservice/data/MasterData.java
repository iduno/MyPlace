package com.air.advantage.aaservice.data;

import com.google.gson.annotations.SerializedName;
import java.util.TreeMap;

/* compiled from: MasterData.java */
/* renamed from: com.air.advantage.aaservice.o.n */
/* loaded from: classes.dex */
public class MasterData {

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
}