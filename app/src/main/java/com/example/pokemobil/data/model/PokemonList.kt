package com.example.pokemobil.data.model

data class PokemonList(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<PokemonResult>
)