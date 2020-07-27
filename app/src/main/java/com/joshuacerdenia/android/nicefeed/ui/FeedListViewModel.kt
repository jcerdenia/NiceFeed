package com.joshuacerdenia.android.nicefeed.ui

import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.Repository

open class FeedListViewModel: ViewModel() {

    val repository = Repository.get()
    val feedListLiveData = repository.getFeeds()
    val feedsInfoLiveData = repository.getFeedsInfo()

    var currentFeedId: String? = null
    var categories: List<String> = listOf()
}