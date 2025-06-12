package com.air.advantage.libraryairconlightjson.backup;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.TreeMap;

/* compiled from: BackupDataV1.java */
/* renamed from: b.a.a.a.k.b */
/* loaded from: classes.dex */
public class BackupDataV1 {

    /* renamed from: a */
    @SerializedName("airconBackup")
    public TreeMap<String, BackupAirconV1> airconBackup;

    /* renamed from: b */
    @SerializedName("plansBackup")
    public TreeMap<String, BackupSnapshotSceneV1> plansBackup;

    /* renamed from: c */
    @SerializedName("groupsBackup")
    public ArrayList<BackupLightV1> groupsBackup;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public BackupDataV1() {
        new TreeMap();
        new ArrayList();
    }
}