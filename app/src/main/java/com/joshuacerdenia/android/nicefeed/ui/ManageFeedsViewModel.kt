package com.joshuacerdenia.android.nicefeed.ui

import androidx.lifecycle.LiveData
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedMinimal

private const val TAG = "ManageFeedsViewModel"

class ManageFeedsViewModel: FeedListViewModel() {

    var selectedItems = mutableListOf<FeedMinimal>()

    val feedsMinimalLiveData: LiveData<List<FeedMinimal>> = repository.getAllFeedsMinimal()

    fun updateFeeds(feeds: List<Feed>) {
        repository.updateFeeds(feeds)
    }

    fun deleteFeedsAndEntriesByIds(ids: List<String>) {
        repository.deleteFeedsAndEntriesByIds(ids)
    }

    fun updateCategoryByFeedIds(ids: List<String>, category: String) {
        repository.updateCategoryByFeedIds(ids.toTypedArray(), category)
    }
}