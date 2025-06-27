package com.air.advantage.data;

import com.air.advantage.libraryairconlightjson.LightState;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.m */
/* loaded from: classes.dex */
public final class DataGroup {

    @Nullable
    @SerializedName("id")
    @JvmField
    public String id;

    @SerializedName("lightsOrder")
    @NotNull
    @JvmField
    public final ArrayList<String> lightsOrder = new ArrayList<>();

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    @JvmField
    public String name;

    @Nullable
    @SerializedName("state")
    @JvmField
    public LightState state;

    @Nullable
    @SerializedName("value")
    @JvmField
    public Integer value;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public DataGroup() {
    }

    private final boolean lightsOrderArrayListsAreEqual(ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        if (arrayList.size() != arrayList2.size()) {
            return false;
        }
        int size = arrayList.size();
        for (int i10 = 0; i10 < size; i10++) {
            if (!Intrinsics.areEqual(arrayList.get(i10), arrayList2.get(i10))) {
                return false;
            }
        }
        return true;
    }

    public static /* synthetic */ boolean update$default(DataGroup dataGroup, DataGroup dataGroup2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 4) != 0) {
            z7 = false;
        }
        return dataGroup.update(dataGroup2, dataManager, z7);
    }

    public final void clearDataForBackup() {
        this.state = null;
        this.value = null;
    }

    public final void sanitiseData() {
        this.id = null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@NotNull DataGroup dataGroupSource, @Nullable DataManager dataManager) {
        Intrinsics.checkNotNullParameter(dataGroupSource, "dataGroupSource");
        return update$default(this, dataGroupSource, dataManager, false, 4, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    @JvmOverloads
    public final boolean update(@NotNull DataGroup dataGroupSource, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        String str;
        Intrinsics.checkNotNullParameter(dataGroupSource, "dataGroupSource");
        String str2 = dataGroupSource.id;
        if (str2 == null || ((str = this.id) != null && Intrinsics.areEqual(str, str2))) {
            z10 = false;
        } else {
            this.id = dataGroupSource.id;
            z10 = true;
        }
        if (!lightsOrderArrayListsAreEqual(this.lightsOrder, dataGroupSource.lightsOrder)) {
            this.lightsOrder.clear();
            this.lightsOrder.addAll(dataGroupSource.lightsOrder);
            if (dataManager != null) {
                dataManager.add("lightsOrder", dataGroupSource.lightsOrder);
            }
            z10 = true;
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
        LightState lightState = dataGroupSource.state;
        if (lightState != null) {
            LightState lightState2 = this.state;
            if (lightState2 == null || lightState2 != lightState) {
                this.state = lightState;
                if (dataManager != null) {
                    dataManager.add("state", dataGroupSource.state);
                }
                z10 = true;
            }
        } else if (z7 && this.state != null) {
            if (dataManager != null) {
                dataManager.add("state", null);
            }
            z10 = true;
        }
        Integer num = dataGroupSource.value;
        if (num != null) {
            Integer num2 = this.value;
            if (num2 == null || !Intrinsics.areEqual(num2, num)) {
                this.value = dataGroupSource.value;
                if (dataManager == null) {
                    return true;
                }
                dataManager.add("value", dataGroupSource.value);
                return true;
            }
        } else if (z7 && this.value != null) {
            if (dataManager == null) {
                return true;
            }
            dataManager.add("value", null);
            return true;
        }
        return z10;
    }

    public DataGroup(@Nullable String str) {
        this.id = str;
    }
}