package com.example.saverx.app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saverx.app.adapters.LinkAdapter
import com.example.saverx.app.model.LinkModelApp
import domain.DataRepositoryDomain
import domain.VKGetDataUseCase
import domain.model.LinkModelDomain
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(private val vkGetData: VKGetDataUseCase, dataRepos: DataRepositoryDomain) :
    ViewModel() {

    private val _linkAdapter = MutableLiveData<LinkAdapter>()
    val linkAdapter: LiveData<LinkAdapter> = _linkAdapter

    private val _activityState = MutableLiveData<Int>()
    val activityState: LiveData<Int> = _activityState

    private val _resultLiveData = MutableLiveData<Boolean>()
    val resultLiveData: LiveData<Boolean> = _resultLiveData

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> = _progress

    val downloadProgress: StateFlow<Int> = dataRepos.downloadProgress

    init {
        _activityState.value = 100
        _linkAdapter.value = LinkAdapter { item -> onItemClicked(item) }


    }

    fun fetchData(data: LinkModelApp) {
        viewModelScope.launch(Dispatchers.IO) {
            vkGetData.execute(LinkModelDomain(data.link, data.fileName), object : VKGetDataUseCase.Callback {
                override fun onProgressUpdate(progress: Int) {
                    _progress.postValue(progress)
                }

                override fun onSuccess(result: Boolean) {
                    _resultLiveData.postValue(result)
                }

                override fun onError(error: Exception?) {
                    Log.e("TAG", "РЕЗ: ошибка $error")
                }
            })
        }
    }

    fun onItemClicked(item: Int) {
        _activityState.value = item
    }
}