package com.air.advantage.data;

import com.air.advantage.ActivityMain;
import com.air.advantage.AppFeatures;
import com.air.advantage.MyApp;
import com.air.advantage.data.DataAirconSystem;
import com.air.advantage.libraryairconlightjson.AirconMode;
import com.air.advantage.libraryairconlightjson.FanStatus;
import com.air.advantage.libraryairconlightjson.LightState;
import com.air.advantage.libraryairconlightjson.SystemState;
import com.air.advantage.libraryairconlightjson.ZoneState;
import com.air.advantage.myair5.R;
import com.air.advantage.sonos.Sonos;
import com.bosma.api.lab.net.UrlConfig;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@PurelyImplements({"SMAP\nDataMonitorActions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DataMonitorActions.kt\ncom/air/advantage/data/DataMonitorActions\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,899:1\n1002#2,2:900\n1002#2,2:902\n1002#2,2:904\n*S KotlinDebug\n*F\n+ 1 DataMonitorActions.kt\ncom/air/advantage/data/DataMonitorActions\n*L\n535#1:900,2\n667#1:902,2\n736#1:904,2\n*E\n"})
/* renamed from: com.air.advantage.data.y */
/* loaded from: classes.dex */
public final class DataMonitorActions {

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = DataMonitorActions.class.getSimpleName();

    @Nullable
    @SerializedName("aircons")
    @JvmField
    public HashMap aircons;

    @Nullable
    @SerializedName("airconsEnabled")
    @JvmField
    public Boolean airconsEnabled;

    @Nullable
    @SerializedName("autoActionEnabled")
    @JvmField
    public Boolean autoActionEnabled;

    @Nullable
    @SerializedName("autoActionSummary")
    @JvmField
    public String autoActionSummary;

    @Nullable
    @SerializedName("launchMyAppEnabled")
    @JvmField
    public Boolean launchMyAppEnabled;

    @Nullable
    @SerializedName("launchMyAppName")
    @JvmField
    public String launchMyAppName;

    @Nullable
    @SerializedName("launchMyAppPackageName")
    @JvmField
    public String launchMyAppPackageName;

    @Nullable
    @SerializedName("lights")
    @JvmField
    public HashMap lights;

    @Nullable
    @SerializedName("lightsEnabled")
    @JvmField
    public Boolean lightsEnabled;

    @Nullable
    @SerializedName("notificationEnabled")
    @JvmField
    public Boolean notificationEnabled;

    @Nullable
    @SerializedName("notificationPhoneNumber")
    @JvmField
    public String notificationPhoneNumber;

    @Nullable
    @SerializedName("notificationPhoneNumberEnabled")
    @JvmField
    public Boolean notificationPhoneNumberEnabled;

    @Nullable
    @SerializedName("sonos")
    @JvmField
    public HashMap sonos;

    @Nullable
    @SerializedName("sonosEnabled")
    @JvmField
    public Boolean sonosEnabled;

    @Nullable
    @SerializedName("things")
    @JvmField
    public HashMap things;

    @Nullable
    @SerializedName("thingsEnabled")
    @JvmField
    public Boolean thingsEnabled;

    /* renamed from: com.air.advantage.data.y$a */
    public static final class a {
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    @PurelyImplements({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 DataMonitorActions.kt\ncom/air/advantage/data/DataMonitorActions\n*L\n1#1,328:1\n535#2:329\n*E\n"})
    /* renamed from: com.air.advantage.data.y$b */
    public static final class b<T> implements Comparator {
        public final int compare(Object obj, Object obj2) {
            return ComparisonsKt__ComparisonsKt.compareValues((String) obj, (String) obj2);
        }
    }

    @PurelyImplements({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 DataMonitorActions.kt\ncom/air/advantage/data/DataMonitorActions\n*L\n1#1,328:1\n667#2:329\n*E\n"})
    /* renamed from: com.air.advantage.data.y$c */
    public static final class c<T> implements Comparator {
        public final int compare(Object obj, Object obj2) {
            return ComparisonsKt__ComparisonsKt.compareValues((String) obj, (String) obj2);
        }
    }

    @PurelyImplements({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 DataMonitorActions.kt\ncom/air/advantage/data/DataMonitorActions\n*L\n1#1,328:1\n736#2:329\n*E\n"})
    /* renamed from: com.air.advantage.data.y$d */
    public static final class d<T> implements Comparator {
        public final int compare(Object obj, Object obj2) {
            return ComparisonsKt__ComparisonsKt.compareValues((String) obj, (String) obj2);
        }
    }

    private final boolean isAirconsCollectionEqual(HashMap hashMap) {
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
            for (String str2 : treeMap3.keySet()) {
                TreeMap<String, DataZone> treeMap4 = dataAirconSystem2.zones;
                Intrinsics.checkNotNull(treeMap4);
                if (!treeMap4.containsKey(str2)) {
                    return false;
                }
            }
            TreeMap<String, DataZone> treeMap5 = dataAirconSystem.zones;
            Intrinsics.checkNotNull(treeMap5);
            Iterator it3 = new ArrayList(treeMap5.keySet()).iterator();
            while (it3.hasNext()) {
                String str3 = (String) it3.next();
                TreeMap<String, DataZone> treeMap6 = dataAirconSystem.zones;
                Intrinsics.checkNotNull(treeMap6);
                DataZone dataZone = treeMap6.get(str3);
                TreeMap<String, DataZone> treeMap7 = dataAirconSystem2.zones;
                Intrinsics.checkNotNull(treeMap7);
                DataZone dataZone2 = treeMap7.get(str3);
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

    private final boolean isLightsCollectionEqual(HashMap hashMap) {
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

    private final boolean isSonosCollectionEqual(HashMap hashMap) {
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
        for (Sonos sonos : hashMap4.values()) {
            String component1 = sonos.component1();
            boolean component8 = sonos.component8();
            boolean z7 = false;
            for (Sonos sonos2 : hashMap.values()) {
                String component12 = sonos2.component1();
                boolean component82 = sonos2.component8();
                if (Intrinsics.areEqual(component1, component12)) {
                    if (component8 != component82) {
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

    private final boolean isThingsCollectionEqual(HashMap hashMap) {
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

    public static /* synthetic */ boolean update$default(DataMonitorActions dataMonitorActions, DataMonitorActions dataMonitorActions2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 4) != 0) {
            z7 = false;
        }
        return dataMonitorActions.update(dataMonitorActions2, dataManager, z7);
    }

    public final void clear() {
        this.aircons = null;
        this.airconsEnabled = null;
        this.autoActionEnabled = null;
        this.autoActionSummary = null;
        this.lights = null;
        this.lightsEnabled = null;
        this.launchMyAppEnabled = null;
        this.launchMyAppName = null;
        this.launchMyAppPackageName = null;
        this.notificationEnabled = null;
        this.notificationPhoneNumberEnabled = null;
        this.notificationPhoneNumber = null;
        this.sonos = null;
        this.sonosEnabled = null;
        this.things = null;
        this.thingsEnabled = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x02b7 A[PHI: r2
      0x02b7: PHI (r2v26 java.lang.StringBuilder) = (r2v24 java.lang.StringBuilder), (r2v30 java.lang.StringBuilder), (r2v30 java.lang.StringBuilder) binds: [B:26:0x00ee, B:53:0x01ab, B:55:0x01b4] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:341:0x05ef  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void generateSummary(@NotNull DataMaster dataMaster) {
        String str;
        String str2;
        String str3;
        boolean z7;
        StringBuilder sb;
        String str4;
        String str5;
        String str6;
        Integer num;
        String str7;
        String str8;
        LightState lightState;
        Iterator it;
        Iterator it2;
        ZoneState zoneState;
        ZoneState zoneState2;
        DataMonitorActions dataMonitorActions = this;
        DataMaster masterData = dataMaster;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        StringBuilder sb2 = new StringBuilder();
        HashMap hashMap = dataMonitorActions.aircons;
        if (hashMap != null) {
            Intrinsics.checkNotNull(hashMap);
            if (hashMap.size() <= 0) {
                HashMap hashMap2 = dataMonitorActions.lights;
                if (hashMap2 != null) {
                    Intrinsics.checkNotNull(hashMap2);
                    if (hashMap2.size() <= 0) {
                        HashMap hashMap3 = dataMonitorActions.things;
                        if (hashMap3 != null) {
                            Intrinsics.checkNotNull(hashMap3);
                            if (hashMap3.size() <= 0) {
                                HashMap hashMap4 = dataMonitorActions.sonos;
                                if (hashMap4 != null) {
                                    Intrinsics.checkNotNull(hashMap4);
                                    if (hashMap4.size() > 0) {
                                        sb2.append("Auto action configuration:\n\n");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        HashMap hashMap5 = dataMonitorActions.aircons;
        int i10 = 2;
        String str9 = "\n";
        int i11 = 0;
        int i12 = 1;
        if (hashMap5 != null) {
            Intrinsics.checkNotNull(hashMap5);
            if (hashMap5.size() > 0) {
                sb2.append(MyApp.appContextProvider.appContext().getResources().getString(R.string.dataSceneSummaryMyAir));
            }
            HashMap hashMap6 = dataMonitorActions.aircons;
            Intrinsics.checkNotNull(hashMap6);
            ArrayList arrayList = new ArrayList(hashMap6.keySet());
            if (arrayList.size() > 1 && arrayList.size() > 1) {
                CollectionsKt__MutableCollectionsJVMKt.m0(arrayList, new b());
            }
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                String str10 = (String) it3.next();
                HashMap hashMap7 = dataMonitorActions.aircons;
                Intrinsics.checkNotNull(hashMap7);
                DataAirconSystem dataAirconSystem = (DataAirconSystem) hashMap7.get(str10);
                DataAirconSystem dataAirconSystem2 = masterData.aircons.get(str10);
                if (dataAirconSystem != null) {
                    HashMap hashMap8 = dataMonitorActions.aircons;
                    Intrinsics.checkNotNull(hashMap8);
                    if (hashMap8.size() == i12) {
                        sb2.append("Aircon: ");
                    } else if (dataAirconSystem2 != null) {
                        String str11 = dataAirconSystem2.info.name;
                        if (str11 != null) {
                            sb2.append(str11);
                            sb2.append(": ");
                        }
                    } else {
                        String str12 = dataAirconSystem.info.name;
                        if (str12 != null) {
                            sb2.append(str12);
                            sb2.append(": ");
                        } else {
                            sb2.append("AC: ");
                        }
                    }
                    SystemState systemState = dataAirconSystem.info.state;
                    if (systemState != null) {
                        sb2.append(String.valueOf(systemState));
                        if (dataAirconSystem.info.state == SystemState.on) {
                            sb2.append(", ");
                            if (dataAirconSystem.info.mode != null) {
                                sb2.append("mode: ");
                                AirconMode airconMode = dataAirconSystem.info.mode;
                                if (airconMode == AirconMode.vent) {
                                    sb2.append("fan, ");
                                } else if (airconMode == AirconMode.myauto) {
                                    sb2.append(MyApp.appContextProvider.appContext().getString(R.string.myAutoModeString));
                                    sb2.append(", ");
                                } else {
                                    sb2.append(String.valueOf(airconMode));
                                    sb2.append(", ");
                                }
                            }
                            if (dataAirconSystem.info.fan != null) {
                                sb2.append("fan speed: ");
                                FanStatus fanStatus = dataAirconSystem.info.fan;
                                if (fanStatus == FanStatus.medium) {
                                    sb2.append("med, ");
                                } else if (fanStatus == FanStatus.autoAA) {
                                    sb2.append(MyApp.appContextProvider.appContext().getString(R.string.myAutoFanSpeedString));
                                    sb2.append(", ");
                                } else {
                                    sb2.append(String.valueOf(fanStatus));
                                    sb2.append(", ");
                                }
                            }
                            Float targetTemperature = dataAirconSystem.getTargetTemperature();
                            if (targetTemperature == null || targetTemperature.floatValue() <= 0.1f) {
                                sb2 = new StringBuilder(sb2.substring(i11, sb2.length() - i10));
                            } else {
                                sb2.append((int) targetTemperature.floatValue());
                                sb2.append("°C");
                            }
                            sb2.append("\n");
                            TreeMap<String, DataZone> treeMap = dataAirconSystem.zones;
                            if (treeMap != null) {
                                Intrinsics.checkNotNull(treeMap);
                                if (treeMap.size() > 0) {
                                    sb2.append("open zones:\n");
                                    TreeMap<String, DataZone> treeMap2 = dataAirconSystem.zones;
                                    Intrinsics.checkNotNull(treeMap2);
                                    int i13 = i12;
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
                                                    if (i13 != 0) {
                                                        i13 = 0;
                                                    } else {
                                                        sb2.append(", ");
                                                    }
                                                    sb2.append(dataZone.name);
                                                    Integer num2 = dataAirconSystem.info.myZone;
                                                    if (num2 != null && Intrinsics.areEqual(DataZone.Companion.getZoneKey(num2), entry.getKey())) {
                                                        sb2.append("*");
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
                                                if (i13 != 0) {
                                                    i13 = 0;
                                                } else {
                                                    sb2.append(", ");
                                                }
                                                sb2.append(key);
                                                Integer num3 = dataAirconSystem.info.myZone;
                                                if (num3 != null && Intrinsics.areEqual(DataZone.Companion.getZoneKey(num3), entry.getKey())) {
                                                    sb2.append("*");
                                                }
                                            }
                                        }
                                        if (value != null && value.state == null) {
                                            AppFeatures.logError(AppFeatures.instance, new RuntimeException("Auto Action summary - zone state is null, " + str10 + " - " + ((Object) entry.getKey())), null, 2, null);
                                        }
                                        it3 = it2;
                                    }
                                    it = it3;
                                    sb2.append("\n");
                                } else {
                                    it = it3;
                                }
                            }
                        } else {
                            it = it3;
                            sb2.append("\n");
                        }
                        sb2.append("\n");
                        it3 = it;
                        i10 = 2;
                        i11 = 0;
                        i12 = 1;
                    }
                }
            }
        }
        HashMap hashMap9 = dataMonitorActions.lights;
        String str13 = "will turn on:";
        String str14 = ActivityMain.MYAIR5;
        String str15 = "\n\n";
        if (hashMap9 == null) {
            str = ActivityMain.MYAIR5;
        } else {
            String packageName = ActivityMain.Companion.getPackageName();
            if (packageName != null && StringsKt__StringsKt.startsWith$default(packageName, ActivityMain.MYAIR5, false, 2, null)) {
                HashMap hashMap10 = dataMonitorActions.lights;
                Intrinsics.checkNotNull(hashMap10);
                if (hashMap10.size() > 0) {
                    sb2.append("MyLights:\n");
                    StringBuilder sb3 = new StringBuilder();
                    StringBuilder sb4 = new StringBuilder();
                    HashMap hashMap11 = dataMonitorActions.lights;
                    Intrinsics.checkNotNull(hashMap11);
                    ArrayList arrayList2 = new ArrayList(hashMap11.keySet());
                    if (arrayList2.size() > 1 && arrayList2.size() > 1) {
                        CollectionsKt__MutableCollectionsJVMKt.m0(arrayList2, new c());
                    }
                    Iterator it4 = arrayList2.iterator();
                    int i14 = 0;
                    int i15 = 0;
                    boolean z10 = true;
                    boolean z11 = true;
                    while (it4.hasNext()) {
                        Iterator it5 = it4;
                        String str16 = (String) it4.next();
                        String str17 = str14;
                        HashMap hashMap12 = dataMonitorActions.lights;
                        Intrinsics.checkNotNull(hashMap12);
                        DataLight dataLight = (DataLight) hashMap12.get(str16);
                        TreeMap<String, DataLight> treeMap4 = masterData.myLights.lights;
                        Intrinsics.checkNotNull(treeMap4);
                        DataLight dataLight2 = treeMap4.get(str16);
                        if (dataLight != null && (lightState = dataLight.state) != null) {
                            if (lightState == LightState.on) {
                                if (dataLight2 != null && dataLight2.name != null) {
                                    if (z11) {
                                        z11 = false;
                                    } else {
                                        sb3.append(", ");
                                    }
                                    sb3.append(dataLight2.name);
                                }
                                i14++;
                            } else {
                                if (dataLight2 != null && dataLight2.name != null) {
                                    if (z10) {
                                        z10 = false;
                                    } else {
                                        sb4.append(", ");
                                    }
                                    sb4.append(dataLight2.name);
                                }
                                i15++;
                            }
                        }
                        dataMonitorActions = this;
                        str14 = str17;
                        it4 = it5;
                    }
                    str = str14;
                    if (i14 != 0) {
                        sb2.append("will turn on:");
                        sb2.append("\n");
                        sb2.append((CharSequence) sb3);
                        sb2.append("\n\n");
                    }
                    if (i15 != 0) {
                        sb2.append("will turn off:");
                        sb2.append("\n");
                        sb2.append((CharSequence) sb4);
                        sb2.append("\n\n");
                    }
                }
            }
        }
        DataMonitorActions dataMonitorActions2 = this;
        if (dataMonitorActions2.things == null) {
            str2 = "\n";
            str3 = "\n\n";
        } else {
            String packageName2 = ActivityMain.Companion.getPackageName();
            if (packageName2 != null && StringsKt__StringsKt.startsWith$default(packageName2, str, false, 2, null)) {
                HashMap hashMap13 = dataMonitorActions2.things;
                Intrinsics.checkNotNull(hashMap13);
                if (hashMap13.size() > 0) {
                    sb2.append("MyPlace:\n");
                    StringBuilder sb5 = new StringBuilder();
                    StringBuilder sb6 = new StringBuilder();
                    StringBuilder sb7 = new StringBuilder();
                    StringBuilder sb8 = new StringBuilder();
                    StringBuilder sb9 = new StringBuilder();
                    StringBuilder sb10 = new StringBuilder();
                    HashMap hashMap14 = dataMonitorActions2.things;
                    Intrinsics.checkNotNull(hashMap14);
                    ArrayList arrayList3 = new ArrayList(hashMap14.keySet());
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
                    int i16 = 0;
                    int i17 = 0;
                    int i18 = 0;
                    int i19 = 0;
                    int i20 = 0;
                    int i21 = 0;
                    while (it6.hasNext()) {
                        String str18 = (String) it6.next();
                        Iterator it7 = it6;
                        HashMap hashMap15 = dataMonitorActions2.things;
                        Intrinsics.checkNotNull(hashMap15);
                        DataMyThing dataMyThing = (DataMyThing) hashMap15.get(str18);
                        TreeMap<String, DataMyThing> treeMap5 = masterData.myThings.things;
                        Intrinsics.checkNotNull(treeMap5);
                        DataMyThing dataMyThing2 = treeMap5.get(str18);
                        if (dataMyThing == null || (num = dataMyThing.value) == null) {
                            sb = sb2;
                            str4 = str13;
                            str5 = str9;
                            str6 = str15;
                        } else {
                            str6 = str15;
                            str5 = str9;
                            sb = sb2;
                            if (num == null) {
                                str4 = str13;
                            } else {
                                str4 = str13;
                                if (num.intValue() == 100) {
                                    if (dataMyThing2 != null && (str7 = dataMyThing2.buttonType) != null) {
                                        if (Intrinsics.areEqual(str7, DataMyThing.BUTTON_TYPE_ON_OFF) || Intrinsics.areEqual(dataMyThing2.buttonType, DataMyThing.BUTTON_TYPE_DIMMABLE)) {
                                            if (dataMyThing2.name != null) {
                                                if (z12) {
                                                    z12 = false;
                                                } else {
                                                    sb5.append(", ");
                                                }
                                                sb5.append(dataMyThing2.name);
                                            }
                                            i16++;
                                        } else if (Intrinsics.areEqual(dataMyThing2.buttonType, DataMyThing.BUTTON_TYPE_UP_DOWN)) {
                                            if (dataMyThing2.name != null) {
                                                if (z14) {
                                                    z14 = false;
                                                } else {
                                                    sb7.append(", ");
                                                }
                                                sb7.append(dataMyThing2.name);
                                            }
                                            i18++;
                                        } else if (Intrinsics.areEqual(dataMyThing2.buttonType, DataMyThing.BUTTON_TYPE_OPEN_CLOSE)) {
                                            if (dataMyThing2.name != null) {
                                                if (z15) {
                                                    z15 = false;
                                                } else {
                                                    sb9.append(", ");
                                                }
                                                sb9.append(dataMyThing2.name);
                                            }
                                            i20++;
                                        }
                                    }
                                }
                            }
                            Integer num4 = dataMyThing.value;
                            if (num4 != null && num4.intValue() == 0 && dataMyThing2 != null && (str8 = dataMyThing2.buttonType) != null) {
                                if (Intrinsics.areEqual(str8, DataMyThing.BUTTON_TYPE_ON_OFF) || Intrinsics.areEqual(dataMyThing2.buttonType, DataMyThing.BUTTON_TYPE_DIMMABLE)) {
                                    if (dataMyThing2.name != null) {
                                        if (z13) {
                                            z13 = false;
                                        } else {
                                            sb6.append(", ");
                                        }
                                        sb6.append(dataMyThing2.name);
                                    }
                                    i17++;
                                } else if (Intrinsics.areEqual(dataMyThing2.buttonType, DataMyThing.BUTTON_TYPE_UP_DOWN)) {
                                    if (dataMyThing2.name != null) {
                                        if (z16) {
                                            z16 = false;
                                        } else {
                                            sb8.append(", ");
                                        }
                                        sb8.append(dataMyThing2.name);
                                    }
                                    i19++;
                                } else if (Intrinsics.areEqual(dataMyThing2.buttonType, DataMyThing.BUTTON_TYPE_OPEN_CLOSE)) {
                                    if (dataMyThing2.name != null) {
                                        if (z17) {
                                            z17 = false;
                                        } else {
                                            sb10.append(", ");
                                        }
                                        sb10.append(dataMyThing2.name);
                                    }
                                    i21++;
                                }
                            }
                        }
                        dataMonitorActions2 = this;
                        masterData = dataMaster;
                        it6 = it7;
                        str15 = str6;
                        str9 = str5;
                        sb2 = sb;
                        str13 = str4;
                    }
                    StringBuilder sb11 = sb2;
                    String str19 = str13;
                    String str20 = str9;
                    String str21 = str15;
                    if (i16 != 0) {
                        sb2 = sb11;
                        sb2.append(str19);
                        str2 = str20;
                        sb2.append(str2);
                        sb2.append((CharSequence) sb5);
                        str3 = str21;
                        sb2.append(str3);
                    } else {
                        str3 = str21;
                        str2 = str20;
                        sb2 = sb11;
                    }
                    if (i17 != 0) {
                        sb2.append("will turn off:");
                        sb2.append(str2);
                        sb2.append((CharSequence) sb6);
                        sb2.append(str3);
                    }
                    if (i18 != 0) {
                        sb2.append("will move up:");
                        sb2.append(str2);
                        sb2.append((CharSequence) sb7);
                        sb2.append(str3);
                    }
                    if (i19 != 0) {
                        sb2.append("will move down:");
                        sb2.append(str2);
                        sb2.append((CharSequence) sb8);
                        sb2.append(str3);
                    }
                    if (i20 != 0) {
                        sb2.append("will open:");
                        sb2.append(str2);
                        sb2.append((CharSequence) sb9);
                        sb2.append(str3);
                    }
                    if (i21 != 0) {
                        sb2.append("will close:");
                        sb2.append(str2);
                        sb2.append((CharSequence) sb10);
                        sb2.append(str3);
                    }
                }
            }
        }
        HashMap hashMap16 = this.sonos;
        if (hashMap16 != null) {
            Intrinsics.checkNotNull(hashMap16);
            if (hashMap16.size() > 0) {
                sb2.append("Sonos:");
                HashMap hashMap17 = this.sonos;
                Intrinsics.checkNotNull(hashMap17);
                boolean z18 = true;
                boolean z19 = true;
                for (Sonos sonos : hashMap17.values()) {
                    String component5 = sonos.component5();
                    boolean component8 = sonos.component8();
                    StringBuilder sb12 = new StringBuilder();
                    if (component8) {
                        if (z19) {
                            sb12.append("\nwill play:");
                            sb12.append(str2);
                            z19 = false;
                        }
                        if (!z18) {
                            sb12.append(", ");
                        }
                        sb12.append(component5);
                        z18 = false;
                    }
                    sb2.append(sb12.toString());
                }
                HashMap hashMap18 = this.sonos;
                Intrinsics.checkNotNull(hashMap18);
                boolean z20 = true;
                boolean z21 = true;
                for (Sonos sonos2 : hashMap18.values()) {
                    String component52 = sonos2.component5();
                    boolean component82 = sonos2.component8();
                    StringBuilder sb13 = new StringBuilder();
                    if (!component82) {
                        if (z20) {
                            sb13.append("\nwill pause:");
                            sb13.append(str2);
                            z7 = false;
                        } else {
                            z7 = z20;
                        }
                        if (!z21) {
                            sb13.append(", ");
                        }
                        sb13.append(component52);
                        z20 = z7;
                        z21 = false;
                    }
                    sb2.append(sb13.toString());
                }
            }
        }
        String sb14 = sb2.toString();
        Intrinsics.checkNotNullExpressionValue(sb14, "toString(...)");
        if (StringsKt__StringsJVMKt.J1(sb14, str3, false, 2, null)) {
            sb2 = new StringBuilder(sb2.substring(0, sb2.length() - 2));
        }
        this.autoActionSummary = sb2.toString();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@Nullable DataMonitorActions dataMonitorActions, @Nullable DataManager dataManager) {
        return update$default(this, dataMonitorActions, dataManager, false, 4, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    @JvmOverloads
    public final boolean update(@Nullable DataMonitorActions dataMonitorActions, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        Intrinsics.checkNotNull(dataMonitorActions);
        HashMap hashMap = dataMonitorActions.aircons;
        if (hashMap != null) {
            if (this.aircons == null || !isAirconsCollectionEqual(hashMap)) {
                this.aircons = dataMonitorActions.aircons;
                if (dataManager != null) {
                    dataManager.addSetValue("aircons", dataMonitorActions.aircons);
                }
                z10 = true;
            }
            z10 = false;
        } else {
            if (z7 && this.aircons != null) {
                if (dataManager != null) {
                    dataManager.add("aircons", null);
                }
                z10 = true;
            }
            z10 = false;
        }
        Boolean bool = dataMonitorActions.airconsEnabled;
        if (bool != null) {
            Boolean bool2 = this.airconsEnabled;
            if (bool2 == null || !Intrinsics.areEqual(bool2, bool)) {
                this.airconsEnabled = dataMonitorActions.airconsEnabled;
                if (dataManager != null) {
                    dataManager.add("airconsEnabled", dataMonitorActions.airconsEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.airconsEnabled != null) {
            if (dataManager != null) {
                dataManager.add("airconsEnabled", null);
            }
            z10 = true;
        }
        Boolean bool3 = dataMonitorActions.autoActionEnabled;
        if (bool3 != null) {
            Boolean bool4 = this.autoActionEnabled;
            if (bool4 == null || !Intrinsics.areEqual(bool4, bool3)) {
                this.autoActionEnabled = dataMonitorActions.autoActionEnabled;
                if (dataManager != null) {
                    dataManager.add("autoActionEnabled", dataMonitorActions.autoActionEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.autoActionEnabled != null) {
            if (dataManager != null) {
                dataManager.add("autoActionEnabled", null);
            }
            z10 = true;
        }
        String str = dataMonitorActions.autoActionSummary;
        if (str != null) {
            String str2 = this.autoActionSummary;
            if (str2 == null || !Intrinsics.areEqual(str2, str)) {
                this.autoActionSummary = dataMonitorActions.autoActionSummary;
                if (dataManager != null) {
                    dataManager.add("autoActionSummary", dataMonitorActions.autoActionSummary);
                }
                z10 = true;
            }
        } else if (z7 && this.autoActionSummary != null) {
            if (dataManager != null) {
                dataManager.add("autoActionSummary", null);
            }
            z10 = true;
        }
        HashMap hashMap2 = dataMonitorActions.lights;
        if (hashMap2 != null) {
            if (this.lights == null || !isLightsCollectionEqual(hashMap2)) {
                this.lights = dataMonitorActions.lights;
                if (dataManager != null) {
                    dataManager.addSetValue("lights", dataMonitorActions.lights);
                }
                z10 = true;
            }
        } else if (z7 && this.lights != null) {
            if (dataManager != null) {
                dataManager.add("lights", null);
            }
            z10 = true;
        }
        Boolean bool5 = dataMonitorActions.lightsEnabled;
        if (bool5 != null) {
            Boolean bool6 = this.lightsEnabled;
            if (bool6 == null || !Intrinsics.areEqual(bool6, bool5)) {
                this.lightsEnabled = dataMonitorActions.lightsEnabled;
                if (dataManager != null) {
                    dataManager.add("lightsEnabled", dataMonitorActions.lightsEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.lightsEnabled != null) {
            if (dataManager != null) {
                dataManager.add("lightsEnabled", null);
            }
            z10 = true;
        }
        Boolean bool7 = dataMonitorActions.launchMyAppEnabled;
        if (bool7 != null) {
            Boolean bool8 = this.launchMyAppEnabled;
            if (bool8 == null || !Intrinsics.areEqual(bool8, bool7)) {
                this.launchMyAppEnabled = dataMonitorActions.launchMyAppEnabled;
                if (dataManager != null) {
                    dataManager.add("launchMyAppEnabled", dataMonitorActions.launchMyAppEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.launchMyAppEnabled != null) {
            if (dataManager != null) {
                dataManager.add("launchMyAppEnabled", null);
            }
            z10 = true;
        }
        String str3 = dataMonitorActions.launchMyAppName;
        if (str3 != null) {
            String str4 = this.launchMyAppName;
            if (str4 == null || !Intrinsics.areEqual(str4, str3)) {
                this.launchMyAppName = dataMonitorActions.launchMyAppName;
                if (dataManager != null) {
                    dataManager.add("launchMyAppName", dataMonitorActions.launchMyAppName);
                }
                z10 = true;
            }
        } else if (z7 && this.launchMyAppName != null) {
            if (dataManager != null) {
                dataManager.add("launchMyAppName", null);
            }
            z10 = true;
        }
        String str5 = dataMonitorActions.launchMyAppPackageName;
        if (str5 != null) {
            String str6 = this.launchMyAppPackageName;
            if (str6 == null || !Intrinsics.areEqual(str6, str5)) {
                this.launchMyAppPackageName = dataMonitorActions.launchMyAppPackageName;
                if (dataManager != null) {
                    dataManager.add("launchMyAppPackageName", dataMonitorActions.launchMyAppPackageName);
                }
                z10 = true;
            }
        } else if (z7 && this.launchMyAppPackageName != null) {
            if (dataManager != null) {
                dataManager.add("launchMyAppPackageName", null);
            }
            z10 = true;
        }
        Boolean bool9 = dataMonitorActions.notificationEnabled;
        if (bool9 != null) {
            Boolean bool10 = this.notificationEnabled;
            if (bool10 == null || !Intrinsics.areEqual(bool10, bool9)) {
                this.notificationEnabled = dataMonitorActions.notificationEnabled;
                if (dataManager != null) {
                    dataManager.add("notificationEnabled", dataMonitorActions.notificationEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.notificationEnabled != null) {
            if (dataManager != null) {
                dataManager.add("notificationEnabled", null);
            }
            z10 = true;
        }
        Boolean bool11 = dataMonitorActions.notificationPhoneNumberEnabled;
        if (bool11 != null) {
            Boolean bool12 = this.notificationPhoneNumberEnabled;
            if (bool12 == null || !Intrinsics.areEqual(bool12, bool11)) {
                this.notificationPhoneNumberEnabled = dataMonitorActions.notificationPhoneNumberEnabled;
                if (dataManager != null) {
                    dataManager.add("notificationPhoneNumberEnabled", dataMonitorActions.notificationPhoneNumberEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.notificationPhoneNumberEnabled != null) {
            if (dataManager != null) {
                dataManager.add("notificationPhoneNumberEnabled", null);
            }
            z10 = true;
        }
        String str7 = dataMonitorActions.notificationPhoneNumber;
        if (str7 != null) {
            String str8 = this.notificationPhoneNumber;
            if (str8 == null || !Intrinsics.areEqual(str8, str7)) {
                this.notificationPhoneNumber = dataMonitorActions.notificationPhoneNumber;
                if (dataManager != null) {
                    dataManager.add("notificationPhoneNumber", dataMonitorActions.notificationPhoneNumber);
                }
                z10 = true;
            }
        } else if (z7 && this.notificationPhoneNumber != null) {
            if (dataManager != null) {
                dataManager.add("notificationPhoneNumber", null);
            }
            z10 = true;
        }
        HashMap hashMap3 = dataMonitorActions.things;
        if (hashMap3 != null) {
            if (this.things == null || !isThingsCollectionEqual(hashMap3)) {
                this.things = dataMonitorActions.things;
                if (dataManager != null) {
                    dataManager.addSetValue("things", dataMonitorActions.things);
                }
                z10 = true;
            }
        } else if (z7 && this.things != null) {
            if (dataManager != null) {
                dataManager.add("things", null);
            }
            z10 = true;
        }
        Boolean bool13 = dataMonitorActions.thingsEnabled;
        if (bool13 != null) {
            Boolean bool14 = this.thingsEnabled;
            if (bool14 == null || !Intrinsics.areEqual(bool14, bool13)) {
                this.thingsEnabled = dataMonitorActions.thingsEnabled;
                if (dataManager != null) {
                    dataManager.add("thingsEnabled", dataMonitorActions.thingsEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.thingsEnabled != null) {
            if (dataManager != null) {
                dataManager.add("thingsEnabled", null);
            }
            z10 = true;
        }
        HashMap hashMap4 = dataMonitorActions.sonos;
        if (hashMap4 != null) {
            if (this.sonos == null || !isSonosCollectionEqual(hashMap4)) {
                this.sonos = dataMonitorActions.sonos;
                if (dataManager != null) {
                    dataManager.addSetValue("sonos", dataMonitorActions.sonos);
                }
                z10 = true;
            }
        } else if (z7 && this.sonos != null) {
            if (dataManager != null) {
                dataManager.add("sonos", null);
            }
            z10 = true;
        }
        Boolean bool15 = dataMonitorActions.sonosEnabled;
        if (bool15 != null) {
            Boolean bool16 = this.sonosEnabled;
            if (bool16 == null || !Intrinsics.areEqual(bool16, bool15)) {
                this.sonosEnabled = dataMonitorActions.sonosEnabled;
                if (dataManager == null) {
                    return true;
                }
                dataManager.add("sonosEnabled", dataMonitorActions.sonosEnabled);
                return true;
            }
        } else if (z7 && this.sonosEnabled != null) {
            if (dataManager == null) {
                return true;
            }
            dataManager.add("sonosEnabled", null);
            return true;
        }
        return z10;
    }
}