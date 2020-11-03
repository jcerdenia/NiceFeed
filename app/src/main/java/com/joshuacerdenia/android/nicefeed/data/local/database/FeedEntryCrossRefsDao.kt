package com.joshuacerdenia.android.nicefeed.data.local.database

import androidx.room.*
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedEntryCrossRef

interface FeedEntryCrossRefsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFeedEntryCrossRefs(crossRefs: List<FeedEntryCrossRef>)

    @Delete
    fun deleteFeedEntryCrossRefs(crossRefs: List<FeedEntryCrossRef>)

    @Query("DELETE FROM FeedEntryCrossRef WHERE feedUrl = :feedId AND entryUrl IN (:entryIds)")
    fun deleteFeedEntryCrossRefs(feedId: String, entryIds: List<String>)

    @Query("DELETE FROM FeedEntryCrossRef WHERE feedUrl IN (:feedId)")
    fun deleteCrossRefsByFeed(vararg feedId: String)

    @Query("DELETE FROM FeedEntryCrossRef WHERE feedUrl NOT IN (SELECT url FROM Feed)")
    fun deleteLeftoverCrossRefs()
}