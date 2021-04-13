package com.joshuacerdenia.android.nicefeed.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest

class NetworkMonitor(context: Context) {

    var isOnline = false
        private set

    private val conMan = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val builder = NetworkRequest.Builder()
    private val callback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            isOnline = true
        }

        override fun onLost(network: Network) {
            isOnline = false
        }
    }

    init {
        conMan.registerNetworkCallback(builder.build(), callback)
    }
}