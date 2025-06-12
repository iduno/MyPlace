package com.air.advantage.libraryairconlightjson;

/* compiled from: LightState.java */
/* renamed from: b.a.a.a.g */
/* loaded from: classes.dex */
public enum LightState {
    off(0),
    on(1);

    private int value;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    LightState(int i) {
        this.value = i;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public int getValue() {
        return this.value;
    }
}