package com.joshuacerdenia.android.nicefeed.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFeedManagerFragment
import com.joshuacerdenia.android.nicefeed.utils.extensions.pathified
import com.joshuacerdenia.android.nicefeed.utils.extensions.sortedByCategory
import com.joshuacerdenia.android.nicefeed.utils.extensions.sortedByTitle
import java.util.*

class ManageFeedsViewModel: ViewModel() {

    private val repo = NiceFeedRepository.get()

    private val sourceFeedsLiveData: LiveData<List<FeedManageable>> = repo.getFeedsManageable()
    val feedsManageableLiveData = MediatorLiveData<List<FeedManageable>>()

    private var _anyIsSelected: MutableLiveData<Boolean> = MutableLiveData(false)
    val anyIsSelected: LiveData<Boolean>
        get() = _anyIsSelected

    private val _selectedItems = mutableListOf<FeedManageable>()
    val selectedItems: List<FeedManageable>
        get() = _selectedItems

    var currentOrder = 0
        private set
    var currentQuery = ""
        private set

    init {
        feedsManageableLiveData.addSource(sourceFeedsLiveData) { feeds ->
            feedsManageableLiveData.value = sortFeeds(feeds, currentOrder)
        }
    }

    private fun setFeedList() {
        sourceFeedsLiveData.value?.let { feeds ->
            val filteredFeeds = queryFeeds(feeds, currentQuery)
            feedsManageableLiveData.value = sortFeeds(filteredFeeds, currentOrder)
        }
    }

    fun addSelection(feed: FeedManageable) {
        _selectedItems.add(feed)
        _anyIsSelected.value = true
    }

    fun resetSelection(feeds: List<FeedManageable>? = null) {
        _selectedItems.clear()
        feeds?.forEach { _selectedItems.add(it) }
        _anyIsSelected.value = _selectedItems.isNotEmpty()
    }

    fun removeSelection(vararg feed: FeedManageable) {
        feed.forEach { _selectedItems.remove(it) }
        _anyIsSelected.value = _selectedItems.isNotEmpty()
    }

    fun setOrder(order: Int) {
        currentOrder = order
        setFeedList()
    }

    fun submitQuery(query: String) {
        currentQuery = query.toLowerCase(Locale.ROOT).trim()
        setFeedList()
    }

    private fun sortFeeds(feeds: List<FeedManageable>, order: Int): List<FeedManageable> {
        return when (order) {
            SortFeedManagerFragment.SORT_BY_CATEGORY -> feeds.sortedByCategory()
            SortFeedManagerFragment.SORT_BY_TITLE -> feeds.sortedByTitle()
            else -> feeds.reversed() // Default
        }
    }

    private fun queryFeeds(feeds: List<FeedManageable>, query: String): List<FeedManageable> {
        val filteredFeeds = feeds.filter { feed ->
            feed.title.toLowerCase(Locale.ROOT).contains(query)
                    || feed.category.toLowerCase(Locale.ROOT).contains(query)
                    || feed.url.pathified().contains(query)
        }
        _selectedItems.filter { (!filteredFeeds.contains(it)) }.toTypedArray().run (::removeSelection)
        return filteredFeeds
    }

    fun getCategories(): Array<String> {
        val categories = mutableSetOf<String>()
        sourceFeedsLiveData.value?.let { feeds ->
            for (feed in feeds) categories.add(feed.category)
        }
        return categories.toTypedArray()
    }

    fun deleteItems(vararg feedId: String) {
        repo.deleteFeedAndEntriesById(*feedId)
    }

    fun updateCategoryByFeedIds(ids: List<String>, category: String) {
        repo.updateFeedCategory(*ids.toTypedArray(), category = category)
    }

    fun updateFeedDetails(feedId: String, title: String, category: String) {
        repo.updateFeedTitleAndCategory(feedId, title, category)
    }
}