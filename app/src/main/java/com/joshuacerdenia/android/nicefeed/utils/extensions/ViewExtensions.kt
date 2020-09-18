package com.joshuacerdenia.android.nicefeed.utils.extensions

import android.R
import android.util.TypedValue
import android.view.View

fun View.addRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(R.attr.selectableItemBackground, this, true)
    setBackgroundResource(resourceId)
}