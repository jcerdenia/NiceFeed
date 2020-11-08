package com.joshuacerdenia.android.nicefeed.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem
import com.joshuacerdenia.android.nicefeed.util.extensions.simplified
import com.squareup.picasso.Picasso

class FeedSearchAdapter(
    private val listener: OnItemClickListener,
) : ListAdapter<SearchResultItem, FeedSearchAdapter.FeedHolder>(DiffCallback()) {

    interface OnItemClickListener {
        fun onItemClicked(searchResultItem: SearchResultItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_search_result, parent, false)
        return FeedHolder(view, listener)
    }

    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FeedHolder(
        view: View,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var searchResultItem: SearchResultItem
        private val titleTextView: TextView = itemView.findViewById(R.id.textView_title)
        private val infoTextView: TextView = itemView.findViewById(R.id.textView_info)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView_image)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(searchResultItem: SearchResultItem) {
            this.searchResultItem = searchResultItem

            titleTextView.text = searchResultItem.title
            infoTextView.text = searchResultItem.website?.simplified()
            Picasso.get().load(searchResultItem.imageUrl)
                .placeholder(R.drawable.feed_icon).into(imageView)
        }

        override fun onClick(v: View) {
            listener.onItemClicked(searchResultItem)
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