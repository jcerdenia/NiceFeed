package com.joshuacerdenia.android.nicefeed.utils.work

import android.content.Context
import androidx.work.*
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.data.remote.FeedParser
import java.util.concurrent.TimeUnit

class UpdateAllWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val repo = NiceFeedRepository.get()
    private val feedParser = FeedParser(repo.networkMonitor)

    override suspend fun doWork(): Result {
        val feedUrls = repo.getFeedUrlsSynchronously()
        if (feedUrls.isEmpty()) return Result.success()

        for (url in feedUrls) {
            val currentEntryIds: List<String> = repo.getEntryIdsByFeedSynchronously(url)
            val feedWithEntries: FeedWithEntries? = feedParser.getFeedSynchronously(url)

            feedWithEntries?.entries?.let { entries ->
                val entryIds = entries.map { it.url }
                val newEntries = entries.filterNot { currentEntryIds.contains(it.url) }
                val oldEntryIds = currentEntryIds.filterNot { entryIds.contains(it) }
                repo.handleBackgroundUpdate(url, newEntries, oldEntryIds)
            }
        }

        return Result.success()
    }

    companion object {
        private const val WORK_NAME = "com.joshuacerdenia.android.nicefeed.utils.work.UpdateAllWorker"

        private val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .build()

        fun start(context: Context) {
            val request = OneTimeWorkRequest.Builder(UpdateAllWorker::class.java)
                .setConstraints(constraints).build()
            WorkManager.getInstance(context).enqueue(request)
        }

        fun startRecurring(context: Context) {
            val request = PeriodicWorkRequest.Builder(
                UpdateAllWorker::class.java, 24, TimeUnit.HOURS
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