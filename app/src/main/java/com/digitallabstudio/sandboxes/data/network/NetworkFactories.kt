package com.digitallabstudio.sandboxes.data.network

import android.util.Log
import com.digitallabstudio.sandboxes.BuildConfig
import com.digitallabstudio.sandboxes.data.network.api.ApiService
import com.digitallabstudio.sandboxes.data.network.api.converters.DateTimeAdapter
import com.digitallabstudio.sandboxes.data.network.cache.ApplicationCacheStorage
import com.digitallabstudio.sandboxes.data.network.error.NetworkErrorHandler
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.dlsunity.arctour.data.network.api.converters.LocalDateTimeAdapter
import space.dlsunity.arctour.data.network.error.NetworkErrorInterceptor
import java.util.concurrent.TimeUnit


internal object NetFactories {

    @JvmStatic
    fun createApiService(url: String, okHttpClient: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @JvmStatic
    fun createNetworkErrorInterceptor(
        errorHandler: NetworkErrorHandler,
        applicationCacheStorage: ApplicationCacheStorage
    ): Interceptor {
        return NetworkErrorInterceptor(
            errorHandler = errorHandler,
            applicationCacheStorage = applicationCacheStorage
        )
    }

    @JvmStatic
    fun createNetworkErrorHandler(moshi: Moshi): NetworkErrorHandler {
        return NetworkErrorHandler(moshi = moshi)
    }

    @JvmStatic
    private fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Log.d("OkHttp", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @JvmStatic
    fun createMoshi(): Moshi {
        return Moshi.Builder()
            .add(DateTimeAdapter())
            .add(LocalDateTimeAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @JvmStatic
    fun createOkHttpClient(
        readTimeout: Long,
        writeTimeout: Long,
        connectTimeout: Long,
        errorInterceptor: Interceptor,
    ): OkHttpClient {
        val builder = createBaseOkHttpClient(readTimeout, writeTimeout, connectTimeout)
            .addInterceptor(errorInterceptor)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(createHttpLoggingInterceptor())

        }

        return builder.build()
    }

    private fun createBaseOkHttpClient(
        readTimeout: Long,
        writeTimeout: Long,
        connectTimeout: Long
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
    }
}
