package com.joshuacerdenia.android.nicefeed.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.Entry

class EntryViewModel: ViewModel() {

    private val repo = NiceFeedRepository.get()
    var lastPosition: Pair<Int, Int> = Pair(0, 0)

    private val entryIdLiveData = MutableLiveData<String>()
    val entryLiveData = Transformations.switchMap(entryIdLiveData) { entryId ->
        repo.getEntry(entryId)
    }

    fun getEntryById(entryId: String) {
        entryIdLiveData.value = entryId
    }

    fun updateEntry(entry: Entry) {
        repo.updateEntryAndFeedUnreadCount(entry.url, entry.isRead, entry.isStarred)
    }

    fun updateFeedUnreadCountById(id: String, count: Int) {
        repo.updateFeedUnreadCount(id, count)
    }
}