package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.p0 */
/* loaded from: classes.dex */
public final class DataSensorsAllImport {

    @NotNull
    public static final a Companion = new a(null);
    public static final int MAX_SENSOR = 10;

    @Nullable
    @SerializedName("sensorsOrder")
    @JvmField
    public ArrayList<String> sensorsOrder = new ArrayList<>();

    @Nullable
    @SerializedName("sensors")
    @JvmField
    public HashMap<String, DataSensor> sensors = new HashMap<>();

    /* renamed from: com.air.advantage.data.p0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.p0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    public final boolean addSensor(@NotNull DataSensor dataSensor) {
        Intrinsics.checkNotNullParameter(dataSensor, "dataSensor");
        DataSensor sensor = getSensor(dataSensor.id);
        if (sensor != null) {
            DataSensor.update$default(sensor, dataSensor, null, false, 4, null);
            return true;
        }
        HashMap<String, DataSensor> hashMap = this.sensors;
        Intrinsics.checkNotNull(hashMap);
        if (hashMap.size() >= 10) {
            return false;
        }
        HashMap<String, DataSensor> hashMap2 = this.sensors;
        Intrinsics.checkNotNull(hashMap2);
        hashMap2.put(dataSensor.id, dataSensor);
        ArrayList<String> arrayList = this.sensorsOrder;
        Intrinsics.checkNotNull(arrayList);
        arrayList.add(dataSensor.id);
        return true;
    }

    public final void clearSensors() {
        HashMap<String, DataSensor> hashMap = this.sensors;
        Intrinsics.checkNotNull(hashMap);
        hashMap.clear();
        ArrayList<String> arrayList = this.sensorsOrder;
        Intrinsics.checkNotNull(arrayList);
        arrayList.clear();
    }

    @Nullable
    public final DataSensor getSensor(@Nullable String str) {
        if (str == null) {
            return null;
        }
        HashMap<String, DataSensor> hashMap = this.sensors;
        Intrinsics.checkNotNull(hashMap);
        return hashMap.get(str);
    }

    public final boolean removeSensor(@Nullable String str) {
        ArrayList<String> arrayList = this.sensorsOrder;
        Intrinsics.checkNotNull(arrayList);
        arrayList.remove(str);
        HashMap<String, DataSensor> hashMap = this.sensors;
        Intrinsics.checkNotNull(hashMap);
        return hashMap.remove(str) != null;
    }
}