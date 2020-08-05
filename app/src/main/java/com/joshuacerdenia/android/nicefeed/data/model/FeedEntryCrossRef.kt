package com.joshuacerdenia.android.nicefeed.data.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["url", "guid"],
    indices = [(Index(value = ["guid"]))]
)
data class FeedEntryCrossRef(
    val url: String,
    val guid: String
)