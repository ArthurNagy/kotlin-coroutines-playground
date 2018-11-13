package me.arthurnagy.news.core.model

enum class SortType(val value: String) {
    RELEVANCY("relevancy"),
    POPULARITY("popularity"),
    PUBLISHED_AT("publishedAt")
}