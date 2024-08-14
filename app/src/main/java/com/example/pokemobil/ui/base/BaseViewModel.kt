package com.example.pokemobil.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemobil.data.util.Resource
import com.example.pokemobil.data.util.Status
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



