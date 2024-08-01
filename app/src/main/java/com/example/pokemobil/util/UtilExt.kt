package com.example.pokemobil.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.pokemobil.R

fun ImageView.getUrl(url: String) {
    Glide.with(this).asGif().load(url).thumbnail(
        Glide.with(this).asGif()
            .load(R.drawable.spinnerblack)
    ).into(this)
}