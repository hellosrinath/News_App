package com.srinath.newsapp.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.srinath.newsapp.data.model.ApiResponse
import com.srinath.newsapp.data.model.Article
import com.srinath.newsapp.data.util.Resource
import com.srinath.newsapp.domain.usecase.DeleteSavedNewsUseCase
import com.srinath.newsapp.domain.usecase.GetNewsHeadlineUseCase
import com.srinath.newsapp.domain.usecase.GetSavedNewsUseCase
import com.srinath.newsapp.domain.usecase.GetSearchedNewsUseCase
import com.srinath.newsapp.domain.usecase.SaveNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NewsViewModel(
    private val app: Application,
    private val getNewsHeadlineUseCase: GetNewsHeadlineUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val savedNewsUseCase: SaveNewsUseCase
) : AndroidViewModel(app) {

    val newsHeadLines: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.P)
    fun getNewsHeadLines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {

        newsHeadLines.postValue(Resource.Loading())
        try {
            if (isDeviceOnline(app)) {
                val apiResult = getNewsHeadlineUseCase.execute(country, page)
                newsHeadLines.postValue(apiResult)
            } else {
                newsHeadLines.postValue(Resource.Error("Internet In Not Available"))
            }
        } catch (e: Exception) {
            newsHeadLines.postValue(Resource.Error(e.message.toString()))
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun isDeviceOnline(context: Context): Boolean {
        val connManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connManager.getNetworkCapabilities(connManager.activeNetwork)
        return if (networkCapabilities == null) {
            false
        } else {
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED)
        }
    }

    // search
    val searchNews: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.P)
    fun searchNews(
        country: String,
        searchQuery: String,
        page: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        searchNews.postValue(Resource.Loading())
        try {
            if (isDeviceOnline(app)) {
                val response = getSearchedNewsUseCase.execute(
                    country, searchQuery, page
                )
                searchNews.postValue(response)
            } else {
                searchNews.postValue(Resource.Error("No Internet Available"))
            }
        } catch (e: Exception) {
            searchNews.postValue(Resource.Error(e.message.toString()))
        }
    }

    // save news
    fun saveNews(article: Article)= viewModelScope.launch(Dispatchers.IO) {
        savedNewsUseCase.execute(article)
    }

}