package com.joshuacerdenia.android.nicefeed.utils

import android.app.Activity
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.joshuacerdenia.android.nicefeed.utils.work.NewEntriesWorker

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (resultCode != Activity.RESULT_OK) {
            return
        } else {
            val requestCode = intent.getIntExtra(NewEntriesWorker.EXTRA_REQUEST_CODE, 0)
            val notification: Notification? = intent.getParcelableExtra(NewEntriesWorker.EXTRA_NOTIFICATION)
            notification?.let { NotificationManagerCompat.from(context).notify(requestCode, it) }
        }
    }
}