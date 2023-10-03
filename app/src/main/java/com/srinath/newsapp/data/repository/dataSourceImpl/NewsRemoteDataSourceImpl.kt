package com.srinath.newsapp.data.repository.dataSourceImpl

import com.srinath.newsapp.data.api.NewsApiService
import com.srinath.newsapp.data.model.ApiResponse
import com.srinath.newsapp.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsApiService: NewsApiService,
    private val country: String,
    private val page: Int
) : NewsRemoteDataSource {
    override suspend fun getTopHeadLines(): Response<ApiResponse> =
        newsApiService.getTopHeadLines(country, page)
}