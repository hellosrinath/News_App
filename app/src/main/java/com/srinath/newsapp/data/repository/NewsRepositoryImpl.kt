package com.srinath.newsapp.data.repository

import com.srinath.newsapp.data.model.ApiResponse
import com.srinath.newsapp.data.model.Article
import com.srinath.newsapp.data.repository.dataSource.NewsLocalDataSource
import com.srinath.newsapp.data.repository.dataSource.NewsRemoteDataSource
import com.srinath.newsapp.data.util.Resource
import com.srinath.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {
    override suspend fun getNewsHeadLines(country: String, page: Int): Resource<ApiResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadLines(country, page))
    }

    private fun responseToResource(response: Response<ApiResponse>): Resource<ApiResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<ApiResponse> = responseToResource(
        newsRemoteDataSource.getSearchedNews(country, searchQuery, page)
    )

    override suspend fun saveNews(article: Article) =
        newsLocalDataSource.saveArticleToDb(article)

    override suspend fun deleteNews(article: Article) =
        newsLocalDataSource.deleteArticle(article)

    override fun getSavedNews(): Flow<List<Article>> =
        newsLocalDataSource.getSavedArticles()
}