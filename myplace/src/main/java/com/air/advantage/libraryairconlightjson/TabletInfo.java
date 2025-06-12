package com.air.advantage.libraryairconlightjson;

import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: TabletInfo.java */
/* renamed from: b.a.a.a.i */
/* loaded from: classes.dex */
public class TabletInfo {

    /* renamed from: a */
    public static AtomicBoolean debugMode = new AtomicBoolean(false);

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: a */
    private static String getTabletModel() {
        return "MyAir5";
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: b */
    public static boolean isPic7SeriesTablet() {
        return getTabletModel().equals("PIC7GS8") || getTabletModel().equals("PIC7GS10") || getTabletModel().equals("PIC7GS10-A") || getTabletModel().equals("MJY7012") || getTabletModel().equals("tb8167p3_64_bsp");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: c */
    public static boolean isEZoneTablet() {
        return getTabletModel().contains("eZone") || getTabletModel().contains("e-zone") || getTabletModel().equals("PIC7KS-EZ") || getTabletModel().equals("PIC7KS6") || getTabletModel().equals("PIC7KS6-EZ");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: d */
    public static boolean isMyAir4Tablet() {
        return getTabletModel().contains("MyAir4") || getTabletModel().equals("PIC7KS-MY4") || getTabletModel().equals("PIC7KS6-MY4");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: e */
    public static boolean isMyAir5OrTspTablet() {
        return debugMode.get() || getTabletModel().equals("MyAir5") || getTabletModel().equals("PIC8KS-MY5") || getTabletModel().equals("PIC8KS-TSP6") || getTabletModel().equals("PIC8KS-TSP7") || getTabletModel().equals("PIC8KS6-TSP7") || getTabletModel().equals("PIC8GS7-TSP7") || getTabletModel().equals("PIC10GS7-TSP7") || getTabletModel().equals("PIC8GS7-TSP8") || getTabletModel().equals("PIC10GS7-TSP8");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: f */
    public static boolean isVamsTablet() {
        return getTabletModel().equals("PIC7KS-VAMS") || getTabletModel().equals("PIC7KS6-VAMS");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: g */
    public static boolean isZone10ETablet() {
        return getTabletModel().equals("zone10e") || getTabletModel().equals("PIC7KS-Z10E") || getTabletModel().equals("PIC7KS6-Z10E");
    }
}