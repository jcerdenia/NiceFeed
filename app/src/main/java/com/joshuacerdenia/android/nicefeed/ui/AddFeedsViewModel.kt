package com.joshuacerdenia.android.nicefeed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.FeedParser
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedEntryCrossRef
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries

open class AddFeedsViewModel: ViewModel() {

    val repository = NiceFeedRepository.get()
    private val feedParser = FeedParser()

    var requestFailedNoticeEnabled = false
    var alreadyAddedNoticeEnabled = false

    val feedRequestLiveData: LiveData<FeedWithEntries>? = feedParser.feedRequestLiveData
    val feedIdsLiveData = repository.getFeedIds()
    var feedsToImport = listOf<Feed>()

    fun addFeedEntryCrossRef(feedId: String, entryId: String) {
        repository.addFeedEntryCrossRef(FeedEntryCrossRef(feedId, entryId))
    }

    fun requestFeed(vararg url: String) {
        requestFailedNoticeEnabled = true
        alreadyAddedNoticeEnabled = true
        feedParser.requestFeed(*url)
    }

    fun addFeedWithEntries(feedWithEntries: FeedWithEntries) {
        repository.addFeedWithEntries(feedWithEntries)
    }

    //fun getEntriesAssociatedWithFeedId(feedId: String) {
        //repository.getEntriesAssociatedWithFeedId(feedId)
    //}

    fun addFeeds(feeds: List<Feed>) {
        repository.addFeeds(feeds)
    }
}