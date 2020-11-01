package com.joshuacerdenia.android.nicefeed.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.data.remote.FeedParser
import kotlinx.coroutines.launch

open class AddFeedsViewModel: ViewModel() {

    val repo = NiceFeedRepository.get()
    private val parser = FeedParser(repo.networkMonitor)

    var requestFailedNoticeEnabled = false
    var alreadyAddedNoticeEnabled = false
    var subscriptionLimitNoticeEnabled = false

    val feedRequestLiveData: LiveData<FeedWithEntries?> = parser.feedRequestLiveData
    val feedIdsLiveData = repo.getFeedIds()
    var feedsToImport = listOf<Feed>()
    var currentFeedIds: List<String> = emptyList()
        private set

    fun requestFeed(url: String, backup: String? = null) {
        onFeedRequested()
        viewModelScope.launch {
            parser.requestFeed(url, backup)
        }
    }

    fun cancelRequest() {
        parser.cancelRequest()
    }

    fun addFeedWithEntries(feedWithEntries: FeedWithEntries) {
        repo.addFeedWithEntries(feedWithEntries)
    }

    fun addFeeds(vararg feed: Feed) {
        repo.addFeeds(*feed)
    }

    fun onFeedIdsObtained(feedIds: List<String>) {
        currentFeedIds = feedIds
    }

    private fun onFeedRequested() {
        requestFailedNoticeEnabled = true
        alreadyAddedNoticeEnabled = true
        subscriptionLimitNoticeEnabled = true
    }
}