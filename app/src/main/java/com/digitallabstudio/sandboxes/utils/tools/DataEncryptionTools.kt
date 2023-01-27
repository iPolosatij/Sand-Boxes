package com.digitallabstudio.sandboxes.utils.tools

object DataEncryptionTools {

    const val USER_GET_KEY = "user_key"

    private fun stringEncryptDecrypting(string: String, encrypt: Boolean): String {
        var counter = 3
        val array = ArrayList<Byte>()
        val byteArray = string.toByteArray()
        for (cr in byteArray) {
            if (encrypt) {
                array.add((cr + counter/2).toByte())
                counter++
            } else {
                array.add((cr - counter/2).toByte())
                counter++
            }
            if (counter > 7) counter = 3
        }
        return String(array.toByteArray())
    }
}