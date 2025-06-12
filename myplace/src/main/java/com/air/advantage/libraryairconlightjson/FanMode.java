package com.air.advantage.libraryairconlightjson;

/* compiled from: FanMode.java */
/* renamed from: b.a.a.a.f */
/* loaded from: classes.dex */
public enum FanMode {
    off(0),
    low(1),
    medium(2),
    high(3),
    auto(4),
    autoAA(5);

    private int value;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    FanMode(int i) {
        this.value = i;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public int getValue() {
        return this.value;
    }
}