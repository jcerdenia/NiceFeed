package com.joshuacerdenia.android.nicefeed.work

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.joshuacerdenia.android.nicefeed.MainActivity
import com.joshuacerdenia.android.nicefeed.NOTIFICATION_CHANNEL_ID
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.FeedParser
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.utils.sortedByDatePublished

private const val TAG = "LatestEntriesWorker"

class LatestEntriesWorker(
    val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val repository = NiceFeedRepository.get()
    private val parser = FeedParser()
    private val resources = context.resources

    companion object {
        const val WORK_NAME = "com.joshuacerdenia.android.nicefeed.work.LatestEntriesWorker"
    }

    override suspend fun doWork(): Result {
        val feedUrls = repository.getAllFeedUrlsSync()
        val url = feedUrls.shuffled().first()
        val currentEntryIds = repository.getEntryIdsByFeedIdSync(url)
        val feedWithEntries: FeedWithEntries? = parser.getFeedSynchronously(url)

        return if (feedWithEntries != null) {
            val newEntries = feedWithEntries.entries.toMutableList()
            for (entry in newEntries) {
                if (currentEntryIds.contains(entry.url)) {
                    newEntries.remove(entry)
                }
            }

            if (newEntries.isNotEmpty()) {
                val notification = createNotification(
                    feedWithEntries.feed.title,
                    newEntries.sortedByDatePublished().first().title
                )
                repository.addEntriesAndCrossRefs(newEntries, feedWithEntries.feed.url)
                NotificationManagerCompat.from(context).notify(0, notification)
            }

            Result.success()
        } else {
            Result.failure()
        }
    }

    private fun createNotification(feedTitle: String, entryTitle: String): Notification {
        val intent = MainActivity.newIntent(context) // TODO open with feedId
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setTicker(resources.getString(R.string.new_entries_notification_title, feedTitle))
            .setSmallIcon(R.drawable.ic_rss_feed)
            .setContentTitle(resources.getString(R.string.new_entries_notification_title, feedTitle))
            .setContentText(entryTitle)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }
}