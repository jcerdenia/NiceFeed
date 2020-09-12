package com.joshuacerdenia.android.nicefeed.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Feed(
    @PrimaryKey val url: String, // Feed ID
    var title: String,
    var website: String,
    val description: String? = null,
    val imageUrl: String? = null,
    var category: String = "Uncategorized",
    var unreadCount: Int
): Serializable {

    fun updateUnreadCount() {
        // TODO
    }
}