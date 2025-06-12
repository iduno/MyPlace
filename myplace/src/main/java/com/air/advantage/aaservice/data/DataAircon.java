package com.air.advantage.aaservice.data;

import java.util.TreeMap;

import com.google.gson.annotations.SerializedName;

public class DataAircon {

    @SerializedName("info")
    public final DataAirconInfo airconInfo = new DataAirconInfo();

    @SerializedName("zones")
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
}