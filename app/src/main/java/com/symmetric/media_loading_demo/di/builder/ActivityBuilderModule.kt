package com.symmetric.media_loading_demo.di.builder

import com.symmetric.media_loading_demo.ui.main.MainActivity
import com.symmetric.media_loading_demo.ui.main.MainActivityModule
import com.symmetric.media_loading_demo.ui.main.home.HomeFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(
        modules = [
        MainActivityModule::class,
        HomeFragmentProvider::class
        ]
    )
    abstract fun bindMainActivity(): MainActivity
}