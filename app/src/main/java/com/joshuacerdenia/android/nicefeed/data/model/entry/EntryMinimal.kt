package com.joshuacerdenia.android.nicefeed.data.model.entry

import java.util.*

// Minimal version of Entry – no url, website, image, isStarred, isRead
data class EntryMinimal (
    val title: String,
    val date: Date?,
    val author: String?,
    val content: String
)