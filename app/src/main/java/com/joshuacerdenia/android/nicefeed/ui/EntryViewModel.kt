package com.joshuacerdenia.android.nicefeed.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.Entry

class EntryViewModel: ViewModel() {

    private val repository = NiceFeedRepository.get()
    private val entryIdLiveData = MutableLiveData<String>()
    val entryLiveData = Transformations.switchMap(entryIdLiveData) { entryId ->
        repository.getEntryById(entryId)
    }

    fun getEntryById(entryId: String) {
        entryIdLiveData.value = entryId
    }

    fun updateEntry(entry: Entry) {
        repository.updateEntry(entry)
    }

    fun updateFeedUnreadCountById(id: String, count: Int) {
        repository.updateFeedUnreadCountById(id, count)
    }
}