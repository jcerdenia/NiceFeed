package com.joshuacerdenia.android.nicefeed.util.work

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.text.HtmlCompat
import androidx.work.*
import com.joshuacerdenia.android.nicefeed.NiceFeedApplication.Companion.NOTIFICATION_CHANNEL_ID
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.data.model.entry.Entry
import com.joshuacerdenia.android.nicefeed.ui.activity.MainActivity
import com.joshuacerdenia.android.nicefeed.util.extensions.sortedByDate
import java.util.concurrent.TimeUnit

open class NewEntriesWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : BackgroundSyncWorker(context, workerParams) {

    private val resources = context.resources

    override suspend fun doWork(): Result {
        val feedUrls = repo.getFeedUrlsSynchronously()
        if (feedUrls.isEmpty()) return Result.success()
        val lastIndex = NiceFeedPreferences.getLastPolledIndex(context)
        val newIndex = if (lastIndex + 1 >= feedUrls.size) 0 else lastIndex + 1
        val url = feedUrls[newIndex]

        val feedData = repo.getFeedTitleWithEntriesToggleableSynchronously(url)
        val title = feedData.feedTitle // Need user-set title saved in DB
        val storedEntries = feedData.entriesToggleable
        val storedEntryIds: List<String> = storedEntries.map { it.url }
        val feedWithEntries: FeedWithEntries? = parser.getFeedSynchronously(url)

        feedWithEntries?.let { fwe ->
            val newEntries = fwe.entries.filterNot { storedEntryIds.contains(it.url) }
            handleRetrievedData(fwe, storedEntries, newEntries)

            if (newEntries.isNotEmpty()) {
                val notification = createNotification(url, title, newEntries)
                Intent(ACTION_SHOW_NOTIFICATION).apply {
                    putExtra(EXTRA_REQUEST_CODE, NOTIFICATION_ID)
                    putExtra(EXTRA_NOTIFICATION, notification)
                }.also { intent -> context.sendOrderedBroadcast(intent, PERM_PRIVATE) }
            }
        }

        NiceFeedPreferences.saveLastPolledIndex(context, newIndex)
        return Result.success()
    }

    private fun createNotification(
        feedId: String,
        feedTitle: String,
        entries: List<Entry>
    ): Notification {
        val latestEntry = entries.sortedByDate().first()
        val intent = MainActivity.newIntent(context, feedId, latestEntry.url)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        )
        val text = if (entries.size > 1) {
            resources.getString(R.string.and_more, latestEntry.title)
        } else {
            latestEntry.title
        }.also { text -> HtmlCompat.fromHtml(text, 0) }

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

    companion object {
        private const val WORK_NAME = "com.joshuacerdenia.android.nicefeed.utils.work.NewEntriesWorker"
        const val ACTION_SHOW_NOTIFICATION = "com.joshuacerdenia.android.nicefeed.utils.work.SHOW_NOTIFICATION"
        const val NOTIFICATION_ID = 1
        const val PERM_PRIVATE = "com.joshuacerdenia.android.nicefeed.PRIVATE"
        const val EXTRA_REQUEST_CODE = "REQUEST_CODE"
        const val EXTRA_NOTIFICATION = "NOTIFICATION"

        fun start(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .build()
            val request = PeriodicWorkRequest.Builder(
                NewEntriesWorker::class.java, 20, TimeUnit.MINUTES
            ).setConstraints(constraints).build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
        }
    }
}