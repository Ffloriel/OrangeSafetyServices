package com.example.floriel.orangesafetyservices.objects

import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient

class GoogleFitConnectionCallbacks : GoogleApiClient.ConnectionCallbacks {
    override fun onConnected(p0: Bundle?) {
        Log.d("ueue", "Connected")
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.d("ueue", "On Connection suspended")
    }

}