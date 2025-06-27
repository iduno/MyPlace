package com.air.advantage.data;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.air.advantage.libraryairconlightjson.UartConstants;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.data.u0 */
/* loaded from: classes.dex */
public final class DataMyThing {

    @NotNull
    public static final String BUTTON_TYPE_DIMMABLE = "dimmable";

    @NotNull
    public static final String BUTTON_TYPE_NONE = "none";

    @NotNull
    public static final String BUTTON_TYPE_ON_OFF = "onOff";

    @NotNull
    public static final String BUTTON_TYPE_OPEN_CLOSE = "openClose";

    @NotNull
    public static final String BUTTON_TYPE_UP_DOWN = "upDown";
    public static final int CHANNEL_DIP_STATE_BLIND_DUAL_RELAY = 2;
    public static final int CHANNEL_DIP_STATE_BLIND_SINGLE_RELAY = 1;
    public static final int CHANNEL_DIP_STATE_DIMMABLE_DM_LIGHT = 5;
    public static final int CHANNEL_DIP_STATE_DIMMABLE_FAN = 9;
    public static final int CHANNEL_DIP_STATE_GARAGE_DOOR = 3;
    public static final int CHANNEL_DIP_STATE_GARAGE_DOOR_V2 = 10;
    public static final int CHANNEL_DIP_STATE_LIGHT = 4;
    public static final int CHANNEL_DIP_STATE_NOT_USED = 0;
    public static final int CHANNEL_DIP_STATE_OTHER_RELAY = 8;

    @NotNull
    public static final a Companion = new a(null);
    public static final int DOWN_OFF_CLOSE_VALUE = 0;
    public static final int IN_PROGRESS_DOWN_CLOSE_VALUE = 25;
    public static final int IN_PROGRESS_UP_OPEN_VALUE = 75;
    public static final int STOP_VALUE = 50;
    public static final int TYPE_CLOSE_OPEN = 5;
    public static final int TYPE_DUAL_RELAY = 3;
    public static final int TYPE_FAVOURITE_GROUP = 6;
    public static final int TYPE_GROUP = 1;
    public static final int TYPE_OFF_ON = 4;
    public static final int TYPE_SINGLE_RELAY = 2;
    public static final int UP_ON_OPEN_VALUE = 100;

    @Nullable
    @SerializedName("RFLinkTimeout")
    @JvmField
    public Boolean RFLinkTimeout;

    @Exclude
    @Nullable
    @JvmField
    public transient Long RFLinkTimeoutTimeStamp;

    @Nullable
    @SerializedName("batteryLow")
    @JvmField
    public Boolean batteryLow;

    @Nullable
    @SerializedName("buttonType")
    @JvmField
    public String buttonType;

    @Nullable
    @SerializedName("channelDipState")
    @JvmField
    public Integer channelDipState;

    @Nullable
    @SerializedName("dimOffset")
    @JvmField
    public Integer dimOffset;

    @Nullable
    @SerializedName("dimPercent")
    @JvmField
    public Integer dimPercent;

    @Exclude
    @Nullable
    private transient Boolean enableInScene;

    @Exclude
    @Nullable
    @JvmField
    public transient Long expiryTime;

    @Nullable
    @SerializedName("id")
    @JvmField
    public String id;

    @Nullable
    @SerializedName("isCalibrated")
    @JvmField
    public Boolean isCalibrated;

    @Exclude
    @Nullable
    @JvmField
    public transient Long lowBatteryTimeStamp;

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    @JvmField
    public String name;

    @Exclude
    @JvmField
    public transient boolean thisIsRFDevice;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("type")
    @JvmField
    public Integer type;

    @Nullable
    @SerializedName("value")
    @JvmField
    public Integer value;

    /* renamed from: com.air.advantage.data.u0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.u0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* renamed from: com.air.advantage.data.u0$b */
    public static final class b {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ b[] $VALUES;
        public static final b GARAGE_BUTTON_STATE_UP = new b("GARAGE_BUTTON_STATE_UP", 0);
        public static final b GARAGE_BUTTON_STATE_DOWN = new b("GARAGE_BUTTON_STATE_DOWN", 1);
        public static final b GARAGE_BUTTON_STATE_OPENING = new b("GARAGE_BUTTON_STATE_OPENING", 2);
        public static final b GARAGE_BUTTON_STATE_CLOSING = new b("GARAGE_BUTTON_STATE_CLOSING", 3);
        public static final b GARAGE_BUTTON_STATE_UNKNOWN = new b("GARAGE_BUTTON_STATE_UNKNOWN", 4);

        private static final /* synthetic */ b[] $values() {
            return new b[]{GARAGE_BUTTON_STATE_UP, GARAGE_BUTTON_STATE_DOWN, GARAGE_BUTTON_STATE_OPENING, GARAGE_BUTTON_STATE_CLOSING, GARAGE_BUTTON_STATE_UNKNOWN};
        }

        static {
            b[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private b(String str, int i10) {
        }

        @NotNull
        public static EnumEntries<b> getEntries() {
            return $ENTRIES;
        }

        public static b valueOf(String str) {
            return (b) Enum.valueOf(b.class, str);
        }

        public static b[] values() {
            return (b[]) $VALUES.clone();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public DataMyThing() {
        this.expiryTime = 0L;
        this.type = 2;
        this.enableInScene = Boolean.TRUE;
    }

    public static /* synthetic */ boolean update$default(DataMyThing dataMyThing, Context context, DataMyThing dataMyThing2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 8) != 0) {
            z7 = false;
        }
        return dataMyThing.update(context, dataMyThing2, dataManager, z7);
    }

    public final void clearDataForBackup() {
        this.channelDipState = null;
        this.dimOffset = null;
        this.dimPercent = null;
        this.enableInScene = null;
        this.expiryTime = null;
        this.type = null;
        this.value = null;
        this.batteryLow = null;
        this.RFLinkTimeout = null;
        this.isCalibrated = null;
    }

    public final void doUpdate(@Nullable Context context) {
        Timber.Forest forest = Timber.forest;
        forest.d("Sending update of thing " + this.id, new Object[0]);
        Intent intent = new Intent(UartConstants.THING_DATA_UPDATE);
        intent.putExtra("roomId", this.id);
        if (this.id == null) {
            forest.d("Warning - sending thingDataUpdate with null roomId", new Object[0]);
        }
        Intrinsics.checkNotNull(context);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Nullable
    public final Boolean getEnableInScene() {
        return this.enableInScene;
    }

    public final void setEnableInScene(@Nullable Boolean bool) {
        this.enableInScene = bool;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@Nullable Context context, @Nullable DataMyThing dataMyThing, @Nullable DataManager dataManager) {
        return update$default(this, context, dataMyThing, dataManager, false, 8, null);
    }

    public final void updateThingDataForScene(@NotNull DataMyThing sourceDataThing) {
        Intrinsics.checkNotNullParameter(sourceDataThing, "sourceDataThing");
        this.id = sourceDataThing.id;
        this.name = null;
        this.value = sourceDataThing.value;
        this.dimPercent = sourceDataThing.dimPercent;
        this.dimOffset = sourceDataThing.dimOffset;
        this.type = null;
        this.buttonType = null;
        this.enableInScene = null;
        this.batteryLow = null;
        this.RFLinkTimeout = null;
        this.isCalibrated = null;
    }

    public final void workOutType() {
        int i10;
        String str = this.id;
        Intrinsics.checkNotNull(str);
        if (str.length() < 7) {
            i10 = 1;
        } else {
            Integer num = this.channelDipState;
            if (num == null || num == null || num.intValue() != 2) {
                Integer num2 = this.channelDipState;
                i10 = (num2 == null || num2 == null || num2.intValue() != 8) ? 2 : 4;
            } else {
                i10 = 3;
            }
        }
        this.type = i10;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    /* JADX WARN: Removed duplicated region for block: B:62:0x018a  */
    @JvmOverloads
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean update(@Nullable Context context, @Nullable DataMyThing dataMyThing, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        String str;
        Intrinsics.checkNotNull(dataMyThing);
        String str2 = dataMyThing.id;
        boolean z11 = true;
        if (str2 == null || ((str = this.id) != null && Intrinsics.areEqual(str, str2))) {
            z10 = false;
        } else {
            this.id = dataMyThing.id;
            z10 = true;
        }
        String str3 = dataMyThing.name;
        if (str3 != null) {
            String str4 = this.name;
            if (str4 == null || !Intrinsics.areEqual(str4, str3)) {
                this.name = dataMyThing.name;
                if (dataManager != null) {
                    dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, dataMyThing.name);
                }
                z10 = true;
            }
        } else if (z7 && this.name != null) {
            if (dataManager != null) {
                dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, null);
            }
            z10 = true;
        }
        Integer num = dataMyThing.value;
        if (num != null) {
            Integer num2 = this.value;
            if (num2 == null || !Intrinsics.areEqual(num2, num)) {
                this.value = dataMyThing.value;
                if (dataManager != null) {
                    dataManager.add("value", dataMyThing.value);
                }
                z10 = true;
            }
        } else if (z7 && this.value != null) {
            if (dataManager != null) {
                dataManager.add("value", null);
            }
            z10 = true;
        }
        Integer num3 = dataMyThing.dimPercent;
        if (num3 != null) {
            Integer num4 = this.dimPercent;
            if (num4 == null || !Intrinsics.areEqual(num4, num3)) {
                this.dimPercent = dataMyThing.dimPercent;
                if (dataManager != null) {
                    dataManager.add("dimPercent", dataMyThing.dimPercent);
                }
                z10 = true;
            }
        } else if (z7 && this.dimPercent != null) {
            if (dataManager != null) {
                dataManager.add("dimPercent", null);
            }
            z10 = true;
        }
        Integer num5 = dataMyThing.dimOffset;
        if (num5 != null) {
            Integer num6 = this.dimOffset;
            if (num6 == null || !Intrinsics.areEqual(num6, num5)) {
                this.dimOffset = dataMyThing.dimOffset;
                if (dataManager != null) {
                    dataManager.add("dimOffset", dataMyThing.dimOffset);
                }
                z10 = true;
            }
        } else if (z7 && this.dimOffset != null) {
            if (dataManager != null) {
                dataManager.add("dimOffset", null);
            }
            z10 = true;
        }
        Boolean bool = dataMyThing.batteryLow;
        if (bool != null) {
            Boolean bool2 = this.batteryLow;
            if (bool2 == null || !Intrinsics.areEqual(bool2, bool)) {
                this.batteryLow = dataMyThing.batteryLow;
                if (dataManager != null) {
                    dataManager.add("batteryLow", dataMyThing.batteryLow);
                }
                z10 = true;
            }
        } else if (z7 && this.batteryLow != null) {
            if (dataManager != null) {
                dataManager.add("batteryLow", null);
            }
            z10 = true;
        }
        Boolean bool3 = dataMyThing.RFLinkTimeout;
        if (bool3 != null) {
            Boolean bool4 = this.RFLinkTimeout;
            if (bool4 == null || !Intrinsics.areEqual(bool4, bool3)) {
                this.RFLinkTimeout = dataMyThing.RFLinkTimeout;
                if (dataManager != null) {
                    dataManager.add("RFLinkTimeout", dataMyThing.RFLinkTimeout);
                }
                z10 = true;
            }
        } else if (z7 && this.RFLinkTimeout != null) {
            if (dataManager != null) {
                dataManager.add("RFLinkTimeout", null);
            }
            z10 = true;
        }
        Boolean bool5 = dataMyThing.isCalibrated;
        if (bool5 != null) {
            Boolean bool6 = this.isCalibrated;
            if (bool6 == null || !Intrinsics.areEqual(bool6, bool5)) {
                this.isCalibrated = dataMyThing.isCalibrated;
                if (dataManager != null) {
                    dataManager.add("isCalibrated", dataMyThing.isCalibrated);
                }
                z10 = true;
            }
        } else if (z7 && this.isCalibrated != null) {
            if (dataManager != null) {
                dataManager.add("isCalibrated", null);
            }
            z10 = true;
        }
        String str5 = dataMyThing.buttonType;
        if (str5 != null) {
            String str6 = this.buttonType;
            if (str6 == null || !Intrinsics.areEqual(str6, str5)) {
                this.buttonType = dataMyThing.buttonType;
                if (dataManager != null) {
                    dataManager.add("buttonType", dataMyThing.buttonType);
                }
                z10 = true;
            }
        } else if (z7 && this.buttonType != null) {
            if (dataManager != null) {
                dataManager.add("buttonType", null);
            }
            z10 = true;
        }
        Integer num7 = dataMyThing.channelDipState;
        if (num7 != null) {
            Integer num8 = this.channelDipState;
            if (num8 == null || !Intrinsics.areEqual(num8, num7)) {
                this.channelDipState = dataMyThing.channelDipState;
                if (dataManager != null) {
                    dataManager.add("channelDipState", dataMyThing.channelDipState);
                }
            } else {
                z11 = z10;
            }
        } else if (z7 && this.channelDipState != null) {
            if (dataManager != null) {
                dataManager.add("channelDipState", null);
            }
        }
        workOutType();
        if (z11 && context != null) {
            doUpdate(context);
        }
        return z11;
    }

    public DataMyThing(@Nullable String str, @Nullable String str2, @Nullable Integer num, @Nullable Integer num2) {
        this.expiryTime = 0L;
        this.type = 2;
        this.enableInScene = Boolean.TRUE;
        this.id = str;
        this.name = str2;
        this.value = num;
        this.channelDipState = num2;
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0005: CONSTRUCTOR 
      (r1v0 java.lang.String)
      (r2v0 java.lang.String)
      (r3v0 java.lang.Integer)
      (wrap:java.lang.Integer:?: TERNARY null = ((wrap:int:0x0000: ARITH (r5v0 int) & (8 int) A[WRAPPED]) != (0 int)) ? (null java.lang.Integer) : (r4v0 java.lang.Integer))
     A[MD:(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer):void (m)] (LINE:13) call: com.air.advantage.data.u0.<init>(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer):void type: THIS */
    public /* synthetic */ DataMyThing(String str, String str2, Integer num, Integer num2, int i10, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, num, (i10 & 8) != 0 ? null : num2);
    }
}