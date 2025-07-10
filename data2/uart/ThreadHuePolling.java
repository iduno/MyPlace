package com.air.advantage.uart;

import android.content.Context;
import android.os.SystemClock;
import com.air.advantage.MyApp;
import com.air.advantage.data.DataHueBridges;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataSensor;
import com.air.advantage.data.DataSystem;
import com.air.advantage.libraryairconlightjson.CommonFuncs;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

/* renamed from: com.air.advantage.uart.g0 */
/* loaded from: classes.dex */
public final class ThreadHuePolling extends Thread {

    /* renamed from: g, reason: collision with root package name */
    public static final int f7094g = 2500;

    /* renamed from: j */
    @Nullable
    private static volatile ThreadHuePolling instance;

    @NotNull
    private final WeakReference<Context> a;

    /* renamed from: b, reason: collision with root package name */
    @NotNull
    private final OkHttpClient f7097b;

    /* renamed from: c, reason: collision with root package name */
    @NotNull
    private final AtomicBoolean f7098c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f7099d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f7100e;

    /* renamed from: f, reason: collision with root package name */
    @NotNull
    public static final a f7093f = new a(null);

    /* renamed from: h, reason: collision with root package name */
    @NotNull
    private static final int[] f7095h = {250, 250, 500};

    /* renamed from: i, reason: collision with root package name */
    @NotNull
    private static final int[] f7096i = {5000, 5000, 5000};

    /* renamed from: com.air.advantage.uart.g0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.g0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final ThreadHuePolling a(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (ThreadHuePolling.instance == null) {
                synchronized (ThreadHuePolling.class) {
                    if (ThreadHuePolling.instance == null) {
                        a aVar = ThreadHuePolling.f7093f;
                        Context applicationContext = context.getApplicationContext();
                        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                        ThreadHuePolling.instance = new ThreadHuePolling(applicationContext, null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            ThreadHuePolling threadHuePolling = ThreadHuePolling.instance;
            Intrinsics.checkNotNull(threadHuePolling);
            return threadHuePolling;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* renamed from: com.air.advantage.uart.g0$b */
    public static final class ThreadHuePollingRunner implements Callback {
        ThreadHuePollingRunner() {
        }

        @Override // okhttp3.Callback
        /* renamed from: a */
        public void onFailure(@NotNull Call call, @NotNull IOException e7) {
            Intrinsics.checkNotNullParameter(call, "call");
            Intrinsics.checkNotNullParameter(e7, "e");
            HandlerHue.Companion.getInstance().k(call, e7);
            ThreadHuePolling.this.f7098c.set(false);
        }

        @Override // okhttp3.Callback
        /* renamed from: b */
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            ResponseBody q7;
            Intrinsics.checkNotNullParameter(call, "call");
            Intrinsics.checkNotNullParameter(response, "response");
            if (response.E() && (q7 = response.q()) != null) {
                HandlerHue.Companion.getInstance().u(q7.string());
            }
            ThreadHuePolling.this.f7098c.set(false);
            response.close();
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 android.content.Context) A[MD:(android.content.Context):void (m)] (LINE:1) call: com.air.advantage.uart.g0.<init>(android.content.Context):void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ ThreadHuePolling(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    public final boolean d() {
        return this.f7099d;
    }

    public final boolean e() {
        return this.f7100e;
    }

    public final void f(boolean z7) {
        this.f7100e = z7;
    }

    /* renamed from: g */
    public final void destroy() {
        this.f7099d = false;
        this.f7100e = false;
        instance = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x013b A[Catch: all -> 0x01a2, TryCatch #0 {, blocks: (B:15:0x0016, B:17:0x0029, B:19:0x0045, B:21:0x0049, B:24:0x004f, B:25:0x008c, B:26:0x006e, B:27:0x00b0, B:28:0x00c9, B:30:0x00cf, B:33:0x00dd, B:36:0x00ea, B:42:0x00f1, B:44:0x0108, B:46:0x010e, B:51:0x013b, B:53:0x014c, B:56:0x016a, B:77:0x0159, B:78:0x0117, B:79:0x0121, B:81:0x0127, B:83:0x0130), top: B:14:0x0016 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x014c A[Catch: all -> 0x01a2, TryCatch #0 {, blocks: (B:15:0x0016, B:17:0x0029, B:19:0x0045, B:21:0x0049, B:24:0x004f, B:25:0x008c, B:26:0x006e, B:27:0x00b0, B:28:0x00c9, B:30:0x00cf, B:33:0x00dd, B:36:0x00ea, B:42:0x00f1, B:44:0x0108, B:46:0x010e, B:51:0x013b, B:53:0x014c, B:56:0x016a, B:77:0x0159, B:78:0x0117, B:79:0x0121, B:81:0x0127, B:83:0x0130), top: B:14:0x0016 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0157 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0159 A[Catch: all -> 0x01a2, TryCatch #0 {, blocks: (B:15:0x0016, B:17:0x0029, B:19:0x0045, B:21:0x0049, B:24:0x004f, B:25:0x008c, B:26:0x006e, B:27:0x00b0, B:28:0x00c9, B:30:0x00cf, B:33:0x00dd, B:36:0x00ea, B:42:0x00f1, B:44:0x0108, B:46:0x010e, B:51:0x013b, B:53:0x014c, B:56:0x016a, B:77:0x0159, B:78:0x0117, B:79:0x0121, B:81:0x0127, B:83:0x0130), top: B:14:0x0016 }] */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void run() {
        DataHueBridges dataHueBridges;
        boolean z7;
        boolean z10;
        String str;
        String str2;
        String str3;
        this.f7099d = true;
        while (true) {
            int i10 = 0;
            while (this.f7099d) {
                Context context = this.a.get();
                if (context != null) {
                    synchronized (MyMasterData.class) {
                        DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
                        ArrayList<String> arrayList = dataMaster.myAddOns.hueBridgesOrder;
                        Intrinsics.checkNotNull(arrayList);
                        if (arrayList.size() > 0) {
                            HashMap<String, DataHueBridges> hashMap = dataMaster.myAddOns.hueBridges;
                            Intrinsics.checkNotNull(hashMap);
                            ArrayList<String> arrayList2 = dataMaster.myAddOns.hueBridgesOrder;
                            Intrinsics.checkNotNull(arrayList2);
                            dataHueBridges = hashMap.get(arrayList2.get(0));
                        } else {
                            dataHueBridges = null;
                        }
                        if (dataHueBridges != null && (str = dataHueBridges.userName) != null && (str2 = dataHueBridges.ipAddress) != null) {
                            if (i10 == 1) {
                                str3 = "http://" + str2 + "/api/" + str + "/lights";
                            } else {
                                str3 = "http://" + str2 + "/api/" + str + "/sensors";
                            }
                            Request build = new Request.Builder().url(str3).g().build();
                            this.f7098c.set(true);
                            this.f7097b.newCall(build).enqueue(new ThreadHuePollingRunner());
                        }
                        long uptime = CommonFuncs.getUptime();
                        HashMap<String, DataSensor> hashMap2 = dataMaster.mySensors.sensors;
                        Intrinsics.checkNotNull(hashMap2);
                        Iterator it = new ArrayList(hashMap2.keySet()).iterator();
                        boolean z11 = false;
                        while (it.hasNext()) {
                            String str4 = (String) it.next();
                            DataSensor sensor = dataMaster.mySensors.getSensor(str4);
                            if (sensor != null) {
                                Long l8 = sensor.expiryTime;
                                Intrinsics.checkNotNull(l8);
                                if (l8.longValue() < uptime) {
                                    dataMaster.mySensors.removeSensor(str4);
                                    z11 = true;
                                }
                            }
                        }
                        DataSystem dataSystem = new DataSystem();
                        dataSystem.update(dataMaster.system);
                        HashMap<String, DataSensor> hashMap3 = dataMaster.mySensors.sensors;
                        Intrinsics.checkNotNull(hashMap3);
                        if (hashMap3.size() > 0) {
                            Boolean bool = dataMaster.system.hasSensors;
                            if (bool != null) {
                                Intrinsics.checkNotNull(bool);
                                if (!bool.booleanValue()) {
                                }
                                z10 = z7;
                                if (z11) {
                                    SensorDBStore.f7078e.a().d(MyApp.appContextProvider.appContext(), dataMaster);
                                }
                                if (z7) {
                                    AirconDBStore.Companion.getInstance(context).update(context, dataSystem);
                                }
                                if (!z11 || z10) {
                                    HandlerJson.Companion.getInstance(MyApp.appContextProvider.appContext()).processData(dataMaster, "threadHuePolling");
                                }
                                Unit unit = Unit.INSTANCE;
                            }
                            Boolean bool2 = Boolean.TRUE;
                            dataSystem.hasSensors = bool2;
                            dataMaster.system.hasSensors = bool2;
                            z7 = true;
                            z10 = z7;
                            if (z11) {
                            }
                            if (z7) {
                            }
                            if (!z11) {
                                HandlerJson.Companion.getInstance(MyApp.appContextProvider.appContext()).processData(dataMaster, "threadHuePolling");
                                Unit unit2 = Unit.INSTANCE;
                            }
                        } else {
                            Boolean bool3 = dataMaster.system.hasSensors;
                            if (bool3 != null) {
                                Intrinsics.checkNotNull(bool3);
                                if (bool3.booleanValue()) {
                                    dataSystem.hasSensors = Boolean.FALSE;
                                    z7 = true;
                                    z10 = false;
                                    if (z11) {
                                    }
                                    if (z7) {
                                    }
                                    if (!z11) {
                                    }
                                }
                            }
                        }
                        z7 = false;
                        z10 = z7;
                        if (z11) {
                        }
                        if (z7) {
                        }
                        if (!z11) {
                        }
                    }
                    int i11 = 0;
                    while (this.f7098c.get()) {
                        SystemClock.sleep(10L);
                        i11++;
                        if (i11 > 260) {
                            this.f7098c.set(false);
                        }
                    }
                    if (this.f7100e) {
                        SystemClock.sleep(f7096i[i10]);
                    } else {
                        SystemClock.sleep(f7095h[i10]);
                    }
                    i10++;
                    if (i10 >= 3) {
                        break;
                    }
                } else {
                    this.f7099d = false;
                }
            }
            Timber.forest.d("ThreadHuePolling - stopped", new Object[0]);
            return;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private ThreadHuePolling(Context context) {
        this.f7098c = new AtomicBoolean(false);
        setName("ThreadHuePolling");
        this.a = new WeakReference<>(context);
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.t(1);
        this.f7097b = ((OkHttpClient) KoinJavaComponent.get$default(OkHttpClient.class, null, null, 6, null)).newBuilder().callTimeout(2500L, TimeUnit.MILLISECONDS).dispatcher(dispatcher).build();
    }
}