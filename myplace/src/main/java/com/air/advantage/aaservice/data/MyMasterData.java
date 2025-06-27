package com.air.advantage.aaservice.data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

/* compiled from: MyMasterData.java */
/* renamed from: com.air.advantage.aaservice.o.o */
/* loaded from: classes.dex */
@ApplicationScoped
public class MyMasterData {
    public final MasterData masterData = new MasterData();

    @ConfigProperty(name = "myplace.config.path", defaultValue = "/data/config.json")
    String configPath;

    @ConfigProperty(name = "myplace.save.delay.minutes", defaultValue = "5")
    long saveDelayMinutes;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private volatile ScheduledFuture<?> scheduledSave;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .addSerializationExclusionStrategy(new JsonExporterExclusionStrategy())
            .addDeserializationExclusionStrategy(new JsonExporterExclusionStrategy())
            .create();

    @PostConstruct
    void init() {
        loadConfig();
    }

    public synchronized void updateConfig(MasterData newData) {
        this.masterData.copyFrom(newData);
        scheduleSave();
    }

    private synchronized void scheduleSave() {
        if (scheduledSave == null || scheduledSave.isDone()) {
            scheduledSave = scheduler.schedule(this::saveConfig, saveDelayMinutes, TimeUnit.MINUTES);
        }
    }

    private synchronized void saveConfig() {
        try (FileWriter writer = new FileWriter(configPath)) {
            gson.toJson(this.masterData, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save config", e);
        }
    }

    public void loadConfig() {
        File file = new File(configPath);
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