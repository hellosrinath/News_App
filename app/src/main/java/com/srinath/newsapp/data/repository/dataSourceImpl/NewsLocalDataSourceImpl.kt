package com.srinath.newsapp.data.repository.dataSourceImpl

import com.srinath.newsapp.data.db.ArticleDAO
import com.srinath.newsapp.data.model.Article
import com.srinath.newsapp.data.repository.dataSource.NewsLocalDataSource

class NewsLocalDataSourceImpl(
    private val articleDAO: ArticleDAO
): NewsLocalDataSource {
    override suspend fun saveArticleToDb(article: Article) =
        articleDAO.insert(article)
}