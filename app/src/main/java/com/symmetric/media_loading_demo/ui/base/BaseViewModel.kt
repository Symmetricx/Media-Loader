
package com.symmetric.media_loading_demo.ui.base


import androidx.lifecycle.ViewModel

import java.lang.ref.WeakReference

abstract class BaseViewModel<N> : ViewModel() {


    var loading : Boolean = false

    private lateinit var mNavigator: WeakReference<N>

    fun  setNavigator(navigator: N) {
        this.mNavigator = WeakReference<N>(navigator)
    }

    fun  getNavigator() : N? {
        return mNavigator.get()
    }

    init {
    }


}
