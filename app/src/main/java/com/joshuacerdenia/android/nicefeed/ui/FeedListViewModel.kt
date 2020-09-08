package com.joshuacerdenia.android.nicefeed.ui

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.CategoryHeader
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedMenuItem
import com.joshuacerdenia.android.nicefeed.utils.sortedByUnreadCount

open class FeedListViewModel: ViewModel() {

    val repository = NiceFeedRepository.get()
    private val sourceFeedsLiveData = repository.getFeeds()

    var activeFeedId: String? = null
    var categories = arrayOf<String>()
        private set
    val minimizedCategories = mutableSetOf<String>()

    val feedListLiveData = MediatorLiveData<List<FeedMenuItem>>()

    init {
        feedListLiveData.addSource(sourceFeedsLiveData) { feeds ->
            feedListLiveData.value = arrangeFeedsAndCategories(feeds, minimizedCategories)
        }
    }

    fun setMinimizedCategories(categories: Set<String>?) {
        categories?.forEach { category ->
            minimizedCategories.add(category)
        }
    }

    fun toggleCategoryDropDown(category: String) {
        if (minimizedCategories.contains(category)) {
            minimizedCategories.remove(category)
        } else {
            minimizedCategories.add(category)
        }

        sourceFeedsLiveData.value?.let { feeds ->
            feedListLiveData.value = arrangeFeedsAndCategories(feeds, minimizedCategories)
        }
    }

    private fun arrangeFeedsAndCategories(
        feeds: List<Feed>,
        minimizedCategories: Set<String>
    ): List<FeedMenuItem> {
        val categories = getOrderedCategories(feeds)
        val arrangedMenu = mutableListOf<FeedMenuItem>()

        for (category in categories) {
            val isMinimized = minimizedCategories.contains(category)
            val categoryHeader = CategoryHeader(category, isMinimized)
            arrangedMenu.add(FeedMenuItem(categoryHeader))

            for (feed in feeds.sortedByUnreadCount()) {
                if (feed.category == category) {
                    categoryHeader.unreadCount += feed.unreadCount
                    if (!isMinimized) {
                        arrangedMenu.add(FeedMenuItem(feed))
                    }
                }
            }
        }

        this.categories = categories.toTypedArray()
        return arrangedMenu
    }

    private fun getOrderedCategories(feeds: List<Feed>): List<String> {
        val categories = mutableSetOf<String>()
        for (feed in feeds) {
            categories.add(feed.category)
        }
        return categories.toList().sorted() // Sort alphabetically
    }
}