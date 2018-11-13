package me.arthurnagy.news.core.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import me.arthurnagy.news.core.model.Article
import me.arthurnagy.news.core.model.Source

@Database(entities = [Source::class, Article::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun sourceDao(): SourceDao

    abstract fun articleDao(): ArticleDao
}