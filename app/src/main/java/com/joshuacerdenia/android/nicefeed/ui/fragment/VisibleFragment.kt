package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.fragment.app.Fragment
import com.joshuacerdenia.android.nicefeed.work.NewEntriesWorker

// To be extended by all fragments

abstract class VisibleFragment : Fragment() {

    private val onShowNotification = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // Suppress notification when app is in the foreground
            resultCode = Activity.RESULT_CANCELED
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(NewEntriesWorker.ACTION_SHOW_NOTIFICATION)
        requireActivity().registerReceiver(
            onShowNotification,
            filter,
            NewEntriesWorker.PERM_PRIVATE,
            null
        )
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(onShowNotification)
    }
}