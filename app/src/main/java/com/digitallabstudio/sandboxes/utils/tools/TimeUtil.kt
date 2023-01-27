package com.digitallabstudio.sandboxes.utils.tools

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    fun getCurrentDate():String{
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return dateFormat.format(Date())
    }

    fun getDateFromLongChatFormat(mils: Long): String{
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy HH:mm")
        return dateFormat.format(Date(mils))
    }

    fun getDay(mils: Long): String{
        val dateFormat = SimpleDateFormat("dd")
        return dateFormat.format(Date(mils))
    }
    fun getMonth(mils: Long): String{
        val dateFormat = SimpleDateFormat("MM")
        return dateFormat.format(Date(mils))
    }
    fun getYear(mils: Long): String{
        val dateFormat = SimpleDateFormat("yyyy")
        return dateFormat.format(Date(mils))
    }

    fun getDateFromLongUnitFormat(mils: Long): String{
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return dateFormat.format(Date(mils))
    }

    fun getDateFromLongAmPmFormat(mils: Long): String{
        val dateFormat = SimpleDateFormat("dd.MM.yyyy  aa_hh:mm")
        return dateFormat.format(Date(mils))
    }
}