package com.joshuacerdenia.android.nicefeed.ui.viewmodel

import androidx.lifecycle.*
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.EntryLight
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.data.remote.FeedParser
import com.joshuacerdenia.android.nicefeed.ui.dialog.FilterEntriesFragment
import com.joshuacerdenia.android.nicefeed.ui.fragment.EntryListFragment
import com.joshuacerdenia.android.nicefeed.utils.UpdateManager
import com.joshuacerdenia.android.nicefeed.utils.extensions.shortened
import com.joshuacerdenia.android.nicefeed.utils.extensions.sortedByDate
import com.joshuacerdenia.android.nicefeed.utils.extensions.sortedUnreadOnTop
import kotlinx.coroutines.launch
import java.util.*

private const val MAX_NEW_ENTRIES = 50 // Maybe this can be changed dynamically

class EntryListViewModel: ViewModel(), UpdateManager.UpdateReceiver {

    private val repo = NiceFeedRepository.get()
    private val parser = FeedParser(repo.networkMonitor)
    private val updateManager = UpdateManager(this)

    private val feedIdLiveData = MutableLiveData<String>()
    val feedLiveData = Transformations.switchMap(feedIdLiveData) { feedId ->
        repo.getFeed(feedId)
    }
    private val sourceEntriesLiveData = Transformations.switchMap(feedIdLiveData) { feedId ->
        when (feedId) {
            EntryListFragment.FOLDER_NEW -> repo.getNewEntries(MAX_NEW_ENTRIES)
            EntryListFragment.FOLDER_STARRED -> repo.getStarredEntries()
            else -> repo.getEntriesByFeed(feedId)
        }
    }

    private val entriesLiveData = MediatorLiveData<List<Entry>>()
    val entriesLightLiveData = MediatorLiveData<List<EntryLight>>()
    val updateResultLiveData = parser.feedRequestLiveData

    var currentQuery = ""
        private set
    var currentOrder = 0
        private set
    var currentFilter = 0
        private set
    var updateValues: Pair<Int, Int>? = null
        private set

    private var updateWasRequested = false
    var shouldAutoRefresh = true

    init {
        entriesLiveData.addSource(sourceEntriesLiveData) { source ->
            val filteredEntries = filterEntries(source, currentFilter)
            entriesLiveData.value = queryEntries(filteredEntries, currentQuery)
            updateManager.setInitialEntries(source)
        }

        entriesLightLiveData.addSource(entriesLiveData) { entries ->
            val list = entries.map { entry -> EntryLight(
                    url = entry.url,
                    title = entry.title,
                    website = entry.website,
                    date = entry.date,
                    image = entry.image,
                    isRead = entry.isRead,
                    isStarred = entry.isStarred,
            ) }
            entriesLightLiveData.value = sortEntries(list, currentOrder)
        }
    }

    fun getFeedWithEntries(feedId: String) {
        feedIdLiveData.value = feedId
    }

    fun requestUpdate(url: String) {
        shouldAutoRefresh = false
        updateWasRequested = true
        viewModelScope.launch { parser.requestFeed(url) }
    }

    fun onFeedLoaded() {
        feedLiveData.value?.let { feed ->
            updateManager.setInitialFeed(feed)
        }
    }

    fun onUpdatesDownloaded(feedWithEntries: FeedWithEntries) {
        if (updateWasRequested) {
            updateManager.submitUpdates(feedWithEntries)
            updateWasRequested = false
        }
    }

    fun setFilter(filter: Int) {
        currentFilter = filter
        sourceEntriesLiveData.value?.let { entries ->
            val filteredEntries = filterEntries(entries, filter)
            entriesLiveData.value = queryEntries(filteredEntries, currentQuery)
        }
    }

    fun setOrder(order: Int) {
        if (currentOrder != order) {
            currentOrder = order
            entriesLightLiveData.value?.let { entries ->
                entriesLightLiveData.value = sortEntries(entries, order)
            }
        }
    }

    fun submitQuery(query: String) {
        currentQuery = query
        sourceEntriesLiveData.value?.let { source ->
            val filteredEntries = filterEntries(source, currentFilter)
            entriesLiveData.value = if (currentQuery.isNotEmpty()) {
                queryEntries(filteredEntries, query)
            } else filteredEntries
        }
    }

    fun starAllCurrentEntries() {
        val entries = entriesLightLiveData.value ?: emptyList()
        val isStarred = !allIsStarred(entries)
        val entryIds = entries.map { entry -> entry.url }.toTypedArray()
        repo.updateEntryIsStarred(*entryIds, isStarred = isStarred)
    }

    fun markAllCurrentEntriesAsRead() {
        val entries = entriesLightLiveData.value ?: emptyList()
        val isRead = !allIsRead(entries)
        val entryIds = entries.map { entry -> entry.url }.toTypedArray()
        repo.updateEntryIsRead(*entryIds, isRead = isRead)
    }

    fun allIsStarred(
        entries: List<EntryLight> = entriesLightLiveData.value ?: emptyList()
    ): Boolean {
        var count = 0
        for (entry in entries) {
            if (entry.isStarred) {
                count += 1
            } else break
        }
        return count == entries.size
    }

    fun allIsRead(
        entries: List<EntryLight> = entriesLightLiveData.value ?: emptyList()
    ): Boolean {
        var count = 0
        for (entry in entries) {
            if (entry.isRead) {
                count += 1
            } else break
        }
        return count == entries.size
    }

    private fun queryEntries(entries: List<Entry>, query: String): List<Entry> {
        val results = mutableListOf<Entry>()
        for (entry in entries) {
            if (entry.title.toLowerCase(Locale.ROOT).contains(query) ||
                    entry.website.shortened().toLowerCase(Locale.ROOT).contains(query)) {
                results.add(entry)
            }
        }
        return results
    }

    private fun filterEntries(entries: List<Entry>, filter: Int): List<Entry> {
        return when (filter) {
            FilterEntriesFragment.FILTER_UNREAD -> entries.filter { !it.isRead }
            FilterEntriesFragment.FILTER_STARRED -> entries.filter { it.isStarred }
            else -> entries
        }
    }

    private fun sortEntries(entries: List<EntryLight>, order: Int): List<EntryLight> {
        return if (order == NiceFeedPreferences.ENTRY_ORDER_UNREAD) {
            entries.sortedUnreadOnTop()
        } else entries.sortedByDate()
    }

    override fun onUnreadEntriesCounted(feedId: String, unreadCount: Int) {
        repo.updateFeedUnreadCount(feedId, unreadCount)
    }

    override fun onFeedNeedsUpdate(feed: Feed) {
        repo.updateFeed(feed)
    }

    override fun onOldAndNewEntriesCompared(
        entriesToAdd: List<Entry>,
        entriesToUpdate: List<Entry>,
        entriesToDelete: List<Entry>,
        feedId: String
    ) {
        repo.handleEntryUpdates(entriesToAdd, entriesToUpdate, entriesToDelete, feedId)
        updateValues = if (entriesToAdd.size + entriesToUpdate.size > 0) {
            Pair(entriesToAdd.size, entriesToUpdate.size)
        } else null
    }

    fun updateCategory(category: String) {
        updateManager.currentFeed.apply {
            this?.category = category
        }?.let { feed -> repo.updateFeedCategory(feed.url, category = category) }
    }

    fun getCurrentFeed() = updateManager.currentFeed

    fun updateEntryIsStarred(entryId: String, isStarred: Boolean) {
        repo.updateEntryIsStarred(entryId, isStarred = isStarred)
    }

    fun updateEntryIsRead(entryId: String, isRead: Boolean) {
        repo.updateEntryIsRead(entryId, isRead = isRead)
    }

    fun onUpdateNoticeShown() {
        updateValues = null
    }

    fun deleteFeedAndEntries() {
        getCurrentFeed()?.url?.let { feedId -> repo.deleteFeedAndEntriesById(feedId) }
    }
}