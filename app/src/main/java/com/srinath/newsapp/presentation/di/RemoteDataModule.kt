package com.srinath.newsapp.presentation.di

import com.srinath.newsapp.data.api.NewsApiService
import com.srinath.newsapp.data.repository.dataSource.NewsRemoteDataSource
import com.srinath.newsapp.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(
        newsApiService: NewsApiService
    ): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsApiService)
    }

}