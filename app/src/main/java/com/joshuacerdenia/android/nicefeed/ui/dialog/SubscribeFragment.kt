package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.data.model.SearchResultItem
import com.joshuacerdenia.android.nicefeed.ui.viewmodel.AddFeedsViewModel
import com.joshuacerdenia.android.nicefeed.utils.RssUrlTransformer
import com.joshuacerdenia.android.nicefeed.utils.Utils
import com.joshuacerdenia.android.nicefeed.utils.extensions.addRipple
import com.squareup.picasso.Picasso
import java.text.DateFormat

class SubscribeFragment(private val viewModel: AddFeedsViewModel): BottomSheetDialogFragment() {

    interface Callbacks {
        fun onRequestCompleted(feedWithEntries: FeedWithEntries?)
        fun onRequestCanceled()
    }

    private lateinit var titleTextView: TextView
    private lateinit var urlTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var updatedTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var subscribeButton: Button
    private lateinit var progressBar: ProgressBar

    private var isRequested = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_subscribe, container, false)
        titleTextView = view.findViewById(R.id.title_text_view)
        urlTextView = view.findViewById(R.id.url_text_view)
        descriptionTextView = view.findViewById(R.id.description_text_view)
        updatedTextView = view.findViewById(R.id.updated_text_view)
        imageView = view.findViewById(R.id.image_view)
        subscribeButton = view.findViewById(R.id.subscribe_button)
        progressBar = view.findViewById(R.id.progress_bar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchResultItem = arguments?.getSerializable(ARG_SEARCH_RESULT_ITEM) as SearchResultItem
        val lastUpdated = formatDate(searchResultItem.updated?.toLong())
        Picasso.get().load(searchResultItem.imageUrl).placeholder(R.drawable.feed_icon).into(imageView)

        titleTextView.text = searchResultItem.title
        updatedTextView.text = getString(R.string.last_updated, lastUpdated)
        urlTextView.apply {
            text = searchResultItem.id?.substringAfter("feed/")
            addRipple()
            setOnClickListener {
                setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_green_check, 0, 0,0)
                Utils.copyLinkToClipboard(context, this.text.toString())
            }
        }

        descriptionTextView.apply {
            if (!searchResultItem.description.isNullOrEmpty()) {
                visibility = View.VISIBLE
                text = HtmlCompat.fromHtml(searchResultItem.description, 0)
            } else visibility = View.GONE
        }

        subscribeButton.apply {
            text = getString(R.string.subscribe)
            setOnClickListener {
                isRequested = true
                val url = searchResultItem.id?.let { RssUrlTransformer.getUrl(it) }.toString()
                val backup = searchResultItem.website?.let { RssUrlTransformer.getUrl(it) }
                // "website" property is also a usable URL
                viewModel.requestFeed(url, backup)
                progressBar.visibility = View.VISIBLE
                this.text = getString(R.string.loading)
                this.isEnabled = false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.feedRequestLiveData.observe(viewLifecycleOwner, { result ->
            if (isRequested) {
                targetFragment?.let { (it as Callbacks).onRequestCompleted(result) }
                dismiss()
            }
        })
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        if (progressBar.visibility == View.VISIBLE) {
            targetFragment?.let { (it as Callbacks).onRequestCanceled() }
        }
        dismiss()
    }

    private fun formatDate(epoch: Long?): String? {
        return if (epoch != null) {
            DateFormat.getDateInstance(DateFormat.MEDIUM).format(epoch)
        } else null
    }

    companion object {
        private const val ARG_SEARCH_RESULT_ITEM = "ARG_SEARCH_RESULT_ITEM"

        fun newInstance(
            searchResultItem: SearchResultItem,
            viewModel: AddFeedsViewModel
        ): SubscribeFragment {
            val args = Bundle().apply { putSerializable(ARG_SEARCH_RESULT_ITEM, searchResultItem) }
            return SubscribeFragment(viewModel).apply { arguments = args }
        }
    }
}