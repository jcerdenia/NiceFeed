package com.joshuacerdenia.android.nicefeed.data.model

import androidx.room.ColumnInfo
import java.util.*

data class FeedInfo(
    @ColumnInfo (name = "website") var website: String,
    @ColumnInfo (name = "title") var title: String?,
    @ColumnInfo (name = "imageUrl") var imageUrl: String?,
    @ColumnInfo (name = "category") var category: String,
    @ColumnInfo (name = "unreadCount") var unreadCount: Int
)