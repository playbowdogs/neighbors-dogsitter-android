package com.playbowdogs.neighbors_dogsitter_android.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.playbowdogs.neighbors_dogsitter_android.utils.GlideApp

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("CamImage")
    public fun loadImage(imageView: ImageView, imageUrl: String) {
        GlideApp.with(imageView.context)
            .load(imageUrl)
            .dontAnimate()
            .into(imageView)
    }
}