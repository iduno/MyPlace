package com.air.advantage.aaservice.data;

import java.util.ArrayList;

import com.air.advantage.aaservice.data.DataLight.LightState;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.Nulls;


/* compiled from: DataGroup.java */
/* renamed from: com.air.advantage.aaservice.o.d */

/* loaded from: classes.dex */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataGroup {

    /* renamed from: a */

    @JsonProperty("lightsOrder")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public ArrayList<String> lightsOrder = new ArrayList<>();

    /* renamed from: b */

    @JsonProperty("id")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String id;

    /* renamed from: c */

    @JsonProperty("name")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String name;

    @JsonProperty("state")
    @JsonView({JsonExporterViews.Export.class})
    public LightState state;

    @JsonProperty("value")
    @JsonView({JsonExporterViews.Export.class})
    public Integer value;

    public void copyFrom(DataGroup other) {
        if (other == null) return;
        this.lightsOrder.clear();
        if (other.lightsOrder != null) this.lightsOrder.addAll(other.lightsOrder);
        if (other.id != null) this.id = other.id;
        if (other.name != null) this.name = other.name;
        if (other.state != null) this.state = other.state;
        if (other.value != null) this.value = other.value;
    }
}