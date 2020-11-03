package com.joshuacerdenia.android.nicefeed.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.data.remote.FeedParser
import kotlinx.coroutines.launch

abstract class FeedAddingViewModel: ViewModel() {

    val repo = NiceFeedRepository.get()
    private val parser = FeedParser(repo.networkMonitor)
    val feedRequestLiveData = parser.feedRequestLiveData
    var currentFeedIds = listOf<String>()

    var requestFailedNoticeEnabled = false
    var alreadyAddedNoticeEnabled = false
    var subscriptionLimitNoticeEnabled = false
    var lastAttemptedUrl = ""

    fun requestFeed(url: String, backup: String? = null) {
        onFeedRequested()
        viewModelScope.launch {
            parser.requestFeed(url, backup)
        }
    }

    private fun onFeedRequested() {
        requestFailedNoticeEnabled = true
        alreadyAddedNoticeEnabled = true
        subscriptionLimitNoticeEnabled = true
    }

    fun addFeedWithEntries(feedWithEntries: FeedWithEntries) {
        repo.addFeedWithEntries(feedWithEntries)
    }

    fun cancelRequest() {
        parser.cancelRequest()
    }
}