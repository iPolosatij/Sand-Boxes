package com.digitallabstudio.sandboxes.utils.tools

object ContentChecker {

    fun checkName(name: String): Boolean{
        val checkArray = name.toCharArray()
        return if(checkArray.isNotEmpty()){
            checkArray.size in 3..44
        }else false
    }

    fun checkEmail(mail: String): Boolean{
        val checkArray = mail.toCharArray()
        return if(checkArray.isNotEmpty()){
            checkArray.contains('@') && checkArray.contains('.') && checkArray.size > 4
        }else false
    }
}