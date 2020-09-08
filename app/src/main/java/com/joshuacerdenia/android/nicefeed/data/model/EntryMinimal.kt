package com.joshuacerdenia.android.nicefeed.data.model

import java.util.*

data class EntryMinimal(
    val url: String,
    val title: String,
    val website: String,
    val date: Date?,
    val image: String?,
    var isStarred: Boolean = false,
    var isRead: Boolean = false
)