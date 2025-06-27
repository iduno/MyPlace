package com.air.advantage.data;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.y0 */
/* loaded from: classes.dex */
public final class DataVersion {
    private int majorVersion;
    private int minorVersion;

    public DataVersion(@NotNull String versionText) {
        int p32;
        Intrinsics.checkNotNullParameter(versionText, "versionText");
        if (!new Regex("[0-9.]+").matches(versionText) || versionText.length() > 7 || (p32 = StringsKt__StringsKt.p3(versionText, ".", 0, false, 6, null)) < 1 || p32 > 3) {
            return;
        }
        int i10 = 0;
        String substring = versionText.substring(0, p32);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        Integer valueOf = Integer.valueOf(substring);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(...)");
        this.majorVersion = valueOf.intValue();
        if (versionText.length() > p32 + 4) {
            this.majorVersion = 0;
            return;
        }
        if (p32 != versionText.length()) {
            String substring2 = versionText.substring(p32 + 1, versionText.length());
            Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String…ing(startIndex, endIndex)");
            Integer valueOf2 = Integer.valueOf(substring2);
            Intrinsics.checkNotNull(valueOf2);
            i10 = valueOf2.intValue();
        }
        this.minorVersion = i10;
    }

    public final int compareTo(@NotNull DataVersion compareVersion) {
        Intrinsics.checkNotNullParameter(compareVersion, "compareVersion");
        int i10 = this.majorVersion;
        int i11 = compareVersion.majorVersion;
        return i10 == i11 ? Intrinsics.compare(this.minorVersion, compareVersion.minorVersion) : Intrinsics.compare(i10, i11);
    }

    public boolean equals(@Nullable Object obj) {
        return obj instanceof DataVersion ? compareTo((DataVersion) obj) == 0 : super.equals(obj);
    }

    public final int getMajorVersion() {
        return this.majorVersion;
    }

    public final int getMinorVersion() {
        return this.minorVersion;
    }

    public final void setMajorVersion(int i10) {
        this.majorVersion = i10;
    }

    public final void setMinorVersion(int i10) {
        this.minorVersion = i10;
    }

    @NotNull
    public String toString() {
        return this.majorVersion + "." + this.minorVersion;
    }

    public final void update(@NotNull DataVersion dataVersion) {
        Intrinsics.checkNotNullParameter(dataVersion, "dataVersion");
        this.majorVersion = dataVersion.majorVersion;
        this.minorVersion = dataVersion.minorVersion;
    }
}