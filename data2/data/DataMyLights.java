package com.air.advantage.data;

import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.air.advantage.libraryairconlightjson.LightState;
import com.air.advantage.uart.HandlerLights;
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
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.t */
/* loaded from: classes.dex */
public final class DataMyLights {

    @NotNull
    public static final String DEFAULT_GROUP = "g0";
    public static final int MAX_NO_OF_ALARMS = 4;

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = DataMyLights.class.getSimpleName();

    @Nullable
    @SerializedName("lights")
    @JvmField
    public TreeMap<String, DataLight> lights = new TreeMap<>();

    @JsonExporter(export = false)
    @NotNull
    @SerializedName("backupLights")
    @JvmField
    public ArrayList<b> backupLights = new ArrayList<>();

    @Nullable
    @SerializedName("groups")
    @JvmField
    public TreeMap<String, DataGroup> groups = new TreeMap<>();

    @Nullable
    @SerializedName("groupsOrder")
    @JvmField
    public ArrayList<String> groupsOrder = new ArrayList<>();

    @JsonExporter(export = false)
    @NotNull
    @SerializedName("scenes")
    @JvmField
    public TreeMap<String, DataScene> scenes = new TreeMap<>();

    @JsonExporter(export = false)
    @NotNull
    @SerializedName("scenesOrder")
    @JvmField
    public ArrayList<String> scenesOrder = new ArrayList<>();

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("alarms")
    @JvmField
    public TreeMap<String, DataAlarm> alarms = new TreeMap<>();

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("alarmsOrder")
    @JvmField
    public ArrayList<String> alarmsOrder = new ArrayList<>();

    @Nullable
    @SerializedName("system")
    @JvmField
    public DataLightsSystem system = new DataLightsSystem();

    /* renamed from: com.air.advantage.data.t$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.t.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* renamed from: com.air.advantage.data.t$b */
    public static final class b {

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

    private final DataGroup getGroupFromLightId(String str) {
        TreeMap<String, DataGroup> treeMap = this.groups;
        Intrinsics.checkNotNull(treeMap);
        for (DataGroup dataGroup : treeMap.values()) {
            Intrinsics.checkNotNull(dataGroup);
            Iterator<String> it = dataGroup.lightsOrder.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(it.next(), str)) {
                    return dataGroup;
                }
            }
        }
        return null;
    }

    private final void removeNonAALightsForBackup() {
        TreeMap<String, DataGroup> treeMap = this.groups;
        Intrinsics.checkNotNull(treeMap);
        for (DataGroup dataGroup : treeMap.values()) {
            ArrayList arrayList = new ArrayList();
            Intrinsics.checkNotNull(dataGroup);
            Iterator<String> it = dataGroup.lightsOrder.iterator();
            while (it.hasNext()) {
                String next = it.next();
                Intrinsics.checkNotNull(next);
                if (next.length() != 7 || !new Regex(HandlerLights.hexRegx).matches(next)) {
                    arrayList.add(next);
                }
            }
            if (arrayList.size() > 0) {
                dataGroup.lightsOrder.removeAll(arrayList);
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    String str = (String) it2.next();
                    TreeMap<String, DataLight> treeMap2 = this.lights;
                    Intrinsics.checkNotNull(treeMap2);
                    treeMap2.remove(str);
                }
            }
        }
    }

    public final boolean addAlarm(@NotNull DataAlarm dataAlarm) {
        Intrinsics.checkNotNullParameter(dataAlarm, "dataAlarm");
        DataAlarm alarm = getAlarm(dataAlarm.id);
        if (alarm != null) {
            alarm.name = dataAlarm.name;
            alarm.activeDays = dataAlarm.activeDays;
            alarm.timerEnabled = dataAlarm.timerEnabled;
            alarm.startTime = dataAlarm.startTime;
            alarm.lights = dataAlarm.lights;
            alarm.canMessages = dataAlarm.canMessages;
            return true;
        }
        TreeMap<String, DataAlarm> treeMap = this.alarms;
        Intrinsics.checkNotNull(treeMap);
        if (treeMap.size() >= 4) {
            return false;
        }
        TreeMap<String, DataAlarm> treeMap2 = this.alarms;
        Intrinsics.checkNotNull(treeMap2);
        treeMap2.put(dataAlarm.id, dataAlarm);
        ArrayList<String> arrayList = this.alarmsOrder;
        Intrinsics.checkNotNull(arrayList);
        arrayList.add(dataAlarm.id);
        return true;
    }

    public final void clearDataForBackup() {
        TreeMap<String, DataAlarm> treeMap = this.alarms;
        Intrinsics.checkNotNull(treeMap);
        treeMap.clear();
        ArrayList<String> arrayList = this.alarmsOrder;
        Intrinsics.checkNotNull(arrayList);
        arrayList.clear();
        this.backupLights.clear();
        TreeMap<String, DataGroup> treeMap2 = this.groups;
        if (treeMap2 != null) {
            Intrinsics.checkNotNull(treeMap2);
            for (DataGroup dataGroup : treeMap2.values()) {
                Intrinsics.checkNotNull(dataGroup);
                dataGroup.clearDataForBackup();
            }
        }
        removeNonAALightsForBackup();
        TreeMap<String, DataLight> treeMap3 = this.lights;
        if (treeMap3 != null) {
            Intrinsics.checkNotNull(treeMap3);
            for (DataLight dataLight : treeMap3.values()) {
                Intrinsics.checkNotNull(dataLight);
                dataLight.clearDataForBackup();
            }
        }
        this.scenes.clear();
        this.scenesOrder.clear();
        DataLightsSystem dataLightsSystem = this.system;
        Intrinsics.checkNotNull(dataLightsSystem);
        dataLightsSystem.clearDataForBackup();
    }

    @Nullable
    public final DataAlarm getAlarm(@Nullable String str) {
        TreeMap<String, DataAlarm> treeMap = this.alarms;
        Intrinsics.checkNotNull(treeMap);
        for (DataAlarm dataAlarm : treeMap.values()) {
            Intrinsics.checkNotNull(dataAlarm);
            if (Intrinsics.areEqual(dataAlarm.id, str)) {
                return dataAlarm;
            }
        }
        return null;
    }

    @Nullable
    public final DataGroup getDataGroup(@Nullable String str) {
        if (str == null) {
            return null;
        }
        TreeMap<String, DataGroup> treeMap = this.groups;
        Intrinsics.checkNotNull(treeMap);
        return treeMap.get(str);
    }

    @Nullable
    public final String getGroupId(@NotNull String lightId) {
        Intrinsics.checkNotNullParameter(lightId, "lightId");
        DataGroup groupFromLightId = getGroupFromLightId(lightId);
        if (groupFromLightId != null) {
            return groupFromLightId.id;
        }
        return null;
    }

    @Nullable
    public final DataLight getLightData(@Nullable String str) {
        if (str == null) {
            return null;
        }
        TreeMap<String, DataLight> treeMap = this.lights;
        Intrinsics.checkNotNull(treeMap);
        return treeMap.get(str);
    }

    public final int getLightPosition(@NotNull String lightId) {
        Intrinsics.checkNotNullParameter(lightId, "lightId");
        TreeMap<String, DataGroup> treeMap = this.groups;
        Intrinsics.checkNotNull(treeMap);
        for (DataGroup dataGroup : treeMap.values()) {
            Intrinsics.checkNotNull(dataGroup);
            Iterator<String> it = dataGroup.lightsOrder.iterator();
            int i10 = 0;
            while (it.hasNext()) {
                if (Intrinsics.areEqual(it.next(), lightId)) {
                    return i10;
                }
                i10++;
            }
        }
        return -1;
    }

    public final int numberGroups() {
        TreeMap<String, DataGroup> treeMap = this.groups;
        Intrinsics.checkNotNull(treeMap);
        return treeMap.size();
    }

    public final int numberLights() {
        TreeMap<String, DataLight> treeMap = this.lights;
        Intrinsics.checkNotNull(treeMap);
        return treeMap.size();
    }

    public final boolean removeAlarm(@Nullable String str) {
        TreeMap<String, DataAlarm> treeMap = this.alarms;
        Intrinsics.checkNotNull(treeMap);
        return treeMap.remove(str) != null;
    }

    public final void removeLight(@NotNull String lightId) {
        ArrayList<String> arrayList;
        Intrinsics.checkNotNullParameter(lightId, "lightId");
        DataGroup groupFromLightId = getGroupFromLightId(lightId);
        if (groupFromLightId != null && (arrayList = groupFromLightId.lightsOrder) != null) {
            arrayList.remove(lightId);
        }
        TreeMap<String, DataLight> treeMap = this.lights;
        Intrinsics.checkNotNull(treeMap);
        treeMap.remove(lightId);
    }

    public final void updateGroupStates() {
        boolean z7;
        LightState lightState;
        LightState lightState2;
        LightState lightState3;
        TreeMap<String, DataGroup> treeMap = this.groups;
        Intrinsics.checkNotNull(treeMap);
        for (DataGroup dataGroup : treeMap.values()) {
            Intrinsics.checkNotNull(dataGroup);
            Iterator<String> it = dataGroup.lightsOrder.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z7 = false;
                    break;
                }
                DataLight lightData = getLightData(it.next());
                if (lightData != null && (lightState3 = lightData.state) != null && lightState3 == LightState.on) {
                    z7 = true;
                    break;
                }
            }
            LightState lightState4 = dataGroup.state;
            if (lightState4 != null) {
                if (z7 && lightState4 != (lightState2 = LightState.on)) {
                    dataGroup.state = lightState2;
                } else if (!z7 && lightState4 != (lightState = LightState.off)) {
                    dataGroup.state = lightState;
                }
            } else if (z7) {
                dataGroup.state = LightState.on;
            } else {
                dataGroup.state = LightState.off;
            }
        }
    }

    public final boolean updateLightName(@Nullable String str, @NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        synchronized (MyMasterData.class) {
            DataLight lightData = getLightData(str);
            if (lightData == null || Intrinsics.areEqual(lightData.name, name)) {
                Unit unit = Unit.INSTANCE;
                return false;
            }
            lightData.name = name;
            return true;
        }
    }
}