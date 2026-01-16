package com.air.advantage.aaservice.data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.air.advantage.config.MyPlaceConfig;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

/* compiled from: MyMasterData.java */
/* renamed from: com.air.advantage.aaservice.o.o */
/* loaded from: classes.dex */
@ApplicationScoped
public class MyMasterData {
    public static final MasterData masterData = new MasterData();

    @Inject
    MyPlaceConfig config;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private volatile ScheduledFuture<?> scheduledSave;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);

    public void onStart(@Observes StartupEvent event) {
        System.out.println("MyMasterData initialization started");
        System.out.println("Using config path: " + config.config().path());
        System.out.println("Using save delay: " + config.config().saveDelayMinutes() + " minutes");
        
        try {
            initializeDefaultSystem();
            loadConfig();
            System.out.println("MyMasterData initialization completed successfully");
        } catch (Exception e) {
            System.err.println("Error during MyMasterData initialization: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize MyMasterData", e);
        }
    }

    public synchronized MasterData getMasterData() {

        return masterData;
    }

    public synchronized void updateConfig(MasterData newData) {
        MyMasterData.masterData.copyFrom(newData);
        scheduleSave();
    }

    public synchronized void scheduleSave() {
        if (config.config().autoSave() == true && (scheduledSave == null || scheduledSave.isDone())) {
            scheduledSave = scheduler.schedule(this::saveConfig, config.config().saveDelayMinutes(), TimeUnit.MINUTES);
        }
    }

    private synchronized void saveConfig() {
        try (FileWriter writer = new FileWriter(config.config().path())) {
            // write only properties annotated for the SaveThis view
            objectMapper.writerWithView(JsonExporterViews.SaveThis.class)
                    .writeValue(writer, MyMasterData.masterData);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save config", e);
        }
    }

    public void loadConfig() {
        File file = new File(config.config().path());
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                MasterData loaded = objectMapper.readValue(reader, MasterData.class);
                if (loaded != null) {
                    MyMasterData.masterData.copyFrom(loaded);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to load config", e);
            }
        }
    }


    /**
     * Initialize system with default values
     */
    public void initializeDefaultSystem() {
        DataSystem system = MyMasterData.masterData.system;
        system.aaServiceRev = config.system().aaServiceRev();
        system.name = config.system().name();
        system.myAppRev = config.system().myAppRev();
        system.sysType = config.system().sysType();
        system.tspModel = config.system().tspModel();
        system.tspIp = config.system().tspIp();
        system.drawLightsTab = false;
        system.drawThingsTab = false;
        system.hasAircons = false;
        system.hasLights = false;
        system.hasLocks = false;
        system.hasSensors = false;
        system.hasThings = false;
        system.hasThingsBOG = false;
        system.hasThingsLight = false;
        system.needsUpdate = false;
        system.noOfAircons = 0;
        system.noOfSnapshots = 0;
        system.remoteAccessPairingEnabled = false;
        system.showMeasuredTemp = false;
        system.allTspErrorCodes = new java.util.HashMap<>();
    }

    public void shutdown() {
        if (scheduledSave != null && !scheduledSave.isDone()) {
            scheduledSave.cancel(false);
            saveConfig();
        }
        scheduler.shutdown();
    }
}