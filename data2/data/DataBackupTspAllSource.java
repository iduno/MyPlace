package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.i */
/* loaded from: classes.dex */
public final class DataBackupTspAllSource {

    @Nullable
    @SerializedName("backups")
    @JvmField
    public HashMap<String, Backup> backups = new HashMap<>();

    public final void add(@NotNull String backupId, @Nullable Backup backup) {
        Intrinsics.checkNotNullParameter(backupId, "backupId");
        if (this.backups == null) {
            this.backups = new HashMap<>();
        }
        HashMap<String, Backup> hashMap = this.backups;
        Intrinsics.checkNotNull(hashMap);
        hashMap.put(backupId, backup);
    }

    @NotNull
    public final DataBackupTspAllSource copy() {
        DataBackupTspAllSource dataBackupTspAllSource = new DataBackupTspAllSource();
        HashMap<String, Backup> hashMap = this.backups;
        if (hashMap != null) {
            Intrinsics.checkNotNull(hashMap);
            for (String str : hashMap.keySet()) {
                HashMap<String, Backup> hashMap2 = this.backups;
                Intrinsics.checkNotNull(hashMap2);
                Backup backup = hashMap2.get(str);
                if (backup != null) {
                    Intrinsics.checkNotNull(str);
                    dataBackupTspAllSource.add(str, backup.copy());
                }
            }
        }
        return dataBackupTspAllSource;
    }

    public final void populateBackupId() {
        HashMap<String, BackupConfigs> hashMap;
        HashMap<String, Backup> hashMap2 = this.backups;
        if (hashMap2 != null) {
            Intrinsics.checkNotNull(hashMap2);
            for (String str : hashMap2.keySet()) {
                HashMap<String, Backup> hashMap3 = this.backups;
                Intrinsics.checkNotNull(hashMap3);
                Backup backup = hashMap3.get(str);
                if (backup != null && (hashMap = backup.backupConfigs) != null) {
                    Intrinsics.checkNotNull(hashMap);
                    for (BackupConfigs backupConfigs : hashMap.values()) {
                        Intrinsics.checkNotNull(backupConfigs);
                        backupConfigs.backupId = str;
                    }
                }
            }
        }
    }

    public final void set(@NotNull DataBackupTspAllSource dataBackupTspAllSource) {
        Intrinsics.checkNotNullParameter(dataBackupTspAllSource, "dataBackupTspAllSource");
        HashMap<String, Backup> hashMap = this.backups;
        Intrinsics.checkNotNull(hashMap);
        hashMap.clear();
        HashMap<String, Backup> hashMap2 = dataBackupTspAllSource.backups;
        Intrinsics.checkNotNull(hashMap2);
        for (String str : hashMap2.keySet()) {
            HashMap<String, Backup> hashMap3 = dataBackupTspAllSource.backups;
            Intrinsics.checkNotNull(hashMap3);
            Backup backup = hashMap3.get(str);
            Intrinsics.checkNotNull(str);
            Intrinsics.checkNotNull(backup);
            add(str, backup.copy());
        }
    }
}