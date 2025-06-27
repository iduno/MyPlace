package com.air.advantage.data;

import com.air.advantage.jsondata.MasterStore;
import com.air.advantage.libraryairconlightjson.UartConstants;
import com.air.advantage.uart.HandlerAircon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.data.l */
/* loaded from: classes.dex */
public final class DataManager {

    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final String LOG_TAG = DataManager.class.getSimpleName();

    @Nullable
    private BroadcastInfo broadcastInfo;

    @NotNull
    @JvmField
    public final ArrayList<BroadcastInfo> broadcastInfos = new ArrayList<>();

    @NotNull
    private String path = "";

    @NotNull
    @JvmField
    public Map<String, Object> updateChildrenStore = new HashMap();

    @NotNull
    private Map<String, Object> setValueStore = new HashMap();

    /* renamed from: com.air.advantage.data.l$a */
    public final class BroadcastInfo {

        @Nullable
        @JvmField
        public String airconKey;

        @NotNull
        @JvmField
        public String broadcastString;

        @Nullable
        private String groupId;

        @Nullable
        private String lightId;

        @Nullable
        private String sceneId;

        @Nullable
        @JvmField
        public String snapshotId;
        final /* synthetic */ DataManager this$0;

        @Nullable
        @JvmField
        public String zoneId;

        public BroadcastInfo(@NotNull DataManager dataManager, @Nullable String broadcastString, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, String str6) {
            Intrinsics.checkNotNullParameter(broadcastString, "broadcastString");
            this.this$0 = dataManager;
            this.broadcastString = broadcastString;
            this.airconKey = str;
            this.zoneId = str2;
            this.snapshotId = str3;
            this.lightId = str4;
            this.groupId = str5;
            this.sceneId = str6;
        }

        @Nullable
        public final String getGroupId() {
            return this.groupId;
        }

        @Nullable
        public final String getLightId() {
            return this.lightId;
        }

        @Nullable
        public final String getSceneId() {
            return this.sceneId;
        }

        public final void setGroupId(@Nullable String str) {
            this.groupId = str;
        }

        public final void setLightId(@Nullable String str) {
            this.lightId = str;
        }

        public final void setSceneId(@Nullable String str) {
            this.sceneId = str;
        }
    }

    /* renamed from: com.air.advantage.data.l$b */
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.l.b.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private Companion() {
        }
    }

    public DataManager() {
        if (!HandlerAircon.lock.get() && !Thread.holdsLock(MasterStore.class)) {
            throw new NullPointerException("This is strongly linked to MasterStore this needs to be synchronized (MasterStore.class)");
        }
    }

    public final void add(@Nullable String str, @Nullable Object obj) {
        if (str != null) {
            if (!(str.length() == 0)) {
                if (obj == null) {
                    ArrayList arrayList = new ArrayList();
                    for (String str2 : this.updateChildrenStore.keySet()) {
                        if (StringsKt__StringsJVMKt.startsWith(str2, this.path + str, false, 2, null)) {
                            arrayList.add(str2);
                        }
                    }
                    if (arrayList.size() > 0) {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            String str3 = (String) it.next();
                            Timber.forest.d("Deleting data in path " + str3, new Object[0]);
                            this.updateChildrenStore.remove(str3);
                        }
                    }
                }
                this.updateChildrenStore.put(this.path + str, obj);
                BroadcastInfo broadcastInfo = this.broadcastInfo;
                if (broadcastInfo == null || CollectionsKt___CollectionsKt.contains(this.broadcastInfos, broadcastInfo)) {
                    return;
                }
                ArrayList<BroadcastInfo> arrayList2 = this.broadcastInfos;
                BroadcastInfo broadcastInfo2 = this.broadcastInfo;
                Intrinsics.checkNotNull(broadcastInfo2);
                arrayList2.add(broadcastInfo2);
                return;
            }
        }
        throw new NullPointerException("name is null or empty");
    }

    public final void addSetValue(@NotNull String name, @Nullable Object obj) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (this.path.length() == 0) {
            Timber.forest.e(LOG_TAG, "WARNING, YOU'RE ABOUT TO DELETE THE WHOLE FIREBASE STRUCTURE!!!");
            return;
        }
        this.setValueStore.put(this.path + name, obj);
        BroadcastInfo broadcastInfo = this.broadcastInfo;
        if (broadcastInfo == null || CollectionsKt___CollectionsKt.contains(this.broadcastInfos, broadcastInfo)) {
            return;
        }
        ArrayList<BroadcastInfo> arrayList = this.broadcastInfos;
        BroadcastInfo broadcastInfo2 = this.broadcastInfo;
        Intrinsics.checkNotNull(broadcastInfo2);
        arrayList.add(broadcastInfo2);
    }

    @NotNull
    public final String getPath() {
        return this.path;
    }

    @NotNull
    public final Map<String, Object> getSetValueStore() {
        return this.setValueStore;
    }

    public final void setPath(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.path = str;
    }

    public final void setSetValueStore(@NotNull Map<String, Object> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.setValueStore = map;
    }

    public final void updateAirconPath() {
        this.path = "/aircons/";
        this.broadcastInfo = null;
    }

    public final void updateAlarmPath(@Nullable String str) {
        this.path = "/myLights/alarms/" + str;
        if (!(str == null || str.length() == 0)) {
            this.path = this.path + "/";
        }
        this.broadcastInfo = null;
    }

    public final void updateAlarmsOrderPath() {
        this.path = "/myLights/";
        this.broadcastInfo = null;
    }

    public final void updateGarageControllersPath(@Nullable String str) {
        this.path = "/myGarageRFControllers/garageControllers/" + str;
        if (!(str == null || str.length() == 0)) {
            this.path = this.path + "/";
        }
        this.broadcastInfo = null;
    }

    public final void updateGroupPath(@Nullable String str) {
        this.path = "/myLights/groups/" + str;
        if (!(str == null || str.length() == 0)) {
            this.path = this.path + "/";
        }
        this.broadcastInfo = null;
    }

    public final void updateGroupThingPath(@Nullable String str) {
        this.path = "/myThings/groups/" + str;
        if (!(str == null || str.length() == 0)) {
            this.path = this.path + "/";
        }
        this.broadcastInfo = null;
    }

    public final void updateHueBridgesOrderPath() {
        this.path = "/myAddOns/";
        this.broadcastInfo = null;
    }

    public final void updateHueBridgesPath(@Nullable String str) {
        this.path = "/myAddOns/hueBridges/" + str;
        if (!(str == null || str.length() == 0)) {
            this.path = this.path + "/";
        }
        this.broadcastInfo = null;
    }

    public final void updateInfoPath(@NotNull String airconKey, @Nullable String str) {
        Intrinsics.checkNotNullParameter(airconKey, "airconKey");
        if (str == null) {
            this.path = "/aircons/" + airconKey + "/info/";
            this.broadcastInfo = new BroadcastInfo(this, UartConstants.SYSTEM_DATA_UPDATE, airconKey, null, null, null, null, null);
            return;
        }
        this.path = str + "/aircons/" + airconKey + "/info/";
    }

    public final void updateLightOrderPath() {
        this.path = "/myLights/";
        this.broadcastInfo = null;
    }

    public final void updateLightPath(@Nullable String str) {
        this.path = "/myLights/lights/" + str;
        if (!(str == null || str.length() == 0)) {
            this.path = this.path + "/";
        }
        this.broadcastInfo = null;
    }

    public final void updateLightsSystemPath() {
        this.path = "/myLights/system/";
        this.broadcastInfo = null;
    }

    public final void updateMonitorActionsPath(@Nullable String str) {
        this.path = "/myMonitors/monitors/" + str + "/actions/";
        this.broadcastInfo = null;
    }

    public final void updateMonitorEventsPath(@Nullable String str) {
        this.path = "/myMonitors/monitors/" + str + "/events/";
        this.broadcastInfo = null;
    }

    public final void updateMonitorPath(@Nullable String str) {
        this.path = "/myMonitors/monitors/" + str;
        if (!(str == null || str.length() == 0)) {
            this.path = this.path + "/";
        }
        this.broadcastInfo = null;
    }

    public final void updateMonitorsOrderPath() {
        this.path = "/myMonitors/";
        this.broadcastInfo = null;
    }

    public final void updateMyViewPath() {
        this.path = "/myView/";
        this.broadcastInfo = null;
    }

    public final void updateScenePath(@Nullable String str) {
        this.path = "/myScenes/scenes/" + str;
        if (!(str == null || str.length() == 0)) {
            this.path = this.path + "/";
        }
        this.broadcastInfo = null;
    }

    public final void updateScenesOrderPath() {
        this.path = "/myScenes/";
        this.broadcastInfo = null;
    }

    public final void updateSensorPath(@Nullable String str) {
        this.path = "/mySensors/sensors/" + str;
        if (!(str == null || str.length() == 0)) {
            this.path = this.path + "/";
        }
        this.broadcastInfo = null;
    }

    public final void updateSensorsOrderPath() {
        this.path = "/mySensors/";
        this.broadcastInfo = null;
    }

    public final void updateSnapshotPath(@Nullable String str) {
        this.path = "/snapshots/" + str;
        if (!(str == null || str.length() == 0)) {
            this.path = this.path + "/";
        }
        this.broadcastInfo = new BroadcastInfo(this, UartConstants.SCHEDULE_DATA_UPDATE, null, null, str, null, null, null);
    }

    public final void updateSystemPath() {
        this.path = "/system/";
        this.broadcastInfo = new BroadcastInfo(this, UartConstants.SYSTEM_DATA_UPDATE, null, null, null, null, null, null);
    }

    public final void updateThingOrderPath() {
        this.path = "/myThings/";
        this.broadcastInfo = null;
    }

    public final void updateThingPath(@Nullable String str) {
        this.path = "/myThings/things/" + str;
        if (!(str == null || str.length() == 0)) {
            this.path = this.path + "/";
        }
        this.broadcastInfo = null;
    }

    public final void updateThingsSystemPath() {
        this.path = "/myThings/system/";
        this.broadcastInfo = null;
    }

    public final void updateZonePath(@Nullable String str, @Nullable String str2, @Nullable String str3) {
        if (str3 == null) {
            this.path = "/aircons/" + str + "/zones/" + str2;
            if (!(str2 == null || str2.length() == 0)) {
                this.path = this.path + "/";
            }
            this.broadcastInfo = new BroadcastInfo(this, UartConstants.ZONE_DATA_UPDATE, str, str2, null, null, null, null);
            return;
        }
        this.path = str3 + "/aircons/" + str + "/zones/" + str2;
        if (str2 == null || str2.length() == 0) {
            return;
        }
        this.path = this.path + "/";
    }
}