package com.air.advantage.data;

import androidx.browser.customtabs.CustomTabsCallback;
import com.air.advantage.data.DataAirconSystem;
import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.air.advantage.uart.Xml2JsonFunctions;
import com.bosma.blemodule.common.CommandKey;
import com.google.firebase.database.Exclude;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import kotlin.Unit;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.data.h1 */
/* loaded from: classes.dex */
public final class DataMaster {

    @Exclude
    @JvmField
    public transient boolean multipleAirconDetectedOnOneAirconOnlySystem;

    @Nullable
    @SerializedName("myView")
    @JvmField
    public DataMyView myView;

    @Exclude
    @JvmField
    public transient boolean oneAirconOnly;

    @Nullable
    @SerializedName(CustomTabsCallback.ONLINE_EXTRAS_KEY)
    private Boolean online;

    @Exclude
    @Nullable
    @JvmField
    public transient String wifiIpAddress;

    @SerializedName("aircons")
    @NotNull
    @JvmField
    public final TreeMap<String, DataAirconSystem> aircons = new TreeMap<>();

    @SerializedName("snapshots")
    @NotNull
    @JvmField
    public final TreeMap<String, SnapShot> snapshots = new TreeMap<>(new SortOrder());

    @SerializedName("system")
    @NotNull
    @JvmField
    public final DataSystem system = new DataSystem();

    @SerializedName("myLights")
    @NotNull
    @JvmField
    public DataMyLights myLights = new DataMyLights();

    @SerializedName("myMonitors")
    @NotNull
    @JvmField
    public DataMyMonitors myMonitors = new DataMyMonitors();

    @SerializedName("myScenes")
    @NotNull
    @JvmField
    public DataMyScene myScenes = new DataMyScene();

    @SerializedName("myThings")
    @NotNull
    @JvmField
    public DataThings myThings = new DataThings();

    @SerializedName("mySensors")
    @NotNull
    @JvmField
    public DataSensorsAllImport mySensors = new DataSensorsAllImport();

    @Nullable
    @SerializedName("myLocks")
    @JvmField
    public DataLocksAllImport myLocks = new DataLocksAllImport();

    @SerializedName("myAddOns")
    @NotNull
    @JvmField
    public DataAddOnsAllImport myAddOns = new DataAddOnsAllImport();

    @SerializedName("myGarageRFControllers")
    @NotNull
    @JvmField
    public DataMyGarageController myGarageRFControllers = new DataMyGarageController();

    @Exclude
    @NotNull
    @JvmField
    public final transient ArrayList<String> listOfRM2WithInvalidDipSetting = new ArrayList<>();

    @Exclude
    @NotNull
    @JvmField
    public final transient ArrayList<String> listOfNonConfiguredRM2 = new ArrayList<>();

    @Exclude
    @NotNull
    @JvmField
    public final transient ArrayList<String> listOfDMWithInvalidDipSetting = new ArrayList<>();

    @Exclude
    @NotNull
    @JvmField
    public final transient ArrayList<String> listOfNonConfiguredDM = new ArrayList<>();

    @Exclude
    @NotNull
    @JvmField
    public final transient HashMap<String, Long> moduleExpiryTime = new HashMap<>();

    /* renamed from: com.air.advantage.data.h1$a */
    public static final class a implements ExclusionStrategy {
        a() {
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipClass(@Nullable Class<?> cls) {
            return false;
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipField(@NotNull FieldAttributes fieldAttributes) {
            Intrinsics.checkNotNullParameter(fieldAttributes, "fieldAttributes");
            JsonExporter jsonExporter = (JsonExporter) fieldAttributes.getAnnotation(JsonExporter.class);
            return (jsonExporter == null || jsonExporter.export()) ? false : true;
        }
    }

    private final int getMaxNumberOfAircon() {
        return 8;
    }

    /* renamed from: hasMinimumDataToBroadcastToUserInterface */
    private final boolean a() {
        return this.system.mid != null;
    }

    private final void removeInactiveAircon() {
        DataSystem dataSystem = this.system;
        if (dataSystem.hasAircons == null) {
            dataSystem.hasAircons = Boolean.FALSE;
        }
        if (!this.oneAirconOnly) {
            dataSystem.noOfAircons = Integer.valueOf(this.aircons.size());
            return;
        }
        int maxNumberOfAircon = getMaxNumberOfAircon();
        int i10 = 2;
        if (2 <= maxNumberOfAircon) {
            while (true) {
                this.aircons.remove(CommandKey.CMD_REQUEST_AEGIS_LOCAL_LOCK_THE_LOCK_V2 + i10);
                if (i10 == maxNumberOfAircon) {
                    break;
                } else {
                    i10++;
                }
            }
        }
        this.system.noOfAircons = 1;
    }

    public static /* synthetic */ boolean update$default(DataMaster dataMaster, DataMaster dataMaster2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 4) != 0) {
            z7 = false;
        }
        return dataMaster.update(dataMaster2, dataManager, z7);
    }

    public final void clear() {
        this.aircons.clear();
        this.snapshots.clear();
        this.system.clear();
        this.myLights = new DataMyLights();
        this.myMonitors = new DataMyMonitors();
        this.myScenes = new DataMyScene();
        this.myThings = new DataThings();
        this.mySensors = new DataSensorsAllImport();
        this.myAddOns = new DataAddOnsAllImport();
        this.oneAirconOnly = false;
        this.multipleAirconDetectedOnOneAirconOnlySystem = false;
        this.listOfRM2WithInvalidDipSetting.clear();
        this.listOfNonConfiguredRM2.clear();
        this.listOfDMWithInvalidDipSetting.clear();
        this.listOfNonConfiguredDM.clear();
        this.moduleExpiryTime.clear();
        this.wifiIpAddress = null;
        this.online = null;
        this.myView = null;
    }

    public final void clearDataForBackup() {
        for (DataAirconSystem dataAirconSystem : this.aircons.values()) {
            Intrinsics.checkNotNull(dataAirconSystem);
            dataAirconSystem.clearDataForBackup();
        }
        this.myAddOns.clearDataForBackup();
        this.myLights.clearDataForBackup();
        this.myMonitors = new DataMyMonitors();
        this.myScenes = new DataMyScene();
        this.mySensors = new DataSensorsAllImport();
        this.myLocks = new DataLocksAllImport();
        this.myThings.clearDataForBackup();
        this.system.clearDataForBackup();
        this.online = null;
    }

    public final boolean containsSplitAircon() {
        for (DataAirconSystem dataAirconSystem : this.aircons.values()) {
            Intrinsics.checkNotNull(dataAirconSystem);
            Integer num = dataAirconSystem.info.cbType;
            if (num != null) {
                if (num != null && num.intValue() == 4) {
                    return true;
                }
                Integer num2 = dataAirconSystem.info.cbType;
                if (num2 != null && num2.intValue() == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    @NotNull
    public final DataMaster copy() {
        DataMaster dataMaster = new DataMaster();
        update$default(dataMaster, this, null, false, 4, null);
        return dataMaster;
    }

    @NotNull
    public final String generateJSONStringForExport() {
        String json = new GsonBuilder().addSerializationExclusionStrategy(new a()).create().toJson(this);
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        return json;
    }

    @Nullable
    public final DataAirconSystem getAirconByUid(@Nullable String str) {
        if (str == null) {
            throw new NullPointerException("Warning trying to get a DataAircon with a null uid");
        }
        if (Intrinsics.areEqual(str, Xml2JsonFunctions.DEFAULT_UID)) {
            Timber.forest.d("Warning trying to get a DataAircon with blank mac (This should happen during motor calibration)", new Object[0]);
            return null;
        }
        for (DataAirconSystem dataAirconSystem : this.aircons.values()) {
            Intrinsics.checkNotNull(dataAirconSystem);
            String str2 = dataAirconSystem.info.uid;
            if (str2 != null && Intrinsics.areEqual(str2, str)) {
                return dataAirconSystem;
            }
        }
        return null;
    }

    @Nullable
    public final String getMasterDataInMemoryAsJson() {
        if (!a()) {
            return null;
        }
        removeInactiveAircon();
        return generateJSONStringForExport();
    }

    @Nullable
    public final String getMasterDataInMemoryAsJsonWithoutLocks() {
        if (!a()) {
            return null;
        }
        removeInactiveAircon();
        DataMaster dataMaster = new DataMaster();
        update$default(dataMaster, this, null, false, 4, null);
        dataMaster.myLocks = null;
        return dataMaster.generateJSONStringForExport();
    }

    @Nullable
    public final Boolean getOnline() {
        return this.online;
    }

    public final void setOnline(@Nullable Boolean bool) {
        this.online = bool;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0076  */
    // TODO: Check Code
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void sortAircons(boolean z7) {
        Integer num;
        String str = this.system.mid;
        if (str != null) {
            Intrinsics.checkNotNull(str);
            num = Integer.valueOf(str.length());
        } else {
            num = null;
        }
        Iterator it = new ArrayList(this.aircons.keySet()).iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            DataAirconSystem dataAirconSystem = this.aircons.get(str2);
            if (dataAirconSystem == null) {
                Timber.forest.d("dataAircon is null", new Object[0]);
            } else {
                if (num != null) {
                    String str3 = dataAirconSystem.info.uid;
                    Intrinsics.checkNotNull(str3);
                    if (str3.length() != num.intValue()) {
                        this.aircons.remove(str2);
                    }
                }
                if (!Intrinsics.areEqual(str2, dataAirconSystem.info.uid)) {
                    Boolean bool = dataAirconSystem.info.enabled;
                    if (bool != null) {
                        Intrinsics.checkNotNull(bool);
                        if (bool.booleanValue()) {
                            this.aircons.put(dataAirconSystem.info.uid, dataAirconSystem);
                        }
                        this.aircons.remove(str2);
                    }
                } else if (z7) {
                    dataAirconSystem.info.uid = null;
                }
            }
        }
        int i10 = 1;
        int maxNumberOfAircon = this.oneAirconOnly ? 1 : getMaxNumberOfAircon();
        Iterator it2 = new ArrayList(this.aircons.keySet()).iterator();
        while (it2.hasNext()) {
            String str4 = (String) it2.next();
            if (i10 <= maxNumberOfAircon) {
                DataAirconSystem dataAirconSystem2 = this.aircons.get(str4);
                this.aircons.put(CommandKey.CMD_REQUEST_AEGIS_LOCAL_LOCK_THE_LOCK_V2 + i10, dataAirconSystem2);
            }
            this.aircons.remove(str4);
            i10++;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@Nullable DataMaster dataMaster, @Nullable DataManager dataManager) {
        return update$default(this, dataMaster, dataManager, false, 4, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    /* JADX WARN: Removed duplicated region for block: B:565:0x08cb  */
    @JvmOverloads
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean update(@Nullable DataMaster dataMaster, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        ArrayList<String> arrayList;
        ArrayList<String> arrayList2;
        ArrayList<String> arrayList3;
        ArrayList<String> arrayList4;
        ArrayList<String> arrayList5;
        ArrayList<String> arrayList6;
        ArrayList<String> arrayList7;
        Boolean bool;
        String str;
        String str2;
        String str3;
        if (dataMaster == null) {
            return false;
        }
        if (!Intrinsics.areEqual(this, dataMaster)) {
            HashMap hashMap = new HashMap();
            for (String str4 : this.aircons.keySet()) {
                DataAirconSystem dataAirconSystem = this.aircons.get(str4);
                if (dataAirconSystem != null && (str3 = dataAirconSystem.info.uid) != null) {
                    hashMap.put(str3, str4);
                }
            }
            Iterator it = new ArrayList(dataMaster.aircons.keySet()).iterator();
            while (it.hasNext()) {
                DataAirconSystem dataAirconSystem2 = dataMaster.aircons.get((String) it.next());
                if (dataAirconSystem2 != null && (str2 = dataAirconSystem2.info.uid) != null && !hashMap.containsKey(str2)) {
                    this.aircons.put(dataAirconSystem2.info.uid, new DataAirconSystem(dataAirconSystem2.info.uid));
                }
            }
            sortAircons(true);
            for (String str5 : this.aircons.keySet()) {
                DataAirconSystem dataAirconSystem3 = this.aircons.get(str5);
                if (dataAirconSystem3 != null && (str = dataAirconSystem3.info.uid) != null && !Intrinsics.areEqual(hashMap.get(str), str5)) {
                    this.aircons.put(str5, new DataAirconSystem());
                }
            }
        }
        boolean z11 = false;
        for (String str6 : dataMaster.aircons.keySet()) {
            DataAirconSystem dataAirconSystem4 = dataMaster.aircons.get(str6);
            DataAirconSystem dataAirconSystem5 = this.aircons.get(str6);
            if (dataAirconSystem5 == null) {
                dataAirconSystem5 = new DataAirconSystem();
                this.aircons.put(str6, dataAirconSystem5);
            }
            if (dataAirconSystem5.update(str6, dataAirconSystem4, dataManager, null, z7)) {
                z11 = true;
            }
        }
        Integer num = dataMaster.system.noOfAircons;
        int size = this.aircons.size();
        if (num != null && size > num.intValue()) {
            if (dataManager != null) {
                dataManager.updateAirconPath();
                Unit unit = Unit.INSTANCE;
            }
            int intValue = num.intValue() + 1;
            if (intValue <= size) {
                while (true) {
                    TreeMap<String, DataAirconSystem> treeMap = this.aircons;
                    DataAirconSystem.Companion companion = DataAirconSystem.Companion;
                    treeMap.remove(companion.generateCommandKey(intValue));
                    if (dataManager != null) {
                        dataManager.add(companion.generateCommandKey(intValue), null);
                        Unit unit2 = Unit.INSTANCE;
                    }
                    if (intValue == size) {
                        break;
                    }
                    intValue++;
                }
                z11 = true;
            }
        }
        Boolean bool2 = dataMaster.online;
        if (bool2 != null && ((bool = this.online) == null || !Intrinsics.areEqual(bool, bool2))) {
            this.online = dataMaster.online;
            z11 = true;
        }
        if (dataManager != null) {
            dataManager.updateSystemPath();
            Unit unit3 = Unit.INSTANCE;
        }
        if (this.system.update(dataMaster.system, dataManager, z7)) {
            z11 = true;
        }
        for (String str7 : dataMaster.snapshots.keySet()) {
            SnapShot snapShot = dataMaster.snapshots.get(str7);
            if (snapShot != null) {
                if (dataManager != null) {
                    dataManager.updateSnapshotPath(str7);
                    Unit unit4 = Unit.INSTANCE;
                }
                SnapShot snapShot2 = this.snapshots.get(str7);
                if (snapShot2 == null) {
                    snapShot2 = new SnapShot();
                    this.snapshots.put(str7, snapShot2);
                }
                if (snapShot2.update(snapShot, dataManager, z7)) {
                    z11 = true;
                }
            }
        }
        ArrayList arrayList8 = new ArrayList(this.snapshots.keySet());
        Set<String> keySet = dataMaster.snapshots.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet, "<get-keys>(...)");
        arrayList8.removeAll(keySet);
        Iterator it2 = arrayList8.iterator();
        while (it2.hasNext()) {
            String str8 = (String) it2.next();
            this.snapshots.remove(str8);
            if (dataManager != null) {
                dataManager.updateSnapshotPath("");
                dataManager.add(str8, null);
            }
        }
        TreeMap<String, DataLight> treeMap2 = dataMaster.myLights.lights;
        Intrinsics.checkNotNull(treeMap2);
        for (String str9 : treeMap2.keySet()) {
            TreeMap<String, DataLight> treeMap3 = dataMaster.myLights.lights;
            Intrinsics.checkNotNull(treeMap3);
            DataLight dataLight = treeMap3.get(str9);
            if (dataLight != null) {
                if (dataManager != null) {
                    dataManager.updateLightPath(str9);
                    Unit unit5 = Unit.INSTANCE;
                }
                TreeMap<String, DataLight> treeMap4 = this.myLights.lights;
                Intrinsics.checkNotNull(treeMap4);
                DataLight dataLight2 = treeMap4.get(str9);
                if (dataLight2 == null) {
                    dataLight2 = new DataLight();
                    TreeMap<String, DataLight> treeMap5 = this.myLights.lights;
                    Intrinsics.checkNotNull(treeMap5);
                    treeMap5.put(str9, dataLight2);
                }
                if (dataLight2.update(null, dataLight, dataManager, z7)) {
                    z11 = true;
                }
            }
        }
        TreeMap<String, DataLight> treeMap6 = this.myLights.lights;
        Intrinsics.checkNotNull(treeMap6);
        ArrayList arrayList9 = new ArrayList(treeMap6.keySet());
        TreeMap<String, DataLight> treeMap7 = dataMaster.myLights.lights;
        Intrinsics.checkNotNull(treeMap7);
        Set<String> keySet2 = treeMap7.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet2, "<get-keys>(...)");
        arrayList9.removeAll(keySet2);
        Iterator it3 = arrayList9.iterator();
        while (it3.hasNext()) {
            String str10 = (String) it3.next();
            TreeMap<String, DataLight> treeMap8 = this.myLights.lights;
            Intrinsics.checkNotNull(treeMap8);
            treeMap8.remove(str10);
            if (dataManager != null) {
                dataManager.updateLightPath("");
                dataManager.add(str10, null);
            }
        }
        TreeMap<String, DataGroup> treeMap9 = dataMaster.myLights.groups;
        Intrinsics.checkNotNull(treeMap9);
        for (String str11 : treeMap9.keySet()) {
            TreeMap<String, DataGroup> treeMap10 = dataMaster.myLights.groups;
            Intrinsics.checkNotNull(treeMap10);
            DataGroup dataGroup = treeMap10.get(str11);
            if (dataGroup != null) {
                if (dataManager != null) {
                    dataManager.updateGroupPath(str11);
                    Unit unit6 = Unit.INSTANCE;
                }
                DataGroup dataGroup2 = this.myLights.getDataGroup(str11);
                if (dataGroup2 == null) {
                    dataGroup2 = new DataGroup(str11);
                    TreeMap<String, DataGroup> treeMap11 = this.myLights.groups;
                    Intrinsics.checkNotNull(treeMap11);
                    treeMap11.put(str11, dataGroup2);
                }
                if (dataGroup2.update(dataGroup, dataManager, z7)) {
                    z11 = true;
                }
            }
        }
        TreeMap<String, DataGroup> treeMap12 = this.myLights.groups;
        Intrinsics.checkNotNull(treeMap12);
        ArrayList arrayList10 = new ArrayList(treeMap12.keySet());
        TreeMap<String, DataGroup> treeMap13 = dataMaster.myLights.groups;
        Intrinsics.checkNotNull(treeMap13);
        Set<String> keySet3 = treeMap13.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet3, "<get-keys>(...)");
        arrayList10.removeAll(keySet3);
        Iterator it4 = arrayList10.iterator();
        while (it4.hasNext()) {
            String str12 = (String) it4.next();
            TreeMap<String, DataGroup> treeMap14 = this.myLights.groups;
            Intrinsics.checkNotNull(treeMap14);
            treeMap14.remove(str12);
            if (dataManager != null) {
                dataManager.updateGroupPath("");
                dataManager.add(str12, null);
            }
        }
        ArrayList<String> arrayList11 = dataMaster.myLights.groupsOrder;
        if (arrayList11 != null && ((arrayList7 = this.myLights.groupsOrder) == null || !Intrinsics.areEqual(arrayList7, arrayList11))) {
            this.myLights.groupsOrder = dataMaster.myLights.groupsOrder;
            if (dataManager != null) {
                dataManager.updateLightOrderPath();
                dataManager.add("groupsOrder", this.myLights.groupsOrder);
            }
            z11 = true;
        }
        TreeMap<String, DataScene> treeMap15 = dataMaster.myScenes.scenes;
        if (treeMap15 != null) {
            Intrinsics.checkNotNull(treeMap15);
            for (String str13 : treeMap15.keySet()) {
                TreeMap<String, DataScene> treeMap16 = dataMaster.myScenes.scenes;
                Intrinsics.checkNotNull(treeMap16);
                DataScene dataScene = treeMap16.get(str13);
                if (dataScene != null && !Intrinsics.areEqual(dataScene.id, "s0")) {
                    if (dataManager != null) {
                        dataManager.updateScenePath(str13);
                        Unit unit7 = Unit.INSTANCE;
                    }
                    DataScene scene = this.myScenes.getScene(str13);
                    if (scene == null) {
                        scene = new DataScene();
                        TreeMap<String, DataScene> treeMap17 = this.myScenes.scenes;
                        Intrinsics.checkNotNull(treeMap17);
                        treeMap17.put(str13, scene);
                    }
                    if (scene.update(null, dataScene, dataManager, z7)) {
                        z11 = true;
                    }
                }
            }
            TreeMap<String, DataScene> treeMap18 = this.myScenes.scenes;
            Intrinsics.checkNotNull(treeMap18);
            ArrayList arrayList12 = new ArrayList(treeMap18.keySet());
            TreeMap<String, DataScene> treeMap19 = dataMaster.myScenes.scenes;
            Intrinsics.checkNotNull(treeMap19);
            Set<String> keySet4 = treeMap19.keySet();
            Intrinsics.checkNotNullExpressionValue(keySet4, "<get-keys>(...)");
            arrayList12.removeAll(keySet4);
            Iterator it5 = arrayList12.iterator();
            while (it5.hasNext()) {
                String str14 = (String) it5.next();
                TreeMap<String, DataScene> treeMap20 = this.myScenes.scenes;
                Intrinsics.checkNotNull(treeMap20);
                treeMap20.remove(str14);
                if (dataManager != null) {
                    dataManager.updateScenePath("");
                    dataManager.add(str14, null);
                }
            }
            ArrayList<String> arrayList13 = dataMaster.myScenes.scenesOrder;
            if (arrayList13 != null && ((arrayList6 = this.myScenes.scenesOrder) == null || !Intrinsics.areEqual(arrayList6, arrayList13))) {
                this.myScenes.scenesOrder = dataMaster.myScenes.scenesOrder;
                if (dataManager != null) {
                    dataManager.updateScenesOrderPath();
                    dataManager.add("scenesOrder", this.myScenes.scenesOrder);
                }
                z11 = true;
            }
        }
        HashMap<String, DataMonitor> hashMap2 = dataMaster.myMonitors.monitors;
        if (hashMap2 != null) {
            Intrinsics.checkNotNull(hashMap2);
            for (String str15 : hashMap2.keySet()) {
                HashMap<String, DataMonitor> hashMap3 = dataMaster.myMonitors.monitors;
                Intrinsics.checkNotNull(hashMap3);
                DataMonitor dataMonitor = hashMap3.get(str15);
                if (dataMonitor != null) {
                    if (dataManager != null) {
                        dataManager.updateMonitorPath(str15);
                        Unit unit8 = Unit.INSTANCE;
                    }
                    DataMonitor monitor = this.myMonitors.getMonitor(str15);
                    if (monitor == null) {
                        monitor = new DataMonitor();
                        HashMap<String, DataMonitor> hashMap4 = this.myMonitors.monitors;
                        Intrinsics.checkNotNull(hashMap4);
                        hashMap4.put(str15, monitor);
                    }
                    if (monitor.update(null, dataMonitor, dataManager, z7)) {
                        z11 = true;
                    }
                }
            }
            HashMap<String, DataMonitor> hashMap5 = this.myMonitors.monitors;
            Intrinsics.checkNotNull(hashMap5);
            ArrayList arrayList14 = new ArrayList(hashMap5.keySet());
            HashMap<String, DataMonitor> hashMap6 = dataMaster.myMonitors.monitors;
            Intrinsics.checkNotNull(hashMap6);
            Set<String> keySet5 = hashMap6.keySet();
            Intrinsics.checkNotNullExpressionValue(keySet5, "<get-keys>(...)");
            arrayList14.removeAll(keySet5);
            Iterator it6 = arrayList14.iterator();
            while (it6.hasNext()) {
                String str16 = (String) it6.next();
                HashMap<String, DataMonitor> hashMap7 = this.myMonitors.monitors;
                Intrinsics.checkNotNull(hashMap7);
                hashMap7.remove(str16);
                if (dataManager != null) {
                    dataManager.updateMonitorPath("");
                    dataManager.add(str16, null);
                }
            }
            ArrayList<String> arrayList15 = dataMaster.myMonitors.monitorsOrder;
            if (arrayList15 != null && ((arrayList5 = this.myMonitors.monitorsOrder) == null || !Intrinsics.areEqual(arrayList5, arrayList15))) {
                this.myMonitors.monitorsOrder = dataMaster.myMonitors.monitorsOrder;
                if (dataManager != null) {
                    dataManager.updateMonitorsOrderPath();
                    dataManager.add("monitorsOrder", this.myMonitors.monitorsOrder);
                }
                z11 = true;
            }
        }
        HashMap<String, DataSensor> hashMap8 = dataMaster.mySensors.sensors;
        if (hashMap8 != null) {
            Intrinsics.checkNotNull(hashMap8);
            for (String str17 : hashMap8.keySet()) {
                HashMap<String, DataSensor> hashMap9 = dataMaster.mySensors.sensors;
                Intrinsics.checkNotNull(hashMap9);
                DataSensor dataSensor = hashMap9.get(str17);
                if (dataSensor != null) {
                    if (dataManager != null) {
                        dataManager.updateSensorPath(str17);
                        Unit unit9 = Unit.INSTANCE;
                    }
                    DataSensor sensor = this.mySensors.getSensor(str17);
                    if (sensor == null) {
                        sensor = new DataSensor();
                        HashMap<String, DataSensor> hashMap10 = this.mySensors.sensors;
                        Intrinsics.checkNotNull(hashMap10);
                        hashMap10.put(str17, sensor);
                    }
                    if (sensor.update(dataSensor, dataManager, z7)) {
                        z11 = true;
                    }
                }
            }
            HashMap<String, DataSensor> hashMap11 = this.mySensors.sensors;
            Intrinsics.checkNotNull(hashMap11);
            ArrayList arrayList16 = new ArrayList(hashMap11.keySet());
            HashMap<String, DataSensor> hashMap12 = dataMaster.mySensors.sensors;
            Intrinsics.checkNotNull(hashMap12);
            Set<String> keySet6 = hashMap12.keySet();
            Intrinsics.checkNotNullExpressionValue(keySet6, "<get-keys>(...)");
            arrayList16.removeAll(keySet6);
            Iterator it7 = arrayList16.iterator();
            while (it7.hasNext()) {
                String str18 = (String) it7.next();
                HashMap<String, DataSensor> hashMap13 = this.mySensors.sensors;
                Intrinsics.checkNotNull(hashMap13);
                hashMap13.remove(str18);
                if (dataManager != null) {
                    dataManager.updateSensorPath("");
                    dataManager.add(str18, null);
                }
            }
            ArrayList<String> arrayList17 = dataMaster.mySensors.sensorsOrder;
            if (arrayList17 != null && ((arrayList4 = this.mySensors.sensorsOrder) == null || !Intrinsics.areEqual(arrayList4, arrayList17))) {
                this.mySensors.sensorsOrder = dataMaster.mySensors.sensorsOrder;
                if (dataManager != null) {
                    dataManager.updateSensorsOrderPath();
                    dataManager.add("sensorsOrder", this.mySensors.sensorsOrder);
                }
                z11 = true;
            }
        }
        HashMap<String, DataHueBridges> hashMap14 = dataMaster.myAddOns.hueBridges;
        if (hashMap14 != null) {
            Intrinsics.checkNotNull(hashMap14);
            for (String str19 : hashMap14.keySet()) {
                HashMap<String, DataHueBridges> hashMap15 = dataMaster.myAddOns.hueBridges;
                Intrinsics.checkNotNull(hashMap15);
                DataHueBridges dataHueBridges = hashMap15.get(str19);
                if (dataHueBridges != null) {
                    if (dataManager != null) {
                        dataManager.updateHueBridgesPath(str19);
                        Unit unit10 = Unit.INSTANCE;
                    }
                    DataHueBridges hueBridge = this.myAddOns.getHueBridge(str19);
                    if (hueBridge == null) {
                        hueBridge = new DataHueBridges();
                        HashMap<String, DataHueBridges> hashMap16 = this.myAddOns.hueBridges;
                        Intrinsics.checkNotNull(hashMap16);
                        hashMap16.put(str19, hueBridge);
                    }
                    if (hueBridge.update(dataHueBridges, dataManager, z7)) {
                        z11 = true;
                    }
                }
            }
            HashMap<String, DataHueBridges> hashMap17 = this.myAddOns.hueBridges;
            Intrinsics.checkNotNull(hashMap17);
            ArrayList arrayList18 = new ArrayList(hashMap17.keySet());
            HashMap<String, DataHueBridges> hashMap18 = dataMaster.myAddOns.hueBridges;
            Intrinsics.checkNotNull(hashMap18);
            Set<String> keySet7 = hashMap18.keySet();
            Intrinsics.checkNotNullExpressionValue(keySet7, "<get-keys>(...)");
            arrayList18.removeAll(keySet7);
            Iterator it8 = arrayList18.iterator();
            while (it8.hasNext()) {
                String str20 = (String) it8.next();
                HashMap<String, DataHueBridges> hashMap19 = this.myAddOns.hueBridges;
                Intrinsics.checkNotNull(hashMap19);
                hashMap19.remove(str20);
                if (dataManager != null) {
                    dataManager.updateHueBridgesPath("");
                    dataManager.add(str20, null);
                }
            }
            ArrayList<String> arrayList19 = dataMaster.myAddOns.hueBridgesOrder;
            if (arrayList19 != null && ((arrayList3 = this.myAddOns.hueBridgesOrder) == null || !Intrinsics.areEqual(arrayList3, arrayList19))) {
                this.myAddOns.hueBridgesOrder = dataMaster.myAddOns.hueBridgesOrder;
                if (dataManager != null) {
                    dataManager.updateHueBridgesOrderPath();
                    dataManager.add("hueBridgesOrder", this.myAddOns.hueBridgesOrder);
                }
                z11 = true;
            }
        }
        TreeMap<String, DataAlarm> treeMap21 = dataMaster.myLights.alarms;
        if (treeMap21 != null) {
            Intrinsics.checkNotNull(treeMap21);
            for (String str21 : treeMap21.keySet()) {
                TreeMap<String, DataAlarm> treeMap22 = dataMaster.myLights.alarms;
                Intrinsics.checkNotNull(treeMap22);
                DataAlarm dataAlarm = treeMap22.get(str21);
                if (dataAlarm != null) {
                    if (dataManager != null) {
                        dataManager.updateAlarmPath(str21);
                        Unit unit11 = Unit.INSTANCE;
                    }
                    DataAlarm alarm = this.myLights.getAlarm(str21);
                    if (alarm == null) {
                        alarm = new DataAlarm();
                        TreeMap<String, DataAlarm> treeMap23 = this.myLights.alarms;
                        Intrinsics.checkNotNull(treeMap23);
                        treeMap23.put(str21, alarm);
                    }
                    if (alarm.update(null, dataAlarm, dataManager, z7)) {
                        z11 = true;
                    }
                }
            }
            TreeMap<String, DataAlarm> treeMap24 = this.myLights.alarms;
            Intrinsics.checkNotNull(treeMap24);
            ArrayList arrayList20 = new ArrayList(treeMap24.keySet());
            TreeMap<String, DataAlarm> treeMap25 = dataMaster.myLights.alarms;
            Intrinsics.checkNotNull(treeMap25);
            Set<String> keySet8 = treeMap25.keySet();
            Intrinsics.checkNotNullExpressionValue(keySet8, "<get-keys>(...)");
            arrayList20.removeAll(keySet8);
            Iterator it9 = arrayList20.iterator();
            while (it9.hasNext()) {
                String str22 = (String) it9.next();
                TreeMap<String, DataAlarm> treeMap26 = this.myLights.alarms;
                Intrinsics.checkNotNull(treeMap26);
                treeMap26.remove(str22);
                if (dataManager != null) {
                    dataManager.updateAlarmPath("");
                    dataManager.add(str22, null);
                }
            }
            ArrayList<String> arrayList21 = dataMaster.myLights.alarmsOrder;
            if (arrayList21 != null && ((arrayList2 = this.myLights.alarmsOrder) == null || !Intrinsics.areEqual(arrayList2, arrayList21))) {
                this.myLights.alarmsOrder = dataMaster.myLights.alarmsOrder;
                if (dataManager != null) {
                    dataManager.updateAlarmsOrderPath();
                    dataManager.add("alarmsOrder", this.myLights.alarmsOrder);
                }
                z11 = true;
            }
        }
        if (dataMaster.myLights.system != null) {
            if (dataManager != null) {
                dataManager.updateLightsSystemPath();
                Unit unit12 = Unit.INSTANCE;
            }
            DataLightsSystem dataLightsSystem = this.myLights.system;
            Intrinsics.checkNotNull(dataLightsSystem);
            if (dataLightsSystem.update(dataMaster.myLights.system, dataManager, z7)) {
                z11 = true;
            }
        }
        TreeMap<String, DataMyThing> treeMap27 = dataMaster.myThings.things;
        Intrinsics.checkNotNull(treeMap27);
        for (String str23 : treeMap27.keySet()) {
            TreeMap<String, DataMyThing> treeMap28 = dataMaster.myThings.things;
            Intrinsics.checkNotNull(treeMap28);
            DataMyThing dataMyThing = treeMap28.get(str23);
            if (dataMyThing != null) {
                if (dataManager != null) {
                    dataManager.updateThingPath(str23);
                    Unit unit13 = Unit.INSTANCE;
                }
                TreeMap<String, DataMyThing> treeMap29 = this.myThings.things;
                Intrinsics.checkNotNull(treeMap29);
                DataMyThing dataMyThing2 = treeMap29.get(str23);
                if (dataMyThing2 == null) {
                    dataMyThing2 = new DataMyThing();
                    TreeMap<String, DataMyThing> treeMap30 = this.myThings.things;
                    Intrinsics.checkNotNull(treeMap30);
                    treeMap30.put(str23, dataMyThing2);
                }
                if (dataMyThing2.update(null, dataMyThing, dataManager, z7)) {
                    z11 = true;
                }
            }
        }
        TreeMap<String, DataMyThing> treeMap31 = this.myThings.things;
        Intrinsics.checkNotNull(treeMap31);
        ArrayList arrayList22 = new ArrayList(treeMap31.keySet());
        TreeMap<String, DataMyThing> treeMap32 = dataMaster.myThings.things;
        Intrinsics.checkNotNull(treeMap32);
        Set<String> keySet9 = treeMap32.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet9, "<get-keys>(...)");
        arrayList22.removeAll(keySet9);
        Iterator it10 = arrayList22.iterator();
        while (it10.hasNext()) {
            String str24 = (String) it10.next();
            TreeMap<String, DataMyThing> treeMap33 = this.myThings.things;
            Intrinsics.checkNotNull(treeMap33);
            treeMap33.remove(str24);
            if (dataManager != null) {
                dataManager.updateThingPath("");
                dataManager.add(str24, null);
            }
        }
        TreeMap<String, DataGroupThing> treeMap34 = dataMaster.myThings.groups;
        Intrinsics.checkNotNull(treeMap34);
        for (String str25 : treeMap34.keySet()) {
            TreeMap<String, DataGroupThing> treeMap35 = dataMaster.myThings.groups;
            Intrinsics.checkNotNull(treeMap35);
            DataGroupThing dataGroupThing = treeMap35.get(str25);
            if (dataGroupThing != null) {
                if (dataManager != null) {
                    dataManager.updateGroupThingPath(str25);
                    Unit unit14 = Unit.INSTANCE;
                }
                DataGroupThing dataGroupThing2 = this.myThings.getDataGroupThing(str25);
                if (dataGroupThing2 == null) {
                    dataGroupThing2 = new DataGroupThing(str25);
                    TreeMap<String, DataGroupThing> treeMap36 = this.myThings.groups;
                    Intrinsics.checkNotNull(treeMap36);
                    treeMap36.put(str25, dataGroupThing2);
                }
                if (dataGroupThing2.update(dataGroupThing, dataManager, z7)) {
                    z11 = true;
                }
            }
        }
        TreeMap<String, DataGroupThing> treeMap37 = this.myThings.groups;
        Intrinsics.checkNotNull(treeMap37);
        ArrayList arrayList23 = new ArrayList(treeMap37.keySet());
        TreeMap<String, DataGroupThing> treeMap38 = dataMaster.myThings.groups;
        Intrinsics.checkNotNull(treeMap38);
        Set<String> keySet10 = treeMap38.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet10, "<get-keys>(...)");
        arrayList23.removeAll(keySet10);
        Iterator it11 = arrayList23.iterator();
        while (it11.hasNext()) {
            String str26 = (String) it11.next();
            TreeMap<String, DataGroupThing> treeMap39 = this.myThings.groups;
            Intrinsics.checkNotNull(treeMap39);
            treeMap39.remove(str26);
            if (dataManager != null) {
                dataManager.updateGroupThingPath("");
                dataManager.add(str26, null);
            }
        }
        ArrayList<String> arrayList24 = dataMaster.myThings.groupsOrder;
        if (arrayList24 != null && ((arrayList = this.myThings.groupsOrder) == null || !Intrinsics.areEqual(arrayList, arrayList24))) {
            this.myThings.groupsOrder = dataMaster.myThings.groupsOrder;
            if (dataManager != null) {
                dataManager.updateThingOrderPath();
                dataManager.add("groupsOrder", this.myThings.groupsOrder);
            }
            z11 = true;
        }
        if (dataMaster.myThings.system != null) {
            if (dataManager != null) {
                dataManager.updateThingsSystemPath();
                Unit unit15 = Unit.INSTANCE;
            }
            DataThingsSystem dataThingsSystem = this.myThings.system;
            Intrinsics.checkNotNull(dataThingsSystem);
            if (dataThingsSystem.update(dataMaster.myThings.system, dataManager, z7)) {
                z11 = true;
            }
        }
        if (dataMaster.myView == null) {
            z10 = z11;
        } else {
            if (dataManager != null) {
                dataManager.updateMyViewPath();
                Unit unit16 = Unit.INSTANCE;
            }
            if (this.myView == null) {
                this.myView = new DataMyView(null, null, null, null, 15, null);
            }
            DataMyView dataMyView = this.myView;
            Intrinsics.checkNotNull(dataMyView);
            DataMyView dataMyView2 = dataMaster.myView;
            Intrinsics.checkNotNull(dataMyView2);
            if (dataMyView.update(dataMyView2, dataManager)) {
                this.myView = dataMaster.myView;
                z10 = true;
            }
        }
        HashMap<String, DataGroupSource> hashMap20 = dataMaster.myGarageRFControllers.garageControllers;
        if (hashMap20 == null) {
            return z10;
        }
        Intrinsics.checkNotNull(hashMap20);
        for (String str27 : hashMap20.keySet()) {
            HashMap<String, DataGroupSource> hashMap21 = dataMaster.myGarageRFControllers.garageControllers;
            Intrinsics.checkNotNull(hashMap21);
            DataGroupSource dataGroupSource = hashMap21.get(str27);
            if (dataGroupSource != null) {
                if (dataManager != null) {
                    dataManager.updateGarageControllersPath(str27);
                    Unit unit17 = Unit.INSTANCE;
                }
                DataGroupSource garageController = this.myGarageRFControllers.getGarageController(str27);
                if (garageController == null) {
                    garageController = new DataGroupSource();
                    HashMap<String, DataGroupSource> hashMap22 = this.myGarageRFControllers.garageControllers;
                    Intrinsics.checkNotNull(hashMap22);
                    hashMap22.put(str27, garageController);
                }
                if (garageController.update(dataGroupSource, dataManager, z7)) {
                    z10 = true;
                }
            }
        }
        HashMap<String, DataGroupSource> hashMap23 = this.myGarageRFControllers.garageControllers;
        Intrinsics.checkNotNull(hashMap23);
        ArrayList arrayList25 = new ArrayList(hashMap23.keySet());
        HashMap<String, DataGroupSource> hashMap24 = dataMaster.myGarageRFControllers.garageControllers;
        Intrinsics.checkNotNull(hashMap24);
        Set<String> keySet11 = hashMap24.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet11, "<get-keys>(...)");
        arrayList25.removeAll(keySet11);
        Iterator it12 = arrayList25.iterator();
        while (it12.hasNext()) {
            String str28 = (String) it12.next();
            HashMap<String, DataGroupSource> hashMap25 = this.myGarageRFControllers.garageControllers;
            Intrinsics.checkNotNull(hashMap25);
            hashMap25.remove(str28);
            if (dataManager != null) {
                dataManager.updateGarageControllersPath("");
                dataManager.add(str28, null);
            }
        }
        ArrayList<String> arrayList26 = dataMaster.myGarageRFControllers.garageControllersOrder;
        if (arrayList26 == null) {
            return z10;
        }
        ArrayList<String> arrayList27 = this.myGarageRFControllers.garageControllersOrder;
        if (arrayList27 != null && Intrinsics.areEqual(arrayList27, arrayList26)) {
            return z10;
        }
        this.myGarageRFControllers.garageControllersOrder = dataMaster.myGarageRFControllers.garageControllersOrder;
        if (dataManager != null) {
            dataManager.updateHueBridgesOrderPath();
            dataManager.add("garageControllersOrder", this.myGarageRFControllers.garageControllersOrder);
        }
        return true;
    }
}