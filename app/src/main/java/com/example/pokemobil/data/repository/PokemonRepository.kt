package com.example.pokemobil.data.repository

import com.example.pokemobil.data.service.PokemonService
import com.example.pokemobil.data.util.ServiceCountConst.MaxServiceCount
import com.example.pokemobil.data.base.BaseRepository
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonService: PokemonService
) : BaseRepository() {

    suspend fun getPokemonList() =
        fetchData { pokemonService.pokemonList(MaxServiceCount) }

    suspend fun getPokemon(pokemonId: Int) =
        fetchData { pokemonService.pokemonSearch(pokemonId) }
}