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

    // Compare new and existing versions of an entry, ignoring certain properties
    fun isSameAs(entry: Entry): Boolean {
        val checklist = listOf(
            entry.url == url,
            entry.title == title,
            entry.author == author,
            entry.date == date,
            entry.content == content,
            entry.image == image
        )
        var count = 0
        for (itemChecked in checklist) {
            if (itemChecked) count += 1 else break
        }
        return count == checklist.size
    }
}