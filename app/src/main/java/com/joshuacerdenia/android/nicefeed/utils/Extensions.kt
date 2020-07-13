package com.joshuacerdenia.android.nicefeed.utils

import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.Feed

fun List<Feed>.sortedByTitle() = this.sortedBy { it.title }

fun List<Feed>.sortedByCategory() = this.sortedBy { it.category }

fun List<Feed>.sortedByUnreadCount() = this.sortedByDescending { it.unreadCount }

fun List<Entry>.sortedByDate() = this.sortedByDescending { it.date}

fun String?.pathified() = this?.substringAfter(
    "www.",
    this.substringAfter("://")
)?.removeSuffix("/")