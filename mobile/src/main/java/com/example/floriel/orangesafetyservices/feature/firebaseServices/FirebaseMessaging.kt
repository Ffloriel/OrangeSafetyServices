package com.example.floriel.orangesafetyservices.feature.firebaseServices

import android.util.Log
import com.example.floriel.orangesafetyservices.view.notification.NewDisasterNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessaging : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("truc", remoteMessage.data["event"])
        NewDisasterNotification.notify(this.applicationContext, remoteMessage.data?.get("event")!!, 1)
    }

}
