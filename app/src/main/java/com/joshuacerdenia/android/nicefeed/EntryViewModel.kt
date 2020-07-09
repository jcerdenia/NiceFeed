package com.joshuacerdenia.android.nicefeed

import androidx.lifecycle.ViewModel

class EntryViewModel: ViewModel() {

    private val repository = Repository.get()

    fun updateEntry(entry: Entry) {
        repository.updateEntry(entry)
    }
}