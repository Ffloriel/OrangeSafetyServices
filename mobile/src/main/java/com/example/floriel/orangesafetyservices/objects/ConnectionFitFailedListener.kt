package com.example.floriel.orangesafetyservices.objects

import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

class ConnectionFitFailedListener : GoogleApiClient.OnConnectionFailedListener {
    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("ueue", "On connection failed")
        Log.i("ueuee", "Google Play services connection failed. Cause: " +
                p0.toString())
    }

}