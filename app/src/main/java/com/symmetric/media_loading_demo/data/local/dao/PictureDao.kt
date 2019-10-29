package com.symmetric.media_loading_demo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.symmetric.media_loading_demo.data.model.db.PictureDb
import com.symmetric.media_loading_demo.data.model.db.User

@Dao
interface PictureDao : BaseDao<PictureDb> {

    @Query("SELECT * FROM pictures ")
    fun picturesPag(): LiveData<List<PictureDb>>
}
