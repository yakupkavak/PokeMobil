package com.example.pokemobil.repository

import com.example.pokemobil.model.PokemonList
import com.example.pokemobil.model.Resource
import com.example.pokemobil.service.RetrofitAPI
import com.example.pokemobil.util.const.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonRepository {

    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(RetrofitAPI::class.java)

    suspend fun getPokemonList(): Resource<PokemonList> {
        val response = service.pokemonList()
        if (response.) {
            response.body()?.let {
                return@let Resource.success(it)
            }
        } else {
            return Resource.error(null)

        }
    }
}