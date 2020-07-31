package com.joshuacerdenia.android.nicefeed.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedMinimal
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries

@Dao
interface FeedsAndEntriesDao {

    @Query("SELECT * FROM feeds")
    fun getAllFeeds(): LiveData<List<Feed>>

    @Query("SELECT website, title, category FROM feeds")
    fun getAllFeedsMinimal(): LiveData<List<FeedMinimal>>

    @Query("SELECT website FROM feeds")
    fun getAllFeedIds(): LiveData<List<String>>

    @Query("SELECT unreadCount FROM feeds WHERE website = :feedId")
    fun getFeedUnreadCount(feedId: String): LiveData<Int>

    @Query("SELECT DISTINCT category FROM feeds")
    fun getCategories(): LiveData<List<String>>

    @Query("SELECT * FROM feeds WHERE website = :id")
    fun getFeedById(id: String): LiveData<Feed>

    @Transaction
    @Query("SELECT * FROM feeds WHERE website = :feedId")
    fun getFeedWithEntriesById(feedId: String): LiveData<FeedWithEntries>

    @Query("DELETE FROM feeds WHERE website IN (:ids)")
    fun deleteFeedsBy(ids: List<String>)

    @Query("DELETE FROM feeds WHERE website = :id")
    fun deleteFeedById(id: String)

    @Query("DELETE FROM entries WHERE website = :feedId")
    fun deleteEntriesByFeedId(feedId: String)

    @Query("DELETE FROM entries WHERE website IN (:feedIds)")
    fun deleteEntriesByFeedIds(feedIds: List<String>)

    @Transaction
    fun deleteFeedAndEntriesById(id: String) {
        deleteFeedById(id)
        deleteEntriesByFeedId(id)
    }

    @Transaction
    fun deleteFeedsAndEntriesByIds(ids: List<String>) {
        deleteFeedsBy(ids)
        deleteEntriesByFeedIds(ids)
    }

    @Query("UPDATE feeds SET category = :category WHERE website IN (:ids)")
    fun updateCategoryByFeedIds(ids: Array<String>, category: String)

    @Query("UPDATE entries SET isRead = :isRead WHERE guid = :guid")
    fun updateEntryIsRead(vararg guid: String, isRead: Boolean)

    @Query("UPDATE entries SET isStarred = :isStarred WHERE guid = :guid")
    fun updateEntryIsStarred(vararg guid: String, isStarred: Boolean)

    @Update
    fun updateFeed(feed: Feed)

    @Query("UPDATE feeds SET unreadCount = :count WHERE website = :id")
    fun updateFeedUnreadCountById(id: String, count: Int)

    @Query("UPDATE feeds SET unreadCount =+ :operator WHERE website = :id")
    fun updateFeedUnreadCountByOperator(id: String, operator: Int)

    @Transaction
    fun updateEntryIsReadAndFeedUnreadCount(id: String, isRead: Boolean, operator: Int) {
        updateEntryIsRead(id, isRead = isRead)
        updateFeedUnreadCountByOperator(id, operator)
    }

    @Update
    fun updateFeeds(feeds: List<Feed>)

    @Insert
    fun addFeed(feed: Feed)

    @Delete
    fun deleteFeed(feed: Feed)

    @Delete
    fun deleteFeeds(feeds: List<Feed>)

    @Delete
    fun deleteFeedsAndEntries(feeds: List<Feed>, entries: List<Entry>)

    @Delete
    fun deleteFeedAndEntries(feed: Feed, entries: List<Entry>)

    @Query("SELECT * FROM entries")
    fun getEntries(): LiveData<List<Entry>>

    @Query("SELECT * FROM entries WHERE website = :feedId")
    fun getEntriesByFeedId(feedId: String): LiveData<List<Entry>>

    @Query("SELECT * FROM entries WHERE guid = :guid")
    fun getEntry(guid: String?): LiveData<Entry?>

    @Query("SELECT * FROM entries WHERE website IN (:websites)")
    fun getEntriesByFeeds(websites: Array<String>): List<Entry>

    @Query("SELECT website FROM entries WHERE isRead")
    fun getUnreadEntryIds(): List<String>

    @Update
    fun updateEntry(entry: Entry)

    @Update
    fun updateEntries(entries: List<Entry>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFeeds(feeds: List<Feed>)

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    fun addEntry(entry: Entry)

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    fun addEntries(entries: List<Entry>)

    @Delete
    fun deleteEntry(entry: Entry)

    @Delete
    fun deleteEntries(entries: List<Entry>)

    @Query("DELETE FROM entries WHERE website IN (:websites)")
    fun deleteEntriesByWebsite(websites: List<String>)

    @Transaction
    fun refreshEntries(toAdd: List<Entry>, toUpdate: List<Entry>, toDelete: List<Entry>) {
        if (toAdd.isNotEmpty()) {
            addEntries(toAdd)
        }

        if (toUpdate.isNotEmpty()) {
            updateEntries(toUpdate)
        }

        if (toDelete.isNotEmpty()) {
            deleteEntries(toDelete)
        }
    }
}