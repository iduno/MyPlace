package com.air.advantage.aaservice.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataModuleInfoSource {

    @JsonProperty("firmwareVersion")
    @JsonView({JsonExporterViews.Export.class})
    public String firmwareVersion;

    @JsonProperty("moduleType")
    @JsonView({JsonExporterViews.Export.class})
    public String moduleType;

    public void copyFrom(DataModuleInfoSource other) {
        if (other == null) return;
        if (other.firmwareVersion != null) this.firmwareVersion = other.firmwareVersion;
        if (other.moduleType != null) this.moduleType = other.moduleType;
    }
}