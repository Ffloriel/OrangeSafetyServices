package com.example.floriel.orangesafetyservices.feature.firebaseServices

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class FirebaseInstService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        super.onTokenRefresh()
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d("yo dude", "Refreshed token: " + refreshedToken)

        // TODO: Implement this method to send any registration to your app's servers.
        //sendRegistrationToServer(refreshedToken)
    }
}
