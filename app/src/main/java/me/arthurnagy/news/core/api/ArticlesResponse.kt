package me.arthurnagy.news.core.api

import me.arthurnagy.news.core.data.article.Article

data class ArticlesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)