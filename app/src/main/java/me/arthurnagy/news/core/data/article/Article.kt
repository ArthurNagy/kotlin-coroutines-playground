package me.arthurnagy.news.core.data.article

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.arthurnagy.news.core.data.article.Article.Companion.TABLE_NAME
import me.arthurnagy.news.core.data.source.Source

@Entity(tableName = TABLE_NAME)
data class Article(
    @Embedded // TODO: temporary, save it separately and create relation between them
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