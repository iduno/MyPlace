package com.air.advantage.aaservice.data;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.annotations.SerializedName;

/* compiled from: DataGroup.java */
/* renamed from: com.air.advantage.aaservice.o.d */
/* loaded from: classes.dex */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataGroup {

    /* renamed from: a */
    @SerializedName("lightsOrder")
    @JsonProperty("lightsOrder")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public final ArrayList<String> lightsOrder = new ArrayList<>();

    /* renamed from: b */
    @SerializedName("id")
    @JsonProperty("id")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String id;

    /* renamed from: c */
    @SerializedName("name")
    @JsonProperty("name")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String name;

    public void copyFrom(DataGroup other) {
        if (other == null) return;
        this.lightsOrder.clear();
        if (other.lightsOrder != null) this.lightsOrder.addAll(other.lightsOrder);
        if (other.id != null) this.id = other.id;
        if (other.name != null) this.name = other.name;
    }
}