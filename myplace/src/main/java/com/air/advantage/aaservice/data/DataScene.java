package com.air.advantage.aaservice.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;

/* compiled from: DataScene.java */
/* renamed from: com.air.advantage.aaservice.o.j */
/* loaded from: classes.dex */
public class DataScene {

    /* renamed from: a */
    @SerializedName("id")
    public String id;

    /* renamed from: b */
    @SerializedName("lights")
    public HashMap<String, DataLight> lights;

    /* renamed from: c */
    @SerializedName("canMessages")
    @Expose(serialize = false)
    public String canMessages;
}