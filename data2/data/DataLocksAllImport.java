package com.air.advantage.data;

import com.air.advantage.locks.model.Lock;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.v */
/* loaded from: classes.dex */
public final class DataLocksAllImport {

    @NotNull
    public static final a Companion = new a(null);
    public static final int MAX_LOCK = 10;

    @SerializedName("locksOrder")
    @NotNull
    private ArrayList<String> locksOrder = new ArrayList<>();

    @SerializedName("locks")
    @NotNull
    @JvmField
    public HashMap<String, Lock> locks = new HashMap<>();

    /* renamed from: com.air.advantage.data.v$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.v.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    public final boolean addLock(@NotNull Lock lock) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        if (getLock(lock.getBleMac()) != null) {
            return true;
        }
        if (this.locks.size() >= 10) {
            return false;
        }
        this.locks.put(lock.getBleMac(), lock);
        this.locksOrder.add(lock.getBleMac());
        return true;
    }

    public final void clearLocks() {
        this.locks.clear();
        this.locksOrder.clear();
    }

    @Nullable
    public final Lock getLock(@Nullable String str) {
        if (str != null) {
            return this.locks.get(str);
        }
        return null;
    }

    @NotNull
    public final ArrayList<String> getLocksOrder() {
        return this.locksOrder;
    }

    public final boolean removeLock(@NotNull String lockId) {
        Intrinsics.checkNotNullParameter(lockId, "lockId");
        this.locksOrder.remove(lockId);
        return this.locks.remove(lockId) != null;
    }

    public final void setLocksOrder(@NotNull ArrayList<String> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.locksOrder = arrayList;
    }
}