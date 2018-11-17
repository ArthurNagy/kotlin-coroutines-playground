package me.arthurnagy.news.feature.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.arthurnagy.news.base.NewsViewModel
import me.arthurnagy.news.core.AppDispatchers
import me.arthurnagy.news.core.data.Resource
import me.arthurnagy.news.core.data.article.Article
import me.arthurnagy.news.core.data.article.ArticleRepository

class HomeViewModel(
    dispatchers: AppDispatchers,
    private val articleRepository: ArticleRepository
) : NewsViewModel(dispatchers) {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> get() = _articles
    private val _refreshing = MutableLiveData<Boolean>()
    val refreshing: LiveData<Boolean> get() = _refreshing

    init {
        launch {
            val articlesResult = withContext(dispatchers.io) {
                articleRepository.getArticles()
            }
            when (articlesResult) {
                is Resource.Success -> {
                    _articles.value = articlesResult.data
                    Log.d("HomeViewModel", "Articles: ${articlesResult.data}")
                }
                is Resource.Error -> Log.d("HomeViewModel", "Handle error: ${articlesResult.exception}")
            }
        }
    }

    fun refreshArticles() {
        launch {
            _refreshing.value = true
            val articlesResult = withContext(dispatchers.io) {
                articleRepository.fetchArticles()
            }
            _refreshing.value = false
            when (articlesResult) {
                is Resource.Success -> _articles.value = articlesResult.data
                is Resource.Error -> Log.d("HomeViewModel", "Handle error: ${articlesResult.exception}")
            }
        }
    }

}