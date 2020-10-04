package com.joshuacerdenia.android.nicefeed.utils.work

import android.content.Context
import androidx.work.*
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import java.util.concurrent.TimeUnit

class SweeperWorker(
    context: Context,
    workerParams: WorkerParameters
): Worker(context, workerParams) {

    private val repo = NiceFeedRepository.get()

    override fun doWork(): Result {
        repo.deleteLeftoverItems() // Just in case
        return Result.success()
    }

    companion object {
        private const val WORK_NAME = "com.joshuacerdenia.android.nicefeed.utils.work.SweeperWorker"

        fun setup(context: Context) {
            val request = PeriodicWorkRequest.Builder(
                SweeperWorker::class.java,
                3,
                TimeUnit.DAYS
            ).build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}