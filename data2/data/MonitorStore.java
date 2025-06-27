package com.air.advantage.data;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.air.advantage.jsondata.MasterStore;
import com.air.advantage.libraryairconlightjson.UartConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

@PurelyImplements({"SMAP\nMonitorStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MonitorStore.kt\ncom/air/advantage/data/MonitorStore\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,204:1\n1#2:205\n*E\n"})
/* renamed from: com.air.advantage.data.l1 */
/* loaded from: classes.dex */
public final class MonitorStore {

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = MonitorStore.class.getSimpleName();
    private boolean blockMonitorUpdate;

    @Nullable
    private ArrayList<DataMonitor> blockedMonitor;
    private boolean isMonitorPaused;

    @Nullable
    private b onMonitorChangeListener;

    @NotNull
    private final List<String> monitors = new ArrayList();

    @NotNull
    private final HashMap<String, DataMonitor> monitorHashMap = new HashMap<>();

    /* renamed from: com.air.advantage.data.l1$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.l1.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* renamed from: com.air.advantage.data.l1$b */
    public interface b {
        void onMonitorAdded(@Nullable String str, int i10, int i11);

        void onMonitorRemoved(@Nullable String str, int i10, int i11);

        void onMonitorUpdated(@Nullable String str, int i10);
    }

    public final void addMonitor(@Nullable Context context, @NotNull DataMonitor monitorSource, int i10) {
        Intrinsics.checkNotNullParameter(monitorSource, "monitorSource");
        Timber.forest.d("Adding new monitor " + monitorSource.id, new Object[0]);
        DataMonitor dataMonitor = new DataMonitor();
        DataMonitor.update$default(dataMonitor, context, monitorSource, null, false, 8, null);
        synchronized (MasterStore.class) {
            this.monitors.add(i10, monitorSource.id);
            this.monitorHashMap.put(dataMonitor.id, dataMonitor);
        }
        b bVar = this.onMonitorChangeListener;
        if (bVar != null) {
            if (i10 >= 19) {
                Intrinsics.checkNotNull(bVar);
                bVar.onMonitorUpdated(monitorSource.id, i10);
                return;
            }
            Intrinsics.checkNotNull(bVar);
            bVar.onMonitorAdded(monitorSource.id, i10, 1);
            Intent intent = new Intent(UartConstants.NUMBER_OF_MONITOR_UPDATE);
            Intrinsics.checkNotNull(context);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }

    public final boolean checkDuplicateName(@NotNull String monitorName) {
        Intrinsics.checkNotNullParameter(monitorName, "monitorName");
        synchronized (MasterStore.class) {
            Iterator<DataMonitor> it = this.monitorHashMap.values().iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(it.next().name, monitorName)) {
                    return true;
                }
            }
            Unit unit = Unit.INSTANCE;
            return false;
        }
    }

    public final void clear() {
        synchronized (MasterStore.class) {
            this.monitors.clear();
            this.monitorHashMap.clear();
            this.onMonitorChangeListener = null;
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void deleteMonitor(@Nullable Context context, @NotNull String monitorId) {
        Intrinsics.checkNotNullParameter(monitorId, "monitorId");
        synchronized (MasterStore.class) {
            Timber.forest.d("Deleting monitor " + monitorId, new Object[0]);
            int indexOf = this.monitors.indexOf(monitorId);
            this.monitorHashMap.remove(monitorId);
            this.monitors.remove(monitorId);
            b bVar = this.onMonitorChangeListener;
            if (bVar != null) {
                if (indexOf < 19) {
                    Intrinsics.checkNotNull(bVar);
                    bVar.onMonitorRemoved(monitorId, indexOf, 1);
                    Intent intent = new Intent(UartConstants.NUMBER_OF_MONITOR_UPDATE);
                    Intrinsics.checkNotNull(context);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } else {
                    Intrinsics.checkNotNull(bVar);
                    bVar.onMonitorUpdated(monitorId, indexOf);
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    @NotNull
    public final List<String> getItemList() {
        return new ArrayList(this.monitors);
    }

    @Nullable
    public final DataMonitor getMonitor(@Nullable String str) {
        DataMonitor dataMonitor;
        synchronized (MasterStore.class) {
            dataMonitor = this.monitorHashMap.get(str);
        }
        return dataMonitor;
    }

    @Nullable
    public final DataMonitor getMonitorAtPosition(int i10) {
        synchronized (MasterStore.class) {
            if (this.monitorHashMap.size() > i10) {
                return this.monitorHashMap.get(this.monitors.get(i10));
            }
            Unit unit = Unit.INSTANCE;
            return null;
        }
    }

    public final int numberOfMonitors() {
        int size;
        if (this.isMonitorPaused) {
            return 0;
        }
        synchronized (MasterStore.class) {
            size = this.monitors.size();
            if (size < 20) {
                size++;
            }
            Unit unit = Unit.INSTANCE;
        }
        return size;
    }

    public final int numberOfRealMonitor() {
        int size;
        synchronized (MasterStore.class) {
            size = this.monitors.size();
        }
        return size;
    }

    public final void setBlockMonitorUpdate(@Nullable Context context, boolean z7) {
        ArrayList<DataMonitor> arrayList;
        this.blockMonitorUpdate = z7;
        if (z7 || (arrayList = this.blockedMonitor) == null || context == null) {
            return;
        }
        updateMonitor(context, arrayList);
        this.blockedMonitor = null;
    }

    public final void setMonitorPaused(@Nullable Context context, boolean z7) {
        if (z7) {
            int numberOfMonitors = numberOfMonitors();
            this.isMonitorPaused = true;
            b bVar = this.onMonitorChangeListener;
            if (bVar != null) {
                Intrinsics.checkNotNull(bVar);
                bVar.onMonitorRemoved("Paused", 0, numberOfMonitors);
                Intent intent = new Intent(UartConstants.NUMBER_OF_MONITOR_UPDATE);
                Intrinsics.checkNotNull(context);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                return;
            }
            return;
        }
        this.isMonitorPaused = false;
        if (this.onMonitorChangeListener != null) {
            int numberOfMonitors2 = numberOfMonitors();
            b bVar2 = this.onMonitorChangeListener;
            Intrinsics.checkNotNull(bVar2);
            bVar2.onMonitorAdded("Paused", 0, numberOfMonitors2);
            Intent intent2 = new Intent(UartConstants.NUMBER_OF_MONITOR_UPDATE);
            Intrinsics.checkNotNull(context);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);
        }
    }

    public final void setOnMonitorChangeListener(@Nullable b bVar) {
        this.onMonitorChangeListener = bVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void updateMonitor(@NotNull Context context, @NotNull DataMaster incomingMasterData) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(incomingMasterData, "incomingMasterData");
        ArrayList<DataMonitor> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = incomingMasterData.myMonitors.monitorsOrder;
        Intrinsics.checkNotNull(arrayList2);
        Iterator<String> it = arrayList2.iterator();
        while (it.hasNext()) {
            DataMonitor monitor = incomingMasterData.myMonitors.getMonitor(it.next());
            if (monitor != null) {
                DataMonitor dataMonitor = new DataMonitor();
                DataMonitor.update$default(dataMonitor, null, monitor, null, false, 8, null);
                arrayList.add(arrayList.size(), dataMonitor);
            }
        }
        updateMonitor(context, arrayList);
    }

    private final void updateMonitor(Context context, ArrayList<DataMonitor> arrayList) {
        if (this.blockMonitorUpdate) {
            this.blockedMonitor = new ArrayList<>(arrayList);
            return;
        }
        ArrayList arrayList2 = new ArrayList(this.monitors);
        Intrinsics.checkNotNull(arrayList);
        Iterator<DataMonitor> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.remove(it.next().id);
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            String str = (String) it2.next();
            if (str != null) {
                deleteMonitor(context, str);
            }
        }
        Iterator<DataMonitor> it3 = arrayList.iterator();
        int i10 = 0;
        while (it3.hasNext()) {
            DataMonitor next = it3.next();
            int indexOf = this.monitors.indexOf(next.id);
            if (indexOf < 0) {
                Intrinsics.checkNotNull(next);
                addMonitor(context, next, i10);
            } else {
                if (indexOf != i10) {
                    Timber.forest.d("Monitor moved", new Object[0]);
                }
                DataMonitor monitorAtPosition = getMonitorAtPosition(i10);
                if (monitorAtPosition != null) {
                    DataMonitor.update$default(monitorAtPosition, context, next, null, false, 8, null);
                } else {
                    Timber.forest.d("Warning - null monitor", new Object[0]);
                }
            }
            i10++;
        }
    }
}