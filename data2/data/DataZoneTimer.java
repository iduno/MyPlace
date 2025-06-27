package com.air.advantage.data;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.air.advantage.libraryairconlightjson.UartConstants;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.data.a1 */
/* loaded from: classes.dex */
public final class DataZoneTimer {

    @NotNull
    public static final a Companion = new a(null);
    public static final int OFF = 0;
    public static final int OFF_ONLY = 2;
    public static final int ON_AND_OFF = 3;
    public static final int ON_ONLY = 1;
    private int endTimeHours;
    private int endTimeMinutes;
    private int startTimeHours;
    private int startTimeMinutes;
    private final boolean syncToken;
    private int timerStatus = 0;
    private boolean isValid = false;

    /* renamed from: com.air.advantage.data.a1$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.a1.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    public final int getEndTimeHours() {
        return this.endTimeHours;
    }

    public final int getEndTimeMinutes() {
        return this.endTimeMinutes;
    }

    public final int getStartTimeHours() {
        return this.startTimeHours;
    }

    public final int getStartTimeMinutes() {
        return this.startTimeMinutes;
    }

    public final boolean getSyncToken() {
        return this.syncToken;
    }

    public final int getTimerStatus() {
        return this.timerStatus;
    }

    public final boolean isValid() {
        return this.isValid;
    }

    public final void setEndTimeHours(int i10) {
        this.endTimeHours = i10;
    }

    public final void setEndTimeMinutes(int i10) {
        this.endTimeMinutes = i10;
    }

    public final void setStartTimeHours(int i10) {
        this.startTimeHours = i10;
    }

    public final void setStartTimeMinutes(int i10) {
        this.startTimeMinutes = i10;
    }

    public final void setTimerStatus(int i10) {
        this.timerStatus = i10;
    }

    public final void setValid(boolean z7) {
        this.isValid = z7;
    }

    public final void update(@Nullable Context context, @NotNull DataZoneTimer dataZoneTimer) {
        boolean z7;
        Intrinsics.checkNotNullParameter(dataZoneTimer, "dataZoneTimer");
        synchronized (Boolean.valueOf(this.syncToken)) {
            int i10 = this.startTimeHours;
            int i11 = dataZoneTimer.startTimeHours;
            if (i10 != i11) {
                this.startTimeHours = i11;
                z7 = true;
            } else {
                z7 = false;
            }
            int i12 = this.startTimeMinutes;
            int i13 = dataZoneTimer.startTimeMinutes;
            if (i12 != i13) {
                this.startTimeMinutes = i13;
                z7 = true;
            }
            int i14 = this.endTimeHours;
            int i15 = dataZoneTimer.endTimeHours;
            if (i14 != i15) {
                this.endTimeHours = i15;
                z7 = true;
            }
            int i16 = this.endTimeMinutes;
            int i17 = dataZoneTimer.endTimeMinutes;
            if (i16 != i17) {
                this.endTimeMinutes = i17;
                z7 = true;
            }
            int i18 = this.timerStatus;
            int i19 = dataZoneTimer.timerStatus;
            if (i18 != i19) {
                this.timerStatus = i19;
                z7 = true;
            }
            if (z7) {
                Timber.forest.d(DataZoneTimer.class.getName(), "data has been updated");
                Intent intent = new Intent(UartConstants.CLOCK_DATA_UPDATE);
                Intrinsics.checkNotNull(context);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
            this.isValid = true;
            Unit unit = Unit.INSTANCE;
        }
    }
}