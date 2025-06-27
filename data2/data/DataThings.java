package com.air.advantage.data;

import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.air.advantage.uart.MyMasterData;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import kotlin.Unit;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.w0 */
/* loaded from: classes.dex */
public final class DataThings {

    @NotNull
    public static final String DEFAULT_GROUP = "m0";

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = DataThings.class.getSimpleName();

    @Nullable
    @SerializedName("things")
    @JvmField
    public TreeMap<String, DataMyThing> things = new TreeMap<>();

    @JsonExporter(export = false)
    @NotNull
    @SerializedName("backupThings")
    @JvmField
    public ArrayList<b> backupThings = new ArrayList<>();

    @Nullable
    @SerializedName("groups")
    @JvmField
    public TreeMap<String, DataGroupThing> groups = new TreeMap<>();

    @Nullable
    @SerializedName("groupsOrder")
    @JvmField
    public ArrayList<String> groupsOrder = new ArrayList<>();

    @Nullable
    @SerializedName("system")
    @JvmField
    public DataThingsSystem system = new DataThingsSystem();

    /* renamed from: com.air.advantage.data.w0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.w0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* renamed from: com.air.advantage.data.w0$b */
    public static final class b {

        @Nullable
        @SerializedName("buttonType")
        @JvmField
        public String buttonType;

        @Nullable
        @SerializedName("groupId")
        @JvmField
        public String groupId;

        @Nullable
        @SerializedName("id")
        @JvmField
        public String id;

        @Nullable
        @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
        @JvmField
        public String name;
    }

    private final DataGroupThing getGroupFromThingId(String str) {
        TreeMap<String, DataGroupThing> treeMap = this.groups;
        Intrinsics.checkNotNull(treeMap);
        for (DataGroupThing dataGroupThing : treeMap.values()) {
            Intrinsics.checkNotNull(dataGroupThing);
            Iterator<String> it = dataGroupThing.thingsOrder.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(it.next(), str)) {
                    return dataGroupThing;
                }
            }
        }
        return null;
    }

    public final void clearDataForBackup() {
        this.backupThings.clear();
        TreeMap<String, DataGroupThing> treeMap = this.groups;
        if (treeMap != null) {
            Intrinsics.checkNotNull(treeMap);
            for (DataGroupThing dataGroupThing : treeMap.values()) {
                Intrinsics.checkNotNull(dataGroupThing);
                dataGroupThing.clearDataForBackup();
            }
        }
        this.system = null;
        TreeMap<String, DataMyThing> treeMap2 = this.things;
        if (treeMap2 != null) {
            Intrinsics.checkNotNull(treeMap2);
            for (DataMyThing dataMyThing : treeMap2.values()) {
                Intrinsics.checkNotNull(dataMyThing);
                dataMyThing.clearDataForBackup();
            }
        }
    }

    @Nullable
    public final DataGroupThing getDataGroupThing(@Nullable String str) {
        if (str == null) {
            return null;
        }
        TreeMap<String, DataGroupThing> treeMap = this.groups;
        Intrinsics.checkNotNull(treeMap);
        return treeMap.get(str);
    }

    @Nullable
    public final String getGroupId(@NotNull String thingId) {
        Intrinsics.checkNotNullParameter(thingId, "thingId");
        DataGroupThing groupFromThingId = getGroupFromThingId(thingId);
        if (groupFromThingId != null) {
            return groupFromThingId.id;
        }
        return null;
    }

    @Nullable
    public final DataMyThing getThingData(@Nullable String str) {
        if (str == null) {
            return null;
        }
        TreeMap<String, DataMyThing> treeMap = this.things;
        Intrinsics.checkNotNull(treeMap);
        return treeMap.get(str);
    }

    public final int getThingPosition(@NotNull String thingId) {
        Intrinsics.checkNotNullParameter(thingId, "thingId");
        TreeMap<String, DataGroupThing> treeMap = this.groups;
        Intrinsics.checkNotNull(treeMap);
        for (DataGroupThing dataGroupThing : treeMap.values()) {
            Intrinsics.checkNotNull(dataGroupThing);
            Iterator<String> it = dataGroupThing.thingsOrder.iterator();
            int i10 = 0;
            while (it.hasNext()) {
                if (Intrinsics.areEqual(it.next(), thingId)) {
                    return i10;
                }
                i10++;
            }
        }
        return -1;
    }

    public final int numberGroups() {
        TreeMap<String, DataGroupThing> treeMap = this.groups;
        Intrinsics.checkNotNull(treeMap);
        return treeMap.size();
    }

    public final int numberLights() {
        TreeMap<String, DataMyThing> treeMap = this.things;
        Intrinsics.checkNotNull(treeMap);
        return treeMap.size();
    }

    public final void removeThing(@NotNull String thingId) {
        ArrayList<String> arrayList;
        Intrinsics.checkNotNullParameter(thingId, "thingId");
        DataGroupThing groupFromThingId = getGroupFromThingId(thingId);
        if (groupFromThingId != null && (arrayList = groupFromThingId.thingsOrder) != null) {
            arrayList.remove(thingId);
        }
        TreeMap<String, DataMyThing> treeMap = this.things;
        Intrinsics.checkNotNull(treeMap);
        treeMap.remove(thingId);
    }

    public final void updateGroupStates() {
        String str;
        TreeMap<String, DataGroupThing> treeMap = this.groups;
        Intrinsics.checkNotNull(treeMap);
        for (DataGroupThing dataGroupThing : treeMap.values()) {
            Intrinsics.checkNotNull(dataGroupThing);
            Iterator<String> it = dataGroupThing.thingsOrder.iterator();
            boolean z7 = false;
            boolean z10 = false;
            boolean z11 = false;
            String str2 = null;
            boolean z12 = false;
            while (true) {
                str = "none";
                if (!it.hasNext()) {
                    break;
                }
                DataMyThing thingData = getThingData(it.next());
                if (thingData != null) {
                    Integer num = thingData.value;
                    if (num != null) {
                        if (num == null || num.intValue() != 100) {
                            Integer num2 = thingData.value;
                            if (num2 == null || num2.intValue() != 0) {
                                Integer num3 = thingData.value;
                                if (num3 != null && num3.intValue() == 50) {
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
                    if (str2 != null) {
                        if (!Intrinsics.areEqual(str2, thingData.buttonType)) {
                            str2 = "none";
                            if (z10) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        str2 = thingData.buttonType;
                    }
                }
            }
            if (z10) {
                dataGroupThing.state = State.mixed;
            } else if (z12) {
                dataGroupThing.state = State.on;
            } else if (z7) {
                dataGroupThing.state = State.off;
            } else {
                dataGroupThing.state = State.stop;
            }
            if (str2 != null) {
                str = str2;
            }
            dataGroupThing.buttonType = str;
        }
    }

    public final boolean updateThingButtonType(@Nullable String str, @NotNull String buttonType) {
        Intrinsics.checkNotNullParameter(buttonType, "buttonType");
        synchronized (MyMasterData.class) {
            DataMyThing thingData = getThingData(str);
            if (thingData == null || Intrinsics.areEqual(thingData.buttonType, buttonType)) {
                Unit unit = Unit.INSTANCE;
                return false;
            }
            thingData.buttonType = buttonType;
            return true;
        }
    }

    public final boolean updateThingName(@Nullable String str, @NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        synchronized (MyMasterData.class) {
            DataMyThing thingData = getThingData(str);
            if (thingData == null || Intrinsics.areEqual(thingData.name, name)) {
                Unit unit = Unit.INSTANCE;
                return false;
            }
            thingData.name = name;
            return true;
        }
    }
}