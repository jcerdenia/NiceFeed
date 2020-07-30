package com.joshuacerdenia.android.nicefeed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.FeedParser
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries

open class AddFeedsViewModel: ViewModel() {

    val repository = NiceFeedRepository.get()
    private val feedParser =
        FeedParser()

    var requestFailedNoticeEnabled = false
    var alreadyAddedNoticeEnabled = false

    val feedRequestLiveData: LiveData<FeedWithEntries>? = feedParser.feedRequestLiveData
    //val feedListLiveData = repository.getFeeds()
    val feedIdsLiveData = repository.getFeedIds()

    fun requestFeed(vararg url: String) {
        requestFailedNoticeEnabled = true
        alreadyAddedNoticeEnabled = true
        feedParser.requestFeed(*url)
    }

    fun saveFeedWithEntries (feedWithEntries: FeedWithEntries) {
        repository.addFeedWithEntries(feedWithEntries)
    }
}