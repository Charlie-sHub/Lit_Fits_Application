package com.example.lit_fits_application.miscellaneous;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

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
        message = Arrays.toString(cipher.doFinal(message.getBytes()));
        return message;
    }
}
