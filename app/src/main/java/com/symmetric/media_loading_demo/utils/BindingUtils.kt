package com.symmetric.media_loading_demo.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.symmetric.medialoader.MediaLoader


object BindingUtils{

    @JvmStatic
    @BindingAdapter("url")
    fun setImageUrl(imageView: ImageView, url: String) {
        MediaLoader
            .get(imageView.context)
            .load(url)
            .into(imageView)
    }
}