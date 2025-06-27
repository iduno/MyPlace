package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.a0 */
/* loaded from: classes.dex */
public final class DataMyMonitors {

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = DataMyMonitors.class.getSimpleName();
    public static final int MAX_MONITOR = 20;

    @Nullable
    @SerializedName("monitors")
    @JvmField
    public HashMap<String, DataMonitor> monitors = new HashMap<>();

    @Nullable
    @SerializedName("monitorsOrder")
    @JvmField
    public ArrayList<String> monitorsOrder = new ArrayList<>();

    /* renamed from: com.air.advantage.data.a0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.a0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* JADX DEBUG: Class process forced to load method for inline: com.air.advantage.data.c0.update$default(com.air.advantage.data.c0, com.air.advantage.data.c0, com.air.advantage.data.l, boolean, int, java.lang.Object):boolean */
    /* JADX DEBUG: Class process forced to load method for inline: com.air.advantage.data.y.update$default(com.air.advantage.data.y, com.air.advantage.data.y, com.air.advantage.data.l, boolean, int, java.lang.Object):boolean */
    public final boolean addMonitor(@NotNull DataMonitor dataMonitor) {
        Intrinsics.checkNotNullParameter(dataMonitor, "dataMonitor");
        DataMonitor monitor = getMonitor(dataMonitor.id);
        if (monitor != null) {
            monitor.name = dataMonitor.name;
            monitor.activeDays = dataMonitor.activeDays;
            monitor.monitorEnabled = dataMonitor.monitorEnabled;
            monitor.startTime = dataMonitor.startTime;
            monitor.endTime = dataMonitor.endTime;
            DataMonitorActions.update$default(monitor.actions, dataMonitor.actions, null, false, 4, null);
            Events.update$default(monitor.events, dataMonitor.events, null, false, 4, null);
            monitor.monitorSummary = dataMonitor.monitorSummary;
            return true;
        }
        HashMap<String, DataMonitor> hashMap = this.monitors;
        Intrinsics.checkNotNull(hashMap);
        if (hashMap.size() >= 20) {
            return false;
        }
        HashMap<String, DataMonitor> hashMap2 = this.monitors;
        Intrinsics.checkNotNull(hashMap2);
        hashMap2.put(dataMonitor.id, dataMonitor);
        ArrayList<String> arrayList = this.monitorsOrder;
        Intrinsics.checkNotNull(arrayList);
        arrayList.add(dataMonitor.id);
        return true;
    }

    public final void clearDataForBackup() {
        HashMap<String, DataMonitor> hashMap = this.monitors;
        Intrinsics.checkNotNull(hashMap);
        for (DataMonitor dataMonitor : hashMap.values()) {
            Intrinsics.checkNotNull(dataMonitor);
            dataMonitor.clearDataForBackup();
        }
    }

    @Nullable
    public final DataMonitor getMonitor(@Nullable String str) {
        HashMap<String, DataMonitor> hashMap = this.monitors;
        Intrinsics.checkNotNull(hashMap);
        return hashMap.get(str);
    }

    public final boolean removeMonitor(@Nullable String str) {
        HashMap<String, DataMonitor> hashMap = this.monitors;
        Intrinsics.checkNotNull(hashMap);
        return hashMap.remove(str) != null;
    }
}