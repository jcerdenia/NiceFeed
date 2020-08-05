package com.joshuacerdenia.android.nicefeed.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.joshuacerdenia.android.nicefeed.data.model.*

@Dao
interface FeedsAndEntriesDao {

    // Methods for Feeds

    @Query("SELECT * FROM Feed")
    fun getAllFeeds(): LiveData<List<Feed>>

    @Query("SELECT url FROM Feed")
    fun getAllFeedIds(): LiveData<List<String>>

    @Query("SELECT url, website, title, category FROM Feed")
    fun getAllFeedsMinimal(): LiveData<List<FeedMinimal>>

    @Query("SELECT * FROM Feed WHERE url = :feedId")
    fun getFeedById(feedId: String): LiveData<Feed>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFeed(feed: Feed)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFeeds(feeds: List<Feed>)

    @Update
    fun updateFeed(feed: Feed)

    @Query("UPDATE Feed SET category = :category WHERE url IN (:ids)")
    fun updateCategoryByFeedIds(ids: Array<String>, category: String)

    @Query("UPDATE Feed SET unreadCount = :count WHERE url = :id")
    fun updateFeedUnreadCountById(id: String, count: Int)

    @Delete
    fun deleteFeed(feed: Feed)

    @Query("DELETE FROM Feed WHERE url = :id")
    fun deleteFeedById(id: String)

    @Delete
    fun deleteFeeds(feeds: List<Feed>)

    @Query("DELETE FROM Feed WHERE url IN (:ids)")
    fun deleteFeedsByIds(ids: List<String>)

    // Methods for Entries

    @Query("SELECT * FROM Entry")
    fun getAllEntries(): LiveData<List<Entry>>

    @Query("SELECT * FROM Entry WHERE guid = :guid")
    fun getEntry(guid: String?): LiveData<Entry>

    @Query("SELECT Entry.guid, title, website, author, date, content, image, isStarred, isRead " +
            "FROM FeedEntryCrossRef AS _junction " +
            "INNER JOIN Entry ON (_junction.guid = Entry.guid) " +
            "WHERE _junction.url = (:feedId)")
    fun getEntriesByFeedId(feedId: String): LiveData<List<Entry>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addEntry(entry: Entry)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addEntries(entries: List<Entry>)

    @Update
    fun updateEntry(entry: Entry)

    @Update
    fun updateEntries(entries: List<Entry>)

    @Delete
    fun deleteEntry(entry: Entry)

    @Delete
    fun deleteEntries(entries: List<Entry>)

    @Query("DELETE FROM Entry WHERE (" +
            "SELECT _junction.url " +
            "FROM FeedEntryCrossRef AS _junction INNER JOIN Entry ON (_junction.guid = Entry.guid)" +
            ") = (:feedId)")
    fun deleteEntriesByFeedId(feedId: String)

    @Query("DELETE FROM Entry WHERE (" +
            "SELECT _junction.url " +
            "FROM FeedEntryCrossRef AS _junction INNER JOIN Entry ON (_junction.guid = Entry.guid)" +
            ") IN (:feedIds)")
    fun deleteEntriesByFeedIds(feedIds: List<String>)

    // Combined Methods

    @Transaction
    @Query("SELECT * FROM Feed")
    fun getAllFeedsWithEntries(): LiveData<List<FeedWithEntries>>

    @Transaction
    @Query("SELECT * FROM Feed WHERE url = :feedId")
    fun getFeedWithEntriesByFeedId(feedId: String): LiveData<FeedWithEntries>

    @Transaction
    @Query("SELECT url, website, title, description, imageUrl, category FROM Feed WHERE url = :feedId")
    fun getFeedSansUnreadCountWithEntriesByFeedId(feedId: String): LiveData<FeedSansUnreadCountWithEntries>

    @Transaction
    fun addFeedAndEntries(feed: Feed, entries: List<Entry>, crossRefs: List<FeedEntryCrossRef>) {
        addFeed(feed)
        addEntries(entries)
        addFeedEntryCrossRefs(crossRefs)
    }

    @Transaction
    fun deleteFeedAndEntries(feed: Feed, entries: List<Entry>, crossRefs: List<FeedEntryCrossRef>) {
        deleteEntries(entries)
        deleteFeedEntryCrossRefs(crossRefs)
        deleteFeed(feed)
    }

    @Transaction
    fun deleteFeedAndEntriesById(feedId: String) {
        deleteEntriesByFeedId(feedId)
        deleteCrossRefsByFeedId(feedId)
        deleteFeedById(feedId)
    }

    @Transaction
    fun deleteFeedsAndEntriesByIds(feedIds: List<String>) {
        deleteEntriesByFeedIds(feedIds)
        deleteCrossRefsByFeedIds(feedIds)
        deleteFeedsByIds(feedIds)
    }

    @Transaction
    fun refreshEntries(
        toAdd: List<Entry>,
        toUpdate: List<Entry>,
        toDelete: List<Entry>,
        crossRefsToAdd: List<FeedEntryCrossRef>,
        crossRefsToDelete: List<FeedEntryCrossRef>
    ) {
        addEntries(toAdd)
        addFeedEntryCrossRefs(crossRefsToAdd)
        updateEntries(toUpdate)
        deleteFeedEntryCrossRefs(crossRefsToDelete)
        deleteEntries(toDelete)
    }

    // Relational Table Methods

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFeedEntryCrossRef(crossRef: FeedEntryCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFeedEntryCrossRefs(crossRefs: List<FeedEntryCrossRef>)

    @Delete
    fun deleteFeedEntryCrossRefs(crossRefs: List<FeedEntryCrossRef>)

    @Query("DELETE FROM FeedEntryCrossRef WHERE url = :feedId")
    fun deleteCrossRefsByFeedId(feedId: String)

    @Query("DELETE FROM FeedEntryCrossRef WHERE url IN (:feedIds)")
    fun deleteCrossRefsByFeedIds(feedIds: List<String>)
}