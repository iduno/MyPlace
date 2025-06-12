package com.air.advantage.aaservice.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.TreeMap;

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
}