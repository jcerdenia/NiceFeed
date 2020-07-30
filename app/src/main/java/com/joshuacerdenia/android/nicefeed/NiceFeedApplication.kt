package com.joshuacerdenia.android.nicefeed

import android.app.Application
import com.joshuacerdenia.android.nicefeed.data.NiceFeedRepository

class NiceFeedApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NiceFeedRepository.initialize(this)
    }
}