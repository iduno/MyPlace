package com.air.advantage.aaservice.data;

import java.util.HashMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    public void copyFrom(DataScene other) {
        if (other == null) return;
        if (other.id != null) this.id = other.id;
        if (other.lights != null) {
            if (this.lights == null) this.lights = new java.util.HashMap<>();
            for (String key : other.lights.keySet()) {
                DataLight light = other.lights.get(key);
                if (light != null) {
                    DataLight newLight = this.lights.get(key);
                    if (newLight != null) {
                        newLight.copyFrom(light);
                    } else {
                        newLight = new DataLight();
                        newLight.copyFrom(light);
                    }
                    this.lights.put(key, newLight);
                }
            }
        }
        if (other.canMessages != null) this.canMessages = other.canMessages;
    }
}