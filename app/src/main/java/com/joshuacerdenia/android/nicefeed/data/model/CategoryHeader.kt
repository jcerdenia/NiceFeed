package com.joshuacerdenia.android.nicefeed.data.model

data class CategoryHeader(
    val category: String,
    val isMinimized: Boolean,
    var unreadCount: Int = 0
)