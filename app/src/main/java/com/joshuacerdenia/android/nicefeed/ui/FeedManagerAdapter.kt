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
import com.joshuacerdenia.android.nicefeed.data.model.FeedMinimal
import com.joshuacerdenia.android.nicefeed.utils.simplified

class FeedManagerAdapter(
    private val listener: ItemCheckBoxListener,
    private val selectedItems: List<FeedMinimal>
) : ListAdapter<FeedMinimal, FeedManagerAdapter.FeedHolder>(DiffCallback()) {

    private val checkBoxes = mutableSetOf<CheckBox>()

    interface ItemCheckBoxListener {
        fun onItemClicked(feed: FeedMinimal, isChecked: Boolean)
        fun onAllItemsChecked(isChecked: Boolean)
    }

    fun handleCheckBoxes(checkAll: Boolean) {
        for (checkBox in checkBoxes) {
            checkBox.isChecked = checkAll
        }
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
        holder.bind(getItem(position), itemIsChecked)
    }

    inner class FeedHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var feed: FeedMinimal
        private val titleCheckBox: CheckBox = itemView.findViewById(R.id.feed_title_checkbox)
        private val websiteTextView: TextView = itemView.findViewById(R.id.feed_website)
        private val categoryTextView: TextView = itemView.findViewById(R.id.feed_category)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(feed: FeedMinimal, isChecked: Boolean) {
            this.feed = feed
            websiteTextView.text = feed.website.simplified()
            categoryTextView.text = feed.category

            titleCheckBox.apply {
                text = feed.title
                this.isChecked = isChecked
                checkBoxes.add(this)

                setOnClickListener {
                    listener.onItemClicked(feed, this.isChecked)
                }
            }
        }

        override fun onClick(v: View?) {
            titleCheckBox.isChecked = !titleCheckBox.isChecked
            listener.onItemClicked(feed, titleCheckBox.isChecked)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<FeedMinimal>() {

        override fun areItemsTheSame(oldItem: FeedMinimal, newItem: FeedMinimal): Boolean {
            return oldItem.website == newItem.website
        }

        override fun areContentsTheSame(oldItem: FeedMinimal, newItem: FeedMinimal): Boolean {
            return oldItem.category == newItem.category
        }
    }
}