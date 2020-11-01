package com.joshuacerdenia.android.nicefeed.data.model

import java.util.*

// Light version of Entry – no content and author
data class EntryLight(
    val url: String,
    val title: String,
    val website: String,
    val date: Date?,
    val image: String?,
    var isStarred: Boolean = false,
    var isRead: Boolean = false
)