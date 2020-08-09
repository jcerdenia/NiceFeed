package com.joshuacerdenia.android.nicefeed.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class Entry(
    @PrimaryKey val url: String, // Doubles as URL
    val title: String,
    val website: String,
    val author: String?,
    val date: Date?,
    val content: String?,
    val image: String?,
    var isStarred: Boolean = false,
    var isRead: Boolean = false
) : Serializable {

    fun isTheSameAs(entry: Entry): Boolean {
        // Compares new and existing versions of an entry, ignoring certain properties
        val checklist = listOf(
            (entry.url == url),
            (entry.title == title),
            (entry.website == website),
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