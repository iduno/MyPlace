package com.air.advantage.data;

import com.air.advantage.jsondata.MasterStore;
import com.air.advantage.libraryairconlightjson.AirconFunctionsConstants;
import com.air.advantage.sonos.Sonos;
import com.air.advantage.sonos.SonosRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import kotlin.Unit;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

@PurelyImplements({"SMAP\nSceneStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SceneStore.kt\ncom/air/advantage/data/SceneStore\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,596:1\n1#2:597\n*E\n"})
/* renamed from: com.air.advantage.data.n1 */
/* loaded from: classes.dex */
public final class SceneStore {

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = SceneStore.class.getSimpleName();

    @NotNull
    private static final String MONITOR_GROUP_ID = "n0";

    @NotNull
    private static final String MONITOR_GROUP_NAME = "Events";

    @NotNull
    private static final String SONOS_GROUP_ID = "o0";

    @NotNull
    private static final String SONOS_GROUP_NAME = "Sonos";

    @JvmField
    public boolean disableAllMonitorsItemsOnRepopulate;

    @JvmField
    public boolean disableAllMyPlaceItemsOnRepopulate;

    @JvmField
    public boolean disableAllSonosItemsOnRepopulate;

    @JvmField
    public boolean enableAllMyAirItemsOnRepopulate;

    @JvmField
    public boolean enableAllMyLightsItemsOnRepopulate;

    @JvmField
    public boolean monitorsSelected;

    @JvmField
    public boolean myAirSelected;

    @JvmField
    public boolean myLightsSelected;

    @JvmField
    public boolean myPlaceSelected;

    @JvmField
    public boolean newScene;

    @Nullable
    private SceneStoreChangeListener onStoreItemChangeListener;

    @JvmField
    public boolean oneTabSystemOnly;

    @JvmField
    public boolean sonosSelected;

    @NotNull
    @JvmField
    public ArrayList<Item> itemsAndGroupsForScene = new ArrayList<>();

    @NotNull
    private final ArrayList<String> itemIdListForSceneEdit = new ArrayList<>();

    @NotNull
    @JvmField
    public final DataScene editSceneData = new DataScene();

    @NotNull
    private final ArrayList<Item> myAirItems = new ArrayList<>();

    @NotNull
    private final ArrayList<String> myAirItemsIdList = new ArrayList<>();

    @NotNull
    private final ArrayList<Item> myLightsItems = new ArrayList<>();

    @NotNull
    private final ArrayList<String> myLightsItemsIdList = new ArrayList<>();

    @NotNull
    private final ArrayList<Item> myPlaceItems = new ArrayList<>();

    @NotNull
    private final ArrayList<String> myPlaceItemsIdList = new ArrayList<>();

    @NotNull
    private final ArrayList<Item> monitorsItems = new ArrayList<>();

    @NotNull
    private final ArrayList<String> monitorsItemsIdList = new ArrayList<>();

    @NotNull
    private final ArrayList<Item> sonosItems = new ArrayList<>();

    @NotNull
    private final ArrayList<String> sonosItemsIdList = new ArrayList<>();

    @NotNull
    private final SonosRepository sonosRepository = (SonosRepository) KoinJavaComponent.get$default(SonosRepository.class, null, null, 6, null);

    /* renamed from: com.air.advantage.data.n1$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.n1.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* renamed from: com.air.advantage.data.n1$b */
    public interface SceneStoreChangeListener {
        void onStoreItemMoved(@Nullable String str, int i10, int i11);

        void onStoreItemUpdated(int i10);

        void onStoreItemsAdded(@Nullable String str, int i10, int i11);

        void onStoreItemsRemoved(@Nullable String str, int i10, int i11);

        void requestScrollToPosition(int i10);
    }

    private final String getGroupIdOfItemInEditScene(String str) {
        Iterator<String> it = this.itemIdListForSceneEdit.iterator();
        String str2 = null;
        while (it.hasNext()) {
            String next = it.next();
            Intrinsics.checkNotNull(next);
            if (StringsKt__StringsJVMKt.startsWith(next, "g", false, 2, null) || StringsKt__StringsJVMKt.startsWith(next, "m", false, 2, null) || Intrinsics.areEqual(next, MONITOR_GROUP_ID) || Intrinsics.areEqual(next, SONOS_GROUP_ID)) {
                str2 = next;
            }
            if (Intrinsics.areEqual(next, str)) {
                break;
            }
        }
        return str2;
    }

    private final int getItemPositionForSceneEdit(String str) {
        return this.itemIdListForSceneEdit.indexOf(str);
    }

    public final void checkAndUpdateGroupIncludedStateInEditSceneForAnItem(@NotNull Item dataStoreItem) {
        Item itemForSceneEdit;
        boolean z7;
        Intrinsics.checkNotNullParameter(dataStoreItem, "dataStoreItem");
        synchronized (MasterStore.class) {
            MasterStore helper = MasterStore.helper.getInstance();
            String str = dataStoreItem.id;
            String groupIdOfItemInEditScene = str != null ? helper.dataFavourites.sceneStore.getGroupIdOfItemInEditScene(str) : null;
            if (groupIdOfItemInEditScene != null && (itemForSceneEdit = helper.dataFavourites.sceneStore.getItemForSceneEdit(groupIdOfItemInEditScene)) != null) {
                Boolean bool = dataStoreItem.enableInScene;
                Intrinsics.checkNotNull(bool);
                if (bool.booleanValue()) {
                    itemForSceneEdit.enableInScene = Boolean.TRUE;
                    helper.dataFavourites.sceneStore.notifyItemInEditSceneUpdateListener(groupIdOfItemInEditScene);
                } else {
                    Iterator<Item> it = helper.dataFavourites.sceneStore.getitemsInGroupFromEditSceneCollection(groupIdOfItemInEditScene).iterator();
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
                        itemForSceneEdit.enableInScene = Boolean.FALSE;
                        helper.dataFavourites.sceneStore.notifyItemInEditSceneUpdateListener(groupIdOfItemInEditScene);
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final int getAllStoreItemsCountForSceneEdit() {
        return this.itemsAndGroupsForScene.size();
    }

    @Nullable
    public final Item getItemAtPositionForSceneEdit(int i10) {
        synchronized (MasterStore.class) {
            if (i10 < this.itemsAndGroupsForScene.size()) {
                return this.itemsAndGroupsForScene.get(i10);
            }
            Unit unit = Unit.INSTANCE;
            return null;
        }
    }

    @Nullable
    public final Item getItemForSceneEdit(@NotNull String itemId) {
        Intrinsics.checkNotNullParameter(itemId, "itemId");
        synchronized (MasterStore.class) {
            Iterator<Item> it = this.itemsAndGroupsForScene.iterator();
            while (it.hasNext()) {
                Item next = it.next();
                if (Intrinsics.areEqual(next.id, itemId)) {
                    return next;
                }
            }
            return null;
        }
    }

    @NotNull
    public final ArrayList<Item> getitemsInGroupFromEditSceneCollection(@NotNull String groupId) {
        ArrayList<Item> arrayList;
        Intrinsics.checkNotNullParameter(groupId, "groupId");
        synchronized (MasterStore.class) {
            arrayList = new ArrayList<>();
            Iterator<Item> it = this.itemsAndGroupsForScene.iterator();
            boolean z7 = false;
            while (it.hasNext()) {
                Item next = it.next();
                String str = next.id;
                if (str != null) {
                    Intrinsics.checkNotNull(str);
                    if (!StringsKt__StringsJVMKt.startsWith(str, "g", false, 2, null)) {
                        String str2 = next.id;
                        Intrinsics.checkNotNull(str2);
                        if (!StringsKt__StringsJVMKt.startsWith(str2, "m", false, 2, null) && !Intrinsics.areEqual(next.id, MONITOR_GROUP_ID) && !Intrinsics.areEqual(next.id, SONOS_GROUP_ID)) {
                            if (z7) {
                                arrayList.add(next);
                            }
                        }
                    }
                    z7 = Intrinsics.areEqual(next.id, groupId);
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x028a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x029b A[Catch: all -> 0x054a, TryCatch #0 {, blocks: (B:4:0x0005, B:6:0x0063, B:7:0x006f, B:9:0x0075, B:11:0x0097, B:12:0x00a1, B:14:0x00a5, B:16:0x00ab, B:18:0x00b6, B:20:0x00dc, B:22:0x00f1, B:24:0x0105, B:27:0x010b, B:29:0x011a, B:31:0x0128, B:33:0x0144, B:39:0x0150, B:40:0x0154, B:42:0x015a, B:45:0x016b, B:47:0x0182, B:49:0x018c, B:50:0x0197, B:52:0x019d, B:55:0x01b0, B:60:0x01c6, B:62:0x01cf, B:64:0x01dd, B:65:0x01f9, B:70:0x0205, B:71:0x020f, B:73:0x0216, B:76:0x0222, B:80:0x0231, B:83:0x0248, B:87:0x0257, B:89:0x025b, B:92:0x026a, B:96:0x0278, B:99:0x027d, B:108:0x028e, B:110:0x029b, B:112:0x02a3, B:113:0x02ae, B:115:0x02b4, B:118:0x02c7, B:123:0x02d4, B:125:0x02d8, B:127:0x02e6, B:128:0x0302, B:134:0x0271, B:137:0x0263, B:139:0x024d, B:142:0x0236, B:144:0x023d, B:147:0x0227, B:153:0x0310, B:155:0x031a, B:157:0x032c, B:159:0x033c, B:160:0x035a, B:161:0x036a, B:163:0x0370, B:166:0x037c, B:168:0x0389, B:170:0x038f, B:171:0x039a, B:173:0x03a0, B:176:0x03b3, B:181:0x03cb, B:183:0x03cf, B:185:0x03dd, B:186:0x03f9, B:192:0x0407, B:194:0x0413, B:196:0x0428, B:198:0x043a, B:200:0x044a, B:201:0x0468, B:202:0x0487, B:204:0x048d, B:207:0x0495, B:209:0x04a6, B:211:0x04ac, B:212:0x04b7, B:214:0x04bd, B:217:0x04cf, B:222:0x04e1, B:224:0x04e5, B:226:0x04f7, B:227:0x0517, B:232:0x0527), top: B:3:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x02d8 A[Catch: all -> 0x054a, TryCatch #0 {, blocks: (B:4:0x0005, B:6:0x0063, B:7:0x006f, B:9:0x0075, B:11:0x0097, B:12:0x00a1, B:14:0x00a5, B:16:0x00ab, B:18:0x00b6, B:20:0x00dc, B:22:0x00f1, B:24:0x0105, B:27:0x010b, B:29:0x011a, B:31:0x0128, B:33:0x0144, B:39:0x0150, B:40:0x0154, B:42:0x015a, B:45:0x016b, B:47:0x0182, B:49:0x018c, B:50:0x0197, B:52:0x019d, B:55:0x01b0, B:60:0x01c6, B:62:0x01cf, B:64:0x01dd, B:65:0x01f9, B:70:0x0205, B:71:0x020f, B:73:0x0216, B:76:0x0222, B:80:0x0231, B:83:0x0248, B:87:0x0257, B:89:0x025b, B:92:0x026a, B:96:0x0278, B:99:0x027d, B:108:0x028e, B:110:0x029b, B:112:0x02a3, B:113:0x02ae, B:115:0x02b4, B:118:0x02c7, B:123:0x02d4, B:125:0x02d8, B:127:0x02e6, B:128:0x0302, B:134:0x0271, B:137:0x0263, B:139:0x024d, B:142:0x0236, B:144:0x023d, B:147:0x0227, B:153:0x0310, B:155:0x031a, B:157:0x032c, B:159:0x033c, B:160:0x035a, B:161:0x036a, B:163:0x0370, B:166:0x037c, B:168:0x0389, B:170:0x038f, B:171:0x039a, B:173:0x03a0, B:176:0x03b3, B:181:0x03cb, B:183:0x03cf, B:185:0x03dd, B:186:0x03f9, B:192:0x0407, B:194:0x0413, B:196:0x0428, B:198:0x043a, B:200:0x044a, B:201:0x0468, B:202:0x0487, B:204:0x048d, B:207:0x0495, B:209:0x04a6, B:211:0x04ac, B:212:0x04b7, B:214:0x04bd, B:217:0x04cf, B:222:0x04e1, B:224:0x04e5, B:226:0x04f7, B:227:0x0517, B:232:0x0527), top: B:3:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x020f A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0287  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initStoreItemsAndGroupsForScene(boolean z7, boolean z10) {
        HashMap<String, Sonos> hashMap;
        HashMap<String, DataMonitor> hashMap2;
        boolean z11;
        String str;
        boolean z12;
        synchronized (MasterStore.class) {
            MasterStore helper = MasterStore.helper.getInstance();
            DataFavourites dataFavourites = helper.dataFavourites;
            LightStore lightStore = dataFavourites.lightStore;
            MonitorStore monitorStore = dataFavourites.monitorStore;
            this.itemsAndGroupsForScene.clear();
            this.itemIdListForSceneEdit.clear();
            List<String> lightList = lightStore.getLightList();
            workoutTabItemsToShow();
            this.myAirItems.clear();
            this.myAirItemsIdList.clear();
            this.myLightsItems.clear();
            this.myLightsItemsIdList.clear();
            this.myPlaceItems.clear();
            this.myPlaceItemsIdList.clear();
            this.monitorsItems.clear();
            this.monitorsItemsIdList.clear();
            this.sonosItems.clear();
            this.sonosItemsIdList.clear();
            if (Intrinsics.areEqual(helper.dataMaster.system.hasAircons, Boolean.TRUE)) {
                for (String str2 : helper.dataMaster.aircons.keySet()) {
                    DataAirconSystem dataAirconSystem = helper.dataMaster.aircons.get(str2);
                    DataAirconSystem dataAirconSystem2 = new DataAirconSystem();
                    dataAirconSystem2.updateForSnapshot(dataAirconSystem);
                    if (helper.dataMaster.aircons.size() > 1) {
                        Intrinsics.checkNotNull(dataAirconSystem);
                        str = dataAirconSystem.info.name;
                    } else {
                        str = AirconFunctionsConstants.AIRCON;
                    }
                    if (this.newScene) {
                        z12 = true;
                    } else {
                        HashMap<String, DataAirconSystem> hashMap3 = this.editSceneData.aircons;
                        if (hashMap3 != null) {
                            Intrinsics.checkNotNull(hashMap3);
                            DataAirconSystem dataAirconSystem3 = hashMap3.get(str2);
                            if (dataAirconSystem3 != null) {
                                dataAirconSystem2.updateForSnapshot(dataAirconSystem3);
                                DataAirconInfo dataAirconInfo = dataAirconSystem2.info;
                                Intrinsics.checkNotNull(dataAirconSystem);
                                dataAirconInfo.uid = dataAirconSystem.info.uid;
                                dataAirconSystem2.info.name = str;
                                TreeMap<String, Zone> treeMap = dataAirconSystem.zones;
                                Intrinsics.checkNotNull(treeMap);
                                int size = treeMap.size();
                                TreeMap<String, Zone> treeMap2 = dataAirconSystem2.zones;
                                Intrinsics.checkNotNull(treeMap2);
                                if (size < treeMap2.size()) {
                                    TreeMap<String, Zone> treeMap3 = dataAirconSystem.zones;
                                    Intrinsics.checkNotNull(treeMap3);
                                    int size2 = treeMap3.size();
                                    TreeMap<String, Zone> treeMap4 = dataAirconSystem2.zones;
                                    Intrinsics.checkNotNull(treeMap4);
                                    int size3 = treeMap4.size();
                                    int i10 = size2 + 1;
                                    if (i10 <= size3) {
                                        while (true) {
                                            TreeMap<String, Zone> treeMap5 = dataAirconSystem2.zones;
                                            Intrinsics.checkNotNull(treeMap5);
                                            treeMap5.remove(Zone.Companion.getZoneKey(Integer.valueOf(i10)));
                                            if (i10 == size3) {
                                                break;
                                            } else {
                                                i10++;
                                            }
                                        }
                                    }
                                }
                                z12 = true;
                            }
                        }
                        z12 = false;
                    }
                    Item item = new Item(str2, dataAirconSystem2);
                    item.enableInScene = Boolean.valueOf(z12);
                    if (this.myAirSelected) {
                        this.itemsAndGroupsForScene.add(item);
                        this.itemIdListForSceneEdit.add(str2);
                        SceneStoreChangeListener sceneStoreChangeListener = this.onStoreItemChangeListener;
                        if (sceneStoreChangeListener != null) {
                            Intrinsics.checkNotNull(sceneStoreChangeListener);
                            sceneStoreChangeListener.onStoreItemsAdded(str2, this.itemsAndGroupsForScene.size() - 1, 1);
                            SceneStoreChangeListener sceneStoreChangeListener2 = this.onStoreItemChangeListener;
                            Intrinsics.checkNotNull(sceneStoreChangeListener2);
                            sceneStoreChangeListener2.onStoreItemUpdated(this.itemsAndGroupsForScene.size() - 1);
                        }
                    }
                    this.myAirItems.add(item);
                    this.myAirItemsIdList.add(str2);
                }
            }
            for (String str3 : lightList) {
                DataLight dataLight = new DataLight();
                DataLight light = lightStore.getLight(str3);
                if (light != null) {
                    DataLight.update$default(dataLight, null, light, null, false, 8, null);
                    dataLight.enableInScene = Boolean.TRUE;
                    if (!this.newScene) {
                        dataLight.enableInScene = Boolean.FALSE;
                        HashMap<String, DataLight> hashMap4 = this.editSceneData.lights;
                        if (hashMap4 != null) {
                            Intrinsics.checkNotNull(hashMap4);
                            for (DataLight dataLight2 : hashMap4.values()) {
                                Intrinsics.checkNotNull(dataLight2);
                                if (Intrinsics.areEqual(dataLight2.id, dataLight.id)) {
                                    DataLight.update$default(dataLight, null, dataLight2, null, false, 8, null);
                                    dataLight.enableInScene = Boolean.TRUE;
                                }
                            }
                        }
                    }
                    Item item2 = new Item(dataLight);
                    if (this.myLightsSelected) {
                        this.itemsAndGroupsForScene.add(item2);
                        this.itemIdListForSceneEdit.add(str3);
                        SceneStoreChangeListener sceneStoreChangeListener3 = this.onStoreItemChangeListener;
                        if (sceneStoreChangeListener3 != null) {
                            Intrinsics.checkNotNull(sceneStoreChangeListener3);
                            sceneStoreChangeListener3.onStoreItemsAdded(str3, this.itemsAndGroupsForScene.size() - 1, 1);
                            SceneStoreChangeListener sceneStoreChangeListener4 = this.onStoreItemChangeListener;
                            Intrinsics.checkNotNull(sceneStoreChangeListener4);
                            sceneStoreChangeListener4.onStoreItemUpdated(this.itemsAndGroupsForScene.size() - 1);
                        }
                    }
                    this.myLightsItems.add(item2);
                    this.myLightsItemsIdList.add(str3);
                }
            }
            ThingStore thingStore = dataFavourites.thingStore;
            for (String str4 : thingStore.getItemList()) {
                Item item3 = thingStore.getItem(str4);
                if (item3 != null) {
                    Integer num = item3.itemType;
                    boolean z13 = num != null && num.intValue() == 4;
                    Integer num2 = item3.type;
                    if (num2 != null && num2.intValue() == 7 && Intrinsics.areEqual(item3.id, ThingStore.MOTION_SENSOR_GROUP_ID)) {
                        z13 = true;
                    }
                    Integer num3 = item3.itemType;
                    boolean z14 = num3 != null && num3.intValue() == 6;
                    String str5 = item3.id;
                    if ((str5 != null && Intrinsics.areEqual(str5, ThingStore.LOCK_GROUP_ID)) || item3.getItemAsLock() != null) {
                        z14 = true;
                    }
                    Integer num4 = item3.channelDipState;
                    if (num4 == null) {
                        z11 = false;
                        if (z13 && !z14 && !z11) {
                            Item item4 = new Item(item3);
                            Boolean bool = Boolean.FALSE;
                            item4.enableInScene = bool;
                            if (!this.newScene) {
                                item4.enableInScene = bool;
                                HashMap<String, DataMyThing> hashMap5 = this.editSceneData.things;
                                if (hashMap5 != null) {
                                    Intrinsics.checkNotNull(hashMap5);
                                    for (DataMyThing dataMyThing : hashMap5.values()) {
                                        Intrinsics.checkNotNull(dataMyThing);
                                        if (Intrinsics.areEqual(dataMyThing.id, item4.id)) {
                                            item4.update(null, new Item(dataMyThing));
                                            item4.enableInScene = Boolean.TRUE;
                                        }
                                    }
                                }
                            }
                            if (this.myPlaceSelected) {
                                this.itemsAndGroupsForScene.add(item4);
                                this.itemIdListForSceneEdit.add(str4);
                                SceneStoreChangeListener sceneStoreChangeListener5 = this.onStoreItemChangeListener;
                                if (sceneStoreChangeListener5 != null) {
                                    Intrinsics.checkNotNull(sceneStoreChangeListener5);
                                    sceneStoreChangeListener5.onStoreItemsAdded(str4, this.itemsAndGroupsForScene.size() - 1, 1);
                                    SceneStoreChangeListener sceneStoreChangeListener6 = this.onStoreItemChangeListener;
                                    Intrinsics.checkNotNull(sceneStoreChangeListener6);
                                    sceneStoreChangeListener6.onStoreItemUpdated(this.itemsAndGroupsForScene.size() - 1);
                                }
                            }
                            this.myPlaceItems.add(item4);
                            this.myPlaceItemsIdList.add(str4);
                        }
                    } else {
                        if (num4 == null || num4.intValue() != 3) {
                            Integer num5 = item3.channelDipState;
                            if (num5 != null && num5.intValue() == 10) {
                            }
                            if (z13) {
                                Item item42 = new Item(item3);
                                Boolean bool2 = Boolean.FALSE;
                                item42.enableInScene = bool2;
                                if (!this.newScene) {
                                }
                                if (this.myPlaceSelected) {
                                }
                                this.myPlaceItems.add(item42);
                                this.myPlaceItemsIdList.add(str4);
                            }
                        }
                        z11 = true;
                        if (z13) {
                        }
                    }
                }
            }
            if (z7) {
                List<String> itemList = monitorStore.getItemList();
                if (itemList.size() > 0) {
                    Item createMonitorGroupStoreItem = Item.Companion.createMonitorGroupStoreItem(MONITOR_GROUP_ID, MONITOR_GROUP_NAME);
                    createMonitorGroupStoreItem.enableInScene = Boolean.FALSE;
                    if (this.monitorsSelected) {
                        this.itemsAndGroupsForScene.add(createMonitorGroupStoreItem);
                        this.itemIdListForSceneEdit.add(MONITOR_GROUP_ID);
                        SceneStoreChangeListener sceneStoreChangeListener7 = this.onStoreItemChangeListener;
                        if (sceneStoreChangeListener7 != null) {
                            Intrinsics.checkNotNull(sceneStoreChangeListener7);
                            sceneStoreChangeListener7.onStoreItemsAdded(MONITOR_GROUP_ID, this.itemsAndGroupsForScene.size() - 1, 1);
                            SceneStoreChangeListener sceneStoreChangeListener8 = this.onStoreItemChangeListener;
                            Intrinsics.checkNotNull(sceneStoreChangeListener8);
                            sceneStoreChangeListener8.onStoreItemUpdated(this.itemsAndGroupsForScene.size() - 1);
                        }
                    }
                    this.monitorsItems.add(createMonitorGroupStoreItem);
                    this.monitorsItemsIdList.add(MONITOR_GROUP_ID);
                    for (String str6 : itemList) {
                        DataMonitor monitor = monitorStore.getMonitor(str6);
                        if (monitor != null) {
                            Item item5 = new Item(str6, monitor);
                            item5.enableInScene = Boolean.FALSE;
                            if (!this.newScene && (hashMap2 = this.editSceneData.monitors) != null) {
                                Intrinsics.checkNotNull(hashMap2);
                                for (DataMonitor dataMonitor : hashMap2.values()) {
                                    Intrinsics.checkNotNull(dataMonitor);
                                    if (Intrinsics.areEqual(dataMonitor.id, item5.id)) {
                                        item5.update(null, new Item(item5.id, dataMonitor));
                                        DataMonitor dataMonitor2 = item5.monitor;
                                        Intrinsics.checkNotNull(dataMonitor2);
                                        dataMonitor2.name = monitor.name;
                                        item5.enableInScene = Boolean.TRUE;
                                    }
                                }
                            }
                            if (this.monitorsSelected) {
                                this.itemsAndGroupsForScene.add(item5);
                                this.itemIdListForSceneEdit.add(str6);
                                SceneStoreChangeListener sceneStoreChangeListener9 = this.onStoreItemChangeListener;
                                if (sceneStoreChangeListener9 != null) {
                                    Intrinsics.checkNotNull(sceneStoreChangeListener9);
                                    sceneStoreChangeListener9.onStoreItemsAdded(str6, this.itemsAndGroupsForScene.size() - 1, 1);
                                    SceneStoreChangeListener sceneStoreChangeListener10 = this.onStoreItemChangeListener;
                                    Intrinsics.checkNotNull(sceneStoreChangeListener10);
                                    sceneStoreChangeListener10.onStoreItemUpdated(this.itemsAndGroupsForScene.size() - 1);
                                }
                            }
                            this.monitorsItems.add(item5);
                            this.monitorsItemsIdList.add(str6);
                        }
                    }
                }
            }
            if (z10 && this.sonosRepository.u().getValue() != null) {
                List<Sonos> value = this.sonosRepository.u().getValue();
                Intrinsics.checkNotNull(value);
                if (value.size() > 0) {
                    Item createSonosGroupStoreItem = Item.Companion.createSonosGroupStoreItem(SONOS_GROUP_ID, SONOS_GROUP_NAME);
                    createSonosGroupStoreItem.enableInScene = Boolean.FALSE;
                    if (this.sonosSelected) {
                        this.itemsAndGroupsForScene.add(createSonosGroupStoreItem);
                        this.itemIdListForSceneEdit.add(SONOS_GROUP_ID);
                        SceneStoreChangeListener sceneStoreChangeListener11 = this.onStoreItemChangeListener;
                        if (sceneStoreChangeListener11 != null) {
                            Intrinsics.checkNotNull(sceneStoreChangeListener11);
                            sceneStoreChangeListener11.onStoreItemsAdded(SONOS_GROUP_ID, this.itemsAndGroupsForScene.size() - 1, 1);
                            SceneStoreChangeListener sceneStoreChangeListener12 = this.onStoreItemChangeListener;
                            Intrinsics.checkNotNull(sceneStoreChangeListener12);
                            sceneStoreChangeListener12.onStoreItemUpdated(this.itemsAndGroupsForScene.size() - 1);
                        }
                    }
                    this.sonosItems.add(createSonosGroupStoreItem);
                    this.sonosItemsIdList.add(SONOS_GROUP_ID);
                    List<Sonos> value2 = this.sonosRepository.u().getValue();
                    Intrinsics.checkNotNull(value2);
                    for (Sonos sonos : value2) {
                        if (sonos != null) {
                            Item item6 = new Item(sonos.getUdn(), sonos);
                            item6.enableInScene = Boolean.FALSE;
                            if (!this.newScene && (hashMap = this.editSceneData.sonos) != null) {
                                Intrinsics.checkNotNull(hashMap);
                                for (Sonos sonos2 : hashMap.values()) {
                                    if (Intrinsics.areEqual(sonos2.getUdn(), item6.id)) {
                                        String str7 = item6.id;
                                        Intrinsics.checkNotNull(sonos2);
                                        item6.update(null, new Item(str7, sonos2));
                                        item6.enableInScene = Boolean.TRUE;
                                    }
                                }
                            }
                            if (this.sonosSelected) {
                                this.itemsAndGroupsForScene.add(item6);
                                this.itemIdListForSceneEdit.add(sonos.getUdn());
                                SceneStoreChangeListener sceneStoreChangeListener13 = this.onStoreItemChangeListener;
                                if (sceneStoreChangeListener13 != null) {
                                    Intrinsics.checkNotNull(sceneStoreChangeListener13);
                                    sceneStoreChangeListener13.onStoreItemsAdded(sonos.getUdn(), this.itemsAndGroupsForScene.size() - 1, 1);
                                    SceneStoreChangeListener sceneStoreChangeListener14 = this.onStoreItemChangeListener;
                                    Intrinsics.checkNotNull(sceneStoreChangeListener14);
                                    sceneStoreChangeListener14.onStoreItemUpdated(this.itemsAndGroupsForScene.size() - 1);
                                }
                            }
                            this.sonosItems.add(item6);
                            this.sonosItemsIdList.add(sonos.getUdn());
                        }
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
        Timber.forest.d("total items and groups for scene edit:" + this.itemsAndGroupsForScene.size(), new Object[0]);
    }

    public final void notifyItemInEditSceneUpdateListener(@NotNull String itemId) {
        Intrinsics.checkNotNullParameter(itemId, "itemId");
        synchronized (MasterStore.class) {
            SceneStoreChangeListener sceneStoreChangeListener = this.onStoreItemChangeListener;
            if (sceneStoreChangeListener != null) {
                Intrinsics.checkNotNull(sceneStoreChangeListener);
                sceneStoreChangeListener.onStoreItemUpdated(getItemPositionForSceneEdit(itemId));
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void repopulateStoreItemsAndGroupsForSceneEdit() {
        this.itemsAndGroupsForScene.clear();
        this.itemIdListForSceneEdit.clear();
        if (this.myAirSelected) {
            if (this.enableAllMyAirItemsOnRepopulate) {
                Iterator<Item> it = this.myAirItems.iterator();
                while (it.hasNext()) {
                    it.next().enableInScene = Boolean.TRUE;
                }
            }
            this.itemsAndGroupsForScene.addAll(this.myAirItems);
            this.itemIdListForSceneEdit.addAll(this.myAirItemsIdList);
        }
        if (this.myLightsSelected) {
            if (this.enableAllMyLightsItemsOnRepopulate) {
                Iterator<Item> it2 = this.myLightsItems.iterator();
                while (it2.hasNext()) {
                    it2.next().enableInScene = Boolean.TRUE;
                }
            }
            this.itemsAndGroupsForScene.addAll(this.myLightsItems);
            this.itemIdListForSceneEdit.addAll(this.myLightsItemsIdList);
        }
        if (this.myPlaceSelected) {
            if (this.disableAllMyPlaceItemsOnRepopulate) {
                Iterator<Item> it3 = this.myPlaceItems.iterator();
                while (it3.hasNext()) {
                    it3.next().enableInScene = Boolean.FALSE;
                }
            }
            this.itemsAndGroupsForScene.addAll(this.myPlaceItems);
            this.itemIdListForSceneEdit.addAll(this.myPlaceItemsIdList);
        }
        if (this.monitorsSelected) {
            if (this.disableAllMonitorsItemsOnRepopulate) {
                Iterator<Item> it4 = this.monitorsItems.iterator();
                while (it4.hasNext()) {
                    it4.next().enableInScene = Boolean.FALSE;
                }
            }
            this.itemsAndGroupsForScene.addAll(this.monitorsItems);
            this.itemIdListForSceneEdit.addAll(this.monitorsItemsIdList);
        }
        if (this.sonosSelected) {
            if (this.disableAllSonosItemsOnRepopulate) {
                Iterator<Item> it5 = this.sonosItems.iterator();
                while (it5.hasNext()) {
                    it5.next().enableInScene = Boolean.FALSE;
                }
            }
            this.itemsAndGroupsForScene.addAll(this.sonosItems);
            this.itemIdListForSceneEdit.addAll(this.sonosItemsIdList);
        }
        SceneStoreChangeListener sceneStoreChangeListener = this.onStoreItemChangeListener;
        if (sceneStoreChangeListener != null) {
            Intrinsics.checkNotNull(sceneStoreChangeListener);
            sceneStoreChangeListener.onStoreItemsAdded("Repopulate", 0, this.itemsAndGroupsForScene.size());
        }
    }

    public final void setOnStoreItemChangeListener(@Nullable SceneStoreChangeListener sceneStoreChangeListener) {
        this.onStoreItemChangeListener = sceneStoreChangeListener;
    }

    public final void workoutTabItemsToShow() {
        HashMap<String, DataAirconSystem> hashMap = this.editSceneData.aircons;
        if (hashMap != null) {
            Intrinsics.checkNotNull(hashMap);
            if (hashMap.size() > 0) {
                this.myAirSelected = true;
            }
        }
        HashMap<String, DataLight> hashMap2 = this.editSceneData.lights;
        if (hashMap2 != null) {
            Intrinsics.checkNotNull(hashMap2);
            if (hashMap2.size() > 0) {
                this.myLightsSelected = true;
            }
        }
        HashMap<String, DataMyThing> hashMap3 = this.editSceneData.things;
        if (hashMap3 != null) {
            Intrinsics.checkNotNull(hashMap3);
            if (hashMap3.size() > 0) {
                this.myPlaceSelected = true;
            }
        }
        HashMap<String, DataMonitor> hashMap4 = this.editSceneData.monitors;
        if (hashMap4 != null) {
            Intrinsics.checkNotNull(hashMap4);
            if (hashMap4.size() > 0) {
                this.monitorsSelected = true;
            }
        }
        HashMap<String, Sonos> hashMap5 = this.editSceneData.sonos;
        if (hashMap5 != null) {
            Intrinsics.checkNotNull(hashMap5);
            if (hashMap5.size() > 0) {
                this.sonosSelected = true;
            }
        }
    }
}