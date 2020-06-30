package com.joshuacerdenia.android.nicefeed

import android.content.Context

object SharedPreferences {

    private const val SHARED_PREFS = "SHARED_PREFS"
    private const val KEY_WEBSITE: String = "KEY_WEBSITE"

    fun getSavedWebsite(context: Context): String? {
        val sharedPrefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        return sharedPrefs.getString(KEY_WEBSITE, null)
    }

    fun saveWebsite(context: Context, website: String) {
        val sharedPrefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        sharedPrefs.edit().putString(KEY_WEBSITE, website).apply()
    }
}