package com.joshuacerdenia.android.nicefeed.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.joshuacerdenia.android.nicefeed.R

class EntryPageFragment : Fragment() {

    companion object {
        private const val ARG_URL = "entry_page_url"

        fun newInstance(content: String): EntryPageFragment {
            return EntryPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, content)
                }
            }
        }
    }

    private lateinit var content: String
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content = arguments?.getString(ARG_URL).toString()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entry_page, container, false)
        webView = view.findViewById(R.id.webView_entry)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadData(content, "text/html", "UTF-8")
        return view
    }
}