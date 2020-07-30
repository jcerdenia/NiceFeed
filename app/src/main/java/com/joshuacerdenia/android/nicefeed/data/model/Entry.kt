package com.joshuacerdenia.android.nicefeed.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(
    tableName = "entries",
    foreignKeys = [ForeignKey(
        entity = Feed::class,
        parentColumns = ["website"],
        childColumns = ["website"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Entry(
    @PrimaryKey val guid: String, // Doubles as URL
    val website: String, // associates Entry with a particular Feed
    val title: String,
    val description: String?,
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