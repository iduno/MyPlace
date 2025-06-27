package com.air.advantage.data;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.n */
/* loaded from: classes.dex */
public final class DataGroupThing {

    @NotNull
    public static final String BUTTON_TYPE_NONE = "none";

    @NotNull
    public static final a Companion = new a(null);

    @Nullable
    @SerializedName("buttonType")
    @JvmField
    public String buttonType;

    @Nullable
    @SerializedName("id")
    @JvmField
    public String id;

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    @JvmField
    public String name;

    @Nullable
    @SerializedName("state")
    @JvmField
    public State state;

    @SerializedName("thingsOrder")
    @NotNull
    @JvmField
    public final ArrayList<String> thingsOrder = new ArrayList<>();

    /* renamed from: com.air.advantage.data.n$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.n.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public DataGroupThing() {
    }

    private final boolean thingsOrderArrayListsAreEqual(ArrayList<String> arrayList, ArrayList<String> arrayList2) {
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

    public static /* synthetic */ boolean update$default(DataGroupThing dataGroupThing, DataGroupThing dataGroupThing2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 4) != 0) {
            z7 = false;
        }
        return dataGroupThing.update(dataGroupThing2, dataManager, z7);
    }

    public final void clearDataForBackup() {
        this.buttonType = null;
        this.state = null;
    }

    public final void sanitiseData() {
        this.id = null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@NotNull DataGroupThing dataGroupSource, @Nullable DataManager dataManager) {
        Intrinsics.checkNotNullParameter(dataGroupSource, "dataGroupSource");
        return update$default(this, dataGroupSource, dataManager, false, 4, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    @JvmOverloads
    public final boolean update(@NotNull DataGroupThing dataGroupSource, @Nullable DataManager dataManager, boolean z7) {
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
        if (!thingsOrderArrayListsAreEqual(this.thingsOrder, dataGroupSource.thingsOrder)) {
            this.thingsOrder.clear();
            this.thingsOrder.addAll(dataGroupSource.thingsOrder);
            if (dataManager != null) {
                dataManager.add("thingsOrder", dataGroupSource.thingsOrder);
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
        State state = dataGroupSource.state;
        if (state != null) {
            State state2 = this.state;
            if (state2 == null || state2 != state) {
                this.state = state;
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
        String str5 = dataGroupSource.buttonType;
        if (str5 != null) {
            String str6 = this.buttonType;
            if (str6 == null || !Intrinsics.areEqual(str6, str5)) {
                this.buttonType = dataGroupSource.buttonType;
                if (dataManager == null) {
                    return true;
                }
                dataManager.add("buttonType", dataGroupSource.buttonType);
                return true;
            }
        } else if (z7 && this.buttonType != null) {
            if (dataManager == null) {
                return true;
            }
            dataManager.add("buttonType", null);
            return true;
        }
        return z10;
    }

    public DataGroupThing(@Nullable String str) {
        this.id = str;
    }
}