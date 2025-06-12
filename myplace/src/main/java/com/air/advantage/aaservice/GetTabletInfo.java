package com.air.advantage.aaservice;

import com.air.advantage.libraryairconlightjson.TabletInfo;

/* compiled from: GetTabletInfo.java */
/* renamed from: com.air.advantage.aaservice.f */
/* loaded from: classes.dex */
class GetTabletInfo {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* renamed from: a */
    static boolean isSupportedTablet() {
        return TabletInfo.isEZoneTablet() || TabletInfo.isMyAir4Tablet() || TabletInfo.isMyAir5OrTspTablet() || TabletInfo.isZone10ETablet() || TabletInfo.isVamsTablet() || TabletInfo.isPic7SeriesTablet();
    }
}