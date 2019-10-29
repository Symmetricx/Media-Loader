package com.symmetric.media_loading_demo


import com.symmetric.media_loading_demo.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class MediaLoadingDemoApp : DaggerApplication(){
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        val appComponent =  DaggerAppComponent
            .builder()
            .application(this)
            .build()

        appComponent.inject(this)

        setInstance(this)

        return appComponent

    }

    companion object Factory{
        private lateinit var catalogApp: MediaLoadingDemoApp;

        fun  getAppContext(): MediaLoadingDemoApp {
            return catalogApp;
        }

        fun setInstance(_catalogApp: MediaLoadingDemoApp){
            catalogApp = _catalogApp;
        }
    }
}