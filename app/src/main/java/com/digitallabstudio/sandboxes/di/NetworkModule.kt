package space.dlsunity.arctour.di

import android.content.Context
import android.net.ConnectivityManager
import com.digitallabstudio.sandboxes.data.network.NetFactories
import com.digitallabstudio.sandboxes.data.network.api.ApiService
import com.digitallabstudio.sandboxes.data.network.cache.ApplicationCacheStorage
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module

fun networkModule(
    serverUrl: String,
    uploadTimeOut: Long,
) = module {


    single<ApiService> {
        NetFactories.createApiService(
            url = serverUrl,
            okHttpClient = get<OkHttpClient>()
        )
    }

    factory<Interceptor> {
        NetFactories.createNetworkErrorInterceptor(
            errorHandler = NetFactories.createNetworkErrorHandler(
                moshi = get<Moshi>()
            ),
            applicationCacheStorage = get<ApplicationCacheStorage>()
        )
    }

    factory<Moshi> {
        NetFactories.createMoshi()
    }

    single<ConnectivityManager> {
        get<Context>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    factory<OkHttpClient>{
        NetFactories.createOkHttpClient(
            readTimeout = uploadTimeOut,
            writeTimeout = uploadTimeOut,
            connectTimeout = uploadTimeOut,
            errorInterceptor = get<Interceptor>()
        )
    }
}