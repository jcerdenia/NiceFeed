package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.DialogFragment
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem
import com.squareup.picasso.Picasso
import java.text.DateFormat

class ConfirmAddDialogFragment: DialogFragment() {

    companion object {
        private const val ARG_SEARCH_RESULT_ITEM = "ARG_SEARCH_RESULT_ITEM"

        fun newInstance(searchResultItem: SearchResultItem): ConfirmAddDialogFragment {
            val args = Bundle().apply {
                putSerializable(ARG_SEARCH_RESULT_ITEM, searchResultItem)
                }
            return ConfirmAddDialogFragment()
                .apply {
                arguments = args
            }
        }
    }

    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var updatedTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var addFeedButton: Button
    private lateinit var cancelButton: Button

    interface Callbacks {
        fun onAddConfirmed(feed: SearchResultItem)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_confirm_dialog, container, false)

        titleTextView = view.findViewById(R.id.feed_title)
        descriptionTextView = view.findViewById(R.id.feed_description)
        updatedTextView = view.findViewById(R.id.last_updated)
        imageView = view.findViewById(R.id.feed_image)
        addFeedButton = view.findViewById(R.id.add_feed_button)
        cancelButton = view.findViewById(R.id.cancel_button)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val feed = arguments?.getSerializable(ARG_SEARCH_RESULT_ITEM) as SearchResultItem
        val lastUpdated = formatDate(feed.updated?.toLong())

        titleTextView.text = feed.title
        descriptionTextView.text = feed.description?.let { HtmlCompat.fromHtml(it, 0) }
        updatedTextView.text = getString(R.string.last_updated, lastUpdated)

        Picasso.get()
            .load(feed.imageUrl)
            .placeholder(R.drawable.ic_rss_feed)
            .into(imageView)

        addFeedButton.setOnClickListener {
            targetFragment?.let { (it as Callbacks).onAddConfirmed(feed) }
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun formatDate(epoch: Long?): String? {
        return if (epoch != null) {
            DateFormat.getDateInstance(DateFormat.LONG).format(epoch)
        } else {
            null
        }
    }

    /*
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val feed = arguments?.getSerializable(ARG_SEARCH_RESULT_ITEM) as SearchResultItem

        val dialogBuilder = AlertDialog.Builder(context!!)

        val message = "${feed.description?.trim()}<br><br>" +
                "Last updated ${feed.updated}"

        dialogBuilder
            .setTitle(feed.title)
            .setMessage(HtmlCompat.fromHtml(message, 0))
            .setIcon(R.drawable.ic_success)
            .setCancelable(true)
            .setPositiveButton(getString(R.string.add_feed)) { dialog, _ ->
                targetFragment?.let { fragment ->
                    feed.title?.let { (fragment as Callbacks).onAddConfirmed(it) }
                }
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }

        val dialog = dialogBuilder.create()
        dialog.show()

        return dialog
    } */
}