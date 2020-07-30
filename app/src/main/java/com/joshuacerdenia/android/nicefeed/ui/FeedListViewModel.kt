package com.joshuacerdenia.android.nicefeed.ui

import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository

open class FeedListViewModel: ViewModel() {

    val repository = NiceFeedRepository.get()
    val feedsLiveData = repository.getFeeds()

    var activeFeedId: String? = null
    var isInitialLoading = true
}