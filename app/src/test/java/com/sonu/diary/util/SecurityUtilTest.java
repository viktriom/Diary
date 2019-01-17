package com.sonu.diary.util;

import org.bouncycastle.cms.CMSException;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


public class SecurityUtilTest {


    @Test
    public void testSecurityUtil() throws CertificateEncodingException, IOException, CMSException, InvalidKeyException {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);

            SecretKey key = keyGen.generateKey();
            System.out.println(SecurityUtil.getHexString(key.getEncoded()));
            byte[] data = "My name is Vivek Tripathi.".getBytes();
            System.out.println("Original Plain Text : " + SecurityUtil.getStringFromByte(data));
            byte[] cypherText = SecurityUtil.encryptData(data, key);
            System.out.println("Cypher Text : " + SecurityUtil.getStringFromByte(cypherText));
            System.out.println("Hex Cypher Text : " + SecurityUtil.getHexString(cypherText));
            byte[] plainText = SecurityUtil.decryptData(cypherText, key);
            System.out.println("Decrypted Text : " + SecurityUtil.getStringFromByte(plainText));
        } catch (NoSuchAlgorithmException | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws NoSuchAlgorithmException {
        int maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
        System.out.println(maxKeySize);
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(1024);

        for(int i=0;i<10; i ++) {

            SecretKey secret = keyGen.generateKey();

            StringBuilder sb = new StringBuilder();

            for (Byte b : secret.getEncoded()) {
                sb.append(String.format("%02X", b));
            }

            System.out.println(sb);
        }

    }
}