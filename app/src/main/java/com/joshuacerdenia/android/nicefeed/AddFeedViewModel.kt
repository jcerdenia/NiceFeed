package com.joshuacerdenia.android.nicefeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

open class AddFeedViewModel: ViewModel() {

    val repository = Repository.get()
    private val feedParser = FeedParser()

    val feedRequestLiveData: LiveData<FeedWithEntries>? = feedParser.feedRequestLiveData
    val feedListLiveData = repository.getFeeds()

    var requestFailedNoticeEnabled = false
    var alreadyAddedNoticeEnabled = false

    fun requestFeed(url: String) {
        requestFailedNoticeEnabled = true
        alreadyAddedNoticeEnabled = true
        feedParser.requestFeed(url)
    }

    fun saveFeedWithEntries (feedWithEntries: FeedWithEntries) {
        repository.addFeedWithEntries(feedWithEntries)
    }
}