package com.air.advantage.data;

import com.air.advantage.ActivityMain;
import com.google.gson.annotations.SerializedName;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* renamed from: com.air.advantage.data.h0 */
/* loaded from: classes.dex */
public final class RFParing {

    @SerializedName("pairingControl")
    @JvmField
    public int pairingControl;

    @SerializedName("rfDeviceType")
    @JvmField
    public int rfDeviceType;

    @SerializedName(ActivityMain.UID)
    @NotNull
    @JvmField
    public String uid;

    @SerializedName("zoneChannelNo")
    @JvmField
    public int zoneChannelNo;

    public RFParing(@NotNull String uid, int i10, int i11, int i12) {
        Intrinsics.checkNotNullParameter(uid, "uid");
        this.uid = uid;
        this.pairingControl = i10;
        this.rfDeviceType = i11;
        this.zoneChannelNo = i12;
    }
}