package com.air.advantage.data;

import android.content.Context;
import java.lang.ref.WeakReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.b */
/* loaded from: classes.dex */
public abstract class BaseData {

    @PurelyImplements({"SMAP\nBaseData.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BaseData.kt\ncom/air/advantage/data/BaseData$RunnableUpdate\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,23:1\n1#2:24\n*E\n"})
    /* renamed from: com.air.advantage.data.b$a */
    public final class RunnableUpdate implements Runnable {

        @Nullable
        private WeakReference<Context> weakReference;

        /* JADX DEBUG: Incorrect args count in method signature: ()V */
        public RunnableUpdate() {
        }

        @Nullable
        public final WeakReference<Context> getWeakReference() {
            return this.weakReference;
        }

        @Override // java.lang.Runnable
        public void run() {
            WeakReference<Context> weakReference = this.weakReference;
            Intrinsics.checkNotNull(weakReference);
            Context context = weakReference.get();
            if (context != null) {
                BaseData.this.sendUpdate(context);
            }
        }

        public final void setContext(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            this.weakReference = new WeakReference<>(context);
        }

        public final void setWeakReference(@Nullable WeakReference<Context> weakReference) {
            this.weakReference = weakReference;
        }
    }

    public abstract void sendUpdate(@Nullable Context context);
}