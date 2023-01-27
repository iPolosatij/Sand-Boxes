package com.digitallabstudio.sandboxes.utils.tools

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.digitallabstudio.sandboxes.utils.auxiliary.Event


object MyPermissionManager {

    const val WRITE_STORAGE_REQUEST = 101
    const val READ_STORAGE_REQUEST = 102
    const val MANAGE_STORAGE_REQUEST = 103

    private val _requestStatus: MutableLiveData<Event<Int>> = MutableLiveData<Event<Int>>()
    val requestStatus: LiveData<Event<Int>>
        get() = _requestStatus

    fun requestStorageWritePermission(act: AppCompatActivity){
        act.requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_STORAGE_REQUEST)
    }

    fun requestStorageReadPermission(act: AppCompatActivity){
        act.requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_STORAGE_REQUEST)
    }

    fun requestStorageManagePermission(act: AppCompatActivity){
        requestPermissions(act, MANAGE_STORAGE_REQUEST)
    }

    fun hasPermissions(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            Environment.isExternalStorageManager()
        else
            ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    fun publicRequestCode(code: Int){
        _requestStatus.postValue(Event(code))
    }

    private fun requestPermissions(activity: AppCompatActivity, requestCode: Int) {
        try {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.addCategory("android.intent.category.DEFAULT")
            intent.data = Uri.parse(String.format("package:%s", activity.packageName))
            activity.startActivityForResult(intent, requestCode)
        } catch (e: Exception) {
            val intent = Intent()
            intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
            activity.startActivityForResult(intent, requestCode)
        }
    }
}