package com.joshuacerdenia.android.nicefeed.util.extensions

import android.text.Editable

fun String.pathified() = this.substringAfter(
    "www.",
    this.substringAfter("://")
).removeSuffix("/")

fun String.simplified() = this.pathified().substringBefore("?")

fun String.shortened() = this.simplified().substringBefore("/")

fun String?.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)