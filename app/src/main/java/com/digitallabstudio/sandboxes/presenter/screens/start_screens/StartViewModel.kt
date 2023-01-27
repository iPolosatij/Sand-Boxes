package com.digitallabstudio.sandboxes.presenter.screens.start_screens

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.digitallabstudio.sandboxes.presenter.base.mvvm.BaseViewModel
import com.digitallabstudio.sandboxes.presenter.screens.errors.ErrorModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StartViewModel(localContext: Context) : BaseViewModel() {

    private val _navigateCommander = MutableSharedFlow<AppDestination>()
    val navigateCommander: SharedFlow<AppDestination> = _navigateCommander.asSharedFlow()

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

    init {
        navigateToWelcome()
    }

    private fun navigateToWelcome() {
        viewModelScope.launch {
            delay(3000)
            _navigateCommander.emit(AppDestination.ToWelcome)
        }
    }

}