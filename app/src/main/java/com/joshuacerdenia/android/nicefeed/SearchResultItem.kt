package com.joshuacerdenia.android.nicefeed

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchResultItem(
    val title: String? = null,
    @SerializedName("feedId") val id: String? = null,
    val website: String? = null,
    val description: String? = null,
    val updated: String? = null,
    //val topics: List<String>? = null,
    @SerializedName("visualUrl") val imageUrl: String? = null
): Serializable