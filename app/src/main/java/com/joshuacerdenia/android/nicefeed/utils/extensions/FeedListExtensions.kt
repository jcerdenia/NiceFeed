package com.joshuacerdenia.android.nicefeed.utils.extensions

import com.joshuacerdenia.android.nicefeed.data.model.FeedLight
import com.joshuacerdenia.android.nicefeed.data.model.FeedMinimal

@JvmName("sortedByTitleFeedLight")
fun List<FeedLight>.sortedByTitle() = this.sortedBy { it.title }

fun List<FeedLight>.sortedByUnreadCount() = this.sortedByDescending { it.unreadCount }

fun List<FeedMinimal>.sortedByTitle() = this.sortedBy { it.title }

fun List<FeedMinimal>.sortedByCategory() = this.sortedBy { it.category }