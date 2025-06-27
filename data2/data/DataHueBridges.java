package com.air.advantage.data;

import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.google.gson.annotations.SerializedName;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.q */
/* loaded from: classes.dex */
public final class DataHueBridges {

    @Nullable
    @SerializedName("id")
    @JvmField
    public String id;

    @Nullable
    @SerializedName("ipAddress")
    @JvmField
    public String ipAddress;

    @Nullable
    @SerializedName("modelNumber")
    @JvmField
    public String modelNumber;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("userName")
    @JvmField
    public String userName;

    public static /* synthetic */ boolean update$default(DataHueBridges dataHueBridges, DataHueBridges dataHueBridges2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 4) != 0) {
            z7 = false;
        }
        return dataHueBridges.update(dataHueBridges2, dataManager, z7);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@NotNull DataHueBridges dataHueBridgeSource, @Nullable DataManager dataManager) {
        Intrinsics.checkNotNullParameter(dataHueBridgeSource, "dataHueBridgeSource");
        return update$default(this, dataHueBridgeSource, dataManager, false, 4, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    @JvmOverloads
    public final boolean update(@NotNull DataHueBridges dataHueBridgeSource, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        String str;
        Intrinsics.checkNotNullParameter(dataHueBridgeSource, "dataHueBridgeSource");
        if (dataManager != null && (str = dataHueBridgeSource.id) != null) {
            dataManager.updateHueBridgesPath(str);
        }
        String str2 = dataHueBridgeSource.ipAddress;
        if (str2 != null) {
            String str3 = this.ipAddress;
            if (str3 == null || !Intrinsics.areEqual(str3, str2)) {
                this.ipAddress = dataHueBridgeSource.ipAddress;
                if (dataManager != null) {
                    dataManager.add("ipAddress", dataHueBridgeSource.ipAddress);
                }
                z10 = true;
            }
            z10 = false;
        } else {
            if (z7 && this.ipAddress != null) {
                if (dataManager != null) {
                    dataManager.add("ipAddress", null);
                }
                z10 = true;
            }
            z10 = false;
        }
        String str4 = dataHueBridgeSource.userName;
        if (str4 != null) {
            String str5 = this.userName;
            if (str5 == null || !Intrinsics.areEqual(str5, str4)) {
                this.userName = dataHueBridgeSource.userName;
                if (dataManager != null) {
                    dataManager.add("userName", dataHueBridgeSource.userName);
                }
                z10 = true;
            }
        } else if (z7 && this.userName != null) {
            if (dataManager != null) {
                dataManager.add("userName", null);
            }
            z10 = true;
        }
        String str6 = dataHueBridgeSource.modelNumber;
        if (str6 != null) {
            String str7 = this.modelNumber;
            if (str7 == null || !Intrinsics.areEqual(str7, str6)) {
                this.modelNumber = dataHueBridgeSource.modelNumber;
                if (dataManager == null) {
                    return true;
                }
                dataManager.add("modelNumber", dataHueBridgeSource.modelNumber);
                return true;
            }
        } else if (z7 && this.modelNumber != null) {
            if (dataManager == null) {
                return true;
            }
            dataManager.add("modelNumber", null);
            return true;
        }
        return z10;
    }
}