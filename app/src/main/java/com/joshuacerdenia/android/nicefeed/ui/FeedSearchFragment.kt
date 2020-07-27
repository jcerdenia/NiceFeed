package com.joshuacerdenia.android.nicefeed.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem
import com.joshuacerdenia.android.nicefeed.ui.dialog.SubscribeFragment
import com.joshuacerdenia.android.nicefeed.utils.RssUrlTransformer
import com.joshuacerdenia.android.nicefeed.utils.Utils
import kotlinx.android.synthetic.main.fragment_feed_search.*

private const val TAG = "FeedSearchFragment"
private const val ARG_INITIAL_QUERY = "ARG_INITIAL_QUERY"

class FeedSearchFragment : AddFeedFragment(),
    SubscribeFragment.Callbacks,
    FeedSearchAdapter.OnItemClickListener
{

    companion object {
        fun newInstance(initialQuery: String?): FeedSearchFragment {
            return FeedSearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_INITIAL_QUERY, initialQuery)
                }
            }
        }
    }

    private val fragment = this@FeedSearchFragment
    private val viewModel: FeedSearchViewModel by lazy {
        ViewModelProvider(this).get(FeedSearchViewModel::class.java)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: FeedSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FeedSearchAdapter(this, viewModel.itemBeingLoaded)
        setHasOptionsMenu(true)
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
                        viewModel.performSearch(queryText)
                        progressBar.visibility = View.VISIBLE
                    }

                    clearFocus()
                    activity?.let { Utils.hideSoftKeyBoard(it, this@apply) }
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

        activity?.let { Utils.hideSoftKeyBoard(it, searchView) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed_search, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.feed_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.feedListLiveData.observe(viewLifecycleOwner, Observer {
            for (feed in it) {
                currentFeeds.add(feed.website)
            }
        })

        viewModel.searchResultLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            progressBar.visibility = View.GONE
        })

        viewModel.feedRequestLiveData?.observe(viewLifecycleOwner, Observer {
            handleFeedRequestResult(
                viewModel,
                constraint_layout,
                it,
                R.string.failed_to_connect
            )

            adapter.onFinishedLoading()
            viewModel.itemBeingLoaded = null
            viewModel.itemSelectionEnabled = true
        })
    }

    override fun onAddConfirmed(searchResultItem: SearchResultItem) {
        viewModel.itemBeingLoaded = searchResultItem
        adapter.onLoadingItem(searchResultItem.id)
        viewModel.itemSelectionEnabled = false

        val url = searchResultItem.id?.let { RssUrlTransformer.getUrl(it) } ?: ""
        val backupUrl = searchResultItem.website?.let { RssUrlTransformer.getUrl(it) } ?: ""
        // Good to have backup because "website" property is also a usable URL

        viewModel.requestFeed(url, backupUrl)
    }

    override fun onItemClicked(searchResultItem: SearchResultItem) {
        searchView.clearFocus()
        activity?.let { Utils.hideSoftKeyBoard(it, recyclerView) }

        if (viewModel.itemSelectionEnabled) {
            SubscribeFragment.newInstance(searchResultItem).apply {
                setTargetFragment(fragment, 0)
                show(fragment.requireFragmentManager(), "confirm add")
            }
        }
    }
}