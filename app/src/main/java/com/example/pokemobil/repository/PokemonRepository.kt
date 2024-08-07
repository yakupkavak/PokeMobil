package com.example.pokemobil.repository

import com.example.pokemobil.service.PokemonService
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val pokemonService: PokemonService
) : BaseRepository() {

    suspend fun getPokemonList() =
        fetchData { pokemonService.pokemonList() }


    suspend fun getPokemon(pokemonId: Int) =
        fetchData { pokemonService.pokemonSearch(pokemonId) }
}