package com.air.advantage.data;

import android.content.Context;
import android.content.Intent;
import com.air.advantage.AppFeatures;
import com.air.advantage.SharedPreferencesStore;
import com.air.advantage.di.LocalBroadcaster;
import com.air.advantage.jsondata.MasterStore;
import com.air.advantage.libraryairconlightjson.CommonFuncs;
import com.air.advantage.libraryairconlightjson.LightState;
import com.air.advantage.libraryairconlightjson.UartConstants;
import com.air.advantage.lights.LightFunctions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.DebugKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

@PurelyImplements({"SMAP\nLightStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LightStore.kt\ncom/air/advantage/data/LightStore\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,1035:1\n1603#2,9:1036\n1855#2:1045\n1856#2:1047\n1612#2:1048\n731#2,9:1050\n1#3:1046\n1#3:1049\n37#4,2:1059\n*S KotlinDebug\n*F\n+ 1 LightStore.kt\ncom/air/advantage/data/LightStore\n*L\n119#1:1036,9\n119#1:1045\n119#1:1047\n119#1:1048\n906#1:1050,9\n119#1:1046\n907#1:1059,2\n*E\n"})
/* renamed from: com.air.advantage.data.g1 */
/* loaded from: classes.dex */
public final class LightStore {
    public static final int GROUP_ALL_OFF = 4;
    public static final int GROUP_ALL_ON = 3;
    public static final int GROUP_EXPANDED = 1;
    private static final int GROUP_SHRUNK = 2;
    public static final int MAX_NO_OF_FAVOURITE_SCENES = 4;
    private boolean blockLightUpdates;

    @Nullable
    private ArrayList<DataLight> blockedLights;
    private boolean lightsPaused;

    @JvmField
    public boolean newAlarm;
    private boolean newScene;

    @Nullable
    private b onAlarmNameChangeListener;

    @Nullable
    private OnLightChangeListener onLightChangeListener;

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = LightStore.class.getSimpleName();

    @NotNull
    private final DataScene editSceneData = new DataScene();

    @NotNull
    @JvmField
    public final DataAlarm editAlarmData = new DataAlarm();

    @NotNull
    private final LightFunctions lightFunctions = LightFunctions.f6458c.a();

    @NotNull
    private ArrayList<DataLight> lightsAndGroupsForScene = new ArrayList<>();

    @NotNull
    @JvmField
    public ArrayList<String> favScenes = new ArrayList<>();

    @NotNull
    @JvmField
    public ArrayList<DataLight> lightsAndGroupsForAlarm = new ArrayList<>();

    @NotNull
    private final ArrayList<String> lightIdListForAlarmEdit = new ArrayList<>();

    @NotNull
    private ArrayList<DataLight> lightsDimmableInDM = new ArrayList<>();

    @NotNull
    private final ArrayList<String> expandedGroups = new ArrayList<>();

    @NotNull
    private ArrayList<String> expandedList = new ArrayList<>();

    @NotNull
    private ArrayList<String> groupList = new ArrayList<>();

    @NotNull
    private final ArrayList<String> lightIdListForSceneEdit = new ArrayList<>();

    @NotNull
    @JvmField
    public final HashMap<String, Integer> numberInGroup = new HashMap<>();

    @NotNull
    private final HashMap<String, DataLight> lightHashMap = new HashMap<>();

    @NotNull
    private final ArrayList<String> lightList = new ArrayList<>();

    /* renamed from: com.air.advantage.data.g1$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.g1.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* renamed from: com.air.advantage.data.g1$b */
    public interface b {
        void onAlarmNameUpdated(@Nullable String str);
    }

    /* renamed from: com.air.advantage.data.g1$c */
    public interface OnLightChangeListener {
        void onLightMoved(@Nullable String str, int i10, int i11);

        void onLightUpdated(int i10);

        void onLightsAdded(@Nullable String str, int i10, int i11);

        void onLightsRemoved(@Nullable String str, int i10, int i11);

        void requestScrollToPosition(int i10);
    }

    private final int getExpandedLightPositionFromId(String str) {
        return this.expandedList.indexOf(str);
    }

    private final String getGroupIdOfLightInEditScene(String str) {
        Iterator<String> it = this.lightIdListForSceneEdit.iterator();
        String str2 = null;
        while (it.hasNext()) {
            String next = it.next();
            Intrinsics.checkNotNull(next);
            if (StringsKt__StringsJVMKt.startsWith(next, "g", false, 2, null)) {
                str2 = next;
            }
            if (Intrinsics.areEqual(next, str)) {
                break;
            }
        }
        return str2;
    }

    private final DataLight getLightAtPosition(int i10) {
        if (i10 < this.lightList.size()) {
            return this.lightHashMap.get(this.lightList.get(i10));
        }
        return null;
    }

    private final int getLightPosition(String str) {
        return this.lightList.indexOf(str);
    }

    private final int getLightPositionForAlarmEdit(String str) {
        return this.lightIdListForAlarmEdit.indexOf(str);
    }

    private final int getLightPositionForSceneEdit(String str) {
        return this.lightIdListForSceneEdit.indexOf(str);
    }

    private final void sendGroupChangeBroadcast(String str) {
        Intent intent = new Intent(UartConstants.LIGHT_GROUP_UPDATE);
        intent.putExtra("groupId", str);
        ((LocalBroadcaster) KoinJavaComponent.get$default(LocalBroadcaster.class, null, null, 6, null)).sendBroadcast(intent);
    }

    private final void sendLightAddedMessage(String str, String str2, int i10) {
        if (i10 >= 0) {
            Timber.forest.d("New light " + str + " has appeared in group " + str2 + " position " + i10, new Object[0]);
            OnLightChangeListener onLightChangeListener = this.onLightChangeListener;
            if (onLightChangeListener != null) {
                Intrinsics.checkNotNull(onLightChangeListener);
                onLightChangeListener.onLightsAdded(str, i10, 1);
            }
        }
    }

    private final ArrayList<String> workOutExpandedList() {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        Iterator<String> it = this.lightList.iterator();
        String str = "";
        int i10 = 0;
        while (it.hasNext()) {
            String next = it.next();
            Intrinsics.checkNotNull(next);
            if (StringsKt__StringsJVMKt.startsWith(next, "g", false, 2, null)) {
                arrayList.add(next);
                arrayList2.add(next);
                this.numberInGroup.put(next, 0);
                i10 = 0;
                str = next;
            } else if (this.expandedGroups.contains(str)) {
                arrayList.add(next);
                i10++;
                this.numberInGroup.put(str, Integer.valueOf(i10));
            } else {
                i10++;
                this.numberInGroup.put(str, Integer.valueOf(i10));
            }
        }
        this.groupList = arrayList2;
        return arrayList;
    }

    public final void addGroup(@NotNull DataLight dataGroup) {
        Intrinsics.checkNotNullParameter(dataGroup, "dataGroup");
        synchronized (MasterStore.class) {
            this.lightHashMap.put(dataGroup.id, dataGroup);
            this.lightList.add(dataGroup.id);
            this.expandedList = workOutExpandedList();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void addLightToGroup(@Nullable String str, @Nullable String str2) {
        synchronized (MasterStore.class) {
            new CommonFuncs().moveItem(this.lightList, getLightPosition(str), getLightPosition(str2));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void checkAndUpdateGroupIncludedStateInEditSceneForALight(@NotNull DataLight dataLight) {
        DataLight lightForSceneEdit;
        boolean z7;
        Intrinsics.checkNotNullParameter(dataLight, "dataLight");
        synchronized (MasterStore.class) {
            MasterStore helper = MasterStore.helper.getInstance();
            LightStore lightStore = helper.dataFavourites.lightStore;
            String str = dataLight.id;
            Intrinsics.checkNotNull(str);
            String groupIdOfLightInEditScene = lightStore.getGroupIdOfLightInEditScene(str);
            if (groupIdOfLightInEditScene != null && (lightForSceneEdit = helper.dataFavourites.lightStore.getLightForSceneEdit(groupIdOfLightInEditScene)) != null) {
                Boolean bool = dataLight.enableInScene;
                Intrinsics.checkNotNull(bool);
                if (bool.booleanValue()) {
                    lightForSceneEdit.enableInScene = Boolean.TRUE;
                    helper.dataFavourites.lightStore.notifyLightInEditSceneUpdateListener(groupIdOfLightInEditScene);
                } else {
                    Iterator<DataLight> it = helper.dataFavourites.lightStore.getLightsInGroupFromEditSceneCollection(groupIdOfLightInEditScene).iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z7 = true;
                            break;
                        }
                        Boolean bool2 = it.next().enableInScene;
                        Intrinsics.checkNotNull(bool2);
                        if (bool2.booleanValue()) {
                            z7 = false;
                            break;
                        }
                    }
                    if (z7) {
                        lightForSceneEdit.enableInScene = Boolean.FALSE;
                        helper.dataFavourites.lightStore.notifyLightInEditSceneUpdateListener(groupIdOfLightInEditScene);
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void checkGroupState(@NotNull Context context, @Nullable MasterStore masterStore, @Nullable String str) {
        LightState lightState;
        LightState lightState2;
        LightState lightState3;
        Intrinsics.checkNotNullParameter(context, "context");
        String groupIdOfLight = getGroupIdOfLight(str);
        int indexOf = this.lightList.indexOf(groupIdOfLight) + 1;
        Integer num = this.numberInGroup.get(groupIdOfLight);
        Intrinsics.checkNotNull(num);
        int intValue = num.intValue();
        LightState lightState4 = LightState.off;
        int i10 = intValue + indexOf;
        while (true) {
            if (indexOf < i10) {
                DataLight lightAtPosition = getLightAtPosition(indexOf);
                if (lightAtPosition != null && (lightState2 = lightAtPosition.state) != null && lightState2 == (lightState3 = LightState.on)) {
                    lightState = lightState3;
                    break;
                }
                indexOf++;
            } else {
                lightState = lightState4;
                break;
            }
        }
        DataLight dataLight = this.lightHashMap.get(groupIdOfLight);
        if (dataLight != null) {
            LightFunctions lightFunctions = this.lightFunctions;
            Intrinsics.checkNotNull(masterStore);
            lightFunctions.s(context, masterStore, dataLight, lightState, false, false);
        }
    }

    public final void clear() {
        synchronized (MasterStore.class) {
            this.lightHashMap.clear();
            this.lightList.clear();
            this.expandedGroups.clear();
            this.expandedList = workOutExpandedList();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void deleteGroup(@NotNull String groupId) {
        Intrinsics.checkNotNullParameter(groupId, "groupId");
        synchronized (MasterStore.class) {
            if (StringsKt__StringsJVMKt.startsWith(groupId, "g", false, 2, null)) {
                this.lightHashMap.remove(groupId);
                this.lightList.remove(groupId);
                this.expandedList = workOutExpandedList();
                sendGroupChangeBroadcast(groupId);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final int getAllLightsCountForSceneEdit() {
        return this.lightsAndGroupsForScene.size();
    }

    @NotNull
    public final DataScene getEditSceneData() {
        return this.editSceneData;
    }

    @Nullable
    public final DataLight getExpandedLightByPosition(int i10) {
        synchronized (MasterStore.class) {
            if (i10 >= 0) {
                if (i10 < numberOfExpandedLightsToShow()) {
                    return this.lightHashMap.get(this.expandedList.get(i10));
                }
            }
            Unit unit = Unit.INSTANCE;
            return null;
        }
    }

    public final int getExpandedLightPosition(@Nullable String str) {
        int indexOf;
        synchronized (MasterStore.class) {
            indexOf = this.expandedList.indexOf(str);
        }
        return indexOf;
    }

    @Nullable
    public final DataLight getGroupByNumber(int i10) {
        synchronized (MasterStore.class) {
            if (i10 < this.groupList.size()) {
                return this.lightHashMap.get(this.groupList.get(i10));
            }
            Unit unit = Unit.INSTANCE;
            return null;
        }
    }

    @Nullable
    public final String getGroupIdOfLight(@Nullable String str) {
        String str2;
        synchronized (MasterStore.class) {
            Iterator<String> it = this.lightList.iterator();
            str2 = null;
            while (it.hasNext()) {
                String next = it.next();
                Intrinsics.checkNotNull(next);
                if (StringsKt__StringsJVMKt.startsWith(next, "g", false, 2, null)) {
                    str2 = next;
                }
                if (Intrinsics.areEqual(next, str)) {
                    break;
                }
            }
        }
        return str2;
    }

    @Nullable
    public final DataLight getLight(@Nullable String str) {
        DataLight dataLight;
        synchronized (MasterStore.class) {
            dataLight = this.lightHashMap.get(str);
        }
        return dataLight;
    }

    @Nullable
    public final DataLight getLightAtPositionForAlarmEdit(int i10) {
        synchronized (MasterStore.class) {
            if (i10 < this.lightsAndGroupsForAlarm.size()) {
                return this.lightsAndGroupsForAlarm.get(i10);
            }
            Unit unit = Unit.INSTANCE;
            return null;
        }
    }

    @Nullable
    public final DataLight getLightAtPositionForDimOffsetSetup(int i10) {
        synchronized (MasterStore.class) {
            if (i10 < this.lightsDimmableInDM.size()) {
                return this.lightsDimmableInDM.get(i10);
            }
            Unit unit = Unit.INSTANCE;
            return null;
        }
    }

    @Nullable
    public final DataLight getLightAtPositionForSceneEdit(int i10) {
        synchronized (MasterStore.class) {
            if (i10 < this.lightsAndGroupsForScene.size()) {
                return this.lightsAndGroupsForScene.get(i10);
            }
            Unit unit = Unit.INSTANCE;
            return null;
        }
    }

    @Nullable
    public final DataLight getLightForAlarmEdit(@NotNull String lightId) {
        Intrinsics.checkNotNullParameter(lightId, "lightId");
        synchronized (MasterStore.class) {
            Iterator<DataLight> it = this.lightsAndGroupsForAlarm.iterator();
            while (it.hasNext()) {
                DataLight next = it.next();
                if (Intrinsics.areEqual(next.id, lightId)) {
                    return next;
                }
            }
            return null;
        }
    }

    @Nullable
    public final DataLight getLightForSceneEdit(@NotNull String lightId) {
        Intrinsics.checkNotNullParameter(lightId, "lightId");
        synchronized (MasterStore.class) {
            Iterator<DataLight> it = this.lightsAndGroupsForScene.iterator();
            while (it.hasNext()) {
                DataLight next = it.next();
                if (Intrinsics.areEqual(next.id, lightId)) {
                    return next;
                }
            }
            return null;
        }
    }

    @NotNull
    public final List<String> getLightList() {
        return new ArrayList(this.lightList);
    }

    @NotNull
    public final ArrayList<DataLight> getLightsAndGroupsForScene() {
        return this.lightsAndGroupsForScene;
    }

    @NotNull
    public final ArrayList<DataLight> getLightsDimmableInDM() {
        return this.lightsDimmableInDM;
    }

    @NotNull
    public final ArrayList<DataLight> getLightsInGroupFromEditSceneCollection(@NotNull String groupId) {
        ArrayList<DataLight> arrayList;
        Intrinsics.checkNotNullParameter(groupId, "groupId");
        synchronized (MasterStore.class) {
            arrayList = new ArrayList<>();
            Iterator<DataLight> it = this.lightsAndGroupsForScene.iterator();
            boolean z7 = false;
            while (it.hasNext()) {
                DataLight next = it.next();
                String str = next.id;
                if (str != null) {
                    Intrinsics.checkNotNull(str);
                    if (StringsKt__StringsJVMKt.startsWith(str, "g", false, 2, null)) {
                        z7 = Intrinsics.areEqual(next.id, groupId);
                    } else if (z7) {
                        arrayList.add(next);
                    }
                }
            }
        }
        return arrayList;
    }

    public final boolean getNewScene() {
        return this.newScene;
    }

    public final void initDMLightsForDimOffsetSetup() {
        synchronized (MasterStore.class) {
            this.lightsDimmableInDM.clear();
            Iterator<String> it = this.lightList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                DataLight dataLight = new DataLight();
                DataLight dataLight2 = this.lightHashMap.get(next);
                if (dataLight2 != null && dataLight2.moduleType != null) {
                    Boolean bool = dataLight2.relay;
                    if (bool != null) {
                        Intrinsics.checkNotNull(bool);
                        if (!bool.booleanValue()) {
                        }
                    }
                    if (Intrinsics.areEqual(dataLight2.moduleType, DataLight.MODULE_TYPE_STRING_DM)) {
                        DataLight.update$default(dataLight, null, dataLight2, null, false, 8, null);
                        this.lightsDimmableInDM.add(dataLight);
                        OnLightChangeListener onLightChangeListener = this.onLightChangeListener;
                        if (onLightChangeListener != null) {
                            Intrinsics.checkNotNull(onLightChangeListener);
                            onLightChangeListener.onLightsAdded(next, this.lightsDimmableInDM.size() - 1, 1);
                            OnLightChangeListener onLightChangeListener2 = this.onLightChangeListener;
                            Intrinsics.checkNotNull(onLightChangeListener2);
                            onLightChangeListener2.onLightUpdated(this.lightsDimmableInDM.size() - 1);
                        }
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
        Timber.forest.d("total lights and groups for alarm edit:" + this.lightsAndGroupsForAlarm.size(), new Object[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x007a A[Catch: all -> 0x00bf, TryCatch #0 {, blocks: (B:4:0x0008, B:6:0x0018, B:8:0x0025, B:10:0x002b, B:11:0x0033, B:13:0x0039, B:19:0x004a, B:21:0x005c, B:23:0x0068, B:24:0x0073, B:26:0x007a, B:27:0x007e, B:29:0x0084, B:34:0x0098, B:41:0x009f, B:47:0x00a9, B:44:0x00af, B:37:0x00b5, B:52:0x00bb, B:61:0x0056), top: B:3:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initFavouritesScenes(@NotNull Context context) {
        List<String> list;
        List<String> split;
        List emptyList;
        String[] strArr;
        Intrinsics.checkNotNullParameter(context, "context");
        synchronized (MasterStore.class) {
            MasterStore helper = MasterStore.helper.getInstance();
            String scenesFav = SharedPreferencesStore.Companion.getScenesFav(context);
            if (scenesFav == null || (split = new Regex("\\s*,\\s*").split(scenesFav, 0)) == null) {
                list = null;
                this.favScenes.clear();
                if (list != null) {
                    for (String str : list) {
                        Intrinsics.checkNotNull(str);
                        if (str.length() > 0) {
                            if (str.length() <= 2) {
                                this.favScenes.add(str);
                            } else if (helper.dataFavourites.lightScenes.getScene(str) != null) {
                                this.favScenes.add(str);
                            } else {
                                SharedPreferencesStore.Companion.R0(context, str);
                            }
                        }
                    }
                }
                Unit unit = Unit.INSTANCE;
            } else if (split.isEmpty()) {
                emptyList = CollectionsKt.emptyList();
                if (emptyList == null) {
                    list = null;
                    this.favScenes.clear();
                    if (list != null) {
                    }
                    Unit unit2 = Unit.INSTANCE;
                }
            } else {
                ListIterator<String> listIterator = split.listIterator(split.size());
                while (listIterator.hasPrevious()) {
                    if (!(listIterator.previous().length() == 0)) {
                        emptyList = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                        break;
                    }
                }
                emptyList = CollectionsKt.emptyList();
                if (emptyList == null && (strArr = (String[]) emptyList.toArray(new String[0])) != null) {
                    list = Arrays.asList(Arrays.copyOf(strArr, strArr.length));
                }
                this.favScenes.clear();
                if (list != null) {
                }
                Unit unit22 = Unit.INSTANCE;
            }
        }
    }

    public final void initLightsAndGroupsForAlarm() {
        synchronized (MasterStore.class) {
            this.lightsAndGroupsForAlarm.clear();
            this.lightIdListForAlarmEdit.clear();
            Iterator<String> it = this.lightList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                DataLight dataLight = new DataLight();
                DataLight dataLight2 = this.lightHashMap.get(next);
                if (dataLight2 != null) {
                    DataLight.update$default(dataLight, null, dataLight2, null, false, 8, null);
                    Boolean bool = Boolean.FALSE;
                    dataLight.enableInScene = bool;
                    if (!this.newAlarm) {
                        dataLight.enableInScene = bool;
                        HashMap<String, DataLight> hashMap = this.editAlarmData.lights;
                        if (hashMap != null) {
                            Intrinsics.checkNotNull(hashMap);
                            Collection<DataLight> values = hashMap.values();
                            Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                            ArrayList arrayList = new ArrayList();
                            for (DataLight dataLight3 : values) {
                                if (dataLight3 != null) {
                                    arrayList.add(dataLight3);
                                }
                            }
                            Iterator it2 = arrayList.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    break;
                                }
                                DataLight dataLight4 = (DataLight) it2.next();
                                if (Intrinsics.areEqual(dataLight4.id, dataLight.id)) {
                                    DataLight.update$default(dataLight, null, dataLight4, null, false, 8, null);
                                    dataLight.enableInScene = Boolean.TRUE;
                                    break;
                                }
                            }
                        }
                    }
                    this.lightsAndGroupsForAlarm.add(dataLight);
                    this.lightIdListForAlarmEdit.add(next);
                    OnLightChangeListener onLightChangeListener = this.onLightChangeListener;
                    if (onLightChangeListener != null) {
                        Intrinsics.checkNotNull(onLightChangeListener);
                        onLightChangeListener.onLightsAdded(next, this.lightsAndGroupsForAlarm.size() - 1, 1);
                        OnLightChangeListener onLightChangeListener2 = this.onLightChangeListener;
                        Intrinsics.checkNotNull(onLightChangeListener2);
                        onLightChangeListener2.onLightUpdated(this.lightsAndGroupsForAlarm.size() - 1);
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
        Timber.forest.d("total lights and groups for alarm edit:" + this.lightsAndGroupsForAlarm.size(), new Object[0]);
    }

    public final void initLightsAndGroupsForScene() {
        synchronized (MasterStore.class) {
            this.lightsAndGroupsForScene.clear();
            this.lightIdListForSceneEdit.clear();
            Iterator<String> it = this.lightList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                DataLight dataLight = new DataLight();
                DataLight dataLight2 = this.lightHashMap.get(next);
                if (dataLight2 != null) {
                    DataLight.update$default(dataLight, null, dataLight2, null, false, 8, null);
                    dataLight.enableInScene = Boolean.TRUE;
                    if (!this.newScene) {
                        dataLight.enableInScene = Boolean.FALSE;
                        HashMap<String, DataLight> hashMap = this.editSceneData.lights;
                        if (hashMap != null) {
                            Intrinsics.checkNotNull(hashMap);
                            for (DataLight dataLight3 : hashMap.values()) {
                                Intrinsics.checkNotNull(dataLight3);
                                if (Intrinsics.areEqual(dataLight3.id, dataLight.id)) {
                                    DataLight.update$default(dataLight, null, dataLight3, null, false, 8, null);
                                    dataLight.enableInScene = Boolean.TRUE;
                                }
                            }
                        }
                    }
                    this.lightsAndGroupsForScene.add(dataLight);
                    this.lightIdListForSceneEdit.add(next);
                    OnLightChangeListener onLightChangeListener = this.onLightChangeListener;
                    if (onLightChangeListener != null) {
                        Intrinsics.checkNotNull(onLightChangeListener);
                        onLightChangeListener.onLightsAdded(next, this.lightsAndGroupsForScene.size() - 1, 1);
                        OnLightChangeListener onLightChangeListener2 = this.onLightChangeListener;
                        Intrinsics.checkNotNull(onLightChangeListener2);
                        onLightChangeListener2.onLightUpdated(this.lightsAndGroupsForScene.size() - 1);
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
        Timber.forest.d("total lights and groups for scene edit:" + this.lightsAndGroupsForScene.size(), new Object[0]);
    }

    public final boolean isGroupExpanded(@Nullable String str) {
        boolean contains;
        synchronized (MasterStore.class) {
            contains = this.expandedGroups.contains(str);
        }
        return contains;
    }

    public final void lightBeingMoved(@NotNull Context context, boolean z7, @Nullable String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        synchronized (MasterStore.class) {
            if (z7) {
                Iterator<String> it = this.groupList.iterator();
                boolean z10 = false;
                while (it.hasNext()) {
                    String next = it.next();
                    if (!isGroupExpanded(next)) {
                        toggleExpandedGroup(next);
                        z10 = true;
                    }
                }
                if (z10 && this.onLightChangeListener != null) {
                    int lightPosition = getLightPosition(str);
                    OnLightChangeListener onLightChangeListener = this.onLightChangeListener;
                    Intrinsics.checkNotNull(onLightChangeListener);
                    onLightChangeListener.requestScrollToPosition(lightPosition);
                }
            } else {
                this.blockedLights = null;
            }
            setBlockLightUpdates(context, z7);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void notifyAlarmNameChangeListener(@Nullable String str) {
        synchronized (MasterStore.class) {
            b bVar = this.onAlarmNameChangeListener;
            if (bVar != null) {
                Intrinsics.checkNotNull(bVar);
                bVar.onAlarmNameUpdated(str);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void notifyLightInEditAlarmUpdateListener(@NotNull String lightId) {
        Intrinsics.checkNotNullParameter(lightId, "lightId");
        synchronized (MasterStore.class) {
            OnLightChangeListener onLightChangeListener = this.onLightChangeListener;
            if (onLightChangeListener != null) {
                Intrinsics.checkNotNull(onLightChangeListener);
                onLightChangeListener.onLightUpdated(getLightPositionForAlarmEdit(lightId));
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void notifyLightInEditSceneUpdateListener(@NotNull String lightId) {
        Intrinsics.checkNotNullParameter(lightId, "lightId");
        synchronized (MasterStore.class) {
            OnLightChangeListener onLightChangeListener = this.onLightChangeListener;
            if (onLightChangeListener != null) {
                Intrinsics.checkNotNull(onLightChangeListener);
                onLightChangeListener.onLightUpdated(getLightPositionForSceneEdit(lightId));
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final int numberOfExpandedLightsToShow() {
        int size;
        if (this.lightsPaused) {
            return 0;
        }
        synchronized (MasterStore.class) {
            size = this.expandedList.size();
        }
        return size;
    }

    public final int numberOfGroups() {
        int size;
        synchronized (MasterStore.class) {
            size = this.groupList.size();
        }
        return size;
    }

    public final void setBlockLightUpdates(@NotNull Context context, boolean z7) {
        ArrayList<DataLight> arrayList;
        Intrinsics.checkNotNullParameter(context, "context");
        synchronized (MasterStore.class) {
            this.blockLightUpdates = z7;
            if (!z7 && (arrayList = this.blockedLights) != null) {
                Intrinsics.checkNotNull(arrayList);
                updateLight(context, arrayList);
                this.blockedLights = null;
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setGroupState(@NotNull Context context, @Nullable String str, int i10) {
        LightState lightState;
        String str2;
        int i11;
        Intrinsics.checkNotNullParameter(context, "context");
        synchronized (MasterStore.class) {
            MasterStore helper = MasterStore.helper.getInstance();
            if (i10 == 3) {
                lightState = LightState.on;
                str2 = DebugKt.DEBUG_PROPERTY_VALUE_ON;
            } else {
                if (i10 != 4) {
                    return;
                }
                lightState = LightState.off;
                str2 = DebugKt.DEBUG_PROPERTY_VALUE_OFF;
            }
            LightState lightState2 = lightState;
            String str3 = str2;
            int lightPosition = getLightPosition(str);
            int indexOf = this.groupList.indexOf(str);
            int size = indexOf == this.groupList.size() - 1 ? this.lightList.size() : getLightPosition(this.groupList.get(indexOf + 1));
            boolean z7 = false;
            if (lightPosition < size) {
                int i12 = lightPosition;
                boolean z10 = false;
                while (i12 < size) {
                    DataLight lightAtPosition = getLightAtPosition(i12);
                    if (lightAtPosition != null) {
                        i11 = i12;
                        this.lightFunctions.s(context, helper, lightAtPosition, lightState2, false, false);
                        Boolean bool = lightAtPosition.reachable;
                        if (bool != null) {
                            Intrinsics.checkNotNull(bool);
                            if (!bool.booleanValue()) {
                                z10 = true;
                            }
                        }
                    } else {
                        i11 = i12;
                    }
                    i12 = i11 + 1;
                }
                z7 = z10;
            }
            if (z7) {
                helper.dataFavourites.lightStore.checkGroupState(context, helper, str);
            }
            DataGroup dataGroup = new DataGroup(str);
            if (Intrinsics.areEqual(str3, DebugKt.DEBUG_PROPERTY_VALUE_OFF)) {
                dataGroup.state = LightState.off;
            } else {
                dataGroup.state = LightState.on;
            }
            dataGroup.name = null;
            this.lightFunctions.h(context, dataGroup);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setLightsAndGroupsForScene(@NotNull ArrayList<DataLight> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.lightsAndGroupsForScene = arrayList;
    }

    public final void setLightsDimmableInDM(@NotNull ArrayList<DataLight> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.lightsDimmableInDM = arrayList;
    }

    public final void setLightsPaused(boolean z7) {
        synchronized (MasterStore.class) {
            if (z7) {
                int numberOfExpandedLightsToShow = numberOfExpandedLightsToShow();
                this.lightsPaused = true;
                OnLightChangeListener onLightChangeListener = this.onLightChangeListener;
                if (onLightChangeListener != null) {
                    Intrinsics.checkNotNull(onLightChangeListener);
                    onLightChangeListener.onLightsRemoved("Paused", 0, numberOfExpandedLightsToShow);
                }
            } else {
                this.lightsPaused = false;
                if (this.onLightChangeListener != null) {
                    int numberOfExpandedLightsToShow2 = numberOfExpandedLightsToShow();
                    OnLightChangeListener onLightChangeListener2 = this.onLightChangeListener;
                    Intrinsics.checkNotNull(onLightChangeListener2);
                    onLightChangeListener2.onLightsAdded("Paused", 0, numberOfExpandedLightsToShow2);
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setNewScene(boolean z7) {
        this.newScene = z7;
    }

    public final void setOnAlarmNameChangeListener(@Nullable b bVar) {
        this.onAlarmNameChangeListener = bVar;
    }

    public final void setOnLightChangeListener(@Nullable OnLightChangeListener onLightChangeListener) {
        this.onLightChangeListener = onLightChangeListener;
    }

    public final int toggleExpandedGroup(@Nullable String str) {
        synchronized (MasterStore.class) {
            int expandedLightPositionFromId = getExpandedLightPositionFromId(str);
            if (!this.expandedGroups.contains(str)) {
                int numberOfExpandedLightsToShow = numberOfExpandedLightsToShow();
                this.expandedGroups.add(str);
                this.expandedList = workOutExpandedList();
                int numberOfExpandedLightsToShow2 = numberOfExpandedLightsToShow();
                OnLightChangeListener onLightChangeListener = this.onLightChangeListener;
                if (onLightChangeListener != null) {
                    Intrinsics.checkNotNull(onLightChangeListener);
                    onLightChangeListener.onLightsAdded(str, expandedLightPositionFromId + 1, numberOfExpandedLightsToShow2 - numberOfExpandedLightsToShow);
                    OnLightChangeListener onLightChangeListener2 = this.onLightChangeListener;
                    Intrinsics.checkNotNull(onLightChangeListener2);
                    onLightChangeListener2.onLightUpdated(expandedLightPositionFromId);
                }
                Unit unit = Unit.INSTANCE;
                return 1;
            }
            if (numberOfGroups() == 1) {
                return 1;
            }
            int numberOfExpandedLightsToShow3 = numberOfExpandedLightsToShow();
            this.expandedGroups.remove(str);
            this.expandedList = workOutExpandedList();
            int numberOfExpandedLightsToShow4 = numberOfExpandedLightsToShow();
            OnLightChangeListener onLightChangeListener3 = this.onLightChangeListener;
            if (onLightChangeListener3 != null) {
                Intrinsics.checkNotNull(onLightChangeListener3);
                onLightChangeListener3.onLightsRemoved(str, expandedLightPositionFromId + 1, numberOfExpandedLightsToShow3 - numberOfExpandedLightsToShow4);
                OnLightChangeListener onLightChangeListener4 = this.onLightChangeListener;
                Intrinsics.checkNotNull(onLightChangeListener4);
                onLightChangeListener4.onLightUpdated(expandedLightPositionFromId);
            }
            return 2;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void updateLight(@NotNull Context context, @NotNull DataMaster incomingMasterData) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(incomingMasterData, "incomingMasterData");
        synchronized (MasterStore.class) {
            ArrayList<DataLight> arrayList = new ArrayList<>();
            ArrayList<String> arrayList2 = incomingMasterData.myLights.groupsOrder;
            Intrinsics.checkNotNull(arrayList2);
            Iterator<String> it = arrayList2.iterator();
            while (it.hasNext()) {
                DataGroup dataGroup = incomingMasterData.myLights.getDataGroup(it.next());
                if (dataGroup != null) {
                    DataLight dataLight = new DataLight(dataGroup.id, dataGroup.name, dataGroup.state);
                    dataLight.type = 1;
                    arrayList.add(dataLight);
                    Iterator<String> it2 = dataGroup.lightsOrder.iterator();
                    while (it2.hasNext()) {
                        DataLight lightData = incomingMasterData.myLights.getLightData(it2.next());
                        if (lightData != null) {
                            arrayList.add(lightData);
                        }
                    }
                }
            }
            Timber.forest.d("Updating " + arrayList.size() + " of lights", new Object[0]);
            updateLight(context, arrayList);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void updatePosition(@NotNull Context context, @NotNull String lightId, int i10) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(lightId, "lightId");
        synchronized (MasterStore.class) {
            MasterStore helper = MasterStore.helper.getInstance();
            int indexOf = this.lightList.indexOf(lightId);
            String groupIdOfLight = getGroupIdOfLight(lightId);
            DataLight expandedLightByPosition = getExpandedLightByPosition(i10);
            if (expandedLightByPosition != null) {
                new CommonFuncs().moveItem(this.lightList, indexOf, this.lightList.indexOf(expandedLightByPosition.id));
                this.expandedList = workOutExpandedList();
                String groupIdOfLight2 = getGroupIdOfLight(lightId);
                if (Intrinsics.areEqual(groupIdOfLight, groupIdOfLight2)) {
                    Timber.forest.d("Light " + lightId + " has moved within group " + groupIdOfLight2, new Object[0]);
                } else {
                    Timber.forest.d("Light " + lightId + " has moved from group " + groupIdOfLight + " to " + groupIdOfLight2, new Object[0]);
                    helper.dataFavourites.lightStore.checkGroupState(context, helper, groupIdOfLight);
                    helper.dataFavourites.lightStore.checkGroupState(context, helper, groupIdOfLight2);
                }
                int indexOf2 = this.lightList.indexOf(groupIdOfLight2);
                int indexOf3 = this.lightList.indexOf(lightId);
                DataLight dataLight = this.lightHashMap.get(this.lightList.get(indexOf3));
                Intrinsics.checkNotNull(dataLight);
                String str = "id=" + dataLight.id + "&groupId=" + groupIdOfLight2 + "&position=" + (indexOf3 - indexOf2);
                Timber.forest.d("setLightToGroup " + str, new Object[0]);
                AppFeatures.sendOptionsRequest(context, "setLightToGroup", str, false, 8, null);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void updateLight(Context context, ArrayList<DataLight> arrayList) {
        int i10;
        if (this.blockLightUpdates) {
            Timber.forest.d("Light update blocked", new Object[0]);
            this.blockedLights = new ArrayList<>(arrayList);
            return;
        }
        ArrayList arrayList2 = new ArrayList(this.lightList);
        Iterator<DataLight> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.remove(it.next().id);
        }
        Iterator it2 = arrayList2.iterator();
        boolean z7 = false;
        while (true) {
            i10 = 1;
            if (!it2.hasNext()) {
                break;
            }
            String str = (String) it2.next();
            int indexOf = this.expandedList.indexOf(str);
            String groupIdOfLight = getGroupIdOfLight(str);
            Timber.Forest forest = Timber.forest;
            forest.d("Existing light has been removed " + str + " " + groupIdOfLight + " " + indexOf, new Object[0]);
            if (groupIdOfLight == null) {
                forest.d("Null groupId from light ID (to be deleted): " + str + " " + indexOf, new Object[0]);
            } else if (Intrinsics.areEqual(groupIdOfLight, str)) {
                if (this.expandedList.contains(groupIdOfLight)) {
                    this.expandedGroups.remove(groupIdOfLight);
                }
                if (!z7) {
                    z7 = true;
                }
            }
            this.lightHashMap.remove(str);
            this.lightList.remove(str);
            int size = this.expandedList.size();
            ArrayList<String> workOutExpandedList = workOutExpandedList();
            this.expandedList = workOutExpandedList;
            int size2 = size - workOutExpandedList.size();
            if (indexOf >= 0) {
                if (size2 > 0) {
                    OnLightChangeListener onLightChangeListener = this.onLightChangeListener;
                    if (onLightChangeListener != null) {
                        Intrinsics.checkNotNull(onLightChangeListener);
                        onLightChangeListener.onLightsRemoved(str, indexOf, size2);
                    }
                } else if (z7) {
                    forest.d("Collapsed group with lights deleted", new Object[0]);
                    OnLightChangeListener onLightChangeListener2 = this.onLightChangeListener;
                    if (onLightChangeListener2 != null) {
                        Intrinsics.checkNotNull(onLightChangeListener2);
                        onLightChangeListener2.onLightUpdated(indexOf);
                    }
                }
            }
        }
        Iterator<DataLight> it3 = arrayList.iterator();
        int i11 = 0;
        while (it3.hasNext()) {
            DataLight next = it3.next();
            if (this.lightList.indexOf(next.id) == -1) {
                this.lightList.add(i11, next.id);
                DataLight dataLight = new DataLight();
                DataLight.update$default(dataLight, null, next, null, false, 8, null);
                this.lightHashMap.put(next.id, dataLight);
                int size3 = this.expandedList.size();
                ArrayList<String> workOutExpandedList2 = workOutExpandedList();
                this.expandedList = workOutExpandedList2;
                int indexOf2 = workOutExpandedList2.indexOf(next.id);
                if (size3 == this.expandedList.size()) {
                    OnLightChangeListener onLightChangeListener3 = this.onLightChangeListener;
                    if (onLightChangeListener3 != null && indexOf2 > 0) {
                        Intrinsics.checkNotNull(onLightChangeListener3);
                        onLightChangeListener3.onLightUpdated(indexOf2);
                    }
                } else {
                    sendLightAddedMessage(next.id, getGroupIdOfLight(next.id), indexOf2);
                }
                Integer num = next.type;
                if (num != null && num.intValue() == 1 && !z7) {
                    z7 = true;
                }
            }
            i11++;
        }
        HashMap hashMap = new HashMap();
        for (String str2 : this.numberInGroup.keySet()) {
            hashMap.put(str2, this.numberInGroup.get(str2));
        }
        ArrayList arrayList3 = new ArrayList(this.expandedList);
        CommonFuncs commonFuncs = new CommonFuncs();
        Iterator<DataLight> it4 = arrayList.iterator();
        int i12 = 0;
        while (it4.hasNext()) {
            DataLight next2 = it4.next();
            int indexOf3 = this.lightList.indexOf(next2.id);
            boolean contains = arrayList3.contains(next2.id);
            int indexOf4 = arrayList3.indexOf(next2.id);
            commonFuncs.moveItem(this.lightList, indexOf3, i12);
            ArrayList<String> workOutExpandedList3 = workOutExpandedList();
            this.expandedList = workOutExpandedList3;
            boolean contains2 = workOutExpandedList3.contains(next2.id);
            int indexOf5 = this.expandedList.indexOf(next2.id);
            if (indexOf5 >= arrayList3.size()) {
                indexOf5 = arrayList3.size();
            }
            if (!contains && contains2) {
                Timber.forest.d("Existing light " + next2.id + " is now visible at " + indexOf5, new Object[0]);
                OnLightChangeListener onLightChangeListener4 = this.onLightChangeListener;
                if (onLightChangeListener4 != null) {
                    Intrinsics.checkNotNull(onLightChangeListener4);
                    onLightChangeListener4.onLightsAdded(next2.id, indexOf5, i10);
                }
                arrayList3.add(indexOf5, next2.id);
            } else if (contains && !contains2) {
                Timber.forest.d("Existing light " + next2.id + " is now no longer visible at " + indexOf4, new Object[0]);
                OnLightChangeListener onLightChangeListener5 = this.onLightChangeListener;
                if (onLightChangeListener5 != null) {
                    Intrinsics.checkNotNull(onLightChangeListener5);
                    onLightChangeListener5.onLightsRemoved(next2.id, indexOf4, i10);
                }
                arrayList3.remove(indexOf4);
            } else if (contains && indexOf4 != indexOf5) {
                Timber.forest.d("Existing light " + next2.id + " has moved from " + indexOf4 + " to " + indexOf5, new Object[0]);
                OnLightChangeListener onLightChangeListener6 = this.onLightChangeListener;
                if (onLightChangeListener6 != null) {
                    Intrinsics.checkNotNull(onLightChangeListener6);
                    onLightChangeListener6.onLightMoved(next2.id, indexOf4, indexOf5);
                }
                commonFuncs.moveItem(arrayList3, indexOf4, indexOf5);
            }
            i12++;
            i10 = 1;
        }
        for (String str3 : this.numberInGroup.keySet()) {
            Integer num2 = (Integer) hashMap.get(str3);
            Integer num3 = this.numberInGroup.get(str3);
            if (num2 != null && num3 != null && ((num2.intValue() == 1 && num3.intValue() == 0) || (num2.intValue() == 0 && num3.intValue() == 1))) {
                synchronized (MasterStore.class) {
                    checkGroupState(context, MasterStore.helper.getInstance(), str3);
                    Unit unit = Unit.INSTANCE;
                }
            }
        }
        Iterator<DataLight> it5 = arrayList.iterator();
        while (it5.hasNext()) {
            DataLight next3 = it5.next();
            DataLight light = getLight(next3.id);
            if (light != null) {
                DataLight.update$default(light, context, next3, null, false, 8, null);
            }
        }
        if (z7) {
            sendGroupChangeBroadcast("Change");
        }
        if (numberOfGroups() != 1 || this.expandedGroups.contains(DataMyLights.DEFAULT_GROUP)) {
            return;
        }
        toggleExpandedGroup(DataMyLights.DEFAULT_GROUP);
    }
}