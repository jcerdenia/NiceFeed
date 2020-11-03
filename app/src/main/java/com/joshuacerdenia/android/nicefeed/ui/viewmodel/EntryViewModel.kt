package com.joshuacerdenia.android.nicefeed.ui.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.entry.Entry
import com.joshuacerdenia.android.nicefeed.data.model.entry.EntryMinimal
import com.joshuacerdenia.android.nicefeed.data.remote.FeedParser
import com.joshuacerdenia.android.nicefeed.utils.EntryToHtmlFormatter

class EntryViewModel : ViewModel() {

    private val repo = NiceFeedRepository.get()

    private val entryIdLiveData = MutableLiveData<String>()
    private val entryLiveData = Transformations.switchMap(entryIdLiveData) { entryId ->
        repo.getEntry(entryId)
    }
    val htmlLiveData = MediatorLiveData<String?>()

    var lastPosition: Pair<Int, Int> = Pair(0, 0)
    var textSize = 0
        private set
    var font = 0
    var bannerIsEnabled = true
    var viewAsWebPage = true
    var isInitialLoading = true

    var entry: Entry? = null
        private set
    // As of now, unused:
    var isExcerpt = false
        private set

    init {
        htmlLiveData.addSource(entryLiveData) { source ->
            if (source != null) {
                entry = source
                isExcerpt = source.content?.startsWith(FeedParser.FLAG_EXCERPT) ?: false
                drawHtml(source)
            } else htmlLiveData.value = null
        }
    }

    fun getEntryById(entryId: String) {
        entryIdLiveData.value = entryId
    }

    fun setTextSize(textSize: Int) {
        this.textSize = textSize
        entryLiveData.value?.let { entry -> drawHtml(entry) }
    }

    private fun drawHtml(entry: Entry) {
        if (!viewAsWebPage) {
            EntryMinimal(
                title = entry.title,
                date = entry.date,
                author = entry.author,
                content = entry.content?.removePrefix(FeedParser.FLAG_EXCERPT) ?: ""
            ).let { entryData ->
                htmlLiveData.value = EntryToHtmlFormatter(
                    textSize,
                    font,
                    !bannerIsEnabled
                ).getHtml(entryData)
            }
        } else htmlLiveData.value = entry.url
    }

    fun toggleLoadAsWebPage() {
        viewAsWebPage = !viewAsWebPage
        entry?.let { drawHtml(it) }
    }

    fun saveChanges() {
        entry?.let { repo.updateEntryAndFeedUnreadCount(it.url, true, it.isStarred) }
    }
}