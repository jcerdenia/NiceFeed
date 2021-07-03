package com.joshuacerdenia.android.nicefeed.data.model.entry

import java.text.DateFormat
import java.util.*

// Minimal version of Entry – no url, website, image, isStarred, isRead
data class EntryMinimal (
    val title: String,
    val date: Date?,
    val author: String?,
    val content: String
) {

    val formattedDate: String? get() = this.date?.let { date ->
        DateFormat
            .getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
            .format(date)
    }
}