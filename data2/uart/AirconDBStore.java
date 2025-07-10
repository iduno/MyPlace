package com.air.advantage.uart;

import android.content.Context;
import com.air.advantage.AppFeatures;
import com.air.advantage.SharedPreferencesStore;
import com.air.advantage.data.DataAirconInfo;
import com.air.advantage.data.DataAirconSystem;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataSystem;
import com.air.advantage.data.SnapShot;
import com.air.advantage.libraryairconlightjson.CommonFuncs;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

@PurelyImplements({"SMAP\nAirconDBStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AirconDBStore.kt\ncom/air/advantage/uart/AirconDBStore\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,306:1\n1002#2,2:307\n*S KotlinDebug\n*F\n+ 1 AirconDBStore.kt\ncom/air/advantage/uart/AirconDBStore\n*L\n201#1:307,2\n*E\n"})
/* renamed from: com.air.advantage.uart.c */
/* loaded from: classes.dex */
public final class AirconDBStore {

    /* renamed from: g */
    @NotNull
    public static final Companion Companion = new Companion(null);

    /* renamed from: h, reason: collision with root package name */
    public static final long f7066h = 80;

    /* renamed from: i, reason: collision with root package name */
    public static final long f7067i = 260;

    /* renamed from: j */
    @Nullable
    private static AirconDBStore INSTANCE;

    /* renamed from: a */
    @NotNull
    private final ArrayList<String> airconKeys;

    /* renamed from: b */
    @NotNull
    private final ArrayList<String> snapShotKeys;

    /* renamed from: c */
    @NotNull
    private final Gson gson;

    /* renamed from: d */
    @NotNull
    private final SharedPreferencesStore sharedPreferencesStore;

    /* renamed from: e */
    @NotNull
    private final HashMap<String, String> dataAirconKeys;

    /* renamed from: f */
    @NotNull
    private final String snapShot;

    /* renamed from: com.air.advantage.uart.c$a */
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.c.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        /* renamed from: a */
        public final void initialize() {
            AirconDBStore.INSTANCE = null;
        }

        @NotNull
        /* renamed from: b */
        public final AirconDBStore getInstance(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (AirconDBStore.INSTANCE == null) {
                synchronized (AirconDBStore.class) {
                    if (AirconDBStore.INSTANCE == null) {
                        Companion companion = AirconDBStore.Companion;
                        Context applicationContext = context.getApplicationContext();
                        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                        AirconDBStore.INSTANCE = new AirconDBStore(applicationContext, null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            AirconDBStore airconDBStore = AirconDBStore.INSTANCE;
            Intrinsics.checkNotNull(airconDBStore);
            return airconDBStore;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private Companion() {
        }
    }

    @PurelyImplements({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 AirconDBStore.kt\ncom/air/advantage/uart/AirconDBStore\n*L\n1#1,328:1\n201#2:329\n*E\n"})
    /* renamed from: com.air.advantage.uart.c$b */
    public static final class Comparisons<T> implements Comparator {
        /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: T */
        /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: T */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Comparator
        public final int compare(T t7, T t10) {
            return ComparisonsKt__ComparisonsKt.compareValues((String) t7, (String) t10);
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 android.content.Context) A[MD:(android.content.Context):void (m)] (LINE:1) call: com.air.advantage.uart.c.<init>(android.content.Context):void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ AirconDBStore(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    @JvmStatic
    /* renamed from: c */
    public static final void initialize() {
        Companion.initialize();
    }

    /* renamed from: h */
    private final void updateAIrconKeys(Context context) {
        ArrayList<String> jsonFromPreference = this.sharedPreferencesStore.getJsonFromPreference(context, SharedPreferencesStore.Companion.getAirconIds());
        this.airconKeys.clear();
        if (jsonFromPreference != null) {
            this.airconKeys.addAll(jsonFromPreference);
        }
    }

    /* renamed from: l */
    private final void updateSnapshotKeys(Context context) {
        ArrayList<String> jsonFromPreference = this.sharedPreferencesStore.getJsonFromPreference(context, SharedPreferencesStore.Companion.getStoredSnapshotIds());
        this.snapShotKeys.clear();
        if (jsonFromPreference != null) {
            Iterator<String> it = jsonFromPreference.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (next != null) {
                    String sharedPreference = this.sharedPreferencesStore.getSharedPreference(context, SharedPreferencesStore.Companion.getSnapshotDataKey() + next);
                    if (!(sharedPreference == null || sharedPreference.length() == 0) && !Intrinsics.areEqual(sharedPreference, this.snapShot)) {
                        this.snapShotKeys.add(next);
                    }
                }
            }
        }
    }

    /* renamed from: n */
    private final void updateAircon(Context context) {
        this.sharedPreferencesStore.updatePreference(context, SharedPreferencesStore.Companion.getAirconIds(), this.gson.toJson(this.airconKeys));
    }

    /* renamed from: o */
    private final void updateSnapShot(Context context) {
        this.sharedPreferencesStore.updatePreference(context, SharedPreferencesStore.Companion.getStoredSnapshotIds(), this.gson.toJson(this.snapShotKeys));
    }

    /* renamed from: d */
    public final void updateSnapshot(@Nullable Context context, @Nullable String str) {
        this.snapShotKeys.remove(str);
        this.sharedPreferencesStore.updatePreference(context, SharedPreferencesStore.Companion.getSnapshotDataKey() + str, null);
    }

    /* renamed from: e */
    public final void disableAirConSystem(@Nullable Context context, @Nullable String str, @Nullable DataAirconSystem dataAirconSystem) {
        Intrinsics.checkNotNull(dataAirconSystem);
        Boolean bool = dataAirconSystem.info.enabled;
        if (bool != null) {
            Intrinsics.checkNotNull(bool);
            if (!bool.booleanValue()) {
                return;
            }
        }
        dataAirconSystem.info.enabled = Boolean.FALSE;
        updateStore(context, str, dataAirconSystem);
    }

    @NotNull
    /* renamed from: f */
    public final DataAirconSystem getAircon(@Nullable Context context, @NotNull String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        DataAirconSystem updateAirconSystemExpiry = updateAirconSystemExpiry(context, id);
        if (updateAirconSystemExpiry == null) {
            updateAirconSystemExpiry = new DataAirconSystem(id);
            updateAirconSystemExpiry.info.Initialisation();
        }
        DataAirconInfo dataAirconInfo = updateAirconSystemExpiry.info;
        dataAirconInfo.uid = id;
        dataAirconInfo.enabled = Boolean.TRUE;
        updateStore(context, id, updateAirconSystemExpiry);
        return updateAirconSystemExpiry;
    }

    @Nullable
    /* renamed from: g */
    public final DataAirconSystem getAirconSystem(@Nullable Context context, @NotNull String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        try {
            return (DataAirconSystem) this.gson.fromJson(this.sharedPreferencesStore.getSharedPreference(context, SharedPreferencesStore.Companion.getAirconDataKey() + id), DataAirconSystem.class);
        } catch (JsonIOException e7) {
            AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
            return null;
        }
    }

    /* renamed from: i */
    public final void updateSnapShots(@Nullable Context context, @NotNull DataMaster masterData) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        Iterator<String> it = this.snapShotKeys.iterator();
        while (it.hasNext()) {
            String next = it.next();
            SnapShot snapShot = getSnapShot(context, next);
            if (snapShot != null) {
                masterData.snapshots.put(next, snapShot);
            }
        }
        masterData.system.noOfSnapshots = Integer.valueOf(masterData.snapshots.size());
    }

    @Nullable
    /* renamed from: j */
    public final DataAirconSystem updateAirconSystemExpiry(@Nullable Context context, @NotNull String id) {
        DataAirconSystem dataAirconSystem;
        Intrinsics.checkNotNullParameter(id, "id");
        try {
            dataAirconSystem = (DataAirconSystem) this.gson.fromJson(this.sharedPreferencesStore.getSharedPreference(context, SharedPreferencesStore.Companion.getAirconDataKey() + id), DataAirconSystem.class);
        } catch (JsonIOException e7) {
            AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
            dataAirconSystem = null;
        }
        if (dataAirconSystem != null) {
            dataAirconSystem.info.expireTime = Long.valueOf(CommonFuncs.getUptime() + 80);
        }
        return dataAirconSystem;
    }

    @Nullable
    /* renamed from: k */
    public final SnapShot getSnapShot(@Nullable Context context, @Nullable String str) {
        try {
            return (SnapShot) this.gson.fromJson(this.sharedPreferencesStore.getSharedPreference(context, SharedPreferencesStore.Companion.getSnapshotDataKey() + str), SnapShot.class);
        } catch (JsonIOException e7) {
            AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
            return null;
        }
    }

    @NotNull
    /* renamed from: m */
    public final DataSystem getDataSystem(@Nullable Context context) {
        DataSystem dataSystem;
        try {
            dataSystem = (DataSystem) this.gson.fromJson(this.sharedPreferencesStore.getSharedPreference(context, SharedPreferencesStore.Companion.getSystemDataKey()), DataSystem.class);
        } catch (JsonIOException e7) {
            AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
            dataSystem = null;
        }
        if (dataSystem == null) {
            dataSystem = new DataSystem();
        }
        dataSystem.isValidSuburbTemp = Boolean.FALSE;
        return dataSystem;
    }

    @NotNull
    /* renamed from: p */
    public final String updateSnapShot(@Nullable Context context, @Nullable String str, @NotNull SnapShot snapShot) {
        Intrinsics.checkNotNullParameter(snapShot, "snapShot");
        SnapShot snapShot2 = getSnapShot(context, str);
        if (snapShot2 == null) {
            snapShot2 = new SnapShot(snapShot.name, snapShot.enabled, snapShot.snapshotId, snapShot.activeDays, snapShot.startTime, snapShot.stopTime, snapShot.runNow, snapShot.summary);
            snapShot2.CANmsgs = snapShot.CANmsgs;
            snapShot2.aircons = snapShot.aircons;
        } else {
            snapShot2.snapshotId = str;
            snapShot2.name = snapShot.name;
            snapShot2.enabled = snapShot.enabled;
            snapShot2.activeDays = snapShot.activeDays;
            snapShot2.startTime = snapShot.startTime;
            snapShot2.stopTime = snapShot.stopTime;
            snapShot2.runNow = snapShot.runNow;
            snapShot2.CANmsgs = snapShot.CANmsgs;
            snapShot2.summary = snapShot.summary;
            snapShot2.aircons = snapShot.aircons;
        }
        if (!this.snapShotKeys.contains(str)) {
            this.snapShotKeys.add(str);
            updateSnapShot(context);
        }
        return saveSnapshot(context, snapShot2.snapshotId, snapShot2);
    }

    @NotNull
    /* renamed from: q */
    public final String update(@Nullable Context context, @NotNull DataSystem dataSystem) {
        Intrinsics.checkNotNullParameter(dataSystem, "dataSystem");
        String generateJSONString = dataSystem.generateJSONString();
        this.sharedPreferencesStore.updatePreference(context, SharedPreferencesStore.Companion.getSystemDataKey(), generateJSONString);
        return generateJSONString;
    }

    @NotNull
    /* renamed from: r */
    public final String updateStore(@Nullable Context context, @Nullable String str, @Nullable DataAirconSystem dataAirconSystem) {
        Timber.Forest forest = Timber.forest;
        forest.d("updateStore " + str, new Object[0]);
        if (!this.airconKeys.contains(str)) {
            this.airconKeys.add(str);
            ArrayList<String> arrayList = this.airconKeys;
            if (arrayList.size() > 1) {
                CollectionsKt__MutableCollectionsJVMKt.m0(arrayList, new Comparisons());
            }
            updateAircon(context);
        }
        Intrinsics.checkNotNull(dataAirconSystem);
        String json = dataAirconSystem.gsonForDB.toJson(dataAirconSystem);
        if (this.dataAirconKeys.containsKey(str) && Intrinsics.areEqual(json, this.dataAirconKeys.get(str))) {
            forest.i("updateStore dataAircon - identical data", new Object[0]);
        }
        HashMap<String, String> hashMap = this.dataAirconKeys;
        Intrinsics.checkNotNull(json);
        hashMap.put(str, json);
        this.sharedPreferencesStore.updatePreference(context, SharedPreferencesStore.Companion.getAirconDataKey() + str, json);
        return json;
    }

    @NotNull
    /* renamed from: s */
    public final String saveSnapshot(@Nullable Context context, @Nullable String str, @NotNull SnapShot snapShot) {
        Intrinsics.checkNotNullParameter(snapShot, "snapShot");
        if (str == null) {
            throw new NullPointerException("Trying to save a snapshot with a null id!");
        }
        if (!this.snapShotKeys.contains(str)) {
            this.snapShotKeys.add(str);
            updateSnapShot(context);
        }
        String generateJSONString = snapShot.generateJSONString();
        this.sharedPreferencesStore.updatePreference(context, SharedPreferencesStore.Companion.getSnapshotDataKey() + str, generateJSONString);
        return generateJSONString;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private AirconDBStore(Context context) {
        this.airconKeys = new ArrayList<>();
        this.snapShotKeys = new ArrayList<>();
        Gson gson = new Gson();
        this.gson = gson;
        this.sharedPreferencesStore = (SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null);
        this.dataAirconKeys = new HashMap<>();
        String json = gson.toJson(new SnapShot());
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        this.snapShot = json;
        updateAIrconKeys(context);
        updateSnapshotKeys(context);
    }
}