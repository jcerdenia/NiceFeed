package com.joshuacerdenia.android.nicefeed.ui.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.EntryMinimal
import com.joshuacerdenia.android.nicefeed.utils.EntryToHtmlFormatter

class EntryViewModel : ViewModel() {

    private val repo = NiceFeedRepository.get()

    private val entryIdLiveData = MutableLiveData<String>()
    private val entryLiveData = Transformations.switchMap(entryIdLiveData) { entryId ->
        repo.getEntry(entryId)
    }
    val htmlLiveData = MediatorLiveData<String>()

    var lastPosition: Pair<Int, Int> = Pair(0, 0)
    var textSize = 0
        private set

    lateinit var url: String
        private set
    lateinit var title: String
        private set
    lateinit var website: String
        private set
    var isStarred = false

    init {
        htmlLiveData.addSource(entryLiveData) { data ->
            data?.let { entry ->
                url = entry.url
                title = entry.title
                website = entry.website
                isStarred = entry.isStarred
                drawHtml(entry)
            }
        }
    }

    fun getEntryById(entryId: String) {
        entryIdLiveData.value = entryId
    }

    fun setTextSize(textSize: Int) {
        this.textSize = textSize
        entryLiveData.value?.let { entry ->
            drawHtml(entry)
        }
    }

    private fun drawHtml(entry: Entry) {
        EntryMinimal(
            title = entry.title,
            date = entry.date,
            author = entry.author,
            content = entry.content
        ).let { minimalEntry ->
            htmlLiveData.value = EntryToHtmlFormatter(textSize).getHtml(minimalEntry)
        }
    }

    fun saveChanges() {
        repo.updateEntryAndFeedUnreadCount(url, true, isStarred)
    }
}