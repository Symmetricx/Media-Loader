package com.symmetric.media_loading_demo.ui.main.home

import com.symmetric.media_loading_demo.ui.main.home.HomeFragment
import com.symmetric.media_loading_demo.ui.main.home.HomeFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class  HomeFragmentProvider {

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    internal abstract fun provideItemsFragmentFactory(): HomeFragment

}