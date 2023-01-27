package com.digitallabstudio.sandboxes.utils.intent

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import com.digitallabstudio.sandboxes.utils.extensions.FileType

object AppIntent {

    fun openSettingsApp(context: Context) {
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            addCategory(Intent.CATEGORY_DEFAULT)
            data = Uri.parse("package:" + context.packageName)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        }
        try {
            context.startActivity(intent)
        } catch (ex: Exception) {
            Log.d("Error open settings app" ,  ex.localizedMessage)
        }
    }

    fun openFile(context: Context, uri: Uri, type: FileType) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, getIntentType(type))

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

        try {
            context.startActivity(intent)
        } catch (ex: Exception) {
            Log.d("Error open file", ex.localizedMessage)
        }
    }

    private fun getIntentType(type: FileType): String {
        return when (type) {
            FileType.Image -> "image/jpeg"
            FileType.Pdf -> "application/pdf"
            FileType.Xls, FileType.Xlsx -> "application/vnd.ms-excel"
            FileType.Video -> "video/*"
            FileType.Audio -> "audio/x-wav"
            FileType.Doc, FileType.Docx -> "application/msword"
            FileType.Ppt, FileType.Pptx -> "application/vnd.ms-powerpoint"
            FileType.Txt -> "text/plain"
            FileType.Unknown -> "*/*"
        }
    }
}
