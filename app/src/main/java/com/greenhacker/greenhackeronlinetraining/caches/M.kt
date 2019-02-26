package com.greenhacker.greenhackeronlinetraining.caches

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

object M {
    fun isConnectionAvailable(context: Context): Boolean {

        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val netInfo = connectivityManager.activeNetworkInfo
            if (netInfo != null && netInfo.isConnected
                    && netInfo.isConnectedOrConnecting
                    && netInfo.isAvailable) {
                return true
            }
        }
        return false
    }


    fun l(TAG: String, e: Throwable) {
        Log.e(TAG, "", e)
    }
}
