package com.joshuacerdenia.android.nicefeed

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.work.*
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.ui.activity.ManagingActivity
import com.joshuacerdenia.android.nicefeed.work.NewEntriesWorker
import com.joshuacerdenia.android.nicefeed.work.SweeperWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

private const val TAG = "NiceFeedApplication"
const val NOTIFICATION_CHANNEL_ID = "nicefeed_new_entries"

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

        NiceFeedPreferences.getPollingSetting(this).run {
            delayedInit(this)
        }
    }

    private fun delayedInit(shouldPoll: Boolean) {
        applicationScope.launch {
            if (shouldPoll) {
                setupNewEntriesWork()
            }
            setupSweeperWork()
        }
    }

    private fun setupNewEntriesWork() {
        Log.d(TAG, "Setting up polling for new entries...")

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .build()
        val request = PeriodicWorkRequest.Builder(
            NewEntriesWorker::class.java,
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            NewEntriesWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    private fun setupSweeperWork() {
        val request = PeriodicWorkRequest.Builder(
            SweeperWorker::class.java,
            3,
            TimeUnit.DAYS
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            SweeperWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    override fun onBackgroundWorkSettingChanged(isOn: Boolean) {
        if (isOn) {
            setupNewEntriesWork()
        } else {
            WorkManager.getInstance(this).cancelUniqueWork(NewEntriesWorker.WORK_NAME)
            Log.d(TAG, "Background work cancelled!")
        }
    }
}