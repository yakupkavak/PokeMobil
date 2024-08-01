package com.example.pokemobil.model

import com.google.gson.annotations.SerializedName

data class Versions(
    @SerializedName("generation-i") val generationI: Generationİ,
    @SerializedName("generation-ii") val generationII: Generationİi,
    @SerializedName("generation-iii") val generationIII: Generationİii,
    @SerializedName("generation-iv") val generationIV: Generationİv,
    @SerializedName("generation-v") val generationV: GenerationV,
    @SerializedName("generation-vi") val generationVI: GenerationVi,
    @SerializedName("generation-vii") val generationVII: GenerationVii,
    @SerializedName("generation-viii") val generationVIII: GenerationViii
)