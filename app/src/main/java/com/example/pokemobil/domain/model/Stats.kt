package com.example.pokemobil.domain.model

import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("base_stat") val baseStat: Int,
    val effort: Int,
    val stat: Stat
)