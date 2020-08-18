package com.joshuacerdenia.android.nicefeed.work

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.text.HtmlCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.joshuacerdenia.android.nicefeed.MainActivity
import com.joshuacerdenia.android.nicefeed.NOTIFICATION_CHANNEL_ID
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.local.UserPreferences
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.FeedIdPair
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.data.remote.FeedParser
import com.joshuacerdenia.android.nicefeed.utils.sortedByDatePublished

private const val TAG = "LatestEntriesWorker"

class LatestEntriesWorker(
    val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val repository = NiceFeedRepository.get()
    private val feedParser = FeedParser()
    private val resources = context.resources

    companion object {
        const val WORK_NAME = "com.joshuacerdenia.android.nicefeed.work.LatestEntriesWorker"
        const val ACTION_SHOW_NOTIFICATION = "com.joshuacerdenia.android.nicefeed.work.SHOW_NOTIFICATION"
        const val NOTIFICATION_ID = 0
        const val PERM_PRIVATE = "com.joshuacerdenia.android.nicefeed.PRIVATE"
        const val EXTRA_REQUEST_CODE = "REQUEST_CODE"
        const val EXTRA_NOTIFICATION = "NOTIFICATION"
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "Background work started")
        val feedUrls = repository.getAllFeedUrlsSync()
        val lastIndex = UserPreferences.getLastPolledIndex(context)
        val newIndex = if (lastIndex + 1 >= feedUrls.size) {
            0
        } else {
            lastIndex + 1
        }

        val url = feedUrls[newIndex]
        val currentEntryIds = repository.getEntryIdsByFeedIdSync(url)
        val feedWithEntries: FeedWithEntries? = feedParser.getFeedSynchronously(url)
        UserPreferences.saveLastPolledIndex(context, newIndex)

        return if (feedWithEntries != null) {
            val newEntries = mutableListOf<Entry>()
            for (entry in feedWithEntries.entries) {
                if (!currentEntryIds.contains(entry.url)) {
                    newEntries.add(entry)
                }
            }

            Log.d(TAG, "Got ${newEntries.size} new items in ${feedWithEntries.feed.title}")
            if (newEntries.isNotEmpty()) {
                val notification = createNotification(
                    feedWithEntries.feed.url,
                    feedWithEntries.feed.title,
                    newEntries
                )

                repository.handleLatestEntriesFound(newEntries, feedWithEntries.feed.url)
                Intent(ACTION_SHOW_NOTIFICATION).apply {
                    putExtra(EXTRA_REQUEST_CODE, NOTIFICATION_ID)
                    putExtra(EXTRA_NOTIFICATION, notification)
                }.also { intent ->
                    context.sendOrderedBroadcast(intent, PERM_PRIVATE)
                }
            }

            Result.success()
        } else {
            Result.failure()
        }
    }

    private fun createNotification(
        feedId: String,
        feedTitle: String,
        entries: List<Entry>
    ): Notification {
        val latestEntry = entries.sortedByDatePublished().first()
        val intent = MainActivity.newIntent(context, FeedIdPair(feedId, feedTitle))
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        val text = if (entries.size > 1) {
            resources.getString(R.string.and_more, latestEntry.title)
        } else {
            latestEntry.title
        }.also { text ->
            HtmlCompat.fromHtml(text, 0)
        }

        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setTicker(resources.getString(R.string.new_entries_notification_title, feedTitle))
            .setSmallIcon(R.drawable.ic_nicefeed_notif)
            .setContentTitle(resources.getString(R.string.new_entries_notification_title, feedTitle))
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }
}