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


        /*RSA  1024 */
        //        String RSA_PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIarYvrIMZGHKa8f2E6ubg0//28R1zJ4ArD+XELXYvDrM8UBR42PqJCpjPN3hC91YAnnk2Y9U+X5o/rGxH5ZTZzYy+rkAmZFJa1fK2mWDxPYJoxH+DGHQc+h8t83BMB4pKqVPhcJVF6Ie+qpD5RFUU/e5iEz8ZZFDroVE3ubKaKwIDAQAB";
        //        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIhqti+sgxkYcprx/YTq5uDT//bxHXMngCsP5cQtdi8OszxQFHjY+okKmM83eEL3VgCeeTZj1T5fmj+sbEfllNnNjL6uQCZkUlrV8raZYPE9gmjEf4MYdBz6Hy3zcEwHikqpU+FwlUXoh76qkPlEVRT97mITPxlkUOuhUTe5sporAgMBAAECgYA0aSND37iifKUTaKOpXIKFoI23910EMAnrAXmaTIkafUBZjL7Ay0Q+QIcDHeGjgNlW9YvGXMbB5wMhMYKMgOUV1FpeqQdDslO4Z7zynRjkDJkjOKkE2/j10CvmNO8e2uCWKsYYUE9IyTkxcypjBCv16ifT0qmdxb7uKLccYI16eQJBANMutfNO/q7kUKiYvilBLN9+pZOg6eTmKmV0Xygoa3ClpQTfurwLA8W/Fv3oXnjHXTryNVHeoxSH69imo0RZ9kcCQQClXhMbXlfvl5iInmwziFhtYBztvkLuyQ084FgszR7iR0nuOWoURLQa5O7sLL724FNRlSvOCmmmWguh2vmQgRr9AkBDS5tHkWCvMqpRT3spgk9eWOlChgCCpKXV9qNsFJVILEDNsM28pnXpSd91wdp4+m7HHe/Hyv6EyFtrio50dYZ5AkAODVVwUO8GBArJKTUml+JzwOQUa8OCSQFf9+xmOjPypH4qySQzfrcTRfrrhM3haqSJ3TQwuP/LTAGLCnGEjwP9AkBqFFyrrQviPOhwel3NWjRv8mftOFgnm0Isk/NQJ4JtoahYvPDeUyP80WSuVWnPyV4zHz9Kw7BggYCPc4xZDACV";


        /*RSA 2048*/

        val publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAichGTEP0QFswnvn+ZAQrgGHM8VeDZLJuezGhgxh4d9SyRUfnIW/zefT71rwS4bZUs1MPxJwavOyxABJOHLuckdHXknCsGEWz78gsA6D0+O+9dl1gCZR29nnN/NlzmNbSjFnzvsTJYBlS88qSr35RXFE+6DM7uPsS8Fm2I+65FteJ8p2yMvpSg72QkIX8xvI1F1uwXrciIB+4u7uTozxIplMOo4a6uhAm3W+Kjpz3ni2btjGqHRbqb3ebSZyl+nFfnjQaBe3XyVxAWDSanjgFj/wbqbeug9FBs+nQFVPIZR9z0aE5Ndi5o3eSkV7HFmWpkxaiPZ0BLRK3XHMaBtuSpwIDAQAB"
        val privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCJyEZMQ/RAWzCe+f5kBCuAYczxV4Nksm57MaGDGHh31LJFR+chb/N59PvWvBLhtlSzUw/EnBq87LEAEk4cu5yR0deScKwYRbPvyCwDoPT47712XWAJlHb2ec382XOY1tKMWfO+xMlgGVLzypKvflFcUT7oMzu4+xLwWbYj7rkW14nynbIy+lKDvZCQhfzG8jUXW7BetyIgH7i7u5OjPEimUw6jhrq6ECbdb4qOnPeeLZu2MaodFupvd5tJnKX6cV+eNBoF7dfJXEBYNJqeOAWP/Bupt66D0UGz6dAVU8hlH3PRoTk12Lmjd5KRXscWZamTFqI9nQEtErdccxoG25KnAgMBAAECggEBAIPz1b88ZTMtIgdejA7lH3Q4Nbn8gc1yRPSet3uBd/3rKT/IeMZBHQBzaqxgOgUIRV3n8nXsun6sf2b+IOjLlErimH2agnZMauL85YokH/g4QU6WZl9GXBf41xmMd3SsZ8AadaEBfYoXNqZcHtcLNogfFwvx5QRnD+A3SoRnH8OLBeVvOEe4AqHLT2xEZ9TeCf3fJe0Rf0fUIbw7I5ioiRZV/ir0L1VM7+1k2JODUkdC2Luj5Tl3nl1Eg6EmkYCmGE1bip1NAatsfjPBLMF7XdPNjLboiffjgKVBOjb7Y9vL18BCoLtWeTT2GkMpi5Sr94T1te1Ox77dF4BP33Xn7eECgYEA1TNUrAQsh14NbbkwFtUHXS8/YXt81p9wbSpFBymIawF2Lkk0913TB4CHSun45LhYXjdZZxK/TgqC5EIq5v2RA0jY3cSxoqVe6RZKB04E8wszeJHiEJPdu2vFnpZh9iAyhswiM5FmuKZKoWsVc2SZrBXAI02smSn3lXYok1VBS3sCgYEApXEZS6gjUu4o7ZL53Ur1HDfi/nxpkxqrPh+D1HVYjzjT+4vTeZwtLXt2VCInPWNXH+f11mzhxIrLkI0jMcSCah81DuU8aFXnqvPuyFvt9uaQBYlVWBtkcGZyeaxHFrbfCyeu0jm7SfwmiIg12hKlIHtPTjEZQUX+kkWr8cdaZ8UCgYEAh0Pl+K09QzVc97yC0jmeTnTnlYWvksvdnKUw3nZvYtSukndH75nLhfr524HOs+5xwnUDd+3hCjaJDSEd7yf5lUfmr+1XdoXNTb0igrfxU/JLWbfU4geuqnaaDyACTxHmfLePC4C413ZJ61fxaCDvjsrN+JgTZanGt0EcRT3WC3kCgYEAgf5/GMJxlw0JXbs515a5R8Xl9358Whj/at3KcRsPTeIiNqnkrc54dR9ol60KViMDZ0+VDDobn5pLXzZ26/jzXD1PLHgU4gp18Q6glhAdx/3cNm11gLhtUCA/XLlwVjm0wggZRpgUQIr/IBKe9c3mr8IUS2Uq6e38nKRf+adhst0CgYAM4tvl+U1MPbbz3YzDv8QPepZ7Pglgdfxqfr5OkXA7jNhqTZjSq10B6oClGvirBo1m6f26F02iUKk1n67AuiLlTP/RRZHi1cfq6P9IaXl23PcxJfUMvIxQDS0U+UTFpNXryTw/qNAkSfufN48YzKdGvc8vHrYJyaeemaVlbdJOCw=="


        try {

            val data = "测试提交数据"

            val publicEncryptBytes = RSAUtils.encryptByPublicKey(data.toByteArray(), publicKey)
            println("公钥加密后的数据：" + Base64.encode(publicEncryptBytes))
            val privatDecryptBytes = RSAUtils.decryptByPrivateKey(publicEncryptBytes, privateKey)
            println("私钥解密后的数据：" + String(privatDecryptBytes))


            println("--------------------")

            val privateKeyEncryptBytes = RSAUtils.encryptByPrivateKey(data.toByteArray(), privateKey)
            println("私钥加密后的数据：" + Base64.encode(privateKeyEncryptBytes))

            val singnData = RSAUtils.sign(data.toByteArray(), privateKey)
            println("私钥签名后的数据：" + singnData!!)


            val publicDecryptBytes = RSAUtils.decryptByPublicKey(privateKeyEncryptBytes, publicKey)
            println("公钥解密后的数据：" + String(publicDecryptBytes))

            val isSign = RSAUtils.verify(data.toByteArray(), publicKey, singnData)
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

        println("RSA_PUB_KEY：" + Base64.encode(publicKey.encoded))
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