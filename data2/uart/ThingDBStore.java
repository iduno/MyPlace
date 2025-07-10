package com.air.advantage.uart;

import android.content.Context;
import com.air.advantage.AppFeatures;
import com.air.advantage.SharedPreferencesStore;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataMyThing;
import com.air.advantage.data.DataThings;
import com.air.advantage.libraryairconlightjson.CommonFuncs;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import java.util.ArrayList;
import java.util.Collection;
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

@PurelyImplements({"SMAP\nThingDBStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ThingDBStore.kt\ncom/air/advantage/uart/ThingDBStore\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,102:1\n1603#2,9:103\n1855#2:112\n1856#2:114\n1612#2:115\n1#3:113\n*S KotlinDebug\n*F\n+ 1 ThingDBStore.kt\ncom/air/advantage/uart/ThingDBStore\n*L\n71#1:103,9\n71#1:112\n71#1:114\n71#1:115\n71#1:113\n*E\n"})
/* renamed from: com.air.advantage.uart.f0 */
/* loaded from: classes.dex */
public final class ThingDBStore {

    /* renamed from: e */
    @NotNull
    public static final a Companion = new a(null);

    /* renamed from: f, reason: collision with root package name */
    private static final String f7084f = ThingDBStore.class.getSimpleName();

    /* renamed from: g, reason: collision with root package name */
    @Nullable
    private static ThingDBStore f7085g;

    @NotNull
    private final Gson a;

    /* renamed from: b, reason: collision with root package name */
    @NotNull
    private final SharedPreferencesStore f7086b;

    /* renamed from: c, reason: collision with root package name */
    @NotNull
    private final String f7087c;

    /* renamed from: d, reason: collision with root package name */
    @NotNull
    private String f7088d;

    /* renamed from: com.air.advantage.uart.f0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.f0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        /* renamed from: a */
        public final void destroy() {
            ThingDBStore.f7085g = null;
        }

        @NotNull
        public final ThingDBStore b() {
            if (ThingDBStore.f7085g == null) {
                synchronized (ThingDBStore.class) {
                    if (ThingDBStore.f7085g == null) {
                        a aVar = ThingDBStore.Companion;
                        ThingDBStore.f7085g = new ThingDBStore(null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            ThingDBStore thingDBStore = ThingDBStore.f7085g;
            Intrinsics.checkNotNull(thingDBStore);
            return thingDBStore;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.f0.<init>():void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ ThingDBStore(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @JvmStatic
    public static final void c() {
        Companion.destroy();
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void d(@Nullable Context context, @NotNull DataMaster masterData) {
        DataThings dataThings;
        JsonIOException e7;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (!Thread.holdsLock(MyMasterData.class)) {
            throw new NullPointerException("need to synchronized(MyMasterData.class) to use this function");
        }
        DataThings dataThings2 = new DataThings();
        String sharedPreference = this.f7086b.getSharedPreference(context, SharedPreferencesStore.Companion.getThingDataKey());
        Timber.Forest forest = Timber.forest;
        forest.d("Using things " + sharedPreference, new Object[0]);
        if (!(sharedPreference == null || sharedPreference.length() == 0)) {
            try {
                Object fromJson = this.a.fromJson(sharedPreference, DataThings.class);
                Intrinsics.checkNotNullExpressionValue(fromJson, "fromJson(...)");
                dataThings = (DataThings) fromJson;
            } catch (JsonIOException e10) {
                dataThings = dataThings2;
                e7 = e10;
            }
            try {
                forest.d("New data things struct", new Object[0]);
            } catch (JsonIOException e11) {
                e7 = e11;
                AppFeatures.instance.logCriticalException(e7, "restoreThingsFromDatabase - " + sharedPreference);
                dataThings2 = dataThings;
                TreeMap<String, DataMyThing> treeMap = dataThings2.things;
                Intrinsics.checkNotNull(treeMap);
                Collection<DataMyThing> values = treeMap.values();
                Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                ArrayList<DataMyThing> arrayList = new ArrayList();
                while (r7.hasNext()) {
                }
                while (r7.hasNext()) {
                }
                masterData.myThings = dataThings2;
            }
            dataThings2 = dataThings;
            TreeMap<String, DataMyThing> treeMap2 = dataThings2.things;
            Intrinsics.checkNotNull(treeMap2);
            Collection<DataMyThing> values2 = treeMap2.values();
            Intrinsics.checkNotNullExpressionValue(values2, "<get-values>(...)");
            ArrayList<DataMyThing> arrayList2 = new ArrayList();
            for (DataMyThing dataMyThing : values2) {
                if (dataMyThing != null) {
                    arrayList2.add(dataMyThing);
                }
            }
            for (DataMyThing dataMyThing2 : arrayList2) {
                if (dataMyThing2.thisIsRFDevice) {
                    dataMyThing2.expiryTime = Long.valueOf(CommonFuncs.getUptime() + HandlerLights.nonRFExpiry);
                } else {
                    dataMyThing2.expiryTime = Long.valueOf(CommonFuncs.getUptime() + 70);
                }
            }
        }
        masterData.myThings = dataThings2;
    }

    @Nullable
    public final String e(@Nullable Context context, @NotNull DataMaster masterData) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (!Thread.holdsLock(MyMasterData.class)) {
            throw new NullPointerException("need to synchronized(MyMasterData.class) to use this function");
        }
        String json = this.a.toJson(masterData.myThings);
        Timber.forest.d("Saving things " + json, new Object[0]);
        if (json != null && Intrinsics.areEqual(json, this.f7087c)) {
            AppFeatures.logError(AppFeatures.instance, new NullPointerException("Warning saving a blank things"), null, 2, null);
            return "";
        }
        if (json != null && !Intrinsics.areEqual(json, this.f7088d)) {
            this.f7088d = json;
            this.f7086b.updatePreference(context, SharedPreferencesStore.Companion.getThingDataKey(), json);
        }
        return json;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private ThingDBStore() {
        Gson gson = new Gson();
        this.a = gson;
        this.f7086b = (SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null);
        this.f7088d = "";
        String json = gson.toJson(new DataThings());
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        this.f7087c = json;
    }
}