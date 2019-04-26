package com.yzq.common.utils


import java.security.*
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher


/**
 * @author : yzq
 * @description: RSA加解密
 * @date : 2019/4/25
 * @time : 15:05
 */

object RSAUtil {


    //加密方式
    private val KEY_RSA = "RSA"
    //公钥
    private val KEY_RSA_PUBLICKEY = "RSAPublicKey"
    //私钥
    private val KEY_RSA_PRIVATEKEY = "RSAPrivateKey"
    //签名算法
    private val KEY_RSA_SIGNATURE = "SHA256withRSA"
    //加密方式，算法，填充方式
    private val cipherMode = "RSA/ECB/PKCS1Padding"


    /**
     * 测试方法
     */
    @JvmStatic
    fun main(args: Array<String>) {
        val publicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAswSYu13+yGlDdAUgAxKcJ81Edt04+CjJUuzqmYmO91ubXCcz7cwy6EHfkk++VuZLAXut/sfQa/jlScTOaUgJos67zWJIrifYc1VQqV3y7pG2HeVOJGAuXBzkPXRDXsIVAYRZRFxU++mI3lo8dvOvORWIO1xMH9TJjBzV0UR888qEXeHd1a80qqTVoKawfiy1nVremtbuJIbu5ZSpruM0RAu2rENg0qr4oHmI2bUq3vECrYYPp+kBbp81dDgQDycOrQPr7JEM1ucJZDz2zU0m2UxboNohjAizteoBkEaKj0503e2AUP09ie7knWoZxtPAzolugpbxT3AO1lgbHKL5pwIDAQAB"
        val privateKey =
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCzBJi7Xf7IaUN0BSADEpwnzUR23Tj4KMlS7OqZiY73W5tcJzPtzDLoQd+ST75W5ksBe63+x9Br+OVJxM5pSAmizrvNYkiuJ9hzVVCpXfLukbYd5U4kYC5cHOQ9dENewhUBhFlEXFT76YjeWjx28685FYg7XEwf1MmMHNXRRHzzyoRd4d3VrzSqpNWgprB+LLWdWt6a1u4khu7llKmu4zREC7asQ2DSqvigeYjZtSre8QKthg+n6QFunzV0OBAPJw6tA+vskQzW5wlkPPbNTSbZTFug2iGMCLO16gGQRoqPTnTd7YBQ/T2J7uSdahnG08DOiW6ClvFPcA7WWBscovmnAgMBAAECggEAKzt58w3hIN8i9hrivzs4UPhmh1onju6yp/8lLM0mpKAP5fJlvRDqXmLCLmBptCzLgmEvBO+Wauzh2q3Xt185TIMmoZQRv1VKFZhN8YkJyQmRdKjS9T/xEje7+wdf2bt/PS2MLVErCOc+MYyTO5rf/yYvDz7b93f48IhqLq6einU/l95tbNEc3gXjhSlihuZKr/6KD1++k4AEEBrl5oPa3DBZouhY6HHGCAclNTlzivfvm5WAjdCE3LMlrodC1ACjgGyDZcOIHIPkOcqFLfxIydI0nVEuFPuIWjeyOzGf3EeBjtesiK09SRsK5ddFrDOzby/fpAHWrhiwFmYqv84XgQKBgQDY1e+bZjRCAqbTxzH0uKvVTKvHnNJyc47jh0P02UijVWhrm/d4t9wKH7DYnWBZwZAesYM6XDAzbMYFZ5KLw3EPcIt1fyuMHxeVCz00UH3IEozN8QxWlhrlHik6ik8A9Kqr67Jcs4KeZaumZRWmmX4SRE7jY712O+3QP/AgDAU9wQKBgQDTWgqppLxIiu4MzIzKY5VkhiuvInqX0MJZ0I8kMRvtRh/b34/T+GjT+r1g3AgSHWcwf9nPufwlYWSwoPzGSPhGCF75T2CF4IXEEAnJ4zCJVK7amybgrlAQZXLGXKaIZHr2T6dHQknjJsfYQWKUFde72oxXt/gvjoNK+eCs5GFhZwKBgQC1LBtldkHXnauSa28cEGjScZtdz3Qu2Mrc5RosrJf6kNQMhWaCYOzjMJNsiiIFHKu0WZFR49EKRqo1vdI+IPCIe/qqE7VpAFmN2LQsz8worQck03EBr62NHmRIW2OjYspvlyGSPxK2EjEXeIJcjwc9cAGSELYu4efUBng17pU6gQKBgQCxsKMES27M4pkPA65eveis8iyp+qftGWM81Z5yxCMBkpJYbhXjFZc0mTs8wuC6MiQ+X08FWQ1HdCGOalr6bgDmCEWo/3ZcOA7ebsl8BdkZrKuxOP4vqf3AOzqK0Pxl8Wx7xy4ROAccxc8A3r/9VnvhAPY7DX3Ipd12XKzrTrscgwKBgFCMCYDMMh1ERiImsa5DE4/ndTuI7rILr77JvkfuNbAqTbaF1C9AHYi4WYR/tqwmNRJySjCUuMKavgEdk3V12kz6uQKRqIKb81xClAuYloZufuGGx26wvFPVw6o7ykfbtaaDxdl2Ifplck18+Gu5hETPOdUoIltCGmtA6csNOfkJ"

        val str = """{"name":"喻志强"}"""
        // 公钥加密，私钥解密
        val encryptByPublicData = encryptByPublic(str, publicKey)
        println("公钥加密后：$encryptByPublicData")
        val decryptByPrivateData = decryptByPrivate(encryptByPublicData, privateKey)
        println("私钥解密后：$decryptByPrivateData")
        // 私钥加密，公钥解密
        val encryptByPrivateData = encryptByPrivate(str, privateKey)
        println("私钥加密后：$encryptByPrivateData")
        val decryptByPublicData = decryptByPublic(encryptByPrivateData, publicKey)
        println("公钥解密后：$decryptByPublicData")
        // 产生签名
        val signData = sign(encryptByPrivateData, privateKey)
        println("签名后的数据:$signData")
        // 验证签名
        val status = verify(encryptByPrivateData, publicKey, signData)
        println("验签是否成功:$status")


    }


    /**
     * 生成公私密钥对
     */
    fun init(): Map<String, Any>? {
        var map: MutableMap<String, Any>? = null
        try {
            val generator = KeyPairGenerator.getInstance(KEY_RSA)
            //设置密钥对的bit数，越大越安全，但速度减慢，一般使用512或1024
            generator.initialize(1024)
            val keyPair = generator.generateKeyPair()
            // 获取公钥
            val publicKey = keyPair.public as RSAPublicKey
            // 获取私钥
            val privateKey = keyPair.private as RSAPrivateKey
            // 将密钥对封装为Map
            map = HashMap()
            map[KEY_RSA_PUBLICKEY] = publicKey
            map[KEY_RSA_PRIVATEKEY] = privateKey
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return map
    }

    /**
     * 获取Base64编码的公钥字符串
     */
    fun getPublicKey(map: Map<String, Any>): String {
        var str = ""
        val key = map[KEY_RSA_PUBLICKEY] as PublicKey
        str = base64Encode2Str(key.encoded)
        return str
    }

    /**
     * 获取Base64编码的私钥字符串
     */
    fun getPrivateKey(map: Map<String, Any>): String {
        var str = ""
        val key = map[KEY_RSA_PRIVATEKEY] as PrivateKey
        str = base64Encode2Str(key.encoded)
        return str
    }

    /**
     * BASE64 解码
     *
     * @param key 需要Base64解码的字符串
     * @return 字节数组
     */
    fun Base64Decode2Bytes(key: String): ByteArray {
        return Base64.decode(key)
    }

    /**
     * BASE64 编码
     *
     * @param key 需要Base64编码的字节数组
     * @return 字符串
     */
    fun base64Encode2Str(key: ByteArray): String {
        return Base64.encode(key)
    }


    /**
     * 公钥加密
     *
     * @param encryptingStr 需要加密的字符串
     * @param publicKeyStr  公钥
     * @return 经过Base64编码后的加密字符串
     */

    @Throws(Exception::class)
    fun encryptByPublic(encryptingStr: String, publicKeyStr: String): String {

        // 将公钥由字符串转为UTF-8格式的字节数组
        val publicKeyBytes = Base64Decode2Bytes(publicKeyStr)
        // 获得公钥
        val keySpec = X509EncodedKeySpec(publicKeyBytes)
        // 取得待加密数据
        val data = encryptingStr.toByteArray()
        val factory: KeyFactory
        factory = KeyFactory.getInstance(KEY_RSA)
        val publicKey = factory.generatePublic(keySpec)
        // 对数据加密
        val cipher = Cipher.getInstance(cipherMode)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        // 返回加密后由Base64编码的加密信息
        return base64Encode2Str(cipher.doFinal(data))

    }

    /**
     * 私钥解密
     *
     * @param encryptedStr  需要解密的字符串
     * @param privateKeyStr 私钥
     * @return 经过Base64编码后的解密数据
     */
    @Throws(Exception::class)
    fun decryptByPrivate(encryptedStr: String, privateKeyStr: String): String {

        // 对私钥解密
        val privateKeyBytes = Base64Decode2Bytes(privateKeyStr)
        // 获得私钥
        val keySpec = PKCS8EncodedKeySpec(privateKeyBytes)
        // 获得待解密数据
        val data = Base64Decode2Bytes(encryptedStr)
        val factory = KeyFactory.getInstance(KEY_RSA)
        val privateKey = factory.generatePrivate(keySpec)
        // 对数据解密
        val cipher = Cipher.getInstance(cipherMode)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        // 返回UTF-8编码的解密信息
        return String(cipher.doFinal(data))

    }

    /**
     * 私钥加密
     *
     * @param encryptingStr 需要加密的字符串
     * @param privateKeyStr 私钥
     * @return 经过Base64编码后的加密字符串
     */
    @Throws(Exception::class)
    fun encryptByPrivate(encryptingStr: String, privateKeyStr: String): String {

        val privateKeyBytes = Base64Decode2Bytes(privateKeyStr)
        // 获得私钥
        val keySpec = PKCS8EncodedKeySpec(privateKeyBytes)
        // 取得待加密数据
        val data = encryptingStr.toByteArray(charset("UTF-8"))
        val factory = KeyFactory.getInstance(KEY_RSA)
        val privateKey = factory.generatePrivate(keySpec)
        // 对数据加密
        val cipher = Cipher.getInstance(cipherMode)
        cipher.init(Cipher.ENCRYPT_MODE, privateKey)
        // 返回加密后由Base64编码的加密信息
        return base64Encode2Str(cipher.doFinal(data))

    }

    /**
     * 公钥解密
     *
     * @param encryptedStr
     * @param publicKeyStr
     * @return
     */
    @Throws(Exception::class)
    fun decryptByPublic(encryptedStr: String, publicKeyStr: String): String {

        // 对公钥解密
        val publicKeyBytes = Base64Decode2Bytes(publicKeyStr)
        // 取得公钥
        val keySpec = X509EncodedKeySpec(publicKeyBytes)
        // 取得待加密数据
        val data = Base64Decode2Bytes(encryptedStr)
        val factory = KeyFactory.getInstance(KEY_RSA)
        val publicKey = factory.generatePublic(keySpec)
        // 对数据解密
        val cipher = Cipher.getInstance(cipherMode)
        cipher.init(Cipher.DECRYPT_MODE, publicKey)
        // 返回UTF-8编码的解密信息
        return String(cipher.doFinal(data))

    }

    /**
     * 用私钥对加密数据进行签名
     *
     * @param encryptedStr 需要进行签名的字符串
     * @param privateKey   私钥
     * @return 经过Base64编码后的签名数据
     */
    fun sign(encryptedStr: String, privateKey: String): String {


        //将私钥加密数据字符串转换为字节数组
        val data = encryptedStr.toByteArray()
        // 解密由base64编码的私钥
        val bytes = Base64Decode2Bytes(privateKey)
        // 构造PKCS8EncodedKeySpec对象
        val pkcs = PKCS8EncodedKeySpec(bytes)
        // 指定的加密算法
        val factory = KeyFactory.getInstance(KEY_RSA)
        // 取私钥对象
        val key = factory.generatePrivate(pkcs)
        // 用私钥对信息生成数字签名
        val signature = Signature.getInstance(KEY_RSA_SIGNATURE)
        signature.initSign(key)
        signature.update(data)
        return base64Encode2Str(signature.sign())
    }

    /**
     * 校验数字签名
     *
     * @param encryptedStr 需要进行验签的加密数据
     * @param publicKey    公钥
     * @param sign         原数据
     * @return 校验成功返回true，失败返回false
     */
    @Throws(Exception::class)
    fun verify(encryptedStr: String, publicKey: String, sign: String): Boolean {

        //将私钥加密数据字符串转换为字节数组
        val data = encryptedStr.toByteArray()
        // 解密由base64编码的公钥
        val bytes = Base64Decode2Bytes(publicKey)
        // 构造X509EncodedKeySpec对象
        val keySpec = X509EncodedKeySpec(bytes)
        // 指定的加密算法
        val factory = KeyFactory.getInstance(KEY_RSA)
        // 取公钥对象
        val key = factory.generatePublic(keySpec)
        // 用公钥验证数字签名
        val signature = Signature.getInstance(KEY_RSA_SIGNATURE)
        signature.initVerify(key)
        signature.update(data)

        return signature.verify(Base64Decode2Bytes(sign))
    }
}