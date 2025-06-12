package com.air.advantage.aaservice;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256 {

    private static final Logger LOGGER = Logger.getLogger(AES256.class.getName());

    private SecretKeySpec secretKeySpec;
    private Cipher encryptCipher;
    private Cipher decryptCipher;
    private final SecureRandom secureRandom = new SecureRandom();
    private byte[] keyBytes = new byte[0];

    private void initializeCiphers(byte[] bArr) {
        if (Arrays.equals(bArr, this.keyBytes)) {
            return;
        }
        byte[] bArr2 = new byte[bArr.length];
        this.keyBytes = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        byte[] bArr3 = new byte[32];
        Arrays.fill(bArr3, (byte) 0);
        System.arraycopy(bArr, 0, bArr3, 0, Math.min(bArr.length, 32));
        this.secretKeySpec = new SecretKeySpec(bArr3, "AES");
        try {
            byte[] bArr4 = new byte[16];
            Arrays.fill(bArr4, (byte) 0);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr4);
            if (this.encryptCipher == null) {
                this.encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            }
            this.encryptCipher.init(Cipher.ENCRYPT_MODE, this.secretKeySpec, ivParameterSpec);
            IvParameterSpec ivParameterSpec2 = new IvParameterSpec(bArr4);
            if (this.decryptCipher == null) {
                this.decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            }
            this.decryptCipher.init(Cipher.DECRYPT_MODE, this.secretKeySpec, ivParameterSpec2);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            LOGGER.severe("Error initializing ciphers: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public byte[] decrypt(byte[] bArr) {
        initializeCiphers(Base64.getDecoder().decode("+07UDwu4yLmTkTpOYxe9Vc4K/2slMFRWrcvN2tuFxvc="));
        if (bArr != null && bArr.length > 0 && this.secretKeySpec != null) {
            try {
                byte[] decode = Base64.getDecoder().decode(bArr);
                if (this.decryptCipher != null) {
                    byte[] doFinal = this.decryptCipher.doFinal(decode);
                    int length = doFinal.length - 3;
                    byte[] bArr2 = new byte[length];
                    System.arraycopy(doFinal, 3, bArr2, 0, length);
                    return bArr2;
                }
            } catch (IllegalArgumentException | BadPaddingException | IllegalBlockSizeException e) {
                LOGGER.severe("Error during decryption: " + e.getMessage());
                e.printStackTrace();
            }
        }
        LOGGER.warning("Returning null - something went wrong");
        return null;
    }

    public byte[] encrypt(byte[] bArr) {
        initializeCiphers(Base64.getDecoder().decode("+07UDwu4yLmTkTpOYxe9Vc4K/2slMFRWrcvN2tuFxvc="));
        if (bArr != null && bArr.length > 0 && this.secretKeySpec != null) {
            byte[] bArr2 = new byte[bArr.length + 3];
            System.arraycopy(generateInitializationVector(), 0, bArr2, 0, 3);
            System.arraycopy(bArr, 0, bArr2, 3, bArr.length);
            try {
                if (this.encryptCipher != null) {
                    return Base64.getEncoder().encode(this.encryptCipher.doFinal(bArr2));
                }
            } catch (IllegalArgumentException | BadPaddingException | IllegalBlockSizeException e) {
                LOGGER.severe("Error during encryption: " + e.getMessage());
                e.printStackTrace();
            }
        }
        LOGGER.warning("Returning null - something went wrong");
        return null;
    }

    private byte[] generateInitializationVector() {
        byte[] bArr = new byte[3];
        this.secureRandom.nextBytes(bArr);
        return Arrays.copyOf(bArr, 3);
    }
}