package com.srinath.newsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.srinath.newsapp.data.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ArticleDatabase : RoomDatabase(){
    abstract fun getArticleDAO(): ArticleDAO
}