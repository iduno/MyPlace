package com.air.advantage.aaservice.data;

import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.annotations.SerializedName;

import jakarta.annotation.Nonnull;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataAircon {

    @SerializedName("info")
    @JsonProperty("info")
    @Nonnull
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public final DataAirconInfo airconInfo = new DataAirconInfo();

    @SerializedName("zones")
    @JsonProperty("zones")
    @Nonnull
    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public final TreeMap<String, DataZone> zones = new TreeMap<>();

    public enum FreshAirStatus {
        none(0),
        off(1),
        on(2);

        private final int value;

        FreshAirStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum CodeStatus {
        noCode(0),
        codeEnabled(1),
        expired(2),
        sending(3);

        private final int value;

        CodeStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum ActivationCode {
        setNewCode(1),
        unlock(2);
        private final int value;
        ActivationCode(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    public enum FanStatus {
        off(0),
        low(1),
        medium(2),
        high(3),
        auto(4),
        autoAA(5);

        private int value;

        FanStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum SystemState {
        off(0),
        on(1);

        private final int value;

        SystemState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum AirconMode {
        cool(1),
        heat(2),
        vent(3),
        auto(4),
        dry(5),
        myauto(6);

        private final int value;

        AirconMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum ZoneState {
    close(0),
    open(1);

    private final int value;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    ZoneState(int i) {
        this.value = i;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public int getValue() {
        return this.value;
    }
}



    DataAircon() {
    }

    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public static String getAirconState(int airconIndex) {
        return "ac" + airconIndex;
    }

    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public static DataAircon create() {
        return new DataAircon();
    }

    @JsonView({JsonExporterViews.Export.class,JsonExporterViews.SaveThis.class})
    public TreeMap<String, DataZone> getZones() {
        return zones;
    }

    public void copyFrom(DataAircon other) {
        if (other == null) return;
        this.airconInfo.copyFrom(other.airconInfo);
        for (String key : other.zones.keySet()) {
            DataZone zone = other.zones.get(key);
            if (zone != null) {
                DataZone newZone = this.zones.get(key);
                if (newZone != null) {
                    newZone.copyFrom(zone);
                } else {
                    newZone = new DataZone();
                    newZone.copyFrom(zone);
                }
                this.zones.put(key, newZone);
            }
        }
    }
}