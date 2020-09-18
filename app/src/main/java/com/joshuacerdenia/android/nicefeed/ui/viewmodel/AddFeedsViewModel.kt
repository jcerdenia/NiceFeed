package com.joshuacerdenia.android.nicefeed.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.data.remote.FeedParser
import kotlinx.coroutines.launch

open class AddFeedsViewModel: ViewModel() {

    val repo = NiceFeedRepository.get()
    private val parser = FeedParser(repo.connectionMonitor)

    var requestFailedNoticeEnabled = false
    var alreadyAddedNoticeEnabled = false
    var subscriptionLimitNoticeEnabled = false

    val feedRequestLiveData: LiveData<FeedWithEntries?> = parser.feedRequestLiveData
    val feedIdsLiveData = repo.getFeedIds()
    var feedsToImport = listOf<Feed>()

    fun requestFeed(url: String, backup: String? = null) {
        onFeedRequested()
        viewModelScope.launch {
            parser.requestFeed(url, backup)
        }
    }

    fun addFeedWithEntries(feedWithEntries: FeedWithEntries) {
        repo.addFeedWithEntries(feedWithEntries)
    }

    fun addFeeds(vararg feed: Feed) {
        repo.addFeeds(*feed)
    }

    private fun onFeedRequested() {
        requestFailedNoticeEnabled = true
        alreadyAddedNoticeEnabled = true
        subscriptionLimitNoticeEnabled = true
    }
}