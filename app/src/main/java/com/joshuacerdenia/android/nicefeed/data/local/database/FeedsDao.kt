package com.joshuacerdenia.android.nicefeed.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedLight
import com.joshuacerdenia.android.nicefeed.data.model.FeedManageable

interface FeedsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFeeds(vararg feed: Feed)

    @Query("SELECT * FROM Feed WHERE url = :feedId")
    fun getFeed(feedId: String): LiveData<Feed?>

    @Query("SELECT url, title, imageUrl, category, unreadCount FROM Feed")
    fun getFeedsLight(): LiveData<List<FeedLight>>

    @Query("SELECT url, title, website, imageUrl, description, category FROM Feed")
    fun getFeedsManageable(): LiveData<List<FeedManageable>>

    @Query("SELECT url FROM Feed")
    fun getFeedIds(): LiveData<List<String>>

    @Query("SELECT url FROM Feed")
    fun getFeedUrlsSynchronously(): List<String>

    @Update
    fun updateFeed(feed: Feed)

    @Transaction
    fun updateFeedDetails(feedId: String, title: String, category: String) {
        updateFeedTitle(feedId, title)
        updateFeedCategory(feedId, category = category)
    }

    @Query("UPDATE Feed SET title = :title WHERE url = :feedId")
    fun updateFeedTitle(feedId: String, title: String)

    @Query("UPDATE Feed SET category = :category WHERE url IN (:feedId)")
    fun updateFeedCategory(vararg feedId: String, category: String)

    @Query("UPDATE Feed SET unreadCount = :count WHERE url = :feedId")
    fun updateFeedUnreadCount(feedId: String, count: Int)

    @Query("UPDATE Feed SET unreadCount = (unreadCount + :addend) WHERE url = :feedId")
    fun addToFeedUnreadCount(feedId: String, addend: Int)

    @Query(
        "UPDATE Feed SET unreadCount = (unreadCount + :addend) WHERE url IN " +
            "(SELECT url FROM FeedEntryCrossRef AS _junction " +
            "INNER JOIN Feed ON (_junction.feedUrl = Feed.url) " +
            "WHERE _junction.entryUrl = (:entryId))"
    )
    fun addToFeedUnreadCountByEntry(entryId: String, addend: Int)

    @Query("DELETE FROM Feed WHERE url IN (:feedId)")
    fun deleteFeeds(vararg feedId: String)
}