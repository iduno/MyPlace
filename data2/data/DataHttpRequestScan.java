package com.air.advantage.data;

import com.air.advantage.MyApp;
import com.air.advantage.myair5.R;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* renamed from: com.air.advantage.data.p */
/* loaded from: classes.dex */
public final class DataHttpRequestScan {

    @NotNull
    public static final a Companion = new a(null);

    @NotNull
    @JvmField
    public String ipAddress;

    @NotNull
    @JvmField
    public String macAddress;

    @NotNull
    @JvmField
    public String messageParameters;

    @NotNull
    @JvmField
    public String messageRequest;

    @NotNull
    @JvmField
    public String name;

    @NotNull
    @JvmField
    public String port;

    @JvmField
    public boolean retryMessageSend;

    @JvmField
    public boolean scanningForDevice;

    /* renamed from: com.air.advantage.data.p$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.p.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        @JvmStatic
        public final DataHttpRequestScan dataHttpRequestScan(@NotNull String ip) {
            Intrinsics.checkNotNullParameter(ip, "ip");
            DataHttpRequestScan dataHttpRequestScan = new DataHttpRequestScan("getSystemData", "");
            dataHttpRequestScan.scanningForDevice = true;
            dataHttpRequestScan.ipAddress = ip;
            dataHttpRequestScan.name = ip;
            String string = MyApp.appContextProvider.appContext().getString(R.string.communication_endpoint);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            dataHttpRequestScan.port = string;
            return dataHttpRequestScan;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    public DataHttpRequestScan(@NotNull String messageRequestData, @NotNull String messageParametersData) {
        Intrinsics.checkNotNullParameter(messageRequestData, "messageRequestData");
        Intrinsics.checkNotNullParameter(messageParametersData, "messageParametersData");
        this.messageRequest = "";
        this.messageParameters = "";
        this.retryMessageSend = true;
        this.ipAddress = "";
        this.name = "";
        String string = MyApp.appContextProvider.appContext().getString(R.string.communication_endpoint);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        this.port = string;
        this.macAddress = "";
        this.messageRequest = messageRequestData;
        this.messageParameters = messageParametersData;
    }

    @NotNull
    @JvmStatic
    public static final DataHttpRequestScan dataHttpRequestScan(@NotNull String str) {
        return Companion.dataHttpRequestScan(str);
    }

    public final void updateIpAddress(@NotNull String ipAddress) {
        Intrinsics.checkNotNullParameter(ipAddress, "ipAddress");
        this.ipAddress = ipAddress;
        String string = MyApp.appContextProvider.appContext().getString(R.string.communication_endpoint);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        this.port = string;
    }
}