package com.air.advantage.data;

import com.air.advantage.ActivityMain;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.e0 */
/* loaded from: classes.dex */
public final class DataGroupSource {

    @Exclude
    @JvmField
    public transient long expiryTime;

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    private String name;

    @Nullable
    @SerializedName("sensor1IsPaired")
    private Boolean sensor1IsPaired;

    @Nullable
    @SerializedName("sensor2IsPaired")
    private Boolean sensor2IsPaired;

    @Nullable
    @SerializedName(ActivityMain.UID)
    @JvmField
    public String uid;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public DataGroupSource() {
    }

    public static /* synthetic */ boolean update$default(DataGroupSource dataGroupSource, DataGroupSource dataGroupSource2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 4) != 0) {
            z7 = false;
        }
        return dataGroupSource.update(dataGroupSource2, dataManager, z7);
    }

    public final void clearDataForBackup() {
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final Boolean getSensor1IsPaired() {
        return this.sensor1IsPaired;
    }

    @Nullable
    public final Boolean getSensor2IsPaired() {
        return this.sensor2IsPaired;
    }

    public final void sanitiseData() {
        this.uid = null;
    }

    public final void setName(@Nullable String str) {
        this.name = str;
    }

    public final void setSensor1IsPaired(@Nullable Boolean bool) {
        this.sensor1IsPaired = bool;
    }

    public final void setSensor2IsPaired(@Nullable Boolean bool) {
        this.sensor2IsPaired = bool;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@NotNull DataGroupSource dataGroupSource, @Nullable DataManager dataManager) {
        Intrinsics.checkNotNullParameter(dataGroupSource, "dataGroupSource");
        return update$default(this, dataGroupSource, dataManager, false, 4, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public DataGroupSource(@Nullable String str) {
        this.uid = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    @JvmOverloads
    public final boolean update(@NotNull DataGroupSource dataGroupSource, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        Intrinsics.checkNotNullParameter(dataGroupSource, "dataGroupSource");
        String str = dataGroupSource.uid;
        if (str != null) {
            String str2 = this.uid;
            if (str2 == null || !Intrinsics.areEqual(str2, str)) {
                this.uid = dataGroupSource.uid;
                if (dataManager != null) {
                    dataManager.add(ActivityMain.UID, dataGroupSource.uid);
                }
                z10 = true;
            }
            z10 = false;
        } else {
            if (z7 && this.uid != null) {
                if (dataManager != null) {
                    dataManager.add(ActivityMain.UID, null);
                }
                z10 = true;
            }
            z10 = false;
        }
        String str3 = dataGroupSource.name;
        if (str3 != null) {
            String str4 = this.name;
            if (str4 == null || !Intrinsics.areEqual(str4, str3)) {
                this.name = dataGroupSource.name;
                if (dataManager != null) {
                    dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, dataGroupSource.name);
                }
                z10 = true;
            }
        } else if (z7 && this.name != null) {
            if (dataManager != null) {
                dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, null);
            }
            z10 = true;
        }
        Boolean bool = dataGroupSource.sensor1IsPaired;
        if (bool != null) {
            Boolean bool2 = this.sensor1IsPaired;
            if (bool2 == null || !Intrinsics.areEqual(bool2, bool)) {
                this.sensor1IsPaired = dataGroupSource.sensor1IsPaired;
                if (dataManager != null) {
                    dataManager.add("sensor1IsPaired", dataGroupSource.sensor1IsPaired);
                }
                z10 = true;
            }
        } else if (z7 && this.sensor1IsPaired != null) {
            if (dataManager != null) {
                dataManager.add("sensor1IsPaired", null);
            }
            z10 = true;
        }
        Boolean bool3 = dataGroupSource.sensor2IsPaired;
        if (bool3 != null) {
            Boolean bool4 = this.sensor2IsPaired;
            if (bool4 == null || !Intrinsics.areEqual(bool4, bool3)) {
                this.sensor2IsPaired = dataGroupSource.sensor2IsPaired;
                if (dataManager == null) {
                    return true;
                }
                dataManager.add("sensor2IsPaired", dataGroupSource.sensor2IsPaired);
                return true;
            }
        } else if (z7 && this.sensor2IsPaired != null) {
            if (dataManager == null) {
                return true;
            }
            dataManager.add("sensor2IsPaired", null);
            return true;
        }
        return z10;
    }
}