package com.example.pokemobil.viewmodel

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

open class BaseViewModel : ViewModel() {

    fun <T> getApiCall(
        dataCall: suspend () -> Resource<T>,
        onSuccess: suspend (T) -> Unit,
        onError: suspend () -> Unit,
        onLoading: suspend () -> Unit
    ) =
        viewModelScope.launch {
            when (dataCall().status) {
                Status.SUCCESS -> {
                    dataCall().data?.let { onSuccess.invoke(it) }
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



