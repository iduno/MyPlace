package com.air.advantage.data;

import com.air.advantage.doorbell.models.CameraDetail;
import com.air.advantage.locks.model.LockDetail;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.g0 */
/* loaded from: classes.dex */
public final class DataMyView {

    @Nullable
    @SerializedName("cameraMessage")
    private final String cameraMessage;

    @Nullable
    @SerializedName("cameras")
    private final List<CameraDetail> cameras;

    @Nullable
    @SerializedName("lockMessage")
    private final String lockMessage;

    @Nullable
    @SerializedName("locks")
    private final List<LockDetail> locks;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public DataMyView() {
        this(null, null, null, null, 15, null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: com.air.advantage.data.g0 */
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ DataMyView copy$default(DataMyView dataMyView, List list, String str, List list2, String str2, int i10, Object obj) {
        if ((i10 & 1) != 0) {
            list = dataMyView.locks;
        }
        if ((i10 & 2) != 0) {
            str = dataMyView.lockMessage;
        }
        if ((i10 & 4) != 0) {
            list2 = dataMyView.cameras;
        }
        if ((i10 & 8) != 0) {
            str2 = dataMyView.cameraMessage;
        }
        return dataMyView.copy(list, str, list2, str2);
    }

    @Nullable
    public final List<LockDetail> component1() {
        return this.locks;
    }

    @Nullable
    public final String component2() {
        return this.lockMessage;
    }

    @Nullable
    public final List<CameraDetail> component3() {
        return this.cameras;
    }

    @Nullable
    public final String component4() {
        return this.cameraMessage;
    }

    @NotNull
    public final DataMyView copy(@Nullable List<LockDetail> list, @Nullable String str, @Nullable List<CameraDetail> list2, @Nullable String str2) {
        return new DataMyView(list, str, list2, str2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DataMyView)) {
            return false;
        }
        DataMyView dataMyView = (DataMyView) obj;
        return Intrinsics.areEqual(this.locks, dataMyView.locks) && Intrinsics.areEqual(this.lockMessage, dataMyView.lockMessage) && Intrinsics.areEqual(this.cameras, dataMyView.cameras) && Intrinsics.areEqual(this.cameraMessage, dataMyView.cameraMessage);
    }

    @Nullable
    public final String getCameraMessage() {
        return this.cameraMessage;
    }

    @Nullable
    public final List<CameraDetail> getCameras() {
        return this.cameras;
    }

    @Nullable
    public final String getLockMessage() {
        return this.lockMessage;
    }

    @Nullable
    public final List<LockDetail> getLocks() {
        return this.locks;
    }

    public int hashCode() {
        List<LockDetail> list = this.locks;
        int hashCode = (list == null ? 0 : list.hashCode()) * 31;
        String str = this.lockMessage;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        List<CameraDetail> list2 = this.cameras;
        int hashCode3 = (hashCode2 + (list2 == null ? 0 : list2.hashCode())) * 31;
        String str2 = this.cameraMessage;
        return hashCode3 + (str2 != null ? str2.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "DataMyView(locks=" + this.locks + ", lockMessage=" + this.lockMessage + ", cameras=" + this.cameras + ", cameraMessage=" + this.cameraMessage + ")";
    }

    public final boolean update(@NotNull DataMyView myView, @Nullable DataManager dataManager) {
        boolean z7;
        Intrinsics.checkNotNullParameter(myView, "myView");
        if (Intrinsics.areEqual(this.locks, myView.locks)) {
            z7 = false;
        } else {
            List<LockDetail> list = myView.locks;
            if (list == null || list.isEmpty()) {
                if (dataManager != null) {
                    dataManager.add("locks", null);
                }
            } else if (dataManager != null) {
                dataManager.add("locks", myView.locks);
            }
            z7 = true;
        }
        if (!Intrinsics.areEqual(this.lockMessage, myView.lockMessage)) {
            String str = myView.lockMessage;
            if (str == null || str.length() == 0) {
                if (dataManager != null) {
                    dataManager.add("lockMessage", null);
                }
            } else if (dataManager != null) {
                dataManager.add("lockMessage", myView.lockMessage);
            }
            z7 = true;
        }
        if (!Intrinsics.areEqual(this.cameras, myView.cameras)) {
            List<CameraDetail> list2 = myView.cameras;
            if (list2 == null || list2.isEmpty()) {
                if (dataManager != null) {
                    dataManager.add("cameras", null);
                }
            } else if (dataManager != null) {
                dataManager.add("cameras", myView.cameras);
            }
            z7 = true;
        }
        if (Intrinsics.areEqual(this.cameraMessage, myView.cameraMessage)) {
            return z7;
        }
        String str2 = myView.cameraMessage;
        if (str2 == null || str2.length() == 0) {
            if (dataManager == null) {
                return true;
            }
            dataManager.add("cameraMessage", null);
            return true;
        }
        if (dataManager == null) {
            return true;
        }
        dataManager.add("cameraMessage", myView.cameraMessage);
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public DataMyView(@Nullable List<LockDetail> list, @Nullable String str, @Nullable List<CameraDetail> list2, @Nullable String str2) {
        this.locks = list;
        this.lockMessage = str;
        this.cameras = list2;
        this.cameraMessage = str2;
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0015: CONSTRUCTOR 
      (wrap:java.util.List:?: TERNARY null = ((wrap:int:0x0000: ARITH (r6v0 int) & (1 int) A[WRAPPED]) != (0 int)) ? (null java.util.List) : (r2v0 java.util.List))
      (wrap:java.lang.String:?: TERNARY null = ((wrap:int:0x0006: ARITH (r6v0 int) & (2 int) A[WRAPPED]) != (0 int)) ? (null java.lang.String) : (r3v0 java.lang.String))
      (wrap:java.util.List:?: TERNARY null = ((wrap:int:0x000b: ARITH (r6v0 int) & (4 int) A[WRAPPED]) != (0 int)) ? (null java.util.List) : (r4v0 java.util.List))
      (wrap:java.lang.String:?: TERNARY null = ((wrap:int:0x0010: ARITH (r6v0 int) & (8 int) A[WRAPPED]) != (0 int)) ? (null java.lang.String) : (r5v0 java.lang.String))
     A[MD:(java.util.List<com.air.advantage.locks.model.LockDetail>, java.lang.String, java.util.List<com.air.advantage.doorbell.models.CameraDetail>, java.lang.String):void (m)] (LINE:7) call: com.air.advantage.data.g0.<init>(java.util.List, java.lang.String, java.util.List, java.lang.String):void type: THIS */
    public /* synthetic */ DataMyView(List list, String str, List list2, String str2, int i10, DefaultConstructorMarker defaultConstructorMarker) {
        this((i10 & 1) != 0 ? null : list, (i10 & 2) != 0 ? null : str, (i10 & 4) != 0 ? null : list2, (i10 & 8) != 0 ? null : str2);
    }
}