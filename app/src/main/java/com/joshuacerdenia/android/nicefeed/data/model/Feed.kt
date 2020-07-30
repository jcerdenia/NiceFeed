package com.joshuacerdenia.android.nicefeed.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "feeds")
data class Feed(
    @PrimaryKey val website: String, // aka feed ID
    val url: String,
    val title: String,
    val description: String? = null,
    //val updated: Date? = null,
    val imageUrl: String? = null,
    var category: String = "Uncategorized",
    var unreadCount: Int
): Serializable