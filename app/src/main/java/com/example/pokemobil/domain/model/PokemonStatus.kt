package com.example.pokemobil.domain.model

import com.google.gson.annotations.SerializedName

data class PokemonStatus(
    @SerializedName("base_experience") val baseExperience: Int,
    val height: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stats>,
    val weight: Int
)