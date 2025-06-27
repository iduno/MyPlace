package com.air.advantage.aaservice.data;

import java.util.TreeMap;

import com.google.gson.annotations.SerializedName;

import jakarta.annotation.Nonnull;

public class DataAircon {

    @SerializedName("info")
    @Nonnull
    public final DataAirconInfo airconInfo = new DataAirconInfo();

    @SerializedName("zones")
    @Nonnull
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

    DataAircon() {
    }

    public static String getAirconState(int airconIndex) {
        return "ac" + airconIndex;
    }

    public static DataAircon create() {
        return new DataAircon();
    }

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