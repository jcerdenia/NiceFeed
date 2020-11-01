package com.joshuacerdenia.android.nicefeed.data.model

import java.io.Serializable

// Minimal version of Feed – no website, imageUrl, description, and unreadCount
data class FeedMinimal (
    val url: String,
    val title: String?,
    val website: String,
    var category: String,
)