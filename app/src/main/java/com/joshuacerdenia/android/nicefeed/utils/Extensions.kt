package com.joshuacerdenia.android.nicefeed.utils

import com.joshuacerdenia.android.nicefeed.data.model.EntryLight
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedMinimal

fun Int.lastDigit() = this.rem(10)

fun List<FeedMinimal>.sortedByTitle() = this.sortedBy { it.title }

fun List<FeedMinimal>.sortedByCategory() = this.sortedBy { it.category }

fun List<Feed>.sortedByUnreadCount() = this.sortedByDescending { it.unreadCount }

fun List<EntryLight>.sortedByDatePublished() = this.sortedByDescending { it.date }

fun List<EntryLight>.sortedUnreadOnTop() = this.sortedByDatePublished().sortedBy { it.isRead }

fun String.pathified() = this.substringAfter(
    "www.",
    this.substringAfter("://")
).removeSuffix("/")

fun String.simplified() = this.substringBefore("?").pathified()

fun String.shortened() = this.pathified().substringBefore("/").substringBefore("?")