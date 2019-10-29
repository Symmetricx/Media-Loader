package com.symmetric.media_loading_demo.ui.main.home

import com.symmetric.media_loading_demo.data.remote.repository.PictureRepository
import com.symmetric.media_loading_demo.ui.base.BaseViewModel
import javax.inject.Inject


class HomeViewModel @Inject constructor(private val pictureRepository: PictureRepository) : BaseViewModel<HomeNavigator>() {

    val pictures = pictureRepository.all()
}
