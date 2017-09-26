package co.id.franknco.controller;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by e_er_de on 13/09/2017.
 */

public class TripleDES {
    private static final Logger logger = Logger.getLogger(TripleDES.class.getName());

    private String key;

    public TripleDES(String myEncryptionKey) {
        key = myEncryptionKey;
    }

    /**
     * Method to encrypt the string
     */
    public String harden(String unencryptedString) {
        if (unencryptedString == null) {
            return "";
        }

        byte[] iv2 = "00000000".getBytes();
        IvParameterSpec iv = new IvParameterSpec(iv2);

        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            for (int j = 0, k = 16; j < 8; ) {
                keyBytes[k++] = keyBytes[j++];
            }

            SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

            byte[] plainTextBytes = unencryptedString.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            String base64EncryptedString = new String(Base64.encodeBase64(buf));

            return base64EncryptedString;
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to decrypt an encrypted string
     *
     * @param encryptedString
     */
    public String soften(String encryptedString) {
        if (encryptedString == null) {
            return "";
        }
        try {
            byte[] message = Base64.decodeBase64(encryptedString.getBytes("utf-8"));

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            for (int j = 0, k = 16; j < 8; ) {
                keyBytes[k++] = keyBytes[j++];
            }

            SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");
            Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            decipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] plainText = decipher.doFinal(message);

            return new String(plainText, "UTF-8");
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
            throw new RuntimeException(e);
        }
    }

}
