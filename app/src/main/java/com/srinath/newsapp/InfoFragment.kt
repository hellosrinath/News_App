package com.srinath.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.srinath.newsapp.databinding.FragmentInfoBinding
import com.srinath.newsapp.presentation.viewmodel.NewsViewModel

class InfoFragment : Fragment() {

    private lateinit var fragmentInfoBinding: FragmentInfoBinding
    private lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentInfoBinding = FragmentInfoBinding.bind(view)
//        val args: InfoFragmentArgs by navArgs()
//        val article = args.selectedArticle

        viewModel = (activity as MainActivity).viewModel
        val article = viewModel.selectedArticle
        fragmentInfoBinding.webView.apply {
            webViewClient = WebViewClient()
            if (article?.url != "")
                article?.url?.let { loadUrl(it) }
        }

        fragmentInfoBinding.fbSave.setOnClickListener {
            viewModel.saveNews(article!!)
            Snackbar.make(view, "Saved Successfully", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

}