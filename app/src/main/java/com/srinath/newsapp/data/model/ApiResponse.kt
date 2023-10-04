package com.srinath.newsapp.data.model

//https://newsapi.org/v2/top-headlines?country=us&page=1&apiKey=4bf238712d1c4195b1a078788ecc0a77

data class ApiResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)