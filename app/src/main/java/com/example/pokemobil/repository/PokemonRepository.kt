package com.example.pokemobil.repository

import com.example.pokemobil.model.PokemonList
import com.example.pokemobil.model.Resource
import com.example.pokemobil.service.RetrofitAPI
import dagger.hilt.android.scopes.ActivityScoped

import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val retrofitAPI: RetrofitAPI
) {
    suspend fun getPokemonList(): Resource<PokemonList> {
        return try {
            val response = retrofitAPI.pokemonList()

            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(null)
            } else {
                return Resource.error(null)
            }
        } catch (e: Exception) {
            return Resource.error(null)
        }
    }
}