package com.symmetric.media_loading_demo.di.components

import android.app.Application
import com.symmetric.media_loading_demo.MediaLoadingDemoApp
import com.symmetric.media_loading_demo.di.builder.ActivityBuilderModule
import com.symmetric.media_loading_demo.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidInjectionModule::class,
        ActivityBuilderModule::class]
)
interface AppComponent: AndroidInjector<DaggerApplication>{
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(catalogApp: MediaLoadingDemoApp)
}
