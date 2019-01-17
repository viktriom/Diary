package com.sonu.diary.util;

import org.bouncycastle.cms.CMSException;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.SQLException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtilTest {

    @Test
    public void testSecurityUtilOld() throws CertificateEncodingException, IOException, CMSException {
        SecurityUtil.init(new FileInputStream("/Users/sonu/dev/proj/android/Diary/app/src/test/java/com/sonu/diary/util/Baeldung.cer"), new FileInputStream("/Users/sonu/dev/proj/android/Diary/app/src/test/java/com/sonu/diary/util/Baeldung.p12"));
        String secretMessage = "My password is 123456Seven";
        System.out.println("Original Message : " + secretMessage);
        byte[] stringToEncrypt = secretMessage.getBytes();
        byte[] encryptedData = SecurityUtil.encryptDataBC(stringToEncrypt, SecurityUtil.certificate);
        System.out.println("Encrypted Message : " + new String(encryptedData));
        byte[] rawData = SecurityUtil.decryptDataBC(encryptedData, SecurityUtil.privateKey);
        String decryptedMessage = new String(rawData);
        System.out.println("Decrypted Message : " + decryptedMessage);
    }

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