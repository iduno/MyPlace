package com.air.advantage.aaservice.data;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/* compiled from: DataSystem.java */
/* renamed from: com.air.advantage.aaservice.o.k */
/* loaded from: classes.dex */
public class DataSystem {

    /* renamed from: a */
    @SerializedName("dealerPhoneNumber")
    public String dealerPhoneNumber;

    /* renamed from: b */
    @SerializedName("hasAircons")
    public Boolean hasAircons;

    /* renamed from: c */
    @SerializedName("hasLights")
    public Boolean hasLights;

    /* renamed from: d */
    @SerializedName("needsUpdate")
    public Boolean needsUpdate;

    /* renamed from: e */
    @SerializedName("logoPIN")
    public String logoPIN;

    /* renamed from: f */
    @SerializedName("mid")
    public String mid;

    /* renamed from: g */
    @SerializedName("myAppRev")
    public String myAppRev;

    /* renamed from: h */
    @SerializedName("name")
    public String name;

    /* renamed from: i */
    @SerializedName("noOfAircons")
    public Integer noOfAircons;

    /* renamed from: j */
    @SerializedName("noOfSnapshots")
    public Integer noOfSnapshots;

    /* renamed from: k */
    @SerializedName("rid")
    public String rid;

    /* renamed from: l */
    @SerializedName("sysType")
    public String sysType;

    /* renamed from: m */
    @SerializedName("deviceIds")
    public ArrayList<String> deviceIds;

    /* renamed from: n */
    @SerializedName("postCode")
    public String postCode;

    /* renamed from: o */
    @SerializedName("country")
    public String country;

    /* renamed from: p */
    @SerializedName("latitude")
    public Double latitude;

    /* renamed from: q */
    @SerializedName("longitude")
    public Double longitude;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    DataSystem() {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean updateSystem(DataSystem dataSystem) {
        boolean changed = false;

        if (dataSystem.dealerPhoneNumber != null && !dataSystem.dealerPhoneNumber.equals(this.dealerPhoneNumber)) {
            this.dealerPhoneNumber = dataSystem.dealerPhoneNumber;
            changed = true;
        }
        if (dataSystem.hasAircons != null && !dataSystem.hasAircons.equals(this.hasAircons)) {
            this.hasAircons = dataSystem.hasAircons;
            changed = true;
        }
        if (dataSystem.hasLights != null && !dataSystem.hasLights.equals(this.hasLights)) {
            this.hasLights = dataSystem.hasLights;
            changed = true;
        }
        if (dataSystem.logoPIN != null && !dataSystem.logoPIN.equals(this.logoPIN)) {
            this.logoPIN = dataSystem.logoPIN;
            changed = true;
        }
        if (dataSystem.mid != null && !dataSystem.mid.equals(this.mid)) {
            this.mid = dataSystem.mid;
            changed = true;
        }
        if (dataSystem.myAppRev != null && !dataSystem.myAppRev.equals(this.myAppRev)) {
            this.myAppRev = dataSystem.myAppRev;
            changed = true;
        }
        if (dataSystem.name != null && !dataSystem.name.equals(this.name)) {
            this.name = dataSystem.name;
            changed = true;
        }
        if (dataSystem.needsUpdate != null && !dataSystem.needsUpdate.equals(this.needsUpdate)) {
            this.needsUpdate = dataSystem.needsUpdate;
            changed = true;
        }
        if (dataSystem.noOfAircons != null && !dataSystem.noOfAircons.equals(this.noOfAircons)) {
            this.noOfAircons = dataSystem.noOfAircons;
            changed = true;
        }
        if (dataSystem.noOfSnapshots != null && !dataSystem.noOfSnapshots.equals(this.noOfSnapshots)) {
            this.noOfSnapshots = dataSystem.noOfSnapshots;
            changed = true;
        }
        if (dataSystem.rid != null && !dataSystem.rid.equals(this.rid)) {
            this.rid = dataSystem.rid;
            changed = true;
        }
        if (dataSystem.sysType != null && !dataSystem.sysType.equals(this.sysType)) {
            this.sysType = dataSystem.sysType;
            changed = true;
        }
        if (dataSystem.deviceIds != null) {
            if (this.deviceIds == null || !this.deviceIds.equals(dataSystem.deviceIds)) {
                this.deviceIds = new ArrayList<>(dataSystem.deviceIds);
                changed = true;
            }
        }
        if (dataSystem.postCode != null && !dataSystem.postCode.equals(this.postCode)) {
            this.postCode = dataSystem.postCode;
            changed = true;
        }
        if (dataSystem.country != null && !dataSystem.country.equals(this.country)) {
            this.country = dataSystem.country;
            changed = true;
        }
        if (dataSystem.latitude != null && !dataSystem.latitude.equals(this.latitude)) {
            this.latitude = dataSystem.latitude;
            changed = true;
        }
        if (dataSystem.longitude != null && !dataSystem.longitude.equals(this.longitude)) {
            this.longitude = dataSystem.longitude;
            changed = true;
        }

        return changed;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public DataSystem(String str) {
        this.name = str;
    }
}