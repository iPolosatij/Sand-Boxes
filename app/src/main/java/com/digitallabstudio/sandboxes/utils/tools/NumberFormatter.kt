package com.digitallabstudio.sandboxes.utils.tools

object NumberFormatter {


    fun numberFormatToSend(string: String): String{
        val exitArray = ArrayList<Char>()
        for(char: Char in string.toCharArray()){
            if (char!=' ' && char!='-' && char!='(' && char!=')'){
                exitArray.add(char)
            }
        }
        return exitArray.toCharArray().concatToString()
    }

    fun numberFormatToSendWithoutPlus(string: String): String{
        val exitArray = ArrayList<Char>()
        for(char: Char in string.toCharArray()){
            if (char != '+' && char!=' ' && char!='-' && char!='(' && char!=')'){
                exitArray.add(char)
            }
        }
        return exitArray.toCharArray().concatToString()
    }
}