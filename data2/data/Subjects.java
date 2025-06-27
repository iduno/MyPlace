package com.air.advantage.data;

import io.reactivex.subjects.BehaviorSubject;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* renamed from: com.air.advantage.data.j1 */
/* loaded from: classes.dex */
public class Subjects {

    @NotNull
    private final BehaviorSubject<DataMyGarageController> dataMyGarageControllersSubject;

    @NotNull
    private final BehaviorSubject<DataMyView> dataMyViewBehaviorSubject;

    public Subjects() {
        BehaviorSubject<DataMyView> create = BehaviorSubject.create();
        Intrinsics.checkNotNullExpressionValue(create, "create(...)");
        this.dataMyViewBehaviorSubject = create;
        BehaviorSubject<DataMyGarageController> create2 = BehaviorSubject.create();
        Intrinsics.checkNotNullExpressionValue(create2, "create(...)");
        this.dataMyGarageControllersSubject = create2;
    }

    @NotNull
    public final BehaviorSubject<DataMyGarageController> getDataMyGarageControllersSubject() {
        return this.dataMyGarageControllersSubject;
    }

    @NotNull
    public final BehaviorSubject<DataMyView> getDataMyViewBehaviorSubject() {
        return this.dataMyViewBehaviorSubject;
    }
}