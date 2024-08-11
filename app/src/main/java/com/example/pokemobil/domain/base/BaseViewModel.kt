package com.example.pokemobil.domain.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemobil.domain.model.Resource
import com.example.pokemobil.domain.model.Status
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    fun <T> getApiCall(
        dataCall: suspend () -> Resource<T>,
        onSuccess: suspend (T?) -> Unit,
        onError: suspend () -> Unit,
        onLoading: suspend () -> Unit
    ) = viewModelScope.launch {
        when (dataCall().status) {
            Status.SUCCESS -> {
                onSuccess.invoke(dataCall().data)
            }

            Status.ERROR -> {
                onError.invoke()
            }

            Status.LOADING -> {
                onLoading.invoke()
            }
        }
    }
}



