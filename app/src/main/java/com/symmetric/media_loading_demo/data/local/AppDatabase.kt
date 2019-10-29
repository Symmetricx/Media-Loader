package com.symmetric.media_loading_demo.data.local

import android.graphics.Picture
import androidx.room.Database
import androidx.room.RoomDatabase
import com.symmetric.media_loading_demo.data.local.dao.PictureDao
import com.symmetric.media_loading_demo.data.model.db.PictureDb
import com.symmetric.media_loading_demo.data.model.db.User

@Database(entities = [PictureDb::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun pictureDao(): PictureDao
}
