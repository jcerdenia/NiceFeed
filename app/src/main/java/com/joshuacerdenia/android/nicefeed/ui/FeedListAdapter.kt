package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.util.Log
import android.view.Gravity.START
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.CategoryHeader
import com.joshuacerdenia.android.nicefeed.data.model.FeedLight
import com.joshuacerdenia.android.nicefeed.data.model.FeedMenuItem
import com.joshuacerdenia.android.nicefeed.utils.addRipple
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_feed.view.*

private const val TYPE_ITEM = 0
private const val TYPE_HEADER = 1

class FeedListAdapter(
    private val context: Context?,
    private val listener: OnItemClickListener
) : ListAdapter<FeedMenuItem, RecyclerView.ViewHolder>(DiffCallback()) {

    private var activeFeedId: String? = null

    interface OnItemClickListener {
        fun onFeedSelected(feedId: String)
        fun onCategoryClicked(category: String)
    }

    fun setActiveFeedId(feedId: String?) {
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
                FeedHolder(view)
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
                val isHighlighted = activeFeedId == (getItem(position).content as FeedLight).url
                holder.bind(getItem(position).content as FeedLight, isHighlighted)
            }
            is CategoryHolder -> holder.bind(getItem(position).content as CategoryHeader)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).content) {
            is FeedLight -> TYPE_ITEM
            is CategoryHeader -> TYPE_HEADER
            else -> throw IllegalArgumentException()
        }
    }

    private inner class FeedHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var feed: FeedLight
        private val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        private val countTextView: TextView = itemView.findViewById(R.id.item_count_text_view)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(feed: FeedLight, isHighlighted: Boolean) {
            this.feed = feed

            if (isHighlighted) {
                context?.let { context ->
                    itemView.setBackgroundColor(getColor(context, R.color.colorSelect))
                }
            } else {
                itemView.addRipple()
            }

            titleTextView.text = feed.title
            countTextView.text = if (feed.unreadCount > 0) {
                feed.unreadCount.toString()
            } else {
                null
            }

            Picasso.get()
                .load(feed.imageUrl)
                .fit()
                .centerCrop(START)
                .placeholder(R.drawable.feed_icon_small)
                .into(itemView.image_view)
        }

        override fun onClick(v: View) {
            listener.onFeedSelected(feed.url)
        }
    }

    private inner class CategoryHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var category: String
        private val categoryTextView: TextView = itemView.findViewById(R.id.category_text_view)
        private val countTextView: TextView = itemView.findViewById(R.id.item_count_text_view)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(categoryHeader: CategoryHeader) {
            this.category = categoryHeader.category
            categoryTextView.text = categoryHeader.category

            val drawableResId: Int
            if (categoryHeader.isMinimized) {
                drawableResId = R.drawable.ic_drop_down
                if (categoryHeader.unreadCount > 0) {
                    countTextView.visibility = View.VISIBLE
                    countTextView.text = categoryHeader.unreadCount.toString()
                } else {
                    countTextView.visibility = View.GONE
                }
            } else {
                drawableResId = R.drawable.ic_drop_up
                countTextView.visibility = View.GONE
            }

            context?.let {
                ContextCompat.getDrawable(context, drawableResId).also { drawable ->
                    categoryTextView.setCompoundDrawablesWithIntrinsicBounds(
                        drawable,
                        null,
                        null,
                        null
                    )
                }
            }
        }

        override fun onClick(v: View?) {
            listener.onCategoryClicked(category)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<FeedMenuItem>() {

        override fun areItemsTheSame(oldItem: FeedMenuItem, newItem: FeedMenuItem): Boolean {
            return when {
                oldItem.content is FeedLight && newItem.content is FeedLight -> {
                    oldItem.content.url == newItem.content.url
                }
                oldItem.content is CategoryHeader && newItem.content is CategoryHeader -> {
                    oldItem.content.category == newItem.content.category
                }
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: FeedMenuItem, newItem: FeedMenuItem): Boolean {
            return oldItem == newItem
        }
    }
}