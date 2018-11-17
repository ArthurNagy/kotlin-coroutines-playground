package me.arthurnagy.news.core.data.model

enum class SortType(val value: String) {
    RELEVANCY("relevancy"),
    POPULARITY("popularity"),
    PUBLISHED_AT("publishedAt")
}