package com.joshuacerdenia.android.nicefeed.ui

import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries

interface FeedRequestCallbacks {

    fun onRequestCompleted(feedWithEntries: FeedWithEntries?)
    fun onRequestCanceled()
}