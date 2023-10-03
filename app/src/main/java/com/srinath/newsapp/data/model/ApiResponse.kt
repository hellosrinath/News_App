package com.srinath.newsapp.data.model

//https://newsapi.org/v2/top-headlines?country=us&page=1&apiKey=f18f276bf5e84ec587ac49e4bae7d3b9

data class ApiResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)