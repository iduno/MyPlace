package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.TreeMap;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* renamed from: com.air.advantage.data.d */
/* loaded from: classes.dex */
public final class Aircon {

    @SerializedName("info")
    @NotNull
    private final DataAirconInfo info = new DataAirconInfo();

    @SerializedName("zones")
    @NotNull
    private final HashMap zones = new HashMap();

    @NotNull
    public final DataAirconSystem getDataAircon() {
        DataAirconSystem dataAirconSystem = new DataAirconSystem();
        DataAirconInfo dataAirconInfo = this.info;
        if (dataAirconInfo.uid == null) {
            dataAirconInfo.uid = "dummy";
        }
        DataAirconInfo.update$default(dataAirconSystem.info, dataAirconInfo, null, false, 4, null);
        if (Intrinsics.areEqual(dataAirconSystem.info.uid, "dummy")) {
            dataAirconSystem.info.uid = null;
        }
        dataAirconSystem.zones = new TreeMap<>(this.zones);
        return dataAirconSystem;
    }

    @NotNull
    public final DataAirconInfo getInfo() {
        return this.info;
    }

    @NotNull
    public final HashMap getZones() {
        return this.zones;
    }
}