package me.arthurnagy.news.core.data.source

import me.arthurnagy.news.core.network.NewsApi

class SourceRepository(
    private val sourceDao: SourceDao,
    private val newsApi: NewsApi
)