package com.joshuacerdenia.android.nicefeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.htmlEncode
import androidx.fragment.app.DialogFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_preview_feed.*

class PreviewFeedDialogFragment: DialogFragment() {

    companion object {
        private const val ARG_FEED = "ARG_FEED"

        fun newInstance(feed: Feed): PreviewFeedDialogFragment {
            val args = Bundle().apply {
                putSerializable(ARG_FEED, feed)
            }
            return PreviewFeedDialogFragment().apply {
                arguments = args
            }
        }
    }

    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_preview_feed, container, false)

        titleTextView = view.findViewById(R.id.feed_title)
        descriptionTextView = view.findViewById(R.id.feed_description)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val feed = arguments?.getSerializable(ARG_FEED) as Feed

        titleTextView.text = feed.title
        descriptionTextView.text = feed.description?.let { HtmlCompat.fromHtml(it, 0) }

        Picasso.get()
            .load(feed.imageUrl)
            .placeholder(R.drawable.ic_rss_feed)
            .into(feed_image)
    }
}