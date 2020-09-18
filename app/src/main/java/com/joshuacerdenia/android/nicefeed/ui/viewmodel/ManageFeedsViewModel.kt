package com.joshuacerdenia.android.nicefeed.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.FeedMinimal
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFeedManagerFragment
import com.joshuacerdenia.android.nicefeed.utils.extensions.sortedByCategory
import com.joshuacerdenia.android.nicefeed.utils.extensions.sortedByTitle

class ManageFeedsViewModel: ViewModel() {

    private val repository = NiceFeedRepository.get()

    private val sourceFeedsLiveData: LiveData<List<FeedMinimal>> = repository.getFeedsMinimal()
    val feedsMinimalLiveData = MediatorLiveData<List<FeedMinimal>>()

    var selectedItems = mutableListOf<FeedMinimal>()
    var currentOrder = 0
        private set

    init {
        feedsMinimalLiveData.addSource(sourceFeedsLiveData) { feeds ->
            feedsMinimalLiveData.value = sortFeeds(feeds, currentOrder)
        }
    }

    fun setOrder(order: Int) {
        currentOrder = order
        sourceFeedsLiveData.value?.let { feeds ->
            feedsMinimalLiveData.value = sortFeeds(feeds, order)
        }
    }

    fun getCategories(): Array<String> {
        val categories = mutableSetOf<String>()
        sourceFeedsLiveData.value?.let { feeds ->
            for (feed in feeds) {
                categories.add(feed.category)
            }
        }
        return categories.toTypedArray()
    }

    private fun sortFeeds(feeds: List<FeedMinimal>, order: Int): List<FeedMinimal> {
        return when (order) {
            SortFeedManagerFragment.SORT_BY_CATEGORY -> feeds.sortedByCategory()
            SortFeedManagerFragment.SORT_BY_TITLE -> feeds.sortedByTitle()
            else -> feeds.reversed() // Default
        }
    }

    fun deleteItems(vararg feedId: String) {
        repository.deleteFeedAndEntriesById(*feedId)
    }

    fun updateCategoryByFeedIds(ids: List<String>, category: String) {
        repository.updateFeedCategory(*ids.toTypedArray(), category = category)
    }
}