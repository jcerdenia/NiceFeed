package com.joshuacerdenia.android.nicefeed.data.model

import androidx.room.ColumnInfo

data class EntryIdPair(
    val guid: String,
    @ColumnInfo(name = "feed_url") val feedUrl: String
)