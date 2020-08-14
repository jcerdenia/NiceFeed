package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.FeedIdPair
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries

abstract class FeedAddingFragment: VisibleFragment() {

    var currentFeedIds: List<String> = emptyList()
    var callbacks: Callbacks? = null

    interface Callbacks {
        fun onNewFeedAdded(feedIdPair: FeedIdPair)
        fun onQuerySubmitted(query: String)
        fun onImportOpmlSelected()
        fun onDoneImporting()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = activity as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    inner class RequestResultManager(
        private val viewModel: AddFeedsViewModel,
        private val view: View,
        private val negativeMessageResId: Int,
        private val urlEditText: EditText? = null
    ) {

        fun submitData(feedWithEntries: FeedWithEntries?) {
            feedWithEntries?.let {
                if (!isAlreadyAdded(it.feed.url)) {
                    viewModel.addFeedWithEntries(it)
                    showFeedAddedNotice(it.feed.url, it.feed.title)
                } else {
                    showAlreadyAddedNotice()
                }
            } ?: showRequestFailedNotice()
        }

        private fun isAlreadyAdded(feedId: String): Boolean {
            return currentFeedIds.contains(feedId)
        }

        private fun showFeedAddedNotice(feedId: String, title: String) {
            Snackbar.make(
                view,
                getString(R.string.feed_added_message, title),
                Snackbar.LENGTH_LONG
            ).setAction(R.string.view) {
                callbacks?.onNewFeedAdded(FeedIdPair(feedId, title))
            }.show()

            viewModel.alreadyAddedNoticeEnabled = false
            urlEditText?.text = null
        }

        private fun showAlreadyAddedNotice() {
            if (viewModel.alreadyAddedNoticeEnabled) {
                Snackbar.make(
                    view,
                    getString(R.string.feed_already_added),
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.alreadyAddedNoticeEnabled = false
                urlEditText?.text = null
            }
        }

        private fun showRequestFailedNotice() {
            if (viewModel.requestFailedNoticeEnabled) {
                Snackbar.make(
                    view,
                    getString(negativeMessageResId),
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.requestFailedNoticeEnabled = false
            }
        }
    }
}