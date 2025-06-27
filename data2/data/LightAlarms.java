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

@PurelyImplements({"SMAP\nLightAlarms.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LightAlarms.kt\ncom/air/advantage/data/LightAlarms\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,150:1\n1#2:151\n*E\n"})
/* renamed from: com.air.advantage.data.d1 */
/* loaded from: classes.dex */
public final class LightAlarms {

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = LightAlarms.class.getSimpleName();

    @Nullable
    private OnAlarmChangeListener onAlarmChangeListener;

    @NotNull
    private final List<String> lightAlarms = new ArrayList();

    @NotNull
    private final HashMap<String, DataAlarm> lightAlarmHashMap = new HashMap<>();

    /* renamed from: com.air.advantage.data.d1$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.d1.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* renamed from: com.air.advantage.data.d1$b */
    public interface OnAlarmChangeListener {
        void onAlarmAdded(@Nullable String str, int i10, int i11);

        void onAlarmRemoved(@Nullable String str, int i10, int i11);

        void onAlarmUpdated(@Nullable String str, int i10);
    }

    public final void addAlarm(@Nullable Context context, @NotNull DataAlarm lightAlarm, int i10) {
        Intrinsics.checkNotNullParameter(lightAlarm, "lightAlarm");
        Timber.forest.d("Adding new alarm " + lightAlarm.id, new Object[0]);
        DataAlarm dataAlarm = new DataAlarm();
        dataAlarm.update(context, lightAlarm, (DataManager) null);
        synchronized (MasterStore.class) {
            this.lightAlarms.add(i10, dataAlarm.id);
            this.lightAlarmHashMap.put(dataAlarm.id, dataAlarm);
        }
        OnAlarmChangeListener onAlarmChangeListener = this.onAlarmChangeListener;
        if (onAlarmChangeListener != null) {
            if (i10 >= 3) {
                Intrinsics.checkNotNull(onAlarmChangeListener);
                onAlarmChangeListener.onAlarmUpdated(lightAlarm.id, i10);
                return;
            }
            Intrinsics.checkNotNull(onAlarmChangeListener);
            onAlarmChangeListener.onAlarmAdded(lightAlarm.id, i10, 1);
            Intent intent = new Intent(UartConstants.NUMBER_OF_LIGHT_ALARM_UPDATE);
            Intrinsics.checkNotNull(context);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }

    public final void clear() {
        synchronized (MasterStore.class) {
            this.lightAlarms.clear();
            this.onAlarmChangeListener = null;
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void deleteAlarm(@Nullable Context context, @NotNull String alarmId) {
        Intrinsics.checkNotNullParameter(alarmId, "alarmId");
        synchronized (MasterStore.class) {
            Timber.forest.d("Deleting alarm " + alarmId, new Object[0]);
            int indexOf = this.lightAlarms.indexOf(alarmId);
            this.lightAlarmHashMap.remove(alarmId);
            this.lightAlarms.remove(alarmId);
            OnAlarmChangeListener onAlarmChangeListener = this.onAlarmChangeListener;
            if (onAlarmChangeListener != null) {
                if (indexOf < 3) {
                    Intrinsics.checkNotNull(onAlarmChangeListener);
                    onAlarmChangeListener.onAlarmRemoved(alarmId, indexOf, 1);
                    Intent intent = new Intent(UartConstants.NUMBER_OF_LIGHT_ALARM_UPDATE);
                    Intrinsics.checkNotNull(context);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } else {
                    Intrinsics.checkNotNull(onAlarmChangeListener);
                    onAlarmChangeListener.onAlarmUpdated(alarmId, indexOf);
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    @Nullable
    public final DataAlarm getAlarm(@Nullable String str) {
        DataAlarm dataAlarm;
        synchronized (MasterStore.class) {
            dataAlarm = this.lightAlarmHashMap.get(str);
        }
        return dataAlarm;
    }

    @Nullable
    public final DataAlarm getAlarmAtPosition(int i10) {
        synchronized (MasterStore.class) {
            if (this.lightAlarms.size() <= i10 || this.lightAlarmHashMap.size() <= i10) {
                Unit unit = Unit.INSTANCE;
                return null;
            }
            return this.lightAlarmHashMap.get(this.lightAlarms.get(i10));
        }
    }

    public final int numberOfAlarms() {
        int size;
        synchronized (MasterStore.class) {
            size = this.lightAlarms.size();
            if (size < 4) {
                size++;
            }
        }
        return size;
    }

    public final int numberOfRealAlarms() {
        int size;
        synchronized (MasterStore.class) {
            size = this.lightAlarms.size();
        }
        return size;
    }

    public final void setOnAlarmChangeListener(@Nullable OnAlarmChangeListener onAlarmChangeListener) {
        this.onAlarmChangeListener = onAlarmChangeListener;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void updateAlarm(@NotNull Context context, @NotNull DataMaster incomingMasterData) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(incomingMasterData, "incomingMasterData");
        ArrayList<DataAlarm> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = incomingMasterData.myLights.alarmsOrder;
        Intrinsics.checkNotNull(arrayList2);
        Iterator<String> it = arrayList2.iterator();
        while (it.hasNext()) {
            DataAlarm alarm = incomingMasterData.myLights.getAlarm(it.next());
            if (alarm != null) {
                arrayList.add(arrayList.size(), alarm);
            }
        }
        updateAlarm(context, arrayList);
    }

    private final void updateAlarm(Context context, ArrayList<DataAlarm> arrayList) {
        ArrayList arrayList2 = new ArrayList(this.lightAlarms);
        Iterator<DataAlarm> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.remove(it.next().id);
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            String str = (String) it2.next();
            if (str != null) {
                deleteAlarm(context, str);
            }
        }
        Iterator<DataAlarm> it3 = arrayList.iterator();
        int i10 = 0;
        while (it3.hasNext()) {
            DataAlarm next = it3.next();
            int indexOf = this.lightAlarms.indexOf(next.id);
            if (indexOf < 0) {
                Intrinsics.checkNotNull(next);
                addAlarm(context, next, i10);
            } else {
                DataAlarm alarmAtPosition = getAlarmAtPosition(i10);
                if (indexOf != i10) {
                    Timber.forest.d("Alarm moved", new Object[0]);
                }
                Intrinsics.checkNotNull(alarmAtPosition);
                alarmAtPosition.update(context, next, (DataManager) null);
            }
            i10++;
        }
    }
}