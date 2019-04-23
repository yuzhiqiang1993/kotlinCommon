package com.yzq.common.utils

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESUtils {


    private val cipherMode = "AES/CBC/PKCS5Padding"//算法/模式/补码方式

    /*
    *  AES秘钥支持128bit/192bit/256bit三种长度的秘钥，一个字节等于8bit，
    *  因此支持生成的字符串的长度应该是 16/24/32
    * */
    private val keyLength = 16


    /*当加密模式为CBC时  需要偏移量*/
    private val offset = "1234567890000000"


    /**
     * @param length 需要生成的字符串长度
     * @return 随机生成的字符串
     */
    fun getRandomKey(length: Int): String {

        if (length != 16 && length != 24 && length != 32) {
            println("长度必须为16/24/32")
            return ""
        }


        val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val stringBuilder = StringBuilder()
        for (i in 0 until length) {
            val number = random.nextInt(str.length)
            stringBuilder.append(str[number])
        }
        return stringBuilder.toString()

    }

    /**
     * @param data 需要加密的数据
     * @param key  加密使用的key
     * @return 加密后的数据(Base64编码)
     * @throws Exception
     */
    @Throws(Exception::class)
    fun encrypt(data: String, key: String): String {

        val length = key.length
        if (length != 16 && length != 24 && length != 32) {
            println("长度必须为16/24/32")
            return ""
        }

        val raw = key.toByteArray()
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance(cipherMode)
        val iv = IvParameterSpec(offset.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
        val encrypted = cipher.doFinal(data.toByteArray())

        return Base64.encode(encrypted)
    }

    /**
     * @param data 需要解密的数据
     * @param key  解密用的key
     * @return 解密后的数据
     * @throws Exception
     */
    @Throws(Exception::class)
    fun decrypt(data: String, key: String): String? {

        val length = key.length
        if (length != 16 && length != 24 && length != 32) {
            println("长度必须为16/24/32")
            return null
        }

        val raw = key.toByteArray()
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance(cipherMode)
        val iv = IvParameterSpec(offset.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
        val encrypted = Base64.decode(data)//先用base64解密

        val original = cipher.doFinal(encrypted)
        return String(original)


    }


    @JvmStatic
    fun main(args: Array<String>) {

        /*构建一个随机密码*/
        val key = getRandomKey(keyLength)
        println("随机生成的key：$key")

        val data = "{'fig':1,'message':'登录成功'}"

        try {
            val encriptData = encrypt(data, key)
            println("加密后的数据：$encriptData")


            val decryptData = decrypt(encriptData, key)

            println("解密后的数据：$decryptData")

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}