package com.yzq.common.utils;


import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*
*
* AES加密工具类
*
* */
public class AESUtils {

    private static String cipherMode = "AES/ECB/PKCS5Padding";//"算法/模式/补码方式"


    public static void main(String[] args) {

        /*构建一个随机的16位密码*/

        String key=getRandomKey(16);
        System.out.println("随机生成的key："+key);

        String data="{'fig':1,'message':'登录成功'}";

        try {
            String encriptData=AESUtils.encrypt(data,key);
            System.out.println("加密后的数据："+encriptData);

           String decryptData= decrypt(encriptData,key);

            System.out.println("解密后的数据："+decryptData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    /*获得一个随机的16位key*/
    public static String getRandomKey(int length){

        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();

    }

    // 加密
    public static String encrypt(String data, String key) throws Exception {


        if (key == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (key.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = key.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(cipherMode);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes("utf-8"));

        return Base64.encode(encrypted);
    }

    // 解密
    public static String decrypt(String data, String key) throws Exception {
        try {
            // 判断Key是否正确
            if (key == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (key.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(cipherMode);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted = Base64.decode(data);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted);
                return new String(original, "utf-8");
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

}