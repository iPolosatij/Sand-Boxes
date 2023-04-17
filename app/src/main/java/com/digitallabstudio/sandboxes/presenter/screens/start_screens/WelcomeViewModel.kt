package com.digitallabstudio.sandboxes.presenter.screens.start_screens

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.digitallabstudio.sandboxes.data.room.data.Bd_data
import com.digitallabstudio.sandboxes.presenter.base.mvvm.BaseViewModel
import com.digitallabstudio.sandboxes.presenter.screens.errors.ErrorModel
import com.digitallabstudio.sandboxes.utils.auxiliary.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.dlsunity.arctour.domain.usecases.bd.DeleteAllBdDataUseCase
import space.dlsunity.arctour.domain.usecases.bd.GetBdDataUseCase
import space.dlsunity.arctour.domain.usecases.bd.SaveBdDataUseCase

class WelcomeViewModel(
    val localContext: Context,
    val deleteAllBdDataUseCase: DeleteAllBdDataUseCase,
    val saveBdDataUseCase: SaveBdDataUseCase,
    val getAllBdDataUseCase: GetBdDataUseCase
) : BaseViewModel() {

    private val _navigateCommander = MutableSharedFlow<AppDestination>()
    val navigateCommander: SharedFlow<AppDestination> = _navigateCommander.asSharedFlow()

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

    private val _message: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    private val _data: MutableLiveData<Event<List<Bd_data>>> = MutableLiveData<Event<List<Bd_data>>>()
    val data: LiveData<Event<List<Bd_data>>>
        get() = _data

    init {
        loadData()
    }
    fun saveListData(list: List<Bd_data>){
        try {
            for (item in list){
                saveData(item)
            }
        }catch (e: Exception){

        }
        CoroutineScope(Dispatchers.Default).launch {
            delay(3000)
            getData()
        }
    }
    fun getData(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                getAllBdDataUseCase.invoke().let {
                    _data.postValue(Event(it))
                }
            }catch (e: Exception){
                _message.postValue(Event(e.stackTraceToString()))
            }
        }
    }

    private fun saveData(bdData: Bd_data){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                saveBdDataUseCase.invoke(bdData)
            }catch (e: Exception){
                _message.postValue(Event(e.stackTraceToString()))
            }
        }
    }

    private fun loadData() {}
}