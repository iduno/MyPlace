package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.z */
/* loaded from: classes.dex */
public final class Actions {

    @Nullable
    @SerializedName("aircons")
    private HashMap<String, Aircon> aircons;

    @Nullable
    @SerializedName("airconsEnabled")
    private Boolean airconsEnabled;

    @Nullable
    @SerializedName("autoActionEnabled")
    private Boolean autoActionEnabled;

    @Nullable
    @SerializedName("autoActionSummary")
    private String autoActionSummary;

    @Nullable
    @SerializedName("launchMyAppEnabled")
    private Boolean launchMyAppEnabled;

    @Nullable
    @SerializedName("launchMyAppName")
    private String launchMyAppName;

    @Nullable
    @SerializedName("launchMyAppPackageName")
    private String launchMyAppPackageName;

    @Nullable
    @SerializedName("lights")
    private HashMap<String, DataLight> lights;

    @Nullable
    @SerializedName("lightsEnabled")
    private Boolean lightsEnabled;

    @Nullable
    @SerializedName("notificationEnabled")
    private Boolean notificationEnabled;

    @Nullable
    @SerializedName("notificationPhoneNumber")
    private String notificationPhoneNumber;

    @Nullable
    @SerializedName("notificationPhoneNumberEnabled")
    private Boolean notificationPhoneNumberEnabled;

    @Nullable
    @SerializedName("things")
    private HashMap<String, DataMyThing> things;

    @Nullable
    @SerializedName("thingsEnabled")
    private Boolean thingsEnabled;

    @Nullable
    public final HashMap<String, Aircon> getAircons() {
        return this.aircons;
    }

    @Nullable
    public final Boolean getAirconsEnabled() {
        return this.airconsEnabled;
    }

    @Nullable
    public final Boolean getAutoActionEnabled() {
        return this.autoActionEnabled;
    }

    @Nullable
    public final String getAutoActionSummary() {
        return this.autoActionSummary;
    }

    @NotNull
    public final DataMonitorActions getDataMonitorActions() {
        DataMonitorActions dataMonitorActions = new DataMonitorActions();
        if (this.aircons != null) {
            dataMonitorActions.aircons = new HashMap<>();
            HashMap<String, Aircon> hashMap = this.aircons;
            Intrinsics.checkNotNull(hashMap);
            for (String str : hashMap.keySet()) {
                HashMap<String, Aircon> hashMap2 = this.aircons;
                Intrinsics.checkNotNull(hashMap2);
                Aircon aircon = hashMap2.get(str);
                if (aircon != null) {
                    HashMap<String, DataAirconSystem> hashMap3 = dataMonitorActions.aircons;
                    Intrinsics.checkNotNull(hashMap3);
                    hashMap3.put(str, aircon.getDataAircon());
                }
            }
        }
        dataMonitorActions.airconsEnabled = this.airconsEnabled;
        dataMonitorActions.autoActionEnabled = this.autoActionEnabled;
        dataMonitorActions.autoActionSummary = this.autoActionSummary;
        HashMap<String, DataLight> hashMap4 = this.lights;
        dataMonitorActions.lights = hashMap4;
        if (hashMap4 != null) {
            Intrinsics.checkNotNull(hashMap4);
            for (String str2 : hashMap4.keySet()) {
                HashMap<String, DataLight> hashMap5 = dataMonitorActions.lights;
                Intrinsics.checkNotNull(hashMap5);
                DataLight dataLight = hashMap5.get(str2);
                if (dataLight != null) {
                    dataLight.id = str2;
                }
            }
        }
        dataMonitorActions.lightsEnabled = this.lightsEnabled;
        dataMonitorActions.launchMyAppEnabled = this.launchMyAppEnabled;
        dataMonitorActions.launchMyAppName = this.launchMyAppName;
        dataMonitorActions.launchMyAppPackageName = this.launchMyAppPackageName;
        dataMonitorActions.notificationEnabled = this.notificationEnabled;
        dataMonitorActions.notificationPhoneNumberEnabled = this.notificationPhoneNumberEnabled;
        dataMonitorActions.notificationPhoneNumber = this.notificationPhoneNumber;
        HashMap<String, DataMyThing> hashMap6 = this.things;
        dataMonitorActions.things = hashMap6;
        if (hashMap6 != null) {
            Intrinsics.checkNotNull(hashMap6);
            for (String str3 : hashMap6.keySet()) {
                HashMap<String, DataMyThing> hashMap7 = dataMonitorActions.things;
                Intrinsics.checkNotNull(hashMap7);
                DataMyThing dataMyThing = hashMap7.get(str3);
                if (dataMyThing != null) {
                    dataMyThing.id = str3;
                }
            }
        }
        dataMonitorActions.thingsEnabled = this.thingsEnabled;
        return dataMonitorActions;
    }

    @Nullable
    public final Boolean getLaunchMyAppEnabled() {
        return this.launchMyAppEnabled;
    }

    @Nullable
    public final String getLaunchMyAppName() {
        return this.launchMyAppName;
    }

    @Nullable
    public final String getLaunchMyAppPackageName() {
        return this.launchMyAppPackageName;
    }

    @Nullable
    public final HashMap<String, DataLight> getLights() {
        return this.lights;
    }

    @Nullable
    public final Boolean getLightsEnabled() {
        return this.lightsEnabled;
    }

    @Nullable
    public final Boolean getNotificationEnabled() {
        return this.notificationEnabled;
    }

    @Nullable
    public final String getNotificationPhoneNumber() {
        return this.notificationPhoneNumber;
    }

    @Nullable
    public final Boolean getNotificationPhoneNumberEnabled() {
        return this.notificationPhoneNumberEnabled;
    }

    @Nullable
    public final HashMap<String, DataMyThing> getThings() {
        return this.things;
    }

    @Nullable
    public final Boolean getThingsEnabled() {
        return this.thingsEnabled;
    }

    public final void setAircons(@Nullable HashMap<String, Aircon> hashMap) {
        this.aircons = hashMap;
    }

    public final void setAirconsEnabled(@Nullable Boolean bool) {
        this.airconsEnabled = bool;
    }

    public final void setAutoActionEnabled(@Nullable Boolean bool) {
        this.autoActionEnabled = bool;
    }

    public final void setAutoActionSummary(@Nullable String str) {
        this.autoActionSummary = str;
    }

    public final void setLaunchMyAppEnabled(@Nullable Boolean bool) {
        this.launchMyAppEnabled = bool;
    }

    public final void setLaunchMyAppName(@Nullable String str) {
        this.launchMyAppName = str;
    }

    public final void setLaunchMyAppPackageName(@Nullable String str) {
        this.launchMyAppPackageName = str;
    }

    public final void setLights(@Nullable HashMap<String, DataLight> hashMap) {
        this.lights = hashMap;
    }

    public final void setLightsEnabled(@Nullable Boolean bool) {
        this.lightsEnabled = bool;
    }

    public final void setNotificationEnabled(@Nullable Boolean bool) {
        this.notificationEnabled = bool;
    }

    public final void setNotificationPhoneNumber(@Nullable String str) {
        this.notificationPhoneNumber = str;
    }

    public final void setNotificationPhoneNumberEnabled(@Nullable Boolean bool) {
        this.notificationPhoneNumberEnabled = bool;
    }

    public final void setThings(@Nullable HashMap<String, DataMyThing> hashMap) {
        this.things = hashMap;
    }

    public final void setThingsEnabled(@Nullable Boolean bool) {
        this.thingsEnabled = bool;
    }
}