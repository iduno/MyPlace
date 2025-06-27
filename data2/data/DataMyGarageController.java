package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.data.f0 */
/* loaded from: classes.dex */
public final class DataMyGarageController {

    @NotNull
    public static final a Companion = new a(null);
    public static final int MAX_GARAGE_CONTROLLER = 10;

    @Nullable
    @SerializedName("garageControllersOrder")
    @JvmField
    public ArrayList<String> garageControllersOrder = new ArrayList<>();

    @Nullable
    @SerializedName("garageControllers")
    @JvmField
    public HashMap<String, DataGroupSource> garageControllers = new HashMap<>();

    /* renamed from: com.air.advantage.data.f0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.f0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* renamed from: com.air.advantage.data.f0$b */
    public interface b {
        void onMyGCAdded(@Nullable String str, int i10, int i11);

        void onMyGCRemoved(@Nullable String str, int i10, int i11);

        void onMyGCUpdated(@Nullable String str, int i10);
    }

    public final boolean addGarageController(@NotNull DataGroupSource dataMyGarageController) {
        Intrinsics.checkNotNullParameter(dataMyGarageController, "dataMyGarageController");
        DataGroupSource garageController = getGarageController(dataMyGarageController.uid);
        if (garageController != null) {
            Timber.forest.d("addGarageController success update existing", new Object[0]);
            DataGroupSource.update$default(garageController, dataMyGarageController, null, false, 4, null);
            return true;
        }
        HashMap<String, DataGroupSource> hashMap = this.garageControllers;
        Intrinsics.checkNotNull(hashMap);
        if (hashMap.size() >= 10) {
            return false;
        }
        Timber.forest.d("addGarageController success new one", new Object[0]);
        HashMap<String, DataGroupSource> hashMap2 = this.garageControllers;
        Intrinsics.checkNotNull(hashMap2);
        hashMap2.put(dataMyGarageController.uid, dataMyGarageController);
        ArrayList<String> arrayList = this.garageControllersOrder;
        Intrinsics.checkNotNull(arrayList);
        arrayList.add(dataMyGarageController.uid);
        return true;
    }

    @Nullable
    public final DataGroupSource getGarageController(@Nullable String str) {
        if (str == null) {
            return null;
        }
        HashMap<String, DataGroupSource> hashMap = this.garageControllers;
        Intrinsics.checkNotNull(hashMap);
        return hashMap.get(str);
    }

    public final boolean removeGarageController(@Nullable String str) {
        ArrayList<String> arrayList = this.garageControllersOrder;
        Intrinsics.checkNotNull(arrayList);
        arrayList.remove(str);
        HashMap<String, DataGroupSource> hashMap = this.garageControllers;
        Intrinsics.checkNotNull(hashMap);
        return hashMap.remove(str) != null;
    }

    public final void setOnMyGCChangeListener(@Nullable b bVar) {
    }
}