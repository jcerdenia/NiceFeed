package com.joshuacerdenia.android.nicefeed.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joshuacerdenia.android.nicefeed.data.model.Feed

private const val TAG = "ManageFeedsViewModel"

class ManageFeedsViewModel: FeedListViewModel() {

    var selectedItems = mutableListOf<Feed>()

    fun updateFeeds(feeds: List<Feed>) {
        repository.updateFeeds(feeds)
    }
}