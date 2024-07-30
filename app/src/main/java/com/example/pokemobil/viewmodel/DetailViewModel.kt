package com.example.pokemobil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemobil.model.PokemonList
import com.example.pokemobil.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {

    /*
    private val _pokemonList = MutableLiveData<Resource<PokemonList>>()
    val pokemonList: LiveData<Resource<PokemonList>> get() = _pokemonList

    fun getList() = viewModelScope.launch {
        _pokemonList.postValue(Resource.loading(null))
        val call = repository.getPokemonList()
        call.let {
            _pokemonList.postValue(it)
        }
    }

     */
}