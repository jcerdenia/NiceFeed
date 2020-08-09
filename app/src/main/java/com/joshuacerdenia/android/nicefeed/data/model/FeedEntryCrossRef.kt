package com.joshuacerdenia.android.nicefeed.data.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["feedUrl", "entryUrl"],
    indices = [(Index(value = ["entryUrl"]))]
)
data class FeedEntryCrossRef(
    val feedUrl: String,
    val entryUrl: String
)