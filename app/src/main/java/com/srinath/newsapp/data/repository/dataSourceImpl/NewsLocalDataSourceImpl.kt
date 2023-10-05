package com.srinath.newsapp.data.repository.dataSourceImpl

import com.srinath.newsapp.data.db.ArticleDAO
import com.srinath.newsapp.data.model.Article
import com.srinath.newsapp.data.repository.dataSource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
    private val articleDAO: ArticleDAO
) : NewsLocalDataSource {
    override suspend fun saveArticleToDb(article: Article) =
        articleDAO.insert(article)

    override fun getSavedArticles(): Flow<List<Article>> =
        articleDAO.getAllArticles()

    override suspend fun deleteArticle(article: Article) =
        articleDAO.deleteArticle(article)
}