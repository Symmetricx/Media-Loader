package com.symmetric.media_loading_demo.data.local.dao

import androidx.room.*

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(items: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(item: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(items: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: T)

    @Delete
    fun delete(item: T)
}