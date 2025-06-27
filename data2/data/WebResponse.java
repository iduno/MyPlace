package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.j0 */
/* loaded from: classes.dex */
public final class WebResponse {

    @SerializedName("ack")
    private boolean ack;

    @Nullable
    @SerializedName("reason")
    private String reason;

    @SerializedName("request")
    @NotNull
    private String request;

    public WebResponse(@NotNull String request, @Nullable String str, boolean z7) {
        Intrinsics.checkNotNullParameter(request, "request");
        this.request = request;
        this.reason = str;
        this.ack = z7;
    }

    public final boolean getAck() {
        return this.ack;
    }

    @Nullable
    public final String getReason() {
        return this.reason;
    }

    @NotNull
    public final String getRequest() {
        return this.request;
    }

    public final void setAck(boolean z7) {
        this.ack = z7;
    }

    public final void setReason(@Nullable String str) {
        this.reason = str;
    }

    public final void setRequest(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.request = str;
    }
}