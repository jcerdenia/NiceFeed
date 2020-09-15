package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.ui.OnToolbarInflated
import com.joshuacerdenia.android.nicefeed.ui.dialog.TextSizeFragment
import com.joshuacerdenia.android.nicefeed.ui.viewmodel.EntryViewModel
import com.joshuacerdenia.android.nicefeed.utils.Utils
import com.joshuacerdenia.android.nicefeed.utils.shortened

class EntryFragment: VisibleFragment(), TextSizeFragment.Callbacks {

    private lateinit var viewModel: EntryViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    private var callbacks: OnToolbarInflated? = null
    private var starItem: MenuItem? = null
    private val fragment = this@EntryFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = activity as OnToolbarInflated?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EntryViewModel::class.java)
        context?.let { context ->
            viewModel.setTextSize(NiceFeedPreferences.getTextSize(context))
        }
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
        toolbar = view.findViewById(R.id.toolbar)
        progressBar = view.findViewById(R.id.progress_bar)
        webView = view.findViewById(R.id.web_view)
        webView.apply {
            setBackgroundColor(Color.TRANSPARENT)
            settings.apply {
                javaScriptEnabled = true
                settings.builtInZoomControls = true
                settings.displayZoomControls = false
            }

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    // Open links with default browser
                    request?.url?.let { url -> Utils.openLink(requireActivity(), webView, url) }
                    return true
                }
            }
        }

        toolbar.title = getString(R.string.loading)
        callbacks?.onToolbarInflated(toolbar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setOnClickListener {
            webView.scrollTo(0, 0)
        }

        viewModel.htmlLiveData.observe(viewLifecycleOwner, { html ->
            if (html != null) {
                webView.loadData(html, MIME_TYPE, ENCODING)
                toolbar.title = viewModel.website.shortened()
                Handler().postDelayed({
                    viewModel.lastPosition.let { position ->
                        webView.scrollTo(position.first, position.second)
                    }
                }, 200)
            } else {
                toolbar.title = getString(R.string.app_name)
            }
            progressBar.visibility = View.GONE
            setHasOptionsMenu(true)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_entry, menu)
        starItem = menu.findItem(R.id.menuItem_star)
        toggleStarOptionItem(viewModel.isStarred)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItem_star -> handleStar()
            R.id.menuItem_share -> handleShare()
            R.id.menuItem_copy_link -> handleCopyLink()
            R.id.menuItem_view_in_browser -> handleViewInBrowser(viewModel.url)
            R.id.menuItem_text_size -> handleChangeTextSize()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleStar(): Boolean {
        viewModel.isStarred = !viewModel.isStarred
        toggleStarOptionItem(viewModel.isStarred)
        return true
    }

    private fun toggleStarOptionItem(isStarred: Boolean) {
        starItem?.apply {
            title = if (isStarred) {
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
            putExtra(Intent.EXTRA_SUBJECT, viewModel.title)
            putExtra(Intent.EXTRA_TEXT, viewModel.url)
        }.also { intent ->
            val chooserIntent = Intent.createChooser(intent, getString(R.string.share_entry))
            startActivity(chooserIntent)
        }
        return true
    }

    private fun handleCopyLink(): Boolean {
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        ClipData.newPlainText("link", viewModel.url).run {
            clipboard.setPrimaryClip(this)
        }

        Snackbar.make(
            webView,
            getString(R.string.copied_link_to_clipboard),
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }

    private fun handleViewInBrowser(url: String): Boolean {
        Utils.openLink(requireActivity(), webView, Uri.parse(url))
        return true
    }

    private fun handleChangeTextSize(): Boolean {
        viewModel.lastPosition = Pair(webView.scrollX, webView.scrollY)
        TextSizeFragment.newInstance(viewModel.textSize).apply {
            setTargetFragment(fragment, 0)
            show(fragment.requireFragmentManager(), "change text size")
        }
        return true
    }

    override fun onTextSizeSelected(textSize: Int) {
        viewModel.setTextSize(textSize)
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveChanges()
        viewModel.lastPosition = Pair(webView.scrollX, webView.scrollY)
        context?.let { context ->
            NiceFeedPreferences.saveTextSize(context, viewModel.textSize)
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        private const val ARG_ENTRY_ID = "ARG_ENTRY_ID"
        private const val MIME_TYPE = "text/html; charset=UTF-8"
        private const val ENCODING = "base64"

        fun newInstance(entryId: String): EntryFragment {
            return EntryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ENTRY_ID, entryId)
                }
            }
        }
    }
}