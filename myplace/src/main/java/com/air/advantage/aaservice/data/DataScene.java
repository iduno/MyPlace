package com.air.advantage.aaservice.data;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/* compiled from: DataScene.java */
/* renamed from: com.air.advantage.aaservice.o.j */
/* loaded from: classes.dex */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataScene {

    @SerializedName("id")
    @JsonProperty("id")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String id;

    @SerializedName("lights")
    @JsonProperty("lights")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public HashMap<String, DataLight> lights;

    @SerializedName("canMessages")
    @JsonProperty("canMessages")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
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