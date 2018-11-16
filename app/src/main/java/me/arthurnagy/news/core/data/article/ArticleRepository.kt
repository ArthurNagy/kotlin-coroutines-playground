package me.arthurnagy.news.core.data.article

import me.arthurnagy.news.core.awaitResource
import me.arthurnagy.news.core.data.Resource
import me.arthurnagy.news.core.network.NewsApi

class ArticleRepository(
    private val newsApi: NewsApi,
    private val articleDao: ArticleDao
) {

    private var totalArticles = 0

    suspend fun getArticles(): Resource<List<Article>> {
        val localArticles = articleDao.getAll()
        return if (localArticles.isNullOrEmpty()) fetchArticles() else Resource.Success(localArticles)
    }

    suspend fun fetchArticles(): Resource<List<Article>> {
        val remoteArticleResource = newsApi.getHeadlines().awaitResource()
        return when (remoteArticleResource) {
            is Resource.Success -> {
                articleDao.insert(*remoteArticleResource.data.articles.toTypedArray())
                totalArticles += remoteArticleResource.data.totalResults
                Resource.Success(remoteArticleResource.data.articles)
            }
            is Resource.Error -> remoteArticleResource
        }
    }

}