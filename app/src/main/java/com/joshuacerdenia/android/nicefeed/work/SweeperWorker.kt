package com.joshuacerdenia.android.nicefeed.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository

class SweeperWorker(
    context: Context,
    workerParams: WorkerParameters
): Worker(context, workerParams) {

    private val repository = NiceFeedRepository.get()

    override fun doWork(): Result {
        repository.deleteLeftoverItems()
        return Result.success()
    }

    companion object {
        const val WORK_NAME = "com.joshuacerdenia.android.nicefeed.work.SweeperWorker"
    }
}