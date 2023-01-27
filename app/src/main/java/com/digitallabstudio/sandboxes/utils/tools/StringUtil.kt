package com.digitallabstudio.sandboxes.utils.tools

import android.content.Context
import android.util.Log
import com.digitallabstudio.sandboxes.R
import java.text.DecimalFormat

object StringUtil {
    private const val TAG = "STRING_UTIL"
    fun isNullOrWhiteSpace(value: String?): Boolean {
        return value == null || value.trim { it <= ' ' }.isEmpty()
    }

    /*
	 * The memberId is supposed to be minimum '1000' (4 digits)
	 */
    fun getSecuredMemberId(memberId: String): String? {
        if (isNullOrWhiteSpace(memberId)) {
            return null
        }
        val cleanMemberId = memberId.trim { it <= ' ' }
        val securedMemberId = StringBuilder()
        securedMemberId.append(cleanMemberId.substring(0, 1)) // show first digit
        val defaultMemberMinimumDigits = 4
        val cleanMemberIdLength = cleanMemberId.length
        if (cleanMemberIdLength == defaultMemberMinimumDigits) {
            securedMemberId.append("**")
            securedMemberId.append(cleanMemberId.substring(3))
        } else {
            val minimumAllowedDigitgsAtTheFront = 1 // digits visible at the front
            val minimumAllowedDigitgsAtTheEnd = 1 // digits visible at the end
            val differenceFromMinimum = cleanMemberIdLength - defaultMemberMinimumDigits
            val completePairsFromDifference =
                differenceFromMinimum / 2 // every pair will add a new visible digit at the end
            val totalAllowedDigitsAtTheEnd =
                minimumAllowedDigitgsAtTheEnd + completePairsFromDifference
            val totalSecuredDigits =
                cleanMemberIdLength - totalAllowedDigitsAtTheEnd - minimumAllowedDigitgsAtTheFront
            for (i in 0 until totalSecuredDigits) {
                securedMemberId.append("*")
            }
            securedMemberId.append(cleanMemberId.substring(minimumAllowedDigitgsAtTheFront + totalSecuredDigits))
        }
        return securedMemberId.toString()
    }

    fun getOrdinal(number: Int, context: Context): String {
        val locale = context.resources.configuration.locale
        Log.d(TAG, locale.language)
        return if (locale.language == "ru") {
            when (number) {
                1 -> "1ый"
                2 -> "2ой"
                3 -> "3ий"
                4 -> "4ый"
                5 -> "5ый"
                6 -> "6ой"
                7 -> "7ой"
                8 -> "8ой"
                9 -> "9ый"
                10 -> "10ый"
                11 -> "11ый"
                12 -> "12ый"
                13 -> "13ый"
                14 -> "14ый"
                15 -> "15ый"
                16 -> "16ый"
                17 -> "17ый"
                18 -> "18ый"
                19 -> "19ый"
                20 -> "20ый"
                else -> number.toString() + "ый"
            }
        } else {
            if (number == 1) {
                context.getString(R.string.step_club_ordinal_1)
            } else if (number == 2) {
                context.getString(R.string.step_club_ordinal_2)
            } else if (number == 3) {
                context.getString(R.string.step_club_ordinal_3)
            } else {
                context.getString(R.string.step_club_ordinal_other, number)
            }
        }
    }

    fun isUrl(path: String): Boolean {
        return path.contains("https://")
    }

    fun textCropping(inString: String, length: Int): String{

        return if(inString.length<length){
            inString
        }else{
            inString.substring(0, length) + "..."
        }
    }

    fun fileNameFromUrl(url: String): String{
        return if (url.contains("/")) {
            url.split("/").toTypedArray().last()
        }else{
            ""
        }
    }

    fun cleanFileUrl(url: String): String{
        var outString = ""
        val array = ArrayList<Char>()
        for (cr in url.toCharArray()){
            if (cr != '"') array.add(cr)
        }
        outString = String(array.toCharArray())
        return outString
    }

    fun getIdFromJid(jid: String): String{
        return if (jid.contains("@")) {
            jid.split("@").toTypedArray()[0]
        }else{
            ""
        }
    }

    fun getFileSizeFromBytes(fileSize: String): String {
        if(fileSize.isEmpty()) return ""
        val df = DecimalFormat("#.##")
        val mSize: Double = fileSize.toDouble()/1024
        val outString = if (mSize / 1024 >= 1) {
            if (mSize / 1024 >= 1024) {
                df.format(mSize / 1024/ 1024) + " Gb"
            } else {
                df.format(mSize / 1024) + " Mb"
            }
        } else {
            df.format(mSize) + "Kb"
        }
        return outString
    }
}
