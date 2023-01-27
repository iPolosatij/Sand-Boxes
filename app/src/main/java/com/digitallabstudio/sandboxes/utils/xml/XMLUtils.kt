package com.digitallabstudio.sandboxes.utils.xml

import android.util.Log

object XMLUtils {
    const val ARCHIVED = "archived"
    const val MESSAGE = "message"
    const val STANZA_ID = "stanza-id"
    const val INFO = "info"
    const val ID = "id"
    const val FILE = "file"
    const val URL = "url"
    const val NAME = "name"
    const val SIZE = "size"
    const val MIMETYPE = "mimetype"
    const val SENT = "sent"
    const val TOKEN = "token"
    const val LANG = "lang"

    fun extractStringsFromSpace(elementName: String, xml: String): String{
        val indicate = ("$elementName='").toCharArray()
        var block = false
        val outCharArray = ArrayList<Char>()
        var counter = 0
        for(cr in xml.toCharArray()){
            if(block){
                if(cr == "'".toCharArray()[0]) {
                   return String(outCharArray.toCharArray())
                }
                else outCharArray.add(cr)
            }else{
                if(indicate[counter] == cr){
                    if (counter == indicate.size-1){
                        block = true
                    }
                    counter++
                }else counter = 0
            }
        }
        return String(outCharArray.toCharArray())
    }

    fun extractElementFromXml(space: String, elementName: String, xml: String): String{
        val startIndicate = ("<$space").toCharArray()
        var block = false
        val outCharArray = ArrayList<Char>()
        var counter = 0
        for(cr in xml.toCharArray()){
            if(block){
                if(cr == ">".toCharArray()[0]) {
                    return extractStringsFromSpace(elementName, String(outCharArray.toCharArray()))
                }
                else outCharArray.add(cr)
            }else{
                if(startIndicate[counter] == cr){
                    if (counter == startIndicate.size-1){
                        block = true
                    }
                    counter++
                }else counter = 0
            }
        }
        return String(outCharArray.toCharArray())
    }


    fun getJidFromLongJid(lJid: String): String{
        val outArray = ArrayList<Char>()
        for (cr in lJid.toCharArray()){
            if (cr == '/') return String(outArray.toCharArray())
            outArray.add(cr)
        }
        return String(outArray.toCharArray())
    }

    fun xmlExtract(smackMessage: org.jivesoftware.smack.packet.Message) {
        smackMessage.toXML(null).let { xml->
            if (xml.isNotEmpty() && xml.isNotBlank()){
                // Log.d("XMLUtils ---> ",XMLUtils.extractElementFromXml("archived", "id", xml.toString()))
                Log.d("XML ---> ",xml.toString())
            }
        }
    }
}