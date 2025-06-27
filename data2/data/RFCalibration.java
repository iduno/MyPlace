package com.air.advantage.data;

import com.air.advantage.ActivityMain;
import com.google.gson.annotations.SerializedName;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* renamed from: com.air.advantage.data.i0 */
/* loaded from: classes.dex */
public final class RFCalibration {

    @SerializedName("calibrationControl")
    @JvmField
    public int calibrationControl;

    @SerializedName("channelNo")
    @JvmField
    public int channelNo;

    @SerializedName(ActivityMain.UID)
    @NotNull
    @JvmField
    public String uid;

    @SerializedName("upDownPosition")
    @JvmField
    public int upDownPosition;

    public RFCalibration(@NotNull String uid, int i10, int i11, int i12) {
        Intrinsics.checkNotNullParameter(uid, "uid");
        this.uid = uid;
        this.calibrationControl = i10;
        this.upDownPosition = i11;
        this.channelNo = i12;
    }
}