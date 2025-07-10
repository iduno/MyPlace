package com.air.advantage.uart;

import android.util.Base64;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

/* renamed from: com.air.advantage.uart.a */
/* loaded from: classes.dex */
public final class AES256 {

    /* renamed from: g, reason: collision with root package name */
    private static final int f7054g = 3;

    /* renamed from: i */
    @NotNull
    private static final String cipherKey = "+07UDwu4yLmTkTpOYxe9Vc4K/2slMFRWrcvN2tuFxvc=";

    /* renamed from: b */
    @Nullable
    private SecretKeySpec secretKeySpec;

    /* renamed from: d */
    @Nullable
    private Cipher encryptCipher;

    /* renamed from: e */
    @Nullable
    private Cipher decryptCipher;

    /* renamed from: f, reason: collision with root package name */
    @NotNull
    public static final a f7053f = new a(null);

    /* renamed from: h */
    private static final String LOG_TAG = AES256.class.getName();

    @NotNull
    private final SecureRandom a = new SecureRandom();

    /* renamed from: c, reason: collision with root package name */
    @NotNull
    private byte[] f7055c = new byte[0];

    /* renamed from: com.air.advantage.uart.a$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.uart.a.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* renamed from: c */
    private final byte[] generateInitializationVector() {
        byte[] bArr = new byte[3];
        this.a.nextBytes(bArr);
        String encodeToString = Base64.encodeToString(bArr, 1);
        Intrinsics.checkNotNull(encodeToString);
        String substring = encodeToString.substring(0, 3);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        byte[] bytes = substring.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        return bytes;
    }

    /* renamed from: d */
    private final void initializeCiphers(byte[] bArr) {
        if (Arrays.equals(bArr, this.f7055c)) {
            return;
        }
        byte[] bArr2 = new byte[bArr.length];
        this.f7055c = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        byte[] bArr3 = new byte[32];
        Arrays.fill(bArr3, (byte) 0);
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length < 32 ? bArr.length : 32);
        this.secretKeySpec = new SecretKeySpec(bArr3, "AES");
        try {
            byte[] bArr4 = new byte[16];
            Arrays.fill(bArr4, (byte) 0);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr4);
            if (this.encryptCipher == null) {
                this.encryptCipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            }
            Cipher cipher = this.encryptCipher;
            Intrinsics.checkNotNull(cipher);
            cipher.init(1, this.secretKeySpec, ivParameterSpec);
            IvParameterSpec ivParameterSpec2 = new IvParameterSpec(bArr4);
            if (this.decryptCipher == null) {
                this.decryptCipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            }
            Cipher cipher2 = this.decryptCipher;
            Intrinsics.checkNotNull(cipher2);
            cipher2.init(2, this.secretKeySpec, ivParameterSpec2);
        } catch (InvalidAlgorithmParameterException e7) {
            e7.printStackTrace();
        } catch (InvalidKeyException e10) {
            e10.printStackTrace();
        } catch (NoSuchAlgorithmException e11) {
            e11.printStackTrace();
        } catch (NoSuchPaddingException e12) {
            e12.printStackTrace();
        }
    }

    @Nullable
    /* renamed from: a */
    public final byte[] decrypt(@Nullable byte[] bArr) {
        byte[] decode = Base64.decode(cipherKey, 2);
        Intrinsics.checkNotNullExpressionValue(decode, "decode(...)");
        initializeCiphers(decode);
        if (bArr != null && bArr.length > 0 && this.secretKeySpec != null) {
            try {
                byte[] decode2 = Base64.decode(bArr, 10);
                Cipher cipher = this.decryptCipher;
                if (cipher != null) {
                    Intrinsics.checkNotNull(cipher);
                    byte[] doFinal = cipher.doFinal(decode2);
                    int length = doFinal.length - 3;
                    byte[] bArr2 = new byte[length];
                    System.arraycopy(doFinal, 3, bArr2, 0, length);
                    return bArr2;
                }
            } catch (IllegalArgumentException | BadPaddingException | IllegalBlockSizeException unused) {
            }
        }
        Timber.forest.d("Returning null - something went wrong", new Object[0]);
        return null;
    }

    @Nullable
    /* renamed from: b */
    public final byte[] encrypt(@Nullable byte[] bArr) throws NullPointerException {
        byte[] decode = Base64.decode(cipherKey, 2);
        Intrinsics.checkNotNullExpressionValue(decode, "decode(...)");
        initializeCiphers(decode);
        if (bArr != null && bArr.length > 0 && this.secretKeySpec != null) {
            byte[] bArr2 = new byte[bArr.length + 3];
            System.arraycopy(generateInitializationVector(), 0, bArr2, 0, 3);
            System.arraycopy(bArr, 0, bArr2, 3, bArr.length);
            try {
                Cipher cipher = this.encryptCipher;
                if (cipher != null) {
                    Intrinsics.checkNotNull(cipher);
                    return Base64.encode(cipher.doFinal(bArr2), 10);
                }
            } catch (IllegalArgumentException e7) {
                e7.printStackTrace();
            } catch (BadPaddingException e10) {
                e10.printStackTrace();
            } catch (IllegalBlockSizeException e11) {
                e11.printStackTrace();
            }
        }
        Timber.forest.d("Returning null - something went wrong", new Object[0]);
        return null;
    }
}