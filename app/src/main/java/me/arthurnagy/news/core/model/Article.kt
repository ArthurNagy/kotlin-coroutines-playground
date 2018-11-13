package me.arthurnagy.news.core.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.arthurnagy.news.core.model.Article.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Article(
    val source: Source,
    val author: String?,
    @PrimaryKey val title: String,
    val description: String?,
    val url: String,
    @ColumnInfo(name = "url_to_image") val urlToImage: String?,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    val content: String?
) {
    companion object {
        const val TABLE_NAME = "article"
    }
}