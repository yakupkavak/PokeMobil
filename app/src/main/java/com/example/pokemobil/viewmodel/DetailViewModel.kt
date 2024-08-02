package com.example.pokemobil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemobil.model.DetailPokemonModel
import com.example.pokemobil.model.PokemonStatus
import com.example.pokemobil.model.Resource
import com.example.pokemobil.model.Status
import com.example.pokemobil.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _success = MutableLiveData<Resource<PokemonStatus>>()
    val success: LiveData<Resource<PokemonStatus>> get() = _success

    private val _loading = MutableLiveData<Resource<PokemonStatus>>()
    val loading: LiveData<Resource<PokemonStatus>> get() = _loading

    private val _error = MutableLiveData<Resource<PokemonStatus>>()
    val error: LiveData<Resource<PokemonStatus>> get() = _error

    fun getPokemon(
        onSuccess: (DetailPokemonModel) -> Unit, pokemonName: String
    ) = viewModelScope.launch {
        val call = repository.getPokemon(pokemonName)
        when (call.status) {
            Status.SUCCESS -> {
                _success.postValue(call)
                call.data?.let { data ->
                    val animated = data.sprites.versions.generationV.blackWhite.animated
                    val height = data.height.toString()
                    val exp = data.base_experience.toString()
                    val heart = data.stats[0].base_stat.toString()
                    val sword = data.stats[1].base_stat.toString()
                    val guard = data.stats[2].base_stat.toString()
                    val sAttack = data.stats[3].base_stat.toString()
                    val sDefence = data.stats[4].base_stat.toString()
                    val speed = data.stats[5].base_stat.toString()
                    val pokeModel = DetailPokemonModel(
                        animated, height, exp, heart, sword, guard, sAttack, sDefence, speed
                    )
                    onSuccess.invoke(pokeModel)
                }
            }

            Status.ERROR -> {
                _error.postValue(call)
            }

            Status.LOADING -> {
                _loading.postValue(call)
            }
        }
    }
}