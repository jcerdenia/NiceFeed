package com.joshuacerdenia.android.nicefeed.util

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.THEME_DARK
import com.joshuacerdenia.android.nicefeed.THEME_LIGHT

// General methods needed in multiple places

object Utils {

    fun openLink(context: Context, view: View?, url: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, url)

        // TODO: Figure out why below code doesn't work?
        // It's meant to check if an activity is available to handle the intent.

        /*
        val resolvedActivity = context.packageManager
            .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (resolvedActivity != null) {
            startActivity(context, intent, null)
        } else {
            view?.let { showErrorMessage(it, context.resources) }
        }
        */

        startActivity(context, intent, null)
    }

    fun copyLinkToClipboard(context: Context, stringUrl: String, view: View? = null) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        ClipData.newPlainText("link", stringUrl).run { clipboard.setPrimaryClip(this) }
        view?.let { Snackbar.make(it, context.getString(R.string.copied_link), Snackbar.LENGTH_SHORT).show() }
    }

    fun setTheme(theme: Int) {
        when (theme) {
            THEME_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            THEME_DARK -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }.let { AppCompatDelegate.setDefaultNightMode(it) }
    }

    fun setStatusBarMode(activity: Activity) {
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val statusBarContentColor = if (
                activity.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES
            ) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            window?.apply {
                decorView.systemUiVisibility = statusBarContentColor //statusBarColor
                statusBarColor = Color.TRANSPARENT
            }
        } else {
            window?.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = Color.GRAY
            }
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

    fun showErrorMessage(view: View, resources: Resources) {
        Snackbar.make(view, resources.getString(R.string.error_message), Snackbar.LENGTH_SHORT).show()
    }
}