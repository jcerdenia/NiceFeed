package com.joshuacerdenia.android.nicefeed.ui

import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.Repository
import com.joshuacerdenia.android.nicefeed.data.model.Entry

class EntryViewModel: ViewModel() {

    private val repository = Repository.get()

    fun updateEntry(entry: Entry) {
        repository.updateEntry(entry)
    }
}