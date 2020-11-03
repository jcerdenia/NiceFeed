package com.joshuacerdenia.android.nicefeed.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.TopicBlock

class TopicAdapter(
    private val context: Context,
    private val listener: OnItemClickListener,
) : ListAdapter<TopicBlock, TopicAdapter.TopicHolder>(DiffCallback()) {

    var numOfItems = 0 // Initial value only

    interface OnItemClickListener {
        fun onTopicSelected(topic: String)
    }

    override fun submitList(list: MutableList<TopicBlock>?) {
        super.submitList(list?.take(numOfItems))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item_topic, parent, false)
        return TopicHolder(view)
    }

    override fun onBindViewHolder(holder: TopicHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TopicHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var topicBlock: TopicBlock
        private val topicTextView: TextView = itemView.findViewById(R.id.topic_text_view)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(topicBlock: TopicBlock) {
            this.topicBlock = topicBlock
            topicTextView.text = context.getString(R.string.hashtag, topicBlock.topic)
            val color = ContextCompat.getColor(context, topicBlock.color)
            itemView.setBackgroundColor(color)
        }

        override fun onClick(v: View) {
            listener.onTopicSelected(topicTextView.text.toString())
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<TopicBlock>() {

        override fun areItemsTheSame(oldItem: TopicBlock, newItem: TopicBlock): Boolean {
            return oldItem.topic == newItem.topic
        }

        override fun areContentsTheSame(oldItem: TopicBlock, newItem: TopicBlock): Boolean {
            return oldItem == newItem
        }
    }
}