package com.example.pokemobil.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemobil.model.Resource
import com.example.pokemobil.model.Status
import com.example.pokemobil.repository.BaseRepository
import com.example.pokemobil.service.RetrofitAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
open class BaseViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val retrofitAPI: RetrofitAPI
) : ViewModel() {

    fun <T> getApiCall(dataCall: suspend () -> Response<T>) = viewModelScope.launch {
        val call = repository.fetchData(dataCall)
        when (call.status) {
            Status.SUCCESS -> {
                onSuccess(call)
            }

            Status.ERROR -> {
                onError()
            }

            Status.LOADING -> {
                onLoading()
            }
        }
    }
    open fun <T> onSuccess(data: Resource<T>) {
    }

    open fun onError() {
    }

    open fun onLoading() {
    }
}



