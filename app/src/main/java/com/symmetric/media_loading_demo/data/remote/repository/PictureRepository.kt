package com.symmetric.media_loading_demo.data.remote.repository

import androidx.lifecycle.LiveData
import com.symmetric.media_loading_demo.AppExecutors
import com.symmetric.media_loading_demo.data.local.dao.PictureDao
import com.symmetric.media_loading_demo.data.model.api.ApiResponse
import com.symmetric.media_loading_demo.data.model.api.Picture
import com.symmetric.media_loading_demo.data.model.db.PictureDb
import com.symmetric.media_loading_demo.data.remote.ApiService
import com.symmetric.media_loading_demo.data.remote.NetworkBoundResource
import com.symmetric.media_loading_demo.data.remote.Resource
import javax.inject.Inject

class PictureRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val itemDao: PictureDao,
    private val service: ApiService
) {

    fun all(): LiveData<Resource<List<PictureDb>>> {

        return object : NetworkBoundResource<List<PictureDb>, List<Picture>>(appExecutors) {
            override fun shouldFetch(data: List<PictureDb>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<PictureDb>> {
                return itemDao.picturesPag()
            }

            override fun createCall(): LiveData<ApiResponse<List<Picture>>> {
                return service.getPictures()
            }

            override fun saveCallResult(item: List<Picture>) {
                val pictureDbs = ArrayList<PictureDb>()
                item.forEach {
                    var categories = ""
                    it.categories.forEachIndexed {index, category ->
                        run {
                            categories += "${if(index != 0)  ',' else ' '} ${category.title}"
                        }
                    }
                    val temp = PictureDb(it.id, it.color, it.height, it.width, it.likes, it.urls.regular, it.user.name,categories)
                    pictureDbs.add(temp)
                }
                itemDao.save(pictureDbs)
            }


        }.asLiveData()
    }

}