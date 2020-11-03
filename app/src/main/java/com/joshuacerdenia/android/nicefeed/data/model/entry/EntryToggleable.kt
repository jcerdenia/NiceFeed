package com.joshuacerdenia.android.nicefeed.data.model.entry

data class EntryToggleable(
    val url: String,
    val isStarred: Boolean,
    val isRead: Boolean,
)