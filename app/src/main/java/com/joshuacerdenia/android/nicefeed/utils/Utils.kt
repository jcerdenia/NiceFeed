package com.joshuacerdenia.android.nicefeed.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.joshuacerdenia.android.nicefeed.data.model.Feed

object Utils {

    fun getCategories(feeds: List<Feed>): List<String> {
        val categories: MutableSet<String> = mutableSetOf()
        for (feed in feeds) {
            categories.add(feed.category)
        }
        return categories.toList().sorted()
    }

    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            // TODO: handle exception
            e.printStackTrace()
        }
    }
}