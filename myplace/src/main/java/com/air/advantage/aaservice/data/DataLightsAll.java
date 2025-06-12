package com.air.advantage.aaservice.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.TreeMap;

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
}