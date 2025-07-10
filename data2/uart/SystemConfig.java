package com.air.advantage.uart;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* renamed from: com.air.advantage.uart.d */
/* loaded from: classes.dex */
public final class SystemConfig {

    /* renamed from: a */
    @NotNull
    public static final FileConfig fileConfig = new FileConfig(null);

    /* renamed from: b */
    @NotNull
    public static final String KEY = "fb69967b71cf8bc79be4fa47e4d939c0";

    /* renamed from: c, reason: collision with root package name */
    public static final int f7068c = 15;

    /* renamed from: d, reason: collision with root package name */
    public static final int f7069d = 522;

    /* renamed from: e, reason: collision with root package name */
    public static final int f7070e = 2;

    /* renamed from: f */
    public static final int myAppRev = 1070;

    /* renamed from: g, reason: collision with root package name */
    public static final boolean f7071g = false;

    /* renamed from: h, reason: collision with root package name */
    public static final boolean f7072h = false;

    /* renamed from: i, reason: collision with root package name */
    public static final boolean f7073i = true;

    /* renamed from: com.air.advantage.uart.d$a */
    public static final class FileConfig {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.d.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ FileConfig(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        /* renamed from: a */
        public final File getExternalBomServicesFile() {
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/AAData/bomServices");
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }

        @NotNull
        /* renamed from: b */
        public final File getInternalBomServicesFile(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            File file = new File(context.getFilesDir().toString() + "/AAData/bomServices");
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private FileConfig() {
        }
    }
}