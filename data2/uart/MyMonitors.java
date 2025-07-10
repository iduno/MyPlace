package com.air.advantage.uart;

import android.content.Context;
import com.air.advantage.AppFeatures;
import com.air.advantage.SharedPreferencesStore;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataMyMonitors;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

/* renamed from: com.air.advantage.uart.b0 */
/* loaded from: classes.dex */
public final class MyMonitors {

    /* renamed from: d */
    @NotNull
    public static final a Companion = new a(null);

    /* renamed from: e, reason: collision with root package name */
    private static final String f7063e = MyMonitors.class.getSimpleName();

    /* renamed from: f, reason: collision with root package name */
    @Nullable
    private static MyMonitors f7064f;

    /* renamed from: a */
    @NotNull
    private final Gson gsonConverter;

    /* renamed from: b */
    @NotNull
    private final SharedPreferencesStore sharedPreferencesStore;

    /* renamed from: c, reason: collision with root package name */
    @NotNull
    private String f7065c;

    /* renamed from: com.air.advantage.uart.b0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.b0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        /* renamed from: a */
        public final void destroy() {
            MyMonitors.f7064f = null;
        }

        @NotNull
        public final MyMonitors b() {
            if (MyMonitors.f7064f == null) {
                synchronized (MyMonitors.class) {
                    if (MyMonitors.f7064f == null) {
                        a aVar = MyMonitors.Companion;
                        MyMonitors.f7064f = new MyMonitors(null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            MyMonitors myMonitors = MyMonitors.f7064f;
            Intrinsics.checkNotNull(myMonitors);
            return myMonitors;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.b0.<init>():void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ MyMonitors(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @JvmStatic
    public static final void c() {
        Companion.destroy();
    }

    public final void d(@Nullable Context context, @NotNull DataMaster masterData) {
        DataMyMonitors dataMyMonitors;
        JsonIOException e7;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (!Thread.holdsLock(MyMasterData.class)) {
            throw new NullPointerException("need to synchronized(MyMasterData.class) to use this function");
        }
        DataMyMonitors dataMyMonitors2 = new DataMyMonitors();
        String sharedPreference = this.sharedPreferencesStore.getSharedPreference(context, SharedPreferencesStore.Companion.R());
        Timber.Forest forest = Timber.forest;
        forest.d("Using monitors " + sharedPreference, new Object[0]);
        if (!(sharedPreference == null || sharedPreference.length() == 0)) {
            try {
                dataMyMonitors = (DataMyMonitors) this.gsonConverter.fromJson(sharedPreference, DataMyMonitors.class);
                try {
                    forest.d("New data monitors struct", new Object[0]);
                } catch (JsonIOException e10) {
                    e7 = e10;
                    AppFeatures.instance.logCriticalException(e7, "restoreMonitorsFromDatabase - " + sharedPreference);
                    dataMyMonitors2 = dataMyMonitors;
                    Intrinsics.checkNotNull(dataMyMonitors2);
                    masterData.myMonitors = dataMyMonitors2;
                }
            } catch (JsonIOException e11) {
                dataMyMonitors = dataMyMonitors2;
                e7 = e11;
            }
            dataMyMonitors2 = dataMyMonitors;
        }
        Intrinsics.checkNotNull(dataMyMonitors2);
        masterData.myMonitors = dataMyMonitors2;
    }

    @Nullable
    public final String e(@Nullable Context context, @NotNull DataMaster masterData) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (!Thread.holdsLock(MyMasterData.class)) {
            throw new NullPointerException("need to synchronized(MyMasterData.class) to use this function");
        }
        String json = this.gsonConverter.toJson(masterData.myMonitors);
        if (json != null && !Intrinsics.areEqual(json, this.f7065c)) {
            Timber.forest.d("Saving monitors " + json, new Object[0]);
            this.f7065c = json;
            this.sharedPreferencesStore.updatePreference(context, SharedPreferencesStore.Companion.R(), json);
        }
        return json;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private MyMonitors() {
        this.gsonConverter = new Gson();
        this.sharedPreferencesStore = (SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null);
        this.f7065c = "";
    }
}