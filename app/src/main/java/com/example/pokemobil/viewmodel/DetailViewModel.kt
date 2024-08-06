package com.example.pokemobil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokemobil.model.DetailPokemonModel
import com.example.pokemobil.model.PokemonStatus
import com.example.pokemobil.model.Resource
import com.example.pokemobil.model.Status
import com.example.pokemobil.repository.BaseRepository
import com.example.pokemobil.service.RetrofitAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val retrofitAPI: RetrofitAPI
) : BaseViewModel() {

    private val _success = MutableLiveData<DetailPokemonModel>()
    val success: LiveData<DetailPokemonModel> get() = _success

    private val _loading = MutableLiveData<Resource<Any>>()
    val loading: LiveData<Resource<Any>> get() = _loading

    private val _error = MutableLiveData<Resource<Any>>()
    val error: LiveData<Resource<Any>> get() = _error

    fun getPokemon(
        pokemonName: String
    ) = viewModelScope.launch {
        _loading.postValue(Resource.loading(null))
        getApiCall(
            dataCall = { repository.fetchData { retrofitAPI.pokemonSearch(pokemonName) } },
            onSuccess = { data -> onSuccess(data) },
            onError = { onError() },
            onLoading = { onLoading() }
        )
    }

    private fun onSuccess(data: PokemonStatus) {
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
        _success.postValue(pokeModel)
    }

    private fun onError() {
        _error.postValue(Resource.error(null))

    }

    private fun onLoading() {
        _loading.postValue(Resource.loading(null))
    }
}