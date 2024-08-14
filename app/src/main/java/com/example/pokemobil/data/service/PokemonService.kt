package com.example.pokemobil.data.service

import com.example.pokemobil.data.model.PokemonList
import com.example.pokemobil.data.model.PokemonStatus
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("/api/v2/pokemon")
    suspend fun pokemonList(
        @Query("limit") searchLimit : Int
    ): Response<PokemonList>

    @GET("/api/v2/pokemon/{id}/")
    suspend fun pokemonSearch(
        @Path("id") searchQuery: Int,
    ): Response<PokemonStatus>
}