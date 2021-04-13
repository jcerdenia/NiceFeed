package com.joshuacerdenia.android.nicefeed.data.local.database.dao

import androidx.room.*
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedEntryCrossRef
import com.joshuacerdenia.android.nicefeed.data.model.entry.Entry

interface FeedEntryCrossRefsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFeedEntryCrossRefs(crossRefs: List<FeedEntryCrossRef>)

    @Transaction
    fun addFeedEntryCrossRefs(feedId: String, entries: List<Entry>) {
        addFeedEntryCrossRefs(entries.map { FeedEntryCrossRef(feedId, it.url) })
    }

    @Query("DELETE FROM FeedEntryCrossRef WHERE feedUrl = :feedId AND entryUrl IN (:entryIds)")
    fun deleteFeedEntryCrossRefs(feedId: String, entryIds: List<String>)

    @Query("DELETE FROM FeedEntryCrossRef WHERE feedUrl IN (:feedId)")
    fun deleteCrossRefsByFeed(vararg feedId: String)

    @Query("DELETE FROM FeedEntryCrossRef WHERE feedUrl NOT IN (SELECT url FROM Feed)")
    fun deleteLeftoverCrossRefs()
}