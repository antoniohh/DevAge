package com.antoniohorrillo.devage.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Created by antoniohh on 2/09/16.
 */
public class Cifrado {

    private String sha1 = "";

    public String getSha1(String password) {
        encryptPassword (password);
        return sha1;
    }

    private String encryptPassword (String password) {

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }

        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return sha1;
    }

    private static String byteToHex(final byte[] hash) {

        Formatter formatter = new Formatter();

        for (byte b : hash) {
            formatter.format("%02x", b);
        }

        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
