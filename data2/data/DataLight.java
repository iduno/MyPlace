package com.air.advantage.data;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.air.advantage.libraryairconlightjson.LightState;
import com.air.advantage.libraryairconlightjson.UartConstants;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.data.r */
/* loaded from: classes.dex */
public final class DataLight {
    public static final int LIGHT_MAX_VALUE = 100;
    public static final int LIGHT_STEP_SIZE = 10;

    @NotNull
    public static final String MODULE_TYPE_STRING_DM = "DM";

    @NotNull
    public static final String MODULE_TYPE_STRING_HUE = "HUE";

    @NotNull
    public static final String MODULE_TYPE_STRING_LM = "LM/RM";

    @NotNull
    public static final String MODULE_TYPE_STRING_RM2 = "RM2";
    public static final int TYPE_FAVOURITE_GROUP = 4;
    public static final int TYPE_FAVOURITE_LIGHT = 5;
    public static final int TYPE_FAVOURITE_RELAY = 6;
    public static final int TYPE_GROUP = 1;
    public static final int TYPE_LIGHT = 2;
    public static final int TYPE_RELAY = 3;

    @Nullable
    @SerializedName("dimOffset")
    @JvmField
    public Integer dimOffset;

    @Nullable
    @SerializedName("id")
    @JvmField
    public String id;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("idOnHueBridge")
    @JvmField
    public String idOnHueBridge;

    @Nullable
    @SerializedName("moduleType")
    @JvmField
    public String moduleType;

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    @JvmField
    public String name;

    @Nullable
    @SerializedName("reachable")
    @JvmField
    public Boolean reachable;

    @Nullable
    @SerializedName("relay")
    @JvmField
    public Boolean relay;

    @Nullable
    @SerializedName("state")
    @JvmField
    public LightState state;

    @Exclude
    @JvmField
    public transient boolean thisIsRFDevice;

    @Nullable
    @SerializedName("value")
    @JvmField
    public Integer value;

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = DataLight.class.getSimpleName();

    @Exclude
    @Nullable
    @JvmField
    public transient Long expiryTime = 0L;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("type")
    @JvmField
    public Integer type = 2;

    @Exclude
    @Nullable
    @JvmField
    public transient Boolean enableInScene = Boolean.TRUE;

    /* renamed from: com.air.advantage.data.r$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.r.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public DataLight() {
    }

    public static /* synthetic */ boolean update$default(DataLight dataLight, Context context, DataLight dataLight2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 8) != 0) {
            z7 = false;
        }
        return dataLight.update(context, dataLight2, dataManager, z7);
    }

    public final void clearDataForBackup() {
        this.dimOffset = null;
        this.enableInScene = null;
        this.expiryTime = null;
        this.moduleType = null;
        this.reachable = null;
        this.relay = null;
        this.state = null;
        this.type = null;
        this.value = null;
    }

    public final void doUpdate(@Nullable Context context) {
        Timber.Forest forest = Timber.forest;
        forest.d("Sending update of light " + this.id, new Object[0]);
        Intent intent = new Intent(UartConstants.LIGHT_DATA_UPDATE);
        intent.putExtra("roomId", this.id);
        if (this.id == null) {
            forest.d("Warning - sending lightDataUpdate with null roomId", new Object[0]);
        }
        Intrinsics.checkNotNull(context);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public final void sanitiseData() {
        this.id = null;
    }

    public final int typeFavourite() {
        String str = this.id;
        Intrinsics.checkNotNull(str);
        if (str.length() < 7) {
            return 4;
        }
        Boolean bool = this.relay;
        if (bool == null) {
            return 5;
        }
        Intrinsics.checkNotNull(bool);
        return bool.booleanValue() ? 6 : 5;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@Nullable Context context, @Nullable DataLight dataLight, @Nullable DataManager dataManager) {
        return update$default(this, context, dataLight, dataManager, false, 8, null);
    }

    public final void updateLightDataForAlarm(@NotNull DataLight sourceDataLight) {
        Intrinsics.checkNotNullParameter(sourceDataLight, "sourceDataLight");
        this.id = sourceDataLight.id;
        this.name = null;
        this.enableInScene = null;
        this.relay = null;
        this.type = null;
        this.state = null;
        this.value = null;
    }

    public final void updateLightDataForScene(@NotNull DataLight sourceDataLight) {
        Intrinsics.checkNotNullParameter(sourceDataLight, "sourceDataLight");
        this.id = sourceDataLight.id;
        Integer num = null;
        this.name = null;
        this.enableInScene = null;
        this.relay = null;
        this.type = null;
        this.state = sourceDataLight.state;
        Integer num2 = sourceDataLight.type;
        if (num2 != null && num2.intValue() == 2) {
            num = sourceDataLight.value;
        }
        this.value = num;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void workOutType() {
        int i10;
        String str = this.id;
        Intrinsics.checkNotNull(str);
        if (str.length() < 7) {
            i10 = 1;
        } else {
            Boolean bool = this.relay;
            if (bool != null) {
                Intrinsics.checkNotNull(bool);
                i10 = bool.booleanValue() ? 3 : 2;
            }
        }
        this.type = i10;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    /* JADX WARN: Removed duplicated region for block: B:54:0x014e  */
    @JvmOverloads
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean update(@Nullable Context context, @Nullable DataLight dataLight, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        String str;
        Intrinsics.checkNotNull(dataLight);
        String str2 = dataLight.id;
        boolean z11 = true;
        if (str2 == null || ((str = this.id) != null && Intrinsics.areEqual(str, str2))) {
            z10 = false;
        } else {
            this.id = dataLight.id;
            z10 = true;
        }
        String str3 = dataLight.idOnHueBridge;
        if (str3 != null) {
            String str4 = this.idOnHueBridge;
            if (str4 == null || !Intrinsics.areEqual(str4, str3)) {
                this.idOnHueBridge = dataLight.idOnHueBridge;
                z10 = true;
            }
        } else if (z7 && this.idOnHueBridge != null) {
            if (dataManager != null) {
                dataManager.add("idOnHueBridge", null);
            }
            z10 = true;
        }
        String str5 = dataLight.name;
        if (str5 != null) {
            String str6 = this.name;
            if (str6 == null || !Intrinsics.areEqual(str6, str5)) {
                this.name = dataLight.name;
                if (dataManager != null) {
                    dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, dataLight.name);
                }
                z10 = true;
            }
        } else if (z7 && this.name != null) {
            if (dataManager != null) {
                dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, null);
            }
            z10 = true;
        }
        LightState lightState = dataLight.state;
        if (lightState != null) {
            LightState lightState2 = this.state;
            if (lightState2 == null || lightState2 != lightState) {
                this.state = lightState;
                if (dataManager != null) {
                    dataManager.add("state", dataLight.state);
                }
                z10 = true;
            }
        } else if (z7 && this.state != null) {
            if (dataManager != null) {
                dataManager.add("state", null);
            }
            z10 = true;
        }
        Integer num = dataLight.value;
        if (num != null) {
            Integer num2 = this.value;
            if (num2 == null || !Intrinsics.areEqual(num2, num)) {
                this.value = dataLight.value;
                if (dataManager != null) {
                    dataManager.add("value", dataLight.value);
                }
                z10 = true;
            }
        } else if (z7 && this.value != null) {
            if (dataManager != null) {
                dataManager.add("value", null);
            }
            z10 = true;
        }
        Boolean bool = dataLight.relay;
        if (bool != null) {
            Boolean bool2 = this.relay;
            if (bool2 == null || bool2 != bool) {
                this.relay = bool;
                if (dataManager != null) {
                    dataManager.add("relay", dataLight.relay);
                }
                z10 = true;
            }
        } else if (z7 && this.relay != null) {
            if (dataManager != null) {
                dataManager.add("relay", null);
            }
            z10 = true;
        }
        String str7 = dataLight.moduleType;
        if (str7 != null) {
            String str8 = this.moduleType;
            if (str8 == null || !Intrinsics.areEqual(str8, str7)) {
                this.moduleType = dataLight.moduleType;
                if (dataManager != null) {
                    dataManager.add("moduleType", dataLight.moduleType);
                }
                z10 = true;
            }
        } else if (z7 && this.moduleType != null) {
            if (dataManager != null) {
                dataManager.add("moduleType", null);
            }
            z10 = true;
        }
        Integer num3 = dataLight.dimOffset;
        if (num3 != null) {
            Integer num4 = this.dimOffset;
            if (num4 == null || !Intrinsics.areEqual(num4, num3)) {
                this.dimOffset = dataLight.dimOffset;
                if (dataManager != null) {
                    dataManager.add("dimOffset", dataLight.dimOffset);
                }
                z10 = true;
            }
        } else if (z7 && this.dimOffset != null) {
            if (dataManager != null) {
                dataManager.add("dimOffset", null);
            }
            z10 = true;
        }
        Boolean bool3 = dataLight.reachable;
        if (bool3 != null) {
            Boolean bool4 = this.reachable;
            if (bool4 == null || !Intrinsics.areEqual(bool4, bool3)) {
                this.reachable = dataLight.reachable;
                if (dataManager != null) {
                    dataManager.addSetValue("reachable", dataLight.reachable);
                }
            } else {
                z11 = z10;
            }
        } else if (z7 && this.reachable != null) {
            if (dataManager != null) {
                dataManager.add("reachable", null);
            }
        }
        workOutType();
        if (z11 && context != null) {
            doUpdate(context);
        }
        return z11;
    }

    public DataLight(@Nullable String str, @Nullable String str2, @Nullable LightState lightState) {
        this.id = str;
        this.name = str2;
        this.state = lightState;
    }
}