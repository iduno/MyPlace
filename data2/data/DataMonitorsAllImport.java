package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* renamed from: com.air.advantage.data.b0 */
/* loaded from: classes.dex */
public final class DataMonitorsAllImport {

    @SerializedName("monitors")
    @NotNull
    private HashMap<String, Monitor> monitors = new HashMap<>();

    @SerializedName("monitorsOrder")
    @NotNull
    private ArrayList<String> monitorsOrder = new ArrayList<>();

    @NotNull
    public final DataMyMonitors getDataMonitorAll() {
        DataMyMonitors dataMyMonitors = new DataMyMonitors();
        for (String str : this.monitors.keySet()) {
            Monitor monitor = this.monitors.get(str);
            DataMonitor dataMonitor = monitor != null ? monitor.getDataMonitor() : null;
            HashMap<String, DataMonitor> hashMap = dataMyMonitors.monitors;
            Intrinsics.checkNotNull(hashMap);
            hashMap.put(str, dataMonitor);
        }
        HashMap<String, DataMonitor> hashMap2 = dataMyMonitors.monitors;
        Intrinsics.checkNotNull(hashMap2);
        for (String str2 : hashMap2.keySet()) {
            HashMap<String, DataMonitor> hashMap3 = dataMyMonitors.monitors;
            Intrinsics.checkNotNull(hashMap3);
            DataMonitor dataMonitor2 = hashMap3.get(str2);
            if (dataMonitor2 != null) {
                dataMonitor2.id = str2;
            }
        }
        dataMyMonitors.monitorsOrder = new ArrayList<>(this.monitorsOrder);
        return dataMyMonitors;
    }

    @NotNull
    public final HashMap<String, Monitor> getMonitors() {
        return this.monitors;
    }

    @NotNull
    public final ArrayList<String> getMonitorsOrder() {
        return this.monitorsOrder;
    }

    public final void setMonitors(@NotNull HashMap<String, Monitor> hashMap) {
        Intrinsics.checkNotNullParameter(hashMap, "<set-?>");
        this.monitors = hashMap;
    }

    public final void setMonitorsOrder(@NotNull ArrayList<String> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.monitorsOrder = arrayList;
    }
}