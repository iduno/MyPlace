package com.air.advantage.data;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.TreeMap;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.p1 */
/* loaded from: classes.dex */
public final class FirebaseSnapshot {

    @Nullable
    @SerializedName("activeDays")
    private Integer activeDays;

    @SerializedName("aircons")
    @NotNull
    private HashMap<String, Aircon> aircons = new HashMap<>();

    @Nullable
    @SerializedName("delete")
    private Boolean delete;

    @Nullable
    @SerializedName("enabled")
    private Boolean enabled;

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;

    @Nullable
    @SerializedName("runNow")
    private Boolean runNow;

    @Nullable
    @SerializedName("snapshotId")
    private String snapshotId;

    @Nullable
    @SerializedName("startTime")
    private Integer startTime;

    @Nullable
    @SerializedName("stopTime")
    private Integer stopTime;

    @Nullable
    @SerializedName("summary")
    private String summary;

    @Nullable
    public final Integer getActiveDays() {
        return this.activeDays;
    }

    @NotNull
    public final HashMap<String, Aircon> getAircons() {
        return this.aircons;
    }

    @Nullable
    public final Boolean getDelete() {
        return this.delete;
    }

    @Nullable
    public final Boolean getEnabled() {
        return this.enabled;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final Boolean getRunNow() {
        return this.runNow;
    }

    @NotNull
    public final SnapShot getSnapshot() {
        SnapShot snapShot = new SnapShot();
        snapShot.name = this.name;
        snapShot.enabled = this.enabled;
        snapShot.snapshotId = this.snapshotId;
        snapShot.activeDays = this.activeDays;
        snapShot.startTime = this.startTime;
        snapShot.stopTime = this.stopTime;
        snapShot.runNow = this.runNow;
        snapShot.delete = this.delete;
        for (String str : this.aircons.keySet()) {
            Aircon aircon = this.aircons.get(str);
            DataAirconSystem dataAircon = aircon != null ? aircon.getDataAircon() : null;
            TreeMap<String, DataAirconSystem> treeMap = snapShot.aircons;
            Intrinsics.checkNotNull(treeMap);
            treeMap.put(str, dataAircon);
        }
        snapShot.summary = this.summary;
        return snapShot;
    }

    @Nullable
    public final String getSnapshotId() {
        return this.snapshotId;
    }

    @Nullable
    public final Integer getStartTime() {
        return this.startTime;
    }

    @Nullable
    public final Integer getStopTime() {
        return this.stopTime;
    }

    @Nullable
    public final String getSummary() {
        return this.summary;
    }

    public final void setActiveDays(@Nullable Integer num) {
        this.activeDays = num;
    }

    public final void setAircons(@NotNull HashMap<String, Aircon> hashMap) {
        Intrinsics.checkNotNullParameter(hashMap, "<set-?>");
        this.aircons = hashMap;
    }

    public final void setDelete(@Nullable Boolean bool) {
        this.delete = bool;
    }

    public final void setEnabled(@Nullable Boolean bool) {
        this.enabled = bool;
    }

    public final void setName(@Nullable String str) {
        this.name = str;
    }

    public final void setRunNow(@Nullable Boolean bool) {
        this.runNow = bool;
    }

    public final void setSnapshotId(@Nullable String str) {
        this.snapshotId = str;
    }

    public final void setStartTime(@Nullable Integer num) {
        this.startTime = num;
    }

    public final void setStopTime(@Nullable Integer num) {
        this.stopTime = num;
    }

    public final void setSummary(@Nullable String str) {
        this.summary = str;
    }
}