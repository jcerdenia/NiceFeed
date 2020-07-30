package com.joshuacerdenia.android.nicefeed.ui

import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.Entry

class EntryViewModel: ViewModel() {

    private val repository = NiceFeedRepository.get()

    fun updateEntry(entry: Entry) {
        repository.updateEntry(entry)
    }

    fun updateFeedUnreadCountById(id: String, count: Int) {
        repository.updateFeedUnreadCountById(id, count)
    }
}