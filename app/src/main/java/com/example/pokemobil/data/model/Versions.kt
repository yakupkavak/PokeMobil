package com.example.pokemobil.data.model

import com.google.gson.annotations.SerializedName

data class Versions(
    @SerializedName("generation-v") val generationV: GenerationV,
)