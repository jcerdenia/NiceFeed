package com.joshuacerdenia.android.nicefeed.utils

import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedInfo

fun Int.lastDigit() = this.rem(10)

fun List<Feed>.sortedByTitle() = this.sortedBy { it.title }

fun List<Feed>.sortedByCategory() = this.sortedBy { it.category }

fun List<FeedInfo>.sortedByUnreadCount() = this.sortedByDescending { it.unreadCount }

fun List<Feed>.sortedByUpdated() = this.sortedByDescending { it.updated }

fun List<Entry>.sortedByDatePublished() = this.sortedByDescending { it.date}

fun List<Entry>.unreadOnTop() = this.sortedByDatePublished().sortedBy { it.isRead }

fun String?.pathified() = this?.substringAfter(
    "www.",
    this.substringAfter("://")
)?.removeSuffix("/")

fun String?.simplified() = this?.substringBefore("?").pathified()