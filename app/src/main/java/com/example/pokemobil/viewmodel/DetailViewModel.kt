package com.example.pokemobil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemobil.model.PokemonStatus
import com.example.pokemobil.model.Resource
import com.example.pokemobil.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _pokemon = MutableLiveData<Resource<PokemonStatus>>()
    val pokemon: LiveData<Resource<PokemonStatus>> get() = _pokemon

    fun getPokemon(pokemonName: String) = viewModelScope.launch {
        _pokemon.postValue(Resource.loading(null))
        val call = repository.getPokemon(pokemonName)
        call.let {
            _pokemon.postValue(it)
        }
    }
}