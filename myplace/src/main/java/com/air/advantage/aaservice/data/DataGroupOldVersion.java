package com.air.advantage.aaservice.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/* compiled from: DataGroupOldVersion.java */
/* renamed from: com.air.advantage.aaservice.o.e */
/* loaded from: classes.dex */
public class DataGroupOldVersion extends EnumOnOff {

    /* renamed from: a */
    @SerializedName("lights")
    public final ArrayList<DataLightOldVersion> lights = new ArrayList<>();

    /* renamed from: b */
    @SerializedName("id")
    public String id = "";

    /* renamed from: c */
    @SerializedName("name")
    public String name = "";
}