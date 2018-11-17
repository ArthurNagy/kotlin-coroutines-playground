package me.arthurnagy.news.feature.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.arthurnagy.news.NewsViewModel
import me.arthurnagy.news.core.AppDispatchers
import me.arthurnagy.news.core.data.Resource
import me.arthurnagy.news.core.data.article.Article
import me.arthurnagy.news.core.data.article.ArticleRepository

class DetailViewModel(
    articleId: String,
    dispatchers: AppDispatchers,
    articleRepository: ArticleRepository
) : NewsViewModel(dispatchers) {

    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article> get() = _article

    init {
        launch {
            val articleResult = withContext(dispatchers.io) {
                articleRepository.getArticle(articleId)
            }
            when (articleResult) {
                is Resource.Success -> _article.value = articleResult.data
                is Resource.Error -> Log.d("DetailViewModel", "Handle error: ${articleResult.exception}")
            }
        }
    }

}