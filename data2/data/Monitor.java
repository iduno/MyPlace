package com.air.advantage.data;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.d0 */
/* loaded from: classes.dex */
public final class Monitor {

    @Nullable
    @SerializedName("activeDays")
    private Integer activeDays;

    @Nullable
    @SerializedName("endTime")
    private Integer endTime;

    @Nullable
    @SerializedName("id")
    private String id;

    @Nullable
    @SerializedName("monitorEnabled")
    private Boolean monitorEnabled;

    @Nullable
    @SerializedName("monitorSummary")
    private String monitorSummary;

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;

    @Nullable
    @SerializedName("startTime")
    private Integer startTime;

    @SerializedName("actions")
    @NotNull
    private final Actions actions = new Actions();

    @SerializedName("events")
    @NotNull
    private final Events events = new Events();

    @NotNull
    public final Actions getActions() {
        return this.actions;
    }

    @Nullable
    public final Integer getActiveDays() {
        return this.activeDays;
    }

    @NotNull
    public final DataMonitor getDataMonitor() {
        DataMonitor dataMonitor = new DataMonitor();
        Actions actions = this.actions;
        if (actions != null) {
            DataMonitorActions.update$default(dataMonitor.actions, actions.getDataMonitorActions(), null, false, 4, null);
        }
        dataMonitor.activeDays = this.activeDays;
        dataMonitor.endTime = this.endTime;
        Events events = this.events;
        if (events != null) {
            Events.update$default(dataMonitor.events, events, null, false, 4, null);
        }
        dataMonitor.id = this.id;
        dataMonitor.name = this.name;
        dataMonitor.monitorEnabled = this.monitorEnabled;
        dataMonitor.monitorSummary = this.monitorSummary;
        dataMonitor.startTime = this.startTime;
        return dataMonitor;
    }

    @Nullable
    public final Integer getEndTime() {
        return this.endTime;
    }

    @NotNull
    public final Events getEvents() {
        return this.events;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final Boolean getMonitorEnabled() {
        return this.monitorEnabled;
    }

    @Nullable
    public final String getMonitorSummary() {
        return this.monitorSummary;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final Integer getStartTime() {
        return this.startTime;
    }

    public final void setActiveDays(@Nullable Integer num) {
        this.activeDays = num;
    }

    public final void setEndTime(@Nullable Integer num) {
        this.endTime = num;
    }

    public final void setId(@Nullable String str) {
        this.id = str;
    }

    public final void setMonitorEnabled(@Nullable Boolean bool) {
        this.monitorEnabled = bool;
    }

    public final void setMonitorSummary(@Nullable String str) {
        this.monitorSummary = str;
    }

    public final void setName(@Nullable String str) {
        this.name = str;
    }

    public final void setStartTime(@Nullable Integer num) {
        this.startTime = num;
    }
}