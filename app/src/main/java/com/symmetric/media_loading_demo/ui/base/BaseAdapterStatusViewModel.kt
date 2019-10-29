package com.symmetric.media_loading_demo.ui.base

import androidx.annotation.DrawableRes
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel

class BaseAdapterStatusViewModel : ViewModel {

    @DrawableRes
    var drawable: Int = 0

    var messageRes: ObservableInt


    internal constructor(message: Int) {
        this.messageRes = ObservableInt(message)
    }

    internal constructor(drawable: Int, message: Int) {
        this.messageRes = ObservableInt(message)
    }

}
