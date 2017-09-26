package co.id.franknco.crypto;

import android.util.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by e_er_de on 15/09/2017.
 */

public class TripleDES {

    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    public static byte[] decrypt3DES(byte[] message, byte[] bkey) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            SecretKey keySpec = new SecretKeySpec(bkey, "DESede");
            IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            Cipher d_cipher = Cipher.getInstance("DESede/CBC/NoPadding", "BC");
            d_cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            byte[] cipherText = d_cipher.doFinal(message);
            // System.out.println("Ciphertext: " + new
            // sun.misc.BASE64Encoder().encode(cipherText));
            //System.out.println("hasil dec 3des :" + bytesToHex(cipherText));
            return cipherText;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static byte[] decryptDES(byte[] message, byte[] bkey) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            SecretKey keySpec = new SecretKeySpec(bkey, "DES");
            IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            Cipher d_cipher = Cipher.getInstance("DES/EBC/NoPadding", "BC");
            d_cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            byte[] cipherText = d_cipher.doFinal(message);
            //System.out.println("Ciphertext: " + new sun.misc.BASE64Encoder().encode(cipherText));
            // System.out.println("DES decrypt result: "+ bytesToHex(cipherText));
            return cipherText;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static byte[] encrypt3DES(byte[] message, byte[] bkey) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            SecretKey keySpec = new SecretKeySpec(bkey, "DESede");
            IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            Cipher e_cipher = Cipher.getInstance("DESede/CBC/NoPadding", "BC");
            e_cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] cipherText = e_cipher.doFinal(message);
            // System.out.println("Ciphertext: " + new
            // sun.misc.BASE64Encoder().encode(cipherText));
            // System.out.println("hasil 3des :"+bytesToHex(cipherText));
            return cipherText;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static byte[] encryptDES(byte[] message, byte[] bkey) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            SecretKey keySpec = new SecretKeySpec(bkey, "DES");
            IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            Cipher e_cipher = Cipher.getInstance("DES/EBC/NoPadding");
            e_cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] cipherText = Base64.decode(e_cipher.doFinal(message),Base64.NO_PADDING);

            //System.out.println("Ciphertext: " + new sun.misc.BASE64Encoder().encode(cipherText));
            //System.out.println("DES encrypt result: "+ bytesToHex(cipherText));
            return cipherText;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //CONVERT DECODE ENDCODE STRING TO BYTE[]
    public String decodeUTF8(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }

    public static byte[] encodeUTF8(String string) {
        return string.getBytes(UTF8_CHARSET);
    }

    //CONVERT STRING TO HEX
    public static String stringToHex2(String input) throws UnsupportedEncodingException {
        if (input == null) throw new NullPointerException();
        return asHex(input.getBytes());
    }

    public static String asHex(byte[] buf) {
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i) {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }

    //CONVERT BYTE TO HEX
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_CHARS[v >>> 4];
            hexChars[j * 2 + 1] = HEX_CHARS[v & 0x0F];
        }
        return new String(hexChars);
    }
}
