package me.arthurnagy.news.core.data.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface NewsDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg data: T)

    @Delete
    fun delete(data: T)
}