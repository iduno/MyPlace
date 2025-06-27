package com.air.advantage.data;

import kotlin.jvm.JvmField;
import org.jetbrains.annotations.NotNull;

/* renamed from: com.air.advantage.data.q0 */
/* loaded from: classes.dex */
public final class DataFavourites {

    @NotNull
    @JvmField
    public final LightScenes lightScenes = new LightScenes();

    @NotNull
    @JvmField
    public final LightStore lightStore = new LightStore();

    @NotNull
    @JvmField
    public final LightAlarms lightAlarms = new LightAlarms();

    @NotNull
    @JvmField
    public final LightFavourites lightFavourites = new LightFavourites();

    @NotNull
    @JvmField
    public final MonitorStore monitorStore = new MonitorStore();

    @NotNull
    @JvmField
    public final EditStore monitorEditStore = new EditStore();

    @NotNull
    @JvmField
    public final ThingStore thingStore = new ThingStore();

    @NotNull
    @JvmField
    public final SceneStore sceneStore = new SceneStore();

    @NotNull
    @JvmField
    public final MyPlaceFavourites myPlaceFavourites = new MyPlaceFavourites();

    @NotNull
    @JvmField
    public final DimOffsetSettingStore dimOffsetSettingStore = new DimOffsetSettingStore();

    public final void clearAllDataStores() {
        this.lightScenes.clear();
        this.lightStore.clear();
        this.lightAlarms.clear();
        this.thingStore.clear();
        this.dimOffsetSettingStore.clear();
    }
}