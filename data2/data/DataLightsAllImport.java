package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import org.jetbrains.annotations.NotNull;

@PurelyImplements({"SMAP\nDataLightsAllImport.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DataLightsAllImport.kt\ncom/air/advantage/data/DataLightsAllImport\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,71:1\n1603#2,9:72\n1855#2:81\n1856#2:83\n1612#2:84\n1603#2,9:85\n1855#2:94\n1856#2:96\n1612#2:97\n1#3:82\n1#3:95\n*S KotlinDebug\n*F\n+ 1 DataLightsAllImport.kt\ncom/air/advantage/data/DataLightsAllImport\n*L\n47#1:72,9\n47#1:81\n47#1:83\n47#1:84\n50#1:85,9\n50#1:94\n50#1:96\n50#1:97\n47#1:82\n50#1:95\n*E\n"})
/* renamed from: com.air.advantage.data.u */
/* loaded from: classes.dex */
public final class DataLightsAllImport {

    @SerializedName("lights")
    @NotNull
    private final HashMap<String, DataLight> lights = new HashMap<>();

    @SerializedName("groups")
    @NotNull
    private final HashMap<String, DataGroup> groups = new HashMap<>();

    @SerializedName("alarms")
    @NotNull
    private final HashMap<String, DataAlarm> alarms = new HashMap<>();

    @SerializedName("groupsOrder")
    @NotNull
    private ArrayList<String> groupsOrder = new ArrayList<>();

    @SerializedName("scenesOrder")
    @NotNull
    private ArrayList<String> scenesOrder = new ArrayList<>();

    @SerializedName("alarmsOrder")
    @NotNull
    private ArrayList<String> alarmsOrder = new ArrayList<>();

    @SerializedName("system")
    @NotNull
    private DataLightsSystem system = new DataLightsSystem();

    @NotNull
    public final HashMap<String, DataAlarm> getAlarms() {
        return this.alarms;
    }

    @NotNull
    public final ArrayList<String> getAlarmsOrder() {
        return this.alarmsOrder;
    }

    @NotNull
    public final DataMyLights getDataLightsAll() {
        DataMyLights dataMyLights = new DataMyLights();
        TreeMap<String, DataGroup> treeMap = new TreeMap<>(this.groups);
        dataMyLights.groups = treeMap;
        Intrinsics.checkNotNull(treeMap);
        for (String str : treeMap.keySet()) {
            TreeMap<String, DataGroup> treeMap2 = dataMyLights.groups;
            Intrinsics.checkNotNull(treeMap2);
            DataGroup dataGroup = treeMap2.get(str);
            if (dataGroup != null) {
                dataGroup.id = str;
            }
        }
        TreeMap<String, DataLight> treeMap3 = new TreeMap<>(this.lights);
        dataMyLights.lights = treeMap3;
        Intrinsics.checkNotNull(treeMap3);
        for (String str2 : treeMap3.keySet()) {
            TreeMap<String, DataLight> treeMap4 = dataMyLights.lights;
            Intrinsics.checkNotNull(treeMap4);
            DataLight dataLight = treeMap4.get(str2);
            if (dataLight != null) {
                dataLight.id = str2;
            }
        }
        ArrayList<String> arrayList = this.groupsOrder;
        ArrayList arrayList2 = new ArrayList();
        for (String str3 : arrayList) {
            if (str3 != null) {
                arrayList2.add(str3);
            }
        }
        dataMyLights.groupsOrder = new ArrayList<>(arrayList2);
        TreeMap<String, DataAlarm> treeMap5 = new TreeMap<>(this.alarms);
        dataMyLights.alarms = treeMap5;
        Intrinsics.checkNotNull(treeMap5);
        Set<String> keySet = treeMap5.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet, "<get-keys>(...)");
        ArrayList<String> arrayList3 = new ArrayList();
        for (String str4 : keySet) {
            if (str4 != null) {
                arrayList3.add(str4);
            }
        }
        for (String str5 : arrayList3) {
            TreeMap<String, DataAlarm> treeMap6 = dataMyLights.alarms;
            Intrinsics.checkNotNull(treeMap6);
            DataAlarm dataAlarm = treeMap6.get(str5);
            if (dataAlarm != null) {
                Intrinsics.checkNotNull(str5);
                dataAlarm.id = str5;
            }
        }
        TreeMap<String, DataAlarm> treeMap7 = dataMyLights.alarms;
        Intrinsics.checkNotNull(treeMap7);
        for (DataAlarm dataAlarm2 : treeMap7.values()) {
            Intrinsics.checkNotNull(dataAlarm2);
            HashMap<String, DataLight> hashMap = dataAlarm2.lights;
            if (hashMap != null) {
                Intrinsics.checkNotNull(hashMap);
                for (String str6 : hashMap.keySet()) {
                    HashMap<String, DataLight> hashMap2 = dataAlarm2.lights;
                    Intrinsics.checkNotNull(hashMap2);
                    DataLight dataLight2 = hashMap2.get(str6);
                    if (dataLight2 != null) {
                        dataLight2.id = str6;
                    }
                }
            }
        }
        dataMyLights.alarmsOrder = new ArrayList<>(this.alarmsOrder);
        dataMyLights.system = this.system;
        return dataMyLights;
    }

    @NotNull
    public final HashMap<String, DataGroup> getGroups() {
        return this.groups;
    }

    @NotNull
    public final ArrayList<String> getGroupsOrder() {
        return this.groupsOrder;
    }

    @NotNull
    public final HashMap<String, DataLight> getLights() {
        return this.lights;
    }

    @NotNull
    public final ArrayList<String> getScenesOrder() {
        return this.scenesOrder;
    }

    @NotNull
    public final DataLightsSystem getSystem() {
        return this.system;
    }

    public final void setAlarmsOrder(@NotNull ArrayList<String> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.alarmsOrder = arrayList;
    }

    public final void setGroupsOrder(@NotNull ArrayList<String> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.groupsOrder = arrayList;
    }

    public final void setScenesOrder(@NotNull ArrayList<String> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.scenesOrder = arrayList;
    }

    public final void setSystem(@NotNull DataLightsSystem dataLightsSystem) {
        Intrinsics.checkNotNullParameter(dataLightsSystem, "<set-?>");
        this.system = dataLightsSystem;
    }
}