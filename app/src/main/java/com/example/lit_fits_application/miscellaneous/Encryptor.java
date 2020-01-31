package com.example.lit_fits_application.miscellaneous;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class Encryptor {
    /**
     * Encrypts a given message with a given public key
     *
     * @param message
     * @param publicKeyBytes
     * @return String the secret message
     * @throws java.lang.Exception
     */
    public static String encryptText(String message, byte[] publicKeyBytes) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        message = byteArrayToHexString(cipher.doFinal(message.getBytes()));
        return message;
    }

    /**
     * Converts a given byte array to a hexadecimal string
     *
     * @param secretBytes
     * @return String
     */
    static String byteArrayToHexString(byte[] secretBytes) {
        String HEX = "";
        for (int i = 0; i < secretBytes.length; i++) {
            String h = Integer.toHexString(secretBytes[i] & 0xFF);
            if (h.length() == 1) {
                HEX += "0";
            }
            HEX += h;
        }
        return HEX.toUpperCase();
    }
}
