package com.joshuacerdenia.android.nicefeed.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.joshuacerdenia.android.nicefeed.data.model.Entry

interface EntriesDao {

    @Query("SELECT * FROM Entry WHERE isRead = 0 ORDER BY date DESC LIMIT 50")
    fun getRecentEntries(): LiveData<List<Entry>>

    @Query("SELECT * FROM Entry WHERE isStarred = 1")
    fun getStarredEntries(): LiveData<List<Entry>>

    @Query("SELECT * FROM Entry WHERE url = :entryId")
    fun getEntry(entryId: String): LiveData<Entry?>

    @Query("SELECT Entry.url, title, website, author, date, content, image, isStarred, isRead " +
            "FROM FeedEntryCrossRef AS _junction " +
            "INNER JOIN Entry ON (_junction.entryUrl = Entry.url) " +
            "WHERE _junction.feedUrl = :feedId")
    fun getEntriesByFeed(feedId: String): LiveData<List<Entry>>

    @Query("SELECT Entry.url " +
            "FROM FeedEntryCrossRef AS _junction " +
            "INNER JOIN Entry ON (_junction.entryUrl = Entry.url) " +
            "WHERE _junction.feedUrl = (:feedId)")
    fun getEntryIdsByFeedSynchronously(feedId: String): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addEntries(entries: List<Entry>)

    @Update
    fun updateEntries(entries: List<Entry>)

    @Query("UPDATE Entry SET isStarred = (:isStarred) WHERE url IN (:entryId)")
    fun updateEntryIsStarred(vararg entryId: String, isStarred: Boolean)

    @Query("UPDATE Entry SET isRead = (:isRead) WHERE url IN (:entryId)")
    fun updateEntryIsRead(vararg entryId: String, isRead: Boolean)

    @Delete
    fun deleteEntries(entries: List<Entry>)

    @Query("DELETE FROM Entry WHERE (" +
            "SELECT _junction.feedUrl " +
            "FROM FeedEntryCrossRef AS _junction " +
            "INNER JOIN Entry ON (_junction.entryUrl = Entry.url)" +
            ") IN (:feedId)")
    fun deleteEntriesByFeedId(vararg feedId: String)

    @Query("DELETE FROM Entry WHERE url NOT IN (SELECT entryUrl FROM FeedEntryCrossRef)")
    fun deleteFeedlessEntries()
}