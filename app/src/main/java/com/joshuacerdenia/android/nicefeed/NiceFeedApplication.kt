package com.joshuacerdenia.android.nicefeed

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.*
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.work.LatestEntriesWorker
import com.joshuacerdenia.android.nicefeed.work.SweepEntriesWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

const val NOTIFICATION_CHANNEL_ID = "nicefeed_latest_entries"

class NiceFeedApplication : Application(), ManagingActivity.OnBackgroundWorkSettingListener {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        NiceFeedRepository.initialize(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager?.createNotificationChannel(channel)
        }

        delayedInit()
    }

    // TODO create a method to toggle background work based on current user setting.

    private fun delayedInit() {
        applicationScope.launch {
            setupLatestEntriesWork()
            setupSweepEntriesWork()
        }
    }

    private fun setupLatestEntriesWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .build()
        val periodicRequest = PeriodicWorkRequest.Builder(
            LatestEntriesWorker::class.java,
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            LatestEntriesWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest
        )
    }

    private fun setupSweepEntriesWork() {
        val periodicRequest = PeriodicWorkRequest.Builder(
            SweepEntriesWorker::class.java,
            1,
            TimeUnit.DAYS
        ).build()

        WorkManager.getInstance(this).enqueue(periodicRequest)
    }

    override fun onBackgroundWorkSettingChanged(isOn: Boolean) {
        // TODO: Cancel background worker
    }
}