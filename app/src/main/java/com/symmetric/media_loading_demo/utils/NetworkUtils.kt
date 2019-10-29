package com.symmetric.media_loading_demo.utils

import android.content.Context
import android.net.ConnectivityManager
import com.symmetric.media_loading_demo.MediaLoadingDemoApp.Factory.getAppContext
import com.google.gson.stream.MalformedJsonException
import com.symmetric.media_loading_demo.R
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException


object NetworkUtils {

    fun isNetworkConnected(context: Context): Boolean {
        TODO("Must be implemented")
    }

    fun getCustomErrorMessage(error: Throwable): String {
        return when (error) {
            is SocketTimeoutException -> getAppContext().getString(R.string.requestTimeOutError)
            is MalformedJsonException -> getAppContext().getString(R.string.responseMalformedJson)
            is IOException -> getAppContext().getString(R.string.networkError)
            is SSLHandshakeException -> getAppContext().getString(R.string.sslHandshakeError)
            is HttpException -> error.response().message()
            else -> {
                error.printStackTrace()
                getAppContext().getString(R.string.unknownError)
            }
        }

    }

}
