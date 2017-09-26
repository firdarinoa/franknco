package co.id.franknco.controller;

import android.util.Base64;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by e_er_de on 13/09/2017.
 */

public class Encrypter {
    //    public static String ALGO = "DESede/CBC/PKCS7Padding";
    public static String ALGO = "DESede/CBC/PKCS5Padding";

    public static String _encrypt(String message, String secretKey) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });

        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, getSecreteKey(secretKey));

        byte[] plainTextBytes = message.getBytes("UTF-8");
        byte[] buf = cipher.doFinal(plainTextBytes);
        byte[] base64Bytes = Base64.encode(buf, Base64.DEFAULT);
        String base64EncryptedString = new String(base64Bytes);
        return base64EncryptedString;
    }

    public static String _decrypt(String encryptedText, String secretKey) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);

        byte[] message = Base64.decode(encryptedText.getBytes(), Base64.DEFAULT);

        Cipher decipher = Cipher.getInstance(ALGO);
        decipher.init(Cipher.DECRYPT_MODE, getSecreteKey(secretKey), iv);

        byte[] plainText = decipher.doFinal(message);

        return new String(plainText, "UTF-8");
    }

    public static SecretKey getSecreteKey(String secretKey) throws Exception {
        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        return key;
    }
}
