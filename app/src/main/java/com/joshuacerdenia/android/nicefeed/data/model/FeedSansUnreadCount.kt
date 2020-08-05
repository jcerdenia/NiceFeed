package com.joshuacerdenia.android.nicefeed.data.model

data class FeedSansUnreadCount(
    val url: String,
    val website: String,
    var title: String,
    val description: String? = null,
    val imageUrl: String? = null,
    var category: String = "Uncategorized"
)