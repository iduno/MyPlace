package com.air.advantage.data;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.r1 */
/* loaded from: classes.dex */
public final class DataStoredSystem {

    @Nullable
    @SerializedName("ip")
    @JvmField
    public String ip;

    @Nullable
    @SerializedName("myAppRev")
    private String myAppRev;

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    @JvmField
    public String name;

    @Nullable
    @SerializedName("rid")
    @JvmField
    public String rid;

    public DataStoredSystem(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4) {
        this.ip = str;
        this.name = str2;
        this.rid = str3;
        this.myAppRev = str4;
    }

    public final boolean compareAndUpdate(@NotNull DataStoredSystem source) {
        boolean z7;
        String str;
        String str2;
        String str3;
        String str4;
        Intrinsics.checkNotNullParameter(source, "source");
        String str5 = source.name;
        if (str5 == null || ((str4 = this.name) != null && Intrinsics.areEqual(str4, str5))) {
            z7 = false;
        } else {
            this.name = source.name;
            z7 = true;
        }
        String str6 = source.rid;
        if (str6 != null && ((str3 = this.rid) == null || !Intrinsics.areEqual(str3, str6))) {
            this.rid = source.rid;
            z7 = true;
        }
        String str7 = source.ip;
        if (str7 != null && ((str2 = this.ip) == null || !Intrinsics.areEqual(str2, str7))) {
            this.ip = source.ip;
            z7 = true;
        }
        String str8 = source.myAppRev;
        if (str8 == null || ((str = this.myAppRev) != null && Intrinsics.areEqual(str, str8))) {
            return z7;
        }
        this.myAppRev = source.myAppRev;
        return true;
    }

    public final boolean equals(@NotNull DataStoredSystem compareWith) {
        Intrinsics.checkNotNullParameter(compareWith, "compareWith");
        String str = this.ip;
        if (str != null && !Intrinsics.areEqual(str, compareWith.ip)) {
            return false;
        }
        String str2 = this.name;
        if (str2 != null && !Intrinsics.areEqual(str2, compareWith.name)) {
            return false;
        }
        String str3 = this.rid;
        return str3 == null ? compareWith.rid == null : Intrinsics.areEqual(str3, compareWith.rid);
    }

    @Nullable
    public final String getMyAppRev() {
        return this.myAppRev;
    }

    public final void setMyAppRev(@Nullable String str) {
        this.myAppRev = str;
    }

    public final void update(@NotNull DataStoredSystem source) {
        String str;
        Intrinsics.checkNotNullParameter(source, "source");
        this.ip = source.ip;
        this.name = source.name;
        String str2 = source.rid;
        if (str2 != null) {
            if (!(str2 == null || str2.length() == 0) && ((str = this.rid) == null || !Intrinsics.areEqual(str, source.rid))) {
                this.rid = source.rid;
            }
        }
        this.myAppRev = source.myAppRev;
    }
}