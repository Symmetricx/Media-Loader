package com.symmetric.media_loading_demo.data.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey var id: Int,
    var name: String
)