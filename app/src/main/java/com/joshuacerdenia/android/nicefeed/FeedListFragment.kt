package com.joshuacerdenia.android.nicefeed

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_feed.view.*

private const val TAG = "FeedListFragment"

private fun List<Feed>.sortedByTitle() = this.sortedBy{ (_, _, title) -> title }

class FeedListFragment: Fragment() {

    companion object {
        fun newInstance(): FeedListFragment {
            return FeedListFragment()
        }
    }

    private val feedListViewModel: FeedListViewModel by lazy {
        ViewModelProvider(this).get(FeedListViewModel::class.java)
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var addFeedButton: Button
    private lateinit var feedRecyclerView: RecyclerView
    private var adapter: FeedAdapter = FeedAdapter()

    private var callbacks: Callbacks? = null

    interface Callbacks {
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

        progressBar = view.findViewById(R.id.progress_bar)
        addFeedButton = view.findViewById(R.id.add_feed)
        feedRecyclerView = view.findViewById(R.id.feed_recycler_view)

        feedRecyclerView.layoutManager = LinearLayoutManager(context)

        val divider = DividerItemDecoration(
            feedRecyclerView.context,
            (feedRecyclerView.layoutManager as LinearLayoutManager).orientation
        )
        //feedRecyclerView.addItemDecoration(divider)
        feedRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFeedButton.setOnClickListener {
            callbacks?.onAddFeedSelected()
        }

        feedListViewModel.feedListLiveData.observe(viewLifecycleOwner, Observer { feeds ->
            // TODO: Sort feeds by title, date updated, etc.
            adapter.submitList(feeds.sortedByTitle()) // initial UI population
            progressBar.visibility = View.GONE
        })
    }

    private inner class FeedAdapter
        : ListAdapter<Feed, FeedAdapter.FeedHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
            val view = layoutInflater.inflate(R.layout.list_item_feed, parent, false)
            return FeedHolder(view)
        }

        override fun onBindViewHolder(holder: FeedHolder, position: Int) {
            holder.bind(getItem(position))
        }

        private inner class FeedHolder(view: View)
            : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

            private var feed = Feed()

            val titleTextView: TextView = itemView.findViewById(R.id.title)
            val descriptionTextView: TextView = itemView.findViewById(R.id.description)
            val unreadCount: TextView = itemView.findViewById(R.id.item_count)

            init {
                itemView.setOnClickListener(this)
                itemView.setOnLongClickListener(this)
            }

            fun bind(feed: Feed) {
                this.feed = feed

                titleTextView.text = feed.title
                unreadCount.text = feed.unreadCount.toString() // TODO: change dynamically

                // TODO: Option to hide description
                descriptionTextView.apply {
                    if (feed.description.isNullOrEmpty()) {
                        visibility = View.GONE
                    } else {
                        text = feed.description
                    }
                }

                Picasso.get()
                    .load(feed.imageUrl)
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
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<Feed>() {

        override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem.website == newItem.website
        }

        override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem == newItem
        }
    }
}