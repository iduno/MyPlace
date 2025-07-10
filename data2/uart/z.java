package com.air.advantage.uart;

import io.reactivex.functions.Consumer;

/* loaded from: classes.dex */
public final /* synthetic */ class z implements Consumer {
    public /* synthetic */ z() {
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        SendMessageToCB.b(aVar, obj);
    }
}