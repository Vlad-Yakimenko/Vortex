package com.e.vortex.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.vortex.common.ApiUtils
import com.e.vortex.data.api.VortexApi
import com.e.vortex.data.database.VortexDao
import com.e.vortex.model.Response
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ViewModel(val vortexDao: VortexDao) : ViewModel() {

    private lateinit var disposable: Disposable

    val isLoaded: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorVisible: MutableLiveData<Boolean> = MutableLiveData(false)
    val response: LiveData<Response> = vortexDao.getResponse()
    private val api: VortexApi = ApiUtils.apiService

    init {
        updateResponse()
    }

    private fun updateResponse() {
        disposable = api.getData()
            .doFinally { isLoaded.postValue(true) }
            .doOnSuccess { isErrorVisible.postValue(false) }
            .subscribeOn(Schedulers.io())
            .subscribe(
                { vortexDao.insertResponse(it) }, // OnSuccess
                { isErrorVisible.postValue(true) } // OnError
            )
    }
}