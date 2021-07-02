package com.joshuacerdenia.android.nicefeed.data.model.entry

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
    var image: String?,
    var isStarred: Boolean = false,
    var isRead: Boolean = false
) : Serializable {

    // Compare new and existing versions of the same entry, ignoring certain properties
    fun isSameAs(entry: Entry): Boolean {
        val checklist = listOf(
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
        // ALL items must match to return true
        return count == checklist.size
    }
}