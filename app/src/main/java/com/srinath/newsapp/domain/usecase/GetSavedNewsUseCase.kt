package com.srinath.newsapp.domain.usecase

import com.srinath.newsapp.data.model.Article
import com.srinath.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(
    private val newsRepository: NewsRepository
) {
    fun execute(): Flow<List<Article>> = newsRepository.getSavedNews()
}