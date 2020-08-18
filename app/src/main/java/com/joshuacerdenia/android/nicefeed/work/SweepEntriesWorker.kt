package com.joshuacerdenia.android.nicefeed.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository

class SweepEntriesWorker(
    context: Context,
    workerParams: WorkerParameters
): Worker(context, workerParams) {

    private val repository = NiceFeedRepository.get()

    override fun doWork(): Result {
        repository.deleteFeedLessEntries()
        return Result.success()
    }
}