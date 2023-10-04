package com.srinath.newsapp.domain.repository

import com.srinath.newsapp.data.model.ApiResponse
import com.srinath.newsapp.data.model.Article
import com.srinath.newsapp.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadLines(country: String, page: Int): Resource<ApiResponse>
    suspend fun getSearchedNews(searchQuery: String): Resource<ApiResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>
}