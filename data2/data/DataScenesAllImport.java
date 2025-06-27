package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import org.jetbrains.annotations.NotNull;

@PurelyImplements({"SMAP\nDataScenesAllImport.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DataScenesAllImport.kt\ncom/air/advantage/data/DataScenesAllImport\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,33:1\n1603#2,9:34\n1855#2:43\n1856#2:45\n1612#2:46\n1#3:44\n*S KotlinDebug\n*F\n+ 1 DataScenesAllImport.kt\ncom/air/advantage/data/DataScenesAllImport\n*L\n24#1:34,9\n24#1:43\n24#1:45\n24#1:46\n24#1:44\n*E\n"})
/* renamed from: com.air.advantage.data.n0 */
/* loaded from: classes.dex */
public final class DataScenesAllImport {

    @SerializedName("scenes")
    @NotNull
    private final HashMap<String, Scene> scenes = new HashMap<>();

    @SerializedName("scenesOrder")
    @NotNull
    private ArrayList<String> scenesOrder = new ArrayList<>();

    @NotNull
    public final DataMyScene getDataScenesAll() {
        DataMyScene dataMyScene = new DataMyScene();
        for (String str : this.scenes.keySet()) {
            Scene scene = this.scenes.get(str);
            DataScene dataScene = scene != null ? scene.getDataScene() : null;
            TreeMap<String, DataScene> treeMap = dataMyScene.scenes;
            Intrinsics.checkNotNull(treeMap);
            treeMap.put(str, dataScene);
        }
        TreeMap<String, DataScene> treeMap2 = dataMyScene.scenes;
        Intrinsics.checkNotNull(treeMap2);
        Set<String> keySet = treeMap2.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet, "<get-keys>(...)");
        ArrayList<String> arrayList = new ArrayList();
        for (String str2 : keySet) {
            if (str2 != null) {
                arrayList.add(str2);
            }
        }
        for (String str3 : arrayList) {
            TreeMap<String, DataScene> treeMap3 = dataMyScene.scenes;
            Intrinsics.checkNotNull(treeMap3);
            DataScene dataScene2 = treeMap3.get(str3);
            if (dataScene2 != null) {
                Intrinsics.checkNotNull(str3);
                dataScene2.id = str3;
            }
        }
        dataMyScene.scenesOrder = new ArrayList<>(this.scenesOrder);
        return dataMyScene;
    }

    @NotNull
    public final HashMap<String, Scene> getScenes() {
        return this.scenes;
    }

    @NotNull
    public final ArrayList<String> getScenesOrder() {
        return this.scenesOrder;
    }

    public final void setScenesOrder(@NotNull ArrayList<String> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.scenesOrder = arrayList;
    }
}