package com.joshuacerdenia.android.nicefeed.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFeedManagerFragment
import com.joshuacerdenia.android.nicefeed.util.extensions.pathified
import com.joshuacerdenia.android.nicefeed.util.extensions.sortedByCategory
import com.joshuacerdenia.android.nicefeed.util.extensions.sortedByTitle
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

    var order = 0
        private set
    var query = ""
        private set

    init {
        feedsManageableLiveData.addSource(sourceFeedsLiveData) { feeds ->
            feedsManageableLiveData.value = sortFeeds(feeds, order)
        }
    }

    private fun setObservableFeeds(feeds: List<FeedManageable>) {
        val feedsQueried = queryFeeds(feeds, query.toLowerCase(Locale.ROOT))
        feedsManageableLiveData.value = sortFeeds(feedsQueried, order)
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
        this.order = order
        sourceFeedsLiveData.value?.let { setObservableFeeds(it) }
    }

    fun submitQuery(query: String) {
        this.query = query.trim()
        sourceFeedsLiveData.value?.let { setObservableFeeds(it) }
    }

    fun clearQuery() {
        submitQuery("")
    }

    private fun sortFeeds(feeds: List<FeedManageable>, order: Int): List<FeedManageable> {
        return when (order) {
            SortFeedManagerFragment.SORT_BY_CATEGORY -> feeds.sortedByCategory()
            SortFeedManagerFragment.SORT_BY_TITLE -> feeds.sortedByTitle()
            else -> feeds.reversed() // Default
        }
    }

    private fun queryFeeds(feeds: List<FeedManageable>, query: String): List<FeedManageable> {
        val results = feeds.filter { feed ->
            feed.title.toLowerCase(Locale.ROOT).contains(query)
                    || feed.category.toLowerCase(Locale.ROOT).contains(query)
                    || feed.url.pathified().contains(query)
        }
        // If current selected items contains items not returned by the query, remove them:
        _selectedItems.filter { !results.contains(it) }.toTypedArray().run (::removeSelection)
        return results
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