package com.joshuacerdenia.android.nicefeed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshuacerdenia.android.nicefeed.data.FeedParser
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedEntryCrossRef
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries
import kotlinx.coroutines.launch

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

    fun requestFeed(url: String, backup: String? = null) {
        requestFailedNoticeEnabled = true
        alreadyAddedNoticeEnabled = true

        viewModelScope.launch {
            feedParser.requestFeed(url, backup)
        }
    }

    fun addFeedWithEntries(feedWithEntries: FeedWithEntries) {
        repository.addFeedWithEntries(feedWithEntries)
    }

    fun addFeeds(feeds: List<Feed>) {
        repository.addFeeds(feeds)
    }
}