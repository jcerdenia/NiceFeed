package com.joshuacerdenia.android.nicefeed

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.res.Configuration
import android.os.Build
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.data.local.database.NiceFeedDatabase
import com.joshuacerdenia.android.nicefeed.ui.OnBackgroundWorkSettingChanged
import com.joshuacerdenia.android.nicefeed.utils.NetworkMonitor
import com.joshuacerdenia.android.nicefeed.utils.Utils
import com.joshuacerdenia.android.nicefeed.utils.work.NewEntriesWorker
import com.joshuacerdenia.android.nicefeed.utils.work.SweeperWorker
import com.joshuacerdenia.android.nicefeed.utils.work.UpdateAllWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val NOTIFICATION_CHANNEL_ID = "nicefeed_new_entries"

class NiceFeedApplication : Application(), OnBackgroundWorkSettingChanged {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Utils.setTheme(NiceFeedPreferences.getTheme(this))
        when (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {}
            Configuration.UI_MODE_NIGHT_NO -> {}
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {}
        }

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
        val shouldSyncInBackground = NiceFeedPreferences.syncInBackground(this)

        applicationScope.launch {
            if (shouldPoll) NewEntriesWorker.start(applicationContext)
            if (shouldSyncInBackground) {
                UpdateAllWorker.startRecurring(applicationContext)
            } else UpdateAllWorker.cancel(applicationContext)
            SweeperWorker.setup(applicationContext)
        }
    }

    override fun onBackgroundWorkSettingChanged(isOn: Boolean) {
        if (isOn) NewEntriesWorker.start(this) else NewEntriesWorker.cancel(this)
    }
}