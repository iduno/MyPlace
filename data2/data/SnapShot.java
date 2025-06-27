package com.air.advantage.data;

import com.air.advantage.libraryairconlightjson.AirconMode;
import com.air.advantage.libraryairconlightjson.FanStatus;
import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.air.advantage.libraryairconlightjson.SystemState;
import com.air.advantage.libraryairconlightjson.ZoneState;
import com.bosma.api.lab.net.UrlConfig;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.o1 */
/* loaded from: classes.dex */
public final class SnapShot {

    @NotNull
    public static final a Companion = new a(null);

    @JvmField
    public static int MAX_NO_SNAPSHOTS = 10;

    @JsonExporter(export = false)
    @Expose(serialize = false)
    @Nullable
    @SerializedName("CANmsgs")
    @JvmField
    public ArrayList CANmsgs;

    @Nullable
    @SerializedName("activeDays")
    @JvmField
    public Integer activeDays;

    @Nullable
    @SerializedName("aircons")
    @JvmField
    public TreeMap aircons = new TreeMap();

    @Nullable
    @SerializedName("delete")
    @JvmField
    public Boolean delete;

    @Nullable
    @SerializedName("enabled")
    @JvmField
    public Boolean enabled;

    @Nullable
    @SerializedName(AppMeasurementSdk.ConditionalUserProperty.NAME)
    @JvmField
    public String name;

    @Nullable
    @SerializedName("runNow")
    @JvmField
    public Boolean runNow;

    @Nullable
    @SerializedName("snapshotId")
    @JvmField
    public String snapshotId;

    @Nullable
    @SerializedName("startTime")
    @JvmField
    public Integer startTime;

    @Nullable
    @SerializedName("stopTime")
    @JvmField
    public Integer stopTime;

    @Nullable
    @SerializedName("summary")
    @JvmField
    public String summary;

    /* renamed from: com.air.advantage.data.o1$a */
    public static final class a {
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final String generateShapshotID(int i10) {
            return "p" + i10;
        }

        @JvmStatic
        public final int getCurrentMinutesOfTheDay(@NotNull Calendar calendarCurrentTime) {
            Intrinsics.checkNotNullParameter(calendarCurrentTime, "calendarCurrentTime");
            return (calendarCurrentTime.get(11) * 60) + calendarCurrentTime.get(12);
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* renamed from: com.air.advantage.data.o1$b */
    public static final class b {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ b[] $VALUES;
        public static final b notScheduled = new b("notScheduled", 0, 0);
        public static final b startTimeIsScheduled = new b("startTimeIsScheduled", 1, 1);
        public static final b stopTimeIsScheduled = new b("stopTimeIsScheduled", 2, 2);
        private final int value;

        private static final /* synthetic */ b[] $values() {
            return new b[]{notScheduled, startTimeIsScheduled, stopTimeIsScheduled};
        }

        static {
            b[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private b(String str, int i10, int i11) {
            this.value = i11;
        }

        @NotNull
        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static b valueOf(String str) {
            return (b) Enum.valueOf(b.class, str);
        }

        public static b[] values() {
            return (b[]) $VALUES.clone();
        }

        public final int getValue() {
            return this.value;
        }
    }

    /* renamed from: com.air.advantage.data.o1$c */
    public static final class c implements ExclusionStrategy {
        c() {
        }

        public boolean shouldSkipClass(@Nullable Class cls) {
            return false;
        }

        public boolean shouldSkipField(@NotNull FieldAttributes fieldAttributes) {
            Intrinsics.checkNotNullParameter(fieldAttributes, "fieldAttributes");
            Expose expose = (Expose) fieldAttributes.getAnnotation(Expose.class);
            return (expose == null || expose.serialize()) ? false : true;
        }
    }

    /* renamed from: com.air.advantage.data.o1$d */
    public static final class d implements ExclusionStrategy {
        d() {
        }

        public boolean shouldSkipClass(@Nullable Class cls) {
            return false;
        }

        public boolean shouldSkipField(@NotNull FieldAttributes fieldAttributes) {
            Intrinsics.checkNotNullParameter(fieldAttributes, "fieldAttributes");
            Expose expose = (Expose) fieldAttributes.getAnnotation(Expose.class);
            return (expose == null || expose.deserialize()) ? false : true;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public SnapShot() {
    }

    @JvmStatic
    public static final int getCurrentMinutesOfTheDay(@NotNull Calendar calendar) {
        return Companion.getCurrentMinutesOfTheDay(calendar);
    }

    public static /* synthetic */ boolean update$default(SnapShot snapShot, SnapShot snapShot2, DataManager dataManager, boolean z7, int i10, Object obj) {
        if ((i10 & 4) != 0) {
            z7 = false;
        }
        return snapShot.update(snapShot2, dataManager, z7);
    }

    @NotNull
    public final String generateJSONString() {
        String json = new Gson().toJson(this);
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        return json;
    }

    @NotNull
    public final String generateJSONStringWithExclusion() {
        String json = new GsonBuilder().addSerializationExclusionStrategy(new c()).addDeserializationExclusionStrategy(new d()).create().toJson(this);
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        return json;
    }

    @NotNull
    public final String generateSummary() {
        ZoneState zoneState;
        StringBuilder sb = new StringBuilder();
        TreeMap treeMap = this.aircons;
        if (treeMap != null) {
            Intrinsics.checkNotNull(treeMap);
            Iterator it = treeMap.entrySet().iterator();
            while (it.hasNext()) {
                DataAirconSystem dataAirconSystem = (DataAirconSystem) ((Map.Entry) it.next()).getValue();
                if (dataAirconSystem != null) {
                    TreeMap treeMap2 = this.aircons;
                    Intrinsics.checkNotNull(treeMap2);
                    if (treeMap2.size() == 1) {
                        sb.append("Aircon - ");
                    } else {
                        String str = dataAirconSystem.info.name;
                        if (str != null) {
                            sb.append(str);
                            sb.append(" - ");
                        } else {
                            sb.append("AC - ");
                        }
                    }
                    SystemState systemState = dataAirconSystem.info.state;
                    if (systemState != null) {
                        sb.append(String.valueOf(systemState));
                        if (dataAirconSystem.info.state == SystemState.on) {
                            sb.append(", ");
                            if (dataAirconSystem.info.mode != null) {
                                sb.append("mode - ");
                                AirconMode airconMode = dataAirconSystem.info.mode;
                                if (airconMode == AirconMode.vent) {
                                    sb.append("fan, ");
                                } else {
                                    sb.append(String.valueOf(airconMode));
                                    sb.append(", ");
                                }
                            }
                            if (dataAirconSystem.info.fan != null) {
                                sb.append("fan - ");
                                FanStatus fanStatus = dataAirconSystem.info.fan;
                                if (fanStatus == FanStatus.medium) {
                                    sb.append("med, ");
                                } else {
                                    sb.append(String.valueOf(fanStatus));
                                    sb.append(", ");
                                }
                            }
                            Float targetTemperature = dataAirconSystem.getTargetTemperature();
                            if (targetTemperature == null || targetTemperature.floatValue() <= 0.1f) {
                                sb = new StringBuilder(sb.substring(0, sb.length() - 2));
                            } else {
                                sb.append((int) targetTemperature.floatValue());
                                sb.append("C");
                            }
                            sb.append("\n");
                            TreeMap<String, DataZone> treeMap3 = dataAirconSystem.zones;
                            if (treeMap3 != null) {
                                Intrinsics.checkNotNull(treeMap3);
                                if (treeMap3.size() > 0) {
                                    sb.append("Zones on: ");
                                    TreeMap<String, DataZone> treeMap4 = dataAirconSystem.zones;
                                    Intrinsics.checkNotNull(treeMap4);
                                    for (Map.Entry<String, DataZone> entry : treeMap4.entrySet()) {
                                        DataZone value = entry.getValue();
                                        if (value != null && (zoneState = value.state) != null && zoneState == ZoneState.open) {
                                            String key = entry.getKey();
                                            if (!Intrinsics.areEqual(key, "z10")) {
                                                Intrinsics.checkNotNull(key);
                                                key = StringsKt__StringsJVMKt.replace$default(key, UrlConfig.RESULT_OK, "", false, 4, null);
                                            }
                                            Integer num = dataAirconSystem.info.myZone;
                                            String str2 = (num == null || !Intrinsics.areEqual(DataZone.Companion.getZoneKey(num), entry.getKey())) ? "" : "*";
                                            sb.append(key);
                                            sb.append(str2);
                                            sb.append("  ");
                                        }
                                    }
                                    if (Intrinsics.areEqual(sb.substring(sb.length() - 2), "  ")) {
                                        sb = new StringBuilder(sb.substring(0, sb.length() - 2));
                                    }
                                    sb.append("\n");
                                }
                            }
                        } else {
                            sb.append("\n");
                        }
                    }
                    sb.append("\n");
                }
            }
        }
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        if (StringsKt__StringsJVMKt.J1(sb2, "\n\n", false, 2, null)) {
            sb = new StringBuilder(sb.substring(0, sb.length() - 2));
        }
        String sb3 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb3, "toString(...)");
        return sb3;
    }

    @NotNull
    public final String generateSummaryForZone10e(@Nullable Boolean bool) {
        ZoneState zoneState;
        StringBuilder sb = new StringBuilder();
        TreeMap treeMap = this.aircons;
        if (treeMap != null) {
            Intrinsics.checkNotNull(treeMap);
            if (treeMap.size() > 0) {
                TreeMap treeMap2 = this.aircons;
                Intrinsics.checkNotNull(treeMap2);
                TreeMap treeMap3 = this.aircons;
                Intrinsics.checkNotNull(treeMap3);
                DataAirconSystem dataAirconSystem = (DataAirconSystem) treeMap2.get(treeMap3.firstKey());
                if (dataAirconSystem != null) {
                    if (dataAirconSystem.info.state != null) {
                        if (bool != null && bool.booleanValue()) {
                            sb.append("Aircon - ");
                            sb.append(String.valueOf(dataAirconSystem.info.state));
                        }
                        if (dataAirconSystem.info.state == SystemState.on || bool == null || !bool.booleanValue()) {
                            if (bool != null && bool.booleanValue()) {
                                sb.append("\n");
                            }
                            TreeMap<String, DataZone> treeMap4 = dataAirconSystem.zones;
                            if (treeMap4 != null) {
                                Intrinsics.checkNotNull(treeMap4);
                                if (treeMap4.size() > 0) {
                                    sb.append("Zones on: ");
                                    TreeMap<String, DataZone> treeMap5 = dataAirconSystem.zones;
                                    Intrinsics.checkNotNull(treeMap5);
                                    for (Map.Entry<String, DataZone> entry : treeMap5.entrySet()) {
                                        DataZone value = entry.getValue();
                                        if (value != null && (zoneState = value.state) != null && zoneState == ZoneState.open) {
                                            String key = entry.getKey();
                                            if (!Intrinsics.areEqual(key, "z10")) {
                                                Intrinsics.checkNotNull(key);
                                                key = StringsKt__StringsJVMKt.replace$default(key, UrlConfig.RESULT_OK, "", false, 4, null);
                                            }
                                            Integer num = dataAirconSystem.info.myZone;
                                            String str = (num == null || !Intrinsics.areEqual(DataZone.Companion.getZoneKey(num), entry.getKey())) ? "" : "*";
                                            sb.append(key);
                                            sb.append(str);
                                            sb.append("  ");
                                        }
                                    }
                                    if (Intrinsics.areEqual(sb.substring(sb.length() - 2), "  ")) {
                                        sb = new StringBuilder(sb.substring(0, sb.length() - 2));
                                    }
                                    sb.append("\n");
                                }
                            }
                        } else {
                            sb.append("\n");
                        }
                    }
                    sb.append("\n");
                }
            }
        }
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        if (StringsKt__StringsJVMKt.J1(sb2, "\n\n", false, 2, null)) {
            sb = new StringBuilder(sb.substring(0, sb.length() - 2));
        }
        String sb3 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb3, "toString(...)");
        return sb3;
    }

    public final int getSnapshotIdNumber() {
        try {
            String str = this.snapshotId;
            Intrinsics.checkNotNull(str);
            Integer valueOf = Integer.valueOf(StringsKt__StringsJVMKt.replace$default(str, "p", "", false, 4, null));
            Intrinsics.checkNotNull(valueOf);
            return valueOf.intValue();
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    public final void initialiseAirconsCollection(@NotNull TreeMap airconsCollectionSource) {
        Intrinsics.checkNotNullParameter(airconsCollectionSource, "airconsCollectionSource");
        TreeMap treeMap = this.aircons;
        Intrinsics.checkNotNull(treeMap);
        treeMap.clear();
        for (Map.Entry entry : airconsCollectionSource.entrySet()) {
            String str = (String) entry.getKey();
            DataAirconSystem dataAirconSystem = (DataAirconSystem) entry.getValue();
            if (dataAirconSystem != null) {
                DataAirconSystem dataAirconSystem2 = new DataAirconSystem();
                TreeMap treeMap2 = this.aircons;
                Intrinsics.checkNotNull(treeMap2);
                treeMap2.put(str, dataAirconSystem2);
                dataAirconSystem2.updateForSnapshot(dataAirconSystem);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00b5  */
    @NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final b snapshotIsScheduledAtGivenTime(int i10, int i11) {
        Boolean bool;
        Integer num;
        Integer num2;
        Integer num3;
        if (this.snapshotId != null && (bool = this.enabled) != null && this.activeDays != null && this.startTime != null && this.stopTime != null && Intrinsics.areEqual(bool, Boolean.TRUE) && (((num = this.activeDays) == null || num.intValue() != 0) && !Intrinsics.areEqual(this.startTime, this.stopTime))) {
            Integer num4 = this.startTime;
            Intrinsics.checkNotNull(num4);
            if (num4.intValue() >= 0 && (num3 = this.startTime) != null && num3.intValue() == i11) {
                Integer num5 = this.activeDays;
                Intrinsics.checkNotNull(num5);
                if ((num5.intValue() & (1 << (i10 - 1))) != 0) {
                    return b.startTimeIsScheduled;
                }
            }
            Integer num6 = this.startTime;
            Intrinsics.checkNotNull(num6);
            if (num6.intValue() >= 0) {
                Integer num7 = this.stopTime;
                Intrinsics.checkNotNull(num7);
                if (num7.intValue() >= 0) {
                    Integer num8 = this.stopTime;
                    Intrinsics.checkNotNull(num8);
                    int intValue = num8.intValue();
                    Integer num9 = this.startTime;
                    Intrinsics.checkNotNull(num9);
                    if (intValue < num9.intValue()) {
                        Integer num10 = this.stopTime;
                        if (num10 != null && num10.intValue() == i11) {
                            if (i10 == 1) {
                                Integer num11 = this.activeDays;
                                Intrinsics.checkNotNull(num11);
                                if ((num11.intValue() & 64) != 0) {
                                    return b.stopTimeIsScheduled;
                                }
                            } else {
                                Integer num12 = this.activeDays;
                                Intrinsics.checkNotNull(num12);
                                if (((1 << (i10 - 2)) & num12.intValue()) != 0) {
                                    return b.stopTimeIsScheduled;
                                }
                            }
                        }
                    } else {
                        Integer num13 = this.stopTime;
                        Intrinsics.checkNotNull(num13);
                        if (num13.intValue() >= 0 && (num2 = this.stopTime) != null && num2.intValue() == i11) {
                            Integer num14 = this.activeDays;
                            Intrinsics.checkNotNull(num14);
                            if (((1 << (i10 - 1)) & num14.intValue()) != 0) {
                                return b.stopTimeIsScheduled;
                            }
                        }
                    }
                }
            }
        }
        return b.notScheduled;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@NotNull SnapShot snapshotSource, @Nullable DataManager dataManager) {
        Intrinsics.checkNotNullParameter(snapshotSource, "snapshotSource");
        return update$default(this, snapshotSource, dataManager, false, 4, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public final boolean update(@NotNull SnapShot snapshotFromJson) {
        boolean z7;
        String str;
        Boolean bool;
        Integer num;
        Integer num2;
        Integer num3;
        String str2;
        Boolean bool2;
        String str3;
        Intrinsics.checkNotNullParameter(snapshotFromJson, "snapshotFromJson");
        String str4 = snapshotFromJson.name;
        if (str4 == null || ((str3 = this.name) != null && Intrinsics.areEqual(str3, str4))) {
            z7 = false;
        } else {
            this.name = snapshotFromJson.name;
            z7 = true;
        }
        Boolean bool3 = snapshotFromJson.enabled;
        if (bool3 != null && ((bool2 = this.enabled) == null || !Intrinsics.areEqual(bool2, bool3))) {
            this.enabled = snapshotFromJson.enabled;
            z7 = true;
        }
        String str5 = snapshotFromJson.snapshotId;
        if (str5 != null && ((str2 = this.snapshotId) == null || !Intrinsics.areEqual(str2, str5))) {
            this.snapshotId = snapshotFromJson.snapshotId;
            z7 = true;
        }
        Integer num4 = snapshotFromJson.activeDays;
        if (num4 != null && ((num3 = this.activeDays) == null || !Intrinsics.areEqual(num3, num4))) {
            this.activeDays = snapshotFromJson.activeDays;
            z7 = true;
        }
        Integer num5 = snapshotFromJson.startTime;
        if (num5 != null && ((num2 = this.startTime) == null || !Intrinsics.areEqual(num2, num5))) {
            this.startTime = snapshotFromJson.startTime;
            z7 = true;
        }
        Integer num6 = snapshotFromJson.stopTime;
        if (num6 != null && ((num = this.stopTime) == null || !Intrinsics.areEqual(num, num6))) {
            this.stopTime = snapshotFromJson.stopTime;
            z7 = true;
        }
        Boolean bool4 = snapshotFromJson.runNow;
        if (bool4 != null && ((bool = this.runNow) == null || !Intrinsics.areEqual(bool, bool4))) {
            this.runNow = snapshotFromJson.runNow;
            z7 = true;
        }
        String str6 = snapshotFromJson.summary;
        if (str6 == null || ((str = this.summary) != null && Intrinsics.areEqual(str, str6))) {
            return z7;
        }
        this.summary = snapshotFromJson.summary;
        return true;
    }

    public SnapShot(@Nullable String str, @Nullable Boolean bool, @Nullable String str2, @Nullable Integer num, @Nullable Integer num2, @Nullable Integer num3, @Nullable Boolean bool2, @Nullable String str3) {
        this.name = str;
        this.enabled = bool;
        this.snapshotId = str2;
        this.activeDays = num;
        this.startTime = num2;
        this.stopTime = num3;
        this.runNow = bool2;
        this.summary = str3;
    }

    @JvmOverloads
    public final boolean update(@NotNull SnapShot snapshotSource, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        Intrinsics.checkNotNullParameter(snapshotSource, "snapshotSource");
        Integer num = snapshotSource.activeDays;
        if (num != null) {
            Integer num2 = this.activeDays;
            if (num2 == null || !Intrinsics.areEqual(num2, num)) {
                this.activeDays = snapshotSource.activeDays;
                if (dataManager != null) {
                    dataManager.add("activeDays", snapshotSource.activeDays);
                }
                z10 = true;
            }
            z10 = false;
        } else {
            if (z7 && this.activeDays != null) {
                if (dataManager != null) {
                    dataManager.add("activeDays", null);
                }
                z10 = true;
            }
            z10 = false;
        }
        Boolean bool = snapshotSource.enabled;
        if (bool != null) {
            Boolean bool2 = this.enabled;
            if (bool2 == null || !Intrinsics.areEqual(bool2, bool)) {
                this.enabled = snapshotSource.enabled;
                if (dataManager != null) {
                    dataManager.add("enabled", snapshotSource.enabled);
                }
                z10 = true;
            }
        } else if (z7 && this.enabled != null) {
            if (dataManager != null) {
                dataManager.add("enabled", null);
            }
            z10 = true;
        }
        String str = snapshotSource.name;
        if (str != null) {
            String str2 = this.name;
            if (str2 == null || !Intrinsics.areEqual(str2, str)) {
                this.name = snapshotSource.name;
                if (dataManager != null) {
                    dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, snapshotSource.name);
                }
                z10 = true;
            }
        } else if (z7 && this.name != null) {
            if (dataManager != null) {
                dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, null);
            }
            z10 = true;
        }
        String str3 = snapshotSource.snapshotId;
        if (str3 != null) {
            String str4 = this.snapshotId;
            if (str4 == null || !Intrinsics.areEqual(str4, str3)) {
                this.snapshotId = snapshotSource.snapshotId;
                if (dataManager != null) {
                    dataManager.add("snapshotId", snapshotSource.snapshotId);
                }
                z10 = true;
            }
        } else if (z7 && this.snapshotId != null) {
            if (dataManager != null) {
                dataManager.add("snapshotId", null);
            }
            z10 = true;
        }
        Integer num3 = snapshotSource.startTime;
        if (num3 != null) {
            Integer num4 = this.startTime;
            if (num4 == null || !Intrinsics.areEqual(num4, num3)) {
                this.startTime = snapshotSource.startTime;
                if (dataManager != null) {
                    dataManager.add("startTime", snapshotSource.startTime);
                }
                z10 = true;
            }
        } else if (z7 && this.startTime != null) {
            if (dataManager != null) {
                dataManager.add("startTime", null);
            }
            z10 = true;
        }
        Integer num5 = snapshotSource.stopTime;
        if (num5 != null) {
            Integer num6 = this.stopTime;
            if (num6 == null || !Intrinsics.areEqual(num6, num5)) {
                this.stopTime = snapshotSource.stopTime;
                if (dataManager != null) {
                    dataManager.add("stopTime", snapshotSource.stopTime);
                }
                z10 = true;
            }
        } else if (z7 && this.stopTime != null) {
            if (dataManager != null) {
                dataManager.add("stopTime", null);
            }
            z10 = true;
        }
        String str5 = snapshotSource.summary;
        if (str5 != null) {
            String str6 = this.summary;
            if (str6 == null || !Intrinsics.areEqual(str6, str5)) {
                this.summary = snapshotSource.summary;
                if (dataManager != null) {
                    dataManager.add("summary", snapshotSource.summary);
                }
                z10 = true;
            }
        } else if (z7 && this.summary != null) {
            if (dataManager != null) {
                dataManager.add("summary", null);
            }
            z10 = true;
        }
        TreeMap treeMap = snapshotSource.aircons;
        if (treeMap != null) {
            Intrinsics.checkNotNull(treeMap);
            for (String str7 : treeMap.keySet()) {
                TreeMap treeMap2 = snapshotSource.aircons;
                Intrinsics.checkNotNull(treeMap2);
                DataAirconSystem dataAirconSystem = (DataAirconSystem) treeMap2.get(str7);
                TreeMap treeMap3 = this.aircons;
                Intrinsics.checkNotNull(treeMap3);
                DataAirconSystem dataAirconSystem2 = (DataAirconSystem) treeMap3.get(str7);
                if (dataAirconSystem2 == null) {
                    dataAirconSystem2 = new DataAirconSystem();
                    TreeMap treeMap4 = this.aircons;
                    Intrinsics.checkNotNull(treeMap4);
                    treeMap4.put(str7, dataAirconSystem2);
                }
                if (DataAirconSystem.update$default(dataAirconSystem2, str7, dataAirconSystem, dataManager, "/snapshots/" + this.snapshotId, false, 16, null)) {
                    z10 = true;
                }
            }
        } else if (z7 && this.aircons != null) {
            if (dataManager == null) {
                return true;
            }
            dataManager.add("aircons", null);
            return true;
        }
        return z10;
    }
}