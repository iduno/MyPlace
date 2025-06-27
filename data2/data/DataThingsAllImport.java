package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import org.jetbrains.annotations.NotNull;

@PurelyImplements({"SMAP\nDataThingsAllImport.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DataThingsAllImport.kt\ncom/air/advantage/data/DataThingsAllImport\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,41:1\n1603#2,9:42\n1855#2:51\n1856#2:53\n1612#2:54\n1#3:52\n*S KotlinDebug\n*F\n+ 1 DataThingsAllImport.kt\ncom/air/advantage/data/DataThingsAllImport\n*L\n37#1:42,9\n37#1:51\n37#1:53\n37#1:54\n37#1:52\n*E\n"})
/* renamed from: com.air.advantage.data.x0 */
/* loaded from: classes.dex */
public final class DataThingsAllImport {

    @SerializedName("things")
    @NotNull
    private HashMap<String, DataMyThing> things = new HashMap<>();

    @SerializedName("groups")
    @NotNull
    private HashMap<String, DataGroupThing> groups = new HashMap<>();

    @SerializedName("groupsOrder")
    @NotNull
    private ArrayList<String> groupsOrder = new ArrayList<>();

    @SerializedName("system")
    @NotNull
    private DataThingsSystem system = new DataThingsSystem();

    @NotNull
    public final DataThings getDataThingsAll() {
        DataThings dataThings = new DataThings();
        TreeMap<String, DataGroupThing> treeMap = new TreeMap<>(this.groups);
        dataThings.groups = treeMap;
        Intrinsics.checkNotNull(treeMap);
        for (String str : treeMap.keySet()) {
            TreeMap<String, DataGroupThing> treeMap2 = dataThings.groups;
            Intrinsics.checkNotNull(treeMap2);
            DataGroupThing dataGroupThing = treeMap2.get(str);
            if (dataGroupThing != null) {
                dataGroupThing.id = str;
            }
        }
        TreeMap<String, DataMyThing> treeMap3 = new TreeMap<>(this.things);
        dataThings.things = treeMap3;
        Intrinsics.checkNotNull(treeMap3);
        for (String str2 : treeMap3.keySet()) {
            TreeMap<String, DataMyThing> treeMap4 = dataThings.things;
            Intrinsics.checkNotNull(treeMap4);
            DataMyThing dataMyThing = treeMap4.get(str2);
            if (dataMyThing != null) {
                dataMyThing.id = str2;
            }
        }
        ArrayList<String> arrayList = this.groupsOrder;
        ArrayList arrayList2 = new ArrayList();
        for (String str3 : arrayList) {
            if (str3 != null) {
                arrayList2.add(str3);
            }
        }
        dataThings.groupsOrder = new ArrayList<>(arrayList2);
        dataThings.system = this.system;
        return dataThings;
    }

    @NotNull
    public final HashMap<String, DataGroupThing> getGroups() {
        return this.groups;
    }

    @NotNull
    public final ArrayList<String> getGroupsOrder() {
        return this.groupsOrder;
    }

    @NotNull
    public final DataThingsSystem getSystem() {
        return this.system;
    }

    @NotNull
    public final HashMap<String, DataMyThing> getThings() {
        return this.things;
    }

    public final void setGroups(@NotNull HashMap<String, DataGroupThing> hashMap) {
        Intrinsics.checkNotNullParameter(hashMap, "<set-?>");
        this.groups = hashMap;
    }

    public final void setGroupsOrder(@NotNull ArrayList<String> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.groupsOrder = arrayList;
    }

    public final void setSystem(@NotNull DataThingsSystem dataThingsSystem) {
        Intrinsics.checkNotNullParameter(dataThingsSystem, "<set-?>");
        this.system = dataThingsSystem;
    }

    public final void setThings(@NotNull HashMap<String, DataMyThing> hashMap) {
        Intrinsics.checkNotNullParameter(hashMap, "<set-?>");
        this.things = hashMap;
    }
}