package com.example.pokemobil.data.service

import com.example.pokemobil.domain.model.PokemonList
import com.example.pokemobil.domain.model.PokemonStatus
import com.example.pokemobil.domain.util.ServiceCountConst.MaxServiceCount
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("/api/v2/pokemon?limit=${MaxServiceCount}")
    suspend fun pokemonList(
    ): Response<PokemonList>

    @GET("/api/v2/pokemon/{id}/")
    suspend fun pokemonSearch(
        @Path("id") searchQuery: Int,
    ): Response<PokemonStatus>
}