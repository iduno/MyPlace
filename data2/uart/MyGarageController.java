package com.air.advantage.uart;

import android.content.Context;
import com.air.advantage.AppFeatures;
import com.air.advantage.SharedPreferencesStore;
import com.air.advantage.data.DataMaster;
import com.air.advantage.data.DataMyGarageController;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

/* renamed from: com.air.advantage.uart.g */
/* loaded from: classes.dex */
public final class MyGarageController {

    /* renamed from: d */
    @NotNull
    public static final a Companion = new a(null);

    /* renamed from: e, reason: collision with root package name */
    private static final String f7089e = MyGarageController.class.getSimpleName();

    /* renamed from: f, reason: collision with root package name */
    @Nullable
    private static MyGarageController f7090f;

    @NotNull
    private final Gson a;

    /* renamed from: b, reason: collision with root package name */
    @NotNull
    private final SharedPreferencesStore f7091b;

    /* renamed from: c, reason: collision with root package name */
    @NotNull
    private String f7092c;

    /* renamed from: com.air.advantage.uart.g$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.g.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        /* renamed from: a */
        public final void destroy() {
            MyGarageController.f7090f = null;
        }

        @NotNull
        public final MyGarageController b() {
            if (MyGarageController.f7090f == null) {
                synchronized (MyGarageController.class) {
                    if (MyGarageController.f7090f == null) {
                        a aVar = MyGarageController.Companion;
                        MyGarageController.f7090f = new MyGarageController(null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            MyGarageController myGarageController = MyGarageController.f7090f;
            Intrinsics.checkNotNull(myGarageController);
            return myGarageController;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.g.<init>():void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ MyGarageController(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @JvmStatic
    public static final void c() {
        Companion.destroy();
    }

    public final void d(@Nullable Context context, @NotNull DataMaster masterData) {
        DataMyGarageController dataMyGarageController;
        JsonIOException e7;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (!Thread.holdsLock(MyMasterData.class)) {
            throw new NullPointerException("need to synchronized(MyMasterData.class) to use this function");
        }
        DataMyGarageController dataMyGarageController2 = new DataMyGarageController();
        String sharedPreference = this.f7091b.getSharedPreference(context, SharedPreferencesStore.Companion.getGarageControllerDataKey());
        Timber.Forest forest = Timber.forest;
        forest.d("Using garage contollers " + sharedPreference, new Object[0]);
        if (!(sharedPreference == null || sharedPreference.length() == 0)) {
            try {
                dataMyGarageController = (DataMyGarageController) this.a.fromJson(sharedPreference, DataMyGarageController.class);
                try {
                    forest.d("New data garage contollers struct", new Object[0]);
                } catch (JsonIOException e10) {
                    e7 = e10;
                    AppFeatures.instance.logCriticalException(e7, "restoreGarageControllersFromDatabase - " + sharedPreference);
                    dataMyGarageController2 = dataMyGarageController;
                    Intrinsics.checkNotNull(dataMyGarageController2);
                    masterData.myGarageRFControllers = dataMyGarageController2;
                }
            } catch (JsonIOException e11) {
                dataMyGarageController = dataMyGarageController2;
                e7 = e11;
            }
            dataMyGarageController2 = dataMyGarageController;
        }
        Intrinsics.checkNotNull(dataMyGarageController2);
        masterData.myGarageRFControllers = dataMyGarageController2;
    }

    @Nullable
    public final String e(@Nullable Context context, @NotNull DataMaster masterData) {
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        if (!Thread.holdsLock(MyMasterData.class)) {
            throw new NullPointerException("need to synchronized(MyMasterData.class) to use this function");
        }
        String json = this.a.toJson(masterData.myGarageRFControllers);
        if (json != null && !Intrinsics.areEqual(json, this.f7092c)) {
            Timber.forest.d("Saving garageControllers " + json, new Object[0]);
            this.f7092c = json;
            this.f7091b.updatePreference(context, SharedPreferencesStore.Companion.getGarageControllerDataKey(), json);
        }
        return json;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private MyGarageController() {
        this.a = new Gson();
        this.f7091b = (SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null);
        this.f7092c = "";
    }
}