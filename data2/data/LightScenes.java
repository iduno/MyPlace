package com.air.advantage.data;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.air.advantage.jsondata.MasterStore;
import com.air.advantage.libraryairconlightjson.UartConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

@PurelyImplements({"SMAP\nLightScenes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LightScenes.kt\ncom/air/advantage/data/LightScenes\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,241:1\n1#2:242\n*E\n"})
/* renamed from: com.air.advantage.data.f1 */
/* loaded from: classes.dex */
public final class LightScenes {

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = LightScenes.class.getSimpleName();
    private boolean blockSceneUpdates;

    @Nullable
    private ArrayList<DataScene> blockedScenes;
    private boolean isScenesPaused;

    @Nullable
    private b onSceneChangeListener;

    @NotNull
    private final List<String> lightScenes = new ArrayList();

    @NotNull
    private final HashMap<String, DataScene> lightSceneHashMap = new HashMap<>();

    @NotNull
    private final TreeMap<String, DataScene> masterScenes = new TreeMap<>();

    /* renamed from: com.air.advantage.data.f1$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.f1.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* renamed from: com.air.advantage.data.f1$b */
    public interface b {
        void onSceneAdded(@Nullable String str, int i10, int i11);

        void onSceneRemoved(@Nullable String str, int i10, int i11);

        void onSceneUpdated(@Nullable String str, int i10);
    }

    public final void addScene(@Nullable Context context, @NotNull DataScene lightScene, int i10) {
        Intrinsics.checkNotNullParameter(lightScene, "lightScene");
        Timber.forest.d("Adding new scene " + lightScene.id, new Object[0]);
        DataScene dataScene = new DataScene();
        DataScene.update$default(dataScene, context, lightScene, null, false, 8, null);
        synchronized (MasterStore.class) {
            this.lightScenes.add(i10, dataScene.id);
            this.lightSceneHashMap.put(dataScene.id, dataScene);
        }
        if (i10 >= 11) {
            b bVar = this.onSceneChangeListener;
            if (bVar != null) {
                Intrinsics.checkNotNull(bVar);
                bVar.onSceneUpdated(lightScene.id, i10);
                return;
            }
            return;
        }
        b bVar2 = this.onSceneChangeListener;
        if (bVar2 != null) {
            Intrinsics.checkNotNull(bVar2);
            bVar2.onSceneAdded(lightScene.id, i10, 1);
        }
        Intent intent = new Intent(UartConstants.NUMBER_OF_LIGHT_SCENE_UPDATE);
        Intrinsics.checkNotNull(context);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public final boolean checkDuplicateName(@NotNull String sceneName) {
        Intrinsics.checkNotNullParameter(sceneName, "sceneName");
        synchronized (MasterStore.class) {
            Iterator<DataScene> it = this.lightSceneHashMap.values().iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(it.next().name, sceneName)) {
                    return true;
                }
            }
            Unit unit = Unit.INSTANCE;
            return false;
        }
    }

    public final void clear() {
        synchronized (MasterStore.class) {
            this.lightScenes.clear();
            this.lightSceneHashMap.clear();
            this.onSceneChangeListener = null;
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void deleteScene(@Nullable Context context, @NotNull String scenedId) {
        Intrinsics.checkNotNullParameter(scenedId, "scenedId");
        synchronized (MasterStore.class) {
            Timber.forest.d("Deleting scene " + scenedId, new Object[0]);
            int indexOf = this.lightScenes.indexOf(scenedId);
            this.lightSceneHashMap.remove(scenedId);
            this.lightScenes.remove(scenedId);
            if (indexOf < 11) {
                b bVar = this.onSceneChangeListener;
                if (bVar != null) {
                    Intrinsics.checkNotNull(bVar);
                    bVar.onSceneRemoved(scenedId, indexOf, 1);
                }
                Intent intent = new Intent(UartConstants.NUMBER_OF_LIGHT_SCENE_UPDATE);
                Intrinsics.checkNotNull(context);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            } else {
                b bVar2 = this.onSceneChangeListener;
                if (bVar2 != null) {
                    bVar2.onSceneUpdated(scenedId, indexOf);
                    Unit unit = Unit.INSTANCE;
                }
            }
        }
    }

    @Nullable
    public final DataScene getMasterScene(@Nullable String str) {
        DataScene dataScene;
        synchronized (MasterStore.class) {
            dataScene = this.masterScenes.get(str);
        }
        return dataScene;
    }

    @Nullable
    public final DataScene getScene(@Nullable String str) {
        DataScene dataScene;
        synchronized (MasterStore.class) {
            dataScene = this.lightSceneHashMap.get(str);
        }
        return dataScene;
    }

    @Nullable
    public final DataScene getSceneAtPosition(int i10) {
        synchronized (MasterStore.class) {
            if (this.lightSceneHashMap.size() > i10) {
                return this.lightSceneHashMap.get(this.lightScenes.get(i10));
            }
            Unit unit = Unit.INSTANCE;
            return null;
        }
    }

    public final int numberOfRealScenes() {
        int size;
        synchronized (MasterStore.class) {
            size = this.lightScenes.size();
        }
        return size;
    }

    public final int numberOfScenes() {
        int size;
        if (this.isScenesPaused) {
            return 0;
        }
        synchronized (MasterStore.class) {
            size = this.lightScenes.size();
            if (size < 12) {
                size++;
            }
        }
        return size;
    }

    public final void setBlockSceneUpdates(@Nullable Context context, boolean z7) {
        ArrayList<DataScene> arrayList;
        this.blockSceneUpdates = z7;
        if (z7 || (arrayList = this.blockedScenes) == null || context == null) {
            return;
        }
        Intrinsics.checkNotNull(arrayList);
        updateScene(context, arrayList);
        this.blockedScenes = null;
    }

    public final void setOnSceneChangeListener(@Nullable b bVar) {
        this.onSceneChangeListener = bVar;
    }

    public final void setScenesPaused(@Nullable Context context, boolean z7) {
        if (z7) {
            int numberOfScenes = numberOfScenes();
            this.isScenesPaused = true;
            b bVar = this.onSceneChangeListener;
            if (bVar != null) {
                Intrinsics.checkNotNull(bVar);
                bVar.onSceneRemoved("Paused", 0, numberOfScenes);
                Intent intent = new Intent(UartConstants.NUMBER_OF_LIGHT_SCENE_UPDATE);
                Intrinsics.checkNotNull(context);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                return;
            }
            return;
        }
        this.isScenesPaused = false;
        if (this.onSceneChangeListener != null) {
            int numberOfScenes2 = numberOfScenes();
            b bVar2 = this.onSceneChangeListener;
            Intrinsics.checkNotNull(bVar2);
            bVar2.onSceneAdded("Paused", 0, numberOfScenes2);
            Intent intent2 = new Intent(UartConstants.NUMBER_OF_LIGHT_SCENE_UPDATE);
            Intrinsics.checkNotNull(context);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void updateScene(@Nullable Context context, @NotNull DataMaster incomingMasterData) {
        Intrinsics.checkNotNullParameter(incomingMasterData, "incomingMasterData");
        ArrayList<DataScene> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = incomingMasterData.myScenes.scenesOrder;
        Intrinsics.checkNotNull(arrayList2);
        Iterator<String> it = arrayList2.iterator();
        while (it.hasNext()) {
            DataScene scene = incomingMasterData.myScenes.getScene(it.next());
            if (scene != null) {
                DataScene dataScene = new DataScene();
                DataScene.update$default(dataScene, null, scene, null, false, 8, null);
                if (!Intrinsics.areEqual(dataScene.id, "s0")) {
                    if (dataScene.myTimeEnabled == null) {
                        dataScene.myTimeEnabled = Boolean.FALSE;
                    }
                    arrayList.add(arrayList.size(), dataScene);
                }
            }
        }
        TreeMap<String, DataScene> treeMap = incomingMasterData.myScenes.scenes;
        Intrinsics.checkNotNull(treeMap);
        for (DataScene dataScene2 : treeMap.values()) {
            Intrinsics.checkNotNull(dataScene2);
            if (dataScene2.id.length() < 3) {
                if (this.masterScenes.containsKey(dataScene2.id)) {
                    DataScene dataScene3 = this.masterScenes.get(dataScene2.id);
                    Intrinsics.checkNotNull(dataScene3);
                    DataScene.update$default(dataScene3, context, dataScene2, null, false, 8, null);
                } else {
                    DataScene dataScene4 = new DataScene();
                    DataScene.update$default(dataScene4, context, dataScene2, null, false, 8, null);
                    this.masterScenes.put(dataScene2.id, dataScene4);
                }
            }
        }
        updateScene(context, arrayList);
    }

    private final void updateScene(Context context, ArrayList<DataScene> arrayList) {
        if (this.blockSceneUpdates) {
            this.blockedScenes = new ArrayList<>(arrayList);
            return;
        }
        ArrayList arrayList2 = new ArrayList(this.lightScenes);
        Iterator<DataScene> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.remove(it.next().id);
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            String str = (String) it2.next();
            if (str != null) {
                deleteScene(context, str);
            }
        }
        Iterator<DataScene> it3 = arrayList.iterator();
        int i10 = 0;
        while (it3.hasNext()) {
            DataScene next = it3.next();
            int indexOf = this.lightScenes.indexOf(next.id);
            if (indexOf < 0) {
                Intrinsics.checkNotNull(next);
                addScene(context, next, i10);
            } else {
                if (indexOf != i10) {
                    Timber.forest.d("Scene moved", new Object[0]);
                }
                DataScene sceneAtPosition = getSceneAtPosition(i10);
                if (sceneAtPosition != null) {
                    DataScene.update$default(sceneAtPosition, context, next, null, false, 8, null);
                } else {
                    Timber.forest.d("Warning - null scene", new Object[0]);
                }
            }
            i10++;
        }
    }
}