package me.arthurnagy.news.core.data.article

import androidx.room.Dao
import androidx.room.Query
import me.arthurnagy.news.core.data.db.NewsDao

@Dao
interface ArticleDao : NewsDao<Article> {

    @Query("select * from ${Article.TABLE_NAME}")
    fun getAll(): List<Article>

    @Query("select * from ${Article.TABLE_NAME} where title like :title")
    fun getByTitle(title: String): Article
}