package com.easemob.wukong.utils.wukong;

import com.sun.org.apache.xml.internal.security.encryption.EncryptedType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yangbs on 16-10-14.
 */

public class EncryptUtiles {

    /*加密算法 支持MD5 和 SHA 加密算法*/
    public static String encryptEncode(String plaintext, String encryptedType) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(encryptedType);
        byte[] byteArray = plaintext.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
