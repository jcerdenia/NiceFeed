package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem
import com.joshuacerdenia.android.nicefeed.ui.FeedRequestCallbacks
import com.joshuacerdenia.android.nicefeed.ui.adapter.FeedSearchAdapter
import com.joshuacerdenia.android.nicefeed.ui.dialog.InputUrlFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.SubscribeFragment
import com.joshuacerdenia.android.nicefeed.ui.viewmodel.SearchFeedsViewModel
import com.joshuacerdenia.android.nicefeed.utils.Utils
import com.joshuacerdenia.android.nicefeed.utils.extensions.hide
import com.joshuacerdenia.android.nicefeed.utils.extensions.show

class SearchFeedsFragment : FeedAddingFragment(),
    FeedSearchAdapter.OnItemClickListener,
    FeedRequestCallbacks {

    private lateinit var viewModel: SearchFeedsViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var noItemsTextView: TextView
    private lateinit var searchView: SearchView
    private lateinit var adapter: FeedSearchAdapter

    private val fragment = this@SearchFeedsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchFeedsViewModel::class.java)
        adapter = FeedSearchAdapter(this)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_feeds, container, false)
        progressBar = view.findViewById(R.id.search_progress_bar)
        noItemsTextView = view.findViewById(R.id.empty_message_text_view)
        recyclerView = view.findViewById(R.id.recycler_view)
        toolbar = view.findViewById(R.id.toolbar)
        setupRecyclerView()
        setupToolbar()
        return view
    }
    
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
    
    private fun setupToolbar() {
        toolbar.title = getString(R.string.search_feeds)
        callbacks?.onToolbarInflated(toolbar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manager = RequestResultManager(viewModel, recyclerView, R.string.failed_to_connect)
        
        viewModel.feedIdsLiveData.observe(viewLifecycleOwner, { feedIds ->
            viewModel.onFeedIdsRetrieved(feedIds)
        })

        viewModel.searchResultLiveData.observe(viewLifecycleOwner, { results ->
            adapter.submitList(results)
            progressBar.hide()
            if (results.isEmpty()) noItemsTextView.show() else noItemsTextView.hide()
        })

        viewModel.feedRequestLiveData.observe(viewLifecycleOwner, { feedWithEntries ->
            manager?.submitData(feedWithEntries)
            if (viewModel.isActiveRequest) {
                parentFragmentManager.findFragmentByTag(SubscribeFragment.TAG).let { fragment ->
                    (fragment as? DialogFragment)?.dismiss()
                    viewModel.isActiveRequest = false
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_feed_search, menu)
        val initialQuery = arguments?.getString(ARG_INITIAL_QUERY) ?: ""
        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        searchView = searchItem.actionView as SearchView

        searchView.apply {
            isIconified = false
            queryHint = getString(R.string.search_feeds___)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String): Boolean {
                    if (queryText.isNotEmpty()) {
                        progressBar.show()
                        viewModel.performSearch(queryText)
                    }
                    clearFocus()
                    Utils.hideSoftKeyBoard(requireActivity(), this@apply)
                    return true
                }

                override fun onQueryTextChange(queryText: String): Boolean {
                    viewModel.newQuery = queryText
                    return false
                }
            })

            if (!viewModel.initialQueryIsMade) {
                setQuery(initialQuery, true)
                viewModel.initialQueryIsMade = true
            } else {
                setQuery(viewModel.newQuery, false)
                clearFocus()
            }
        }

        Utils.hideSoftKeyBoard(requireActivity(), searchView)
    }

    override fun onRequestSubmitted(url: String, backup: String?) {
        viewModel.requestFeed(url, backup)
    }

    override fun onRequestDismissed() {
        viewModel.cancelRequest()
    }

    override fun onItemClicked(searchResultItem: SearchResultItem) {
        searchView.clearFocus()
        activity?.let { Utils.hideSoftKeyBoard(it, recyclerView) }

        SubscribeFragment.newInstance(searchResultItem).apply {
            setTargetFragment(fragment, 0)
            show(fragment.parentFragmentManager, SubscribeFragment.TAG)
        }
    }

    companion object {

        private const val ARG_INITIAL_QUERY = "ARG_INITIAL_QUERY"

        fun newInstance(initialQuery: String?): SearchFeedsFragment {
            return SearchFeedsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_INITIAL_QUERY, initialQuery)
                }
            }
        }
    }
}