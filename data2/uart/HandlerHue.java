package com.air.advantage.uart;

import android.content.Context;
import android.net.wifi.WifiManager;
import com.air.advantage.ActivityMain;
import com.air.advantage.AppFeatures;
import com.air.advantage.MyApp;
import com.air.advantage.data.DataHueBridges;
import com.air.advantage.data.DataLight;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataSensor;
import com.air.advantage.data.DataSystem;
import com.air.advantage.data.Events;
import com.air.advantage.firebase.FirebaseFunctions;
import com.air.advantage.libraryairconlightjson.CommonFuncs;
import com.air.advantage.libraryairconlightjson.LightState;
import com.air.advantage.membership.MembershipRepository;
import com.air.advantage.membership.model.HueBridgeSerialNumberStatus;
import com.air.advantage.membership.model.MembershipStatus;
import com.air.advantage.uart.MyMasterData;
import com.air.advantage.uart.ThreadHuePolling;
import com.air.advantage.upnp.SsdpRequest;
import com.air.advantage.upnp.SsdpTask;
import com.air.advantage.webserver.WebServer;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

@PurelyImplements({"SMAP\nHandlerHue.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HandlerHue.kt\ncom/air/advantage/uart/HandlerHue\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,767:1\n1603#2,9:768\n1855#2:777\n1856#2:779\n1612#2:780\n1#3:778\n*S KotlinDebug\n*F\n+ 1 HandlerHue.kt\ncom/air/advantage/uart/HandlerHue\n*L\n611#1:768,9\n611#1:777\n611#1:779\n611#1:780\n611#1:778\n*E\n"})
/* renamed from: com.air.advantage.uart.o */
/* loaded from: classes.dex */
public final class HandlerHue {

    /* renamed from: l, reason: collision with root package name */
    public static final int f7137l = 10;

    /* renamed from: m, reason: collision with root package name */
    private static final int f7138m = 254;

    /* renamed from: n, reason: collision with root package name */
    private static final int f7139n = 12;

    /* renamed from: o, reason: collision with root package name */
    private static final int f7140o = 10;

    /* renamed from: p, reason: collision with root package name */
    private static final long f7141p = 60000;

    /* renamed from: r, reason: collision with root package name */
    @Nullable
    private static volatile HandlerHue f7143r;

    @NotNull
    private final Gson a;

    /* renamed from: b, reason: collision with root package name */
    @NotNull
    private final OkHttpClient f7144b;

    /* renamed from: c, reason: collision with root package name */
    @NotNull
    private final HashMap<String, ArrayList<String>> f7145c;

    /* renamed from: d, reason: collision with root package name */
    @NotNull
    private final HashMap<String, Integer> f7146d;

    /* renamed from: e, reason: collision with root package name */
    @NotNull
    private final HashMap<String, ArrayList<String>> f7147e;

    /* renamed from: f, reason: collision with root package name */
    @NotNull
    private final HashMap<String, ArrayList<String>> f7148f;

    /* renamed from: g, reason: collision with root package name */
    private int f7149g;

    /* renamed from: h, reason: collision with root package name */
    private boolean f7150h;

    /* renamed from: i, reason: collision with root package name */
    private boolean f7151i;

    /* renamed from: j, reason: collision with root package name */
    private boolean f7152j;

    /* renamed from: k */
    @NotNull
    public static final Companion Companion = new Companion(null);

    /* renamed from: q, reason: collision with root package name */
    private static final String f7142q = HandlerHue.class.getSimpleName();

    /* renamed from: com.air.advantage.uart.o$a */
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.o.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void c() {
        }

        @JvmStatic
        /* renamed from: a */
        public final void destroy() {
            HandlerHue.f7143r = null;
        }

        @NotNull
        /* renamed from: b */
        public final HandlerHue getInstance() {
            if (HandlerHue.f7143r == null) {
                synchronized (HandlerHue.class) {
                    if (HandlerHue.f7143r == null) {
                        Companion companion = HandlerHue.Companion;
                        HandlerHue.f7143r = new HandlerHue(null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            HandlerHue handlerHue = HandlerHue.f7143r;
            Intrinsics.checkNotNull(handlerHue);
            return handlerHue;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* renamed from: com.air.advantage.uart.o$b */
    public static final class b {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ b[] $VALUES;
        public static final b HUE_LIGHT = new b("HUE_LIGHT", 0);
        public static final b HUE_SENSOR = new b("HUE_SENSOR", 1);

        private static final /* synthetic */ b[] $values() {
            return new b[]{HUE_LIGHT, HUE_SENSOR};
        }

        static {
            b[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private b(String str, int i10) {
        }

        @NotNull
        public static EnumEntries<b> getEntries() {
            return $ENTRIES;
        }

        public static b valueOf(String str) {
            return (b) Enum.valueOf(b.class, str);
        }

        public static b[] values() {
            return (b[]) $VALUES.clone();
        }
    }

    /* renamed from: com.air.advantage.uart.o$c */
    public static final class c implements SsdpTask.b {
        c() {
        }

        @Override // com.air.advantage.upnp.SsdpTask.b
        public void onFinish(@NotNull HashMap<String, SsdpRequest> devices) {
            boolean z7;
            DataHueBridges dataHueBridges;
            Intrinsics.checkNotNullParameter(devices, "devices");
            Context appContext = MyApp.appContextProvider.appContext();
            if (devices.size() > 0) {
                synchronized (MyMasterData.class) {
                    DataMaster dataMaster = MyMasterData.Companion.getDataMaster(appContext);
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
                    if (dataHueBridges == null || dataHueBridges.id == null) {
                        z7 = false;
                    } else {
                        z7 = false;
                        for (SsdpRequest ssdpRequest : devices.values()) {
                            if (Intrinsics.areEqual(dataHueBridges.id, ssdpRequest.getSerialNumber())) {
                                dataHueBridges.ipAddress = ssdpRequest.getHostAddress();
                                com.air.advantage.uart.b.Companion.b().e(appContext, dataMaster);
                                HandlerJson.Companion.getInstance(appContext).processData(dataMaster, "updateHueBridge");
                                z7 = true;
                            }
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                }
            } else {
                z7 = false;
            }
            HandlerHue companion = HandlerHue.Companion.getInstance();
            if (z7) {
                companion.E(appContext, false);
                return;
            }
            HandlerHue.this.f7152j = true;
            synchronized (MyMasterData.class) {
                HandlerJson.Companion.getInstance(appContext).processData(MyMasterData.Companion.getDataMaster(appContext), "handlerHue - can't connect to bridge");
                Unit unit2 = Unit.INSTANCE;
            }
            companion.E(appContext, true);
        }

        @Override // com.air.advantage.upnp.SsdpTask.b
        public void onStart() {
        }
    }

    /* renamed from: com.air.advantage.uart.o$d */
    static final class d extends FunctionBase implements Function1<MembershipStatus, SingleSource<? extends HueBridgeSerialNumberStatus>> {
        final /* synthetic */ String a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        d(String str) {
            super(1);
            this.a = str;
        }

        /* JADX DEBUG: Method merged with bridge method: invoke(Ljava/lang/Object;)Ljava/lang/Object; */
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: c, reason: merged with bridge method [inline-methods] */
        public final SingleSource<? extends HueBridgeSerialNumberStatus> invoke(@NotNull MembershipStatus membershipStatus) {
            Intrinsics.checkNotNullParameter(membershipStatus, "membershipStatus");
            Timber.forest.d("observer - HandlerHue - membership status: %s", membershipStatus);
            if (membershipStatus == MembershipStatus.ActiveMember) {
                return Single.just(HueBridgeSerialNumberStatus.SerialNumberValid);
            }
            FirebaseFunctions firebaseFunctions = (FirebaseFunctions) KoinJavaComponent.get$default(FirebaseFunctions.class, null, null, 6, null);
            String str = this.a;
            Intrinsics.checkNotNull(str);
            return firebaseFunctions.l(str);
        }
    }

    /* renamed from: com.air.advantage.uart.o$e */
    static final class e extends FunctionBase implements Function1<HueBridgeSerialNumberStatus, Unit> {
        final /* synthetic */ Context a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        e(Context context) {
            super(1);
            this.a = context;
        }

        public final void c(@NotNull HueBridgeSerialNumberStatus status) {
            Intrinsics.checkNotNullParameter(status, "status");
            Timber.forest.d("observer - HandlerHue - hue bridge id status: %s", status);
            HandlerHue companion = HandlerHue.Companion.getInstance();
            if (status == HueBridgeSerialNumberStatus.SerialNumberValid || status == HueBridgeSerialNumberStatus.Offline) {
                companion.E(this.a, false);
            } else {
                companion.destroy(this.a);
            }
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
        /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(HueBridgeSerialNumberStatus hueBridgeSerialNumberStatus) {
            c(hueBridgeSerialNumberStatus);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.air.advantage.uart.o$f */
    static final class f extends FunctionBase implements Function1<Long, SingleSource<? extends HueBridgeSerialNumberStatus>> {
        final /* synthetic */ String a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        f(String str) {
            super(1);
            this.a = str;
        }

        /* JADX DEBUG: Method merged with bridge method: invoke(Ljava/lang/Object;)Ljava/lang/Object; */
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: c, reason: merged with bridge method [inline-methods] */
        public final SingleSource<? extends HueBridgeSerialNumberStatus> invoke(@Nullable Long l8) {
            FirebaseFunctions firebaseFunctions = (FirebaseFunctions) KoinJavaComponent.get$default(FirebaseFunctions.class, null, null, 6, null);
            String str = this.a;
            Intrinsics.checkNotNull(str);
            return firebaseFunctions.l(str);
        }
    }

    /* renamed from: com.air.advantage.uart.o$g */
    static final class g extends FunctionBase implements Function1<HueBridgeSerialNumberStatus, Unit> {
        final /* synthetic */ Context a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        g(Context context) {
            super(1);
            this.a = context;
        }

        public final void c(@NotNull HueBridgeSerialNumberStatus status) {
            Intrinsics.checkNotNullParameter(status, "status");
            Timber.forest.d("observer - HandlerHue - hue bridge id status: %s", status);
            HandlerHue companion = HandlerHue.Companion.getInstance();
            if (status == HueBridgeSerialNumberStatus.SerialNumberValid || status == HueBridgeSerialNumberStatus.Offline) {
                companion.E(this.a, false);
            } else {
                companion.destroy(this.a);
            }
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
        /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(HueBridgeSerialNumberStatus hueBridgeSerialNumberStatus) {
            c(hueBridgeSerialNumberStatus);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.air.advantage.uart.o$h */
    public static final class h extends TypeToken<HashMap<String, com.air.advantage.thirdparty.hue.a>> {
        h() {
        }
    }

    /* renamed from: com.air.advantage.uart.o$i */
    public static final class i implements Callback {
        i() {
        }

        @Override // okhttp3.Callback
        /* renamed from: a */
        public void onFailure(@NotNull Call call, @NotNull IOException e7) {
            Intrinsics.checkNotNullParameter(call, "call");
            Intrinsics.checkNotNullParameter(e7, "e");
        }

        @Override // okhttp3.Callback
        /* renamed from: b */
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            Intrinsics.checkNotNullParameter(call, "call");
            Intrinsics.checkNotNullParameter(response, "response");
        }
    }

    /* renamed from: com.air.advantage.uart.o$j */
    public static final class j implements Callback {
        j() {
        }

        @Override // okhttp3.Callback
        /* renamed from: a */
        public void onFailure(@NotNull Call call, @NotNull IOException e7) {
            Intrinsics.checkNotNullParameter(call, "call");
            Intrinsics.checkNotNullParameter(e7, "e");
        }

        @Override // okhttp3.Callback
        /* renamed from: b */
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            Intrinsics.checkNotNullParameter(call, "call");
            Intrinsics.checkNotNullParameter(response, "response");
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.o.<init>():void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ HandlerHue(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @JvmStatic
    public static final void h() {
        Companion.destroy();
    }

    @NotNull
    public static final HandlerHue j() {
        return Companion.getInstance();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final SingleSource m(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (SingleSource) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void n(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final SingleSource o(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (SingleSource) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void p(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final boolean s(com.air.advantage.thirdparty.hue.a aVar) {
        String str;
        return (aVar.type == null || (str = aVar.uniqueid) == null || str.length() <= 0 || aVar.state == null || aVar.name == null) ? false : true;
    }

    private final boolean t(com.air.advantage.thirdparty.hue.a aVar) {
        com.air.advantage.thirdparty.hue.b bVar = aVar.config;
        return (bVar == null || bVar.on == null || bVar.reachable == null) ? false : true;
    }

    private final void v(String str, Integer num) {
        Integer num2;
        ArrayList<String> arrayList;
        Integer num3;
        if (num != null) {
            ArrayList<String> arrayList2 = null;
            for (String str2 : this.f7146d.keySet()) {
                if (this.f7148f.containsKey(str2)) {
                    ArrayList<String> arrayList3 = this.f7148f.get(str2);
                    if (arrayList3 != null && !arrayList3.contains(str) && (arrayList = this.f7147e.get(str2)) != null && arrayList.contains(str) && (num3 = this.f7146d.get(str2)) != null && num.intValue() >= num3.intValue()) {
                        arrayList3.add(str);
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList<>();
                        }
                        arrayList2.add(str2);
                    }
                } else {
                    ArrayList<String> arrayList4 = this.f7147e.get(str2);
                    if (arrayList4 != null && arrayList4.contains(str) && (num2 = this.f7146d.get(str2)) != null && num.intValue() >= num2.intValue()) {
                        ArrayList<String> arrayList5 = new ArrayList<>();
                        arrayList5.add(str);
                        this.f7148f.put(str2, arrayList5);
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList<>();
                        }
                        arrayList2.add(str2);
                    }
                }
            }
            if (arrayList2 == null || arrayList2.size() <= 0) {
                return;
            }
            HandlerMonitor.Companion.b().z(Events.MOTION_SENSOR_TRIGGER_ON_NO_MOTION, str, arrayList2);
        }
    }

    private final void w(String str) {
        ArrayList<String> arrayList = null;
        for (String str2 : this.f7145c.keySet()) {
            ArrayList<String> arrayList2 = this.f7145c.get(str2);
            if (arrayList2 != null && arrayList2.size() > 0 && arrayList2.contains(str)) {
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                arrayList.add(str2);
            }
        }
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        HandlerMonitor.Companion.b().z(Events.MOTION_SENSOR_TRIGGER_ON_MOTION, str, arrayList);
    }

    public final void A(@NotNull DataMaster masterData, @Nullable String str, @Nullable String str2, @Nullable b bVar) {
        DataHueBridges dataHueBridges;
        String str3;
        String str4;
        String str5;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (str != null) {
            if ((str.length() == 0) || str2 == null) {
                return;
            }
            if ((str2.length() == 0) || bVar == null) {
                return;
            }
            String str6 = "{\"name\":\"" + str2 + "\"}";
            ArrayList<String> arrayList = masterData.myAddOns.hueBridgesOrder;
            Intrinsics.checkNotNull(arrayList);
            if (arrayList.size() > 0) {
                HashMap<String, DataHueBridges> hashMap = masterData.myAddOns.hueBridges;
                Intrinsics.checkNotNull(hashMap);
                ArrayList<String> arrayList2 = masterData.myAddOns.hueBridgesOrder;
                Intrinsics.checkNotNull(arrayList2);
                dataHueBridges = hashMap.get(arrayList2.get(0));
            } else {
                dataHueBridges = null;
            }
            if (dataHueBridges == null || (str3 = dataHueBridges.userName) == null || (str4 = dataHueBridges.ipAddress) == null) {
                return;
            }
            if (bVar == b.HUE_LIGHT) {
                str5 = "http://" + str4 + "/api/" + str3 + "/lights/" + str;
            } else {
                str5 = "http://" + str4 + "/api/" + str3 + "/sensors/" + str;
            }
            Request build = new Request.Builder().url(str5).s(RequestBody.Companion.create(str6, MediaType.Companion.c("application/json; charset=utf-8"))).build();
            HandlerHue handlerHue = f7143r;
            Intrinsics.checkNotNull(handlerHue);
            handlerHue.f7144b.newCall(build).enqueue(new i());
        }
    }

    public final void B(@NotNull DataMaster masterData, @NotNull DataLight dataLight) {
        DataHueBridges dataHueBridges;
        LightState lightState;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        Intrinsics.checkNotNullParameter(dataLight, "dataLight");
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (dataLight.state != null) {
            sb.append("\"on\":");
            LightState lightState2 = dataLight.state;
            LightState lightState3 = LightState.on;
            if (lightState2 == lightState3) {
                sb.append("true");
            } else {
                sb.append("false");
            }
            if (dataLight.value != null && dataLight.state == lightState3) {
                sb.append(",");
            }
        }
        Integer num = dataLight.value;
        if (num != null && (lightState = dataLight.state) != null && lightState == LightState.on) {
            Intrinsics.checkNotNull(num);
            int intValue = (num.intValue() * 254) / 100;
            sb.append("\"bri\":");
            sb.append(intValue);
        }
        sb.append("}");
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        if (Intrinsics.areEqual(sb2, "{}")) {
            return;
        }
        ArrayList<String> arrayList = masterData.myAddOns.hueBridgesOrder;
        Intrinsics.checkNotNull(arrayList);
        if (arrayList.size() > 0) {
            HashMap<String, DataHueBridges> hashMap = masterData.myAddOns.hueBridges;
            Intrinsics.checkNotNull(hashMap);
            ArrayList<String> arrayList2 = masterData.myAddOns.hueBridgesOrder;
            Intrinsics.checkNotNull(arrayList2);
            dataHueBridges = hashMap.get(arrayList2.get(0));
        } else {
            dataHueBridges = null;
        }
        if (dataHueBridges == null || dataHueBridges.userName == null || dataHueBridges.ipAddress == null || dataLight.idOnHueBridge == null) {
            return;
        }
        if (sb2.length() == 0) {
            return;
        }
        Request build = new Request.Builder().url("http://" + dataHueBridges.ipAddress + "/api/" + dataHueBridges.userName + "/lights/" + dataLight.idOnHueBridge + "/state").s(RequestBody.Companion.create(sb2, MediaType.Companion.c("application/json; charset=utf-8"))).build();
        HandlerHue handlerHue = f7143r;
        Intrinsics.checkNotNull(handlerHue);
        handlerHue.f7144b.newCall(build).enqueue(new j());
    }

    /* renamed from: C */
    public final int setSensor(@Nullable Context context, @NotNull DataSensor dataSensorFromJson) throws ExceptionUart {
        Intrinsics.checkNotNullParameter(dataSensorFromJson, "dataSensorFromJson");
        Timber.forest.d("setSensor JSON received", new Object[0]);
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            DataSensor sensor = dataMaster.mySensors.getSensor(dataSensorFromJson.id);
            if (sensor != null) {
                A(dataMaster, sensor.idOnHueBridge, dataSensorFromJson.name, b.HUE_SENSOR);
            }
            Unit unit = Unit.INSTANCE;
        }
        return WebServer.ACK;
    }

    /* renamed from: D */
    public final void destroy(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        ThreadHuePolling.f7093f.a(context).destroy();
    }

    public final void E(@NotNull Context context, boolean z7) {
        DataHueBridges dataHueBridges;
        Intrinsics.checkNotNullParameter(context, "context");
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
            Unit unit = Unit.INSTANCE;
        }
        if (dataHueBridges != null) {
            this.f7150h = false;
            this.f7149g = 0;
            ThreadHuePolling a = ThreadHuePolling.f7093f.a(context);
            if (a.d()) {
                return;
            }
            a.f(z7);
            a.start();
        }
    }

    /* renamed from: F */
    public final void setAddHueBridge(@NotNull Context context, @Nullable String str) throws ExceptionUart {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            DataHueBridges dataHueBridges = (DataHueBridges) this.a.fromJson(str, DataHueBridges.class);
            if (dataHueBridges == null) {
                throw new ExceptionUart("Invalid json message");
            }
            if (dataHueBridges.id == null || dataHueBridges.userName == null) {
                throw new ExceptionUart("Invalid bridge data");
            }
            synchronized (MyMasterData.class) {
                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
                if (dataMaster.myAddOns.addHueBridge(dataHueBridges)) {
                    com.air.advantage.uart.b.Companion.b().e(context, dataMaster);
                    HandlerJson.Companion.getInstance(context).processData(dataMaster, "updateHueBridge");
                }
                Unit unit = Unit.INSTANCE;
            }
            E(context, false);
        } catch (JsonIOException unused) {
            Timber.forest.d("Failed to parse message", new Object[0]);
            throw new ExceptionUart("Failed to parse message for updateHueBridge");
        }
    }

    public final void i(@Nullable String str) {
        this.f7145c.remove(str);
        this.f7146d.remove(str);
        this.f7147e.remove(str);
        this.f7148f.remove(str);
    }

    public final void k(@Nullable Call call, @Nullable IOException iOException) {
        int i10 = this.f7149g + 1;
        this.f7149g = i10;
        if (i10 <= 15 || this.f7150h) {
            return;
        }
        this.f7150h = true;
        ThreadHuePolling.a aVar = ThreadHuePolling.f7093f;
        MyApp.AppContextProvider appContextProvider = MyApp.appContextProvider;
        aVar.a(appContextProvider.appContext()).destroy();
        Object systemService = appContextProvider.appContext().getApplicationContext().getSystemService("wifi");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.wifi.WifiManager");
        if (((WifiManager) systemService).isWifiEnabled()) {
            ActivityMain companion = ActivityMain.Companion.getInstance();
            if (companion != null) {
                SsdpTask.Companion.discoveryDevices(companion, new c());
                return;
            }
            return;
        }
        this.f7151i = true;
        synchronized (MyMasterData.class) {
            HandlerJson.Companion.getInstance(appContextProvider.appContext()).processData(MyMasterData.Companion.getDataMaster(appContextProvider.appContext()), "handlerHue - TSP wifi disabled");
            Unit unit = Unit.INSTANCE;
        }
        Companion.getInstance().E(appContextProvider.appContext(), true);
    }

    @NotNull
    public final Disposable l(@NotNull Context context, @Nullable String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (AppFeatures.CheckFeature(AppFeatures.OptionsEnum.MY_MEMBERSHIP)) {
            Observable<MembershipStatus> observeOn = ((MembershipRepository) KoinJavaComponent.get$default(MembershipRepository.class, null, null, 6, null)).getMembershipStatusSubject().distinctUntilChanged().subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
            final d dVar = new d(str);
            Observable observeOn2 = observeOn.switchMapMaybe(new Function() { // from class: com.air.advantage.uart.k
                @Override // io.reactivex.functions.Function
                /* renamed from: apply */
                public final Object mo604apply(Object obj) {
                    return HandlerHue.m(dVar, obj);
                }
            }).observeOn(AndroidSchedulers.mainThread());
            final e eVar = new e(context);
            Disposable D5 = observeOn2.D5(new Consumer() { // from class: com.air.advantage.uart.l
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    HandlerHue.n(eVar, obj);
                }
            });
            Intrinsics.checkNotNull(D5);
            return D5;
        }
        Observable<Long> d3 = Observable.d3(0L, 12L, TimeUnit.HOURS);
        final f fVar = new f(str);
        Observable observeOn3 = d3.switchMapMaybe(new Function() { // from class: com.air.advantage.uart.m
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo604apply(Object obj) {
                return HandlerHue.o(fVar, obj);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        final g gVar = new g(context);
        Disposable D52 = observeOn3.D5(new Consumer() { // from class: com.air.advantage.uart.n
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HandlerHue.p(gVar, obj);
            }
        });
        Intrinsics.checkNotNull(D52);
        return D52;
    }

    public final boolean q() {
        return this.f7152j;
    }

    public final boolean r() {
        return this.f7151i;
    }

    /* JADX WARN: Removed duplicated region for block: B:150:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x01cf A[Catch: all -> 0x021b, TryCatch #2 {, blocks: (B:111:0x00bb, B:113:0x00d1, B:115:0x00db, B:117:0x00df, B:119:0x00e8, B:121:0x00ec, B:123:0x00f5, B:125:0x00f9, B:127:0x00fd, B:129:0x0106, B:131:0x0111, B:132:0x011d, B:134:0x0123, B:137:0x0133, B:139:0x013b, B:141:0x0146, B:148:0x01b3, B:152:0x01cf, B:154:0x01d7, B:156:0x01de, B:158:0x01e6, B:159:0x01ed, B:160:0x0216, B:163:0x0150, B:165:0x015b, B:167:0x0166, B:168:0x0173, B:170:0x017e, B:172:0x0182, B:174:0x019c, B:176:0x01a6, B:178:0x01af, B:180:0x01fa, B:182:0x0204), top: B:110:0x00bb }] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x01de A[Catch: all -> 0x021b, TryCatch #2 {, blocks: (B:111:0x00bb, B:113:0x00d1, B:115:0x00db, B:117:0x00df, B:119:0x00e8, B:121:0x00ec, B:123:0x00f5, B:125:0x00f9, B:127:0x00fd, B:129:0x0106, B:131:0x0111, B:132:0x011d, B:134:0x0123, B:137:0x0133, B:139:0x013b, B:141:0x0146, B:148:0x01b3, B:152:0x01cf, B:154:0x01d7, B:156:0x01de, B:158:0x01e6, B:159:0x01ed, B:160:0x0216, B:163:0x0150, B:165:0x015b, B:167:0x0166, B:168:0x0173, B:170:0x017e, B:172:0x0182, B:174:0x019c, B:176:0x01a6, B:178:0x01af, B:180:0x01fa, B:182:0x0204), top: B:110:0x00bb }] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0150 A[Catch: all -> 0x021b, TryCatch #2 {, blocks: (B:111:0x00bb, B:113:0x00d1, B:115:0x00db, B:117:0x00df, B:119:0x00e8, B:121:0x00ec, B:123:0x00f5, B:125:0x00f9, B:127:0x00fd, B:129:0x0106, B:131:0x0111, B:132:0x011d, B:134:0x0123, B:137:0x0133, B:139:0x013b, B:141:0x0146, B:148:0x01b3, B:152:0x01cf, B:154:0x01d7, B:156:0x01de, B:158:0x01e6, B:159:0x01ed, B:160:0x0216, B:163:0x0150, B:165:0x015b, B:167:0x0166, B:168:0x0173, B:170:0x017e, B:172:0x0182, B:174:0x019c, B:176:0x01a6, B:178:0x01af, B:180:0x01fa, B:182:0x0204), top: B:110:0x00bb }] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0173 A[Catch: all -> 0x021b, TryCatch #2 {, blocks: (B:111:0x00bb, B:113:0x00d1, B:115:0x00db, B:117:0x00df, B:119:0x00e8, B:121:0x00ec, B:123:0x00f5, B:125:0x00f9, B:127:0x00fd, B:129:0x0106, B:131:0x0111, B:132:0x011d, B:134:0x0123, B:137:0x0133, B:139:0x013b, B:141:0x0146, B:148:0x01b3, B:152:0x01cf, B:154:0x01d7, B:156:0x01de, B:158:0x01e6, B:159:0x01ed, B:160:0x0216, B:163:0x0150, B:165:0x015b, B:167:0x0166, B:168:0x0173, B:170:0x017e, B:172:0x0182, B:174:0x019c, B:176:0x01a6, B:178:0x01af, B:180:0x01fa, B:182:0x0204), top: B:110:0x00bb }] */
    /* JADX WARN: Removed duplicated region for block: B:179:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x0387 A[Catch: all -> 0x03bf, TryCatch #5 {, blocks: (B:197:0x034c, B:199:0x0364, B:201:0x0378, B:203:0x037e, B:206:0x03b1, B:207:0x0387, B:208:0x03bb), top: B:196:0x034c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void u(@Nullable String str) {
        boolean z7;
        boolean z10;
        Boolean bool;
        Boolean bool2;
        Boolean bool3;
        String str2;
        Context appContext = MyApp.appContextProvider.appContext();
        boolean z11 = false;
        this.f7149g = 0;
        if (this.f7151i || this.f7152j) {
            this.f7151i = false;
            this.f7152j = false;
            synchronized (MyMasterData.class) {
                HandlerJson.Companion.getInstance(appContext).processData(MyMasterData.Companion.getDataMaster(appContext), "handlerHue - can't connect to bridge");
                Unit unit = Unit.INSTANCE;
            }
        }
        ThreadHuePolling a = ThreadHuePolling.f7093f.a(appContext);
        if (a.e()) {
            a.f(false);
        }
        try {
            HashMap hashMap = (HashMap) this.a.fromJson(str, new h().getType());
            if (hashMap != null) {
                ArrayList arrayList = new ArrayList(hashMap.keySet());
                if (arrayList.size() > 1) {
                    Collections.sort(arrayList);
                }
                Iterator it = arrayList.iterator();
                boolean z12 = false;
                boolean z13 = false;
                boolean z14 = false;
                while (it.hasNext()) {
                    String str3 = (String) it.next();
                    com.air.advantage.thirdparty.hue.a aVar = (com.air.advantage.thirdparty.hue.a) hashMap.get(str3);
                    if (aVar == null || !s(aVar)) {
                        z11 = false;
                    } else {
                        int i10 = 10;
                        if (t(aVar)) {
                            if (Intrinsics.areEqual(aVar.type, com.air.advantage.thirdparty.hue.a.TYPE_STRING_ZLLPRESENCE) || Intrinsics.areEqual(aVar.type, com.air.advantage.thirdparty.hue.a.TYPE_STRING_ZLLLIGHTLEVEL) || Intrinsics.areEqual(aVar.type, com.air.advantage.thirdparty.hue.a.TYPE_STRING_ZLLTEMPERATURE)) {
                                DataSensor dataSensor = new DataSensor();
                                dataSensor.update(str3, aVar);
                                synchronized (MyMasterData.class) {
                                    DataMaster dataMaster = MyMasterData.Companion.getDataMaster(MyApp.appContextProvider.appContext());
                                    DataSensor sensor = dataMaster.mySensors.getSensor(dataSensor.id);
                                    if (sensor != null) {
                                        if (!Intrinsics.areEqual(aVar.type, com.air.advantage.thirdparty.hue.a.TYPE_STRING_ZLLPRESENCE) || (bool = dataSensor.enabled) == null) {
                                            z7 = z11;
                                            z10 = z7;
                                            if (DataSensor.update$default(sensor, dataSensor, null, false, 4, null)) {
                                                z12 = true;
                                            }
                                            if (z7 && this.f7145c.size() > 0) {
                                                w(dataSensor.id);
                                            }
                                            if (z10 && this.f7146d.size() > 0) {
                                                v(sensor.id, sensor.minutesFromLastPresence);
                                            }
                                            sensor.expiryTime = Long.valueOf(CommonFuncs.getUptime() + 10);
                                        } else {
                                            Intrinsics.checkNotNull(bool);
                                            if (bool.booleanValue() && (bool2 = dataSensor.reachable) != null) {
                                                Intrinsics.checkNotNull(bool2);
                                                if (bool2.booleanValue() && (bool3 = dataSensor.presence) != null) {
                                                    Boolean bool4 = sensor.presence;
                                                    if (bool4 != null) {
                                                        Intrinsics.checkNotNull(bool4);
                                                        if (!bool4.booleanValue()) {
                                                            Boolean bool5 = dataSensor.presence;
                                                            Intrinsics.checkNotNull(bool5);
                                                            if (bool5.booleanValue()) {
                                                                sensor.minutesFromLastPresence = 0;
                                                                for (String str4 : this.f7148f.keySet()) {
                                                                    ArrayList<String> arrayList2 = this.f7148f.get(str4);
                                                                    if (arrayList2 != null) {
                                                                        if (arrayList2.contains(dataSensor.id)) {
                                                                            arrayList2.remove(dataSensor.id);
                                                                            if (arrayList2.size() == 0) {
                                                                                this.f7148f.remove(str4);
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                z10 = z11;
                                                                z7 = true;
                                                            } else {
                                                                Boolean bool6 = sensor.presence;
                                                                Intrinsics.checkNotNull(bool6);
                                                                if (bool6.booleanValue()) {
                                                                    Boolean bool7 = dataSensor.presence;
                                                                    Intrinsics.checkNotNull(bool7);
                                                                    if (bool7.booleanValue()) {
                                                                        Boolean bool8 = dataSensor.presence;
                                                                        Intrinsics.checkNotNull(bool8);
                                                                        if (!bool8.booleanValue()) {
                                                                            if (sensor.lastPresenceTimestamp != null) {
                                                                                long currentTimeMillis = System.currentTimeMillis();
                                                                                Long l8 = sensor.lastPresenceTimestamp;
                                                                                Intrinsics.checkNotNull(l8);
                                                                                long longValue = (currentTimeMillis - l8.longValue()) / 60000;
                                                                                if (longValue > 0) {
                                                                                    sensor.minutesFromLastPresence = Integer.valueOf((int) longValue);
                                                                                }
                                                                            }
                                                                            z7 = z11;
                                                                            z10 = true;
                                                                        }
                                                                    } else {
                                                                        dataSensor.lastPresenceTimestamp = Long.valueOf(System.currentTimeMillis());
                                                                        sensor.minutesFromLastPresence = 0;
                                                                    }
                                                                }
                                                            }
                                                            if (DataSensor.update$default(sensor, dataSensor, null, false, 4, null)) {
                                                            }
                                                            if (z7) {
                                                                w(dataSensor.id);
                                                            }
                                                            if (z10) {
                                                                v(sensor.id, sensor.minutesFromLastPresence);
                                                            }
                                                            sensor.expiryTime = Long.valueOf(CommonFuncs.getUptime() + 10);
                                                        }
                                                    } else {
                                                        Intrinsics.checkNotNull(bool3);
                                                        if (bool3.booleanValue()) {
                                                            sensor.minutesFromLastPresence = 0;
                                                        }
                                                    }
                                                    z7 = z11;
                                                    z10 = z7;
                                                    if (DataSensor.update$default(sensor, dataSensor, null, false, 4, null)) {
                                                    }
                                                    if (z7) {
                                                    }
                                                    if (z10) {
                                                    }
                                                    sensor.expiryTime = Long.valueOf(CommonFuncs.getUptime() + 10);
                                                }
                                            }
                                        }
                                    } else if (Intrinsics.areEqual(aVar.type, com.air.advantage.thirdparty.hue.a.TYPE_STRING_ZLLPRESENCE)) {
                                        dataSensor.expiryTime = Long.valueOf(CommonFuncs.getUptime() + 10);
                                        dataMaster.mySensors.addSensor(dataSensor);
                                        z12 = true;
                                    }
                                    Unit unit2 = Unit.INSTANCE;
                                }
                            }
                        } else if (Intrinsics.areEqual(aVar.type, com.air.advantage.thirdparty.hue.a.TYPE_STRING_ON_OFF_LIGHT) || Intrinsics.areEqual(aVar.type, com.air.advantage.thirdparty.hue.a.TYPE_STRING_DIMMABLE_LIGHT) || Intrinsics.areEqual(aVar.type, com.air.advantage.thirdparty.hue.a.TYPE_STRING_COLOR_TEMPERATURE_LIGHT) || Intrinsics.areEqual(aVar.type, com.air.advantage.thirdparty.hue.a.TYPE_STRING_COLOR_LIGHT) || Intrinsics.areEqual(aVar.type, com.air.advantage.thirdparty.hue.a.TYPE_STRING_EXTENDED_COLOR_LIGHT)) {
                            com.air.advantage.thirdparty.hue.c cVar = aVar.state;
                            Intrinsics.checkNotNull(cVar);
                            if (cVar.reachable != null) {
                                synchronized (MyMasterData.class) {
                                    DataMaster dataMaster2 = MyMasterData.Companion.getDataMaster(MyApp.appContextProvider.appContext());
                                    HandlerLights companion = HandlerLights.Companion.getInstance();
                                    boolean z15 = aVar.state.bri == null;
                                    String str5 = aVar.name;
                                    Intrinsics.checkNotNull(str5);
                                    if (str5.length() > 12) {
                                        str2 = aVar.name.substring(0, 12);
                                        Intrinsics.checkNotNullExpressionValue(str2, "this as java.lang.String…ing(startIndex, endIndex)");
                                    } else {
                                        str2 = aVar.name;
                                    }
                                    if (companion.registerOrUpdateLight(dataMaster2, aVar.uniqueid, z15, DataLight.MODULE_TYPE_STRING_HUE, false)) {
                                        z13 = true;
                                    }
                                    Boolean bool9 = aVar.state.on;
                                    Intrinsics.checkNotNull(bool9);
                                    LightState lightState = (bool9.booleanValue() && Intrinsics.areEqual(aVar.state.reachable, Boolean.TRUE)) ? LightState.on : LightState.off;
                                    if (aVar.state.bri != null) {
                                        int round = Math.round(((r13.intValue() * 100) / 254) / 5.0f) * 5;
                                        if (round >= 10) {
                                            i10 = round;
                                        }
                                    } else {
                                        i10 = 0;
                                    }
                                    if (companion.updateLight(dataMaster2, aVar.uniqueid, lightState, Integer.valueOf(i10), true)) {
                                        z13 = true;
                                    }
                                    TreeMap<String, DataLight> treeMap = dataMaster2.myLights.lights;
                                    DataLight dataLight = treeMap != null ? treeMap.get(aVar.uniqueid) : null;
                                    if (dataLight != null) {
                                        String str6 = dataLight.name;
                                        if (str6 == null || !Intrinsics.areEqual(str6, str2)) {
                                            dataLight.name = str2;
                                            z13 = true;
                                        }
                                        String str7 = dataLight.idOnHueBridge;
                                        if (str7 == null || !Intrinsics.areEqual(str7, str3)) {
                                            dataLight.idOnHueBridge = str3;
                                            z13 = true;
                                        }
                                        Boolean bool10 = dataLight.reachable;
                                        if (bool10 == null || !Intrinsics.areEqual(bool10, aVar.state.reachable)) {
                                            dataLight.reachable = aVar.state.reachable;
                                            z13 = true;
                                        }
                                    }
                                    Unit unit3 = Unit.INSTANCE;
                                }
                                z11 = false;
                                z14 = true;
                            }
                        }
                        z11 = false;
                    }
                }
                if (z12 || z13 || z14) {
                    synchronized (MyMasterData.class) {
                        MyMasterData.a aVar2 = MyMasterData.Companion;
                        MyApp.AppContextProvider appContextProvider = MyApp.appContextProvider;
                        DataMaster dataMaster3 = aVar2.getDataMaster(appContextProvider.appContext());
                        HandlerJson companion2 = HandlerJson.Companion.getInstance(appContextProvider.appContext());
                        if (z12) {
                            SensorDBStore.f7078e.a().d(appContextProvider.appContext(), dataMaster3);
                            companion2.processData(dataMaster3, "processHueMessage - sensor update");
                        }
                        if (z14) {
                            Boolean bool11 = dataMaster3.system.hasLights;
                            if (bool11 != null) {
                                Intrinsics.checkNotNull(bool11);
                                if (!bool11.booleanValue()) {
                                    DataSystem dataSystem = new DataSystem();
                                    dataSystem.update(dataMaster3.system);
                                    Boolean bool12 = Boolean.TRUE;
                                    dataSystem.hasLights = bool12;
                                    dataMaster3.system.hasLights = bool12;
                                    AirconDBStore.Companion.getInstance(appContextProvider.appContext()).update(appContextProvider.appContext(), dataSystem);
                                    companion2.processData(dataMaster3, "processHueMessage - hasLight");
                                }
                                if (z13) {
                                    dataMaster3.myLights.updateGroupStates();
                                    companion2.processData(dataMaster3, "processHueMessage - light status update");
                                }
                            }
                        }
                        Unit unit4 = Unit.INSTANCE;
                    }
                }
            }
        } catch (JsonIOException unused) {
            Timber.forest.d("Failed to parse hue device message", new Object[0]);
        } catch (Exception e7) {
            CommonFuncs.logException(e7, "");
        }
    }

    public final void x(@Nullable String str, @Nullable ArrayList<String> arrayList) {
        this.f7147e.remove(str);
        this.f7146d.remove(str);
        this.f7148f.remove(str);
        this.f7145c.put(str, new ArrayList<>(arrayList));
        synchronized (MyMasterData.class) {
            HashMap<String, DataSensor> hashMap = MyMasterData.Companion.getDataMaster(MyApp.appContextProvider.appContext()).mySensors.sensors;
            Intrinsics.checkNotNull(hashMap);
            Collection<DataSensor> values = hashMap.values();
            Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
            ArrayList<DataSensor> arrayList2 = new ArrayList();
            for (DataSensor dataSensor : values) {
                if (dataSensor != null) {
                    arrayList2.add(dataSensor);
                }
            }
            for (DataSensor dataSensor2 : arrayList2) {
                Boolean bool = dataSensor2.presence;
                if (bool != null) {
                    Intrinsics.checkNotNull(bool);
                    if (bool.booleanValue()) {
                        w(dataSensor2.id);
                    }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void y(@Nullable String str, @Nullable Integer num, @Nullable ArrayList<String> arrayList) {
        this.f7145c.remove(str);
        this.f7146d.put(str, num);
        this.f7147e.put(str, new ArrayList<>(arrayList));
    }

    /* renamed from: z */
    public final void setRemoveHueBridge(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        destroy(context);
        this.f7151i = false;
        this.f7152j = false;
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            dataMaster.myAddOns.clearHueBridges();
            com.air.advantage.uart.b.Companion.b().e(context, dataMaster);
            dataMaster.mySensors.clearSensors();
            SensorDBStore.f7078e.a().d(context, dataMaster);
            dataMaster.system.hasSensors = Boolean.FALSE;
            AirconDBStore.Companion.getInstance(context).update(context, dataMaster.system);
            HandlerJson.Companion.getInstance(context).processData(dataMaster, "removeHueBridge");
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private HandlerHue() {
        this.a = new Gson();
        this.f7145c = new HashMap<>();
        this.f7146d = new HashMap<>();
        this.f7147e = new HashMap<>();
        this.f7148f = new HashMap<>();
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.t(1);
        this.f7144b = ((OkHttpClient) KoinJavaComponent.get$default(OkHttpClient.class, null, null, 6, null)).newBuilder().callTimeout(2500L, TimeUnit.MILLISECONDS).dispatcher(dispatcher).build();
    }
}