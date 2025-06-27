package com.air.advantage.data;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.air.advantage.AppFeatures;
import com.air.advantage.MyApp;
import com.air.advantage.data.DataAirconSystem;
import com.air.advantage.data.SnapShot;
import com.air.advantage.libraryairconlightjson.AirconMode;
import com.air.advantage.libraryairconlightjson.FanStatus;
import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.air.advantage.libraryairconlightjson.LightState;
import com.air.advantage.libraryairconlightjson.SystemState;
import com.air.advantage.libraryairconlightjson.UartConstants;
import com.air.advantage.libraryairconlightjson.ZoneState;
import com.air.advantage.myair5.R;
import com.air.advantage.sonos.Sonos;
import com.bosma.api.lab.net.UrlConfig;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.appindexing.builders.AlarmBuilder;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

@PurelyImplements({"SMAP\nDataScene.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DataScene.kt\ncom/air/advantage/data/DataScene\n+ 2 Strings.kt\nkotlin/text/StringsKt__StringsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1309:1\n107#2:1310\n79#2,22:1311\n1002#3,2:1333\n1002#3,2:1335\n1002#3,2:1337\n1002#3,2:1339\n1002#3,2:1341\n*S KotlinDebug\n*F\n+ 1 DataScene.kt\ncom/air/advantage/data/DataScene\n*L\n100#1:1310\n100#1:1311,22\n678#1:1333,2\n810#1:1335,2\n879#1:1337,2\n1000#1:1339,2\n1161#1:1341,2\n*E\n"})
/* renamed from: com.air.advantage.data.k0 */
/* loaded from: classes.dex */
public class DataScene {
    public static final int MAXIMUM_START_AND_STOP_TIME_VALUE = 1440;

    @Nullable
    @SerializedName("activeDays")
    @JvmField
    public Integer activeDays;

    @Nullable
    @SerializedName("airconStopTime")
    @JvmField
    public Integer airconStopTime;

    @Nullable
    @SerializedName("airconStopTimeEnabled")
    @JvmField
    public Boolean airconStopTimeEnabled;

    @Nullable
    @SerializedName("aircons")
    @JvmField
    public HashMap aircons;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("canMessages")
    @JvmField
    public String canMessages;

    @SerializedName("id")
    @NotNull
    @JvmField
    public String id;

    @Nullable
    @SerializedName("lights")
    @JvmField
    public HashMap lights;

    @Nullable
    @SerializedName("monitors")
    @JvmField
    public HashMap monitors;

    @Nullable
    @SerializedName("myTimeEnabled")
    @JvmField
    public Boolean myTimeEnabled;

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    @JvmField
    public String name;

    @Nullable
    @SerializedName("runNow")
    @JvmField
    public Boolean runNow;

    @Nullable
    @SerializedName("sonos")
    @JvmField
    public HashMap sonos;

    @Nullable
    @SerializedName("startTime")
    @JvmField
    public Integer startTime;

    @Nullable
    @SerializedName("summary")
    @JvmField
    public String summary;

    @Nullable
    @SerializedName("things")
    @JvmField
    public HashMap things;

    @Nullable
    @SerializedName("timerEnabled")
    @JvmField
    public Boolean timerEnabled;

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = DataScene.class.getSimpleName();

    @NotNull
    private static final String[] SCENE_DAYS_STRING = {AlarmBuilder.MONDAY, AlarmBuilder.TUESDAY, AlarmBuilder.WEDNESDAY, AlarmBuilder.THURSDAY, AlarmBuilder.FRIDAY, AlarmBuilder.SATURDAY, AlarmBuilder.SUNDAY};

    /* renamed from: com.air.advantage.data.k0$a */
    public static final class a {
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Removed duplicated region for block: B:56:0x00b8  */
        @NotNull
        @JvmStatic
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final SnapShot.b sceneIsScheduledAtGivenTime(@Nullable DataScene dataScene, int i10, int i11) {
            Boolean bool;
            Integer num;
            Boolean bool2;
            Integer num2;
            Integer num3;
            if (dataScene != null && dataScene.id != null && (bool = dataScene.timerEnabled) != null && dataScene.activeDays != null && dataScene.startTime != null) {
                Intrinsics.checkNotNull(bool);
                if (bool.booleanValue() && ((num = dataScene.activeDays) == null || num.intValue() != 0)) {
                    Integer num4 = dataScene.activeDays;
                    Intrinsics.checkNotNull(num4);
                    int intValue = num4.intValue();
                    Integer num5 = dataScene.startTime;
                    Intrinsics.checkNotNull(num5);
                    if (num5.intValue() >= 0 && (num3 = dataScene.startTime) != null && num3.intValue() == i11 && ((1 << (i10 - 1)) & intValue) != 0) {
                        return SnapShot.b.startTimeIsScheduled;
                    }
                    if (dataScene.airconStopTime != null && (bool2 = dataScene.airconStopTimeEnabled) != null && dataScene.aircons != null) {
                        Intrinsics.checkNotNull(bool2);
                        if (bool2.booleanValue()) {
                            HashMap hashMap = dataScene.aircons;
                            Intrinsics.checkNotNull(hashMap);
                            if (hashMap.size() > 0) {
                                Integer num6 = dataScene.startTime;
                                Intrinsics.checkNotNull(num6);
                                if (num6.intValue() >= 0) {
                                    Integer num7 = dataScene.airconStopTime;
                                    Intrinsics.checkNotNull(num7);
                                    if (num7.intValue() >= 0) {
                                        Integer num8 = dataScene.airconStopTime;
                                        Intrinsics.checkNotNull(num8);
                                        int intValue2 = num8.intValue();
                                        Integer num9 = dataScene.startTime;
                                        Intrinsics.checkNotNull(num9);
                                        if (intValue2 <= num9.intValue()) {
                                            Integer num10 = dataScene.airconStopTime;
                                            if (num10 != null && num10.intValue() == i11) {
                                                if (i10 == 1) {
                                                    if ((intValue & 64) != 0) {
                                                        return SnapShot.b.stopTimeIsScheduled;
                                                    }
                                                } else if (((1 << (i10 - 2)) & intValue) != 0) {
                                                    return SnapShot.b.stopTimeIsScheduled;
                                                }
                                            }
                                        } else {
                                            Integer num11 = dataScene.airconStopTime;
                                            Intrinsics.checkNotNull(num11);
                                            if (num11.intValue() >= 0 && (num2 = dataScene.airconStopTime) != null && num2.intValue() == i11 && ((1 << (i10 - 1)) & intValue) != 0) {
                                                return SnapShot.b.stopTimeIsScheduled;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return SnapShot.b.notScheduled;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    @PurelyImplements({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 DataScene.kt\ncom/air/advantage/data/DataScene\n*L\n1#1,328:1\n678#2:329\n*E\n"})
    /* renamed from: com.air.advantage.data.k0$b */
    public static final class b<T> implements Comparator {
        public final int compare(Object obj, Object obj2) {
            return ComparisonsKt__ComparisonsKt.compareValues((String) obj, (String) obj2);
        }
    }

    @PurelyImplements({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 DataScene.kt\ncom/air/advantage/data/DataScene\n*L\n1#1,328:1\n810#2:329\n*E\n"})
    /* renamed from: com.air.advantage.data.k0$c */
    public static final class c<T> implements Comparator {
        public final int compare(Object obj, Object obj2) {
            return ComparisonsKt__ComparisonsKt.compareValues((String) obj, (String) obj2);
        }
    }

    @PurelyImplements({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 DataScene.kt\ncom/air/advantage/data/DataScene\n*L\n1#1,328:1\n879#2:329\n*E\n"})
    /* renamed from: com.air.advantage.data.k0$d */
    public static final class d<T> implements Comparator {
        public final int compare(Object obj, Object obj2) {
            return ComparisonsKt__ComparisonsKt.compareValues((String) obj, (String) obj2);
        }
    }

    @PurelyImplements({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 DataScene.kt\ncom/air/advantage/data/DataScene\n*L\n1#1,328:1\n1000#2:329\n*E\n"})
    /* renamed from: com.air.advantage.data.k0$e */
    public static final class e<T> implements Comparator {
        public final int compare(Object obj, Object obj2) {
            return ComparisonsKt__ComparisonsKt.compareValues((String) obj, (String) obj2);
        }
    }

    @PurelyImplements({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 DataScene.kt\ncom/air/advantage/data/DataScene\n*L\n1#1,328:1\n1161#2:329\n*E\n"})
    /* renamed from: com.air.advantage.data.k0$f */
    public static final class f<T> implements Comparator {
        public final int compare(Object obj, Object obj2) {
            return ComparisonsKt__ComparisonsKt.compareValues((String) obj, (String) obj2);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public DataScene() {
        this.id = "";
    }

    private final void doUpdate(Context context) {
        Timber.forest.d("data has been updated for LightScene " + this.id + " " + this.name, new Object[0]);
        Intent intent = new Intent(UartConstants.LIGHT_SCENE_UPDATE);
        intent.putExtra("sceneID", this.id);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private final boolean isAirconsCollectionEqualForScenePurpose(HashMap hashMap) {
        HashMap hashMap2 = this.aircons;
        Intrinsics.checkNotNull(hashMap2);
        int size = hashMap2.size();
        Intrinsics.checkNotNull(hashMap);
        if (size != hashMap.size()) {
            return false;
        }
        HashMap hashMap3 = this.aircons;
        Intrinsics.checkNotNull(hashMap3);
        if (hashMap3.size() == 0) {
            return true;
        }
        HashMap hashMap4 = this.aircons;
        Intrinsics.checkNotNull(hashMap4);
        Iterator it = hashMap4.keySet().iterator();
        while (it.hasNext()) {
            if (!hashMap.containsKey((String) it.next())) {
                return false;
            }
        }
        HashMap hashMap5 = this.aircons;
        Intrinsics.checkNotNull(hashMap5);
        Iterator it2 = new ArrayList(hashMap5.keySet()).iterator();
        while (it2.hasNext()) {
            String str = (String) it2.next();
            HashMap hashMap6 = this.aircons;
            Intrinsics.checkNotNull(hashMap6);
            DataAirconSystem dataAirconSystem = (DataAirconSystem) hashMap6.get(str);
            DataAirconSystem dataAirconSystem2 = (DataAirconSystem) hashMap.get(str);
            Intrinsics.checkNotNull(dataAirconSystem2);
            if (dataAirconSystem2.info.state != null) {
                Intrinsics.checkNotNull(dataAirconSystem);
                SystemState systemState = dataAirconSystem.info.state;
                if (systemState == null || systemState != dataAirconSystem2.info.state) {
                    return false;
                }
            }
            if (dataAirconSystem2.info.mode != null) {
                Intrinsics.checkNotNull(dataAirconSystem);
                AirconMode airconMode = dataAirconSystem.info.mode;
                if (airconMode == null || airconMode != dataAirconSystem2.info.mode) {
                    return false;
                }
            }
            if (dataAirconSystem2.info.fan != null) {
                Intrinsics.checkNotNull(dataAirconSystem);
                FanStatus fanStatus = dataAirconSystem.info.fan;
                if (fanStatus == null || fanStatus != dataAirconSystem2.info.fan) {
                    return false;
                }
            }
            if (dataAirconSystem2.info.setTemp != null) {
                Intrinsics.checkNotNull(dataAirconSystem);
                Float f3 = dataAirconSystem.info.setTemp;
                if (f3 == null || !Intrinsics.areEqual(f3, dataAirconSystem2.info.setTemp)) {
                    return false;
                }
            }
            if (dataAirconSystem2.info.myZone != null) {
                Intrinsics.checkNotNull(dataAirconSystem);
                Integer num = dataAirconSystem.info.myZone;
                if (num == null || !Intrinsics.areEqual(num, dataAirconSystem2.info.myZone)) {
                    return false;
                }
            }
            if (dataAirconSystem2.info.freshAirStatus != null) {
                Intrinsics.checkNotNull(dataAirconSystem);
                DataAirconSystem.FreshAirStatus freshAirStatus = dataAirconSystem.info.freshAirStatus;
                if (freshAirStatus == null || freshAirStatus != dataAirconSystem2.info.freshAirStatus) {
                    return false;
                }
            }
            if (dataAirconSystem2.info.name != null) {
                Intrinsics.checkNotNull(dataAirconSystem);
                String str2 = dataAirconSystem.info.name;
                if (str2 == null || !Intrinsics.areEqual(str2, dataAirconSystem2.info.name)) {
                    return false;
                }
            }
            if (dataAirconSystem2.info.uid != null) {
                Intrinsics.checkNotNull(dataAirconSystem);
                String str3 = dataAirconSystem.info.uid;
                if (str3 == null || !Intrinsics.areEqual(str3, dataAirconSystem2.info.uid)) {
                    return false;
                }
            }
            Intrinsics.checkNotNull(dataAirconSystem);
            TreeMap<String, DataZone> treeMap = dataAirconSystem.zones;
            Intrinsics.checkNotNull(treeMap);
            int size2 = treeMap.size();
            TreeMap<String, DataZone> treeMap2 = dataAirconSystem2.zones;
            Intrinsics.checkNotNull(treeMap2);
            if (size2 != treeMap2.size()) {
                return false;
            }
            TreeMap<String, DataZone> treeMap3 = dataAirconSystem.zones;
            Intrinsics.checkNotNull(treeMap3);
            for (String str4 : treeMap3.keySet()) {
                TreeMap<String, DataZone> treeMap4 = dataAirconSystem2.zones;
                Intrinsics.checkNotNull(treeMap4);
                if (!treeMap4.containsKey(str4)) {
                    return false;
                }
            }
            TreeMap<String, DataZone> treeMap5 = dataAirconSystem.zones;
            Intrinsics.checkNotNull(treeMap5);
            Iterator it3 = new ArrayList(treeMap5.keySet()).iterator();
            while (it3.hasNext()) {
                String str5 = (String) it3.next();
                TreeMap<String, DataZone> treeMap6 = dataAirconSystem.zones;
                Intrinsics.checkNotNull(treeMap6);
                DataZone dataZone = treeMap6.get(str5);
                TreeMap<String, DataZone> treeMap7 = dataAirconSystem2.zones;
                Intrinsics.checkNotNull(treeMap7);
                DataZone dataZone2 = treeMap7.get(str5);
                Intrinsics.checkNotNull(dataZone2);
                if (dataZone2.state != null) {
                    Intrinsics.checkNotNull(dataZone);
                    ZoneState zoneState = dataZone.state;
                    if (zoneState == null || zoneState != dataZone2.state) {
                        return false;
                    }
                }
                if (dataZone2.setTemp != null) {
                    Intrinsics.checkNotNull(dataZone);
                    Float f7 = dataZone.setTemp;
                    if (f7 == null || !Intrinsics.areEqual(f7, dataZone2.setTemp)) {
                        return false;
                    }
                }
                if (dataZone2.value != null) {
                    Intrinsics.checkNotNull(dataZone);
                    Integer num2 = dataZone.value;
                    if (num2 == null || !Intrinsics.areEqual(num2, dataZone2.value)) {
                        return false;
                    }
                }
                if (dataZone2.motionConfig != null) {
                    Intrinsics.checkNotNull(dataZone);
                    Integer num3 = dataZone.motionConfig;
                    if (num3 == null || !Intrinsics.areEqual(num3, dataZone2.motionConfig)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private final boolean isLightsCollectionEqualForScenePurpose(HashMap hashMap) {
        Integer num;
        HashMap hashMap2 = this.lights;
        Intrinsics.checkNotNull(hashMap2);
        int size = hashMap2.size();
        Intrinsics.checkNotNull(hashMap);
        if (size != hashMap.size()) {
            return false;
        }
        HashMap hashMap3 = this.lights;
        Intrinsics.checkNotNull(hashMap3);
        if (hashMap3.size() == 0) {
            return true;
        }
        HashMap hashMap4 = this.lights;
        Intrinsics.checkNotNull(hashMap4);
        for (DataLight dataLight : hashMap4.values()) {
            boolean z7 = false;
            for (DataLight dataLight2 : hashMap.values()) {
                Intrinsics.checkNotNull(dataLight);
                String str = dataLight.id;
                Intrinsics.checkNotNull(dataLight2);
                if (Intrinsics.areEqual(str, dataLight2.id)) {
                    if (dataLight.state != dataLight2.state) {
                        return false;
                    }
                    Integer num2 = dataLight2.value;
                    if (num2 != null && ((num = dataLight.value) == null || !Intrinsics.areEqual(num, num2))) {
                        return false;
                    }
                    z7 = true;
                }
            }
            if (!z7) {
                return false;
            }
        }
        return true;
    }

    private final boolean isMonitorsCollectionEqualForScenePurpose(HashMap hashMap) {
        Boolean bool;
        HashMap hashMap2 = this.monitors;
        Intrinsics.checkNotNull(hashMap2);
        int size = hashMap2.size();
        Intrinsics.checkNotNull(hashMap);
        if (size != hashMap.size()) {
            return false;
        }
        HashMap hashMap3 = this.monitors;
        Intrinsics.checkNotNull(hashMap3);
        if (hashMap3.size() == 0) {
            return true;
        }
        HashMap hashMap4 = this.monitors;
        Intrinsics.checkNotNull(hashMap4);
        Iterator it = hashMap4.keySet().iterator();
        while (it.hasNext()) {
            if (!hashMap.containsKey((String) it.next())) {
                return false;
            }
        }
        HashMap hashMap5 = this.monitors;
        Intrinsics.checkNotNull(hashMap5);
        Iterator it2 = new ArrayList(hashMap5.keySet()).iterator();
        while (it2.hasNext()) {
            String str = (String) it2.next();
            HashMap hashMap6 = this.monitors;
            Intrinsics.checkNotNull(hashMap6);
            DataMonitor dataMonitor = (DataMonitor) hashMap6.get(str);
            DataMonitor dataMonitor2 = (DataMonitor) hashMap.get(str);
            if (dataMonitor2 != null && dataMonitor != null) {
                Boolean bool2 = dataMonitor2.monitorEnabled;
                if (bool2 != null && ((bool = dataMonitor.monitorEnabled) == null || !Intrinsics.areEqual(bool, bool2))) {
                    return false;
                }
            } else if ((dataMonitor2 == null && dataMonitor != null) || (dataMonitor2 != null && dataMonitor == null)) {
                return false;
            }
        }
        return true;
    }

    private final boolean isSonosCollectionEqualForScenePurpose(HashMap hashMap) {
        HashMap hashMap2 = this.sonos;
        Intrinsics.checkNotNull(hashMap2);
        int size = hashMap2.size();
        Intrinsics.checkNotNull(hashMap);
        if (size != hashMap.size()) {
            return false;
        }
        HashMap hashMap3 = this.sonos;
        Intrinsics.checkNotNull(hashMap3);
        if (hashMap3.size() == 0) {
            return true;
        }
        HashMap hashMap4 = this.sonos;
        Intrinsics.checkNotNull(hashMap4);
        Iterator it = hashMap4.keySet().iterator();
        while (it.hasNext()) {
            if (!hashMap.containsKey((String) it.next())) {
                return false;
            }
        }
        HashMap hashMap5 = this.sonos;
        Intrinsics.checkNotNull(hashMap5);
        Iterator it2 = new ArrayList(hashMap5.keySet()).iterator();
        while (it2.hasNext()) {
            String str = (String) it2.next();
            HashMap hashMap6 = this.sonos;
            Intrinsics.checkNotNull(hashMap6);
            Sonos sonos = (Sonos) hashMap6.get(str);
            Sonos sonos2 = (Sonos) hashMap.get(str);
            if (sonos2 == null || sonos == null) {
                if ((sonos2 == null && sonos != null) || (sonos2 != null && sonos == null)) {
                    return false;
                }
            } else if (sonos.isDifferent(sonos2)) {
                return false;
            }
        }
        return true;
    }

    private final boolean isThingsCollectionEqualForScenePurpose(HashMap hashMap) {
        Integer num;
        HashMap hashMap2 = this.things;
        Intrinsics.checkNotNull(hashMap2);
        int size = hashMap2.size();
        Intrinsics.checkNotNull(hashMap);
        if (size != hashMap.size()) {
            return false;
        }
        HashMap hashMap3 = this.things;
        Intrinsics.checkNotNull(hashMap3);
        if (hashMap3.size() == 0) {
            return true;
        }
        HashMap hashMap4 = this.things;
        Intrinsics.checkNotNull(hashMap4);
        for (DataMyThing dataMyThing : hashMap4.values()) {
            boolean z7 = false;
            for (DataMyThing dataMyThing2 : hashMap.values()) {
                Intrinsics.checkNotNull(dataMyThing);
                String str = dataMyThing.id;
                Intrinsics.checkNotNull(dataMyThing2);
                if (Intrinsics.areEqual(str, dataMyThing2.id)) {
                    Integer num2 = dataMyThing2.value;
                    if (num2 != null && ((num = dataMyThing.value) == null || !Intrinsics.areEqual(num, num2))) {
                        return false;
                    }
                    z7 = true;
                }
            }
            if (!z7) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    @JvmStatic
    public static final SnapShot.b sceneIsScheduledAtGivenTime(@Nullable DataScene dataScene, int i10, int i11) {
        return Companion.sceneIsScheduledAtGivenTime(dataScene, i10, i11);
    }

    public static /* synthetic */ boolean update$default(DataScene dataScene, Context context, DataScene dataScene2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: update");
        }
        if ((i10 & 8) != 0) {
            z7 = false;
        }
        return dataScene.update(context, dataScene2, dataManager, z7);
    }

    public final void clearDataForBackup() {
        this.summary = "";
    }

    @NotNull
    public final DataScene copy() {
        DataScene dataScene = new DataScene();
        dataScene.id = this.id;
        dataScene.myTimeEnabled = this.myTimeEnabled;
        dataScene.name = this.name;
        dataScene.timerEnabled = this.timerEnabled;
        dataScene.startTime = this.startTime;
        dataScene.airconStopTime = this.airconStopTime;
        dataScene.airconStopTimeEnabled = this.airconStopTimeEnabled;
        dataScene.activeDays = this.activeDays;
        if (this.lights != null) {
            dataScene.lights = new HashMap();
            HashMap hashMap = this.lights;
            Intrinsics.checkNotNull(hashMap);
            for (DataLight dataLight : hashMap.values()) {
                DataLight dataLight2 = new DataLight();
                DataLight.update$default(dataLight2, null, dataLight, null, false, 8, null);
                HashMap hashMap2 = dataScene.lights;
                Intrinsics.checkNotNull(hashMap2);
                hashMap2.put(dataLight2.id, dataLight2);
            }
        }
        if (this.things != null) {
            dataScene.things = new HashMap();
            HashMap hashMap3 = this.things;
            Intrinsics.checkNotNull(hashMap3);
            for (DataMyThing dataMyThing : hashMap3.values()) {
                DataMyThing dataMyThing2 = new DataMyThing();
                DataMyThing.update$default(dataMyThing2, null, dataMyThing, null, false, 8, null);
                HashMap hashMap4 = dataScene.things;
                Intrinsics.checkNotNull(hashMap4);
                hashMap4.put(dataMyThing2.id, dataMyThing2);
            }
        }
        if (this.aircons != null) {
            dataScene.aircons = new HashMap();
            HashMap hashMap5 = this.aircons;
            Intrinsics.checkNotNull(hashMap5);
            for (String str : hashMap5.keySet()) {
                HashMap hashMap6 = this.aircons;
                Intrinsics.checkNotNull(hashMap6);
                DataAirconSystem dataAirconSystem = (DataAirconSystem) hashMap6.get(str);
                DataAirconSystem dataAirconSystem2 = new DataAirconSystem();
                DataAirconSystem.update$default(dataAirconSystem2, null, dataAirconSystem, null, null, false, 16, null);
                HashMap hashMap7 = dataScene.aircons;
                Intrinsics.checkNotNull(hashMap7);
                hashMap7.put(str, dataAirconSystem2);
            }
        }
        if (this.monitors != null) {
            dataScene.monitors = new HashMap();
            HashMap hashMap8 = this.monitors;
            Intrinsics.checkNotNull(hashMap8);
            for (String str2 : hashMap8.keySet()) {
                HashMap hashMap9 = this.monitors;
                Intrinsics.checkNotNull(hashMap9);
                DataMonitor dataMonitor = (DataMonitor) hashMap9.get(str2);
                DataMonitor dataMonitor2 = new DataMonitor();
                DataMonitor.update$default(dataMonitor2, null, dataMonitor, null, false, 8, null);
                HashMap hashMap10 = dataScene.monitors;
                Intrinsics.checkNotNull(hashMap10);
                hashMap10.put(str2, dataMonitor2);
            }
        }
        if (this.sonos != null) {
            dataScene.sonos = new HashMap();
            HashMap hashMap11 = this.sonos;
            Intrinsics.checkNotNull(hashMap11);
            for (String str3 : hashMap11.keySet()) {
                HashMap hashMap12 = this.sonos;
                Intrinsics.checkNotNull(hashMap12);
                Sonos sonos = (Sonos) hashMap12.get(str3);
                Intrinsics.checkNotNull(sonos);
                Sonos sonos2 = new Sonos(sonos.getUdn(), sonos.getHostAddress(), sonos.getModelNumber(), sonos.getModelName(), sonos.getRoomName(), sonos.getDisplayName(), sonos.getFriendlyName(), sonos.getPlayInScene());
                HashMap hashMap13 = dataScene.sonos;
                Intrinsics.checkNotNull(hashMap13);
                Intrinsics.checkNotNull(str3);
                hashMap13.put(str3, sonos2);
            }
        }
        dataScene.summary = this.summary;
        return dataScene;
    }

    /* JADX WARN: Removed duplicated region for block: B:123:0x02c1 A[PHI: r2
      0x02c1: PHI (r2v25 java.lang.StringBuilder) = (r2v23 java.lang.StringBuilder), (r2v29 java.lang.StringBuilder), (r2v29 java.lang.StringBuilder) binds: [B:38:0x00f8, B:65:0x01b5, B:67:0x01be] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:413:0x05cd  */
    @NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String generateSummary(@NotNull DataMaster dataMaster) {
        String str;
        Boolean bool;
        StringBuilder sb;
        String str2;
        StringBuilder sb2;
        String str3;
        Integer num;
        StringBuilder sb3;
        String str4;
        String str5;
        LightState lightState;
        Iterator it;
        Iterator it2;
        ZoneState zoneState;
        ZoneState zoneState2;
        DataScene dataScene = this;
        DataMaster masterdata = dataMaster;
        Intrinsics.checkNotNullParameter(masterdata, "masterdata");
        StringBuilder sb4 = new StringBuilder();
        sb4.append("Days this scene will run:\n");
        Integer num2 = dataScene.activeDays;
        int i10 = 0;
        int i11 = 1;
        if (num2 == null || (num2 != null && num2.intValue() == 0)) {
            sb4.append("none");
        } else {
            int i12 = 0;
            boolean z7 = true;
            while (i12 < 7) {
                Integer num3 = dataScene.activeDays;
                Intrinsics.checkNotNull(num3);
                int i13 = i12 + 1;
                if ((num3.intValue() & (1 << (i13 % 7))) != 0) {
                    if (!z7) {
                        sb4.append(", ");
                    }
                    sb4.append(SCENE_DAYS_STRING[i12]);
                    z7 = false;
                }
                i12 = i13;
            }
        }
        String str6 = "\n\n";
        sb4.append("\n\n");
        HashMap hashMap = dataScene.aircons;
        int i14 = 2;
        String str7 = "\n";
        if (hashMap != null) {
            Intrinsics.checkNotNull(hashMap);
            if (hashMap.size() > 0) {
                sb4.append(MyApp.appContextProvider.appContext().getResources().getString(R.string.dataSceneSummaryMyAir));
            }
            HashMap hashMap2 = dataScene.aircons;
            Intrinsics.checkNotNull(hashMap2);
            ArrayList arrayList = new ArrayList(hashMap2.keySet());
            if (arrayList.size() > 1 && arrayList.size() > 1) {
                CollectionsKt__MutableCollectionsJVMKt.m0(arrayList, new b());
            }
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                String str8 = (String) it3.next();
                HashMap hashMap3 = dataScene.aircons;
                Intrinsics.checkNotNull(hashMap3);
                DataAirconSystem dataAirconSystem = (DataAirconSystem) hashMap3.get(str8);
                DataAirconSystem dataAirconSystem2 = masterdata.aircons.get(str8);
                if (dataAirconSystem != null) {
                    HashMap hashMap4 = dataScene.aircons;
                    Intrinsics.checkNotNull(hashMap4);
                    if (hashMap4.size() == i11) {
                        sb4.append("Aircon: ");
                    } else if (dataAirconSystem2 != null) {
                        String str9 = dataAirconSystem2.info.name;
                        if (str9 != null) {
                            sb4.append(str9);
                            sb4.append(": ");
                        }
                    } else {
                        String str10 = dataAirconSystem.info.name;
                        if (str10 != null) {
                            sb4.append(str10);
                            sb4.append(": ");
                        } else {
                            sb4.append("AC: ");
                        }
                    }
                    SystemState systemState = dataAirconSystem.info.state;
                    if (systemState != null) {
                        sb4.append(String.valueOf(systemState));
                        if (dataAirconSystem.info.state == SystemState.on) {
                            sb4.append(", ");
                            if (dataAirconSystem.info.mode != null) {
                                sb4.append("mode: ");
                                AirconMode airconMode = dataAirconSystem.info.mode;
                                if (airconMode == AirconMode.vent) {
                                    sb4.append("fan, ");
                                } else if (airconMode == AirconMode.myauto) {
                                    sb4.append(MyApp.appContextProvider.appContext().getString(R.string.myAutoModeString));
                                    sb4.append(", ");
                                } else {
                                    sb4.append(String.valueOf(airconMode));
                                    sb4.append(", ");
                                }
                            }
                            if (dataAirconSystem.info.fan != null) {
                                sb4.append("fan speed: ");
                                FanStatus fanStatus = dataAirconSystem.info.fan;
                                if (fanStatus == FanStatus.medium) {
                                    sb4.append("med, ");
                                } else if (fanStatus == FanStatus.autoAA) {
                                    sb4.append(MyApp.appContextProvider.appContext().getString(R.string.myAutoFanSpeedString));
                                    sb4.append(", ");
                                } else {
                                    sb4.append(String.valueOf(fanStatus));
                                    sb4.append(", ");
                                }
                            }
                            Float targetTemperature = dataAirconSystem.getTargetTemperature();
                            if (targetTemperature == null || targetTemperature.floatValue() <= 0.1f) {
                                sb4 = new StringBuilder(sb4.substring(i10, sb4.length() - i14));
                            } else {
                                sb4.append((int) targetTemperature.floatValue());
                                sb4.append("°C");
                            }
                            sb4.append("\n");
                            TreeMap<String, DataZone> treeMap = dataAirconSystem.zones;
                            if (treeMap != null) {
                                Intrinsics.checkNotNull(treeMap);
                                if (treeMap.size() > 0) {
                                    sb4.append("open zones:\n");
                                    TreeMap<String, DataZone> treeMap2 = dataAirconSystem.zones;
                                    Intrinsics.checkNotNull(treeMap2);
                                    int i15 = i11;
                                    for (Map.Entry<String, DataZone> entry : treeMap2.entrySet()) {
                                        DataZone value = entry.getValue();
                                        if (dataAirconSystem2 != null) {
                                            TreeMap<String, DataZone> treeMap3 = dataAirconSystem2.zones;
                                            Intrinsics.checkNotNull(treeMap3);
                                            DataZone dataZone = treeMap3.get(entry.getKey());
                                            if (value == null || (zoneState2 = value.state) == null) {
                                                it2 = it3;
                                            } else {
                                                it2 = it3;
                                                if (zoneState2 == ZoneState.open && dataZone != null && dataZone.name != null) {
                                                    if (i15 != 0) {
                                                        i15 = 0;
                                                    } else {
                                                        sb4.append(", ");
                                                    }
                                                    sb4.append(dataZone.name);
                                                    Integer num4 = dataAirconSystem.info.myZone;
                                                    if (num4 != null && Intrinsics.areEqual(DataZone.Companion.getZoneKey(num4), entry.getKey())) {
                                                        sb4.append("*");
                                                    }
                                                }
                                            }
                                        } else {
                                            it2 = it3;
                                            if (value != null && (zoneState = value.state) != null && zoneState == ZoneState.open) {
                                                String key = entry.getKey();
                                                if (!Intrinsics.areEqual(key, "z10")) {
                                                    Intrinsics.checkNotNull(key);
                                                    key = StringsKt__StringsJVMKt.replace$default(key, UrlConfig.RESULT_OK, "", false, 4, null);
                                                }
                                                if (i15 != 0) {
                                                    i15 = 0;
                                                } else {
                                                    sb4.append(", ");
                                                }
                                                sb4.append(key);
                                                Integer num5 = dataAirconSystem.info.myZone;
                                                if (num5 != null && Intrinsics.areEqual(DataZone.Companion.getZoneKey(num5), entry.getKey())) {
                                                    sb4.append("*");
                                                }
                                            }
                                        }
                                        if (value != null && value.state == null) {
                                            AppFeatures.logError(AppFeatures.instance, new RuntimeException("Scene summary - zone state is null, " + str8 + " - " + ((Object) entry.getKey())), null, 2, null);
                                        }
                                        it3 = it2;
                                    }
                                    it = it3;
                                    sb4.append("\n");
                                } else {
                                    it = it3;
                                }
                            }
                        } else {
                            it = it3;
                            sb4.append("\n");
                        }
                        sb4.append("\n");
                        it3 = it;
                        i10 = 0;
                        i11 = 1;
                        i14 = 2;
                    }
                }
            }
        }
        HashMap hashMap5 = dataScene.lights;
        String str11 = "will turn on:";
        if (hashMap5 != null) {
            Intrinsics.checkNotNull(hashMap5);
            if (hashMap5.size() > 0) {
                sb4.append("MyLights:\n");
                StringBuilder sb5 = new StringBuilder();
                StringBuilder sb6 = new StringBuilder();
                HashMap hashMap6 = dataScene.lights;
                Intrinsics.checkNotNull(hashMap6);
                ArrayList arrayList2 = new ArrayList(hashMap6.keySet());
                if (arrayList2.size() > 1 && arrayList2.size() > 1) {
                    CollectionsKt__MutableCollectionsJVMKt.m0(arrayList2, new c());
                }
                Iterator it4 = arrayList2.iterator();
                boolean z10 = true;
                boolean z11 = true;
                int i16 = 0;
                int i17 = 0;
                while (it4.hasNext()) {
                    String str12 = (String) it4.next();
                    Iterator it5 = it4;
                    HashMap hashMap7 = dataScene.lights;
                    Intrinsics.checkNotNull(hashMap7);
                    DataLight dataLight = (DataLight) hashMap7.get(str12);
                    TreeMap<String, DataLight> treeMap4 = masterdata.myLights.lights;
                    Intrinsics.checkNotNull(treeMap4);
                    DataLight dataLight2 = treeMap4.get(str12);
                    if (dataLight != null && (lightState = dataLight.state) != null) {
                        if (lightState == LightState.on) {
                            if (dataLight2 != null && dataLight2.name != null) {
                                if (z11) {
                                    z11 = false;
                                } else {
                                    sb5.append(", ");
                                }
                                sb5.append(dataLight2.name);
                            }
                            i16++;
                        } else {
                            if (dataLight2 != null && dataLight2.name != null) {
                                if (z10) {
                                    z10 = false;
                                } else {
                                    sb6.append(", ");
                                }
                                sb6.append(dataLight2.name);
                            }
                            i17++;
                        }
                    }
                    dataScene = this;
                    it4 = it5;
                }
                if (i16 != 0) {
                    sb4.append("will turn on:");
                    sb4.append("\n");
                    sb4.append((CharSequence) sb5);
                    sb4.append("\n\n");
                }
                if (i17 != 0) {
                    sb4.append("will turn off:");
                    sb4.append("\n");
                    sb4.append((CharSequence) sb6);
                    sb4.append("\n\n");
                }
            }
        }
        DataScene dataScene2 = this;
        HashMap hashMap8 = dataScene2.things;
        if (hashMap8 != null) {
            Intrinsics.checkNotNull(hashMap8);
            if (hashMap8.size() > 0) {
                sb4.append("MyPlace:\n");
                StringBuilder sb7 = new StringBuilder();
                StringBuilder sb8 = new StringBuilder();
                StringBuilder sb9 = new StringBuilder();
                StringBuilder sb10 = new StringBuilder();
                StringBuilder sb11 = new StringBuilder();
                StringBuilder sb12 = new StringBuilder();
                HashMap hashMap9 = dataScene2.things;
                Intrinsics.checkNotNull(hashMap9);
                ArrayList arrayList3 = new ArrayList(hashMap9.keySet());
                if (arrayList3.size() > 1 && arrayList3.size() > 1) {
                    CollectionsKt__MutableCollectionsJVMKt.m0(arrayList3, new d());
                }
                Iterator it6 = arrayList3.iterator();
                boolean z12 = true;
                boolean z13 = true;
                boolean z14 = true;
                boolean z15 = true;
                boolean z16 = true;
                boolean z17 = true;
                int i18 = 0;
                int i19 = 0;
                int i20 = 0;
                int i21 = 0;
                int i22 = 0;
                int i23 = 0;
                while (it6.hasNext()) {
                    Iterator it7 = it6;
                    String str13 = (String) it6.next();
                    String str14 = str6;
                    HashMap hashMap10 = dataScene2.things;
                    Intrinsics.checkNotNull(hashMap10);
                    DataMyThing dataMyThing = (DataMyThing) hashMap10.get(str13);
                    TreeMap<String, DataMyThing> treeMap5 = masterdata.myThings.things;
                    Intrinsics.checkNotNull(treeMap5);
                    DataMyThing dataMyThing2 = treeMap5.get(str13);
                    if (dataMyThing == null || (num = dataMyThing.value) == null) {
                        sb = sb4;
                        str2 = str11;
                        sb2 = sb8;
                        str3 = str7;
                    } else {
                        str3 = str7;
                        sb = sb4;
                        str2 = str11;
                        if (num == null) {
                            sb3 = sb8;
                        } else {
                            sb3 = sb8;
                            if (num.intValue() == 100) {
                                if (dataMyThing2 != null && (str4 = dataMyThing2.buttonType) != null) {
                                    if (Intrinsics.areEqual(str4, DataMyThing.BUTTON_TYPE_ON_OFF) || Intrinsics.areEqual(dataMyThing2.buttonType, DataMyThing.BUTTON_TYPE_DIMMABLE)) {
                                        if (dataMyThing2.name != null) {
                                            if (z12) {
                                                z12 = false;
                                            } else {
                                                sb7.append(", ");
                                            }
                                            sb7.append(dataMyThing2.name);
                                        }
                                        i18++;
                                    } else if (Intrinsics.areEqual(dataMyThing2.buttonType, DataMyThing.BUTTON_TYPE_UP_DOWN)) {
                                        if (dataMyThing2.name != null) {
                                            if (z14) {
                                                z14 = false;
                                            } else {
                                                sb9.append(", ");
                                            }
                                            sb9.append(dataMyThing2.name);
                                        }
                                        i20++;
                                    } else if (Intrinsics.areEqual(dataMyThing2.buttonType, DataMyThing.BUTTON_TYPE_OPEN_CLOSE)) {
                                        if (dataMyThing2.name != null) {
                                            if (z15) {
                                                z15 = false;
                                            } else {
                                                sb11.append(", ");
                                            }
                                            sb11.append(dataMyThing2.name);
                                        }
                                        i22++;
                                    }
                                    dataScene2 = this;
                                    masterdata = dataMaster;
                                    str6 = str14;
                                    it6 = it7;
                                    str7 = str3;
                                    sb4 = sb;
                                    str11 = str2;
                                    sb8 = sb3;
                                }
                                sb2 = sb3;
                            }
                        }
                        Integer num6 = dataMyThing.value;
                        if (num6 == null || num6.intValue() != 0 || dataMyThing2 == null || (str5 = dataMyThing2.buttonType) == null) {
                            sb2 = sb3;
                        } else if (Intrinsics.areEqual(str5, DataMyThing.BUTTON_TYPE_ON_OFF) || Intrinsics.areEqual(dataMyThing2.buttonType, DataMyThing.BUTTON_TYPE_DIMMABLE)) {
                            if (dataMyThing2.name != null) {
                                if (z13) {
                                    sb2 = sb3;
                                    z13 = false;
                                } else {
                                    sb2 = sb3;
                                    sb2.append(", ");
                                }
                                sb2.append(dataMyThing2.name);
                            } else {
                                sb2 = sb3;
                            }
                            i19++;
                        } else {
                            if (Intrinsics.areEqual(dataMyThing2.buttonType, DataMyThing.BUTTON_TYPE_UP_DOWN)) {
                                if (dataMyThing2.name != null) {
                                    if (z16) {
                                        z16 = false;
                                    } else {
                                        sb10.append(", ");
                                    }
                                    sb10.append(dataMyThing2.name);
                                }
                                i21++;
                            } else if (Intrinsics.areEqual(dataMyThing2.buttonType, DataMyThing.BUTTON_TYPE_OPEN_CLOSE)) {
                                if (dataMyThing2.name != null) {
                                    if (z17) {
                                        z17 = false;
                                    } else {
                                        sb12.append(", ");
                                    }
                                    sb12.append(dataMyThing2.name);
                                }
                                i23++;
                            } else {
                                sb2 = sb3;
                            }
                            dataScene2 = this;
                            masterdata = dataMaster;
                            str6 = str14;
                            it6 = it7;
                            str7 = str3;
                            sb4 = sb;
                            str11 = str2;
                            sb8 = sb3;
                        }
                    }
                    dataScene2 = this;
                    sb8 = sb2;
                    str6 = str14;
                    it6 = it7;
                    str7 = str3;
                    sb4 = sb;
                    str11 = str2;
                    masterdata = dataMaster;
                }
                StringBuilder sb13 = sb4;
                String str15 = str6;
                String str16 = str11;
                StringBuilder sb14 = sb8;
                String str17 = str7;
                if (i18 != 0) {
                    sb4 = sb13;
                    sb4.append(str16);
                    str = str17;
                    sb4.append(str);
                    sb4.append((CharSequence) sb7);
                    str6 = str15;
                    sb4.append(str6);
                } else {
                    str6 = str15;
                    str = str17;
                    sb4 = sb13;
                }
                if (i19 != 0) {
                    sb4.append("will turn off:");
                    sb4.append(str);
                    sb4.append((CharSequence) sb14);
                    sb4.append(str6);
                }
                if (i20 != 0) {
                    sb4.append("will move up:");
                    sb4.append(str);
                    sb4.append((CharSequence) sb9);
                    sb4.append(str6);
                }
                if (i21 != 0) {
                    sb4.append("will move down:");
                    sb4.append(str);
                    sb4.append((CharSequence) sb10);
                    sb4.append(str6);
                }
                if (i22 != 0) {
                    sb4.append("will open:");
                    sb4.append(str);
                    sb4.append((CharSequence) sb11);
                    sb4.append(str6);
                }
                if (i23 != 0) {
                    sb4.append("will close:");
                    sb4.append(str);
                    sb4.append((CharSequence) sb12);
                    sb4.append(str6);
                }
            } else {
                str = "\n";
            }
        }
        HashMap hashMap11 = this.monitors;
        if (hashMap11 != null) {
            Intrinsics.checkNotNull(hashMap11);
            if (hashMap11.size() > 0) {
                sb4.append("Events:\n");
                StringBuilder sb15 = new StringBuilder();
                StringBuilder sb16 = new StringBuilder();
                HashMap hashMap12 = this.monitors;
                Intrinsics.checkNotNull(hashMap12);
                ArrayList arrayList4 = new ArrayList(hashMap12.keySet());
                if (arrayList4.size() > 1 && arrayList4.size() > 1) {
                    CollectionsKt__MutableCollectionsJVMKt.m0(arrayList4, new e());
                }
                Iterator it8 = arrayList4.iterator();
                boolean z18 = true;
                boolean z19 = true;
                int i24 = 0;
                int i25 = 0;
                while (it8.hasNext()) {
                    String str18 = (String) it8.next();
                    HashMap hashMap13 = this.monitors;
                    Intrinsics.checkNotNull(hashMap13);
                    DataMonitor dataMonitor = (DataMonitor) hashMap13.get(str18);
                    DataMonitor monitor = dataMaster.myMonitors.getMonitor(str18);
                    Iterator it9 = it8;
                    if (dataMonitor != null && (bool = dataMonitor.monitorEnabled) != null) {
                        Intrinsics.checkNotNull(bool);
                        if (bool.booleanValue()) {
                            if (monitor != null) {
                                if (monitor.name != null) {
                                    if (z19) {
                                        z19 = false;
                                    } else {
                                        sb15.append(", ");
                                    }
                                    sb15.append(monitor.name);
                                } else if (dataMonitor.name != null) {
                                    if (z19) {
                                        z19 = false;
                                    } else {
                                        sb15.append(", ");
                                    }
                                    sb15.append(dataMonitor.name);
                                } else {
                                    if (z19) {
                                        z19 = false;
                                    } else {
                                        sb15.append(", ");
                                    }
                                    sb15.append("Event " + str18);
                                }
                                i24++;
                            }
                        } else if (monitor != null) {
                            if (monitor.name != null) {
                                if (z18) {
                                    z18 = false;
                                } else {
                                    sb16.append(", ");
                                }
                                sb16.append(monitor.name);
                            } else if (dataMonitor.name != null) {
                                if (z18) {
                                    z18 = false;
                                } else {
                                    sb16.append(", ");
                                }
                                sb16.append(dataMonitor.name);
                            } else {
                                if (z18) {
                                    z18 = false;
                                } else {
                                    sb16.append(", ");
                                }
                                sb16.append("Event " + str18);
                            }
                            i25++;
                        }
                    }
                    it8 = it9;
                }
                if (i24 > 0) {
                    sb4.append("will be enabled:");
                    sb4.append(str);
                    sb4.append((CharSequence) sb15);
                    sb4.append(str6);
                }
                if (i25 > 0) {
                    sb4.append("will be disabled:");
                    sb4.append(str);
                    sb4.append((CharSequence) sb16);
                    sb4.append(str6);
                }
            }
        }
        HashMap hashMap14 = this.sonos;
        if (hashMap14 != null) {
            Intrinsics.checkNotNull(hashMap14);
            if (hashMap14.size() > 0) {
                sb4.append("Sonos:");
                HashMap hashMap15 = this.sonos;
                Intrinsics.checkNotNull(hashMap15);
                boolean z20 = true;
                boolean z21 = true;
                for (Sonos sonos : hashMap15.values()) {
                    String component5 = sonos.component5();
                    boolean component8 = sonos.component8();
                    StringBuilder sb17 = new StringBuilder();
                    if (component8) {
                        if (z21) {
                            sb17.append("\nwill play:");
                            sb17.append(str);
                            z21 = false;
                        }
                        if (!z20) {
                            sb17.append(", ");
                        }
                        sb17.append(component5);
                        z20 = false;
                    }
                    sb4.append(sb17.toString());
                }
                HashMap hashMap16 = this.sonos;
                Intrinsics.checkNotNull(hashMap16);
                boolean z22 = true;
                boolean z23 = true;
                for (Sonos sonos2 : hashMap16.values()) {
                    String component52 = sonos2.component5();
                    boolean component82 = sonos2.component8();
                    StringBuilder sb18 = new StringBuilder();
                    if (!component82) {
                        if (z22) {
                            sb18.append("\nwill pause:");
                            sb18.append(str);
                            z22 = false;
                        }
                        if (!z23) {
                            sb18.append(", ");
                        }
                        sb18.append(component52);
                        z23 = false;
                    }
                    sb4.append(sb18.toString());
                }
            }
        }
        String sb19 = sb4.toString();
        Intrinsics.checkNotNullExpressionValue(sb19, "toString(...)");
        if (StringsKt__StringsJVMKt.J1(sb19, str6, false, 2, null)) {
            sb4 = new StringBuilder(sb4.substring(0, sb4.length() - 2));
        }
        String sb20 = sb4.toString();
        Intrinsics.checkNotNullExpressionValue(sb20, "toString(...)");
        return sb20;
    }

    @NotNull
    public final String generateSummaryForZone10e(@NotNull DataMaster masterdata, @Nullable Boolean bool) {
        ZoneState zoneState;
        ZoneState zoneState2;
        Intrinsics.checkNotNullParameter(masterdata, "masterdata");
        StringBuilder sb = new StringBuilder();
        sb.append("Days this scene will run:\n");
        Integer num = this.activeDays;
        boolean z7 = true;
        if (num == null || (num != null && num.intValue() == 0)) {
            sb.append("none");
        } else {
            int i10 = 0;
            boolean z10 = true;
            while (i10 < 7) {
                Integer num2 = this.activeDays;
                Intrinsics.checkNotNull(num2);
                int i11 = i10 + 1;
                if ((num2.intValue() & (1 << (i11 % 7))) != 0) {
                    if (!z10) {
                        sb.append(", ");
                    }
                    sb.append(SCENE_DAYS_STRING[i10]);
                    z10 = false;
                }
                i10 = i11;
            }
        }
        sb.append("\n\n");
        HashMap hashMap = this.aircons;
        if (hashMap != null) {
            Intrinsics.checkNotNull(hashMap);
            if (hashMap.size() > 0) {
                HashMap hashMap2 = this.aircons;
                Intrinsics.checkNotNull(hashMap2);
                ArrayList arrayList = new ArrayList(hashMap2.keySet());
                if (arrayList.size() > 1 && arrayList.size() > 1) {
                    CollectionsKt__MutableCollectionsJVMKt.m0(arrayList, new f());
                }
                HashMap hashMap3 = this.aircons;
                Intrinsics.checkNotNull(hashMap3);
                DataAirconSystem dataAirconSystem = (DataAirconSystem) hashMap3.get(arrayList.get(0));
                DataAirconSystem dataAirconSystem2 = masterdata.aircons.get(arrayList.get(0));
                if (dataAirconSystem != null) {
                    if (dataAirconSystem.info.state != null) {
                        if (bool != null && bool.booleanValue()) {
                            sb.append("Aircon - ");
                            sb.append(String.valueOf(dataAirconSystem.info.state));
                        }
                        if (dataAirconSystem.info.state == SystemState.on || bool == null || !bool.booleanValue()) {
                            if (bool != null && bool.booleanValue()) {
                                sb.append("\n");
                            }
                            TreeMap<String, DataZone> treeMap = dataAirconSystem.zones;
                            if (treeMap != null) {
                                Intrinsics.checkNotNull(treeMap);
                                if (treeMap.size() > 0) {
                                    sb.append("open zones:\n");
                                    TreeMap<String, DataZone> treeMap2 = dataAirconSystem.zones;
                                    Intrinsics.checkNotNull(treeMap2);
                                    for (Map.Entry<String, DataZone> entry : treeMap2.entrySet()) {
                                        DataZone value = entry.getValue();
                                        if (dataAirconSystem2 != null) {
                                            TreeMap<String, DataZone> treeMap3 = dataAirconSystem2.zones;
                                            Intrinsics.checkNotNull(treeMap3);
                                            DataZone dataZone = treeMap3.get(entry.getKey());
                                            if (value != null && (zoneState2 = value.state) != null && zoneState2 == ZoneState.open && dataZone != null && dataZone.name != null) {
                                                if (z7) {
                                                    z7 = false;
                                                } else {
                                                    sb.append(", ");
                                                }
                                                sb.append(dataZone.name);
                                                Integer num3 = dataAirconSystem.info.myZone;
                                                if (num3 != null && Intrinsics.areEqual(DataZone.Companion.getZoneKey(num3), entry.getKey())) {
                                                    sb.append("*");
                                                }
                                            }
                                        } else if (value != null && (zoneState = value.state) != null && zoneState == ZoneState.open) {
                                            String key = entry.getKey();
                                            if (!Intrinsics.areEqual(key, "z10")) {
                                                Intrinsics.checkNotNull(key);
                                                key = StringsKt__StringsJVMKt.replace$default(key, UrlConfig.RESULT_OK, "", false, 4, null);
                                            }
                                            if (z7) {
                                                z7 = false;
                                            } else {
                                                sb.append(", ");
                                            }
                                            sb.append(key);
                                            Integer num4 = dataAirconSystem.info.myZone;
                                            if (num4 != null && Intrinsics.areEqual(DataZone.Companion.getZoneKey(num4), entry.getKey())) {
                                                sb.append("*");
                                            }
                                        }
                                        if (value != null && value.state == null) {
                                            AppFeatures.logError(AppFeatures.instance, new RuntimeException("Scene summary - zone state is null, " + arrayList.get(0) + " - " + ((Object) entry.getKey())), null, 2, null);
                                        }
                                    }
                                    sb.append("\n");
                                }
                            }
                        } else {
                            sb.append("\n");
                        }
                    }
                    sb.append("\n");
                }
            }
        }
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        if (StringsKt__StringsJVMKt.J1(sb2, "\n\n", false, 2, null)) {
            sb = new StringBuilder(sb.substring(0, sb.length() - 2));
        }
        String sb3 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb3, "toString(...)");
        return sb3;
    }

    public final void sanitiseData() {
        this.canMessages = null;
        this.activeDays = null;
        this.things = null;
        this.lights = null;
        this.aircons = null;
        this.monitors = null;
        this.summary = null;
        this.airconStopTimeEnabled = null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@Nullable Context context, @Nullable DataScene dataScene, @Nullable DataManager dataManager) {
        return update$default(this, context, dataScene, dataManager, false, 8, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x01db  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0049 A[Catch: all -> 0x0247, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:7:0x000d, B:9:0x0013, B:10:0x001a, B:12:0x001f, B:14:0x0023, B:16:0x0045, B:18:0x0049, B:20:0x004d, B:22:0x006f, B:24:0x0073, B:26:0x0077, B:28:0x0099, B:30:0x009d, B:32:0x00a1, B:34:0x00c3, B:36:0x00c7, B:38:0x00cb, B:40:0x00ed, B:42:0x00f1, B:44:0x00f5, B:46:0x0117, B:48:0x011b, B:50:0x011f, B:52:0x0141, B:54:0x0145, B:56:0x0149, B:58:0x016b, B:60:0x016f, B:62:0x0173, B:64:0x0195, B:66:0x0199, B:68:0x019d, B:70:0x01bf, B:72:0x01c3, B:74:0x01c7, B:76:0x01e9, B:78:0x01ed, B:80:0x01f1, B:82:0x0213, B:84:0x0217, B:86:0x021b, B:91:0x0242, B:96:0x0221, B:98:0x0227, B:100:0x0231, B:103:0x0237, B:104:0x01f7, B:106:0x01fd, B:109:0x0207, B:112:0x020d, B:113:0x01cd, B:115:0x01d3, B:118:0x01dd, B:121:0x01e3, B:122:0x01a3, B:124:0x01a9, B:127:0x01b3, B:130:0x01b9, B:131:0x0179, B:133:0x017f, B:136:0x0189, B:139:0x018f, B:140:0x014f, B:142:0x0155, B:145:0x015f, B:148:0x0165, B:149:0x0125, B:151:0x012b, B:154:0x0135, B:157:0x013b, B:158:0x00fb, B:160:0x0101, B:163:0x010b, B:166:0x0111, B:167:0x00d1, B:169:0x00d7, B:172:0x00e1, B:175:0x00e7, B:176:0x00a7, B:178:0x00ad, B:181:0x00b7, B:184:0x00bd, B:185:0x007d, B:187:0x0083, B:190:0x008d, B:193:0x0093, B:194:0x0053, B:196:0x0059, B:199:0x0063, B:202:0x0069, B:203:0x0029, B:205:0x002f, B:208:0x0039, B:211:0x003f), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0073 A[Catch: all -> 0x0247, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:7:0x000d, B:9:0x0013, B:10:0x001a, B:12:0x001f, B:14:0x0023, B:16:0x0045, B:18:0x0049, B:20:0x004d, B:22:0x006f, B:24:0x0073, B:26:0x0077, B:28:0x0099, B:30:0x009d, B:32:0x00a1, B:34:0x00c3, B:36:0x00c7, B:38:0x00cb, B:40:0x00ed, B:42:0x00f1, B:44:0x00f5, B:46:0x0117, B:48:0x011b, B:50:0x011f, B:52:0x0141, B:54:0x0145, B:56:0x0149, B:58:0x016b, B:60:0x016f, B:62:0x0173, B:64:0x0195, B:66:0x0199, B:68:0x019d, B:70:0x01bf, B:72:0x01c3, B:74:0x01c7, B:76:0x01e9, B:78:0x01ed, B:80:0x01f1, B:82:0x0213, B:84:0x0217, B:86:0x021b, B:91:0x0242, B:96:0x0221, B:98:0x0227, B:100:0x0231, B:103:0x0237, B:104:0x01f7, B:106:0x01fd, B:109:0x0207, B:112:0x020d, B:113:0x01cd, B:115:0x01d3, B:118:0x01dd, B:121:0x01e3, B:122:0x01a3, B:124:0x01a9, B:127:0x01b3, B:130:0x01b9, B:131:0x0179, B:133:0x017f, B:136:0x0189, B:139:0x018f, B:140:0x014f, B:142:0x0155, B:145:0x015f, B:148:0x0165, B:149:0x0125, B:151:0x012b, B:154:0x0135, B:157:0x013b, B:158:0x00fb, B:160:0x0101, B:163:0x010b, B:166:0x0111, B:167:0x00d1, B:169:0x00d7, B:172:0x00e1, B:175:0x00e7, B:176:0x00a7, B:178:0x00ad, B:181:0x00b7, B:184:0x00bd, B:185:0x007d, B:187:0x0083, B:190:0x008d, B:193:0x0093, B:194:0x0053, B:196:0x0059, B:199:0x0063, B:202:0x0069, B:203:0x0029, B:205:0x002f, B:208:0x0039, B:211:0x003f), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x009d A[Catch: all -> 0x0247, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:7:0x000d, B:9:0x0013, B:10:0x001a, B:12:0x001f, B:14:0x0023, B:16:0x0045, B:18:0x0049, B:20:0x004d, B:22:0x006f, B:24:0x0073, B:26:0x0077, B:28:0x0099, B:30:0x009d, B:32:0x00a1, B:34:0x00c3, B:36:0x00c7, B:38:0x00cb, B:40:0x00ed, B:42:0x00f1, B:44:0x00f5, B:46:0x0117, B:48:0x011b, B:50:0x011f, B:52:0x0141, B:54:0x0145, B:56:0x0149, B:58:0x016b, B:60:0x016f, B:62:0x0173, B:64:0x0195, B:66:0x0199, B:68:0x019d, B:70:0x01bf, B:72:0x01c3, B:74:0x01c7, B:76:0x01e9, B:78:0x01ed, B:80:0x01f1, B:82:0x0213, B:84:0x0217, B:86:0x021b, B:91:0x0242, B:96:0x0221, B:98:0x0227, B:100:0x0231, B:103:0x0237, B:104:0x01f7, B:106:0x01fd, B:109:0x0207, B:112:0x020d, B:113:0x01cd, B:115:0x01d3, B:118:0x01dd, B:121:0x01e3, B:122:0x01a3, B:124:0x01a9, B:127:0x01b3, B:130:0x01b9, B:131:0x0179, B:133:0x017f, B:136:0x0189, B:139:0x018f, B:140:0x014f, B:142:0x0155, B:145:0x015f, B:148:0x0165, B:149:0x0125, B:151:0x012b, B:154:0x0135, B:157:0x013b, B:158:0x00fb, B:160:0x0101, B:163:0x010b, B:166:0x0111, B:167:0x00d1, B:169:0x00d7, B:172:0x00e1, B:175:0x00e7, B:176:0x00a7, B:178:0x00ad, B:181:0x00b7, B:184:0x00bd, B:185:0x007d, B:187:0x0083, B:190:0x008d, B:193:0x0093, B:194:0x0053, B:196:0x0059, B:199:0x0063, B:202:0x0069, B:203:0x0029, B:205:0x002f, B:208:0x0039, B:211:0x003f), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00c7 A[Catch: all -> 0x0247, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:7:0x000d, B:9:0x0013, B:10:0x001a, B:12:0x001f, B:14:0x0023, B:16:0x0045, B:18:0x0049, B:20:0x004d, B:22:0x006f, B:24:0x0073, B:26:0x0077, B:28:0x0099, B:30:0x009d, B:32:0x00a1, B:34:0x00c3, B:36:0x00c7, B:38:0x00cb, B:40:0x00ed, B:42:0x00f1, B:44:0x00f5, B:46:0x0117, B:48:0x011b, B:50:0x011f, B:52:0x0141, B:54:0x0145, B:56:0x0149, B:58:0x016b, B:60:0x016f, B:62:0x0173, B:64:0x0195, B:66:0x0199, B:68:0x019d, B:70:0x01bf, B:72:0x01c3, B:74:0x01c7, B:76:0x01e9, B:78:0x01ed, B:80:0x01f1, B:82:0x0213, B:84:0x0217, B:86:0x021b, B:91:0x0242, B:96:0x0221, B:98:0x0227, B:100:0x0231, B:103:0x0237, B:104:0x01f7, B:106:0x01fd, B:109:0x0207, B:112:0x020d, B:113:0x01cd, B:115:0x01d3, B:118:0x01dd, B:121:0x01e3, B:122:0x01a3, B:124:0x01a9, B:127:0x01b3, B:130:0x01b9, B:131:0x0179, B:133:0x017f, B:136:0x0189, B:139:0x018f, B:140:0x014f, B:142:0x0155, B:145:0x015f, B:148:0x0165, B:149:0x0125, B:151:0x012b, B:154:0x0135, B:157:0x013b, B:158:0x00fb, B:160:0x0101, B:163:0x010b, B:166:0x0111, B:167:0x00d1, B:169:0x00d7, B:172:0x00e1, B:175:0x00e7, B:176:0x00a7, B:178:0x00ad, B:181:0x00b7, B:184:0x00bd, B:185:0x007d, B:187:0x0083, B:190:0x008d, B:193:0x0093, B:194:0x0053, B:196:0x0059, B:199:0x0063, B:202:0x0069, B:203:0x0029, B:205:0x002f, B:208:0x0039, B:211:0x003f), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00f1 A[Catch: all -> 0x0247, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:7:0x000d, B:9:0x0013, B:10:0x001a, B:12:0x001f, B:14:0x0023, B:16:0x0045, B:18:0x0049, B:20:0x004d, B:22:0x006f, B:24:0x0073, B:26:0x0077, B:28:0x0099, B:30:0x009d, B:32:0x00a1, B:34:0x00c3, B:36:0x00c7, B:38:0x00cb, B:40:0x00ed, B:42:0x00f1, B:44:0x00f5, B:46:0x0117, B:48:0x011b, B:50:0x011f, B:52:0x0141, B:54:0x0145, B:56:0x0149, B:58:0x016b, B:60:0x016f, B:62:0x0173, B:64:0x0195, B:66:0x0199, B:68:0x019d, B:70:0x01bf, B:72:0x01c3, B:74:0x01c7, B:76:0x01e9, B:78:0x01ed, B:80:0x01f1, B:82:0x0213, B:84:0x0217, B:86:0x021b, B:91:0x0242, B:96:0x0221, B:98:0x0227, B:100:0x0231, B:103:0x0237, B:104:0x01f7, B:106:0x01fd, B:109:0x0207, B:112:0x020d, B:113:0x01cd, B:115:0x01d3, B:118:0x01dd, B:121:0x01e3, B:122:0x01a3, B:124:0x01a9, B:127:0x01b3, B:130:0x01b9, B:131:0x0179, B:133:0x017f, B:136:0x0189, B:139:0x018f, B:140:0x014f, B:142:0x0155, B:145:0x015f, B:148:0x0165, B:149:0x0125, B:151:0x012b, B:154:0x0135, B:157:0x013b, B:158:0x00fb, B:160:0x0101, B:163:0x010b, B:166:0x0111, B:167:0x00d1, B:169:0x00d7, B:172:0x00e1, B:175:0x00e7, B:176:0x00a7, B:178:0x00ad, B:181:0x00b7, B:184:0x00bd, B:185:0x007d, B:187:0x0083, B:190:0x008d, B:193:0x0093, B:194:0x0053, B:196:0x0059, B:199:0x0063, B:202:0x0069, B:203:0x0029, B:205:0x002f, B:208:0x0039, B:211:0x003f), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x011b A[Catch: all -> 0x0247, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:7:0x000d, B:9:0x0013, B:10:0x001a, B:12:0x001f, B:14:0x0023, B:16:0x0045, B:18:0x0049, B:20:0x004d, B:22:0x006f, B:24:0x0073, B:26:0x0077, B:28:0x0099, B:30:0x009d, B:32:0x00a1, B:34:0x00c3, B:36:0x00c7, B:38:0x00cb, B:40:0x00ed, B:42:0x00f1, B:44:0x00f5, B:46:0x0117, B:48:0x011b, B:50:0x011f, B:52:0x0141, B:54:0x0145, B:56:0x0149, B:58:0x016b, B:60:0x016f, B:62:0x0173, B:64:0x0195, B:66:0x0199, B:68:0x019d, B:70:0x01bf, B:72:0x01c3, B:74:0x01c7, B:76:0x01e9, B:78:0x01ed, B:80:0x01f1, B:82:0x0213, B:84:0x0217, B:86:0x021b, B:91:0x0242, B:96:0x0221, B:98:0x0227, B:100:0x0231, B:103:0x0237, B:104:0x01f7, B:106:0x01fd, B:109:0x0207, B:112:0x020d, B:113:0x01cd, B:115:0x01d3, B:118:0x01dd, B:121:0x01e3, B:122:0x01a3, B:124:0x01a9, B:127:0x01b3, B:130:0x01b9, B:131:0x0179, B:133:0x017f, B:136:0x0189, B:139:0x018f, B:140:0x014f, B:142:0x0155, B:145:0x015f, B:148:0x0165, B:149:0x0125, B:151:0x012b, B:154:0x0135, B:157:0x013b, B:158:0x00fb, B:160:0x0101, B:163:0x010b, B:166:0x0111, B:167:0x00d1, B:169:0x00d7, B:172:0x00e1, B:175:0x00e7, B:176:0x00a7, B:178:0x00ad, B:181:0x00b7, B:184:0x00bd, B:185:0x007d, B:187:0x0083, B:190:0x008d, B:193:0x0093, B:194:0x0053, B:196:0x0059, B:199:0x0063, B:202:0x0069, B:203:0x0029, B:205:0x002f, B:208:0x0039, B:211:0x003f), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0145 A[Catch: all -> 0x0247, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:7:0x000d, B:9:0x0013, B:10:0x001a, B:12:0x001f, B:14:0x0023, B:16:0x0045, B:18:0x0049, B:20:0x004d, B:22:0x006f, B:24:0x0073, B:26:0x0077, B:28:0x0099, B:30:0x009d, B:32:0x00a1, B:34:0x00c3, B:36:0x00c7, B:38:0x00cb, B:40:0x00ed, B:42:0x00f1, B:44:0x00f5, B:46:0x0117, B:48:0x011b, B:50:0x011f, B:52:0x0141, B:54:0x0145, B:56:0x0149, B:58:0x016b, B:60:0x016f, B:62:0x0173, B:64:0x0195, B:66:0x0199, B:68:0x019d, B:70:0x01bf, B:72:0x01c3, B:74:0x01c7, B:76:0x01e9, B:78:0x01ed, B:80:0x01f1, B:82:0x0213, B:84:0x0217, B:86:0x021b, B:91:0x0242, B:96:0x0221, B:98:0x0227, B:100:0x0231, B:103:0x0237, B:104:0x01f7, B:106:0x01fd, B:109:0x0207, B:112:0x020d, B:113:0x01cd, B:115:0x01d3, B:118:0x01dd, B:121:0x01e3, B:122:0x01a3, B:124:0x01a9, B:127:0x01b3, B:130:0x01b9, B:131:0x0179, B:133:0x017f, B:136:0x0189, B:139:0x018f, B:140:0x014f, B:142:0x0155, B:145:0x015f, B:148:0x0165, B:149:0x0125, B:151:0x012b, B:154:0x0135, B:157:0x013b, B:158:0x00fb, B:160:0x0101, B:163:0x010b, B:166:0x0111, B:167:0x00d1, B:169:0x00d7, B:172:0x00e1, B:175:0x00e7, B:176:0x00a7, B:178:0x00ad, B:181:0x00b7, B:184:0x00bd, B:185:0x007d, B:187:0x0083, B:190:0x008d, B:193:0x0093, B:194:0x0053, B:196:0x0059, B:199:0x0063, B:202:0x0069, B:203:0x0029, B:205:0x002f, B:208:0x0039, B:211:0x003f), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x016f A[Catch: all -> 0x0247, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:7:0x000d, B:9:0x0013, B:10:0x001a, B:12:0x001f, B:14:0x0023, B:16:0x0045, B:18:0x0049, B:20:0x004d, B:22:0x006f, B:24:0x0073, B:26:0x0077, B:28:0x0099, B:30:0x009d, B:32:0x00a1, B:34:0x00c3, B:36:0x00c7, B:38:0x00cb, B:40:0x00ed, B:42:0x00f1, B:44:0x00f5, B:46:0x0117, B:48:0x011b, B:50:0x011f, B:52:0x0141, B:54:0x0145, B:56:0x0149, B:58:0x016b, B:60:0x016f, B:62:0x0173, B:64:0x0195, B:66:0x0199, B:68:0x019d, B:70:0x01bf, B:72:0x01c3, B:74:0x01c7, B:76:0x01e9, B:78:0x01ed, B:80:0x01f1, B:82:0x0213, B:84:0x0217, B:86:0x021b, B:91:0x0242, B:96:0x0221, B:98:0x0227, B:100:0x0231, B:103:0x0237, B:104:0x01f7, B:106:0x01fd, B:109:0x0207, B:112:0x020d, B:113:0x01cd, B:115:0x01d3, B:118:0x01dd, B:121:0x01e3, B:122:0x01a3, B:124:0x01a9, B:127:0x01b3, B:130:0x01b9, B:131:0x0179, B:133:0x017f, B:136:0x0189, B:139:0x018f, B:140:0x014f, B:142:0x0155, B:145:0x015f, B:148:0x0165, B:149:0x0125, B:151:0x012b, B:154:0x0135, B:157:0x013b, B:158:0x00fb, B:160:0x0101, B:163:0x010b, B:166:0x0111, B:167:0x00d1, B:169:0x00d7, B:172:0x00e1, B:175:0x00e7, B:176:0x00a7, B:178:0x00ad, B:181:0x00b7, B:184:0x00bd, B:185:0x007d, B:187:0x0083, B:190:0x008d, B:193:0x0093, B:194:0x0053, B:196:0x0059, B:199:0x0063, B:202:0x0069, B:203:0x0029, B:205:0x002f, B:208:0x0039, B:211:0x003f), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0199 A[Catch: all -> 0x0247, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:7:0x000d, B:9:0x0013, B:10:0x001a, B:12:0x001f, B:14:0x0023, B:16:0x0045, B:18:0x0049, B:20:0x004d, B:22:0x006f, B:24:0x0073, B:26:0x0077, B:28:0x0099, B:30:0x009d, B:32:0x00a1, B:34:0x00c3, B:36:0x00c7, B:38:0x00cb, B:40:0x00ed, B:42:0x00f1, B:44:0x00f5, B:46:0x0117, B:48:0x011b, B:50:0x011f, B:52:0x0141, B:54:0x0145, B:56:0x0149, B:58:0x016b, B:60:0x016f, B:62:0x0173, B:64:0x0195, B:66:0x0199, B:68:0x019d, B:70:0x01bf, B:72:0x01c3, B:74:0x01c7, B:76:0x01e9, B:78:0x01ed, B:80:0x01f1, B:82:0x0213, B:84:0x0217, B:86:0x021b, B:91:0x0242, B:96:0x0221, B:98:0x0227, B:100:0x0231, B:103:0x0237, B:104:0x01f7, B:106:0x01fd, B:109:0x0207, B:112:0x020d, B:113:0x01cd, B:115:0x01d3, B:118:0x01dd, B:121:0x01e3, B:122:0x01a3, B:124:0x01a9, B:127:0x01b3, B:130:0x01b9, B:131:0x0179, B:133:0x017f, B:136:0x0189, B:139:0x018f, B:140:0x014f, B:142:0x0155, B:145:0x015f, B:148:0x0165, B:149:0x0125, B:151:0x012b, B:154:0x0135, B:157:0x013b, B:158:0x00fb, B:160:0x0101, B:163:0x010b, B:166:0x0111, B:167:0x00d1, B:169:0x00d7, B:172:0x00e1, B:175:0x00e7, B:176:0x00a7, B:178:0x00ad, B:181:0x00b7, B:184:0x00bd, B:185:0x007d, B:187:0x0083, B:190:0x008d, B:193:0x0093, B:194:0x0053, B:196:0x0059, B:199:0x0063, B:202:0x0069, B:203:0x0029, B:205:0x002f, B:208:0x0039, B:211:0x003f), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01c3 A[Catch: all -> 0x0247, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:7:0x000d, B:9:0x0013, B:10:0x001a, B:12:0x001f, B:14:0x0023, B:16:0x0045, B:18:0x0049, B:20:0x004d, B:22:0x006f, B:24:0x0073, B:26:0x0077, B:28:0x0099, B:30:0x009d, B:32:0x00a1, B:34:0x00c3, B:36:0x00c7, B:38:0x00cb, B:40:0x00ed, B:42:0x00f1, B:44:0x00f5, B:46:0x0117, B:48:0x011b, B:50:0x011f, B:52:0x0141, B:54:0x0145, B:56:0x0149, B:58:0x016b, B:60:0x016f, B:62:0x0173, B:64:0x0195, B:66:0x0199, B:68:0x019d, B:70:0x01bf, B:72:0x01c3, B:74:0x01c7, B:76:0x01e9, B:78:0x01ed, B:80:0x01f1, B:82:0x0213, B:84:0x0217, B:86:0x021b, B:91:0x0242, B:96:0x0221, B:98:0x0227, B:100:0x0231, B:103:0x0237, B:104:0x01f7, B:106:0x01fd, B:109:0x0207, B:112:0x020d, B:113:0x01cd, B:115:0x01d3, B:118:0x01dd, B:121:0x01e3, B:122:0x01a3, B:124:0x01a9, B:127:0x01b3, B:130:0x01b9, B:131:0x0179, B:133:0x017f, B:136:0x0189, B:139:0x018f, B:140:0x014f, B:142:0x0155, B:145:0x015f, B:148:0x0165, B:149:0x0125, B:151:0x012b, B:154:0x0135, B:157:0x013b, B:158:0x00fb, B:160:0x0101, B:163:0x010b, B:166:0x0111, B:167:0x00d1, B:169:0x00d7, B:172:0x00e1, B:175:0x00e7, B:176:0x00a7, B:178:0x00ad, B:181:0x00b7, B:184:0x00bd, B:185:0x007d, B:187:0x0083, B:190:0x008d, B:193:0x0093, B:194:0x0053, B:196:0x0059, B:199:0x0063, B:202:0x0069, B:203:0x0029, B:205:0x002f, B:208:0x0039, B:211:0x003f), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01ed A[Catch: all -> 0x0247, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:7:0x000d, B:9:0x0013, B:10:0x001a, B:12:0x001f, B:14:0x0023, B:16:0x0045, B:18:0x0049, B:20:0x004d, B:22:0x006f, B:24:0x0073, B:26:0x0077, B:28:0x0099, B:30:0x009d, B:32:0x00a1, B:34:0x00c3, B:36:0x00c7, B:38:0x00cb, B:40:0x00ed, B:42:0x00f1, B:44:0x00f5, B:46:0x0117, B:48:0x011b, B:50:0x011f, B:52:0x0141, B:54:0x0145, B:56:0x0149, B:58:0x016b, B:60:0x016f, B:62:0x0173, B:64:0x0195, B:66:0x0199, B:68:0x019d, B:70:0x01bf, B:72:0x01c3, B:74:0x01c7, B:76:0x01e9, B:78:0x01ed, B:80:0x01f1, B:82:0x0213, B:84:0x0217, B:86:0x021b, B:91:0x0242, B:96:0x0221, B:98:0x0227, B:100:0x0231, B:103:0x0237, B:104:0x01f7, B:106:0x01fd, B:109:0x0207, B:112:0x020d, B:113:0x01cd, B:115:0x01d3, B:118:0x01dd, B:121:0x01e3, B:122:0x01a3, B:124:0x01a9, B:127:0x01b3, B:130:0x01b9, B:131:0x0179, B:133:0x017f, B:136:0x0189, B:139:0x018f, B:140:0x014f, B:142:0x0155, B:145:0x015f, B:148:0x0165, B:149:0x0125, B:151:0x012b, B:154:0x0135, B:157:0x013b, B:158:0x00fb, B:160:0x0101, B:163:0x010b, B:166:0x0111, B:167:0x00d1, B:169:0x00d7, B:172:0x00e1, B:175:0x00e7, B:176:0x00a7, B:178:0x00ad, B:181:0x00b7, B:184:0x00bd, B:185:0x007d, B:187:0x0083, B:190:0x008d, B:193:0x0093, B:194:0x0053, B:196:0x0059, B:199:0x0063, B:202:0x0069, B:203:0x0029, B:205:0x002f, B:208:0x0039, B:211:0x003f), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0217 A[Catch: all -> 0x0247, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:7:0x000d, B:9:0x0013, B:10:0x001a, B:12:0x001f, B:14:0x0023, B:16:0x0045, B:18:0x0049, B:20:0x004d, B:22:0x006f, B:24:0x0073, B:26:0x0077, B:28:0x0099, B:30:0x009d, B:32:0x00a1, B:34:0x00c3, B:36:0x00c7, B:38:0x00cb, B:40:0x00ed, B:42:0x00f1, B:44:0x00f5, B:46:0x0117, B:48:0x011b, B:50:0x011f, B:52:0x0141, B:54:0x0145, B:56:0x0149, B:58:0x016b, B:60:0x016f, B:62:0x0173, B:64:0x0195, B:66:0x0199, B:68:0x019d, B:70:0x01bf, B:72:0x01c3, B:74:0x01c7, B:76:0x01e9, B:78:0x01ed, B:80:0x01f1, B:82:0x0213, B:84:0x0217, B:86:0x021b, B:91:0x0242, B:96:0x0221, B:98:0x0227, B:100:0x0231, B:103:0x0237, B:104:0x01f7, B:106:0x01fd, B:109:0x0207, B:112:0x020d, B:113:0x01cd, B:115:0x01d3, B:118:0x01dd, B:121:0x01e3, B:122:0x01a3, B:124:0x01a9, B:127:0x01b3, B:130:0x01b9, B:131:0x0179, B:133:0x017f, B:136:0x0189, B:139:0x018f, B:140:0x014f, B:142:0x0155, B:145:0x015f, B:148:0x0165, B:149:0x0125, B:151:0x012b, B:154:0x0135, B:157:0x013b, B:158:0x00fb, B:160:0x0101, B:163:0x010b, B:166:0x0111, B:167:0x00d1, B:169:0x00d7, B:172:0x00e1, B:175:0x00e7, B:176:0x00a7, B:178:0x00ad, B:181:0x00b7, B:184:0x00bd, B:185:0x007d, B:187:0x0083, B:190:0x008d, B:193:0x0093, B:194:0x0053, B:196:0x0059, B:199:0x0063, B:202:0x0069, B:203:0x0029, B:205:0x002f, B:208:0x0039, B:211:0x003f), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x023d  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x022f  */
    @JvmOverloads
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean update(@Nullable Context context, @Nullable DataScene dataScene, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        boolean z11;
        String str;
        Boolean bool;
        Integer num;
        Integer num2;
        Boolean bool2;
        Integer num3;
        HashMap hashMap;
        HashMap hashMap2;
        HashMap hashMap3;
        HashMap hashMap4;
        HashMap hashMap5;
        String str2;
        String str3;
        synchronized (this) {
            Intrinsics.checkNotNull(dataScene);
            String str4 = dataScene.id;
            z10 = true;
            if (str4 == null || ((str3 = this.id) != null && Intrinsics.areEqual(str3, str4))) {
                z11 = false;
            } else {
                this.id = dataScene.id;
                z11 = true;
            }
            Boolean bool3 = dataScene.myTimeEnabled;
            if (bool3 != null) {
                Boolean bool4 = this.myTimeEnabled;
                if (bool4 == null || !Intrinsics.areEqual(bool4, bool3)) {
                    this.myTimeEnabled = dataScene.myTimeEnabled;
                    if (dataManager != null) {
                        dataManager.add("myTimeEnabled", dataScene.myTimeEnabled);
                    }
                    z11 = true;
                }
                str = dataScene.name;
                if (str == null) {
                    String str5 = this.name;
                    if (str5 == null || !Intrinsics.areEqual(str5, str)) {
                        this.name = dataScene.name;
                        if (dataManager != null) {
                            dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, dataScene.name);
                        }
                        z11 = true;
                    }
                    bool = dataScene.timerEnabled;
                    if (bool == null) {
                        Boolean bool5 = this.timerEnabled;
                        if (bool5 == null || !Intrinsics.areEqual(bool5, bool)) {
                            this.timerEnabled = dataScene.timerEnabled;
                            if (dataManager != null) {
                                dataManager.add("timerEnabled", dataScene.timerEnabled);
                            }
                            z11 = true;
                        }
                        num = dataScene.startTime;
                        if (num == null) {
                            Integer num4 = this.startTime;
                            if (num4 == null || !Intrinsics.areEqual(num4, num)) {
                                this.startTime = dataScene.startTime;
                                if (dataManager != null) {
                                    dataManager.add("startTime", dataScene.startTime);
                                }
                                z11 = true;
                            }
                            num2 = dataScene.airconStopTime;
                            if (num2 == null) {
                                Integer num5 = this.airconStopTime;
                                if (num5 == null || !Intrinsics.areEqual(num5, num2)) {
                                    this.airconStopTime = dataScene.airconStopTime;
                                    if (dataManager != null) {
                                        dataManager.add("airconStopTime", dataScene.airconStopTime);
                                    }
                                    z11 = true;
                                }
                                bool2 = dataScene.airconStopTimeEnabled;
                                if (bool2 == null) {
                                    Boolean bool6 = this.airconStopTimeEnabled;
                                    if (bool6 == null || !Intrinsics.areEqual(bool6, bool2)) {
                                        this.airconStopTimeEnabled = dataScene.airconStopTimeEnabled;
                                        if (dataManager != null) {
                                            dataManager.add("airconStopTimeEnabled", dataScene.airconStopTimeEnabled);
                                        }
                                        z11 = true;
                                    }
                                    num3 = dataScene.activeDays;
                                    if (num3 == null) {
                                        Integer num6 = this.activeDays;
                                        if (num6 == null || !Intrinsics.areEqual(num6, num3)) {
                                            this.activeDays = dataScene.activeDays;
                                            if (dataManager != null) {
                                                dataManager.add("activeDays", dataScene.activeDays);
                                            }
                                            z11 = true;
                                        }
                                        hashMap = dataScene.lights;
                                        if (hashMap == null) {
                                            if (this.lights == null || !isLightsCollectionEqualForScenePurpose(hashMap)) {
                                                this.lights = dataScene.lights;
                                                if (dataManager != null) {
                                                    dataManager.addSetValue("lights", dataScene.lights);
                                                }
                                                z11 = true;
                                            }
                                            hashMap2 = dataScene.things;
                                            if (hashMap2 == null) {
                                                if (this.things == null || !isThingsCollectionEqualForScenePurpose(hashMap2)) {
                                                    this.things = dataScene.things;
                                                    if (dataManager != null) {
                                                        dataManager.addSetValue("things", dataScene.things);
                                                    }
                                                    z11 = true;
                                                }
                                                hashMap3 = dataScene.aircons;
                                                if (hashMap3 == null) {
                                                    if (this.aircons == null || !isAirconsCollectionEqualForScenePurpose(hashMap3)) {
                                                        this.aircons = dataScene.aircons;
                                                        if (dataManager != null) {
                                                            dataManager.addSetValue("aircons", dataScene.aircons);
                                                        }
                                                        z11 = true;
                                                    }
                                                    hashMap4 = dataScene.monitors;
                                                    if (hashMap4 == null) {
                                                        if (this.monitors == null || !isMonitorsCollectionEqualForScenePurpose(hashMap4)) {
                                                            this.monitors = dataScene.monitors;
                                                            if (dataManager != null) {
                                                                dataManager.addSetValue("monitors", dataScene.monitors);
                                                            }
                                                            z11 = true;
                                                        }
                                                        hashMap5 = dataScene.sonos;
                                                        if (hashMap5 == null) {
                                                            if (this.sonos == null || !isSonosCollectionEqualForScenePurpose(hashMap5)) {
                                                                this.sonos = dataScene.sonos;
                                                                if (dataManager != null) {
                                                                    dataManager.addSetValue("sonos", dataScene.sonos);
                                                                }
                                                                z11 = true;
                                                            }
                                                            str2 = dataScene.summary;
                                                            if (str2 == null) {
                                                                String str6 = this.summary;
                                                                if (str6 == null || !Intrinsics.areEqual(str6, str2)) {
                                                                    this.summary = dataScene.summary;
                                                                    if (dataManager != null) {
                                                                        dataManager.add("summary", dataScene.summary);
                                                                    }
                                                                } else {
                                                                    z10 = z11;
                                                                }
                                                                if (z10 && context != null) {
                                                                    doUpdate(context);
                                                                }
                                                            } else {
                                                                if (z7 && this.summary != null) {
                                                                    if (dataManager != null) {
                                                                        dataManager.add("summary", null);
                                                                    }
                                                                }
                                                                if (z10) {
                                                                    doUpdate(context);
                                                                }
                                                            }
                                                        } else {
                                                            if (z7 && this.sonos != null) {
                                                                if (dataManager != null) {
                                                                    dataManager.add("sonos", null);
                                                                }
                                                                z11 = true;
                                                            }
                                                            str2 = dataScene.summary;
                                                            if (str2 == null) {
                                                            }
                                                        }
                                                    } else {
                                                        if (z7 && this.monitors != null) {
                                                            if (dataManager != null) {
                                                                dataManager.add("monitors", null);
                                                            }
                                                            z11 = true;
                                                        }
                                                        hashMap5 = dataScene.sonos;
                                                        if (hashMap5 == null) {
                                                        }
                                                    }
                                                } else {
                                                    if (z7 && this.aircons != null) {
                                                        if (dataManager != null) {
                                                            dataManager.add("aircons", null);
                                                        }
                                                        z11 = true;
                                                    }
                                                    hashMap4 = dataScene.monitors;
                                                    if (hashMap4 == null) {
                                                    }
                                                }
                                            } else {
                                                if (z7 && this.things != null) {
                                                    if (dataManager != null) {
                                                        dataManager.add("things", null);
                                                    }
                                                    z11 = true;
                                                }
                                                hashMap3 = dataScene.aircons;
                                                if (hashMap3 == null) {
                                                }
                                            }
                                        } else {
                                            if (z7 && this.lights != null) {
                                                if (dataManager != null) {
                                                    dataManager.add("lights", null);
                                                }
                                                z11 = true;
                                            }
                                            hashMap2 = dataScene.things;
                                            if (hashMap2 == null) {
                                            }
                                        }
                                    } else {
                                        if (z7 && this.activeDays != null) {
                                            if (dataManager != null) {
                                                dataManager.add("activeDays", null);
                                            }
                                            z11 = true;
                                        }
                                        hashMap = dataScene.lights;
                                        if (hashMap == null) {
                                        }
                                    }
                                } else {
                                    if (z7 && this.airconStopTimeEnabled != null) {
                                        if (dataManager != null) {
                                            dataManager.add("airconStopTimeEnabled", null);
                                        }
                                        z11 = true;
                                    }
                                    num3 = dataScene.activeDays;
                                    if (num3 == null) {
                                    }
                                }
                            } else {
                                if (z7 && this.airconStopTime != null) {
                                    if (dataManager != null) {
                                        dataManager.add("airconStopTime", null);
                                    }
                                    z11 = true;
                                }
                                bool2 = dataScene.airconStopTimeEnabled;
                                if (bool2 == null) {
                                }
                            }
                        } else {
                            if (z7 && this.startTime != null) {
                                if (dataManager != null) {
                                    dataManager.add("startTime", null);
                                }
                                z11 = true;
                            }
                            num2 = dataScene.airconStopTime;
                            if (num2 == null) {
                            }
                        }
                    } else {
                        if (z7 && this.timerEnabled != null) {
                            if (dataManager != null) {
                                dataManager.add("timerEnabled", null);
                            }
                            z11 = true;
                        }
                        num = dataScene.startTime;
                        if (num == null) {
                        }
                    }
                } else {
                    if (z7 && this.name != null) {
                        if (dataManager != null) {
                            dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, null);
                        }
                        z11 = true;
                    }
                    bool = dataScene.timerEnabled;
                    if (bool == null) {
                    }
                }
            } else {
                if (z7 && this.myTimeEnabled != null) {
                    if (dataManager != null) {
                        dataManager.add("myTimeEnabled", null);
                    }
                    z11 = true;
                }
                str = dataScene.name;
                if (str == null) {
                }
            }
        }
        return z10;
    }

    public DataScene(@NotNull String id, @NotNull String name, @NotNull String canMessages) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(canMessages, "canMessages");
        this.id = id;
        this.name = name;
        int length = canMessages.length() - 1;
        int i10 = 0;
        boolean z7 = false;
        while (i10 <= length) {
            boolean z10 = Intrinsics.compare(canMessages.charAt(!z7 ? i10 : length), 32) <= 0;
            if (z7) {
                if (!z10) {
                    break;
                } else {
                    length--;
                }
            } else if (z10) {
                i10++;
            } else {
                z7 = true;
            }
        }
        this.canMessages = canMessages.subSequence(i10, length + 1).toString();
    }
}