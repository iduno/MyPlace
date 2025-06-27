package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlinx.coroutines.DebugKt;
import org.jetbrains.annotations.NotNull;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: com.air.advantage.data.c1 */
/* loaded from: classes.dex */
public final class State {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ State[] $VALUES;
    private final int value;

    @SerializedName(DebugKt.DEBUG_PROPERTY_VALUE_OFF)
    public static final State off = new State(DebugKt.DEBUG_PROPERTY_VALUE_OFF, 0, 0);

    @SerializedName(DebugKt.DEBUG_PROPERTY_VALUE_ON)
    public static final State on = new State(DebugKt.DEBUG_PROPERTY_VALUE_ON, 1, 1);

    @SerializedName("stop")
    public static final State stop = new State("stop", 2, 2);

    @SerializedName("mixed")
    public static final State mixed = new State("mixed", 3, 3);

    private static final /* synthetic */ State[] $values() {
        return new State[]{off, on, stop, mixed};
    }

    static {
        State[] $values = $values();
        $VALUES = $values;
        $ENTRIES = EnumEntriesKt.enumEntries($values);
    }

    private State(String str, int i10, int i11) {
        this.value = i11;
    }

    @NotNull
    public static EnumEntries<State> getEntries() {
        return $ENTRIES;
    }

    public static State valueOf(String str) {
        return (State) Enum.valueOf(State.class, str);
    }

    public static State[] values() {
        return (State[]) $VALUES.clone();
    }
}