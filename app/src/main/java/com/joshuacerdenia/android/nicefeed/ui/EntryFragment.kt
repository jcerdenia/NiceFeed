package com.joshuacerdenia.android.nicefeed.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.utils.EntryToHtmlFormatter
import java.util.*

private const val TAG = "EntryFragment"
private const val ARG_ENTRY = "ARG_ENTRY"
private const val MIME_TYPE = "text/html; charset=UTF-8"
private const val ENCODING = "base64"

class EntryFragment: Fragment() {

    companion object {
        fun newInstance(entry: Entry): EntryFragment {
            return EntryFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ENTRY, entry)
                }
            }
        }
    }

    private val viewModel: EntryViewModel by lazy {
        ViewModelProvider(this).get(EntryViewModel::class.java)
    }

    private lateinit var webView: WebView
    private lateinit var formatter: EntryToHtmlFormatter
    private lateinit var entry: Entry
    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onEntryLoaded(date: Date?, website: String?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = activity as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entry = arguments?.getSerializable(ARG_ENTRY) as Entry
        formatter = EntryToHtmlFormatter(entry)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_entry, menu)
        setStarOptionItem(menu.findItem(R.id.menuItem_star))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItem_star -> handleStar(item)
            R.id.menuItem_view_in_browser -> handleViewInBrowser()
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entry, container, false)
        webView = view.findViewById(R.id.webView_entry)

        webView.settings.javaScriptEnabled = true
        //webView.settings.builtInZoomControls = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                // Open links with default browser
                val intent = Intent(Intent.ACTION_VIEW, request?.url)
                startActivity(intent)
                return true
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callbacks?.onEntryLoaded(entry.date, entry.website)
        webView.loadData(formatter.getHtml(), MIME_TYPE, ENCODING)
    }

    override fun onStart() {
        super.onStart()
        entry.isRead = true
        viewModel.updateEntry(entry)
    }

    private fun handleStar(item: MenuItem): Boolean {
        entry.isStarred = !entry.isStarred
        setStarOptionItem(item)
        viewModel.updateEntry(entry)
        return true
    }

    private fun setStarOptionItem(item: MenuItem) {
        item.apply {
            title = if (entry.isStarred) {
                setIcon(R.drawable.ic_star)
                getString(R.string.unstar)
            } else {
                setIcon(R.drawable.ic_star_border)
                getString(R.string.star)
            }
        }
    }

    private fun handleViewInBrowser(): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(entry.guid))
        startActivity(intent)
        return true
    }

    fun scrollToTop() {
        webView.scrollTo(0, 0)
    }
}