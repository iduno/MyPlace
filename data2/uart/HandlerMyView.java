package com.air.advantage.uart;

import com.air.advantage.AppFeatures;
import com.air.advantage.TabletInfo;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataMyView;
import com.air.advantage.di.Logger;
import com.air.advantage.di.RxBinding;
import com.air.advantage.doorbell.BosmaRepository;
import com.air.advantage.doorbell.models.CameraDetail;
import com.air.advantage.locks.FireStoreService;
import com.air.advantage.locks.LocksRepository;
import com.air.advantage.locks.model.LockDetail;
import com.air.advantage.systemlistener.SystemListener;
import com.air.advantage.systemlistener.model.SystemListenerModel;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* renamed from: com.air.advantage.uart.t */
/* loaded from: classes.dex */
public final class HandlerMyView {

    /* renamed from: a */
    @NotNull
    private final HandlerJson handlerJson;

    /* renamed from: b */
    @NotNull
    private final DataMaster masterData;

    /* renamed from: c, reason: collision with root package name */
    @NotNull
    private DataMyView f7203c;

    /* renamed from: com.air.advantage.uart.t$a */
    static final class a extends FunctionBase implements Function1<List<? extends LockDetail>, Unit> {
        a() {
            super(1);
        }

        public final void c(List<LockDetail> list) {
            HandlerMyView.this.f("locks changed to " + list);
            HandlerMyView handlerMyView = HandlerMyView.this;
            Intrinsics.checkNotNull(list);
            handlerMyView.k(list);
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
        /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(List<? extends LockDetail> list) {
            c(list);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.air.advantage.uart.t$b */
    static final class b extends FunctionBase implements Function1<List<? extends CameraDetail>, Unit> {
        b() {
            super(1);
        }

        public final void c(List<CameraDetail> list) {
            HandlerMyView.this.f("camera changed to " + list);
            HandlerMyView handlerMyView = HandlerMyView.this;
            Intrinsics.checkNotNull(list);
            handlerMyView.i(list);
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
        /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(List<? extends CameraDetail> list) {
            c(list);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.air.advantage.uart.t$c */
    static final class c extends FunctionBase implements Function1<String, Unit> {
        c() {
            super(1);
        }

        public final void c(String str) {
            HandlerMyView.this.f("lock message changed to " + str);
            HandlerMyView handlerMyView = HandlerMyView.this;
            Intrinsics.checkNotNull(str);
            handlerMyView.l(str);
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
        /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(String str) {
            c(str);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.air.advantage.uart.t$d */
    static final class d extends FunctionBase implements Function1<SystemListenerModel, Unit> {
        d() {
            super(1);
        }

        public final void c(SystemListenerModel systemListenerModel) {
            HandlerMyView.this.f("systemUpdate");
            HandlerMyView handlerMyView = HandlerMyView.this;
            synchronized (MyMasterData.class) {
                handlerMyView.g(true);
                Unit unit = Unit.INSTANCE;
            }
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
        /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(SystemListenerModel systemListenerModel) {
            c(systemListenerModel);
            return Unit.INSTANCE;
        }
    }

    public HandlerMyView(@NotNull LocksRepository locksRepository, @NotNull BosmaRepository bosmaRepository, @NotNull TabletInfo getTabletInfo, @NotNull FireStoreService fireStoreService, @NotNull SystemListener systemListener, @NotNull HandlerJson handlerJson, @NotNull DataMaster masterData) {
        Intrinsics.checkNotNullParameter(locksRepository, "locksRepository");
        Intrinsics.checkNotNullParameter(bosmaRepository, "bosmaRepository");
        Intrinsics.checkNotNullParameter(getTabletInfo, "getTabletInfo");
        Intrinsics.checkNotNullParameter(fireStoreService, "fireStoreService");
        Intrinsics.checkNotNullParameter(systemListener, "systemListener");
        Intrinsics.checkNotNullParameter(handlerJson, "handlerJson");
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        this.handlerJson = handlerJson;
        this.masterData = masterData;
        this.f7203c = new DataMyView(null, null, null, null, 15, null);
        if (getTabletInfo.isTSPDevice()) {
            f("init (on tsp)");
            RxBinding.addPublishSubject(locksRepository.m(), new a());
            RxBinding.addPublishSubject(bosmaRepository.l(), new b());
            RxBinding.addPublishSubject(fireStoreService.n(), new c());
            RxBinding.addPublishSubject(systemListener.getSystemListenerModelSubject(), new d());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void f(Object obj) {
        new Logger("HandlerMyView").verbose(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void g(boolean z7) {
        if (Intrinsics.areEqual(this.f7203c, this.masterData.myView)) {
            return;
        }
        if (z7 && this.masterData.myView == null) {
            f("masterData was null - fixing");
            AppFeatures.logError(AppFeatures.instance, new Exception("Updating null masterData.myView"), null, 2, null);
        }
        DataMaster dataMaster = this.masterData;
        dataMaster.myView = this.f7203c;
        this.handlerJson.processData(dataMaster, "updateMyView");
    }

    static /* synthetic */ void h(HandlerMyView handlerMyView, boolean z7, int i10, Object obj) {
        if ((i10 & 1) != 0) {
            z7 = false;
        }
        handlerMyView.g(z7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void i(List<CameraDetail> list) {
        synchronized (MyMasterData.class) {
            f("updateCameraDetails");
            this.f7203c = DataMyView.copy$default(this.f7203c, null, null, list, null, 11, null);
            h(this, false, 1, null);
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void j(String str) {
        synchronized (MyMasterData.class) {
            this.f7203c = DataMyView.copy$default(this.f7203c, null, null, null, str, 7, null);
            h(this, false, 1, null);
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void k(List<LockDetail> list) {
        synchronized (MyMasterData.class) {
            f("updateLockDetails");
            this.f7203c = DataMyView.copy$default(this.f7203c, list, null, null, null, 14, null);
            h(this, false, 1, null);
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void l(String str) {
        synchronized (MyMasterData.class) {
            f("updateLockMessage");
            DataMyView copy$default = DataMyView.copy$default(this.f7203c, null, str, null, null, 13, null);
            this.f7203c = copy$default;
            this.masterData.myView = copy$default;
            h(this, false, 1, null);
            Unit unit = Unit.INSTANCE;
        }
    }
}