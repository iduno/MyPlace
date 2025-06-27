package com.air.advantage.data;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.h */
/* loaded from: classes.dex */
public final class Backup {

    @Nullable
    @SerializedName("backupConfigs")
    @JvmField
    public HashMap<String, BackupConfigs> backupConfigs = new HashMap<>();

    public final void add(@Nullable BackupConfigs backupConfigs) {
        if (this.backupConfigs == null) {
            this.backupConfigs = new HashMap<>();
        }
        HashMap<String, BackupConfigs> hashMap = this.backupConfigs;
        Intrinsics.checkNotNull(hashMap);
        Intrinsics.checkNotNull(backupConfigs);
        DataHardwareConfig dataHardwareConfig = backupConfigs.hardwareConfig;
        Intrinsics.checkNotNull(dataHardwareConfig);
        hashMap.put(dataHardwareConfig.generateHardwareId(), backupConfigs);
    }

    @NotNull
    public final Backup copy() {
        Backup backup = new Backup();
        HashMap<String, BackupConfigs> hashMap = this.backupConfigs;
        if (hashMap != null) {
            Intrinsics.checkNotNull(hashMap);
            for (String str : hashMap.keySet()) {
                HashMap<String, BackupConfigs> hashMap2 = this.backupConfigs;
                Intrinsics.checkNotNull(hashMap2);
                BackupConfigs backupConfigs = hashMap2.get(str);
                if (backupConfigs != null) {
                    backup.add(backupConfigs.copy());
                }
            }
        }
        return backup;
    }
}