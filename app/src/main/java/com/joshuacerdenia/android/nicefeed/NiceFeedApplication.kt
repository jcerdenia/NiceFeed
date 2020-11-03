package com.joshuacerdenia.android.nicefeed

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.data.local.database.NiceFeedDatabase
import com.joshuacerdenia.android.nicefeed.utils.NetworkMonitor
import com.joshuacerdenia.android.nicefeed.utils.Utils
import com.joshuacerdenia.android.nicefeed.utils.work.BackgroundSyncWorker
import com.joshuacerdenia.android.nicefeed.utils.work.NewEntriesWorker
import com.joshuacerdenia.android.nicefeed.utils.work.SweeperWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val NOTIFICATION_CHANNEL_ID = "nicefeed_new_entries"

class NiceFeedApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Utils.setTheme(NiceFeedPreferences.getTheme(this))
        val database = NiceFeedDatabase.build(this)
        val connectionMonitor = NetworkMonitor(this)
        NiceFeedRepository.initialize(database, connectionMonitor)

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

    private fun delayedInit() {
        val isPolling = NiceFeedPreferences.getPollingSetting(this)
        val isSyncing = NiceFeedPreferences.syncInBackground(this)

        applicationScope.launch {
            SweeperWorker.start(applicationContext)
            if (isPolling) NewEntriesWorker.start(applicationContext)
            if (isSyncing) {
                BackgroundSyncWorker.start(applicationContext)
            } else {
                BackgroundSyncWorker.cancel(applicationContext)
            }
        }
    }
}