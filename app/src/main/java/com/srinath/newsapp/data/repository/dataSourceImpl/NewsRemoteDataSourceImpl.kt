package com.srinath.newsapp.data.repository.dataSourceImpl

import com.srinath.newsapp.data.api.NewsApiService
import com.srinath.newsapp.data.model.ApiResponse
import com.srinath.newsapp.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsApiService: NewsApiService,
) : NewsRemoteDataSource {
    override suspend fun getTopHeadLines(country: String, page: Int): Response<ApiResponse> =
        newsApiService.getTopHeadLines(country, page)

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<ApiResponse> = newsApiService.getSearchTopHeadLines(
        country,
        searchQuery, page
    )
}