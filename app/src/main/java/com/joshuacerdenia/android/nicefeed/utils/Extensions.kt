package com.joshuacerdenia.android.nicefeed.utils

import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.EntryMinimal
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedMinimal

fun Int.lastDigit() = this.rem(10)

fun List<FeedMinimal>.sortedByTitle() = this.sortedBy { it.title }

fun List<FeedMinimal>.sortedByCategory() = this.sortedBy { it.category }

fun List<Feed>.sortedByUnreadCount() = this.sortedByDescending { it.unreadCount }

fun List<EntryMinimal>.sortedByDatePublished() = this.sortedByDescending { it.date }

fun List<EntryMinimal>.sortedUnreadOnTop() = this.sortedByDatePublished().sortedBy { it.isRead }

fun String.pathified() = this.substringAfter(
    "www.",
    this.substringAfter("://")
).removeSuffix("/")

fun String.simplified() = this.substringBefore("?").pathified()