package com.example.pokemobil.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.pokemobil.R
import com.example.pokemobil.model.StatData

fun ImageView.getUrl(url: String) {
    Glide.with(this).asGif().load(url).thumbnail(
        Glide.with(this).asGif()
            .load(R.drawable.spinnerblack)
    ).into(this)
}

fun getStat(search: String,statList: List<StatData>) : String{
    return statList.find { it.statName == search }?.baseStat.toString()
}