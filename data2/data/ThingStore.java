package com.air.advantage.data;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.air.advantage.AppFeatures;
import com.air.advantage.jsondata.MasterStore;
import com.air.advantage.libraryairconlightjson.CommonFuncs;
import com.air.advantage.libraryairconlightjson.UartConstants;
import com.air.advantage.things.ThingFunctions;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.DebugKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

@PurelyImplements({"SMAP\nThingStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ThingStore.kt\ncom/air/advantage/data/ThingStore\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,941:1\n1#2:942\n1002#3,2:943\n*S KotlinDebug\n*F\n+ 1 ThingStore.kt\ncom/air/advantage/data/ThingStore\n*L\n875#1:943,2\n*E\n"})
/* renamed from: com.air.advantage.data.s1 */
/* loaded from: classes.dex */
public final class ThingStore {
    public static final int GROUP_ALL_OFF = 4;
    public static final int GROUP_ALL_ON = 3;
    public static final int GROUP_EXPANDED = 1;
    private static final int GROUP_SHRUNK = 2;

    @NotNull
    public static final String LOCK_GROUP_ID = "l1";

    @NotNull
    public static final String LOCK_GROUP_NAME = "Locks";

    @NotNull
    public static final String MOTION_SENSOR_GROUP_ID = "m1";

    @NotNull
    public static final String MOTION_SENSOR_GROUP_NAME = "Motion Sensors";
    private boolean blockItemUpdates;

    @Nullable
    private ArrayList<Item> blockedItems;
    private int numberOfLockItems;
    private int numberOfSensorItems;

    @Nullable
    private ThingsChangeListener onThingChangeListener;

    @Nullable
    private c onThingRenameChangeListener;
    private boolean thingsPaused;

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = ThingStore.class.getSimpleName();

    @NotNull
    private final ThingFunctions thingFunctions = ThingFunctions.Companion.a();

    @NotNull
    private ArrayList<String> favScenes = new ArrayList<>();

    @NotNull
    private ArrayList<Item> thingsDimmableInDM = new ArrayList<>();

    @NotNull
    private final ArrayList<String> expandedGroups = new ArrayList<>();

    @NotNull
    private ArrayList<String> expandedList = new ArrayList<>();

    @NotNull
    private ArrayList<String> groupList = new ArrayList<>();

    @NotNull
    private final HashMap<String, Integer> numberInGroup = new HashMap<>();

    @NotNull
    private final ArrayList<String> thingIdListForRename = new ArrayList<>();

    @NotNull
    private final HashMap<String, Item> itemHashMap = new HashMap<>();

    @NotNull
    private final ArrayList<String> itemList = new ArrayList<>();

    /* renamed from: com.air.advantage.data.s1$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.s1.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* renamed from: com.air.advantage.data.s1$b */
    public interface ThingsChangeListener {
        void onThingMoved(@Nullable String str, int i10, int i11);

        void onThingUpdated(int i10);

        void onThingsAdded(@Nullable String str, int i10, int i11);

        void onThingsRemoved(@Nullable String str, int i10, int i11);

        void requestScrollToPosition(int i10);
    }

    /* renamed from: com.air.advantage.data.s1$c */
    public interface c {
        void onThingRenameUpdated(int i10);

        void onThingsRenameAdded(@Nullable String str, int i10, int i11);
    }

    @PurelyImplements({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 ThingStore.kt\ncom/air/advantage/data/ThingStore\n*L\n1#1,328:1\n875#2:329\n*E\n"})
    /* renamed from: com.air.advantage.data.s1$d */
    public static final class d<T> implements Comparator {
        /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: T */
        /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: T */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Comparator
        public final int compare(T t7, T t10) {
            return ComparisonsKt__ComparisonsKt.compareValues((String) t7, (String) t10);
        }
    }

    private final int getExpandedItemPositionFromId(String str) {
        return this.expandedList.indexOf(str);
    }

    private final Item getItemAtPosition(int i10) {
        if (i10 < this.itemList.size()) {
            return this.itemHashMap.get(this.itemList.get(i10));
        }
        return null;
    }

    private final int getItemPosition(String str) {
        return this.itemList.indexOf(str);
    }

    private final boolean isChannelDipStateGarageType(Integer num) {
        return num != null && (num.intValue() == 3 || num.intValue() == 10);
    }

    private final void sendGroupChangeBroadcast(Context context, String str) {
        Intent intent = new Intent(UartConstants.THING_GROUP_UPDATE);
        intent.putExtra("groupId", str);
        Intrinsics.checkNotNull(context);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private final void sendItemAddedMessage(String str, String str2, int i10) {
        if (i10 >= 0) {
            Timber.forest.d("ThingStore - New item " + str + " has appeared in group " + str2 + " position " + i10, new Object[0]);
            ThingsChangeListener thingsChangeListener = this.onThingChangeListener;
            if (thingsChangeListener != null) {
                Intrinsics.checkNotNull(thingsChangeListener);
                thingsChangeListener.onThingsAdded(str, i10, 1);
            }
        }
    }

    private final ArrayList<String> workOutExpandedList() {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        Iterator<String> it = this.itemList.iterator();
        String str = "";
        int i10 = 0;
        while (it.hasNext()) {
            String next = it.next();
            Intrinsics.checkNotNull(next);
            if (StringsKt__StringsJVMKt.startsWith(next, "m", false, 2, null)) {
                arrayList.add(next);
                arrayList2.add(next);
                this.numberInGroup.put(next, 0);
            } else if (StringsKt__StringsJVMKt.startsWith(next, "l", false, 2, null)) {
                arrayList.add(next);
                arrayList2.add(next);
                this.numberInGroup.put(next, 0);
            } else if (this.expandedGroups.contains(str)) {
                arrayList.add(next);
                i10++;
                this.numberInGroup.put(str, Integer.valueOf(i10));
            } else {
                i10++;
                this.numberInGroup.put(str, Integer.valueOf(i10));
            }
            i10 = 0;
            str = next;
        }
        this.groupList = arrayList2;
        return arrayList;
    }

    public final void addGroup(@NotNull Item dataStoreItem) {
        Intrinsics.checkNotNullParameter(dataStoreItem, "dataStoreItem");
        synchronized (MasterStore.class) {
            this.itemHashMap.put(dataStoreItem.id, dataStoreItem);
            this.itemList.add(dataStoreItem.id);
            this.expandedList = workOutExpandedList();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void addItemToGroup(@Nullable String str, @Nullable String str2) {
        synchronized (MasterStore.class) {
            new CommonFuncs().moveItem(this.itemList, getItemPosition(str), getItemPosition(str2));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void checkGroupState(@Nullable Context context, @Nullable MasterStore masterStore, @Nullable String str) {
        String groupIdOfItem = getGroupIdOfItem(str);
        int indexOf = this.itemList.indexOf(groupIdOfItem) + 1;
        Integer num = this.numberInGroup.get(groupIdOfItem);
        Intrinsics.checkNotNull(num);
        int intValue = num.intValue() + indexOf;
        boolean z7 = false;
        boolean z10 = false;
        boolean z11 = false;
        String str2 = null;
        boolean z12 = false;
        while (true) {
            if (indexOf >= intValue) {
                break;
            }
            Item itemAtPosition = getItemAtPosition(indexOf);
            if (itemAtPosition != null) {
                if (itemAtPosition.getItemValue() != null) {
                    Integer itemValue = itemAtPosition.getItemValue();
                    if (itemValue == null || itemValue.intValue() != 100) {
                        Integer itemValue2 = itemAtPosition.getItemValue();
                        if (itemValue2 == null || itemValue2.intValue() != 0) {
                            Integer itemValue3 = itemAtPosition.getItemValue();
                            if (itemValue3 != null && itemValue3.intValue() == 50) {
                                if (z12 || z7) {
                                    z10 = true;
                                    z11 = true;
                                } else {
                                    z11 = true;
                                }
                            }
                        } else if (z12 || z11) {
                            z7 = true;
                            z10 = true;
                        } else {
                            z7 = true;
                        }
                    } else if (z7 || z11) {
                        z12 = true;
                        z10 = true;
                    } else {
                        z12 = true;
                    }
                }
                if (str2 == null) {
                    str2 = itemAtPosition.buttonType;
                } else if (Intrinsics.areEqual(str2, itemAtPosition.buttonType)) {
                    continue;
                } else {
                    str2 = "none";
                    if (z10) {
                        break;
                    }
                }
            }
            indexOf++;
        }
        Item item = this.itemHashMap.get(groupIdOfItem);
        if (item != null) {
            if (z10) {
                item.groupState = State.mixed;
            } else if (z12) {
                item.groupState = State.on;
            } else if (z7) {
                item.groupState = State.off;
            } else {
                item.groupState = State.stop;
            }
            item.buttonType = str2 != null ? str2 : "none";
            item.doUpdate(context);
        }
    }

    public final void clear() {
        synchronized (MasterStore.class) {
            this.itemHashMap.clear();
            this.itemList.clear();
            this.expandedGroups.clear();
            this.expandedList = workOutExpandedList();
            this.numberOfSensorItems = 0;
            this.numberOfLockItems = 0;
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void clearThingIdListForRename() {
        this.thingIdListForRename.clear();
    }

    public final void deleteGroup(@Nullable Context context, @NotNull String groupId) {
        Intrinsics.checkNotNullParameter(groupId, "groupId");
        synchronized (MasterStore.class) {
            if (StringsKt__StringsJVMKt.startsWith(groupId, "m", false, 2, null)) {
                this.itemHashMap.remove(groupId);
                this.itemList.remove(groupId);
                this.expandedList = workOutExpandedList();
                sendGroupChangeBroadcast(context, groupId);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    @Nullable
    public final Item getExpandedItemByPosition(int i10) {
        synchronized (MasterStore.class) {
            if (i10 >= 0) {
                if (i10 < numberOfExpandedItemsToShow()) {
                    return this.itemHashMap.get(this.expandedList.get(i10));
                }
            }
            Unit unit = Unit.INSTANCE;
            return null;
        }
    }

    public final int getExpandedItemPosition(@Nullable String str) {
        int indexOf;
        synchronized (MasterStore.class) {
            indexOf = this.expandedList.indexOf(str);
        }
        return indexOf;
    }

    @NotNull
    public final ArrayList<String> getFavScenes() {
        return this.favScenes;
    }

    @Nullable
    public final String getFirstGarageItemIdFromTheItemList() {
        Iterator<String> it = this.itemList.iterator();
        while (it.hasNext()) {
            String next = it.next();
            Intrinsics.checkNotNull(next);
            if (!StringsKt__StringsJVMKt.startsWith(next, "m", false, 2, null)) {
                Item item = this.itemHashMap.get(next);
                Intrinsics.checkNotNull(item);
                if (isChannelDipStateGarageType(item.channelDipState)) {
                    return next;
                }
            }
        }
        return null;
    }

    @Nullable
    public final Item getGroupByNumber(int i10) {
        synchronized (MasterStore.class) {
            int size = this.groupList.size();
            if (this.numberOfSensorItems > 0) {
                size = this.groupList.size() - 1;
            }
            if (i10 < size) {
                return this.numberOfSensorItems == 0 ? this.itemHashMap.get(this.groupList.get(i10)) : this.itemHashMap.get(this.groupList.get(i10 + 1));
            }
            Unit unit = Unit.INSTANCE;
            return null;
        }
    }

    @Nullable
    public final String getGroupIdOfItem(@Nullable String str) {
        String str2;
        synchronized (MasterStore.class) {
            Iterator<String> it = this.itemList.iterator();
            str2 = null;
            while (it.hasNext()) {
                String next = it.next();
                Intrinsics.checkNotNull(next);
                if (StringsKt__StringsJVMKt.startsWith(next, "m", false, 2, null)) {
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
    public final Item getItem(@Nullable String str) {
        Item item;
        synchronized (MasterStore.class) {
            item = this.itemHashMap.get(str);
        }
        return item;
    }

    @NotNull
    public final List<String> getItemList() {
        return new ArrayList(this.itemList);
    }

    public final int getNumberOfGarageDoor() {
        Iterator<String> it = this.itemList.iterator();
        int i10 = 0;
        while (it.hasNext()) {
            String next = it.next();
            Intrinsics.checkNotNull(next);
            if (!StringsKt__StringsJVMKt.startsWith(next, "m", false, 2, null)) {
                Item item = this.itemHashMap.get(next);
                Intrinsics.checkNotNull(item);
                if (isChannelDipStateGarageType(item.channelDipState)) {
                    i10++;
                }
            }
        }
        return i10;
    }

    public final int getNumberOfLockItems() {
        return this.numberOfLockItems;
    }

    public final int getNumberOfSensorItems() {
        return this.numberOfSensorItems;
    }

    @Nullable
    public final String getThingIdRenameFromPosition(int i10) {
        return this.thingIdListForRename.get(i10);
    }

    @NotNull
    public final ArrayList<Item> getThingsDimmableInDM() {
        return this.thingsDimmableInDM;
    }

    public final void initDMThingsForDimOffsetSetup() {
        Integer num;
        synchronized (MasterStore.class) {
            this.thingsDimmableInDM.clear();
            Iterator<String> it = this.itemList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                Item item = this.itemHashMap.get(next);
                if (item != null && (num = item.type) != null && num != null && num.intValue() == 19) {
                    this.thingsDimmableInDM.add(new Item(item));
                    ThingsChangeListener thingsChangeListener = this.onThingChangeListener;
                    if (thingsChangeListener != null) {
                        Intrinsics.checkNotNull(thingsChangeListener);
                        thingsChangeListener.onThingsAdded(next, this.thingsDimmableInDM.size() - 1, 1);
                        ThingsChangeListener thingsChangeListener2 = this.onThingChangeListener;
                        Intrinsics.checkNotNull(thingsChangeListener2);
                        thingsChangeListener2.onThingUpdated(this.thingsDimmableInDM.size() - 1);
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void initThingsIdListForRename() {
        synchronized (MasterStore.class) {
            MasterStore helper = MasterStore.helper.getInstance();
            this.thingIdListForRename.clear();
            HashMap<String, DataSensor> hashMap = helper.dataMaster.mySensors.sensors;
            Intrinsics.checkNotNull(hashMap);
            if (hashMap.size() > 0) {
                HashMap<String, DataSensor> hashMap2 = helper.dataMaster.mySensors.sensors;
                Intrinsics.checkNotNull(hashMap2);
                ArrayList arrayList = new ArrayList(hashMap2.keySet());
                if (arrayList.size() > 1 && arrayList.size() > 1) {
                    CollectionsKt__MutableCollectionsJVMKt.m0(arrayList, new d());
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    this.thingIdListForRename.add(str);
                    c cVar = this.onThingRenameChangeListener;
                    if (cVar != null) {
                        Intrinsics.checkNotNull(cVar);
                        cVar.onThingsRenameAdded(str, this.thingIdListForRename.size() - 1, 1);
                        c cVar2 = this.onThingRenameChangeListener;
                        Intrinsics.checkNotNull(cVar2);
                        cVar2.onThingRenameUpdated(this.thingIdListForRename.size() - 1);
                    }
                }
            }
            TreeMap<String, DataMyThing> treeMap = helper.dataMaster.myThings.things;
            Intrinsics.checkNotNull(treeMap);
            for (DataMyThing dataMyThing : treeMap.values()) {
                if (dataMyThing != null) {
                    this.thingIdListForRename.add(dataMyThing.id);
                }
                c cVar3 = this.onThingRenameChangeListener;
                if (cVar3 != null) {
                    if (dataMyThing != null) {
                        Intrinsics.checkNotNull(cVar3);
                        cVar3.onThingsRenameAdded(dataMyThing.id, this.thingIdListForRename.size() - 1, 1);
                    }
                    c cVar4 = this.onThingRenameChangeListener;
                    Intrinsics.checkNotNull(cVar4);
                    cVar4.onThingRenameUpdated(this.thingIdListForRename.size() - 1);
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final boolean isGroupExpanded(@Nullable String str) {
        boolean contains;
        synchronized (MasterStore.class) {
            contains = this.expandedGroups.contains(str);
        }
        return contains;
    }

    public final void itemBeingMoved(@Nullable Context context, boolean z7, @Nullable String str) {
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
                if (z10 && this.onThingChangeListener != null) {
                    int itemPosition = getItemPosition(str);
                    ThingsChangeListener thingsChangeListener = this.onThingChangeListener;
                    Intrinsics.checkNotNull(thingsChangeListener);
                    thingsChangeListener.requestScrollToPosition(itemPosition);
                }
            } else {
                this.blockedItems = null;
            }
            setBlockItemUpdates(context, z7);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final int numberOfExpandedItemsToShow() {
        int size;
        if (this.thingsPaused) {
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
            int i10 = this.numberOfSensorItems;
            size = (i10 == 0 && this.numberOfLockItems == 0) ? this.groupList.size() : (i10 == 0 || this.numberOfLockItems == 0) ? this.groupList.size() - 1 : this.groupList.size() - 2;
        }
        return size;
    }

    public final int numberOfThingsRename() {
        return this.thingIdListForRename.size();
    }

    public final void setBlockItemUpdates(@Nullable Context context, boolean z7) {
        ArrayList<Item> arrayList;
        synchronized (MasterStore.class) {
            this.blockItemUpdates = z7;
            if (!z7 && (arrayList = this.blockedItems) != null) {
                Intrinsics.checkNotNull(arrayList);
                updateItem(context, arrayList);
                this.blockedItems = null;
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setFavScenes(@NotNull ArrayList<String> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.favScenes = arrayList;
    }

    public final void setGroupState(@NotNull Context context, @Nullable String str, int i10) {
        String str2;
        int i11;
        Intrinsics.checkNotNullParameter(context, "context");
        synchronized (MasterStore.class) {
            MasterStore helper = MasterStore.helper.getInstance();
            if (i10 == 3) {
                str2 = DebugKt.DEBUG_PROPERTY_VALUE_ON;
                i11 = 100;
            } else {
                if (i10 != 4) {
                    return;
                }
                str2 = DebugKt.DEBUG_PROPERTY_VALUE_OFF;
                i11 = 0;
            }
            int i12 = i11;
            int itemPosition = getItemPosition(str);
            int indexOf = this.groupList.indexOf(str);
            int size = indexOf == this.groupList.size() + (-1) ? this.itemList.size() : getItemPosition(this.groupList.get(indexOf + 1));
            if (itemPosition < size) {
                for (int i13 = itemPosition; i13 < size; i13++) {
                    Item itemAtPosition = getItemAtPosition(i13);
                    if (itemAtPosition != null) {
                        Integer num = itemAtPosition.type;
                        if (num != null && num.intValue() == 7) {
                            if (Intrinsics.areEqual(str2, DebugKt.DEBUG_PROPERTY_VALUE_ON)) {
                                itemAtPosition.groupState = State.on;
                            } else {
                                itemAtPosition.groupState = State.off;
                            }
                            itemAtPosition.doUpdate(context);
                        } else {
                            this.thingFunctions.m(context, helper, itemAtPosition, i12, false, false);
                        }
                    }
                }
            }
            DataGroupThing dataGroupThing = new DataGroupThing(str);
            if (Intrinsics.areEqual(str2, DebugKt.DEBUG_PROPERTY_VALUE_OFF)) {
                dataGroupThing.state = State.off;
            } else {
                dataGroupThing.state = State.on;
            }
            dataGroupThing.name = null;
            this.thingFunctions.h(context, dataGroupThing);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setOnThingChangeListener(@Nullable ThingsChangeListener thingsChangeListener) {
        this.onThingChangeListener = thingsChangeListener;
    }

    public final void setOnThingRenameChangeListener(@Nullable c cVar) {
        this.onThingRenameChangeListener = cVar;
    }

    public final void setThingsDimmableInDM(@NotNull ArrayList<Item> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.thingsDimmableInDM = arrayList;
    }

    public final void setThingsPaused(boolean z7) {
        synchronized (MasterStore.class) {
            if (z7) {
                int numberOfExpandedItemsToShow = numberOfExpandedItemsToShow();
                this.thingsPaused = true;
                ThingsChangeListener thingsChangeListener = this.onThingChangeListener;
                if (thingsChangeListener != null) {
                    Intrinsics.checkNotNull(thingsChangeListener);
                    thingsChangeListener.onThingsRemoved("Paused", 0, numberOfExpandedItemsToShow);
                }
            } else {
                this.thingsPaused = false;
                if (this.onThingChangeListener != null) {
                    int numberOfExpandedItemsToShow2 = numberOfExpandedItemsToShow();
                    ThingsChangeListener thingsChangeListener2 = this.onThingChangeListener;
                    Intrinsics.checkNotNull(thingsChangeListener2);
                    thingsChangeListener2.onThingsAdded("Paused", 0, numberOfExpandedItemsToShow2);
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final int toggleExpandedGroup(@Nullable String str) {
        synchronized (MasterStore.class) {
            int expandedItemPositionFromId = getExpandedItemPositionFromId(str);
            if (!this.expandedGroups.contains(str)) {
                int numberOfExpandedItemsToShow = numberOfExpandedItemsToShow();
                this.expandedGroups.add(str);
                this.expandedList = workOutExpandedList();
                int numberOfExpandedItemsToShow2 = numberOfExpandedItemsToShow();
                ThingsChangeListener thingsChangeListener = this.onThingChangeListener;
                if (thingsChangeListener != null) {
                    Intrinsics.checkNotNull(thingsChangeListener);
                    thingsChangeListener.onThingsAdded(str, expandedItemPositionFromId + 1, numberOfExpandedItemsToShow2 - numberOfExpandedItemsToShow);
                    ThingsChangeListener thingsChangeListener2 = this.onThingChangeListener;
                    Intrinsics.checkNotNull(thingsChangeListener2);
                    thingsChangeListener2.onThingUpdated(expandedItemPositionFromId);
                }
                Unit unit = Unit.INSTANCE;
                return 1;
            }
            if (numberOfGroups() == 1 && this.numberOfSensorItems == 0 && this.numberOfLockItems == 0) {
                return 1;
            }
            if (numberOfGroups() == 0 && this.numberOfSensorItems != 0 && this.numberOfLockItems == 0) {
                return 1;
            }
            if (numberOfGroups() == 0 && this.numberOfSensorItems == 0 && this.numberOfLockItems != 0) {
                return 1;
            }
            int numberOfExpandedItemsToShow3 = numberOfExpandedItemsToShow();
            this.expandedGroups.remove(str);
            this.expandedList = workOutExpandedList();
            int numberOfExpandedItemsToShow4 = numberOfExpandedItemsToShow();
            ThingsChangeListener thingsChangeListener3 = this.onThingChangeListener;
            if (thingsChangeListener3 != null) {
                Intrinsics.checkNotNull(thingsChangeListener3);
                thingsChangeListener3.onThingsRemoved(str, expandedItemPositionFromId + 1, numberOfExpandedItemsToShow3 - numberOfExpandedItemsToShow4);
                ThingsChangeListener thingsChangeListener4 = this.onThingChangeListener;
                Intrinsics.checkNotNull(thingsChangeListener4);
                thingsChangeListener4.onThingUpdated(expandedItemPositionFromId);
            }
            return 2;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void updateItem(@Nullable Context context, @NotNull DataMaster incomingMasterData, boolean z7) {
        Intrinsics.checkNotNullParameter(incomingMasterData, "incomingMasterData");
        synchronized (MasterStore.class) {
            ArrayList<Item> arrayList = new ArrayList<>();
            if (z7) {
                incomingMasterData.myLocks = MasterStore.helper.getInstance().dataMaster.myLocks;
            }
            this.numberOfLockItems = 0;
            HashMap<String, DataSensor> hashMap = incomingMasterData.mySensors.sensors;
            Intrinsics.checkNotNull(hashMap);
            if (hashMap.size() > 0) {
                DataGroupThing dataGroupThing = new DataGroupThing();
                dataGroupThing.id = MOTION_SENSOR_GROUP_ID;
                dataGroupThing.name = MOTION_SENSOR_GROUP_NAME;
                arrayList.add(new Item(dataGroupThing));
                ArrayList<String> arrayList2 = incomingMasterData.mySensors.sensorsOrder;
                Intrinsics.checkNotNull(arrayList2);
                Iterator<String> it = arrayList2.iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    DataSensor sensor = incomingMasterData.mySensors.getSensor(next);
                    if (sensor != null) {
                        arrayList.add(new Item(next, sensor));
                    }
                }
                ArrayList<String> arrayList3 = incomingMasterData.mySensors.sensorsOrder;
                Intrinsics.checkNotNull(arrayList3);
                this.numberOfSensorItems = arrayList3.size() + 1;
            } else {
                this.numberOfSensorItems = 0;
            }
            TreeMap<String, DataMyThing> treeMap = incomingMasterData.myThings.things;
            Intrinsics.checkNotNull(treeMap);
            if (treeMap.size() > 0) {
                ArrayList<String> arrayList4 = incomingMasterData.myThings.groupsOrder;
                Intrinsics.checkNotNull(arrayList4);
                Iterator<String> it2 = arrayList4.iterator();
                while (it2.hasNext()) {
                    DataGroupThing dataGroupThing2 = incomingMasterData.myThings.getDataGroupThing(it2.next());
                    if (dataGroupThing2 != null) {
                        arrayList.add(new Item(dataGroupThing2));
                        Iterator<String> it3 = dataGroupThing2.thingsOrder.iterator();
                        while (it3.hasNext()) {
                            DataMyThing thingData = incomingMasterData.myThings.getThingData(it3.next());
                            if (thingData != null) {
                                arrayList.add(new Item(thingData));
                            }
                        }
                    }
                }
            }
            Timber.forest.d("ThingStore - Updating " + arrayList.size() + " of things", new Object[0]);
            updateItem(context, arrayList);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void updatePosition(@Nullable Context context, @NotNull String itemId, int i10) {
        Intrinsics.checkNotNullParameter(itemId, "itemId");
        synchronized (MasterStore.class) {
            MasterStore helper = MasterStore.helper.getInstance();
            int indexOf = this.itemList.indexOf(itemId);
            String groupIdOfItem = getGroupIdOfItem(itemId);
            Item expandedItemByPosition = getExpandedItemByPosition(i10);
            if (expandedItemByPosition != null) {
                new CommonFuncs().moveItem(this.itemList, indexOf, this.itemList.indexOf(expandedItemByPosition.id));
                this.expandedList = workOutExpandedList();
                String groupIdOfItem2 = getGroupIdOfItem(itemId);
                if (Intrinsics.areEqual(groupIdOfItem, groupIdOfItem2)) {
                    Timber.forest.d("ThingStore - Item " + itemId + " has moved within group " + groupIdOfItem2, new Object[0]);
                } else {
                    Timber.forest.d("ThingStore - Item " + itemId + " has moved from group " + groupIdOfItem + " to " + groupIdOfItem2, new Object[0]);
                    helper.dataFavourites.thingStore.checkGroupState(context, helper, groupIdOfItem);
                    helper.dataFavourites.thingStore.checkGroupState(context, helper, groupIdOfItem2);
                }
                if (context != null) {
                    int indexOf2 = this.itemList.indexOf(groupIdOfItem2);
                    int indexOf3 = this.itemList.indexOf(itemId);
                    Item item = this.itemHashMap.get(this.itemList.get(indexOf3));
                    Intrinsics.checkNotNull(item);
                    String str = "id=" + item.id + "&groupId=" + groupIdOfItem2 + "&position=" + (indexOf3 - indexOf2);
                    Timber.forest.d("setThingToGroupThing " + str, new Object[0]);
                    AppFeatures.sendOptionsRequest(context, "setThingToGroupThing", str, false, 8, null);
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x0184 A[PHI: r5
      0x0184: PHI (r5v6 boolean) = (r5v4 boolean), (r5v7 boolean) binds: [B:79:0x0182, B:71:0x0177] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void updateItem(Context context, ArrayList<Item> arrayList) {
        Iterator<Item> it;
        int i10;
        int i11 = 0;
        if (this.blockItemUpdates) {
            Timber.forest.d("ThingStore items update blocked", new Object[0]);
            this.blockedItems = new ArrayList<>(arrayList);
            return;
        }
        ArrayList arrayList2 = new ArrayList(this.itemList);
        Iterator<Item> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            arrayList2.remove(it2.next().id);
        }
        Iterator it3 = arrayList2.iterator();
        boolean z7 = false;
        boolean z10 = false;
        while (it3.hasNext()) {
            String str = (String) it3.next();
            int indexOf = this.expandedList.indexOf(str);
            String groupIdOfItem = getGroupIdOfItem(str);
            Timber.Forest forest = Timber.forest;
            forest.d("ThingStore - Existing item has been removed " + str + " " + groupIdOfItem + " " + indexOf, new Object[0]);
            if (groupIdOfItem != null) {
                if (Intrinsics.areEqual(groupIdOfItem, str)) {
                    if (this.expandedList.contains(groupIdOfItem)) {
                        this.expandedGroups.remove(groupIdOfItem);
                    }
                    if (!z7) {
                        z7 = true;
                    }
                }
            } else {
                forest.d("ThingStore - Null groupId from item ID (to be deleted): " + str + " " + indexOf, new Object[0]);
            }
            Item item = getItem(str);
            if (item != null && isChannelDipStateGarageType(item.channelDipState)) {
                z10 = true;
            }
            this.itemHashMap.remove(str);
            this.itemList.remove(str);
            int size = this.expandedList.size();
            ArrayList<String> workOutExpandedList = workOutExpandedList();
            this.expandedList = workOutExpandedList;
            int size2 = size - workOutExpandedList.size();
            if (indexOf >= 0) {
                if (size2 > 0) {
                    ThingsChangeListener thingsChangeListener = this.onThingChangeListener;
                    if (thingsChangeListener != null) {
                        Intrinsics.checkNotNull(thingsChangeListener);
                        thingsChangeListener.onThingsRemoved(str, indexOf, size2);
                    }
                } else if (z7) {
                    forest.d("Collapsed group with items deleted", new Object[0]);
                    ThingsChangeListener thingsChangeListener2 = this.onThingChangeListener;
                    if (thingsChangeListener2 != null) {
                        Intrinsics.checkNotNull(thingsChangeListener2);
                        thingsChangeListener2.onThingUpdated(indexOf);
                    }
                }
            }
        }
        Iterator<Item> it4 = arrayList.iterator();
        int i12 = 0;
        while (it4.hasNext()) {
            Item next = it4.next();
            int indexOf2 = this.itemList.indexOf(next.id);
            if (indexOf2 == -1) {
                this.itemList.add(i12, next.id);
                this.itemHashMap.put(next.id, new Item(next));
                int size3 = this.expandedList.size();
                ArrayList<String> workOutExpandedList2 = workOutExpandedList();
                this.expandedList = workOutExpandedList2;
                int indexOf3 = workOutExpandedList2.indexOf(next.id);
                if (size3 == this.expandedList.size()) {
                    ThingsChangeListener thingsChangeListener3 = this.onThingChangeListener;
                    if (thingsChangeListener3 != null && indexOf3 > 0) {
                        Intrinsics.checkNotNull(thingsChangeListener3);
                        thingsChangeListener3.onThingUpdated(indexOf3);
                    }
                } else {
                    sendItemAddedMessage(next.id, getGroupIdOfItem(next.id), indexOf3);
                }
                Integer num = next.type;
                if (num != null && num.intValue() == 7 && !z7) {
                    z7 = true;
                }
                if (isChannelDipStateGarageType(next.channelDipState)) {
                    z10 = true;
                }
            } else if (i12 != indexOf2 && isChannelDipStateGarageType(next.channelDipState)) {
            }
            i12++;
        }
        ArrayList arrayList3 = new ArrayList(this.expandedList);
        CommonFuncs commonFuncs = new CommonFuncs();
        Iterator<Item> it5 = arrayList.iterator();
        int i13 = 0;
        while (it5.hasNext()) {
            Item next2 = it5.next();
            int indexOf4 = this.itemList.indexOf(next2.id);
            boolean contains = arrayList3.contains(next2.id);
            int indexOf5 = arrayList3.indexOf(next2.id);
            commonFuncs.moveItem(this.itemList, indexOf4, i13);
            ArrayList<String> workOutExpandedList3 = workOutExpandedList();
            this.expandedList = workOutExpandedList3;
            boolean contains2 = workOutExpandedList3.contains(next2.id);
            int indexOf6 = this.expandedList.indexOf(next2.id);
            if (indexOf6 >= arrayList3.size()) {
                indexOf6 = arrayList3.size();
            }
            if (!contains && contains2) {
                Timber.forest.d("ThingStore - Existing item " + next2.id + " is now visible at " + indexOf6, new Object[i11]);
                ThingsChangeListener thingsChangeListener4 = this.onThingChangeListener;
                if (thingsChangeListener4 != null) {
                    Intrinsics.checkNotNull(thingsChangeListener4);
                    thingsChangeListener4.onThingsAdded(next2.id, indexOf6, 1);
                }
                arrayList3.add(indexOf6, next2.id);
            } else if (!contains || contains2) {
                if (contains && indexOf5 != indexOf6) {
                    it = it5;
                    i10 = 0;
                    Timber.forest.d(LOG_TAG, "ThingStore - Existing item " + next2.id + " has moved from " + indexOf5 + " to " + indexOf6);
                    ThingsChangeListener thingsChangeListener5 = this.onThingChangeListener;
                    if (thingsChangeListener5 != null) {
                        Intrinsics.checkNotNull(thingsChangeListener5);
                        thingsChangeListener5.onThingMoved(next2.id, indexOf5, indexOf6);
                    }
                    commonFuncs.moveItem(arrayList3, indexOf5, indexOf6);
                }
                i13++;
                i11 = i10;
                it5 = it;
            } else {
                Timber.forest.d("ThingStore - Existing item " + next2.id + " is now no longer visible at " + indexOf5, new Object[i11]);
                ThingsChangeListener thingsChangeListener6 = this.onThingChangeListener;
                if (thingsChangeListener6 != null) {
                    Intrinsics.checkNotNull(thingsChangeListener6);
                    thingsChangeListener6.onThingsRemoved(next2.id, indexOf5, 1);
                }
                arrayList3.remove(indexOf5);
            }
            i10 = i11;
            it = it5;
            i13++;
            i11 = i10;
            it5 = it;
        }
        Iterator<Item> it6 = arrayList.iterator();
        while (it6.hasNext()) {
            Item next3 = it6.next();
            Item item2 = getItem(next3.id);
            if (item2 != null) {
                item2.update(context, next3);
            }
        }
        if (z7) {
            sendGroupChangeBroadcast(context, "Change");
        }
        if (numberOfGroups() == 1 && this.numberOfSensorItems == 0 && this.numberOfLockItems == 0 && !this.expandedGroups.contains(DataThings.DEFAULT_GROUP)) {
            toggleExpandedGroup(DataThings.DEFAULT_GROUP);
        }
        if (numberOfGroups() == 0 && this.numberOfSensorItems == 0 && this.numberOfLockItems > 0 && !this.expandedGroups.contains(LOCK_GROUP_ID)) {
            toggleExpandedGroup(LOCK_GROUP_ID);
        }
        if (numberOfGroups() == 0 && this.numberOfLockItems == 0 && this.numberOfSensorItems > 0 && !this.expandedGroups.contains(MOTION_SENSOR_GROUP_ID)) {
            toggleExpandedGroup(MOTION_SENSOR_GROUP_ID);
        }
        if (z10) {
            Intent intent = new Intent(UartConstants.THING_GARAGE_DATA_UPDATE);
            Intrinsics.checkNotNull(context);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }
}