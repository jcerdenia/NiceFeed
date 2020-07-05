package com.joshuacerdenia.android.nicefeed.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.joshuacerdenia.android.nicefeed.Entry
import com.joshuacerdenia.android.nicefeed.Feed
import com.joshuacerdenia.android.nicefeed.FeedWithEntries

@Dao
interface FeedDao {

    @Query("SELECT * FROM Feed")
    fun getFeeds(): LiveData<List<Feed>>

    @Transaction
    @Query("SELECT * FROM Feed")
    fun getFeedsWithEntries(): List<FeedWithEntries>

    @Query("SELECT * FROM Feed WHERE website = :website")
    fun getFeed(website: String): LiveData<Feed?>

    @Transaction
    @Query("SELECT * FROM Feed WHERE website = :website")
    fun getFeedWithEntries(website: String): LiveData<FeedWithEntries>

    @Update
    fun updateFeed(feed: Feed)

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
}