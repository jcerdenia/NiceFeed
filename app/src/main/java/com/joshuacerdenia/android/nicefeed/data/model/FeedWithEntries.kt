package com.joshuacerdenia.android.nicefeed.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class FeedWithEntries(
    @Embedded val feed: Feed,
    @Relation(
        parentColumn = "url",
        entityColumn = "feedUrl"
    )
    val entries: List<Entry>
)