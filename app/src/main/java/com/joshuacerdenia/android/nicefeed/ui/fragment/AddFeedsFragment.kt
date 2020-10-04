package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmImportFragment
import com.joshuacerdenia.android.nicefeed.ui.viewmodel.AddFeedsViewModel
import com.joshuacerdenia.android.nicefeed.utils.OpmlImporter
import com.joshuacerdenia.android.nicefeed.utils.Utils
import com.joshuacerdenia.android.nicefeed.utils.work.NewEntriesWorker
import com.joshuacerdenia.android.nicefeed.utils.work.UpdateAllWorker
import java.util.*

class AddFeedsFragment: FeedAddingFragment(),
    OpmlImporter.OnOpmlParsedListener,
    ConfirmImportFragment.Callbacks
{

    private lateinit var viewModel: AddFeedsViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var linearLayout: LinearLayout
    private lateinit var urlEditText: EditText
    private lateinit var subscribeButton: Button
    private lateinit var importOpmlButton: Button
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar

    private val fragment = this@AddFeedsFragment
    private var opmlImporter: OpmlImporter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddFeedsViewModel::class.java)
        context?.let { context -> opmlImporter = OpmlImporter(context, this) }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_feeds, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        linearLayout = view.findViewById(R.id.linearLayout)
        urlEditText = view.findViewById(R.id.editText_url)
        subscribeButton = view.findViewById(R.id.button_subscribe)
        importOpmlButton = view.findViewById(R.id.button_import_opml)
        searchView = view.findViewById(R.id.searchView)
        progressBar = view.findViewById(R.id.progressBar_add_feed)

        toolbar.title = getString(R.string.add_feeds)
        callbacks?.onToolbarInflated(toolbar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resultManager = RequestResultManager(
            viewModel,
            linearLayout,
            R.string.failed_to_get_feed,
            urlEditText
        )

        viewModel.feedIdsLiveData.observe(viewLifecycleOwner, { feedIds ->
            viewModel.onFeedIdsObtained(feedIds)
        })

        viewModel.feedRequestLiveData.observe(viewLifecycleOwner, { feedWithEntries ->
            progressBar.visibility = View.INVISIBLE
            resultManager.submitData(feedWithEntries)
            subscribeButton.apply {
                isEnabled = true
                text = getString(R.string.subscribe)
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryText: String): Boolean {
                if (queryText.isNotBlank()) callbacks?.onQuerySubmitted(queryText)
                return true
            }

            override fun onQueryTextChange(queryText: String?): Boolean {
                return true
            }
        })

        subscribeButton.setOnClickListener {
            val url = urlEditText.text.toString().toLowerCase(Locale.ROOT).trim()
            if (url.contains("://")) {
                viewModel.requestFeed(url) // If scheme is provided, use as is
            } else viewModel.requestFeed("https://$url", "http://$url")

            Utils.hideSoftKeyBoard(requireActivity(), linearLayout)
            progressBar.visibility = View.VISIBLE
            subscribeButton.apply {
                isEnabled = false
                text = getString(R.string.loading)
            }
        }

        importOpmlButton.setOnClickListener { callbacks?.onImportOpmlSelected() }
    }

    fun submitUriForImport(uri: Uri) {
        opmlImporter?.submitUri(uri)
    }

    override fun onOpmlParsed(feeds: List<Feed>) {
        viewModel.feedsToImport = feeds.filterNot { viewModel.currentFeedIds.contains(it.url) }
        ConfirmImportFragment.newInstance(feeds.size).apply {
            setTargetFragment(fragment, 0)
            show(fragment.parentFragmentManager, "confirm import")
        }
    }

    override fun onParseOpmlFailed() {
        Utils.showErrorMessage(linearLayout, resources)
    }

    override fun onImportConfirmed() {
        viewModel.feedsToImport.toTypedArray().run { viewModel.addFeeds(*this) }
        viewModel.feedsToImport = emptyList()

        Snackbar.make(linearLayout, getString(R.string.import_successful), Snackbar.LENGTH_SHORT)
            .setAction(R.string.update_all) {
                UpdateAllWorker.start(requireContext().applicationContext)
                callbacks?.onFinished()
            }.show()
    }

    companion object {

        fun newInstance(): AddFeedsFragment {
            return AddFeedsFragment()
        }
    }
}