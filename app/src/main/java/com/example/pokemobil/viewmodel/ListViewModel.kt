package com.example.pokemobil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemobil.model.Status
import com.example.pokemobil.repository.BaseRepository
import com.example.pokemobil.service.RetrofitAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val retrofitAPI: RetrofitAPI
) : ViewModel() {

    private val _pokemonListSuccess = MutableLiveData<List<String>>()
    val pokemonListSuccess: LiveData<List<String>> get() = _pokemonListSuccess

    init {
        getList()
    }

    private var setOnSuccess: ((List<String>) -> Unit)? = null

    private var setOnError: (() -> Unit)? = null

    private var setOnLoading: (() -> Unit)? = null

    fun setStatus(success: ((List<String>) -> Unit), error: (() -> Unit), loading: (() -> Unit)) {
        setOnSuccess = success
        setOnError = error
        setOnLoading = loading
    }

    fun getList() = viewModelScope.launch {
        setOnLoading?.invoke()
        val call = repository.fetchData {retrofitAPI.pokemonList()  }
        when (call.status) {
            Status.SUCCESS -> {
                call.data?.let { data ->
                    val pokeList = data.results.map { pokemonResult -> pokemonResult.name }
                    setOnSuccess?.invoke(pokeList)
                    _pokemonListSuccess.postValue(pokeList)
                }
            }

            Status.ERROR -> {
                setOnError?.invoke()
            }

            Status.LOADING -> {
                setOnLoading?.invoke()
            }
        }
    }
}