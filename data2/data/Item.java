package com.air.advantage.data;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.air.advantage.libraryairconlightjson.LightState;
import com.air.advantage.libraryairconlightjson.UartConstants;
import com.air.advantage.locks.model.Lock;
import com.air.advantage.sonos.Sonos;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.data.r0 */
/* loaded from: classes.dex */
public final class Item {
    public static final int ITEM_TYPE_AIRCON = 3;
    public static final int ITEM_TYPE_LIGHT = 1;
    public static final int ITEM_TYPE_LOCK = 6;
    public static final int ITEM_TYPE_MONITOR = 5;
    public static final int ITEM_TYPE_SENSOR = 4;
    public static final int ITEM_TYPE_SONOS = 7;
    public static final int ITEM_TYPE_THING = 2;
    public static final int TYPE_AIRCON = 18;
    public static final int TYPE_FAVOURITE_LIGHT = 5;
    public static final int TYPE_FAVOURITE_LIGHT_GROUP = 4;
    public static final int TYPE_FAVOURITE_LIGHT_RELAY = 6;
    public static final int TYPE_LAUNCHER = 17;
    public static final int TYPE_LIGHT = 2;
    public static final int TYPE_LIGHT_GROUP = 1;
    public static final int TYPE_LIGHT_RELAY = 3;
    public static final int TYPE_MONITOR = 22;
    public static final int TYPE_MONITOR_GROUP = 23;
    public static final int TYPE_SENSOR = 21;
    public static final int TYPE_SONOS = 24;
    public static final int TYPE_SONOS_GROUP = 25;
    public static final int TYPE_THING_CLOSE_OPEN = 11;
    public static final int TYPE_THING_DIMMABLE_FAN = 19;
    public static final int TYPE_THING_DUAL_RELAY = 9;
    public static final int TYPE_THING_FAVOURITE_CLOSE_OPEN = 16;
    public static final int TYPE_THING_FAVOURITE_DIMMABLE_FAN = 20;
    public static final int TYPE_THING_FAVOURITE_DUAL_RELAY = 14;
    public static final int TYPE_THING_FAVOURITE_GARAGE_V2 = 27;
    public static final int TYPE_THING_FAVOURITE_GROUP = 12;
    public static final int TYPE_THING_FAVOURITE_OFF_ON = 15;
    public static final int TYPE_THING_FAVOURITE_SINGLE_RELAY = 13;
    public static final int TYPE_THING_GARAGE_V2 = 26;
    public static final int TYPE_THING_GROUP = 7;
    public static final int TYPE_THING_OFF_ON = 10;
    public static final int TYPE_THING_SINGLE_RELAY = 8;

    @Nullable
    @SerializedName("RFLinkTimeout")
    private Boolean RFLinkTimeout;

    @Nullable
    @SerializedName("aircon")
    @JvmField
    public DataAirconSystem aircon;

    @Nullable
    private String appToLaunchPackageName;

    @Nullable
    @SerializedName("batteryLow")
    private Boolean batteryLow;

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
    @JvmField
    public transient Boolean enableInScene;

    @Exclude
    private transient long expiryTime;

    @Nullable
    @SerializedName("groupState")
    @JvmField
    public State groupState;

    @Nullable
    @SerializedName("id")
    @JvmField
    public String id;

    @Nullable
    @SerializedName("isCalibrated")
    private Boolean isCalibrated;

    @Nullable
    @SerializedName("lock")
    private Lock itemAsLock;

    @Nullable
    @SerializedName("itemType")
    @JvmField
    public Integer itemType;

    @Nullable
    @SerializedName("value")
    private Integer itemValue;

    @Nullable
    @SerializedName("moduleType")
    @JvmField
    public String moduleType;

    @Nullable
    @SerializedName("monitor")
    @JvmField
    public DataMonitor monitor;

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    @JvmField
    public String name;

    @Nullable
    @SerializedName("relay")
    private Boolean relay;

    @Nullable
    @SerializedName("sensor")
    @JvmField
    public DataSensor sensor;

    @Nullable
    @SerializedName("sonos")
    @JvmField
    public Sonos sonos;

    @Nullable
    @SerializedName("state")
    @JvmField
    public LightState state;

    @JsonExporter(export = false)
    @Nullable
    @SerializedName("type")
    @JvmField
    public Integer type;

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = Item.class.getSimpleName();

    /* renamed from: com.air.advantage.data.r0$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.r0.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final Item createMonitorGroupStoreItem(@Nullable String str, @Nullable String str2) {
            Item item = new Item((DefaultConstructorMarker) null);
            item.id = str;
            item.name = str2;
            item.itemType = 5;
            item.type = 23;
            return item;
        }

        @NotNull
        public final Item createSonosGroupStoreItem(@Nullable String str, @Nullable String str2) {
            Item item = new Item((DefaultConstructorMarker) null);
            item.id = str;
            item.name = str2;
            item.itemType = 7;
            item.type = 25;
            return item;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.r0.<init>():void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public /* synthetic */ Item(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private final void doGroupNameUpdate(Context context) {
        Integer num;
        Integer num2 = this.type;
        if (num2 != null) {
            if ((num2 != null && num2.intValue() == 7) || ((num = this.type) != null && num.intValue() == 12)) {
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(UartConstants.THING_GROUP_NAME_UPDATE));
            }
        }
    }

    private final void doGroupUpdate(Context context) {
        Integer num;
        Integer num2 = this.type;
        if (num2 != null) {
            if ((num2 != null && num2.intValue() == 7) || ((num = this.type) != null && num.intValue() == 12)) {
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(UartConstants.THING_GROUP_UPDATE));
            }
        }
    }

    public final boolean checkForIsNotCalibrated() {
        Boolean bool = this.isCalibrated;
        if (bool == null) {
            return false;
        }
        Intrinsics.checkNotNull(bool);
        return !bool.booleanValue();
    }

    public final boolean checkForLowBattery() {
        Boolean bool = this.batteryLow;
        if (bool == null) {
            return false;
        }
        Intrinsics.checkNotNull(bool);
        return bool.booleanValue();
    }

    public final boolean checkForRFLinkTimeout() {
        Boolean bool = this.RFLinkTimeout;
        if (bool == null) {
            return false;
        }
        Intrinsics.checkNotNull(bool);
        return bool.booleanValue();
    }

    public final void doUpdate(@Nullable Context context) {
        Intent intent;
        Integer num = this.itemType;
        if (num != null) {
            if (num != null && num.intValue() == 1) {
                Timber.forest.d("Sending update of light " + this.id, new Object[0]);
                intent = new Intent(UartConstants.LIGHT_DATA_UPDATE);
            } else {
                Integer num2 = this.itemType;
                if (num2 != null && num2.intValue() == 2) {
                    Timber.forest.d("Sending update of thing " + this.id, new Object[0]);
                    intent = new Intent(UartConstants.THING_DATA_UPDATE);
                } else {
                    Integer num3 = this.itemType;
                    if (num3 != null && num3.intValue() == 4) {
                        Timber.forest.d("Sending update of sensor " + this.id, new Object[0]);
                        intent = new Intent(UartConstants.SENSOR_DATA_UPDATE);
                    } else {
                        intent = null;
                    }
                }
            }
            if (intent != null) {
                intent.putExtra("roomId", this.id);
                if (this.id == null) {
                    Timber.forest.d("Warning - sending lightDataUpdate or thingDataUpdate with null roomId", new Object[0]);
                }
                Intrinsics.checkNotNull(context);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        }
    }

    @Nullable
    public final String getAppToLaunchPackageName() {
        return this.appToLaunchPackageName;
    }

    @Nullable
    public final Boolean getBatteryLow() {
        return this.batteryLow;
    }

    public final long getExpiryTime() {
        return this.expiryTime;
    }

    public final int getFavouriteType() {
        Integer num = this.type;
        if (num == null) {
            return 0;
        }
        if (num != null && num.intValue() == 2) {
            return 5;
        }
        if (num != null && num.intValue() == 1) {
            return 4;
        }
        if (num != null && num.intValue() == 3) {
            return 6;
        }
        if (num != null && num.intValue() == 7) {
            return 12;
        }
        if (num != null && num.intValue() == 8) {
            return 13;
        }
        if (num != null && num.intValue() == 9) {
            return 14;
        }
        if (num != null && num.intValue() == 10) {
            return 15;
        }
        if (num != null && num.intValue() == 11) {
            return 16;
        }
        if (num != null && num.intValue() == 19) {
            return 20;
        }
        return (num != null && num.intValue() == 26) ? 27 : 0;
    }

    @Nullable
    public final DataGroupThing getItemAsDataGroupThing() {
        Integer num;
        Integer num2 = this.itemType;
        if (num2 == null || num2 == null || num2.intValue() != 2 || (num = this.type) == null || num == null || num.intValue() != 7) {
            return null;
        }
        DataGroupThing dataGroupThing = new DataGroupThing();
        dataGroupThing.id = this.id;
        dataGroupThing.name = this.name;
        dataGroupThing.state = this.groupState;
        return dataGroupThing;
    }

    @Nullable
    public final DataLight getItemAsDataLight() {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4 = this.itemType;
        if (num4 == null || num4 == null || num4.intValue() != 1 || (num = this.type) == null) {
            return null;
        }
        if ((num == null || num.intValue() != 2) && (((num2 = this.type) == null || num2.intValue() != 3) && ((num3 = this.type) == null || num3.intValue() != 1))) {
            return null;
        }
        DataLight dataLight = new DataLight();
        dataLight.id = this.id;
        dataLight.name = this.name;
        dataLight.state = this.state;
        dataLight.value = this.itemValue;
        dataLight.dimOffset = this.dimOffset;
        dataLight.relay = this.relay;
        dataLight.enableInScene = this.enableInScene;
        dataLight.expiryTime = Long.valueOf(this.expiryTime);
        dataLight.workOutType();
        return dataLight;
    }

    @Nullable
    public final DataMyThing getItemAsDataThing() {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Integer num5;
        Integer num6;
        Integer num7 = this.itemType;
        if (num7 == null || num7 == null || num7.intValue() != 2 || (num = this.type) == null) {
            return null;
        }
        if ((num == null || num.intValue() != 8) && (((num2 = this.type) == null || num2.intValue() != 9) && (((num3 = this.type) == null || num3.intValue() != 11) && (((num4 = this.type) == null || num4.intValue() != 10) && (((num5 = this.type) == null || num5.intValue() != 19) && ((num6 = this.type) == null || num6.intValue() != 26)))))) {
            return null;
        }
        DataMyThing dataMyThing = new DataMyThing();
        dataMyThing.id = this.id;
        dataMyThing.name = this.name;
        dataMyThing.value = this.itemValue;
        dataMyThing.dimPercent = this.dimPercent;
        dataMyThing.dimOffset = this.dimOffset;
        dataMyThing.channelDipState = this.channelDipState;
        dataMyThing.buttonType = this.buttonType;
        dataMyThing.setEnableInScene(this.enableInScene);
        dataMyThing.expiryTime = Long.valueOf(this.expiryTime);
        dataMyThing.batteryLow = this.batteryLow;
        dataMyThing.RFLinkTimeout = this.RFLinkTimeout;
        dataMyThing.isCalibrated = this.isCalibrated;
        return dataMyThing;
    }

    @Nullable
    public final Lock getItemAsLock() {
        return this.itemAsLock;
    }

    @Nullable
    public final Integer getItemValue() {
        return this.itemValue;
    }

    @Nullable
    public final Boolean getRFLinkTimeout() {
        return this.RFLinkTimeout;
    }

    @Nullable
    public final Boolean getRelay() {
        return this.relay;
    }

    @Nullable
    public final Boolean isCalibrated() {
        return this.isCalibrated;
    }

    public final boolean isGarageVersion2() {
        Integer num = this.type;
        return (num == null || num == null || num.intValue() != 26) ? false : true;
    }

    public final void setAppToLaunchPackageName(@Nullable String str) {
        this.appToLaunchPackageName = str;
    }

    public final void setBatteryLow(@Nullable Boolean bool) {
        this.batteryLow = bool;
    }

    public final void setCalibrated(@Nullable Boolean bool) {
        this.isCalibrated = bool;
    }

    public final void setExpiryTime(long j10) {
        this.expiryTime = j10;
    }

    public final void setItemAsLock(@Nullable Lock lock) {
        this.itemAsLock = lock;
    }

    public final void setItemValue(@Nullable Integer num) {
        this.itemValue = num;
    }

    public final void setRFLinkTimeout(@Nullable Boolean bool) {
        this.RFLinkTimeout = bool;
    }

    public final void setRelay(@Nullable Boolean bool) {
        this.relay = bool;
    }

    public final boolean thisItemHasWarning() {
        if (checkForLowBattery() || checkForRFLinkTimeout()) {
            return true;
        }
        return checkForIsNotCalibrated();
    }

    /* JADX WARN: Removed duplicated region for block: B:168:0x0227  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean update(@Nullable Context context, @Nullable Item item) {
        boolean z7;
        boolean z10;
        Lock lock;
        Boolean bool;
        Boolean bool2;
        Boolean bool3;
        String str;
        State state;
        Integer num;
        String str2;
        String str3;
        Integer num2;
        Integer num3;
        Boolean bool4;
        Integer num4;
        Integer num5;
        Integer num6;
        LightState lightState;
        String str4;
        String str5;
        Intrinsics.checkNotNull(item);
        String str6 = item.id;
        boolean z11 = true;
        boolean z12 = false;
        if (str6 == null || ((str5 = this.id) != null && Intrinsics.areEqual(str5, str6))) {
            z7 = false;
        } else {
            this.id = item.id;
            z7 = true;
        }
        String str7 = item.name;
        if (str7 == null || ((str4 = this.name) != null && Intrinsics.areEqual(str4, str7))) {
            z10 = false;
        } else {
            this.name = item.name;
            z7 = true;
            z10 = true;
        }
        LightState lightState2 = item.state;
        if (lightState2 != null && ((lightState = this.state) == null || lightState != lightState2)) {
            this.state = lightState2;
            z7 = true;
        }
        Integer num7 = item.itemValue;
        if (num7 != null && ((num6 = this.itemValue) == null || !Intrinsics.areEqual(num6, num7))) {
            this.itemValue = item.itemValue;
            z7 = true;
        }
        Integer num8 = item.dimPercent;
        if (num8 != null && ((num5 = this.dimPercent) == null || !Intrinsics.areEqual(num5, num8))) {
            this.dimPercent = item.dimPercent;
            z7 = true;
        }
        Integer num9 = item.dimOffset;
        if (num9 != null && ((num4 = this.dimOffset) == null || !Intrinsics.areEqual(num4, num9))) {
            this.dimOffset = item.dimOffset;
            z7 = true;
        }
        Boolean bool5 = item.relay;
        if (bool5 != null && ((bool4 = this.relay) == null || !Intrinsics.areEqual(bool4, bool5))) {
            this.relay = item.relay;
            z7 = true;
        }
        Integer num10 = item.type;
        if (num10 != null && ((num3 = this.type) == null || !Intrinsics.areEqual(num3, num10))) {
            this.type = item.type;
            z7 = true;
        }
        Integer num11 = item.itemType;
        if (num11 != null && ((num2 = this.itemType) == null || !Intrinsics.areEqual(num2, num11))) {
            this.itemType = item.itemType;
            z7 = true;
        }
        String str8 = item.appToLaunchPackageName;
        if (str8 != null && ((str3 = this.appToLaunchPackageName) == null || !Intrinsics.areEqual(str3, str8))) {
            this.appToLaunchPackageName = item.appToLaunchPackageName;
            z7 = true;
        }
        String str9 = item.buttonType;
        if (str9 != null && ((str2 = this.buttonType) == null || !Intrinsics.areEqual(str2, str9))) {
            this.buttonType = item.buttonType;
            z7 = true;
            z12 = true;
        }
        Integer num12 = item.channelDipState;
        if (num12 != null && ((num = this.channelDipState) == null || !Intrinsics.areEqual(num, num12))) {
            this.channelDipState = item.channelDipState;
            z7 = true;
        }
        State state2 = item.groupState;
        if (state2 != null && ((state = this.groupState) == null || state != state2)) {
            this.groupState = state2;
            z7 = true;
        }
        String str10 = item.moduleType;
        if (str10 != null && ((str = this.moduleType) == null || !Intrinsics.areEqual(str, str10))) {
            this.moduleType = item.moduleType;
            z7 = true;
        }
        Boolean bool6 = item.batteryLow;
        if (bool6 != null && ((bool3 = this.batteryLow) == null || !Intrinsics.areEqual(bool3, bool6))) {
            this.batteryLow = item.batteryLow;
            z7 = true;
        }
        Boolean bool7 = item.RFLinkTimeout;
        if (bool7 != null && ((bool2 = this.RFLinkTimeout) == null || !Intrinsics.areEqual(bool2, bool7))) {
            this.RFLinkTimeout = item.RFLinkTimeout;
            z7 = true;
        }
        Boolean bool8 = item.isCalibrated;
        if (bool8 != null && ((bool = this.isCalibrated) == null || !Intrinsics.areEqual(bool, bool8))) {
            this.isCalibrated = item.isCalibrated;
            z7 = true;
        }
        if (item.aircon != null) {
            if (this.aircon == null) {
                this.aircon = new DataAirconSystem();
            }
            DataAirconSystem dataAirconSystem = this.aircon;
            Intrinsics.checkNotNull(dataAirconSystem);
            if (DataAirconSystem.update$default(dataAirconSystem, null, item.aircon, null, null, false, 16, null)) {
                z7 = true;
            }
        }
        if (item.sensor != null) {
            if (this.sensor == null) {
                this.sensor = new DataSensor();
            }
            DataSensor dataSensor = this.sensor;
            Intrinsics.checkNotNull(dataSensor);
            if (DataSensor.update$default(dataSensor, item.sensor, null, false, 4, null)) {
                z7 = true;
            }
        }
        Lock lock2 = item.itemAsLock;
        if (lock2 != null && ((lock = this.itemAsLock) == null || !Intrinsics.areEqual(lock, lock2))) {
            this.itemAsLock = item.itemAsLock;
        }
        if (item.monitor != null) {
            if (this.monitor == null) {
                this.monitor = new DataMonitor();
            }
            DataMonitor dataMonitor = this.monitor;
            Intrinsics.checkNotNull(dataMonitor);
            if (DataMonitor.update$default(dataMonitor, null, item.monitor, null, false, 8, null)) {
                z7 = true;
            }
        }
        if (item.sonos == null) {
            z11 = z7;
        } else {
            Sonos sonos = this.sonos;
            if (sonos != null) {
                Intrinsics.checkNotNull(sonos);
                Sonos sonos2 = item.sonos;
                Intrinsics.checkNotNull(sonos2);
                if (sonos.isDifferent(sonos2)) {
                }
            }
            Sonos sonos3 = item.sonos;
            Intrinsics.checkNotNull(sonos3);
            String udn = sonos3.getUdn();
            Sonos sonos4 = item.sonos;
            Intrinsics.checkNotNull(sonos4);
            String hostAddress = sonos4.getHostAddress();
            Sonos sonos5 = item.sonos;
            Intrinsics.checkNotNull(sonos5);
            String modelNumber = sonos5.getModelNumber();
            Sonos sonos6 = item.sonos;
            Intrinsics.checkNotNull(sonos6);
            String modelName = sonos6.getModelName();
            Sonos sonos7 = item.sonos;
            Intrinsics.checkNotNull(sonos7);
            String roomName = sonos7.getRoomName();
            Sonos sonos8 = item.sonos;
            Intrinsics.checkNotNull(sonos8);
            String displayName = sonos8.getDisplayName();
            Sonos sonos9 = item.sonos;
            Intrinsics.checkNotNull(sonos9);
            String friendlyName = sonos9.getFriendlyName();
            Sonos sonos10 = item.sonos;
            Intrinsics.checkNotNull(sonos10);
            this.sonos = new Sonos(udn, hostAddress, modelNumber, modelName, roomName, displayName, friendlyName, sonos10.getPlayInScene());
        }
        workOutType();
        if (z11 && context != null) {
            doUpdate(context);
        }
        if (z12 && context != null) {
            doGroupUpdate(context);
        }
        if (z10 && context != null) {
            doGroupNameUpdate(context);
        }
        return z11;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void workOutType() {
        int valueOf;
        int i10;
        Integer num = this.itemType;
        if (num != null) {
            if (num != null && num.intValue() == 1) {
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
                return;
            }
            Integer num2 = this.itemType;
            if (num2 != null && num2.intValue() == 2) {
                String str2 = this.id;
                Intrinsics.checkNotNull(str2);
                if (str2.length() < 7) {
                    valueOf = 7;
                } else {
                    Integer num3 = this.channelDipState;
                    int i11 = 8;
                    if (num3 == null || num3 == null || num3.intValue() != 1) {
                        Integer num4 = this.channelDipState;
                        if (num4 == null || num4 == null || num4.intValue() != 2) {
                            Integer num5 = this.channelDipState;
                            if (num5 == null || num5 == null || num5.intValue() != 10) {
                                Integer num6 = this.channelDipState;
                                if (num6 == null || num6 == null || num6.intValue() != 3) {
                                    Integer num7 = this.channelDipState;
                                    if (num7 == null || num7 == null || num7.intValue() != 9) {
                                        String str3 = this.buttonType;
                                        if (str3 == null || !Intrinsics.areEqual(str3, DataMyThing.BUTTON_TYPE_OPEN_CLOSE)) {
                                            String str4 = this.buttonType;
                                            if (str4 == null || !Intrinsics.areEqual(str4, DataMyThing.BUTTON_TYPE_UP_DOWN)) {
                                                i11 = 10;
                                            }
                                        } else {
                                            i11 = 11;
                                        }
                                        valueOf = Integer.valueOf(i11);
                                    } else {
                                        valueOf = 19;
                                    }
                                } else {
                                    valueOf = 8;
                                }
                            } else {
                                valueOf = 26;
                            }
                        } else {
                            valueOf = 9;
                        }
                    } else {
                        valueOf = 8;
                    }
                }
                this.type = valueOf;
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private Item() {
        this.enableInScene = Boolean.TRUE;
    }

    public Item(@Nullable Item item) {
        this.enableInScene = Boolean.TRUE;
        update(null, item);
    }

    public Item(@Nullable DataMyThing dataMyThing) {
        this.enableInScene = Boolean.TRUE;
        Intrinsics.checkNotNull(dataMyThing);
        this.id = dataMyThing.id;
        this.name = dataMyThing.name;
        this.itemValue = dataMyThing.value;
        this.dimPercent = dataMyThing.dimPercent;
        this.dimOffset = dataMyThing.dimOffset;
        this.buttonType = dataMyThing.buttonType;
        this.channelDipState = dataMyThing.channelDipState;
        this.enableInScene = dataMyThing.getEnableInScene();
        this.itemType = 2;
        this.batteryLow = dataMyThing.batteryLow;
        this.RFLinkTimeout = dataMyThing.RFLinkTimeout;
        this.isCalibrated = dataMyThing.isCalibrated;
        workOutType();
    }

    public Item(@NotNull DataGroupThing dataGroupThingSource) {
        Intrinsics.checkNotNullParameter(dataGroupThingSource, "dataGroupThingSource");
        this.enableInScene = Boolean.TRUE;
        this.id = dataGroupThingSource.id;
        this.name = dataGroupThingSource.name;
        this.groupState = dataGroupThingSource.state;
        String str = dataGroupThingSource.buttonType;
        if (str == null) {
            this.buttonType = "none";
        } else {
            this.buttonType = str;
        }
        this.itemType = 2;
        workOutType();
    }

    public Item(@Nullable DataLight dataLight) {
        this.enableInScene = Boolean.TRUE;
        Intrinsics.checkNotNull(dataLight);
        this.id = dataLight.id;
        this.name = dataLight.name;
        this.itemValue = dataLight.value;
        this.dimOffset = dataLight.dimOffset;
        this.state = dataLight.state;
        this.relay = dataLight.relay;
        this.moduleType = dataLight.moduleType;
        this.enableInScene = dataLight.enableInScene;
        this.itemType = 1;
        workOutType();
    }

    public Item(@Nullable String str, @Nullable DataAirconSystem dataAirconSystem) {
        this.enableInScene = Boolean.TRUE;
        this.id = str;
        DataAirconSystem dataAirconSystem2 = new DataAirconSystem();
        this.aircon = dataAirconSystem2;
        Intrinsics.checkNotNull(dataAirconSystem2);
        DataAirconSystem.update$default(dataAirconSystem2, null, dataAirconSystem, null, null, false, 16, null);
        this.itemType = 3;
        this.type = 18;
    }

    public Item(@Nullable String str, @Nullable DataSensor dataSensor) {
        this.enableInScene = Boolean.TRUE;
        this.id = str;
        DataSensor dataSensor2 = new DataSensor();
        this.sensor = dataSensor2;
        Intrinsics.checkNotNull(dataSensor2);
        DataSensor.update$default(dataSensor2, dataSensor, null, false, 4, null);
        this.itemType = 4;
        this.type = 21;
    }

    public Item(@Nullable String str, @Nullable DataMonitor dataMonitor) {
        this.enableInScene = Boolean.TRUE;
        this.id = str;
        DataMonitor dataMonitor2 = new DataMonitor();
        this.monitor = dataMonitor2;
        Intrinsics.checkNotNull(dataMonitor2);
        DataMonitor.update$default(dataMonitor2, null, dataMonitor, null, false, 8, null);
        this.itemType = 5;
        this.type = 22;
    }

    public Item(@Nullable String str, @NotNull Sonos sonosDeviceSource) {
        Intrinsics.checkNotNullParameter(sonosDeviceSource, "sonosDeviceSource");
        this.enableInScene = Boolean.TRUE;
        this.id = str;
        Intrinsics.checkNotNull(str);
        this.sonos = new Sonos(str, sonosDeviceSource.getHostAddress(), sonosDeviceSource.getModelNumber(), sonosDeviceSource.getModelName(), sonosDeviceSource.getRoomName(), sonosDeviceSource.getDisplayName(), sonosDeviceSource.getFriendlyName(), sonosDeviceSource.getPlayInScene());
        this.itemType = 7;
        this.type = 24;
    }
}