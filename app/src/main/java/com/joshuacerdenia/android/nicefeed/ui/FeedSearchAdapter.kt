package com.joshuacerdenia.android.nicefeed.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem
import com.joshuacerdenia.android.nicefeed.utils.simplified
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_entry_list.view.*

class FeedSearchAdapter(
    private val listener: OnItemClickListener,
    loadingItem: SearchResultItem?,
    private var clickingIsEnabled: Boolean
) : ListAdapter<SearchResultItem, FeedSearchAdapter.FeedHolder>(DiffCallback()) {

    private var activeItemProgressBar: ProgressBar? = null
    private var loadingItemId = loadingItem?.id

    interface OnItemClickListener {
        fun onItemClicked(searchResultItem: SearchResultItem)
    }

    fun onLoadingItem(itemId: String?) {
        clickingIsEnabled = false
        loadingItemId = itemId
        activeItemProgressBar?.visibility = View.VISIBLE
    }

    fun onFinishedLoading() {
        clickingIsEnabled = true
        loadingItemId = null
        activeItemProgressBar?.visibility = View.INVISIBLE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_search_result,
            parent,
            false
        )
        return FeedHolder(view, listener)
    }

    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        val isLoading = loadingItemId == getItem(position).id
        holder.bind(getItem(position), isLoading)
    }

    inner class FeedHolder(
        view: View,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var searchResultItem: SearchResultItem
        private val titleTextView: TextView = itemView.findViewById(R.id.textView_title)
        private val infoTextView: TextView = itemView.findViewById(R.id.textView_info)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView_image)
        private val itemProgressBar: ProgressBar = itemView.findViewById(R.id.progressBar_item)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(searchResultItem: SearchResultItem, isLoading: Boolean) {
            this.searchResultItem = searchResultItem

            titleTextView.text = searchResultItem.title
            infoTextView.text = searchResultItem.website?.simplified()
            itemProgressBar.visibility = if (isLoading) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }

            Picasso.get()
                .load(searchResultItem.imageUrl)
                .placeholder(R.drawable.feed_icon)
                .into(imageView)
        }

        override fun onClick(v: View) {
            if (clickingIsEnabled) {
                listener.onItemClicked(searchResultItem)
                activeItemProgressBar = itemProgressBar
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