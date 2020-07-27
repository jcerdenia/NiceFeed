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
    //var url: String? = null, // Probably not needed
    var date: Date? = null,
    var content: String? = null,
    var image: String? = null,
    //var audio: String? = null,
    //var video: String? = null,
    var isStarred: Boolean = false,
    var isRead: Boolean = false
) : Serializable {

    fun isTheSameAs(entry: Entry): Boolean {
        // Compares new and existing versions of an entry, ignoring certain properties
        val checklist = arrayOf(
            (entry.guid == guid),
            (entry.title == title),
            (entry.description == description),
            (entry.author == author),
            (entry.date == date),
            (entry.content == content),
            (entry.image == image)
        )

        var count = 0
        for (check in checklist) {
            if (check) count += 1 else break
        }

        return count == checklist.size
    }
}