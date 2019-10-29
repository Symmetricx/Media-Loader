package com.symmetric.media_loading_demo.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.symmetric.media_loading_demo.MediaLoadingDemoViewModelFactory
import com.symmetric.media_loading_demo.ui.main.MainViewModel
import com.symmetric.media_loading_demo.ui.main.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Suppress("unused")
@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MediaLoadingDemoViewModelFactory): ViewModelProvider.Factory
}