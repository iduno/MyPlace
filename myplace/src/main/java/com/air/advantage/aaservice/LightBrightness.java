package com.air.advantage.aaservice;

/* compiled from: LightBrightness.java */
/* renamed from: com.air.advantage.aaservice.k */
/* loaded from: classes.dex */
class LightBrightness {

    /* renamed from: a */
    private static final double[] brightnessLevels = {0.0d, 0.1d, 0.2d, 0.3d, 0.8d, 1.6d, 2.7d, 4.3d, 6.4d, 9.1d, 12.5d, 16.6d, 21.6d, 27.5d, 34.3d, 42.2d, 51.2d, 61.4d, 72.9d, 85.7d, 100.0d};

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: a */
    static int getBrightnessLevel(double brightness) {
        int i = 0;
        while (true) {
            double[] levels = brightnessLevels;
            if (i >= levels.length) {
                return 5;
            }
            if (brightness == levels[i]) {
                return i * 5;
            }
            i++;
        }
    }
}