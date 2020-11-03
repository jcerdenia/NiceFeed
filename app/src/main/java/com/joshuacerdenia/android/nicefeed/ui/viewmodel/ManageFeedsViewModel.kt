package com.joshuacerdenia.android.nicefeed.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFeedManagerFragment
import com.joshuacerdenia.android.nicefeed.utils.extensions.sortedByCategory
import com.joshuacerdenia.android.nicefeed.utils.extensions.sortedByTitle

class ManageFeedsViewModel: ViewModel() {

    private val repo = NiceFeedRepository.get()

    private val sourceFeedsLiveData: LiveData<List<FeedManageable>> = repo.getFeedsManageable()
    val feedsManageableLiveData = MediatorLiveData<List<FeedManageable>>()

    var selectedItems = mutableListOf<FeedManageable>()
    var currentOrder = 0
        private set

    init {
        feedsManageableLiveData.addSource(sourceFeedsLiveData) { feeds ->
            feedsManageableLiveData.value = sortFeeds(feeds, currentOrder)
        }
    }

    fun setOrder(order: Int) {
        currentOrder = order
        sourceFeedsLiveData.value?.let { feeds ->
            feedsManageableLiveData.value = sortFeeds(feeds, order)
        }
    }

    fun getCategories(): Array<String> {
        val categories = mutableSetOf<String>()
        sourceFeedsLiveData.value?.let { feeds ->
            for (feed in feeds) categories.add(feed.category)
        }
        return categories.toTypedArray()
    }

    private fun sortFeeds(feeds: List<FeedManageable>, order: Int): List<FeedManageable> {
        return when (order) {
            SortFeedManagerFragment.SORT_BY_CATEGORY -> feeds.sortedByCategory()
            SortFeedManagerFragment.SORT_BY_TITLE -> feeds.sortedByTitle()
            else -> feeds.reversed() // Default
        }
    }

    fun deleteItems(vararg feedId: String) {
        repo.deleteFeedAndEntriesById(*feedId)
    }

    fun updateCategoryByFeedIds(ids: List<String>, category: String) {
        repo.updateFeedCategory(*ids.toTypedArray(), category = category)
    }

    fun updateFeedDetails(feedId: String, title: String, category: String) {
        repo.updateFeedDetails(feedId, title, category)
    }
}