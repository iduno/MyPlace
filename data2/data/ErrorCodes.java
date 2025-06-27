package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.annotations.NotNull;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: com.air.advantage.data.t1 */
/* loaded from: classes.dex */
public final class ErrorCodes {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ ErrorCodes[] $VALUES;

    @SerializedName("AA128")
    public static final ErrorCodes AA128 = new ErrorCodes("AA128", 0);

    @SerializedName("AA129")
    public static final ErrorCodes AA129 = new ErrorCodes("AA129", 1);

    @SerializedName("AA123")
    public static final ErrorCodes AA123 = new ErrorCodes("AA123", 2);

    @SerializedName("AA124")
    public static final ErrorCodes AA124 = new ErrorCodes("AA124", 3);

    @SerializedName("AA125")
    public static final ErrorCodes AA125 = new ErrorCodes("AA125", 4);

    @SerializedName("AA126")
    public static final ErrorCodes AA126 = new ErrorCodes("AA126", 5);

    @SerializedName("AA127")
    public static final ErrorCodes AA127 = new ErrorCodes("AA127", 6);

    @SerializedName("AA98")
    public static final ErrorCodes AA98 = new ErrorCodes("AA98", 7);

    @SerializedName("AA101")
    public static final ErrorCodes AA101 = new ErrorCodes("AA101", 8);

    @SerializedName("AA104")
    public static final ErrorCodes AA104 = new ErrorCodes("AA104", 9);

    @SerializedName("AA107")
    public static final ErrorCodes AA107 = new ErrorCodes("AA107", 10);

    @SerializedName("AA121")
    public static final ErrorCodes AA121 = new ErrorCodes("AA121", 11);

    @SerializedName("AA122")
    public static final ErrorCodes AA122 = new ErrorCodes("AA122", 12);

    @SerializedName("AA132")
    public static final ErrorCodes AA132 = new ErrorCodes("AA132", 13);

    @SerializedName("AA133")
    public static final ErrorCodes AA133 = new ErrorCodes("AA133", 14);

    @SerializedName("AA134")
    public static final ErrorCodes AA134 = new ErrorCodes("AA134", 15);

    @SerializedName("AA135")
    public static final ErrorCodes AA135 = new ErrorCodes("AA135", 16);

    @SerializedName("noError")
    public static final ErrorCodes noError = new ErrorCodes("noError", 17);

    private static final /* synthetic */ ErrorCodes[] $values() {
        return new ErrorCodes[]{AA128, AA129, AA123, AA124, AA125, AA126, AA127, AA98, AA101, AA104, AA107, AA121, AA122, AA132, AA133, AA134, AA135, noError};
    }

    static {
        ErrorCodes[] $values = $values();
        $VALUES = $values;
        $ENTRIES = EnumEntriesKt.enumEntries($values);
    }

    private ErrorCodes(String str, int i10) {
    }

    @NotNull
    public static EnumEntries<ErrorCodes> getEntries() {
        return $ENTRIES;
    }

    public static ErrorCodes valueOf(String str) {
        return (ErrorCodes) Enum.valueOf(ErrorCodes.class, str);
    }

    public static ErrorCodes[] values() {
        return (ErrorCodes[]) $VALUES.clone();
    }
}