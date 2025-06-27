package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.k */
/* loaded from: classes.dex */
public final class DataAddOnsAllImport {

    @Nullable
    @SerializedName("hueBridges")
    @JvmField
    public HashMap<String, DataHueBridges> hueBridges = new HashMap<>();

    @Nullable
    @SerializedName("hueBridgesOrder")
    @JvmField
    public ArrayList<String> hueBridgesOrder = new ArrayList<>();

    public final boolean addHueBridge(@NotNull DataHueBridges dataHueBridge) {
        Intrinsics.checkNotNullParameter(dataHueBridge, "dataHueBridge");
        if (getHueBridge(dataHueBridge.id) != null) {
            return false;
        }
        HashMap<String, DataHueBridges> hashMap = this.hueBridges;
        Intrinsics.checkNotNull(hashMap);
        hashMap.put(dataHueBridge.id, dataHueBridge);
        ArrayList<String> arrayList = this.hueBridgesOrder;
        Intrinsics.checkNotNull(arrayList);
        arrayList.add(dataHueBridge.id);
        return true;
    }

    public final void clearDataForBackup() {
        HashMap<String, DataHueBridges> hashMap = this.hueBridges;
        Intrinsics.checkNotNull(hashMap);
        hashMap.clear();
        ArrayList<String> arrayList = this.hueBridgesOrder;
        Intrinsics.checkNotNull(arrayList);
        arrayList.clear();
    }

    public final void clearHueBridges() {
        ArrayList<String> arrayList = this.hueBridgesOrder;
        Intrinsics.checkNotNull(arrayList);
        arrayList.clear();
        HashMap<String, DataHueBridges> hashMap = this.hueBridges;
        Intrinsics.checkNotNull(hashMap);
        hashMap.clear();
    }

    @Nullable
    public final DataHueBridges getHueBridge(@Nullable String str) {
        HashMap<String, DataHueBridges> hashMap = this.hueBridges;
        Intrinsics.checkNotNull(hashMap);
        return hashMap.get(str);
    }

    public final boolean removeHueBridge(@Nullable String str) {
        ArrayList<String> arrayList = this.hueBridgesOrder;
        Intrinsics.checkNotNull(arrayList);
        arrayList.remove(str);
        HashMap<String, DataHueBridges> hashMap = this.hueBridges;
        Intrinsics.checkNotNull(hashMap);
        return hashMap.remove(str) != null;
    }
}