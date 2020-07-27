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
import com.joshuacerdenia.android.nicefeed.data.model.FeedInfo
import com.joshuacerdenia.android.nicefeed.data.model.FeedListItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_feed.view.*

private const val TYPE_ITEM = 0
private const val TYPE_HEADER = 1

class FeedListAdapter(
    private val context: Context?,
    private val listener: OnItemClickListener,
    var currentFeedId: String?
) : ListAdapter<FeedListItem, RecyclerView.ViewHolder>(DiffCallback()) {

    interface OnItemClickListener {
        fun onItemClicked(feedId: String)
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
                val isActive = currentFeedId == (getItem(position).content as FeedInfo).website
                holder.bind(getItem(position).content as FeedInfo, isActive)
            }
            is CategoryHolder -> holder.bind(getItem(position).content as String)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).content) {
            is FeedInfo -> TYPE_ITEM
            is String -> TYPE_HEADER
            else -> throw IllegalArgumentException()
        }
    }

    private inner class FeedHolder(
        private val context: Context?,
        view: View,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var feed: FeedInfo
        val itemContainer: ConstraintLayout = itemView.findViewById(R.id.item_container)
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val unreadCount: TextView = itemView.findViewById(R.id.item_count)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(feed: FeedInfo, isActive: Boolean) {
            this.feed = feed

            if (isActive) {
                // Change color
                context?.let {
                    itemContainer.setBackgroundColor(getColor(context, R.color.colorSelect))
                }
            }

            titleTextView.text = feed.title
            unreadCount.text = if (feed.unreadCount > 0) {
                feed.unreadCount.toString()
            } else {
                null
            }

            Picasso.get()
                .load(feed.imageUrl)
                .fit()
                .centerCrop(START)
                .placeholder(R.drawable.feed_icon_small)
                .into(itemView.image)
        }

        override fun onClick(v: View) {
            currentFeedId = feed.website
            listener.onItemClicked(feed.website)
        }
    }

    private class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {

        val headerTextView: TextView = itemView.findViewById(R.id.category)

        fun bind(category: String) {
            headerTextView.text = category
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<FeedListItem>() {

        override fun areItemsTheSame(oldItem: FeedListItem, newItem: FeedListItem): Boolean {
            return when {
                oldItem.content is FeedInfo && newItem.content is FeedInfo -> {
                    oldItem.content.website == newItem.content.website
                }
                oldItem.content is String && newItem.content is String -> {
                    oldItem.content == newItem.content
                }
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: FeedListItem, newItem: FeedListItem): Boolean {
            return oldItem == newItem
        }
    }
}