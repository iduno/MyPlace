package com.air.advantage.uart;

import android.content.Context;
import com.air.advantage.AppFeatures;
import com.air.advantage.SharedPreferencesStore;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataMyScene;
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

/* loaded from: classes.dex */
public final class d0 {

    /* renamed from: d */
    @NotNull
    public static final a Companion = new a(null);

    /* renamed from: e, reason: collision with root package name */
    private static final String f7074e = d0.class.getSimpleName();

    /* renamed from: f, reason: collision with root package name */
    @Nullable
    private static d0 f7075f;

    @NotNull
    private final Gson a;

    /* renamed from: b, reason: collision with root package name */
    @NotNull
    private final SharedPreferencesStore f7076b;

    /* renamed from: c, reason: collision with root package name */
    @NotNull
    private String f7077c;

    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.d0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        /* renamed from: a */
        public final void destroy() {
            d0.f7075f = null;
        }

        @NotNull
        public final d0 b() {
            if (d0.f7075f == null) {
                synchronized (d0.class) {
                    if (d0.f7075f == null) {
                        a aVar = d0.Companion;
                        d0.f7075f = new d0(null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            d0 d0Var = d0.f7075f;
            Intrinsics.checkNotNull(d0Var);
            return d0Var;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.d0.<init>():void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ d0(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @JvmStatic
    public static final void c() {
        Companion.destroy();
    }

    public final void d(@Nullable Context context, @NotNull DataMaster masterData) {
        DataMyScene dataMyScene;
        JsonIOException e7;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (!Thread.holdsLock(MyMasterData.class)) {
            throw new NullPointerException("need to synchronized(MyMasterData.class) to use this function");
        }
        DataMyScene dataMyScene2 = new DataMyScene();
        String sharedPreference = this.f7076b.getSharedPreference(context, SharedPreferencesStore.Companion.getSceneDataKey());
        Timber.Forest forest = Timber.forest;
        forest.d("Using scenes " + sharedPreference, new Object[0]);
        if (!(sharedPreference == null || sharedPreference.length() == 0)) {
            try {
                dataMyScene = (DataMyScene) this.a.fromJson(sharedPreference, DataMyScene.class);
                try {
                    forest.d("New data scenes struct", new Object[0]);
                } catch (JsonIOException e10) {
                    e7 = e10;
                    AppFeatures.instance.logCriticalException(e7, "restoreScenesFromDatabase - " + sharedPreference);
                    dataMyScene2 = dataMyScene;
                    Intrinsics.checkNotNull(dataMyScene2);
                    masterData.myScenes = dataMyScene2;
                }
            } catch (JsonIOException e11) {
                dataMyScene = dataMyScene2;
                e7 = e11;
            }
            dataMyScene2 = dataMyScene;
        }
        Intrinsics.checkNotNull(dataMyScene2);
        masterData.myScenes = dataMyScene2;
    }

    @Nullable
    public final String e(@Nullable Context context, @NotNull DataMaster masterData) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (!Thread.holdsLock(MyMasterData.class)) {
            throw new NullPointerException("need to synchronized(MyMasterData.class) to use this function");
        }
        String json = this.a.toJson(masterData.myScenes);
        if (json != null && !Intrinsics.areEqual(json, this.f7077c)) {
            Timber.forest.d("Saving scenes " + json, new Object[0]);
            this.f7077c = json;
            this.f7076b.updatePreference(context, SharedPreferencesStore.Companion.getSceneDataKey(), json);
        }
        return json;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private d0() {
        this.a = new Gson();
        this.f7076b = (SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null);
        this.f7077c = "";
    }
}