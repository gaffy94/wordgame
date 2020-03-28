package com.gaf.anagram.utils;

import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Logger;

@Service
public class JEncrypt {
    private static Logger logger = Logger.getGlobal();
    private static String charsetName = "UTF-8";
    private static String algorithm2 = "AES/GCM/NoPadding";
    private static final String KEY = "ECO*&^58FRHEC#!$@O654RDS03245ifnUIYDSDF< )czxmv*&*(MYzxcdp9uvhoibv0q374tsbv kjzx v&$&^9zxcv87xvmzxvcv!@#$!#$@!~%#$^&^%**(&";
    private static MessageDigest md;
    private static byte[] hash;
    public static final int GCM_TAG_LENGTH = 16;

    static {
        try {
            md = MessageDigest.getInstance("SHA-512");
            hash = md.digest(KEY.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            logger.info("NoSuchAlgorithmException generating hash from KEY: " + ex.getMessage());
            logger.warning("Exception " + Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        }
    }

    public String encryptV2(String data) {
        try {
            final byte[] keyByte = new byte[16];
            final byte[] IV = new byte[16];

            System.arraycopy(hash, 0, keyByte, 0, 16);
            System.arraycopy(hash, 16, keyByte, 0, 16);

            SecretKey secretKey = new SecretKeySpec(keyByte, "AES");

            byte[] dataBytes = data.getBytes(charsetName);
            // Get Cipher Instance
            Cipher cipher = Cipher.getInstance(algorithm2);
            // Create GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
            // Initialize Cipher for ENCRYPT_MODE
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);
            // Perform Encryption
            byte[] cipherText = cipher.doFinal(dataBytes);
            return new String(Base64.getEncoder().encode(cipherText));

        } catch (Throwable e) {
            logger.info("Exception In connection : " + e.getMessage());
            logger.warning("Exception " + Arrays.toString(e.getStackTrace()).replaceAll(", ", "\n"));
            return null;
        }
    }

    public String decryptV2(String data) {
        try {
            byte[] dataBytes = Base64.getDecoder().decode(data.getBytes());

            final byte[] keyByte = new byte[16];

            final byte[] IV = new byte[16];

            System.arraycopy(hash, 0, keyByte, 0, 16);
            System.arraycopy(hash, 16, keyByte, 0, 16);

            // Get Cipher Instance
            Cipher cipher = Cipher.getInstance(algorithm2);

            // Create SecretKeySpec
            SecretKey secretKey = new SecretKeySpec(keyByte, "AES");
            // Create GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
            // Initialize Cipher for DECRYPT_MODE
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
            // Perform Decryption
            byte[] decryptedText = cipher.doFinal(dataBytes);
            return new String(decryptedText);
        } catch (Throwable e) {
            logger.info("Exception In connection : " + e.getMessage());
            logger.warning("Exception " + Arrays.toString(e.getStackTrace()).replaceAll(", ", "\n"));
            return null;
        }
    }


    public static void main(String s[]) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {

//        String a = encryptV2("love");
//        System.out.println(a);
//        System.out.println(decryptV2(a));

        JEncrypt enc = new JEncrypt();
        System.out.println(enc.decryptV2("Sd0X8ZdLl15oT0QCRW4zx6p7aQSsC+7nMmwuZ3xfZ0IY4i35mFkdRvmFxKJpscjnSbsUH8AssROuySTiUTPZow9Q1DrxSYJpS9JXhtPAl1wSf+EwxqRApeIrUmB1gvHeq7h+rpNCTJjOG0RzdMamaL5riDE5jyiaI4gRacgvjJXtReG7u0R/pgip+5l/qtWTOmZXvn0/rA=="));

    }
}
