package com.symmetric.media_loading_demo.di.module


import android.app.Application
import androidx.room.Room
import com.symmetric.media_loading_demo.data.local.AppDatabase
import com.symmetric.media_loading_demo.data.local.dao.PictureDao
import com.symmetric.media_loading_demo.data.remote.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.CertificatePinner
import com.symmetric.media_loading_demo.data.remote.ApiConstants
import com.symmetric.media_loading_demo.data.remote.RequestInterceptor
import com.symmetric.media_loading_demo.utils.AppConstants
import com.symmetric.media_loading_demo.utils.LiveDataCallAdapterFactory


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()

        okHttpClient.connectTimeout(ApiConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.readTimeout(ApiConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.writeTimeout(ApiConstants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.addInterceptor(RequestInterceptor())
        okHttpClient.addInterceptor(HttpLoggingInterceptor())
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {

       return Room
            .databaseBuilder(app, AppDatabase::class.java, AppConstants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePictureDao(db: AppDatabase): PictureDao {
        return db.pictureDao()
    }

}