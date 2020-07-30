package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries

abstract class FeedAddingFragment: Fragment() {

    var currentFeedIds: List<String> = emptyList()
    var callbacks: Callbacks? = null

    interface Callbacks {
        fun onNewFeedAdded(feedId: String)
        fun onQuerySubmitted(query: String)
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
        private val negativeMessageResId: Int
    ) {

        fun submitData(feedWithEntries: FeedWithEntries?) {
            feedWithEntries?.let {
                if (!isAlreadyAdded(it.feed.website)) {
                    viewModel.saveFeedWithEntries(it)
                    showFeedAddedNotice(it.feed.title, it.feed.website)
                } else {
                    showAlreadyAddedNotice()
                }
            } ?: showRequestFailedNotice()
        }

        private fun isAlreadyAdded(feedId: String): Boolean {
            return currentFeedIds.contains(feedId)
        }

        private fun showFeedAddedNotice(title: String, feedId: String) {
            Snackbar.make(
                view,
                getString(R.string.feed_added_message, title),
                Snackbar.LENGTH_LONG
            ).setAction(R.string.view) {
                callbacks?.onNewFeedAdded(feedId)
            }.show()

            viewModel.alreadyAddedNoticeEnabled = false
        }

        private fun showAlreadyAddedNotice() {
            if (viewModel.alreadyAddedNoticeEnabled) {
                Snackbar.make(
                    view,
                    getString(R.string.feed_already_added),
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.alreadyAddedNoticeEnabled = false
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