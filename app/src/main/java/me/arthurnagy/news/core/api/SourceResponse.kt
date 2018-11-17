package me.arthurnagy.news.core.api

import me.arthurnagy.news.core.data.source.Source

data class SourceResponse(
    val status: String,
    val sources: List<Source>
)