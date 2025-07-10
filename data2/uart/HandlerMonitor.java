package com.air.advantage.uart;

import android.content.Context;
import android.content.Intent;
import androidx.exifinterface.media.ExifInterface;
import com.air.advantage.ActivityMain;
import com.air.advantage.AppFeatures;
import com.air.advantage.FirebaseComms;
import com.air.advantage.MyApp;
import com.air.advantage.UartStrings;
import com.air.advantage.data.DataAirconSystem;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataMonitor;
import com.air.advantage.data.DataMonitorActions;
import com.air.advantage.data.DataScene;
import com.air.advantage.data.DataSensor;
import com.air.advantage.data.DataSystem;
import com.air.advantage.data.DataZone;
import com.air.advantage.data.Events;
import com.air.advantage.di.RxBinding;
import com.air.advantage.myair5.R;
import com.air.advantage.notification.NotificationData;
import com.air.advantage.notification.NotificationService;
import com.air.advantage.notification.NotificationType;
import com.air.advantage.weather.WeatherListener;
import com.air.advantage.weather.WeatherUtility;
import com.air.advantage.weather.livedata.WeatherDataBomService;
import com.air.advantage.weather.livedata.WeatherDataOpenWeatherMapService;
import com.air.advantage.weather.openweatherapi.Main;
import com.air.advantage.weather.openweatherapi.MainWeatherData;
import com.air.advantage.weather.openweatherapi.Sys;
import com.air.advantage.weather.room.db.WeatherDataItem;
import com.air.advantage.weather.room.model.forecast.Area;
import com.air.advantage.weather.room.model.forecast.ForecastPeriod;
import com.air.advantage.weather.room.model.forecast.Text;
import com.air.advantage.weather.room.model.observations.BomObservation;
import com.air.advantage.weather.room.model.observations.WeatherData;
import com.air.advantage.webserver.WebServer;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import io.reactivex.disposables.CompositeDisposable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

@PurelyImplements({"SMAP\nHandlerMonitor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HandlerMonitor.kt\ncom/air/advantage/uart/HandlerMonitor\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,1202:1\n1603#2,9:1203\n1855#2:1212\n1856#2:1214\n1612#2:1215\n731#2,9:1216\n1603#2,9:1227\n1855#2:1236\n1856#2:1238\n1612#2:1239\n1603#2,9:1240\n1855#2:1249\n1856#2:1251\n1612#2:1252\n1603#2,9:1253\n1855#2:1262\n1856#2:1264\n1612#2:1265\n1603#2,9:1266\n1855#2:1275\n1856#2:1277\n1612#2:1278\n1#3:1213\n1#3:1237\n1#3:1250\n1#3:1263\n1#3:1276\n37#4,2:1225\n*S KotlinDebug\n*F\n+ 1 HandlerMonitor.kt\ncom/air/advantage/uart/HandlerMonitor\n*L\n148#1:1203,9\n148#1:1212\n148#1:1214\n148#1:1215\n257#1:1216,9\n543#1:1227,9\n543#1:1236\n543#1:1238\n543#1:1239\n690#1:1240,9\n690#1:1249\n690#1:1251\n690#1:1252\n744#1:1253,9\n744#1:1262\n744#1:1264\n744#1:1265\n1157#1:1266,9\n1157#1:1275\n1157#1:1277\n1157#1:1278\n148#1:1213\n543#1:1237\n690#1:1250\n744#1:1263\n1157#1:1276\n257#1:1225,2\n*E\n"})
/* renamed from: com.air.advantage.uart.s */
/* loaded from: classes.dex */
public final class HandlerMonitor extends FRLParser {

    /* renamed from: f0 */
    @NotNull
    public static final a Companion = new a(null);

    /* renamed from: g0 */
    private static final String f7191g0 = HandlerMonitor.class.getSimpleName();

    /* renamed from: h0 */
    @NotNull
    private static final String f7192h0 = "[0-9a-f]+$";

    /* renamed from: i0 */
    private static final int f7193i0 = 12;

    /* renamed from: j0 */
    private static final long RETRY_PERIOD_MS = 300000;

    /* renamed from: k0 */
    private static final long BOM_POLLING_PERIOD_MS = 3600000;

    /* renamed from: l0 */
    private static final long f7194l0 = 86400000;

    /* renamed from: m0 */
    private static final int f7195m0 = 2;

    /* renamed from: n0 */
    @Nullable
    private static volatile HandlerMonitor f7196n0;

    @NotNull
    private final AtomicLong U;

    /* renamed from: V */
    @NotNull
    private final AtomicLong retryTime;

    /* renamed from: W */
    @NotNull
    private final AtomicBoolean isClearWeather;

    /* renamed from: X */
    @NotNull
    private final AtomicLong airTemp;

    @NotNull
    private final AtomicBoolean Y;

    /* renamed from: Z */
    @NotNull
    private final AtomicBoolean isSunnyWeather;

    /* renamed from: a0 */
    @NotNull
    private final AtomicInteger f7197a0;

    /* renamed from: b0 */
    @NotNull
    private final AtomicInteger retryCount;

    /* renamed from: c0 */
    @NotNull
    private final Gson f7198c0;

    /* renamed from: d0 */
    @NotNull
    private final ArrayList f7199d0;

    /* renamed from: e0 */
    @NotNull
    private final CompositeDisposable f7200e0;

    /* renamed from: com.air.advantage.uart.s$a */
    public static final class a {
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void c() {
        }

        @JvmStatic
        /* renamed from: a */
        public final void destroy() {
            HandlerMonitor.o(null);
        }

        @NotNull
        public final HandlerMonitor b() {
            if (HandlerMonitor.f() == null) {
                synchronized (HandlerLights.class) {
                    if (HandlerMonitor.f() == null) {
                        a aVar = HandlerMonitor.Companion;
                        HandlerMonitor.o(new HandlerMonitor(null));
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            HandlerMonitor f3 = HandlerMonitor.f();
            Intrinsics.checkNotNull(f3);
            return f3;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* renamed from: com.air.advantage.uart.s$b */
    public static final class b implements WeatherDataOpenWeatherMapService.WeatherDataListener {

        /* renamed from: b */
        final /* synthetic */ Context f7201b;

        b(Context context) {
            this.f7201b = context;
        }

        /* renamed from: b */
        public void onFailure() {
            HandlerMonitor.m(HandlerMonitor.this).set(false);
            int i10 = HandlerMonitor.h(HandlerMonitor.this).get();
            if (i10 > 0) {
                HandlerMonitor.l(HandlerMonitor.this).set(System.currentTimeMillis());
                HandlerMonitor.h(HandlerMonitor.this).set(i10 - 1);
            } else {
                AppFeatures.logError(AppFeatures.instance, new RuntimeException("Unable to get suburb temp, No retry attempt left"), null, 2, null);
            }
            HandlerMonitor.p(HandlerMonitor.this, this.f7201b);
        }

        /* JADX DEBUG: Method merged with bridge method: a(Ljava/lang/Object;)V */
        /* renamed from: c */
        public void onResponse(@Nullable MainWeatherData mainWeatherData) {
            if (mainWeatherData == null) {
                return;
            }
            Main main = mainWeatherData.getMain();
            Double temp = main != null ? main.getTemp() : null;
            if (temp == null) {
                onFailure();
                return;
            }
            double doubleValue = temp.doubleValue() - 273.15d;
            Timber.Forest forest = Timber.forest;
            String name = mainWeatherData.getName();
            Sys sys = mainWeatherData.getSys();
            String country = sys != null ? sys.getCountry() : null;
            Sys sys2 = mainWeatherData.getSys();
            forest.d(name + " " + country + ", airtemp* = " + doubleValue + ", sunset =" + (sys2 != null ? sys2.getSunset() : null), new Object[0]);
            HandlerMonitor.k(HandlerMonitor.this).set(Double.doubleToLongBits(doubleValue));
            HandlerMonitor.m(HandlerMonitor.this).set(true);
            HandlerMonitor.q(HandlerMonitor.this, this.f7201b, doubleValue);
            HandlerMonitor.h(HandlerMonitor.this).set(2);
        }
    }

    /* renamed from: com.air.advantage.uart.s$c */
    static final class WeatherFunctionCall extends FunctionBase implements Function1 {

        /* renamed from: b */
        final /* synthetic */ Context context;

        /* renamed from: c */
        final /* synthetic */ WeatherDataItem weatherDataItem;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        WeatherFunctionCall(Context context, WeatherDataItem weatherDataItem) {
            super(1);
            this.context = context;
            this.weatherDataItem = weatherDataItem;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        /* renamed from: invoke */
        public /* bridge */ /* synthetic */ Object mo2781invoke(Object obj) {
            invoke2((BomObservation) obj);
            return Unit.INSTANCE;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        /* renamed from: invoke */
        public final void invoke2(@NotNull BomObservation weatherObservations) {
            List<com.air.advantage.weather.room.model.observations.WeatherDataItem> data;
            com.air.advantage.weather.room.model.observations.WeatherDataItem weatherDataItem;
            Intrinsics.checkNotNullParameter(weatherObservations, "weatherObservations");
            WeatherData weatherData = weatherObservations.getWeatherData();
            Double d3 = null;
            if (weatherData == null || (data = weatherData.getData()) == null || (weatherDataItem = (com.air.advantage.weather.room.model.observations.WeatherDataItem) CollectionsKt___CollectionsKt.D2(data)) == null) {
                WeatherDataItem weatherDataItem2 = this.weatherDataItem;
                AppFeatures.logError(AppFeatures.instance, new RuntimeException("Empty observatory data from: " + weatherDataItem2.getName()), null, 2, null);
                return;
            }
            Integer num = weatherDataItem.sortOrder;
            if (num == null || num.intValue() != 0 || weatherDataItem.getAirTemp() == null) {
                return;
            }
            try {
                Object airTemp = weatherDataItem.getAirTemp();
                Intrinsics.checkNotNull(airTemp, "null cannot be cast to non-null type kotlin.Double");
                Double d10 = (Double) airTemp;
                Timber.forest.d(weatherDataItem.name + ", airTemp = " + d10, new Object[0]);
                d3 = d10;
            } catch (Exception unused) {
            }
            if (d3 != null) {
                HandlerMonitor.k(HandlerMonitor.this).set(Double.doubleToLongBits(d3.doubleValue()));
                HandlerMonitor.m(HandlerMonitor.this).set(true);
                HandlerMonitor.h(HandlerMonitor.this).set(2);
                HandlerMonitor.q(HandlerMonitor.this, this.context, d3.doubleValue());
            }
        }
    }

    /* renamed from: com.air.advantage.uart.s$d */
    static final class d extends FunctionBase implements Function1 {

        /* renamed from: b */
        final /* synthetic */ Context f7202b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        d(Context context) {
            super(1);
            this.f7202b = context;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        /* renamed from: invoke */
        public /* bridge */ /* synthetic */ Object mo2781invoke(Object obj) {
            invoke2((Throwable) obj);
            return Unit.INSTANCE;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        /* renamed from: invoke */
        public final void invoke2(@NotNull Throwable it) {
            Intrinsics.checkNotNullParameter(it, "it");
            Timber.forest.d("retrying requestWeatherDataByLocation, because of null response", new Object[0]);
            int i10 = HandlerMonitor.h(HandlerMonitor.this).get();
            if (i10 > 0) {
                HandlerMonitor.l(HandlerMonitor.this).set(System.currentTimeMillis());
                HandlerMonitor.h(HandlerMonitor.this).set(i10 - 1);
            } else {
                AppFeatures.logError(AppFeatures.instance, new RuntimeException("Unable to get suburb temp, No retry attempt left"), null, 2, null);
            }
            HandlerMonitor.p(HandlerMonitor.this, this.f7202b);
        }
    }

    /* renamed from: com.air.advantage.uart.s$e */
    public static final class e implements WeatherListener.Listener {
        e() {
        }

        /* renamed from: b */
        public void retryHandler(@Nullable String str) {
            HandlerMonitor.n(HandlerMonitor.this).set(false);
            int i10 = HandlerMonitor.g(HandlerMonitor.this).get();
            if (i10 <= 0) {
                AppFeatures.logError(AppFeatures.instance, new RuntimeException("Unable to get cloud coverage to determine pv condition, No retry attempt left"), null, 2, null);
                return;
            }
            HandlerMonitor.j(HandlerMonitor.this).set(System.currentTimeMillis() + 300000);
            HandlerMonitor.g(HandlerMonitor.this).set(i10 - 1);
        }

        /* JADX DEBUG: Method merged with bridge method: a(Ljava/lang/Object;)V */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00a9  */
        /* renamed from: c */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void setForcast(@Nullable Area area) {
            List<Text> text;
            String content;
            List<ForecastPeriod> forecastPeriod = area != null ? area.getForecastPeriod() : null;
            if (forecastPeriod == null) {
                retryHandler("response was null");
                return;
            }
            for (ForecastPeriod forecastPeriod2 : forecastPeriod) {
                Integer index = forecastPeriod2.getIndex();
                if (index != null && index.intValue() == 0 && (text = forecastPeriod2.getText()) != null) {
                    for (Text text2 : text) {
                        if (Intrinsics.areEqual(text2.getType(), "precis") && (content = text2.getContent()) != null) {
                            boolean z7 = false;
                            Timber.forest.d("forecast summary for " + area.getDescription() + ": " + content, new Object[0]);
                            AtomicBoolean n10 = HandlerMonitor.n(HandlerMonitor.this);
                            if (Intrinsics.areEqual(content, "Clear.") || Intrinsics.areEqual(content, "Mostly clear.")) {
                                z7 = true;
                                n10.set(z7);
                                HandlerMonitor.i(HandlerMonitor.this).set(true);
                                HandlerMonitor.g(HandlerMonitor.this).set(2);
                            } else {
                                Locale locale = Locale.getDefault();
                                Intrinsics.checkNotNullExpressionValue(locale, "getDefault(...)");
                                String lowerCase = content.toLowerCase(locale);
                                Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(locale)");
                                if (StringsKt__StringsKt.startsWith$default(lowerCase, "sunny", false, 2, null)) {
                                }
                                n10.set(z7);
                                HandlerMonitor.i(HandlerMonitor.this).set(true);
                                HandlerMonitor.g(HandlerMonitor.this).set(2);
                            }
                        }
                    }
                    return;
                }
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ HandlerMonitor(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private final void B(Context context, FirebaseComms firebaseComms, DataMaster dataMaster, DataMonitor dataMonitor) {
        HandlerLights companion = HandlerLights.Companion.getInstance();
        HandlerAircon companion2 = HandlerAircon.Companion.getInstance();
        DataScene dataScene = new DataScene();
        dataScene.id = "Event - " + dataMonitor.id;
        DataMonitorActions dataMonitorActions = dataMonitor.actions;
        dataScene.aircons = dataMonitorActions.aircons;
        dataScene.lights = dataMonitorActions.lights;
        dataScene.things = dataMonitorActions.things;
        dataScene.sonos = dataMonitorActions.sonos;
        dataScene.myTimeEnabled = Boolean.FALSE;
        dataScene.timerEnabled = Boolean.TRUE;
        dataScene.name = "Event - " + dataMonitor.name;
        Timber.Forest forest = Timber.forest;
        boolean z7 = false;
        forest.d("running auto action", new Object[0]);
        DataScene V = companion.V(context, companion.f0(context, dataScene, dataMaster), dataMaster);
        String L = companion.L(context, V);
        if (companion.o0(context, L, V, dataMaster)) {
            forest.d("checkAndRunSceneSchedule - sending out scene CAN msgs from Scheduler: " + L, new Object[0]);
            z7 = true;
        }
        boolean checkAndRunSceneSchedule = companion2.checkAndRunSceneSchedule(V);
        if (firebaseComms != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Event triggered, ");
            String str = dataMonitor.id;
            if (str != null) {
                sb.append(str);
                sb.append(" - ");
            } else {
                sb.append("null event id - ");
            }
            String str2 = dataMonitor.name;
            if (str2 != null) {
                sb.append(str2);
            } else {
                sb.append("null event name");
            }
            if (z7) {
                sb.append(", sent CAN");
            }
            if (checkAndRunSceneSchedule) {
                sb.append(", sent XML");
            }
            String sb2 = sb.toString();
            Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
            firebaseComms.parseUrl(sb2, ExifInterface.GPS_DIRECTION_TRUE);
        }
    }

    private final boolean C(Context context, DataMaster dataMaster, DataMonitor dataMonitor) {
        dataMonitor.generateSummary(dataMaster);
        if (!dataMaster.myMonitors.addMonitor(dataMonitor)) {
            return false;
        }
        Timber.forest.d("saveMonitor - success - saving - " + dataMonitor.id, new Object[0]);
        MyMonitors.Companion.b().e(context, dataMaster);
        this.f7199d0.remove(dataMonitor.id);
        r(context);
        return true;
    }

    private final void D(DataMaster dataMaster, HashMap hashMap, HashMap hashMap2, HashMap hashMap3, String str) {
        HashMap hashMap4 = hashMap;
        if (hashMap4 == null || hashMap.size() <= 0) {
            return;
        }
        Timber.forest.d("Sending notifications", new Object[0]);
        String str2 = dataMaster.system.name;
        String str3 = str2 != null ? str2 + " Event Alert!" : "Event Alert!";
        NotificationService notificationService = (NotificationService) KoinJavaComponent.get$default(NotificationService.class, null, null, 6, null);
        for (String str4 : hashMap.keySet()) {
            String str5 = (String) hashMap4.get(str4);
            String str6 = (String) hashMap2.get(str4);
            String str7 = (String) hashMap3.get(str4);
            NotificationType notificationType = NotificationType.MONITOR;
            Intrinsics.checkNotNull(str4);
            Intrinsics.checkNotNull(str5);
            NotificationData notificationData = new NotificationData(notificationType, str4, str3, str5, str6, str7, null, Boolean.TRUE, null);
            notificationService.sendNotification(notificationData);
            notificationService.parse(notificationData);
            hashMap4 = hashMap;
        }
    }

    /* renamed from: G */
    private final void weatherUpdate(Context context) {
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            DataSystem dataSystem = new DataSystem();
            dataSystem.update(dataMaster.system);
            dataSystem.isValidSuburbTemp = Boolean.FALSE;
            if (HandlerAircon.Companion.getInstance().update(dataMaster, dataSystem)) {
                Timber.forest.d("suburb temp has changed", new Object[0]);
                HandlerJson.Companion.getInstance(context).processData(dataMaster, "weather update");
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    /* renamed from: H */
    private final void weatherUpdate(Context context, double d3) {
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            DataSystem dataSystem = new DataSystem();
            dataSystem.update(dataMaster.system);
            dataSystem.suburbTemp = Double.valueOf(d3);
            dataSystem.isValidSuburbTemp = Boolean.TRUE;
            if (HandlerAircon.Companion.getInstance().update(dataMaster, dataSystem)) {
                Timber.forest.d("suburb temp has changed", new Object[0]);
                HandlerJson.Companion.getInstance(context).processData(dataMaster, "weather update");
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    private final boolean J(String str) {
        return !(str == null || str.length() == 0) && StringsKt__StringsJVMKt.startsWith(str, "n", false, 2, null) && t(str) == 5;
    }

    public static final /* synthetic */ HandlerMonitor f() {
        return f7196n0;
    }

    public static final /* synthetic */ AtomicInteger g(HandlerMonitor handlerMonitor) {
        return handlerMonitor.retryCount;
    }

    public static final /* synthetic */ AtomicInteger h(HandlerMonitor handlerMonitor) {
        return handlerMonitor.f7197a0;
    }

    public static final /* synthetic */ AtomicBoolean i(HandlerMonitor handlerMonitor) {
        return handlerMonitor.isSunnyWeather;
    }

    public static final /* synthetic */ AtomicLong j(HandlerMonitor handlerMonitor) {
        return handlerMonitor.retryTime;
    }

    public static final /* synthetic */ AtomicLong k(HandlerMonitor handlerMonitor) {
        return handlerMonitor.airTemp;
    }

    public static final /* synthetic */ AtomicLong l(HandlerMonitor handlerMonitor) {
        return handlerMonitor.U;
    }

    public static final /* synthetic */ AtomicBoolean m(HandlerMonitor handlerMonitor) {
        return handlerMonitor.Y;
    }

    public static final /* synthetic */ AtomicBoolean n(HandlerMonitor handlerMonitor) {
        return handlerMonitor.isClearWeather;
    }

    public static final /* synthetic */ void o(HandlerMonitor handlerMonitor) {
        f7196n0 = handlerMonitor;
    }

    public static final /* synthetic */ void p(HandlerMonitor handlerMonitor, Context context) {
        handlerMonitor.weatherUpdate(context);
    }

    public static final /* synthetic */ void q(HandlerMonitor handlerMonitor, Context context, double d3) {
        handlerMonitor.weatherUpdate(context, d3);
    }

    @JvmStatic
    public static final void s() {
        Companion.destroy();
    }

    private final int t(String str) {
        List emptyList;
        List<String> split = new Regex("").split(str, 0);
        if (split.isEmpty()) {
            emptyList = CollectionsKt.emptyList();
        } else {
            ListIterator<String> listIterator = split.listIterator(split.size());
            while (listIterator.hasPrevious()) {
                if (!(listIterator.previous().length() == 0)) {
                    emptyList = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                    break;
                }
            }
            emptyList = CollectionsKt.emptyList();
        }
        int i10 = 0;
        for (String str2 : (String[]) emptyList.toArray(new String[0])) {
            if (new Regex(HandlerLights.hexRegx).matches(str2)) {
                i10++;
            }
        }
        return i10;
    }

    private final boolean u(Context context, DataMaster dataMaster, String str) {
        boolean z7 = false;
        if (!dataMaster.myMonitors.removeMonitor(str)) {
            return false;
        }
        ArrayList<String> arrayList = dataMaster.myMonitors.monitorsOrder;
        Intrinsics.checkNotNull(arrayList);
        if (!arrayList.remove(str)) {
            Timber.forest.d("Failed to delete monitor from the monitor order, monitorId may not exist in the array", new Object[0]);
        }
        MyMonitors.Companion.b().e(context, dataMaster);
        TreeMap<String, DataScene> treeMap = dataMaster.myScenes.scenes;
        Intrinsics.checkNotNull(treeMap);
        Collection<DataScene> values = treeMap.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        ArrayList<DataScene> arrayList2 = new ArrayList();
        for (DataScene dataScene : values) {
            if (dataScene != null) {
                arrayList2.add(dataScene);
            }
        }
        for (DataScene dataScene2 : arrayList2) {
            HashMap hashMap = dataScene2.monitors;
            if (hashMap != null) {
                Intrinsics.checkNotNull(hashMap);
                if (hashMap.size() > 0) {
                    HashMap hashMap2 = dataScene2.monitors;
                    Intrinsics.checkNotNull(hashMap2);
                    if (hashMap2.containsKey(str)) {
                        HashMap hashMap3 = dataScene2.monitors;
                        Intrinsics.checkNotNull(hashMap3);
                        hashMap3.remove(str);
                        dataScene2.summary = dataScene2.generateSummary(dataMaster);
                        z7 = true;
                    }
                }
            }
        }
        if (z7) {
            d0.Companion.b().e(context, dataMaster);
        }
        this.f7199d0.remove(str);
        r(context);
        return true;
    }

    @NotNull
    public static final HandlerMonitor v() {
        return Companion.b();
    }

    private final boolean w(DataMaster dataMaster) {
        Collection<DataAirconSystem> values = dataMaster.aircons.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        ArrayList arrayList = new ArrayList();
        for (DataAirconSystem dataAirconSystem : values) {
            if (dataAirconSystem != null) {
                arrayList.add(dataAirconSystem);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            DataAirconSystem.CodeStatus codeStatus = ((DataAirconSystem) it.next()).info.activationCodeStatus;
            if (codeStatus != null) {
                Intrinsics.checkNotNull(codeStatus);
                if (codeStatus.value >= DataAirconSystem.CodeStatus.expired.value) {
                    return true;
                }
            }
        }
        return false;
    }

    private final void x(Context context, String str) {
        Intent launchIntentForPackage;
        if ((str == null || str.length() == 0) || (launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str)) == null) {
            return;
        }
        launchIntentForPackage.setFlags(268435456);
        context.startActivity(launchIntentForPackage);
        Intent intent = new Intent(UartStrings.INSTANCE.WAKE_SCREEN_U());
        intent.setPackage("com.air.advantage.aawakeupscreen");
        context.sendBroadcast(intent);
    }

    public final void A() {
        Timber.forest.d("refreshWeatherDataOnNextUpdateCheck", new Object[0]);
        this.Y.set(false);
        this.isSunnyWeather.set(false);
        this.U.set(System.currentTimeMillis());
        this.retryTime.set(System.currentTimeMillis());
        this.f7197a0.set(2);
        this.retryCount.set(2);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x007f A[Catch: all -> 0x0112, TryCatch #0 {, blocks: (B:10:0x0034, B:12:0x0041, B:14:0x0049, B:18:0x0054, B:20:0x005d, B:22:0x00eb, B:26:0x00fa, B:30:0x007f, B:32:0x00be, B:34:0x00dd, B:35:0x00e5, B:38:0x0102, B:39:0x0109, B:40:0x010a, B:41:0x0111), top: B:9:0x0034 }] */
    /* renamed from: E */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int setMonitor(@NotNull Context context, @Nullable String str, @Nullable Boolean bool) throws ExceptionUart {
        Object obj;
        boolean C;
        Intrinsics.checkNotNullParameter(context, "context");
        Timber.forest.d("setMonitor JSON received", new Object[0]);
        try {
            obj = this.f7198c0.fromJson(str, DataMonitor.class);
        } catch (JsonParseException e7) {
            e7.printStackTrace();
            obj = null;
        }
        if (obj == null) {
            throw new ExceptionUart("Invalid json message");
        }
        String str2 = ((DataMonitor) obj).id;
        if (!J(str2)) {
            throw new ExceptionUart("Invalid monitor id");
        }
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            String str3 = ((DataMonitor) obj).name;
            if (str3 == null || str3.length() > 12) {
                if (str3 != null) {
                    throw new ExceptionUart("name too long");
                }
                throw new ExceptionUart("problem with name");
            }
            if (str3.length() == 0) {
                Intrinsics.checkNotNull(bool);
                if (bool.booleanValue()) {
                    Timber.forest.d("setMonitor - ID : " + str2 + " Name : " + str3, new Object[0]);
                    DataMonitor dataMonitor = new DataMonitor();
                    dataMonitor.id = str2;
                    dataMonitor.name = str3;
                    DataMonitor.update$default(dataMonitor, null, (DataMonitor) obj, null, false, 8, null);
                    DataMonitor monitor = dataMaster.myMonitors.getMonitor(str2);
                    if (monitor != null) {
                        DataMonitor dataMonitor2 = new DataMonitor();
                        DataMonitor.update$default(dataMonitor2, null, monitor, null, false, 8, null);
                        if (DataMonitor.update$default(dataMonitor2, null, dataMonitor, null, false, 8, null)) {
                            monitor.events.lastTriggeredTimestamp = null;
                            dataMonitor.events.lastTriggeredTimestamp = null;
                        }
                    }
                    C = C(context, dataMaster, dataMonitor);
                } else {
                    Timber.forest.d("setMonitor - ID : " + str2 + " deleting.", new Object[0]);
                    C = u(context, dataMaster, str2);
                }
            }
            if (C) {
                HandlerJson.Companion.getInstance(context).processData(dataMaster, "updateMonitor");
                return WebServer.ACK;
            }
            Unit unit = Unit.INSTANCE;
            return WebServer.NACK;
        }
    }

    public final void F(@NotNull Context context, @NotNull DataMaster masterData, @Nullable HashMap hashMap) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        Intrinsics.checkNotNull(hashMap);
        boolean z7 = false;
        for (String str : hashMap.keySet()) {
            DataMonitor dataMonitor = (DataMonitor) hashMap.get(str);
            DataMonitor monitor = masterData.myMonitors.getMonitor(str);
            if (monitor != null && dataMonitor != null && !Intrinsics.areEqual(monitor.monitorEnabled, dataMonitor.monitorEnabled)) {
                monitor.monitorEnabled = dataMonitor.monitorEnabled;
                monitor.generateSummary(masterData);
                this.f7199d0.remove(str);
                z7 = true;
            }
        }
        if (z7) {
            MyMonitors.Companion.b().e(context, masterData);
            r(context);
            HandlerJson.Companion.getInstance(context).processData(masterData, "updateMonitorsForScene");
        }
    }

    /* renamed from: I */
    public final void requestWeatherDataByLocation(@NotNull Context context) {
        long j10;
        Intrinsics.checkNotNullParameter(context, "context");
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
            Double d3 = dataMaster.system.latitude;
            if (d3 != null) {
                double doubleValue = d3.doubleValue();
                Double d10 = dataMaster.system.longitude;
                if (d10 != null) {
                    double doubleValue2 = d10.doubleValue();
                    String str = dataMaster.system.country;
                    Unit unit = Unit.INSTANCE;
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis > this.U.get()) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(currentTimeMillis);
                        calendar.set(12, 0);
                        this.U.set(calendar.getTimeInMillis() + 3600000);
                        this.Y.set(false);
                        if (Intrinsics.areEqual(str, "New Zealand") || Intrinsics.areEqual(str, "South Africa")) {
                            j10 = currentTimeMillis;
                            new WeatherDataOpenWeatherMapService().requestWeatherForecast(doubleValue, doubleValue2, new b(context));
                        } else {
                            j10 = currentTimeMillis;
                            WeatherDataItem weatherData = WeatherUtility.INSTANCE.getWeatherData(doubleValue, doubleValue2, 1001);
                            if (weatherData != null) {
                                Timber.forest.d("observatory found now calling getWeatherData: " + weatherData.getParent() + "," + weatherData.getSubParent(), new Object[0]);
                                this.f7200e0.clear();
                                this.f7200e0.add(RxBinding.D(((WeatherDataBomService) KoinJavaComponent.get$default(WeatherDataBomService.class, null, null, 6, null)).getBomObservationSingle(weatherData.getParent(), weatherData.getSubParent()), new WeatherFunctionCall(context, weatherData), new d(context)));
                            } else {
                                Timber.Forest forest = Timber.forest;
                                forest.d("observatory is null, can't get weather data", new Object[0]);
                                forest.wtf(f7191g0, "requestWeatherDataByLocation: failed!");
                            }
                        }
                    } else {
                        j10 = currentTimeMillis;
                    }
                    if (j10 > this.retryTime.get()) {
                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.setTimeInMillis(j10);
                        calendar2.set(11, 7);
                        calendar2.set(12, 0);
                        this.retryTime.set(calendar2.getTimeInMillis() + 86400000);
                        this.isSunnyWeather.set(false);
                        WeatherDataItem weatherData2 = WeatherUtility.INSTANCE.getWeatherData(doubleValue, doubleValue2, 1000);
                        if (weatherData2 == null) {
                            return;
                        }
                        new WeatherListener().a(context, weatherData2, new e());
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:177:0x069b A[Catch: all -> 0x0765, TryCatch #0 {all -> 0x0765, blocks: (B:199:0x02cd, B:201:0x02d3, B:203:0x02dc, B:205:0x02e4, B:207:0x02e8, B:209:0x02ec, B:211:0x02f6, B:213:0x0301, B:215:0x0307, B:217:0x0319, B:218:0x032f, B:220:0x0333, B:221:0x0338, B:222:0x034c, B:224:0x0352, B:227:0x035a, B:232:0x035e, B:233:0x0362, B:235:0x0368, B:242:0x037b, B:245:0x037f, B:257:0x0399, B:262:0x03a7, B:75:0x0481, B:77:0x0487, B:79:0x0490, B:81:0x0498, B:83:0x049c, B:84:0x04b7, B:86:0x04bd, B:89:0x04c5, B:94:0x04c9, B:95:0x04cd, B:97:0x04d3, B:100:0x04dd, B:103:0x04e1, B:106:0x04ea, B:116:0x0503, B:119:0x0511, B:109:0x0556, B:112:0x0564, B:126:0x05aa, B:128:0x05b0, B:130:0x05b9, B:132:0x05c3, B:134:0x05cb, B:137:0x05ec, B:139:0x05f2, B:141:0x05fb, B:142:0x0601, B:144:0x0607, B:146:0x0610, B:147:0x0617, B:149:0x061d, B:151:0x0626, B:154:0x0634, B:156:0x063c, B:158:0x0649, B:159:0x0642, B:164:0x0655, B:166:0x066a, B:168:0x0673, B:170:0x0679, B:174:0x0687, B:175:0x0695, B:177:0x069b, B:179:0x06a4, B:181:0x06aa, B:185:0x06b8, B:186:0x06cc, B:248:0x0401, B:254:0x0415, B:239:0x0375, B:287:0x0286, B:293:0x06f9, B:312:0x0717, B:316:0x074d, B:317:0x0756), top: B:198:0x02cd }] */
    /* JADX WARN: Removed duplicated region for block: B:190:0x06c4  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0693  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x06c7  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x05e7 A[PHI: r1
      0x05e7: PHI (r1v37 int) = (r1v36 int), (r1v36 int), (r1v36 int), (r1v74 int), (r1v74 int) binds: [B:125:0x05a8, B:127:0x05ae, B:129:0x05b7, B:131:0x05c1, B:133:0x05c9] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x02cd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0256 A[Catch: all -> 0x0767, TryCatch #1 {all -> 0x0767, blocks: (B:4:0x003d, B:9:0x004b, B:10:0x006c, B:12:0x0072, B:15:0x007a, B:20:0x007e, B:21:0x0083, B:23:0x0089, B:25:0x009c, B:27:0x00b0, B:29:0x00bb, B:33:0x012e, B:35:0x015c, B:37:0x0169, B:39:0x0172, B:41:0x0178, B:43:0x017c, B:45:0x0184, B:46:0x018e, B:48:0x0194, B:49:0x019c, B:50:0x01a2, B:51:0x01a8, B:52:0x01ad, B:54:0x01b3, B:58:0x01ca, B:60:0x01d5, B:62:0x01de, B:64:0x01e6, B:66:0x01ec, B:68:0x01f5, B:70:0x0211, B:277:0x0256, B:279:0x025e, B:281:0x0262, B:282:0x0264, B:284:0x0268, B:294:0x00d1, B:298:0x00dd, B:302:0x00f4, B:304:0x0101, B:307:0x010d, B:309:0x011f), top: B:3:0x003d }] */
    /* JADX WARN: Removed duplicated region for block: B:289:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x06ee  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x00dd A[Catch: all -> 0x0767, TryCatch #1 {all -> 0x0767, blocks: (B:4:0x003d, B:9:0x004b, B:10:0x006c, B:12:0x0072, B:15:0x007a, B:20:0x007e, B:21:0x0083, B:23:0x0089, B:25:0x009c, B:27:0x00b0, B:29:0x00bb, B:33:0x012e, B:35:0x015c, B:37:0x0169, B:39:0x0172, B:41:0x0178, B:43:0x017c, B:45:0x0184, B:46:0x018e, B:48:0x0194, B:49:0x019c, B:50:0x01a2, B:51:0x01a8, B:52:0x01ad, B:54:0x01b3, B:58:0x01ca, B:60:0x01d5, B:62:0x01de, B:64:0x01e6, B:66:0x01ec, B:68:0x01f5, B:70:0x0211, B:277:0x0256, B:279:0x025e, B:281:0x0262, B:282:0x0264, B:284:0x0268, B:294:0x00d1, B:298:0x00dd, B:302:0x00f4, B:304:0x0101, B:307:0x010d, B:309:0x011f), top: B:3:0x003d }] */
    /* JADX WARN: Removed duplicated region for block: B:307:0x010d A[Catch: all -> 0x0767, TryCatch #1 {all -> 0x0767, blocks: (B:4:0x003d, B:9:0x004b, B:10:0x006c, B:12:0x0072, B:15:0x007a, B:20:0x007e, B:21:0x0083, B:23:0x0089, B:25:0x009c, B:27:0x00b0, B:29:0x00bb, B:33:0x012e, B:35:0x015c, B:37:0x0169, B:39:0x0172, B:41:0x0178, B:43:0x017c, B:45:0x0184, B:46:0x018e, B:48:0x0194, B:49:0x019c, B:50:0x01a2, B:51:0x01a8, B:52:0x01ad, B:54:0x01b3, B:58:0x01ca, B:60:0x01d5, B:62:0x01de, B:64:0x01e6, B:66:0x01ec, B:68:0x01f5, B:70:0x0211, B:277:0x0256, B:279:0x025e, B:281:0x0262, B:282:0x0264, B:284:0x0268, B:294:0x00d1, B:298:0x00dd, B:302:0x00f4, B:304:0x0101, B:307:0x010d, B:309:0x011f), top: B:3:0x003d }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01a8 A[Catch: all -> 0x0767, TryCatch #1 {all -> 0x0767, blocks: (B:4:0x003d, B:9:0x004b, B:10:0x006c, B:12:0x0072, B:15:0x007a, B:20:0x007e, B:21:0x0083, B:23:0x0089, B:25:0x009c, B:27:0x00b0, B:29:0x00bb, B:33:0x012e, B:35:0x015c, B:37:0x0169, B:39:0x0172, B:41:0x0178, B:43:0x017c, B:45:0x0184, B:46:0x018e, B:48:0x0194, B:49:0x019c, B:50:0x01a2, B:51:0x01a8, B:52:0x01ad, B:54:0x01b3, B:58:0x01ca, B:60:0x01d5, B:62:0x01de, B:64:0x01e6, B:66:0x01ec, B:68:0x01f5, B:70:0x0211, B:277:0x0256, B:279:0x025e, B:281:0x0262, B:282:0x0264, B:284:0x0268, B:294:0x00d1, B:298:0x00dd, B:302:0x00f4, B:304:0x0101, B:307:0x010d, B:309:0x011f), top: B:3:0x003d }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01ca A[Catch: all -> 0x0767, TryCatch #1 {all -> 0x0767, blocks: (B:4:0x003d, B:9:0x004b, B:10:0x006c, B:12:0x0072, B:15:0x007a, B:20:0x007e, B:21:0x0083, B:23:0x0089, B:25:0x009c, B:27:0x00b0, B:29:0x00bb, B:33:0x012e, B:35:0x015c, B:37:0x0169, B:39:0x0172, B:41:0x0178, B:43:0x017c, B:45:0x0184, B:46:0x018e, B:48:0x0194, B:49:0x019c, B:50:0x01a2, B:51:0x01a8, B:52:0x01ad, B:54:0x01b3, B:58:0x01ca, B:60:0x01d5, B:62:0x01de, B:64:0x01e6, B:66:0x01ec, B:68:0x01f5, B:70:0x0211, B:277:0x0256, B:279:0x025e, B:281:0x0262, B:282:0x0264, B:284:0x0268, B:294:0x00d1, B:298:0x00dd, B:302:0x00f4, B:304:0x0101, B:307:0x010d, B:309:0x011f), top: B:3:0x003d }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0479  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void r(@NotNull Context context) {
        Class<MyMasterData> cls;
        Calendar calendar;
        int i10;
        int i11;
        HashMap hashMap;
        HashMap hashMap2;
        HandlerHue handlerHue;
        Iterator it;
        boolean z7;
        boolean z10;
        boolean z11;
        HashMap hashMap3;
        int i12;
        int i13;
        int i14;
        HashMap hashMap4;
        HandlerHue handlerHue2;
        DataAirconSystem dataAirconSystem;
        int i15;
        HashMap hashMap5;
        boolean z12;
        Boolean bool;
        String str;
        String str2;
        Boolean bool2;
        Boolean bool3;
        Boolean bool4;
        Integer num;
        Intrinsics.checkNotNullParameter(context, "context");
        Calendar calendar2 = Calendar.getInstance();
        int i16 = (calendar2.get(11) * 60) + calendar2.get(12);
        int i17 = calendar2.get(7);
        FirebaseComms firebaseComms = (FirebaseComms) KoinJavaComponent.get$default(FirebaseComms.class, null, null, 6, null);
        HashMap hashMap6 = new HashMap();
        HashMap hashMap7 = new HashMap();
        HashMap hashMap8 = new HashMap();
        Class<MyMasterData> cls2 = MyMasterData.class;
        synchronized (cls2) {
            try {
                DataMaster dataMaster = MyMasterData.Companion.getDataMaster(context);
                if (!w(dataMaster)) {
                    HandlerHue companion = HandlerHue.Companion.getInstance();
                    HashMap<String, DataMonitor> hashMap9 = dataMaster.myMonitors.monitors;
                    Intrinsics.checkNotNull(hashMap9);
                    Collection<DataMonitor> values = hashMap9.values();
                    Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
                    ArrayList arrayList = new ArrayList();
                    for (DataMonitor dataMonitor : values) {
                        if (dataMonitor != null) {
                            arrayList.add(dataMonitor);
                        }
                    }
                    Iterator it2 = arrayList.iterator();
                    boolean z13 = false;
                    while (it2.hasNext()) {
                        DataMonitor dataMonitor2 = (DataMonitor) it2.next();
                        Boolean bool5 = dataMonitor2.monitorEnabled;
                        Intrinsics.checkNotNull(bool5);
                        if (bool5.booleanValue()) {
                            Integer num2 = dataMonitor2.activeDays;
                            Intrinsics.checkNotNull(num2);
                            it = it2;
                            if ((num2.intValue() & (1 << (i17 - 1))) != 0) {
                                Integer num3 = dataMonitor2.startTime;
                                Intrinsics.checkNotNull(num3);
                                if (num3.intValue() <= i16) {
                                    Integer num4 = dataMonitor2.endTime;
                                    Intrinsics.checkNotNull(num4);
                                    int intValue = num4.intValue();
                                    Integer num5 = dataMonitor2.startTime;
                                    Intrinsics.checkNotNull(num5);
                                    if (intValue > num5.intValue()) {
                                        Integer num6 = dataMonitor2.endTime;
                                        Intrinsics.checkNotNull(num6);
                                        if (num6.intValue() >= i16) {
                                        }
                                    }
                                } else {
                                    Integer num7 = dataMonitor2.endTime;
                                    Intrinsics.checkNotNull(num7);
                                    int intValue2 = num7.intValue();
                                    Integer num8 = dataMonitor2.startTime;
                                    Intrinsics.checkNotNull(num8);
                                    if (intValue2 <= num8.intValue()) {
                                        if (i17 == 1) {
                                            Integer num9 = dataMonitor2.activeDays;
                                            Intrinsics.checkNotNull(num9);
                                            if ((num9.intValue() & 64) != 0) {
                                                Integer num10 = dataMonitor2.endTime;
                                                Intrinsics.checkNotNull(num10);
                                                z10 = num10.intValue() >= i16;
                                            } else {
                                                Integer num11 = dataMonitor2.activeDays;
                                                Intrinsics.checkNotNull(num11);
                                                if ((num11.intValue() & (1 << (i17 - 2))) != 0) {
                                                    Integer num12 = dataMonitor2.endTime;
                                                    Intrinsics.checkNotNull(num12);
                                                    if (num12.intValue() >= i16) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (z10) {
                                    Timber.Forest forest = Timber.forest;
                                    String str3 = dataMonitor2.name;
                                    i10 = i16;
                                    StringBuilder sb = new StringBuilder();
                                    z7 = z13;
                                    sb.append("Monitor ");
                                    sb.append(str3);
                                    sb.append(" is in active period - checking conditions");
                                    forest.d(sb.toString(), new Object[0]);
                                    if (!this.f7199d0.contains(dataMonitor2.id)) {
                                        this.f7199d0.add(dataMonitor2.id);
                                        Boolean bool6 = dataMonitor2.events.motionSensorEnabled;
                                        if (bool6 != null) {
                                            Intrinsics.checkNotNull(bool6);
                                            if (bool6.booleanValue()) {
                                                Events events = dataMonitor2.events;
                                                String str4 = events.motionSensorTrigger;
                                                if (str4 == null) {
                                                    companion.i(dataMonitor2.id);
                                                } else if (events.motionSensorsIdList == null) {
                                                    companion.i(dataMonitor2.id);
                                                } else if (Intrinsics.areEqual(str4, Events.MOTION_SENSOR_TRIGGER_ON_MOTION)) {
                                                    companion.x(dataMonitor2.id, dataMonitor2.events.motionSensorsIdList);
                                                } else {
                                                    Events events2 = dataMonitor2.events;
                                                    Integer num13 = events2.motionSensorTriggerDelayMinutes;
                                                    if (num13 != null) {
                                                        companion.y(dataMonitor2.id, num13, events2.motionSensorsIdList);
                                                    }
                                                }
                                            } else {
                                                companion.i(dataMonitor2.id);
                                            }
                                        }
                                    }
                                    Long l8 = dataMonitor2.events.lastTriggeredTimestamp;
                                    if (l8 != null) {
                                        Intrinsics.checkNotNull(l8);
                                        calendar2.setTimeInMillis(l8.longValue());
                                        z11 = calendar2.get(7) == i17;
                                        if (z11) {
                                            ArrayList arrayList2 = new ArrayList();
                                            Boolean bool7 = dataMonitor2.events.suburbTempEnabled;
                                            if (bool7 != null) {
                                                Intrinsics.checkNotNull(bool7);
                                                if (bool7.booleanValue()) {
                                                    if (this.Y.get()) {
                                                        Boolean bool8 = dataMonitor2.events.suburbTempBelowThresholdSelected;
                                                        if (bool8 != null) {
                                                            Intrinsics.checkNotNull(bool8);
                                                            if (bool8.booleanValue()) {
                                                                double longBitsToDouble = Double.longBitsToDouble(this.airTemp.get());
                                                                Intrinsics.checkNotNull(dataMonitor2.events.suburbTempThresholdValue);
                                                                if (longBitsToDouble < r15.intValue()) {
                                                                    forest.d("suburb temp below is true", new Object[0]);
                                                                    dataMonitor2 = dataMonitor2;
                                                                    calendar = calendar2;
                                                                    arrayList2.add(context.getString(R.string.handler_monitor_suburb_temperature_notification_message_string_start) + "below " + dataMonitor2.events.suburbTempThresholdValue + context.getString(R.string.handler_monitor_degree_celsius_string));
                                                                    hashMap3 = hashMap8;
                                                                    cls = cls2;
                                                                    i12 = 1;
                                                                    i13 = 1;
                                                                    if (i12 == i13) {
                                                                        try {
                                                                            Boolean bool9 = dataMonitor2.events.zoneTempEnabled;
                                                                            if (bool9 != null) {
                                                                                Intrinsics.checkNotNull(bool9);
                                                                                if (bool9.booleanValue()) {
                                                                                    int i18 = i12 + 1;
                                                                                    Events events3 = dataMonitor2.events;
                                                                                    String str5 = events3.zoneTempAirconId;
                                                                                    if (str5 == null || events3.zoneTempThresholdValue == null || events3.zoneTempBelowThresholdSelected == null || (dataAirconSystem = dataMaster.aircons.get(str5)) == null) {
                                                                                        i14 = i18;
                                                                                        i11 = i17;
                                                                                        hashMap4 = hashMap7;
                                                                                        handlerHue2 = companion;
                                                                                        i12 = i14;
                                                                                    } else {
                                                                                        String str6 = "";
                                                                                        if (dataMaster.aircons.size() > 1) {
                                                                                            String str7 = dataAirconSystem.info.name;
                                                                                            str6 = str7 != null ? str7 + " - " : dataMonitor2.events.zoneTempAirconId + " - ";
                                                                                        }
                                                                                        TreeMap<String, DataZone> treeMap = dataAirconSystem.zones;
                                                                                        if (treeMap == null) {
                                                                                            treeMap = new TreeMap<>();
                                                                                        }
                                                                                        Collection<DataZone> values2 = treeMap.values();
                                                                                        Intrinsics.checkNotNullExpressionValue(values2, "<get-values>(...)");
                                                                                        ArrayList<DataZone> arrayList3 = new ArrayList();
                                                                                        for (DataZone dataZone : values2) {
                                                                                            if (dataZone != null) {
                                                                                                arrayList3.add(dataZone);
                                                                                            }
                                                                                        }
                                                                                        for (DataZone dataZone2 : arrayList3) {
                                                                                            Integer num14 = dataZone2.type;
                                                                                            if (num14 != null && (num14 == null || num14.intValue() != 0)) {
                                                                                                Float f3 = dataZone2.measuredTemp;
                                                                                                if (f3 != null) {
                                                                                                    Intrinsics.checkNotNull(f3);
                                                                                                    float floatValue = f3.floatValue() + 0.5f;
                                                                                                    i14 = i18;
                                                                                                    Boolean bool10 = dataMonitor2.events.zoneTempBelowThresholdSelected;
                                                                                                    Intrinsics.checkNotNull(bool10);
                                                                                                    if (bool10.booleanValue()) {
                                                                                                        int i19 = (int) floatValue;
                                                                                                        Integer num15 = dataMonitor2.events.zoneTempThresholdValue;
                                                                                                        Intrinsics.checkNotNull(num15);
                                                                                                        if (i19 < num15.intValue()) {
                                                                                                            Timber.Forest forest2 = Timber.forest;
                                                                                                            String str8 = f7191g0;
                                                                                                            i11 = i17;
                                                                                                            String zoneKey = dataZone2.getZoneKey();
                                                                                                            handlerHue2 = companion;
                                                                                                            StringBuilder sb2 = new StringBuilder();
                                                                                                            hashMap4 = hashMap7;
                                                                                                            sb2.append("zone temp below is true, for zone : ");
                                                                                                            sb2.append(zoneKey);
                                                                                                            forest2.d(str8, sb2.toString());
                                                                                                            i13++;
                                                                                                            arrayList2.add(str6 + dataZone2.name + " temperature is below " + dataMonitor2.events.zoneTempThresholdValue + context.getString(R.string.handler_monitor_degree_celsius_string));
                                                                                                            break;
                                                                                                        }
                                                                                                        i18 = i14;
                                                                                                    } else {
                                                                                                        i11 = i17;
                                                                                                        hashMap4 = hashMap7;
                                                                                                        handlerHue2 = companion;
                                                                                                        int i20 = (int) floatValue;
                                                                                                        Integer num16 = dataMonitor2.events.zoneTempThresholdValue;
                                                                                                        Intrinsics.checkNotNull(num16);
                                                                                                        if (i20 > num16.intValue()) {
                                                                                                            Timber.forest.d(f7191g0, "zone temp above is true, for zone : " + dataZone2.getZoneKey());
                                                                                                            i13++;
                                                                                                            arrayList2.add(str6 + dataZone2.name + " temperature is above " + dataMonitor2.events.zoneTempThresholdValue + context.getString(R.string.handler_monitor_degree_celsius_string));
                                                                                                            break;
                                                                                                        }
                                                                                                        i18 = i14;
                                                                                                        i17 = i11;
                                                                                                        companion = handlerHue2;
                                                                                                        hashMap7 = hashMap4;
                                                                                                    }
                                                                                                } else {
                                                                                                    continue;
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                        i14 = i18;
                                                                                        i11 = i17;
                                                                                        hashMap4 = hashMap7;
                                                                                        handlerHue2 = companion;
                                                                                        i12 = i14;
                                                                                    }
                                                                                } else {
                                                                                    i11 = i17;
                                                                                    hashMap4 = hashMap7;
                                                                                    handlerHue2 = companion;
                                                                                }
                                                                                if (i12 == i13 && (bool3 = dataMonitor2.events.hueTempEnabled) != null) {
                                                                                    Intrinsics.checkNotNull(bool3);
                                                                                    if (bool3.booleanValue()) {
                                                                                        i12++;
                                                                                        Events events4 = dataMonitor2.events;
                                                                                        if (events4.hueTempThresholdValue != null && events4.hueTempBelowThresholdSelected != null) {
                                                                                            HashMap<String, DataSensor> hashMap10 = dataMaster.mySensors.sensors;
                                                                                            Intrinsics.checkNotNull(hashMap10);
                                                                                            Collection<DataSensor> values3 = hashMap10.values();
                                                                                            Intrinsics.checkNotNullExpressionValue(values3, "<get-values>(...)");
                                                                                            ArrayList arrayList4 = new ArrayList();
                                                                                            for (DataSensor dataSensor : values3) {
                                                                                                if (dataSensor != null) {
                                                                                                    arrayList4.add(dataSensor);
                                                                                                }
                                                                                            }
                                                                                            Iterator it3 = arrayList4.iterator();
                                                                                            while (true) {
                                                                                                if (!it3.hasNext()) {
                                                                                                    break;
                                                                                                }
                                                                                                DataSensor dataSensor2 = (DataSensor) it3.next();
                                                                                                if (dataSensor2.temperature != null && (bool4 = dataSensor2.enabled) != null) {
                                                                                                    Intrinsics.checkNotNull(bool4);
                                                                                                    if (bool4.booleanValue()) {
                                                                                                        Float f7 = dataSensor2.temperature;
                                                                                                        Intrinsics.checkNotNull(f7);
                                                                                                        float floatValue2 = f7.floatValue() + 0.5f;
                                                                                                        Boolean bool11 = dataMonitor2.events.hueTempBelowThresholdSelected;
                                                                                                        Intrinsics.checkNotNull(bool11);
                                                                                                        if (!bool11.booleanValue()) {
                                                                                                            int i21 = (int) floatValue2;
                                                                                                            Integer num17 = dataMonitor2.events.hueTempThresholdValue;
                                                                                                            Intrinsics.checkNotNull(num17);
                                                                                                            if (i21 > num17.intValue()) {
                                                                                                                Timber.forest.d("hue temp above is true, for sensor : " + dataSensor2.name, new Object[0]);
                                                                                                                i13++;
                                                                                                                arrayList2.add(dataSensor2.name + " temperature is above " + dataMonitor2.events.hueTempThresholdValue + context.getString(R.string.handler_monitor_degree_celsius_string));
                                                                                                                break;
                                                                                                            }
                                                                                                        } else {
                                                                                                            int i22 = (int) floatValue2;
                                                                                                            Integer num18 = dataMonitor2.events.hueTempThresholdValue;
                                                                                                            Intrinsics.checkNotNull(num18);
                                                                                                            if (i22 < num18.intValue()) {
                                                                                                                Timber.forest.d("hue temp below is true, for sensor : " + dataSensor2.name, new Object[0]);
                                                                                                                i13++;
                                                                                                                arrayList2.add(dataSensor2.name + " temperature is below " + dataMonitor2.events.hueTempThresholdValue + context.getString(R.string.handler_monitor_degree_celsius_string));
                                                                                                                break;
                                                                                                            }
                                                                                                        }
                                                                                                    } else {
                                                                                                        continue;
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                                if (i12 != i13 || (bool2 = dataMonitor2.events.weatherConditionForPvEnabled) == null) {
                                                                                    i15 = 0;
                                                                                    if (i12 > 0 || i12 != i13) {
                                                                                        hashMap2 = hashMap3;
                                                                                        hashMap = hashMap4;
                                                                                        handlerHue = handlerHue2;
                                                                                    } else {
                                                                                        Boolean bool12 = dataMonitor2.actions.autoActionEnabled;
                                                                                        if (bool12 != null) {
                                                                                            Intrinsics.checkNotNull(bool12);
                                                                                            if (bool12.booleanValue()) {
                                                                                                Intrinsics.checkNotNull(dataMonitor2);
                                                                                                B(context, firebaseComms, dataMaster, dataMonitor2);
                                                                                            }
                                                                                        }
                                                                                        Boolean bool13 = dataMonitor2.actions.launchMyAppEnabled;
                                                                                        if (bool13 != null) {
                                                                                            Intrinsics.checkNotNull(bool13);
                                                                                            if (bool13.booleanValue()) {
                                                                                                x(context, dataMonitor2.actions.launchMyAppPackageName);
                                                                                            }
                                                                                        }
                                                                                        Boolean bool14 = dataMonitor2.actions.notificationEnabled;
                                                                                        if (bool14 != null) {
                                                                                            Intrinsics.checkNotNull(bool14);
                                                                                            if (bool14.booleanValue()) {
                                                                                                StringBuilder sb3 = new StringBuilder();
                                                                                                int size = arrayList2.size();
                                                                                                for (int i23 = i15; i23 < size; i23++) {
                                                                                                    if (i23 > 0) {
                                                                                                        if (i23 == arrayList2.size() - 1) {
                                                                                                            sb3.append(", and ");
                                                                                                        } else {
                                                                                                            sb3.append(", ");
                                                                                                        }
                                                                                                    }
                                                                                                    sb3.append((String) arrayList2.get(i23));
                                                                                                }
                                                                                                z12 = true;
                                                                                                String str9 = dataMonitor2.id;
                                                                                                String sb4 = sb3.toString();
                                                                                                Intrinsics.checkNotNullExpressionValue(sb4, "toString(...)");
                                                                                                hashMap6.put(str9, sb4);
                                                                                                Boolean bool15 = dataMonitor2.actions.notificationPhoneNumberEnabled;
                                                                                                if (bool15 != null) {
                                                                                                    Intrinsics.checkNotNull(bool15);
                                                                                                    if (!bool15.booleanValue() || (str2 = dataMonitor2.actions.notificationPhoneNumber) == null) {
                                                                                                        hashMap = hashMap4;
                                                                                                        bool = dataMonitor2.actions.launchMyAppEnabled;
                                                                                                        if (bool == null) {
                                                                                                            Intrinsics.checkNotNull(bool);
                                                                                                            if (!bool.booleanValue() || (str = dataMonitor2.actions.launchMyAppPackageName) == null) {
                                                                                                                hashMap5 = hashMap3;
                                                                                                            } else {
                                                                                                                Intrinsics.checkNotNull(str);
                                                                                                                if ((str.length() > 0 ? 1 : i15) != 0) {
                                                                                                                    hashMap5 = hashMap3;
                                                                                                                    hashMap5.put(dataMonitor2.id, dataMonitor2.actions.launchMyAppPackageName);
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    } else {
                                                                                                        Intrinsics.checkNotNull(str2);
                                                                                                        if ((str2.length() > 0 ? 1 : i15) != 0) {
                                                                                                            hashMap = hashMap4;
                                                                                                            hashMap.put(dataMonitor2.id, dataMonitor2.actions.notificationPhoneNumber);
                                                                                                        }
                                                                                                        bool = dataMonitor2.actions.launchMyAppEnabled;
                                                                                                        if (bool == null) {
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            } else {
                                                                                                hashMap5 = hashMap3;
                                                                                                hashMap = hashMap4;
                                                                                                z12 = true;
                                                                                            }
                                                                                            dataMonitor2.events.lastTriggeredTimestamp = Long.valueOf(System.currentTimeMillis());
                                                                                            z13 = z12;
                                                                                            it2 = it;
                                                                                            i16 = i10;
                                                                                            calendar2 = calendar;
                                                                                            cls2 = cls;
                                                                                            companion = handlerHue2;
                                                                                            hashMap8 = hashMap5;
                                                                                        }
                                                                                    }
                                                                                } else {
                                                                                    Intrinsics.checkNotNull(bool2);
                                                                                    if (bool2.booleanValue()) {
                                                                                        i12++;
                                                                                        if (this.isSunnyWeather.get() && this.isClearWeather.get()) {
                                                                                            i15 = 0;
                                                                                            Timber.forest.d("sunny and clear weather condition is true", new Object[0]);
                                                                                            i13++;
                                                                                            String string = context.getString(R.string.handler_monitor_good_pv_weather_message_string);
                                                                                            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                                                                                            arrayList2.add(string);
                                                                                        }
                                                                                        if (i12 > 0) {
                                                                                        }
                                                                                        hashMap2 = hashMap3;
                                                                                        hashMap = hashMap4;
                                                                                        handlerHue = handlerHue2;
                                                                                    }
                                                                                }
                                                                            }
                                                                        } catch (Throwable th) {
                                                                            th = th;
                                                                            throw th;
                                                                        }
                                                                    }
                                                                } else {
                                                                    calendar = calendar2;
                                                                    dataMonitor2 = dataMonitor2;
                                                                }
                                                            } else {
                                                                calendar = calendar2;
                                                                Events events5 = dataMonitor2.events;
                                                                if (events5.suburbTempThresholdValue == null && (num = events5.suburbTempAboveValue) != null) {
                                                                    events5.suburbTempThresholdValue = num;
                                                                }
                                                                if (events5.suburbTempThresholdValue != null) {
                                                                    double longBitsToDouble2 = Double.longBitsToDouble(this.airTemp.get());
                                                                    Intrinsics.checkNotNull(dataMonitor2.events.suburbTempThresholdValue);
                                                                    hashMap3 = hashMap8;
                                                                    cls = cls2;
                                                                    if (longBitsToDouble2 > r15.intValue()) {
                                                                        forest.d("suburb temp above is true", new Object[0]);
                                                                        arrayList2.add(context.getString(R.string.handler_monitor_suburb_temperature_notification_message_string_start) + "above " + dataMonitor2.events.suburbTempThresholdValue + context.getString(R.string.handler_monitor_degree_celsius_string));
                                                                        i12 = 1;
                                                                        i13 = 1;
                                                                        if (i12 == i13) {
                                                                        }
                                                                    }
                                                                }
                                                                i12 = 1;
                                                            }
                                                        }
                                                    } else {
                                                        calendar = calendar2;
                                                    }
                                                    hashMap3 = hashMap8;
                                                    cls = cls2;
                                                    i12 = 1;
                                                } else {
                                                    calendar = calendar2;
                                                    hashMap3 = hashMap8;
                                                    cls = cls2;
                                                    i12 = 0;
                                                }
                                                i13 = 0;
                                                if (i12 == i13) {
                                                }
                                            }
                                        } else {
                                            calendar = calendar2;
                                            i11 = i17;
                                            hashMap = hashMap7;
                                            hashMap2 = hashMap8;
                                            cls = cls2;
                                            handlerHue = companion;
                                        }
                                    }
                                    if (z11) {
                                    }
                                } else {
                                    calendar = calendar2;
                                    i10 = i16;
                                    i11 = i17;
                                    hashMap = hashMap7;
                                    hashMap2 = hashMap8;
                                    cls = cls2;
                                    z7 = z13;
                                    this.f7199d0.remove(dataMonitor2.id);
                                    handlerHue = companion;
                                    handlerHue.i(dataMonitor2.id);
                                }
                            }
                            hashMap7 = hashMap;
                            i17 = i11;
                        } else {
                            calendar = calendar2;
                            i10 = i16;
                            i11 = i17;
                            hashMap = hashMap7;
                            hashMap2 = hashMap8;
                            cls = cls2;
                            handlerHue = companion;
                            it = it2;
                            z7 = z13;
                            this.f7199d0.remove(dataMonitor2.id);
                            handlerHue.i(dataMonitor2.id);
                        }
                        companion = handlerHue;
                        hashMap8 = hashMap2;
                        it2 = it;
                        i16 = i10;
                        z13 = z7;
                        calendar2 = calendar;
                        cls2 = cls;
                        hashMap7 = hashMap;
                        i17 = i11;
                    }
                    HashMap hashMap11 = hashMap7;
                    HashMap hashMap12 = hashMap8;
                    cls = cls2;
                    if (z13) {
                        MyMonitors.Companion.b().e(context, dataMaster);
                    }
                    D(dataMaster, hashMap6, hashMap11, hashMap12, "");
                    Unit unit = Unit.INSTANCE;
                }
            } catch (Throwable th2) {
                th = th2;
                cls = cls2;
            }
        }
    }

    public final void y(@NotNull DataMaster masterData, @Nullable String str, @Nullable String str2, @NotNull String currentState) {
        Boolean bool;
        String str3;
        String str4;
        String str5;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        Intrinsics.checkNotNullParameter(currentState, "currentState");
        if (w(masterData)) {
            return;
        }
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        Iterator it = this.f7199d0.iterator();
        while (it.hasNext()) {
            String str6 = (String) it.next();
            DataMonitor monitor = masterData.myMonitors.getMonitor(str6);
            if (monitor != null && (bool = monitor.events.garageDoorEnabled) != null) {
                Intrinsics.checkNotNull(bool);
                if (bool.booleanValue() && (str3 = monitor.events.garageDoorTrigger) != null && Intrinsics.areEqual(str3, currentState)) {
                    ActivityMain companion = ActivityMain.Companion.getInstance();
                    if (companion == null) {
                        return;
                    }
                    FirebaseComms firebaseComms = (FirebaseComms) KoinJavaComponent.get$default(FirebaseComms.class, null, null, 6, null);
                    Boolean bool2 = monitor.actions.notificationEnabled;
                    if (bool2 != null) {
                        Intrinsics.checkNotNull(bool2);
                        if (bool2.booleanValue()) {
                            hashMap.put(str6, "\"" + str2 + (Intrinsics.areEqual(currentState, Events.GARAGE_DOOR_TRIGGER_ON_CLOSE) ? "\" is closed" : "\" is opened"));
                            Boolean bool3 = monitor.actions.notificationPhoneNumberEnabled;
                            if (bool3 != null) {
                                Intrinsics.checkNotNull(bool3);
                                if (bool3.booleanValue() && (str5 = monitor.actions.notificationPhoneNumber) != null) {
                                    Intrinsics.checkNotNull(str5);
                                    if (str5.length() > 0) {
                                        hashMap2.put(monitor.id, monitor.actions.notificationPhoneNumber);
                                    }
                                }
                            }
                            Boolean bool4 = monitor.actions.launchMyAppEnabled;
                            if (bool4 != null) {
                                Intrinsics.checkNotNull(bool4);
                                if (bool4.booleanValue() && (str4 = monitor.actions.launchMyAppPackageName) != null) {
                                    Intrinsics.checkNotNull(str4);
                                    if (str4.length() > 0) {
                                        hashMap3.put(monitor.id, monitor.actions.launchMyAppPackageName);
                                    }
                                }
                            }
                        }
                    }
                    Boolean bool5 = monitor.actions.autoActionEnabled;
                    if (bool5 != null) {
                        Intrinsics.checkNotNull(bool5);
                        if (bool5.booleanValue()) {
                            B(companion, firebaseComms, masterData, monitor);
                        }
                    }
                    Boolean bool6 = monitor.actions.launchMyAppEnabled;
                    if (bool6 != null) {
                        Intrinsics.checkNotNull(bool6);
                        if (bool6.booleanValue()) {
                            x(companion, monitor.actions.launchMyAppPackageName);
                        }
                    }
                }
            }
        }
        D(masterData, hashMap, hashMap2, hashMap3, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x00e1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x00d9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ac A[Catch: all -> 0x022a, TryCatch #0 {, blocks: (B:4:0x0022, B:9:0x0036, B:10:0x003a, B:12:0x0040, B:15:0x0058, B:17:0x005c, B:19:0x0065, B:21:0x0069, B:23:0x0072, B:25:0x0079, B:27:0x0082, B:29:0x0088, B:31:0x008c, B:33:0x0092, B:35:0x0098, B:37:0x00a1, B:39:0x00ac, B:41:0x00b2, B:43:0x00bb, B:46:0x00c8, B:48:0x00ce, B:52:0x00d9, B:54:0x00e3, B:56:0x00f3, B:58:0x00fc, B:60:0x0104, B:61:0x0193, B:63:0x019c, B:65:0x01a5, B:67:0x01ab, B:71:0x01b9, B:73:0x01c2, B:75:0x01c8, B:77:0x01d1, B:79:0x01d7, B:83:0x01e7, B:85:0x0119, B:87:0x0121, B:89:0x012c, B:91:0x0147, B:94:0x015a, B:95:0x017d, B:96:0x01f0, B:98:0x01f6, B:100:0x01ff, B:101:0x0202, B:103:0x0208, B:105:0x0211, B:123:0x021c), top: B:3:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ce A[Catch: all -> 0x022a, TryCatch #0 {, blocks: (B:4:0x0022, B:9:0x0036, B:10:0x003a, B:12:0x0040, B:15:0x0058, B:17:0x005c, B:19:0x0065, B:21:0x0069, B:23:0x0072, B:25:0x0079, B:27:0x0082, B:29:0x0088, B:31:0x008c, B:33:0x0092, B:35:0x0098, B:37:0x00a1, B:39:0x00ac, B:41:0x00b2, B:43:0x00bb, B:46:0x00c8, B:48:0x00ce, B:52:0x00d9, B:54:0x00e3, B:56:0x00f3, B:58:0x00fc, B:60:0x0104, B:61:0x0193, B:63:0x019c, B:65:0x01a5, B:67:0x01ab, B:71:0x01b9, B:73:0x01c2, B:75:0x01c8, B:77:0x01d1, B:79:0x01d7, B:83:0x01e7, B:85:0x0119, B:87:0x0121, B:89:0x012c, B:91:0x0147, B:94:0x015a, B:95:0x017d, B:96:0x01f0, B:98:0x01f6, B:100:0x01ff, B:101:0x0202, B:103:0x0208, B:105:0x0211, B:123:0x021c), top: B:3:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00e3 A[Catch: all -> 0x022a, TRY_ENTER, TryCatch #0 {, blocks: (B:4:0x0022, B:9:0x0036, B:10:0x003a, B:12:0x0040, B:15:0x0058, B:17:0x005c, B:19:0x0065, B:21:0x0069, B:23:0x0072, B:25:0x0079, B:27:0x0082, B:29:0x0088, B:31:0x008c, B:33:0x0092, B:35:0x0098, B:37:0x00a1, B:39:0x00ac, B:41:0x00b2, B:43:0x00bb, B:46:0x00c8, B:48:0x00ce, B:52:0x00d9, B:54:0x00e3, B:56:0x00f3, B:58:0x00fc, B:60:0x0104, B:61:0x0193, B:63:0x019c, B:65:0x01a5, B:67:0x01ab, B:71:0x01b9, B:73:0x01c2, B:75:0x01c8, B:77:0x01d1, B:79:0x01d7, B:83:0x01e7, B:85:0x0119, B:87:0x0121, B:89:0x012c, B:91:0x0147, B:94:0x015a, B:95:0x017d, B:96:0x01f0, B:98:0x01f6, B:100:0x01ff, B:101:0x0202, B:103:0x0208, B:105:0x0211, B:123:0x021c), top: B:3:0x0022 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void z(@NotNull String str, @Nullable String str2, @NotNull ArrayList activeMonitorsWithMotionSensor) {
        Boolean bool;
        Boolean bool2;
        boolean z7;
        Boolean bool3;
        ActivityMain companion;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String motionTrigger = str;
        Intrinsics.checkNotNullParameter(motionTrigger, "motionTrigger");
        Intrinsics.checkNotNullParameter(activeMonitorsWithMotionSensor, "activeMonitorsWithMotionSensor");
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        synchronized (MyMasterData.class) {
            DataMaster dataMaster = MyMasterData.Companion.getDataMaster(MyApp.appContextProvider.appContext());
            if (w(dataMaster)) {
                return;
            }
            Iterator it = activeMonitorsWithMotionSensor.iterator();
            while (it.hasNext()) {
                String str8 = (String) it.next();
                DataMonitor monitor = dataMaster.myMonitors.getMonitor(str8);
                DataSensor sensor = dataMaster.mySensors.getSensor(str2);
                if (monitor == null || sensor == null || (bool = sensor.enabled) == null) {
                    motionTrigger = str;
                } else {
                    Intrinsics.checkNotNull(bool);
                    if (bool.booleanValue() && (bool2 = sensor.reachable) != null) {
                        Intrinsics.checkNotNull(bool2);
                        if (bool2.booleanValue()) {
                            Boolean bool4 = monitor.events.motionSensorLightLevelEnabled;
                            if (bool4 != null) {
                                Intrinsics.checkNotNull(bool4);
                                if (!bool4.booleanValue() || (str7 = monitor.events.motionSensorLightLevel) == null || sensor.lightLevelString == null) {
                                    z7 = false;
                                    bool3 = monitor.events.motionSensorLightLevelEnabled;
                                    if (bool3 == null) {
                                        Intrinsics.checkNotNull(bool3);
                                        if (bool3.booleanValue() && !z7) {
                                        }
                                    }
                                    companion = ActivityMain.Companion.getInstance();
                                    if (companion != null) {
                                        return;
                                    }
                                    FirebaseComms firebaseComms = (FirebaseComms) KoinJavaComponent.get$default(FirebaseComms.class, null, null, 6, null);
                                    Boolean bool5 = monitor.actions.notificationEnabled;
                                    if (bool5 != null) {
                                        Intrinsics.checkNotNull(bool5);
                                        if (bool5.booleanValue()) {
                                            if (Intrinsics.areEqual(motionTrigger, Events.MOTION_SENSOR_TRIGGER_ON_MOTION)) {
                                                str3 = "motion detected at " + sensor.name;
                                            } else {
                                                String str9 = "";
                                                Integer num = monitor.events.motionSensorTriggerDelayMinutes;
                                                if (num != null) {
                                                    Intrinsics.checkNotNull(num);
                                                    if (num.intValue() < 60) {
                                                        str4 = " for " + monitor.events.motionSensorTriggerDelayMinutes + " mins";
                                                    } else {
                                                        Integer num2 = monitor.events.motionSensorTriggerDelayMinutes;
                                                        Intrinsics.checkNotNull(num2);
                                                        if (num2.intValue() < 120) {
                                                            str4 = " for 1 hour";
                                                        } else {
                                                            Integer num3 = monitor.events.motionSensorTriggerDelayMinutes;
                                                            Intrinsics.checkNotNull(num3);
                                                            str4 = " for " + (num3.intValue() / 60) + " hours";
                                                        }
                                                    }
                                                    str9 = str4;
                                                }
                                                str3 = "no motion at " + sensor.name + str9;
                                            }
                                            hashMap.put(str8, str3);
                                            Boolean bool6 = monitor.actions.notificationPhoneNumberEnabled;
                                            if (bool6 != null) {
                                                Intrinsics.checkNotNull(bool6);
                                                if (bool6.booleanValue() && (str6 = monitor.actions.notificationPhoneNumber) != null) {
                                                    Intrinsics.checkNotNull(str6);
                                                    if (str6.length() > 0) {
                                                        hashMap2.put(monitor.id, monitor.actions.notificationPhoneNumber);
                                                    }
                                                }
                                            }
                                            Boolean bool7 = monitor.actions.launchMyAppEnabled;
                                            if (bool7 != null) {
                                                Intrinsics.checkNotNull(bool7);
                                                if (bool7.booleanValue() && (str5 = monitor.actions.launchMyAppPackageName) != null) {
                                                    Intrinsics.checkNotNull(str5);
                                                    if (str5.length() > 0) {
                                                        hashMap3.put(monitor.id, monitor.actions.launchMyAppPackageName);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    Boolean bool8 = monitor.actions.autoActionEnabled;
                                    if (bool8 != null) {
                                        Intrinsics.checkNotNull(bool8);
                                        if (bool8.booleanValue()) {
                                            B(companion, firebaseComms, dataMaster, monitor);
                                        }
                                    }
                                    Boolean bool9 = monitor.actions.launchMyAppEnabled;
                                    if (bool9 != null) {
                                        Intrinsics.checkNotNull(bool9);
                                        if (bool9.booleanValue()) {
                                            x(companion, monitor.actions.launchMyAppPackageName);
                                        }
                                    }
                                } else {
                                    if (sensor.compareLightLevelPosition(str7) != 0) {
                                        Boolean bool10 = monitor.events.motionSensorLightLevelEqualOrBelowSelected;
                                        if (bool10 != null) {
                                            Intrinsics.checkNotNull(bool10);
                                            if (!bool10.booleanValue() || sensor.compareLightLevelPosition(monitor.events.motionSensorLightLevel) != -1) {
                                                Boolean bool11 = monitor.events.motionSensorLightLevelEqualOrBelowSelected;
                                                if (bool11 != null) {
                                                    Intrinsics.checkNotNull(bool11);
                                                    if (bool11.booleanValue() || sensor.compareLightLevelPosition(monitor.events.motionSensorLightLevel) != 1) {
                                                    }
                                                    bool3 = monitor.events.motionSensorLightLevelEnabled;
                                                    if (bool3 == null) {
                                                    }
                                                    companion = ActivityMain.Companion.getInstance();
                                                    if (companion != null) {
                                                    }
                                                }
                                                z7 = false;
                                                bool3 = monitor.events.motionSensorLightLevelEnabled;
                                                if (bool3 == null) {
                                                }
                                                companion = ActivityMain.Companion.getInstance();
                                                if (companion != null) {
                                                }
                                            }
                                        }
                                    }
                                    z7 = true;
                                    bool3 = monitor.events.motionSensorLightLevelEnabled;
                                    if (bool3 == null) {
                                    }
                                    companion = ActivityMain.Companion.getInstance();
                                    if (companion != null) {
                                    }
                                }
                            }
                        }
                        motionTrigger = str;
                    }
                }
            }
            D(dataMaster, hashMap, hashMap2, hashMap3, str2);
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private HandlerMonitor() {
        this.U = new AtomicLong();
        this.retryTime = new AtomicLong();
        this.isClearWeather = new AtomicBoolean(false);
        this.airTemp = new AtomicLong();
        this.Y = new AtomicBoolean(false);
        this.isSunnyWeather = new AtomicBoolean(false);
        this.f7197a0 = new AtomicInteger(2);
        this.retryCount = new AtomicInteger(2);
        this.f7198c0 = new Gson();
        this.f7199d0 = new ArrayList();
        this.f7200e0 = new CompositeDisposable();
    }
}