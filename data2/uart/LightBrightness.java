package com.air.advantage.uart;

import org.jetbrains.annotations.NotNull;

/* renamed from: com.air.advantage.uart.v */
/* loaded from: classes.dex */
public final class LightBrightness {

    /* renamed from: a */
    @NotNull
    public static final LightBrightness instance = new LightBrightness();

    /* renamed from: b */
    @NotNull
    private static final double[] brightnessLevels = {0.0d, 0.1d, 0.2d, 0.3d, 0.8d, 1.6d, 2.7d, 4.3d, 6.4d, 9.1d, 12.5d, 16.6d, 21.6d, 27.5d, 34.3d, 42.2d, 51.2d, 61.4d, 72.9d, 85.7d, 100.0d};

    private LightBrightness() {
    }

    /* renamed from: a */
    public final int getBrightnessLevelIndex(double d3) {
        int length = brightnessLevels.length;
        for (int i10 = 0; i10 < length; i10++) {
            if (d3 == brightnessLevels[i10]) {
                return i10 * 5;
            }
        }
        return 5;
    }

    /* renamed from: b */
    public final double getBrightnessLevel(int i10) {
        return brightnessLevels[i10 / 5];
    }
}