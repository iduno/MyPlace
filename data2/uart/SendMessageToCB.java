package com.air.advantage.uart;

import android.content.Context;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import timber.log.Timber;

/* renamed from: com.air.advantage.uart.a0 */
/* loaded from: classes.dex */
public final class SendMessageToCB {

    @NotNull
    private final Context a;

    /* renamed from: b */
    @NotNull
    private final LinkedList<String> f7057b;

    /* renamed from: c */
    @NotNull
    private final CompositeDisposable f7058c;

    /* renamed from: com.air.advantage.uart.a0$a */
    static final class a extends FunctionBase implements Function1<Long, Unit> {
        a() {
            super(1);
        }

        public final void c(Long l8) {
            SendMessageToCB sendMessageToCB = SendMessageToCB.this;
            synchronized (MyMasterData.class) {
                if (!sendMessageToCB.f7057b.isEmpty()) {
                    String str = (String) sendMessageToCB.f7057b.getFirst();
                    sendMessageToCB.f7057b.removeFirst();
                    Timber.forest.d("---sending message to CB from queue: " + str, new Object[0]);
                    HandlerCan.Companion.getInstance().sendCanMessageToCB(sendMessageToCB.a, str);
                }
                Unit unit = Unit.INSTANCE;
            }
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
        /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Long l8) {
            c(l8);
            return Unit.INSTANCE;
        }
    }

    public SendMessageToCB(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.a = context;
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        this.f7058c = compositeDisposable;
        Timber.forest.d("init!!!", new Object[0]);
        this.f7057b = new LinkedList<>();
        Observable<Long> observeOn = Observable.g3(3L, TimeUnit.SECONDS, Schedulers.io()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        final a aVar = new a();
        compositeDisposable.add(observeOn.D5(new Consumer() { // from class: com.air.advantage.uart.z
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SendMessageToCB.b(aVar, obj);
            }
        }));
    }

    /* JADX DEBUG: Marked for inline */
    /* JADX DEBUG: Method not inlined, still used in: [com.air.advantage.uart.z.accept(java.lang.Object):void] */
    public static /* synthetic */ void a(Function1 function1, Object obj) {
        b(function1, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void b(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* renamed from: e */
    public final void addCBMessage(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        synchronized (MyMasterData.class) {
            if (Intrinsics.areEqual(message, "") || this.f7057b.contains(message)) {
                Timber.forest.d("already exist in queue or empty string, ignoring this new message: " + message, new Object[0]);
                Unit unit = Unit.INSTANCE;
            } else {
                Timber.forest.d("adding new message to queue: " + message, new Object[0]);
                this.f7057b.add(message);
            }
        }
    }

    public final void f() {
        this.f7057b.clear();
    }
}