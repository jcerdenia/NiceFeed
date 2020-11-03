package com.joshuacerdenia.android.nicefeed.data.model.cross

import com.joshuacerdenia.android.nicefeed.data.model.entry.EntryToggleable

data class FeedTitleWithEntriesToggleable(
    val feedTitle: String,
    val entriesToggleable: List<EntryToggleable>
)