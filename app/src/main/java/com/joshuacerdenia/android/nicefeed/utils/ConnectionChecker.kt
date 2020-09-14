package com.joshuacerdenia.android.nicefeed.utils

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R

object ConnectionChecker {

    fun isConnected(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager?.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            // TODO: Replace the following deprecated methods
            val networkInfo = connectivityManager?.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }

    fun showNoConnectionMessage(view: View, resources: Resources) {
        Snackbar.make(
            view,
            resources.getString(R.string.no_internet_connection),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}