package com.air.advantage.aaservice.data;

import com.air.advantage.libraryairconlightjson.AirconMode;
import com.air.advantage.libraryairconlightjson.FanMode;
import com.air.advantage.libraryairconlightjson.SystemState;
import com.google.gson.annotations.SerializedName;

/* compiled from: DataAirconInfo.java */
/* renamed from: com.air.advantage.aaservice.o.c */
/* loaded from: classes.dex */
public class DataAirconInfo {

    /* renamed from: d */
    @SerializedName("myZone")
    public Integer myZone;

    /* renamed from: f */
    @SerializedName("setTemp")
    public Float setTemp;

    /* renamed from: a */
    @SerializedName("fan")
    public FanMode fan = null;

    /* renamed from: b */
    @SerializedName("freshAirStatus")
    public DataAircon.FreshAirStatus freshAirStatus = null;

    /* renamed from: c */
    @SerializedName("mode")
    public AirconMode mode = null;

    /* renamed from: e */
    @SerializedName("name")
    public String name = null;

    /* renamed from: g */
    @SerializedName("state")
    public SystemState state = null;

    /* renamed from: h */
    @SerializedName("uid")
    public String uid = null;
}