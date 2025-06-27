package com.air.advantage.data;

import com.air.advantage.libraryairconlightjson.AirconMode;
import com.air.advantage.libraryairconlightjson.FanStatus;
import com.air.advantage.libraryairconlightjson.JsonExporter;
import com.air.advantage.libraryairconlightjson.SystemState;
import com.air.advantage.libraryairconlightjson.ZoneState;
import com.bosma.blemodule.common.CommandKey;
import com.google.firebase.database.Exclude;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.data.c */
/* loaded from: classes.dex */
public final class DataAirconSystem {
    public static final int DEFAULT_CONSTANT1 = 0;
    public static final int DEFAULT_CONSTANT2 = 0;
    public static final int DEFAULT_CONSTANT3 = 0;
    public static final int DEFAULT_NOOFCONSTANTS = 0;
    public static final int DEFAULT_NOOFZONES = 0;
    public static final int DEFAULT_RESETFILTERCLEAN = 1;
    public static final int DEFAULT_RFSYSID = 0;
    public static final float DEFAULT_SETTEMP = 25.0f;
    public static final int DEFAULT_UNITCONTROLTEMPSETTING = 0;

    @NotNull
    public static final String SYSTEM_TYPE_ANYWAIR = "anywAiR";

    @NotNull
    public static final String SYSTEM_TYPE_EZONE = "e-zone";

    @NotNull
    public static final String SYSTEM_TYPE_EZONE_INTERNET = "e-zonei";

    @NotNull
    public static final String SYSTEM_TYPE_MYAIR1 = "MyAir1";

    @NotNull
    public static final String SYSTEM_TYPE_MYAIR2 = "MyAir2";

    @NotNull
    public static final String SYSTEM_TYPE_MYAIR3 = "MyAir3";

    @NotNull
    public static final String SYSTEM_TYPE_MYAIR4 = "MyAir4";

    @NotNull
    public static final String SYSTEM_TYPE_MYAIR4_INTERNET = "MyAir4i";

    @NotNull
    public static final String SYSTEM_TYPE_MYAIR5 = "MyAir5";

    @NotNull
    public static final String SYSTEM_TYPE_VAMS = "vams";

    @NotNull
    public static final String SYSTEM_TYPE_ZONE10 = "zone10";

    @NotNull
    public static final String SYSTEM_TYPE_ZONE10E = "zone10e";
    public static final int UNIT_CONTROL_ACUCM = 1;
    public static final int UNIT_CONTROL_INFO_ACTRON_AIR_MODBUS = 34;
    public static final int UNIT_CONTROL_INFO_BRAEMAR_VRF = 35;
    public static final int UNIT_CONTROL_INFO_BRIVIS = 32;
    public static final int UNIT_CONTROL_INFO_CM_AA_MYDICT = 24;
    public static final int UNIT_CONTROL_INFO_DICT_ELV = 29;
    public static final int UNIT_CONTROL_INFO_DK_AA_MYDICTv2 = 17;
    public static final int UNIT_CONTROL_INFO_DK_ADAPTOR = 2;
    public static final int UNIT_CONTROL_INFO_DK_INTENSIS = 4;
    public static final int UNIT_CONTROL_INFO_DK_ON_OFF_CONTROL_ONLY = 52;
    public static final int UNIT_CONTROL_INFO_FJ_AA_MYDICTv2 = 19;
    public static final int UNIT_CONTROL_INFO_FJ_INTENSIS = 5;
    public static final int UNIT_CONTROL_INFO_FJ_ON_OFF_CONTROL_ONLY = 50;
    public static final int UNIT_CONTROL_INFO_FUJITSU_SPLIT_SYSTEMS = 38;
    public static final int UNIT_CONTROL_INFO_FUJITSU_VRF = 37;
    public static final int UNIT_CONTROL_INFO_GREE = 31;
    public static final int UNIT_CONTROL_INFO_HAIER = 30;
    public static final int UNIT_CONTROL_INFO_HAIERv2 = 39;
    public static final int UNIT_CONTROL_INFO_HITACHI = 27;
    public static final int UNIT_CONTROL_INFO_LG_AA_MYDICTv2 = 20;
    public static final int UNIT_CONTROL_INFO_LG_INTENSIS = 8;
    public static final int UNIT_CONTROL_INFO_ME_AA_MYDICTv2 = 21;
    public static final int UNIT_CONTROL_INFO_ME_INTENSIS = 7;
    public static final int UNIT_CONTROL_INFO_MHI_AA_MYDICTv2 = 22;
    public static final int UNIT_CONTROL_INFO_MHI_INTENSIS = 12;
    public static final int UNIT_CONTROL_INFO_NO_UNIT = 26;
    public static final int UNIT_CONTROL_INFO_PA_AA_MYDICTv2 = 18;
    public static final int UNIT_CONTROL_INFO_PA_INTENSIS = 6;
    public static final int UNIT_CONTROL_INFO_PA_ON_OFF_CONTROL_ONLY = 51;
    public static final int UNIT_CONTROL_INFO_RINNAI_VRF = 36;
    public static final int UNIT_CONTROL_INFO_SAM_AA_MIDICT = 16;
    public static final int UNIT_CONTROL_INFO_SAM_AA_MIDICTv2 = 25;
    public static final int UNIT_CONTROL_INFO_TEMPERZONE = 28;
    public static final int UNIT_CONTROL_INFO_TOSHIBA_TYPE_2 = 33;
    public static final int UNIT_CONTROL_INFO_TOSH_AA_MYDICTv2 = 23;
    public static final int UNIT_CONTROL_INFO_TOSH_INTENSIS = 10;

    @NotNull
    public static final String UNIT_ERROR_CODE_NO_TEMP_SENSORS = "AA4";
    public static final int UNIT_NO_DEVICE_CONNECTED = 0;

    @NotNull
    @JvmField
    public final transient Gson gsonForDB;

    @NotNull
    @JvmField
    public final transient Gson gsonForSendingExternally;

    @SerializedName("info")
    @NotNull
    @JvmField
    public final DataAirconInfo info;

    @Nullable
    @SerializedName("zones")
    @JvmField
    public TreeMap<String, DataZone> zones;

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    @JvmField
    public static final FanStatus DEFAULT_FAN = FanStatus.auto;

    @NotNull
    private static final FreshAirStatus DEFAULT_FRESHAIRSTATUS = FreshAirStatus.none;

    @NotNull
    @JvmField
    public static final AirconMode DEFAULT_MODE = AirconMode.auto;

    @NotNull
    private static final SystemState DEFAULT_STATE = SystemState.off;
    private static final String LOG_TAG = DataAirconSystem.class.getSimpleName();

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* renamed from: com.air.advantage.data.c$a */
    public static final class CodeStatus {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ CodeStatus[] $VALUES;

        @JvmField
        public final int value;

        @SerializedName("noCode")
        public static final CodeStatus noCode = new CodeStatus("noCode", 0, 0);

        @SerializedName("codeEnabled")
        public static final CodeStatus codeEnabled = new CodeStatus("codeEnabled", 1, 1);

        @SerializedName("expired")
        public static final CodeStatus expired = new CodeStatus("expired", 2, 2);

        @SerializedName("sending")
        public static final CodeStatus sending = new CodeStatus("sending", 3, 3);

        private static final /* synthetic */ CodeStatus[] $values() {
            return new CodeStatus[]{noCode, codeEnabled, expired, sending};
        }

        static {
            CodeStatus[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private CodeStatus(String str, int i10, int i11) {
            this.value = i11;
        }

        @NotNull
        public static EnumEntries<CodeStatus> getEntries() {
            return $ENTRIES;
        }

        public static CodeStatus valueOf(String str) {
            return (CodeStatus) Enum.valueOf(CodeStatus.class, str);
        }

        public static CodeStatus[] values() {
            return (CodeStatus[]) $VALUES.clone();
        }
    }

    /* renamed from: com.air.advantage.data.c$b */
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.c.b.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        /* renamed from: generateAirconKey */
        public final String generateCommandKey(int i10) {
            return CommandKey.CMD_REQUEST_AEGIS_LOCAL_LOCK_THE_LOCK_V2 + i10;
        }

        @NotNull
        public final FreshAirStatus getDEFAULT_FRESHAIRSTATUS() {
            return DataAirconSystem.DEFAULT_FRESHAIRSTATUS;
        }

        @NotNull
        public final SystemState getDEFAULT_STATE() {
            return DataAirconSystem.DEFAULT_STATE;
        }

        @JvmStatic
        public final boolean isMyAutoRangeCoolValueValid(int i10) {
            return i10 >= 20 && i10 <= 32;
        }

        @JvmStatic
        public final boolean isMyAutoRangeHeatAndCoolDifferenceValid(int i10, int i11) {
            return i10 - i11 >= 4;
        }

        @JvmStatic
        public final boolean isMyAutoRangeHeatValueValid(int i10) {
            return i10 >= 16 && i10 <= 28;
        }

        @Exclude
        @JvmStatic
        public final boolean isMyAutoRangeValid(@Nullable Integer num, @Nullable Integer num2) {
            return num != null && num2 != null && num.intValue() >= 20 && num.intValue() <= 32 && num2.intValue() >= 16 && num2.intValue() <= 28 && num.intValue() - num2.intValue() >= 4;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* renamed from: com.air.advantage.data.c$c */
    public static final class FreshAirStatus {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ FreshAirStatus[] $VALUES;

        @SerializedName("none")
        public static final FreshAirStatus none = new FreshAirStatus("none", 0, 0);

        @SerializedName(DebugKt.DEBUG_PROPERTY_VALUE_OFF)
        public static final FreshAirStatus off = new FreshAirStatus(DebugKt.DEBUG_PROPERTY_VALUE_OFF, 1, 1);

        @SerializedName(DebugKt.DEBUG_PROPERTY_VALUE_ON)
        public static final FreshAirStatus on = new FreshAirStatus(DebugKt.DEBUG_PROPERTY_VALUE_ON, 2, 2);

        @JvmField
        public final int value;

        private static final /* synthetic */ FreshAirStatus[] $values() {
            return new FreshAirStatus[]{none, off, on};
        }

        static {
            FreshAirStatus[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private FreshAirStatus(String str, int i10, int i11) {
            this.value = i11;
        }

        @NotNull
        public static EnumEntries<FreshAirStatus> getEntries() {
            return $ENTRIES;
        }

        public static FreshAirStatus valueOf(String str) {
            return (FreshAirStatus) Enum.valueOf(FreshAirStatus.class, str);
        }

        public static FreshAirStatus[] values() {
            return (FreshAirStatus[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* renamed from: com.air.advantage.data.c$d */
    public static final class ActivationCode {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ ActivationCode[] $VALUES;

        @SerializedName("setNewCode")
        public static final ActivationCode setNewCode = new ActivationCode("setNewCode", 0, 1);

        @SerializedName("unlock")
        public static final ActivationCode unlock = new ActivationCode("unlock", 1, 2);

        @JvmField
        public final int value;

        private static final /* synthetic */ ActivationCode[] $values() {
            return new ActivationCode[]{setNewCode, unlock};
        }

        static {
            ActivationCode[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private ActivationCode(String str, int i10, int i11) {
            this.value = i11;
        }

        @NotNull
        public static EnumEntries<ActivationCode> getEntries() {
            return $ENTRIES;
        }

        public static ActivationCode valueOf(String str) {
            return (ActivationCode) Enum.valueOf(ActivationCode.class, str);
        }

        public static ActivationCode[] values() {
            return (ActivationCode[]) $VALUES.clone();
        }
    }

    /* renamed from: com.air.advantage.data.c$e */
    public static final class SaveThisSerializationExclusionStrategy implements ExclusionStrategy {
        SaveThisSerializationExclusionStrategy() {
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipClass(@Nullable Class<?> cls) {
            return false;
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipField(@NotNull FieldAttributes fieldAttributes) {
            Intrinsics.checkNotNullParameter(fieldAttributes, "fieldAttributes");
            JsonExporter jsonExporter = (JsonExporter) fieldAttributes.getAnnotation(JsonExporter.class);
            return (jsonExporter == null || jsonExporter.saveThis()) ? false : true;
        }
    }

    /* renamed from: com.air.advantage.data.c$f */
    public static final class SaveThisDeserializationExclusionStrategy implements ExclusionStrategy {
        SaveThisDeserializationExclusionStrategy() {
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipClass(@Nullable Class<?> cls) {
            return false;
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipField(@NotNull FieldAttributes fieldAttributes) {
            Intrinsics.checkNotNullParameter(fieldAttributes, "fieldAttributes");
            JsonExporter jsonExporter = (JsonExporter) fieldAttributes.getAnnotation(JsonExporter.class);
            return (jsonExporter == null || jsonExporter.saveThis()) ? false : true;
        }
    }

    /* renamed from: com.air.advantage.data.c$g */
    public static final class ExportSerializationExclusionStrategy implements ExclusionStrategy {
        ExportSerializationExclusionStrategy() {
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipClass(@Nullable Class<?> cls) {
            return false;
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipField(@NotNull FieldAttributes fieldAttributes) {
            Intrinsics.checkNotNullParameter(fieldAttributes, "fieldAttributes");
            JsonExporter jsonExporter = (JsonExporter) fieldAttributes.getAnnotation(JsonExporter.class);
            return (jsonExporter == null || jsonExporter.export()) ? false : true;
        }
    }

    /* renamed from: com.air.advantage.data.c$h */
    public static final class ExportDeserializationExclusionStrategy implements ExclusionStrategy {
        ExportDeserializationExclusionStrategy() {
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipClass(@Nullable Class<?> cls) {
            return false;
        }

        @Override // com.google.gson.ExclusionStrategy
        public boolean shouldSkipField(@NotNull FieldAttributes fieldAttributes) {
            Intrinsics.checkNotNullParameter(fieldAttributes, "fieldAttributes");
            JsonExporter jsonExporter = (JsonExporter) fieldAttributes.getAnnotation(JsonExporter.class);
            return (jsonExporter == null || jsonExporter.export()) ? false : true;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public DataAirconSystem() {
        this.info = new DataAirconInfo();
        Gson create = new GsonBuilder().addSerializationExclusionStrategy(new ExportSerializationExclusionStrategy()).addDeserializationExclusionStrategy(new ExportDeserializationExclusionStrategy()).create();
        Intrinsics.checkNotNullExpressionValue(create, "create(...)");
        this.gsonForSendingExternally = create;
        Gson create2 = new GsonBuilder().addSerializationExclusionStrategy(new SaveThisSerializationExclusionStrategy()).addDeserializationExclusionStrategy(new SaveThisDeserializationExclusionStrategy()).create();
        Intrinsics.checkNotNullExpressionValue(create2, "create(...)");
        this.gsonForDB = create2;
        this.zones = new TreeMap<>();
    }

    private final Integer getMyZoneZoneNumber() {
        Integer num = this.info.myZone;
        if (num != null) {
            return num;
        }
        Timber.forest.w(LOG_TAG, "DBG null aircon unitControlTempSetting for aircon ");
        return 0;
    }

    @JvmStatic
    public static final boolean isMyAutoRangeCoolValueValid(int i10) {
        return Companion.isMyAutoRangeCoolValueValid(i10);
    }

    @JvmStatic
    public static final boolean isMyAutoRangeHeatAndCoolDifferenceValid(int i10, int i11) {
        return Companion.isMyAutoRangeHeatAndCoolDifferenceValid(i10, i11);
    }

    @JvmStatic
    public static final boolean isMyAutoRangeHeatValueValid(int i10) {
        return Companion.isMyAutoRangeHeatValueValid(i10);
    }

    @Exclude
    @JvmStatic
    public static final boolean isMyAutoRangeValid(@Nullable Integer num, @Nullable Integer num2) {
        return Companion.isMyAutoRangeValid(num, num2);
    }

    public static /* synthetic */ boolean update$default(DataAirconSystem dataAirconSystem, String str, DataAirconSystem dataAirconSystem2, DataManager dataManager, String str2, boolean z7, int i10, Object obj) {
        if ((i10 & 16) != 0) {
            z7 = false;
        }
        return dataAirconSystem.update(str, dataAirconSystem2, dataManager, str2, z7);
    }

    public final void clearDataForBackup() {
        this.info.clearDataForBackup();
        TreeMap<String, DataZone> treeMap = this.zones;
        if (treeMap != null) {
            Intrinsics.checkNotNull(treeMap);
            for (DataZone dataZone : treeMap.values()) {
                Intrinsics.checkNotNull(dataZone);
                dataZone.clearDataForBackup();
            }
        }
    }

    public final void fixOldData() {
        ArrayList arrayList = new ArrayList();
        TreeMap<String, DataZone> treeMap = this.zones;
        Intrinsics.checkNotNull(treeMap);
        for (String str : treeMap.keySet()) {
            TreeMap<String, DataZone> treeMap2 = this.zones;
            Intrinsics.checkNotNull(treeMap2);
            DataZone dataZone = treeMap2.get(str);
            if (dataZone != null) {
                Intrinsics.checkNotNull(str);
                if (str.length() == 2) {
                    Timber.forest.d("updating old zone key " + str, new Object[0]);
                    if (dataZone.number != null) {
                        TreeMap<String, DataZone> treeMap3 = this.zones;
                        Intrinsics.checkNotNull(treeMap3);
                        treeMap3.put(dataZone.getZoneKey(), dataZone);
                    }
                    arrayList.add(str);
                }
                if (this.info.noOfZones != null) {
                    Integer num = dataZone.number;
                    if (num != null) {
                        Intrinsics.checkNotNull(num);
                        int intValue = num.intValue();
                        Integer num2 = this.info.noOfZones;
                        Intrinsics.checkNotNull(num2);
                        if (intValue > num2.intValue()) {
                            arrayList.add(str);
                        }
                    } else {
                        arrayList.add(str);
                    }
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            TreeMap<String, DataZone> treeMap4 = this.zones;
            Intrinsics.checkNotNull(treeMap4);
            treeMap4.remove(str2);
        }
    }

    @Nullable
    public final String generateJSONStringForAirconNameOnly() {
        String str;
        DataAirconSystem dataAirconSystem = new DataAirconSystem();
        DataAirconInfo dataAirconInfo = this.info;
        String str2 = dataAirconInfo.uid;
        if (str2 == null || (str = dataAirconInfo.name) == null) {
            return null;
        }
        DataAirconInfo dataAirconInfo2 = dataAirconSystem.info;
        dataAirconInfo2.uid = str2;
        dataAirconInfo2.name = str;
        return this.gsonForDB.toJson(dataAirconSystem);
    }

    @Nullable
    public final String generateJSONStringForZoneNamesOnly() {
        DataAirconSystem dataAirconSystem = new DataAirconSystem();
        if (this.info.uid == null) {
            return null;
        }
        TreeMap<String, DataZone> treeMap = this.zones;
        Intrinsics.checkNotNull(treeMap);
        if (treeMap.size() == 0) {
            return null;
        }
        dataAirconSystem.info.uid = this.info.uid;
        TreeMap<String, DataZone> treeMap2 = this.zones;
        Intrinsics.checkNotNull(treeMap2);
        for (DataZone dataZone : treeMap2.values()) {
            Intrinsics.checkNotNull(dataZone);
            if (dataZone.name == null) {
                return null;
            }
            DataZone dataZone2 = new DataZone(dataZone.number);
            dataZone2.name = dataZone.name;
            TreeMap<String, DataZone> treeMap3 = dataAirconSystem.zones;
            Intrinsics.checkNotNull(treeMap3);
            treeMap3.put(dataZone2.getZoneKey(), dataZone2);
        }
        return this.gsonForDB.toJson(dataAirconSystem);
    }

    @NotNull
    public final String getMyZoneZoneName() {
        String str;
        ZoneState zoneState;
        Integer myZoneZoneNumber = getMyZoneZoneNumber();
        if (myZoneZoneNumber == null || myZoneZoneNumber.intValue() != 0) {
            TreeMap<String, DataZone> treeMap = this.zones;
            Intrinsics.checkNotNull(treeMap);
            DataZone dataZone = treeMap.get(DataZone.Companion.getZoneKey(myZoneZoneNumber));
            if (dataZone != null && (str = dataZone.name) != null && dataZone.setTemp != null && (zoneState = dataZone.state) != null && zoneState == ZoneState.open) {
                Intrinsics.checkNotNull(str);
                return str;
            }
        }
        return "";
    }

    @NotNull
    public final DataZone getOrMakeDataZone(@Nullable String str) {
        TreeMap<String, DataZone> treeMap = this.zones;
        Intrinsics.checkNotNull(treeMap);
        DataZone dataZone = treeMap.get(str);
        if (dataZone != null) {
            return dataZone;
        }
        DataZone dataZone2 = new DataZone((Integer) 0);
        dataZone2.setNumberFromKey(str);
        TreeMap<String, DataZone> treeMap2 = this.zones;
        Intrinsics.checkNotNull(treeMap2);
        treeMap2.put(str, dataZone2);
        return dataZone2;
    }

    @Nullable
    public final Float getTargetTemperature() {
        Float f3;
        ZoneState zoneState;
        Float f7;
        Float valueOf = Float.valueOf(0.0f);
        Integer myZoneZoneNumber = getMyZoneZoneNumber();
        if (myZoneZoneNumber != null && myZoneZoneNumber.intValue() == 0) {
            AirconMode airconMode = this.info.mode;
            return (!(airconMode == null || ((airconMode != AirconMode.dry || isTemperatureControlInDryAvailable()) && this.info.mode != AirconMode.vent)) || (f7 = this.info.setTemp) == null) ? valueOf : f7;
        }
        TreeMap<String, DataZone> treeMap = this.zones;
        Intrinsics.checkNotNull(treeMap);
        DataZone dataZone = treeMap.get(DataZone.Companion.getZoneKey(myZoneZoneNumber));
        return (dataZone == null || (f3 = dataZone.setTemp) == null || (zoneState = dataZone.state) == null || zoneState != ZoneState.open) ? valueOf : f3;
    }

    public final boolean hasAnyConstants() {
        try {
            TreeMap<String, DataZone> treeMap = this.zones;
            Intrinsics.checkNotNull(treeMap);
            for (DataZone dataZone : treeMap.values()) {
                Intrinsics.checkNotNull(dataZone);
                Integer num = dataZone.number;
                Intrinsics.checkNotNull(num);
                if (isZoneConstant(num.intValue())) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public final boolean hasJZ14UpdateToSend(@NotNull DataAirconSystem airconSource) {
        AirconMode airconMode;
        FreshAirStatus freshAirStatus;
        Integer num;
        Float f3;
        FanStatus fanStatus;
        AirconMode airconMode2;
        SystemState systemState;
        Intrinsics.checkNotNullParameter(airconSource, "airconSource");
        DataAirconInfo dataAirconInfo = airconSource.info;
        SystemState systemState2 = dataAirconInfo.state;
        boolean z7 = systemState2 != null && ((systemState = this.info.state) == null || systemState != systemState2);
        AirconMode airconMode3 = dataAirconInfo.mode;
        if (airconMode3 != null && ((airconMode2 = this.info.mode) == null || airconMode2 != airconMode3)) {
            z7 = true;
        }
        FanStatus fanStatus2 = dataAirconInfo.fan;
        if (fanStatus2 != null && ((fanStatus = this.info.fan) == null || fanStatus != fanStatus2)) {
            z7 = true;
        }
        Float f7 = dataAirconInfo.setTemp;
        if (f7 != null && ((f3 = this.info.setTemp) == null || !Intrinsics.areEqual(f3, f7))) {
            z7 = true;
        }
        Integer num2 = airconSource.info.myZone;
        if (num2 != null && ((num = this.info.myZone) == null || !Intrinsics.areEqual(num, num2))) {
            z7 = true;
        }
        DataAirconInfo dataAirconInfo2 = airconSource.info;
        FreshAirStatus freshAirStatus2 = dataAirconInfo2.freshAirStatus;
        if (freshAirStatus2 != null && ((freshAirStatus = this.info.freshAirStatus) == null || freshAirStatus != freshAirStatus2)) {
            z7 = true;
        }
        AirconMode airconMode4 = dataAirconInfo2.myAutoModeCurrentSetMode;
        if (airconMode4 == null || ((airconMode = this.info.myAutoModeCurrentSetMode) != null && airconMode == airconMode4)) {
            return z7;
        }
        return true;
    }

    public final boolean hasJZ6UpdateToSend(@NotNull DataAirconSystem airconSource) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Integer num5;
        Integer num6;
        Intrinsics.checkNotNullParameter(airconSource, "airconSource");
        Integer num7 = airconSource.info.noOfZones;
        boolean z7 = num7 != null && ((num6 = this.info.noOfZones) == null || !Intrinsics.areEqual(num6, num7));
        Integer num8 = airconSource.info.noOfConstantZones;
        if (num8 != null && ((num5 = this.info.noOfConstantZones) == null || !Intrinsics.areEqual(num5, num8))) {
            z7 = true;
        }
        Integer num9 = airconSource.info.constantZone1;
        if (num9 != null && ((num4 = this.info.constantZone1) == null || !Intrinsics.areEqual(num4, num9))) {
            z7 = true;
        }
        Integer num10 = airconSource.info.constantZone2;
        if (num10 != null && ((num3 = this.info.constantZone2) == null || !Intrinsics.areEqual(num3, num10))) {
            z7 = true;
        }
        Integer num11 = airconSource.info.constantZone3;
        if (num11 != null && ((num2 = this.info.constantZone3) == null || !Intrinsics.areEqual(num2, num11))) {
            z7 = true;
        }
        Integer num12 = airconSource.info.filterCleanStatus;
        if (num12 == null || ((num = this.info.filterCleanStatus) != null && Intrinsics.areEqual(num, num12))) {
            return z7;
        }
        return true;
    }

    @Exclude
    public final boolean isAutoFanAvailable() {
        Integer num = this.info.unitType;
        if (num != null) {
            return !(((((((((((((num != null && num.intValue() == 1) || (num != null && num.intValue() == 2)) || (num != null && num.intValue() == 4)) || (num != null && num.intValue() == 17)) || (num != null && num.intValue() == 8)) || (num != null && num.intValue() == 20)) || (num != null && num.intValue() == 12)) || (num != null && num.intValue() == 22)) || (num != null && num.intValue() == 0)) || (num != null && num.intValue() == 27)) || (num != null && num.intValue() == 29)) || (num != null && num.intValue() == 32)) || (num != null && num.intValue() == 34));
        }
        return false;
    }

    @Exclude
    public final boolean isAutoFanAvailableAAVersion() {
        Integer num = this.info.unitType;
        if (num != null) {
            return !((num != null && num.intValue() == 1) || (num != null && num.intValue() == 29));
        }
        return false;
    }

    @Exclude
    public final boolean isDryModeAvailable() {
        Integer num = this.info.unitType;
        if (num != null) {
            return !((((((((num != null && num.intValue() == 2) || (num != null && num.intValue() == 10)) || (num != null && num.intValue() == 23)) || (num != null && num.intValue() == 1)) || (num != null && num.intValue() == 0)) || (num != null && num.intValue() == 29)) || (num != null && num.intValue() == 32)) || (num != null && num.intValue() == 34));
        }
        return false;
    }

    @Exclude
    public final boolean isTemperatureControlInDryAvailable() {
        if (!isDryModeAvailable()) {
            return false;
        }
        Integer num = this.info.unitType;
        return !(((((num != null && num.intValue() == 4) || (num != null && num.intValue() == 17)) || (num != null && num.intValue() == 8)) || (num != null && num.intValue() == 20)) || (num != null && num.intValue() == 32));
    }

    @Exclude
    public final boolean isUnitControlFullControlDb() {
        Integer num = this.info.unitType;
        if (num != null) {
            return (((((((((num != null && num.intValue() == 16) || (num != null && num.intValue() == 17)) || (num != null && num.intValue() == 18)) || (num != null && num.intValue() == 19)) || (num != null && num.intValue() == 20)) || (num != null && num.intValue() == 21)) || (num != null && num.intValue() == 22)) || (num != null && num.intValue() == 23)) || (num != null && num.intValue() == 24)) || (num != null && num.intValue() == 25);
        }
        return false;
    }

    @Exclude
    public final boolean isUnitControlOnOffOnlyDb() {
        Integer num = this.info.unitType;
        if (num != null) {
            return ((num != null && num.intValue() == 50) || (num != null && num.intValue() == 51)) || (num != null && num.intValue() == 52);
        }
        return false;
    }

    public final boolean isZoneConstant(int i10) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4 = this.info.noOfConstantZones;
        if (num4 != null) {
            Intrinsics.checkNotNull(num4);
            if (num4.intValue() >= 1 && (num3 = this.info.constantZone1) != null && num3 != null && i10 == num3.intValue()) {
                return true;
            }
        }
        Integer num5 = this.info.noOfConstantZones;
        if (num5 != null) {
            Intrinsics.checkNotNull(num5);
            if (num5.intValue() >= 2 && (num2 = this.info.constantZone2) != null && num2 != null && i10 == num2.intValue()) {
                return true;
            }
        }
        Integer num6 = this.info.noOfConstantZones;
        if (num6 == null) {
            return false;
        }
        Intrinsics.checkNotNull(num6);
        return num6.intValue() >= 3 && (num = this.info.constantZone3) != null && num != null && i10 == num.intValue();
    }

    public final void sanitiseData() {
        this.info.sanitiseData();
        TreeMap<String, DataZone> treeMap = this.zones;
        Intrinsics.checkNotNull(treeMap);
        for (DataZone dataZone : treeMap.values()) {
            Intrinsics.checkNotNull(dataZone);
            dataZone.sanitiseData();
        }
    }

    public final void setOnAAHardware(boolean z7) {
        this.info.setOnAAHardware(Boolean.valueOf(z7));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmOverloads
    public final boolean update(@Nullable String str, @Nullable DataAirconSystem dataAirconSystem, @Nullable DataManager dataManager, @Nullable String str2) {
        return update$default(this, str, dataAirconSystem, dataManager, str2, false, 16, null);
    }

    public final boolean updateForAutoModeBackup(@Nullable DataAirconSystem dataAirconSystem) {
        Integer num;
        Float f3;
        String str;
        boolean z7 = false;
        if (dataAirconSystem == null) {
            return false;
        }
        String str2 = dataAirconSystem.info.uid;
        if (str2 != null && ((str = this.info.uid) == null || !Intrinsics.areEqual(str, str2))) {
            this.info.uid = dataAirconSystem.info.uid;
            z7 = true;
        }
        Float f7 = dataAirconSystem.info.setTemp;
        if (f7 != null && ((f3 = this.info.setTemp) == null || !Intrinsics.areEqual(f3, f7))) {
            this.info.setTemp = dataAirconSystem.info.setTemp;
            z7 = true;
        }
        Integer num2 = dataAirconSystem.info.myZone;
        if (num2 != null && ((num = this.info.myZone) == null || !Intrinsics.areEqual(num, num2))) {
            this.info.myZone = dataAirconSystem.info.myZone;
            z7 = true;
        }
        TreeMap<String, DataZone> treeMap = dataAirconSystem.zones;
        Intrinsics.checkNotNull(treeMap);
        if (treeMap.size() > 0) {
            TreeMap<String, DataZone> treeMap2 = dataAirconSystem.zones;
            Intrinsics.checkNotNull(treeMap2);
            for (String str3 : treeMap2.keySet()) {
                TreeMap<String, DataZone> treeMap3 = dataAirconSystem.zones;
                Intrinsics.checkNotNull(treeMap3);
                DataZone dataZone = treeMap3.get(str3);
                TreeMap<String, DataZone> treeMap4 = this.zones;
                Intrinsics.checkNotNull(treeMap4);
                DataZone dataZone2 = treeMap4.get(str3);
                if (dataZone2 == null) {
                    dataZone2 = new DataZone(str3);
                    TreeMap<String, DataZone> treeMap5 = this.zones;
                    Intrinsics.checkNotNull(treeMap5);
                    treeMap5.put(str3, dataZone2);
                }
                if (dataZone2.updateForAutoModeBackup(dataZone)) {
                    z7 = true;
                }
            }
        }
        return z7;
    }

    public final boolean updateForSnapshot(@Nullable DataAirconSystem dataAirconSystem) {
        boolean z7;
        DataAirconInfo dataAirconInfo;
        FreshAirStatus freshAirStatus;
        Integer num;
        Float f3;
        DataAirconInfo dataAirconInfo2;
        FanStatus fanStatus;
        DataAirconInfo dataAirconInfo3;
        AirconMode airconMode;
        String str;
        DataAirconInfo dataAirconInfo4;
        SystemState systemState;
        String str2;
        Intrinsics.checkNotNull(dataAirconSystem);
        String str3 = dataAirconSystem.info.uid;
        if (str3 == null || ((str2 = this.info.uid) != null && Intrinsics.areEqual(str2, str3))) {
            z7 = false;
        } else {
            this.info.uid = dataAirconSystem.info.uid;
            z7 = true;
        }
        DataAirconInfo dataAirconInfo5 = dataAirconSystem.info;
        SystemState systemState2 = dataAirconInfo5.state;
        if (systemState2 != null && ((systemState = (dataAirconInfo4 = this.info).state) == null || systemState != systemState2)) {
            dataAirconInfo4.state = systemState2;
            z7 = true;
        }
        String str4 = dataAirconInfo5.name;
        if (str4 != null && ((str = this.info.name) == null || !Intrinsics.areEqual(str, str4))) {
            this.info.name = dataAirconSystem.info.name;
            z7 = true;
        }
        DataAirconInfo dataAirconInfo6 = dataAirconSystem.info;
        AirconMode airconMode2 = dataAirconInfo6.mode;
        if (airconMode2 != null && ((airconMode = (dataAirconInfo3 = this.info).mode) == null || airconMode != airconMode2)) {
            dataAirconInfo3.mode = airconMode2;
            z7 = true;
        }
        FanStatus fanStatus2 = dataAirconInfo6.fan;
        if (fanStatus2 != null && ((fanStatus = (dataAirconInfo2 = this.info).fan) == null || fanStatus != fanStatus2)) {
            dataAirconInfo2.fan = fanStatus2;
            z7 = true;
        }
        Float f7 = dataAirconInfo6.setTemp;
        if (f7 != null && ((f3 = this.info.setTemp) == null || !Intrinsics.areEqual(f3, f7))) {
            this.info.setTemp = dataAirconSystem.info.setTemp;
            z7 = true;
        }
        Integer num2 = dataAirconSystem.info.myZone;
        if (num2 != null && ((num = this.info.myZone) == null || !Intrinsics.areEqual(num, num2))) {
            this.info.myZone = dataAirconSystem.info.myZone;
            z7 = true;
        }
        FreshAirStatus freshAirStatus2 = dataAirconSystem.info.freshAirStatus;
        if (freshAirStatus2 != null && ((freshAirStatus = (dataAirconInfo = this.info).freshAirStatus) == null || freshAirStatus != freshAirStatus2)) {
            dataAirconInfo.freshAirStatus = freshAirStatus2;
            z7 = true;
        }
        TreeMap<String, DataZone> treeMap = dataAirconSystem.zones;
        Intrinsics.checkNotNull(treeMap);
        if (treeMap.size() > 0) {
            TreeMap<String, DataZone> treeMap2 = dataAirconSystem.zones;
            Intrinsics.checkNotNull(treeMap2);
            for (String str5 : treeMap2.keySet()) {
                TreeMap<String, DataZone> treeMap3 = dataAirconSystem.zones;
                Intrinsics.checkNotNull(treeMap3);
                DataZone dataZone = treeMap3.get(str5);
                TreeMap<String, DataZone> treeMap4 = this.zones;
                Intrinsics.checkNotNull(treeMap4);
                DataZone dataZone2 = treeMap4.get(str5);
                if (dataZone2 == null) {
                    dataZone2 = new DataZone(str5);
                    TreeMap<String, DataZone> treeMap5 = this.zones;
                    Intrinsics.checkNotNull(treeMap5);
                    treeMap5.put(str5, dataZone2);
                }
                if (dataZone2.updateForSnapshot(dataZone)) {
                    z7 = true;
                }
            }
        }
        return z7;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    @JvmOverloads
    public final boolean update(@Nullable String str, @Nullable DataAirconSystem dataAirconSystem, @Nullable DataManager dataManager, @Nullable String str2, boolean z7) {
        Set<String> emptySet;
        int intValue;
        if (str != null && dataManager != null) {
            dataManager.updateInfoPath(str, str2);
        }
        DataAirconInfo dataAirconInfo = this.info;
        Intrinsics.checkNotNull(dataAirconSystem);
        boolean update = dataAirconInfo.update(dataAirconSystem.info, dataManager, z7);
        TreeMap<String, DataZone> treeMap = dataAirconSystem.zones;
        if (treeMap == null || (emptySet = treeMap.keySet()) == null) {
            emptySet = SetsKt__SetsKt.emptySet();
        }
        for (String str3 : emptySet) {
            TreeMap<String, DataZone> treeMap2 = dataAirconSystem.zones;
            DataZone dataZone = treeMap2 != null ? treeMap2.get(str3) : null;
            if (str != null && dataManager != null) {
                dataManager.updateZonePath(str, str3, str2);
            }
            TreeMap<String, DataZone> treeMap3 = this.zones;
            DataZone dataZone2 = treeMap3 != null ? treeMap3.get(str3) : null;
            if (dataZone2 == null) {
                dataZone2 = new DataZone(str3);
                TreeMap<String, DataZone> treeMap4 = this.zones;
                if (treeMap4 != null) {
                    treeMap4.put(str3, dataZone2);
                }
            }
            if (dataZone2.update(dataZone, dataManager, null, z7)) {
                update = true;
            }
        }
        Integer num = this.info.noOfZones;
        TreeMap<String, DataZone> treeMap5 = this.zones;
        int size = treeMap5 != null ? treeMap5.size() : 0;
        if (num == null || size <= num.intValue() || (intValue = num.intValue() + 1) > size) {
            return update;
        }
        while (true) {
            String zoneKey = DataZone.Companion.getZoneKey(Integer.valueOf(intValue));
            TreeMap<String, DataZone> treeMap6 = this.zones;
            if (treeMap6 != null) {
                treeMap6.remove(zoneKey);
            }
            if (dataManager != null) {
                dataManager.updateZonePath(str, "", str2);
                dataManager.add(zoneKey, null);
            }
            if (intValue == size) {
                return true;
            }
            intValue++;
        }
    }

    public DataAirconSystem(@Nullable String str) {
        DataAirconInfo dataAirconInfo = new DataAirconInfo();
        this.info = dataAirconInfo;
        Gson create = new GsonBuilder().addSerializationExclusionStrategy(new ExportSerializationExclusionStrategy()).addDeserializationExclusionStrategy(new ExportDeserializationExclusionStrategy()).create();
        Intrinsics.checkNotNullExpressionValue(create, "create(...)");
        this.gsonForSendingExternally = create;
        Gson create2 = new GsonBuilder().addSerializationExclusionStrategy(new SaveThisSerializationExclusionStrategy()).addDeserializationExclusionStrategy(new SaveThisDeserializationExclusionStrategy()).create();
        Intrinsics.checkNotNullExpressionValue(create2, "create(...)");
        this.gsonForDB = create2;
        this.zones = new TreeMap<>();
        if (str != null) {
            dataAirconInfo.uid = str;
            return;
        }
        throw new NullPointerException("Warning creating a DataAircon with a null uid");
    }

    public DataAirconSystem(@Nullable String str, @Nullable Integer num, @Nullable Integer num2) {
        DataAirconInfo dataAirconInfo = new DataAirconInfo();
        this.info = dataAirconInfo;
        Gson create = new GsonBuilder().addSerializationExclusionStrategy(new ExportSerializationExclusionStrategy()).addDeserializationExclusionStrategy(new ExportDeserializationExclusionStrategy()).create();
        Intrinsics.checkNotNullExpressionValue(create, "create(...)");
        this.gsonForSendingExternally = create;
        Gson create2 = new GsonBuilder().addSerializationExclusionStrategy(new SaveThisSerializationExclusionStrategy()).addDeserializationExclusionStrategy(new SaveThisDeserializationExclusionStrategy()).create();
        Intrinsics.checkNotNullExpressionValue(create2, "create(...)");
        this.gsonForDB = create2;
        this.zones = new TreeMap<>();
        if (str != null) {
            dataAirconInfo.uid = str;
            dataAirconInfo.cbFWRevMajor = num;
            dataAirconInfo.cbFWRevMinor = num2;
            return;
        }
        throw new NullPointerException("Warning creating a DataAircon with a null uid");
    }
}