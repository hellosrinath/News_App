package com.srinath.newsapp.domain.usecase

import com.srinath.newsapp.data.model.ApiResponse
import com.srinath.newsapp.data.util.Resource
import com.srinath.newsapp.domain.repository.NewsRepository

class GetSearchedNewsUseCase(
    private val newsRepository: NewsRepository
) {
    suspend fun execute(country: String, searchQuery: String, page: Int): Resource<ApiResponse> =
        newsRepository.getSearchedNews(country, searchQuery, page)
}