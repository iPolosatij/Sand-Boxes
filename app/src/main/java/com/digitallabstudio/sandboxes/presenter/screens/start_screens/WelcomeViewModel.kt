package com.digitallabstudio.sandboxes.presenter.screens.start_screens

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.digitallabstudio.sandboxes.presenter.base.mvvm.BaseViewModel
import com.digitallabstudio.sandboxes.presenter.screens.errors.ErrorModel
import com.digitallabstudio.sandboxes.utils.auxiliary.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

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
    var counter = 0.00
    var start = false
    fun startTimer(){
        start = true
        CoroutineScope(Dispatchers.Default).launch {
            while (start){
                counter+=0.01
                delay(10)
                _message.postValue(Event(((counter*100.0).roundToInt()/100.0).toString()))
            }
        }
    }
    fun stopTimer(){
        start = false
    }

    private fun loadData() {}
}