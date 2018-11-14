package me.arthurnagy.news.core.data.article

import kotlinx.coroutines.withContext
import me.arthurnagy.news.core.AppDispatchers
import me.arthurnagy.news.core.awaitResource
import me.arthurnagy.news.core.data.Resource
import me.arthurnagy.news.core.network.NewsApi
import me.arthurnagy.news.core.withContextResource

class ArticleRepository(
    private val dispatchers: AppDispatchers,
    private val newsApi: NewsApi,
    private val articleDao: ArticleDao
) {

    private var totalArticles = 0

    suspend fun getArticles(): Resource<List<Article>> {
        val localArticlesResource = withContextResource(dispatchers.io) {
            articleDao.getAll()
        }
        return when (localArticlesResource) {
            is Resource.Success -> if (localArticlesResource.data.isNullOrEmpty()) fetchArticles() else localArticlesResource
            is Resource.Error -> fetchArticles()
        }
    }

    suspend fun fetchArticles(): Resource<List<Article>> {
        val remoteArticleResource = newsApi.getHeadlines().awaitResource()
        return when (remoteArticleResource) {
            is Resource.Success -> {
                withContext(dispatchers.io) {
                    articleDao.insert(*remoteArticleResource.data.articles.toTypedArray())
                }
                totalArticles += remoteArticleResource.data.totalResults
                Resource.Success(remoteArticleResource.data.articles)
            }
            is Resource.Error -> remoteArticleResource
        }
    }

}