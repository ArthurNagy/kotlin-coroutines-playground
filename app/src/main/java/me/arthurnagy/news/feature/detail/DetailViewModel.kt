package me.arthurnagy.news.feature.detail

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.util.Log
import androidx.core.text.buildSpannedString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.arthurnagy.news.base.NewsViewModel
import me.arthurnagy.news.base.dependentLiveData
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

    val showAuthor = dependentLiveData(article, defaultValue = false) { !article.value?.author.isNullOrEmpty() }
    val articleContent = dependentLiveData(article) {
        val article = article.value
        val description = article?.description
        val content = article?.content
        buildSpannedString {
            append(description)
            append("\n\n")
            append(content)
            setArticleClickableSpan(article?.url)
        }
    }

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

    private fun SpannableStringBuilder.setArticleClickableSpan(articleUrl: String?): SpannableStringBuilder {
        val content = toString()
        Regex("\\[\\+.*]").find(content)?.let {
            val linkText = it.value
            val startPosition = content.indexOf(linkText)
            val endPosition = content.length
            setSpan(ArticleClickableSpan(articleUrl), startPosition, endPosition, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return this
    }

}