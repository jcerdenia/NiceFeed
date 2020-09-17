package com.joshuacerdenia.android.nicefeed.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest

class ConnectionMonitor(
    private val context: Context,
    private val listener: OnConnectionChangedListener
) {

    interface OnConnectionChangedListener {
        fun onConnectionChanged(isConnected: Boolean)
    }

    fun initialize() {
        val conMan = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // ^ Couldn't resist
        val builder = NetworkRequest.Builder()
        val networkCallback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                listener.onConnectionChanged(true)
            }

            override fun onLost(network: Network) {
                listener.onConnectionChanged(false)
            }
        }

        conMan.registerNetworkCallback(builder.build(), networkCallback)
    }
}