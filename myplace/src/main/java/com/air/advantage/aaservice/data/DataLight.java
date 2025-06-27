package com.air.advantage.aaservice.data;

import com.google.gson.annotations.SerializedName;

/* compiled from: DataLight.java */
/* renamed from: com.air.advantage.aaservice.o.f */
/* loaded from: classes.dex */
public class DataLight {
    public static final int LIGHT_MAX_VALUE = 100;
    public static final int LIGHT_STEP_SIZE = 10;
    public static final String MODULE_TYPE_STRING_DM = "DM";
    public static final String MODULE_TYPE_STRING_HUE = "HUE";

    /* renamed from: a */
    @SerializedName("id")
    public String id;

    /* renamed from: b */
    @SerializedName("name")
    public String name;

    /* renamed from: c */
    public transient Long nextPollTime;
    
    @SerializedName("value")
    public Integer value;
    
    @SerializedName("moduleType")
    public String moduleType;
    
    @SerializedName("deviceType")
    public String deviceType;
    
    @SerializedName("state")
    public String state;

    public void copyFrom(DataLight other) {
        if (other == null) return;
        if (other.id != null) this.id = other.id;
        if (other.name != null) this.name = other.name;
        if (other.nextPollTime != null) this.nextPollTime = other.nextPollTime;
        if (other.value != null) this.value = other.value;
        if (other.moduleType != null) this.moduleType = other.moduleType;
        if (other.deviceType != null) this.deviceType = other.deviceType;
        if (other.state != null) this.state = other.state;
    }
}