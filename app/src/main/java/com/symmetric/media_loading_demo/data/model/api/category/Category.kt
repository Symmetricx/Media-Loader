package com.symmetric.media_loading_demo.data.model.api.category

import com.google.gson.annotations.SerializedName

data class Category(
    val id: Int,
    @SerializedName("links")
    val links: Link,
    @SerializedName("photo_count")
    val photoCount: Int,
    val title: String
)