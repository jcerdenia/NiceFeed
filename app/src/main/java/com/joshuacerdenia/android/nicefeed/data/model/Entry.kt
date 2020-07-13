package com.joshuacerdenia.android.nicefeed.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class Entry(
    @PrimaryKey var guid: String = "",
    var website: String? = null, // associates Entry with a particular Feed
    var title: String? = null,
    var description: String? = null,
    var author: String? = null,
    var url: String? = null,
    var date: Date? = null,
    var content: String? = null,
    var image: String? = null,
    var audio: String? = null,
    var video: String? = null,
    var isRead: Boolean = false
): Serializable {

    fun isTheSameAs(entry: Entry): Boolean {
        // Determines whether an entry's contents are the same, except for isRead property
        return guid == entry.guid &&
                title == entry.title &&
                description == entry.description &&
                author == entry.author &&
                //url == entry.url &&
                date == entry.date &&
                content == entry.content &&
                image == entry.image &&
                audio == entry.audio &&
                video == entry.video
    }
}