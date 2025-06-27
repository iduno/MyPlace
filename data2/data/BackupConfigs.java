package com.air.advantage.data;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.g */
/* loaded from: classes.dex */
public final class BackupConfigs implements Comparable<BackupConfigs> {

    @Exclude
    @Nullable
    @JvmField
    public transient String backupId;

    @Nullable
    @SerializedName("hardwareConfig")
    @JvmField
    public DataHardwareConfig hardwareConfig;

    @Nullable
    @SerializedName("lastUpdated")
    @JvmField
    public Long lastUpdated;

    @Exclude
    @Nullable
    @JvmField
    public transient String masterDataBackupJsonString;

    @Nullable
    @SerializedName("preferenceName")
    @JvmField
    public String preferenceName;

    @Nullable
    @SerializedName("summary")
    @JvmField
    public String summary;

    @Nullable
    @SerializedName("systemName")
    @JvmField
    public String systemName;

    @NotNull
    public final BackupConfigs copy() {
        BackupConfigs backupConfigs = new BackupConfigs();
        DataHardwareConfig dataHardwareConfig = this.hardwareConfig;
        Intrinsics.checkNotNull(dataHardwareConfig);
        backupConfigs.hardwareConfig = dataHardwareConfig.copy();
        backupConfigs.lastUpdated = this.lastUpdated;
        backupConfigs.systemName = this.systemName;
        backupConfigs.backupId = this.backupId;
        backupConfigs.summary = this.summary;
        backupConfigs.preferenceName = this.preferenceName;
        backupConfigs.masterDataBackupJsonString = this.masterDataBackupJsonString;
        return backupConfigs;
    }

    public final void updateSummary(@NotNull DataMaster sourceMasterdata) {
        Intrinsics.checkNotNullParameter(sourceMasterdata, "sourceMasterdata");
        StringBuilder sb = new StringBuilder();
        if (sourceMasterdata.aircons.size() == 1) {
            sb.append("1 Aircon");
        } else if (sourceMasterdata.aircons.size() > 1) {
            sb.append(sourceMasterdata.aircons.size());
            sb.append(" Aircons");
        }
        HashMap<String, DataModuleInfoSource> hashMap = sourceMasterdata.system.versions;
        if (hashMap != null) {
            Intrinsics.checkNotNull(hashMap);
            if (hashMap.size() > 0 && sourceMasterdata.aircons.size() > 0) {
                sb.append(" - ");
            }
            HashMap hashMap2 = new HashMap();
            HashMap<String, DataModuleInfoSource> hashMap3 = sourceMasterdata.system.versions;
            Intrinsics.checkNotNull(hashMap3);
            for (DataModuleInfoSource dataModuleInfoSource : hashMap3.values()) {
                Intrinsics.checkNotNull(dataModuleInfoSource);
                String str = dataModuleInfoSource.moduleType;
                if (str != null) {
                    Integer num = (Integer) hashMap2.get(str);
                    if (num == null) {
                        hashMap2.put(dataModuleInfoSource.moduleType, 1);
                    } else {
                        hashMap2.put(dataModuleInfoSource.moduleType, Integer.valueOf(num.intValue() + 1));
                    }
                }
            }
            Iterator it = hashMap2.keySet().iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                Integer num2 = (Integer) hashMap2.get(str2);
                if (num2 != null) {
                    sb.append(num2.intValue());
                    sb.append(" ");
                    sb.append(str2);
                    if (num2.intValue() > 1) {
                        sb.append("s");
                    }
                }
                if (it.hasNext()) {
                    sb.append(" - ");
                }
            }
        }
        this.summary = sb.toString();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    /* JADX DEBUG: Method merged with bridge method: compareTo(Ljava/lang/Object;)I */
    @Override // java.lang.Comparable
    public int compareTo(@NotNull BackupConfigs dataBackup) {
        Intrinsics.checkNotNullParameter(dataBackup, "dataBackup");
        Long l8 = this.lastUpdated;
        Intrinsics.checkNotNull(l8);
        long longValue = l8.longValue();
        Long l10 = dataBackup.lastUpdated;
        Intrinsics.checkNotNull(l10);
        if (longValue > l10.longValue()) {
            return -1;
        }
        Long l11 = this.lastUpdated;
        Intrinsics.checkNotNull(l11);
        long longValue2 = l11.longValue();
        Long l12 = dataBackup.lastUpdated;
        Intrinsics.checkNotNull(l12);
        return longValue2 < l12.longValue() ? 1 : 0;
    }
}