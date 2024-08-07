package com.example.pokemobil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemobil.model.DetailPokemonModel
import com.example.pokemobil.model.PokemonStatus
import com.example.pokemobil.model.Resource
import com.example.pokemobil.model.StatData
import com.example.pokemobil.repository.BaseRepository
import com.example.pokemobil.repository.PokemonRepository
import com.example.pokemobil.service.PokemonService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val pokemonService: PokemonService
) : BaseViewModel() {

    private val _success = MutableLiveData<DetailPokemonModel>()
    val success: LiveData<DetailPokemonModel> get() = _success

    private val _loading = MutableLiveData<Resource<Any>>()
    val loading: LiveData<Resource<Any>> get() = _loading

    private val _error = MutableLiveData<Resource<Any>>()
    val error: LiveData<Resource<Any>> get() = _error

    fun getPokemon(
        pokemonName: String
    ) {
        _loading.postValue(Resource.loading(null))
        getApiCall(
            dataCall = { pokemonRepository.getPokemon(pokemonName)},
            onSuccess = { data -> onSuccess(data) },
            onError = {
                _error.postValue(Resource.error(null))
            },
            onLoading = { _loading.postValue(Resource.loading(null)) }
        )
    }

    private fun onSuccess(pokemonStatus: PokemonStatus?) {
        pokemonStatus?.let { status ->
            val animated = status.sprites.versions.generationV.blackWhite.animated
            val statList = status.stats.map { StatData(it.stat.name, it.baseStat) }
            //val heartt = statList.find { it.statName == "heart" }
            val height = status.height.toString()
            val exp = status.baseExperience.toString()
            val heart = status.stats[0].baseStat.toString()
            val sword = status.stats[1].baseStat.toString()
            val guard = status.stats[2].baseStat.toString()
            val sAttack = status.stats[3].baseStat.toString()
            val sDefence = status.stats[4].baseStat.toString()
            val speed = status.stats[5].baseStat.toString()
            val pokeModel = DetailPokemonModel(
                animated, height, exp, heart, sword, guard, sAttack, sDefence, speed
            )
            _success.postValue(pokeModel)
        }
    }
}