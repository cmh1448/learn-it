package github.cmh1448.backend.common.utils


import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

object AES256Utils {
    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"

    fun generateKey(): SecretKey {
        val keyBytes = ByteArray(32)
        Random.Default.nextBytes(keyBytes)
        return SecretKeySpec(keyBytes, ALGORITHM)
    }

    fun generateIv(): IvParameterSpec {
        val iv = ByteArray(16)
        Random.nextBytes(iv)
        return IvParameterSpec(iv)
    }

    fun encrypt(plainText: String, key: SecretKey, iv: IvParameterSpec): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        return cipher.doFinal(plainText.toByteArray())
    }

    fun decrypt(cipherText: ByteArray, key: SecretKey, iv: IvParameterSpec): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        return cipher.doFinal(cipherText).toString(Charsets.UTF_8)
    }
}