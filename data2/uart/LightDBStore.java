package com.air.advantage.uart;

import android.content.Context;
import com.air.advantage.AppFeatures;
import com.air.advantage.SharedPreferencesStore;
import com.air.advantage.data.DataLight;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataMyLights;
import com.air.advantage.libraryairconlightjson.CommonFuncs;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

@PurelyImplements({"SMAP\nLightDBStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LightDBStore.kt\ncom/air/advantage/uart/LightDBStore\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,99:1\n1603#2,9:100\n1855#2:109\n1856#2:111\n1612#2:112\n1#3:110\n*S KotlinDebug\n*F\n+ 1 LightDBStore.kt\ncom/air/advantage/uart/LightDBStore\n*L\n72#1:100,9\n72#1:109\n72#1:111\n72#1:112\n72#1:110\n*E\n"})
/* renamed from: com.air.advantage.uart.w */
/* loaded from: classes.dex */
public final class LightDBStore {

    /* renamed from: e */
    @NotNull
    public static final a Companion = new a(null);

    /* renamed from: f, reason: collision with root package name */
    private static final String f7204f = LightDBStore.class.getSimpleName();

    /* renamed from: g, reason: collision with root package name */
    @Nullable
    private static LightDBStore f7205g;

    @NotNull
    private final Gson a;

    /* renamed from: b, reason: collision with root package name */
    @NotNull
    private final SharedPreferencesStore f7206b;

    /* renamed from: c, reason: collision with root package name */
    @NotNull
    private final String f7207c;

    /* renamed from: d, reason: collision with root package name */
    @NotNull
    private String f7208d;

    /* renamed from: com.air.advantage.uart.w$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.w.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        /* renamed from: a */
        public final void destroy() {
            LightDBStore.f7205g = null;
        }

        @NotNull
        public final LightDBStore b() {
            if (LightDBStore.f7205g == null) {
                synchronized (LightDBStore.class) {
                    if (LightDBStore.f7205g == null) {
                        a aVar = LightDBStore.Companion;
                        LightDBStore.f7205g = new LightDBStore(null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            LightDBStore lightDBStore = LightDBStore.f7205g;
            Intrinsics.checkNotNull(lightDBStore);
            return lightDBStore;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.w.<init>():void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ LightDBStore(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @JvmStatic
    public static final void c() {
        Companion.destroy();
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00ad A[LOOP:1: B:28:0x00a7->B:30:0x00ad, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void d(@Nullable Context context, @NotNull DataMaster masterData) {
        DataMyLights dataMyLights;
        JsonIOException e7;
        Iterator it;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (!Thread.holdsLock(MyMasterData.class)) {
            throw new NullPointerException("need to synchronized(MyMasterData.class) to use this function");
        }
        DataMyLights dataMyLights2 = new DataMyLights();
        String sharedPreference = this.f7206b.getSharedPreference(context, SharedPreferencesStore.Companion.H());
        Timber.Forest forest = Timber.forest;
        forest.d("Using lights " + sharedPreference, new Object[0]);
        if (!(sharedPreference == null || sharedPreference.length() == 0)) {
            try {
                Object fromJson = this.a.fromJson(sharedPreference, DataMyLights.class);
                Intrinsics.checkNotNullExpressionValue(fromJson, "fromJson(...)");
                dataMyLights = (DataMyLights) fromJson;
            } catch (JsonIOException e10) {
                dataMyLights = dataMyLights2;
                e7 = e10;
            }
            try {
                forest.d("New data lights struct", new Object[0]);
            } catch (JsonIOException e11) {
                e7 = e11;
                AppFeatures.instance.logCriticalException(e7, "restoreLightFromDatabase - " + sharedPreference);
                dataMyLights2 = dataMyLights;
                TreeMap<String, DataLight> treeMap = dataMyLights2.lights;
                Intrinsics.checkNotNull(treeMap);
                Collection<DataLight> values = treeMap.values();
                Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                ArrayList arrayList = new ArrayList();
                while (r7.hasNext()) {
                }
                it = arrayList.iterator();
                while (it.hasNext()) {
                }
                masterData.myLights = dataMyLights2;
            }
            dataMyLights2 = dataMyLights;
            TreeMap<String, DataLight> treeMap2 = dataMyLights2.lights;
            Intrinsics.checkNotNull(treeMap2);
            Collection<DataLight> values2 = treeMap2.values();
            Intrinsics.checkNotNullExpressionValue(values2, "<get-values>(...)");
            ArrayList arrayList2 = new ArrayList();
            for (DataLight dataLight : values2) {
                if (dataLight != null) {
                    arrayList2.add(dataLight);
                }
            }
            it = arrayList2.iterator();
            while (it.hasNext()) {
                ((DataLight) it.next()).expiryTime = Long.valueOf(CommonFuncs.getUptime() + 70);
            }
        }
        masterData.myLights = dataMyLights2;
    }

    @NotNull
    public final String e(@Nullable Context context, @NotNull DataMaster masterData) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (!Thread.holdsLock(MyMasterData.class)) {
            throw new NullPointerException("need to synchronized(MyMasterData.class) to use this function");
        }
        String json = this.a.toJson(masterData.myLights);
        Timber.Forest forest = Timber.forest;
        forest.d("saveLightsToDatabase called", new Object[0]);
        if (json != null && Intrinsics.areEqual(json, this.f7207c)) {
            AppFeatures.logError(AppFeatures.instance, new NullPointerException("Warning trying to save a blank lights - aborting"), null, 2, null);
            return "";
        }
        if (json != null && !Intrinsics.areEqual(json, this.f7208d)) {
            this.f7208d = json;
            this.f7206b.updatePreference(context, SharedPreferencesStore.Companion.H(), json);
            forest.d("Saving lights - " + json, new Object[0]);
        }
        Intrinsics.checkNotNull(json);
        return json;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private LightDBStore() {
        Gson gson = new Gson();
        this.a = gson;
        this.f7206b = (SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null);
        this.f7208d = "";
        String json = gson.toJson(new DataMyLights());
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        this.f7207c = json;
    }
}