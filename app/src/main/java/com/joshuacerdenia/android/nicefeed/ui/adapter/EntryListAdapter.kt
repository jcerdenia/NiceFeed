package com.joshuacerdenia.android.nicefeed.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.EntryLight
import com.joshuacerdenia.android.nicefeed.utils.shortened
import com.squareup.picasso.Picasso
import java.text.DateFormat.*
import java.util.*

class EntryListAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<EntryLight, EntryListAdapter.EntryHolder>(DiffCallback()) {

    interface OnItemClickListener {
        fun onItemClicked(entry: EntryLight)
        fun onItemLongClicked(entry: EntryLight, view: View?)
    }

    var latestClickedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_entry,
            parent,
            false
        )
        return EntryHolder(view, listener)
    }

    override fun onBindViewHolder(holder: EntryHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EntryHolder(
        view: View,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

        private lateinit var entry: EntryLight

        private val container: ConstraintLayout = itemView.findViewById(R.id.constraintLayout_container)
        private val titleTextView: TextView = itemView.findViewById(R.id.textView_title)
        private val infoTextView: TextView = itemView.findViewById(R.id.textView_info)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView_image)
        private val starView: ImageView = itemView.findViewById(R.id.imageView_star)

        init {
            container.setOnClickListener(this)
            container.setOnLongClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(entry: EntryLight) {
            this.entry = entry

            val date = entry.date?.let {
                if (getDateInstance().format(it) == getDateInstance().format(Date())) {
                    getTimeInstance(SHORT).format(it)
                } else {
                    getDateInstance(MEDIUM).format(it)
                }
            } ?: ""

            titleTextView.apply {
                text = HtmlCompat.fromHtml(entry.title, 0)
                setTextColor(if (entry.isRead) {
                    Color.GRAY
                } else {
                    Color.BLACK
                })
            }

            infoTextView.text = "$date – ${entry.website.shortened()}"
            starView.visibility = if (entry.isStarred) {
                View.VISIBLE
            } else {
                View.GONE
            }

            Picasso.get()
                .load(entry.image?.let { image ->
                    if (image.isNotEmpty()) {
                        image
                    } else null
                })
                .fit()
                .centerCrop()
                .placeholder(R.drawable.vintage_newspaper)
                .into(imageView)
        }

        override fun onClick(v: View) {
            latestClickedPosition = adapterPosition
            listener.onItemClicked(entry)
        }

        override fun onLongClick(v: View?): Boolean {
            latestClickedPosition = adapterPosition
            listener.onItemLongClicked(entry, v)
            return true
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<EntryLight>() {

        override fun areItemsTheSame(oldItem: EntryLight, newItem: EntryLight): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: EntryLight, newItem: EntryLight): Boolean {
            return oldItem == newItem
        }
    }
}