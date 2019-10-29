package com.symmetric.media_loading_demo.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.symmetric.medialoader.MediaLoader


object BindingUtils{

    @JvmStatic
    @BindingAdapter("url")
    fun setImageUrl(imageView: ImageView, image: String) {
        MediaLoader
            .get(imageView.context)
            .load(image)
            .into(imageView)
    }
}