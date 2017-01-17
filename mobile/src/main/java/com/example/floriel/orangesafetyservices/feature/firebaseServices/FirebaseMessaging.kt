package com.example.floriel.orangesafetyservices.feature.firebaseServices

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessaging : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        //super.onMessageReceived(p0)
        // Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d("Truc", "Notification Message Body: " + remoteMessage?.notification?.body)
    }

}
