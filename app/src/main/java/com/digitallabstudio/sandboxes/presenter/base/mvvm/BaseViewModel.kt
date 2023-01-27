package com.digitallabstudio.sandboxes.presenter.base.mvvm

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitallabstudio.sandboxes.presenter.screens.errors.ErrorModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _progressEvent: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val progressEvent: StateFlow<Boolean> = _progressEvent.asStateFlow()

    private val _errorEvent: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val errorEvent: Flow<ErrorModel> = _errorEvent.filterNotNull()

    suspend fun safeProgressHandler(
        scope: CoroutineScope = viewModelScope,
        progress: MutableStateFlow<Boolean> = _progressEvent,
        error: MutableSharedFlow<ErrorModel> = _errorEvent,
        block: suspend () -> Unit,
    ) {
        try {
            progress.value = true
            safeProgressHandler(
                process = progress,
                error = error,
                block = block,
                retryAction = {
                    scope.launch {
                        safeProgressHandler(scope, progress, error, block)
                    }
                }
            )
        } finally {
            progress.value = false
        }
    }

    private suspend fun safeProgressHandler(
        process: MutableStateFlow<Boolean> = _progressEvent,
        error: MutableSharedFlow<ErrorModel> = _errorEvent,
        block: suspend () -> Unit,
        retryAction: () -> Unit,
    ) {
        try {
            process.value = true
            safeErrorHandler(error, block, retryAction)
        } finally {
            process.value = false
        }
    }

    suspend fun safeErrorHandler(
        scope: CoroutineScope = viewModelScope,
        error: MutableSharedFlow<ErrorModel> = _errorEvent,
        block: suspend () -> Unit,
    ) {
        safeErrorHandler(
            error = error,
            block = block,
            retryAction = {
                scope.launch {
                    safeErrorHandler(scope, error, block)
                }
            }
        )
    }

    private suspend fun safeErrorHandler(
        error: MutableSharedFlow<ErrorModel> = _errorEvent,
        block: suspend () -> Unit,
        retryAction: () -> Unit,
    ) {
        try {
            block()
        } catch (ex: Exception) {
            error.emit(ErrorModel(ex, retryAction))
            Log.d("ArcTour_Log", "SafeHandler Error: $ex")
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}