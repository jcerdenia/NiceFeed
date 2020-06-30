package com.joshuacerdenia.android.nicefeed

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.squareup.picasso.Picasso
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
    private val adapter: FeedAdapter = FeedAdapter()

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onSearchItemSelected(url: String) // TODO send selection back to MainActivity
        fun onNewFeedAdded(title: String)
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
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_feed_search, menu)

        val initialQuery = arguments?.getString(ARG_INITIAL_QUERY) ?: ""

        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String): Boolean {
                    if (queryText.isNotEmpty()) {
                        Log.d(TAG, "Searching '$queryText'...")
                        feedSearchViewModel.performSearch(queryText)
                        progressBar.visibility = View.VISIBLE
                    }
                    //activity?.let { Utils.hideSoftKeyBoard(it, this@apply) }
                    return true
                }

                override fun onQueryTextChange(queryText: String): Boolean {
                    feedSearchViewModel.newQuery = queryText
                    return true
                }
            })

            if (!feedSearchViewModel.initialQueryIsMade) {
                setQuery(initialQuery, true)
                feedSearchViewModel.initialQueryIsMade = true
            } else {
                setQuery(feedSearchViewModel.newQuery, false)
            }

            isIconified = false
            queryHint = getString(R.string.search_feeds___)
        }
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

        feedSearchViewModel.searchResultLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            progressBar.visibility = View.GONE
        })

        feedSearchViewModel.feedListLiveData.observe(viewLifecycleOwner, Observer {
            getCurrentFeedPaths(it)
        })

        feedSearchViewModel.feedRequestLiveData?.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                if (!isAlreadyAdded(it.feed.url)) {
                    feedWithEntries = it
                    feedSearchViewModel.requestFailedNoticeEnabled = false
                    showConfirmDialog(this@FeedSearchFragment, it.feed)
                } else {
                    showAlreadyAddedNotice()
                }
            } ?: showRequestFailedNotice(R.string.failed_to_connect)

            progressBar.visibility = View.GONE
        })
    }

    private inner class FeedAdapter
        : ListAdapter<SearchResultItem, FeedAdapter.FeedHolder>(DiffCallback()) {

        private val rssUrlGenerator = RssUrlGenerator()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
            val view = layoutInflater.inflate(R.layout.list_item_search_result, parent, false)
            return FeedHolder(view)
        }

        override fun onBindViewHolder(holder: FeedHolder, position: Int) {
            holder.bind(getItem(position))
        }

        private inner class FeedHolder(view: View)
            : RecyclerView.ViewHolder(view), View.OnClickListener {

            var feed = SearchResultItem()

            val titleTextView: TextView = itemView.findViewById(R.id.title)
            val descriptionTextView: TextView = itemView.findViewById(R.id.description)

            init {
                itemView.setOnClickListener(this)
            }

            fun bind(feed: SearchResultItem) {
                this.feed = feed

                titleTextView.text = feed.title
                descriptionTextView.text = feed.description

                Picasso.get()
                    .load(feed.imageUrl)
                    .resize(48, 48)
                    .placeholder(R.drawable.ic_rss_feed)
                    .into(itemView.image)
            }

            override fun onClick(v: View) {
                val url = feed.id?.let { rssUrlGenerator.getUrl(it) }
                val backupUrl = feed.website?.let { rssUrlGenerator.getUrl(it) }
                    // Useful to have a backup because the "website" property is also an URL

                BackupUrl.setUrl(backupUrl)
                Log.d(TAG, "URLS generated: $url, backup: ${BackupUrl.getUrl()}")

                if (url != null) {
                    feedSearchViewModel.requestFeed(url)
                    progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<SearchResultItem>() {

        override fun areItemsTheSame(oldItem: SearchResultItem, newItem: SearchResultItem):
                Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchResultItem, newItem: SearchResultItem):
                Boolean {
            return oldItem == newItem
        }
    }
}