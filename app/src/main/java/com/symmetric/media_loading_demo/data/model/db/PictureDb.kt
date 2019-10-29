package com.symmetric.media_loading_demo.data.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.symmetric.media_loading_demo.data.model.api.Urls

@Entity(tableName = "pictures")
data class PictureDb(
    @PrimaryKey
    var id: String,
    val color: String,
    val height: Int,
    val width: Int,
    val likes: Int,
    val url: String,
    val user: String,
    val categories: String
)