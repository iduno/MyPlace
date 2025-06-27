package com.air.advantage.aaservice.data;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/* compiled from: DataGroup.java */
/* renamed from: com.air.advantage.aaservice.o.d */
/* loaded from: classes.dex */
public class DataGroup {

    /* renamed from: a */
    @SerializedName("lightsOrder")
    public final ArrayList<String> lightsOrder = new ArrayList<>();

    /* renamed from: b */
    @SerializedName("id")
    public String id;

    /* renamed from: c */
    @SerializedName("name")
    public String name;

    public void copyFrom(DataGroup other) {
        if (other == null) return;
        this.lightsOrder.clear();
        if (other.lightsOrder != null) this.lightsOrder.addAll(other.lightsOrder);
        if (other.id != null) this.id = other.id;
        if (other.name != null) this.name = other.name;
    }
}