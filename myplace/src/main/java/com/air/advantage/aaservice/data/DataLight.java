package com.air.advantage.aaservice.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;


/* compiled from: DataLight.java */
/* renamed from: com.air.advantage.aaservice.o.f */
/* loaded from: classes.dex */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataLight {
    public static final int LIGHT_MAX_VALUE = 100;
    public static final int LIGHT_STEP_SIZE = 10;
    public static final String MODULE_TYPE_STRING_DM = "DM";
    public static final String MODULE_TYPE_STRING_HUE = "HUE";

    /* renamed from: a */

    @JsonProperty("id")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String id;


    @JsonProperty("name")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String name;

    public transient Long nextPollTime;


    @JsonProperty("value")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public Integer value;


    @JsonProperty("moduleType")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String moduleType;


    @JsonProperty("deviceType")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String deviceType;



    @JsonProperty("reachable")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean reachable;


    @JsonProperty("relay")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean relay;


    @JsonProperty("state")
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public String state;


    @JsonProperty("thisIsRFDevice")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean thisIsRFDevice;


    @JsonProperty("dimOffset")
    @JsonView({JsonExporterViews.Export.class})
    public Integer dimOffset;

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