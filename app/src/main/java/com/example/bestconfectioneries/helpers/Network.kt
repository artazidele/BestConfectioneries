package com.example.bestconfectioneries.helpers

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat

class Network {
    public fun checkConnection(context: Context): Boolean {
        var isConnection = false
        val connectionManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiConnection = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobileDataConnection =
            connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifiConnection!!.isConnectedOrConnecting || mobileDataConnection!!.isConnectedOrConnecting) {
            isConnection = true
        }
        return isConnection
    }
}