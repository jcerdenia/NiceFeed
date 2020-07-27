package com.joshuacerdenia.android.nicefeed.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.joshuacerdenia.android.nicefeed.data.model.Entry

@Dao
interface EntryDao {

    @Query("SELECT * FROM entry")
    fun getEntries(): LiveData<List<Entry>>

    @Query("SELECT * FROM entry WHERE website = :website")
    fun getEntriesByFeed(website: String): LiveData<List<Entry>>

    @Query("SELECT * FROM entry WHERE guid = :guid")
    fun getEntry(guid: String?): LiveData<Entry?>

    @Query("SELECT * FROM entry WHERE website IN (:websites)")
    fun getEntriesByFeeds(websites: Array<String>): List<Entry>

    @Update
    fun updateEntry(entry: Entry)

    @Update
    fun updateEntries(entries: List<Entry>)

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    fun addEntry(entry: Entry)

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    fun addEntries(entries: List<Entry>)

    @Delete
    fun deleteEntry(entry: Entry)

    @Delete
    fun deleteEntries(entries: List<Entry>)

    @Query("DELETE FROM entry WHERE website IN (:websites)")
    fun deleteEntriesByWebsite(websites: List<String>)
}