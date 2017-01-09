package com.graypn.cmmon.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import java.security.SecureRandom;

/**
 * DES 加密解密工具类
 * <p>
 * Created by graypn on 16/9/13.
 */
public class DESUtils {

    public static String password = "1566028820109132570743325311898426347857298773549468758875018579537757772163084478873699447306034466200616411960574122434059469100235892702736860872901247123456";


    public static void main(String args[]) {

        String str = "10101100";

        System.out.println("加密后:" + encrypt(str, password));

        System.out.println("解密后:" + decrypt(encrypt(str, password), password));
    }


    public static String encrypt(String str, String password) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String encryptStr = str;
        byte[] bytes = encrypt(str.getBytes(), password);
        if (bytes != null) {
            encryptStr = Base64Utils.encode(bytes);
        }
        return encryptStr;
    }

    public static String decrypt(String str, String password) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String decryptStr;
        try {
            decryptStr = new String(decrypt(Base64Utils.decode(str), password));
        } catch (Exception e) {
            return "wrong key";
        }
        return decryptStr;
    }

    /**
     * 加密
     *
     * @param datasource byte[]
     * @param password   String
     * @return byte[]
     */
    public static byte[] encrypt(byte[] datasource, String password) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(datasource);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param src      byte[]
     * @param password String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String password) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }
}