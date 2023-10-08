package com.srinath.newsapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.srinath.newsapp.data.util.Resource
import com.srinath.newsapp.databinding.FragmentNewsBinding
import com.srinath.newsapp.presentation.adapter.NewsAdapter
import com.srinath.newsapp.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "NewsFragment"

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var fragmentNewsBinding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter

    private var countryName = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0;

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsBinding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {
            try {
                viewModel.selectedArticle = it
                findNavController().navigate(
                    R.id.action_newsFragment_to_infoFragment,
                )
            } catch (e: Exception) {
                Log.d(TAG, "onViewCreated: ${e.message.toString()}")
            }
        }
        initRecyclerView()
        viewNewsList()
        setSearchView()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun initRecyclerView() {
        // newsAdapter = NewsAdapter()
        fragmentNewsBinding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }
        viewNewsList()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun viewNewsList() {
        viewModel.getNewsHeadLines(countryName, page)
        viewModel.newsHeadLines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }

                is Resource.Success -> {
                    Log.d(TAG, "viewNewsList: ${response.data}")
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        // page count and finding last page
                        pages = if (it.totalResults % 20 == 0) {
                            it.totalResults / 20
                        } else {
                            it.totalResults / 20 + 1
                        }
                        isLastPage = page == pages
                    }

                }

                is Resource.Error -> {
                    Log.d(TAG, "viewNewsList: ${response.message}")
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showProgressBar() {
        isLoading = true
        fragmentNewsBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        fragmentNewsBinding.progressBar.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        @RequiresApi(Build.VERSION_CODES.P)
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = fragmentNewsBinding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItem = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition + visibleItem >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            if (shouldPaginate) {
                page++
                viewModel.getNewsHeadLines(countryName, page)
                isScrolling = false
            }

        }
    }

    // search

    @RequiresApi(Build.VERSION_CODES.P)
    fun setSearchView() {
        fragmentNewsBinding.svNews.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                @RequiresApi(Build.VERSION_CODES.P)
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    viewModel.searchNews(countryName, p0.toString(), page)
                    viewSearchedNews()
                    return false
                }

                @RequiresApi(Build.VERSION_CODES.P)
                override fun onQueryTextChange(p0: String?): Boolean {

                    MainScope().launch {
                        delay(2000)
                        viewModel.searchNews(countryName, p0.toString(), page)
                        viewSearchedNews()
                    }

                    return false
                }

            }
        )

        fragmentNewsBinding.svNews.setOnCloseListener {
            initRecyclerView()
            viewNewsList()
            false
        }
    }

    fun viewSearchedNews() {
        try {
            viewModel.searchNews.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Loading -> {
                        showProgressBar()
                    }

                    is Resource.Success -> {
                        Log.d(TAG, "viewNewsList: ${response.data}")
                        hideProgressBar()
                        response.data?.let {
                            newsAdapter.differ.submitList(it.articles.toList())
                            // page count and finding last page
                            pages = if (it.totalResults % 20 == 0) {
                                it.totalResults / 20
                            } else {
                                it.totalResults / 20 + 1
                            }
                            isLastPage = page == pages
                        }
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "viewNewsList: ${response.message}")
                        hideProgressBar()
                        response.message?.let {
                            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }catch (e: Exception){e.message.toString()}

    }
}