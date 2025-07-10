package com.air.advantage.uart;

import android.content.Context;
import com.air.advantage.AppFeatures;
import com.air.advantage.SharedPreferencesStore;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataSensor;
import com.air.advantage.data.DataSensorsAllImport;
import com.air.advantage.libraryairconlightjson.CommonFuncs;
import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

@PurelyImplements({"SMAP\nSensorDBStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SensorDBStore.kt\ncom/air/advantage/uart/SensorDBStore\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,106:1\n1603#2,9:107\n1855#2:116\n1856#2:118\n1612#2:119\n1#3:117\n*S KotlinDebug\n*F\n+ 1 SensorDBStore.kt\ncom/air/advantage/uart/SensorDBStore\n*L\n84#1:107,9\n84#1:116\n84#1:118\n84#1:119\n84#1:117\n*E\n"})
/* renamed from: com.air.advantage.uart.e0 */
/* loaded from: classes.dex */
public final class SensorDBStore {

    /* renamed from: e, reason: collision with root package name */
    @NotNull
    public static final a f7078e = new a(null);

    /* renamed from: f, reason: collision with root package name */
    private static final String f7079f = SensorDBStore.class.getSimpleName();

    /* renamed from: g, reason: collision with root package name */
    @Nullable
    private static SensorDBStore f7080g;
    private final Gson a;

    /* renamed from: b, reason: collision with root package name */
    @NotNull
    private final SharedPreferencesStore f7081b;

    /* renamed from: c, reason: collision with root package name */
    @NotNull
    private final String f7082c;

    /* renamed from: d, reason: collision with root package name */
    @NotNull
    private String f7083d;

    /* renamed from: com.air.advantage.uart.e0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.e0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final SensorDBStore a() {
            if (SensorDBStore.f7080g == null) {
                synchronized (SensorDBStore.class) {
                    if (SensorDBStore.f7080g == null) {
                        a aVar = SensorDBStore.f7078e;
                        SensorDBStore.f7080g = new SensorDBStore(null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            SensorDBStore sensorDBStore = SensorDBStore.f7080g;
            Intrinsics.checkNotNull(sensorDBStore);
            return sensorDBStore;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* renamed from: com.air.advantage.uart.e0$b */
    public static final class b implements ExclusionStrategy {
        b() {
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipClass(@Nullable Class<?> cls) {
            return false;
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipField(@NotNull FieldAttributes fieldAttributes) {
            Intrinsics.checkNotNullParameter(fieldAttributes, "fieldAttributes");
            JsonExporter jsonExporter = (JsonExporter) fieldAttributes.getAnnotation(JsonExporter.class);
            return (jsonExporter == null || jsonExporter.saveThis()) ? false : true;
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.e0.<init>():void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ SensorDBStore(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00ad A[LOOP:1: B:28:0x00a7->B:30:0x00ad, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void c(@Nullable Context context, @NotNull DataMaster masterData) {
        DataSensorsAllImport dataSensorsAllImport;
        JsonIOException e7;
        Iterator it;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (!Thread.holdsLock(MyMasterData.class)) {
            throw new NullPointerException("need to synchronized(MyMasterData.class) to use this function");
        }
        DataSensorsAllImport dataSensorsAllImport2 = new DataSensorsAllImport();
        String sharedPreference = this.f7081b.getSharedPreference(context, SharedPreferencesStore.Companion.getSensorDataKey());
        Timber.Forest forest = Timber.forest;
        forest.d("Using sensors " + sharedPreference, new Object[0]);
        if (!(sharedPreference == null || sharedPreference.length() == 0)) {
            try {
                Object fromJson = this.a.fromJson(sharedPreference, DataSensorsAllImport.class);
                Intrinsics.checkNotNullExpressionValue(fromJson, "fromJson(...)");
                dataSensorsAllImport = (DataSensorsAllImport) fromJson;
            } catch (JsonIOException e10) {
                dataSensorsAllImport = dataSensorsAllImport2;
                e7 = e10;
            }
            try {
                forest.d("New data sensors object", new Object[0]);
            } catch (JsonIOException e11) {
                e7 = e11;
                AppFeatures.instance.logCriticalException(e7, "restoreSensorsFromDatabase - " + sharedPreference);
                dataSensorsAllImport2 = dataSensorsAllImport;
                HashMap<String, DataSensor> hashMap = dataSensorsAllImport2.sensors;
                Intrinsics.checkNotNull(hashMap);
                Collection<DataSensor> values = hashMap.values();
                Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                ArrayList arrayList = new ArrayList();
                while (r7.hasNext()) {
                }
                it = arrayList.iterator();
                while (it.hasNext()) {
                }
                masterData.mySensors = dataSensorsAllImport2;
            }
            dataSensorsAllImport2 = dataSensorsAllImport;
            HashMap<String, DataSensor> hashMap2 = dataSensorsAllImport2.sensors;
            Intrinsics.checkNotNull(hashMap2);
            Collection<DataSensor> values2 = hashMap2.values();
            Intrinsics.checkNotNullExpressionValue(values2, "<get-values>(...)");
            ArrayList arrayList2 = new ArrayList();
            for (DataSensor dataSensor : values2) {
                if (dataSensor != null) {
                    arrayList2.add(dataSensor);
                }
            }
            it = arrayList2.iterator();
            while (it.hasNext()) {
                ((DataSensor) it.next()).expiryTime = Long.valueOf(CommonFuncs.getUptime() + 10);
            }
        }
        masterData.mySensors = dataSensorsAllImport2;
    }

    @Nullable
    public final String d(@Nullable Context context, @NotNull DataMaster masterData) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (!Thread.holdsLock(MyMasterData.class)) {
            throw new NullPointerException("need to synchronized(MyMasterData.class) to use this function");
        }
        String json = this.a.toJson(masterData.mySensors);
        Timber.Forest forest = Timber.forest;
        forest.d("Saving sensors " + json, new Object[0]);
        if (json != null && !Intrinsics.areEqual(json, this.f7083d)) {
            forest.d("Saving to disk", new Object[0]);
            this.f7083d = json;
            this.f7081b.updatePreference(context, SharedPreferencesStore.Companion.getSensorDataKey(), json);
        }
        return json;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private SensorDBStore() {
        Gson create = new GsonBuilder().addSerializationExclusionStrategy(new b()).create();
        this.a = create;
        this.f7081b = (SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null);
        this.f7083d = "";
        String json = create.toJson(new DataSensorsAllImport());
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        this.f7082c = json;
    }
}