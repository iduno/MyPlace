package com.air.advantage.data;

import com.air.advantage.uart.HandlerLights;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import kotlin.collections.ArraysKt___ArraysKt1;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@PurelyImplements({"SMAP\nDataHardwareConfig.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DataHardwareConfig.kt\ncom/air/advantage/data/DataHardwareConfig\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,143:1\n1603#2,9:144\n1855#2:153\n1856#2:155\n1612#2:156\n1#3:154\n37#4,2:157\n6143#5,2:159\n*S KotlinDebug\n*F\n+ 1 DataHardwareConfig.kt\ncom/air/advantage/data/DataHardwareConfig\n*L\n113#1:144,9\n113#1:153\n113#1:155\n113#1:156\n113#1:154\n113#1:157,2\n114#1:159,2\n*E\n"})
/* renamed from: com.air.advantage.data.o */
/* loaded from: classes.dex */
public final class DataHardwareConfig {

    @Nullable
    @SerializedName("airconUids")
    private HashMap<String, String> airconUids = new HashMap<>();

    @Nullable
    @SerializedName("moduleUids")
    private ArrayList<String> moduleUids = new ArrayList<>();

    @PurelyImplements({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 DataHardwareConfig.kt\ncom/air/advantage/data/DataHardwareConfig\n*L\n1#1,328:1\n114#2:329\n*E\n"})
    /* renamed from: com.air.advantage.data.o$a */
    public static final class Comparisons<T> implements Comparator {
        /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: T */
        /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: T */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Comparator
        public final int compare(T t7, T t10) {
            return ComparisonsKt__ComparisonsKt.compareValues((String) t7, (String) t10);
        }
    }

    private final String generateAirconsHardwareId() {
        StringBuilder sb = new StringBuilder();
        HashMap<String, String> hashMap = this.airconUids;
        if (hashMap != null) {
            Intrinsics.checkNotNull(hashMap);
            Set<String> keySet = hashMap.keySet();
            Intrinsics.checkNotNullExpressionValue(keySet, "<get-keys>(...)");
            ArrayList arrayList = new ArrayList();
            for (String str : keySet) {
                if (str != null) {
                    arrayList.add(str);
                }
            }
            String[] strArr = (String[]) arrayList.toArray(new String[0]);
            boolean z7 = true;
            if (strArr.length > 1) {
                ArraysKt___ArraysKt1.sortWith(strArr, new Comparisons());
            }
            for (String str2 : strArr) {
                if (z7) {
                    z7 = false;
                } else {
                    sb.append("-");
                }
                HashMap<String, String> hashMap2 = this.airconUids;
                Intrinsics.checkNotNull(hashMap2);
                sb.append(hashMap2.get(str2));
            }
        }
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        return sb2;
    }

    @NotNull
    public final DataHardwareConfig copy() {
        DataHardwareConfig dataHardwareConfig = new DataHardwareConfig();
        dataHardwareConfig.airconUids = new HashMap<>();
        HashMap<String, String> hashMap = this.airconUids;
        Intrinsics.checkNotNull(hashMap);
        for (String str : hashMap.keySet()) {
            HashMap<String, String> hashMap2 = dataHardwareConfig.airconUids;
            Intrinsics.checkNotNull(hashMap2);
            HashMap<String, String> hashMap3 = this.airconUids;
            Intrinsics.checkNotNull(hashMap3);
            hashMap2.put(str, hashMap3.get(str));
        }
        dataHardwareConfig.moduleUids = new ArrayList<>(this.moduleUids);
        return dataHardwareConfig;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x001f, code lost:
    
        if (r1.size() == 0) goto L31;
     */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0014  */
    // TODO: Look at code
    @NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String generateHardwareId() {
        StringBuilder sb = new StringBuilder();
        HashMap<String, String> hashMap = this.airconUids;
        if (hashMap != null) {
            Intrinsics.checkNotNull(hashMap);
            if (hashMap.size() == 0) {
                ArrayList<String> arrayList = this.moduleUids;
                if (arrayList != null) {
                    Intrinsics.checkNotNull(arrayList);
                }
                return "";
            }
        }
        sb.append(generateAirconsHardwareId());
        HashMap<String, String> hashMap2 = this.airconUids;
        if (hashMap2 != null) {
            Intrinsics.checkNotNull(hashMap2);
            if (hashMap2.size() == 0 && Intrinsics.areEqual(sb.toString(), "")) {
                sb.append("fffff");
            }
        }
        ArrayList<String> arrayList2 = this.moduleUids;
        Intrinsics.checkNotNull(arrayList2);
        if (arrayList2.size() > 0) {
            sb.append("-");
        }
        ArrayList<String> arrayList3 = this.moduleUids;
        Intrinsics.checkNotNull(arrayList3);
        Iterator<String> it = arrayList3.iterator();
        boolean z7 = true;
        while (it.hasNext()) {
            String next = it.next();
            if (z7) {
                z7 = false;
            } else {
                sb.append("-");
            }
            sb.append(next);
        }
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        return sb2;
    }

    @Nullable
    public final HashMap<String, String> getAirconUids() {
        return this.airconUids;
    }

    @Nullable
    public final ArrayList<String> getModuleUids() {
        return this.moduleUids;
    }

    public final void setAirconUids(@Nullable HashMap<String, String> hashMap) {
        this.airconUids = hashMap;
    }

    public final void setModuleUids(@Nullable ArrayList<String> arrayList) {
        this.moduleUids = arrayList;
    }

    public final void updateFromMasterData(@NotNull DataMaster masterdata) {
        Intrinsics.checkNotNullParameter(masterdata, "masterdata");
        for (String str : masterdata.aircons.keySet()) {
            DataAirconSystem dataAirconSystem = masterdata.aircons.get(str);
            if (dataAirconSystem != null && dataAirconSystem.info.uid != null) {
                HashMap<String, String> hashMap = this.airconUids;
                Intrinsics.checkNotNull(hashMap);
                hashMap.put(str, dataAirconSystem.info.uid);
            }
        }
        TreeMap<String, DataLight> treeMap = masterdata.myLights.lights;
        Intrinsics.checkNotNull(treeMap);
        for (String str2 : treeMap.keySet()) {
            TreeMap<String, DataLight> treeMap2 = masterdata.myLights.lights;
            Intrinsics.checkNotNull(treeMap2);
            if (treeMap2.get(str2) != null) {
                Intrinsics.checkNotNull(str2);
                if (str2.length() == 7 && new Regex(HandlerLights.hexRegx).matches(str2)) {
                    String substring = str2.substring(0, 5);
                    Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
                    ArrayList<String> arrayList = this.moduleUids;
                    Intrinsics.checkNotNull(arrayList);
                    if (!arrayList.contains(substring)) {
                        ArrayList<String> arrayList2 = this.moduleUids;
                        Intrinsics.checkNotNull(arrayList2);
                        arrayList2.add(substring);
                    }
                }
            }
        }
        TreeMap<String, DataMyThing> treeMap3 = masterdata.myThings.things;
        Intrinsics.checkNotNull(treeMap3);
        for (String str3 : treeMap3.keySet()) {
            TreeMap<String, DataMyThing> treeMap4 = masterdata.myThings.things;
            Intrinsics.checkNotNull(treeMap4);
            if (treeMap4.get(str3) != null) {
                Intrinsics.checkNotNull(str3);
                String substring2 = str3.substring(0, 5);
                Intrinsics.checkNotNullExpressionValue(substring2, "this as java.lang.String…ing(startIndex, endIndex)");
                ArrayList<String> arrayList3 = this.moduleUids;
                Intrinsics.checkNotNull(arrayList3);
                if (!arrayList3.contains(substring2)) {
                    ArrayList<String> arrayList4 = this.moduleUids;
                    Intrinsics.checkNotNull(arrayList4);
                    arrayList4.add(substring2);
                }
            }
        }
        Collections.sort(this.moduleUids);
    }
}