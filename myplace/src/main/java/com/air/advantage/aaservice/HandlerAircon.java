package com.air.advantage.aaservice;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.air.advantage.aaservice.data.BackupDataFunctions;
import com.air.advantage.aaservice.data.DataSystem;
import com.air.advantage.aaservice.data.MasterData;
import com.air.advantage.aaservice.data.MyMasterData;

/* compiled from: HandlerAircon.java */
/* renamed from: com.air.advantage.aaservice.g */
/* loaded from: classes.dex */
public class HandlerAircon extends HandlerBase {

    private static final Logger LOGGER = Logger.getLogger(HandlerAircon.class.getName());
    private static HandlerAircon instance;

    private String defaultCommand = "";

    private HandlerAircon() {
        LOGGER.log(Level.INFO, "Creating HandlerAircon");
    }

    /**
     * Returns the singleton instance of HandlerAircon.
     *
     * @return The singleton instance.
     */
    public static HandlerAircon getInstance() {
        if (instance == null) {
            synchronized (HandlerAircon.class) {
                if (instance == null) {
                    instance = new HandlerAircon();
                }
            }
        }
        return instance;
    }

    /**
     * Returns the initialization command.
     *
     * @return The initialization command string.
     */
    String getInitCommand() {
        return "0701000000600000000000000";
    }

    /**
     * Initializes the master data.
     *
     * @param context    The application context.
     * @param masterData The master data to initialize.
     */
    public void initializeMasterData(MasterData masterData) {
        //AirconDBStore airconDBStore = AirconDBStore.getInstance();
        DataSystem dataSystem = null; //airconDBStore.getDataSystem();
        if (dataSystem != null) {
            masterData.system.updateSystem(dataSystem);
        } else {
            masterData.system = new DataSystem("Aircon");
        }
        masterData.system.myAppRev = "14.116";
        masterData.system.sysType = "";
        setMasterData(masterData);
        updateSystemType(masterData);
        //airconDBStore.loadSnapshots(masterData);
    }

    /**
     * Updates the system type in the master data.
     *
     * @param context    The application context.
     * @param masterData The master data to update.
     */
    private void updateSystemType(MasterData masterData) {
        //AirconDBStore.getInstance().loadAircons(context, masterData);
        //masterData.myLights = LightDBStore.getInstance().loadLightsData(context);
    }

    /**
     * Sets the master data with default values if necessary.
     *
     * @param masterData The master data to set.
     */
    private void setMasterData(MasterData masterData) {
        String systemName = masterData.system.name;
        if (systemName == null || systemName.isEmpty()) {
            masterData.system.name = "Aircon";
        }
    }

    /**
     * Resets the system and updates the default command.
     *
     * @param context The application context.
     */
    void resetSystem() {
        synchronized (MyMasterData.class) {
            this.defaultCommand = new BackupDataFunctions().toJson(MyMasterData.getInstance());
        }
    }
}