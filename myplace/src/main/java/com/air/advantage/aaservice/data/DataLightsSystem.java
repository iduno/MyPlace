package com.air.advantage.aaservice.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataLightsSystem {
    
    @JsonProperty("lastUsedLightId")
    @JsonView({JsonExporterViews.Export.class})
    public String lastUsedLightId;

    @JsonProperty("numberClicks")
    @JsonView({JsonExporterViews.Export.class})
    public Long numberClicks;

    @JsonProperty("sunsetTime")
    @JsonView({JsonExporterViews.Export.class})
    public String sunsetTime;

    public void copyFrom(DataLightsSystem other) {
        if (other == null) return;
        this.lastUsedLightId = other.lastUsedLightId;
        this.numberClicks = other.numberClicks;
        this.sunsetTime = other.sunsetTime;
    }
}
