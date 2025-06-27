package com.air.advantage.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.air.advantage.data.DataAirconSystem;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.t0 */
/* loaded from: classes.dex */
public final class DataCommissioning implements Parcelable {
    private static final int MAX_CONSTANT_ZONES = 3;

    @Nullable
    @JvmField
    public String activationCode1;

    @Nullable
    @JvmField
    public String activationCode2;

    @NotNull
    @JvmField
    public DataAirconSystem.CodeStatus activationCodeStatus;

    @NotNull
    @JvmField
    public final Integer[] constantZones;

    @JvmField
    public boolean constantZonesLowestFirst;

    @JvmField
    public int dateDOW;

    @JvmField
    public int dateDay;

    @JvmField
    public int dateMonth;

    @JvmField
    public int dateYear;

    @Nullable
    @JvmField
    public String dealerLogoNumber;

    @Nullable
    @JvmField
    public String dealerPhoneNumber;

    @JvmField
    public boolean demoMode;

    @JvmField
    public boolean devMode;

    @NotNull
    @JvmField
    public DataAirconSystem.FreshAirStatus freshAirState;

    @JvmField
    public int hours;

    @Nullable
    @JvmField
    public String ipAddress;

    @Nullable
    @JvmField
    public String macAddress;

    @JvmField
    public int minutes;

    @JvmField
    public int myZoneNumber;

    @JvmField
    public int numConstantZonesWanted;

    @JvmField
    public int numZonesWanted;

    @NotNull
    @JvmField
    public OperationType operationType;

    @Nullable
    private String port;

    @NotNull
    @JvmField
    public final Integer[] rfStrength;

    @JvmField
    public boolean showMeasuredTemp;

    @JvmField
    public int systemRFID;

    @Nullable
    @JvmField
    public String systemType;

    @NotNull
    @JvmField
    public final Integer[] zoneFollowing;

    @NotNull
    @JvmField
    public final Integer[] zoneMaxDamper;

    @NotNull
    @JvmField
    public final Integer[] zoneMinDamper;

    @NotNull
    @JvmField
    public String[] zoneNames;

    @JvmField
    public boolean zoneNamesLowestFirst;

    @NotNull
    @JvmField
    public final Integer[] zoneSensorType;

    @NotNull
    @JvmField
    public final String[] zoneSensorWarning;

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    @JvmField
    public static final Parcelable.Creator<DataCommissioning> CREATOR = new Creator();

    /* renamed from: com.air.advantage.data.t0$a */
    public static final class Creator implements Parcelable.Creator<DataCommissioning> {
        Creator() {
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public DataCommissioning createFromParcel(@NotNull Parcel in) {
            Intrinsics.checkNotNullParameter(in, "in");
            return new DataCommissioning(in);
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public DataCommissioning[] newArray(int i10) {
            DataCommissioning[] dataCommissioningArr = new DataCommissioning[i10];
            for (int i11 = 0; i11 < i10; i11++) {
                dataCommissioningArr[i11] = new DataCommissioning();
            }
            return dataCommissioningArr;
        }
    }

    /* renamed from: com.air.advantage.data.t0$b */
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.t0.b.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* renamed from: com.air.advantage.data.t0$c */
    public static final class OperationType {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ OperationType[] $VALUES;
        public static final OperationType TS_WIZARD = new OperationType("TS_WIZARD", 0);
        public static final OperationType TS_ACTIVATION = new OperationType("TS_ACTIVATION", 1);
        public static final OperationType TS_ZONE_BALANCING = new OperationType("TS_ZONE_BALANCING", 2);
        public static final OperationType TS_FRESHAIR_STATE = new OperationType("TS_FRESHAIR_STATE", 3);
        public static final OperationType TS_ACTUAL_TEMPERATURE_STATE = new OperationType("TS_ACTUAL_TEMPERATURE_STATE", 4);

        private static final /* synthetic */ OperationType[] $values() {
            return new OperationType[]{TS_WIZARD, TS_ACTIVATION, TS_ZONE_BALANCING, TS_FRESHAIR_STATE, TS_ACTUAL_TEMPERATURE_STATE};
        }

        static {
            OperationType[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private OperationType(String str, int i10) {
        }

        @NotNull
        public static EnumEntries<OperationType> getEntries() {
            return $ENTRIES;
        }

        public static OperationType valueOf(String str) {
            return (OperationType) Enum.valueOf(OperationType.class, str);
        }

        public static OperationType[] values() {
            return (OperationType[]) $VALUES.clone();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public DataCommissioning(@NotNull Parcel in) {
        Intrinsics.checkNotNullParameter(in, "in");
        this.constantZones = new Integer[4];
        this.zoneMinDamper = new Integer[11];
        this.zoneMaxDamper = new Integer[11];
        this.zoneSensorWarning = new String[11];
        this.rfStrength = new Integer[11];
        this.zoneSensorType = new Integer[11];
        this.zoneFollowing = new Integer[11];
        this.zoneNames = new String[11];
        this.ipAddress = in.readString();
        this.port = in.readString();
        this.macAddress = in.readString();
        this.operationType = OperationType.values()[in.readInt()];
        this.numZonesWanted = in.readInt();
        this.numConstantZonesWanted = in.readInt();
        this.dealerPhoneNumber = in.readString();
        this.dealerLogoNumber = in.readString();
        for (int i10 = 1; i10 < 11; i10++) {
            this.zoneNames[i10] = in.readString();
        }
        for (int i11 = 1; i11 < 4; i11++) {
            this.constantZones[i11] = Integer.valueOf(in.readInt());
        }
        this.zoneNamesLowestFirst = in.readByte() != 0;
        this.constantZonesLowestFirst = in.readByte() != 0;
        String readString = in.readString();
        Intrinsics.checkNotNull(readString);
        this.activationCodeStatus = DataAirconSystem.CodeStatus.valueOf(readString);
        for (int i12 = 1; i12 < 11; i12++) {
            this.zoneMinDamper[i12] = Integer.valueOf(in.readInt());
        }
        for (int i13 = 1; i13 < 11; i13++) {
            this.zoneMaxDamper[i13] = Integer.valueOf(in.readInt());
        }
        for (int i14 = 1; i14 < 11; i14++) {
            this.zoneSensorWarning[i14] = in.readString();
        }
        for (int i15 = 1; i15 < 11; i15++) {
            this.rfStrength[i15] = Integer.valueOf(in.readInt());
        }
        for (int i16 = 1; i16 < 11; i16++) {
            this.zoneSensorType[i16] = Integer.valueOf(in.readInt());
        }
        for (int i17 = 1; i17 < 11; i17++) {
            this.zoneFollowing[i17] = Integer.valueOf(in.readInt());
        }
        this.systemRFID = in.readInt();
        this.dateDOW = in.readInt();
        this.dateDay = in.readInt();
        this.dateMonth = in.readInt();
        this.dateYear = in.readInt();
        this.hours = in.readInt();
        this.minutes = in.readInt();
        this.activationCode1 = in.readString();
        this.activationCode2 = in.readString();
        this.devMode = in.readByte() != 0;
        this.demoMode = in.readByte() != 0;
        this.myZoneNumber = in.readInt();
        this.systemType = in.readString();
        String readString2 = in.readString();
        Intrinsics.checkNotNull(readString2);
        this.freshAirState = DataAirconSystem.FreshAirStatus.valueOf(readString2);
        this.showMeasuredTemp = in.readByte() != 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Nullable
    public final String getPort() {
        return this.port;
    }

    public final void setPort(@Nullable String str) {
        this.port = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NotNull Parcel out, int i10) {
        Intrinsics.checkNotNullParameter(out, "out");
        out.writeString(this.ipAddress);
        out.writeString(this.port);
        out.writeString(this.macAddress);
        out.writeInt(this.operationType.ordinal());
        out.writeInt(this.numZonesWanted);
        out.writeInt(this.numConstantZonesWanted);
        out.writeString(this.dealerPhoneNumber);
        out.writeString(this.dealerLogoNumber);
        for (int i11 = 1; i11 < 11; i11++) {
            out.writeString(this.zoneNames[i11]);
        }
        for (int i12 = 1; i12 < 4; i12++) {
            Integer num = this.constantZones[i12];
            Intrinsics.checkNotNull(num);
            out.writeInt(num.intValue());
        }
        out.writeByte(this.zoneNamesLowestFirst ? (byte) 1 : (byte) 0);
        out.writeByte(this.constantZonesLowestFirst ? (byte) 1 : (byte) 0);
        out.writeString(this.activationCodeStatus.toString());
        for (int i13 = 1; i13 < 11; i13++) {
            Integer num2 = this.zoneMinDamper[i13];
            Intrinsics.checkNotNull(num2);
            out.writeInt(num2.intValue());
        }
        for (int i14 = 1; i14 < 11; i14++) {
            Integer num3 = this.zoneMaxDamper[i14];
            Intrinsics.checkNotNull(num3);
            out.writeInt(num3.intValue());
        }
        for (int i15 = 1; i15 < 11; i15++) {
            out.writeString(this.zoneSensorWarning[i15]);
        }
        for (int i16 = 1; i16 < 11; i16++) {
            Integer num4 = this.rfStrength[i16];
            Intrinsics.checkNotNull(num4);
            out.writeInt(num4.intValue());
        }
        for (int i17 = 1; i17 < 11; i17++) {
            Integer num5 = this.zoneSensorType[i17];
            Intrinsics.checkNotNull(num5);
            out.writeInt(num5.intValue());
        }
        for (int i18 = 1; i18 < 11; i18++) {
            Integer num6 = this.zoneFollowing[i18];
            Intrinsics.checkNotNull(num6);
            out.writeInt(num6.intValue());
        }
        out.writeInt(this.systemRFID);
        out.writeInt(this.dateDOW);
        out.writeInt(this.dateDay);
        out.writeInt(this.dateMonth);
        out.writeInt(this.dateYear);
        out.writeInt(this.hours);
        out.writeInt(this.minutes);
        out.writeString(this.activationCode1);
        out.writeString(this.activationCode2);
        out.writeByte(this.devMode ? (byte) 1 : (byte) 0);
        out.writeByte(this.demoMode ? (byte) 1 : (byte) 0);
        out.writeInt(this.myZoneNumber);
        out.writeString(this.systemType);
        out.writeString(this.freshAirState.toString());
        out.writeByte(this.showMeasuredTemp ? (byte) 1 : (byte) 0);
    }

    public DataCommissioning() {
        this.constantZones = new Integer[4];
        this.zoneMinDamper = new Integer[11];
        this.zoneMaxDamper = new Integer[11];
        this.zoneSensorWarning = new String[11];
        this.rfStrength = new Integer[11];
        this.zoneSensorType = new Integer[11];
        this.zoneFollowing = new Integer[11];
        this.zoneNames = new String[11];
        this.ipAddress = "";
        this.port = "";
        this.macAddress = "";
        this.operationType = OperationType.TS_WIZARD;
        this.numZonesWanted = 10;
        this.numConstantZonesWanted = 0;
        this.dealerPhoneNumber = "";
        this.dealerLogoNumber = "1234";
        this.zoneNames = new String[11];
        for (int i10 = 1; i10 < 11; i10++) {
            this.zoneNames[i10] = null;
        }
        for (int i11 = 1; i11 < 4; i11++) {
            this.constantZones[i11] = 0;
        }
        this.zoneNamesLowestFirst = true;
        this.constantZonesLowestFirst = true;
        this.activationCodeStatus = DataAirconSystem.CodeStatus.noCode;
        for (int i12 = 1; i12 < 11; i12++) {
            this.zoneMinDamper[i12] = 0;
        }
        for (int i13 = 1; i13 < 11; i13++) {
            this.zoneMaxDamper[i13] = 100;
        }
        for (int i14 = 1; i14 < 11; i14++) {
            this.zoneSensorWarning[i14] = "";
        }
        for (int i15 = 1; i15 < 11; i15++) {
            this.rfStrength[i15] = 0;
        }
        for (int i16 = 1; i16 < 11; i16++) {
            this.zoneSensorType[i16] = 0;
        }
        for (int i17 = 1; i17 < 11; i17++) {
            this.zoneFollowing[i17] = 0;
        }
        this.systemRFID = 0;
        this.dateDOW = 1;
        this.dateDay = 1;
        this.dateMonth = 1;
        this.dateYear = 2014;
        this.hours = 13;
        this.minutes = 5;
        this.activationCode1 = "";
        this.activationCode2 = "";
        this.devMode = false;
        this.demoMode = false;
        this.myZoneNumber = 0;
        this.systemType = "";
        this.freshAirState = DataAirconSystem.FreshAirStatus.none;
        this.showMeasuredTemp = false;
    }
}