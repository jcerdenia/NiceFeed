package com.joshuacerdenia.android.nicefeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

open class AddFeedViewModel: ViewModel() {

    val repository = Repository.get()
    private val feedParser = FeedParser()

    val feedRequestLiveData: LiveData<Event<FeedWithEntries>>? = feedParser.feedRequestLiveData
    val feedListLiveData = repository.getFeeds()

    var requestFailedNoticeEnabled = false

    fun requestFeed(url: String) {
        feedParser.requestFeed(url)
    }

    fun saveFeedWithEntries (feedWithEntries: FeedWithEntries) {
        repository.addFeedWithEntries(feedWithEntries)
    }
}