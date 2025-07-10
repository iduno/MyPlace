package com.air.advantage.uart;

import android.content.Context;
import com.air.advantage.data.DataLight;
import com.air.advantage.data.DataMaster;
import com.air.advantage.libraryairconlightjson.LightState;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.uart.y */
/* loaded from: classes.dex */
public final class MyLightsV2 {

    /* renamed from: d, reason: collision with root package name */
    public static final long f7214d = 120000;

    /* renamed from: e, reason: collision with root package name */
    public static final int f7215e = 19;

    @NotNull
    private final ConcurrentHashMap<String, DataLight> a = new ConcurrentHashMap<>();

    /* renamed from: b, reason: collision with root package name */
    @NotNull
    private final ArrayList<String> f7217b = new ArrayList<>();

    /* renamed from: c, reason: collision with root package name */
    @NotNull
    public static final a f7213c = new a(null);

    /* renamed from: f, reason: collision with root package name */
    private static final String f7216f = MyLightsV2.class.getSimpleName();

    /* renamed from: com.air.advantage.uart.y$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.y.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0035 A[Catch: all -> 0x0061, TryCatch #0 {, blocks: (B:9:0x0011, B:11:0x0028, B:13:0x002c, B:15:0x0035, B:17:0x0055, B:18:0x005c), top: B:8:0x0011 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean a(@Nullable Context context, @Nullable DataLight dataLight) {
        String str;
        if (dataLight == null || (str = dataLight.id) == null || this.a.containsKey(str)) {
            return false;
        }
        synchronized (MyMasterData.class) {
            TreeMap<String, DataLight> treeMap = MyMasterData.Companion.getDataMaster(context).myLights.lights;
            Intrinsics.checkNotNull(treeMap);
            DataLight dataLight2 = treeMap.get(dataLight.id);
            if (dataLight2 != null) {
                Boolean bool = dataLight2.reachable;
                if (bool != null) {
                    Intrinsics.checkNotNull(bool);
                    if (bool.booleanValue()) {
                        DataLight dataLight3 = new DataLight();
                        DataLight.update$default(dataLight3, null, dataLight, null, false, 8, null);
                        ConcurrentHashMap<String, DataLight> concurrentHashMap = this.a;
                        String str2 = dataLight3.id;
                        Intrinsics.checkNotNull(str2);
                        concurrentHashMap.put(str2, dataLight3);
                        if (dataLight2.state == LightState.off) {
                            this.f7217b.add(dataLight.id);
                        }
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
        return true;
    }

    public final void b() {
        this.a.clear();
        this.f7217b.clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00ca A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x002a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b3 A[Catch: all -> 0x0121, TryCatch #0 {, blocks: (B:6:0x001a, B:7:0x002a, B:9:0x0030, B:12:0x0040, B:46:0x00d1, B:14:0x0057, B:17:0x005e, B:19:0x0062, B:42:0x006b, B:22:0x0071, B:24:0x0078, B:25:0x0082, B:28:0x008d, B:30:0x00a2, B:31:0x00b7, B:34:0x00ca, B:38:0x00b3, B:39:0x0087, B:52:0x00ed), top: B:5:0x001a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean c(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.a.size() <= 0) {
            Timber.forest.d("DBG no lights to control", new Object[0]);
            return false;
        }
        ArrayList arrayList = new ArrayList();
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            for (DataLight dataLight : this.a.values()) {
                DataLight lightData = dataMaster.myLights.getLightData(dataLight.id);
                if (lightData != null) {
                    DataLight dataLight2 = new DataLight();
                    DataLight.update$default(dataLight2, null, lightData, null, false, 8, null);
                    Integer num = dataLight2.type;
                    if (num != null && num.intValue() == 2) {
                        Boolean bool = lightData.reachable;
                        if (bool != null) {
                            Intrinsics.checkNotNull(bool);
                            if (!bool.booleanValue()) {
                                arrayList.add(dataLight.id);
                            }
                        }
                        if (dataLight2.state == LightState.off) {
                            dataLight2.state = LightState.on;
                            dataLight2.value = 5;
                        }
                        Integer num2 = dataLight.value;
                        if (num2 != null && num2.intValue() == 5) {
                            dataLight2.value = dataLight.value;
                            HandlerLights.Companion.getInstance().s0(context, dataLight2);
                            if (!Intrinsics.areEqual(dataLight2.value, dataLight.value)) {
                                arrayList.add(dataLight.id);
                            }
                        } else {
                            Integer num3 = dataLight2.value;
                            Intrinsics.checkNotNull(num3);
                            int intValue = num3.intValue();
                            Integer num4 = dataLight.value;
                            Intrinsics.checkNotNull(num4);
                            if (intValue <= num4.intValue() - 5) {
                                Integer num5 = dataLight2.value;
                                Intrinsics.checkNotNull(num5);
                                dataLight2.value = Integer.valueOf(num5.intValue() + 5);
                            }
                            HandlerLights.Companion.getInstance().s0(context, dataLight2);
                            if (!Intrinsics.areEqual(dataLight2.value, dataLight.value)) {
                            }
                        }
                    } else {
                        dataLight2.state = LightState.on;
                        dataLight2.value = 100;
                        HandlerLights.Companion.getInstance().s0(context, dataLight2);
                        arrayList.add(dataLight.id);
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            d((String) it.next());
        }
        if (this.a.size() > 0) {
            Timber.forest.d("DBG still got lights to control", new Object[0]);
            return true;
        }
        Timber.forest.d("DBG no more lights to control", new Object[0]);
        return false;
    }

    public final boolean d(@Nullable String str) {
        if (str == null || !this.a.containsKey(str)) {
            return false;
        }
        this.a.remove(str);
        e(str);
        return true;
    }

    public final boolean e(@Nullable String str) {
        if (str == null || !this.f7217b.contains(str)) {
            return false;
        }
        this.f7217b.remove(str);
        return true;
    }
}