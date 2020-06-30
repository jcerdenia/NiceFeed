package com.joshuacerdenia.android.nicefeed

import android.app.Application

class NiceFeedApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Repository.initialize(this)
    }
}