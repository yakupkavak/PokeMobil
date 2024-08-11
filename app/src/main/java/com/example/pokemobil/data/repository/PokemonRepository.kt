package com.example.pokemobil.data.repository

import com.example.pokemobil.data.service.PokemonService
import com.example.pokemobil.domain.base.BaseRepository
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonService: PokemonService
) : BaseRepository() {

    suspend fun getPokemonList() =
        fetchData { pokemonService.pokemonList() }

    suspend fun getPokemon(pokemonId: Int) =
        fetchData { pokemonService.pokemonSearch(pokemonId) }
}