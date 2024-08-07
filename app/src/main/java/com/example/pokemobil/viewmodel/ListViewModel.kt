package com.example.pokemobil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokemobil.model.PokemonList
import com.example.pokemobil.model.PokemonNameUrl
import com.example.pokemobil.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : BaseViewModel() {

    init {
        getList()
    }

    private val _success = MutableLiveData<List<PokemonNameUrl>>()
    val success: LiveData<List<PokemonNameUrl>> get() = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    fun getList() = viewModelScope.launch {
        getApiCall(dataCall = { pokemonRepository.getPokemonList() },
            onSuccess = { data -> onSuccess(data) },
            onError = { _error.postValue(true) },
            onLoading = { _loading.postValue(true) })
    }

    private fun onSuccess(pokemonList: PokemonList?) {
        pokemonList?.let { data ->
            val pokeList = data.results.map { PokemonNameUrl(it.name,it.url) }
            _success.postValue(pokeList)
        }
    }
}