package com.joshuacerdenia.android.nicefeed.data.model

// Minimal version of Feed – no website, description, and unreadCount

data class FeedMinimal (
    val url: String,
    val title: String?,
    val website: String,
    var category: String
)