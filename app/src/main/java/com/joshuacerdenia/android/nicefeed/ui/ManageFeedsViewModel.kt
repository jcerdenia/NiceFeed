package com.joshuacerdenia.android.nicefeed.ui

import com.joshuacerdenia.android.nicefeed.data.model.Feed

private const val TAG = "ManageFeedsViewModel"

class ManageFeedsViewModel: FeedListViewModel() {

    var selectedItems = mutableListOf<Feed>()

    fun updateFeeds(feeds: List<Feed>) {
        repository.updateFeeds(feeds)
    }

    fun deleteFeedsAndEntriesByIds(ids: List<String>) {
        repository.deleteFeedsAndEntriesByIds(ids)
    }
}