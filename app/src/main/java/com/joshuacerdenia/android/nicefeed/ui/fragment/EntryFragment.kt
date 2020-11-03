package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.ui.OnToolbarInflated
import com.joshuacerdenia.android.nicefeed.ui.dialog.TextSizeFragment
import com.joshuacerdenia.android.nicefeed.ui.viewmodel.EntryViewModel
import com.joshuacerdenia.android.nicefeed.utils.Utils
import com.joshuacerdenia.android.nicefeed.utils.extensions.shortened
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.util.*

class EntryFragment: VisibleFragment(), TextSizeFragment.Callbacks {

    interface Callbacks: OnToolbarInflated

    private lateinit var viewModel: EntryViewModel
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var toolbar: Toolbar
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var subtitleTextView: TextView

    private var callbacks: Callbacks? = null
    private var starItem: MenuItem? = null
    private var viewAsWebPageItem: MenuItem? = null
    private var textSizeItem: MenuItem? = null
    private val fragment = this@EntryFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EntryViewModel::class.java)
        viewModel.apply {
            setTextSize(NiceFeedPreferences.getTextSize(requireContext()))
            font = NiceFeedPreferences.getFont(requireContext())
            bannerIsEnabled = NiceFeedPreferences.bannerIsEnabled(requireContext())
            viewAsWebPage = NiceFeedPreferences.viewAsWebPage(requireContext())
        }

        arguments?.getString(ARG_ENTRY_ID)?.let { entryId -> viewModel.getEntryById(entryId) }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entry, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        nestedScrollView = view.findViewById(R.id.nested_scroll_view)
        progressBar = view.findViewById(R.id.progress_bar)
        imageView = view.findViewById(R.id.image_view)
        titleTextView = view.findViewById(R.id.title_text_view)
        subtitleTextView = view.findViewById(R.id.subtitle_text_view)

        webView = view.findViewById(R.id.web_view)
        webView.apply {
            setBackgroundColor(Color.TRANSPARENT)
            settings.apply {
                javaScriptEnabled = true
                builtInZoomControls = false
                displayZoomControls = false
            }

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    // Open all links with default browser
                    request?.url?.let { url -> Utils.openLink(requireActivity(), webView, url) }
                    return true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (!viewModel.isInitialLoading) {
                        val position = viewModel.lastPosition
                        nestedScrollView.smoothScrollTo(position.first, position.second)
                    }
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    progressBar.progress = newProgress
                    if (newProgress == 100) progressBar.visibility = View.GONE
                }
            }
        }

        toolbar.title = getString(R.string.loading)
        callbacks?.onToolbarInflated(toolbar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toggleBannerViews(viewModel.bannerIsEnabled)

        viewModel.htmlLiveData.observe(viewLifecycleOwner, { data ->
            if (data != null) {
                if (data.startsWith("http")) {
                    webView.loadUrl(data)
                    toggleBannerViews(false)
                } else {
                    webView.loadData(data, MIME_TYPE, ENCODING)
                    toggleBannerViews(viewModel.bannerIsEnabled)
                }

                toolbar.title = viewModel.entry?.website?.shortened()
                if (viewModel.bannerIsEnabled) viewModel.entry?.let { entry ->
                    updateBanner(entry.title, entry.date, entry.author)
                    Picasso.get().load(entry.image).fit().centerCrop()
                        .placeholder(R.drawable.vintage_newspaper).into(imageView)
                }
                setHasOptionsMenu(true)
            } else {
                toggleBannerViews(false)
                progressBar.visibility = View.GONE
                toolbar.title = getString(R.string.app_name)
                Utils.showErrorMessage(nestedScrollView, resources)
            }
        })
    }

    private fun toggleBannerViews(isEnabled: Boolean) {
        if (isEnabled) {
            imageView.visibility = View.VISIBLE
            titleTextView.visibility = View.VISIBLE
            subtitleTextView.visibility = View.VISIBLE
        } else {
            imageView.visibility = View.GONE
            titleTextView.visibility = View.GONE
            subtitleTextView.visibility = View.GONE
        }
    }

    private fun updateBanner(title: String, date: Date?, author: String?) {
        titleTextView.text = title
        val formattedDate = date?.let {
            DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(it)
        }
        subtitleTextView.text = when {
            author.isNullOrEmpty() -> formattedDate
            formattedDate.isNullOrEmpty() -> author
            else -> "$formattedDate – $author"
        }
    }

    override fun onStart() {
        super.onStart()
        toolbar.setOnClickListener { nestedScrollView.smoothScrollTo(0, 0) }
        imageView.setOnClickListener { handleViewInBrowser() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_entry, menu)
        starItem = menu.findItem(R.id.item_star)
        viewAsWebPageItem = menu.findItem(R.id.item_view_as_web_page)
        textSizeItem = menu.findItem(R.id.item_text_size)
        viewModel.entry?.let { entry -> toggleStarOptionItem(entry.isStarred) }
        toggleViewAsWebPageItem(viewModel.viewAsWebPage)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_star -> handleStar()
            R.id.item_share -> handleShare()
            R.id.item_copy_link -> handleCopyLink()
            R.id.item_view_in_browser -> handleViewInBrowser()
            R.id.item_view_as_web_page -> handleViewAsWebPage()
            R.id.item_text_size -> handleChangeTextSize()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleStar(): Boolean {
        viewModel.entry?.let { entry ->
            entry.isStarred = !entry.isStarred
            toggleStarOptionItem(entry.isStarred)
            return true
        } ?: return false
    }

    private fun toggleStarOptionItem(isStarred: Boolean) {
        starItem?.apply {
            title = if (isStarred) {
                setIcon(R.drawable.ic_star_yellow)
                getString(R.string.unstar)
            } else {
                setIcon(R.drawable.ic_star_border)
                getString(R.string.star)
            }
        }
    }

    private fun toggleViewAsWebPageItem(isViewingAsWebPage: Boolean) {
        viewAsWebPageItem?.apply {
            if (isViewingAsWebPage) {
                textSizeItem?.isEnabled = false
                title = getString(R.string.view_as_entry)
                setIcon(R.drawable.ic_notes)
            } else {
                textSizeItem?.isEnabled = true
                title = getString(R.string.view_as_web_page)
                setIcon(R.drawable.ic_web)
            }
        }
    }

    private fun handleShare(): Boolean {
        viewModel.entry?.let { entry ->
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, entry.title)
                putExtra(Intent.EXTRA_TEXT, entry.url)
            }.also { intent ->
                val chooserIntent = Intent.createChooser(intent, getString(R.string.share_entry))
                startActivity(chooserIntent)
            }
            return true
        } ?: return false
    }

    private fun handleCopyLink(): Boolean {
        viewModel.entry?.let { entry ->
            Utils.copyLinkToClipboard(requireContext(), entry.url, webView)
            return true
        } ?: return false
    }

    private fun handleViewInBrowser(): Boolean {
        Utils.openLink(requireActivity(), webView, Uri.parse(viewModel.entry?.url))
        return true
    }

    private fun handleViewAsWebPage(): Boolean {
        progressBar.visibility = View.VISIBLE
        viewModel.toggleLoadAsWebPage()
        toggleViewAsWebPageItem(viewModel.viewAsWebPage)
        return true
    }

    private fun handleChangeTextSize(): Boolean {
        saveScrollPosition()
        TextSizeFragment.newInstance(viewModel.textSize).apply {
            setTargetFragment(fragment, 0)
            show(fragment.parentFragmentManager, "change text size")
        }
        return true
    }

    override fun onTextSizeSelected(textSize: Int) {
        viewModel.setTextSize(textSize)
    }

    private fun saveScrollPosition() {
        viewModel.lastPosition = Pair(nestedScrollView.scrollX, nestedScrollView.scrollY)
    }

    override fun onStop() {
        super.onStop()
        saveScrollPosition()
        viewModel.isInitialLoading = false
        viewModel.saveChanges()
        context?.let { context ->
            NiceFeedPreferences.saveTextSize(context, viewModel.textSize)
            NiceFeedPreferences.setViewAsWebPage(context, viewModel.viewAsWebPage)
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