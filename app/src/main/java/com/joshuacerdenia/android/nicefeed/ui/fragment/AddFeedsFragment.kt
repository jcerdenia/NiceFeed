package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.feed.Feed
import com.joshuacerdenia.android.nicefeed.ui.FeedRequestCallbacks
import com.joshuacerdenia.android.nicefeed.ui.adapter.TopicAdapter
import com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmImportFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.InputUrlFragment
import com.joshuacerdenia.android.nicefeed.ui.viewmodel.AddFeedsViewModel
import com.joshuacerdenia.android.nicefeed.utils.OpmlImporter
import com.joshuacerdenia.android.nicefeed.utils.Utils
import com.joshuacerdenia.android.nicefeed.utils.work.BackgroundSyncWorker
import java.util.*

class AddFeedsFragment: FeedAddingFragment(),
    OpmlImporter.OnOpmlParsedListener,
    ConfirmImportFragment.Callbacks,
    TopicAdapter.OnItemClickListener,
    FeedRequestCallbacks
{

    private lateinit var viewModel: AddFeedsViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var linearLayout: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TopicAdapter
    private lateinit var addUrlTextView: TextView
    private lateinit var importOpmlTextView: TextView
    private lateinit var searchView: SearchView

    private val fragment = this@AddFeedsFragment
    private var opmlImporter: OpmlImporter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddFeedsViewModel::class.java)
        viewModel.initDefaultTopics(viewModel.defaultTopicsResId.map { getString(it) })
        opmlImporter = OpmlImporter(requireContext(), this)
        adapter = TopicAdapter(requireContext(), this)
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_feeds, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        linearLayout = view.findViewById(R.id.linearLayout)
        searchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.recycler_view)
        addUrlTextView = view.findViewById(R.id.add_url_text_view)
        importOpmlTextView = view.findViewById(R.id.import_opml_text_view)
        setupRecyclerView()
        setupToolbar()
        return view
    }
    
    private fun setupRecyclerView() {
        val span = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 5
        recyclerView.layoutManager = GridLayoutManager(context, span)
        recyclerView.adapter = adapter.apply { numOfItems = if (span == 3) 9 else 10 }
    }
    
    private fun setupToolbar() {
        toolbar.title = getString(R.string.add_feeds)
        callbacks?.onToolbarInflated(toolbar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manager = RequestResultManager(viewModel, linearLayout, R.string.failed_to_get_feed)

        viewModel.feedIdsWithCategoriesLiveData.observe(viewLifecycleOwner, { data ->
            viewModel.onFeedDataRetrieved(data)
        })

        viewModel.topicBlocksLiveData.observe(viewLifecycleOwner, { topics ->
            adapter.submitList(topics.toMutableList())
        })

        viewModel.feedRequestLiveData.observe(viewLifecycleOwner, { feedWithEntries ->
            // A little delay to prevent resulting snackbar from jumping:
            Handler().postDelayed({ manager?.submitData(feedWithEntries) }, 250)
            InputUrlFragment.dismissInstance()
        })
    }

    override fun onStart() {
        super.onStart()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryText: String): Boolean {
                if (queryText.isNotBlank()) callbacks?.onQuerySubmitted(queryText)
                return true
            }

            override fun onQueryTextChange(queryText: String?): Boolean {
                return true
            }
        })

        addUrlTextView.setOnClickListener {
            InputUrlFragment.newInstance(viewModel.lastAttemptedUrl).apply {
                setTargetFragment(fragment, 0)
                show(fragment.parentFragmentManager, "TAG")
            }
        }

        importOpmlTextView.setOnClickListener {
            callbacks?.onImportOpmlSelected()
        }
    }

    override fun onRequestSubmitted(url: String, backup: String?) {
        viewModel.lastAttemptedUrl = url
        val link = url.toLowerCase(Locale.ROOT).trim()
        if (link.contains("://")) {
            viewModel.requestFeed(url) // If scheme is provided, use as is
        } else {
            viewModel.requestFeed("https://$link", "http://$link")
        }
    }

    override fun onRequestDismissed() {
        viewModel.cancelRequest()
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
                BackgroundSyncWorker.runOnce(requireContext().applicationContext)
                callbacks?.onFinished()
            }.show()
    }

    override fun onTopicSelected(topic: String) {
        callbacks?.onQuerySubmitted(topic)
    }

    companion object {

        fun newInstance(): AddFeedsFragment {
            return AddFeedsFragment()
        }
    }
}