package com.joshuacerdenia.android.nicefeed.utils.work

import android.content.Context
import androidx.work.*
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.cross.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.data.model.entry.Entry
import com.joshuacerdenia.android.nicefeed.data.model.entry.EntryUsed
import com.joshuacerdenia.android.nicefeed.data.remote.FeedParser
import java.util.concurrent.TimeUnit

open class BackgroundSyncWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    val repo = NiceFeedRepository.get()
    val parser = FeedParser(repo.networkMonitor)
    
    override suspend fun doWork(): Result {
        val feedUrls = repo.getFeedUrlsSynchronously()
        if (feedUrls.isEmpty()) return Result.success()

        for (url in feedUrls) {
            val storedEntries = repo.getEntriesUsedByFeedSynchronously(url)
            val storedEntryIds: List<String> = storedEntries.map { it.url }
            val feedWithEntries: FeedWithEntries? = parser.getFeedSynchronously(url)

            feedWithEntries?.entries?.let { entries ->
                val newEntries = entries.filterNot { storedEntryIds.contains(it.url) }
                handleRetrievedData(url, storedEntries, entries, newEntries)
            }
        }

        return Result.success()
    }

    fun handleRetrievedData(
        url: String,
        storedEntries: List<EntryUsed>,
        retrievedEntries: List<Entry>,
        newEntries: List<Entry>
    ) {
        val entryIds = retrievedEntries.map { it.url }
        val oldEntries = storedEntries.filterNot { entryIds.contains(it.url) }
        val entryIdsToDelete = oldEntries.filter { it.isRead && !it.isStarred }.map { it.url }
        repo.handleBackgroundUpdate(url, newEntries, entryIdsToDelete)
    }

    companion object {
        private const val WORK_NAME = "com.joshuacerdenia.android.nicefeed.utils.work.BackgroundSyncWorker"

        private val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .build()

        fun startOnce(context: Context) {
            val request = OneTimeWorkRequest.Builder(BackgroundSyncWorker::class.java)
                .setConstraints(constraints).build()
            WorkManager.getInstance(context).enqueue(request)
        }

        fun startRecurring(context: Context) {
            val request = PeriodicWorkRequest.Builder(
                BackgroundSyncWorker::class.java, 24, TimeUnit.HOURS
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