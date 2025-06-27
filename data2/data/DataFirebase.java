package com.air.advantage.data;

import androidx.browser.customtabs.CustomTabsCallback;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.i1 */
/* loaded from: classes.dex */
public final class DataFirebase {

    @Nullable
    @SerializedName("myView")
    private DataMyView myView;

    @Nullable
    @SerializedName(CustomTabsCallback.ONLINE_EXTRAS_KEY)
    private Boolean online;

    @SerializedName("aircons")
    @NotNull
    private final HashMap<String, Aircon> aircons = new HashMap<>();

    @SerializedName("snapshots")
    @NotNull
    private final HashMap<String, FirebaseSnapshot> snapshots = new HashMap<>();

    @SerializedName("system")
    @NotNull
    private final DataSystem system = new DataSystem();

    @SerializedName("myLights")
    @NotNull
    private DataLightsAllImport myLights = new DataLightsAllImport();

    @SerializedName("myMonitors")
    @NotNull
    private DataMonitorsAllImport myMonitors = new DataMonitorsAllImport();

    @SerializedName("myScenes")
    @NotNull
    private DataScenesAllImport myScenes = new DataScenesAllImport();

    @SerializedName("myThings")
    @NotNull
    private DataThingsAllImport myThings = new DataThingsAllImport();

    @SerializedName("mySensors")
    @NotNull
    private DataSensorsAllImport mySensors = new DataSensorsAllImport();

    @SerializedName("myAddOns")
    @NotNull
    private DataAddOnsAllImport myAddOns = new DataAddOnsAllImport();

    @NotNull
    public final HashMap<String, Aircon> getAircons() {
        return this.aircons;
    }

    @NotNull
    public final DataMaster getMasterData() {
        DataMaster dataMaster = new DataMaster();
        Iterator<String> it = this.aircons.keySet().iterator();
        while (true) {
            DataAirconSystem dataAirconSystem = null;
            if (!it.hasNext()) {
                break;
            }
            String next = it.next();
            Aircon aircon = this.aircons.get(next);
            if (aircon != null) {
                dataAirconSystem = aircon.getDataAircon();
            }
            dataMaster.aircons.put(next, dataAirconSystem);
        }
        dataMaster.setOnline(this.online);
        for (String str : this.snapshots.keySet()) {
            FirebaseSnapshot firebaseSnapshot = this.snapshots.get(str);
            dataMaster.snapshots.put(str, firebaseSnapshot != null ? firebaseSnapshot.getSnapshot() : null);
        }
        dataMaster.system.update(this.system);
        dataMaster.myLights = this.myLights.getDataLightsAll();
        dataMaster.myMonitors = this.myMonitors.getDataMonitorAll();
        dataMaster.myScenes = this.myScenes.getDataScenesAll();
        dataMaster.myThings = this.myThings.getDataThingsAll();
        dataMaster.mySensors = this.mySensors;
        dataMaster.myAddOns = this.myAddOns;
        dataMaster.myView = this.myView;
        return dataMaster;
    }

    @NotNull
    public final DataAddOnsAllImport getMyAddOns() {
        return this.myAddOns;
    }

    @NotNull
    public final DataLightsAllImport getMyLights() {
        return this.myLights;
    }

    @NotNull
    public final DataMonitorsAllImport getMyMonitors() {
        return this.myMonitors;
    }

    @NotNull
    public final DataScenesAllImport getMyScenes() {
        return this.myScenes;
    }

    @NotNull
    public final DataSensorsAllImport getMySensors() {
        return this.mySensors;
    }

    @NotNull
    public final DataThingsAllImport getMyThings() {
        return this.myThings;
    }

    @Nullable
    public final DataMyView getMyView() {
        return this.myView;
    }

    @Nullable
    public final Boolean getOnline() {
        return this.online;
    }

    @NotNull
    public final HashMap<String, FirebaseSnapshot> getSnapshots() {
        return this.snapshots;
    }

    @NotNull
    public final DataSystem getSystem() {
        return this.system;
    }

    public final void setMyAddOns(@NotNull DataAddOnsAllImport dataAddOnsAllImport) {
        Intrinsics.checkNotNullParameter(dataAddOnsAllImport, "<set-?>");
        this.myAddOns = dataAddOnsAllImport;
    }

    public final void setMyLights(@NotNull DataLightsAllImport dataLightsAllImport) {
        Intrinsics.checkNotNullParameter(dataLightsAllImport, "<set-?>");
        this.myLights = dataLightsAllImport;
    }

    public final void setMyMonitors(@NotNull DataMonitorsAllImport dataMonitorsAllImport) {
        Intrinsics.checkNotNullParameter(dataMonitorsAllImport, "<set-?>");
        this.myMonitors = dataMonitorsAllImport;
    }

    public final void setMyScenes(@NotNull DataScenesAllImport dataScenesAllImport) {
        Intrinsics.checkNotNullParameter(dataScenesAllImport, "<set-?>");
        this.myScenes = dataScenesAllImport;
    }

    public final void setMySensors(@NotNull DataSensorsAllImport dataSensorsAllImport) {
        Intrinsics.checkNotNullParameter(dataSensorsAllImport, "<set-?>");
        this.mySensors = dataSensorsAllImport;
    }

    public final void setMyThings(@NotNull DataThingsAllImport dataThingsAllImport) {
        Intrinsics.checkNotNullParameter(dataThingsAllImport, "<set-?>");
        this.myThings = dataThingsAllImport;
    }

    public final void setMyView(@Nullable DataMyView dataMyView) {
        this.myView = dataMyView;
    }

    public final void setOnline(@Nullable Boolean bool) {
        this.online = bool;
    }
}