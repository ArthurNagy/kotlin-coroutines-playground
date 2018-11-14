package me.arthurnagy.news.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import me.arthurnagy.news.core.data.article.Article
import me.arthurnagy.news.core.data.article.ArticleDao
import me.arthurnagy.news.core.data.source.Source
import me.arthurnagy.news.core.data.source.SourceDao

@Database(entities = [Source::class, Article::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun sourceDao(): SourceDao

    abstract fun articleDao(): ArticleDao
}