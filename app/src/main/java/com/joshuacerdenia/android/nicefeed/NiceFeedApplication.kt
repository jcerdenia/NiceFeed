package com.joshuacerdenia.android.nicefeed

import android.app.Application
import com.joshuacerdenia.android.nicefeed.data.Repository

class NiceFeedApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Repository.initialize(this)
    }
}