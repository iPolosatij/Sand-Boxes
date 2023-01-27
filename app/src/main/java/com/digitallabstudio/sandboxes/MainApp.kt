package com.digitallabstudio.sandboxes

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LifecycleObserver
import androidx.multidex.MultiDexApplication
import com.digitallabstudio.sandboxes.di.presentationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import space.dlsunity.arctour.di.dataModule
import space.dlsunity.arctour.di.domainModule
import space.dlsunity.arctour.di.networkModule

class MainApp : MultiDexApplication(), LifecycleObserver {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    var sApplication = this

    fun getContext(): Application {
        return sApplication
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this
        setUpLightMode()
        setUpVectorCompat()
        setUpKoin()
        setUpLogger()
        setUpNotificationChannel()
        runChatService(applicationContext)
    }

    private fun setUpLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun setUpVectorCompat() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    private fun setUpKoin() {
        startKoin {
            androidContext(sApplication)
            modules(
                listOf(
                    networkModule(
                        serverUrl = Config.BASE_URL,
                        uploadTimeOut = Config.OKHTTP_TIMEOUT_60S,
                    ),
                    dataModule,
                    domainModule,
                    presentationModule
                )
            )
        }
    }

    private fun runChatService(context: Context) {
        //val intent = Intent(context, ChatService::class.java)
       // context.startService(intent)
    }

    private fun setUpLogger() {
    }

    private fun setUpNotificationChannel() {
    }
}
