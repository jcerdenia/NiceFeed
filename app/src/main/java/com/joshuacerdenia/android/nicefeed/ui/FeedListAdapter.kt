package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.view.Gravity.START
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedMenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_feed.view.*

private const val TYPE_ITEM = 0
private const val TYPE_HEADER = 1

class FeedListAdapter(
    private val context: Context?,
    private val listener: OnItemClickListener,
    private var activeFeedId: String? = null
) : ListAdapter<FeedMenuItem, RecyclerView.ViewHolder>(DiffCallback()) {

    private val arranger = MenuArranger(this)
    var categories = arrayOf<String>()

    interface OnItemClickListener {
        fun onItemClicked(feed: Feed)
    }

    fun submitFeeds(feeds: List<Feed>) {
        arranger.arrangeFeedsAndCategories(feeds)
    }

    fun overrideActiveFeedId(feedId: String?) {
        activeFeedId = feedId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_feed,
                    parent,
                    false
                )
                FeedHolder(context, view, listener)
            }
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.list_item_category,
                    parent,
                    false
                )
                CategoryHolder(view)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is FeedHolder -> {
                val isHighlighted = activeFeedId == (getItem(position).content as Feed).url
                holder.bind(getItem(position).content as Feed, isHighlighted)
            }
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

    private inner class FeedHolder(
        private val context: Context?,
        view: View,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var feed: Feed
        val itemContainer: ConstraintLayout = itemView.findViewById(R.id.item_container)
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val unreadCount: TextView = itemView.findViewById(R.id.item_count)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(feed: Feed, isHighlighted: Boolean) {
            this.feed = feed
            if (isHighlighted) {
                context?.let {
                    itemView.setBackgroundColor(getColor(context, R.color.colorSelect))
                }
            }

            titleTextView.text = feed.title
            unreadCount.text = if (feed.unreadCount > 0) {
                feed.unreadCount.toString()
            } else null

            Picasso.get()
                .load(feed.imageUrl)
                .fit()
                .centerCrop(START)
                .placeholder(R.drawable.feed_icon_small)
                .into(itemView.image)
        }

        override fun onClick(v: View) {
            activeFeedId = feed.url
            listener.onItemClicked(feed)
        }
    }

    private class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {

        val headerTextView: TextView = itemView.findViewById(R.id.category)

        fun bind(category: String) {
            headerTextView.text = category
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<FeedMenuItem>() {

        override fun areItemsTheSame(oldItem: FeedMenuItem, newItem: FeedMenuItem): Boolean {
            return when {
                oldItem.content is Feed && newItem.content is Feed -> {
                    oldItem.content.url == newItem.content.url
                }
                oldItem.content is String && newItem.content is String -> {
                    oldItem.content == newItem.content
                }
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: FeedMenuItem, newItem: FeedMenuItem): Boolean {
            return oldItem == newItem
        }
    }

    private class MenuArranger(private val adapter: FeedListAdapter) {

        fun arrangeFeedsAndCategories(feeds: List<Feed>) {
            val categories = getOrderedCategories(feeds)
            val arrangedMenu: MutableList<FeedMenuItem> = mutableListOf()

            for (category in categories) {
                arrangedMenu.add(FeedMenuItem(category))

                for (feed in feeds) {
                    if (feed.category == category) {
                        arrangedMenu.add(FeedMenuItem(feed))
                    }
                }
            }

            adapter.submitList(arrangedMenu)
            adapter.categories = categories.toTypedArray()
        }

        private fun getOrderedCategories(feeds: List<Feed>): List<String> {
            val categories: MutableSet<String> = mutableSetOf()
            for (feed in feeds) {
                categories.add(feed.category)
            }
            return categories.toList().sorted() // Sort alphabetically
        }
    }
}