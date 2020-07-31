package com.joshuacerdenia.android.nicefeed.data.model

import androidx.room.ColumnInfo

data class FeedMinimal (
    val website: String,
    val title: String?,
    var category: String
)