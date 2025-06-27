package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.v0 */
/* loaded from: classes.dex */
public final class DataThingsSystem {

    @Nullable
    @SerializedName("lastUsedThingId")
    @JvmField
    public String lastUsedThingId;

    @Nullable
    @SerializedName("numberClicks")
    @JvmField
    public Long numberClicks;

    public static /* synthetic */ boolean update$default(DataThingsSystem dataThingsSystem, DataThingsSystem dataThingsSystem2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 4) != 0) {
            z7 = false;
        }
        return dataThingsSystem.update(dataThingsSystem2, dataManager, z7);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@Nullable DataThingsSystem dataThingsSystem, @Nullable DataManager dataManager) {
        return update$default(this, dataThingsSystem, dataManager, false, 4, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    @JvmOverloads
    public final boolean update(@Nullable DataThingsSystem dataThingsSystem, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        Intrinsics.checkNotNull(dataThingsSystem);
        Long l8 = dataThingsSystem.numberClicks;
        if (l8 != null) {
            Long l10 = this.numberClicks;
            if (l10 == null || !Intrinsics.areEqual(l10, l8)) {
                this.numberClicks = dataThingsSystem.numberClicks;
                if (dataManager != null) {
                    dataManager.add("numberClicks", dataThingsSystem.numberClicks);
                }
                z10 = true;
            }
            z10 = false;
        } else {
            if (z7 && this.numberClicks != null) {
                if (dataManager != null) {
                    dataManager.add("numberClicks", null);
                }
                z10 = true;
            }
            z10 = false;
        }
        String str = dataThingsSystem.lastUsedThingId;
        if (str != null) {
            String str2 = this.lastUsedThingId;
            if (str2 == null || !Intrinsics.areEqual(str2, str)) {
                this.lastUsedThingId = dataThingsSystem.lastUsedThingId;
                if (dataManager == null) {
                    return true;
                }
                dataManager.add("lastUsedThingId", dataThingsSystem.lastUsedThingId);
                return true;
            }
        } else if (z7 && this.lastUsedThingId != null) {
            if (dataManager == null) {
                return true;
            }
            dataManager.add("lastUsedThingId", null);
            return true;
        }
        return z10;
    }
}