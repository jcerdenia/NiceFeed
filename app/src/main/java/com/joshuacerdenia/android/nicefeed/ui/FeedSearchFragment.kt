package com.joshuacerdenia.android.nicefeed.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem
import com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmAddDialogFragment
import com.joshuacerdenia.android.nicefeed.utils.BackupUrls
import com.joshuacerdenia.android.nicefeed.utils.RssUrlGenerator
import com.joshuacerdenia.android.nicefeed.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_feed_search.*
import kotlinx.android.synthetic.main.list_item_search_result.view.*

private const val TAG = "FeedSearchFragment"
private const val ARG_INITIAL_QUERY = "ARG_INITIAL_QUERY"

class FeedSearchFragment: AddFeedFragment(), ConfirmAddDialogFragment.Callbacks {

    companion object {
        fun newInstance(initialQuery: String?): FeedSearchFragment {
            return FeedSearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_INITIAL_QUERY, initialQuery)
                }
            }
        }
    }

    private val feedSearchViewModel: FeedSearchViewModel by lazy {
        ViewModelProvider(this).get(FeedSearchViewModel::class.java)
    }

    private lateinit var feedRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private val adapter: FeedAdapter = FeedAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        feedSearchViewModel.performSearch(queryText)
                        progressBar.visibility = View.VISIBLE
                    }

                    clearFocus()
                    activity?.let { Utils.hideSoftKeyBoard(it, this@apply) }
                    return true
                }

                override fun onQueryTextChange(queryText: String): Boolean {
                    feedSearchViewModel.newQuery = queryText
                    return false
                }
            })

            if (!feedSearchViewModel.initialQueryIsMade) {
                setQuery(initialQuery, true)
                feedSearchViewModel.initialQueryIsMade = true
            } else {
                setQuery(feedSearchViewModel.newQuery, false)
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

        feedRecyclerView = view.findViewById(R.id.feed_recycler_view)
        feedRecyclerView.layoutManager = LinearLayoutManager(context)
        val divider = DividerItemDecoration(
            feedRecyclerView.context,
            (feedRecyclerView.layoutManager as LinearLayoutManager).orientation
        )
        feedRecyclerView.addItemDecoration(divider)
        feedRecyclerView.adapter = adapter
        
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedSearchViewModel.feedListLiveData.observe(viewLifecycleOwner, Observer {
            for (feed in it) {
                currentFeeds.add(feed.website)
            }
        })

        feedSearchViewModel.searchResultLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            progressBar.visibility = View.GONE
        })

        feedSearchViewModel.feedRequestLiveData?.observe(viewLifecycleOwner, Observer {
            handleFeedRequestResult(
                feedSearchViewModel,
                constraint_layout,
                it,
                R.string.failed_to_connect
            )

            feedSearchViewModel.selectedItemProgressBar?.visibility = View.INVISIBLE
            feedSearchViewModel.itemSelectionEnabled = true
        })
    }

    private fun showConfirmDialog(fragment: Fragment, searchResultItem: SearchResultItem) {
        ConfirmAddDialogFragment.newInstance(searchResultItem).apply {
            setTargetFragment(fragment, 0)
            show(fragment.requireFragmentManager(), "confirm add")
        }
    }

    override fun onAddConfirmed(feed: SearchResultItem) {
        feedSearchViewModel.selectedItemProgressBar?.visibility = View.VISIBLE
        feedSearchViewModel.itemSelectionEnabled = false

        val url = feed.id?.let { RssUrlGenerator.getUrl(it) }

        // Good to have backup because "website" property is also a usable URL
        val backupUrl = feed.website?.let { RssUrlGenerator.getUrl(it) }
        BackupUrls.setBase(backupUrl)

        Log.d(TAG, "URLs generated: $url, backup: ${BackupUrls.get()}. Requesting...")
        if (url != null) {
            feedSearchViewModel.requestFeed(url)
        }
    }

    private inner class FeedAdapter : ListAdapter<SearchResultItem, FeedAdapter.FeedHolder>(
        DiffCallback()
    ) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
            val view = layoutInflater.inflate(R.layout.list_item_search_result, parent, false)
            return FeedHolder(view)
        }

        override fun onBindViewHolder(holder: FeedHolder, position: Int) {
            holder.bind(getItem(position))
        }

        private inner class FeedHolder(view: View)
            : RecyclerView.ViewHolder(view), View.OnClickListener {

            var feed =
                SearchResultItem()

            val titleTextView: TextView = itemView.findViewById(R.id.title)
            val descriptionTextView: TextView = itemView.findViewById(R.id.description)
            val itemProgressBar: ProgressBar = itemView.findViewById(R.id.item_progress_bar)

            init {
                itemView.setOnClickListener(this)
            }

            fun bind(feed: SearchResultItem) {
                this.feed = feed

                titleTextView.text = feed.title
                descriptionTextView.text = feed.description

                Picasso.get()
                    .load(feed.imageUrl)
                    .placeholder(R.drawable.ic_rss_feed)
                    .into(itemView.image)
            }

            override fun onClick(v: View) {
                searchView.clearFocus()
                activity?.let { Utils.hideSoftKeyBoard(it, feedRecyclerView) }

                if (feedSearchViewModel.itemSelectionEnabled) {
                    feedSearchViewModel.selectedItemProgressBar = itemProgressBar
                    showConfirmDialog(this@FeedSearchFragment, feed)
                }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<SearchResultItem>() {

        override fun areItemsTheSame(oldItem: SearchResultItem, newItem: SearchResultItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchResultItem, newItem: SearchResultItem): Boolean {
            return oldItem == newItem
        }
    }
}