package com.air.advantage.aaservice.data;

/* compiled from: MyMasterData.java */
/* renamed from: com.air.advantage.aaservice.o.o */
/* loaded from: classes.dex */
public class MyMasterData {

    /* renamed from: b */
    private static MyMasterData INSTANCE;

    /* renamed from: a */
    private final MasterData masterData = new MasterData();

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private MyMasterData() {
        // HandlerAircon.getInstance().initializeMasterData(this.masterData);
        // LightDBStore lightDBStore = LightDBStore.getInstance();
        //this.masterData.myLights = lightDBStore.loadLightsData();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static MasterData getInstance() {
        if (INSTANCE == null) {
            synchronized (MyMasterData.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyMasterData();
                }
            }
        }
        if (Thread.holdsLock(MyMasterData.class)) {
            return INSTANCE.masterData;
        }
        throw new NullPointerException("You need to have synchronized (MyMasterData.class)");
    }
}