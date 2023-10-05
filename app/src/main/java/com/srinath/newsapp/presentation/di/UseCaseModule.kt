package com.srinath.newsapp.presentation.di

import com.srinath.newsapp.domain.repository.NewsRepository
import com.srinath.newsapp.domain.usecase.DeleteSavedNewsUseCase
import com.srinath.newsapp.domain.usecase.GetNewsHeadlineUseCase
import com.srinath.newsapp.domain.usecase.GetSavedNewsUseCase
import com.srinath.newsapp.domain.usecase.GetSearchedNewsUseCase
import com.srinath.newsapp.domain.usecase.SaveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetNewsHeadLineUseCase(
        newsRepository: NewsRepository
    ): GetNewsHeadlineUseCase {
        return GetNewsHeadlineUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideGetSearchedNewsUseCase(
        newsRepository: NewsRepository
    ): GetSearchedNewsUseCase {
        return GetSearchedNewsUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideSaveNewsUseCase(
        newsRepository: NewsRepository
    ): SaveNewsUseCase {
        return SaveNewsUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideGetSavedNewsCase(
        newsRepository: NewsRepository
    ): GetSavedNewsUseCase {
        return GetSavedNewsUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteSavedNews(
        newsRepository: NewsRepository
    ): DeleteSavedNewsUseCase {
        return DeleteSavedNewsUseCase(newsRepository)
    }

}