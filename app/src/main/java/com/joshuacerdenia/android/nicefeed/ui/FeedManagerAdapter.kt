package com.joshuacerdenia.android.nicefeed.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.utils.pathified

private const val TAG = "FeedManagerAdapter"

class FeedManagerAdapter(private val listener: ItemCheckBoxListener, private val selectedItems: List<Feed>)
    : ListAdapter<Feed, FeedManagerAdapter.FeedHolder>(DiffCallback()) {

    val checkBoxes = mutableListOf<CheckBox>()

    interface ItemCheckBoxListener {
        fun onItemClicked(feed: Feed, isChecked: Boolean)
        fun onAllItemsChecked(isChecked: Boolean)
    }

    fun onSelectAllChecked(isChecked: Boolean) {
        if (isChecked) {
            for (checkBox in checkBoxes) {
                checkBox.isChecked = true
                //listener.onAllItemsChecked(true)
            }
        } else {
            for (checkBox in checkBoxes) {
                checkBox.isChecked = false
                //listener.onAllItemsChecked(false)
            }
        }
    }

    interface OnSelectAllCheckedListener {
        fun onSelectAllChecked(isChecked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_feed_manager,
            parent,
            false
        )
        return FeedHolder(view)
    }

    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        val itemIsChecked = selectedItems.contains(getItem(position))
        holder.bind(getItem(position), listener, itemIsChecked)
    }

    inner class FeedHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val titleCheckBox: CheckBox = itemView.findViewById(R.id.feed_title_checkbox)
        private val websiteTextView: TextView = itemView.findViewById(R.id.feed_website)
        private val categoryTextView: TextView = itemView.findViewById(R.id.feed_category)

        fun bind(feed: Feed, listener: ItemCheckBoxListener, isChecked: Boolean) {
            websiteTextView.text = feed.website.pathified()
            categoryTextView.text = feed.category

            titleCheckBox.apply {
                text = feed.title
                this.isChecked = isChecked
                checkBoxes.add(this)

                setOnClickListener() {
                    if (this.isChecked) {
                        listener.onItemClicked(feed, true)
                    } else {
                        listener.onItemClicked(feed, false)
                    }
                }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Feed>() {

        override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem.website == newItem.website
        }

        override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem == newItem
        }
    }
}