package com.joshuacerdenia.android.nicefeed.data.model

import androidx.room.Embedded
import androidx.room.Relation

class EntryWithFeedId(
    val entry: Entry,
    val url: String
)