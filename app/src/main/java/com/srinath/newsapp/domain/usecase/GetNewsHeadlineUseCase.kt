package com.srinath.newsapp.domain.usecase

import com.srinath.newsapp.data.model.ApiResponse
import com.srinath.newsapp.data.util.Resource
import com.srinath.newsapp.domain.repository.NewsRepository

class GetNewsHeadlineUseCase(
    private val newsRepository: NewsRepository
) {
    suspend fun execute(): Resource<ApiResponse> =
        newsRepository.getNewsHeadLines()
}