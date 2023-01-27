package com.digitallabstudio.sandboxes.presenter.screens.start_screens

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.digitallabstudio.sandboxes.presenter.base.mvvm.BaseViewModel
import com.digitallabstudio.sandboxes.presenter.screens.errors.ErrorModel
import com.digitallabstudio.sandboxes.utils.auxiliary.Event
import kotlinx.coroutines.flow.*

class WelcomeViewModel(localContext: Context) : BaseViewModel() {

    private val _navigateCommander = MutableSharedFlow<AppDestination>()
    val navigateCommander: SharedFlow<AppDestination> = _navigateCommander.asSharedFlow()

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

    private val _message: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    init {
        loadData()
    }

    private fun loadData() {}
}