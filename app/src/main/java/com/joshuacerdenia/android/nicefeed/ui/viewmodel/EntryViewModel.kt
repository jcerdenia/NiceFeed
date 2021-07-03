package com.joshuacerdenia.android.nicefeed.ui.viewmodel

import androidx.lifecycle.*
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.local.FeedPreferences
import com.joshuacerdenia.android.nicefeed.data.model.entry.Entry
import com.joshuacerdenia.android.nicefeed.data.model.entry.EntryMinimal
import com.joshuacerdenia.android.nicefeed.data.remote.FeedParser
import com.joshuacerdenia.android.nicefeed.util.EntryToHtmlFormatter

class EntryViewModel : ViewModel() {

    private val repo = NiceFeedRepository.get()

    private val entryIdLiveData = MutableLiveData<String>()
    private val entryLiveData = Transformations
        .switchMap(entryIdLiveData) { entryId ->
            repo.getEntry(entryId)
        }

    private val _htmlLiveData = MediatorLiveData<String?>()
    val htmlLiveData: LiveData<String?> get() = _htmlLiveData

    var lastPosition: Pair<Int, Int> = Pair(0, 0)

    var textSize = 0
        get() = FeedPreferences.textSize
        private set

    var font = FeedPreferences.font
    var isBannerEnabled = FeedPreferences.isBannerEnabled
    var isInitialLoading = true

    var entry: Entry? = null
        private set

    private var isExcerpt = false // As of now, unused

    init {
        _htmlLiveData.addSource(entryLiveData) { source ->
            if (source != null) {
                entry = source
                isExcerpt = source.content?.startsWith(FeedParser.FLAG_EXCERPT) ?: false
                drawHtml(source)
            } else _htmlLiveData.value = null
        }
    }

    fun getEntryById(entryId: String) {
        entryIdLiveData.value = entryId
    }

    fun setTextSize(textSize: Int) {
        FeedPreferences.textSize = textSize
        entryLiveData.value?.let { entry -> drawHtml(entry) }
    }

    private fun drawHtml(entry: Entry) {
        EntryMinimal(
            title = entry.title, date = entry.date, author = entry.author,
            content = entry.content?.removePrefix(FeedParser.FLAG_EXCERPT) ?: ""
        ).let { _htmlLiveData.value = EntryToHtmlFormatter(textSize, font, !isBannerEnabled).getHtml(it) }
    }

    fun saveChanges() {
        entry?.let { repo.updateEntryAndFeedUnreadCount(it.url, true, it.isStarred) }
    }
}