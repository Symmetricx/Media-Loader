package com.symmetric.media_loading_demo.data.model.api

import com.google.gson.annotations.SerializedName
import com.symmetric.media_loading_demo.data.model.api.category.Category
import com.symmetric.media_loading_demo.data.model.api.user.User


data class Picture(
    val categories: List<Category>,
    val color: String,
    val createdAt: String,
    @SerializedName("current_user_collections")
    val currentUserCollections: List<Any>,
    val height: Int,
    val id: String,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    val likes: Int,
    val links: PictureLink,
    val urls: Urls,
    val user: User,
    val width: Int
)


