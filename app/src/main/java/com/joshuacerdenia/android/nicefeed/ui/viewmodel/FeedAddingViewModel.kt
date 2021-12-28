package com.joshuacerdenia.android.nicefeed.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.data.remote.FeedFetcher
import kotlinx.coroutines.launch

abstract class FeedAddingViewModel: ViewModel() {

    val repo = NiceFeedRepository.get()
    private val fetcher = FeedFetcher()

    val feedRequestLiveData = fetcher.feedWithEntriesLive
    var currentFeedIds = listOf<String>()

    var isActiveRequest = false
    var requestFailedNoticeEnabled = false
    var alreadyAddedNoticeEnabled = false
    var subscriptionLimitNoticeEnabled = false
    var lastInputUrl = ""

    fun requestFeed(url: String, backup: String? = null) {
        onFeedRequested()
        viewModelScope.launch {
            fetcher.request(url)
        }
    }

    private fun onFeedRequested() {
        isActiveRequest = true
        requestFailedNoticeEnabled = true
        alreadyAddedNoticeEnabled = true
        subscriptionLimitNoticeEnabled = true
    }

    fun addFeedWithEntries(feedWithEntries: FeedWithEntries) {
        repo.addFeedWithEntries(feedWithEntries)
    }

    fun cancelRequest() {
        // TODO
    }
}