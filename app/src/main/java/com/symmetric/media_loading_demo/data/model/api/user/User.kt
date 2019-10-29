package com.symmetric.media_loading_demo.data.model.api.user

import com.google.gson.annotations.SerializedName

data class User(
    val id: String,
    val links: Link,
    val name: String,
    @SerializedName("profile_image")
    val profileImage: Image,
    val username: String
)