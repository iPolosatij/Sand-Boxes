package com.digitallabstudio.sandboxes.utils.cryptUtil

import android.util.Base64
import android.util.Log
import java.io.UnsupportedEncodingException
import java.security.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/*****************************************************************
 * CrossPlatform CryptUtil
 *
 *
 *
 * This cross platform CryptUtil uses AES 256 for encryption. This library can
 * be used for encryptoion and de-cryption of string on iOS, Android and Windows
 * platform.<br></br>
 * Features: <br></br>
 * 1. 256 bit AES encryption
 * 2. Random IV generation.
 * 3. Provision for SHA256 hashing of key.
 *
 *
 * @since 1.0
 * @author navneet
 */
class CryptUtil {
    /**
     * Encryption mode enumeration
     */

    private enum class EncryptMode {
        ENCRYPT, DECRYPT
    }

    // cipher to be used for encryption and decryption
    var _cx: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")

    // encryption key and initialization vector
    var _key: ByteArray = ByteArray(32)
    var _iv: ByteArray = ByteArray(16)

    /**
     *
     * @param _inputText
     * Text to be encrypted or decrypted
     * @param _encryptionKey
     * Encryption key to used for encryption / decryption
     * @param _mode
     * specify the mode encryption / decryption
     * @param _initVector
     * Initialization vector
     * @return encrypted or decrypted string based on the mode
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    @Throws(
        UnsupportedEncodingException::class,
        InvalidKeyException::class,
        InvalidAlgorithmParameterException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    private fun encryptDecrypt(
        _inputText: String, _encryptionKey: String,
        _mode: EncryptMode, _initVector: String
    ): String? {
        var _out: String? = "" // output string
        //_encryptionKey = md5(_encryptionKey);
        Log.d("CryptUtil", "key = $_encryptionKey")
        var len = _encryptionKey.toByteArray(charset("UTF-8")).size // length of the key	provided
        if (_encryptionKey.toByteArray(charset("UTF-8")).size > _key.size) len = _key.size
        var ivlen = _initVector.toByteArray(charset("UTF-8")).size
        if (_initVector.toByteArray(charset("UTF-8")).size > _iv.size) ivlen = _iv.size
        System.arraycopy(_encryptionKey.toByteArray(charset("UTF-8")), 0, _key, 0, len)
        System.arraycopy(_initVector.toByteArray(charset("UTF-8")), 0, _iv, 0, ivlen)
        //KeyGenerator _keyGen = KeyGenerator.getInstance("AES");
        //_keyGen.init(128);

        val keySpec = SecretKeySpec(_key, "AES")
        /* Create a new SecretKeySpec for the specified key data and algorithm name.*/
        val ivSpec = IvParameterSpec(_iv)
        /* Create a new IvParameterSpec instance with the bytes from the specified buffer iv used as initialization vector.*/

        // encryption
        if (_mode == EncryptMode.ENCRYPT) {
            /* Potentially insecure random numbers on Android 4.3 and older.
            Read https://android-developers.blogspot.com/2013/08/some-securerandom-thoughts.html
            for more info.*/
            _cx.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec) // Initialize this cipher instance
            val results = _cx.doFinal(_inputText.toByteArray(charset("UTF-8"))) // Finish
            // multi-part
            // transformation
            // (encryption)
            _out = Base64.encodeToString(results, Base64.NO_WRAP) // ciphertext
            // output
        }

        // decryption
        if (_mode == EncryptMode.DECRYPT) {
            _cx.init(Cipher.DECRYPT_MODE, keySpec, ivSpec) // Initialize this ipher instance
            val decodedValue = Base64.decode(
                _inputText.toByteArray(),
                Base64.NO_WRAP
            )
            val decryptedVal = _cx.doFinal(decodedValue) // Finish
            // multi-part
            // transformation
            // (decryption)
            _out = String(decryptedVal)
        }
        println(_out)
        return _out // return encrypted/decrypted string
    }

    /***
     * This function encrypts the plain text to cipher text using the key
     * provided. You'll have to use the same key for decryption
     *
     * @param _plainText
     * Plain text to be encrypted
     * @param _key
     * Encryption Key. You'll have to use the same key for decryption
     * @param _iv
     * initialization Vector
     * @return returns encrypted (cipher) text
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    @Throws(
        InvalidKeyException::class,
        UnsupportedEncodingException::class,
        InvalidAlgorithmParameterException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun encrypt(_plainText: String, _key: String, _iv: String): String? {
        return encryptDecrypt(_plainText, _key, EncryptMode.ENCRYPT, _iv)
    }

    /***
     * This funtion decrypts the encrypted text to plain text using the key
     * provided. You'll have to use the same key which you used during
     * encryprtion
     *
     * @param _encryptedText
     * Encrypted/Cipher text to be decrypted
     * @param _key
     * Encryption key which you used during encryption
     * @param _iv
     * initialization Vector
     * @return encrypted value
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    @Throws(
        InvalidKeyException::class,
        UnsupportedEncodingException::class,
        InvalidAlgorithmParameterException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun decrypt(_encryptedText: String, _key: String, _iv: String): String? {
        return encryptDecrypt(_encryptedText, _key, EncryptMode.DECRYPT, _iv)
    }

    init {
        // initialize the cipher with transformation AES/CBC/PKCS5Padding
        //256 bit key space
        //128 bit IV
    }

    companion object {
        /**
         * Note: This function is no longer used.
         * This function generates md5 hash of the input string
         * @param inputString
         * @return md5 hash of the input string
         */

        const val QR_CODE_ENCRYPTION_KEY = "BeNgxyccn0hozPi4OL6g7uGXbdzKC9kR"
        const val QR_CODE_ENCRYPTION_IV = "pjeGyQGrqykoesEg"

        fun md5(inputString: String): String {
            val MD5 = "MD5"
            try {
                // Create MD5 Hash
                val digest = MessageDigest
                    .getInstance(MD5)
                digest.update(inputString.toByteArray())
                val messageDigest = digest.digest()

                // Create Hex String
                val hexString = StringBuilder()
                for (aMessageDigest in messageDigest) {
                    var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                    while (h.length < 2) h = "0$h"
                    hexString.append(h)
                }
                return hexString.toString()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return ""
        }

        /***
         * This function computes the SHA256 hash of input string
         * @param text input text whose SHA256 hash has to be computed
         * @param length length of the text to be returned
         * @return returns SHA256 hash of input text
         * @throws NoSuchAlgorithmException
         * @throws UnsupportedEncodingException
         */
        @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
        fun SHA256(text: String, length: Int): String {
            val resultStr: String
            val md = MessageDigest.getInstance("SHA-256")
            md.update(text.toByteArray(charset("UTF-8")))
            val digest = md.digest()
            val result = StringBuffer()
            for (b in digest) {
                result.append(String.format("%02x", b)) //convert to hex
            }
            //return result.toString();
            resultStr = if (length > result.toString().length) {
                result.toString()
            } else {
                result.toString().substring(0, length)
            }
            return resultStr
        }

        /**
         * this function generates random string for given length
         * @param length
         * Desired length
         * * @return
         */
        fun generateRandomIV(length: Int): String {
            val ranGen = SecureRandom()
            val aesKey = ByteArray(16)
            ranGen.nextBytes(aesKey)
            val result = StringBuffer()
            for (b in aesKey) {
                result.append(String.format("%02x", b)) //convert to hex
            }
            return if (length > result.toString().length) {
                result.toString()
            } else {
                result.toString().substring(0, length)
            }
        }
    }
}