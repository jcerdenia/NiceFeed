package com.joshuacerdenia.android.nicefeed

import androidx.lifecycle.ViewModel

class FeedListViewModel: ViewModel() {

    private val repository = Repository.get()

    val feedListLiveData = repository.getFeeds()

    var isManagingFeeds = false
}