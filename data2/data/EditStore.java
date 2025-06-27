package com.air.advantage.data;

import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* renamed from: com.air.advantage.data.k1 */
/* loaded from: classes.dex */
public final class EditStore {

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = LightScenes.class.getSimpleName();

    @JvmField
    public boolean newMonitor = true;

    @JvmField
    public boolean newAutoAction = true;

    @NotNull
    @JvmField
    public final DataMonitor editMonitorData = new DataMonitor();

    /* renamed from: com.air.advantage.data.k1$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.k1.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    public final int numberOfActionsItem() {
        return 2;
    }

    public final int numberOfEventsItem() {
        return 2;
    }
}