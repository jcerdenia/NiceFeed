package com.joshuacerdenia.android.nicefeed.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences

// General methods needed in multiple places

object Utils {

    fun openLink(context: Context, view: View, url: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, url)
        // Check if activity is available to handle intent
        val resolvedActivity = context.packageManager.resolveActivity(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        if (resolvedActivity != null) {
            startActivity(context, intent, null)
        } else {
           showErrorMessage(view, context.resources)
        }
    }

    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setTheme(theme: Int) {
        when (theme) {
            NiceFeedPreferences.THEME_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            NiceFeedPreferences.THEME_DARK -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }.let { mode ->
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }

    fun showErrorMessage(view: View, resources: Resources) {
        Snackbar.make(
            view,
            resources.getString(R.string.error_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}