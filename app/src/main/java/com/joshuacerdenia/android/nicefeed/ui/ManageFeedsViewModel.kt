package com.joshuacerdenia.android.nicefeed.ui

import com.joshuacerdenia.android.nicefeed.data.model.Feed

private const val TAG = "ManageFeedsViewModel"

class ManageFeedsViewModel: FeedListViewModel() {

    var selectedItems = mutableListOf<Feed>()

    val entryListLiveData = repository.getEntries()

    fun updateFeeds(feeds: List<Feed>) {
        repository.updateFeeds(feeds)
    }

    fun deleteFeedsWithEntries(websites: List<String>) {
        repository.deleteFeedsByWebsite(websites)
        repository.deleteEntriesByWebsite(websites)
    }
}