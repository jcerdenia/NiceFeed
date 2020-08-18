package com.joshuacerdenia.android.nicefeed.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.ui.dialog.EditCategoryFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.TextSizeFragment
import com.joshuacerdenia.android.nicefeed.utils.EntryToHtmlFormatter

private const val TAG = "EntryFragment"
private const val ARG_ENTRY_ID = "ARG_ENTRY_ID"
private const val MIME_TYPE = "text/html; charset=UTF-8"
private const val ENCODING = "base64"

class EntryFragment: VisibleFragment(), TextSizeFragment.Callbacks {

    companion object {
        fun newInstance(entryId: String): EntryFragment {
            return EntryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ENTRY_ID, entryId)
                }
            }
        }
    }

    private val fragment = this@EntryFragment
    private val viewModel: EntryViewModel by lazy {
        ViewModelProvider(this).get(EntryViewModel::class.java)
    }

    private lateinit var entry: Entry
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onEntryLoaded(website: String)
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
        arguments?.getString(ARG_ENTRY_ID)?.let { entryId ->
            viewModel.getEntryById(entryId)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entry, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        webView = view.findViewById(R.id.web_view_entry)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                // Open links with default browser
                Intent(Intent.ACTION_VIEW, request?.url).run {
                    startActivity(this)
                }
                return true
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.entryLiveData.observe(viewLifecycleOwner, Observer { entry ->
            this.entry = entry
            drawEntry(entry)
            callbacks?.onEntryLoaded(entry.website)
            progressBar.visibility = View.GONE
            setHasOptionsMenu(true)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_entry, menu)
        setStarOptionItem(menu.findItem(R.id.menuItem_star))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItem_star -> handleStar(item)
            R.id.menuItem_share -> handleShare()
            R.id.menuItem_copy_link -> handleCopyLink()
            R.id.menuItem_view_in_browser -> handleViewInBrowser()
            R.id.menuItem_text_size -> handleChangeTextSize()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun drawEntry(entry: Entry) {
        context?.let { context ->
            EntryToHtmlFormatter(context).format(entry)
        }.also { html ->
            webView.loadData(html, MIME_TYPE, ENCODING)
        }
    }

    private fun handleStar(item: MenuItem): Boolean {
        entry.isStarred = !entry.isStarred
        setStarOptionItem(item)
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

    private fun handleShare(): Boolean {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, entry.title)
            putExtra(Intent.EXTRA_TEXT, entry.url)
        }.also { intent ->
            val chooserIntent = Intent.createChooser(intent, getString(R.string.share_entry))
            startActivity(chooserIntent)
        }

        return true
    }

    private fun handleCopyLink(): Boolean {
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        ClipData.newPlainText("link", entry.url).run {
            clipboard.setPrimaryClip(this)
        }

        Snackbar.make(
            webView,
            getString(R.string.copied_link_to_clipboard),
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }

    private fun handleViewInBrowser(): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(entry.url))
        startActivity(intent)
        return true
    }

    private fun handleChangeTextSize(): Boolean {
        TextSizeFragment.newInstance().apply {
            setTargetFragment(fragment, 0)
            show(fragment.requireFragmentManager(), "change text size")
        }
        return true
    }

    override fun onTextSizeSelected() {
        drawEntry(entry)
    }

    fun scrollToTop() {
        webView.scrollTo(0, 0)
    }

    override fun onPause() {
        super.onPause()
        entry.isRead = true
        viewModel.updateEntry(entry)
    }
}