package com.air.advantage.uart;

import android.content.Context;
import com.air.advantage.data.DataLight;
import com.air.advantage.data.DataMaster;
import com.air.advantage.libraryairconlightjson.LightState;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.uart.x */
/* loaded from: classes.dex */
public final class MyLights {

    /* renamed from: c, reason: collision with root package name */
    public static final long f7210c = 120000;

    /* renamed from: d, reason: collision with root package name */
    public static final int f7211d = 19;

    @NotNull
    private final ArrayList<String> a = new ArrayList<>();

    /* renamed from: b, reason: collision with root package name */
    @NotNull
    public static final a f7209b = new a(null);

    /* renamed from: e, reason: collision with root package name */
    private static final String f7212e = MyLights.class.getSimpleName();

    /* renamed from: com.air.advantage.uart.x$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.x.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    public final boolean a(@Nullable String str) {
        if (str == null || this.a.contains(str)) {
            return false;
        }
        return this.a.add(str);
    }

    public final void b() {
        this.a.clear();
    }

    public final boolean c(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.a.size() <= 0) {
            Timber.forest.d("DBG no lights to control", new Object[0]);
            return false;
        }
        ArrayList arrayList = new ArrayList();
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            Iterator<String> it = this.a.iterator();
            while (it.hasNext()) {
                String next = it.next();
                DataLight lightData = dataMaster.myLights.getLightData(next);
                if (lightData != null) {
                    DataLight dataLight = new DataLight();
                    DataLight.update$default(dataLight, null, lightData, null, false, 8, null);
                    Integer num = dataLight.type;
                    if (num != null && num.intValue() == 2) {
                        if (dataLight.state == LightState.off) {
                            dataLight.state = LightState.on;
                            dataLight.value = 5;
                        }
                        Integer num2 = dataLight.value;
                        Intrinsics.checkNotNull(num2);
                        if (num2.intValue() <= 95) {
                            Integer num3 = dataLight.value;
                            Intrinsics.checkNotNull(num3);
                            dataLight.value = Integer.valueOf(num3.intValue() + 5);
                        } else {
                            dataLight.value = 100;
                        }
                        HandlerLights.Companion.getInstance().s0(context, dataLight);
                        Integer num4 = dataLight.value;
                        if (num4 != null && num4.intValue() == 100) {
                            arrayList.add(next);
                        }
                    } else {
                        dataLight.state = LightState.on;
                        dataLight.value = 100;
                        HandlerLights.Companion.getInstance().s0(context, dataLight);
                        arrayList.add(next);
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            d((String) it2.next());
        }
        if (this.a.size() > 0) {
            Timber.forest.d("DBG still got lights to control", new Object[0]);
            return true;
        }
        Timber.forest.d("DBG no more lights to control", new Object[0]);
        return false;
    }

    public final boolean d(@Nullable String str) {
        return this.a.remove(str);
    }
}