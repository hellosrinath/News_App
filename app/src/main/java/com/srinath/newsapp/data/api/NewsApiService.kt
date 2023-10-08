package com.srinath.newsapp.data.api

import com.srinath.newsapp.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadLines(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = "4bf238712d1c4195b1a078788ecc0a77"
    ): Response<ApiResponse>

    @GET(/*"v2/top-headlines"*/"my-file/-/raw/master/news.json")
    suspend fun getSearchTopHeadLines(
        @Query("country")
        country: String,
        @Query("q")
        searchQuery: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = "4bf238712d1c4195b1a078788ecc0a77"
    ): Response<ApiResponse>
}