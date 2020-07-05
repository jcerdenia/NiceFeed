package com.joshuacerdenia.android.nicefeed.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.joshuacerdenia.android.nicefeed.Entry

@Dao
interface EntryDao {

    @Query("SELECT * FROM Entry")
    fun getEntries(): LiveData<List<Entry>>

    @Query("SELECT * FROM Entry WHERE website = :website")
    fun getEntriesByFeed(website: String): LiveData<List<Entry>>

    @Query("SELECT * FROM Entry WHERE guid = :guid")
    fun getEntry(guid: String?): LiveData<Entry?>

    @Update
    fun updateEntry(entry: Entry)

    @Update
    fun updateEntries(entries: List<Entry>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEntry(entry: Entry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEntries(entries: List<Entry>)

    @Delete
    fun deleteEntry(entry: Entry)

    @Delete
    fun deleteEntries(entries: List<Entry>)
}