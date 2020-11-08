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
import com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight
import com.joshuacerdenia.android.nicefeed.util.extensions.hide
import com.joshuacerdenia.android.nicefeed.util.extensions.shortened
import com.joshuacerdenia.android.nicefeed.util.extensions.show
import com.squareup.picasso.Picasso
import java.text.DateFormat.*
import java.util.*

class EntryListAdapter(
    private val listener: OnEntrySelected
) : ListAdapter<EntryLight, EntryListAdapter.EntryHolder>(DiffCallback()) {

    interface OnEntrySelected {
        fun onEntryClicked(entryId: String)
        fun onEntryLongClicked(entry: EntryLight, view: View?)
    }

    var lastClickedPosition = 0
        private set

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
        private val listener: OnEntrySelected
    ) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

        lateinit var entry: EntryLight

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
                } else getDateInstance(SHORT).format(it)
            } ?: ""

            titleTextView.apply {
                text = HtmlCompat.fromHtml(entry.title, 0)
                setTextColor(if (entry.isRead) Color.GRAY else Color.BLACK)
            }

            infoTextView.text = "$date – ${entry.website.shortened()}"
            if (entry.isStarred) starView.show() else starView.hide()

            Picasso.get().load(entry.image).fit().centerCrop()
                .placeholder(R.drawable.vintage_newspaper).into(imageView)
        }

        override fun onClick(v: View) {
            lastClickedPosition = adapterPosition
            listener.onEntryClicked(entry.url)
        }

        override fun onLongClick(v: View?): Boolean {
            lastClickedPosition = adapterPosition
            listener.onEntryLongClicked(entry, v)
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