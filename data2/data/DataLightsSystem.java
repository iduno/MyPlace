package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.s */
/* loaded from: classes.dex */
public final class DataLightsSystem {

    @Nullable
    @SerializedName("lastUsedLightId")
    @JvmField
    public String lastUsedLightId;

    @Nullable
    @SerializedName("numberClicks")
    @JvmField
    public Long numberClicks;

    @Nullable
    @SerializedName("sunsetTime")
    @JvmField
    public String sunsetTime;

    public static /* synthetic */ boolean update$default(DataLightsSystem dataLightsSystem, DataLightsSystem dataLightsSystem2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 4) != 0) {
            z7 = false;
        }
        return dataLightsSystem.update(dataLightsSystem2, dataManager, z7);
    }

    public final void clearDataForBackup() {
        this.lastUsedLightId = null;
        this.numberClicks = null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@Nullable DataLightsSystem dataLightsSystem, @Nullable DataManager dataManager) {
        return update$default(this, dataLightsSystem, dataManager, false, 4, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    @JvmOverloads
    public final boolean update(@Nullable DataLightsSystem dataLightsSystem, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        Intrinsics.checkNotNull(dataLightsSystem);
        String str = dataLightsSystem.sunsetTime;
        if (str != null) {
            String str2 = this.sunsetTime;
            if (str2 == null || !Intrinsics.areEqual(str2, str)) {
                this.sunsetTime = dataLightsSystem.sunsetTime;
                if (dataManager != null) {
                    dataManager.add("sunsetTime", dataLightsSystem.sunsetTime);
                }
                z10 = true;
            }
            z10 = false;
        } else {
            if (z7 && this.sunsetTime != null) {
                if (dataManager != null) {
                    dataManager.add("sunsetTime", null);
                }
                z10 = true;
            }
            z10 = false;
        }
        Long l8 = dataLightsSystem.numberClicks;
        if (l8 != null) {
            Long l10 = this.numberClicks;
            if (l10 == null || !Intrinsics.areEqual(l10, l8)) {
                this.numberClicks = dataLightsSystem.numberClicks;
                if (dataManager != null) {
                    dataManager.add("numberClicks", dataLightsSystem.numberClicks);
                }
                z10 = true;
            }
        } else if (z7 && this.numberClicks != null) {
            if (dataManager != null) {
                dataManager.add("numberClicks", null);
            }
            z10 = true;
        }
        String str3 = dataLightsSystem.lastUsedLightId;
        if (str3 != null) {
            String str4 = this.lastUsedLightId;
            if (str4 == null || !Intrinsics.areEqual(str4, str3)) {
                this.lastUsedLightId = dataLightsSystem.lastUsedLightId;
                if (dataManager == null) {
                    return true;
                }
                dataManager.add("lastUsedLightId", dataLightsSystem.lastUsedLightId);
                return true;
            }
        } else if (z7 && this.lastUsedLightId != null) {
            if (dataManager == null) {
                return true;
            }
            dataManager.add("lastUsedLightId", null);
            return true;
        }
        return z10;
    }
}