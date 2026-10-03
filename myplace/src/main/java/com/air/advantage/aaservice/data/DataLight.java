package com.air.advantage.aaservice.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
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
    @JsonView({JsonExporterViews.Export.class})
    public Integer value;


    @JsonProperty("moduleType")
    @JsonView({JsonExporterViews.Export.class})
    public ModuleType moduleType;


    @JsonProperty("deviceType")
    @JsonView({JsonExporterViews.Export.class})
    public String deviceType;

    @JsonProperty("type")
    @JsonView({JsonExporterViews.Export.class})
    public Type type;

    @JsonProperty("reachable")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean reachable;


    @JsonProperty("relay")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean relay;


    @JsonProperty("state")
    @JsonView({JsonExporterViews.Export.class})
    public LightState state;


    @JsonProperty("thisIsRFDevice")
    @JsonView({JsonExporterViews.Export.class})
    public Boolean thisIsRFDevice;


    @JsonProperty("dimOffset")
    @JsonView({JsonExporterViews.Export.class})
    public Integer dimOffset;

    @JsonProperty("lowBattery")
    @JsonView(JsonExporterViews.Export.class)
    public Boolean lowBattery;

    @JsonProperty("calibrated")
    @JsonView(JsonExporterViews.Export.class)
    public Boolean calibrated;

    @JsonProperty("poll")
    @JsonView(JsonExporterViews.Export.class)
    public Boolean poll;

    public enum LightState {
        off(0),
        on(1);

        private final int value;

        LightState(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum ModuleType {
        DM("DM"),
        HUE("HUE"),
        LMRM("LM/RM"),
        LM("LM"),
        RM("RM"),
        RM2("RM2"),
        GDM("GDM");

        private final String value;

        ModuleType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum Type {
        GROUP(1),
        LIGHT(2),
        RELAY(3),
        FAVOURITE_GROUP(4),
        FAVOURITE_LIGHT(5),
        FAVOURITE_RELAY(6);

        private final int value;

        Type(int value) {
            this.value = value;
        }

        @JsonValue
        public int getValue() {
            return this.value;
        }
    }

    public void copyFrom(DataLight other) {
        if (other == null) return;
        if (other.id != null) this.id = other.id;
        if (other.name != null) this.name = other.name;
        if (other.nextPollTime != null) this.nextPollTime = other.nextPollTime;
        if (other.value != null) this.value = other.value;
        if (other.moduleType != null) this.moduleType = other.moduleType;
        if (other.deviceType != null) this.deviceType = other.deviceType;
        if (other.reachable != null) this.reachable = other.reachable;
        if (other.thisIsRFDevice != null) this.thisIsRFDevice = other.thisIsRFDevice;
        if (other.dimOffset != null) this.dimOffset = other.dimOffset;
        if (other.state != null) this.state = other.state;
        if (other.lowBattery != null) this.lowBattery = other.lowBattery;
        if (other.calibrated != null) this.calibrated = other.calibrated;
        if (other.poll != null) this.poll = other.poll;
        if (other.type != null) this.type = other.type;
        if (other.relay != null) this.relay = other.relay;
    }
}