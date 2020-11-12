package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.ui.OnFinished
import com.joshuacerdenia.android.nicefeed.ui.OnToolbarInflated
import com.joshuacerdenia.android.nicefeed.ui.viewmodel.FeedAddingViewModel

/*  Gives ability to subscribe to new feeds, must be extended. */
abstract class FeedAddingFragment: VisibleFragment() {

    interface Callbacks: OnToolbarInflated, OnFinished {
        fun onNewFeedAdded(feedId: String)
        fun onQuerySubmitted(query: String)
        fun onImportOpmlSelected()
    }

    var callbacks: Callbacks? = null
    var resultManager: RequestResultManager? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    /*  Retrieved data should be fed to this class; handles Snackbars and directing data to DB.
        Must be initialized by extending class. */
    inner class RequestResultManager(
        private val viewModel: FeedAddingViewModel,
        private val view: View,
        private val negativeMessageRes: Int,
    ) {

        fun submitData(feedWithEntries: FeedWithEntries?) {
            feedWithEntries?.let { data ->
                if (viewModel.currentFeedIds.size < SUBSCRIPTION_LIMIT) {
                    if (!isAlreadyAdded(data.feed.url)) {
                        viewModel.addFeedWithEntries(data)
                        showFeedAddedNotice(data.feed.url, data.feed.title)
                    } else {
                        showAlreadyAddedNotice()
                    }
                    viewModel.lastInputUrl = ""
                } else {
                    showLimitReachedNotice()
                }
            } ?: showRequestFailedNotice()
        }

        fun onRequestDismissed() {
            viewModel.requestFailedNoticeEnabled = false
            viewModel.cancelRequest()
            Snackbar.make(view, R.string.request_canceled, Snackbar.LENGTH_SHORT).show()
        }

        private fun isAlreadyAdded(feedId: String): Boolean {
            return viewModel.currentFeedIds.contains(feedId)
        }

        private fun showFeedAddedNotice(feedId: String, title: String) {
            Snackbar.make(view, getString(R.string.feed_added_message, title), Snackbar.LENGTH_LONG)
                .setAction(R.string.view) { callbacks?.onNewFeedAdded(feedId) }.show()
            viewModel.alreadyAddedNoticeEnabled = false
        }

        private fun showAlreadyAddedNotice() {
            if (viewModel.alreadyAddedNoticeEnabled) {
                Snackbar.make(view, getString(R.string.feed_already_added), Snackbar.LENGTH_SHORT).show()
                viewModel.alreadyAddedNoticeEnabled = false
            }
        }

        private fun showRequestFailedNotice() {
            if (viewModel.requestFailedNoticeEnabled) {
                Snackbar.make(view, getString(negativeMessageRes), Snackbar.LENGTH_SHORT).show()
                viewModel.requestFailedNoticeEnabled = false
            }
        }

        private fun showLimitReachedNotice() {
            if (viewModel.subscriptionLimitNoticeEnabled) {
                Snackbar.make(view, getString(R.string.subscription_limit_reached), Snackbar.LENGTH_SHORT).show()
                viewModel.subscriptionLimitNoticeEnabled = false
            }
        }
    }

    companion object {

        private const val SUBSCRIPTION_LIMIT = 1000 // Arbitrary
    }
}