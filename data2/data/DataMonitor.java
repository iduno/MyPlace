package com.air.advantage.data;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.air.advantage.AppFeatures;
import com.air.advantage.libraryairconlightjson.UartConstants;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.appindexing.builders.AlarmBuilder;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.data.x */
/* loaded from: classes.dex */
public final class DataMonitor {
    public static final int MAXIMUM_START_AND_END_TIME_VALUE = 1440;
    private static final int NUMBER_OF_SENSOR_PER_LINE_IN_SUMMARY = 3;

    @Nullable
    @SerializedName("activeDays")
    @JvmField
    public Integer activeDays;

    @Nullable
    @SerializedName("endTime")
    @JvmField
    public Integer endTime;

    @Nullable
    @SerializedName("id")
    @JvmField
    public String id;

    @Nullable
    @SerializedName("monitorEnabled")
    @JvmField
    public Boolean monitorEnabled;

    @Nullable
    @SerializedName("monitorSummary")
    @JvmField
    public String monitorSummary;

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    @JvmField
    public String name;

    @Nullable
    @SerializedName("startTime")
    @JvmField
    public Integer startTime;

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = DataMonitor.class.getSimpleName();

    @NotNull
    private static final String[] DAYS_STRING = {AlarmBuilder.MONDAY, AlarmBuilder.TUESDAY, AlarmBuilder.WEDNESDAY, AlarmBuilder.THURSDAY, AlarmBuilder.FRIDAY, AlarmBuilder.SATURDAY, AlarmBuilder.SUNDAY};

    @SerializedName("actions")
    @NotNull
    @JvmField
    public final DataMonitorActions actions = new DataMonitorActions();

    @SerializedName("events")
    @NotNull
    @JvmField
    public final Events events = new Events();

    /* renamed from: com.air.advantage.data.x$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.x.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    public static /* synthetic */ boolean update$default(DataMonitor dataMonitor, Context context, DataMonitor dataMonitor2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 8) != 0) {
            z7 = false;
        }
        return dataMonitor.update(context, dataMonitor2, dataManager, z7);
    }

    public final void clear() {
        this.actions.clear();
        this.activeDays = null;
        this.name = null;
        this.id = null;
        this.monitorSummary = null;
        this.events.clear();
        this.endTime = null;
        this.startTime = null;
        this.monitorEnabled = null;
    }

    public final void clearDataForBackup() {
        this.monitorSummary = "";
        this.actions.autoActionSummary = "";
    }

    /* JADX WARN: Removed duplicated region for block: B:122:0x02a5  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0330  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0364  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x039b  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x03aa  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0101  */
    // TODO: Fixme
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void generateSummary(@NotNull DataMaster masterData) {
        String str;
        Boolean bool;
        DataSensor sensor;
        Intrinsics.checkNotNullParameter(masterData, "masterData");
        StringBuilder sb = new StringBuilder();
        Integer num = this.activeDays;
        if (num == null || (num != null && num.intValue() == 0)) {
            sb.append("No active days have been configured on this event.");
        } else {
            sb.append("This event is active between:\n");
            AppFeatures appFeatures = AppFeatures.instance;
            sb.append(appFeatures.h(this.startTime));
            sb.append(" and ");
            sb.append(appFeatures.h(this.endTime));
            Integer num2 = this.endTime;
            Intrinsics.checkNotNull(num2);
            int intValue = num2.intValue();
            Integer num3 = this.startTime;
            Intrinsics.checkNotNull(num3);
            if (intValue <= num3.intValue()) {
                sb.append("(next day)");
            }
            StringBuilder sb2 = new StringBuilder();
            int i10 = 0;
            int i11 = 0;
            boolean z7 = true;
            while (i10 < 7) {
                Integer num4 = this.activeDays;
                Intrinsics.checkNotNull(num4);
                int i12 = i10 + 1;
                if ((num4.intValue() & (1 << (i12 % 7))) != 0) {
                    i11++;
                    if (!z7) {
                        sb2.append(", ");
                    }
                    sb2.append(DAYS_STRING[i10]);
                    z7 = false;
                }
                i10 = i12;
            }
            if (i11 == 1) {
                sb.append("\non ");
                sb.append(sb2.toString());
            } else {
                sb.append("\non these days:\n");
                sb.append(sb2.toString());
            }
        }
        Boolean bool2 = this.events.suburbTempEnabled;
        if (bool2 != null) {
            Intrinsics.checkNotNull(bool2);
            if (!bool2.booleanValue()) {
                Boolean bool3 = this.events.zoneTempEnabled;
                if (bool3 != null) {
                    Intrinsics.checkNotNull(bool3);
                    if (!bool3.booleanValue()) {
                        Boolean bool4 = this.events.hueTempEnabled;
                        if (bool4 != null) {
                            Intrinsics.checkNotNull(bool4);
                            if (!bool4.booleanValue()) {
                                Boolean bool5 = this.events.weatherConditionForPvEnabled;
                                if (bool5 != null) {
                                    Intrinsics.checkNotNull(bool5);
                                    if (!bool5.booleanValue()) {
                                        Boolean bool6 = this.events.motionSensorEnabled;
                                        if (bool6 != null) {
                                            Intrinsics.checkNotNull(bool6);
                                            if (!bool6.booleanValue()) {
                                                Boolean bool7 = this.events.garageDoorEnabled;
                                                if (bool7 != null) {
                                                    Intrinsics.checkNotNull(bool7);
                                                    if (bool7.booleanValue()) {
                                                        sb.append("\n\nconditions:");
                                                        Boolean bool8 = this.events.motionSensorEnabled;
                                                        if (bool8 != null) {
                                                            Intrinsics.checkNotNull(bool8);
                                                            if (bool8.booleanValue()) {
                                                                sb.append("\n- motion sensors - ");
                                                                String str2 = this.events.motionSensorTrigger;
                                                                if (str2 != null) {
                                                                    if (Intrinsics.areEqual(str2, Events.MOTION_SENSOR_TRIGGER_ON_MOTION)) {
                                                                        sb.append("on motion");
                                                                    } else {
                                                                        sb.append("no motion detected for ");
                                                                        Integer num5 = this.events.motionSensorTriggerDelayMinutes;
                                                                        if (num5 != null) {
                                                                            Intrinsics.checkNotNull(num5);
                                                                            if (num5.intValue() < 60) {
                                                                                sb.append(this.events.motionSensorTriggerDelayMinutes + " mins");
                                                                            } else {
                                                                                Integer num6 = this.events.motionSensorTriggerDelayMinutes;
                                                                                Intrinsics.checkNotNull(num6);
                                                                                if (num6.intValue() < 120) {
                                                                                    sb.append("1 hour");
                                                                                } else {
                                                                                    Integer num7 = this.events.motionSensorTriggerDelayMinutes;
                                                                                    Intrinsics.checkNotNull(num7);
                                                                                    sb.append((num7.intValue() / 60) + " hours");
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                if (this.events.motionSensorsIdList != null) {
                                                                    sb.append("\n  sensors:");
                                                                    ArrayList<String> arrayList = masterData.mySensors.sensorsOrder;
                                                                    Intrinsics.checkNotNull(arrayList);
                                                                    Iterator<String> it = arrayList.iterator();
                                                                    int i13 = 0;
                                                                    boolean z10 = true;
                                                                    boolean z11 = true;
                                                                    while (it.hasNext()) {
                                                                        String next = it.next();
                                                                        ArrayList<String> arrayList2 = this.events.motionSensorsIdList;
                                                                        Intrinsics.checkNotNull(arrayList2);
                                                                        if (arrayList2.contains(next) && (sensor = masterData.mySensors.getSensor(next)) != null && sensor.name != null) {
                                                                            if (z10) {
                                                                                if (z11) {
                                                                                    z11 = false;
                                                                                } else {
                                                                                    sb.append(",");
                                                                                }
                                                                                sb.append("\n    ");
                                                                                z10 = false;
                                                                            } else {
                                                                                sb.append(", ");
                                                                            }
                                                                            sb.append(sensor.name);
                                                                            i13++;
                                                                            if (i13 >= 3) {
                                                                                i13 = 0;
                                                                                z10 = true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                Boolean bool9 = this.events.motionSensorLightLevelEnabled;
                                                                if (bool9 != null) {
                                                                    Intrinsics.checkNotNull(bool9);
                                                                    if (bool9.booleanValue()) {
                                                                        sb.append("\n  when sensor light level is: ");
                                                                        sb.append(this.events.motionSensorLightLevel);
                                                                        String str3 = this.events.motionSensorLightLevel;
                                                                        if (str3 != null && ((Intrinsics.areEqual(str3, DataSensor.LIGHT_LEVEL_STRING_DIM) || Intrinsics.areEqual(this.events.motionSensorLightLevel, DataSensor.LIGHT_LEVEL_STRING_COSY) || Intrinsics.areEqual(this.events.motionSensorLightLevel, DataSensor.LIGHT_LEVEL_STRING_NORMAL)) && (bool = this.events.motionSensorLightLevelEqualOrBelowSelected) != null)) {
                                                                            Intrinsics.checkNotNull(bool);
                                                                            if (bool.booleanValue()) {
                                                                                sb.append(" or below");
                                                                            } else {
                                                                                sb.append(" or above");
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        Boolean bool10 = this.events.garageDoorEnabled;
                                                        if (bool10 != null) {
                                                            Intrinsics.checkNotNull(bool10);
                                                            if (bool10.booleanValue()) {
                                                                sb.append("\n- any garage door is ");
                                                                String str4 = this.events.garageDoorTrigger;
                                                                if (str4 != null) {
                                                                    if (Intrinsics.areEqual(str4, Events.GARAGE_DOOR_TRIGGER_ON_OPEN)) {
                                                                        sb.append("opened");
                                                                    } else {
                                                                        sb.append("closed");
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        Boolean bool11 = this.events.suburbTempEnabled;
                                                        if (bool11 != null) {
                                                            Intrinsics.checkNotNull(bool11);
                                                            if (bool11.booleanValue()) {
                                                                sb.append("\n- suburb temp is ");
                                                                Boolean bool12 = this.events.suburbTempBelowThresholdSelected;
                                                                if (bool12 != null) {
                                                                    Intrinsics.checkNotNull(bool12);
                                                                    if (bool12.booleanValue()) {
                                                                        sb.append("below ");
                                                                    } else {
                                                                        sb.append("above ");
                                                                    }
                                                                    Events events = this.events;
                                                                    Integer num8 = events.suburbTempThresholdValue;
                                                                    if (num8 != null) {
                                                                        sb.append(num8);
                                                                    } else {
                                                                        Integer num9 = events.suburbTempAboveValue;
                                                                        if (num9 != null) {
                                                                            sb.append(num9);
                                                                        } else {
                                                                            sb.append("-");
                                                                        }
                                                                    }
                                                                    sb.append("°C");
                                                                }
                                                            }
                                                        }
                                                        Boolean bool13 = this.events.zoneTempEnabled;
                                                        if (bool13 != null) {
                                                            Intrinsics.checkNotNull(bool13);
                                                            if (bool13.booleanValue()) {
                                                                Integer num10 = masterData.system.noOfAircons;
                                                                Intrinsics.checkNotNull(num10);
                                                                if (num10.intValue() > 1) {
                                                                    String str5 = this.events.zoneTempAirconId;
                                                                    if (str5 != null) {
                                                                        DataAirconSystem dataAirconSystem = masterData.aircons.get(str5);
                                                                        if (dataAirconSystem == null || (str = dataAirconSystem.info.name) == null) {
                                                                            sb.append("\n- any zone temp in  is ");
                                                                        } else {
                                                                            sb.append("\n- any zone temp in " + str + " is ");
                                                                        }
                                                                    } else {
                                                                        sb.append("\n- any zone temp is ");
                                                                    }
                                                                } else {
                                                                    sb.append("\n- any zone temp is ");
                                                                }
                                                                Boolean bool14 = this.events.zoneTempBelowThresholdSelected;
                                                                if (bool14 != null) {
                                                                    Intrinsics.checkNotNull(bool14);
                                                                    if (bool14.booleanValue()) {
                                                                        sb.append("below ");
                                                                    } else {
                                                                        sb.append("above ");
                                                                    }
                                                                    sb.append(this.events.zoneTempThresholdValue);
                                                                    sb.append("°C");
                                                                }
                                                            }
                                                        }
                                                        Boolean bool15 = this.events.hueTempEnabled;
                                                        if (bool15 != null) {
                                                            Intrinsics.checkNotNull(bool15);
                                                            if (bool15.booleanValue()) {
                                                                sb.append("\n- any hue sensor temp is ");
                                                                Boolean bool16 = this.events.hueTempBelowThresholdSelected;
                                                                if (bool16 != null) {
                                                                    Intrinsics.checkNotNull(bool16);
                                                                    if (bool16.booleanValue()) {
                                                                        sb.append("below ");
                                                                    } else {
                                                                        sb.append("above ");
                                                                    }
                                                                    sb.append(this.events.hueTempThresholdValue);
                                                                    sb.append("°C");
                                                                }
                                                            }
                                                        }
                                                        Boolean bool17 = this.events.weatherConditionForPvEnabled;
                                                        if (bool17 != null) {
                                                            Intrinsics.checkNotNull(bool17);
                                                            if (bool17.booleanValue()) {
                                                                sb.append("\n- clear and sunny weather");
                                                            }
                                                        }
                                                    }
                                                }
                                                sb.append("\n\nconditions:");
                                                sb.append("\n- none selected");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Boolean bool18 = this.actions.notificationEnabled;
        if (bool18 != null) {
            Intrinsics.checkNotNull(bool18);
            if (!bool18.booleanValue()) {
                Boolean bool19 = this.actions.autoActionEnabled;
                if (bool19 != null) {
                    Intrinsics.checkNotNull(bool19);
                    if (!bool19.booleanValue()) {
                        Boolean bool20 = this.actions.launchMyAppEnabled;
                        if (bool20 != null) {
                            Intrinsics.checkNotNull(bool20);
                            if (bool20.booleanValue()) {
                                sb.append("\n\ntasks:");
                                Boolean bool21 = this.actions.notificationEnabled;
                                if (bool21 != null) {
                                    Intrinsics.checkNotNull(bool21);
                                    if (bool21.booleanValue()) {
                                        sb.append("\n- send notification");
                                        Boolean bool22 = this.actions.notificationPhoneNumberEnabled;
                                        if (bool22 != null) {
                                            Intrinsics.checkNotNull(bool22);
                                            if (bool22.booleanValue() && this.actions.notificationPhoneNumber != null) {
                                                sb.append("\n  phone #: ");
                                                sb.append(this.actions.notificationPhoneNumber);
                                            }
                                        }
                                    }
                                }
                                Boolean bool23 = this.actions.autoActionEnabled;
                                if (bool23 != null) {
                                    Intrinsics.checkNotNull(bool23);
                                    if (bool23.booleanValue()) {
                                        sb.append("\n- auto action");
                                    }
                                }
                                Boolean bool24 = this.actions.launchMyAppEnabled;
                                if (bool24 != null) {
                                    Intrinsics.checkNotNull(bool24);
                                    if (bool24.booleanValue()) {
                                        sb.append("\n- launch app: ");
                                        sb.append(this.actions.launchMyAppName);
                                    }
                                }
                            }
                        }
                        sb.append("\n\nactions:");
                        sb.append("\n- none selected");
                    }
                }
            }
        }
        sb.append("\n\n");
        this.monitorSummary = sb.toString();
        this.actions.generateSummary(masterData);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@Nullable Context context, @Nullable DataMonitor dataMonitor, @Nullable DataManager dataManager) {
        return update$default(this, context, dataMonitor, dataManager, false, 8, null);
    }

    public final void updateMonitorDataForScene(@NotNull DataMonitor sourceDataMonitor) {
        Intrinsics.checkNotNullParameter(sourceDataMonitor, "sourceDataMonitor");
        this.id = sourceDataMonitor.id;
        this.monitorEnabled = sourceDataMonitor.monitorEnabled;
        this.name = sourceDataMonitor.name;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    /* JADX WARN: Removed duplicated region for block: B:104:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003b A[Catch: all -> 0x0195, TryCatch #0 {, blocks: (B:128:0x0003, B:130:0x000a, B:3:0x000d, B:5:0x001a, B:7:0x001e, B:8:0x0021, B:12:0x002f, B:14:0x0033, B:15:0x0036, B:17:0x003b, B:19:0x003f, B:21:0x0061, B:23:0x0065, B:25:0x0069, B:27:0x008b, B:29:0x008f, B:31:0x0093, B:33:0x00ac, B:35:0x00b0, B:37:0x00b4, B:39:0x00d6, B:41:0x00da, B:43:0x00de, B:45:0x0100, B:47:0x0104, B:49:0x0108, B:51:0x012a, B:53:0x012e, B:55:0x0132, B:60:0x0159, B:67:0x0138, B:69:0x013e, B:71:0x0148, B:74:0x014e, B:75:0x010e, B:77:0x0114, B:80:0x011e, B:83:0x0124, B:84:0x00e4, B:86:0x00ea, B:89:0x00f4, B:92:0x00fa, B:93:0x00ba, B:95:0x00c0, B:98:0x00ca, B:101:0x00d0, B:102:0x0099, B:105:0x00a0, B:108:0x00a6, B:109:0x006f, B:111:0x0075, B:114:0x007f, B:117:0x0085, B:118:0x0045, B:120:0x004b, B:123:0x0055, B:126:0x005b), top: B:127:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0065 A[Catch: all -> 0x0195, TryCatch #0 {, blocks: (B:128:0x0003, B:130:0x000a, B:3:0x000d, B:5:0x001a, B:7:0x001e, B:8:0x0021, B:12:0x002f, B:14:0x0033, B:15:0x0036, B:17:0x003b, B:19:0x003f, B:21:0x0061, B:23:0x0065, B:25:0x0069, B:27:0x008b, B:29:0x008f, B:31:0x0093, B:33:0x00ac, B:35:0x00b0, B:37:0x00b4, B:39:0x00d6, B:41:0x00da, B:43:0x00de, B:45:0x0100, B:47:0x0104, B:49:0x0108, B:51:0x012a, B:53:0x012e, B:55:0x0132, B:60:0x0159, B:67:0x0138, B:69:0x013e, B:71:0x0148, B:74:0x014e, B:75:0x010e, B:77:0x0114, B:80:0x011e, B:83:0x0124, B:84:0x00e4, B:86:0x00ea, B:89:0x00f4, B:92:0x00fa, B:93:0x00ba, B:95:0x00c0, B:98:0x00ca, B:101:0x00d0, B:102:0x0099, B:105:0x00a0, B:108:0x00a6, B:109:0x006f, B:111:0x0075, B:114:0x007f, B:117:0x0085, B:118:0x0045, B:120:0x004b, B:123:0x0055, B:126:0x005b), top: B:127:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x008f A[Catch: all -> 0x0195, TryCatch #0 {, blocks: (B:128:0x0003, B:130:0x000a, B:3:0x000d, B:5:0x001a, B:7:0x001e, B:8:0x0021, B:12:0x002f, B:14:0x0033, B:15:0x0036, B:17:0x003b, B:19:0x003f, B:21:0x0061, B:23:0x0065, B:25:0x0069, B:27:0x008b, B:29:0x008f, B:31:0x0093, B:33:0x00ac, B:35:0x00b0, B:37:0x00b4, B:39:0x00d6, B:41:0x00da, B:43:0x00de, B:45:0x0100, B:47:0x0104, B:49:0x0108, B:51:0x012a, B:53:0x012e, B:55:0x0132, B:60:0x0159, B:67:0x0138, B:69:0x013e, B:71:0x0148, B:74:0x014e, B:75:0x010e, B:77:0x0114, B:80:0x011e, B:83:0x0124, B:84:0x00e4, B:86:0x00ea, B:89:0x00f4, B:92:0x00fa, B:93:0x00ba, B:95:0x00c0, B:98:0x00ca, B:101:0x00d0, B:102:0x0099, B:105:0x00a0, B:108:0x00a6, B:109:0x006f, B:111:0x0075, B:114:0x007f, B:117:0x0085, B:118:0x0045, B:120:0x004b, B:123:0x0055, B:126:0x005b), top: B:127:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00b0 A[Catch: all -> 0x0195, TryCatch #0 {, blocks: (B:128:0x0003, B:130:0x000a, B:3:0x000d, B:5:0x001a, B:7:0x001e, B:8:0x0021, B:12:0x002f, B:14:0x0033, B:15:0x0036, B:17:0x003b, B:19:0x003f, B:21:0x0061, B:23:0x0065, B:25:0x0069, B:27:0x008b, B:29:0x008f, B:31:0x0093, B:33:0x00ac, B:35:0x00b0, B:37:0x00b4, B:39:0x00d6, B:41:0x00da, B:43:0x00de, B:45:0x0100, B:47:0x0104, B:49:0x0108, B:51:0x012a, B:53:0x012e, B:55:0x0132, B:60:0x0159, B:67:0x0138, B:69:0x013e, B:71:0x0148, B:74:0x014e, B:75:0x010e, B:77:0x0114, B:80:0x011e, B:83:0x0124, B:84:0x00e4, B:86:0x00ea, B:89:0x00f4, B:92:0x00fa, B:93:0x00ba, B:95:0x00c0, B:98:0x00ca, B:101:0x00d0, B:102:0x0099, B:105:0x00a0, B:108:0x00a6, B:109:0x006f, B:111:0x0075, B:114:0x007f, B:117:0x0085, B:118:0x0045, B:120:0x004b, B:123:0x0055, B:126:0x005b), top: B:127:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00da A[Catch: all -> 0x0195, TryCatch #0 {, blocks: (B:128:0x0003, B:130:0x000a, B:3:0x000d, B:5:0x001a, B:7:0x001e, B:8:0x0021, B:12:0x002f, B:14:0x0033, B:15:0x0036, B:17:0x003b, B:19:0x003f, B:21:0x0061, B:23:0x0065, B:25:0x0069, B:27:0x008b, B:29:0x008f, B:31:0x0093, B:33:0x00ac, B:35:0x00b0, B:37:0x00b4, B:39:0x00d6, B:41:0x00da, B:43:0x00de, B:45:0x0100, B:47:0x0104, B:49:0x0108, B:51:0x012a, B:53:0x012e, B:55:0x0132, B:60:0x0159, B:67:0x0138, B:69:0x013e, B:71:0x0148, B:74:0x014e, B:75:0x010e, B:77:0x0114, B:80:0x011e, B:83:0x0124, B:84:0x00e4, B:86:0x00ea, B:89:0x00f4, B:92:0x00fa, B:93:0x00ba, B:95:0x00c0, B:98:0x00ca, B:101:0x00d0, B:102:0x0099, B:105:0x00a0, B:108:0x00a6, B:109:0x006f, B:111:0x0075, B:114:0x007f, B:117:0x0085, B:118:0x0045, B:120:0x004b, B:123:0x0055, B:126:0x005b), top: B:127:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0104 A[Catch: all -> 0x0195, TryCatch #0 {, blocks: (B:128:0x0003, B:130:0x000a, B:3:0x000d, B:5:0x001a, B:7:0x001e, B:8:0x0021, B:12:0x002f, B:14:0x0033, B:15:0x0036, B:17:0x003b, B:19:0x003f, B:21:0x0061, B:23:0x0065, B:25:0x0069, B:27:0x008b, B:29:0x008f, B:31:0x0093, B:33:0x00ac, B:35:0x00b0, B:37:0x00b4, B:39:0x00d6, B:41:0x00da, B:43:0x00de, B:45:0x0100, B:47:0x0104, B:49:0x0108, B:51:0x012a, B:53:0x012e, B:55:0x0132, B:60:0x0159, B:67:0x0138, B:69:0x013e, B:71:0x0148, B:74:0x014e, B:75:0x010e, B:77:0x0114, B:80:0x011e, B:83:0x0124, B:84:0x00e4, B:86:0x00ea, B:89:0x00f4, B:92:0x00fa, B:93:0x00ba, B:95:0x00c0, B:98:0x00ca, B:101:0x00d0, B:102:0x0099, B:105:0x00a0, B:108:0x00a6, B:109:0x006f, B:111:0x0075, B:114:0x007f, B:117:0x0085, B:118:0x0045, B:120:0x004b, B:123:0x0055, B:126:0x005b), top: B:127:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x012e A[Catch: all -> 0x0195, TryCatch #0 {, blocks: (B:128:0x0003, B:130:0x000a, B:3:0x000d, B:5:0x001a, B:7:0x001e, B:8:0x0021, B:12:0x002f, B:14:0x0033, B:15:0x0036, B:17:0x003b, B:19:0x003f, B:21:0x0061, B:23:0x0065, B:25:0x0069, B:27:0x008b, B:29:0x008f, B:31:0x0093, B:33:0x00ac, B:35:0x00b0, B:37:0x00b4, B:39:0x00d6, B:41:0x00da, B:43:0x00de, B:45:0x0100, B:47:0x0104, B:49:0x0108, B:51:0x012a, B:53:0x012e, B:55:0x0132, B:60:0x0159, B:67:0x0138, B:69:0x013e, B:71:0x0148, B:74:0x014e, B:75:0x010e, B:77:0x0114, B:80:0x011e, B:83:0x0124, B:84:0x00e4, B:86:0x00ea, B:89:0x00f4, B:92:0x00fa, B:93:0x00ba, B:95:0x00c0, B:98:0x00ca, B:101:0x00d0, B:102:0x0099, B:105:0x00a0, B:108:0x00a6, B:109:0x006f, B:111:0x0075, B:114:0x007f, B:117:0x0085, B:118:0x0045, B:120:0x004b, B:123:0x0055, B:126:0x005b), top: B:127:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x00c8  */
    @JvmOverloads
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean update(@Nullable Context context, @Nullable DataMonitor dataMonitor, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        Integer num;
        Integer num2;
        String str;
        String str2;
        Boolean bool;
        String str3;
        Integer num3;
        String str4;
        String str5;
        synchronized (this) {
            if (dataManager != null) {
                Intrinsics.checkNotNull(dataMonitor);
                String str6 = dataMonitor.id;
                if (str6 != null) {
                    dataManager.updateMonitorActionsPath(str6);
                }
                DataMonitorActions dataMonitorActions = this.actions;
                Intrinsics.checkNotNull(dataMonitor);
                boolean update = dataMonitorActions.update(dataMonitor.actions, dataManager, z7);
                if (dataManager != null && (str5 = dataMonitor.id) != null) {
                    dataManager.updateMonitorEventsPath(str5);
                }
                z10 = true;
                if (this.events.update(dataMonitor.events, dataManager, z7)) {
                    update = true;
                }
                if (dataManager != null && (str4 = dataMonitor.id) != null) {
                    dataManager.updateMonitorPath(str4);
                }
                num = dataMonitor.activeDays;
                if (num == null) {
                    Integer num4 = this.activeDays;
                    if (num4 == null || !Intrinsics.areEqual(num4, num)) {
                        this.activeDays = dataMonitor.activeDays;
                        if (dataManager != null) {
                            dataManager.add("activeDays", dataMonitor.activeDays);
                        }
                        update = true;
                    }
                    num2 = dataMonitor.endTime;
                    if (num2 == null) {
                        Integer num5 = this.endTime;
                        if (num5 == null || !Intrinsics.areEqual(num5, num2)) {
                            this.endTime = dataMonitor.endTime;
                            if (dataManager != null) {
                                dataManager.add("endTime", dataMonitor.endTime);
                            }
                            update = true;
                        }
                        str = dataMonitor.id;
                        if (str == null) {
                            String str7 = this.id;
                            if (str7 == null || !Intrinsics.areEqual(str7, str)) {
                                this.id = dataMonitor.id;
                                update = true;
                            }
                            str2 = dataMonitor.name;
                            if (str2 == null) {
                                String str8 = this.name;
                                if (str8 == null || !Intrinsics.areEqual(str8, str2)) {
                                    this.name = dataMonitor.name;
                                    if (dataManager != null) {
                                        dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, dataMonitor.name);
                                    }
                                    update = true;
                                }
                                bool = dataMonitor.monitorEnabled;
                                if (bool == null) {
                                    Boolean bool2 = this.monitorEnabled;
                                    if (bool2 == null || !Intrinsics.areEqual(bool2, bool)) {
                                        this.monitorEnabled = dataMonitor.monitorEnabled;
                                        if (dataManager != null) {
                                            dataManager.add("monitorEnabled", dataMonitor.monitorEnabled);
                                        }
                                        update = true;
                                    }
                                    str3 = dataMonitor.monitorSummary;
                                    if (str3 == null) {
                                        String str9 = this.monitorSummary;
                                        if (str9 == null || !Intrinsics.areEqual(str9, str3)) {
                                            this.monitorSummary = dataMonitor.monitorSummary;
                                            if (dataManager != null) {
                                                dataManager.add("monitorSummary", dataMonitor.monitorSummary);
                                            }
                                            update = true;
                                        }
                                        num3 = dataMonitor.startTime;
                                        if (num3 == null) {
                                            Integer num6 = this.startTime;
                                            if (num6 == null || !Intrinsics.areEqual(num6, num3)) {
                                                this.startTime = dataMonitor.startTime;
                                                if (dataManager != null) {
                                                    dataManager.add("startTime", dataMonitor.startTime);
                                                }
                                            } else {
                                                z10 = update;
                                            }
                                            if (z10 && context != null) {
                                                Timber.forest.d("Monitor data has been update for id: " + this.id + " - " + this.name, new Object[0]);
                                                Intent intent = new Intent(UartConstants.MONITOR_UPDATE);
                                                intent.putExtra("monitorId", this.id);
                                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                            }
                                        } else {
                                            if (z7 && this.startTime != null) {
                                                if (dataManager != null) {
                                                    dataManager.add("startTime", null);
                                                }
                                            }
                                            if (z10) {
                                                Timber.forest.d("Monitor data has been update for id: " + this.id + " - " + this.name, new Object[0]);
                                                Intent intent2 = new Intent(UartConstants.MONITOR_UPDATE);
                                                intent2.putExtra("monitorId", this.id);
                                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);
                                            }
                                        }
                                    } else {
                                        if (z7 && this.monitorSummary != null) {
                                            if (dataManager != null) {
                                                dataManager.add("monitorSummary", null);
                                            }
                                            update = true;
                                        }
                                        num3 = dataMonitor.startTime;
                                        if (num3 == null) {
                                        }
                                    }
                                } else {
                                    if (z7 && this.monitorEnabled != null) {
                                        if (dataManager != null) {
                                            dataManager.add("monitorEnabled", null);
                                        }
                                        update = true;
                                    }
                                    str3 = dataMonitor.monitorSummary;
                                    if (str3 == null) {
                                    }
                                }
                            } else {
                                if (z7 && this.name != null) {
                                    if (dataManager != null) {
                                        dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, null);
                                    }
                                    update = true;
                                }
                                bool = dataMonitor.monitorEnabled;
                                if (bool == null) {
                                }
                            }
                        } else {
                            if (z7 && this.id != null) {
                                if (dataManager != null) {
                                    dataManager.add("id", null);
                                }
                                update = true;
                            }
                            str2 = dataMonitor.name;
                            if (str2 == null) {
                            }
                        }
                    } else {
                        if (z7 && this.endTime != null) {
                            if (dataManager != null) {
                                dataManager.add("endTime", null);
                            }
                            update = true;
                        }
                        str = dataMonitor.id;
                        if (str == null) {
                        }
                    }
                } else {
                    if (z7 && this.activeDays != null) {
                        if (dataManager != null) {
                            dataManager.add("activeDays", null);
                        }
                        update = true;
                    }
                    num2 = dataMonitor.endTime;
                    if (num2 == null) {
                    }
                }
            } else {
                DataMonitorActions dataMonitorActions2 = this.actions;
                Intrinsics.checkNotNull(dataMonitor);
                boolean update2 = dataMonitorActions2.update(dataMonitor.actions, dataManager, z7);
                if (dataManager != null) {
                    dataManager.updateMonitorEventsPath(str5);
                }
                z10 = true;
                if (this.events.update(dataMonitor.events, dataManager, z7)) {
                }
                if (dataManager != null) {
                    dataManager.updateMonitorPath(str4);
                }
                num = dataMonitor.activeDays;
                if (num == null) {
                }
            }
        }
        return z10;
    }
}