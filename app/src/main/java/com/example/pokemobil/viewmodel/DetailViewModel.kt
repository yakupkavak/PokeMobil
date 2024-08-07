package com.example.pokemobil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemobil.model.DetailPokemonModel
import com.example.pokemobil.model.PokemonStatus
import com.example.pokemobil.model.Resource
import com.example.pokemobil.model.StatData
import com.example.pokemobil.repository.PokemonRepository
import com.example.pokemobil.util.getStat
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : BaseViewModel() {

    private val _success = MutableLiveData<DetailPokemonModel>()
    val success: LiveData<DetailPokemonModel> get() = _success

    private val _loading = MutableLiveData<Resource<Any>>()
    val loading: LiveData<Resource<Any>> get() = _loading

    private val _error = MutableLiveData<Resource<Any>>()
    val error: LiveData<Resource<Any>> get() = _error

    fun getPokemon(
        pokemonId: Int
    ) {
        _loading.postValue(Resource.loading(null))
        getApiCall(
            dataCall = { pokemonRepository.getPokemon(pokemonId) },
            onSuccess = { data -> onSuccess(data) },
            onError = {
                _error.postValue(Resource.error(null))
            },
            onLoading = { _loading.postValue(Resource.loading(null)) }
        )
    }

    private fun onSuccess(pokemonStatus: PokemonStatus?) {
        pokemonStatus?.let { status ->
            val name = status.name
            val animated = status.sprites.versions.generationV.blackWhite.animated
            val statList = status.stats.map { StatData(it.stat.name, it.baseStat) }
            val height = status.height.toString()
            val exp = status.baseExperience.toString()
            val heart = getStat("hp", statList)
            val sword = getStat("attack", statList)
            val guard = getStat("defense", statList)
            val sAttack = getStat("special-attack", statList)
            val sDefence = getStat("special-defense", statList)
            val speed = getStat("speed", statList)
            val pokeModel = DetailPokemonModel(
                name, animated, height, exp, heart, sword, guard, sAttack, sDefence, speed
            )
            _success.postValue(pokeModel)
        }
    }
}