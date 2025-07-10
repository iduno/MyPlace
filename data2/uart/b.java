package com.air.advantage.uart;

import android.content.Context;
import com.air.advantage.AppFeatures;
import com.air.advantage.SharedPreferencesStore;
import com.air.advantage.data.DataAddOnsAllImport;
import com.air.advantage.data.DataMaster;
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
public final class b {

    /* renamed from: e */
    @NotNull
    public static final a Companion = new a(null);

    /* renamed from: f, reason: collision with root package name */
    private static final String f7058f = b.class.getSimpleName();

    /* renamed from: g, reason: collision with root package name */
    @Nullable
    private static b f7059g;

    @NotNull
    private final Gson a;

    /* renamed from: b, reason: collision with root package name */
    @NotNull
    private final SharedPreferencesStore f7060b;

    /* renamed from: c, reason: collision with root package name */
    @NotNull
    private final String f7061c;

    /* renamed from: d, reason: collision with root package name */
    @NotNull
    private String f7062d;

    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.b.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        /* renamed from: a */
        public final void destroy() {
            b.f7059g = null;
        }

        @NotNull
        public final b b() {
            if (b.f7059g == null) {
                synchronized (b.class) {
                    if (b.f7059g == null) {
                        a aVar = b.Companion;
                        b.f7059g = new b(null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            b bVar = b.f7059g;
            Intrinsics.checkNotNull(bVar);
            return bVar;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.b.<init>():void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @JvmStatic
    public static final void c() {
        Companion.destroy();
    }

    public final void d(@Nullable Context context, @NotNull DataMaster masterData) {
        DataAddOnsAllImport dataAddOnsAllImport;
        JsonIOException e7;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (!Thread.holdsLock(MyMasterData.class)) {
            throw new NullPointerException("need to synchronized(MyMasterData.class) to use this function");
        }
        DataAddOnsAllImport dataAddOnsAllImport2 = new DataAddOnsAllImport();
        String sharedPreference = this.f7060b.getSharedPreference(context, SharedPreferencesStore.Companion.getAddOnDataKey());
        Timber.Forest forest = Timber.forest;
        forest.d("Using add on " + sharedPreference, new Object[0]);
        if (!(sharedPreference == null || sharedPreference.length() == 0)) {
            try {
                dataAddOnsAllImport = (DataAddOnsAllImport) this.a.fromJson(sharedPreference, DataAddOnsAllImport.class);
                try {
                    forest.d("New data external devices object", new Object[0]);
                } catch (JsonIOException e10) {
                    e7 = e10;
                    AppFeatures.instance.logCriticalException(e7, "restoreAddOnFromDatabase - " + sharedPreference);
                    dataAddOnsAllImport2 = dataAddOnsAllImport;
                    Intrinsics.checkNotNull(dataAddOnsAllImport2);
                    masterData.myAddOns = dataAddOnsAllImport2;
                }
            } catch (JsonIOException e11) {
                dataAddOnsAllImport = dataAddOnsAllImport2;
                e7 = e11;
            }
            dataAddOnsAllImport2 = dataAddOnsAllImport;
        }
        Intrinsics.checkNotNull(dataAddOnsAllImport2);
        masterData.myAddOns = dataAddOnsAllImport2;
    }

    @Nullable
    public final String e(@Nullable Context context, @NotNull DataMaster masterData) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (!Thread.holdsLock(MyMasterData.class)) {
            throw new NullPointerException("need to synchronized(MyMasterData.class) to use this function");
        }
        String json = this.a.toJson(masterData.myAddOns);
        Timber.forest.d("Saving add on " + json, new Object[0]);
        if (json != null && !Intrinsics.areEqual(json, this.f7062d)) {
            this.f7062d = json;
            this.f7060b.updatePreference(context, SharedPreferencesStore.Companion.getAddOnDataKey(), json);
        }
        return json;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private b() {
        Gson gson = new Gson();
        this.a = gson;
        this.f7060b = (SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null);
        this.f7062d = "";
        String json = gson.toJson(new DataAddOnsAllImport());
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        this.f7061c = json;
    }
}