package com.joshuacerdenia.android.nicefeed.work

import android.app.Activity
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

private const val TAG = "NotificationReceiver"

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (resultCode != Activity.RESULT_OK) {
            return
        } else {
            val requestCode = intent.getIntExtra(LatestEntriesWorker.EXTRA_REQUEST_CODE, 0)
            val notification: Notification? = intent.getParcelableExtra(LatestEntriesWorker.EXTRA_NOTIFICATION)
            if (notification != null) {
                NotificationManagerCompat.from(context).notify(requestCode, notification)
            }
        }
    }
}