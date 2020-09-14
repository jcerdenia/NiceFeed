package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.utils.pathified
import com.squareup.picasso.Picasso

class AboutFeedFragment: BottomSheetDialogFragment() {

    interface Callbacks {
        fun onEditCategoryClicked()
    }

    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var categoryTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var editCategoryButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about_feed, container, false)
        titleTextView = view.findViewById(R.id.textView_title)
        descriptionTextView = view.findViewById(R.id.textView_description)
        categoryTextView = view.findViewById(R.id.textView_additional_info)
        imageView = view.findViewById(R.id.imageView_feed)
        editCategoryButton = view.findViewById(R.id.button_positive)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val feed = arguments?.getSerializable(ARG_FEED) as Feed

        Picasso.get()
            .load(feed.imageUrl)
            .placeholder(R.drawable.feed_icon)
            .into(imageView)

        titleTextView.text = feed.title
        categoryTextView.text = getString(R.string.category_, feed.category)
        descriptionTextView.text = if (!feed.description.isNullOrEmpty()) {
            feed.description
        } else {
            feed.website.pathified()
        }

        editCategoryButton.apply {
            text = getString(R.string.edit_category)

            setOnClickListener {
                targetFragment?.let { (it as Callbacks).onEditCategoryClicked() }
                dismiss()
            }
        }
    }

    companion object {
        private const val ARG_FEED = "ARG_FEED"

        fun newInstance(feed: Feed): AboutFeedFragment {
            val args = Bundle().apply {
                putSerializable(ARG_FEED, feed)
            }

            return AboutFeedFragment().apply {
                arguments = args
            }
        }
    }
}