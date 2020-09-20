package com.joshuacerdenia.android.nicefeed

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.data.local.database.NiceFeedDatabase
import com.joshuacerdenia.android.nicefeed.ui.OnBackgroundWorkSettingChanged
import com.joshuacerdenia.android.nicefeed.utils.NetworkMonitor
import com.joshuacerdenia.android.nicefeed.utils.Utils
import com.joshuacerdenia.android.nicefeed.work.NewEntriesWorker
import com.joshuacerdenia.android.nicefeed.work.SweeperWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val NOTIFICATION_CHANNEL_ID = "nicefeed_new_entries"

@HiltAndroidApp
class NiceFeedApplication : Application(), OnBackgroundWorkSettingChanged {

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
        val shouldPoll = NiceFeedPreferences.getPollingSetting(this)
        applicationScope.launch {
            SweeperWorker.setup(applicationContext)
            if (shouldPoll) {
                NewEntriesWorker.setup(applicationContext)
            }
        }
    }

    override fun onBackgroundWorkSettingChanged(isOn: Boolean) {
        if (isOn) {
            NewEntriesWorker.setup(this)
        } else {
            NewEntriesWorker.cancel(this)
        }
    }
}