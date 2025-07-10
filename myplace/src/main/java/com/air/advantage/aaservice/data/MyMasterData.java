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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .addSerializationExclusionStrategy(new JsonExporterExclusionStrategy())
            .addDeserializationExclusionStrategy(new JsonExporterExclusionStrategy())
            .create();

    public void onStart(@Observes StartupEvent event) {
        System.out.println("MyMasterData initialization started");
        System.out.println("Using config path: " + config.config().path());
        System.out.println("Using save delay: " + config.config().saveDelayMinutes() + " minutes");
        
        try {
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
        this.masterData.copyFrom(newData);
        scheduleSave();
    }

    private synchronized void scheduleSave() {
        if (scheduledSave == null || scheduledSave.isDone()) {
            scheduledSave = scheduler.schedule(this::saveConfig, config.config().saveDelayMinutes(), TimeUnit.MINUTES);
        }
    }

    private synchronized void saveConfig() {
        try (FileWriter writer = new FileWriter(config.config().path())) {
            gson.toJson(this.masterData, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save config", e);
        }
    }

    public void loadConfig() {
        File file = new File(config.config().path());
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                MasterData loaded = gson.fromJson(reader, MasterData.class);
                if (loaded != null) {
                    this.masterData.copyFrom(loaded);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to load config", e);
            }
        }
    }

    public void shutdown() {
        if (scheduledSave != null && !scheduledSave.isDone()) {
            scheduledSave.cancel(false);
            saveConfig();
        }
        scheduler.shutdown();
    }
}