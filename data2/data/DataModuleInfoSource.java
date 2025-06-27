package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.w */
/* loaded from: classes.dex */
public final class DataModuleInfoSource {

    @Nullable
    @SerializedName("firmwareVersion")
    @JvmField
    public String firmwareVersion;

    @Nullable
    @SerializedName("moduleType")
    @JvmField
    public String moduleType;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !Intrinsics.areEqual(DataModuleInfoSource.class, obj.getClass())) {
            return false;
        }
        DataModuleInfoSource dataModuleInfoSource = (DataModuleInfoSource) obj;
        return Intrinsics.areEqual(this.firmwareVersion, dataModuleInfoSource.firmwareVersion) && Intrinsics.areEqual(this.moduleType, dataModuleInfoSource.moduleType);
    }

    public final boolean update(@NotNull DataModuleInfoSource dataModuleInfoSource, @Nullable DataManager dataManager) {
        boolean z7;
        String str;
        String str2;
        Intrinsics.checkNotNullParameter(dataModuleInfoSource, "dataModuleInfoSource");
        String str3 = dataModuleInfoSource.firmwareVersion;
        if (str3 == null || ((str2 = this.firmwareVersion) != null && Intrinsics.areEqual(str2, str3))) {
            z7 = false;
        } else {
            this.firmwareVersion = dataModuleInfoSource.firmwareVersion;
            if (dataManager != null) {
                dataManager.add("firmwareVersion", dataModuleInfoSource.firmwareVersion);
            }
            z7 = true;
        }
        String str4 = dataModuleInfoSource.moduleType;
        if (str4 == null || ((str = this.moduleType) != null && Intrinsics.areEqual(str, str4))) {
            return z7;
        }
        this.moduleType = dataModuleInfoSource.moduleType;
        if (dataManager == null) {
            return true;
        }
        dataManager.addSetValue("moduleType", dataModuleInfoSource.moduleType);
        return true;
    }
}