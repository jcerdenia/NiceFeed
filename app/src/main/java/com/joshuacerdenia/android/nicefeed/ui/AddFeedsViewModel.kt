package com.joshuacerdenia.android.nicefeed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.data.remote.FeedParser
import kotlinx.coroutines.launch

open class AddFeedsViewModel: ViewModel() {

    private val repository = NiceFeedRepository.get()
    private val feedParser = FeedParser()

    var requestFailedNoticeEnabled = false
    var alreadyAddedNoticeEnabled = false

    val feedRequestLiveData: LiveData<FeedWithEntries?> = feedParser.feedRequestLiveData
    val feedIdsLiveData = repository.getFeedIds()
    var feedsToImport = listOf<Feed>()

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

    fun addFeeds(vararg feed: Feed) {
        repository.addFeeds(*feed)
    }
}