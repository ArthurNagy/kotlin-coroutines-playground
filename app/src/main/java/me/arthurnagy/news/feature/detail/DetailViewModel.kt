package me.arthurnagy.news.feature.detail

import me.arthurnagy.news.NewsViewModel
import me.arthurnagy.news.core.AppDispatchers
import me.arthurnagy.news.core.data.article.ArticleRepository

class DetailViewModel(
    dispatchers: AppDispatchers,
    private val articleRepository: ArticleRepository
) : NewsViewModel(dispatchers) {

}