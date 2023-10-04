package com.srinath.newsapp.presentation.di

import android.app.Application
import com.srinath.newsapp.domain.usecase.GetNewsHeadlineUseCase
import com.srinath.newsapp.domain.usecase.GetSearchedNewsUseCase
import com.srinath.newsapp.domain.usecase.SaveNewsUseCase
import com.srinath.newsapp.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        application: Application,
        getNewsHeadlineUseCase: GetNewsHeadlineUseCase,
        getSearchedNewsUseCase: GetSearchedNewsUseCase,
        saveNewsUseCase: SaveNewsUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(
            application,
            getNewsHeadlineUseCase,
            getSearchedNewsUseCase,
            saveNewsUseCase
        )
    }

}