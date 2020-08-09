package com.joshuacerdenia.android.nicefeed.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class FeedWithEntries(
    @Embedded val feed: Feed,
    @Relation(
        parentColumn = "url",
        entityColumn = "url",
        associateBy = Junction(
            value = FeedEntryCrossRef::class,
            parentColumn = "feedUrl",
            entityColumn = "entryUrl"
        )
    )
    val entries: List<Entry>
)