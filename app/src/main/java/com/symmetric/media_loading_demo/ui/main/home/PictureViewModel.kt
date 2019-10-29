package com.symmetric.media_loading_demo.ui.main.home

import androidx.databinding.ObservableField
import com.symmetric.media_loading_demo.data.model.db.PictureDb

class PictureViewModel constructor(
    private val picture: PictureDb
) {
    val id: ObservableField<String> = ObservableField(picture.id)
    val categories: ObservableField<String> = ObservableField(picture.categories)
    val likes: ObservableField<String> = ObservableField(picture.likes.toString())
    var img: ObservableField<String> = ObservableField(picture.url)
    var username: ObservableField<String> = ObservableField(picture.user)

}
