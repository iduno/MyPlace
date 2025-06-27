package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.TreeMap;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.m0 */
/* loaded from: classes.dex */
public final class DataMyScene {
    public static final int MAX_SCENES = 12;

    @Nullable
    @SerializedName("scenes")
    @JvmField
    public TreeMap<String, DataScene> scenes = new TreeMap<>();

    @Nullable
    @SerializedName("scenesOrder")
    @JvmField
    public ArrayList<String> scenesOrder = new ArrayList<>();

    @NotNull
    public static final a Companion = new a(null);

    @NotNull
    @JvmField
    public static final DataScene SCENE_MY_UNDO = new DataScene("s0", "MyUndo", "");

    @NotNull
    @JvmField
    public static final DataScene SCENE_MY_WELCOME = new DataScene("s1", "MyWelcome", "");

    @NotNull
    @JvmField
    public static final DataScene SCENE_MY_GOODBYE = new DataScene("s2", "MyGoodbye", "");

    @NotNull
    @JvmField
    public static final DataScene SCENE_MY_ECO = new DataScene("s3", "MyEco", "");

    @NotNull
    @JvmField
    public static final DataScene SCENE_MY_SUNSET = new DataScene("s4", "MySunset", "");

    /* renamed from: com.air.advantage.data.m0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.m0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    private final ArrayList<DataScene> getNonSpecialScene() {
        ArrayList<DataScene> arrayList = new ArrayList<>();
        TreeMap<String, DataScene> treeMap = this.scenes;
        Intrinsics.checkNotNull(treeMap);
        for (DataScene dataScene : treeMap.values()) {
            Intrinsics.checkNotNull(dataScene);
            if (!Intrinsics.areEqual(dataScene.id, SCENE_MY_UNDO.id) && !Intrinsics.areEqual(dataScene.id, SCENE_MY_ECO.id) && !Intrinsics.areEqual(dataScene.id, SCENE_MY_WELCOME.id) && !Intrinsics.areEqual(dataScene.id, SCENE_MY_SUNSET.id) && !Intrinsics.areEqual(dataScene.id, SCENE_MY_GOODBYE.id)) {
                arrayList.add(dataScene);
            }
        }
        return arrayList;
    }

    public final boolean addScene(@NotNull DataScene dataScene) {
        Intrinsics.checkNotNullParameter(dataScene, "dataScene");
        DataScene scene = getScene(dataScene.id);
        if (scene == null) {
            if (Intrinsics.areEqual(dataScene.id, SCENE_MY_GOODBYE.id) || Intrinsics.areEqual(dataScene.id, SCENE_MY_WELCOME.id) || Intrinsics.areEqual(dataScene.id, SCENE_MY_SUNSET.id) || Intrinsics.areEqual(dataScene.id, SCENE_MY_UNDO.id)) {
                TreeMap<String, DataScene> treeMap = this.scenes;
                Intrinsics.checkNotNull(treeMap);
                treeMap.put(dataScene.id, dataScene);
                return true;
            }
            if (getNonSpecialScene().size() >= 12) {
                return false;
            }
            TreeMap<String, DataScene> treeMap2 = this.scenes;
            Intrinsics.checkNotNull(treeMap2);
            treeMap2.put(dataScene.id, dataScene);
            ArrayList<String> arrayList = this.scenesOrder;
            Intrinsics.checkNotNull(arrayList);
            arrayList.add(dataScene.id);
            return true;
        }
        if (Intrinsics.areEqual(dataScene.id, SCENE_MY_GOODBYE.id) || Intrinsics.areEqual(dataScene.id, SCENE_MY_WELCOME.id) || Intrinsics.areEqual(dataScene.id, SCENE_MY_SUNSET.id) || Intrinsics.areEqual(dataScene.id, SCENE_MY_UNDO.id)) {
            scene.canMessages = dataScene.canMessages;
            scene.lights = dataScene.lights;
            scene.things = dataScene.things;
            scene.aircons = dataScene.aircons;
            scene.monitors = dataScene.monitors;
            scene.sonos = dataScene.sonos;
            if (Intrinsics.areEqual(dataScene.id, SCENE_MY_SUNSET.id)) {
                scene.timerEnabled = dataScene.timerEnabled;
                scene.myTimeEnabled = dataScene.myTimeEnabled;
            }
        } else {
            scene.name = dataScene.name;
            scene.activeDays = dataScene.activeDays;
            scene.timerEnabled = dataScene.timerEnabled;
            scene.myTimeEnabled = dataScene.myTimeEnabled;
            scene.startTime = dataScene.startTime;
            scene.airconStopTime = dataScene.airconStopTime;
            scene.airconStopTimeEnabled = dataScene.airconStopTimeEnabled;
            scene.lights = dataScene.lights;
            scene.things = dataScene.things;
            scene.aircons = dataScene.aircons;
            scene.monitors = dataScene.monitors;
            scene.sonos = dataScene.sonos;
            scene.canMessages = dataScene.canMessages;
            scene.summary = dataScene.summary;
        }
        return true;
    }

    public final void clearDataForBackup() {
        TreeMap<String, DataScene> treeMap = this.scenes;
        Intrinsics.checkNotNull(treeMap);
        for (DataScene dataScene : treeMap.values()) {
            Intrinsics.checkNotNull(dataScene);
            dataScene.clearDataForBackup();
        }
    }

    @Nullable
    public final DataScene getScene(@Nullable String str) {
        TreeMap<String, DataScene> treeMap = this.scenes;
        Intrinsics.checkNotNull(treeMap);
        for (DataScene dataScene : treeMap.values()) {
            Intrinsics.checkNotNull(dataScene);
            String str2 = dataScene.id;
            if (str2 != null && Intrinsics.areEqual(str2, str)) {
                return dataScene;
            }
        }
        return null;
    }

    public final boolean removeScene(@Nullable String str) {
        TreeMap<String, DataScene> treeMap = this.scenes;
        Intrinsics.checkNotNull(treeMap);
        return treeMap.remove(str) != null;
    }
}