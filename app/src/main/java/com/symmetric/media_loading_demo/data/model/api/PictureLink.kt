package com.symmetric.media_loading_demo.data.model.api

import com.google.gson.annotations.SerializedName

data class PictureLink(
    val download: String,
    val html: String,
    val self: String
)