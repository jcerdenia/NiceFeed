package com.joshuacerdenia.android.nicefeed

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Entry(
    @PrimaryKey var guid: String = "",
    var website: String? = null, // formerly feedLink
    var title: String? = null,
    var description: String? = null,
    var author: String? = null,
    var url: String? = null,
    var date: String? = null,
    var content: String? = null,
    var image: String? = null,
    var audio: String? = null,
    var video: String? = null,
    var categories: String = listOf<String>().toString(),
    var isUnread: Boolean = true
): Serializable