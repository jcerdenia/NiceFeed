package com.joshuacerdenia.android.nicefeed.data.model

import androidx.room.ColumnInfo

data class FeedMinimal(
    /*
    @ColumnInfo (name = "website") val website: String,
    @ColumnInfo (name = "title") val title: String?,
    @ColumnInfo (name = "imageUrl") val imageUrl: String?,
    @ColumnInfo (name = "category") var category: String,
    @ColumnInfo (name = "unreadCount") var unreadCount: Int
     */

    val website: String,
    val title: String?,
    var category: String
)