package com.srinath.newsapp.data.repository.dataSource

import com.srinath.newsapp.data.model.ApiResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadLines(): Response<ApiResponse>
}