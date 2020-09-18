package com.joshuacerdenia.android.nicefeed.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest

class NetworkMonitor(context: Context) {

    var isOnline = false
        private set

    private val conMan = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    // ^ Couldn't resist
    private val builder = NetworkRequest.Builder()
    private val networkCallback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            isOnline = true
        }

        override fun onLost(network: Network) {
            isOnline = false
        }
    }

    init {
        conMan.registerNetworkCallback(builder.build(), networkCallback)
    }
}