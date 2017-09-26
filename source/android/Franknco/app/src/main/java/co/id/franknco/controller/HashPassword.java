package co.id.franknco.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator-Handy on 4/29/2016.
 */
public class HashPassword {

    public static String md5(String input) {

        String md5 = null;

        if(null == input) return null;

        try {

            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("SHA-512");

            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());

            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return md5;
    }

    public static String sha256(String input) {

        String sha512 = null;

        if(null == input) return null;

        try {

            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());

            //Converts message digest value in base 16 (hex)
            sha512 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return sha512;
    }

//    public String get_SHA_512_SecurePassword(String passwordToHash, String salt){
//        String generatedPassword = null;
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-512");
//            md.update(salt.getBytes("UTF-8"));
//            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
//            StringBuilder sb = new StringBuilder();
//            for(int i=0; i< bytes.length ;i++){
//                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
//            }
//            generatedPassword = sb.toString();
//        }
//        catch (NoSuchAlgorithmException e){
//            e.printStackTrace();
//        }
//        return generatedPassword;
//    }

}
