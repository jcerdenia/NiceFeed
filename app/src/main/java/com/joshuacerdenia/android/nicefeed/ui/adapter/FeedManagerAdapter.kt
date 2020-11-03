package com.joshuacerdenia.android.nicefeed.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable
import com.joshuacerdenia.android.nicefeed.utils.extensions.pathified

class FeedManagerAdapter(
    private val listener: ItemCheckBoxListener,
    var selectedItems: List<FeedManageable>
) : ListAdapter<FeedManageable, FeedManagerAdapter.FeedHolder>(DiffCallback()) {

    interface ItemCheckBoxListener {
        fun onItemClicked(feed: FeedManageable, isChecked: Boolean)
        fun onAllItemsChecked(isChecked: Boolean)
    }

    private val checkBoxes = mutableSetOf<CheckBox>()

    fun toggleCheckBoxes(checkAll: Boolean) {
        selectedItems = if (checkAll) currentList else emptyList()
        checkBoxes.forEach { checkBox -> checkBox.isChecked = checkAll }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_feed_manager, parent, false)
        return FeedHolder(view)
    }

    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        val itemIsChecked = selectedItems.contains(getItem(position))
        holder.bind(getItem(position), itemIsChecked)
    }

    inner class FeedHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var feed: FeedManageable
        private val titleCheckBox: CheckBox = itemView.findViewById(R.id.feed_title_checkbox)
        private val websiteTextView: TextView = itemView.findViewById(R.id.feed_website)
        private val categoryTextView: TextView = itemView.findViewById(R.id.feed_category)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(feed: FeedManageable, isChecked: Boolean) {
            this.feed = feed
            websiteTextView.text = feed.url.pathified()
            categoryTextView.text = feed.category

            titleCheckBox.apply {
                text = feed.title
                this.isChecked = isChecked
                checkBoxes.add(this)
                setOnClickListener { listener.onItemClicked(feed, this.isChecked) }
            }
        }

        override fun onClick(v: View?) {
            titleCheckBox.isChecked = !titleCheckBox.isChecked
            listener.onItemClicked(feed, titleCheckBox.isChecked)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<FeedManageable>() {

        override fun areItemsTheSame(oldItem: FeedManageable, newItem: FeedManageable): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: FeedManageable, newItem: FeedManageable): Boolean {
            return oldItem.category == newItem.category
        }
    }
}