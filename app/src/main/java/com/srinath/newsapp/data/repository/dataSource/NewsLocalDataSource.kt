package com.srinath.newsapp.data.repository.dataSource

import com.srinath.newsapp.data.model.Article

interface NewsLocalDataSource {
    suspend fun saveArticleToDb(article: Article)
}