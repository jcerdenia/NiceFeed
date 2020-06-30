package com.joshuacerdenia.android.nicefeed

import androidx.room.Embedded
import androidx.room.Relation

data class FeedWithEntries(
    @Embedded val feed: Feed,
    @Relation(
        parentColumn = "website",
        entityColumn = "website"
    )
    val entries: List<Entry>
)