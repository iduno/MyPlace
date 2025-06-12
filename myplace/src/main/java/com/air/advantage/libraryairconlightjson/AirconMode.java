package com.air.advantage.libraryairconlightjson;

/* compiled from: AirconMode.java */
/* renamed from: b.a.a.a.a */
/* loaded from: classes.dex */
public enum AirconMode {
    cool(1),
    heat(2),
    vent(3),
    auto(4),
    dry(5),
    myauto(6);

    private int value;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    AirconMode(int i) {
        this.value = i;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public int getValue() {
        return this.value;
    }
}