package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.FeedPreferences
import com.joshuacerdenia.android.nicefeed.databinding.FragmentEntryBinding
import com.joshuacerdenia.android.nicefeed.databinding.ToolbarBinding
import com.joshuacerdenia.android.nicefeed.ui.OnToolbarInflated
import com.joshuacerdenia.android.nicefeed.ui.dialog.TextSizeFragment
import com.joshuacerdenia.android.nicefeed.ui.viewmodel.EntryViewModel
import com.joshuacerdenia.android.nicefeed.util.EntryToHtmlUtil
import com.joshuacerdenia.android.nicefeed.util.Utils
import com.joshuacerdenia.android.nicefeed.util.extensions.hide
import com.joshuacerdenia.android.nicefeed.util.extensions.setSimpleVisibility
import com.joshuacerdenia.android.nicefeed.util.extensions.shortened
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.util.*

class EntryFragment: VisibleFragment() {

    private var _binding : FragmentEntryBinding? = null
    private var _toolbarBinding: ToolbarBinding? = null
    private val binding get() = _binding!!
    private val toolbarBinding get() = _toolbarBinding!!

    private lateinit var viewModel: EntryViewModel
    private var callbacks: Callbacks? = null
    private var starMenuItem: MenuItem? = null
    private var appTheme = 0

    interface Callbacks: OnToolbarInflated

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EntryViewModel::class.java)
        appTheme = AppCompatDelegate.getDefaultNightMode()
        arguments?.getString(ENTRY_ID)?.let { entryId ->
            viewModel.getEntryById(entryId)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntryBinding.inflate(inflater, container, false)
        _toolbarBinding = binding.toolbar

        binding.webView.apply {
            setBackgroundColor(Color.TRANSPARENT)
            settings.javaScriptEnabled = true
            settings.builtInZoomControls = false
            settings.displayZoomControls = false
            webViewClient = EntryWebViewClient()
            webChromeClient = EntryWebChromeClient()
        }

        toolbarBinding.toolbar.title = getString(R.string.loading)
        callbacks?.onToolbarInflated(toolbarBinding.toolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.htmlLiveData.observe(viewLifecycleOwner, { html ->
            if (html != null) {
                binding.webView.loadData(html, MIME_TYPE, ENCODING)
                toggleBannerViews(viewModel.isBannerEnabled)
                setHasOptionsMenu(true)
                toolbarBinding.toolbar.title = viewModel.entry?.website?.shortened()
                if (viewModel.isBannerEnabled) viewModel.entry?.let { entry ->
                    updateBanner(entry.title, entry.date, entry.author)
                    Picasso.get().load(entry.image).fit().centerCrop()
                        .placeholder(R.drawable.vintage_newspaper).into(binding.imageView)
                }
            } else {
                toggleBannerViews(false)
                binding.progressBar.hide()
                toolbarBinding.toolbar.title = getString(R.string.app_name)
                Utils.showErrorMessage(binding.root, resources)
            }
        })

        parentFragmentManager.setFragmentResultListener(
            TextSizeFragment.TEXT_SIZE,
            viewLifecycleOwner,
            { key, result ->
                result.getInt(key).run { viewModel.setTextSize(this) }
            }
        )

        toolbarBinding.toolbar.setOnClickListener {
            binding.nestedScrollView.smoothScrollTo(0, 0)
        }

        binding.imageView.setOnClickListener {
            handleViewInBrowser()
        }
    }

    private fun toggleBannerViews(isEnabled: Boolean) {
        binding.imageView.setSimpleVisibility(isEnabled)
        binding.titleTextView.setSimpleVisibility(isEnabled)
        binding.subtitleTextView.setSimpleVisibility(isEnabled)
    }

    private fun updateBanner(title: String, date: Date?, author: String?) {
        binding.titleTextView.text = HtmlCompat.fromHtml(title, 0)

        val formattedDate = date?.let {
            DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(it)
        }

        binding.subtitleTextView.text = when {
            author.isNullOrEmpty() -> formattedDate
            formattedDate.isNullOrEmpty() -> author
            else -> "$formattedDate – $author"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_entry, menu)
        starMenuItem = menu.findItem(R.id.item_star)
        viewModel.entry?.let { toggleStarOptionItem(it.isStarred) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_star -> handleStar()
            R.id.item_share -> handleShare()
            R.id.item_copy_link -> handleCopyLink()
            R.id.item_view_in_browser -> handleViewInBrowser()
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
        starMenuItem?.apply {
            title = if (isStarred) {
                setIcon(R.drawable.ic_star_yellow)
                getString(R.string.unstar)
            } else {
                setIcon(R.drawable.ic_star_border)
                getString(R.string.star)
            }
        }
    }

    private fun handleShare(): Boolean {
        viewModel.entry?.let { entry ->
            Intent(Intent.ACTION_SEND)
                .apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, entry.title)
                    putExtra(Intent.EXTRA_TEXT, entry.url)
                }
                .run { Intent.createChooser(this, getString(R.string.share_entry)) }
                .run { startActivity(this) }
            return true
        } ?: return false
    }

    private fun handleCopyLink(): Boolean {
        viewModel.entry?.let { entry ->
            Utils.copyLinkToClipboard(requireContext(), entry.url, binding.root)
            return true
        } ?: return false
    }

    private fun handleViewInBrowser(): Boolean {
        Utils.openLink(requireActivity(), binding.root, Uri.parse(viewModel.entry?.url))
        return true
    }

    private fun handleChangeTextSize(): Boolean {
        saveScrollPosition()
        TextSizeFragment
            .newInstance(viewModel.textSize)
            .show(parentFragmentManager, TextSizeFragment.TAG)
        return true
    }

    private fun saveScrollPosition() {
        viewModel.lastPosition = Pair(
            binding.nestedScrollView.scrollX,
            binding.nestedScrollView.scrollY
        )
    }

    override fun onStop() {
        saveScrollPosition()
        viewModel.isInitialLoading = false
        viewModel.saveChanges()
        FeedPreferences.textSize = viewModel.textSize
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    inner class EntryWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            // Open all links with default browser.
            request?.url?.let { url ->
                Utils.openLink(requireActivity(), binding.root, url)
            }

            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            if (appTheme == AppCompatDelegate.MODE_NIGHT_YES && Build.VERSION.SDK_INT < 29) {
                EntryToHtmlUtil.DARK_MODE_JAVASCRIPT.trimIndent().run { view?.loadUrl(this) }
            }

            if (!viewModel.isInitialLoading) {
                val (x, y) = viewModel.lastPosition
                binding.nestedScrollView.smoothScrollTo(x, y)
            }
        }
    }

    inner class EntryWebChromeClient : WebChromeClient() {

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            binding.progressBar.progress = newProgress
            if (newProgress == 100) binding.progressBar.hide()
        }
    }

    companion object {

        private const val ENTRY_ID = "ENTRY_ID"
        private const val MIME_TYPE = "text/html; charset=UTF-8"
        private const val ENCODING = "base64"

        fun newInstance(entryId: String): EntryFragment {
            return EntryFragment().apply {
                arguments = Bundle().apply {
                    putString(ENTRY_ID, entryId)
                }
            }
        }
    }
}