package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.utils.Utils
import com.joshuacerdenia.android.nicefeed.utils.sortedByUnreadCount
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_feed.view.*

private const val TAG = "FeedListFragment"
private const val TYPE_ITEM = 0
private const val TYPE_HEADER = 1

class FeedListFragment: Fragment() {

    companion object {
        fun newInstance(): FeedListFragment {
            return FeedListFragment()
        }
    }

    private val feedListViewModel: FeedListViewModel by lazy {
        ViewModelProvider(this).get(FeedListViewModel::class.java)
    }

    private lateinit var manageFeedsButton: Button
    private lateinit var addFeedButton: Button
    private lateinit var feedRecyclerView: RecyclerView
    private var adapter: FeedAdapter = FeedAdapter()

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onManageFeedsSelected()
        fun onAddFeedSelected()
        fun onFeedSelected(website: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = activity as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed_list, container, false)

        manageFeedsButton = view.findViewById(R.id.manage_feeds)
        addFeedButton = view.findViewById(R.id.add_feed)
        feedRecyclerView = view.findViewById(R.id.feed_recycler_view)

        feedRecyclerView.layoutManager = LinearLayoutManager(context)
        feedRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageFeedsButton.setOnClickListener {
            callbacks?.onManageFeedsSelected()
        }

        addFeedButton.setOnClickListener {
            callbacks?.onAddFeedSelected()
        }

        feedListViewModel.feedListLiveData.observe(viewLifecycleOwner, Observer { feeds ->
            // TODO: Sort feeds by title, date updated, etc. Also, get only needed data for UI
            val arrangedList = arrangeFeedsAndCategories(feeds.sortedByUnreadCount())
            adapter.submitList(arrangedList)
        })
    }

    private fun arrangeFeedsAndCategories(feeds: List<Feed>): List<FeedAdapterItem> {
        val categories = Utils.getCategories(feeds)
        val arrangedList: MutableList<FeedAdapterItem> = mutableListOf()

        for (category in categories) {
            arrangedList.add(
                FeedAdapterItem(
                    category
                )
            )

            for (feed in feeds) {
                if (feed.category == category) {
                    arrangedList.add(
                        FeedAdapterItem(
                            feed
                        )
                    )
                }
            }
        }

        return arrangedList
    }
    
    private inner class FeedAdapter : ListAdapter<FeedAdapterItem, RecyclerView.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                TYPE_ITEM -> {
                    val view = layoutInflater.inflate(R.layout.list_item_feed, parent, false)
                    FeedHolder(view)
                }
                TYPE_HEADER -> {
                    val view = layoutInflater.inflate(R.layout.list_item_category, parent, false)
                    CategoryHolder(view)
                }
                else -> throw IllegalArgumentException()
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is FeedHolder -> holder.bind(getItem(position).content as Feed)
                is CategoryHolder -> holder.bind(getItem(position).content as String)
            }
        }

        override fun getItemViewType(position: Int): Int {
            return when (getItem(position).content) {
                is Feed -> TYPE_ITEM
                is String -> TYPE_HEADER
                else -> throw IllegalArgumentException()
            }
        }

        private inner class FeedHolder(view: View) : RecyclerView.ViewHolder(view),
            View.OnClickListener, View.OnLongClickListener {

            private var feed =
                Feed()

            val titleTextView: TextView = itemView.findViewById(R.id.title)
            val unreadCount: TextView = itemView.findViewById(R.id.item_count)

            init {
                itemView.setOnClickListener(this)
                itemView.setOnLongClickListener(this)
            }

            fun bind(feed: Feed) {
                this.feed = feed

                titleTextView.text = feed.title
                unreadCount.text = if (feed.unreadCount > 0) {
                    feed.unreadCount.toString()
                } else {
                    null
                }

                Picasso.get()
                    .load(feed.imageUrl)
                    .resize(24, 24)
                    .placeholder(R.drawable.ic_rss_feed)
                    .into(itemView.image)
            }

            override fun onClick(v: View) {
                callbacks?.onFeedSelected(feed.website)
            }

            // TODO: Manage feeds (delete, edit, etc.)
            override fun onLongClick(v: View?): Boolean {
                feedListViewModel.isManagingFeeds = !feedListViewModel.isManagingFeeds
                Log.d(TAG, "User is managing feeds: ${feedListViewModel.isManagingFeeds}")
                return true
            }
        }

        private inner class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {

            val headerTextView: TextView = itemView.findViewById(R.id.category)

            fun bind(category: String) {
                headerTextView.text = category
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<FeedAdapterItem>() {

        override fun areItemsTheSame(oldItem: FeedAdapterItem, newItem: FeedAdapterItem): Boolean {
            return when {
                oldItem.content is Feed && newItem.content is Feed ->
                    oldItem.content.website == newItem.content.website
                oldItem.content is String && newItem.content is String ->
                    oldItem.content == newItem.content
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: FeedAdapterItem, newItem: FeedAdapterItem): Boolean {
            return oldItem == newItem
        }
    }

    private data class FeedAdapterItem(val content: Any) // Can be a Feed item or Category header
}