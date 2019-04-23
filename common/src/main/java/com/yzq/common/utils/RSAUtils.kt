package com.yzq.common.utils


import java.io.ByteArrayOutputStream
import java.security.Key
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.Signature
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher


/**
 * @author : yzq
 * @Description: RSA工具类，支持长度为2048的秘钥
 * @date : 2019/3/18
 * @time : 16:29
 */
object RSAUtils {


    /**
     * 加密算法RSA
     */
    private val KEY_ALGORITHM = "RSA"

    /**
     * 签名算法
     */
    // public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private val SIGNATURE_ALGORITHM = "SHA256withRSA"

    private val cipherMode = "RSA/ECB/PKCS1Padding"

    /**
     * 获取公钥的key
     */
    private val PUBLIC_KEY = "RSAPublicKey"


    /**
     * 获取私钥的key
     */
    private val PRIVATE_KEY = "RSAPrivateKey"


    /**
     * RSA最大加密明文大小
     */
    private val MAX_ENCRYPT_BLOCK = 117


    /**
     * RSA最大解密密文大小
     */
    private val MAX_DECRYPT_BLOCK = 256


    @JvmStatic
    fun main(args: Array<String>) {


        /*RSA 2048*/
        val publicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAswSYu13+yGlDdAUgAxKcJ81Edt04+CjJUuzqmYmO91ubXCcz7cwy6EHfkk++VuZLAXut/sfQa/jlScTOaUgJos67zWJIrifYc1VQqV3y7pG2HeVOJGAuXBzkPXRDXsIVAYRZRFxU++mI3lo8dvOvORWIO1xMH9TJjBzV0UR888qEXeHd1a80qqTVoKawfiy1nVremtbuJIbu5ZSpruM0RAu2rENg0qr4oHmI2bUq3vECrYYPp+kBbp81dDgQDycOrQPr7JEM1ucJZDz2zU0m2UxboNohjAizteoBkEaKj0503e2AUP09ie7knWoZxtPAzolugpbxT3AO1lgbHKL5pwIDAQAB"


        /*PKCS8(Java适用)*/
        val privateKey =
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCzBJi7Xf7IaUN0BSADEpwnzUR23Tj4KMlS7OqZiY73W5tcJzPtzDLoQd+ST75W5ksBe63+x9Br+OVJxM5pSAmizrvNYkiuJ9hzVVCpXfLukbYd5U4kYC5cHOQ9dENewhUBhFlEXFT76YjeWjx28685FYg7XEwf1MmMHNXRRHzzyoRd4d3VrzSqpNWgprB+LLWdWt6a1u4khu7llKmu4zREC7asQ2DSqvigeYjZtSre8QKthg+n6QFunzV0OBAPJw6tA+vskQzW5wlkPPbNTSbZTFug2iGMCLO16gGQRoqPTnTd7YBQ/T2J7uSdahnG08DOiW6ClvFPcA7WWBscovmnAgMBAAECggEAKzt58w3hIN8i9hrivzs4UPhmh1onju6yp/8lLM0mpKAP5fJlvRDqXmLCLmBptCzLgmEvBO+Wauzh2q3Xt185TIMmoZQRv1VKFZhN8YkJyQmRdKjS9T/xEje7+wdf2bt/PS2MLVErCOc+MYyTO5rf/yYvDz7b93f48IhqLq6einU/l95tbNEc3gXjhSlihuZKr/6KD1++k4AEEBrl5oPa3DBZouhY6HHGCAclNTlzivfvm5WAjdCE3LMlrodC1ACjgGyDZcOIHIPkOcqFLfxIydI0nVEuFPuIWjeyOzGf3EeBjtesiK09SRsK5ddFrDOzby/fpAHWrhiwFmYqv84XgQKBgQDY1e+bZjRCAqbTxzH0uKvVTKvHnNJyc47jh0P02UijVWhrm/d4t9wKH7DYnWBZwZAesYM6XDAzbMYFZ5KLw3EPcIt1fyuMHxeVCz00UH3IEozN8QxWlhrlHik6ik8A9Kqr67Jcs4KeZaumZRWmmX4SRE7jY712O+3QP/AgDAU9wQKBgQDTWgqppLxIiu4MzIzKY5VkhiuvInqX0MJZ0I8kMRvtRh/b34/T+GjT+r1g3AgSHWcwf9nPufwlYWSwoPzGSPhGCF75T2CF4IXEEAnJ4zCJVK7amybgrlAQZXLGXKaIZHr2T6dHQknjJsfYQWKUFde72oxXt/gvjoNK+eCs5GFhZwKBgQC1LBtldkHXnauSa28cEGjScZtdz3Qu2Mrc5RosrJf6kNQMhWaCYOzjMJNsiiIFHKu0WZFR49EKRqo1vdI+IPCIe/qqE7VpAFmN2LQsz8worQck03EBr62NHmRIW2OjYspvlyGSPxK2EjEXeIJcjwc9cAGSELYu4efUBng17pU6gQKBgQCxsKMES27M4pkPA65eveis8iyp+qftGWM81Z5yxCMBkpJYbhXjFZc0mTs8wuC6MiQ+X08FWQ1HdCGOalr6bgDmCEWo/3ZcOA7ebsl8BdkZrKuxOP4vqf3AOzqK0Pxl8Wx7xy4ROAccxc8A3r/9VnvhAPY7DX3Ipd12XKzrTrscgwKBgFCMCYDMMh1ERiImsa5DE4/ndTuI7rILr77JvkfuNbAqTbaF1C9AHYi4WYR/tqwmNRJySjCUuMKavgEdk3V12kz6uQKRqIKb81xClAuYloZufuGGx26wvFPVw6o7ykfbtaaDxdl2Ifplck18+Gu5hETPOdUoIltCGmtA6csNOfkJ"

        /*PKCS1(非Java适用)*/
//        val privateKey =
//            "MIIEpAIBAAKCAQEAswSYu13+yGlDdAUgAxKcJ81Edt04+CjJUuzqmYmO91ubXCcz7cwy6EHfkk++VuZLAXut/sfQa/jlScTOaUgJos67zWJIrifYc1VQqV3y7pG2HeVOJGAuXBzkPXRDXsIVAYRZRFxU++mI3lo8dvOvORWIO1xMH9TJjBzV0UR888qEXeHd1a80qqTVoKawfiy1nVremtbuJIbu5ZSpruM0RAu2rENg0qr4oHmI2bUq3vECrYYPp+kBbp81dDgQDycOrQPr7JEM1ucJZDz2zU0m2UxboNohjAizteoBkEaKj0503e2AUP09ie7knWoZxtPAzolugpbxT3AO1lgbHKL5pwIDAQABAoIBACs7efMN4SDfIvYa4r87OFD4ZodaJ47usqf/JSzNJqSgD+XyZb0Q6l5iwi5gabQsy4JhLwTvlmrs4dqt17dfOUyDJqGUEb9VShWYTfGJCckJkXSo0vU/8RI3u/sHX9m7fz0tjC1RKwjnPjGMkzua3/8mLw8+2/d3+PCIai6unop1P5febWzRHN4F44UpYobmSq/+ig9fvpOABBAa5eaD2twwWaLoWOhxxggHJTU5c4r375uVgI3QhNyzJa6HQtQAo4Bsg2XDiByD5DnKhS38SMnSNJ1RLhT7iFo3sjsxn9xHgY7XrIitPUkbCuXXRawzs28v36QB1q4YsBZmKr/OF4ECgYEA2NXvm2Y0QgKm08cx9Lir1Uyrx5zScnOO44dD9NlIo1Voa5v3eLfcCh+w2J1gWcGQHrGDOlwwM2zGBWeSi8NxD3CLdX8rjB8XlQs9NFB9yBKMzfEMVpYa5R4pOopPAPSqq+uyXLOCnmWrpmUVppl+EkRO42O9djvt0D/wIAwFPcECgYEA01oKqaS8SIruDMyMymOVZIYrryJ6l9DCWdCPJDEb7UYf29+P0/ho0/q9YNwIEh1nMH/Zz7n8JWFksKD8xkj4Rghe+U9gheCFxBAJyeMwiVSu2psm4K5QEGVyxlymiGR69k+nR0JJ4ybH2EFilBXXu9qMV7f4L46DSvngrORhYWcCgYEAtSwbZXZB152rkmtvHBBo0nGbXc90LtjK3OUaLKyX+pDUDIVmgmDs4zCTbIoiBRyrtFmRUePRCkaqNb3SPiDwiHv6qhO1aQBZjdi0LM/MKK0HJNNxAa+tjR5kSFtjo2LKb5chkj8SthIxF3iCXI8HPXABkhC2LuHn1AZ4Ne6VOoECgYEAsbCjBEtuzOKZDwOuXr3orPIsqfqn7RljPNWecsQjAZKSWG4V4xWXNJk7PMLgujIkPl9PBVkNR3Qhjmpa+m4A5ghFqP92XDgO3m7JfAXZGayrsTj+L6n9wDs6itD8ZfFse8cuETgHHMXPAN6//VZ74QD2Ow19yKXddlys6067HIMCgYBQjAmAzDIdREYiJrGuQxOP53U7iO6yC6++yb5H7jWwKk22hdQvQB2IuFmEf7asJjUSckowlLjCmr4BHZN1ddpM+rkCkaiCm/NcQpQLmJaGbn7hhsdusLxT1cOqO8pH27Wmg8XZdiH6ZXJNfPhruYREzznVKCJbQhprQOnLDTn5CQ=="


        try {

            val data = "测试提交数据"

            val publicEncryptBytes = encryptByPublicKey(data.toByteArray(), publicKey)
            println("公钥加密后的数据：" + Base64.encode(publicEncryptBytes))
            val privatDecryptBytes = decryptByPrivateKey(publicEncryptBytes, privateKey)
            println("私钥解密后的数据：" + String(privatDecryptBytes))


            println("--------------------")

            val privateKeyEncryptBytes = encryptByPrivateKey(data.toByteArray(), privateKey)
            println("私钥加密后的数据：" + Base64.encode(privateKeyEncryptBytes))

            val singnData = sign(data.toByteArray(), privateKey)
            println("私钥签名后的数据：" + singnData!!)


            val publicDecryptBytes = decryptByPublicKey(privateKeyEncryptBytes, publicKey)
            println("公钥解密后的数据：" + String(publicDecryptBytes))

            val isSign = verify(data.toByteArray(), publicKey, singnData)
            println("签名是否正确：$isSign")


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    /**
     * @param keySize 生成的秘钥长度  一般为1024或2048
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun genKeyPair(keySize: Int): Map<String, Any> {
        val keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM)
        keyPairGen.initialize(keySize)
        val keyPair = keyPairGen.generateKeyPair()
        val publicKey = keyPair.public as RSAPublicKey
        val privateKey = keyPair.private as RSAPrivateKey
        val keyMap = HashMap<String, Any>(2)
        keyMap[PUBLIC_KEY] = publicKey
        keyMap[PRIVATE_KEY] = privateKey

        println("publicKey：" + Base64.encode(publicKey.encoded))
        println("privateKey：" + Base64.encode(privateKey.encoded))

        return keyMap
    }


    /**
     * 对已加密数据进行签名
     *
     * @param data       已加密的数据
     * @param privateKey 私钥
     * @return 对已加密数据生成的签名
     * @throws Exception
     */

    @Throws(Exception::class)
    fun sign(data: ByteArray, privateKey: String): String? {
        val keyBytes = Base64.decode(privateKey)
        val pkcs8KeySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        val privateK = keyFactory.generatePrivate(pkcs8KeySpec)
        val signature = Signature.getInstance(SIGNATURE_ALGORITHM)
        signature.initSign(privateK)
        signature.update(data)

        return Base64.encode(signature.sign())
    }


    /**
     * 验签
     *
     * @param data      签名之前的数据
     * @param publicKey 公钥
     * @param sign      签名之后的数据
     * @return 验签是否成功
     * @throws Exception
     */
    @Throws(Exception::class)
    fun verify(data: ByteArray, publicKey: String, sign: String?): Boolean {
        val keyBytes = Base64.decode(publicKey)
        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        val publicK = keyFactory.generatePublic(keySpec)
        val signature = Signature.getInstance(SIGNATURE_ALGORITHM)
        signature.initVerify(publicK)
        signature.update(data)
        return signature.verify(Base64.decode(sign))
    }


    /**
     * 用私钥对数据进行解密
     *
     * @param encryptedData 使用公钥加密过的数据
     * @param privateKey    私钥
     * @return 解密后的数据
     * @throws Exception
     */
    @Throws(Exception::class)
    fun decryptByPrivateKey(encryptedData: ByteArray, privateKey: String): ByteArray {
        val keyBytes = Base64.decode(privateKey)
        val pkcs8KeySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        val privateK = keyFactory.generatePrivate(pkcs8KeySpec)
        //Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        val cipher = Cipher.getInstance(cipherMode)
        cipher.init(Cipher.DECRYPT_MODE, privateK)

        val inputLen = encryptedData.size
        val out = ByteArrayOutputStream()
        var offSet = 0
        var cache: ByteArray
        var i = 0
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK)
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet)
            }
            out.write(cache, 0, cache.size)
            i++
            offSet = i * MAX_DECRYPT_BLOCK
        }
        val decryptedData = out.toByteArray()
        out.close()


        return decryptedData
    }

    /**
     * 公钥解密
     *
     * @param encryptedData 使用私钥加密过的数据
     * @param publicKey     公钥
     * @return 解密后的数据
     * @throws Exception
     */
    @Throws(Exception::class)
    fun decryptByPublicKey(encryptedData: ByteArray, publicKey: String): ByteArray {
        val keyBytes = Base64.decode(publicKey)
        val x509KeySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        val publicK = keyFactory.generatePublic(x509KeySpec)
        val cipher = Cipher.getInstance(cipherMode)
        cipher.init(Cipher.DECRYPT_MODE, publicK)
        val inputLen = encryptedData.size
        val out = ByteArrayOutputStream()
        var offSet = 0
        var cache: ByteArray
        var i = 0
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK)
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet)
            }
            out.write(cache, 0, cache.size)
            i++
            offSet = i * MAX_DECRYPT_BLOCK
        }
        val decryptedData = out.toByteArray()
        out.close()
        return decryptedData
    }


    /**
     * 公钥加密
     *
     * @param data      需要加密的数据
     * @param publicKey 公钥
     * @return 使用公钥加密后的数据
     * @throws Exception
     */
    @Throws(Exception::class)
    fun encryptByPublicKey(data: ByteArray, publicKey: String): ByteArray {
        val keyBytes = Base64.decode(publicKey)
        val x509KeySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        val publicK = keyFactory.generatePublic(x509KeySpec)
        // 对数据加密
        val cipher = Cipher.getInstance(cipherMode)
        cipher.init(Cipher.ENCRYPT_MODE, publicK)
        val inputLen = data.size
        val out = ByteArrayOutputStream()
        var offSet = 0
        var cache: ByteArray
        var i = 0
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK)
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet)
            }
            out.write(cache, 0, cache.size)
            i++
            offSet = i * MAX_ENCRYPT_BLOCK
        }
        val encryptedData = out.toByteArray()
        out.close()
        return encryptedData
    }


    /**
     * 私钥加密
     *
     * @param data       待加密的数据
     * @param privateKey 私钥
     * @return 使用私钥加密后的数据
     * @throws Exception
     */
    @Throws(Exception::class)
    fun encryptByPrivateKey(data: ByteArray, privateKey: String): ByteArray {
        val keyBytes = Base64.decode(privateKey)
        val pkcs8KeySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(KEY_ALGORITHM)
        val privateK = keyFactory.generatePrivate(pkcs8KeySpec)
        val cipher = Cipher.getInstance(cipherMode)
        cipher.init(Cipher.ENCRYPT_MODE, privateK)
        val inputLen = data.size
        val out = ByteArrayOutputStream()
        var offSet = 0
        var cache: ByteArray
        var i = 0
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK)
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet)
            }
            out.write(cache, 0, cache.size)
            i++
            offSet = i * MAX_ENCRYPT_BLOCK
        }
        val encryptedData = out.toByteArray()
        out.close()
        return encryptedData
    }


    /**
     * 获取私钥
     *
     * @param keyMap 生成的秘钥对
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getPrivateKey(keyMap: Map<String, Any>): String? {
        val key = keyMap[PRIVATE_KEY] as Key?
        return Base64.encode(key!!.encoded)
    }


    /**
     * 获取公钥
     *
     * @param keyMap 生成的秘钥对
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getPublicKey(keyMap: Map<String, Any>): String? {
        val key = keyMap[PUBLIC_KEY] as Key?
        return Base64.encode(key!!.encoded)
    }


}