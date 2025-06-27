package com.air.advantage.data;

import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.l0 */
/* loaded from: classes.dex */
public final class Scene {

    @Nullable
    @SerializedName("activeDays")
    private Integer activeDays;

    @Nullable
    @SerializedName("airconStopTime")
    private Integer airconStopTime;

    @Nullable
    @SerializedName("airconStopTimeEnabled")
    private Boolean airconStopTimeEnabled;

    @Nullable
    @SerializedName("aircons")
    private HashMap<String, Aircon> aircons;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("canMessages")
    private String canMessages;

    @Nullable
    @SerializedName("id")
    private String id;

    @Nullable
    @SerializedName("lights")
    private HashMap<String, DataLight> lights;

    @Nullable
    @SerializedName("monitors")
    private HashMap<String, DataMonitor> monitors;

    @Nullable
    @SerializedName("myTimeEnabled")
    private Boolean myTimeEnabled;

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;

    @Nullable
    @SerializedName("runNow")
    private Boolean runNow;

    @Nullable
    @SerializedName("startTime")
    private Integer startTime;

    @Nullable
    @SerializedName("summary")
    private String summary;

    @Nullable
    @SerializedName("things")
    private HashMap<String, DataMyThing> things;

    @Nullable
    @SerializedName("timerEnabled")
    private Boolean timerEnabled;

    @Nullable
    public final Integer getActiveDays() {
        return this.activeDays;
    }

    @Nullable
    public final Integer getAirconStopTime() {
        return this.airconStopTime;
    }

    @Nullable
    public final Boolean getAirconStopTimeEnabled() {
        return this.airconStopTimeEnabled;
    }

    @Nullable
    public final HashMap<String, Aircon> getAircons() {
        return this.aircons;
    }

    @Nullable
    public final String getCanMessages() {
        return this.canMessages;
    }

    @NotNull
    public final DataScene getDataScene() {
        DataScene dataScene = new DataScene();
        String str = this.id;
        if (str == null) {
            str = "";
        }
        dataScene.id = str;
        dataScene.name = this.name;
        dataScene.timerEnabled = this.timerEnabled;
        dataScene.activeDays = this.activeDays;
        dataScene.startTime = this.startTime;
        dataScene.airconStopTime = this.airconStopTime;
        dataScene.airconStopTimeEnabled = this.airconStopTimeEnabled;
        dataScene.runNow = this.runNow;
        HashMap<String, DataLight> hashMap = this.lights;
        dataScene.lights = hashMap;
        if (hashMap != null) {
            Intrinsics.checkNotNull(hashMap);
            for (String str2 : hashMap.keySet()) {
                HashMap<String, DataLight> hashMap2 = dataScene.lights;
                Intrinsics.checkNotNull(hashMap2);
                DataLight dataLight = hashMap2.get(str2);
                if (dataLight != null) {
                    dataLight.id = str2;
                }
            }
        } else {
            dataScene.lights = new HashMap<>();
        }
        HashMap<String, DataMyThing> hashMap3 = this.things;
        dataScene.things = hashMap3;
        if (hashMap3 != null) {
            Intrinsics.checkNotNull(hashMap3);
            for (String str3 : hashMap3.keySet()) {
                HashMap<String, DataMyThing> hashMap4 = dataScene.things;
                Intrinsics.checkNotNull(hashMap4);
                DataMyThing dataMyThing = hashMap4.get(str3);
                if (dataMyThing != null) {
                    dataMyThing.id = str3;
                }
            }
        } else {
            dataScene.things = new HashMap<>();
        }
        dataScene.aircons = new HashMap<>();
        HashMap<String, Aircon> hashMap5 = this.aircons;
        if (hashMap5 != null) {
            Intrinsics.checkNotNull(hashMap5);
            for (String str4 : hashMap5.keySet()) {
                HashMap<String, Aircon> hashMap6 = this.aircons;
                Intrinsics.checkNotNull(hashMap6);
                Aircon aircon = hashMap6.get(str4);
                if (aircon != null) {
                    HashMap<String, DataAirconSystem> hashMap7 = dataScene.aircons;
                    Intrinsics.checkNotNull(hashMap7);
                    hashMap7.put(str4, aircon.getDataAircon());
                }
            }
        }
        HashMap<String, DataMonitor> hashMap8 = this.monitors;
        dataScene.monitors = hashMap8;
        if (hashMap8 != null) {
            Intrinsics.checkNotNull(hashMap8);
            for (String str5 : hashMap8.keySet()) {
                HashMap<String, DataMonitor> hashMap9 = dataScene.monitors;
                Intrinsics.checkNotNull(hashMap9);
                DataMonitor dataMonitor = hashMap9.get(str5);
                if (dataMonitor != null) {
                    dataMonitor.id = str5;
                }
            }
        } else {
            dataScene.monitors = new HashMap<>();
        }
        dataScene.myTimeEnabled = this.myTimeEnabled;
        dataScene.summary = this.summary;
        return dataScene;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final HashMap<String, DataLight> getLights() {
        return this.lights;
    }

    @Nullable
    public final HashMap<String, DataMonitor> getMonitors() {
        return this.monitors;
    }

    @Nullable
    public final Boolean getMyTimeEnabled() {
        return this.myTimeEnabled;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final Boolean getRunNow() {
        return this.runNow;
    }

    @Nullable
    public final Integer getStartTime() {
        return this.startTime;
    }

    @Nullable
    public final String getSummary() {
        return this.summary;
    }

    @Nullable
    public final HashMap<String, DataMyThing> getThings() {
        return this.things;
    }

    @Nullable
    public final Boolean getTimerEnabled() {
        return this.timerEnabled;
    }

    public final void setActiveDays(@Nullable Integer num) {
        this.activeDays = num;
    }

    public final void setAirconStopTime(@Nullable Integer num) {
        this.airconStopTime = num;
    }

    public final void setAirconStopTimeEnabled(@Nullable Boolean bool) {
        this.airconStopTimeEnabled = bool;
    }

    public final void setAircons(@Nullable HashMap<String, Aircon> hashMap) {
        this.aircons = hashMap;
    }

    public final void setCanMessages(@Nullable String str) {
        this.canMessages = str;
    }

    public final void setId(@Nullable String str) {
        this.id = str;
    }

    public final void setLights(@Nullable HashMap<String, DataLight> hashMap) {
        this.lights = hashMap;
    }

    public final void setMonitors(@Nullable HashMap<String, DataMonitor> hashMap) {
        this.monitors = hashMap;
    }

    public final void setMyTimeEnabled(@Nullable Boolean bool) {
        this.myTimeEnabled = bool;
    }

    public final void setName(@Nullable String str) {
        this.name = str;
    }

    public final void setRunNow(@Nullable Boolean bool) {
        this.runNow = bool;
    }

    public final void setStartTime(@Nullable Integer num) {
        this.startTime = num;
    }

    public final void setSummary(@Nullable String str) {
        this.summary = str;
    }

    public final void setThings(@Nullable HashMap<String, DataMyThing> hashMap) {
        this.things = hashMap;
    }

    public final void setTimerEnabled(@Nullable Boolean bool) {
        this.timerEnabled = bool;
    }
}