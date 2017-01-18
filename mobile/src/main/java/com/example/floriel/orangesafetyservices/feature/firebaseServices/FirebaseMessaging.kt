package com.example.floriel.orangesafetyservices.feature.firebaseServices

import com.example.floriel.orangesafetyservices.view.notification.NewDisasterNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessaging : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        NewDisasterNotification.notify(this.applicationContext, remoteMessage?.data?.get("event")!!, 1)
    }

}
