package com.example.pokemobil.service

import com.example.pokemobil.model.PokemonList
import com.example.pokemobil.model.PokemonStatus
import com.example.pokemobil.model.Resource
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitAPI {

    @GET("/api/v2/pokemon?limit=100")
    suspend fun pokemonList(
    ): Resource<PokemonList>

    @GET("/api/v2/pokemon/{name}/")
    suspend fun pokemonSearch(
        @Path("name") searchQuery: String,
    ): Resource<PokemonStatus>
    
}