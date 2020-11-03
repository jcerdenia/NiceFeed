package com.joshuacerdenia.android.nicefeed.data.model.cross

import com.joshuacerdenia.android.nicefeed.data.model.entry.EntryUsed

data class FeedTitleWithEntryInfo(
    val feedTitle: String,
    val entriesUsed: List<EntryUsed>
)