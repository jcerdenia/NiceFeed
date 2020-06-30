package com.joshuacerdenia.android.nicefeed

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Feed(
    @PrimaryKey var website: String = "", // formerly link
    var url: String? = null,
    var title: String? = null,
    var description: String? = null,
    var updated: String? = null,
    var imageUrl: String? = null,
    var category: String = "Uncategorized",
    var unreadCount: Int? = null
): Serializable