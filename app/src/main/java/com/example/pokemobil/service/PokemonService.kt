package com.example.pokemobil.service

import com.example.pokemobil.model.PokemonList
import com.example.pokemobil.model.PokemonStatus
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("/api/v2/pokemon?limit=200")
    suspend fun pokemonList(
    ): Response<PokemonList>

    @GET("/api/v2/pokemon/{name}/")
    suspend fun pokemonSearch(
        @Path("name") searchQuery: String,
    ): Response<PokemonStatus>
}