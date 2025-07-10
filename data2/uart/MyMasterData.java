package com.air.advantage.uart;

import android.content.Context;
import com.air.advantage.ActivityMain;
import com.air.advantage.AppFeatures;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataMyScene;
import com.air.advantage.data.DataScene;
import com.air.advantage.data.SnapShot;
import com.air.advantage.jsondata.MasterStore;
import com.google.android.gms.search.SearchAuth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.uart.c0 */
/* loaded from: classes.dex */
public final class MyMasterData {

    /* renamed from: b */
    @NotNull
    public static final a Companion = new a(null);

    /* renamed from: c */
    @Nullable
    private static MyMasterData instance;

    @NotNull
    private final DataMaster a;

    /* renamed from: com.air.advantage.uart.c0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.c0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        /* renamed from: a */
        public final void destroy() {
            MyMasterData.instance = null;
        }

        @NotNull
        /* renamed from: b */
        public final DataMaster getDataMaster(@Nullable Context context) {
            if (MyMasterData.instance == null) {
                synchronized (MyMasterData.class) {
                    if (context == null) {
                        throw new NullPointerException("You need to have initialised with context at least once");
                    }
                    if (MyMasterData.instance == null) {
                        a aVar = MyMasterData.Companion;
                        Context applicationContext = context.getApplicationContext();
                        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                        MyMasterData.instance = new MyMasterData(applicationContext, null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            if (!Thread.holdsLock(MyMasterData.class)) {
                throw new NullPointerException("You need to have synchronized (MyMasterData.class)");
            }
            if (Thread.holdsLock(MasterStore.class)) {
                throw new NullPointerException("This thread already synchronized with MasterStore.class");
            }
            MyMasterData myMasterData = MyMasterData.instance;
            Intrinsics.checkNotNull(myMasterData);
            return myMasterData.a;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 android.content.Context) A[MD:(android.content.Context):void (m)] (LINE:1) call: com.air.advantage.uart.c0.<init>(android.content.Context):void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ MyMasterData(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    @JvmStatic
    public static final void d() {
        Companion.destroy();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    /* JADX WARN: Removed duplicated region for block: B:107:0x02a8  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02fc A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private MyMasterData(Context context) {
        boolean z7;
        boolean z10;
        TreeMap<String, SnapShot> treeMap;
        DataMaster dataMaster = new DataMaster();
        this.a = dataMaster;
        HandlerAircon.Companion.getInstance(context).updateMasterData(dataMaster);
        LightDBStore b10 = LightDBStore.Companion.b();
        b10.d(context, dataMaster);
        ThingDBStore.Companion.b().d(context, dataMaster);
        d0 b11 = d0.Companion.b();
        b11.d(context, dataMaster);
        MyMonitors.Companion.b().d(context, dataMaster);
        MyGarageController.Companion.b().d(context, dataMaster);
        b.Companion.b().d(context, dataMaster);
        SensorDBStore.f7078e.a().c(context, dataMaster);
        if (dataMaster.myLights.scenes.size() > 0 && !AppFeatures.isAnywair()) {
            Iterator it = new ArrayList(dataMaster.myLights.scenes.keySet()).iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                DataScene dataScene = this.a.myLights.scenes.get(str);
                if (str.length() > 3) {
                    boolean z11 = false;
                    int i10 = SearchAuth.StatusCodes.AUTH_THROTTLED;
                    while (!z11 && i10 <= 10012) {
                        TreeMap<String, DataScene> treeMap2 = this.a.myScenes.scenes;
                        Intrinsics.checkNotNull(treeMap2);
                        if (treeMap2.get("s" + i10) == null) {
                            z11 = true;
                        } else {
                            i10++;
                        }
                    }
                    if (z11) {
                        Intrinsics.checkNotNull(dataScene);
                        dataScene.id = "s" + i10;
                        this.a.myScenes.addScene(dataScene);
                        this.a.myLights.scenes.remove(str);
                        this.a.myLights.scenesOrder.remove(str);
                    } else {
                        AppFeatures.logError(AppFeatures.instance, new RuntimeException("Error transferring scenes from myLights.scenes to myScenes.scenes - no space left in new location"), null, 2, null);
                    }
                } else {
                    DataMyScene dataMyScene = this.a.myScenes;
                    Intrinsics.checkNotNull(dataScene);
                    dataMyScene.addScene(dataScene);
                    this.a.myLights.scenes.remove(str);
                }
            }
            b10.e(context, this.a);
            b11.e(context, this.a);
        }
        if (!AppFeatures.isAnywair() && (treeMap = this.a.snapshots) != null && treeMap.size() > 0) {
            AirconDBStore companion = AirconDBStore.Companion.getInstance(context);
            Iterator it2 = new ArrayList(this.a.snapshots.keySet()).iterator();
            while (it2.hasNext()) {
                String str2 = (String) it2.next();
                SnapShot snapShot = this.a.snapshots.get(str2);
                boolean z12 = false;
                int i11 = SearchAuth.StatusCodes.AUTH_THROTTLED;
                while (!z12 && i11 <= 10012) {
                    TreeMap<String, DataScene> treeMap3 = this.a.myScenes.scenes;
                    Intrinsics.checkNotNull(treeMap3);
                    if (treeMap3.get("s" + i11) == null) {
                        z12 = true;
                    } else {
                        i11++;
                    }
                }
                if (z12) {
                    DataScene dataScene2 = new DataScene();
                    dataScene2.id = "s" + i11;
                    Intrinsics.checkNotNull(snapShot);
                    if (snapShot.activeDays == null || snapShot.startTime == null || snapShot.stopTime == null || snapShot.aircons == null) {
                        AppFeatures.logError(AppFeatures.instance, new RuntimeException("Error transferring plan(snapshot) id:" + str2 + " to scenes - incomplete data from snapshot"), null, 2, null);
                    } else {
                        String str3 = snapShot.name;
                        if (str3 != null) {
                            dataScene2.name = str3;
                        } else {
                            dataScene2.name = "Scene " + i11;
                        }
                        dataScene2.activeDays = snapShot.activeDays;
                        dataScene2.startTime = snapShot.startTime;
                        dataScene2.airconStopTime = snapShot.stopTime;
                        dataScene2.airconStopTimeEnabled = Boolean.TRUE;
                        Boolean bool = snapShot.enabled;
                        if (bool != null) {
                            dataScene2.timerEnabled = bool;
                        } else {
                            dataScene2.timerEnabled = Boolean.FALSE;
                        }
                        dataScene2.myTimeEnabled = Boolean.FALSE;
                        dataScene2.aircons = new HashMap<>(snapShot.aircons);
                        String packageName = ActivityMain.Companion.getPackageName();
                        if (packageName != null && StringsKt__StringsKt.startsWith$default(packageName, "zone10", false, 2, null)) {
                            String str4 = snapShot.summary;
                            if (str4 != null) {
                                dataScene2.summary = str4;
                            } else {
                                dataScene2.summary = "";
                            }
                        } else {
                            dataScene2.summary = dataScene2.generateSummary(this.a);
                        }
                        dataScene2.canMessages = "";
                        dataScene2.things = new HashMap<>();
                        dataScene2.lights = new HashMap<>();
                        this.a.myScenes.addScene(dataScene2);
                    }
                } else {
                    AppFeatures.logError(AppFeatures.instance, new RuntimeException("Error transferring plan(snapshot) id:" + str2 + " to scenes - no space left in scenes"), null, 2, null);
                }
                this.a.snapshots.remove(str2);
                Intrinsics.checkNotNull(snapShot);
                companion.updateSnapshot(context, snapShot.snapshotId);
            }
            b11.e(context, this.a);
        }
        String packageName2 = ActivityMain.Companion.getPackageName();
        if (packageName2 != null) {
            z7 = true;
            z10 = StringsKt__StringsKt.startsWith$default(packageName2, ActivityMain.MYAIR5, false, 2, null) ? true : z10;
            if (z10) {
                return;
            }
            ArrayList<String> arrayList = this.a.myScenes.scenesOrder;
            Intrinsics.checkNotNull(arrayList);
            if (arrayList.size() > 0) {
                ArrayList<String> arrayList2 = this.a.myScenes.scenesOrder;
                Intrinsics.checkNotNull(arrayList2);
                Iterator<String> it3 = arrayList2.iterator();
                while (it3.hasNext()) {
                    String next = it3.next();
                    TreeMap<String, DataScene> treeMap4 = this.a.myScenes.scenes;
                    Intrinsics.checkNotNull(treeMap4);
                    DataScene dataScene3 = treeMap4.get(next);
                    if (dataScene3 != null) {
                        String str5 = dataScene3.summary;
                        if (str5 != null) {
                            if ((str5 == null || str5.length() == 0) ? z7 : false) {
                            }
                        }
                        dataScene3.summary = dataScene3.generateSummary(this.a);
                    }
                }
                return;
            }
            return;
        }
        z7 = true;
        z10 = false;
        if (z10) {
        }
    }
}