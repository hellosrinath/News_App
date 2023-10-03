package com.srinath.newsapp.domain.usecase

import com.srinath.newsapp.data.model.Article
import com.srinath.newsapp.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(
    private val newsRepository: NewsRepository
) {
    suspend fun execute(article: Article) = newsRepository.deleteNews(article)
}