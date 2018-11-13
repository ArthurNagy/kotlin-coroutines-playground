package me.arthurnagy.news.core.storage

import androidx.room.Dao
import androidx.room.Query
import me.arthurnagy.news.core.model.Source

@Dao
interface SourceDao : NewsDao<Source> {

    @Query("select * from ${Source.TABLE_NAME}")
    fun getAll(): List<Source>

    @Query("select * from ${Source.TABLE_NAME} where name like :name")
    fun getByName(name: String): Source
}