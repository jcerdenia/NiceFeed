package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem
import com.squareup.picasso.Picasso
import java.text.DateFormat

class SubscribeFragment: BottomSheetDialogFragment() {

    interface Callbacks {
        fun onAddConfirmed(searchResultItem: SearchResultItem)
    }

    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var updatedTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var subscribeButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about_feed, container, false)
        titleTextView = view.findViewById(R.id.textView_title)
        descriptionTextView = view.findViewById(R.id.textView_description)
        updatedTextView = view.findViewById(R.id.textView_additional_info)
        imageView = view.findViewById(R.id.imageView_feed)
        subscribeButton = view.findViewById(R.id.button_positive)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchResultItem = arguments?.getSerializable(ARG_SEARCH_RESULT_ITEM) as SearchResultItem
        val lastUpdated = formatDate(searchResultItem.updated?.toLong())

        Picasso.get()
            .load(searchResultItem.imageUrl)
            .placeholder(R.drawable.feed_icon)
            .into(imageView)

        titleTextView.text = searchResultItem.title
        updatedTextView.text = getString(R.string.last_updated, lastUpdated)

        descriptionTextView.apply {
            if (!searchResultItem.description.isNullOrEmpty()) {
                visibility = View.VISIBLE
                text = HtmlCompat.fromHtml(searchResultItem.description, 0)
            } else {
                visibility = View.GONE
            }
        }

        subscribeButton.apply {
            text = getString(R.string.subscribe)
            setOnClickListener {
                targetFragment?.let { (it as Callbacks).onAddConfirmed(searchResultItem) }
                dismiss()
            }
        }
    }

    private fun formatDate(epoch: Long?): String? {
        return if (epoch != null) {
            DateFormat.getDateInstance(DateFormat.MEDIUM).format(epoch)
        } else {
            null
        }
    }

    companion object {
        private const val ARG_SEARCH_RESULT_ITEM = "ARG_SEARCH_RESULT_ITEM"

        fun newInstance(searchResultItem: SearchResultItem): SubscribeFragment {
            val args = Bundle().apply {
                putSerializable(ARG_SEARCH_RESULT_ITEM, searchResultItem)
            }

            return SubscribeFragment().apply {
                arguments = args
            }
        }
    }
}