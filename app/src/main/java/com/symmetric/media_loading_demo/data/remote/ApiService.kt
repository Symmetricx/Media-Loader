package com.symmetric.media_loading_demo.data.remote

import androidx.lifecycle.LiveData
import com.symmetric.media_loading_demo.data.model.api.ApiResponse
import com.symmetric.media_loading_demo.data.model.api.Picture
import retrofit2.http.GET


interface ApiService {
    @GET("wgkJgazE")
    fun getPictures(): LiveData<ApiResponse<List<Picture>>>
}
