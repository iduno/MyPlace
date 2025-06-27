package com.air.advantage.data;

import android.content.Context;
import com.air.advantage.AppFeatures;
import com.air.advantage.StoredSystems;
import com.air.advantage.di.LocalBroadcaster;
import com.air.advantage.jsondata.MasterStore;
import com.air.advantage.libraryairconlightjson.UartConstants;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;
import java.util.Collection;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

/* renamed from: com.air.advantage.data.a */
/* loaded from: classes.dex */
public final class DataMasterAllImport {

    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final String LOG_TAG = DataMasterAllImport.class.getSimpleName();

    @NotNull
    private final ConcurrentSkipListMap<String, DataMaster> hashMap = new ConcurrentSkipListMap<>();

    /* renamed from: com.air.advantage.data.a$a */
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.a.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private Companion() {
        }
    }

    public final void clear() {
        this.hashMap.clear();
    }

    @Nullable
    public final DataMaster get(@Nullable String str) {
        return this.hashMap.get(str);
    }

    @Nullable
    public final DataMaster getFirst() {
        try {
            String firstKey = this.hashMap.firstKey();
            if (firstKey != null) {
                return this.hashMap.get(firstKey);
            }
            return null;
        } catch (NoSuchElementException e7) {
            AppFeatures.Error(AppFeatures.instance, e7, null, 2, null);
            return null;
        }
    }

    @NotNull
    public final NavigableSet<String> keySet() {
        Set<String> keySet = this.hashMap.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet, "<get-keys>(...)");
        return (NavigableSet) keySet;
    }

    public final void remove(@Nullable String str) {
        this.hashMap.remove(str);
    }

    public final int size() {
        return this.hashMap.size();
    }

    public final void update(@Nullable Context context, @NotNull DataMaster data, @Nullable String str) {
        Intrinsics.checkNotNullParameter(data, "data");
        String str2 = data.system.mid;
        if (str2 == null) {
            String json = new Gson().toJson(data);
            FirebaseCrashlytics.getInstance().recordException(new NullPointerException("MasterData mid is null " + json));
            return;
        }
        if (!this.hashMap.containsKey(str2)) {
            this.hashMap.put(data.system.mid, new DataMaster());
        }
        DataMaster dataMaster = this.hashMap.get(data.system.mid);
        Intrinsics.checkNotNull(dataMaster);
        DataMaster.update$default(dataMaster, data, null, false, 4, null);
        dataMaster.wifiIpAddress = str;
        dataMaster.myLights.scenesOrder = data.myLights.scenesOrder;
        if (StoredSystems.Companion.getInstance(context).numRemoteSystems() > 1) {
            ((LocalBroadcaster) KoinJavaComponent.get$default(LocalBroadcaster.class, null, null, 6, null)).sendBroadcast(UartConstants.SYSTEM_DISCOVERED);
        } else {
            Timber.forest.v("Sending quick broadcast as number of remotes <= 1", new Object[0]);
            MasterStore.helper.broadcast();
        }
    }

    @NotNull
    public final Collection<DataMaster> values() {
        Collection<DataMaster> values = this.hashMap.values();
        Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
        return values;
    }
}